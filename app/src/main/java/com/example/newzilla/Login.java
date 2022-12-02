package com.example.newzilla;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newzilla.bean.AccountBean;
import com.example.newzilla.database.DataBaseHandlerAccount;
import com.example.newzilla.utils.Tools;


import org.json.JSONObject;

import java.util.HashMap;


public class Login extends AppCompatActivity {

    EditText et_mobile_no,et_password;
    CheckBox cb_terms;

    String name="",mobile_no="",password="",gender="",message="";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String slide1[], mNavigationData, mBannerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("newzilla", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initToolbar();
        initComponent();

    }

    private void initToolbar() {
        Tools.setSystemBarColor(this, R.color.green_toolbar);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void initComponent() {

        et_mobile_no = findViewById(R.id.et_mobile_no);
        et_password = findViewById(R.id.et_password);
        cb_terms = findViewById(R.id.cb_terms);

        (findViewById(R.id.btn_back)).setOnClickListener(v -> finish());


        (findViewById(R.id.ll_signup)).setOnClickListener(v -> {

            Intent intent = new Intent(this, Register.class);

            startActivity(intent);

        });

        (findViewById(R.id.ll_sign_in)).setOnClickListener(v -> {


            if(checkValidation()){

                if(!cb_terms.isChecked()){

                    Toast.makeText(getApplicationContext(),"Please check our Terms and Conditions",Toast.LENGTH_SHORT).show();
                    return;
                }


                login(mobile_no,password);

            }


        });




    }

    private boolean checkValidation() {

         mobile_no = et_mobile_no.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if(mobile_no.length() == 0){

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
        }

        return true;
    }


    private void login(final String customer_mobile_no,String password) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(Login.this);
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result="";
                String response="";
                try {

                    AccountBean accountBean = DataBaseHandlerAccount.getInstance(Login.this).check_login(mobile_no,password);

                    if(accountBean != null ){

                        if(accountBean.getmPassword().equals(password)){

                            result = "1";
                            message = "Login Successful";
                        }else {

                            result = "2";
                            message = "Wrong Password";
                        }

                    }else{

                        result = "0";
                        message = "User does not exists,Please Register";
                    }


                }catch (Exception e){
                    result = response;

                }
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                dialog.dismiss();
                try {
                if(result.equalsIgnoreCase("1")){

                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

                        editor.putString("mobile_no", customer_mobile_no);
                        editor.putBoolean("hasLoggedIn", true);
                        editor.commit();

                        Intent intent = new Intent(Login.this,Home.class);
                        intent.putExtra("mobile_no",customer_mobile_no);
                        startActivity(intent);
                        finish();


                }else if(result.equalsIgnoreCase("2")) {

                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
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
        ru.execute(customer_mobile_no,password);
    }


}