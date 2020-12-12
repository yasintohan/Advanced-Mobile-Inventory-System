package com.tohandesign.data_project_mobile;

import java.util.List;

public class ProductItem {



    private int id;
    private String itemId;
    private int amountOnHand;
    private int scheduledReceipt;
    private int arrivalOnWeek;
    private int leadTime;
    private int lotSizingRule;
    private int itemRequired = 1;
    private List<Long> childs = null;
    private String[][] table = new String[7][12];
    private static final String[] HeaderData =  { "","Period","1","2","3","4","5","6","7","8","9","10" };

    public ProductItem() {
    }

    public ProductItem(String itemId, int amountOnHand, int scheduledReceipt, int arrivalOnWeek, int leadTime, int lotSizingRule, int itemRequired, List<Long> childs) {
        this.itemId = itemId;
        this.amountOnHand = amountOnHand;
        this.scheduledReceipt = scheduledReceipt;
        this.arrivalOnWeek = arrivalOnWeek;
        this.leadTime = leadTime;
        this.lotSizingRule = lotSizingRule;
        this.itemRequired = itemRequired;
        this.childs = childs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getAmountOnHand() {
        return amountOnHand;
    }

    public void setAmountOnHand(int amountOnHand) {
        this.amountOnHand = amountOnHand;
    }

    public int getScheduledReceipt() {
        return scheduledReceipt;
    }

    public void setScheduledReceipt(int scheduledReceipt) {
        this.scheduledReceipt = scheduledReceipt;
    }

    public int getArrivalOnWeek() {
        return arrivalOnWeek;
    }

    public void setArrivalOnWeek(int arrivalOnWeek) {
        this.arrivalOnWeek = arrivalOnWeek;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(int leadTime) {
        this.leadTime = leadTime;
    }

    public int getLotSizingRule() {
        return lotSizingRule;
    }

    public void setLotSizingRule(int lotSizingRule) {
        this.lotSizingRule = lotSizingRule;
    }


    public List<Long> getChilds() {
        return childs;
    }

    public long getChilds(int i) { return childs.get(i); }

    public void setChilds(List<Long> childs) {
        this.childs = childs;
    }

    public int getItemRequired() {
        return itemRequired;
    }

    public void setItemRequired(int itemRequired) {
        this.itemRequired = itemRequired;
    }


    public String[][] getTable() {
        return table;
    }

    public void setTable(String[][] table) {
        this.table = table;
    }

    public String[][] printTable(ProductItem item, int[] grossreq, int[] amofhand, int[] netreq, int[] plannedorder) {

        String[][] table = {
                {item.getItemId(),              "Gross Requirements",   "","","","","","","","","",""},
                {"",                    "Scheduled Receipts",                        "","","","","","","","","",""},
                {"LT =" + item.getLeadTime(), "On hand From prior period",           "","","","","","","","","",""},
                {"",                    "Net Requirements",                          "","","","","","","","","",""},
                {"Q =","Time-phased Net Requirements",      "","","","","","","","","",""},
                {"",                    "Planned Order releases",                    "","","","","","","","","",""},
                {"",                    "Planned Order delivery",                    "","","","","","","","","",""},
        };



        if(item.getLotSizingRule()==1)
            table[4][0] = "Q = L4L";
        else
            table[4][0] = "Q =" + item.getLotSizingRule();

        if(arrivalOnWeek!=0)
            table[1][item.arrivalOnWeek+1] = item.scheduledReceipt + "";


        for(int i = 0; i<grossreq.length; i++) {
            table[0][i+2] = String.valueOf(grossreq[i]);
            table[2][i+2] = String.valueOf(amofhand[i]);
            table[3][i+2] = String.valueOf(netreq[i]);
            if(i+2-item.getLeadTime() > 1) {
                table[4][i + 2 - item.getLeadTime()] = String.valueOf(netreq[i]);
                table[5][i + 2 - item.getLeadTime()] = String.valueOf(plannedorder[i]);
            }
            table[6][i+2] = String.valueOf(plannedorder[i]);


        }
        for(int j = 0;j<item.getLeadTime();j++){
            table[4][table[0].length-1-j] = "0";
            table[5][table[0].length-1-j] = "0";

        }


        return table;

    }

    public static String[] getHeaderData() {
        return HeaderData;
    }
}
