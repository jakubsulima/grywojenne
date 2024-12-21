package Army;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {

    @Test
    void testSoldierInitializationRank() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        assertEquals(MilitaryRank.SZEREGOWY, soldier.getRank());
    }

    @Test
    void testSoldierInitializationExperience() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        assertEquals(1, soldier.getExperience());
    }

    @Test
    void testIncreaseExperience() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        soldier.increaseExperience();
        assertEquals(2, soldier.getExperience());
    }

    @Test
    void testPromotionToNextRank() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        for (int i = 0; i < 5; i++) soldier.increaseExperience();
        assertEquals(MilitaryRank.KAPRAL, soldier.getRank());
    }

    @Test
    void testExperienceResetsAfterPromotion() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        for (int i = 0; i < 5; i++) soldier.increaseExperience();
        assertEquals(2, soldier.getExperience());
    }

    @Test
    void testNoPromotionBeyondMajorRank() {
        Soldier soldier = new Soldier(MilitaryRank.MAJOR);
        for (int i = 0; i < 10; i++) soldier.increaseExperience();
        assertEquals(MilitaryRank.MAJOR, soldier.getRank());
    }

    @Test
    void testDecreaseExperience() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        soldier.decreaseExperience();
        assertEquals(0, soldier.getExperience());
    }

    @Test
    void testPowerCalculationForRankSzeregowy() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        soldier.increaseExperience();
        assertEquals(2, soldier.getPower());
    }

    @Test
    void testPowerCalculationForRankKapral() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        soldier.increaseExperience();
        assertEquals(4, soldier.getPower());
    }

    @Test
    void testToStringOutputInitial() {
        Soldier soldier = new Soldier(MilitaryRank.SZEREGOWY);
        assertEquals("SZEREGOWY (Value: 1, Experience: 1)", soldier.toString());
    }

    @Test
    void testToStringOutputAfterExperienceIncrease() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        for (int i = 0; i < 3; i++) soldier.increaseExperience();
        assertEquals("KAPRAL (Value: 2, Experience: 4)", soldier.toString());
    }
}
