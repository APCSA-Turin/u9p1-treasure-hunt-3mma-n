package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    // Variables that store escape codes (to set text color)
    // source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size;
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
        // determine the row and col of the sprite to be placed
        int row = grid.length - s.getY() - 1;
        int col = s.getX();
        if (direction != "") {
            grid[row][col] = s;
        }
        // replaces the tile the sprite moved from with a Dot
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

    // prints out the current grid
    public void display() {
        // displays a key of important sprites
        System.out.println("-------- Key --------");
        System.out.println(ANSI_BLUE + "P: Player, " + ANSI_RED + "1 or 2: Enemy");
        System.out.println(ANSI_YELLOW + "T: Treasure, " + ANSI_CYAN + "W: Win Tile");
        System.out.println(ANSI_RESET + "Collect Treasures and Reach the Win Tile");
        // creates new variables to store parts of the grid to be printed
        String str = "";
        String border = "";
        // creates a horizontal border of the right length based on grid size
        for(int i = 0; i < (grid[0].length * 3) + 4; i++) {
            border += "─";
        }
        // adds the border to the top of the grid
        str += border + "\n";
        // adds each item in the grid to str
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // adds part of the left border to str if in the first col
                if (j == 0) {
                    str += "|  ";
                }
                // adds a symbol according to the sprite at each location
                if (grid[i][j] instanceof Player) {
                    str += ANSI_BLUE + "P";
                } else if (grid[i][j] instanceof Enemy) {
                    str += ANSI_RED + ((Enemy)grid[i][j]).getMoveDist();
                } else if (grid[i][j] instanceof Trophy) {
                    str += ANSI_CYAN + "W";
                } else if (grid[i][j] instanceof Treasure) {
                    str += ANSI_YELLOW + "T";
                } else {
                    str += "•";
                }
                // resets the color to white in case it was changed
                str += ANSI_RESET;
                // adds a space to make the grid display more readable
                str += "  ";
                // adds part of the right border if in the last col
                if (j == grid[0].length - 1) {
                    str += "|";
                }
            }
            str += "\n";
        }
        // adds the horizontal border to the bottom as well
        str += border;
        // prints out the full grid display string with a border
        System.out.println(str);
    }

    // returns the sprite in a provided direction from another sprite
    public Sprite getInDirection(Sprite sp, String direction) {
        // gets the x and y of the base sprite
        int x = sp.getX();
        int y = sp.getY();
        // changes the temporary variables based on the direction parameter
        if (direction.equals("d")) {x++;}
        else if (direction.equals("a")) {x--;}
        else if (direction.equals("w")) {y++;}
        else if (direction.equals("s")) {y--;}
        // returns null if the space in that direction is out of bounds
        if (y < 0 || x < 0 || x >= size || y >= size) {
            return null;
        }
        // returns the sprite in the provided direction from the base sprite
        return grid[grid.length - y - 1][x];
    }
    
    public void gameover(){ //use this method to display a loss
        System.out.println("You have run out of lives!");
        System.out.println("Better luck next time...");
    }

    public void win(){ //use this method to display a win
        System.out.println("You have collected all the treasure and reached the trophy!");
        System.out.println("Congratulations traveler!");
    }

}