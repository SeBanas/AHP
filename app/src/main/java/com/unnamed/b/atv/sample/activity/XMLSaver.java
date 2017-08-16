package com.unnamed.b.atv.sample.activity;



import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map.Entry;

public class XMLSaver {
    private static final String BREL_TAG="<Relation Name=\"";
    private static final String EREL_TAG="</Relation>";
    private static final String BCRI_TAG="<Subcriteria Name=\"";
    private static final String ECRI_TAG="</Subcriteria>";
    private Criteria goal;
    private String name;
    private File dir;
    private int level=0;

    public XMLSaver(Criteria c, String name,File path){
        goal=c;
        this.name=name;
        this.dir=path;
    }

    public void saveToFile() throws IOException{
        File myFile = new File(dir,name+".txt");
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter =
                new OutputStreamWriter(fOut);
        myOutWriter.append(getHierarchy(goal));
        myOutWriter.close();
        fOut.close();
    }

    private String getRelations(Criteria c){
        if(c.brothers.size()==0) return "";
        StringBuilder builder=new StringBuilder();
        Iterator<Entry<Criteria, Double>> it=c.brothers.entrySet().iterator();
        Entry<Criteria, Double> entry;
        while(it.hasNext()){
            entry=it.next();
            for(int i=0;i<level;i++)
                builder.append("    ");
            builder.append(BREL_TAG+entry.getKey().getName()+"\">"+entry.getValue()+EREL_TAG+"\n");
        }
        return builder.toString();
    }

    private String getHierarchy(Criteria c){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<level;i++)
            builder.append("    ");
        builder.append(BCRI_TAG+c.getName()+">\n");
        level++;
        builder.append(getRelations(c));
        Iterator<Criteria> it=c.subcriteria.iterator();
        while(it.hasNext())
            builder.append(getHierarchy(it.next()));
        level--;
        for(int i=0;i<level;i++)
            builder.append("    ");
        builder.append(ECRI_TAG+"\n");
        return builder.toString();
    }

   static public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


}
