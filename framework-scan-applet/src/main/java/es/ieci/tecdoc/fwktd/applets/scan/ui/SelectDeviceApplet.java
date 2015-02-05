package es.ieci.tecdoc.fwktd.applets.scan.ui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionScan;


public class SelectDeviceApplet extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JButton jButtonCancel;
	private JLabel jLabelFuente;
	private JButton jButtonAceptar;
	private JComboBox jComboBoxSource;

	@SuppressWarnings("unchecked")
	private ArrayList sources;	
	@SuppressWarnings("unchecked")
	private HashMap properties;
	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				SelectDeviceApplet inst = new SelectDeviceApplet(frame,true);
				
				inst.setTitle("IeciScan");
				//Image im = (new ImageIcon(getClass().getClassLoader().getResource("icons/corte.jpeg"))).getImage();
				//inst.setIconImage(im);
				inst.setVisible(true);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public SelectDeviceApplet(JFrame frame,boolean state) {		
		super(frame,true);
		properties = new HashMap();			
		sources = ActionScan.getSources();	
		initGUI();
	}
	
	@SuppressWarnings("unchecked")
	public SelectDeviceApplet(boolean state) {		
		super(new JFrame(),true);
		properties = new HashMap();			
		sources = ActionScan.getSources();	
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(null);
				jPanel1.setPreferredSize(new java.awt.Dimension(350, 120));
				{
					ComboBoxModel jComboBoxSourceModel = new DefaultComboBoxModel();
					jComboBoxSource = new JComboBox();
					jPanel1.add(jComboBoxSource);					
					jComboBoxSource.setModel(jComboBoxSourceModel);
					jComboBoxSource.setBounds(25, 48, 294, 21);
					for(int i = 0; i<sources.size();i++){
						jComboBoxSource.addItem(sources.get(i));
					}					
				}
				{
					jButtonAceptar = new JButton();
					jPanel1.add(jButtonAceptar);
					jButtonAceptar.setBounds(104, 86, 102, 21);
					jButtonAceptar.setText("Aceptar");
					jButtonAceptar.addActionListener(new ActionListener() {						
					      public void actionPerformed(ActionEvent e) {				    	  	
								dispose();
					        }
					      });
				}
				{
					jLabelFuente = new JLabel();
					jPanel1.add(jLabelFuente);
					jLabelFuente.setText("Selecciona la fuente");
					jLabelFuente.setBounds(25, 22, 156, 14);
				}
				{
					jButtonCancel = new JButton();
					jPanel1.add(jButtonCancel);
					jButtonCancel.setText("Cancelar");
					jButtonCancel.setBounds(217, 86, 102, 21);
					jButtonCancel.addActionListener(new ActionListener() {						
					      public void actionPerformed(ActionEvent e) {		
					    	  	getJComboBoxSource().setSelectedItem(null);
								dispose();
					        }
					      });
				}
			}
			setSize(400, 300);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	public JComboBox getJComboBoxSource() {
		return jComboBoxSource;
	}

	@SuppressWarnings("unchecked")
	public HashMap getProperties() {
		return properties;
	}
}
