package es.ieci.tecdoc.fwktd.applets.scan.ui;
import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import es.ieci.tecdoc.fwktd.applets.scan.key.IconsKeys;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;

public class InformationApplet extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JTextPane jTextPane1;
	private JLabel jLabel1;
	private JSeparator jSeparator1;
	private JPanel jPanelIcon;
	private Properties messages;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				InformationApplet inst = new InformationApplet(frame,true);
				inst.setVisible(true);
			}
		});
	}
	
	public InformationApplet(JFrame frame, boolean modal) {
		super(frame,modal);
		initGUI();
	}
	
	public InformationApplet(JFrame frame, boolean modal, Properties messages) {
		super(frame,modal);
		this.messages = messages;
		initGUI();
	}
	
	public InformationApplet(boolean modal, Properties messages) {
		super(new JFrame(),modal);
		this.messages = messages;
		initGUI();
	}

	private void initGUI() {
		try {
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setPreferredSize(new java.awt.Dimension(480, 124));
				jPanel1.setLayout(null);
				{
					jTextPane1 = new JTextPane();
					jTextPane1.setEditable(false);
					jPanel1.add(jTextPane1);
					jTextPane1.setText(messages.getProperty(MessageKeys.INFO));
					jTextPane1.setBounds(103, 12, 300, 54);
					jTextPane1.setOpaque(false);
					//jTextPane1.setBackground(new java.awt.Color(236,233,216));
				}
				{
					jSeparator1 = new JSeparator();
					jPanel1.add(jSeparator1);
					jSeparator1.setBounds(19, 80, 442, 10);
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText(messages.getProperty(MessageKeys.COPYRIGHT));
					jLabel1.setBounds(55, 88, 480, 14);
				}
				{					
					jPanelIcon = new JPanel();
					jPanelIcon.setBounds(25, 18, 50, 50);
					ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.LOGO));					
					JLabel jl = this.scale(icono);
					jPanelIcon.add(jl);
					jPanelIcon.repaint();
					jPanel1.add(jPanelIcon);
					//jPanel1.add(jl);
					//jPanelIcon.setLayout(null);					
					//jPanelIcon.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				}
			}
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JLabel scale(ImageIcon icono){
		Image img = icono.getImage();
		int w = jPanelIcon.getWidth();
		int h = jPanelIcon.getHeight();
		if (w > h) {
			w = h;
		} else {
			h = w;
		}
		img = img
				.getScaledInstance(w - 20, h - 20, java.awt.Image.SCALE_SMOOTH);

		ImageIcon icon = new ImageIcon(img);
		JLabel jl = new JLabel(icon);
		jl.setBounds(1, 1, icono.getIconWidth() - 1, icono.getIconHeight() - 1);
		return jl;
	}
}
