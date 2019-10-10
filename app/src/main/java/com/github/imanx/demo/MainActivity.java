package com.github.imanx.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zarinpal.libs.httpRequest.HttpRequest;
import com.zarinpal.libs.httpRequest.OnCallbackRequestListener;

import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView          view    = (TextView) findViewById(R.id.txt);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("title", "asdasdasdasdas");
        hashMap.put("content", "asdasdasdasdas");
        hashMap.put("email", "hooman6445@gmail.com");
        hashMap.put("name", "Hooooooooman");
        hashMap.put("phone", "09369800507");
        hashMap.put("ticket_department_id", "3");
        hashMap.put("g_recaptcha", "3");


        new HttpRequest(this)
                .setURL("https://www.zarinpal.com/rest/v3/ticket/guest.json")
                .setParams(hashMap)
                .setRequestMethod(HttpRequest.POST)
                .setSSLSocketFactory(null)
                .get(new OnCallbackRequestListener() {
                    @Override
                    public void onSuccessResponse(JSONObject jsonObject, String content) {
                        view.setText(jsonObject.toString());
                    }

                    @Override
                    public void onFailureResponse(int httpCode, String dataError) {
                        view.setText(httpCode + " ");

                    }
                });


    }


}
