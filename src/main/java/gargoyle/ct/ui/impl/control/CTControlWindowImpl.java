package gargoyle.ct.ui.impl.control;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.ui.CTControlActions;
import gargoyle.ct.ui.CTControlWindow;
import gargoyle.ct.ui.util.CTDragHelper;
import gargoyle.ct.util.CTTimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.prefs.PreferenceChangeEvent;

public final class CTControlWindowImpl extends JWindow implements CTControlWindow {
    private static final String MSG_TOOLTIP_ERROR = "tooltip error";
    private static final String MSG_TRANSPARENCY_NOT_SUPPORTED = "transparency not supported";
    private static final int SNAP = 20;
    private static final String TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE = "ToolTipManager.enableToolTipMode";
    private static final long serialVersionUID = 1L;
    private final transient CTControlActions app;
    private final JLabel label;
    private volatile boolean live = true;
    private volatile boolean reshow;

    public CTControlWindowImpl(Frame owner, CTControlActions app, URL imageURL, JPopupMenu menu) {
        super(owner);
        this.app = app;
        if (imageURL == null) {
            throw new IllegalArgumentException("image not found");
        }
        UIManager.getDefaults().put(TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE, "");
        setAlwaysOnTop(true);
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        label = new JLabel(new ImageIcon(imageURL));
        pane.add(label, BorderLayout.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pack();
        setComponentPopupMenu(menu);
        Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
        setLocation(screenSize.width - getWidth(), screenSize.height - getHeight());
        getOwner().setLocation(getLocation());
        CTDragHelper.makeDraggable(label, SNAP);
        CTDragHelper.makeDraggable(this, SNAP);
        ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.setDismissDelay(1000);
        toolTipManager.setInitialDelay(100);
        toolTipManager.setReshowDelay(100);
        toolTipManager.setEnabled(true);
        toolTipManager.setLightWeightPopupEnabled(true);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                onMouseMoved(true, false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                onMouseMoved(false, true);
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    void onMouseMoved(boolean reshow, boolean transparency) {
        this.reshow = reshow;
        transparency(transparency);
    }

    private void transparency(boolean transparent) {
        CTPreferences preferences = app.preferences();
        try {
            setOpacity(preferences.isTransparency() && transparent ? preferences.getTransparencyLevel() : 1);
        } catch (UnsupportedOperationException e) {
            Log.warn(e, MSG_TRANSPARENCY_NOT_SUPPORTED);
        }
    }

    private void setComponentPopupMenu(JPopupMenu menu) {
        label.setComponentPopupMenu(menu);
    }

    @Override
    public void destroy() {
        setVisible(false);
        if (live) {
            live = false;
            dispose();
            getOwner().dispose();
            live = true;
        }
    }

    @Override
    public void showMe() {
        setVisible(true);
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        String key = evt.getKey();
        if (CTPreferences.PREF_TRANSPARENCY.equals(key) || CTPreferences.PREF_TRANSPARENCY_LEVEL.equals(key)) {
            transparency(true);
        }
    }

    @Override
    public void setToolTipText(String text) {
        label.setToolTipText(text);
        if (reshow && text != null && !text.isEmpty()) {
            try {
                ToolTipManager.sharedInstance()
                        .mouseMoved(
                                new MouseEvent(label, MouseEvent.MOUSE_MOVED, CTTimeUtil.currentTimeMillis(), 0, getWidth(),
                                        getHeight(), 0, false));
            } catch (RuntimeException ex) {
                Log.debug(ex, MSG_TOOLTIP_ERROR);
            }
        }
    }
}
