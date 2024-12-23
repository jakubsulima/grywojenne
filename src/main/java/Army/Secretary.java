package Army;

import java.util.List;

public class Secretary {
    public void observeGeneralsActions(General general) {
        System.out.println("Observing actions of General: " + general.getName());
        general.printArmy();
    }

    public void generateReport(General general) {
        System.out.println("\n===== General's Report =====");
        System.out.println("General Name: " + general.getName());
        System.out.println("Gold: " + general.getGold());
        System.out.println("Army Potential: " + general.getPotentialOfArmy());
        System.out.println("Army Composition:");
        general.printArmy();
        System.out.println("============================\n");
    }
}

