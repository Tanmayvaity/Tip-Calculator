package com.example.tipcalculator;

import java.util.ArrayList;

public class TipsList {


    public static int id=0;
    private int tipName;
    private  int total ;
    private  int totalPerPerson;



    public TipsList(int total , int totalPerPerson ) {
        this.tipName = ++id;
        this.total = total;
        this.totalPerPerson = totalPerPerson;

    }




    public  int getTipName() {
        return tipName;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPerPerson() {
        return totalPerPerson;
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
