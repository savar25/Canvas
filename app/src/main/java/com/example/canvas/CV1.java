package com.example.canvas;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class CV1 extends View {
    public Paint paint, cpaint, markpaint, bpaint;
    public ArrayList<ArrayList<Point>> grid;
    public int col, row;
    private Bitmap bitmap;
    public Canvas canvas;
    public Path path;
    public Point initPoint, finalPoint;
    ArrayList<ArrayList<Point>>lineStore=new ArrayList<>();
    ArrayList<ArrayList<Point>> element =new ArrayList<>(),Lelement=new ArrayList<>(),LEelement=new ArrayList<>(),Relement=new ArrayList<>();
    ArrayList<Point> init=new ArrayList<>(),Linit=new ArrayList<>(),Rinit=new ArrayList<>(),LEinit=new ArrayList<>();
    private static final String TAG = "CV1";
    private static final String TAG1 = "Trial";
    private static final String TAG2 = "Box";
    public int s=0;


    public CV1(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
        grid = new ArrayList<>();

        path = new Path();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CV1, 0, 0);

        try {
            col = a.getInteger(R.styleable.CV1_columns, 0);
            row = a.getInteger(R.styleable.CV1_rows, 0);
        } finally {
            a.recycle();
        }


    }


    public void setupPaint() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);


        cpaint = new Paint(Paint.DITHER_FLAG);
        cpaint.setColor(getResources().getColor(R.color.Orange));
        cpaint.setAntiAlias(false);
        cpaint.setStrokeWidth(15);
        cpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cpaint.setStrokeJoin(Paint.Join.MITER);
        cpaint.setStrokeCap(Paint.Cap.BUTT);


        markpaint = new Paint(Paint.DITHER_FLAG);
        markpaint.setColor(Color.GREEN);
        markpaint.setAntiAlias(false);
        markpaint.setStrokeWidth(4);
        markpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        markpaint.setStrokeJoin(Paint.Join.MITER);
        markpaint.setStrokeCap(Paint.Cap.BUTT);

        bpaint = new Paint(Paint.DITHER_FLAG);
        bpaint.setColor(Color.RED);
        bpaint.setAntiAlias(false);
        bpaint.setStrokeWidth(15);
        bpaint.setStyle(Paint.Style.STROKE);
        bpaint.setStrokeJoin(Paint.Join.MITER);
        bpaint.setStrokeCap(Paint.Cap.BUTT);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

    }


    @Override
    public void onDraw(Canvas canvas) {

        int screenWidth = this.getMeasuredWidth();
        int screenHeight = this.getMeasuredHeight();
        //Bitmap Setup
        canvas.drawBitmap(bitmap, 0, 0, cpaint);


        for (int i = 0; i < row; i++) {
            ArrayList<Point> line = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                line.add(new Point(screenWidth / (col + 1) + j * (screenWidth / (col + 1)), screenHeight / (row + 1) + i * (screenHeight / (row + 1))));
            }
            grid.add(line);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.canvas.drawCircle(grid.get(i).get(j).x, grid.get(i).get(j).y, 5, paint);
            }
        }


        canvas.drawRect(0, 0, screenWidth, screenHeight, bpaint);
    }


    public Paint getMarkpaint() {
        return markpaint;
    }

    public void setMarkpaint(Paint markpaint) {
        this.markpaint = markpaint;
    }


    public Point returnPoint(Point p) {
        Point gp = new Point();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if ((p.x <= (grid.get(i).get(j).x + 50) && p.x >= (grid.get(i).get(j).x - 50)) && (p.y <= (grid.get(i).get(j).y + 50) && p.y >= (grid.get(i).get(j).y - 50))) {
                    gp = grid.get(i).get(j);

                }
            }


        }
        return gp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Point p = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
        Point zero = new Point(0, 0);
        Point p1=new Point();
        int c=0;
        int sw = getMeasuredWidth();
        int sh = getMeasuredHeight();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (returnPoint(p) != zero) {
                    initPoint = returnPoint(p);
                    path.moveTo(initPoint.x, initPoint.y);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (returnPoint(p) != zero) {
                    finalPoint = returnPoint(p);
                    p1 = finalPoint;
                    path.lineTo(finalPoint.x, finalPoint.y);
                    if ((initPoint != finalPoint) && (finalPoint.x == initPoint.x + sw / (col + 1)) || (finalPoint.x == initPoint.x - sw / (col + 1) || (finalPoint.y == initPoint.y - sh / (row + 1)) || (finalPoint.y == initPoint.y + sh / (row + 1)))
                            && (initPoint != zero) && (finalPoint != zero)) {
                        if (initPoint.x == finalPoint.x || initPoint.y == finalPoint.y) {
                            canvas.drawPath(path, markpaint);
                            path.reset();


                            ArrayList<Point> initiate = new ArrayList<>();
                            initiate.add(initPoint);
                            initiate.add(finalPoint);
                            lineStore.add(initiate);
                            Log.d(TAG, "addPoint: called");
                            Log.d(TAG, "onTouchEvent: "+lineStore.size());
                            Log.d(TAG, "onTouchEvent: "+lineStore.get(s).get(0).x+","+lineStore.get(s).get(0).y);
                            Log.d(TAG, "onTouchEvent: "+lineStore.get(s).get(1).x+","+lineStore.get(s).get(1).y);






                            boolean flag=false;
                            Log.d(TAG, "checkbox: called");
                            Log.d(TAG, "checkbox: "+lineStore.get(0).get(1).x+","+lineStore.get(0).get(1).y);
                            if(initPoint.x==finalPoint.x){
                                c=3;

                            }else if(initPoint.y==finalPoint.y){
                                c=1;
                            }

                            s+=1;


                            switch(c){
                                case 1:
                                    Log.d(TAG, "upperboxCheck: called");
                                    int dh = getMeasuredHeight() / (row + 1);
                                    Point UinitPlus = new Point(initPoint.x, initPoint.y - dh);
                                    Point UfinalPlus = new Point(finalPoint.x, finalPoint.y - dh);

                                    init.add(initPoint);
                                    init.add(finalPoint);
                                    element.add(init);
                                    Log.d(TAG, "onTouchEvent: "+element);
                                    init.clear();
                                    init.add(UinitPlus);
                                    init.add(initPoint);
                                    element.add(init);
                                    Log.d(TAG, "onTouchEvent: "+element);
                                    init.clear();
                                    init.add(UfinalPlus);
                                    init.add(finalPoint);
                                    element.add(init);
                                    Log.d(TAG, "onTouchEvent: "+element);
                                    init.clear();
                                    init.add(UinitPlus);
                                    init.add(UfinalPlus);
                                    element.add(init);
                                    Log.d(TAG, "onTouchEvent: "+element);
                                    Log.d(TAG, "onTouchEvent: upper"+ element);
                                    init.clear();
                                    if (checkPoint(element.get(0)) && checkPoint(element.get(1)) && checkPoint(element.get(2)) && checkPoint(element.get(3))) {
                                        canvas.drawRect(UinitPlus.x, UinitPlus.y, UfinalPlus.x, finalPoint.y, markpaint);
                                        Log.d(TAG2, "upperboxCheck: assigned");
                                    }

                                case 2:
                                    Log.d(TAG, "lowerBoxCheck: called");
                                    int Ldh = getMeasuredHeight() / (row + 1);
                                    Point LinitPlus = new Point(initPoint.x, initPoint.y + Ldh);
                                    Point LfinalPlus = new Point(finalPoint.x, finalPoint.y + Ldh);
                                    Linit.add(initPoint);
                                    Linit.add(finalPoint);
                                    Lelement.add(Linit);
                                    Linit.clear();
                                    Linit.add(LinitPlus);
                                    Linit.add(initPoint);
                                    Lelement.add(Linit);
                                    Linit.clear();
                                    Linit.add(LfinalPlus);
                                    Linit.add(finalPoint);
                                    Lelement.add(Linit);
                                    Linit.clear();
                                    Linit.add(LinitPlus);
                                    Linit.add(LfinalPlus);
                                    Lelement.add(Linit);
                                    Linit.clear();
                                    Log.d(TAG, "onTouchEvent: lower:"+ Lelement);
                                    if((checkPoint(Lelement.get(0)) && checkPoint(Lelement.get(1)) && checkPoint(Lelement.get(2)) && checkPoint(Lelement.get(3)))) {
                                        canvas.drawRect(LinitPlus.x, LinitPlus.y, LfinalPlus.x, finalPoint.y, markpaint);
                                        Log.d(TAG2, "lowerBoxCheck: assgigned");
                                    }
                                    break;
                                case 3:
                                    Log.d(TAG, "rightCheckBox: called");
                                    int Rdw = getMeasuredWidth() / (row + 1);
                                    Point RinitPlus = new Point(initPoint.x+Rdw, initPoint.y);
                                    Point RfinalPlus = new Point(finalPoint.x+Rdw, finalPoint.y );
                                    Rinit.add(initPoint);
                                    Rinit.add(finalPoint);
                                    Relement.add(Rinit);
                                    Rinit.clear();
                                    Rinit.add(RinitPlus);
                                    Rinit.add(initPoint);
                                    Relement.add(Rinit);
                                    Rinit.clear();
                                    Rinit.add(RfinalPlus);
                                    Rinit.add(finalPoint);
                                    Relement.add(Rinit);
                                    Rinit.clear();
                                    Rinit.add(RinitPlus);
                                    Rinit.add(RfinalPlus);
                                    Relement.add(Rinit);
                                    Rinit.clear();
                                    Log.d(TAG, "onTouchEvent: rightCheck"+Relement);
                                    if ((checkPoint(Relement.get(0)) && checkPoint(Relement.get(1)) && checkPoint(Relement.get(2)) && checkPoint(Relement.get(3)))) {
                                        canvas.drawRect(15, 32, 45, 65, markpaint);
                                        Log.d(TAG2, "rightCheckBox: assigned");
                                    }
                                case 4:
                                    chnnelH:
                                    Log.d(TAG, "leftCheckBox: called");
                                    int Ldw = getMeasuredWidth() / (row + 1);
                                    Point LEinitPlus = new Point(initPoint.x-Ldw, initPoint.y);
                                    Point LEfinalPlus = new Point(finalPoint.x-Ldw, finalPoint.y );
                                    LEinit.add(initPoint);
                                    LEinit.add(finalPoint);
                                    LEelement.add(LEinit);
                                    LEinit.clear();
                                    LEinit.add(LEinitPlus);
                                    LEinit.add(initPoint);
                                    LEelement.add(LEinit);
                                    LEinit.clear();
                                    LEinit.add(LEfinalPlus);
                                    LEinit.add(finalPoint);
                                    LEelement.add(LEinit);
                                    LEinit.clear();
                                    LEinit.add(LEinitPlus);
                                    LEinit.add(LEfinalPlus);
                                    LEelement.add(LEinit);
                                    LEinit.clear();
                                    Log.d(TAG, "onTouchEvent: left check"+ LEelement);
                                    if ((checkPoint(LEelement.get(0)) && checkPoint(LEelement.get(1)) && checkPoint(LEelement.get(2)) && checkPoint(LEelement.get(3)))) {
                                        canvas.drawRect(initPoint.x, finalPoint.y, LEinitPlus.x, finalPoint.y, markpaint);
                                        Log.d(TAG2, "leftCheckBox: assigned");
                                    }
                                    break;
                            }


                            switch (markpaint.getColor()) {
                                case Color.GREEN:
                                    markpaint.setColor(Color.YELLOW);
                                    MainActivity.setPlayerShow("Player 2", 2);
                                    MainActivity.setColor(2);
                                    break;
                                case Color.YELLOW:
                                    markpaint.setColor(Color.GREEN);
                                    MainActivity.setPlayerShow("Player 1", 1);
                                    MainActivity.setColor(1);

                                    break;
                            }

                        } else {
                            path.reset();
                        }


                    }

                }



                break;
        }


        invalidate();
        return true;



    }

    /*public void addPoint(Point a, Point b) {


    }*/

    /*public void checkbox(Point a,Point b,ArrayList<ArrayList<Point>> points) {

    }*/




    /*public boolean upperboxCheck(Point a, Point b,ArrayList<ArrayList<Point>> points) {

        getMeasuredHeight();
        getMeasuredWidth();

            return  true;
        }else {
            Log.d(TAG2, "upperboxCheck: not assigned");
            return false;
        }
    }*/

/*    public boolean lowerBoxCheck(Point a,Point b,ArrayList<ArrayList<Point>> points){
        getMeasuredHeight();
        getMeasuredWidth();

            return  true;
        }else {
            Log.d(TAG2, "lowerboxCheck: not assigned");
            return false;
        }
    }*/

   /* public boolean rightCheckBox(Point a,Point b,ArrayList<ArrayList<Point>> points){


            return  true;
        }else {
            Log.d(TAG2, "rightboxCheck: not assigned");
            return false;
        }
    }*/

    /*public boolean leftCheckBox(Point a,Point b,ArrayList<ArrayList<Point>> points){
        getMeasuredHeight();
        getMeasuredWidth();

            return  true;
        }else {
            Log.d(TAG2, "leftboxCheck: not assigned");
            return false;
        }

    }*/



    public boolean checkPoint (ArrayList<Point> element){
        getMeasuredHeight();
        getMeasuredWidth();
        Log.d(TAG, "checkPoint: called");

        for(int i=0;i<lineStore.size();i++){
            Log.d(TAG, "checkPoint: "+lineStore.get(i));
            Log.d(TAG, "checkPoint: "+element);
            if(element==lineStore.get(i)){
                Log.d(TAG, "checkPoint: element present");
                return true;}
        }
        return false;
    }


}


