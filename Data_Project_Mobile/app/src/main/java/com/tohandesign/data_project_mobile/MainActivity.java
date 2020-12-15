package com.tohandesign.data_project_mobile;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public DatabaseHelper db;
    public Intent invintent;
    public Intent treeintent;
    public Intent orderintent;
    public Intent additemintent;
    public Intent upditemintent;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());
        builder = new AlertDialog.Builder(this);


        invintent = new Intent(this, InventoryActivity.class);
        treeintent = new Intent(this, TreeViewActivity.class);
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

        Button itemTree = (Button)findViewById(R.id.itemTree);

        itemTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(treeintent);
            }
        });


        Button clearDB = (Button)findViewById(R.id.clearDB);

        clearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String title = "Clear Database";
                    String msg = "Are you sure you want to clear the database?";

                    //Setting message manually and performing action on button click
                    builder.setMessage(msg)
                            .setTitle(title)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    db.clearDB();
                                    db.closeDB();
                                    Toast.makeText(getApplicationContext(),"Database have been cleared",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(),"Nothing was deleted",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();



            }
        });





    }





}
