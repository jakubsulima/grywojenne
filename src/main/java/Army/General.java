package Army;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class General implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public boolean maneuvers(int numberOfSoldiers) {
        if (numberOfSoldiers > army.size() || numberOfSoldiers < 1) {
            System.out.println("wrong number of Soldiers");
            return false;
        }
        if (gold < getCostOfManeuvers()) {
            System.out.println("Not enough money to do maneuvers");
            return false;
        }
        for (int i = 0; i < numberOfSoldiers; i++) {
            army.get(i).increaseExperience();
        }
        return true;
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
            army.remove(random.nextInt(army.size()));
            opponent.army.remove(random.nextInt(opponent.army.size()));
        } else if (firstArmyPotential < secondArmyPotential) {
            lose(opponent);
        } else {
            opponent.lose(this);
        }

    }

    public void lose(General opponent) {
        opponent.addGold( (reduceGold( (int)(getGold()*0.1) )));
        Iterator<Soldier> iterator = army.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().decreaseExperience()) {
                iterator.remove();
            }
        }
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
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
        for (Soldier soldier : army) {
            System.out.println(soldier);
        }
    }

    public String getName() {
        return name;
    }

    public int getArmySize() {
        return army.size();
    }
}

