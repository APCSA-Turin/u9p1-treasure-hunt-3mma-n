package com.example.project;
import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size;
    private String message;

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
        int turn = -1;
        // iterates until the loop is broken
        while(true){
            // declares a message variable that will be altered and displayed based on the events of the turn
            String message = "";
            turn++;
            // checks whether the enemies or player should move 
            if (turn == 3) {
                turn = -1;
                System.out.println("!!ENEMY TURN!!");
                // sets a temp variable for the players current lives
                int temp = player.getLives();
                // moves and places each enemy on the grid
                for (Enemy e : enemies) {
                    String d = e.followPlayer(player, grid);
                    // System.out.println(e.getCoords() + " " + d);
                    grid.placeSprite(e, d);

                }
                // checks whether the players lives has decreased this turn
                if (player.getLives() < temp) {
                    message = "You've been hit! " + player.getLives() + " lives remain.";
                }
                System.out.print("Press Enter to Continue. ");
                String direction = sc.nextLine();

            } else {
                System.out.println("Enemy Move Charge: " + turn + "/3");
                // prints the player's coordinates and lives (after the grid is displayed)
                System.out.println(player.getCoords() + "  Lives:" + player.getLives());
                // prompts the user to enter a direction and saves it to a variable
                System.out.print("Enter Move Direction (WASD): ");
                String direction = sc.nextLine();

                // only tries to move if the player wouldn't leave bounds
                if (player.isValid(size, direction)) {
                    // obtains the sprite the player would be moving into
                    Sprite target = grid.getInDirection(player, direction);
                    // runs the player interaction with the sprite at their move location
                    player.interact(size, direction, treasures.length, target);

                    // checks if the sprite will be replaced by the player or if the player will not move
                    if (!(target instanceof Enemy) && (!(target instanceof Trophy) || player.getTreasureCount() == treasures.length)) {
                        // moves and places the player
                        player.move(direction);
                        grid.placeSprite(player, direction);

                        // displays a message based on what the player interacted with
                        if (target instanceof Trophy) {
                            clearScreen();
                            grid.display();
                            grid.win();
                            System.out.print("Would you like to play again? (y/n) ");
                            if (!sc.nextLine().equals("y")) {
                                break;
                            }
                            initialize(size);
                            turn = 1;
                        } else if (target instanceof Treasure) {
                            message = "Obtained 1 Treasure! (" + player.getTreasureCount() + "/" + treasures.length + ")";
                        }
                    } else {
                        // displays a message based on what the player interacted with
                        if (target instanceof Enemy) {
                            message = "You ran into an enemy! " + player.getLives() + " lives remain.";
                        } else {
                            message = "You must collect all " + treasures.length + " treasures to claim the trophy.";
                        }
                    }
                } else {
                    // displays a message (runs if the player tries to move out of bounds)
                    message = "You cannot move that way.";
                }
            }
            clearScreen();
            grid.display();
            if (!message.equals("")) {
                System.out.println(message);
            }
            if (player.getLives() <= 0) {
                clearScreen();
                grid.display();
                System.out.println(message);
                grid.gameover();
                System.out.print("Would you like to play again? (y/n) ");
                if (!sc.nextLine().equals("y")) {
                    break;
                }
                initialize(size);
                turn = 1;
            }

        }
        sc.close(); 
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
        clearScreen();
        grid.display();
    }

    public void setMessage(String n) {
        message = n;
    }

    // public void randomizeBoard(int numEnemies, int numTreasures) {
    //     enemies = new Enemy[numEnemies];
    //     treasures = new Treasure[numTreasures];
    //     for (int i = 0; i < numEnemies; i++) {
    //         int row = Math.randInt()
    //     }
    // }


    public static void main(String[] args) {
        Game myGame = new Game(8);
    }
}