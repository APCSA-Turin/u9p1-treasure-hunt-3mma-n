package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        grid = new Sprite[size][size];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                // adds a new dot to each slot in the grid
                grid[row][col] = new Dot(col, size - row - 1);
            }
        }
    }

 
    public Sprite[][] getGrid(){return grid;}



    public void placeSprite(Sprite s){ //place sprite in new spot
        grid[grid.length - s.getY() - 1][s.getX()] = s;
    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        // NOT FUNCTIONAL YET
        temp = grid[grid.length - s.getY() - 1][s.getX()];
        if (direction.equals("d")) {
            s.setX(s.getX() + 1);
        } else if (direction.equals("a")) {
            s.setX(s.getX() - 1);
        } else if (direction.equals("w")) {
            s.setY(s.getY() + 1);
        } else if (direction.equals("s")){
            s.setY(s.getY() - 1);
        }

        
    }


    public void display() { //print out the current grid to the screen 
    }
    
    public void gameover(){ //use this method to display a loss
    }

    public void win(){ //use this method to display a win 
    }


}