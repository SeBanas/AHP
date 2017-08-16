package com.unnamed.b.atv.sample.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.sample.R;


public class TextViewAdapter extends BaseAdapter {
    private Context context;
    private Criteria[] criterias;
    private final String[] textViewValues;

    public TextViewAdapter(Context context, String[] textViewValues,Criteria[] criterias) {
        this.context = context;
        this.textViewValues = textViewValues;
        this.criterias=criterias;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        final int columns=((GridView)parent).getNumColumns();


        if (convertView == null) {

            gridView = new View(context);
            if(position==0) {
                gridView = inflater.inflate(R.layout.grid_item, null);
                TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
                textView.setLayoutParams(new GridView.LayoutParams(90, 90));
                textView.setVisibility(View.INVISIBLE);
                }
            else if(position<columns || position%columns==0 || position%columns==position/columns){
                gridView = inflater.inflate(R.layout.grid_item, null);
                TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
                textView.setLayoutParams(new GridView.LayoutParams(90, 90));
                textView.setText(textViewValues[position]);}
            else{
                gridView = inflater.inflate(R.layout.grid_item_editable, null);
                EditText editText=(EditText) gridView.findViewById(R.id.grid_edit);
                editText.setLayoutParams(new GridView.LayoutParams(90,90));
                editText.setText(textViewValues[position]);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(!b) {
                            try{
                            double tmp = Double.parseDouble(((EditText) view).getText().toString());
                                int col = position % columns;
                                int row = position / columns;
                                if(tmp<0){
                                    Toast.makeText(context.getApplicationContext(),"Wrong proportion",Toast.LENGTH_LONG).show();
                                    tmp=(double) 1;
                                    ((EditText) view).setText("1.0");
                                }


                            criterias[row - 1].setProportion(tmp, criterias[col - 1]);
                            int pos=0;
                            if(col>row){
                                 pos=col*columns+row;
                            }
                            else{
                                pos=col*columns+row;
                            }
                            EditText ed = (EditText) parent.getChildAt(pos);
                            ed.setText("" + (1 / tmp));
                            ((ConfigureActivity)context).checkPreference();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                });


            }
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return textViewValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
