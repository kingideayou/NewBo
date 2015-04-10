package com.next.newbo.model;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by NeXT on 15-4-10.
 */
public abstract class BaseListModel<I, L> implements Parcelable {

    public int total_number = 0;
    public String next_cursor = "0";
    public String previous_cursor = "0";

    public abstract int getSize();
    public abstract I get(int position);
    public abstract List<? extends  I> getList();

    /**
     *  @param toTop If true, add to top, else add to bottom
     *  @param values All values need to be added
     */
    public abstract void addAll(boolean toTop, L values);

    @Override
    public L clone() {
        BaseListModel<I, L> ret = null;
        try {
            ret = this.getClass().newInstance();
            ret.addAll(false, (L)this);
            return (L)ret;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
