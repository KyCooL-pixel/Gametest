package com.example.gametest;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;

public class MainThread extends Thread {
    public static final double MAX_UPS = 45.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    private double averageUPS;
    private double averageFPS;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public double getAverageUPS(){
        return averageUPS;
    }

    public double getAverageFPS(){
        return averageFPS;
    }

    public void getRunning(boolean isRunning){
        running = isRunning;
    }
    public void setRunning(boolean isRunning){ running = isRunning;}

    @Override
    public void run(){
         // declare time and cycle count vars
        int updateCount =0;
        int frameCount =0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        startTime = System.currentTimeMillis();
        while(running){
            canvas = null;
            //try update and render screen
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    updateCount++;

                    this.gameView.draw(canvas);
                }
            }catch (Exception e){
            }
            finally{
                if(canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                    catch(Exception e){e.printStackTrace();}

                }
            }
            //pause thread to control UPS
            elapsedTime =System.currentTimeMillis() - startTime;
            sleepTime =(long)(updateCount*UPS_PERIOD -elapsedTime);
            if(sleepTime >0){
                try{
                    sleep(sleepTime);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            //skip frames to keep up with target UPS
            while(sleepTime<9 && updateCount < MAX_UPS -1){
                gameView.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long)(updateCount*UPS_PERIOD -elapsedTime);
            }

            // calc average ups and fps
            elapsedTime =System.currentTimeMillis() -startTime;
            if(elapsedTime >= 1000){
                averageUPS =updateCount /(1E-3*elapsedTime);
                averageFPS =frameCount /(1E-3*elapsedTime);
                updateCount =0;
                frameCount =0;
                startTime =System.currentTimeMillis();
            }
        }

    }
}
