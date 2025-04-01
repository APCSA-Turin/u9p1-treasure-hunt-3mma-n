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
        int turn = 0;
        clearScreen();
        grid.display();
        // iterates until the loop is broken
        while(true){
            turn++;
            System.out.println("Enemy Move Charge: " + turn + "/3");
            // prints the player's coordinates and lives (after the grid is displayed)
            System.out.println(player.getCoords() + "  Lives:" + player.getLives());
            // prompts the user to enter a direction and saves it to a variable
            System.out.print("Enter Move Direction (WASD): ");
            String direction = sc.nextLine();

            if (turn > 2) {
                for (Enemy e : enemies) {
                    String d = e.followPlayer(player, grid);
                    // System.out.println(e.getCoords() + " " + d);
                        grid.placeSprite(e, d);
                }
                turn = 1;
            }

            // only tries to move if the player wouldn't leave bounds
            if (player.isValid(size, direction)) {
                // obtains the sprite the player would be moving into
                Sprite target = grid.getInDirection(player, direction);
                // runs the player interaction with the sprite at their move location
                player.interact(size, direction, treasures.length, target);

                // checks if the sprite will be replaced by the player or if the player will not move
                if (!(target instanceof Enemy) && (!(target instanceof Trophy) || player.getTreasureCount() == treasures.length)) {
                    // moves and places the player then resets the screen 
                    player.move(direction);
                    grid.placeSprite(player, direction);
                    clearScreen();
                    grid.display();

                    // displays a message based on what the player interacted with
                    if (target instanceof Trophy) {
                        grid.win();
                        break;
                    } else if (target instanceof Treasure) {
                        System.out.println("Obtained 1 Treasure! (" + player.getTreasureCount() + "/" + treasures.length + ")");
                    }
                } else {
                    // resets the screen
                    clearScreen();
                    grid.display();

                    // displays a message based on what the player interacted with
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
                // resets the screen and displays a message (runs if the player tries to move out of bounds)
                clearScreen();
                grid.display();
                System.out.println("You cannot move that way.");
            }
        }
            
     
    }

    public void initialize(int size){
        // initializes the grid and places the player
        this.size = size;
        grid = new Grid(size);
        player = new Player(1, 1);
        grid.placeSprite(player);

        // creates new enemies and places them on the board
        enemies = new Enemy[2];
        enemies[0] = new Enemy(3, 4);
        enemies[1] = new Enemy(5, 0);
        for (Enemy each : enemies) {
            grid.placeSprite(each);
        }

        // creates new treasures and places them on the board
        treasures = new Treasure[2];
        treasures[0] = new Treasure(4, 1);
        treasures[1] = new Treasure(0, 7);
        for (Treasure each : treasures) {
            grid.placeSprite(each);
        }
        // adds the trophy to the board
        grid.placeSprite(new Trophy(6, 6));
    }

    public static void main(String[] args) {
        Game myGame = new Game(8);
    }
}