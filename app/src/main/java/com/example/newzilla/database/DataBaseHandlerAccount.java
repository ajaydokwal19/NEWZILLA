package com.example.newzilla.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newzilla.bean.AccountBean;

import java.util.ArrayList;


public class DataBaseHandlerAccount extends SQLiteOpenHelper {

    private final static String name = "db_account";
    private final static int version = 1;
    private final static String TABLE_NAME_ACCOUNT = "account";
    private final static String CUSTOMER_ID = "customer_id";
    private final static String CUSTOMER_DETAILS = "customer_account_string";
    private final static String DROP_TABLE_ACCOUNT = "DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNT;
    private final static String DELETE_TABLE_ACCOUNT = "DELETE FROM " + TABLE_NAME_ACCOUNT;
    private final static String SELECT_VALUE_SELECT = "select ";
    private final static String SELECT_VALUE_FROM = "from ";
    private Cursor cursor;

    private final static String CUSTOMER_NAME = "customer_name";
    private final static String CUSTOMER_MOBILE = "customer_mobile";
    private final static String CUSTOMER_PASSWORD = "customer_password";
    private final static String CUSTOMER_GENDER = "customer_gender";
    private final static String CUSTOMER_IMAGE = "customer_image";

    private final static String CREATE_ACCOUNT_NEW = "create table " + TABLE_NAME_ACCOUNT + "(" + CUSTOMER_ID + " integer primary key," + CUSTOMER_NAME + " text," + CUSTOMER_MOBILE + " text," + CUSTOMER_PASSWORD + " text,"+ CUSTOMER_GENDER + " text," + CUSTOMER_IMAGE + " text," + CUSTOMER_DETAILS + " text);";

    private static DataBaseHandlerAccount sInstance;

    public static synchronized DataBaseHandlerAccount getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataBaseHandlerAccount(context.getApplicationContext());
        }
        return sInstance;
    }

    private DataBaseHandlerAccount(Context context) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_NEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_ACCOUNT);
        onCreate(db);
    }

    public Boolean insert_account_detail_new(AccountBean accountDataSets) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_ID, accountDataSets.getmCustomerId());
        contentValues.put(CUSTOMER_NAME, accountDataSets.getmName());
        contentValues.put(CUSTOMER_MOBILE, accountDataSets.getmMobileNo());
        contentValues.put(CUSTOMER_PASSWORD, accountDataSets.getmPassword());
        contentValues.put(CUSTOMER_GENDER, accountDataSets.getmGender());
        contentValues.put(CUSTOMER_IMAGE, accountDataSets.getmImage());
        contentValues.put(CUSTOMER_DETAILS, accountDataSets.getmDetails());
        db.insert(TABLE_NAME_ACCOUNT, null, contentValues);
        return true;
    }

    public AccountBean check_login(String mobile_no,String password) {
        String select = SELECT_VALUE_SELECT + "*" + " " + SELECT_VALUE_FROM + TABLE_NAME_ACCOUNT+" WHERE "+CUSTOMER_MOBILE+"="+mobile_no;
        AccountBean bean = null;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(select, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                bean = new AccountBean();

                bean.setmCustomerId(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_ID)));
                bean.setmName(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_NAME)));
                bean.setmMobileNo(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_MOBILE)));
                bean.setmPassword(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_PASSWORD)));
                bean.setmGender(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_GENDER)));
                bean.setmImage(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_IMAGE)));


            }
            cursor.close();
        }


        return bean;
    }

    public AccountBean get_account_detail(String mobile_no) {
        String select = SELECT_VALUE_SELECT +  "*" + SELECT_VALUE_FROM + TABLE_NAME_ACCOUNT +" WHERE "+CUSTOMER_MOBILE+" ="+mobile_no;
        AccountBean bean = null;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(select, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                bean = new AccountBean();

                bean.setmCustomerId(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_ID)));
                bean.setmName(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_NAME)));
                bean.setmMobileNo(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_MOBILE)));
                bean.setmGender(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_GENDER)));
                bean.setmImage(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_IMAGE)));

            }
            cursor.close();
        }
        if (bean != null) {
            return bean;
        } else {
            return null;
        }

    }

    public ArrayList<AccountBean> getCustomerDetails() {
        String select = SELECT_VALUE_SELECT + "*" + " " + SELECT_VALUE_FROM + TABLE_NAME_ACCOUNT;
        ArrayList<AccountBean> beanArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(select, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                AccountBean bean = new AccountBean();

                bean.setmCustomerId(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_ID)));
                bean.setmName(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_NAME)));
                bean.setmMobileNo(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_MOBILE)));
                bean.setmGender(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_GENDER)));
                bean.setmImage(cursor.getString(cursor.getColumnIndexOrThrow(CUSTOMER_IMAGE)));

                beanArrayList.add(bean);

            }
            cursor.close();
        }


        return beanArrayList;
    }



    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
