package com.tohandesign.data_project_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        TreeView treeView = (TreeView) findViewById(R.id.treeView);
        BaseTreeAdapter adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.node) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.mTextView.setText(data.toString());
                viewHolder.reqTextView.setText(db.getItem(Long.parseLong(data.toString())).getItemRequired()+ " Required");
            }
        };
        treeView.setAdapter(adapter);

        if(items.size()>0)
            adapter.setRootNode(getNode(items.get(0), db));





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
