package com.example.des.annualleave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Des on 12/11/2016.
 */

public class Menu extends AppCompatActivity{

    //menu buttons
    Button requestList;

    protected String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");
        //getting the current users id that is passed in
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            currentUserId = extras.getString("key");
        }
    }
}
