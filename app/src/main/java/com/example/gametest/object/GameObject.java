package com.example.gametest.object;

import android.graphics.Canvas;

import com.example.gametest.GameDisplay;

public abstract class GameObject {
    protected double x,y;
    protected double velocityX;
    protected double velocityY;
    protected double directionX = 1.0;
    protected double directionY;
    protected double AimVelocityX;
    protected double AimVelocityY;
    protected double aimDirectionX;
    protected double aimDirectionY;

    public GameObject(double x, double y){
        this.x =x;
        this.y =y;
    }



    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();

    public double getPositionX(){
        return x;
    }

    public double getPositionY(){
        return y;
    }

    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() -obj1.getPositionX(),2)+
                        Math.pow(obj2.getPositionY() -obj1.getPositionY(),2)
        );
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public double getAimDirectionX(){return aimDirectionX;}

    public double getAimDirectionY(){return aimDirectionY;}
}

