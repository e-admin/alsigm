package uk.co.mmscomputing.device.sane.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import uk.co.mmscomputing.device.sane.OptionDescriptor;
import uk.co.mmscomputing.device.sane.SaneConstants;
import uk.co.mmscomputing.device.sane.SaneDevice;
import uk.co.mmscomputing.device.sane.SaneIOException;
import uk.co.mmscomputing.device.sane.jsane;
import uk.co.mmscomputing.device.sane.option.Descriptor;

@SuppressWarnings("serial")
public class SaneAcquirePanel extends javax.swing.JDialog{

  static private String previewtabstr        = jsane.getResource("gui.SaneAcquirePanel.previewtabstr");
  static private String previewbutstr        = jsane.getResource("gui.SaneAcquirePanel.previewbutstr");
  static private String scanstr              = jsane.getResource("gui.SaneAcquirePanel.scanstr");
  static private String cancelstr            = jsane.getResource("gui.SaneAcquirePanel.cancelstr");
  static private String progressbartitlestr  = jsane.getResource("gui.SaneAcquirePanel.progressbartitlestr");
  static private String acquireframetitlestr = jsane.getResource("gui.SaneAcquirePanel.acquireframetitlestr");


  private SaneDevice         scanner;
  private SanePreviewPanel   preview;
  private SaneSpecialPanel   special;
  private JTabbedPane        tp;
  @SuppressWarnings("unchecked")
  private HashMap            options;

  @SuppressWarnings("unchecked")
public SaneAcquirePanel(SaneDevice device)throws SaneIOException{
	super(new JFrame(acquireframetitlestr), true);
    scanner=device;
    options=new HashMap();

    initGUI();
    //this.pack();
    //this.setTitle(acquireframetitlestr);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setSize(400,500);
    this.setLocationRelativeTo(null);
	this.setVisible(true);
  }

  private void initGUI() {
	  	setLayout(new BorderLayout());
	  
	    tp=new JTabbedPane();
	    buildTab();
	    add(tp,BorderLayout.CENTER);

	    JPanel p=new JPanel();
	    p.setLayout(new BorderLayout());

	    JProgressBar pbar=new JProgressBar();
	    pbar.setBorder(new TitledBorder(progressbartitlestr));
	    pbar.setStringPainted(true);
	    pbar.setMinimum(0);
	    pbar.setString(scanner.getName());
	    p.add(pbar,BorderLayout.CENTER);
	    scanner.setProgressBar(pbar);

	    JPanel bp=new JPanel();
	    bp.setLayout(new GridLayout(1,3));

	    JButton button;

	    button=new JButton(previewbutstr);
	    button.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "preview"));
	    bp.add(button);

	    button=new JButton(scanstr);
	    button.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "scan"));
	    bp.add(button);

	    button=new JButton(cancelstr);
	    button.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "cancelScan"));
	    bp.add(button);

	    p.add(bp,BorderLayout.SOUTH);
	    add(p,BorderLayout.SOUTH);
  }
  
  
public void scan(){       
	this.dispose();
}
  
public void cancelScan(){
	scanner.setCancel(true); 
	this.dispose();
}
  
public void preview(){
	new Thread(preview).start();
}      // start reading image data into preview panel

  @SuppressWarnings("unchecked")
private int build(JTabbedPane parent, OptionDescriptor[] list, int no, boolean group){
    while(no<list.length){
      Descriptor od=(Descriptor)list[no];
      if(od!=null){
        if(od.getType()==SaneConstants.SANE_TYPE_GROUP){
          if(group){
            return no-1;  
          }else{
            JTabbedPane tp=new JTabbedPane();
            no=build(tp,list,no+1,true);
            if(tp.getTabCount()>0){
              parent.addTab(od.getTitle(),tp);
            }
          }
        }else{
          try{
            JComponent gui=od.getGUI();
            options.put(od.getName(),od);
            parent.addTab(od.getTitle(),gui);
          }catch(Exception e){                  // silently ignore broken options
//            System.err.println("Cannot create option : "+od.getName()+"\n"+e.getMessage());
            e.printStackTrace();
          }
        }
      }
      no++;
    }
    return no;
  }

  public void buildTab(){
    tp.removeAll();
    try{
      build(tp,scanner.getOptionDescriptors(),1,false);
    }catch(Exception e){
//      System.out.println("9\b"+getClass().getName()+".build:\n\t"+e);
      e.printStackTrace();
    }
    special=new SaneSpecialPanel(scanner,options);
    tp.insertTab("jsane-Special",null,special,null,0);
    preview=new SanePreviewPanel(scanner,options);
    tp.insertTab(previewtabstr,null,preview,null,0);
    tp.setSelectedIndex(0);
  }
}
