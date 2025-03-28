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
        initialize();
        play();
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
        Scanner sc = new Scanner(System.in);
        while(true){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop
            grid.display();
            System.out.print("Enter Move Direction (WASD): ");
            String direction = sc.nextLine();
            if (player.isValid(size, direction)) {
                // player.interact(size, direction, treasures.length);
            }
        }
            
     
    }

    public void initialize(){
        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        grid = new Grid(8);
        player = new Player(1, 1);

        enemies = new Enemy[2];
        enemies[0] = new Enemy(3, 4);
        enemies[1] = new Enemy(5, 0);
        grid.placeSprite(player);
        for (Enemy each : enemies) {
            grid.placeSprite(each);
        }

        treasures = new Treasure[2];
        treasures[0] = new Treasure(4, 1);
        treasures[1] = new Treasure(0, 7);
        for (Treasure each : treasures) {
            grid.placeSprite(each);
        }
        grid.placeSprite(new Trophy(6, 6));
    }

    public static void main(String[] args) {
        Game myGame = new Game(8);
    }
}