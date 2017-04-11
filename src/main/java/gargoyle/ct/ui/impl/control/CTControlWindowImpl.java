package gargoyle.ct.ui.impl.control;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.CTPropertyChangeEvent;
import gargoyle.ct.ui.CTBlockerTextProvider;
import gargoyle.ct.ui.CTControlWindow;
import gargoyle.ct.ui.impl.CTBlockerContent;
import gargoyle.ct.ui.util.CTDragHelper;
import gargoyle.ct.util.CTTimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public final class CTControlWindowImpl extends JWindow implements CTControlWindow {
    private static final String MSG_TOOLTIP_ERROR = "tooltip error";
    private static final String MSG_TRANSPARENCY_NOT_SUPPORTED = "transparency not supported";
    private static final int SNAP = 20;
    private static final String TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE = "ToolTipManager.enableToolTipMode";
    private static final long serialVersionUID = 6345130901927558555L;
    private final CTIconContent iconContent;
    private final transient CTPreferences preferences;
    private final CTBlockerContent textContent;
    private boolean iconMode = true;
    private volatile boolean live = true;
    private volatile boolean reshow;

    public CTControlWindowImpl(Frame owner, CTPreferences preferences, URL imageURL, JPopupMenu menu) {
        super(owner);
        UIManager.getDefaults().put(TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE, "");
        setAlwaysOnTop(true);
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        textContent = new CTBlockerContent(new CTBlockerTextProvider(preferences), false);
        iconContent = new CTIconContent(imageURL);
        showIconContent();
        pack();
        setComponentPopupMenu(menu);
        Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
        setLocation(screenSize.width - getWidth(), screenSize.height - getHeight());
        getOwner().setLocation(getLocation());
        CTDragHelper.makeDraggable(textContent, SNAP);
        CTDragHelper.makeDraggable(iconContent, SNAP);
        CTDragHelper.makeDraggable(this, SNAP);
        ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        toolTipManager.setDismissDelay(1000);
        toolTipManager.setInitialDelay(100);
        toolTipManager.setReshowDelay(100);
        toolTipManager.setEnabled(true);
        toolTipManager.setLightWeightPopupEnabled(true);
        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                onMouseMoved(true, false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                onMouseMoved(false, true);
            }
        };
        textContent.addMouseListener(l);
        iconContent.addMouseListener(l);
        this.preferences = preferences;
    }

    private void showIconContent() {
        Container pane = getContentPane();
        pane.remove(textContent);
        pane.add(iconContent, BorderLayout.CENTER);
        iconContent.repaint();
    }

    @SuppressWarnings("WeakerAccess")
    void onMouseMoved(boolean reshow, boolean transparency) {
        this.reshow = reshow;
        transparency(transparency);
    }

    private void transparency(boolean transparent) {
        CTPreferences preferences = this.preferences;
        try {
            int oldOpacity = (int) (getOpacity() * 100);
            int newOpacity = (iconMode && preferences.transparency().get() && transparent ? preferences.transparencyLevel().get() : 100);
            if (oldOpacity == newOpacity) {
                return;
            }
            setOpacity(newOpacity / 100.0f);
        } catch (UnsupportedOperationException ex) {
            Log.warn(ex, MSG_TRANSPARENCY_NOT_SUPPORTED);
        }
    }

    private void setComponentPopupMenu(JPopupMenu menu) {
        textContent.setComponentPopupMenu(menu);
        iconContent.setComponentPopupMenu(menu);
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
    public void onPropertyChange(CTPropertyChangeEvent event) {
        String key = event.getName();
        if (CTPreferences.TRANSPARENCY.equals(key) || CTPreferences.TRANSPARENCY_LEVEL.equals(key)) {
            transparency(true);
        }
    }

    @Override
    public void setTextMode(boolean textMode) {
        boolean oldIconMode = iconMode;
        boolean newIconMode = !textMode;
        if (oldIconMode == newIconMode) {
            return;
        }
        iconMode = newIconMode;
        transparency(true);
        if (textMode && !preferences.block().get()) {
            showTextContent();
        } else {
            showIconContent();
        }
    }

    private void showTextContent() {
        Container pane = getContentPane();
        pane.remove(iconContent);
        pane.add(textContent, BorderLayout.CENTER);
        textContent.repaint();
    }

    @Override
    public void setToolTipText(String text) {
        textContent.setToolTipText(text);
        iconContent.setToolTipText(text);
        if (reshow && text != null && !text.isEmpty()) {
            try {
                ToolTipManager.sharedInstance()
                        .mouseMoved(
                                new MouseEvent(textContent, MouseEvent.MOUSE_MOVED, CTTimeUtil.currentTimeMillis(), 0, getWidth(),
                                        getHeight(), 0, false));
                ToolTipManager.sharedInstance()
                        .mouseMoved(
                                new MouseEvent(iconContent, MouseEvent.MOUSE_MOVED, CTTimeUtil.currentTimeMillis(), 0, getWidth(),
                                        getHeight(), 0, false));
            } catch (RuntimeException ex) {
                Log.debug(ex, MSG_TOOLTIP_ERROR);
            }
        }
    }

    @Override
    public void showText(Color foreground, String text) {
        textContent.showText(foreground, text);
        textContent.repaint();
    }
}
