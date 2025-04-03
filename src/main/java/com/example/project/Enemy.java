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

    public void move(String direction) { //move the (x,y) coordinates of the player
        // changes the player x or y based on the inputted direction
        if (direction.equals("d")) {setX(getX() + 1);}
        else if (direction.equals("a")) {setX(getX() - 1);} 
        else if (direction.equals("w")) {setY(getY() + 1);} 
        else if (direction.equals("s")) {setY(getY() - 1);}
    }

    public String followPlayer(Player p, Grid grid) {
        int xDif = getX() - p.getX();
        int yDif = getY() - p.getY();
        if (Math.abs(xDif) + Math.abs(yDif) == 1) {
            p.loseLife();
            return "";
        }

        String direction;

        if (Math.abs(xDif) > Math.abs(yDif)) {
            if (xDif > 0) {
                direction = "a";
            } else {
                direction = "d";
            }
            Sprite target = grid.getInDirection(this, direction);
            if (target instanceof Dot) {
                move(direction);
                return direction;
            }
        }

        if (yDif > 0) {
            direction = "s";
        } else {
            direction = "w";
        }
        Sprite target = grid.getInDirection(this, direction);
        if (target instanceof Dot) {
            move(direction);
            return direction;
        }
        return "";
    }
}