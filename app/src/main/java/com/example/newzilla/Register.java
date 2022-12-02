package com.example.newzilla;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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



public class Register extends AppCompatActivity {

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
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("newzilla", MODE_PRIVATE);
        editor = sharedPreferences.edit();

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

        (findViewById(R.id.btn_back)).setOnClickListener(v -> finish());

        (findViewById(R.id.user_profile_photo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String storageState = Environment.getExternalStorageState();

                if(storageState.equals(Environment.MEDIA_MOUNTED)) {


                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(Register.this,storage_path);

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

        (findViewById(R.id.ll_signup)).setOnClickListener(v -> {

            if(checkValidation()){

                if(!cb_terms.isChecked()){

                    Toast.makeText(getApplicationContext(),"Please check our Terms and Conditions",Toast.LENGTH_SHORT).show();
                    return;
                }

                signup();

            }
        });

        (findViewById(R.id.ll_sign_in)).setOnClickListener(v -> {


                Intent intent = new Intent(this,Login.class);
                startActivity(intent);


        });


    }

    private boolean checkValidation() {

        name = et_name.getText().toString().trim();
        mobile_no = et_mobile_no.getText().toString().trim();
        password = et_password.getText().toString().trim();


        if(name.length() == 0){

            et_name.requestFocus();
            et_name.setError("Enter valid name");

            return false;
        }else if(mobile_no.length() == 0){

            et_mobile_no.requestFocus();
            et_mobile_no.setError("Required");
            return false;
        }else if(mobile_no.length() != 10){

            et_mobile_no.requestFocus();
            et_mobile_no.setError("Incorrect mobile no");
            return false;
        }else if(password.length() == 0){

            et_password.requestFocus();
            et_password.setError("Required");
            return false;
        }else if(gender.length() == 0){

            Toast.makeText(getApplicationContext(),"Please check Gender",Toast.LENGTH_SHORT).show();
            return false;
        }else if(image.length() == 0){

            Toast.makeText(getApplicationContext(),"Please take a photo",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void signup() {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(Register.this);
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result="";
                String response="";
                try {

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("name",name);
                    jsonObject.put("mobile_no",mobile_no);
                    jsonObject.put("password",password);
                    jsonObject.put("gender",gender);

                    AccountBean accountBean = new AccountBean();

                    accountBean.setmName(name);
                    accountBean.setmMobileNo(mobile_no);
                    accountBean.setmPassword(password);
                    accountBean.setmGender(gender);
                    accountBean.setmImage(image);
                    accountBean.setmDetails(jsonObject.toString());

                    DataBaseHandlerAccount.getInstance(Register.this).insert_account_detail_new(accountBean);

                    result = "true";

                }catch (Exception e){
                    result = "Error";

                }
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                dialog.dismiss();

                try {

                if(result.equalsIgnoreCase("true")){

                    editor.putString("mobile_no", mobile_no);
                    editor.putBoolean("hasLoggedIn", true);
                    editor.commit();

                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Register.this,Home.class);
                        startActivity(intent);
                        finish();

                }
                else {

                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                }

                }catch (Exception e){

                    Log.e("Exception", e.toString());

                }

            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute();
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


                Glide.with(Register.this).load(file1.toString()).circleCrop().into((ImageView) findViewById(R.id.profile_photo));

            }

        }


    }


}