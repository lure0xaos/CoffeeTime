package gargoyle.ct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class CTBlocker extends JWindow {
	private static final long serialVersionUID = 1L;
	private final JLabel lblMain;
	private final JLabel lblInfo;
	private final MouseAdapter disposer = new MouseAdapter() {
		@Override
		public void mouseClicked(final MouseEvent e) {
			CTBlocker.this.setVisible(false);
			CTBlocker.this.dispose();
		}
	};

	public CTBlocker() {
		this.setAlwaysOnTop(true);
		// this.setType(Type.UTILITY);
		this.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		this.toFront();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.lblMain = this.createMainLabel(), BorderLayout.CENTER);
		this.getContentPane().add(this.lblInfo = this.createInfoLabel(), BorderLayout.SOUTH);
	}

	private JLabel createInfoLabel() {
		final JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.GRAY);
		label.setAlignmentX(1);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		return label;
	}

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
	public void setBackground(final Color color) {
		this.lblMain.setBackground(color);
	}

	@Override
	public void setForeground(final Color color) {
		this.lblMain.setForeground(color);
	}

	public void setText(final String text) {
		this.lblMain.setText(text);
		this.lblInfo.setText(CTUtil.formatHHMMSS(CTUtil.currentTimeMillis()));
		this.repaint();
	}
}
