package com.tohandesign.data_project_mobile;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    public DatabaseHelper db;
    public Intent invintent;
    public Intent orderintent;
    public Intent additemintent;
    public Intent upditemintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());



        invintent = new Intent(this, InventoryActivity.class);
        orderintent = new Intent(this, OrderItemActivity.class);
        additemintent = new Intent(this, AddItemActivity.class);
        upditemintent = new Intent(this, UpdItemActivity.class);


        Button inventory = (Button)findViewById(R.id.inventory);

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(invintent);
            }
        });

        Button neworder = (Button)findViewById(R.id.orderItem);

        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(orderintent);
            }
        });

        Button addItem = (Button)findViewById(R.id.addItem);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(additemintent);
            }
        });

        Button updItem = (Button)findViewById(R.id.updItem);

        updItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(upditemintent);
            }
        });


        Button clearDB = (Button)findViewById(R.id.clearDB);

        clearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearDB();
                db.closeDB();
            }
        });





    }





}
