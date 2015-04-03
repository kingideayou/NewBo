package com.next.newbo.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.next.newbo.R;
import com.next.newbo.ui.adapter.GlobalMenuAdapter;

/**
 * Created by NeXT on 15-3-31.
 */
public class GlobalMenuView extends ListView implements View.OnClickListener {

    private int avatarSize;
    private String profilePhoto;

    private TextView tvNickName;
    private CircleImageView ivAvatar;

    private GlobalMenuAdapter globalMenuAdapter;
    private OnHeaderClickListener onHeaderClickListener;

    public GlobalMenuView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setChoiceMode(CHOICE_MODE_SINGLE);
        setDivider(getResources().getDrawable(android.R.color.transparent));
        setDividerHeight(0);
        setBackgroundColor(Color.WHITE);

        setupHeader();
        setupAdapter();
    }

    private void setupAdapter() {
        globalMenuAdapter = new GlobalMenuAdapter(getContext());
        setAdapter(globalMenuAdapter);
    }

    private void setupHeader() {
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        this.profilePhoto = getResources().getString(R.string.user_profile_photo);
        setHeaderDividersEnabled(true);
        View headerView = LayoutInflater.from(getContext()).inflate(
                R.layout.view_global_menu_header, null);
        ivAvatar = (CircleImageView) headerView.findViewById(R.id.iv_avatar);
        addHeaderView(headerView);
        headerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (onHeaderClickListener != null){
            onHeaderClickListener.onGloableMenuHeaderClick(view);
        }
    }

    public interface  OnHeaderClickListener {
        public void onGloableMenuHeaderClick(View v);
    }

    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        this.onHeaderClickListener = listener;
    }
}
