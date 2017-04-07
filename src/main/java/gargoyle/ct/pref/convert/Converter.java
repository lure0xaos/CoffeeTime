package gargoyle.ct.pref.convert;

import java.io.Serializable;

public interface Converter<T> extends Serializable {
    String format(T data);

    T parse(String data);
}
