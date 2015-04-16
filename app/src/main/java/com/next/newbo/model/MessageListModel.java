package com.next.newbo.model;

import android.content.Context;
import android.os.Parcel;

import com.next.newbo.support.SpannableStringUtils;
import com.next.newbo.support.StatusTimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeXT on 15-4-10.
 */
public class MessageListModel extends BaseListModel<MessageModel, MessageListModel>{

    private class AD {
        public long id = -1;
        public String mark = "";
        @Override
        public boolean equals(Object o) {
            if (o instanceof  AD) {
                return ((AD)o).id == id;
            }
            return super.equals(o);
        }
        @Override
        public int hashCode() {
            return String.valueOf(id).hashCode();
        }
    }

    private List<MessageModel> statuses = new ArrayList<>();
    private List<AD> ad = new ArrayList<>();

    public void spanAll(Context context) {
        for (MessageModel msg : getList()) {
            msg.span = SpannableStringUtils.getSpan(context, msg);
            if (msg.retweeted_status != null) {
                msg.retweeted_status.origSpan =
                        SpannableStringUtils.getOrigSpan(context, msg.retweeted_status);
            }
        }
    }

    public void timestampAll(Context context) {
        StatusTimeUtils utils = StatusTimeUtils.instance(context);
        for (MessageModel messageModel : getList()) {
            messageModel.millis = utils.parseTimeString(messageModel.created_at);
            if (messageModel.retweeted_status != null) {
                messageModel.retweeted_status.millis =
                        utils.parseTimeString(messageModel.created_at);
            }
        }
    }

    @Override
    public int getSize() {
        return statuses.size();
    }

    @Override
    public MessageModel get(int position) {
        return statuses.get(position);
    }

    @Override
    public List<? extends MessageModel> getList() {
        return statuses;
    }

    @Override
    public void addAll(boolean toTop, MessageListModel values) {
        addAll(toTop, false, values, null);
    }

    public void addAll(boolean toTop, boolean friendsOnly, MessageListModel values, String mUid) {
        if (values != null && values.getSize() > 0) {
            for (MessageModel msg : values.getList()) {
                if (!statuses.contains(msg) && !values.ad.contains(msg.id) && msg.user != null
                        && (msg.user.following || msg.user.id.equals(mUid) || !friendsOnly)) {
                    statuses.add(toTop ? values.getList().indexOf(msg) : statuses.size(), msg);
                }
            }
            total_number = values.total_number;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(total_number);
        parcel.writeString(previous_cursor);
        parcel.writeString(next_cursor);
        parcel.writeTypedList(statuses);
    }

    public static final Creator<MessageListModel> CREATOR = new Creator<MessageListModel>(){

        @Override
        public MessageListModel createFromParcel(Parcel parcel) {
            MessageListModel messageListModel = new MessageListModel();
            messageListModel.total_number = parcel.readInt();
            messageListModel.previous_cursor = parcel.readString();
            messageListModel.next_cursor = parcel.readString();
            parcel.readTypedList(messageListModel.statuses, MessageModel.CREATOR);
            return messageListModel;
        }

        @Override
        public MessageListModel[] newArray(int size) {
            return new MessageListModel[size];
        }
    };

}
