package Army;

import java.io.*;
import java.util.Scanner;
import java.time.LocalDateTime;

public class CentralControlSystem {
    private General general1;
    private General general2;
    private Logger logger;
    private Secretary secretary;
    private boolean gameRunning;
    private General currentGeneral;
    private int turn=1;

    public CentralControlSystem() {
        logger = new Logger();
        gameRunning = true;
    }

    public void startGame() {
        System.out.println("Welcome to the General Battle Game!");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 2) {
            if (!loadGame()) {
                System.out.println("Failed to load game. Starting a new game instead.");
                initializeGenerals();
            }
        } else {
            initializeGenerals();
        }

        currentGeneral = general1;

        while (gameRunning) {
            processTurn(currentGeneral);
            turn++;
            switchToNextGeneral();
            secretary.displayGeneralsState(general1,general2);
        }

        endGameReport();
    }

    private void initializeGenerals() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name for General 1:");
        String name1 = scanner.nextLine();
        general1 = new General(name1, 100);

        System.out.println("Enter name for General 2:");
        String name2 = scanner.nextLine();
        general2 = new General(name2, 100);

        logger.logAction("Game initialized with Generals: " + general1.getName() + " and " + general2.getName());
    }

    private void processTurn(General general) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n========== " + general.getName() + "'s Turn ==========");
        System.out.println("Gold: " + general.getGold());
        System.out.println("Army Potential: " + general.getPotentialOfArmy());
        System.out.println("1. Recruit Soldier");
        System.out.println("2. Train Soldiers");
        System.out.println("3. Battle");
        System.out.println("4. Save Game");
        System.out.println("5. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                recruitSoldier(general, scanner);
                break;
            case 2:
                trainSoldiers(general, scanner);
                break;
            case 3:
                battle();
                break;
            case 4:
                saveGame();
                break;
            case 5:
                exitGame();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    private void recruitSoldier(General general, Scanner scanner) {
        System.out.println("Choose rank to recruit:");
        System.out.println("1. SZEREGOWY (Cost: 10)");
        System.out.println("2. KAPRAL (Cost: 20)");
        System.out.println("3. KAPITAN (Cost: 30)");
        System.out.println("4. MAJOR (Cost: 40)");

        int rankChoice = scanner.nextInt();
        MilitaryRank rank;

        switch (rankChoice) {
            case 1:
                rank = MilitaryRank.SZEREGOWY;
                break;
            case 2:
                rank = MilitaryRank.KAPRAL;
                break;
            case 3:
                rank = MilitaryRank.KAPITAN;
                break;
            case 4:
                rank = MilitaryRank.MAJOR;
                break;
            default:
                System.out.println("Invalid rank. No soldier recruited.");
                return;
        }

        boolean success = general.recruitSoldier(rank);
        if (success) {
            logger.logAction(general.getName() + " recruited a " + rank);
        }
    }

    private void trainSoldiers(General general, Scanner scanner) {
        System.out.println("Enter number of soldiers to train:");
        int numSoldiers = scanner.nextInt();

        general.maneuvers(numSoldiers);
        logger.logAction(general.getName() + " trained " + numSoldiers + " soldiers.");
    }

    private void battle() {
        if (general1.getPotentialOfArmy() == 0 || general2.getPotentialOfArmy() == 0) {
            System.out.println("One of the generals has no army. No battle possible.");
            return;
        }

        System.out.println("Battle initiated between " + general1.getName() + " and " + general2.getName());
        general1.battle(general2);
        logger.logAction("Battle occurred between " + general1.getName() + " and " + general2.getName());
    }

    private void saveGame() {
        LocalDateTime now = LocalDateTime.now();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame- turn" +  turn + ".dat"))) {
            oos.writeObject(general1);
            oos.writeObject(general2);
            oos.writeObject(turn);
            oos.writeObject(currentGeneral);
            logger.logAction("Game saved successfully.");
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save game.");
        }
    }

    private boolean loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            general1 = (General) ois.readObject();
            general2 = (General) ois.readObject();
            turn = (int) ois.readObject();
            currentGeneral = (General) ois.readObject();
            logger.logAction("Game loaded successfully.");
            System.out.println("Game loaded successfully.");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load game.");
            return false;
        }
    }

    private void switchToNextGeneral() {
        currentGeneral = (currentGeneral == general1) ? general2 : general1;
    }

    private void exitGame() {
        gameRunning = false;
        System.out.println("Exiting the game.");
    }

    private void endGameReport() {
        System.out.println("\nFinal Game State:");
        general1.printArmy();
        general2.printArmy();
        logger.printLogs();
    }
}
