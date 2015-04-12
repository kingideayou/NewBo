package com.next.newbo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by NeXT on 15-4-10.
 */
public class UserModel implements Parcelable{

    //使用 transient 修饰，防止对象被持久化
    public transient long timestamp = System.currentTimeMillis();
    private String nameWithRemark;

    // Json 映射字段
    public String id;
    public String screen_name;
    public String name;
    public String remark;
    public String province;
    public String city;
    public String location;
    public String description;
    public String url;
    public String profile_image_url;
    public String domain;
    public String gender;
    public int followers_count = 0;
    public int friends_count = 0;
    public int statuses_count = 0;
    public int favourites_count = 0;
    public int verified_type = 0;
    public String created_at;
    public boolean following = false;
    public boolean allow_all_act_msg = false;
    public boolean geo_enabled = false;
    public boolean verified = false;
    public boolean allow_all_comment = false;
    public String avatar_large;
    public String verified_reason;
    public boolean follow_me = false;
    public int online_status = 0;
    public int bi_followers_count = 0;
    public String cover_image = "";
    public String cover_image_phone = "";

    public String getName() {
        if (TextUtils.isEmpty(remark)) {
            return screen_name == null ? name : screen_name;
        } else if (nameWithRemark == null) {
            nameWithRemark = String.format("%s(%s)", (screen_name == null ? name : screen_name), remark);
        }
        return nameWithRemark;
    }

    public String getNameNoRemark() {
        return screen_name == null ? name : screen_name;
    }

    public String getCover() {
        return cover_image.trim().equals("") ? cover_image_phone : cover_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(screen_name);
        parcel.writeString(name);
        parcel.writeString(remark);
        parcel.writeString(province);
        parcel.writeString(city);
        parcel.writeString(location);
        parcel.writeString(description);
        parcel.writeString(url);
        parcel.writeString(profile_image_url);
        parcel.writeString(domain);
        parcel.writeString(gender);
        parcel.writeString(created_at);
        parcel.writeString(avatar_large);
        parcel.writeString(verified_reason);
        parcel.writeInt(followers_count);
        parcel.writeInt(friends_count);
        parcel.writeInt(statuses_count);
        parcel.writeInt(favourites_count);
        parcel.writeInt(verified_type);
        parcel.writeInt(online_status);
        parcel.writeInt(bi_followers_count);
        parcel.writeString(cover_image_phone);
        parcel.writeString(cover_image);
        parcel.writeBooleanArray(
                new boolean[]{following, allow_all_act_msg, geo_enabled, verified, allow_all_comment});
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {

        @Override
        public UserModel createFromParcel(Parcel input) {
            UserModel ret = new UserModel();
            ret.id = input.readString();
            ret.screen_name = input.readString();
            ret.name = input.readString();
            ret.remark = input.readString();
            ret.province = input.readString();
            ret.city = input.readString();
            ret.location = input.readString();
            ret.description = input.readString();
            ret.url = input.readString();
            ret.profile_image_url = input.readString();
            ret.domain = input.readString();
            ret.gender = input.readString();
            ret.created_at = input.readString();
            ret.avatar_large = input.readString();
            ret.verified_reason = input.readString();
            ret.followers_count = input.readInt();
            ret.friends_count = input.readInt();
            ret.statuses_count = input.readInt();
            ret.favourites_count = input.readInt();
            ret.verified_type = input.readInt();
            ret.online_status = input.readInt();
            ret.bi_followers_count = input.readInt();
            ret.cover_image_phone = input.readString();
            ret.cover_image = input.readString();

            boolean[] array = new boolean[5];
            input.readBooleanArray(array);

            ret.following = array[0];
            ret.allow_all_act_msg = array[1];
            ret.geo_enabled = array[2];
            ret.verified = array[3];
            ret.allow_all_comment = array[4];

            return ret;
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };


    @Override
    public String toString() {
        return "UserModel{" +
                "timestamp=" + timestamp +
                ", nameWithRemark='" + nameWithRemark + '\'' +
                ", id='" + id + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", domain='" + domain + '\'' +
                ", gender='" + gender + '\'' +
                ", followers_count=" + followers_count +
                ", friends_count=" + friends_count +
                ", statuses_count=" + statuses_count +
                ", favourites_count=" + favourites_count +
                ", verified_type=" + verified_type +
                ", created_at='" + created_at + '\'' +
                ", following=" + following +
                ", allow_all_act_msg=" + allow_all_act_msg +
                ", geo_enabled=" + geo_enabled +
                ", verified=" + verified +
                ", allow_all_comment=" + allow_all_comment +
                ", avatar_large='" + avatar_large + '\'' +
                ", verified_reason='" + verified_reason + '\'' +
                ", follow_me=" + follow_me +
                ", online_status=" + online_status +
                ", bi_followers_count=" + bi_followers_count +
                ", cover_image='" + cover_image + '\'' +
                ", cover_image_phone='" + cover_image_phone + '\'' +
                '}';
    }
}
