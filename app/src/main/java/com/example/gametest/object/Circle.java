package com.example.gametest.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;
    public Circle(Context context,int color, double x, double y,double radius){
        super(x,y);
        this.radius =radius;
        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle((float)x,(float)y,(float)radius, paint);
    }

}
