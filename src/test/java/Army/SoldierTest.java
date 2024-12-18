package Army;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {

    @Test
    void testSoldierInitialization() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        assertEquals(MilitaryRank.SZEREGOWY, soldier.getRank());
        assertEquals(1, soldier.getExperience());
    }

    @Test
    void testIncreaseExperience() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        for (int i = 0; i < 5; i++) {
            soldier.increaseExperience();
        }
        assertEquals(2, soldier.getExperience());
    }

    @Test
    void testPromotionToNextRank() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        for (int i = 0; i < 5 * MilitaryRank.SZEREGOWY.getValue(); i++) {
            soldier.increaseExperience();
        }
        assertEquals(MilitaryRank.KAPRAL, soldier.getRank());
    }

    @Test
    void testNoPromotionBeyondMajor() {
        Soldier soldier = new Soldier(MilitaryRank.MAJOR);
        for (int i = 0; i < 10; i++) {
            soldier.increaseExperience();
        }
        assertEquals(MilitaryRank.MAJOR, soldier.getRank());
    }

    @Test
    void testDecreaseExperience() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        soldier.decreaseExperience();
        assertFalse(soldier.decreaseExperience()); // Returns false as experience is now 0
        assertEquals(0, soldier.getExperience());
    }

    @Test
    void testPowerCalculation() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        soldier.increaseExperience();
        soldier.increaseExperience();
        assertEquals(MilitaryRank.KAPRAL.getValue() * soldier.getExperience(), soldier.getPower());
    }

    @Test
    void testToStringOutput() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        String expectedOutput = "KAPRAL (Value: 2, Experience: 1)";
        assertEquals(expectedOutput, soldier.toString());
    }
}
