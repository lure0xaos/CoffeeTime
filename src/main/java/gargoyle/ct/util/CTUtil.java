package gargoyle.ct.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class CTUtil {
    private static final String DELIMITER = "\\A";

    private CTUtil() {
    }

    public static String convertStreamToString(InputStream is) {
        try (Scanner scanner = new Scanner(is, StandardCharsets.US_ASCII.name());
             final Scanner s = scanner.useDelimiter(DELIMITER)) {
            return s.hasNext() ? s.next() : "";
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void write(OutputStream stream, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, StandardCharsets.US_ASCII))) {
            writer.write(content);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
