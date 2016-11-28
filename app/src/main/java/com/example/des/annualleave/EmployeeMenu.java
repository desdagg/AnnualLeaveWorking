package com.example.des.annualleave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by home on 27/11/2016.
 */

public class EmployeeMenu extends Menu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");

        setContentView(R.layout.menu_selection_employee);

        //view the list of requests
        requestList = (Button) findViewById(R.id.leaveRequests_button);
        requestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reqList = new Intent(EmployeeMenu.this, RequestList.class);
                reqList.putExtra("key", currentUserId);
                startActivity(reqList);
            }
        });
    }
}
