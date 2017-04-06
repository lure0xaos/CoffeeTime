package gargoyle.ct.util;

import gargoyle.ct.log.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class CTStreamUtil {
    private static final String DELIMITER = "\\A";

    private CTStreamUtil() {
    }

    public static String convertStreamToString(InputStream is) {
        try (Scanner scanner = new Scanner(is, StandardCharsets.US_ASCII.name());
             final Scanner s = scanner.useDelimiter(DELIMITER)) {
            return s.hasNext() ? s.next() : "";
        } catch (RuntimeException ex) {
            Log.error(ex, ex.getMessage());
            throw ex;
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
