package com.zarinpal.libs.httpRequest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.SSLSocketFactory;


/**
 * Android ZarinPal HttpRequest
 * Created by ImanX on 12/22/16.
 * Copyright Alireza Tarazani All Rights Reserved.
 */
public class HttpQueue {
    private static HttpQueue    instance;
    private static RequestQueue queue;

    public static HttpQueue getInstance(Context context, SSLSocketFactory sslSocketFactory) {
        if (instance == null) {
            instance = new HttpQueue();
            queue = Volley.newRequestQueue(context, new HurlStack(null, sslSocketFactory));
        }
        return instance;
    }

    public void addToRequest(Request request) {
        queue.add(request);
    }
}
