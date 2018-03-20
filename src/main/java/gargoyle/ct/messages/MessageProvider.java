package gargoyle.ct.messages;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageProvider {
    @NotNull String getMessage(String message, Object... args);
}
