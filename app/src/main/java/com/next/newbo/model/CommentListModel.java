package com.next.newbo.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.next.newbo.support.SpannableStringUtils;
import com.next.newbo.support.StatusTimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeXT on 15/4/16.
 */
public class CommentListModel extends MessageListModel {

    private List<CommentModel> comments = new ArrayList<>();

    @Override
    public void spanAll(Context context) {
        super.spanAll(context);
        for(CommentModel commentModel : comments) {
            if (commentModel.reply_comment != null) {
                commentModel.reply_comment.origSpan = SpannableStringUtils.getOrigSpan(
                        context, commentModel.reply_comment
                );
            } else if(commentModel.status != null){
                commentModel.status.origSpan = SpannableStringUtils.getOrigSpan(
                        context, commentModel.status);
            }
        }
    }

    @Override
    public void timestampAll(Context context) {
        super.timestampAll(context);
        StatusTimeUtils utils = StatusTimeUtils.instance(context);
        for (CommentModel commentModel : (List<CommentModel>)getList()) {
            if (commentModel.status != null) {
                commentModel.status.millis = utils.parseTimeString(commentModel.status.created_at);
            }
        }
    }

    @Override
    public int getSize() {
        return comments.size();
    }

    @Override
    public MessageModel get(int position) {
        return comments.get(position);
    }

    @Override
    public List<? extends MessageModel> getList() {
        return comments;
    }

    @Override
    public void addAll(boolean toTop, MessageListModel values) {
        if (values instanceof CommentListModel && values != null && values.getSize() > 0) {
            for (MessageModel messageModel : values.getList()) {
                if (!comments.contains(messageModel)) {
                    comments.add(toTop ? values.getList().indexOf(messageModel) :
                        comments.size(), (CommentModel)messageModel);
                }
                total_number = values.total_number;
            }
        }
    }

    @Override
    public void addAll(boolean toTop, boolean friendsOnly, MessageListModel values, String mUid) {
        addAll(toTop, values);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageListModel> CREATOR = new Creator<MessageListModel>() {

        @Override
        public CommentListModel createFromParcel(Parcel in) {
            CommentListModel commentListModel = new CommentListModel();
            commentListModel.total_number = in.readInt();
            commentListModel.previous_cursor = in.readString();
            commentListModel.next_cursor = in.readString();
            in.readTypedList(commentListModel.comments, CommentModel.CREATOR);
            return commentListModel;
        }

        @Override
        public MessageListModel[] newArray(int size) {
            return new MessageListModel[size];
        }
    };
}
