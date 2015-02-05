package uk.co.mmscomputing.imageio.tiff;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.imageio.*;

import uk.co.mmscomputing.concurrent.Semaphore;

public class TIFFIIOParamController extends JPanel implements ActionListener,ChangeListener,IIOParamController,TIFFConstants{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JDialog            dialog;
  private Semaphore          blocker=new Semaphore(0,true);

  private boolean            cancelled=false;

  private JRadioButton       buttonBW;
  private JRadioButton       buttonGray;
  private JRadioButton       buttonRGB;
  private JRadioButton       buttonCMYK;
  private JRadioButton       buttonYCbCr;

  private JRadioButton       buttonNONE;
  private JRadioButton       buttonMH;
  private JRadioButton       buttonT4MH;
  private JRadioButton       buttonT4MR;
  private JRadioButton       buttonT6MMR;
  private JRadioButton       buttonJPEG;

  private JRadioButton       buttonSS111;
  private JRadioButton       buttonSS211;

  private JSlider            sliderQuality;

  private static String      pi="WHITEISZERO";          // Photometric Interpretation
  private static String      compression="t6mmr";       // Compression method
  private static int         quality=50;
  private static int         subsampling=0x22;

  public TIFFIIOParamController(Locale locale){
    super(new BorderLayout());
    
    add(getMainCentralPanel(),BorderLayout.CENTER);

    JPanel bp=new JPanel();
    bp.setLayout(new GridLayout(1,2));

    JButton button = new JButton("ok");
    button.setActionCommand("ok");
    button.addActionListener(this);
    bp.add(button);
/*
    button = new JButton("cancel");
    button.setActionCommand("cancel");
    button.addActionListener(this);
    bp.add(button);
*/
    add(bp,BorderLayout.SOUTH);

    init(pi,compression,subsampling);
  }

  private void init(String pi,String comp,int ss){
    setCompression(comp);
    setPhotometricInterpretation(pi);
    switch(ss){
    case 0x11:setSubSampling("1:1:1");break;
    default:  setSubSampling("2:1:1");break;
    }
  }

  private JPanel getMainCentralPanel(){
    JPanel p = new JPanel(new GridLayout(0,1));
    p.setBorder(new EtchedBorder());
    p.add(getPhotometricInterpretationPanel());
    p.add(getCompressionPanel());
    p.add(getQualityPanel());
    p.add(getSubSamplingPanel());
    return p;
  }

  private JPanel getPhotometricInterpretationPanel(){
    buttonBW = new JRadioButton("Black/White");
    buttonBW.setActionCommand("WHITEISZERO");
    buttonBW.addActionListener(this);

    buttonGray = new JRadioButton("BlackIsZero (Gray)");
    buttonGray.setActionCommand("BLACKISZERO");
    buttonGray.addActionListener(this);

    buttonRGB = new JRadioButton("RGB");
    buttonRGB.setActionCommand("RGB");
    buttonRGB.addActionListener(this);

    buttonCMYK = new JRadioButton("CMYK");
    buttonCMYK.setActionCommand("CMYK");
    buttonCMYK.addActionListener(this);

    buttonYCbCr = new JRadioButton("YCbCr");
    buttonYCbCr.setActionCommand("YCbCr");
    buttonYCbCr.addActionListener(this);

    ButtonGroup group = new ButtonGroup();
    group.add(buttonBW);
    group.add(buttonGray);
    group.add(buttonRGB);
    group.add(buttonCMYK);
    group.add(buttonYCbCr);

    JPanel p = new JPanel(new GridLayout(1,0));
    p.setBorder(new TitledBorder(new EtchedBorder(),"Photometric Interpretation"));
    p.add(buttonBW);
    p.add(buttonGray);
    p.add(buttonRGB);
    p.add(buttonCMYK);
    p.add(buttonYCbCr);
    return p;
  }

  private JPanel getCompressionPanel(){
    buttonNONE = new JRadioButton("None");
    buttonNONE.setActionCommand("none");
    buttonNONE.addActionListener(this);

    buttonMH = new JRadioButton("BL MH");
    buttonMH.setActionCommand("mh");
    buttonMH.addActionListener(this);

    buttonT4MH = new JRadioButton("T4 MH");
    buttonT4MH.setActionCommand("t4mh");
    buttonT4MH.addActionListener(this);

    buttonT4MR = new JRadioButton("T4 MR");
    buttonT4MR.setActionCommand("t4mr");
    buttonT4MR.addActionListener(this);

    buttonT6MMR = new JRadioButton("T6 MMR");
    buttonT6MMR.setActionCommand("t6mmr");
    buttonT6MMR.addActionListener(this);

    buttonJPEG = new JRadioButton("JPEG");
    buttonJPEG.setActionCommand("jpeg");
    buttonJPEG.addActionListener(this);

    ButtonGroup group = new ButtonGroup();
    group.add(buttonNONE);
    group.add(buttonMH);
    group.add(buttonT4MH);
    group.add(buttonT4MR);
    group.add(buttonT6MMR);
    group.add(buttonJPEG);

    JPanel p = new JPanel(new GridLayout(1,0));
    p.setBorder(new TitledBorder(new EtchedBorder(),"Compression"));
    p.add(buttonNONE);
    p.add(buttonMH);
    p.add(buttonT4MH);
    p.add(buttonT4MR);
    p.add(buttonT6MMR);
    p.add(buttonJPEG);

    return p;
  }

  private JPanel getSubSamplingPanel(){
    buttonSS111 = new JRadioButton("1:1:1");
    buttonSS111.setActionCommand("1:1:1");
    buttonSS111.addActionListener(this);

    buttonSS211 = new JRadioButton("2:1:1");
    buttonSS211.setActionCommand("2:1:1");
    buttonSS211.addActionListener(this);

    buttonSS111.setEnabled(false);
    buttonSS211.setEnabled(false);

    ButtonGroup group = new ButtonGroup();
    group.add(buttonSS111);
    group.add(buttonSS211);

    JPanel p = new JPanel(new GridLayout(1,0));
    p.setBorder(new TitledBorder(new EtchedBorder(),"Y-Cb-Cr Sub Sampling"));
    p.add(buttonSS111);
    p.add(buttonSS211);

    return p;
  }

  private boolean setPhotometricInterpretation(String action){
    if(action.equals("WHITEISZERO")){
      pi="WHITEISZERO";
      buttonBW.setSelected(true);
      buttonNONE.setEnabled(false);
      buttonMH.setEnabled(true);
      buttonT4MH.setEnabled(true);
      buttonT4MR.setEnabled(true);
      buttonT6MMR.setEnabled(true);
      buttonJPEG.setEnabled(false);
      if(buttonNONE.isSelected()||buttonJPEG.isSelected()){
        setCompression("t6mmr");
      }
      buttonSS111.setEnabled(false);
      buttonSS211.setEnabled(false);
    }else if(action.equals("BLACKISZERO")){
      pi="BLACKISZERO";
      buttonGray.setSelected(true);
      buttonNONE.setEnabled(true);
      buttonMH.setEnabled(false);
      buttonT4MH.setEnabled(false);
      buttonT4MR.setEnabled(false);
      buttonT6MMR.setEnabled(false);
      buttonJPEG.setEnabled(true);
      if(buttonNONE.isSelected()||buttonJPEG.isSelected()){
      }else{setCompression("none");}
      buttonSS111.setEnabled(false);
      buttonSS211.setEnabled(false);
    }else if(action.equals("RGB")){
      pi="RGB";
      buttonRGB.setSelected(true);
      buttonNONE.setEnabled(true);
      buttonMH.setEnabled(false);
      buttonT4MH.setEnabled(false);
      buttonT4MR.setEnabled(false);
      buttonT6MMR.setEnabled(false);
      buttonJPEG.setEnabled(true);
      if(buttonNONE.isSelected()||buttonJPEG.isSelected()){
      }else{setCompression("none");}
      buttonSS111.setEnabled(false);
      buttonSS211.setEnabled(false);
    }else if(action.equals("CMYK")){
      pi="CMYK";
      buttonCMYK.setSelected(true);
      buttonNONE.setEnabled(true);
      buttonMH.setEnabled(false);
      buttonT4MH.setEnabled(false);
      buttonT4MR.setEnabled(false);
      buttonT6MMR.setEnabled(false);
      buttonJPEG.setEnabled(false);
      if(buttonNONE.isSelected()){
      }else{setCompression("none");}
      buttonSS111.setEnabled(false);
      buttonSS211.setEnabled(false);
    }else if(action.equals("YCbCr")){
      pi="YCbCr";
      buttonYCbCr.setSelected(true);
      buttonNONE.setEnabled(true);
      buttonMH.setEnabled(false);
      buttonT4MH.setEnabled(false);
      buttonT4MR.setEnabled(false);
      buttonT6MMR.setEnabled(false);
      buttonJPEG.setEnabled(true);
      if(buttonNONE.isSelected()||buttonJPEG.isSelected()){
      }else{setCompression("none");}
      buttonSS111.setEnabled(true);
      buttonSS211.setEnabled(true);
    }else{
      return false;
    }
    return true;
  }

  private boolean setCompression(String action){
    if(action.startsWith("none")){
      compression=action;
      int q=quality;sliderQuality.setValue(100);quality=q;
      sliderQuality.setEnabled(false);
      buttonNONE.setSelected(true);
    }else if(action.startsWith("mh")){
      compression=action;
      int q=quality;sliderQuality.setValue(100);quality=q;
      sliderQuality.setEnabled(false);
      buttonMH.setSelected(true);
    }else if(action.startsWith("t4mh")){
      compression=action;
      int q=quality;sliderQuality.setValue(100);quality=q;
      sliderQuality.setEnabled(false);
      buttonT4MH.setSelected(true);
    }else if(action.startsWith("t4mr")){
      compression=action;
      int q=quality;sliderQuality.setValue(100);quality=q;
      sliderQuality.setEnabled(false);
      buttonT4MR.setSelected(true);
    }else if(action.startsWith("t6mmr")){
      compression=action;
      int q=quality;sliderQuality.setValue(100);quality=q;
      sliderQuality.setEnabled(false);
      buttonT6MMR.setSelected(true);
    }else if(action.startsWith("jpeg")){
      compression=action;
      sliderQuality.setValue(quality);
      sliderQuality.setEnabled(true);
      buttonJPEG.setSelected(true);
    }else{
      return false;
    }
    return true;
  }

  private boolean setSubSampling(String action){
    if(action.equals("1:1:1")){
      buttonSS111.setSelected(true);
      subsampling=0x11;
    }else if(action.equals("2:1:1")){
      buttonSS211.setSelected(true);
      subsampling=0x22;
    }else{
      return false;
    }
    return true;
  }

  public void actionPerformed(ActionEvent ev) {
    String action=ev.getActionCommand();

    if(action.equals("ok")){
      cancelled=false;
      if(dialog!=null){dialog.dispose();}
      blocker.release();
    }else if(action.equals("cancel")){
      cancelled=true;
      if(dialog!=null){dialog.dispose();}
      blocker.release();
    }else if(setPhotometricInterpretation(action)){
    }else if(setCompression(action)){
    }else if(setSubSampling(action)){
    }else{
    }
  }

  @SuppressWarnings("unchecked")
private JPanel getQualityPanel(){

    sliderQuality=new JSlider(JSlider.HORIZONTAL,0,100,quality);
    sliderQuality.addChangeListener(this);
    sliderQuality.setEnabled(false);

    sliderQuality.setMinorTickSpacing(2);
    sliderQuality.setMajorTickSpacing(10);
    sliderQuality.setPaintTicks(true);

    Dictionary dict=new Hashtable();
    for(int i=0;i<=100;i+=10){      
      dict.put(new Integer(i), new JLabel(Integer.toString(i)));
    }
    sliderQuality.setLabelTable(dict);
    sliderQuality.setPaintLabels(true);

    JPanel p=new JPanel();
    p.setLayout(new BorderLayout());
    p.setBorder(new TitledBorder(new EtchedBorder(),"JPEG Quality"));
    p.add(sliderQuality,BorderLayout.CENTER);

    return p;
  }

  public void stateChanged(ChangeEvent e){
    JSlider slider=(JSlider)e.getSource();
    if(!slider.getValueIsAdjusting()){
      if(slider==sliderQuality){
        quality=slider.getValue();
        if(quality<=0){quality=1;slider.setValue(1);}
//        System.err.println(quality);
      }
    }
  }

  public void display(){
    try{
      dialog=new JDialog((Frame)null,"TIFF Write Parameter Settings",false);
      dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      dialog.setContentPane(this);
      @SuppressWarnings("unused")
	GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
      dialog.pack();
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);
    }catch(Exception e){
      System.out.println("9\b"+getClass().getName()+".display:\n\t"+e);
      e.printStackTrace();
    }
  }

  public boolean activate(IIOParam param){
    display();
    //try{
//      blocker.acquire();
    //}catch(InterruptedException ie){
//      return false;
//    }
    if(cancelled){
    	return false;
    }
    TIFFImageWriteParam p=(TIFFImageWriteParam)param;
    p.setCompressionType(compression);
    p.setPhotometricInterpretation(pi);
    p.setCompressionQuality(((float)quality)/100);
    p.setSubSampling(subsampling);
    return true;
  }
}

