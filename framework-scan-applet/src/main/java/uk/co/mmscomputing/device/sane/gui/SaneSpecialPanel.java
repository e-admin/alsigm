package uk.co.mmscomputing.device.sane.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import uk.co.mmscomputing.device.sane.SaneDevice;
import uk.co.mmscomputing.device.sane.jsane;

public class SaneSpecialPanel extends JPanel{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static private String adfmodestr  = jsane.getResource("gui.SaneSpecialPanel.adfmodestr");
  static private String adfcountstr = jsane.getResource("gui.SaneSpecialPanel.adfcountstr");

  @SuppressWarnings({ "unchecked", "unused" })
private HashMap    options=null;
  @SuppressWarnings("unused")
private SaneDevice scanner;

  @SuppressWarnings("unchecked")
public SaneSpecialPanel(SaneDevice scanner,HashMap options){
    this.options=options;
    this.scanner=scanner;

    setLayout(new BorderLayout());

    JTabbedPane      tp   = new JTabbedPane();
    SaneNoDocsPanel  sndp = new SaneNoDocsPanel(scanner);
    SaneMaxDocsPanel smdp = new SaneMaxDocsPanel(scanner);

    tp.insertTab(adfmodestr,null,sndp,null,0);
    tp.insertTab(adfcountstr,null,smdp,null,0);
    add(tp,BorderLayout.CENTER);
  }
}