package gargoyle.ct.config.convert;

import java.util.concurrent.TimeUnit;

public interface CTUnitConverter<T> {

    String format(TimeUnit unit, T data);

    /**
     * @throws IllegalArgumentException
     *         on parse
     */
    T parse(String data);
}
