package com.tohandesign.data_project_mobile;

public class Child {

    private int id;
    private int child_id;



    public Child(int id, int child_id) {
        this.id = id;
        this.child_id = child_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }
}
