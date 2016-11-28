package com.example.des.annualleave;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Des on 13/11/2016.
 */

public class EmployeeList extends AppCompatActivity {

    private ListView listView;

    //string array with column names
    final String[] from = new String[]{ DBHandler.COLUMN_EMPLOYEE_ID,
            DBHandler.COLUMN_EMPLOYEE_NAME, DBHandler.COLUMN_EMPLOYEE_EMAIL,
            DBHandler.COLUMN_EMPLOYEE_MANAGERID, DBHandler.COLUMN_ROLE, DBHandler.COLUMN_LEAVE,
            DBHandler.COLUMN_EMPLOYEE_PASSWORD};

    //employee_view id's
    final int[] to = new int[] {R.id.employee_ID, R.id.employee_Name,
                                R.id.employee_Email, R.id.employee_ManagerID, R.id.employee_role};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Employees");
        setContentView(R.layout.employee_list);

        DBManager dbManager = DBManager.getInstance(this);

        //creating cursor and calling select in dbmanager
        Cursor cursor = dbManager.selectEmployees();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        //create the cursor adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.employee_view, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        //add the adapter to teh listview
        listView.setAdapter(adapter);
    }
}
