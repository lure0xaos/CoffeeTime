package gargoyle.ct.cmd;

public interface CTCmd extends CTAnyCmd {

    long getFakeTime();

    boolean isDebug();
}
