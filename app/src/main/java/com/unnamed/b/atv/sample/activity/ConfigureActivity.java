package com.unnamed.b.atv.sample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

public class ConfigureActivity extends ActionBarActivity {
    private String[] textValues;
    private Criteria[] criterias;
    Criteria c= IconTreeItemHolder.myTreeNode.criteria;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        textView=(TextView) findViewById(R.id.textView2);
        TextView textView2=(TextView) findViewById(R.id.textView12);
        EditText editText2=(EditText) findViewById(R.id.editText17);
        button=(Button)findViewById(R.id.button8);
        GridView gridView=(GridView) findViewById(R.id.grid_view);





        gridView.setNumColumns(c.subcriteria.size()+1);
        if(savedInstanceState!=null) {
            textValues = savedInstanceState.getStringArray("Content");
            criterias=(Criteria[]) savedInstanceState.getSerializable("Criteria");
            }
        else {
            textValues = getContent(c);
            criterias=new Criteria[c.subcriteria.size()];
            criterias=c.subcriteria.toArray(criterias);

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              for(Criteria tmp: c.subcriteria)
                for(Criteria tmp2:tmp.brothers.keySet()) {
                    tmp.setProportion(1.0, tmp2);
                }
                recreate();
                //onBackPressed();
            }
        });

        editText2.setText(""+c.getPreferableConsistency());
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    try{
                        double tmp = Double.parseDouble(((EditText) view).getText().toString());
                        if(tmp>100 || tmp<0){
                            Toast.makeText(getApplicationContext(),"Only 0-100 unstability range allowed",Toast.LENGTH_LONG).show();
                            ((EditText) view).setText("10");
                            c.setPreferableConsistency(10);
                        }
                        else{
                            c.setPreferableConsistency(tmp);
                            checkPreference();
                        }
                    }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(),"Error occured during parsing",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        gridView.setAdapter(new TextViewAdapter(this,textValues,criterias));
        checkPreference();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("Content",textValues);
        outState.putSerializable("Criteria",criterias);

    }

    private String[] getContent(Criteria c) {
        Iterator<Criteria> it = c.subcriteria.iterator();
        int size = c.subcriteria.size();
        String[] content = new String[(size + 1) * (size + 1)];
        String[] names = new String[size];
        String[][] vectors = new String[size][size];
        if (size > 0) content[0] = "";


        int i = 1;
        Criteria tmp;
        while (it.hasNext()) {
            tmp = it.next();
            content[i] = tmp.getName();
            names[i - 1] = tmp.getName();
            vectors[i - 1] = tmp.getVector();
            i++;
        }
        boolean flag = false;
        for (int z = 0; z < size; z++) {
            flag = false;
               for (int j = 0; j < size + 1; j++, i++)
                if (j == 0) content[i] = names[z];
                else if (z + 1 == j) {
                    content[i] = "1.0";
                    flag = true;
                } else if (flag == false) content[i] = vectors[z][j - 1];
                else content[i] = vectors[z][j-2];
        }
        return content;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean checkPreference(){
        double result=c.getConsistency();
        try{
        BigDecimal bd=BigDecimal.valueOf(result);
        textView.setText(""+ bd.setScale(2, RoundingMode.HALF_UP).doubleValue());
        if(IconTreeItemHolder.myTreeNode.criteria.getConsistency()>IconTreeItemHolder.myTreeNode.criteria.getPreferableConsistency()){
            textView.setTextColor(Color.RED);
            return false;
        }
        else{
            textView.setTextColor(Color.BLACK);
            return true;
        }}
        catch (Exception e){
            textView.setText("0");
            return false;
        }
    }

    public synchronized static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
