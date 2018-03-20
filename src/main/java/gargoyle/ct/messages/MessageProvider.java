package gargoyle.ct.messages;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface MessageProvider {
    @Nullable String getMessage(String message, Object... args);
}
