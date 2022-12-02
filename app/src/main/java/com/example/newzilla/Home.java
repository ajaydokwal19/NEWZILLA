package com.example.newzilla;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.newzilla.database.DataBaseHandlerAccount;
import com.example.newzilla.fragments.NewsFragment;
import com.example.newzilla.fragments.UsersFragment;
import com.example.newzilla.utils.Methods;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home extends AppCompatActivity {

    Context mContext;
    Toolbar toolbar;
    ProgressBar progressBar;

    BottomNavigationView bottomNavigationView;
    FrameLayout frame_fragment;
    FragmentManager fragmentManager;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        sharedPreferences = getSharedPreferences("newzilla", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        progressBar = findViewById(R.id.splash_screen_progress_bar);
        frame_fragment = findViewById(R.id.frame_fragment);
        bottomNavigationView = findViewById(R.id.navigation);

        fragmentManager = getSupportFragmentManager();


        NewsFragment newsFragment = NewsFragment.getInstance("data1", "data2");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment, newsFragment, "Zero");
        fragmentTransaction.addToBackStack("Zero");
        fragmentTransaction.commit();
        mContext = getApplicationContext();

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


    }



    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        int itemId = item.getItemId();
        if (itemId == R.id.home) {

            NewsFragment newsFragment = NewsFragment.getInstance("data1", "data2");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment, newsFragment, "Zero");
            fragmentTransaction.addToBackStack("Zero");
            fragmentTransaction.commit();
        }
        if (itemId == R.id.users) {

            UsersFragment usersFragment = UsersFragment.getInstance("data1", "data2");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment, usersFragment, "One");
            fragmentTransaction.addToBackStack("One");
            fragmentTransaction.commit();
        }
        if (itemId == R.id.logout) {

            logOut();
        }


        return true;
    };








    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    protected void onRestart() {

        super.onRestart();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void logOut() {

        final ProgressDialog dialog = ProgressDialog.show(Home.this, "Logging Out", "Please wait ...", true);

        dialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // clear session

                    editor.clear();
                    editor.apply();

                    Thread.sleep(3000);
                } catch (Exception e) {

                    e.printStackTrace();

                }

                dialog.dismiss();

                Intent i = new Intent(Home.this, SplashScreen.class);
                startActivity(i);
                finish();


            }
        }).start();
    }


}
