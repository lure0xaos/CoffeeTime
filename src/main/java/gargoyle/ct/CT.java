package gargoyle.ct;

import javax.swing.*;
import java.awt.*;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CT implements CTApp {
    public static final String DOT = ".";
    public static final String HTML = "html";
    public static final String HELP_PAGE = "/doc/help.html";
    public static final String LOC_MESSAGES = "messages";
    public static final String CONFIG_NAME = "CT.cfg";

    public static void main(final String[] args) {
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
            Log.error("App already running");
            return;
        }
        CT.setSystemLookAndFeel();
        final CT app = new CT();
        if ((args != null) && (args.length != 0)) {
            final long fakeTime = CTTimeUtil.parseHHMMSS(args[0]);
            app.setFakeTime(fakeTime);
        }
        app.blocker.debug(debug);
        app.start();
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (final InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (final IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (final UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }

    private final CTBlocker blocker;
    private final CTControlActions control;
    private final TimeHelper timeHelper;
    private final CTTimer timer;
    private final CTMessageProvider messages;

    private CT() {
        this.messages = new CTMessageProvider(CT.LOC_MESSAGES);
        this.timeHelper = new CTTimeHelper();
        final CTBlocker pBlocker = new CTBlocker(this);
        final CTControl pControl = new CTControl(this);
        this.timer = new CTTimer(this.timeHelper, pBlocker, pControl);
        this.blocker = pBlocker;
        this.control = pControl;
    }

    @Override
    public void arm(final CTConfig config) {
        this.timer.arm(config, this.timeHelper.currentTimeMillis());
    }

    @Override
    public void exit() {
        this.blocker.dispose();
        this.timer.terminate();
        CTMutex.release();
    }

    @Override
    public CTConfigs getConfigs() {
        CTConfigs configs;
        final CTConfigResource configResource = CTConfigResource.findLocal(CT.CONFIG_NAME);
        if (configResource != null && configResource.exists()) {
            try (InputStream stream = (configResource.getInputStream())) {
                configs = CTConfigs.parse(CTUtil.convertStreamToString(stream));
                if (configs.getConfigs().isEmpty()) {
                    configs = new CTStandardConfigs();
                }
            } catch (final IOException ex) {
                Log.error(ex, "Cannot load {0}", configResource);
                configs = new CTStandardConfigs();
            }
        } else {
            Log.warn("Not found {0}", configResource);
            configs = new CTStandardConfigs();
        }
        return configs;
    }

    @Override
    public String getMessage(final String message, final Object... args) {
        return this.messages.getMessage(message, args);
    }

    @Override
    public void help() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
            final Resource resource = new ClasspathResource(CT.HELP_PAGE);
            if (resource.exists()) {
                try (InputStream stream = resource.getInputStream()) {
                    final File tempFile = File.createTempFile(CT.class.getName(), CT.DOT + CT.HTML);
                    Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Desktop.getDesktop().browse(tempFile.toURI());
                } catch (final IOException ex) {
                    Log.error(ex, "Page {0} not found", CT.HELP_PAGE);
                }
            } else {
                Log.error("Page {0} not found", CT.HELP_PAGE);
            }
        }
    }

    private void setFakeTime(final long fakeTime) {
        this.timeHelper.setFakeTime(fakeTime);
    }

    private void start() {
        this.control.arm(this.getConfigs().getConfigs().iterator().next());
    }

    @Override
    public void unarm() {
        this.timer.unarm();
    }
}
