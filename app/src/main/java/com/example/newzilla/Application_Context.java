package com.example.newzilla;

import android.app.Application;
import android.content.Context;

public class Application_Context extends Application {
    private static Application_Context mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
