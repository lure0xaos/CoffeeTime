package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.CTStandardConfigs;
import gargoyle.ct.config.convert.CTUnitConverter;
import gargoyle.ct.config.convert.impl.CTConfigsConverter;
import gargoyle.ct.log.Log;
import gargoyle.ct.mutex.CTMutex;
import gargoyle.ct.mutex.impl.FileMutex;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.CTPreferencesImpl;
import gargoyle.ct.prop.impl.CTPropertyChangeManager;
import gargoyle.ct.resource.Resource;
import gargoyle.ct.resource.impl.CTConfigResource;
import gargoyle.ct.resource.internal.ClasspathResource;
import gargoyle.ct.resource.internal.LocalResource;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.helper.CTTimeHelper;
import gargoyle.ct.task.helper.impl.CTTimeHelperImpl;
import gargoyle.ct.task.impl.CTTimer;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.impl.CTBlocker;
import gargoyle.ct.ui.impl.CTControl;
import gargoyle.ct.ui.impl.CTNewConfigDialog;
import gargoyle.ct.ui.impl.CTPreferencesDialog;
import gargoyle.ct.ui.impl.control.CTShowingFrame;
import gargoyle.ct.util.CTStreamUtil;
import gargoyle.ct.util.CTTimeUtil;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class CT implements CTApp {

    private static final String CONFIG_NAME           = "CT.cfg";
    private static final String DOT                   = ".";
    private static final String HELP_PAGE             = "html/help.html";
    private static final String HTML                  = "html";
    private static final String MSG_ALREADY_RUNNING   = "App already running";
    private static final String MSG_CANNOT_LOAD_0     = "Cannot load {0}";
    private static final String MSG_FAKE_TIME_INVALID = "fake time not set";
    private static final String NOT_FOUND_0           = "Not found {0}";
    private static final String PAGE_0_NOT_FOUND      = "Page {0} not found";
    private static final String SLASH                 = "/";
    private static final String URL_ICON_BIG          = "/icon/64/icon64.png";
    private static final String URL_ICON_MEDIUM       = "/icon/32/icon32.png";
    private static final String URL_ICON_SMALL        = "/icon/16/icon16.png";
    private static final String CONFIG_CHARSET        = StandardCharsets.UTF_8.name();
    private static CTMutex         mutex;
    private final  List<CTBlocker> blockers;
    private final CTUnitConverter<CTConfigs> configsConverter = new CTConfigsConverter();
    private final CTControl           control;
    private final Frame               owner;
    private final CTPreferences       preferences;
    private final CTTimeHelper        timeHelper;
    private final CTTimer             timer;
    private       Resource            configResource;
    private       CTPreferencesDialog preferencesDialog;

    private CT() {
        CTPreferences preferences = new CTPreferencesImpl(CT.class);
        this.preferences = preferences;
        CTTimeHelper timeHelper = new CTTimeHelperImpl();
        this.timeHelper = timeHelper;
        List<CTBlocker> blockers = CTBlocker.forAllDevices(this.preferences);
        this.blockers = blockers;
        List<CTTaskUpdatable> updatables = new ArrayList<>(blockers);
        owner = new CTShowingFrame();
        CTControl control = new CTControl(this, owner);
        this.control = control;
        preferences.addPropertyChangeListener(control);
        updatables.add(control);
        timer = new CTTimer(timeHelper, updatables);
    }

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
        mutex = new FileMutex(CT.class.getSimpleName());
        if (/*!debug && */!mutex.acquire()) {
            Log.error(MSG_ALREADY_RUNNING);
            return;
        }
        setSystemLookAndFeel();
        CT app = new CT();
        if (args != null && args.length != 0) {
            try {
                long fakeTime = CTTimeUtil.parseHHMMSS(args[0]);
                app.setFakeTime(fakeTime);
            } catch (NumberFormatException ex) {
                Log.info(MSG_FAKE_TIME_INVALID);
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
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void setFakeTime(long fakeTime) {
        timeHelper.setFakeTime(fakeTime);
    }

    private void start() {
        control.arm(loadConfigs(false).getConfigs().iterator().next());
    }

    @Override
    public URL getBigIcon() {
        return CT.class.getResource(URL_ICON_BIG);
    }

    @Override
    public URL getMediumIcon() {
        return CT.class.getResource(URL_ICON_MEDIUM);
    }

    @Override
    public URL getSmallIcon() {
        return CT.class.getResource(URL_ICON_SMALL);
    }

    @Override
    public CTPreferences getPreferences() {
        return preferences;
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
        mutex.release();
        preferences.removePropertyChangeListener(control);
        CTPropertyChangeManager.getInstance().removePropertyChangeListeners();
    }

    @Override
    public void help() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
            ClassLoader loader = CT.class.getClassLoader();
            Locale      locale = Locale.getDefault();
            //noinspection StringConcatenation
            for (Resource resource : new Resource[]{new ClasspathResource(loader, HELP_PAGE).forLocale(locale),
                                                    new ClasspathResource(loader,
                                                                          SLASH + HELP_PAGE).forLocale(locale)}) {
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
                    //noinspection StringConcatenation
                    Log.debug(PAGE_0_NOT_FOUND, HELP_PAGE + ": " + (resource != null ? resource.getLocation() : null));
                }
            }
            Log.error(PAGE_0_NOT_FOUND, HELP_PAGE);
        }
    }

    @Override
    public CTConfigs loadConfigs(boolean reload) {
        CTConfigs configs;
        if (configResource == null || reload) {
            CTConfigResource configResource = CTConfigResource.findLocalConfig(CONFIG_NAME);
            if (configResource != null && configResource.exists()) {
                try (InputStream stream = configResource.getInputStream()) {
                    configs = configsConverter.parse(CTStreamUtil.convertStreamToString(stream, CONFIG_CHARSET));
                    if (configs.getConfigs().isEmpty()) {
                        configs = new CTStandardConfigs();
                    }
                } catch (IOException ex) {
                    Log.error(ex, MSG_CANNOT_LOAD_0, configResource);
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
                saveConfigs(configs, configResource);
            }
            this.configResource = configResource;
        } else {
            try (InputStream stream = configResource.getInputStream()) {
                configs = configsConverter.parse(CTStreamUtil.convertStreamToString(stream, CONFIG_CHARSET));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return configs;
    }

    @Override
    public void saveConfigs(CTConfigs configs) {
        saveConfigs(configs, configResource);
    }

    @Override
    public CTConfig showNewConfig() {
        try {
            return new CTNewConfigDialog(this, owner).showMe();
        } catch (IllegalArgumentException ex) {
            Log.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public void showPreferences() {
        if (preferencesDialog == null) {
            preferencesDialog = new CTPreferencesDialog(this, owner);
        }
        preferencesDialog.showMe();
    }

    @Override
    public void unarm() {
        timer.unarm();
    }

    private void saveConfigs(CTConfigs configs, Resource configResource) {
        if (configResource != null) {
            try (OutputStream stream = configResource.getOutputStream()) {
                CTStreamUtil.write(stream, configsConverter.format(TimeUnit.MINUTES, configs), CONFIG_CHARSET);
            } catch (IOException ex) {
                Log.warn(NOT_FOUND_0, configResource);
            }
        }
    }
}
