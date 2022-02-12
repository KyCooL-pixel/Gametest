package com.example.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.lang.Math;

public class Ghost {
    private Bitmap img;
    private int destx,desty;
    private int x,y;
    private int movingVectorX = 0;
    private int movingVectorY = 0;
    public static final int velocity =4;


    public Ghost(Bitmap bmp){
        img = bmp;
        x = 450;
        y = 1000;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(img,x,y,null);
    }

    public void update() {
        if((x!=destx) || (y!=desty)) {
            double movingVectorLength = Math.sqrt(movingVectorX * movingVectorX + movingVectorY * movingVectorY);
                // Calculate the new position of the game character.
            this.x = x + (int)(velocity*movingVectorX/movingVectorLength);
            this.y = y + (int)(velocity*movingVectorY/movingVectorLength);
           }
           else{
               movingVectorX =0;
               movingVectorY =0;
        }
        }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setMovingVector(int movingVectorX, int movingVectorY) {
        this.movingVectorX =movingVectorX;
        this.movingVectorY = movingVectorY;
    }
    public void setDestination(int destx, int desty){
        this.destx = destx;
        this.desty = desty;
    }
}
