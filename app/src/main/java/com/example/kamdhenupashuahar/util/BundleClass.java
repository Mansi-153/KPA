package com.example.kamdhenupashuahar.util;

import android.content.Context;

import java.io.Serializable;

public class BundleClass implements Serializable {
    private static BundleClass mInstance;
    String[][] strings;
    String string;
    public static synchronized BundleClass getInstance(){
        if(mInstance==null){
            mInstance= new BundleClass();
        }
        return mInstance;
    }
    private BundleClass(){
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
