package com.example.newzilla.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by xyz on 30-04-16.
 */
public class ConnectionDetector {

    private final Context context;

    public ConnectionDetector(Context context){

        this.context=context;
    }

    public boolean isConnectedToInternet(){

        ConnectivityManager connectivitymanager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();

        if(networkinfo != null && networkinfo.isAvailable() && networkinfo.isConnected()){

            return true;
        }

        return false;

    }
}
