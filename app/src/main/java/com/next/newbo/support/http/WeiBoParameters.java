package com.next.newbo.support.http;

import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by NeXT on 15/4/8.
 */
public class WeiboParameters extends HashMap<String, Object>{

    public String encode() {
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        Set<String> keys = keySet();

        for (String key : keys) {
            Object value = get(key);
            if (value instanceof Bitmap) {
                return null;
            } else {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append("&");
                }
                try {
                    sb.append(URLEncoder.encode(key, "UTF-8")).append("=")
                            .append(URLEncoder.encode(value.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public Object[] toBoundaryMsg() {
        String b = getBoundaryStr();
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(b).append("\r\n");

        Set<String> keys = keySet();
        Bitmap bitmap = null;
        String bmKey = null;
        for (String key : keys) {
            Object value = get(key);
            if (value instanceof Bitmap) {
                bitmap = (Bitmap) value;
                bmKey = key;
            } else {
                sb.append("Content-Disposition: form-data; name=\"");
                sb.append(key).append("\"\r\n\r\n");
                sb.append(value).append("\r\n--");
                sb.append(b).append("\r\n");
            }
        }
        if (bitmap != null) {
            sb.append("Content-Disposition: form-data; name=\"");
            sb.append(bmKey).append("\"; filename=\"").append(System.currentTimeMillis()).append(".jpg");
            sb.append("\"\r\nContent-Type: image/jpeg\r\n\r\n");
        }
        return new Object[]{b, bitmap, sb.toString()};
    }

    private String getBoundaryStr() {
        return String.valueOf(System.currentTimeMillis() * Math.random() % 1024);
    }
}
