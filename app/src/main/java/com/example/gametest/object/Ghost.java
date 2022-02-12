package com.example.gametest.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.gametest.Joystick;
import com.example.gametest.MainThread;
import com.example.gametest.R;

public class Ghost extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;
    private final Joystick joystick;

    public Ghost(Context context, Joystick joystick, double x, double y, double radius){
        super(context, ContextCompat.getColor(context, R.color.player),x,y,radius);
        this.joystick =joystick;
    }


    public void update() {
        //update velocity
        velocityX =joystick.getActuatorX()*MAX_SPEED;
        velocityY =joystick.getActuatorY()*MAX_SPEED;

        // update position
        x+=velocityX;
        y+=velocityY;

    }



}
