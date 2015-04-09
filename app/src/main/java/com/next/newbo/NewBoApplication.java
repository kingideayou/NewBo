package com.next.newbo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by NeXT on 15-3-31.
 */
public class NewBoApplication extends Application{

    private static NewBoApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this);
    }

    public static NewBoApplication getInstance() {
        return context;
    }

}
