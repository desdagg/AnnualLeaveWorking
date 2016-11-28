package com.example.des.annualleave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by home on 27/11/2016.
 */

public class ManagerMenu extends Menu{

    Button empRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");

        setContentView(R.layout.menu_selection_manager);

        //view the list of requests
        requestList = (Button) findViewById(R.id.leaveRequests_button);
        requestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reqList = new Intent(ManagerMenu.this, RequestList.class);
                reqList.putExtra("key", currentUserId);
                startActivity(reqList);
            }
        });

        empRequests = (Button) findViewById(R.id.employeeRequests_button);
        empRequests.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent reqList = new Intent(ManagerMenu.this, EmployeeRequestList.class);
                reqList.putExtra("key", currentUserId);
                startActivity(reqList);
            }
        });
    }
}
