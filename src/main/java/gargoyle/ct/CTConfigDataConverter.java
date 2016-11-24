package gargoyle.ct;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CTConfigDataConverter implements Converter<long[]> {

    private static final String COMMENTS = "#;'/";

    private static final String UNIT_HOURS = "H";

    private static final String UNIT_MINUTES = "M";

    private static final String UNIT_SECONDS = "S";

    private static CTConfigDataConverter instance;

    private CTConfigDataConverter() {
    }

    public static synchronized CTConfigDataConverter getInstance() {
        if (instance == null) {
            instance = new CTConfigDataConverter();
        }
        return instance;
    }

    @Override
    public String format(TimeUnit unit, long... data) {
        String unitChar;
        switch (unit) {
            case HOURS:
                unitChar = UNIT_HOURS;
                break;
            case MINUTES:
                unitChar = UNIT_MINUTES;
                break;
            case SECONDS:
                unitChar = UNIT_SECONDS;
                break;
            default:
                throw new UnsupportedOperationException("Cannot parse line, invalid time unit " + unit.name());
        }
        return CTTimeUtil.fromMillis(unit, data[0]) + unitChar + "/" + CTTimeUtil.fromMillis(unit, data[1]) + unitChar +
            "/" + CTTimeUtil.fromMillis(unit, data[2]) + unitChar;
    }

    @Override
    public long[] parse(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Empty line");
        }
        String tline = line.trim();
        if (tline.isEmpty()) {
            throw new IllegalArgumentException("Empty line: " + line);
        }
        if (COMMENTS.contains(tline.substring(0, 1))) {
            throw new IllegalArgumentException("Commented line: " + line);
        }
        long[] data = new long[3];
        Pattern
            p =
            Pattern.compile("(?:([0-9]+)([a-zA-Z]+))/(?:([0-9]+)([a-zA-Z]+))[^a-zA-Z0-9]+(?:([0-9]+)([a-zA-Z]+))",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(tline);
        if (m.find()) {
            int groupCount = m.groupCount();
            if (groupCount != 6) {
                throw new IllegalArgumentException("Cannot parse line: " + line);// XXX
            }
            for (int g = 1; g <= groupCount; g += 2) {
                String q = m.group(g);
                String u = m.group(g + 1);
                TimeUnit unit;
                switch (u) {
                    case UNIT_HOURS:
                        unit = TimeUnit.HOURS;
                        break;
                    case UNIT_MINUTES:
                        unit = TimeUnit.MINUTES;
                        break;
                    case UNIT_SECONDS:
                        unit = TimeUnit.SECONDS;
                        break;
                    default:
                        throw new IllegalArgumentException("Cannot parse line: " + line + ", invalid time unit " + u);
                }
                try {
                    data[g / 2] = CTTimeUtil.toMillis(unit, Long.parseLong(q));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Cannot parse line: " + line, ex);
                }
            }
        } else {
            throw new IllegalArgumentException(line);
        }
        return data;
    }
}
