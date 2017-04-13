package gargoyle.ct.pref.impl.convert;

public interface Converter<T> {
    String format(T data);

    T parse(String data);
}
