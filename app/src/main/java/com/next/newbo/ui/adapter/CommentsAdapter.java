package com.next.newbo.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.next.newbo.R;
import com.next.newbo.model.MessageListModel;
import com.next.newbo.model.MessageModel;
import com.next.newbo.support.SpannableStringUtils;
import com.next.newbo.ui.activity.WeiboDetailActivity;
import com.next.newbo.ui.view.CircleImageView;
import com.next.newbo.ui.view.HackyTextView;
import com.next.newbo.utils.AppLogger;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by NeXT on 15/4/5.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final static int TYPE_HEADER = 0;
    private final static int TYPE_MENU = 1;
    private final static int TYPE_NORMAL = 2;

    private Context context;
    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private MessageModel messageModel;
    private MessageListModel messageListModel;
    private RecyclerView recyclerView;
    private RecyclerView.OnScrollListener onScrollListener;
    private ArrayList<RecyclerView.OnScrollListener> listeners = new ArrayList<>();

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    public CommentsAdapter(Context context, MessageModel messageModel,
                           MessageListModel messageListModel, RecyclerView recyclerView) {
        this.context = context;
        this.messageModel = messageModel;
        this.messageListModel = messageListModel;
        this.recyclerView = recyclerView;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (RecyclerView.OnScrollListener listener : listeners) {
                    if (listener != null) {
                        listener.onScrollStateChanged(recyclerView, newState);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                for(RecyclerView.OnScrollListener listener : listeners) {
                    if (listener != null) {
                        listener.onScrolled(recyclerView, dx, dy);
                    }
                }
            }
        };
        recyclerView.setOnScrollListener(onScrollListener);

        if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.item_weibo, viewGroup, false);
            return new HeaderViewHolder(headerView);
        } else if (viewType == TYPE_MENU) {
            View menuView = LayoutInflater.from(context).inflate(R.layout.item_weibo_menu, viewGroup, false);
            return new MenuViewHolder(menuView);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment, viewGroup, false);
            return new CommentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        if(getItemViewType(position) == TYPE_HEADER) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder)viewHolder;
            headerViewHolder.tvNickName.setText(
                    messageModel.user != null ? messageModel.user.getName() : "");
            headerViewHolder.tvContent.setText(SpannableStringUtils.getSpan(context, messageModel));
            headerViewHolder.ivAvatar.setImageURI(Uri.parse(messageModel.user.avatar_large));
        } else if(getItemViewType(position) == TYPE_MENU) {
            MenuViewHolder menuViewHolder = (MenuViewHolder)viewHolder;
        } else {
            CommentViewHolder holder = (CommentViewHolder) viewHolder;
            holder.tvComment.setText(SpannableStringUtils.getOrigSpan(context, messageListModel.get(position)));
            String imageUrl = messageListModel.get(position).user.avatar_large;
            holder.ivAvatar.setImageURI(Uri.parse(imageUrl));
            /*
            switch (position % 3) {
                case 0:
                    holder.tvComment.setText("不要走，决战到天亮啊");
                    break;
                case 1:
                    holder.tvComment.setText("你的牌打得也太好了");
                    break;
                case 2:
                    holder.tvComment.setText("你会不会玩啊，专业点！");
                    break;
            }
            */
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.1f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * position : 0)
                    .setInterpolator(new DecelerateInterpolator())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            animationsLocked = true;
                        }
                    })
                    .start();

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_MENU;
        } else {
            return TYPE_NORMAL;
        }
    }


    @Override
    public int getItemCount() {
        return messageListModel.getSize();
    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }

    public void addItem() {
        itemsCount++;
        notifyItemInserted(itemsCount - 1);
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        listeners.add(listener);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;
        @InjectView(R.id.tv_comment)
        TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_avatar)
        SimpleDraweeView ivAvatar;
        @InjectView(R.id.tv_content)
        HackyTextView tvContent;
        @InjectView(R.id.tv_nickname)
        TextView tvNickName;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_repost)
        TextView tvRepost;
        @InjectView(R.id.tv_comment)
        TextView tvComment;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
