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
import gargoyle.ct.resource.internal.LocalResource;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTimer;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.CTBlocker;
import gargoyle.ct.ui.CTControl;
import gargoyle.ct.ui.CTControlActions;
import gargoyle.ct.util.CTMutex;
import gargoyle.ct.util.CTTimeUtil;
import gargoyle.ct.util.CTUtil;
import gargoyle.ct.util.Log;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class CT implements CTApp {

    public static final String LOC_MESSAGES = "messages";
    private static final String SLASH = "/";
    private static final String CONFIG_NAME = "CT.cfg";
    private static final String DOT = ".";
    private static final String HELP_PAGE = "doc/help.html";
    private static final String HTML = "html";
    private static final String NOT_FOUND_0 = "Not found {0}";
    private static final String PAGE_0_NOT_FOUND = "Page {0} not found";
    private final List<CTBlocker> blockers;

    private final CTControlActions control;

    private final CTMessageProvider messages;

    private final TimeHelper timeHelper;

    private final CTTimer timer;
    private CTConfigResource configResource;

    private CT() {
        messages = new CTMessageProvider(LOC_MESSAGES);
        timeHelper = new CTTimeHelper();
        List<CTBlocker> pBlockers = CTBlocker.forAllDevices(this);
        blockers = pBlockers;
        List<CTTaskUpdatable> updatables = new ArrayList<>(pBlockers);
        CTControl pControl = new CTControl(this);
        control = pControl;
        updatables.add(pControl);
        timer = new CTTimer(timeHelper, updatables);
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
            Log.error("App already running");
            return;
        }
        setSystemLookAndFeel();
        CT app = new CT();
        if (args != null && args.length != 0) {
            try {
                long fakeTime = CTTimeUtil.parseHHMMSS(args[0]);
                app.setFakeTime(fakeTime);
            } catch (NumberFormatException e) {
                Log.info("fake time not set");
            }
        }
        for (CTBlocker blocker : app.blockers) {
            blocker.debug(debug);
        }
        app.start();
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static CTConfig showConfigDialog(Component owner, String title) {
        while (true) {
            try {
                JFormattedTextField formattedTextField = new JFormattedTextField(new MaskFormatter("##U/##U/##U"));
                int result = JOptionPane.showConfirmDialog(owner,
                        formattedTextField,
                        title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.CANCEL_OPTION) {
                    return null;
                }
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        return CTConfig.parse(String.valueOf(formattedTextField.getValue()));
                    } catch (IllegalArgumentException ex) {
                        Log.debug(ex, ex.getMessage());
                    }
                }
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    @Override
    public void arm(CTConfig config) {
        timer.arm(config, timeHelper.currentTimeMillis());
    }

    @Override
    public void exit() {
        for (CTBlocker blocker : blockers) {
            blocker.dispose();
        }
        timer.terminate();
        CTMutex.release();
    }

    @Override
    public CTConfigs loadConfigs(boolean reload) {
        CTConfigs configs;
        if (configResource == null || reload) {
            CTConfigResource configResource = CTConfigResource.findLocal(CONFIG_NAME);
            if (configResource != null && configResource.exists()) {
                try (InputStream stream = configResource.getInputStream()) {
                    configs = CTConfigs.parse(CTUtil.convertStreamToString(stream));
                    if (configs.getConfigs().isEmpty()) {
                        configs = new CTStandardConfigs();
                    }
                } catch (IOException ex) {
                    Log.error(ex, "Cannot load {0}", configResource);
                    configs = new CTStandardConfigs();
                }
            } else {
                if (configResource == null) {
                    Log.warn(NOT_FOUND_0, CONFIG_NAME);
                } else {
                    Log.warn(NOT_FOUND_0, configResource);
                }
                configs = new CTStandardConfigs();
                configResource = CTConfigResource.forURL(LocalResource.getHomeDirectoryLocation(), CONFIG_NAME);
                try (OutputStream stream = configResource.getOutputStream()) {
                    CTUtil.write(stream, configs.format());
                } catch (IOException ex) {
                    Log.warn(NOT_FOUND_0, configResource);
                }
            }
            this.configResource = configResource;
        } else {
            try (InputStream stream = configResource.getInputStream()) {
                configs = CTConfigs.parse(CTUtil.convertStreamToString(stream));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
            ClassLoader loader = CT.class.getClassLoader();
            Locale locale = Locale.getDefault();
            for (Resource resource : new Resource[]{
                    new ClasspathResource(loader, HELP_PAGE).forLocale(locale),
                    new ClasspathResource(loader, SLASH + HELP_PAGE).forLocale(locale)
            }) {
                if (resource != null && resource.exists()) {
                    try (InputStream stream = resource.getInputStream()) {
                        File tempFile = File.createTempFile(CT.class.getName(), DOT + HTML);
                        Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Desktop.getDesktop().browse(tempFile.toURI());
                        tempFile.deleteOnExit();
                    } catch (IOException ex) {
                        Log.error(ex, PAGE_0_NOT_FOUND, HELP_PAGE);
                    }
                    return;
                } else {
                    Log.debug(PAGE_0_NOT_FOUND, HELP_PAGE + ": " + (resource != null ? resource.getLocation() : null));
                }
            }
            Log.error(PAGE_0_NOT_FOUND, HELP_PAGE);
        }
    }

    @Override
    public CTConfig newConfig(Component owner, String title) {
        try {
            return showConfigDialog(owner, title);
        } catch (IllegalArgumentException e) {
            Log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void saveConfigs(CTConfigs configs) {
        if (configResource != null) {
            try (OutputStream stream = configResource.getOutputStream()) {
                CTUtil.write(stream, configs.format());
            } catch (IOException ex) {
                Log.warn(NOT_FOUND_0, configResource);
            }
        }
    }

    private void setFakeTime(long fakeTime) {
        timeHelper.setFakeTime(fakeTime);
    }

    private void start() {
        control.arm(loadConfigs(false).getConfigs().iterator().next());
    }

    @Override
    public void unarm() {
        timer.unarm();
    }
}
