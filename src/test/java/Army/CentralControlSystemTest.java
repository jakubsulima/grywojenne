package Army;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class CentralControlSystemTest {
    private CentralControlSystem centralControlSystem;


    @BeforeEach
    void setUp() {
        centralControlSystem = new CentralControlSystem();
    }

    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testGeneral1IsInitialized() {
        centralControlSystem.initializeGenerals("General A", "General B");
        assertNotNull(centralControlSystem.general1);
    }

    @Test
    void testGeneral2IsInitialized() {
        centralControlSystem.initializeGenerals("General A", "General B");
        assertNotNull(centralControlSystem.general2);
    }

    @Test
    void testGeneral1Name() {
        centralControlSystem.initializeGenerals("General A", "General B");
        assertEquals("General A", centralControlSystem.general1.getName());
    }

    @Test
    void testGeneral2Name() {
        centralControlSystem.initializeGenerals("General A", "General B");
        assertEquals("General B", centralControlSystem.general2.getName());
    }

    @Test
    void testRecruitSoldierAddsToArmy() {
        General general = new General("General A", 100);
        provideInput("1\n");
        centralControlSystem.recruitSoldier(general);
        assertEquals(2, general.getArmySize());
    }

    @Test
    void testRecruitSoldierDeductsGold() {
        General general = new General("General A", 100);
        provideInput("1\n");
        centralControlSystem.recruitSoldier(general);
        assertEquals(90, general.getGold()); // Assuming SZEREGOWY costs 10 gold
    }

    @Test
    void testRecruitSoldierFailsForInsufficientFunds() {
        General general = new General("General A", 5);
        provideInput("1\n");
        centralControlSystem.recruitSoldier(general);
        assertEquals(1, general.getArmySize());
    }

    @Test
    void testGoldUnchangedWhenRecruitmentFails() {
        General general = new General("General A", 5);
        provideInput("1\n");
        centralControlSystem.recruitSoldier(general);
        assertEquals(5, general.getGold()); // Shouldn't deduct gold if recruitment fails
    }

    @Test
    void testBattleReducesGeneral1ArmyPotential() {
        General general1 = new General("General A", 100);
        General general2 = new General("General B", 100);

        general1.recruitSoldier(MilitaryRank.SZEREGOWY);
        general2.recruitSoldier(MilitaryRank.SZEREGOWY);

        centralControlSystem.general1 = general1;
        centralControlSystem.general2 = general2;

        centralControlSystem.battle();
        assertTrue(general1.getPotentialOfArmy() < 100);
    }

    @Test
    void testBattleReducesGeneral2ArmyPotential() {
        General general1 = new General("General A", 100);
        General general2 = new General("General B", 100);

        general1.recruitSoldier(MilitaryRank.SZEREGOWY);
        general2.recruitSoldier(MilitaryRank.SZEREGOWY);

        centralControlSystem.general1 = general1;
        centralControlSystem.general2 = general2;

        centralControlSystem.battle();
        assertTrue(general2.getPotentialOfArmy() < 100);
    }

    @Test
    void testSaveGameCreatesFile() {
        provideInput("4\n");
        centralControlSystem.startGame(1, "General A", "General B", 1);
        File file = new File("savegame-turn1.dat");
        assertTrue(file.exists());
        file.delete(); // Clean up after the test
    }

    @Test
    void testLoadGameSucceeds() {
        provideInput("4\n");
        centralControlSystem.startGame(1, "General A", "General B", 1);
        provideInput("1\n");
        boolean loaded = centralControlSystem.loadGame();
        assertTrue(loaded);
    }

    @Test
    void testSwitchToNextGeneral() {
        centralControlSystem.general1 = new General("General A", 100);
        centralControlSystem.general2 = new General("General B", 100);
        centralControlSystem.currentGeneral = centralControlSystem.general1;

        centralControlSystem.switchToNextGeneral();
        assertEquals(centralControlSystem.general2, centralControlSystem.currentGeneral);
    }

    @Test
    void testGameIsRunningInitially() {
        assertTrue(centralControlSystem.isGameRunning());
    }

    @Test
    void testExitGameStopsRunning() {
        centralControlSystem.exitGame();
        assertFalse(centralControlSystem.isGameRunning());
    }
}
