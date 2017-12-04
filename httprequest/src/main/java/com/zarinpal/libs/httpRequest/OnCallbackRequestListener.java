package com.zarinpal.libs.httpRequest;

import org.json.JSONObject;

/**
 * Created by ImanX.
 * httprequestdemo | Copyrights 2017 ZarinPal Crop.
 */

public abstract class OnCallbackRequestListener {
    abstract void onSuccessResponse(JSONObject jsonObject, String content);

    abstract void onFailureResponse(int httpCode, String dataError);

    void onFailureConnection() {
    }

    void onTimeout() {
    }

    void onUnknowError() {
    }
}
