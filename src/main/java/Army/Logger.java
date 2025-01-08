package Army;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Logger implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> logs;

    public Logger() {
        this.logs = new ArrayList<>();
    }

    public void logAction(String action) {
        String timestamp = LocalDateTime.now().toString();
        logs.add(timestamp + ": " + action);
        System.out.println("Logged action: " + action);
    }

    public void generateTurnReport() {
        System.out.println("\n===== Turn Report =====");
        for (String log : logs) {
            System.out.println(log);
        }
        System.out.println("========================\n");
    }

    public void printLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }
}

