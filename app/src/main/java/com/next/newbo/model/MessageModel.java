package com.next.newbo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;

import java.util.ArrayList;

/**
  This class represents a message (or comment)
  In timelines or message pages
  Including auto-highlight function
  Matching with Json from Weibo Api

  credits to: qii(github.com/qii/weiciyuan)
  author: NeXT
*/
public class MessageModel implements Parcelable{

    public static class PictureUrl implements Parcelable {
        // Picture url
        // OMG Sina why you use a special class for a simple data!
        private String thumbnail_pic;

        public String getThumbnail() {
            return thumbnail_pic;
        }

        public String getLarge() {
            return thumbnail_pic.replace("thumbnail", "large");
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(thumbnail_pic);
        }

        public static final Parcelable.Creator<PictureUrl> CREATOR = new Parcelable.Creator<PictureUrl>() {
            @Override
            public MessageModel.PictureUrl createFromParcel(Parcel in) {
                MessageModel.PictureUrl ret = new MessageModel.PictureUrl();
                ret.thumbnail_pic = in.readString();
                return ret;
            }
            @Override
            public MessageModel.PictureUrl[] newArray(int size) {
                return new MessageModel.PictureUrl[size];
            }
        };
    }

    // Json mapping fields
    public String created_at;
    public long id;
    public long mid;
    public String idstr;
    public String text; //微博详情
    public String source;
    public boolean favorited;
    public boolean truncated;
    public boolean liked;
    public String in_reply_to_status_id;
    public String in_reply_to_user_id;
    public String in_reply_to_screen_name;
    public String thumbnail_pic;
    public String bmiddle_pic;
    public String original_pic;

    public GeoModel geo;
    public UserModel user;
    public MessageModel retweeted_status; //如果有转发，这条内容为原文内容

    public int reposts_count;
    public int comments_count;
    public int attitudes_count;
    public ArrayList<AnnotationModel> annotations = new ArrayList<>();

    public transient SpannableString span, origSpan;
    public transient long millis;

    public ArrayList<PictureUrl> pic_urls = new ArrayList<>();
    public boolean unClickable;

    public boolean hasMultiplePictures() {
        return pic_urls.size() > 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof  MessageModel) {
            return ((MessageModel)o).id == id;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return idstr.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_at);
        dest.writeLong(id);
        dest.writeLong(mid);
        dest.writeString(idstr);
        dest.writeString(text);
        dest.writeString(source);
        dest.writeBooleanArray(new boolean[]{favorited, truncated, liked, unClickable});
        dest.writeString(in_reply_to_status_id);
        dest.writeString(in_reply_to_user_id);
        dest.writeString(in_reply_to_screen_name);
        dest.writeString(thumbnail_pic);
        dest.writeString(bmiddle_pic);
        dest.writeString(original_pic);
        dest.writeParcelable(geo, flags);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(retweeted_status, flags);
        dest.writeInt(reposts_count);
        dest.writeInt(comments_count);
        dest.writeInt(attitudes_count);
        dest.writeTypedList(pic_urls);
        dest.writeLong(millis);
        dest.writeTypedList(annotations);
    }

    public static final Parcelable.Creator<MessageModel> CREATOR = new Parcelable.Creator<MessageModel>() {

        @Override
        public MessageModel createFromParcel(Parcel in) {
            MessageModel ret = new MessageModel();
            ret.created_at = in.readString();
            ret.id = in.readLong();
            ret.mid = in.readLong();
            ret.idstr = in.readString();
            ret.text = in.readString();
            ret.source = in.readString();

            boolean[] array = new boolean[4];
            in.readBooleanArray(array);

            ret.favorited = array[0];
            ret.truncated = array[1];
            ret.liked = array[2];

            ret.unClickable = array[3];

            ret.in_reply_to_status_id = in.readString();
            ret.in_reply_to_user_id = in.readString();
            ret.in_reply_to_screen_name = in.readString();
            ret.thumbnail_pic = in.readString();
            ret.bmiddle_pic = in.readString();
            ret.original_pic = in.readString();
            ret.geo = in.readParcelable(GeoModel.class.getClassLoader());
            ret.user = in.readParcelable(UserModel.class.getClassLoader());
            ret.retweeted_status = in.readParcelable(MessageModel.class.getClassLoader());
            ret.reposts_count = in.readInt();
            ret.comments_count = in.readInt();
            ret.attitudes_count = in.readInt();

            in.readTypedList(ret.pic_urls, PictureUrl.CREATOR);

            ret.millis = in.readLong();

            in.readTypedList(ret.annotations, AnnotationModel.CREATOR);

            return ret;
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    @Override
    public String toString() {
        return "MessageModel{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", mid=" + mid +
                ", idstr='" + idstr + '\'' +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", favorited=" + favorited +
                ", truncated=" + truncated +
                ", liked=" + liked +
                ", in_reply_to_status_id='" + in_reply_to_status_id + '\'' +
                ", in_reply_to_user_id='" + in_reply_to_user_id + '\'' +
                ", in_reply_to_screen_name='" + in_reply_to_screen_name + '\'' +
                ", thumbnail_pic='" + thumbnail_pic + '\'' +
                ", bmiddle_pic='" + bmiddle_pic + '\'' +
                ", original_pic='" + original_pic + '\'' +
                ", geo=" + geo +
                ", user=" + user +
                ", retweeted_status=" + retweeted_status +
                ", reposts_count=" + reposts_count +
                ", comments_count=" + comments_count +
                ", attitudes_count=" + attitudes_count +
                ", annotations=" + annotations +
                ", span=" + span +
                ", origSpan=" + origSpan +
                ", millis=" + millis +
                ", pic_urls=" + pic_urls +
                ", unClickable=" + unClickable +
                '}';
    }
}
