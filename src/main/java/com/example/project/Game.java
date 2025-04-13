package com.example.project;
import java.util.Scanner;

public class Game{
    private static Scanner sc = new Scanner(System.in);
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size;
    private String message;

    public Game(int diff){ //the constructor should call initialize() and play()
        initialize(diff);
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
        int turn = -2;
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
                    for (int i = 0; i < e.getMoveDist(); i++) {
                        String d = e.followPlayer(player, grid);
                        grid.placeSprite(e, d);
                    }
                }
                // checks whether the players lives has decreased this turn
                if (player.getLives() < temp) {
                    message = "You've been hit! " + player.getLives() + " lives remain.";
                }
                System.out.print("(Press Enter to Continue) ");
                sc.nextLine();

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

                        // checks if the game should end based on the player winning
                        if (target instanceof Trophy) {
                            clearScreen();
                            grid.display();
                            grid.win();
                            break;
                        } else if (target instanceof Treasure) {
                            message = "Obtained 1 Treasure! (" + player.getTreasureCount() + "/" + treasures.length + ")";
                        }
                    } else {
                        // sets a message based on what the player interacted with
                        if (target instanceof Enemy) {
                            message = "You ran into an enemy! " + player.getLives() + " lives remain.";
                        } else {
                            message = "You must collect all " + treasures.length + " treasures to claim the trophy.";
                        }
                    }
                } else {
                    // sets a message (runs if the player tries to move out of bounds)
                    message = "You cannot move that way.";
                }
            }
            clearScreen();
            grid.display();
            // prints a message if one has been set (based on events of the turn)
            // the message is stored and only printed here so that it will be printed after the grid updates and prints
            if (!message.equals("")) {
                System.out.println(message);
            }
            // checks if the game should end based on the player running out of lives
            if (player.getLives() <= 0) {
                clearScreen();
                grid.display();
                System.out.println(message);
                grid.gameover();
                break;
            }

        }
    }

    public void initialize(int diff){
        // initializes the grid calls randomizeBoard with values based on difficulty
        if (diff == 1) {
            size = 7;
            grid = new Grid(size);
            randomizeBoard(2, 1);
        } else if (diff == 2) {
            size = 8;
            grid = new Grid(size);
            randomizeBoard(3, 2);
        } else {
            size = 8;
            grid = new Grid(size);
            randomizeBoard(5, 4);
        }

        clearScreen();
        grid.display();
    }

    public void setMessage(String n) {
        message = n;
    }

    // randomizes the position of all board objects
    public void randomizeBoard(int numEnemies, int numTreasures) {
        enemies = new Enemy[numEnemies];
        treasures = new Treasure[numTreasures];
        
        // repeats to fill the enemy list (and initialize the player)
        for (int i = 0; i <= numEnemies; i++) {
            // sets the row and col values to random numbers
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            // repeats until an empty space is found
            while (!(grid.getGrid()[row][col] instanceof Dot)) {
                row = (int) (Math.random() * size);
                col = (int) (Math.random() * size);
            }
            // places an enemy with double speed on the first iteration
            if (i == 0) {
                enemies[i] = new Enemy(size - row - 1, col, 2);
                grid.placeSprite(enemies[i]);
            // initializes the player on last loop
            } else if (i == numEnemies) {
                player = new Player(size - row - 1, col);
                grid.placeSprite(player);
            } else {
                enemies[i] = new Enemy(size - row - 1, col);
                grid.placeSprite(enemies[i]);
            }
        }

        // runs once more than numTreasures to add the trophy as well
        for (int i = 0; i <= numTreasures; i++) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            while (!(grid.getGrid()[row][col] instanceof Dot)) {
                row = (int) (Math.random() * size);
                col = (int) (Math.random() * size);
            } 
            // adds the trophy instead of a treasure on the last iteration
            if (i == numTreasures) {
                trophy = new Trophy(size - row - 1, col);
                grid.placeSprite(trophy);
            } else {
                treasures[i] = new Treasure(size - row - 1, col);
                grid.placeSprite(treasures[i]);
            }
        }
    }


    public static void main(String[] args) {
        // prints instructions
        clearScreen();
        System.out.println("Welcome to the Treasure Hunt Game!");
        System.out.println("----------------------------------------------");
        System.out.println("Your task is to collect all the treasures without dying.");
        System.out.println("Every 3 turns, the enemies will move towards you.");
        System.out.println("The number an enemy is displayed as represents its move distance.");
        System.out.println("If you are hit by or run into an enemy, you will lose a life.");
        System.out.println("When you collect all treasures and reach the win tile, you win");
        System.out.println("----------------------------------------------");
        System.out.print("Good Luck! (Press Enter to Continue) ");
        sc.nextLine();
        // runs until the player chooses to break the loop
        while (true) {
            // allows the player to select a difficulty
            clearScreen();
            System.out.println("Difficulty Options");
            System.out.println("1) Easy - 2 Enemies, 1 Treasure, 7x7 Grid");
            System.out.println("2) Medium - 3 Enemies, 2 Treasure, 8x8 Grid");
            System.out.println("3) Hard - 5 Enemies, 4 Treasure, 8x8 Grid");
            System.out.println("----------------------------------------------");
            System.out.print("Select Difficulty: ");
            int difficulty = sc.nextInt();
            // ensures the difficulty is between 1-3
            if (difficulty > 3 || difficulty < 1) {
                difficulty = 1;
            }
            // creates a new instance of Game (WITH THE PARAMETER DIFFICULTY, NOT SIZE)
            Game myGame = new Game(difficulty);
            // prompts the player to play again after the game is ended
            System.out.print("Would you like to play again? (y/n) ");
            if (!sc.nextLine().equals("y")) {
                // breaks the loop if the player chooses to quit
                break;
            }
        }
        System.out.println("Come again soon!");
        sc.close();
    }
}