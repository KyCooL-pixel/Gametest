package com.example.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.lang.Math;

public class Ghost {
    private Bitmap img;
    private double x,y;
    private int movingVectorX = 0;
    private int movingVectorY = 0;
    public static final int velocity =4;


    public Ghost(Bitmap bmp){
        img = bmp;
        x = 450;
        y = 1000;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(img,(float)x,(float)y,null);
    }

    public void update() {

    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;

    }
}
