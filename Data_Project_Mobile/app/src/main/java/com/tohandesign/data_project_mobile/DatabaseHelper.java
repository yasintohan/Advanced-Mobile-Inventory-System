package com.tohandesign.data_project_mobile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "invManager";

    // Table Names
    private static final String TABLE_ITEM = "item";
    private static final String TABLE_CHILDS = "childs";

    // Common column names
    private static final String KEY_ID = "id";


    // Item Table - column nmaes
    private static final String KEY_ITEM_ID = "itemId";
    private static final String KEY_AON_HAND = "amountOnHand";
    private static final String KEY_SCH_RCP = "scheduledReceipt";
    private static final String KEY_ARON_WEEK = "arrivalOnWeek";
    private static final String KEY_LEAD_TIME = "leadTime";
    private static final String KEY_LS_RULE = "lotSizingRule";
    private static final String KEY_REQUIRED = "itemRequired";

    // Childs Table - column names
    private static final String KEY_MAIN_ITEM_ID = "itemId";
    private static final String KEY_CHILD_ID = "childItemId";

    // Table Create Statements
    // Item table create statement
    private static final String CREATE_TABLE_ITEM = "CREATE TABLE " + TABLE_ITEM +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_ITEM_ID + " TEXT," +
            KEY_AON_HAND + " INTEGER," +
            KEY_SCH_RCP + " INTEGER," +
            KEY_ARON_WEEK + " INTEGER," +
            KEY_LEAD_TIME + " INTEGER," +
            KEY_LS_RULE + " INTEGER," +
            KEY_REQUIRED + " INTEGER" +
            ")";

    // Childs table create statement
    private static final String CREATE_TABLE_CHILDS = "CREATE TABLE " + TABLE_CHILDS +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_MAIN_ITEM_ID + " INTEGER," +
            KEY_CHILD_ID + " INTEGER" +
            ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_CHILDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILDS);

        // create new tables
        onCreate(db);
    }


    //Creating a item without main
    public long createItem(ProductItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, item.getItemId());
        values.put(KEY_AON_HAND, item.getAmountOnHand());
        values.put(KEY_SCH_RCP, item.getScheduledReceipt());
        values.put(KEY_ARON_WEEK, item.getArrivalOnWeek());
        values.put(KEY_LEAD_TIME, item.getLeadTime());
        values.put(KEY_LS_RULE, item.getLotSizingRule());
        values.put(KEY_REQUIRED, item.getItemRequired());


        // insert row
        long item_id = db.insert(TABLE_ITEM, null, values);

        return item_id;

    }
    //#Creating a item without main



    //Creating a item with main
    public long createItem(ProductItem item, long main_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, item.getItemId());
        values.put(KEY_AON_HAND, item.getAmountOnHand());
        values.put(KEY_SCH_RCP, item.getScheduledReceipt());
        values.put(KEY_ARON_WEEK, item.getArrivalOnWeek());
        values.put(KEY_LEAD_TIME, item.getLeadTime());
        values.put(KEY_LS_RULE, item.getLotSizingRule());
        values.put(KEY_REQUIRED, item.getItemRequired());


        // insert row
        long item_id = db.insert(TABLE_ITEM, null, values);

        // insert main_id
        createChild(main_id, Long.parseLong(item.getItemId()));

        return item_id;

    }
    //#Creating a item with main

    //Creating a child
    public long createChild(long main_id, long item_id) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_MAIN_ITEM_ID, main_id);
        values.put(KEY_CHILD_ID, item_id);

        // insert row
        long item_child_id = db.insert(TABLE_CHILDS, null, values);

        return item_child_id;
    }
    //#Creating a child




    //get single item
    public ProductItem getItem(long item_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM + " WHERE "
                + KEY_ITEM_ID + " = " + item_id;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ProductItem item = new ProductItem();
        item.setItemId(c.getString(c.getColumnIndex(KEY_ITEM_ID)));
        item.setAmountOnHand(c.getInt(c.getColumnIndex(KEY_AON_HAND)));
        item.setScheduledReceipt(c.getInt(c.getColumnIndex(KEY_SCH_RCP)));
        item.setArrivalOnWeek(c.getInt(c.getColumnIndex(KEY_ARON_WEEK)));
        item.setLeadTime(c.getInt(c.getColumnIndex(KEY_LEAD_TIME)));
        item.setLotSizingRule(c.getInt(c.getColumnIndex(KEY_LS_RULE)));
        item.setItemRequired(c.getInt(c.getColumnIndex(KEY_REQUIRED)));

        item.setChilds(getChilds(Long.parseLong(item.getItemId())));




        return item;
    }
    //#get single item


    //get childs
    public List<Long> getChilds(long item_id) {
        List<Long> childs = new ArrayList<Long>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CHILDS + " WHERE "
                + KEY_MAIN_ITEM_ID + " = " + item_id;


        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                long td = c.getInt((c.getColumnIndex(KEY_CHILD_ID)));

                // adding to item list
                childs.add(td);
            } while (c.moveToNext());
        }

        return childs;
    }
    //#get childs


    //getting all items
    public List<ProductItem> getAllItems() {
        List<ProductItem> items = new ArrayList<ProductItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ProductItem td = getItem(c.getInt((c.getColumnIndex(KEY_ITEM_ID))));
                // adding to item list
                items.add(td);
            } while (c.moveToNext());
        }

        return items;
    }
    //#getting all items


    //getting item count
    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ITEM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    //#getting item count


    //Updating a amount on hand
    public int updateAmount(ProductItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AON_HAND, item.getAmountOnHand());

        // updating row
        return db.update(TABLE_ITEM, values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(item.getItemId()) });
    }
    //#Updating a amount on hand


    //Updating Item
    public int updateItem(ProductItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AON_HAND, item.getAmountOnHand());
        values.put(KEY_SCH_RCP, item.getScheduledReceipt());
        values.put(KEY_ARON_WEEK, item.getArrivalOnWeek());
        values.put(KEY_LEAD_TIME, item.getLeadTime());
        values.put(KEY_LS_RULE, item.getLotSizingRule());
        values.put(KEY_REQUIRED, item.getItemRequired());

        // updating row
        return db.update(TABLE_ITEM, values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(item.getItemId()) });
    }
    //#Updating Item


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    // closing database
    public void clearDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ TABLE_ITEM);
        db.execSQL("delete from "+ TABLE_CHILDS);
    }



}