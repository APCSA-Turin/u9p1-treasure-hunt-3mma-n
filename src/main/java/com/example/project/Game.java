package com.example.project;
import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 

    public Game(int size){ //the constructor should call initialize() and play()
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        Scanner scanner = new Scanner(System.in);


        while(true){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop

     
            }
            
     
    }

    public void initialize(){

        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
   
    }

    public static void main(String[] args) {
        int size = 10;
        grid = new Grid(size);
        player = new Player(0, 0);
        enemy = new Enemy(5, 5);
        enemy2 = new Enemy(7,8);
        treasure = new Treasure(2, 2);
        treasure2 = new Treasure(1,7);
        trophy = new Trophy(9, 9);

        // Row 0: [ ][ ][ ][ ][ ][ ][ ][ ][ ][W]
        // Row 1: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 2: [ ][T][ ][ ][ ][ ][ ][E][ ][ ]
        // Row 3: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 4: [ ][ ][ ][ ][ ][E][ ][ ][ ][ ]
        // Row 5: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 6: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 7: [ ][ ][T][ ][ ][ ][ ][ ][ ][ ]
        // Row 8: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 9: [P][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        //         0  1  2  3  4  5  6  7  8  9

        // Place objects on the grid
        grid.placeSprite(player);
        grid.placeSprite(enemy);
        grid.placeSprite(enemy2);
        grid.placeSprite(treasure);
        grid.placeSprite(treasure2);
        grid.placeSprite(trophy);
        
    }
}