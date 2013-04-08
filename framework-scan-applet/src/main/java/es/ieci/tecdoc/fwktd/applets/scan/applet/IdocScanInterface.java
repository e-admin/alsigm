package es.ieci.tecdoc.fwktd.applets.scan.applet;

import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import es.ieci.tecdoc.fwktd.applets.scan.utils.Cropping;
import es.ieci.tecdoc.fwktd.applets.scan.vo.FileVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.OptionsUIVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.ParametrosVO;
import es.ieci.tecdoc.fwktd.applets.scan.vo.PerfilesVO;

public interface IdocScanInterface {

	// Getters and Setters
	public JPanel getJPanelBackground();
	
	public JMenuItem getMenuItemExit();

	public JMenu getJMenuCapture();

	public JMenuItem getMenuItemScan();

	public JMenuItem getMenuItemScanReplace();

	public JMenuItem getMenuItemScanAdd();

	public JMenuItem getMenuItemScanFinish();

	public JMenuItem getMenuItemScanDelete();
	
	public JMenuBar getJMenuBarScan();

	public JMenu getJMenuOpen();

	public JMenu getJMenuImage();

	public JToolBar getJToolBarImages();

	public JScrollPane getJScrollPaneIcon();

	public JMenu getJMenuHelp();

	public JMenu getJMenuConfig();

	public FileVO getFileVO();

	public PerfilesVO getPerfiles();

	public Properties getMessages();

	public JButton getJButtonScan();

	public JButton getJButtonNext();

	public JButton getJButtonPrevious();

	public JButton getJButtonScale();

	public JButton getJButtonScaleHorizontal();

	public JButton getJButtonScaleVertical();

	public JButton getJButtonRote();

	public JButton getJButtonZoom();

	public JButton getJButtonOutZoom();

	public JButton getJButtonDelete();

	public JButton getJButtonOriginal();

	public JButton getJButtonSalir();

	public JSeparator getJSeparator();

	public JLabel getJLabelPage();

	public JTextField getJTextFuente();

	public JMenuItem getMenuItemSave();
	public JMenuItem getMenuItemOpenFile();

	public JMenuItem getMenuItemNewFile();

	public JMenuItem getMenuItemAddFile();

	public JMenuItem getMenuItemInZoom();

	public JMenuItem getMenuItemOutZoom();

	public JMenuItem getMenuItemScale();

	public JMenuItem getMenuItemOriginal();

	public JMenuItem getMenuItemRotate();
	public JMenuItem getMenuItemScaleVertical();

	public JMenuItem getMenuItemScaleHorizontal();
	public JMenuItem getMenuItemAcercaDe();

	public JMenuItem getMenuItemProfile();

	public JMenuItem getMenuItemCrop();

	public Cropping getCrop();
	public void setCrop(Cropping crop);
	public JMenuItem getMenuItemSource();

	public OptionsUIVO getOptionsUI();

	public JButton getJButtonCrop();
	public JButton getJButtonOK();
	public JButton getJButtonCancel();

	public ParametrosVO getParametrosVO();
	public void setParametrosVO(ParametrosVO parametrosVO);

	public void repaint();

	
}
