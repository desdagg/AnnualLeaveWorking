package com.example.des.annualleave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by home on 27/11/2016.
 */

public class AdminMenu extends Menu {

    Button createEmployee;
    Button employeeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getting the current users id that is passed in


        setContentView(R.layout.menu_selection_admin);

        //Create employees
        createEmployee = (Button) findViewById(R.id.createEmployee_button);
        createEmployee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do database check for login details
                Intent create = new Intent(AdminMenu.this, CreateEmployee.class);
                startActivity(create);
            }
        });

        //View the list of employees
        employeeList = (Button) findViewById(R.id.employeeList_button);
        employeeList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent empList = new Intent(AdminMenu.this, EmployeeList.class);
                startActivity(empList);
            }
        });
    }
}
