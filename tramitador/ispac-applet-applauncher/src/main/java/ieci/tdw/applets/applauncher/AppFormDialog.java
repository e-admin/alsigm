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
import javax.swing.UIManager;

public class AppFormDialog extends JDialog {

	public static final int OK 		= 0;
	public static final int CANCEL 	= 1;
	
	private JTextField docTypeField = new JTextField();
	private JTextField appPathField = new JTextField();
	private boolean exists = false; 
	private int result = CANCEL;
	
	public AppFormDialog(String docType, String appPath) { 
		super();

		this.setTitle(AppLauncherMessages.getString("appLauncherApplet.form.title"));
		this.setModal(true);
		
		if ( (docType != null) && (docType.trim().length() > 0)
				&& (appPath != null) && (appPath.trim().length() > 0) ) {
			exists = true;
			setDocType(docType);
			setAppPath(appPath);
		}
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
		contentPane.add(getCenterPanel(), BorderLayout.CENTER);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);

		this.setResizable(false);
		if (JDialog.isDefaultLookAndFeelDecorated()) {
			boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				this.setUndecorated(true);
				getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
			}
		}
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setResult(CANCEL);
			}
		});
	}
	
	
    public String getAppPath() {
		return appPathField.getText();
	}

	public void setAppPath(String appPath) {
		appPathField.setText(appPath);
	}

	public String getDocType() {
		return docTypeField.getText();
	}

	public void setDocType(String docType) {
		docTypeField.setText(docType);
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	protected JPanel getNorthPanel() {
    	
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel appPathLabel = new JLabel(
        		AppLauncherMessages.getString("appLauncherApplet.form.message"));
        
        panel.add(appPathLabel);
    	
        return panel;
    }

    protected JPanel getCenterPanel() {
    	
    	final JPanel panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
    	panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(0, 5, 0, 5);
    	
    	if (exists) {
	        JLabel docTypeLabel = new JLabel(
	        		AppLauncherMessages.getString("appLauncherApplet.form.docTypeLabel"), 
	        		JLabel.TRAILING);
    		
	        c.gridx = 0;
	    	c.gridy = 0;
	        panel.add(docTypeLabel, c);

	        JLabel docTypeValueLabel = new JLabel(getDocType(), JLabel.LEFT);
	        c.gridx = 1;
	    	c.gridy = 0;
	        panel.add(docTypeValueLabel, c);
	        
    	} else {
	    	docTypeField.setColumns(10);
	
	        JLabel docTypeLabel = new JLabel(
	        		AppLauncherMessages.getString("appLauncherApplet.form.docTypeLabel"), 
	        		JLabel.TRAILING);
	        docTypeLabel.setLabelFor(docTypeField);
	
	        c.gridx = 0;
	    	c.gridy = 0;
	        panel.add(docTypeLabel, c);

	        c.gridx = 1;
	    	c.gridy = 0;
	        panel.add(docTypeField, c);
    	}

    	appPathField.setColumns(25);

        JLabel appPathLabel = new JLabel(
        		AppLauncherMessages.getString("appLauncherApplet.form.appLabel"), 
        		JLabel.TRAILING);
        appPathLabel.setLabelFor(appPathField);

        c.gridx = 0;
    	c.gridy = 1;
        panel.add(appPathLabel, c);

        c.gridx = 1;
    	c.gridy = 1;
    	panel.add(appPathField, c);

        URL imgURL = AppFormDialog.class.getResource("images/Open16.gif");
        JButton openButton = new JButton(new ImageIcon(imgURL));
        openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
	            int returnVal = fc.showOpenDialog(panel);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
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

	protected JPanel getButtonsPanel() {
		
    	JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout());

        JButton okButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.ok"));
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (getDocType().trim().length() == 0) {
					JOptionPane.showMessageDialog(
							AppFormDialog.this,  
							AppLauncherMessages.getString(
									"appLauncherApplet.form.error.docType.empty"), 
							AppLauncherMessages.getString(
									"appLauncherApplet.error.error.title"), 
							JOptionPane.ERROR_MESSAGE);
				} else if (getAppPath().trim().length() == 0) {
					JOptionPane.showMessageDialog(
							AppFormDialog.this,  
							AppLauncherMessages.getString(
									"appLauncherApplet.form.error.application.empty"), 
							AppLauncherMessages.getString(
									"appLauncherApplet.error.error.title"), 
							JOptionPane.ERROR_MESSAGE);
				} else if (!AppLauncherAppletProperties.checkAppPath(getAppPath())) {
					JOptionPane.showMessageDialog(
							AppFormDialog.this,  
							AppLauncherMessages.getString(
									"appLauncherApplet.error.invalidApp"), 
							AppLauncherMessages.getString(
									"appLauncherApplet.error.error.title"), 
							JOptionPane.ERROR_MESSAGE);
				} else {
					AppFormDialog.this.setResult(OK);
					AppFormDialog.this.dispose();
				}
			}
    	});
        panel.add(okButton);

        JButton cancelButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.cancel"));
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppFormDialog.this.setDocType(null);
				AppFormDialog.this.setAppPath(null);
				AppFormDialog.this.setResult(CANCEL);
				AppFormDialog.this.dispose();
			}
    	});
        panel.add(cancelButton);

        return panel;
	}

	public static AppFormDialog showDialog() {
		return showDialog(null, null);
	}

	public static AppFormDialog showDialog(String docType, String appPath) {

		AppFormDialog dialog = new AppFormDialog(docType, appPath);
		dialog.show();

		return dialog;
	}

}
