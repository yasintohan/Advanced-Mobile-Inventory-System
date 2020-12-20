package com.tohandesign.data_project_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class InventoryActivity extends AppCompatActivity {

    DatabaseHelper db;
    public static String[] invHeader = {"Item", "Amount of Hand"};
    public static String[][] inv;
    public static int invLine = 0;

    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        db = new DatabaseHelper(getApplicationContext());
        inv = new String[db.getItemCount()][2];
        final List<ProductItem> items = db.getAllItems();


        if(db.getItemCount() > 0) {
            printInv(items, db);
            setTable(inv,invHeader);
            invLine = 0;
        }

        Button inupdate = (Button)findViewById(R.id.invupdate);

        inupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.getItemCount() > 0) {
                    printInv(items, db);
                    setTable(inv,invHeader);
                    invLine = 0;
                }
            }
        });


        intent = new Intent(this, MainActivity.class);


        Button gomain = (Button)findViewById(R.id.goMain);

        gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }

    public static void printInv(List<ProductItem> items, DatabaseHelper db){

        for (ProductItem item : items) {
            inv[invLine][0] = "Item id :" + item.getItemId();
            inv[invLine][1] = item.getAmountOnHand() + " pieces";
            invLine++;
        }

    }

    public void setTable(String[][] data, String[] header) {

        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        android.view.ViewGroup.LayoutParams params = tableView.getLayoutParams();
        params.height = data[0].length*600;
        tableView.setLayoutParams(params);
        tableView.setColumnCount(data[0].length);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, header));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, data));
    }

}
