package com.example.gametest;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.gametest.object.Enemy;
import com.example.gametest.object.Ghost;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private final Ghost ghost;
    private final Joystick joystick;
    private final Enemy enemy;

    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);
        // create thread
        thread = new MainThread(getHolder(),this);

        // intialize game objects
        joystick = new Joystick(550,1700,100,60);
        ghost = new Ghost(getContext(),joystick,500,1000,30);
        enemy = new Enemy(getContext(),ghost,500,1000,30);

        setFocusable(true);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        // handle touch event actions !!
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                if(joystick.isPressed((double)event.getX(), (double)event.getY())){
                    joystick.setIsPressed(true);
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(),(double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
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
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            }
        }
    public void update() {
        joystick.update();
        ghost.update();
        enemy.update();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            joystick.draw(canvas);
            ghost.draw(canvas);
            enemy.draw(canvas);
        }
    }
}




