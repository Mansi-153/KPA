package com.example.kamdhenupashuahar.util;

import android.content.Context;

import java.io.Serializable;

public class BundleClass implements Serializable {
    Context context;
    String[][] strings;
    String string;
    public BundleClass(Context context){
        this.context = context;
    }
    public void setRes(String[][] strings){
        this.strings= strings;
    }public String[][] getRes(){
        return strings;
    }public void setName(String string){
        this.string= string;
    }public String getName(){
        return string;
    }
}
