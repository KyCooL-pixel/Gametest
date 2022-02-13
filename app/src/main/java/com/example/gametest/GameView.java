package com.example.gametest;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.gametest.object.Circle;
import com.example.gametest.object.Enemy;
import com.example.gametest.object.Ghost;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private final Ghost ghost;
    private final Joystick joystick;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int joystickPointerId = 0;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        // create thread
        thread = new MainThread(getHolder(), this);

        // initialize game objects
        joystick = new Joystick(550, 1700, 100, 60);
        ghost = new Ghost(getContext(), joystick, 500, 1000, 30);


        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // handle touch event actions !!
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()) {
                    //joystick pressed liao before this -> shoot spell
                    spellList.add(new Spell(getContext(), ghost));
                } else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed now -> setIsPressed = true and store id
                    joystickPointerId =event.getPointerId((event.getActionIndex()));
                    joystick.setIsPressed(true);
                } else {
                    spellList.add(new Spell(getContext(), ghost));
                    //joystick was not pressed before and for now (this update) -> shoot spell

                }
                return true;

            case MotionEvent.ACTION_MOVE:
                //joystick was pressed previously and is now moved
                if (joystick.getIsPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                // joystick was let go of -? setIsPressed(false) and reset Actuator


                return true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        joystick.update();
        ghost.update();
        //Spawn enemy if its time
        if (Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), ghost));
        }
        // update state of each enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }
        // update state of each spell
        for (Spell spell : spellList) {
            spell.update();
        }
        //
        //  COLLISION CHECK HERE !!!!!!!!
        //
        //Iterate through enemyList and check for collision for enemy, ghost and spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, ghost)) {
                // CHANGE HERE TO CHANGE WHAT HAPPENS WHEN COLLISION TAKE PLACE
                iteratorEnemy.remove();
                // if hit by player no need to check again for spells
                continue;
            }
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // remove spell if collides with enemy
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            joystick.draw(canvas);
            ghost.draw(canvas);
            for (Enemy enemy : enemyList) {
                enemy.draw(canvas);
            }
            for (Spell spell : spellList) {
                spell.draw(canvas);
            }
        }
    }


}




