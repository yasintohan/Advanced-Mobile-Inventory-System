package com.tohandesign.data_project_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    DatabaseHelper db;

    public Intent intent;
    public Intent intentthis;

    public String ITEM_ID;
    public int AON_HAND = 0;
    public int SCH_RCP = 0;
    public int ARON_WEEK = 1;
    public int LEAD_TIME;
    public int LS_RULE = 1;
    public int REQUIRED = 1;
    public String MAIN_ITEM_ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        db = new DatabaseHelper(getApplicationContext());

        final EditText KEY_ITEM_ID = (EditText) findViewById(R.id.KEY_ITEM_ID);
        final EditText KEY_AON_HAND = (EditText)findViewById(R.id.KEY_AON_HAND);
        final EditText KEY_SCH_RCP = (EditText)findViewById(R.id.KEY_SCH_RCP);
        final EditText KEY_ARON_WEEK = (EditText)findViewById(R.id.KEY_ARON_WEEK);
        final EditText KEY_LEAD_TIME = (EditText)findViewById(R.id.KEY_LEAD_TIME);
        final EditText KEY_LS_RULE = (EditText)findViewById(R.id.KEY_LS_RULE);
        final EditText KEY_REQUIRED = (EditText)findViewById(R.id.KEY_REQUIRED);

        List<ProductItem> items = db.getAllItems();
        // Array of choices
        String itemIds[] = new String[db.getItemCount()+1];
        itemIds[0] = " ";
        int i = 1;
        for(ProductItem item : items) {
            itemIds[i] = item.getItemId();
            i++;
        }

        // Selection of the spinner
        Spinner spinner = (Spinner) findViewById(R.id.mainspinner);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, itemIds);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                MAIN_ITEM_ID = (String) parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        intentthis = new Intent(this, AddItemActivity.class);
        Button addItem = (Button)findViewById(R.id.addItem);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(KEY_ITEM_ID.getText().toString())) {
                    if(!TextUtils.isEmpty(KEY_LEAD_TIME.getText().toString())) {
                        if(!TextUtils.isEmpty(KEY_LEAD_TIME.getText().toString())) {

                            if(!TextUtils.isEmpty(KEY_AON_HAND.getText().toString()))
                                AON_HAND = Integer.parseInt(KEY_AON_HAND.getText().toString());
                            if(!TextUtils.isEmpty(KEY_SCH_RCP.getText().toString()))
                                SCH_RCP = Integer.parseInt(KEY_SCH_RCP.getText().toString());
                            if(!TextUtils.isEmpty(KEY_ARON_WEEK.getText().toString()))
                                ARON_WEEK = Integer.parseInt(KEY_ARON_WEEK.getText().toString());
                            if(!TextUtils.isEmpty(KEY_LS_RULE.getText().toString()))
                                LS_RULE = Integer.parseInt(KEY_LS_RULE.getText().toString());
                            if(!TextUtils.isEmpty(KEY_REQUIRED.getText().toString()))
                                REQUIRED = Integer.parseInt(KEY_REQUIRED.getText().toString());

                            ITEM_ID = KEY_ITEM_ID.getText().toString();
                            LEAD_TIME = Integer.parseInt(KEY_LEAD_TIME.getText().toString());

                            List<Long> mlist = null;
                            ProductItem item;
                            item = new ProductItem(ITEM_ID, AON_HAND, SCH_RCP, ARON_WEEK, LEAD_TIME, LS_RULE, REQUIRED, mlist);

                            if(MAIN_ITEM_ID == " ")
                                db.createItem(item);
                            else
                                db.createItem(item, Long.parseLong(MAIN_ITEM_ID));


                            startActivity(intentthis);
                        }
                    }
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
}
