package com.example.gametest.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.gametest.R;
import com.example.gametest.object.Ghost;

/*
displays ghost/enemy health
 */
public class HealthBar {
    private Ghost ghost;
    private int width, height, margin;
    private Paint borderPaint,healthPaint;

    public HealthBar(Context context, Ghost ghost){
        this.ghost = ghost;
        this.width = 100;
        this.height = 20;
        this.margin = 3;

        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.healthBarBorder);
        borderPaint.setColor(borderColor);


        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context,R.color.healthBar);
        healthPaint.setColor(healthColor);
    }
    public void draw(Canvas canvas) {
        float x = (float)ghost.getPositionX();
        float y = (float)ghost.getPositionY();
        float distanceToGhost = 30;
        float healthPointPercentage = (float)ghost.getHealthPoints()/ghost.MAX_HEALTH_POINTS;

        // draw border
        float borderLeft,borderTop,borderRight, borderBottom;
        borderLeft = x- width/2;
        borderRight = x +width/2;
        // the larger the number the far the bar from ghost
        borderBottom = y -distanceToGhost;
        borderTop = borderBottom - height;
        canvas.drawRect(borderLeft,borderTop,borderRight, borderBottom, borderPaint);

        //draw health meter
        float healthLeft,healthTop,healthRight,healthBottom,healthWidth,healthHeight;
        healthWidth= width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointPercentage;
        healthBottom = borderBottom-margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(healthLeft,healthTop,healthRight,healthBottom, healthPaint);
    }
}
