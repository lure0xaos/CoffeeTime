package gargoyle.ct.config.convert;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public interface CTTimeConverter<T> extends Serializable {
    String format(TimeUnit unit, T data);

    T parse(String data);
}
