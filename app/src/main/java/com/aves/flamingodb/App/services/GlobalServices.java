package com.aves.flamingodb.App.services;

import static android.hardware.usb.UsbDevice.getDeviceId;

import static java.security.AccessController.getContext;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaDrm;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyManager.*;
import android.widget.Toast;

import com.aves.flamingodb.App.FlamingoApp;
import com.aves.flamingodb.App.core.FlamingoProvider;

public class GlobalServices{

    public static GlobalServices instance;
    private static HashMap<String, Object> sessions;
    private static FlamingoApp flamingoApp = FlamingoApp.getInstance();

    public static synchronized GlobalServices getInstance(){
        if(instance == null){
            instance = new GlobalServices();
            sessions = new HashMap<>();
        }
        return instance;
    }

    @SuppressLint("HardwareIds")
    public String getUniqueID() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length > 2) {
            StackTraceElement caller = stackTrace[2];
            String callingClass = caller.getClassName();
            String callingMethod = caller.getMethodName();
            System.out.println("Appelé par la classe : " + callingClass + ", méthode : " + callingMethod);
        }
        return "123456";
    }

    public void set(String key, Object value){
        sessions.put(key, value);
    }

    public Object get(String key){
        return sessions.get(key);
    }

}