package uk.co.mmscomputing.device.sane.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import uk.co.mmscomputing.device.sane.jsane;
import uk.co.mmscomputing.device.sane.SaneDeviceManager;

public class SaneSelectPanel extends JPanel implements ActionListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static  private String    selecttitlestr = jsane.getResource("gui.SaneSelectPanel.selecttitlestr");
  static  private String    selectbutstr   = jsane.getResource("gui.SaneSelectPanel.selectbutstr");
  static  private String    selectframestr = jsane.getResource("gui.SaneSelectPanel.selectframestr");

  static  private String    devicename="";
  private JFrame            dialog=null;
  private JList             list;
  private SaneDeviceManager manager;

  public SaneSelectPanel(SaneDeviceManager manager,String[] devices){
    
	//this.dialog=dialog;
    this.manager=manager;
    setLayout(new BorderLayout());

    list=new JList(devices);
    list.setMinimumSize(new Dimension(200,100));
    list.setVisibleRowCount(6);
    list.setBorder(new TitledBorder(selecttitlestr));
    add(new JScrollPane(list),BorderLayout.CENTER);     

    JButton button=new JButton(selectbutstr);
    button.addActionListener(this);
    add(button,BorderLayout.SOUTH);     

    if((devicename.equals(""))&&(devices.length>0)){
      devicename=devices[0];
    }
    manager.setDevice(devicename);
    list.setSelectedValue(devicename,true);       
  }

  public void actionPerformed(ActionEvent e){
    String str=(String)list.getSelectedValue();
    if(str!=null){ manager.setDevice(str);}
    if(dialog!=null){dialog.dispose();}
  }

  public void showDialog(){
    dialog=new JFrame(selectframestr);
    dialog.getContentPane().add(this);
    dialog.pack();
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }

}

