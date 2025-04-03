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
        
        int row = grid.length - s.getY() - 1;
        int col = s.getX();
        if (direction != "") {
            grid[row][col] = s;
        }
        if (direction.equals("d")) {
            grid[row][col - 1] = new Dot(s.getX() - 1, s.getY());
        } else if (direction.equals("a")) {
            grid[row][col + 1] = new Dot(s.getX() + 1, s.getY());
        } else if (direction.equals("w")) {
            grid[row + 1][col] = new Dot(s.getX(), s.getY() + 1);
        } else if (direction.equals("s")){
            grid[row - 1][col] = new Dot(s.getX(), s.getY() - 1);
        }
    }


    public void display() { //print out the current grid to the screen 
        String str = "";
        String border = "";
        for(int i = 0; i < (grid[0].length * 3) + 4; i++) {
            border += "─";
        }
        str += border + "\n";
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (j == 0) {
                    str += "|  ";
                }
                if (grid[i][j] instanceof Player) {
                    str += "P";
                } else if (grid[i][j] instanceof Enemy) {
                    str += "E";
                } else if (grid[i][j] instanceof Trophy) {
                    str += "W";
                } else if (grid[i][j] instanceof Treasure) {
                    str += "T";
                } else {
                    str += "•";
                }
                str += "  ";
                if (j == grid[0].length - 1) {
                    str += "|";
                }
            }
            str += "\n";
        }
        str += border;
        System.out.println(str);
    }

    public Sprite getInDirection(Sprite sp, String direction) {
        int x = sp.getX();
        int y = sp.getY();
        if (direction.equals("d")) {x++;}
        else if (direction.equals("a")) {x--;}
        else if (direction.equals("w")) {y++;}
        else if (direction.equals("s")) {y--;}
        return grid[grid.length - y - 1][x];
    }
    
    public void gameover(){ //use this method to display a loss
        System.out.println("You have run out of lives!");
        System.out.println("Better luck next time...");
    }

    public void win(){ //use this method to display a win
        System.out.println("You have collected all the treasure and reahed the trophy!");
        System.out.println("Congratulations traveler!");
    }


}