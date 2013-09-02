package ieci.tdw.applets.applauncher;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

final class AppFormDialog extends JDialog {

	private static final long serialVersionUID = 7864450586642936806L;

	static final int OK = 0;
	private static final int CANCEL = 1;

	private final JTextField docTypeField = new JTextField();
	private final JTextField appPathField = new JTextField();
	private boolean exists = false;
	private int result = CANCEL;

	private AppFormDialog(final String docType, final String appPath) {
		super();

		this.setTitle(AppLauncherMessages.getString("appLauncherApplet.form.title")); //$NON-NLS-1$
		this.setModal(true);

		if ( docType != null && docType.trim().length() > 0
				&& appPath != null && appPath.trim().length() > 0 ) {
			this.exists = true;
			setDocType(docType);
			setAppPath(appPath);
		}

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
		contentPane.add(getCenterPanel(), BorderLayout.CENTER);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);

		this.setResizable(false);
		if (JDialog.isDefaultLookAndFeelDecorated()) {
			final boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				this.setUndecorated(true);
				getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
			}
		}
		this.pack();
		this.setLocationRelativeTo(null);

		this.addWindowListener(new WindowAdapter() {
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

	String getDocType() {
		return this.docTypeField.getText();
	}

	void setDocType(final String docType) {
		this.docTypeField.setText(docType);
	}

	int getResult() {
		return this.result;
	}

	void setResult(final int result) {
		this.result = result;
	}

	private static JPanel getNorthPanel() {

	final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        final JLabel appPathLabel = new JLabel(
		AppLauncherMessages.getString("appLauncherApplet.form.message") //$NON-NLS-1$
		);

        panel.add(appPathLabel);

        return panel;
    }

    private JPanel getCenterPanel() {

	final JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	final GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = new Insets(0, 5, 0, 5);

	if (this.exists) {
	        final JLabel docTypeLabel = new JLabel(
			AppLauncherMessages.getString("appLauncherApplet.form.docTypeLabel"), //$NON-NLS-1$
			SwingConstants.TRAILING
		);

	        c.gridx = 0;
		c.gridy = 0;
	        panel.add(docTypeLabel, c);

	        final JLabel docTypeValueLabel = new JLabel(getDocType(), SwingConstants.LEFT);
	        c.gridx = 1;
		c.gridy = 0;
	        panel.add(docTypeValueLabel, c);

	}
	else {
		this.docTypeField.setColumns(10);

	        final JLabel docTypeLabel = new JLabel(
			AppLauncherMessages.getString("appLauncherApplet.form.docTypeLabel"), //$NON-NLS-1$
			SwingConstants.TRAILING
		);
	        docTypeLabel.setLabelFor(this.docTypeField);

	        c.gridx = 0;
		c.gridy = 0;
	        panel.add(docTypeLabel, c);

	        c.gridx = 1;
		c.gridy = 0;
	        panel.add(this.docTypeField, c);
	}

	this.appPathField.setColumns(25);

        final JLabel appPathLabel = new JLabel(
		AppLauncherMessages.getString("appLauncherApplet.form.appLabel"), //$NON-NLS-1$
		SwingConstants.TRAILING
		);
        appPathLabel.setLabelFor(this.appPathField);

        c.gridx = 0;
	c.gridy = 1;
        panel.add(appPathLabel, c);

        c.gridx = 1;
	c.gridy = 1;
	panel.add(this.appPathField, c);

        final URL imgURL = AppFormDialog.class.getResource("images/Open16.gif"); //$NON-NLS-1$
        final JButton openButton = new JButton(new ImageIcon(imgURL));
        openButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
	            final int returnVal = fc.showOpenDialog(panel);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                final File file = fc.getSelectedFile();
	                if (file != null) {
				AppFormDialog.this.setAppPath(file.getAbsolutePath());
	                }
	            }
			}
	});

        c.gridx = 2;
	c.gridy = 1;
        panel.add(openButton, c);

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


				if (getDocType().trim().length() == 0) {
					JOptionPane.showMessageDialog(
						AppFormDialog.this,
						AppLauncherMessages.getString(
							"appLauncherApplet.form.error.docType.empty" //$NON-NLS-1$
						),
						AppLauncherMessages.getString(
							"appLauncherApplet.error.error.title" //$NON-NLS-1$
						),
						JOptionPane.ERROR_MESSAGE
					);
				}
				else if (getAppPath().trim().length() == 0) {
					JOptionPane.showMessageDialog(
						AppFormDialog.this,
						AppLauncherMessages.getString(
							"appLauncherApplet.form.error.application.empty" //$NON-NLS-1$
						),
						AppLauncherMessages.getString(
							"appLauncherApplet.error.error.title" //$NON-NLS-1$
						),
						JOptionPane.ERROR_MESSAGE
					);
				}
				else if (!AppLauncherAppletProperties.checkAppPath(getAppPath())) {
					JOptionPane.showMessageDialog(
						AppFormDialog.this,
						AppLauncherMessages.getString(
							"appLauncherApplet.error.invalidApp" //$NON-NLS-1$
						),
						AppLauncherMessages.getString(
							"appLauncherApplet.error.error.title" //$NON-NLS-1$
						),
						JOptionPane.ERROR_MESSAGE
					);
				}
				else {
					AppFormDialog.this.setResult(OK);
					AppFormDialog.this.dispose();
				}
			}
	});
        panel.add(okButton);

        final JButton cancelButton = new JButton(
		AppLauncherMessages.getString("appLauncherApplet.button.cancel") //$NON-NLS-1$
		);
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AppFormDialog.this.setDocType(null);
				AppFormDialog.this.setAppPath(null);
				AppFormDialog.this.setResult(CANCEL);
				AppFormDialog.this.dispose();
			}
	});
        panel.add(cancelButton);

        return panel;
	}

	static AppFormDialog showDialog() {
		return showDialog(null, null);
	}

	static AppFormDialog showDialog(final String docType, final String appPath) {
		final AppFormDialog dialog = new AppFormDialog(docType, appPath);
		dialog.setVisible(true);
		return dialog;
	}
}
