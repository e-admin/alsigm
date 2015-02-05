package uk.co.mmscomputing.device.sane.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.mmscomputing.device.sane.SaneConstants;
import uk.co.mmscomputing.device.sane.SaneDevice;
import uk.co.mmscomputing.device.sane.jsane;
import uk.co.mmscomputing.device.sane.option.DescriptorPanel;

public class SaneNoDocsPanel extends JPanel implements SaneConstants, ActionListener, ChangeListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static private String adfmodestr      = jsane.getResource("gui.SaneSpecialPanel.adfmodestr");
  static private String adfmodedescstr  = jsane.getResource("gui.SaneNoDocsPanel.adfmodedescstr");

  protected SaneDevice scanner;
  protected JCheckBox  cb;
 
  public SaneNoDocsPanel(SaneDevice scanner){
    this.scanner=scanner;

    setLayout(new BorderLayout());

    cb=new JCheckBox(adfmodestr,scanner.getADFMode());
    cb.addActionListener(this);

    JPanel p=new JPanel();
    p.setLayout(new BorderLayout());
    p.add(cb,BorderLayout.CENTER);

    p.setBorder(new TitledBorder(DescriptorPanel.valstr));
    add(p,BorderLayout.NORTH);

    add(newDescriptionPanel(adfmodedescstr),BorderLayout.CENTER);
  }

  public void paint(Graphics gc){
    cb.setSelected(scanner.getADFMode());
    super.paint(gc);
  }

  private JComponent newDescriptionPanel(String desc){
    JTextArea ta=new JTextArea(desc);
    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    JScrollPane sp=new JScrollPane(ta);
    JPanel p=new JPanel();
    p.setLayout(new BorderLayout());
    p.setBorder(new TitledBorder(DescriptorPanel.descstr));
    p.add(sp);
    return p;
  }

  public void actionPerformed(ActionEvent e){
    JCheckBox cb=(JCheckBox)e.getSource();
    boolean waitForStatusNoDocs=cb.isSelected();
//  System.err.println(getClass().getName()+":\n\twaitForStatusNoDocs "+waitForStatusNoDocs);
    scanner.setADFMode(waitForStatusNoDocs);
  }

  public void stateChanged(ChangeEvent e){}
  }
