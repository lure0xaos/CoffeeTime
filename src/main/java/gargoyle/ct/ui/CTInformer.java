package gargoyle.ct.ui;

import java.awt.Color;

@FunctionalInterface
public interface CTInformer {
    void showText(Color foreground, String text);
}
