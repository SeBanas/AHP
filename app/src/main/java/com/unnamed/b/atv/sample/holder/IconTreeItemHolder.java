package com.unnamed.b.atv.sample.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.activity.ConfigureActivity;
import com.unnamed.b.atv.sample.activity.Criteria;
import com.unnamed.b.atv.sample.activity.DialogName;
import com.unnamed.b.atv.sample.activity.MainActivity;
import com.unnamed.b.atv.sample.activity.MyTreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;


import java.util.concurrent.ExecutionException;

public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private PrintView arrowView;
    public static MyTreeNode myTreeNode;

    Context contex;

    public IconTreeItemHolder(Context context) {
        super(context);
        contex=context;
    }


    @Override
    public View createNodeView(final TreeNode node, final IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);
        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));
        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        view.findViewById(R.id.btn_addFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogName dialog=new DialogName((Activity) context,(MyTreeNode) node,getTreeView());
                dialog.show();
            }
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyTreeNode)node).criteria.diminish();
                getTreeView().removeNode(node);
            }
        });

        view.findViewById(R.id.btn_configure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ConfigureActivity.class);
                IconTreeItemHolder.myTreeNode=(MyTreeNode)node;
                ((Activity)context).startActivityForResult(intent,1);

            }
        });

        if (node.getLevel() == 1) {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }
    }








}
