package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.CTStandardConfigs;
import gargoyle.ct.helper.CTTimeHelper;
import gargoyle.ct.helper.TimeHelper;
import gargoyle.ct.messages.impl.CTMessageProvider;
import gargoyle.ct.resource.Resource;
import gargoyle.ct.resource.impl.CTConfigResource;
import gargoyle.ct.resource.internal.ClasspathResource;
import gargoyle.ct.task.impl.CTTimer;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.CTBlocker;
import gargoyle.ct.ui.CTControl;
import gargoyle.ct.ui.CTControlActions;
import gargoyle.ct.util.CTMutex;
import gargoyle.ct.util.CTTimeUtil;
import gargoyle.ct.util.CTUtil;
import gargoyle.ct.util.Log;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class CT implements CTApp {

    public static final String LOC_MESSAGES = "messages";

    private static final String CONFIG_NAME = "CT.cfg";

    private static final String DOT = ".";

    private static final String HELP_PAGE = "/doc/help.html";

    private static final String HTML = "html";

    private static final String NOT_FOUND_0 = "Not found {0}";

    private static final String PAGE_0_NOT_FOUND = "Page {0} not found";

    private final CTBlocker blocker;

    private final CTControlActions control;

    private final CTMessageProvider messages;

    private final TimeHelper timeHelper;

    private final CTTimer timer;

    private CT() {
        messages = new CTMessageProvider(LOC_MESSAGES);
        timeHelper = new CTTimeHelper();
        CTBlocker pBlocker = new CTBlocker(this);
        CTControl pControl = new CTControl(this);
        timer = new CTTimer(timeHelper, pBlocker, pControl);
        blocker = pBlocker;
        control = pControl;
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void main(String[] args) {
        boolean debug = false;
        if (args != null) {
            if (args.length == 1) {
                debug = true;
            }
            if (args.length == 2) {
                debug = Boolean.parseBoolean(args[1]);
            }
        }
        if (!debug && !CTMutex.acquire()) {
            Log.error("App already running"); //NON-NLS
            return;
        }
        setSystemLookAndFeel();
        CT app = new CT();
        if (args != null && args.length != 0) {
            try {
                long fakeTime = CTTimeUtil.parseHHMMSS(args[0]);
                app.setFakeTime(fakeTime);
            } catch (NumberFormatException e) {
                Log.info("fake time not set"); //NON-NLS
            }
        }
        app.blocker.debug(debug);
        app.start();
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void arm(CTConfig config) {
        timer.arm(config, timeHelper.currentTimeMillis());
    }

    @Override
    public void exit() {
        blocker.dispose();
        timer.terminate();
        CTMutex.release();
    }

    @Override
    public CTConfigs getConfigs() {
        CTConfigs configs;
        CTConfigResource configResource = CTConfigResource.findLocal(CONFIG_NAME);
        if (configResource != null && configResource.exists()) {
            try (InputStream stream = configResource.getInputStream()) {
                configs = CTConfigs.parse(CTUtil.convertStreamToString(stream));
                if (configs.getConfigs().isEmpty()) {
                    configs = new CTStandardConfigs();
                }
            } catch (IOException ex) {
                Log.error(ex, "Cannot load {0}", configResource); //NON-NLS
                configs = new CTStandardConfigs();
            }
        } else {
            if (configResource == null) {
                Log.warn(NOT_FOUND_0, CONFIG_NAME); //NON-NLS
            } else {
                Log.warn(NOT_FOUND_0, configResource); //NON-NLS
            }
            configs = new CTStandardConfigs();
        }
        return configs;
    }

    @Override
    public String getMessage(String message, Object... args) {
        return messages.getMessage(message, args);
    }

    @Override
    public void help() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
            Resource resource = new ClasspathResource(HELP_PAGE);
            if (resource.exists()) {
                try (InputStream stream = resource.getInputStream()) {
                    File tempFile = File.createTempFile(CT.class.getName(), DOT + HTML);
                    Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Desktop.getDesktop().browse(tempFile.toURI());
                } catch (IOException ex) {
                    Log.error(ex, PAGE_0_NOT_FOUND, HELP_PAGE); //NON-NLS
                }
            } else {
                Log.error(PAGE_0_NOT_FOUND, HELP_PAGE); //NON-NLS
            }
        }
    }

    private void setFakeTime(long fakeTime) {
        timeHelper.setFakeTime(fakeTime);
    }

    private void start() {
        control.arm(getConfigs().getConfigs().iterator().next());
    }

    @Override
    public void unarm() {
        timer.unarm();
    }
}
