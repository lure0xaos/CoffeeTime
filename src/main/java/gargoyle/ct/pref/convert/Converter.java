package gargoyle.ct.pref.convert;

public interface Converter<T> {
    String format(T data);

    T parse(String data);
}
