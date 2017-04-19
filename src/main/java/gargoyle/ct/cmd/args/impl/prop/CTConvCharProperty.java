package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.CharConverter;

public class CTConvCharProperty extends CTConvProperty<Character> {

    public CTConvCharProperty(String name, String value) {
        super(new CharConverter(), name, value);
    }
}
