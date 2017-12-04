package com.zarinpal.libs.httpRequest;

import org.json.JSONObject;

/**
 * Created by ImanX.
 * httprequestdemo | Copyrights 2017 ZarinPal Crop.
 */

public abstract class OnCallbackRequestListener {
    public abstract void onSuccessResponse(JSONObject jsonObject, String content);

    public abstract void onFailureResponse(int httpCode, String dataError);

    public void onFailureConnection() {
    }

    public void onTimeout() {
    }

   public void onUnknownError() {
    }
}
