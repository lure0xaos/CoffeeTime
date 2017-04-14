package gargoyle.ct.pref.impl.convert;

public interface Converter<T> {

    String format(T data);

    /**
     * @throws IllegalArgumentException
     *         on parse
     */
    T parse(String data);
}
