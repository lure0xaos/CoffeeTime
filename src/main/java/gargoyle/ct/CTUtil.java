package gargoyle.ct;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class CTUtil {

    private CTUtil() {
    }

    public static String convertStreamToString(InputStream is) {
        try (Scanner scanner = new Scanner(is, StandardCharsets.US_ASCII.name());
             final Scanner s = scanner.useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
