package com.example.gametest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {
    private Paint InnerPaint;
    private Paint outerPaint;
    private int outerCircleCenterX;
    private int outerCircleCenterY;
    private int InnerCircleCenterX;
    private int InnerCircleCenterY;
    private int outerRadius;
    private int innerRadius;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerX, int centerY, int outerRadius, int innerRadius){
        // outer and inner circle = joystick, or should i say, thumbstick ?
        outerCircleCenterX = centerX;
        outerCircleCenterY = centerY;
        InnerCircleCenterX = centerX;
        InnerCircleCenterY = centerY;

        // radii of inner,outer circles
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;

        // paint the circles
        outerPaint = new Paint();
        outerPaint.setColor(Color.GRAY);
        outerPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        InnerPaint = new Paint();
        InnerPaint.setColor(Color.BLUE);
        InnerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void draw(Canvas canvas) {
        // draw outer
        canvas.drawCircle( outerCircleCenterX,outerCircleCenterY,outerRadius,outerPaint);
        // draw inner
        canvas.drawCircle( InnerCircleCenterX,InnerCircleCenterY,innerRadius,InnerPaint);

    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        InnerCircleCenterX = (int)(outerCircleCenterX +actuatorX*outerRadius);
        InnerCircleCenterY = (int)(outerCircleCenterY +actuatorY*outerRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterX -touchPositionX, 2)+
                Math.pow(outerCircleCenterY -touchPositionY, 2)
        );
        return joystickCenterToTouchDistance <outerRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double dx =touchPositionX - outerCircleCenterX;
        double dy =touchPositionY - outerCircleCenterY;
        double dDistance = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));

        if(dDistance <outerRadius){
            actuatorX = dx/outerRadius;
            actuatorY = dy/outerRadius;
        }
        else{
            actuatorX =dx/dDistance;
            actuatorY =dy/dDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }
}
