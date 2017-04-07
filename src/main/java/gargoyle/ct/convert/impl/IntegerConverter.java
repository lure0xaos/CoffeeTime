package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class IntegerConverter implements Converter<Integer> {
    private static final long serialVersionUID = -7691080476504470997L;

    @Override
    public String format(Integer data) {
        return String.valueOf(data);
    }

    @Override
    public Integer parse(String data) {
        return Integer.valueOf(data);
    }
}
