package gargoyle.ct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class CTBlocker extends JWindow implements CTTaskUpdatable {
	private static final String MSG_BLOCKED = "blocked_w";
	private static final String MSG_WARN = "warn_w";
	private static final long serialVersionUID = 1L;
	private transient MessageProvider app;
	private transient MouseAdapter disposer = new MouseAdapter() {
		@Override
		public void mouseClicked(final MouseEvent e) {
			CTBlocker.this.setVisible(false);
			CTBlocker.this.dispose();
		}
	};
	private final JLabel lblInfo;
	private final JLabel lblMain;

	public CTBlocker(final MessageProvider app) {
		this.app = app;
		this.setAlwaysOnTop(true);
		this.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		this.toFront();
		final Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(this.lblMain = this.createMainLabel(), BorderLayout.CENTER);
		container.add(this.lblInfo = this.createInfoLabel(), BorderLayout.SOUTH);
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(final WindowEvent e) {
				//
			}

			@Override
			public void windowLostFocus(final WindowEvent e) {
				if (CTBlocker.this.isVisible()) {
					e.getWindow().requestFocus();
				}
			}
		});
	}

	@SuppressWarnings("static-method")
	private JLabel createInfoLabel() {
		final JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.GRAY);
		label.setAlignmentX(1);
		final int gap = 10;
		label.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 30)));
		return label;
	}

	@SuppressWarnings("static-method")
	private JLabel createMainLabel() {
		final JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.WHITE);
		label.setAlignmentX(.5f);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10)));
		return label;
	}

	void debug(final boolean debug) {
		this.setAlwaysOnTop(!debug);
		if (debug) {
			this.addMouseListener(this.disposer);
		} else {
			this.removeMouseListener(this.disposer);
		}
	}

	@Override
	public void doUpdate(final CTTask task, final long currentMillis) {
		this.lblInfo.setText(CTTimeUtil.formatHHMMSS(currentMillis));
		if (task.isReady()) {
			if (task.isBlocked(currentMillis)) {
				this.setVisible(true);
				this.setForeground(Color.WHITE);
				this.setText(this.app.getMessage(CTBlocker.MSG_BLOCKED,
						CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis)))));
			}
			if (task.isWarn(currentMillis)) {
				this.setVisible(CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, 60, 3));
				this.setForeground(Color.GREEN);
				this.setText(this.app.getMessage(CTBlocker.MSG_WARN,
						CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis)))));
			}
			if (task.isSleeping(currentMillis)) {
				this.setVisible(false);
			}
		}
	}

	@Override
	public void setBackground(final Color color) {
		this.lblMain.setBackground(color);
	}

	@Override
	public void setForeground(final Color color) {
		this.lblMain.setForeground(color);
	}

	public void setText(final String text) {
		this.lblMain.setText(text);
		this.repaint();
	}

	@Override
	public void setVisible(final boolean b) {
		super.setVisible(b);
		this.toFront();
	}
}
