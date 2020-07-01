package com.example.kamdhenupashuahar.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SessionActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPreferences;

    public SessionActivity(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("PriceList", Context.MODE_PRIVATE);
    }

    public void setRespose(int b,int c,int a,int m,int k,int ak,int sk) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Bprice", b);
        editor.putInt("Cprice", c);
        editor.putInt("Aprice", a);
        editor.putInt("Mprice", m);
        editor.putInt("Kprice", k);
        editor.putInt("Akprice", ak);
        editor.putInt("Sprice", sk);
        editor.apply();
        Log.d("nlnnnnnnnnn",String.valueOf(sharedPreferences.getInt("Bprice", -1)));
    }

    public int getBPrice() {
        return sharedPreferences.getInt("Bprice", -1);
    }public int getCPrice() {
        return sharedPreferences.getInt("Cprice", -1);
    }public int getAPrice() {
        return sharedPreferences.getInt("Aprice", -1);
    }public int getMPrice() {
        return sharedPreferences.getInt("Mprice", -1);
    }public int getKPrice() {
        return sharedPreferences.getInt("Kprice", -1);
    }public int getAkPrice() {
        return sharedPreferences.getInt("Akprice", -1);
    }public int getSPrice() {
        return sharedPreferences.getInt("Sprice", -1);
    }

}
