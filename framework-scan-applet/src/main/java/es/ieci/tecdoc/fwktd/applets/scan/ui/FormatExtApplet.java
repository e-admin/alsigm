package es.ieci.tecdoc.fwktd.applets.scan.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import es.ieci.tecdoc.fwktd.applets.scan.key.KeysUtils;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;

public class FormatExtApplet extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private JRadioButton jRadioJpg;
	private JButton jButtonAceptar;
	private JRadioButton jRadioPdf;
	private JRadioButton jRadioTiff;
	private JRadioButton jRadioMultiTiff;
	private ButtonGroup btnGroup; 
	private JComboBox jComboBoxPaper;
	@SuppressWarnings("unused")
	private JFrame frame;
	private Properties messages;
	private boolean accept = false;
	
	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				FormatExtApplet inst = new FormatExtApplet(frame,true);
				inst.setVisible(true);
			}
		});
	}
	
	public FormatExtApplet(JFrame frame, boolean b) {		
		super(frame,b);
		this.frame = frame;
		initGUI();
	}
	
	public FormatExtApplet(JFrame frame, boolean bol,Properties messages) {	
		super(frame,bol);
		this.frame = frame;
		this.messages = messages;
		initGUI();		
	}
	
	public FormatExtApplet(boolean bol,Properties messages) {	
		super(new JFrame(),bol);
		//this.frame = frame;
		this.messages = messages;
		initGUI();		
	}
	
	private void initGUI() {
		try {
			btnGroup = new ButtonGroup();
			{
				jPanel = new JPanel();
				getContentPane().add(jPanel, BorderLayout.CENTER);	
				{
					JLabel jLabel5 = new JLabel();
					jPanel.add(jLabel5);
					jLabel5.setText(messages.getProperty(MessageKeys.FORMAT)+":");
					jLabel5.setBounds(28, 231, 60, 14);
				}
				{
					//TODO SOLO PDF/A TIFF IV o JPEG
					String labels[] = { KeysUtils.JPG, KeysUtils.PDF, KeysUtils.TIFF, KeysUtils.TIFF_Multipage };
					ComboBoxModel jComboBoxPaperModel = new DefaultComboBoxModel(labels);
					jComboBoxPaper = new JComboBox();
					jPanel.add(getJComboBoxPaper());
					jComboBoxPaper.setModel(jComboBoxPaperModel);
					jComboBoxPaper.setBounds(111, 228, 255, 21);						
				}				
				{
					accept=false;
					jButtonAceptar = new JButton();
					jPanel.add(jButtonAceptar);
					jButtonAceptar.setText("Aceptar");
					jButtonAceptar.setBounds(26, 111, 52, 21);
					jButtonAceptar.addActionListener(new ActionListener() {
					      public void actionPerformed(ActionEvent e) {		
					    	  setAccept(true);
					    	  dispose();					    	  
					        }
					      });

				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JRadioButton getJRadioJpg() {
		return jRadioJpg;
	}
	
	public JRadioButton getJRadioMultiTiff() {
		return jRadioMultiTiff;
	}

	public JRadioButton getJRadioTiff() {
		return jRadioTiff;
	}
	
	public JRadioButton getJRadioPdf() {
		return jRadioPdf;
	}
	
	public ButtonGroup getButtonGroup() {
		return btnGroup;
	}

	public JComboBox getJComboBoxPaper() {
		return jComboBoxPaper;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}
}
