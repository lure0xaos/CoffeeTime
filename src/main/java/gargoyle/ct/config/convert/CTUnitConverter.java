package gargoyle.ct.config.convert;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface CTUnitConverter<T> {
    @NotNull String format(TimeUnit unit, T data);

    /**
     * @throws IllegalArgumentException on parse
     */
    @NotNull T parse(String data);
}
