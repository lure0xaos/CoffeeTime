package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public class BytesConverter implements Converter<byte[]> {
    @Override
    public String format(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    @Override
    public byte[] parse(@NotNull String data) {
        return Base64.getDecoder().decode(data);
    }
}
