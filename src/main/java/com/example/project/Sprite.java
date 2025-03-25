package com.example.project;

public class Sprite {
    private int x, y;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // returns x or y
    public int getX(){return x;}
    public int getY(){return y;}
    // sets x or y to a new value
    public void setX(int n){x = n;}
    public void setY(int n){y = n;}

    public String getCoords(){ //returns the coordinates of the sprite ->"(x,y)"
        return "(" + x + "," + y + ")";
    }

    public String getRowCol(int size){ //returns the row and column of the sprite -> "[row][col]"
        return "[" + (size - y - 1) + "][" + x + "]";
    }
    

    public void move(String direction) { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }-

    public void interact() { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }



}
