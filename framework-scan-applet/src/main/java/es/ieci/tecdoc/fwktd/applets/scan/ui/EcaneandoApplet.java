package es.ieci.tecdoc.fwktd.applets.scan.ui;
import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;


public class EcaneandoApplet extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JLabel jLabel1;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				EcaneandoApplet inst = new EcaneandoApplet(frame);
				inst.setVisible(true);
			}
		});
	}

	public EcaneandoApplet(JFrame frame,boolean modal, PerfilesVO perfiles, Properties messages) {
		super(frame, modal);
		initGUI();
	}
	
	public EcaneandoApplet(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(null);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("Se está procesando la petición...");
					jLabel1.setBounds(93, 28, 230, 14);
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
