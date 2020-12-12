package com.tohandesign.data_project_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderItemActivity extends AppCompatActivity {

    DatabaseHelper db;
    RecyclerView recyclerView;
    final ArrayList<ProductItem> mList = new ArrayList<>();

    private static final String[] HeaderData =  { "","Period","1","2","3","4","5","6","7","8","9","10" };
    private static String[][] DATA_TO_SHOW = null;
    public static int[] grossreq = new int[10];


    private Button orderbutton;

    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);


        db = new DatabaseHelper(getApplicationContext());


        recyclerView= findViewById(R.id.cardlisterr);
        final Adapter adapter = new Adapter(this, mList);

        final EditText grosstext1 = (EditText) findViewById(R.id.editText1);
        final EditText grosstext2 = (EditText)findViewById(R.id.editText2);
        final EditText grosstext3 = (EditText)findViewById(R.id.editText3);
        final EditText grosstext4 = (EditText)findViewById(R.id.editText4);
        final EditText grosstext5 = (EditText)findViewById(R.id.editText5);
        final EditText grosstext6 = (EditText)findViewById(R.id.editText6);
        final EditText grosstext7 = (EditText)findViewById(R.id.editText7);
        final EditText grosstext8 = (EditText)findViewById(R.id.editText8);
        final EditText grosstext9 = (EditText)findViewById(R.id.editText9);
        final EditText grosstext10 = (EditText)findViewById(R.id.editText10);

        final EditText itemnumEditText = (EditText)findViewById(R.id.itemnum);


        final Button resetOrder = (Button)findViewById(R.id.resetOrder);
        orderbutton = (Button) findViewById(R.id.orderItem);
        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemnumtext = itemnumEditText.getText().toString();
                if(!TextUtils.isEmpty(itemnumtext)){
                    mList.clear();
                    recyclerView.setAdapter(adapter);
                    if(TextUtils.isEmpty(grosstext1.getText().toString()))
                        grossreq[0] = 0;
                    else
                        grossreq[0] = Integer.parseInt(grosstext1.getText().toString());

                    if(TextUtils.isEmpty(grosstext2.getText().toString()))
                        grossreq[1] = 0;
                    else
                        grossreq[1] = Integer.parseInt(grosstext2.getText().toString());

                    if(TextUtils.isEmpty(grosstext3.getText().toString()))
                        grossreq[2] = 0;
                    else
                        grossreq[2] = Integer.parseInt(grosstext3.getText().toString());

                    if(TextUtils.isEmpty(grosstext4.getText().toString()))
                        grossreq[3] = 0;
                    else
                        grossreq[3] = Integer.parseInt(grosstext4.getText().toString());

                    if(TextUtils.isEmpty(grosstext5.getText().toString()))
                        grossreq[4] = 0;
                    else
                        grossreq[4] = Integer.parseInt(grosstext5.getText().toString());

                    if(TextUtils.isEmpty(grosstext6.getText().toString()))
                        grossreq[5] = 0;
                    else
                        grossreq[5] = Integer.parseInt(grosstext6.getText().toString());

                    if(TextUtils.isEmpty(grosstext7.getText().toString()))
                        grossreq[6] = 0;
                    else
                        grossreq[6] = Integer.parseInt(grosstext7.getText().toString());

                    if(TextUtils.isEmpty(grosstext8.getText().toString()))
                        grossreq[7] = 0;
                    else
                        grossreq[7] = Integer.parseInt(grosstext8.getText().toString());

                    if(TextUtils.isEmpty(grosstext9.getText().toString()))
                        grossreq[8] = 0;
                    else
                        grossreq[8] = Integer.parseInt(grosstext9.getText().toString());

                    if(TextUtils.isEmpty(grosstext10.getText().toString()))
                        grossreq[9] = 0;
                    else
                        grossreq[9] = Integer.parseInt(grosstext10.getText().toString());




                    ProductItem item = null;
                    item = db.getItem(Long.parseLong(itemnumtext));

                    if(item != null) {
                        calculate(item, grossreq);
                        itemnumtext = "";
                        itemnumEditText.setText(itemnumtext);
                        onClick(view);
                    } else {
                        Toast.makeText(OrderItemActivity.this,"item not found", Toast.LENGTH_SHORT).show();
                    }




                    grosstext1.setText("");
                    grosstext2.setText("");
                    grosstext3.setText("");
                    grosstext4.setText("");
                    grosstext5.setText("");
                    grosstext6.setText("");
                    grosstext7.setText("");
                    grosstext8.setText("");
                    grosstext9.setText("");
                    grosstext10.setText("");



                }

            }
        });


        resetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.clear();
            }
        });


        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderItemActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        intent = new Intent(this, MainActivity.class);


        Button gomain = (Button)findViewById(R.id.goMain);

        gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });



    }



    public void calculate(ProductItem item, int[] grossReq) {
        mList.add(item);
        int[] netreq = new int[10];
        int[] scheduled = new int[10];
        int[] amofhand = new int[10];
        int[] plannedorder = new int[10];
        int[] childgrossReq = new int[10];

        amofhand[0] = item.getAmountOnHand();
        if(item.getArrivalOnWeek()!=0)
            scheduled[item.getArrivalOnWeek()-1]=item.getScheduledReceipt();

        for(int j=1;j<10;j++){



            if(amofhand[j-1]+scheduled[j-1] >= grossReq[j-1]) {
                amofhand[j] = amofhand[j - 1]+scheduled[j-1] - grossReq[j - 1];
            }else if(amofhand[j-1]+scheduled[j-1] < grossReq[j-1]) {
                netreq[j-1] = grossReq[j - 1] - amofhand[j - 1]-scheduled[j-1] ;
                plannedorder[j-1] = (int)(Math.ceil((float)netreq[j-1] / (float)item.getLotSizingRule())*item.getLotSizingRule());
                amofhand[j] = amofhand[j]+ plannedorder[j-1] + scheduled[j-1] +amofhand[j-1]- grossReq[j - 1];
            }


        }
        int size = item.getChilds().size();
        if(size !=0){
            for(int k = 0; k<item.getChilds().size();k++){
                for(int i=0;i<plannedorder.length-1;i++){
                    childgrossReq[i]=plannedorder[i+1]*db.getItem(item.getChilds(k)).getItemRequired();
                }
                calculate(db.getItem(item.getChilds(k)),childgrossReq);

                String TAG = "OrderItemActivity";

                Log.i(TAG, "get number " + item.getChilds(k));
            }
        }

        item.setTable(item.printTable(item,grossReq,amofhand,netreq,plannedorder));
        item.setAmountOnHand(amofhand[9]);
        db.updateAmount(item);




    }




}
