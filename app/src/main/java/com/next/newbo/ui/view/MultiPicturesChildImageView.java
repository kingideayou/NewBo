package com.next.newbo.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.next.newbo.R;

/**
 * Created by NeXT on 15-3-30.
 */
public class MultiPicturesChildImageView extends ImageView {

    private Paint paint = new Paint();
    private boolean pressed = false;
    private boolean showGif = false;
    private Bitmap gif;

    public MultiPicturesChildImageView(Context context){
        this(context, null);
    }

    public MultiPicturesChildImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiPicturesChildImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        gif = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_play_gif_small);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showGif){
            int bitmapHeight = gif.getHeight();
            int bitmapWidth = gif.getWidth();
            int x = (getWidth() - bitmapWidth) / 2;
            int y = (getHeight() - bitmapHeight) / 2;
            canvas.drawBitmap(gif, x, y, paint);
        }
        if(pressed){
            canvas.drawColor(getResources().getColor(R.color.transparent_cover));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pressed = true;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                pressed = false;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setGifFlag(boolean flag){
        if(showGif != flag){
            showGif = flag;
            invalidate();
        }
    }

    public ImageView getImageView(){
        return this;
    }
}
