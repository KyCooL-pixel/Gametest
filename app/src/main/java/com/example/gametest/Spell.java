package com.example.gametest;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.gametest.object.Circle;
import com.example.gametest.object.Enemy;
import com.example.gametest.object.GameObject;
import com.example.gametest.object.Ghost;

public class Spell extends Circle {

    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND / MainThread.MAX_UPS;


    public Spell(Context context, Ghost spellcaster, Enemy enemy) {
        super(
                context,
                ContextCompat.getColor(context, R.color.spell),
                spellcaster.getPositionX(),
                spellcaster.getPositionY(),
                20
        );
        velocityX = enemy.getAimDirectionX()*MAX_SPEED;
        velocityY = enemy.getAimDirectionY()*MAX_SPEED;
    }

    @Override
    public void update() {
        x+= velocityX*2;
        y+= velocityY*2;
    }
}
