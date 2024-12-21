package Army;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    private Logger logger;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        logger = new Logger();
        System.setOut(new PrintStream(outputStream)); // Redirect system output for test verification
    }

    @Test
    void testLogAction() {
        String action = "General recruited a soldier.";
        logger.logAction(action);

        String logsOutput = outputStream.toString().trim();
        assertTrue(logsOutput.contains("Logged action: " + action));
    }

    @Test
    void testGenerateTurnReport() {
        String action1 = "General recruited a soldier.";
        String action2 = "General engaged in battle.";

        logger.logAction(action1);
        logger.logAction(action2);
        outputStream.reset(); // Clear output stream for capturing report

        logger.generateTurnReport();

        String reportOutput = outputStream.toString().trim();
        assertTrue(reportOutput.contains("===== Turn Report ====="));
        assertTrue(reportOutput.contains(action1));
        assertTrue(reportOutput.contains(action2));
        assertTrue(reportOutput.contains("========================"));
    }

    @Test
    void testEmptyLogReport() {
        logger.generateTurnReport();
        String reportOutput = outputStream.toString().trim();

        assertTrue(reportOutput.contains("===== Turn Report ====="));
        assertTrue(reportOutput.contains("========================"));
        assertFalse(reportOutput.contains("Logged action:"));
    }
}
