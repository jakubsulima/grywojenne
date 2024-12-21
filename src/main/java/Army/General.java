package Army;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class General {
    private String name;
    private int gold;
    private ArrayList<Soldier> army;

    public General(String name, int initialGold) {
        if (name == null) {
            name = "general";
        }
        this.name = name;
        if (initialGold < 0) {
            initialGold = 0;
        }
        this.gold = initialGold;
        this.army = new ArrayList<Soldier>();
        army.add(new Soldier(MilitaryRank.SZEREGOWY));
    }

    public void addSoldier(Soldier soldier) {
        army.add(soldier);
    }

    public void maneuvers(int numberOfSoldiers) {
        if (numberOfSoldiers > army.size() || numberOfSoldiers < 1) {
            System.out.println("wrong number of Soldiers");
            return;
        }
        if (gold < getCostOfManeuvers()) {
            System.out.println("Not enough money to do maneuvers");
            return;
        }
        for (int i = 0; i < numberOfSoldiers; i++) {
            army.get(i).increaseExperience();
        }
    }

    private int getCostOfManeuvers() {
        int cost = 0;
        for (Soldier soldier : army) {
            cost += soldier.getRank().getValue();
        }
        return cost;
    }

    public int getPotentialOfArmy() {
        int potential = 0;
        for (Soldier soldier : army) {
            potential += soldier.getPower();
        }
        return potential;
    }

    public void battle(General opponent) {
        int firstArmyPotential = getPotentialOfArmy();
        int secondArmyPotential = opponent.getPotentialOfArmy();
        Random random = new Random();
        if (firstArmyPotential == secondArmyPotential) {
            army.remove(random.nextInt(army.size()-1));
            opponent.army.remove(random.nextInt(army.size()-1));
        } else if (firstArmyPotential > secondArmyPotential) {
            lose(opponent);
        } else {
            opponent.lose(this);
        }

    }

    public void lose(General opponent) {
        opponent.addGold( (reduceGold( (int)(getGold()*0.1) )));
        for (Soldier soldier : army) {
            if (!soldier.decreaseExperience()) {
                army.remove(soldier);
            }
        }
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        this.gold = gold;
    }

    public int reduceGold(int gold) {
        this.gold -= gold;
        return gold;
    }
    public List<Soldier> getArmy() {
        return army;
    }

    public boolean recruitSoldier(MilitaryRank rank) {
        if (gold >= 10 * rank.getValue()) {
            Soldier newSoldier = new Soldier(rank);
            army.add(newSoldier);
            gold -= 10 * rank.getValue();
            System.out.println(name + " recruited a " + rank);
            return true;
        } else {
            System.out.println(name + " does not have enough gold to recruit a soldier.");
            return false;
        }
    }

    public void printArmy() {
        System.out.println(name + "'s Army:");
        for (Soldier soldier : army) {
            System.out.println(soldier);
        }
    }
}

