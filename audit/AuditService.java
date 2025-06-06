package audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    private static final String FILE_PATH = "audit_log.csv";

    public static void log(String actionName) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(actionName)
                    .append(",")
                    .append(LocalDateTime.now().toString())
                    .append("\n");
        } catch (IOException e) {
            System.err.println("Audit failed: " + e.getMessage());
        }
    }
}
