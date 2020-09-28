package com.kirubankamaraj.managementapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class SetNotify extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TimePicker timePicker;
    private DatePicker datePicker;
    private EditText notesText;
    private EditText numberView;
//    private String name;
    private String number;
    private String notes;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timePicker = (TimePicker) view;
        TextView timeView = (TextView) findViewById(R.id.time);
        String hour = String.valueOf(hourOfDay);
        String minu = String.valueOf(minute);
        if (hourOfDay > 12) hourOfDay-=12;
        if (hourOfDay < 10) hour = "0"+hour;
        if (minute < 10) minu = "0"+minu;
        timeView.setText(hour+" : "+minu);
    }

    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notify);
        getSupportActionBar().setTitle("Set Notification");

        dateView = (TextView) findViewById(R.id.date);
        notesText = (EditText) findViewById(R.id.notes);
//        name = getIntent().getStringExtra("name");
        numberView = (EditText) findViewById(R.id.number);
        number = numberView.getText().toString();
        Button button = (Button) findViewById(R.id.date_select);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button button1 = (Button) findViewById(R.id.time_select);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button button2 = (Button) findViewById(R.id.notify_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notes = notesText.getText().toString();

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(
                        datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getHour(),
                        timePicker.getMinute(),
                        0
                );

                setMail(calendar1.getTimeInMillis());
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        datePicker = (DatePicker) view;
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());
        dateView.setText(currentDateString);
    }

    public void setMail(long timeInMillis){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Notify.class);
        intent.setAction("SendEmail");
//        intent.putExtra("name", name);
        intent.putExtra("number", number);
        intent.putExtra("body", notes);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);

        Toast.makeText(this,"Alaram is Set", Toast.LENGTH_SHORT).show();
    }

}
