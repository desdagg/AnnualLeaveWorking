package com.example.des.annualleave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Des on 12/11/2016.
 */

public class DBManager {

    //singleton instance
    private static DBManager sInstance;

    private DBHandler dbHandler;
    private SQLiteDatabase myDatabase;
    private Context context;

    //synchronized instance so the instance can only be viewd by one method at a time
    public static synchronized DBManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBManager(context.getApplicationContext());
        }
        return sInstance;
    }

    //private constructor as class is singleton
    private DBManager(Context c){
        context = c;
        try{
            dbHandler = new DBHandler(context);
            myDatabase = dbHandler.getWritableDatabase();
        }catch (SQLiteCantOpenDatabaseException e){
            Log.e("DB exception: ", "unable to open the database",e);
        }
    }

    //open the database
    public DBManager open(){
        //try catch for opening the database
        try{
            dbHandler = new DBHandler(context);
            myDatabase = dbHandler.getWritableDatabase();
        }catch (SQLiteCantOpenDatabaseException e){
            Log.e("DB exception: ", "unable to open the database",e);
        }
        return this;
    }

    //select values from the database
    public Cursor selectEmployees(){
        String[] employees = new String[] {DBHandler.COLUMN_EMPLOYEE_ID,
                DBHandler.COLUMN_EMPLOYEE_NAME, DBHandler.COLUMN_EMPLOYEE_EMAIL,
                DBHandler.COLUMN_EMPLOYEE_MANAGERID, DBHandler.COLUMN_LEAVE,
                DBHandler.COLUMN_EMPLOYEE_PASSWORD, DBHandler.COLUMN_ROLE};
        Cursor cursor = null;

        try{
            cursor = myDatabase.query(DBHandler.TABLE_EMPLOYEES, employees,null,null,null,null,null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
        }catch (SQLiteException e){
            Log.e("DB exception: ", "",e);
        }
        return cursor;
    }

    //get the requests for individual employee
    public Cursor getRequestsPerEmployee(String employee_id){
        Cursor mCursor = null;
        String[] requests = new String[] {DBHandler.COLUMN_REQUEST_ID,
                DBHandler.COLUMN_REQUEST_START_DATE, DBHandler.COLUMN_REQUEST_END_DATE,
                DBHandler.COLUMN_REQUEST_STATUS, DBHandler.COLUMN_REQUEST_EMPLOYEE_ID};
        try {
            if (employee_id == null || employee_id.length() == 0) {
                mCursor = myDatabase.query(DBHandler.TABLE_REQUESTS, requests, null, null, null, null, null);

            } else {
                mCursor = myDatabase.query(DBHandler.TABLE_REQUESTS, requests,
                        DBHandler.COLUMN_REQUEST_EMPLOYEE_ID + " = " + employee_id, null, null, null, null, null);
            }
        }catch (SQLiteException e) {
            Log.e("DB exception: ", "", e);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //get the requests for all employees of specific manager
    public Cursor getRequestsPerManager(String manager_id){

        //string for selecting fields of request table
        String fieldNames = DBHandler.TABLE_REQUESTS + "." + DBHandler.COLUMN_REQUEST_ID + ", " + DBHandler.COLUMN_REQUEST_START_DATE + ", " + DBHandler.COLUMN_REQUEST_END_DATE
                + ", " + DBHandler.COLUMN_REQUEST_STATUS + ", " + DBHandler.COLUMN_EMPLOYEE_NAME +", " + DBHandler.COLUMN_REQUEST_EMPLOYEE_ID;

        Cursor mCursor = null;
        String[] requests = new String[] {DBHandler.COLUMN_REQUEST_ID,
                DBHandler.COLUMN_REQUEST_START_DATE, DBHandler.COLUMN_REQUEST_END_DATE,
                DBHandler.COLUMN_REQUEST_STATUS, DBHandler.COLUMN_REQUEST_EMPLOYEE_ID};

        try {
            if (manager_id == null || manager_id.length() == 0) {
                mCursor = myDatabase.query(DBHandler.TABLE_REQUESTS, requests, null, null, null, null, null);

            } else {
                //query for selecting requests based off the employees manger id
                String query = "SELECT " + fieldNames + " FROM " + DBHandler.TABLE_REQUESTS + " JOIN " + DBHandler.TABLE_EMPLOYEES + " ON "
                        + DBHandler.TABLE_EMPLOYEES + "." + DBHandler.COLUMN_EMPLOYEE_ID + "=" + DBHandler.TABLE_REQUESTS + "." + DBHandler.COLUMN_REQUEST_EMPLOYEE_ID
                        + " WHERE " + DBHandler.COLUMN_EMPLOYEE_MANAGERID + "=" + manager_id;
                System.out.println("query: " + query);
                mCursor = myDatabase.rawQuery(query,null);
            }
        }catch (SQLiteException e) {
            Log.e("DB exception: ", "", e);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    //insert values into the database
    public void addEmployee(String name, String email, String mID, String eID, String leave, String password, String role){
        ContentValues values = new ContentValues();
        values.put(DBHandler.COLUMN_EMPLOYEE_NAME, name);
        values.put(DBHandler.COLUMN_EMPLOYEE_EMAIL, email);
        values.put(DBHandler.COLUMN_EMPLOYEE_MANAGERID, mID);
        values.put(DBHandler.COLUMN_EMPLOYEE_ID, eID);
        values.put(DBHandler.COLUMN_LEAVE, leave);
        values.put(DBHandler.COLUMN_EMPLOYEE_PASSWORD, password);
        values.put(DBHandler.COLUMN_ROLE, role);

        //try catch for inserts
        try{
            myDatabase.insert(DBHandler.TABLE_EMPLOYEES, null, values);
        }catch(SQLiteException e){
            Log.e("DB exception: ","",e);
        }
    }

    //insert a new request to the database
    public void insertRequest(String start, String end, String status, String userId){
        ContentValues values = new ContentValues();
        values.put(DBHandler.COLUMN_REQUEST_START_DATE, start);
        values.put(DBHandler.COLUMN_REQUEST_END_DATE, end);
        values.put(DBHandler.COLUMN_REQUEST_STATUS, status);
        values.put(DBHandler.COLUMN_REQUEST_EMPLOYEE_ID, userId);

        try{
            myDatabase.insert(DBHandler.TABLE_REQUESTS, null, values);
        }catch(SQLiteException e){
            Log.e("DB exception: ","",e);
        }
    }



    //get the login details for validating
    public Cursor getLoginDetails(){
        myDatabase = dbHandler.getReadableDatabase();
        String[] employees = new String[]{DBHandler.COLUMN_EMPLOYEE_EMAIL, DBHandler.COLUMN_EMPLOYEE_PASSWORD,
                DBHandler.COLUMN_EMPLOYEE_NAME, DBHandler.COLUMN_ROLE, DBHandler.COLUMN_EMPLOYEE_ID};
        Cursor cursor = myDatabase.query(DBHandler.TABLE_EMPLOYEES, employees,null,null,null,null,null);
        return cursor;
    }

    //method for approving a request
    public void approveRequest(String requestId){
        System.out.println("gon approve: " + requestId);
        ContentValues values = new ContentValues();
        values.put(DBHandler.COLUMN_REQUEST_STATUS, "Approved");
        myDatabase.update(DBHandler.TABLE_REQUESTS,values, DBHandler.COLUMN_REQUEST_ID + "=" + requestId, null);
    }

    //method for denying a request
    public void denyRequest(String requestId){
        System.out.println("gon deny: " + requestId);
        ContentValues values = new ContentValues();
        values.put(DBHandler.COLUMN_REQUEST_STATUS, "Declined");
        myDatabase.update(DBHandler.TABLE_REQUESTS,values, DBHandler.COLUMN_REQUEST_ID + "=" + requestId, null);
    }

    //delete a request from the database
    public void deleteRequest(String requestId){
        System.out.println("gon delete: " + requestId);
        myDatabase.delete(DBHandler.TABLE_REQUESTS, DBHandler.COLUMN_REQUEST_ID + "=" + requestId, null);
    }

    //close the database
    public void close() {
        try{
            dbHandler.close();
        }catch (SQLiteException e){
            Log.e("DB exception: ","",e);
        }
    }
}
