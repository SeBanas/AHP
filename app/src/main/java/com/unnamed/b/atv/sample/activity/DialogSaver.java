package com.unnamed.b.atv.sample.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.fragment.FolderStructureFragment;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Seba on 2017-04-17.
 */

public class DialogSaver extends Dialog{
    Activity a;
    Button yes;
    EditText editText;
    FolderStructureFragment fragment;

    public DialogSaver(Activity a,FolderStructureFragment fragment) {
        super(a);
        this.a=a;
        this.fragment=fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_signin);
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText.getText().toString();
                if(name.matches("")){}
                else {
                    fragment.saveXML(name);
                    dismiss();
                }
            }
        });
        editText=(EditText) findViewById(R.id.editText);
    }

}
