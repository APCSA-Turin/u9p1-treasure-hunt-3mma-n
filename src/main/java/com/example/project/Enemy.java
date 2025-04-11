package com.example.project;

//Enemy only need constructor and getCoords() getRowCol()
public class Enemy extends Sprite { //child  of Sprite
    
    private int moveDist;

    public Enemy(int x, int y) {
        super(x, y);
        moveDist = 1;
    }

    public Enemy(int x, int y, int m) {
        super(x, y);
        moveDist = m;
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
        String xDirection;
        String yDirection;
        if (xDif > 0) {
            xDirection = "a";
        } else {
            xDirection = "d";
        }
        if (yDif > 0) {
            yDirection = "s";
        } else {
            yDirection = "w";
        }
        Sprite xTarget = grid.getInDirection(this, xDirection);
        Sprite yTarget = grid.getInDirection(this, yDirection);

        // changes x if thats closer to the players direction
        if ((Math.abs(xDif) > Math.abs(yDif) || !(yTarget instanceof Dot)) && xTarget instanceof Dot) {
            move(xDirection);
            return xDirection;
        }
        if (yTarget instanceof Dot) {
            move(yDirection);
            return yDirection;
        }
        return "";
    }

    public int getMoveDist() {
        return moveDist;
    }
}