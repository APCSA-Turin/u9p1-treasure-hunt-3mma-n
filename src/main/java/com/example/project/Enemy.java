package com.example.project;

//Enemy only need constructor and getCoords() getRowCol()
public class Enemy extends Sprite { //child  of Sprite
    
    public Enemy(int x, int y) {
        super(x, y);
    }


    //the methods below should override the super class 


    public String getCoords(){ //returns "Enemy:"+coordinates
        return "Enemy:" + super.getCoords();
    }


    public String getRowCol(int size){ //return "Enemy:"+row col
        return "Enemy:" + super.getRowCol(size);
    }

    public String move(Player p) {
        int xDif = getX() - p.getX();
        int yDif = getY() - p.getY();
        if (Math.abs(xDif) + Math.abs(yDif) == 1) {
            p.loseLife();
            return "";
        }
        else if (Math.abs(xDif) > Math.abs(yDif)) {
            if (xDif > 0) {
                setX(getX() - 1);
                return "a";
            } else {
                setX(getX() + 1);
                return "d";
            }
        } else {
            if (yDif > 0) {
                setY(getY() - 1);
                return "s";
            } else {
                setY(getY() + 1);
                return "w";
            }
        }
    }
}