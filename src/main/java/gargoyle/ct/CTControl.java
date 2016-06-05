package gargoyle.ct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class CTControl implements CTControlActions, CTTaskUpdatable {
	private final class ConfigAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final CTConfig config;

		public ConfigAction(final CTConfig config) {
			super(config.getName());
			this.config = config;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			CTControl.this.arm(this.getConfig());
		}

		public CTConfig getConfig() {
			return this.config;
		}
	}

	private static final class CTControlWindow extends JWindow {
		private static final long serialVersionUID = 1L;
		private final JLabel label;

		public CTControlWindow(final URL imageURL, final JPopupMenu menu) {
			if (imageURL == null) {
				throw new IllegalArgumentException("image not found");
			}
			final CTControlWindow window = this;
			window.setAlwaysOnTop(true);
			final Container pane = window.getContentPane();
			pane.setLayout(new BorderLayout());
			this.label = new JLabel(new ImageIcon(imageURL));
			// ToolTipManager.sharedInstance().setEnabled(true);
			// ToolTipManager.sharedInstance().setLightWeightPopupEnabled(true);
			// ToolTipManager.sharedInstance().setInitialDelay(100);
			// ToolTipManager.sharedInstance().setReshowDelay(100);
			// ToolTipManager.sharedInstance().setDismissDelay(10000);
			// this.label.addMouseListener(new MouseAdapter() {
			// @Override
			// public void mouseEntered(final MouseEvent e) {
			// ToolTipManager.sharedInstance().mouseEntered(e);
			// }
			// });
			// this.label.addMouseMotionListener(new MouseMotionAdapter() {
			// @Override
			// public void mouseMoved(final MouseEvent e) {
			// ToolTipManager.sharedInstance().mouseMoved(e);
			// }
			// });
			pane.add(this.label, BorderLayout.CENTER);
			this.label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			window.pack();
			this.setComponentPopupMenu(menu);
			final Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
					.getSize();
			window.setLocation(screenSize.width - window.getWidth(), screenSize.height - window.getHeight());
			final int snap = 20;
			DragUtil.makeDraggable(this.label, snap);
			DragUtil.makeDraggable(this, snap);
		}

		public void setComponentPopupMenu(final JPopupMenu menu) {
			this.label.setComponentPopupMenu(menu);
		}

		public void setToolTipText(final String text) {
			this.label.setToolTipText(text);
		}
	}

	private static final String STR_EXIT = "exit";
	private static final String URL_ICON = "/icon64.png";
	private final ButtonGroup group;
	final CTApp app;
	private final CTControlWindow controlWindow;

	public CTControl(final CTApp app) {
		this.app = app;
		this.group = new ButtonGroup();
		this.controlWindow = new CTControlWindow(CTControl.class.getResource(CTControl.URL_ICON),
				this.createMenu(app.getConfigs()));
		this.controlWindow.setVisible(true);
	}

	@Override
	public void arm(final CTConfig config) {
		final AbstractButton item = this.findItem(config);
		if (item != null) {
			CTControl.this.group.clearSelection();
			CTControl.this.group.setSelected(item.getModel(), true);
			CTControl.this.app.arm(config);
		}
	}

	private JPopupMenu createMenu(final List<CTConfig> configs) {
		final JPopupMenu menu = new JPopupMenu();
		for (final CTConfig config : configs) {
			final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(new ConfigAction(config));
			this.group.add(menuItem);
			menu.add(menuItem);
		}
		menu.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu.add(new JMenuItem(new AbstractAction(this.app.getMessage(CTControl.STR_EXIT)) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				CTControl.this.exit();
			}
		}));
		return menu;
	}

	@Override
	public void doUpdate(final CTTask task, final long currentMillis) {
		if (task.isReady()) {
			if (task.isBlocked(currentMillis)) {
				this.controlWindow.setToolTipText(
						CTUtil.formatMMSS(CTUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis))));
			}
			if (task.isWarn(currentMillis)) {
				this.controlWindow.setToolTipText(
						CTUtil.formatMMSS(CTUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))));
			}
			if (task.isSleeping(currentMillis)) {
				this.controlWindow.setToolTipText(
						CTUtil.formatMMSS(CTUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))));
			}
		} else {
			this.controlWindow.setToolTipText(CTUtil.formatHHMMSS(currentMillis));
		}
		System.out.println(CTUtil.formatHHMMSS(currentMillis));
	}

	@Override
	public void exit() {
		this.controlWindow.setVisible(false);
		this.controlWindow.dispose();
		this.app.unarm();
		this.app.exit();
	}

	private AbstractButton findItem(final CTConfig config) {
		for (final AbstractButton button : Collections.list(this.group.getElements())) {
			final Action action = button.getAction();
			if ((action != null) && (action instanceof ConfigAction)) {
				final ConfigAction configAction = (ConfigAction) action;
				final CTConfig config2 = configAction.getConfig();
				if ((config2 != null) && config2.equals(config)) {
					return button;
				}
			}
		}
		return null;
	}

	@Override
	public List<CTConfig> getConfigs() {
		return null;
	}

	@Override
	public void unarm() {
		this.app.unarm();
	}
}
