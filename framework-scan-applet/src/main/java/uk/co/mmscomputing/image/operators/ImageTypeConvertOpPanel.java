package uk.co.mmscomputing.image.operators;


import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.mmscomputing.concurrent.Semaphore;

public class ImageTypeConvertOpPanel extends JPanel implements ActionListener,ChangeListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

static private Properties  params=new Properties();

  private JDialog            dialog;
  private boolean            cancelled;

  private JRadioButton       buttonByteBinary;
  private JRadioButton       buttonByteIndexed;
  private JRadioButton       buttonByteGrayScaled;

  @SuppressWarnings("unused")
private JRadioButton       buttonBPP1,buttonBPP2,buttonBPP4,buttonBPP8,buttonBPP16,buttonBPP24,buttonBPP32;

  private JSlider            sliderThreshold;

  private Semaphore          blocker=new Semaphore(0,true);

  public ImageTypeConvertOpPanel(){
    super(new BorderLayout());

    cancelled=false;
    
    add(getMainCentralPanel(),BorderLayout.CENTER);

    JPanel bp=new JPanel();
    bp.setLayout(new GridLayout(1,2));

    JButton button = new JButton("ok");
    button.setActionCommand("ok");
    button.addActionListener(this);
    bp.add(button);

    button = new JButton("cancel");
    button.setActionCommand("cancel");
    button.addActionListener(this);
    bp.add(button);

    add(bp,BorderLayout.SOUTH);
  }

  private JPanel getMainCentralPanel(){
    JPanel p = new JPanel(new GridLayout(0,1));
    p.setBorder(new EtchedBorder());
    p.add(getImageTypePanel());
    p.add(getBPPPanel());
    p.add(getQualityPanel());
    return p;
  }

  private JPanel getImageTypePanel(){
    buttonByteBinary = new JRadioButton("Byte Binary");
    buttonByteBinary.setActionCommand("ByteBinary");
    buttonByteBinary.addActionListener(this);
    buttonByteBinary.setSelected(true);
    params.setProperty("type",Integer.toString(BufferedImage.TYPE_BYTE_BINARY));

    buttonByteIndexed = new JRadioButton("Byte Indexed");
    buttonByteIndexed.setActionCommand("ByteIndexed");
    buttonByteIndexed.addActionListener(this);

    buttonByteGrayScaled = new JRadioButton("Byte Gray Scale");
    buttonByteGrayScaled.setActionCommand("ByteGrayScale");
    buttonByteGrayScaled.addActionListener(this);

    ButtonGroup group = new ButtonGroup();
    group.add(buttonByteBinary);
    group.add(buttonByteIndexed);
    group.add(buttonByteGrayScaled);

    JPanel p = new JPanel(new GridLayout(1,0));
    p.setBorder(new TitledBorder(new EtchedBorder(),"Image Type"));
    p.add(buttonByteBinary);
    p.add(buttonByteIndexed);
    p.add(buttonByteGrayScaled);
    return p;
  }

  private JPanel getBPPPanel(){
    buttonBPP1 = new JRadioButton("1");
    buttonBPP1.setActionCommand("bpp1");
    buttonBPP1.addActionListener(this);
    buttonBPP1.setSelected(true);
    params.setProperty("bpp","1");

    buttonBPP2 = new JRadioButton("2");
    buttonBPP2.setActionCommand("bpp2");
    buttonBPP2.addActionListener(this);

    buttonBPP4 = new JRadioButton("4");
    buttonBPP4.setActionCommand("bpp4");
    buttonBPP4.addActionListener(this);

    buttonBPP8 = new JRadioButton("8");
    buttonBPP8.setActionCommand("bpp8");
    buttonBPP8.addActionListener(this);
    buttonBPP8.setEnabled(false);

    ButtonGroup group = new ButtonGroup();
    group.add(buttonBPP1);
    group.add(buttonBPP2);
    group.add(buttonBPP4);
    group.add(buttonBPP8);

    JPanel p = new JPanel(new GridLayout(1,0));
    p.setBorder(new TitledBorder(new EtchedBorder(),"Bits Per Pixel"));
    p.add(buttonBPP1);
    p.add(buttonBPP2);
    p.add(buttonBPP4);
    p.add(buttonBPP8);
    return p;
  }

  private boolean setType(String action){
    if(action.equals("ByteBinary")){
      params.setProperty("type",Integer.toString(BufferedImage.TYPE_BYTE_BINARY));
      buttonByteBinary.setSelected(true);
      buttonBPP1.setEnabled(true);
      buttonBPP2.setEnabled(true);
      buttonBPP4.setEnabled(true);
      buttonBPP8.setEnabled(false);
      if(buttonBPP8.isSelected()){setBPP("bpp4");}
    }else if(action.equals("ByteIndexed")){
      params.setProperty("type",Integer.toString(BufferedImage.TYPE_BYTE_INDEXED));
      buttonByteIndexed.setSelected(true);
      buttonBPP1.setEnabled(false);
      buttonBPP2.setEnabled(false);
      buttonBPP4.setEnabled(false);
      buttonBPP8.setEnabled(true);
      setBPP("bpp8");
    }else if(action.equals("ByteGrayScale")){
      params.setProperty("type",Integer.toString(BufferedImage.TYPE_BYTE_GRAY));
      buttonByteGrayScaled.setSelected(true);
      buttonBPP1.setEnabled(false);
      buttonBPP2.setEnabled(false);
      buttonBPP4.setEnabled(false);
      buttonBPP8.setEnabled(true);
      setBPP("bpp8");
    }else{
      return false;
    }
    return true;
  }

  private boolean setBPP(String action){
    if(action.equals("bpp1")){
      params.setProperty("bpp","1");
      buttonBPP1.setSelected(true);
      sliderThreshold.setEnabled(true);
    }else if(action.equals("bpp2")){
      params.setProperty("bpp","2");
      buttonBPP2.setSelected(true);
      sliderThreshold.setEnabled(false);
    }else if(action.equals("bpp4")){
      params.setProperty("bpp","4");
      buttonBPP4.setSelected(true);
      sliderThreshold.setEnabled(false);
    }else if(action.equals("bpp8")){
      params.setProperty("bpp","8");
      buttonBPP8.setSelected(true);
      sliderThreshold.setEnabled(false);
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
    }else if(setType(action)){
    }else if(setBPP(action)){
    }else{
    }
  }

  @SuppressWarnings("unchecked")
private JPanel getQualityPanel(){
    int threshold=50;
    params.setProperty("threshold",Integer.toString(threshold));

    sliderThreshold=new JSlider(JSlider.HORIZONTAL,0,100,threshold);
    sliderThreshold.addChangeListener(this);
    sliderThreshold.setEnabled(true);

    sliderThreshold.setMinorTickSpacing(2);
    sliderThreshold.setMajorTickSpacing(10);
    sliderThreshold.setPaintTicks(true);

    Dictionary dict=new Hashtable();
    for(int i=0;i<=100;i+=10){      
      dict.put(new Integer(i), new JLabel(Integer.toString(i)));
    }
    sliderThreshold.setLabelTable(dict);
    sliderThreshold.setPaintLabels(true);

    JPanel p=new JPanel();
    p.setLayout(new BorderLayout());
    p.setBorder(new TitledBorder(new EtchedBorder(),"Threshold"));
    p.add(sliderThreshold,BorderLayout.CENTER);

    return p;
  }

  public void stateChanged(ChangeEvent e){
    JSlider slider=(JSlider)e.getSource();
    if(!slider.getValueIsAdjusting()){
      if(slider==sliderThreshold){
        int threshold=slider.getValue();
        if(threshold<=0){threshold=1;slider.setValue(1);}
        params.setProperty("threshold",Integer.toString(threshold));
      }
    }
  }

  public void display(){
    try{
      dialog=new JDialog((Frame)null,"Image Type Converter Settings",false);
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

  public ImageTypeConvertOp activate(){
    display();
    try{
      blocker.acquire();
    }catch(InterruptedException ie){
      return null;
    }
    if(cancelled){return null;}
    return new ImageTypeConvertOp(params);
  }
}