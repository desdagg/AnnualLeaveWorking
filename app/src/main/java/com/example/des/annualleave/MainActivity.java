package com.example.des.annualleave;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    Button login;

    Context context = this;

    private String currentName = "";
    private String access = "invalid";
    public String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Log In");
        setContentView(R.layout.activity_main);

        //set the edittexts and login button
        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);

        login = (Button) findViewById(R.id.login_button);
        //when login is pressed
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //do database check for login details
                final String user = username.getText().toString();
                final String pword = password.getText().toString();
                access = "invalid";

                DBManager dbManager = DBManager.getInstance(context);

                //create a cursor and get all the login details to validate
                Cursor cursor = dbManager.getLoginDetails();
                cursor.moveToFirst();

                do{
                    //if the username and password match
                    if(user.equals(cursor.getString(0)) && pword.equals(cursor.getString(1))){
                        //set current name to username
                        currentName = cursor.getString(2);
                        //set accesss to the users role
                        access = cursor.getString(3);
                        //set the currentuserId to the users ID
                        currentUserId = cursor.getString(4);
                    }
                }while(cursor.moveToNext());

                //check the access type of the user
                //admin, employee, or manager
                //and bring them to the correct view
                if (access.equals("admin")) {
                    Toast.makeText(MainActivity.this, "Welcome " + currentName + "\n Current role is: admin \n and id of: " + currentUserId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminMenu.class);
                    //pass the id of the current user
                    intent.putExtra("key", currentUserId);
                    startActivity(intent);
                }else if (access.equals("employee")){
                    Toast.makeText(MainActivity.this, "Welcome " + currentName + "\n Current role is: employee", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, EmployeeMenu.class);
                    intent.putExtra("key", currentUserId);
                    startActivity(intent);
                }else if (access.equals("manager")){
                    Toast.makeText(MainActivity.this, "Welcome " + currentName + "\n Current role is: manager\n and id of: " + currentUserId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ManagerMenu.class);
                    intent.putExtra("key", currentUserId);
                    startActivity(intent);
                }
                else{
                    //toast for bad login
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
