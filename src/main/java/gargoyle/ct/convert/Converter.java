package gargoyle.ct.convert;

public interface Converter<T> {
    String format(T data);

    T parse(String data);
}
