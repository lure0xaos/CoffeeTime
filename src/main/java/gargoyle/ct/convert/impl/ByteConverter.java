package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class ByteConverter implements Converter<Byte> {
    @Override
    public String format(Byte data) {
        return String.valueOf(data);
    }

    @Override
    public Byte parse(String data) {
        return Byte.valueOf(data);
    }
}
