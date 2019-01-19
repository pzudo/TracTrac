package group7.tractrac.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import group7.tractrac.R
import kotlinx.android.synthetic.main.custom_cardview_feed.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var listView : ListView
    private lateinit var adapter: FeedAdapter
    private lateinit var loader: LottieAnimationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        listView = view.findViewById(R.id.news_list_view) as ListView
        loader = view.findViewById(R.id.imageLoader) as LottieAnimationView


        listView.visibility = View.GONE

        FirebaseApp.initializeApp(context)
        val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("feed")

        val feed : List<FeedData> = ArrayList<FeedData>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                (feed as ArrayList<FeedData>).clear()

                for (postSnapshot : DataSnapshot in dataSnapshot.children){
                    val data = postSnapshot.getValue(FeedData::class.java)
                    feed.add(data!!)
                }
                adapter = FeedAdapter(context, feed)
                listView.adapter = adapter

                listView.onItemClickListener = object : AdapterView.OnItemClickListener {
                    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                        // value of item that is clicked
                        val itemValue = listView.getItemAtPosition(position) as FeedData

                        val fragment = FeedFragment().apply {
                            arguments = Bundle().apply {
                                this.putString("title", itemValue.name)
                                this.putString("imageUrl", itemValue.imageUrl)
                                this.putString("date", itemValue.date)
                                this.putString("text", itemValue.text)
                            }
                        }

                        fragmentManager!!
                            .beginTransaction()
                            .addToBackStack(tag)
                            .replace(R.id.fragmentFrame, fragment, tag)
                            .commit()

                    }

                }

                loader.visibility = View.GONE   //hide loader when done loading
            }
        })


        listView.visibility = View.VISIBLE

        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_right)
        view.startAnimation(animation)

        return view

    }

    override fun onClick(v: View?) {

    }

}
