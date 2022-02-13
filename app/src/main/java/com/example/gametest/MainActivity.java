package com.example.gametest;


import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity.java","onCreate()");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onStart(){
        Log.d("MainActivity.java","onStart()");
        super.onStart();
    }

    @Override
    protected void onResume(){
        Log.d("MainActivity.java","onResume()");
        super.onResume();
    }

    @Override
    protected void onPause(){
        Log.d("MainActivity.java","onPause()");
        gameView.pause();
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.d("MainActivity.java","onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.d("MainActivity.java","onCreate()");
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        // Comment out the super to prevent back press action
        //super.onBackPressed();
    }
}