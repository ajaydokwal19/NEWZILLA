package com.example.newzilla.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.newzilla.Application_Context;
import com.example.newzilla.R;
import com.example.newzilla.SplashScreen;
import com.example.newzilla.database.DataBaseHandlerAccount;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class Methods {



    public static void toast(String message) {
        Toast.makeText(Application_Context.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void glide_image_loader(Drawable url, final ImageView imageView) {
        Glide.with(Application_Context.getAppContext()).load(url).apply(getOption("Default")).into(imageView);
    }

    public static void glide_image_loader(String url, final ImageView imageView) {
        Glide.with(Application_Context.getAppContext()).load(url).apply(getOption("Default")).into(imageView);
    }

    public static void glide_image_loader_fixed_size(String url, final ImageView imageView) {
        Glide.with(Application_Context.getAppContext()).load(url).apply(getOption("fixed")).into(imageView);
    }

    public static void glide_image_loader_banner(String url, final ImageView imageView) {
        Glide.with(Application_Context.getAppContext()).load(url).apply(getOption("Default")).into(imageView);
    }

    private static RequestOptions getOption(String which) {

        RequestOptions options;
        switch (which) {
            case "fixed":
                options = new RequestOptions()
                        .error(R.drawable.icon)
                        .override(300, 300)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE);
                break;
            case "Category":
                options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE);
                break;
            default:
                options = new RequestOptions()
                        .error(R.drawable.icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE);
                break;
        }
        return options;

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertInputStreamToString(BufferedReader bufferedReader) {
        try {
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getSubmitDatetime() {

        Calendar calendar = Calendar.getInstance();

        int cyear = calendar.get(Calendar.YEAR);
        int cmonth = calendar.get(Calendar.MONTH);
        // int cmonth = 8;

        int cday = calendar.get(Calendar.DAY_OF_MONTH);

        int chour = calendar.get(Calendar.HOUR_OF_DAY);
        int cminute = calendar.get(Calendar.MINUTE);
        int csecond = calendar.get(Calendar.SECOND);

        String year = String.valueOf(cyear);
        String month = String.valueOf(cmonth+1);
        String day = String.valueOf(cday);

        String hour = String.valueOf(chour);
        String minute = String.valueOf(cminute);
        String second = String.valueOf(csecond);

        if ((cmonth+1)<10){

            month = "0"+ month;
        }

        if (cday<10){

            day = "0" + day;
        }

        if (chour<10){

            hour = "0" + hour;
        }

        if (cminute<10){

            minute = "0" + minute;

        }

        if (csecond<10){

            second = "0" + second;
        }

        return day+"-"+month+"-"+year+" "+hour+":"+minute+":"+second;

    }

    public static String getImageName() {

        Calendar calendar = Calendar.getInstance();

        int cyear = calendar.get(Calendar.YEAR);
        int cmonth = calendar.get(Calendar.MONTH);
        // int cmonth = 8;

        int cday = calendar.get(Calendar.DAY_OF_MONTH);

        int chour = calendar.get(Calendar.HOUR_OF_DAY);
        int cminute = calendar.get(Calendar.MINUTE);
        int csecond = calendar.get(Calendar.SECOND);

        String year = String.valueOf(cyear);
        String month = String.valueOf(cmonth+1);
        String day = String.valueOf(cday);

        String hour = String.valueOf(chour);
        String minute = String.valueOf(cminute);
        String second = String.valueOf(csecond);

        if ((cmonth+1)<10){

            month = "0"+ month;
        }

        if (cday<10){

            day = "0" + day;
        }

        if (chour<10){

            hour = "0" + hour;
        }

        if (cminute<10){

            minute = "0" + minute;

        }

        if (csecond<10){

            second = "0" + second;
        }

        return getSubmitDatetime().replace("-", "").replace(":", "").replace(" ", "") + ".jpg";

    }

    public static String convertServerDateToUserTimeZone(String serverDate) {
        String serverdateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String ourdate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(serverdateFormat, Locale.UK);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(serverDate);
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); //this format changeable
            dateFormatter.setTimeZone(timeZone);
            ourdate = dateFormatter.format(value);

        } catch (Exception e) {
            ourdate = "0000-00-00 00:00:00";
        }
        return ourdate;
    }


}
