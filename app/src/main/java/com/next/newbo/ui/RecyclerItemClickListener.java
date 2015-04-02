package com.next.newbo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by NeXT on 15-4-2.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

    public OnItemClickListener onItemClickListener;

    GestureDetector gestureDetector;
    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.onItemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
        }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)){
            onItemClickListener.onItemClick(childView, rv.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
