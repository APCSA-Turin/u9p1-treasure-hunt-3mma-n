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
        initialize(size);
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
        clearScreen();
        grid.display();
        while(true){
            // try {
            //     Thread.sleep(100); // Wait for 1/10 seconds
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            System.out.println(player.getCoords() + "  Lives:" + player.getLives());
            System.out.print("Enter Move Direction (WASD): ");
            String direction = sc.nextLine();
            if (player.isValid(size, direction)) {
                Sprite target = grid.getInDirection(player, direction);
                player.interact(size, direction, treasures.length, target);
                if (!(target instanceof Enemy) && (!(target instanceof Trophy) || player.getTreasureCount() == treasures.length)) {
                    player.move(direction);
                    grid.placeSprite(player, direction);
                    clearScreen();
                    grid.display();
                    if (target instanceof Trophy) {
                        grid.win();
                        break;
                    } else if (target instanceof Treasure) {
                        System.out.println("Obtained 1 Treasure! (" + player.getTreasureCount() + "/" + treasures.length + ")");
                    }
                } else {
                    clearScreen();
                    grid.display();
                    if (target instanceof Enemy) {
                        System.out.println("You've been hit by an enemy! " + player.getLives() + " lives remain.");
                        if (player.getLives() <= 0) {
                            grid.gameover();
                            break;
                        }
                    } else {
                        System.out.println("You must collect all " + treasures.length + " treasures to claim the trophy.");
                    }
                }
            } else {
                clearScreen();
                grid.display();
                System.out.println("You cannot move that way.");
            }
        }
            
     
    }

    public void initialize(int size){
        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        this.size = size;
        grid = new Grid(size);
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