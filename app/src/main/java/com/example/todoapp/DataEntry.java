package com.example.todoapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;
import android.provider.BaseColumns;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class DataEntry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    DataBaseHelper mydb;
    SQLiteDatabase db;

    EditText editText, editText1, editText2;
    Button add;
    public String dateString;
    public String timeString;
    public String textTask;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra);

        editText1 = (EditText) findViewById(R.id.editTextDate);
        editText2 = (EditText) findViewById(R.id.editTextTime);

        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        mydb = new DataBaseHelper(this);
        editText = (EditText) findViewById(R.id.editTextWhat);
        textTask = editText.getText().toString().trim();
        add = (Button) findViewById(R.id.add_button);

        addData();


    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        timeString = Integer.toString(hour) + Integer.toString(min);
        if (min > 9)
            editText2.setText(hour + ":" + min);
        else
            editText2.setText(hour + ":0" + min);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        editText1.setText(currentDateString);
        dateString = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);

    }


    public void addData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                boolean isInserted = mydb.insertData(editText.getText().toString(), dateString, timeString);

                if (isInserted) {
                    editText.getText().clear();
                    editText1.getText().clear();
                    editText2.getText().clear();
                    Toast.makeText(DataEntry.this, "Task Added", Toast.LENGTH_SHORT).show();
                    openActivityProfile();
                    //ob.getmAdapter().swapcursor(cr());
                }
            }
        });
    }

    public Cursor cr() {
        return db.query(DataBaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DataBaseHelper.COL_1 + "DESC"
        );
    }

    public void openActivityProfile()
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    }

