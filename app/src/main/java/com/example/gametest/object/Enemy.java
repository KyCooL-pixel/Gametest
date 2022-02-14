package com.example.gametest.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.gametest.MainThread;
import com.example.gametest.R;

public class Enemy extends Circle{

    private static final double SPAWNS_PER_MINUTE = 50;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = MainThread.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updateUntilNextSpawn = UPDATES_PER_SPAWN;
    private final Ghost ghost;
    public static double rates = 0.7;
    public static boolean firstSpawn = true;
    private static final double SPEED_PIXELS_PER_SECOND = Ghost.SPEED_PIXELS_PER_SECOND*rates;
    private static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;

    public Enemy(Context context, Ghost ghost, double x, double y, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), x, y, radius);
        this.ghost = ghost;
    }

    public Enemy(Context context, Ghost ghost) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
                ghost.getPositionX() + (Math.random()*2-1.00)*2500,
                ghost.getPositionY() + (Math.random()*2-1.00)*3500,
                30
        );
        this.ghost =ghost;
    }

    public static boolean readyToSpawn() {
        if(updateUntilNextSpawn <=0){
            updateUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }else{
            updateUntilNextSpawn--;
            return false;
        }
    }

    public static boolean getfirstSpawn(){
        return firstSpawn;
    }

    public static void setfirstSpawn(boolean state){
        firstSpawn = state;
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

        aimDirectionX = directionX*-1;
        aimDirectionY = directionY*-1;

    }



}
