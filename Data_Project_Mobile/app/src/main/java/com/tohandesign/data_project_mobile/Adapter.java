package com.tohandesign.data_project_mobile;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context mContext;
    ArrayList<ProductItem> mProductList;
    LayoutInflater inflater;

    public Adapter(Context context, ArrayList<ProductItem> products) {
        mContext = context;
        this.mProductList = products;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProductItem selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);




    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TableView tableView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tableView = (TableView) itemView.findViewById(R.id.tableView);


        }

        public void setData(ProductItem selectedProduct, int position) {
            this.tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(mContext,selectedProduct.getHeaderData()));
            this.tableView.setDataAdapter(new SimpleTableDataAdapter(mContext, selectedProduct.getTable()));

        }


        @Override
        public void onClick(View v) {


        }


    }

}
