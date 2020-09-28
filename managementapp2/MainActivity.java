package com.kirubankamaraj.managementapp2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addCus;
    public SQLiteDatabase db;
    private ListView listView;
    private ArrayList<Customer> customers;
    private CustomerListAdapter adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                Customer c = new Customer(customers.size() + 1, name);
                adapter.addCustomer(c);
//                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Management App");

        db = this.openOrCreateDatabase("Customers", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customers (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR NOT NULL, number VARCHAR NOT NULL, " +
                        "mail TEXT, free String, address TEXT)");

        listView = (ListView) findViewById(R.id.list_view);


        customers = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT id, name FROM customers", null);

        int idIndex = c.getColumnIndex("id");
        int nameIndex = c.getColumnIndex("name");

        if(c.moveToFirst()){
            customers.add(new Customer(c.getInt(idIndex), c.getString(nameIndex)));
        }

        while (c.moveToNext()){
            System.out.println(" "+c.getInt(idIndex)+" "+c.getString(nameIndex));
            customers.add(new Customer(c.getInt(idIndex), c.getString(nameIndex)));
        }
        c.close();

        adapter = new CustomerListAdapter(this, R.layout.adapter_view_layout, customers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, CustomerView.class);
                int id = customers.get(i).getId();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        addCus = findViewById(R.id.plus_cus);
        addCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCustomer.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.notify){
            Intent intent = new Intent(MainActivity.this, SetNotify.class);
            startActivityForResult(intent, 2);
        }

        return super.onOptionsItemSelected(item);
    }
}
