package uk.co.mmscomputing.device.twain;

import java.beans.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import uk.co.mmscomputing.util.*;
import uk.co.mmscomputing.device.scanner.*;

public class TwainPanel extends JComponent implements TwainConstants,ScannerListener{

  Scanner scanner=null;
  JButton acqbutton=null;
  JButton selbutton=null;
  JCheckBox guicheckbox=null;

  public TwainPanel(Scanner scanner,int mode)throws TwainIOException{
/*
    setLayout(new GridLayout(0,1));
    acqbutton=new JButton("acquire",new JarImageIcon(getClass(),"32x32/scanner.png"));
    acqbutton.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "acquire"));
    add(acqbutton);

    JPanel p=new JPanel();
    p.setBorder(BorderFactory.createEtchedBorder());
    guicheckbox = new JCheckBox("Enable GUI");
    guicheckbox.setSelected(true);
    p.add(guicheckbox);
    add(p);

    selbutton=new JButton("select",new JarImageIcon(getClass(),"32x32/list.png"));
    selbutton.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "select"));
    add(selbutton);
*/
    this.scanner=scanner;

    switch(mode){
    case 0:
      acqbutton=new JButton("acquire");
      selbutton=new JButton("select");
      setLayout(new GridLayout(0,1));
      break;
    case 1:
      acqbutton=new JButton("acquire");
      selbutton=new JButton("select");
      setLayout(new GridLayout(1,0));
      break;
    case 2:
      acqbutton=new JButton("acquire",new JarImageIcon(getClass(),"16x16/scanner.png"));
      selbutton=new JButton("select",new JarImageIcon(getClass(),"16x16/list.png"));
      setLayout(new GridLayout(0,1));
      break;
    case 3:
      acqbutton=new JButton("acquire",new JarImageIcon(getClass(),"16x16/scanner.png"));
      selbutton=new JButton("select",new JarImageIcon(getClass(),"16x16/list.png"));
      setLayout(new GridLayout(1,0));
      break;
    case 4:
      acqbutton=new JButton("acquire",new JarImageIcon(getClass(),"32x32/scanner.png"));
      selbutton=new JButton("select",new JarImageIcon(getClass(),"32x32/list.png"));
      setLayout(new GridLayout(0,1));
      break;
    case 5:
      acqbutton=new JButton("acquire",new JarImageIcon(getClass(),"32x32/scanner.png"));
      selbutton=new JButton("select",new JarImageIcon(getClass(),"32x32/list.png"));
      setLayout(new GridLayout(1,0));
      break;
    }

    acqbutton.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "acquire"));
    add(acqbutton);
/*
    guicheckbox = new JCheckBox("Enable GUI");
    guicheckbox.setSelected(true);
    JPanel p=new JPanel();
    p.setBorder(BorderFactory.createEtchedBorder());
    p.add(guicheckbox);
    add(p);
*/
    guicheckbox = new JCheckBox("Enable GUI");
    guicheckbox.setSelected(true);
    add(guicheckbox);

    selbutton.addActionListener((ActionListener)EventHandler.create(ActionListener.class, this, "select"));
    add(selbutton);

    scanner.addListener(this);

    if(jtwain.getSource().isBusy()){                   // applets might not be in state 3
      acqbutton.setEnabled(false);
      selbutton.setEnabled(false);
      guicheckbox.setEnabled(false);
    }
  }

  public void acquire(){		                           // acquire BufferedImage from selected/default twain source
    try{
      scanner.acquire();
    }catch(ScannerIOException e){
      scanner.fireExceptionUpdate(e);
    }
  }

  public void select(){                   		         // select twain data source
    try{
      scanner.select();
    }catch(ScannerIOException e){
      scanner.fireExceptionUpdate(e);
    }
  }

  public void update(ScannerIOMetadata.Type type, final ScannerIOMetadata md){
    if(md instanceof TwainIOMetadata){
      TwainIOMetadata metadata=(TwainIOMetadata)md;
      TwainSource source=metadata.getSource();

      if(type.equals(ScannerIOMetadata.STATECHANGE)){
        if(metadata.isState(STATE_SRCMNGOPEN)){        // state = 3
          if(source.isBusy()){
            acqbutton.setEnabled(false);
            selbutton.setEnabled(false);
            guicheckbox.setEnabled(false);
          }else{
            acqbutton.setEnabled(true);
            selbutton.setEnabled(true);
            guicheckbox.setEnabled(true);
          }
        }
      }else if(type.equals(ScannerIOMetadata.NEGOTIATE)){
        if(source.isUIControllable()){                 // if it is possible to hide source's GUI
          source.setShowUI(guicheckbox.isSelected());  // then use checkbox value
        }else{
          if(!guicheckbox.isSelected()){System.out.println("9\bCannot hide twain source's GUI.");}
          guicheckbox.setSelected(true);               // else set to true whatever the user selected
        }
      }
    }
  }
}

