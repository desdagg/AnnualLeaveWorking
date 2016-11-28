package com.example.des.annualleave;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by home on 27/11/2016.
 */

public class EmployeeRequestList extends AppCompatActivity{

    private DBManager dbManager;
    private ListView requestlistView;
    private Button addRequest;
    private String currentUserId;

    private TextView requestInfo;

    private Button approve;
    private Button decline;
    private Button cancel;

    //string array with column names
    final String[] from1 = new String[]{
            DBHandler.COLUMN_REQUEST_START_DATE, DBHandler.COLUMN_REQUEST_END_DATE,
            DBHandler.COLUMN_REQUEST_STATUS,DBHandler.COLUMN_EMPLOYEE_NAME, DBHandler.COLUMN_REQUEST_EMPLOYEE_ID, DBHandler.COLUMN_REQUEST_ID};

    //request_view id's
    final int[] to1 = new int[] {R.id.req_start_date,
            R.id.req_end_date, R.id.req_status, R.id.req_emp_id};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Employee's Requests");
        //getting the current users id that is passed in
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getString("key");
        }
        setContentView(R.layout.request_list);

        dbManager = DBManager.getInstance(this);

        //create cursor and get the requests for the specific manager
        Cursor cursor = dbManager.getRequestsPerManager(currentUserId);

        //create teh listview
        requestlistView = (ListView) findViewById(R.id.request_list_view);
        requestlistView.setEmptyView(findViewById(R.id.request_empty));

        //create the cursor adaptor for the listview
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.request_view, cursor, from1, to1, 0);
        adapter.notifyDataSetChanged();
        //add the adapter to the listview
        requestlistView.setAdapter(adapter);

        //set the listener for the listview if an item is pressed
        requestlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(EmployeeRequestList.this,"id " + id +"\n pos " + position,Toast.LENGTH_SHORT).show();

                //show the dialogue box and pass the id of the clicked list item
                String outId = Long.toString(id);
                showDialog(outId);
            }
        });

        //set the button for adding a request for the current user
        addRequest = (Button) findViewById(R.id.add_request_button);
        addRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent addReq = new Intent(EmployeeRequestList.this, CreateNewRequest.class);
                addReq.putExtra("key", currentUserId);
                startActivity(addReq);
            }
        });
    }

    //creating a popup dialogue box
    private void showDialog(String id){

        final Dialog requestDialog = new Dialog(this);
        final String outId = id;
        requestDialog.setTitle("request response");
        requestDialog.setContentView(R.layout.request_box);

        //the dialogue will show the id of the clicked request
        String output = "Request id: " + id;

        //set the text to the id of the selected request
        requestInfo = (TextView) requestDialog.findViewById(R.id.request_info);
        requestInfo.setText(output);

        cancel = (Button) requestDialog.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //close the popup box
                requestDialog.dismiss();
            }
        });

        approve = (Button) requestDialog.findViewById(R.id.delete_button);
        approve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               //approve the selected request
                dbManager.approveRequest(outId);
                Toast.makeText(EmployeeRequestList.this,"Request No: " + outId + "\n has been approved.", Toast.LENGTH_SHORT).show();
                requestDialog.dismiss();
            }
        });

        decline = (Button) requestDialog.findViewById(R.id.decline_button);
        decline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //decline the selected request
                dbManager.denyRequest(outId);
                Toast.makeText(EmployeeRequestList.this,"Request No: " + outId + "\n has been declined.", Toast.LENGTH_SHORT).show();
                requestDialog.dismiss();
            }
        });

        requestDialog.show();
    }
}
