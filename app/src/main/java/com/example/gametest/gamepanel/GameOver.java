package com.example.gametest.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.gametest.R;


// panel draws gameover text
public class GameOver {
    private Context context;

    public GameOver(Context context){
        this.context=context;
    }

    public void draw(Canvas canvas){
        String text = "Game\n Over";
        float x =150;
        float y =300;

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);
        canvas.drawText(text,x,y,paint);
    }
}
