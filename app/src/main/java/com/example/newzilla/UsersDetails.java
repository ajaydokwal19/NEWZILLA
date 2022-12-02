package com.example.newzilla;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newzilla.bean.AccountBean;
import com.example.newzilla.database.DataBaseHandlerAccount;
import com.example.newzilla.utils.ImagePicker;
import com.example.newzilla.utils.Methods;
import com.example.newzilla.utils.Tools;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;


public class UsersDetails extends AppCompatActivity {

    RelativeLayout user_profile_photo;
    EditText et_name,et_mobile_no,et_password;
    RadioButton rb_male,rb_female;
    CheckBox cb_terms;
    File storage_path;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name="",mobile_no="",password="",gender="",image="",message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_details);

        sharedPreferences = getSharedPreferences("newzilla", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("mobile_no") != null) {
                mobile_no = bundle.getString("mobile_no");
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

         user_profile_photo = findViewById(R.id.user_profile_photo);
         et_name = findViewById(R.id.et_name);
         et_mobile_no = findViewById(R.id.et_mobile_no);
         et_password = findViewById(R.id.et_password);
         rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        cb_terms = findViewById(R.id.cb_terms);


        AccountBean accountBean = new AccountBean();

        accountBean = DataBaseHandlerAccount.getInstance(UsersDetails.this).get_account_detail(mobile_no);

        if(accountBean != null){
            if(accountBean.getmName()!=null){
                et_name.setText(accountBean.getmName());
            }
            et_mobile_no.setText(accountBean.getmMobileNo());
            et_password.setText(accountBean.getmPassword());

            if (accountBean.getmGender().equalsIgnoreCase("M")){
                rb_male.setChecked(true);
            }else {
                rb_female.setChecked(true);
            }

            File file1 = new File(storage_path,accountBean.getmImage());
            Glide.with(UsersDetails.this).load(file1.toString()).circleCrop().into((ImageView) findViewById(R.id.profile_photo));



        }

        (findViewById(R.id.user_profile_photo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String storageState = Environment.getExternalStorageState();

                if(storageState.equals(Environment.MEDIA_MOUNTED)) {


                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(UsersDetails.this,storage_path);

                    startActivityForResult(chooseImageIntent, 1001);
                }
            }
        });

        (findViewById(R.id.rb_male)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               gender = "M";
            }
        });


        (findViewById(R.id.rb_female)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender = "F";
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {

            Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);

            if (bitmap != null) {

                image = Methods.getImageName();

                if (!storage_path.exists()) {

                    storage_path.mkdirs();
                }

                File file1 = new File(storage_path, image);

                try {

                    FileOutputStream out = new FileOutputStream(file1);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }


                Glide.with(UsersDetails.this).load(file1.toString()).circleCrop().into((ImageView) findViewById(R.id.profile_photo));

            }


        }


    }


}