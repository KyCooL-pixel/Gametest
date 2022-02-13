package com.example.gametest;

import com.example.gametest.object.GameObject;

public class GameDisplay {


    private double gameToDisplayCoordOffsetX;
    private double gameToDisplayCoordOffsetY;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int widthPixels,int heightPixels, GameObject centerObject){
        this.centerObject = centerObject;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;
    }
    public void update(){
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayCoordOffsetX = displayCenterX - gameCenterX;
        gameToDisplayCoordOffsetY = displayCenterY - gameCenterY;
    }

    public double gameToDisplayCoordX(double x) {
        return x + gameToDisplayCoordOffsetX;
    }

    public double gameToDisplayCoordY(double y) {
        return y + gameToDisplayCoordOffsetY;
    }
}
