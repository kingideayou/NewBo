package com.next.newbo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.next.newbo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by NeXT on 15-3-27.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private String[] mDataset;

    public FeedAdapter(String[] mDataset){
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
//        feedViewHolder.textView.setText(mDataset[i]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.text_view)
        public TextView textView;

        public FeedViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
