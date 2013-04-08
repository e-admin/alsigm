package es.ieci.tecdoc.fwktd.applets.scan.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionScan;
import es.ieci.tecdoc.fwktd.applets.scan.key.IconsKeys;
import es.ieci.tecdoc.fwktd.applets.scan.key.KeysUtils;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Capabilities;
import es.ieci.tecdoc.fwktd.applets.scan.utils.Messages;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PropertiesVO;

public class NewEditPerfilApplet extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JTextField jTextFieldName;
	private JComboBox jComboBoxResolucion;
	private JComboBox jComboBoxColor;
	private JLabel jLabel4;
	private JComboBox jComboBoxSize;
	private JComboBox jComboBoxPaper;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel5;
	private JLabel jLabelContrast;
	private JLabel jLabelContrast1;
	private JLabel jLabelBrigth;
	private JLabel jLabelBrigth1;
	private JButton jButtonSelect;
	private JTextField jTextFuente;
	private JTextField jTextQuality;
	private JLabel jLabelQuality;
	private JLabel jLabelRangoQuality;
	private JLabel jLabel1;
	private JButton jButtonAceptar;
	private JButton jButtonCancelar;
	private JCheckBox jCheckBoxUI;
	private JCheckBox jCheckBoxCompress;
	private JCheckBox jCheckBoxDuplex;
	private JCheckBox jCheckBoxADF;
	private JLabel Nombre;
	private JFrame frame;
	private Properties messages;
	private boolean cancel = false;
	private JScrollBar scrollContrast;
	private JScrollBar scrollBright;
	private ParametrosVO parametrosVO;
	
	/**
	 * Auto-generated main method to display this JDialog
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				NewEditPerfilApplet inst = new NewEditPerfilApplet(frame, true);
				inst.setVisible(true);
			}
		});
	}
	

	public NewEditPerfilApplet(JFrame frame, boolean modal, Properties messages, ParametrosVO parametrosVO) {
		super(frame, modal);
		this.frame = frame;
		this.messages = messages;
		this.parametrosVO = parametrosVO;
		initGUI();
	}
	
	public NewEditPerfilApplet(JFrame frame, boolean modal) {
		super(frame, modal);
		this.frame = frame;
		initGUI();
	}

	private void initGUI() {
		try {
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(null);
				jPanel1.setPreferredSize(new Dimension(380, 500));
				{
					Nombre = new JLabel();
					jPanel1.add(Nombre);
					Nombre.setText(messages.getProperty(MessageKeys.NAME) + ":");
					Nombre.setBounds(28, 33, 90, 14);
				}
				{
					jCheckBoxUI = new JCheckBox();
					jPanel1.add(jCheckBoxUI);
					jCheckBoxUI.setText(messages.getProperty(MessageKeys.INTERFACE_UI));
					jCheckBoxUI.setBounds(28, 390, 270, 18);
					jCheckBoxUI.addActionListener(new ActionListener() {						
					      public void actionPerformed(ActionEvent e) {				    	  	
								if(jCheckBoxUI.isSelected() && jTextFuente.getText()!=null && !jTextFuente.getText().equals("")){
									disableAll();				
								}else if(jTextFuente.getText()!=null && !jTextFuente.getText().equals("")) {
									enableAll();
								}
					        }
						});
				}
				{
					jCheckBoxDuplex = new JCheckBox();
					jPanel1.add(jCheckBoxDuplex);
					jCheckBoxDuplex.setText(messages.getProperty(MessageKeys.DUPLEX));
					jCheckBoxDuplex.setBounds(28, 430, 120, 18);
					jCheckBoxDuplex.setVisible(false);
					jCheckBoxDuplex.addActionListener(new ActionListener() {						
					      public void actionPerformed(ActionEvent e) {				    	  	
								if(jCheckBoxDuplex.isSelected()){
									jCheckBoxADF.setEnabled(false);
									jCheckBoxADF.setSelected(true);					
								}else {
									jCheckBoxADF.setEnabled(true);
								}
					        }
					      });
				}
				{
					jCheckBoxADF = new JCheckBox();
					jPanel1.add(jCheckBoxADF);
					jCheckBoxADF.setText(messages.getProperty(MessageKeys.ADF));
				//	jCheckBoxADF.setSize(200, 20);
					jCheckBoxADF.setBounds(28, 410, 270, 18);
					jCheckBoxADF.setVisible(false);
				}
				/*
				 {
					jCheckBoxCompress = new JCheckBox();
					jPanel1.add(jCheckBoxCompress);
					jCheckBoxCompress.setText(messages.getProperty(MessageKeys.COMPRESS));
					jCheckBoxCompress.setBounds(230, 350, 110, 18);
					jCheckBoxCompress.setVisible(false);
					jCheckBoxCompress.addActionListener(new ActionListener() {						
					      public void actionPerformed(ActionEvent e) {				    	  	
								if(jCheckBoxCompress.isSelected()){
									jTextQuality.setEnabled(true);					
								}else{
									jTextQuality.setText("");
									jTextQuality.setEnabled(false);
								}
					        }
					      });
				}
				*/
				{
					jLabelQuality = new JLabel();
					jPanel1.add(jLabelQuality);		
					jLabelQuality.setText(messages.getProperty(MessageKeys.QUALITY) + " :");
					jLabelQuality.setBounds(28, 335, 150, 50);		
					jLabelQuality.setVisible(false);
					
					jLabelRangoQuality = new JLabel();
					jPanel1.add(jLabelRangoQuality);		
					jLabelRangoQuality.setText("(1-100)");
					jLabelRangoQuality.setBounds(230, 350, 110, 18);
					jLabelRangoQuality.setVisible(false);
					
					
				}
				{
				//	MaskFormatter mascara = new MaskFormatter("###");					
					jTextQuality = new JTextField();
					jPanel1.add(jTextQuality);				
					jTextQuality.setBounds(111, 350, 116, 18);
					//jTextQuality.setEnabled(false);	
					jTextQuality.setVisible(false);
					jTextQuality.setText("50");
				}
				{
					jTextFieldName = new JTextField();
					jPanel1.add(getJTextFieldName());
					jTextFieldName.setBounds(111, 30, 255, 21);
				}
				{
					jButtonCancelar = new JButton();
					jPanel1.add(jButtonCancelar);
					jButtonCancelar.setText(messages.getProperty(MessageKeys.CANCEL));
					jButtonCancelar.setBounds(264, 460, 102, 21);
					jButtonCancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if((jTextFieldName.getText().equals(""))||(jTextFieldName.getText()==null)){
								jTextFieldName.setText(null);
							}else if((jTextFuente.getText().equals(""))||(jTextFuente.getText()== null)){
								jTextFieldName.setText(null);
							}else{
								cancel = true;
								dispose();
							}
							cancel = true;
							dispose();							
						}
					});
				}
				{
					jButtonAceptar = new JButton();
					jPanel1.add(jButtonAceptar);
					jButtonAceptar.setText(messages.getProperty(MessageKeys.OK));
					jButtonAceptar.setBounds(150, 460, 102, 21);
					jButtonAceptar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try{
								if(jComboBoxPaper.getSelectedItem()!=null 
										&& jComboBoxPaper.getSelectedItem().equals(KeysUtils.JPEG))
								{
									if(Integer.valueOf(jTextQuality.getText().trim())>100){
										jTextQuality.setText("");
										mensaje(messages.getProperty(MessageKeys.ERROR_VALOR_ERRONEO),messages.getProperty(MessageKeys.ERROR_QUALITY_OUT_OF_RANGE));
										return;
									}
								}
							}
							catch (NumberFormatException ex) {
								jTextQuality.setText("");
								mensaje(messages.getProperty(MessageKeys.ERROR_VALOR_ERRONEO),messages.getProperty(MessageKeys.ERROR_QUALITY_OUT_OF_RANGE));
								return;
							}
							 if((jTextFieldName.getText().equals(""))||(jTextFieldName.getText()==null)){
								mensaje(messages.getProperty(MessageKeys.ERROR_CAMPOS_OBLIGATORIO),messages.getProperty(MessageKeys.ERROR_NOMBRE_OBLIGATORIO));
							}else if((jTextFuente.getText().equals(""))||(jTextFuente.getText()== null)){
								mensaje(messages.getProperty(MessageKeys.ERROR_CAMPOS_OBLIGATORIO),messages.getProperty(MessageKeys.ERROR_FUENTE_OBLIGATORIO));
							}else{
								dispose();
							}
						}
					});
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText(messages.getProperty(MessageKeys.SOURCE)+":");
					jLabel1.setBounds(28, 70, 90, 14);
				}
				{
					jTextFuente = new JTextField();
					jPanel1.add(jTextFuente);
					jTextFuente.setBounds(111, 67, 140, 21);
					jTextFuente.setEditable(false);
				}
				{
					jButtonSelect = new JButton();
					jPanel1.add(jButtonSelect);
					jButtonSelect.setText(messages.getProperty(MessageKeys.SELECT));
					//jButtonSelect.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.SOURCE)));
					jButtonSelect.setBounds(260, 67, 106, 21);
					jButtonSelect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							final SelectDeviceApplet app = new SelectDeviceApplet(
									frame, true);
							app.pack();							
							app.setLocationRelativeTo(null);
							app.setResizable(false);
							app.setTitle(messages.getProperty(MessageKeys.CONFIG) + " > "+ messages.getProperty(MessageKeys.PROFILE) + " > " + messages.getProperty(MessageKeys.NEW) + " > "+ messages.getProperty(MessageKeys.SOURCE));
							app.setVisible(true);
							app.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
								}
								public void windowClosed(WindowEvent e) {
									if(app.getJComboBoxSource().getSelectedItem().toString() != null){
										String text = (String) app.getJComboBoxSource().getSelectedItem();
										jTextFuente.setText(text);
										PropertiesVO properties = ActionScan.getProperties(text);
										
										if(parametrosVO != null && parametrosVO.getDummy()!=null && parametrosVO.getDummy().equals("1")){
											if(parametrosVO.getResolucion()!=null){
												float resolucion = new Float(parametrosVO.getResolucion());
												properties.setResolutionTypes(new float[]{resolucion});
											}
											if(parametrosVO.getTamano()!=null){
												int tamano = new Integer(parametrosVO.getTamano());
												properties.setSupportSizes(new int[]{tamano});
											}
											if(parametrosVO.getColor()!=null){
												int color = new Integer(parametrosVO.getColor());
												properties.setPixTypes(new int[]{color});
											}
											//String tipoDoc = parametrosVO.getTipoDoc();
										}
										
										getJCheckBoxDuplex().setVisible(properties.isSupportDuplex());
										getJCheckBoxADF().setVisible(properties.isSupportADF());
										chargeComboSize(properties);
										chargeComboColor(properties);										
										chargeComboResolution(properties);
										//TODO Cambio a Solo Lectura si dummy == 1
										if(parametrosVO.getDummy()!=null && parametrosVO.getDummy().equals("1")){
											jComboBoxSize.setEnabled(false);
											jComboBoxResolucion.setEnabled(false);
											jComboBoxPaper.setEnabled(false);
											jComboBoxColor.setEnabled(false);
											jCheckBoxUI.setSelected(false);
											jCheckBoxUI.setEnabled(false);
										}
									}
								}
							});
						}
					});
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText(messages.getProperty(MessageKeys.RESOLUTION)+":");
					jLabel2.setBounds(28, 110, 90, 14);
				}
				{
					ComboBoxModel jComboBoxResolucionModel = new DefaultComboBoxModel();
					jComboBoxResolucion = new JComboBox();
					jPanel1.add(jComboBoxResolucion);
					jComboBoxResolucion.setModel(jComboBoxResolucionModel);
					jComboBoxResolucion.setBounds(111, 107, 255, 21);
					jComboBoxResolucion.setEnabled(false);
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3);
					jLabel3.setText(messages.getProperty(MessageKeys.SIZE)+":");
					jLabel3.setBounds(28, 149, 90, 14);
				}
				{
					ComboBoxModel jComboBoxSizeModel = new DefaultComboBoxModel();
					jComboBoxSize = new JComboBox();
					jPanel1.add(getJComboBoxSize());
					jComboBoxSize.setModel(jComboBoxSizeModel);
					jComboBoxSize.setBounds(111, 146, 255, 21);
					jComboBoxSize.setEnabled(false);
				}
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4);
					jLabel4.setText(messages.getProperty(MessageKeys.COLOR)+":");
					jLabel4.setBounds(28, 190, 90, 14);
				}
				{
					ComboBoxModel jComboBoxColorModel = new DefaultComboBoxModel();
					jComboBoxColor = new JComboBox();
					jPanel1.add(getJComboBoxColor());
					jComboBoxColor.setModel(jComboBoxColorModel);
					jComboBoxColor.setBounds(111, 187, 255, 21);
					jComboBoxColor.setEnabled(false);
					jComboBoxColor.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							chargeComboPaper();
						}
					});
				}
				{
					jLabel5 = new JLabel();
					jPanel1.add(jLabel5);
					jLabel5.setText(messages.getProperty(MessageKeys.FORMAT)+":");
					jLabel5.setBounds(28, 231, 90, 14);
				}
				{
					//String labels[] = {KeysUtils.PDF, KeysUtils.JPEG, KeysUtils.TIFF, KeysUtils.TIFF_Multipage };
					String labels[] = {};
					ComboBoxModel jComboBoxPaperModel = new DefaultComboBoxModel(labels);
					jComboBoxPaper = new JComboBox();
					jPanel1.add(getJComboBoxPaper());
					jComboBoxPaper.setModel(jComboBoxPaperModel);
					jComboBoxPaper.setBounds(111, 228, 255, 21);
					jComboBoxPaper.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if((jComboBoxPaper.getSelectedItem()!=null && jComboBoxPaper.getSelectedItem().equals(KeysUtils.JPEG))){								
								//jCheckBoxCompress.setSelected(false);
								//jCheckBoxCompress.setVisible(true);
								jTextQuality.setVisible(true);
								jTextQuality.setEnabled(true);
								jLabelQuality.setVisible(true);
								jLabelRangoQuality.setVisible(true);
							}
							else{								
								//jCheckBoxCompress.setVisible(false);
								jTextQuality.setVisible(false);
								jTextQuality.setText("");
								jTextQuality.setEnabled(false);
								jLabelQuality.setVisible(false);
								jLabelRangoQuality.setVisible(false);
							}
						}
					});		
				}
				{
					jLabelContrast= new JLabel();
					jPanel1.add(jLabelContrast);
					jLabelContrast.setText(messages.getProperty(MessageKeys.CONTRAST)+":");
					jLabelContrast.setBounds(28, 269, 90, 14);
				}
				{
					jLabelContrast1= new JLabel();
					jPanel1.add(jLabelContrast1);
					jLabelContrast1.setText("");
					jLabelContrast1.setBounds(235, 254, 90, 14);
				}
				{
					JLabel icon_contrast_more = new JLabel();
					jPanel1.add(icon_contrast_more);
					icon_contrast_more.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.CONSTRAST_MENOS)));
					icon_contrast_more.setBounds(350, 254, 54, 14);
					//icon_bright_more.setText("asa");
				}
				{
					JLabel icon_contrast_less = new JLabel();
					jPanel1.add(icon_contrast_less);
					icon_contrast_less.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.CONTRAST_MAS)));
					icon_contrast_less.setBounds(112, 254, 54, 14);
					//icon_bright_more.setText("asa");
				}
				{
					scrollContrast =  new JScrollBar( Scrollbar.HORIZONTAL,0,1,-1000,1000);
					jPanel1.add(scrollContrast);
					scrollContrast.setBounds(111, 269, 255, 21);
					scrollContrast.addAdjustmentListener(new AdjustmentListener() {
						public void adjustmentValueChanged(AdjustmentEvent e) {
							 int value = e.getValue();
						      String st = Integer.toString(value);
						      jLabelContrast1.setText(st);
						}
					});
				}
				{
					jLabelBrigth= new JLabel();
					jPanel1.add(jLabelBrigth);
					jLabelBrigth.setText(messages.getProperty(MessageKeys.BRIGHT)+":");
					jLabelBrigth.setBounds(28, 310, 90, 14);
				}
				{
					jLabelBrigth1= new JLabel();
					jPanel1.add(jLabelBrigth1);
					jLabelBrigth1.setText("");
					jLabelBrigth1.setBounds(235, 295, 90, 14);
				}
				{
					JLabel icon_bright_more = new JLabel();
					jPanel1.add(icon_bright_more);
					icon_bright_more.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.BRIGHT_MAS)));
					icon_bright_more.setBounds(350, 295, 54, 14);
					//icon_bright_more.setText("asa");
				}
				{
					JLabel icon_bright_less = new JLabel();
					jPanel1.add(icon_bright_less);
					icon_bright_less.setIcon(new ImageIcon(getClass().getClassLoader().getResource(IconsKeys.BRIGHT_MEMOS)));
					icon_bright_less.setBounds(112, 295, 54, 14);
					//icon_bright_more.setText("asa");
				}
				{
					scrollBright =  new JScrollBar( Scrollbar.HORIZONTAL,0,1,-1000,1000);
					jPanel1.add(scrollBright);
					scrollBright.setBounds(111, 310, 255, 21);
					scrollBright.addAdjustmentListener(new AdjustmentListener() {
						public void adjustmentValueChanged(AdjustmentEvent e) {
							 int value = e.getValue();
						      String st = Integer.toString(value);
						      jLabelBrigth1.setText(st);
						}
					});
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void enableAll() {
		jComboBoxResolucion.setEnabled(true);
		jComboBoxColor.setEnabled(true);
		jComboBoxPaper.setEnabled(true);
		jComboBoxSize.setEnabled(true);
		jCheckBoxADF.setEnabled(true);
	//	jCheckBoxCompress.setEnabled(true);
		scrollBright.setEnabled(true);
		scrollContrast.setEnabled(true);
		jTextQuality.setEnabled(true);
		
	}

	public void disableAll() {
		jComboBoxResolucion.setEnabled(false);
		jComboBoxColor.setEnabled(false);
		jComboBoxPaper.setEnabled(false);
		jComboBoxSize.setEnabled(false);
		jCheckBoxADF.setEnabled(false);
	//	jCheckBoxCompress.setEnabled(false);
		scrollBright.setEnabled(false);
		scrollContrast.setEnabled(false);
		jTextQuality.setEnabled(false);
		
		
	}
	
	public void chargeComboResolution(PropertiesVO properties) {
		jComboBoxResolucion.removeAllItems();
		float[] valores =  properties.getResolutionTypes();
		if (valores != null) {			
			for (int i = 0; i < valores.length; i++) {
				//TODO solo permitimos valos 100,200,300,600, 800 y 1200
				if(valores[i]==100.0 || valores[i]==200.0 || valores[i]==300.0 || valores[i]==600.0 || valores[i]==800.0 || valores[i]==1200.0){
					if(parametrosVO.getResolucion()!=null && parametrosVO.getResolucion().equals(new Float(valores[i]).toString())){
						jComboBoxResolucion.addItem(String.valueOf(valores[i]));
					}else if(parametrosVO.getResolucion()==null){
						jComboBoxResolucion.addItem(String.valueOf(valores[i]));
					}
				}
			}
			if(jComboBoxResolucion.getItemCount() == 0){
				jComboBoxResolucion.addItem(String.valueOf(valores[0]));
			}
			jComboBoxResolucion.setEnabled(true);
		} else {
			jComboBoxResolucion.setEnabled(false);
		}
	}
	/*
	@SuppressWarnings("unchecked")
	public void chargeComboPaper() {
		if(parametrosVO.getTipoDoc()!=null && parametrosVO.getTipoDoc().equals("PDF")){
			String labels[] = {KeysUtils.PDF};
			jComboBoxPaper.setModel(new DefaultComboBoxModel(labels));
		}else{
			HashMap map = Capabilities.getColorCombo(0, 1);
			try{
				if((jComboBoxColor.getSelectedItem().equals(map.get("0")))){
					String labels[] = {KeysUtils.PDF, KeysUtils.TIFF, KeysUtils.TIFF_Multipage };
					jComboBoxPaper.setModel(new DefaultComboBoxModel(labels));
				}
				else{								
					String labels[] = {KeysUtils.PDF, KeysUtils.JPEG, KeysUtils.TIFF, KeysUtils.TIFF_Multipage };
					jComboBoxPaper.setModel(new DefaultComboBoxModel(labels));
				}
			}catch (Exception ex) {}
		}
	}
	*/
	@SuppressWarnings("unchecked")
	public void chargeComboPaper() {
		if(parametrosVO.getTipoDoc()!=null){
			String labels[] = {parametrosVO.getTipoDoc()};
			jComboBoxPaper.setModel(new DefaultComboBoxModel(labels));
		}else{
			HashMap map = Capabilities.getColorCombo(0, 1);
			try{
				if((jComboBoxColor.getSelectedItem().equals(map.get("0")))){
					String labels[] = {KeysUtils.PDF, KeysUtils.TIFF, KeysUtils.TIFF_Multipage };
					jComboBoxPaper.setModel(new DefaultComboBoxModel(labels));
					jTextQuality.setEnabled(false);
					jTextQuality.setVisible(false);
					jLabelQuality.setVisible(false);
					jLabelRangoQuality.setVisible(false);
				}
				else{								
					String labels[] = {KeysUtils.PDF, KeysUtils.JPEG, KeysUtils.TIFF, KeysUtils.TIFF_Multipage };
					jComboBoxPaper.setModel(new DefaultComboBoxModel(labels));
				}
			}catch (Exception ex) {}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void chargeComboSize(PropertiesVO properties) {
		jComboBoxSize.removeAllItems();
		HashMap map = Capabilities.getPaperSizeCombo(0, 1);
		int[] valores =  properties.getSupportSizes();
		if (valores != null) {			
			for (int i = 0; i < valores.length; i++) {
				//TODO solo permitimos valos A4 o A3
				//if(valores[i]==1 || valores[i]==11){
					if(parametrosVO.getTamano()!=null && parametrosVO.getTamano().equals(new Integer(valores[i]).toString())){
						Object value = map.get(String.valueOf(valores[i]));
						if (value != null){
							jComboBoxSize.addItem(value);
						}
					}else if(parametrosVO.getTamano()==null){
						Object value = map.get(String.valueOf(valores[i]));
						if (value != null){
							jComboBoxSize.addItem(value);
						}
					}
				//}
			}
			if(jComboBoxSize.getItemCount() == 0){
				jComboBoxSize.addItem(map.get(String.valueOf(valores[0])));
			}
			jComboBoxSize.setEnabled(true);
		} else {
			jComboBoxSize.setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	public void chargeComboColor(PropertiesVO properties) {
		jComboBoxColor.removeAllItems();
		HashMap map = Capabilities.getColorCombo(0, 1);
		int[] valores =  properties.getPixTypes();
		if (valores != null) {			
			for (int i = 0; i < valores.length; i++) {
				//TODO solo permitimos b/n, gris o color
				if(valores[i]==0 || valores[i]==1 || valores[i]==2){
					if(parametrosVO.getColor()!=null && parametrosVO.getColor().equals(new Integer(valores[i]).toString())){
						Object value = map.get(String.valueOf(valores[i]));
						if (value != null) {
							jComboBoxColor.addItem(value);
						}
					}else if(parametrosVO.getColor()==null){
						Object value = map.get(String.valueOf(valores[i]));
						if (value != null) {
							jComboBoxColor.addItem(value);
						}
					}
				}
			}
			jComboBoxColor.setEnabled(true);
		} else {
			jComboBoxColor.setEnabled(false);
		}
	}

	// GETTERS && SETTERS
	public JTextField getJTextFieldName() {
		return jTextFieldName;
	}

	public JCheckBox getJCheckBoxUI() {
		return jCheckBoxUI;
	}

	public JTextField getJTextFuente() {
		return jTextFuente;
	}

	public void setJTextFuente(JTextField textFuente) {
		jTextFuente = textFuente;
	}

	public JComboBox getJComboBoxSize() {
		return jComboBoxSize;
	}

	public boolean isCancel() {
		return cancel;
	}

	public JComboBox getJComboBoxPaper() {
		return jComboBoxPaper;
	}

	public JComboBox getJComboBoxResolucion() {
		return jComboBoxResolucion;
	}

	public JComboBox getJComboBoxColor() {
		return jComboBoxColor;
	}

	public JCheckBox getJCheckBoxDuplex() {
		return jCheckBoxDuplex;
	}
	
	public JCheckBox getJCheckBoxADF() {
		return jCheckBoxADF;
	}

	public JPanel getJPanel1() {
		return jPanel1;
	}

	public void setJPanel1(JPanel panel1) {
		jPanel1 = panel1;
	}

	public JLabel getJLabel4() {
		return jLabel4;
	}

	public void setJLabel4(JLabel label4) {
		jLabel4 = label4;
	}

	public JLabel getJLabel3() {
		return jLabel3;
	}

	public void setJLabel3(JLabel label3) {
		jLabel3 = label3;
	}

	public JLabel getJLabel2() {
		return jLabel2;
	}

	public void setJLabel2(JLabel label2) {
		jLabel2 = label2;
	}

	public JButton getJButtonSelect() {
		return jButtonSelect;
	}

	public void setJButtonSelect(JButton buttonSelect) {
		jButtonSelect = buttonSelect;
	}

	public JLabel getJLabel1() {
		return jLabel1;
	}

	public void setJLabel1(JLabel label1) {
		jLabel1 = label1;
	}

	public JButton getJButtonAceptar() {
		return jButtonAceptar;
	}

	public void setJButtonAceptar(JButton buttonAceptar) {
		jButtonAceptar = buttonAceptar;
	}

	public JLabel getNombre() {
		return Nombre;
	}

	public void setNombre(JLabel nombre) {
		Nombre = nombre;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setJTextFieldName(JTextField textFieldName) {
		jTextFieldName = textFieldName;
	}

	public void setJComboBoxResolucion(JComboBox comboBoxResolucion) {
		jComboBoxResolucion = comboBoxResolucion;
	}

	public void setJComboBoxColor(JComboBox comboBoxColor) {
		jComboBoxColor = comboBoxColor;
	}

	public void setJComboBoxSize(JComboBox comboBoxSize) {
		jComboBoxSize = comboBoxSize;
	}

	public void setJCheckBoxUI(JCheckBox checkBoxUI) {
		jCheckBoxUI = checkBoxUI;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public JLabel getJLabel5() {
		return jLabel5;
	}

	public JButton getJButtonCancelar() {
		return jButtonCancelar;
	}

	public Properties getMessages() {
		return messages;
	}

	public JScrollBar getScrollContrast() {
		return scrollContrast;
	}

	public JScrollBar getScrollBright() {
		return scrollBright;
	}

	public JLabel getJLabelContrast() {
		return jLabelContrast;
	}

	public JLabel getJLabelBrigth() {
		return jLabelBrigth;
	}

	public JTextField getJTextQuality() {
		return jTextQuality;
	}

	public JLabel getJLabelQuality() {
		return jLabelQuality;
	}

	public JCheckBox getJCheckBoxCompress() {
		return jCheckBoxCompress;
	}

	/**
	 * Metodo que lanza un mensaje de informaci�n por pantalla
	 * 
	 * @param titulo
	 * @param mensaje
	 */
	private void mensaje(String titulo, String mensaje) {		
		JOptionPane.showMessageDialog(frame, Messages.getString(mensaje),
				Messages.getString(titulo), JOptionPane.ERROR_MESSAGE);
	}

	public void setParametrosVO(ParametrosVO parametrosVO) {
		this.parametrosVO = parametrosVO;
	}

	public ParametrosVO getParametrosVO() {
		return parametrosVO;
	}

	public void setjLabelContrast1(JLabel jLabelContrast1) {
		this.jLabelContrast1 = jLabelContrast1;
	}

	public JLabel getjLabelContrast1() {
		return jLabelContrast1;
	}

	public void setjLabelBrigth1(JLabel jLabelBrigth1) {
		this.jLabelBrigth1 = jLabelBrigth1;
	}

	public JLabel getjLabelBrigth1() {
		return jLabelBrigth1;
	}
}
