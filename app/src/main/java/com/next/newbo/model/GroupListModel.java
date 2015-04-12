package com.next.newbo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeXT on 15/4/12.
 */
public class GroupListModel extends BaseListModel<GroupModel, GroupListModel> {

    private List<GroupModel> lists = new ArrayList<>();

    @Override
    public int getSize() {
        return lists.size();
    }

    @Override
    public GroupModel get(int position) {
        return lists.get(position);
    }

    @Override
    public List<? extends GroupModel> getList() {
        return lists;
    }

    @Override
    public void addAll(boolean toTop, GroupListModel values) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(lists);
    }

    public static final Creator<GroupListModel> CREATOR = new Creator<GroupListModel>() {

        @Override
        public GroupListModel createFromParcel(Parcel source) {
            GroupListModel groupListModel = new GroupListModel();
            source.readTypedList(groupListModel.lists, GroupModel.CREATOR);
            return groupListModel;
        }

        @Override
        public GroupListModel[] newArray(int size) {
            return new GroupListModel[size];
        }
    };
}
