package com.next.newbo.support;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.FloatMath;

import java.util.ArrayList;


/**
 * Created by NeXT on 15-4-7.
 */
public class ShakeDetector implements SensorEventListener{

    private static final String TAG = ShakeDetector.class.getSimpleName();
    /**
     * 晃动时间间隔
     */
    private static final int INTERVAL = 100;
    //检测振幅
    private static final int THRESHOLD = 10;

    private static ShakeDetector instance = null;

    private long mLastTime;
    private float lastX, lastY, lastZ;
    private float lastV;

    private Vibrator mVibrator;
    private ArrayList<ShakeListener> listeners = new ArrayList<>();

    public synchronized static ShakeDetector getInstance(Context context) {
        if (instance == null) {
            instance = new ShakeDetector(context);
        }
        return instance;
    }

    private ShakeDetector(Context context) {
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //加速传感器
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long millis = System.currentTimeMillis();
        long diff = millis - mLastTime;
        if (diff >= INTERVAL) {
            mLastTime = millis;

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float dX = x - lastX;
            float dY = x - lastY;
            float dZ = x - lastZ;

            float eV = FloatMath.sqrt(dX * dX + dY * dY + dZ * dZ) / diff * 10000;

            if (lastV > -1) {
                float dV = eV - lastV;
                float a = Math.abs(dV / diff);

                if (a >= THRESHOLD) {
                    notifyListeners();
                }
            }

            lastX = x;
            lastY = y;
            lastZ = z;
            lastV = eV;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void notifyListeners() {
        for (ShakeListener listener : listeners) {
            if (listener != null) {
                mVibrator.vibrate(new long[]{0, 100}, -1);
                listener.onShake();
            }
        }
    }

    public void addListeners(ShakeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ShakeListener listener) {
        listeners.remove(listener);
    }

    public static interface ShakeListener {
        public void onShake();
    }

}
