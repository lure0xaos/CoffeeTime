package gargoyle.ct.messages;

@FunctionalInterface
public interface MessageProvider {

    String getMessage(String message, Object... args);
}
