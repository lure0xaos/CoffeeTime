package gargoyle.ct.ui.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.CTDialog;
import gargoyle.ct.ui.util.CTDragHelper;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public final class CTAboutDialog extends JDialog implements CTDialog<Void> {
    private static final float BIG = 1.2f;
    private static final String FORMAT = "%s:";
    private static final String LOC_ABOUT = "messages/about";
    private static final String LOC_VERSION = "messages/version";
    private static final float SMALL = 0.8f;
    private static final long serialVersionUID = 4721343233167467386L;
    private final transient CTMessages about;
    private final Window owner;
    private final transient CTMessages version;

    public CTAboutDialog(CTApp app, Window owner) {
        super(owner.getOwner(), ModalityType.APPLICATION_MODAL);
        this.owner = owner;
        about = new CTMessages(LOC_ABOUT);
        version = new CTMessages(LOC_VERSION);
        init();
    }

    private void addHyperlink(String descriptionKey, String contentKey, URI address) {
        addField(descriptionKey, contentKey, address);
    }

    private void addField(String descriptionKey, String contentKey, URI address) {
        Container pane = getContentPane();
        JLabel descriptionLabel = new JLabel(String.format(FORMAT, about.getMessage(descriptionKey)));
        adjustFontSize(descriptionLabel, SMALL);
        descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pane.add(descriptionLabel);
        String message = version.getMessage(contentKey);
        JLabel contentLabel = address == null ? new JLabel(message) : new JHyperlink(message, address);
        adjustFontSize(contentLabel, BIG);
        pane.add(contentLabel);
        descriptionLabel.setLabelFor(contentLabel);
    }

    private void adjustFontSize(JComponent component, float mul) {
        Font font = component.getFont();
        component.setFont(font.deriveFont(font.getSize() * mul));
    }

    private void addHyperlink(String descriptionKey, String contentKey, String addressKey) {
        try {
            addField(descriptionKey, contentKey, new URI(version.getMessage(addressKey)));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLabel(String descriptionKey, String contentKey) {
        addField(descriptionKey, contentKey, null);
    }

    private String getMessage(String key) {
        return about.getMessage(key);
    }

    private void init() {
        setTitle(version.getMessage("project_name"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(0, 2, 10, 5));
        addLabel("label_project_name", "project_name");
        addLabel("label_project_description", "project_description");
        addLabel("label_project_version", "project_version");
        addLabel("label_organization_name", "organization_name");
        addHyperlink("label_organization_url", "organization_url", "organization_url");
        pane.add(new JButton(new OkAction(this)));
        pack();
    }

    @Override
    public Void showMe() {
        CTDragHelper.setLocationRelativeTo(this, this.owner);
        setVisible(true);
        return null;
    }

    private static class JHyperlink extends JLabel {
        private static final long serialVersionUID = -2543205042883642083L;
        final URI address;
        private Color underlineColor;

        public JHyperlink(String message, URI address) {
            super(message);
            this.address = address;
            setForeground(Color.BLUE.darker());
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new HyperlinkLabelMouseAdapter());
        }

        public Color getUnderlineColor() {
            return underlineColor;
        }

        public void setUnderlineColor(Color underlineColor) {
            this.underlineColor = underlineColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(underlineColor == null ? getForeground() : underlineColor);
            Insets insets = getInsets();
            int left = insets.left;
            if (getIcon() != null) {
                left += getIcon().getIconWidth() + getIconTextGap();
            }
            g.drawLine(left, getHeight() - 1 - insets.bottom, (int) getPreferredSize().getWidth() - insets.right,
                    getHeight() - 1 - insets.bottom);
        }

        private class HyperlinkLabelMouseAdapter extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(address);
                    }
                } catch (UnsupportedOperationException | IOException ex) {
                    Log.error(ex, ex.getLocalizedMessage());
                }
            }
        }
    }

    private static class OkAction extends AbstractAction {
        private static final long serialVersionUID = -4568926242772773378L;
        private final CTAboutDialog dialog;

        public OkAction(CTAboutDialog dialog) {
            super("OK");
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }

        @Override
        public OkAction clone() throws CloneNotSupportedException {
            return (OkAction) super.clone();
        }
    }
}
