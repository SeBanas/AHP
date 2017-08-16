package com.unnamed.b.atv.sample.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Seba on 2017-04-06.
 */

public class DialogName extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Button yes;
    public EditText editText;
    MyTreeNode node;
    AndroidTreeView view;

    public DialogName(Activity a, MyTreeNode node, AndroidTreeView view) {
        super(a);
        this.node=node;
        this.view=view;
        this.c=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_signin);
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);
        editText=(EditText) findViewById(R.id.editText);
    }

    @Override
     public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                String name=editText.getText().toString();
                if(name.matches("")) Toast.makeText(c.getBaseContext(),"Criteria must have a name",Toast.LENGTH_LONG).show();
            else if(((MyTreeNode) node).criteria.names.contains(name))Toast.makeText(getContext(),"Subcriterium with that name already exists",Toast.LENGTH_LONG).show();
            else {
                    Criteria nCriteria = new Criteria(name);
                    IconTreeItemHolder.IconTreeItem item = new IconTreeItemHolder.IconTreeItem(R.string.ic_info, name);
                    MyTreeNode newFolder = new MyTreeNode(item, nCriteria);
                    view.addNode(node, newFolder);
                    ((MyTreeNode) node).criteria.addSubcriterium(nCriteria);
                    dismiss();
                }
        }
    }
}