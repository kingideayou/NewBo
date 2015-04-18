package com.next.newbo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.next.newbo.ui.activity.MainActivity;

/**
 * Created by NeXT on 15-3-31.
 */
public class Utils {

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight (Context context) {
        if (screenHeight == 0){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }
        return screenHeight;
    }

    public static int getScreenWidth (Context context) {
        if (screenWidth == 0){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        return screenWidth;
    }

    public static int getFontHeight(Context context, float fontSize) {
        float px = context.getResources().getDisplayMetrics().density * fontSize + 0.5f;
        Paint paint = new Paint();
        paint.setTextSize(px);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void clearOnGoingUnreadCount(Context context) {
        Settings setting = Settings.getInstance(context);
        setting.putString(Settings.NOTIFICATIN_ONGOING, "");
    }
}
