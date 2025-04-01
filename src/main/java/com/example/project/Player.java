package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y);
        treasureCount = 0;
        numLives = 2;
    }

    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}

  
    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        // changes the player x or y based on the inputted direction
        if (direction.equals("d")) {setX(getX() + 1);}
        else if (direction.equals("a")) {setX(getX() - 1);} 
        else if (direction.equals("w")) {setY(getY() + 1);} 
        else if (direction.equals("s")) {setY(getY() - 1);}
    }


    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
        //numTreasures is the total treasures at the beginning of the game
        // decrements lives if the object is an Enemy
        if (obj instanceof Enemy) {
            numLives--;
        } else if (obj instanceof Treasure) {
            // checks if the player has enough treasures if the object is a Trophy
            if (obj instanceof Trophy) {
                if (treasureCount == numTreasures) {
                    win = true;
                }
            // if the object is a Treasure but not a Trophy, increments treasureCount
            } else {
                treasureCount++;
            }
        }

    }

    public boolean isValid(int size, String direction){ //check grid boundaries
        // checks the necessary bound for each possible direction
        if (direction.equals("d") && getX() == size - 1) {return false;}
        if (direction.equals("a") && getX() == 0) {return false;}
        if (direction.equals("w") && getY() == size - 1) {return false;}
        if (direction.equals("s") && getY() == 0) {return false;}
        return true;
    }

    public String getRowCol(int size){ //return "Player:"+row col
        return "Player:" + super.getRowCol(size);
    }

    public String getCoords(){ //returns "Enemy:"+coordinates
        return "Player:" + super.getCoords();
    }

    public boolean loseLife() {
        numLives -= 1;
        if (numLives <= 0) {
            return true;
        }
        return false;
    }


   
}



