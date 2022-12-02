package com.example.newzilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newzilla.bean.AccountBean;
import com.example.newzilla.database.DataBaseHandlerAccount;
import com.example.newzilla.utils.ImagePicker;
import com.example.newzilla.utils.Methods;
import com.example.newzilla.utils.Tools;

import java.io.File;
import java.io.FileOutputStream;


public class NewsDetails extends AppCompatActivity {

    RelativeLayout user_profile_photo;
    TextView tv_title,tv_content,tv_datetime,tv_author;
    ImageView iv_image;
    CheckBox cb_terms;
    File storage_path;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String title="",content="",datetime="",author="",image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        sharedPreferences = getSharedPreferences("newzilla", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("title") != null) {
                title = bundle.getString("title");
            }
            if (bundle.getString("content") != null) {
                content = bundle.getString("content");
            }
            if (bundle.getString("author") != null) {
                author = bundle.getString("author");
            }
            if (bundle.getString("datetime") != null) {
                datetime = bundle.getString("datetime");
            }
            if (bundle.getString("image") != null) {
                image = bundle.getString("image");
            }

        }

        initToolbar();
        initComponent();

    }

    private void initToolbar() {
        Tools.setSystemBarColor(this, R.color.green_toolbar);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // image storage
        storage_path = new File(getFilesDir(),"images");
    }

    private void initComponent() {


        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_author = findViewById(R.id.tv_author);
        tv_datetime = findViewById(R.id.tv_datetime);
        iv_image = findViewById(R.id.iv_image);




        tv_title.setText(title);
        tv_content.setText(Html.fromHtml(content));
        tv_author.setText(author);
        tv_datetime.setText(datetime);


        File file1 = new File(storage_path,image);
        Glide.with(NewsDetails.this).load(file1.toString()).circleCrop().into(iv_image);



    }



}