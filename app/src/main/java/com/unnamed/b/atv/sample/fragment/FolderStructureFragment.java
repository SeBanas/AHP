package com.unnamed.b.atv.sample.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.activity.Criteria;
import com.unnamed.b.atv.sample.activity.DialogSaver;
import com.unnamed.b.atv.sample.activity.MyTreeNode;
import com.unnamed.b.atv.sample.activity.XMLSaver;
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;


public class FolderStructureFragment extends Fragment {
    private TextView statusBar;
    private AndroidTreeView tView;
    private MyTreeNode computerRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_default, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        statusBar = (TextView) rootView.findViewById(R.id.status_bar);
        TreeNode root = TreeNode.root();
        computerRoot = new MyTreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_star, "Goal"),new Criteria("Goal"));
        root.addChildren(computerRoot);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(nodeLongClickListener);

        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expandAll:
                tView.expandAll();
                break;
            case R.id.collapseAll:
                tView.collapseAll();
                break;
            case R.id.print:
                DialogSaver dialog=new DialogSaver(getActivity(),this);
                dialog.show();
        }
        return true;
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            statusBar.setText("Last clicked: " + item.text);
        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            Toast.makeText(getActivity(), "Long click: " + item.text, Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());

    }

    public void saveXML(String name){
        if(XMLSaver.isExternalStorageWritable())
        try{
        XMLSaver saver=new XMLSaver(computerRoot.criteria,name,getActivity().getBaseContext().getExternalFilesDir(null));
        saver.saveToFile();}
        catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),"Error occured during saving",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(),"No external memory found",Toast.LENGTH_LONG).show();
        }
    }

}
