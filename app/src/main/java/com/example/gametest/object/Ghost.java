package com.example.gametest.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.gametest.GameDisplay;
import com.example.gametest.gamepanel.HealthBar;
import com.example.gametest.gamepanel.Joystick;
import com.example.gametest.MainThread;
import com.example.gametest.R;
import com.example.gametest.Utils;

public class Ghost extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;
    public static int MAX_HEALTH_POINTS = 5;
    private final Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;

    public Ghost(Context context, Joystick joystick, double x, double y, double radius){
        super(context, ContextCompat.getColor(context, R.color.player),x,y,radius);
        this.joystick =joystick;
        this.healthBar = new HealthBar(context,this);
        this.healthPoints = MAX_HEALTH_POINTS;
    }


    public void update() {
        //update velocity
        velocityX =joystick.getActuatorX()*MAX_SPEED;
        velocityY =joystick.getActuatorY()*MAX_SPEED;

        // update position
        x+=velocityX;
        y+=velocityY;

        // update direction
        if(velocityX!=0 || velocityY != 0){
            // find unit vector of velocity
            double distance = Utils.getDistanceBetweenPoints(0,0,velocityX,velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }

        // update AimDirection according to nearest enemy
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        super.draw(canvas, gameDisplay);
        healthBar.draw(canvas, gameDisplay);
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if(healthPoints >=0)
            this.healthPoints = healthPoints;
    }
}
