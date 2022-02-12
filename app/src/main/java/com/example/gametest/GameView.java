package com.example.gametest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Ghost ghost;

    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        // handle touch event actions !!
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                ghost.setPosition((double) event.getX(),(double)event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                ghost.setPosition((double) event.getX(),(double)event.getY());
                return true;

                
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        ghost = new Ghost(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ghost_right_cropped),220, 200, false));
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
        ghost.update();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            ghost.draw(canvas);
        }
    }
}




