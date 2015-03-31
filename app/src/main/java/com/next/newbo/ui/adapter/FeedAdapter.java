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

import com.next.newbo.R;
import com.next.newbo.Utils;
import com.next.newbo.ui.view.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by NeXT on 15-3-27.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private String[] mDataset;
    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    public FeedAdapter(Context context, String[] mDataset){
        this.context = context;
        this.mDataset = mDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_feed, viewGroup, false);
        FeedViewHolder viewHolder = new FeedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
        feedViewHolder.ivAvatar.setImageResource(R.mipmap.ic_launcher);
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


    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @InjectView(R.id.tv_nickname)
        public TextView tvNickName;
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
}
