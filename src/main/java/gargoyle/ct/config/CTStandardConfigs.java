package gargoyle.ct.config;

import java.util.concurrent.TimeUnit;

public class CTStandardConfigs extends CTConfigs {

    public static final  int  BLOCK_10M        = 10;
    public static final  int  BLOCK_20M        = 20;
    public static final  int  BLOCK_5M         = 5;
    public static final  int  WARN_3M          = 3;
    public static final  int  WHOLE_1H         = 60;
    public static final  int  WHOLE_2H         = 120;
    public static final  int  WHOLE_30M        = 30;
    private static final long serialVersionUID = -2778482225848242583L;

    public CTStandardConfigs() {
        super(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M),
              new CTConfig(TimeUnit.MINUTES, WHOLE_30M, BLOCK_5M, WARN_3M),
              new CTConfig(TimeUnit.MINUTES, WHOLE_2H, BLOCK_20M, WARN_3M));
    }
}
