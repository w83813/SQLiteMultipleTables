package com.example.sqlitemultipletables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etProduct,etQty,etPrice;
    Button btAdd;
    Spinner spinner;
    ListView listView1,listView2,listView3;

    DatabaseHelper databaseHelper;

    ArrayList arrayList1,arrayList2,arrayList3;
    ArrayAdapter arrayAdapter1,arrayAdapter2,arrayAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etProduct = findViewById(R.id.et_product);
        etQty = findViewById(R.id.et_qty);
        etPrice = findViewById(R.id.et_price);
        btAdd = findViewById(R.id.bt_add);
        spinner = findViewById(R.id.spinner);
        listView1 = findViewById(R.id.list_view1);
        listView2 = findViewById(R.id.list_view2);
        listView3 = findViewById(R.id.list_view3);

        databaseHelper = new DatabaseHelper(this);

        //Add Database Value to ArrayList
        arrayList1 = databaseHelper.getProduct();
        arrayList2 = databaseHelper.getQty();
        arrayList3 = databaseHelper.getPrice();

        //Initialize ArrayAdapter For ListView
        arrayAdapter1 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList1);
        listView1.setAdapter(arrayAdapter1);

        arrayAdapter2 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList2);
        listView2.setAdapter(arrayAdapter2);

        arrayAdapter3 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList3);
        listView3.setAdapter(arrayAdapter3);


        //Create String
        String[] spinnerList = new String[]{"Product","Qty","Price"};

        //Initialize ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if Product Select
                if (position == 0){
                    listView1.setVisibility(View.VISIBLE);
                    listView2.setVisibility(View.GONE);
                    listView3.setVisibility(View.GONE);
                }

                //if Qty Select
                if (position == 1){
                    listView1.setVisibility(View.GONE);
                    listView2.setVisibility(View.VISIBLE);
                    listView3.setVisibility(View.GONE);
                }

                //if Price Select
                if (position == 2){
                    listView1.setVisibility(View.GONE);
                    listView2.setVisibility(View.GONE);
                    listView3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Values From EditText
                String product = etProduct.getText().toString();
                String qty = etQty.getText().toString();
                String price = etPrice.getText().toString();

                if(!product.isEmpty() && !qty.isEmpty() && !price.isEmpty()){
                    if(databaseHelper.insert(product,qty,price)){

                        //Display Toast Message
                        Toast.makeText(getApplicationContext(),"Data Inserted..",Toast.LENGTH_SHORT).show();

                        //Clear Edit
                        etProduct.setText("");
                        etQty.setText("");
                        etPrice.setText("");

                        //Clear ArrayList
                        arrayList1.clear();
                        arrayList2.clear();
                        arrayList3.clear();

                        //Add Data to ArrayList
                        arrayList1.addAll(databaseHelper.getProduct());
                        arrayList2.addAll(databaseHelper.getQty());
                        arrayList3.addAll(databaseHelper.getPrice());

                        //Notify ArrayAdapter
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();

                        //Refresh ListView
                        listView1.invalidateViews();
                        listView1.refreshDrawableState();
                    }
                }
            }
        });
    }
}
