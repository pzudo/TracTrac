package group7.tractrac.CostumList;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchListItems implements Parcelable {

    public String imageView;
    public String eventName;
    public String event;

    public void setImageView(String imageView) {
        this.imageView = imageView;

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getImageView() {
        return imageView;
    }


    public SearchListItems(String imageView, String eventName, String event) {
        this.imageView = imageView;
        this.eventName = eventName;
        this.event = event;

    }


    //Parcelling
    public SearchListItems(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.imageView = data[0];
        this.eventName = data[1];
        this.event = data[2];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{String.valueOf(this.imageView),
                this.eventName,
                this.event});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SearchListItems createFromParcel(Parcel in) {
            return new SearchListItems(in);
        }

        public SearchListItems[] newArray(int size) {
            return new SearchListItems[size];
        }
    };
}
