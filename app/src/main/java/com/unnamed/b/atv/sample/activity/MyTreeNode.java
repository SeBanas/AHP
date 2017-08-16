package com.unnamed.b.atv.sample.activity;

import com.unnamed.b.atv.model.TreeNode;



public class MyTreeNode extends TreeNode{
    public Criteria criteria;
    public MyTreeNode(Object value,Criteria criteria) {
        super(value);
        this.criteria=criteria;
    }

    public void setCriteria(Criteria c){
        this.criteria=c;
    }

}
