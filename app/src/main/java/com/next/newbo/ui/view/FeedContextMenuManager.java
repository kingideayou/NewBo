package com.next.newbo.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.next.newbo.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * Created by NeXT on 15-4-1.
 */
public class FeedContextMenuManager extends RecyclerView.OnScrollListener implements
        View.OnAttachStateChangeListener {

    private FeedContextMenu contextMenu;
    private static FeedContextMenuManager instance;

    public boolean isContextMenuDismissing;
    public boolean isContextMenuShowing;


    private FeedContextMenuManager() {}

    public static FeedContextMenuManager getInstance() {
        if (instance == null) {
            instance = new FeedContextMenuManager();
        }
        return instance;
    }

    public void toggleContextMenuFromView(View openingView, int feedItem,
                                          FeedContextMenu.OnFeedContextMenuItemClickListener listener){
        if (contextMenu == null) {
            showContextMenuFromView(openingView, feedItem, listener);
        } else {
            hideContextMenu();
        }
    }

    private void showContextMenuFromView(final View openingView, int feedItem,
                                         FeedContextMenu.OnFeedContextMenuItemClickListener listener) {
        if (!isContextMenuShowing) {
            isContextMenuShowing = true;
            contextMenu = new FeedContextMenu(openingView.getContext());
            contextMenu.bindToItem(feedItem);
            contextMenu.addOnAttachStateChangeListener(this);
            contextMenu.setOnFeedContextMenuItemClickListener(listener);

            ((ViewGroup) openingView.getRootView().findViewById(android.R.id.content)).
                    addView(contextMenu);

            contextMenu.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contextMenu.getViewTreeObserver().removeOnPreDrawListener(this);
                    setupContextMenuInitialPosition(openingView);
                    performShowAnimation();
                    return false;
                }
            });
            contextMenu.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                @Override
                public void onGlobalFocusChanged(View view, View view2) {
                    if(contextMenu != null) {
                        hideContextMenu();
                    }
                }
            });
            contextMenu.getViewTreeObserver().addOnTouchModeChangeListener(new ViewTreeObserver.OnTouchModeChangeListener() {
                @Override
                public void onTouchModeChanged(boolean b) {
                    Logger.i("onTouchModeChanged ---");
                    if(contextMenu != null){
                        hideContextMenu();
                    }
                }
            });
        }
    }

    private void setupContextMenuInitialPosition(View openingView) {
        final int[] openingViewLocation = new int[2];
        openingView.getLocationOnScreen(openingViewLocation);
        int additionalBottomMargin = Utils.dpToPx(16);
        contextMenu.setTranslationX(openingViewLocation[0] - contextMenu.getWidth() / 3);
        contextMenu.setTranslationY(openingViewLocation[1] - contextMenu.getHeight() -
                additionalBottomMargin);
    }

    private void performShowAnimation() {
        contextMenu.setPivotX(contextMenu.getWidth() / 2);
        contextMenu.setPivotX(contextMenu.getHeight());
        contextMenu.setScaleX(0.1f);
        contextMenu.setScaleY(0.1f);
        contextMenu.animate()
                .scaleX(1f).scaleY(1f)
                .setDuration(150)
                .setInterpolator(new OvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isContextMenuShowing = false;
                        super.onAnimationEnd(animation);
                    }
                });
    }

    public void hideContextMenu() {
        if(!isContextMenuDismissing){
            isContextMenuDismissing = true;
            performDismissAnimation();
        }
    }

    private void performDismissAnimation() {
        //设置支点
        contextMenu.setPivotX(contextMenu.getWidth() / 2);
        contextMenu.setPivotY(contextMenu.getHeight());
        contextMenu.animate()
                .scaleX(0.1f).scaleY(0.1f)
                .setDuration(150)
                .setInterpolator(new AccelerateInterpolator())
                .setStartDelay(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (contextMenu != null) {
                            contextMenu.dismiss();;
                        }
                        isContextMenuDismissing = false;
                    }
                });
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(contextMenu != null){
            hideContextMenu();
            contextMenu.setTranslationY(contextMenu.getTranslationY() - dy);
        }
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onViewAttachedToWindow(View view) {
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        contextMenu = null;
    }
}
