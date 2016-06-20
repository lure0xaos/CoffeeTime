package gargoyle.ct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

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
		private volatile boolean disposing = false;
		private final JLabel label;
		volatile boolean reshow;

		public CTControlWindow(final URL imageURL, final JPopupMenu menu) {
			super(new JFrame() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isShowing() {
					return true;
				}
			});
			if (imageURL == null) {
				throw new IllegalArgumentException("image not found");
			}
			UIManager.getDefaults().put("ToolTipManager.enableToolTipMode", "");
			this.setAlwaysOnTop(true);
			final Container pane = this.getContentPane();
			pane.setLayout(new BorderLayout());
			this.label = new JLabel(new ImageIcon(imageURL));
			pane.add(this.label, BorderLayout.CENTER);
			this.label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.pack();
			this.setComponentPopupMenu(menu);
			final Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()
					.getSize();
			this.setLocation(screenSize.width - this.getWidth(), screenSize.height - this.getHeight());
			final int snap = 20;
			DragHelper.makeDraggable(this.label, snap);
			DragHelper.makeDraggable(this, snap);
			ToolTipManager.sharedInstance().setDismissDelay(1000);
			ToolTipManager.sharedInstance().setInitialDelay(100);
			ToolTipManager.sharedInstance().setReshowDelay(100);
			ToolTipManager.sharedInstance().setEnabled(true);
			ToolTipManager.sharedInstance().setLightWeightPopupEnabled(true);
			this.label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(final MouseEvent e) {
					CTControlWindow.this.reshow = true;
				}

				@Override
				public void mouseExited(final MouseEvent e) {
					CTControlWindow.this.reshow = false;
				}
			});
		}

		@Override
		public void dispose() {
			if (!this.disposing) {
				this.disposing = true;
				super.dispose();
				this.getOwner().dispose();
				this.disposing = false;
			}
		}

		public void setComponentPopupMenu(final JPopupMenu menu) {
			this.label.setComponentPopupMenu(menu);
		}

		public void setToolTipText(final String text) {
			this.label.setToolTipText(text);
			if (this.reshow && (text != null) && !text.isEmpty()) {
				try {
					ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(this.label, MouseEvent.MOUSE_MOVED,
							CTTimeUtil.currentTimeMillis(), 0, this.getWidth(), this.getHeight(), 0, false));
				} catch (final RuntimeException ex) {
					// IGNORE
				}
			}
		}
	}

	private static final String STR_EXIT = "exit";
	private static final String STR_UNARM = "unarm";
	private static final String STR_HELP = "help";
	private static final String URL_ICON = "/icon64.png";
	final CTApp app;
	private final CTControlWindow controlWindow;
	private final ButtonGroup group;

	public CTControl(final CTApp app) {
		this.app = app;
		this.group = new ButtonGroup();
		this.controlWindow = new CTControlWindow(CTControl.class.getResource(CTControl.URL_ICON),
				this.createMenu(app.getConfigs()));
		this.controlWindow.setVisible(true);
	}

	private void addConfig(final JPopupMenu menu, final CTConfig config) {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(new ConfigAction(config));
		this.group.add(menuItem);
		menu.add(menuItem);
	}

	private void addConfigs(final JPopupMenu menu, final CTConfigs configs) {
		for (final CTConfig config : configs.getConfigs()) {
			this.addConfig(menu, config);
		}
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

	private JPopupMenu createMenu(final CTConfigs configs) {
		final JPopupMenu menu = new JPopupMenu();
		this.addConfigs(menu, configs);
		menu.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu.add(new JMenuItem(new AbstractAction(this.app.getMessage(CTControl.STR_UNARM)) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				CTControl.this.unarm();
			}
		}));
		menu.add(new JMenuItem(new AbstractAction(this.app.getMessage(CTControl.STR_HELP)) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				CTControl.this.help();
			}
		}));
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
						CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis))));
			}
			if (task.isWarn(currentMillis)) {
				this.controlWindow.setToolTipText(
						CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))));
			}
			if (task.isSleeping(currentMillis)) {
				this.controlWindow.setToolTipText(
						CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))));
			}
		} else {
			this.controlWindow.setToolTipText(CTTimeUtil.formatHHMMSS(currentMillis));
		}
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
				final CTConfig cfg = configAction.getConfig();
				if ((cfg != null) && cfg.equals(config)) {
					return button;
				}
			}
		}
		return null;
	}

	@Override
	public CTConfigs getConfigs() {
		return null;
	}

	@Override
	public void help() {
		this.app.help();
	}

	@Override
	public void unarm() {
		CTControl.this.group.clearSelection();
		this.app.unarm();
	}
}
