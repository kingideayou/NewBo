package com.next.newbo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.next.newbo.R;
import com.next.newbo.model.CommentModel;
import com.next.newbo.model.MessageListModel;
import com.next.newbo.model.MessageModel;
import com.next.newbo.support.HackyMovementMethod;
import com.next.newbo.support.SpannableStringUtils;
import com.next.newbo.ui.activity.WeiboDetailActivity;
import com.next.newbo.ui.view.HackyTextView;
import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.Utils;
import com.next.newbo.ui.view.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by NeXT on 15-3-27.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    private MessageListModel messageList;
    private final Map<Integer, Integer> likesCount = new HashMap<>();
    private ArrayList<RecyclerView.OnScrollListener> mListeners = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.OnScrollListener onScrollListener;
    private OnFeedItemClickListener onFeedItemClickListener;

    public FeedAdapter(Context context, MessageListModel messageList, RecyclerView recyclerView){
        this.mContext = context;
        this.messageList = messageList;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_feed, viewGroup, false);
        FeedViewHolder viewHolder = new FeedViewHolder(view);

        viewHolder.tvContent.setMovementMethod(HackyMovementMethod.getInstance());

        viewHolder.cardView.setOnClickListener(this);
//        viewHolder.tvContent.setOnClickListener(this);
        viewHolder.btnComment.setOnClickListener(this);
        viewHolder.btnTranspond.setOnClickListener(this);
        viewHolder.btnMore.setOnClickListener(this);
        viewHolder.ivStar.setOnClickListener(this);


        onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                for (RecyclerView.OnScrollListener listener : mListeners) {
                    if (listener != null) {
                        listener.onScrolled(recyclerView, dx, dy);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (RecyclerView.OnScrollListener listener : mListeners) {
                    if (listener != null) {
                        listener.onScrollStateChanged(recyclerView, newState);
                    }
                }
            }
        };
        recyclerView.setOnScrollListener(onScrollListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
        feedViewHolder.ivAvatar.setImageResource(R.mipmap.ic_launcher);

        feedViewHolder.cardView.setTag(position);
//        feedViewHolder.tvContent.setTag(position);
        feedViewHolder.cardView.setTag(position);
        feedViewHolder.btnComment.setTag(position);
        feedViewHolder.btnTranspond.setTag(position);
        feedViewHolder.btnMore.setTag(position);
        feedViewHolder.ivStar.setTag(feedViewHolder);

//        feedViewHolder.textView.setText(mDataset[i]);

        MessageModel messageModel = messageList.get(position);

        feedViewHolder.tvNickName.setText(
                messageModel.user != null ? messageModel.user.getName() : "");
        feedViewHolder.tvContent.setText(SpannableStringUtils.getSpan(mContext, messageModel));
        feedViewHolder.ivAvatar.setImageURI(Uri.parse(messageModel.user.avatar_large));
    }

    private void runEnterAnimation(View itemView, int position) {
        if (!animateItems || position >= messageList.getSize()) {
            return;
        }
        if (position > lastAnimatedPosition){
            lastAnimatedPosition = position;
            itemView.setTranslationY(Utils.getScreenHeight(mContext));
            itemView.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(500)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return messageList.getSize();
    }

    public void updateItems(boolean animated) {
        animateItems = animated;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onCommentsClick(view, (Integer)view.getTag());
                }
                break;
            case R.id.btn_transpond:
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onTranspondClick(view, (Integer)view.getTag());
                }
                break;
            case R.id.btn_more:
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onMoreClick(view, (Integer)view.getTag());
                }
                break;
            case R.id.tv_content:
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onContentClick(view, (Integer) view.getTag());
                }
                toWeiboDetailPage(view);
                break;
            case R.id.card_view:
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onContentClick(view, (Integer)view.getTag());
                }
                toWeiboDetailPage(view);
                break;
            case R.id.iv_star:
                FeedViewHolder viewHolder = (FeedViewHolder) view.getTag();
                updateLikesCount(viewHolder, true);
                break;
        }
    }

    private void toWeiboDetailPage(View view) {
        try {
            MessageModel messageModel = messageList.get((Integer)view.getTag());
            if (messageModel == null ) {
                return ;
            }
            //TODO 跳转到详情页
            Intent intent = new Intent(mContext, WeiboDetailActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.putExtra("msg", messageModel);

            //TODO 目前动画效果只支持 5.0
            ActivityOptionsCompat o =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) view.getContext(), view, "msg");
            ActivityCompat.startActivity((Activity) view.getContext(), intent, o.toBundle());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void updateLikesCount(FeedViewHolder holder, boolean animated) {
        int currentLikesCount = 126;
        String likesCountText = mContext.getResources().getQuantityString(
                R.plurals.likes_count, currentLikesCount, currentLikesCount
        );

        if (animated) {
            holder.tsLikesCounter.setText(likesCountText);
        } else {
            holder.tsLikesCounter.setCurrentText(likesCountText);
        }
        likesCount.put(holder.getPosition(), currentLikesCount);
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.card_view)
        CardView cardView;
        @InjectView(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;
        @InjectView(R.id.tv_nickname)
        TextView tvNickName;
//        @InjectView(R.id.tv_content)
//        HackyTextView tvContent;
        @InjectView(R.id.grid_main)
        GridLayout gridMain;
        @InjectView(R.id.btn_comment)
        ImageButton btnComment;
        @InjectView(R.id.btn_transpond)
        ImageButton btnTranspond;
        @InjectView(R.id.btn_more)
        ImageButton btnMore;
        @InjectView(R.id.iv_star)
        ImageView ivStar;
        @InjectView(R.id.ts_likes_counter)
        TextSwitcher tsLikesCounter;
        @InjectView(R.id.tv_content)
        HackyTextView tvContent;

        public FeedViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public void setOnFeedClickListener(OnFeedItemClickListener listener){
        this.onFeedItemClickListener = listener;
    }

    public interface OnFeedItemClickListener {

        public void onCommentsClick(View v, int position);
        public void onMoreClick(View v, int position);
        public void onTranspondClick(View v, int position);
        public void onContentClick(View v, int position);

    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mListeners.add(listener);
    }

}
