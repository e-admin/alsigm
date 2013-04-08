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
import javax.swing.UIManager;

public class AppChooserDialog extends JDialog {

	public static final int OK 		= 0;
	public static final int CANCEL 	= 1;
	
	private JTextField appPathField = new JTextField();
	private int result = CANCEL;
	
	private String extension = null;
	
	public AppChooserDialog(String extension) { 
		super();

		this.extension = extension;
		this.setTitle(AppLauncherMessages.getString("appLauncherApplet.select.title"));
		this.setModal(true);
		
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
        		AppLauncherMessages.getString("appLauncherApplet.select.message",
        				new Object [] { extension }));
        
        panel.add(appPathLabel);
    	
        return panel;
    }

    protected JPanel getCenterPanel() {
    	
    	final JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        appPathField.setColumns(20);

        JLabel appPathLabel = new JLabel(
        		AppLauncherMessages.getString("appLauncherApplet.select.appLabel"), 
        		JLabel.TRAILING);
        appPathLabel.setLabelFor(appPathField);

        panel.add(appPathLabel);
        panel.add(appPathField);

        URL imgURL = AppChooserDialog.class.getResource("images/Open16.gif");
        JButton openButton = new JButton(new ImageIcon(imgURL));
        openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
	            int returnVal = fc.showOpenDialog(panel);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                if (file != null) {
	                	AppChooserDialog.this.setAppPath(file.getAbsolutePath());
	                }
	            }
			}
    	});

        panel.add(openButton);
    	
        return panel;
    }

	protected JPanel getButtonsPanel() {
		
    	JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout());

        JButton okButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.ok"));
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppChooserDialog.this.setResult(OK);
				AppChooserDialog.this.dispose();
			}
    	});
        panel.add(okButton);

        JButton cancelButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.cancel"));
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppChooserDialog.this.setAppPath(null);
				AppChooserDialog.this.setResult(CANCEL);
				AppChooserDialog.this.dispose();
			}
    	});
        panel.add(cancelButton);

        return panel;
	}

	public static AppChooserDialog showDialog(String extension) {

		AppChooserDialog dialog = new AppChooserDialog(extension);
		dialog.show();

		return dialog;
	}
}
