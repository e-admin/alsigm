package uk.co.mmscomputing.device.sane.option;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

import uk.co.mmscomputing.device.sane.SaneIOException;

@SuppressWarnings("serial")
public class IntRangePanel extends DescriptorPanel /*implements DocumentListener*/{

  JSlider[]    sliders=null;
  JTextField[] tfs=null;

  public IntRangePanel(IntRange od){
    super(od);

    int len=od.size>>2;
    JPanel p=new JPanel();
    p.setLayout(new GridLayout(len,1));
    tfs=new JTextField[len];
    sliders=new JSlider[len];
    for(int i=0;i<len;i++){
      p.add(createRangePanel(i));
    }

    int extent=od.max-od.min;
    sliders[len-1].setMajorTickSpacing(extent / 10);
//    sliders[len-1].setPaintTicks(true);
    sliders[len-1].setPaintLabels(true);

    addValuePanel(new JScrollPane(p));
/*
    String valstr="Value";
    if(od.unit!=0){ valstr+=" in ["+SANE_UNIT[od.unit]+"]";}
    p.setBorder(new TitledBorder(valstr));
    add(new JScrollPane(p),BorderLayout.NORTH);
*/
  }

  private JPanel createRangePanel(int i){
    IntRange od=(IntRange)this.od;

    JSlider slider=new JSlider(JSlider.HORIZONTAL,od.min,od.max,od.min);
    slider.addChangeListener(this);
    checkCapabilities(slider);

//    int extent=od.max-od.min;
//    slider.setMinorTickSpacing(extent / 20);
//    slider.setMajorTickSpacing(extent / 4);
//    slider.setPaintTicks(true);
//    slider.setPaintLabels(true);

    JTextField tf=new JTextField(10);    
    tf.addActionListener(this);
//    Document td=tf.getDocument();
//    td.addDocumentListener(this);
//    td.putProperty("index",new Integer(i));
//    td.putProperty("slider",slider);

    tf.setHorizontalAlignment(SwingConstants.RIGHT);
    checkCapabilities(tf);
    tf.setText(od.getStringValue(i));

    JPanel p=new JPanel();
    p.setLayout(new BorderLayout());
    p.add(slider,BorderLayout.CENTER);
    p.add(tf,BorderLayout.EAST);

//    tf.setText(od.getStringValue(i));
    slider.setValue(od.getWordValue(i));  // calls stateChanged

    sliders[i]=slider;
    tfs[i]=tf;

    return p;
  }

  // call from Descriptor : PreviewPanel value has changed

  void signalNewValue(){
    System.out.println("9\b"+getClass().getName()+".signalNewValue:\n\t");
    sliders[0].setValue(od.getWordValue(0));// calls stateChanged
  }

/*
  public void stateChanged(ChangeEvent e){
    JSlider slider=(JSlider)e.getSource();
//    if(!slider.getValueIsAdjusting()){
      for(int i=0;i<sliders.length;i++){
        if(sliders[i]==slider){
          JTextField tf=tfs[i];

          String newValue=String.valueOf(slider.getValue());
          String oldValue=tf.getText();

          if(!newValue.equals(oldValue)){
            try{
              od.setWordValue(i,slider.getValue());
              tf.setText(od.getStringValue(i));
            }catch(SaneIOException sioe){
              sioe.printStackTrace();
            }
          }
          break;
        }
      }
//    }
  }
*/
  public void stateChanged(ChangeEvent e){
    JSlider slider=(JSlider)e.getSource();
    for(int i=0;i<sliders.length;i++){
      if(sliders[i]==slider){
        JTextField tf=tfs[i];
        tf.setText(""+slider.getValue());
        if(!slider.getValueIsAdjusting()){
          try{
            od.setWordValue(i,slider.getValue());
            tf.setText(od.getStringValue(i));      
          }catch(SaneIOException sioe){
            sioe.printStackTrace();
          }
        }
      }
    }
  }

  public void actionPerformed(ActionEvent e){
    JTextField tf=(JTextField)e.getSource();
    for(int i=0;i<tfs.length;i++){
      if(tfs[i]==tf){
        try{
          od.setStringValue(i,tf.getText());
          sliders[i].setValue(od.getWordValue(i));
        }catch(SaneIOException sioe){
          sioe.printStackTrace();
        }
      }
    }
  }

/*
  public void changedUpdate(DocumentEvent de){setSlider(de);}
  public void insertUpdate(DocumentEvent de){setSlider(de);}
  public void removeUpdate(DocumentEvent de){setSlider(de);}

  private void setSlider(DocumentEvent de){    
    try{
      Document d       = de.getDocument();
      String   value   = d.getText(d.getStartPosition().getOffset(),d.getEndPosition().getOffset()).trim();
      int      index   = ((Integer)d.getProperty("index")).intValue();

      int proposed = Integer.parseInt(value);

      od.setStringValue(index,value);
      int newValue=od.getWordValue(index);

      if(proposed==newValue){
        JSlider  slider  = (JSlider)d.getProperty("slider");
        int oldValue=slider.getValue();
        if(newValue!=oldValue){slider.setValue(newValue);}
      }
//    }catch(NumberFormatException nfe){
//    }catch(BadLocationException ble){
//    }catch(SaneIOException sioe){
    }catch(Exception e){
      throw new IllegalArgumentException(getClass().getName()+".setSlider:\n\t option name = "+od.getName()+" "+e.getMessage());
//      System.out.println("9\b"+getClass().getName()+".setSlider:\n\t"+e);
//      e.printStackTrace();
    }
  }
*/
}

