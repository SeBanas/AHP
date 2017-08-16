package com.unnamed.b.atv.sample.activity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.ActionBarActivity;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.fragment.FolderStructureFragment;

public class MainActivity extends ActionBarActivity {

    Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        if(savedInstanceState!=null)
            mContent=getFragmentManager().getFragment(savedInstanceState,"myFragmentName");
        else
            mContent= Fragment.instantiate(this, FolderStructureFragment.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.fragment, mContent).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "myFragmentName", mContent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}