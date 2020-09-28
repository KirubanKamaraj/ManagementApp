package com.kirubankamaraj.managementapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomerListAdapter extends ArrayAdapter<Customer> {

    private Context mContext;
    private int mResource;
    private ArrayList<Customer> mCustomers;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        int id = getItem(position).getId();

        Customer customer = new Customer(id, name);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tname = (TextView) convertView.findViewById(R.id.cus_name);
        tname.setText(name);

        return convertView;
    }

    public CustomerListAdapter(Context context, int resource, ArrayList<Customer> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mCustomers = objects;
    }

    public void addCustomer(Customer c){
        mCustomers.add(c);
        notifyDataSetChanged();
    }
}
