package com.example.gametest.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.gametest.MainThread;
import com.example.gametest.R;

public class Enemy extends Circle{

    private final Ghost ghost;
    private static final double SPEED_PIXELS_PER_SECOND = Ghost.SPEED_PIXELS_PER_SECOND*0.4;
    private static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;
    public Enemy(Context context, Ghost ghost, double x, double y, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), x, y, radius);
        this.ghost = ghost;
    }

    @Override
    public void update() {
        //calculate vector from enemy to ghost
        double distanceGX = ghost.getPositionX()-x;
        double distanceGY= ghost.getPositionY()-y;

        //calculate distance enemy and player
        double distanceToG =GameObject.getDistanceBetweenObjects(this,ghost);
        //calculate direction from enemy to player
        double directionX = distanceGX/distanceToG;
        double directionY = distanceGY/distanceToG;

        // set velocity towards ghost
        if(distanceToG >0){
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        }else{
            velocityX = 0;
            velocityY = 0;
        }

        //update enemy position
        x+=velocityX;
        y+=velocityY;

    }



}
