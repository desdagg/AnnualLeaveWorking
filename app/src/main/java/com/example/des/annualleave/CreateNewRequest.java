package com.example.des.annualleave;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Des on 18/11/2016.
 */

public class CreateNewRequest extends AppCompatActivity{

    private EditText startDateText;
    private EditText endDateText;

    private Button submit;

    private String currentUserId;
    private DBManager dbManager;

    Calendar myCalendar = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Request");
        //getting the current users id that is passed in
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            currentUserId = extras.getString("key");
        }
        setContentView(R.layout.edit_request);

        startDateText = (EditText) findViewById(R.id.start_text);
        endDateText = (EditText) findViewById(R.id.end_text);

        submit = (Button) findViewById(R.id.submit_button);

        //select the start date of the request and open up a calandar
        // to select from and put it in the edittext
        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new DatePickerDialog(CreateNewRequest.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //select the end date
        endDateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(CreateNewRequest.this, dateEnd, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //get the database instance
        dbManager = DBManager.getInstance(this);

        //add the dates to database
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.submit_button:
                        final String start = startDateText.getText().toString();
                        final String end = endDateText.getText().toString();
                        final String status = "Pending";
                        final String userId = currentUserId;

                        //pass the data into the dbManager for inserting
                        dbManager.insertRequest(start,end,status,userId);
                        finish();
                        break;
                }
            }
        });
    }

    //set the date to the user selected date and update the label
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        }
    };

    //make sure the date is in the right format and set the edit text to the selected date
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        startDateText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        endDateText.setText(sdf.format(myCalendar.getTime()));
    }
}
