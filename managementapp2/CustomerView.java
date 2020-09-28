package com.kirubankamaraj.managementapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class CustomerView extends AppCompatActivity {

    private SQLiteDatabase db;
    private TextView nameView;
    private TextView phoneView;
    private TextView mailView;
    private TextView addressView;
    private TextView freeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        getSupportActionBar().setTitle("Customer Details");

        String v = String.valueOf(getIntent().getIntExtra("id", 0)).trim();

        nameView = (TextView) findViewById(R.id.name_customer);
        phoneView = (TextView) findViewById(R.id.phone_customer);
        mailView = (TextView) findViewById(R.id.email_customer);
        addressView = (TextView) findViewById(R.id.address_customer);
        freeView = (TextView) findViewById(R.id.free_customer);

        db = this.openOrCreateDatabase("Customers", MODE_PRIVATE, null);
 //       Cursor c = db.query("customers", new String[]{"name", "number", "mail", "free", "address"}, "id", new String[]{v}, null, null, null);
        Cursor c = db.rawQuery("Select * From customers Where id='"+v+"'", null);
        c.moveToFirst();
        int nameIndex = c.getColumnIndex("name");
        String name = c.getString(nameIndex);

        int numberIndex = c.getColumnIndex("number");
        String number = c.getString(numberIndex);

        int mailIndex = c.getColumnIndex("mail");
        String mail = c.getString(mailIndex);

        int freeIndex = c.getColumnIndex("free");
        String free = c.getString(freeIndex);

        int addressIndex = c.getColumnIndex("address");
        String address = c.getString(addressIndex);


        nameView.setText(name);
        phoneView.setText(number);
        mailView.setText(mail);
        freeView.setText(free);
        addressView.setText(address);
        c.close();

    }
}
