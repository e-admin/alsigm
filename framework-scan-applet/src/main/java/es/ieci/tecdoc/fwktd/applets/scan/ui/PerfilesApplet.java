package es.ieci.tecdoc.fwktd.applets.scan.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionScan;
import es.ieci.tecdoc.fwktd.applets.scan.key.MessageKeys;
import es.ieci.tecdoc.fwktd.applets.scan.utils.FileUtils;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PropertiesVO;

public class PerfilesApplet extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JList jList1;
	private JButton jButtonDelete;
	private JButton jButtonPropery;
	private JLabel jLabel1;
	private JButton jButtonAceptar;
	private PerfilesVO perfiles;
	private JFrame frame;
	//final NewEditPerfilApplet app = new NewEditPerfilApplet(frame, true);
	private JButton jButtonAdd;
	private JButton jButtonExit;
	private Properties messages;
	private ParametrosVO parametrosVO;
	private String perfilEditado = null;

	/**
	 * Auto-generated main method to display this JDialog
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				PerfilesApplet inst = new PerfilesApplet(frame);
				inst.setVisible(true);
			}
		});
	}

	public PerfilesApplet(JFrame frame, Properties messages) {
		super(frame);
		this.messages = messages;
		this.frame = frame;
		initGUI();
	}

	public PerfilesApplet(JFrame frame) {
		super(frame);
		this.frame = frame;
		initGUI();
	}

	public PerfilesApplet(JFrame frame, boolean state, PerfilesVO perfiles,	Properties messages, ParametrosVO parametrosVO) {
		super(frame, state);
		this.messages = messages;
		this.perfiles = perfiles;
		this.parametrosVO = parametrosVO;
		initGUI();
		if (perfiles.getHashPerfiles().size() == 0) {
			changeState(false);
		}
	}

	public PerfilesApplet(boolean state, PerfilesVO perfiles,	Properties messages, ParametrosVO parametrosVO) {
		super(new JFrame(), state);
		this.messages = messages;
		this.perfiles = perfiles;
		this.parametrosVO = parametrosVO;
		initGUI();
		if (perfiles.getHashPerfiles().size() == 0) {
			changeState(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void initGUI() {
		try {
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(null);
				jPanel1.setPreferredSize(new java.awt.Dimension(470, 230));

				{
					DefaultListModel jListPerfilesModel = new DefaultListModel();
					Iterator it = perfiles.getHashPerfiles().values().iterator();
					PerfilVO perfil;
					while (it.hasNext()) {
						perfil = (PerfilVO) it.next();
						jListPerfilesModel.addElement(perfil.getName());
					}

					jList1 = new JList();
					jList1.setModel(jListPerfilesModel);
					// jList1.setPreferredSize(new java.awt.Dimension(270,
					// 151));
					jList1.setSelectedValue(perfiles.getSelectName(), true);
				}
				{
					jScrollPane1 = new JScrollPane(jList1);
					jScrollPane1.setBounds(26, 48, 270, 151);
					jScrollPane1.getViewport().add(jList1);
					jScrollPane1.getHorizontalScrollBar().setEnabled(false);
					if (perfiles.getHashPerfiles().size() < 8) {
						jScrollPane1.getVerticalScrollBar().setEnabled(false);
					} else {
						jScrollPane1.getVerticalScrollBar().setEnabled(true);
					}
					jPanel1.add(jScrollPane1, null);
					jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText(messages.getProperty(MessageKeys.PROFILE)+ ":");
					jLabel1.setBounds(26, 22, 200, 14);
				}

				{
					jButtonAceptar = new JButton();
					jPanel1.add(jButtonAceptar);
					jButtonAceptar.setText(messages.getProperty(MessageKeys.SELECT));
					jButtonAceptar.setBounds(339, 48, 102, 21);
					jButtonAceptar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							perfiles.setSelectName(jList1.getSelectedValue().toString());
							FileUtils.savePerfiles(perfiles);
							dispose();
						}
					});
				}
				{
					jButtonAdd = new JButton();
					jPanel1.add(jButtonAdd);
					jButtonAdd.setText(messages.getProperty(MessageKeys.NEW) + "...");
					jButtonAdd.setBounds(339, 80, 102, 21);
					jButtonAdd.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							NewEditPerfilApplet app = new NewEditPerfilApplet(frame, true, messages, parametrosVO);
							app.setTitle(messages.getProperty(MessageKeys.CONFIG) + " > " + messages.getProperty(MessageKeys.PROFILE) + " > " + messages.getProperty(MessageKeys.NEW));
							app.pack();
							app.setLocationRelativeTo(null);
							app.setResizable(false);
							app.setVisible(true);

							app.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {

								}

								public void windowClosed(WindowEvent e) {
									NewEditPerfilApplet app = (NewEditPerfilApplet)e.getComponent();
									if ((app.getJTextFieldName().getText() != null)	&& (!(app.getJTextFieldName().getText().equals("")))) {
										PerfilVO perfilVO = new PerfilVO();
										perfilVO.setName(app.getJTextFieldName().getText());
										perfilVO.setEnableUI(app.getJCheckBoxUI().isSelected());
										perfilVO.setDevice(app.getJTextFuente().getText());
										perfilVO.setResolution((String) app.getJComboBoxResolucion().getSelectedItem());
										perfilVO.setSize((String) app.getJComboBoxSize().getSelectedItem());
										perfilVO.setColor((String) app.getJComboBoxColor().getSelectedItem());
										perfilVO.setFormat((String) app.getJComboBoxPaper().getSelectedItem());
										try{
											perfilVO.setQuality(Integer.parseInt(app.getJTextQuality().getText()));
										}catch(Exception ex){}
										perfilVO.setContrast(app.getScrollContrast().getValue());
										perfilVO.setDuplex(app.getJCheckBoxDuplex().isSelected());
										perfilVO.setAdf(app.getJCheckBoxADF().isSelected());
										//perfilVO.setCompress(app.getJCheckBoxCompress().isSelected());
										perfiles.getHashPerfiles().put(perfilVO.getName(), perfilVO);
										perfiles.setSelectName(perfilVO.getName());
										FileUtils.savePerfiles(perfiles);
										repaintList();
										changeState(true);
										if (perfiles.getHashPerfiles().size() > 7) {
											jScrollPane1.getVerticalScrollBar().setEnabled(true);
										}
										app.removeWindowListener(this);

									}
								}
							});
						}
					});
				}
				{
					jButtonPropery = new JButton();
					jPanel1.add(jButtonPropery);
					jButtonPropery.setText(messages.getProperty(MessageKeys.EDIT) + "...");
					jButtonPropery.setBounds(339, 112, 102, 21);
					jButtonPropery.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							NewEditPerfilApplet app = new NewEditPerfilApplet(frame, true, messages, parametrosVO);
							PerfilVO perfilVO = (PerfilVO) perfiles.getHashPerfiles().get(jList1.getSelectedValue().toString());
							perfilEditado = perfilVO.getName();
							app.pack();
							app.setTitle(messages.getProperty(MessageKeys.CONFIG) + " > " + messages.getProperty(MessageKeys.PROFILE) + " > " + messages.getProperty(MessageKeys.EDIT));
							// propiedades
							app.getJTextFieldName().setText(perfilVO.getName());
							app.getJTextFuente().setText(perfilVO.getDevice());
							app.getJCheckBoxUI().setEnabled(perfilVO.isEnableUI());

							PropertiesVO properties = ActionScan.getProperties(perfilVO.getDevice());
							app.chargeComboSize(properties);
							app.getJCheckBoxDuplex().setVisible(properties.isSupportDuplex());
							app.getJCheckBoxADF().setVisible(properties.isSupportADF());
							app.chargeComboColor(properties);
							app.chargeComboResolution(properties);
							app.getJCheckBoxUI().setSelected(perfilVO.isEnableUI());

							app.getJCheckBoxDuplex().setSelected(perfilVO.isDuplex());
							app.getJCheckBoxADF().setSelected(perfilVO.isAdf());
							app.getJComboBoxColor().setSelectedItem(perfilVO.getColor());
							app.getJComboBoxResolucion().setSelectedItem(perfilVO.getResolution());
							app.getJComboBoxSize().setSelectedItem(perfilVO.getSize());
							app.getJTextQuality().setText(String.valueOf(perfilVO.getQuality()));
						//	app.getJCheckBoxCompress().setSelected(perfilVO.isCompress());
							app.getJComboBoxPaper().setSelectedItem(perfilVO.getFormat());
							app.getScrollContrast().setValue((int)perfilVO.getContrast());
							app.getjLabelContrast1().setText("" + perfilVO.getContrast());
							app.getScrollBright().setValue((int)perfilVO.getBright());
							app.getjLabelBrigth1().setText("" + perfilVO.getBright());
							app.getJCheckBoxUI().setEnabled(true);

							//TODO TODO Cambio a Solo Lectura si dummy == 1
							if(parametrosVO.getDummy()!=null && parametrosVO.getDummy().equals("1")){
								app.getJComboBoxSize().setEnabled(false);
								app.getJComboBoxResolucion().setEnabled(false);
								app.getJComboBoxPaper().setEnabled(false);
								app.getJComboBoxColor().setEnabled(false);
								app.getJCheckBoxUI().setSelected(false);
								app.getJCheckBoxUI().setEnabled(false);
							}

							//Si está marcado UI, deshabilitamos todo
							if(app.getJCheckBoxUI().isSelected()){
								app.disableAll();
							}

							app.setLocationRelativeTo(null);
							app.setResizable(false);
							app.setVisible(true);

							app.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
								}
								public void windowClosed(WindowEvent e) {
									NewEditPerfilApplet app = (NewEditPerfilApplet)e.getComponent();

									if(!app.isCancel()){
										PerfilVO perfilVO = new PerfilVO();
										perfilVO.setName(app.getJTextFieldName().getText());
										perfilVO.setEnableUI(app.getJCheckBoxUI().isSelected());
										perfilVO.setDevice(app.getJTextFuente().getText());
										perfilVO.setResolution((String) app.getJComboBoxResolucion().getSelectedItem());
										perfilVO.setSize((String) app.getJComboBoxSize().getSelectedItem());
										perfilVO.setColor((String) app.getJComboBoxColor().getSelectedItem());
										perfilVO.setContrast(app.getScrollContrast().getValue());
										perfilVO.setBright(app.getScrollBright().getValue());
										try{
											perfilVO.setQuality(Integer.parseInt(app.getJTextQuality().getText()));
										}catch(Exception ex){}
										perfilVO.setFormat((String) app.getJComboBoxPaper().getSelectedItem());
										perfilVO.setDuplex(app.getJCheckBoxDuplex().isSelected());
										perfilVO.setAdf(app.getJCheckBoxADF().isSelected());
									//	perfilVO.setCompress(app.getJCheckBoxCompress().isSelected());
										perfiles.getHashPerfiles().remove(perfilEditado);
										perfiles.getHashPerfiles().put(perfilVO.getName(), perfilVO);
										perfiles.setSelectName(perfilVO.getName());
										FileUtils.savePerfiles(perfiles);
										repaintList();
										app.removeWindowListener(this);

									}
								}
							});
						}
					});
				}
				{
					jButtonDelete = new JButton();
					jPanel1.add(jButtonDelete);
					jButtonDelete.setText(messages.getProperty(MessageKeys.DELETE));
					jButtonDelete.setBounds(339, 144, 102, 21);
					jButtonDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							perfiles.getHashPerfiles().remove(jList1.getSelectedValue());
							if (perfiles.getHashPerfiles().size() == 0) {
								changeState(false);
							}
							if (perfiles.getHashPerfiles().size() < 8) {
								jScrollPane1.getVerticalScrollBar().setEnabled(false);
								jList1.setSelectedIndex(0);
								repaintList();
							}
							repaintList();
						}
					});
				}
				{
					jButtonExit = new JButton();
					jPanel1.add(jButtonExit);
					jButtonExit.setText(messages.getProperty(MessageKeys.CLOSE));
					jButtonExit.setBounds(339, 176, 102, 21);
					jButtonExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							FileUtils.savePerfiles(perfiles);
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

	@SuppressWarnings("unchecked")
	private void repaintList() {
		DefaultListModel jListPerfilesModel = new DefaultListModel();
		Iterator it = perfiles.getHashPerfiles().values().iterator();
		PerfilVO perfil;
		while (it.hasNext()) {
			perfil = (PerfilVO) it.next();
			jListPerfilesModel.addElement(perfil.getName());
		}
		jList1.setModel(jListPerfilesModel);
		jScrollPane1.repaint();
	}

	private void changeState(boolean state) {
		jButtonPropery.setEnabled(state);
		jButtonDelete.setEnabled(state);
		jButtonAceptar.setEnabled(state);
	}

	public void setParametrosVO(ParametrosVO parametrosVO) {
		this.parametrosVO = parametrosVO;
	}

	public ParametrosVO getParametrosVO() {
		return parametrosVO;
	}
}
