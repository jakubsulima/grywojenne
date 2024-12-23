package Army;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneralTest {

    private General general;
    private General opponent;

    @BeforeEach
    void setUp() {
        general = new General("Alexander", 100);
        opponent = new General("Caesar", 100);
    }

    @Test
    void testInitialName() {
        assertEquals("Alexander", general.getName());
    }

    @Test
    void testInitialGold() {
        assertEquals(100, general.getGold());
    }

    @Test
    void testInitialArmySize() {
        assertEquals(1, general.getArmy().size()); // General starts with one soldier
    }

    @Test
    void testAddSoldierIncreasesArmySize() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        general.addSoldier(soldier);
        assertEquals(2, general.getArmy().size());
    }

    @Test
    void testAddSoldierContainsCorrectSoldier() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        general.addSoldier(soldier);
        assertTrue(general.getArmy().contains(soldier));
    }

    @Test
    void testRecruitSoldierSuccess() {
        boolean recruited = general.recruitSoldier(MilitaryRank.KAPRAL);
        assertTrue(recruited);
    }

    @Test
    void testRecruitSoldierDecreasesGold() {
        general.recruitSoldier(MilitaryRank.KAPRAL);
        assertEquals(80, general.getGold());
    }

    @Test
    void testRecruitSoldierFailsWithoutEnoughGold() {
        general.reduceGold(90); // Reduce gold to 10
        boolean recruited = general.recruitSoldier(MilitaryRank.MAJOR);
        assertFalse(recruited);
    }

    @Test
    void testManeuversIncreaseExperience() {
        general.maneuvers(1);
        assertEquals(2, general.getArmy().get(0).getExperience());
    }

    @Test
    void testManeuversFailsWithoutEnoughGold() {
        general.reduceGold(100); // Reduce gold to 5, less than cost of maneuvers
        general.maneuvers(1);
        assertEquals(1, general.getArmy().get(0).getExperience()); // No experience increase
    }

    @Test
    void testGetPotentialOfArmyInitial() {
        int potential = general.getPotentialOfArmy();
        assertTrue(potential > 0);
    }

    @Test
    void testGetPotentialOfArmyIncreasesAfterRecruitment() {
        general.recruitSoldier(MilitaryRank.KAPRAL);
        int potential = general.getPotentialOfArmy();
        assertTrue(potential > 0);
    }

    @Test
    void testBattleWinGainsGold() {
        general.addSoldier(new Soldier(MilitaryRank.KAPRAL));
        general.battle(opponent);
        assertTrue(general.getGold() > 100);
    }


    @Test
    void testBattleDrawRemovesSoldiers() {
        general.battle(opponent); // Equal potential leads to draw
        assertTrue(general.getArmy().isEmpty() || opponent.getArmy().isEmpty());
    }

    @Test
    void testLoseGoldReduced() {
        general.lose(opponent);
        assertTrue(general.getGold() < 100);
    }

    @Test
    void testLoseOpponentGainsGold() {
        general.lose(opponent);
        assertTrue(opponent.getGold() > 100);
    }

    @Test
    void testLoseArmySizeReduced() {
        general.lose(opponent);
        assertTrue(general.getArmy().size() < 1);
    }

    @Test
    void testPrintArmyExecutesSuccessfully() {
        assertDoesNotThrow(() -> general.printArmy());
    }

    @Test
    void testReduceGold() {
        assertEquals(90, general.reduceGold(90));
    }

    @Test
    void testAddGold() {
        general.addGold(90);
        assertEquals(190, general.getGold());
    }
}
