package gargoyle.ct.ui;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface CTDialog<R> {
    @Nullable R showMe();
}
