package com.next.newbo.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.next.newbo.R;
import com.next.newbo.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by NeXT on 15-4-1.
 */
public class FeedContextMenu extends LinearLayout{

    private int feedItem = -1;
    private static final int CONTEXT_MENU_WIDTH = Utils.dpToPx(220);
    private OnFeedContextMenuItemClickListener onFeedContextMenuItemClickListener;

    public FeedContextMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_context_menu, this, true);
        setBackgroundResource(R.mipmap.bg_container_shadow);
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(CONTEXT_MENU_WIDTH, LayoutParams.WRAP_CONTENT));
    }

    public void bindToItem(int feedItem) {
        this.feedItem = feedItem;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.inject(this);
    }

    public void dismiss() {
        ((ViewGroup) getParent()).removeView(FeedContextMenu.this);
    }

    @OnClick(R.id.btn_1)
    public void onButton1Click() {
        if(onFeedContextMenuItemClickListener != null){
            onFeedContextMenuItemClickListener.onButon1Click(feedItem);
        }
    }

    @OnClick(R.id.btn_2)
    public void onButton2Click() {
        if(onFeedContextMenuItemClickListener != null){
            onFeedContextMenuItemClickListener.onButon2Click(feedItem);
        }
    }

    public void setOnFeedContextMenuItemClickListener(OnFeedContextMenuItemClickListener onFeedContextMenuItemClickListener) {
        this.onFeedContextMenuItemClickListener = onFeedContextMenuItemClickListener;
    }

    public interface OnFeedContextMenuItemClickListener {
        public void onButon1Click(int feedItem);
        public void onButon2Click(int feedItem);
    }

}
