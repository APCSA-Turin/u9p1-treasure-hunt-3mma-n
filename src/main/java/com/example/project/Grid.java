package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

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
        System.out.println("-------- Key --------");
        System.out.println(ANSI_BLUE + "P: Player, " + ANSI_RED + "1 or 2: Enemy");
        System.out.println(ANSI_YELLOW + "T: Treasure, " + ANSI_CYAN + "W: Win Tile");
        System.out.println(ANSI_RESET + "Collect Treasures and Reach the Win Tile");
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
                str += ANSI_RESET;
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
        if (y < 0 || x < 0 || x >= size || y >= size) {
            return null;
        }
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