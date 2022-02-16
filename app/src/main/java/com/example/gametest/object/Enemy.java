package com.example.gametest.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.gametest.MainThread;
import com.example.gametest.R;

public class Enemy extends Circle{
    // enemy stats
    private static final double SPAWNS_PER_MINUTE = 300;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = MainThread.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updateUntilNextSpawn = UPDATES_PER_SPAWN;
    public static double ENEMY_SPEED_RATES = 0.42;
    public double RandomSpawnX;
    public double RandomSpawnY;

    private final Ghost ghost;

    public static boolean firstSpawn = true;
    private static final double SPEED_PIXELS_PER_SECOND = Ghost.SPEED_PIXELS_PER_SECOND*ENEMY_SPEED_RATES;
    private static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;

    public Enemy(Context context, Ghost ghost, double x, double y, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), x, y, radius);
        this.ghost = ghost;
    }

    public Enemy(Context context, Ghost ghost) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemy),
                // should determine a game frame for spawning
                ghost.getPositionX() + ((Math.round((Math.random()*2)*Math.pow(10,1))/Math.pow(10,1))-1.00)*5000,
                ghost.getPositionY() + ((Math.round((Math.random()*2)*Math.pow(10,1))/Math.pow(10,1))-1.00)*8000,
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
        // offset for avoiding stacking
        if(Math.random()>0.02){
            x+=velocityX;
            y+=velocityY;
        }
        else{
            x+=velocityX+(Math.random()*8-8);
            y+=velocityY+(Math.random()*8-8);

        }

        aimDirectionX = directionX*-1;
        aimDirectionY = directionY*-1;

    }



}
