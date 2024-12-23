package Army;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecretaryTest {

    @Test
    void testDisplayGeneralsState() {
        // Set up the Secretary, Generals, and their armies
        Secretary secretary = new Secretary();
        General general1 = new General("Alexander", 100);
        General general2 = new General("Napoleon", 120);


        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the method to test
        secretary.displayGeneralsState(general1, general2);

        // Retrieve the printed output
        String actualOutput = outputStream.toString().trim();

        // Expected output
        String expectedOutput = """
                ===== Generals' State =====
                General 1: Alexander
                Gold: 100
                Army Potential: 1
                Army Composition:
                SZEREGOWY (Value: 1, Experience: 1)
                --------------------------
                General 2: Napoleon
                Gold: 120
                Army Potential: 1
                Army Composition:
                SZEREGOWY (Value: 1, Experience: 1)
                ==========================
                """;

        // Verify the entire output
        assertEquals(expectedOutput.stripTrailing(), actualOutput.stripTrailing());
    }
}
