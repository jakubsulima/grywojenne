package Army;

public class Secretary {
    public void displayGeneralsState(General general1, General general2) {
        System.out.println("\n===== Generals' State =====");

        // General 1's State
        System.out.println("General 1: " + general1.getName());
        System.out.println("Gold: " + general1.getGold());
        System.out.println("Army Potential: " + general1.getPotentialOfArmy());
        System.out.println("Army Composition:");
        general1.printArmy();
        System.out.println("--------------------------");

        // General 2's State
        System.out.println("General 2: " + general2.getName());
        System.out.println("Gold: " + general2.getGold());
        System.out.println("Army Potential: " + general2.getPotentialOfArmy());
        System.out.println("Army Composition:");
        general2.printArmy();
        System.out.println("==========================\n");
    }
}


