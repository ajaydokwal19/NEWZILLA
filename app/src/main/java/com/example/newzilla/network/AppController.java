package com.example.newzilla.network;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*@ReportsCrashes(formUri = "",
        mailTo = "ajay.dokwal19@gmail.com",
        mode = ReportingInteractionMode.SILENT,
        resToastText = R.string.crash_toast_text)*/


public class AppController extends Application {
    private static Context context;
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;



    private static AppController myApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }
    private RequestQueue mRequestQueue;




    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();

        myApplication = this;

    }

    public static Context getGlobalContext() {
        return context;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public RequestQueue getReqQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToReqQueue(Request<T> req, String tag) {

        getReqQueue().add(req);
    }

    public <T> void addToReqQueue(Request<T> req) {

        getReqQueue().add(req);
    }

    public void cancelPendingReq(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
