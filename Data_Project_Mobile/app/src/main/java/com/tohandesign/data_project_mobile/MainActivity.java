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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());



        invintent = new Intent(this, InventoryActivity.class);
        orderintent = new Intent(this, OrderItemActivity.class);
        additemintent = new Intent(this, AddItemActivity.class);


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


        Button clearDB = (Button)findViewById(R.id.clearDB);

        clearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearDB();
                db.closeDB();
            }
        });





    }
/*
    public static void setItems(SharedPreferences sp, SharedPreferences.Editor editor){
        item314amount = sp.getInt("314", 0);
        item14127amount = sp.getInt("14127", 60);
        item019amount = sp.getInt("019", 50);
        item2142amount = sp.getInt("2142", 80);
        item129amount = sp.getInt("129", 0);
        item1118amount = sp.getInt("1118", 30);
        item11495amount = sp.getInt("11495", 120);
        item457amount = sp.getInt("457", 0);
        item062amount = sp.getInt("062", 50);
        item13122amount = sp.getInt("13122", 70);
        item048amount = sp.getInt("048", 30);
        item118amount = sp.getInt("118", 0);
        item1605amount = sp.getInt("1605", 30);



        item457 = new ProductItem("457", item457amount,20,2,2,1,1, new ProductItem[]{});
        item062r2 = new ProductItem("062", item062amount,100,6,2,1,2, new ProductItem[]{});
        item1118 = new ProductItem("1118", item1118amount,0,0,3,1,1, new ProductItem[]{});
        item129 = new ProductItem("129", item129amount,100,8,4,40,1, new ProductItem[]{});
        item11495 = new ProductItem("11495", item11495amount,0,0,1,50,1, new ProductItem[]{item129,item1118});
        item13122 = new ProductItem("13122", item13122amount,70,3,1,40,1, new ProductItem[]{item457,item062r2,item11495});
        item048 = new ProductItem("048", item048amount,0,0,3,30,1, new ProductItem[]{});
        item118 = new ProductItem("118", item118amount,50,2,2,1,1, new ProductItem[]{});
        item062r4 = new ProductItem("062", item062amount,100,6,2,1,4, new ProductItem[]{});
        item14127r4 = new ProductItem("14127", item14127amount,0,0,2,50,4, new ProductItem[]{});
        item2142 = new ProductItem("2142", item2142amount,0,0,2,100,1, new ProductItem[]{});
        item019 = new ProductItem("019", item019amount,40,5,2,50,1, new ProductItem[]{});
        item14127r6 = new ProductItem("14127", item14127amount,0,0,2,50,6, new ProductItem[]{});
        item314 = new ProductItem("314", item314amount,50,5,1,50,1, new ProductItem[]{item2142, item019, item14127r6});
        item1605 = new ProductItem("1605", item1605amount,0,0,1,1,1, new ProductItem[]{item13122,item048,item118,item062r4,item14127r4,item314});


    }


    public static void saveItems(ProductItem item, SharedPreferences sp, SharedPreferences.Editor editor){

        editor.putInt(item.getItemId(), item.getAmountOnHand());
        editor.commit();

        if(item.getChilds().length !=0) {
            for(int i = 0; i<item.getChilds().length;i++){
                saveItems(item.getChilds(i ),sp,editor);
            }
        }

    }
*/










}
