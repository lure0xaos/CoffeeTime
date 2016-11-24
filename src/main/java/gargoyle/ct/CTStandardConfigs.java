package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public class CTStandardConfigs extends CTConfigs {

    private static final long serialVersionUID = 5255223498123057991L;

    public CTStandardConfigs() {
        super(new CTConfig(TimeUnit.MINUTES, 120, 20, 3), new CTConfig(TimeUnit.MINUTES, 30, 5, 3),
            new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
    }
}
