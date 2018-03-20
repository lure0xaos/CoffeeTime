package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class ByteConverter implements Converter<Byte> {
    @Override
    public String format(Byte data) {
        return String.valueOf(data);
    }

    @Override
    public Byte parse(@NotNull String data) {
        return Byte.valueOf(data);
    }
}
