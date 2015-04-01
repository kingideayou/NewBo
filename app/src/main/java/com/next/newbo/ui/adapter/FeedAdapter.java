package com.next.newbo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.next.newbo.R;
import com.next.newbo.Utils;
import com.next.newbo.ui.view.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by NeXT on 15-3-27.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private String[] mDataset;
    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    private OnFeedItemClickListener onFeedItemClickListener;

    public FeedAdapter(Context context, String[] mDataset){
        this.context = context;
        this.mDataset = mDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_feed, viewGroup, false);
        FeedViewHolder viewHolder = new FeedViewHolder(view);

        viewHolder.btnComment.setOnClickListener(this);
        viewHolder.btnTranspond.setOnClickListener(this);
        viewHolder.btnMore.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
        feedViewHolder.ivAvatar.setImageResource(R.mipmap.ic_launcher);

        feedViewHolder.btnComment.setTag(position);
        feedViewHolder.btnTranspond.setTag(position);
        feedViewHolder.btnMore.setTag(position);

//        feedViewHolder.textView.setText(mDataset[i]);
    }

    private void runEnterAnimation(View itemView, int position) {
        if (!animateItems || position >= mDataset.length) {
            return;
        }
        if (position > lastAnimatedPosition){
            lastAnimatedPosition = position;
            itemView.setTranslationY(Utils.getScreenHeight(context));
            itemView.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(600)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void updateItems(boolean animated) {
        animateItems = animated;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:
                Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show();
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onCommentsClick(view, (Integer)view.getTag());
                }
                break;
            case R.id.btn_transpond:
                Toast.makeText(context, "转发", Toast.LENGTH_SHORT).show();
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onTranspondClick(view, (Integer)view.getTag());
                }
                break;
            case R.id.btn_more:
                Toast.makeText(context, "更多", Toast.LENGTH_SHORT).show();
                if(onFeedItemClickListener != null){
                    onFeedItemClickListener.onMoreClick(view, (Integer)view.getTag());
                }
                break;
        }
    }


    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @InjectView(R.id.tv_nickname)
        TextView tvNickName;
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.grid_main)
        GridLayout gridMain;
        @InjectView(R.id.btn_comment)
        ImageButton btnComment;
        @InjectView(R.id.btn_transpond)
        ImageButton btnTranspond;
        @InjectView(R.id.btn_more)
        ImageButton btnMore;
        @InjectView(R.id.ts_likes_counter)
        TextSwitcher tsLikeCounter;

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

    }

}
