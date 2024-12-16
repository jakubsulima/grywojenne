package Army;

public class Solider {
    private static int idCounter=0;
    private int id;
    private MilitaryRank type;
    private int experience;
    private int power;

    public Solider(MilitaryRank type, int experience) {
        idCounter++;
        this.id = idCounter;
        this.type = type;
        if(experience<0) {
            this.experience = 3;
        }
        this.experience = experience;

        this.power = experience * type.getValue();
    }

enum MilitaryRank {
    SZEREGOWY(1),
    KAPRAL(2),
    KAPITAN(3),
    MAJOR(4);

    public final int value;
    private MilitaryRank(int value) {
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
}



