package com.aves.flamingodb.App.services;

import static android.hardware.usb.UsbDevice.getDeviceId;

import static java.security.AccessController.getContext;

import java.util.HashMap;

import android.content.Context;
import android.media.MediaDrm;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyManager.*;

public class GlobalServices{

    public static GlobalServices instance;
    private static HashMap<String, Object> sessions;

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public static synchronized GlobalServices getInstance(){
        if(instance == null){
            instance = new GlobalServices();
            sessions = new HashMap<>();
        }
        return instance;
    }

    public String getUniqueID() {
        return "123456";
    }

    public void set(String key, Object value){
        sessions.put(key, value);
    }

    public Object get(String key){
        return sessions.get(key);
    }

}