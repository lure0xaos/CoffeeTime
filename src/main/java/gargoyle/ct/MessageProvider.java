package gargoyle.ct;

@FunctionalInterface
public interface MessageProvider {

    String getMessage(String message, Object... args);
}
