package com.example.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.lang.Math;

public class Ghost {
    private Bitmap img;
    private double x,y;
    private double velocityX;
    private double velocityY;
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;


    public Ghost(Bitmap bmp){
        img = bmp;
        x = 450;
        y = 1000;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(img,(float)x,(float)y,null);
    }

    public void update(Joystick joystick) {
        velocityX =joystick.getActuatorX()*MAX_SPEED;
        velocityY =joystick.getActuatorY()*MAX_SPEED;
        x+=velocityX;
        y+=velocityY;

    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;

    }
}
