package gargoyle.ct.pref.impl.convert.impl;

import gargoyle.ct.pref.impl.convert.Converter;
import gargoyle.ct.log.Log;

public class EnumConverter<E extends Enum<E>> implements Converter<E> {
    private final Class<E> type;

    public EnumConverter(Class<E> type) {
        this.type = type;
    }

    @Override
    public String format(E data) {
        return data.name();
    }

    @Override
    public E parse(String data) {
        try {
            return Enum.valueOf(type, data);
        } catch (IllegalArgumentException ex) {
            Log.error(ex,ex.getMessage());
//            throw new ParseException(ex.getMessage(),0);
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
