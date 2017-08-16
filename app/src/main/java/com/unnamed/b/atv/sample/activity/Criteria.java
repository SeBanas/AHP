package com.unnamed.b.atv.sample.activity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


//import org.apache.commons.math3.linear.Array2DRowRealMatrix;
//import org.apache.commons.math3.linear.EigenDecomposition;

public class Criteria implements Comparable<Criteria>,Serializable{
    SortedSet<Criteria> subcriteria=new TreeSet<Criteria>();
    SortedMap<Criteria,Double> brothers=new TreeMap<Criteria,Double>();
    SortedSet<String> names=new TreeSet<String>() ;
    private Criteria parent;
    private double consistency=0;
    private String name;
    private double preferableConsistency=10;

    public Criteria(String name){
        this.name=name;
    }

    @Override
    public int compareTo(Criteria o) {
       if(name==null || o==null || o.name==null) return 1;
        return name.compareTo(o.name);
    }

    public void setProportion(double prop,Criteria target){
        brothers.put(target, prop);
        target.brothers.put(this, 1/prop);
        parent.updatePerformance();
    }

    public Criteria getParent(){
        return parent;
    }

    public void diminish(){
        Iterator<Criteria> tmp=subcriteria.iterator();
        if(parent!=null) {
            parent.subcriteria.remove(this);
            parent.names.remove(this.getName());
        }

           while (tmp.hasNext()) {
               tmp.next().diminish();
           }
        subcriteria.clear();
        tmp=brothers.keySet().iterator();
        while(tmp.hasNext())
            tmp.next().brothers.remove(this);
        brothers.clear();
        if(parent!=null)
         parent.updatePerformance();
    }

    public String[] getVector() {
        Iterator it = brothers.values().iterator();
        String[] result = new String[brothers.values().size()];
        int i = 0;
        while (it.hasNext()){
            result[i] = it.next().toString();
            i++;
        }
        return result;
    }


    public void addSubcriterium(Criteria c){
        if(!names.contains(c.getName())){
            subcriteria.add(c);
            c.setParent(this);
            Iterator<Criteria> it=subcriteria.iterator();
            Criteria tmp;
            while(it.hasNext()){
                tmp=it.next();
                if(tmp==c) continue;
                tmp.addBrother(c);
                c.addBrother(tmp);
            }
            names.add(c.getName());
            updatePerformance();
        }
        else System.out.println("Error");
    }

    public void setParent(Criteria c){
        parent=c;
    }

    public String getName(){
        return name;
    }

    public double getConsistency(){
        return consistency;
    }

    public String toString(){
        return name;
    }



    public void setPreferableConsistency(double preferableConsistency){
        this.preferableConsistency=preferableConsistency;
    }

    public double getPreferableConsistency() {
        return preferableConsistency;
    }

    public void updatePerformance(){
        double[][] matrix=makeMatrix();
        double[] vector=getColumn(matrix);
        double consistency=getHCI(vector);
        setConsistency(consistency);
    }

    private void addBrother(Criteria c){
        if(!brothers.containsKey(c))
            brothers.put(c, (double) 1);
        else System.out.println("ErrorB"+c.getName());
    }

    private double[] getColumn(double[][] matrix){
        int size=subcriteria.size();
        double[] vector=new double[size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                vector[i]+=matrix[j][i];
        }
        return vector;
    }

    private double getHCI(double[] tab){
        double n=tab.length;
        if(n==1) return 0;
        double bottom=0;
        for(int i=0;i<tab.length;i++)
            bottom+=1/tab[i];
        double HM=n/bottom;
        double HCI=((HM-n)*(n+1))/(n*(n-1));
        return HCI*100;
    }

    private void setConsistency(double consistency){
        this.consistency=consistency;
    }

    private double[] getVector(Criteria c){
        int size=c.brothers.size();
        Iterator<Double> it=c.brothers.values().iterator();
        double[] tmp=new double[size];
        for(int i=0;i<size;i++)
            tmp[i]=it.next().doubleValue();
        return tmp;
    }

    private double[][] makeMatrix(){
        int size=subcriteria.size();
        double[][] matrix=new double[size][size];
        Iterator<Criteria> it=subcriteria.iterator();
        double[] vector;
        for(int i=0;i<size;i++){
            vector=getVector(it.next());
            for(int j=0;j<i;j++)
                matrix[i][j]=vector[j];
            matrix[i][i]=1;
            for(int j=i+1;j<size;j++)
                matrix[i][j]=vector[j-1];
        }
       /* for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                System.out.print(matrix[i][j]+" ");
            System.out.println("");
        }*/
        return matrix;
    }


}
