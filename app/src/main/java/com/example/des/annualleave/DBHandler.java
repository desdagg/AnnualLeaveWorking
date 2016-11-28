package com.example.des.annualleave;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Des on 11/11/2016.
 */

public class DBHandler extends SQLiteOpenHelper{

    //database variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AnnualLeave.db";

    public static final String TABLE_EMPLOYEES = "employees";
    public static final String TABLE_REQUESTS = "requests";

    //columns for employees table
    public static final String COLUMN_EMPLOYEE_NAME = "_name";
    public static final String COLUMN_EMPLOYEE_EMAIL = "_emailAddress";
    public static final String COLUMN_EMPLOYEE_MANAGERID = "_managerID";
    public static final String COLUMN_EMPLOYEE_ID = "_id";
    public static final String COLUMN_LEAVE = "_leave";
    public static final String COLUMN_EMPLOYEE_PASSWORD = "_password";
    public static final String COLUMN_ROLE = "_role";

    //columns for request table
    public static final String COLUMN_REQUEST_ID = "_id";
    public static final String COLUMN_REQUEST_START_DATE = "_startDate";
    public static final String COLUMN_REQUEST_END_DATE = "_endDate";
    public static final String COLUMN_REQUEST_STATUS = "_status";
    public static final String COLUMN_REQUEST_EMPLOYEE_ID = "_employeeId";

    //admin details
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_EMAIL = "a1";
    public static final String ADMIN_ID = "9001";
    public static final String ADMIN_PASSWORD = "p1";
    public static final String ADMIN_MID = "0";
    public static final String ADMIN_LEAVE = "0";
    public static final String ADMIN_ROLE = "admin";

    //dummy request
    public static final int REQ_ID = 1111;
    public static final String REQ_START = "1234";
    public static final String REQ_END = "2345";
    public static final String REQ_STATUS = "Pending";
    public static final String REQ_EMP_ID = "1001";


    //sql database creates fro employees table
    private String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "("
            + COLUMN_EMPLOYEE_ID + " TEXT PRIMARY KEY, "
            + COLUMN_EMPLOYEE_NAME + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_EMAIL + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_MANAGERID + " TEXT, "
            + COLUMN_LEAVE + " TEXT, "
            + COLUMN_ROLE + " TEXT NOT NULL);";

    //sql database creates for requests table
    private String CREATE_REQUESTS_TABLE = "CREATE TABLE " + TABLE_REQUESTS + "("
            + COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
            + COLUMN_REQUEST_START_DATE + " TEXT NOT NULL, "
            + COLUMN_REQUEST_END_DATE + " TEXT NOT NULL, "
            + COLUMN_REQUEST_STATUS + " TEXT NOT NULL, "
            + COLUMN_REQUEST_EMPLOYEE_ID + " TEXT NOT NULL);";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        db.execSQL(CREATE_EMPLOYEES_TABLE);
        db.execSQL(CREATE_REQUESTS_TABLE);


        //auto insert admin
        ContentValues adminValues = new ContentValues();
        adminValues.put(COLUMN_EMPLOYEE_ID, ADMIN_ID);
        adminValues.put(COLUMN_EMPLOYEE_NAME, ADMIN_NAME);
        adminValues.put(COLUMN_EMPLOYEE_PASSWORD, ADMIN_PASSWORD);
        adminValues.put(COLUMN_EMPLOYEE_EMAIL, ADMIN_EMAIL);
        adminValues.put(COLUMN_EMPLOYEE_MANAGERID, ADMIN_MID);
        adminValues.put(COLUMN_LEAVE, ADMIN_LEAVE);
        adminValues.put(COLUMN_ROLE, ADMIN_ROLE);
        //db.insert(TABLE_EMPLOYEES, null, adminValues);

        //test insert a request
        ContentValues reqValues = new ContentValues();
        reqValues.put(COLUMN_REQUEST_ID, REQ_ID);
        reqValues.put(COLUMN_REQUEST_START_DATE, REQ_START);
        reqValues.put(COLUMN_REQUEST_END_DATE, REQ_END);
        reqValues.put(COLUMN_REQUEST_STATUS, REQ_STATUS);
        reqValues.put(COLUMN_REQUEST_EMPLOYEE_ID, REQ_EMP_ID);
        db.insert(TABLE_EMPLOYEES, null, adminValues);
        db.insert(TABLE_REQUESTS, null, reqValues);

        //auto insert a manager
        ContentValues empValues = new ContentValues();
        empValues.put(COLUMN_EMPLOYEE_ID, "8888");
        empValues.put(COLUMN_EMPLOYEE_NAME, "steven");
        empValues.put(COLUMN_EMPLOYEE_PASSWORD, "steven");
        empValues.put(COLUMN_EMPLOYEE_EMAIL, "steven");
        empValues.put(COLUMN_EMPLOYEE_MANAGERID, "8111");
        empValues.put(COLUMN_LEAVE, "10");
        empValues.put(COLUMN_ROLE, "manager");
        db.insert(TABLE_EMPLOYEES, null, empValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        onCreate(db);
    }
}
