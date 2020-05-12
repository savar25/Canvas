package com.example.canvas;

import android.app.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

public class CustomView extends View {
    private Paint paint,opaint;
    private Canvas canvas;
    private Path path,npath;
    public RectF rect;



    public CustomView(Context context) {
        super(context);
        setupPaint();

        DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

       rect= new RectF((float)(screenWidth*0.11),screenHeight/10,(float)(screenWidth*0.9),(float)(screenHeight*0.9));
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        this.canvas.clipRect(rect);

        ArrayList<ArrayList<Point>> net_grid=new ArrayList<>();

       int screenWidth=getWidth();
       int screenHeight=getHeight();
        Context context = this.getContext();
        for(int i=0;i<17;i++){
            ArrayList<Point> points=new ArrayList<>();
            for(int j=0;j<10;j++){

                points.add(new Point((int)(screenWidth*0.15)+j*(screenWidth/10),(int)(screenHeight*0.14)+(i*screenHeight/20)));
            }
            net_grid.add(points);

        }

        for(int i=0;i<17;i++) {
            for (int j = 0; j < 10; j++) {
                this.canvas.drawCircle(net_grid.get(i).get(j).x, net_grid.get(i).get(j).y, 5, paint);
            }
        }

        this.canvas.drawRect(rect,opaint);
    }

    private void setupPaint(){

        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.Brown));
        paint.setAntiAlias(false);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);

        opaint=new Paint();
        opaint.setColor(Color.RED);
        opaint.setAntiAlias(false);
        opaint.setStrokeWidth(5);
        opaint.setStyle(Paint.Style.STROKE);
        opaint.setStrokeJoin(Paint.Join.MITER);
        opaint.setStrokeCap(Paint.Cap.BUTT);
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }



}
