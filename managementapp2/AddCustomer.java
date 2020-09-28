package com.kirubankamaraj.managementapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddCustomer extends AppCompatActivity {
    private TextInputLayout textName;
    private TextInputLayout textPhone;
    private TextInputLayout textEmail;
    private EditText textService;
    private EditText textAddress;
    private Button addBut;
    private String name;
    private String number;
    private long number1;
    private String mail;
    private String service;
    private String address;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Customer");

        setContentView(R.layout.activity_add_customer);
        textName = findViewById(R.id.text_input_name);
        textPhone  = findViewById(R.id.text_input_phone);
        textEmail = findViewById(R.id.text_input_email);
        textService = findViewById(R.id.free_service);
        textAddress = findViewById(R.id.address);
        addBut = findViewById(R.id.addButton);

        db = this.openOrCreateDatabase("Customers", MODE_PRIVATE, null);

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = textPhone.getEditText().getText().toString();
                name = textName.getEditText().getText().toString();
                mail = textEmail.getEditText().getText().toString();
                service = textService.getText().toString();
                address = textAddress.getText().toString();
                number1 = Long.parseLong(number);

                Calendar calendar = Calendar.getInstance();

                calendar.add(Calendar.MONTH, Integer.parseInt(service));

                String date = calendar.get(Calendar.DATE) +"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);
                System.out.println(date);

                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("number", number1);
                contentValues.put("mail", mail);
                contentValues.put("free", date);
                contentValues.put("address", address);

                if (isValidPhone()){
                    db.insert("customers", null, contentValues);
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean isValidPhone(){
        number = textPhone.getEditText().getText().toString();
        name = textName.getEditText().getText().toString();

        if (number.equals("") || name.equals("")) return false;
        return true;
    }

}
