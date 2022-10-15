package com.example.tipcalculator;

import java.util.ArrayList;

public class TipsList {


    public static int id=0;
    private double tipName;
    private  double total ;
    private  double totalPerPerson;
    private int splitNo;
    private int tip;



    public TipsList(int tip,double total , double totalPerPerson,int splitNo ) {
        this.tip = tip;
        this.total = total;
        this.totalPerPerson = totalPerPerson;
        this.splitNo = splitNo;

    }



    public int getTip(){
        return tip;
    }

    public  double getTipName() {
        return tipName;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalPerPerson() {
        return totalPerPerson;
    }

    public int getSplitNo(){
        return splitNo;
    }

//    public ArrayList<TipsList> createTips(int noOfTips){
//
//        for(int i=0;i<=noOfTips;i++){
//
//
//            tips.add(new TipsList( ++id));
//        }
//
//        return tips;
//
//    }






}
