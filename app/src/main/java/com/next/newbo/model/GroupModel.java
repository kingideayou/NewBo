package com.next.newbo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NeXT on 15/4/12.
 */
public class GroupModel implements Parcelable {

    public long id;
    public String idstr;
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(idstr);
        dest.writeString(name);
    }

    public static final Creator<GroupModel> CREATOR = new Creator<GroupModel>() {
        @Override
        public GroupModel createFromParcel(Parcel in) {
            GroupModel groupModel = new GroupModel();
            groupModel.id = in.readLong();
            groupModel.idstr = in.readString();
            groupModel.name = in.readString();
            return groupModel;
        }

        @Override
        public GroupModel[] newArray(int size) {
            return new GroupModel[size];
        }
    };

}
