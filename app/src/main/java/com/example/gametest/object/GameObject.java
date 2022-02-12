package com.example.gametest.object;

import android.graphics.Canvas;

public abstract class GameObject {
    protected double x,y;
    protected double velocityX;
    protected double velocityY;

    public GameObject(double x, double y){
        this.x =x;
        this.y =y;
    }



    public abstract void draw(Canvas canvas);
    public abstract void update();

    protected double getPositionX(){
        return x;
    }

    protected double getPositionY(){
        return y;
    }

    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() -obj1.getPositionX(),2)+
                        Math.pow(obj2.getPositionY() -obj1.getPositionY(),2)
        );
    }
}

