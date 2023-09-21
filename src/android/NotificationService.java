package org.apache.cordova.logcat;

import android.content.Context;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationService implements OSRemoteNotificationReceivedHandler {

    private static String TAG = "NotificationReceivedHandler";

    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent notificationReceivedEvent) {
        OSNotification notification = notificationReceivedEvent.getNotification();

        JSONObject data = notification.getAdditionalData();

        String innerJsonString = data.optString("this");

        JSONObject innerJson = null;
        try {

            innerJson = new JSONObject(innerJsonString);

            String VIN = innerJson.optString("VIN");
            String ClientId = innerJson.optString("ClientId");
            String ClientSecret = innerJson.optString("ClientSecret");
            String TennantId = innerJson.optString("TennantId");

            new LogcatHistoryFile().generateZipFile(context, VIN, ClientId, ClientSecret, TennantId);

        } catch (JSONException e) {
            Log.e(TAG, "Something went wrong while receiving notification", e);
            throw new RuntimeException(e);
        }

        notificationReceivedEvent.complete(notification);
    }

}
