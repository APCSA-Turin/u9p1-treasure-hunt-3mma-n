package com.example.project;

//Enemy only need constructor and getCoords() getRowCol()
public class Enemy extends Sprite { //child  of Sprite
    
    // new instance variable that determines how many spaces an enemy moves
    private int moveDist;

    public Enemy(int x, int y) {
        super(x, y);
        moveDist = 1;
    }

    // additional constructor to allow moveDist to be set to a higher value
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

    public void move(String direction) {
        // changes the x or y based on the inputted direction
        if (direction.equals("d")) {setX(getX() + 1);}
        else if (direction.equals("a")) {setX(getX() - 1);} 
        else if (direction.equals("w")) {setY(getY() + 1);} 
        else if (direction.equals("s")) {setY(getY() - 1);}
    }

    // moves one space to pathfind towards the player, if possible
    public String followPlayer(Player p, Grid grid) {
        // determines the difference in x and y pos from the enemy to the player
        int xDif = getX() - p.getX();
        int yDif = getY() - p.getY();

        // checks if the enemy is next to the player (only one x or y away total)
        if (Math.abs(xDif) + Math.abs(yDif) == 1) {
            // damages the player and doesn't move
            p.loseLife();
            return "";
        }

        // defines variables for which horizontal and vertical directions would move the enemy closest to the player
        // sets either direction to an empty string if the enemy is already aligned with the player on that axis
        String xDirection;
        String yDirection;
        if (xDif > 0) {
            xDirection = "a";
        } else if (xDif < 0) {
            xDirection = "d";
        } else {
            xDirection = "";
        }
        if (yDif > 0) {
            yDirection = "s";
        } else if (yDif < 0) {
            yDirection = "w";
        } else {
            yDirection = "";
        }

        // retrieves the sprite in each of those two directions from the enemy
        Sprite xTarget = grid.getInDirection(this, xDirection);
        Sprite yTarget = grid.getInDirection(this, yDirection);

        /* moves the enemy along the x axis if: 
         * it is further from the player along that axis or the space it would move to along the y axis is not empty
         * BUT ONLY if the space it would move to along the x axis is empty
        */
        if ((Math.abs(xDif) > Math.abs(yDif) || !(yTarget instanceof Dot)) && xTarget instanceof Dot) {
            move(xDirection);
            return xDirection;
        }
        /* moves the enemy along the y axis if
         * it is further from the player along that axis or the space it would move to along the x axis is not empty
         * BUT ONLY if the space it would move to along the y axis is empty
         */
        if (yTarget instanceof Dot) {
            move(yDirection);
            return yDirection;
        }
        // if the enemy cannot move in either direction, an empty string is returned
        return "";
    }

    public int getMoveDist() {
        return moveDist;
    }
}