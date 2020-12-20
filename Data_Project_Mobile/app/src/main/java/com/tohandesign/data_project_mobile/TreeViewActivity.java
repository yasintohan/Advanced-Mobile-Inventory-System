package com.tohandesign.data_project_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;


import java.util.List;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

public class TreeViewActivity extends AppCompatActivity {


    public DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view);

        db = new DatabaseHelper(getApplicationContext());
        List<ProductItem> items = db.getAllItems();
        List<ProductItem> mainitems = db.getAllItems();
        mainitems.clear();
        for(ProductItem item : items) {
            if(db.getMain(Long.parseLong(item.getItemId())) < 0){

                mainitems.add(item);
            }
        }




        TreeView treeView = (TreeView) findViewById(R.id.treeView);
        BaseTreeAdapter adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.node) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {

                if(data != "SYSTEM") {
                viewHolder.mTextView.setText(data.toString());
                viewHolder.reqTextView.setText(db.getItem(Long.parseLong(data.toString())).getItemRequired()+ " Required"); }
                else {
                    viewHolder.mTextView.setText(data.toString());
                }
            }
        };
        treeView.setAdapter(adapter);

        TreeNode rootnode = new TreeNode("SYSTEM");

        if(mainitems.size()>0) {
            for(ProductItem item : mainitems) {
                rootnode.addChild(getNode(item, db));
            }
        }
            adapter.setRootNode(rootnode);





    }



    //Getting root node from item list
    private TreeNode getNode(ProductItem item, DatabaseHelper db) {
        TreeNode node = new TreeNode(item.getItemId());
        if(item.getChilds().size() > 0) {
            for(Long lg: item.getChilds()){
                TreeNode childnode = getNode(db.getItem(lg), db);
                node.addChild(childnode);
            }
        }


        return node;
    }
    //#Getting root node from item list


}
