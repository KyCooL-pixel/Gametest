package com.example.gametest.object;

import android.graphics.Canvas;

public abstract class GameObject {
    protected double x,y;
    protected double velocityX;
    protected double velocityY;
    protected double directionX = 1.0;
    protected double directionY;

    public GameObject(double x, double y){
        this.x =x;
        this.y =y;
    }



    public abstract void draw(Canvas canvas);
    public abstract void update();

    public double getPositionX(){
        return x;
    }

    public double getPositionY(){
        return y;
    }

    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
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
}

