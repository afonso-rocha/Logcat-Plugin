package org.apache.cordova.logcat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.onesignal.OneSignal;

import java.io.File;
import java.io.IOException;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;

public class LogCat extends CordovaPlugin { //LogCatPlugin

    private static final String TAG = "LogCatPlugin";

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("sendLogs")) {
            if(!foregroundServiceRunning()) {
                Activity activity = cordova.getActivity();
                Intent serviceIntent = new Intent(activity, MyForegroundService.class);
                activity.getApplicationContext().startForegroundService(serviceIntent);
            }
            return true;
        } else if (action.equals("registerDevice")) {
                Activity activityCordova = cordova.getActivity();
                OneSignal.initWithContext(activityCordova);
                OneSignal.setAppId(args.getString(0));
                return true;
        } else {
            return false;
        }
    }        //create a new Intent to send the logs
             //Intent serviceIntent = new Intent(cordova.getActivity(), MyForegroundService.class);
             //serviceIntent.setaction(""); //string of the action that want to execute

    //Checks if the foreground service is running
    public boolean foregroundServiceRunning() {
        Context context = cordova.getActivity().getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
