package Army;

class Soldier {
    private MilitaryRank rank;
    private int experience;

    public Soldier(MilitaryRank rank) {
        this.rank = rank;
        this.experience = 1;
    }

    public void increaseExperience() {
        experience++;
        if (experience == rank.getValue() * 5) {
            MilitaryRank nextRank = getNextRank();
            if (nextRank != null) {
                rank = nextRank;
                experience = 1;
            }
        }
    }

    private MilitaryRank getNextRank() {
        MilitaryRank[] ranks = MilitaryRank.values();
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i] == rank && i + 1 < ranks.length) {
                return ranks[i + 1];
            }
        }
        return null;
    }

    public boolean decreaseExperience() {
        if (experience == 0) {
            return false;
        }
        experience--;
        return experience > 0;
    }


    public int getExperience() {
        return experience;
    }

    public MilitaryRank getRank() {
        return rank;
    }

    public int getPower() {
        return rank.getValue() * experience;
    }

    @Override
    public String toString() {
        return rank.name() + " (Value: " + rank.getValue() + ", Experience: " + experience + ")";
    }
}

enum MilitaryRank {
    SZEREGOWY(1),
    KAPRAL(2),
    KAPITAN(3),
    MAJOR(4);

    public final int value;

    MilitaryRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}





