package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static TextView PlayerShow;
    public static ImageView color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayerShow=(TextView)findViewById(R.id.Player);
        color=(ImageView)findViewById(R.id.color);
        PlayerShow.setText("Player 1");
        color.setBackgroundColor(Color.GREEN);

    }

    static public void setPlayerShow(String playerShow,int choice) {
        PlayerShow.setText(playerShow);
        if(choice==1){
            PlayerShow.setTextColor(Color.GREEN);
        }else{
            PlayerShow.setTextColor(Color.YELLOW);
        }
    }

    static public void setColor(int color) {
        if(color==1){
        MainActivity.color.setBackgroundColor(Color.GREEN);}
        else {
            MainActivity.color.setBackgroundColor(Color.YELLOW);}
        }
    }

