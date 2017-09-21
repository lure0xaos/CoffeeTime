package gargoyle.ct.util;

import gargoyle.ct.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public final class CTStreamUtil {
    private static final String DELIMITER = "\\A";

    private CTStreamUtil() {
    }

    public static String convertStreamToString(InputStream is, String charsetName) {
        try (Scanner scanner = new Scanner(is, charsetName); Scanner s = scanner.useDelimiter(DELIMITER)) {
            return s.hasNext() ? s.next() : "";
        } catch (RuntimeException ex) {
            Log.error(ex, ex.getMessage());
            throw ex;
        }
    }

    public static void write(OutputStream stream, String content, String charsetName) {
        try (Writer writer = new OutputStreamWriter(stream, charsetName)) {
            writer.write(content);
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
