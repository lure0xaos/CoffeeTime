package gargoyle.ct.config.convert;

import java.util.concurrent.TimeUnit;

public interface Converter<T> {
    String format(TimeUnit unit, T data);

    T parse(String data);
}