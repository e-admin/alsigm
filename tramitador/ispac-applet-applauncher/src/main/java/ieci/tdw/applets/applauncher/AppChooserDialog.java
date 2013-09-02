package ieci.tdw.applets.applauncher;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

final class AppChooserDialog extends JDialog {

	private static final long serialVersionUID = 7159391470426550788L;

	private static final int OK = 0;
	static final int CANCEL = 1;

	private final JTextField appPathField = new JTextField();
	private int result = CANCEL;

	private String extension = null;

	private AppChooserDialog(final String extension, final String defaultAppPath) {
		super();

		if (defaultAppPath != null) {
			this.appPathField.setText(defaultAppPath);
		}

		this.extension = extension;
		setTitle(AppLauncherMessages.getString("appLauncherApplet.select.title")); //$NON-NLS-1$
		setModal(true);

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
		contentPane.add(getCenterPanel(), BorderLayout.CENTER);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);

		setResizable(false);
		if (JDialog.isDefaultLookAndFeelDecorated()) {
			final boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				setUndecorated(true);
				getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
			}
		}
		pack();
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent we) {
				setResult(CANCEL);
			}
		});
	}


    String getAppPath() {
		return this.appPathField.getText();
	}


	void setAppPath(final String appPath) {
		this.appPathField.setText(appPath);
	}

	int getResult() {
		return this.result;
	}

	void setResult(final int result) {
		this.result = result;
	}

	private JPanel getNorthPanel() {

	final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        final JLabel appPathLabel = new JLabel(
		AppLauncherMessages.getString(
				"appLauncherApplet.select.message", //$NON-NLS-1$
				new Object [] { this.extension }
			)
		);

        panel.add(appPathLabel);

        return panel;
    }

    private JPanel getCenterPanel() {

	final JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.appPathField.setColumns(20);

        final JLabel appPathLabel = new JLabel(
		AppLauncherMessages.getString("appLauncherApplet.select.appLabel"), //$NON-NLS-1$
		SwingConstants.TRAILING
		);
        appPathLabel.setLabelFor(this.appPathField);

        panel.add(appPathLabel);
        panel.add(this.appPathField);

        final URL imgURL = AppChooserDialog.class.getResource("images/Open16.gif"); //$NON-NLS-1$
        final JButton openButton = new JButton(new ImageIcon(imgURL));
        openButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
	            final int returnVal = fc.showOpenDialog(panel);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                final File file = fc.getSelectedFile();
	                if (file != null) {
				AppChooserDialog.this.setAppPath(file.getAbsolutePath());
	                }
	            }
			}
	});

        panel.add(openButton);

        return panel;
    }

	private JPanel getButtonsPanel() {

	final JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout());

        final JButton okButton = new JButton(
		AppLauncherMessages.getString("appLauncherApplet.button.ok") //$NON-NLS-1$
		);
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AppChooserDialog.this.setResult(OK);
				AppChooserDialog.this.dispose();
			}
	});
        panel.add(okButton);

        final JButton cancelButton = new JButton(
		AppLauncherMessages.getString("appLauncherApplet.button.cancel") //$NON-NLS-1$
		);
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AppChooserDialog.this.setAppPath(null);
				AppChooserDialog.this.setResult(CANCEL);
				AppChooserDialog.this.dispose();
			}
	});
        panel.add(cancelButton);

        return panel;
	}

	static AppChooserDialog showDialog(final String extension, final String defaultAppPath) {
		final AppChooserDialog dialog = new AppChooserDialog(extension, defaultAppPath);
		dialog.setVisible(true);
		return dialog;
	}
}
