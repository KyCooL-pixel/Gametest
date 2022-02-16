package com.example.gametest.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gametest.GameDisplay;

public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;
    public Circle(Context context,int color, double x, double y,double radius){
        super(x,y);
        this.radius =radius;
        paint = new Paint();
        paint.setColor(color);
    }

    public static boolean isColliding(Circle obj1, Circle obj2){
        double distance = getDistanceBetweenObjects(obj1,obj2);
        double distanceToCollision = obj1.getRadius() +obj2.getRadius();
        return distance < distanceToCollision;
    }

    public static boolean isTooFar(Circle obj1, Circle obj2){
        double distance = getDistanceBetweenObjects(obj1,obj2);
        return distance > 2500;
    }

    private double getRadius(){return radius;}



    public void draw(Canvas canvas, GameDisplay gameDisplay){

        canvas.drawCircle(
                (float) gameDisplay.gameToDisplayCoordX(x),
                (float) gameDisplay.gameToDisplayCoordY(y),
                (float)radius,
                paint);
    }

}
