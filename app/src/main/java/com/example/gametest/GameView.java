package com.example.gametest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.gametest.gamepanel.GameOver;
import com.example.gametest.gamepanel.Joystick;
import com.example.gametest.object.Circle;
import com.example.gametest.object.Enemy;
import com.example.gametest.object.GameObject;
import com.example.gametest.object.Ghost;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    // initialize
    private MainThread thread;
    private final Ghost ghost;
    private final Joystick joystick;

    private int numberOfSpellsToCast = 0;
    // attack speed parameters
    public int numberOfUpdatesToWait = 50;
    public int numberOfUpdates =0;

    //Initialize target selection variables
  //  public double MinDistanceBetweenGhostAndEnemy = 2147483647;
    public int EnemyReference;

    // initialize list for spells and enemy
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Spell> spellList = new ArrayList<>();

    // Initialize game panel and display
    private int joystickPointerId = 0;
    private GameOver gameOver;
    private GameDisplay gameDisplay;




    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        // create thread
        thread = new MainThread(getHolder(), this);

        //initialize game panels
        gameOver = new GameOver(context);
        joystick = new Joystick(550, 1900, 130, 80);

        //initialize abstract objects

        // initialize game objects
        ghost = new Ghost(getContext(), joystick,0, 0, 50);

        //  initialize game display and center around player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels,displayMetrics.heightPixels,ghost);

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
                } else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed now -> setIsPressed = true and store id
                    joystickPointerId =event.getPointerId((event.getActionIndex()));
                    joystick.setIsPressed(true);
                } else {
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
        Log.d("GameView.java","surfaceCreated()");
        // create a thread every time a thread gets terminated, because a thread object can only run ONCE
        if (thread.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            thread = new MainThread(surfaceHolder, this);
        }
        thread.startThread();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("GameView.java","surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("GameView.java","surfaceDestroyed()");
    }

    public void update() {
        //Initialize shoot direction
        // if game over stop thread (update)
        if(ghost.getHealthPoints()<=0){
            return;
        }

        // update game state
        joystick.update();
        ghost.update();
        //Spawn enemy if it's time
        if (Enemy.readyToSpawn()||Enemy.getfirstSpawn()) {
            for(int i=0;i<4;i++) {
                enemyList.add(new Enemy(getContext(), ghost));
                Enemy.setfirstSpawn(false);
            }
        }
        // update state of each enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }
        //Initialize shoot direction
        int i =0;
        int tempposition =1;
        //
        //  COLLISION CHECK HERE !!!!!!!!
        //
        //Iterate through enemyList and check for collision for enemy, ghost and spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext()){
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, ghost)) {
                // CHANGE HERE TO CHANGE WHAT HAPPENS WHEN COLLISION TAKE PLACE
                iteratorEnemy.remove();
                ghost.setHealthPoints(ghost.getHealthPoints()-1);
                // if hit by player no need to check again for spells
                continue;
            }
            Iterator<Spell> iteratorSpell = spellList.iterator();
            while(iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // remove spell if collides with enemy
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();


                    break;
                }
            }
        }
        double CurrentDistance = 2147483647;
        // Min Distance Check here
        for(Enemy enemy :enemyList){
            double thisDistance = Circle.getDistanceBetweenObjects(enemy, ghost);
            // problem here, if the last  locked nearest enemy is destroyed before comparing the closest now to it, array out of bound error
            // kinda cheated here, can u see?
            if(EnemyReference < enemyList.size())
                CurrentDistance = Circle.getDistanceBetweenObjects(enemyList.get(EnemyReference),ghost);
            Log.d("Reference", Double.toString(CurrentDistance));
            if (thisDistance < CurrentDistance) {
                CurrentDistance = thisDistance;
                EnemyReference = i;

                Log.d("ReferenceC", Integer.toString(EnemyReference));
            }
            while(numberOfSpellsToCast >0){
                Log.d("Reference", Integer.toString(EnemyReference));
                spellList.add(new Spell(getContext(), ghost, enemyList.get(EnemyReference)));
                numberOfSpellsToCast --;
            }
            i++;
        }
        Log.d("Cycle", "one cycle");
        if(numberOfUpdates == numberOfUpdatesToWait){
            numberOfSpellsToCast ++;
            numberOfUpdates =0;
        }else{
            numberOfUpdates++;
        }

        // auto case a spell after waiting for updates

        for (Spell spell : spellList) {
            spell.update();
        }
        gameDisplay.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // draw game objects
            ghost.draw(canvas,gameDisplay);
            for (Enemy enemy : enemyList) {
                enemy.draw(canvas,gameDisplay);
            }
            for (Spell spell : spellList) {
                spell.draw(canvas, gameDisplay);
            }

            // Draw Game Over if ghost dead
            if(ghost.getHealthPoints()<=0){
                gameOver.draw(canvas);
            }

            // draw game panels
            joystick.draw(canvas);
        }
    }


    public void pause() {
        thread.stopThread();
    }
}




