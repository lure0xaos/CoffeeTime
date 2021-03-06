package gargoyle.ct.convert;

public interface Converter<T> {
    String format(T data);

    /**
     * @throws IllegalArgumentException on parse
     */
    T parse(String data);
}
