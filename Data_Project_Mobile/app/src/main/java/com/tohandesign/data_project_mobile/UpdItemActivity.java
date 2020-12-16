package com.tohandesign.data_project_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class UpdItemActivity extends AppCompatActivity {

    DatabaseHelper db;

    public Intent intent;

    public Long MAIN_ITEM_ID;

    public String itemIds[];
    public String mainIds[];

    public String MAIN_ID = "";
    public Spinner KEY_MAIN;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_item);

        db = new DatabaseHelper(getApplicationContext());
        builder = new AlertDialog.Builder(this);

        final EditText KEY_AON_HAND = (EditText)findViewById(R.id.KEY_AON_HAND);
        final EditText KEY_SCH_RCP = (EditText)findViewById(R.id.KEY_SCH_RCP);
        final EditText KEY_ARON_WEEK = (EditText)findViewById(R.id.KEY_ARON_WEEK);
        final EditText KEY_LEAD_TIME = (EditText)findViewById(R.id.KEY_LEAD_TIME);
        final EditText KEY_LS_RULE = (EditText)findViewById(R.id.KEY_LS_RULE);
        final EditText KEY_REQUIRED = (EditText)findViewById(R.id.KEY_REQUIRED);

        KEY_MAIN = (Spinner)findViewById(R.id.KEY_MAIN);


        List<ProductItem> items = db.getAllItems();
        // Array of choices
        itemIds = new String[items.size()];
        mainIds = new String[items.size()+1];
        mainIds[0] = "";
        int i = 0;
        int j = 1;
        for(ProductItem item : items) {
            itemIds[i] = item.getItemId();
            mainIds[j] = item.getItemId();
            j++;
            i++;
        }




        // Selection of the spinner
        Spinner spinner = (Spinner) findViewById(R.id.KEY_ITEM_ID);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, itemIds);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);


        // Application of the Array to the Main Spinner
        ArrayAdapter<String> mainArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, mainIds);
        mainArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        KEY_MAIN.setAdapter(mainArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(itemIds.length != 0) {
                    MAIN_ITEM_ID = Long.parseLong((String) parent.getItemAtPosition(pos));
                    ProductItem item = db.getItem(MAIN_ITEM_ID);
                    KEY_AON_HAND.setText(String.valueOf(item.getAmountOnHand()));
                    KEY_SCH_RCP.setText(String.valueOf(item.getScheduledReceipt()));
                    KEY_ARON_WEEK.setText(String.valueOf(item.getArrivalOnWeek()));
                    KEY_LEAD_TIME.setText(String.valueOf(item.getLeadTime()));
                    KEY_LS_RULE.setText(String.valueOf(item.getLotSizingRule()));
                    KEY_REQUIRED.setText(String.valueOf(item.getItemRequired()));
                    KEY_MAIN.setSelection(getIndexOf(mainIds, String.valueOf(db.getMain(MAIN_ITEM_ID))));

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        KEY_MAIN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(itemIds.length != 0) {
                    MAIN_ID = (String) parent.getItemAtPosition(pos);

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button updButton = (Button)findViewById(R.id.updItem);

        updButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int AON_HAND = Integer.parseInt(String.valueOf(KEY_AON_HAND.getText()));
                int SCH_RCP = Integer.parseInt(String.valueOf(KEY_SCH_RCP.getText()));
                int ARON_WEEK = Integer.parseInt(String.valueOf(KEY_ARON_WEEK.getText()));
                int LEAD_TIME = Integer.parseInt(String.valueOf(KEY_LEAD_TIME.getText()));
                int LS_RULE = Integer.parseInt(String.valueOf(KEY_LS_RULE.getText()));
                int REQUIRED = Integer.parseInt(String.valueOf(KEY_REQUIRED.getText()));
                ProductItem item = new ProductItem(String.valueOf(MAIN_ITEM_ID), AON_HAND, SCH_RCP, ARON_WEEK, LEAD_TIME, LS_RULE, REQUIRED, null);

                db.updateItem(item);
                if(MAIN_ID != "")
                    db.updateChild(Long.parseLong(MAIN_ID), MAIN_ITEM_ID);



                }
        });


        Button delItem = (Button)findViewById(R.id.delItem);

        delItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MAIN_ITEM_ID != null) {
                    String title = "Delete Item";
                    String msg = "If you delete the item, all of its sub-items are also deleted. \n" +
                            "Are you sure you want to delete the item?";

                    //Setting message manually and performing action on button click
                    builder.setMessage(msg)
                            .setTitle(title)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    db.deleteItem(MAIN_ITEM_ID);
                                    Toast.makeText(getApplicationContext(),"The item and sub items you selected have been deleted",
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
                } else {
                    Toast.makeText(getApplicationContext(),"Nothing was selected",
                            Toast.LENGTH_SHORT).show();
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


    public static int getIndexOf(String[] strings, String item) {
        for (int i = 0; i < strings.length; i++) {
            if (item.equals(strings[i])) return i;
        }
        return -1;
    }


}
