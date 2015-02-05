package uk.co.mmscomputing.device.sane.option;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

import uk.co.mmscomputing.device.sane.SaneIOException;

@SuppressWarnings("serial")
public class FixedRangePanel extends DescriptorPanel{

  JSlider[]    sliders=null;
  JTextField[] tfs=null;

  @SuppressWarnings("unchecked")
public FixedRangePanel(FixedRange od){
    super(od);
    
    Dictionary dict=createSliderDictionary();

    int len=od.size>>2;
    JPanel p=new JPanel();
    p.setLayout(new GridLayout(len,1));
    tfs=new JTextField[len];
    sliders=new JSlider[len];
    for(int i=0;i<len;i++){
      p.add(createRangePanel(i));
      sliders[i].setLabelTable(dict);
    }
    sliders[len-1].setPaintLabels(true);

    addValuePanel(new JScrollPane(p));

/*
    String valstr="Value";
    if(od.unit!=0){ valstr+=" in ["+SANE_UNIT[od.unit]+"]";}
    p.setBorder(new TitledBorder(valstr));

    add(new JScrollPane(p),BorderLayout.NORTH);
*/
  }

  @SuppressWarnings({ "unchecked", "static-access" })
private Dictionary createSliderDictionary(){
    FixedRange od=(FixedRange)this.od;
    int extent=(od.max-od.min)/5;
    Dictionary dict=new Hashtable();
    int ival=od.min;
    for(int i=0;i<=5;i++){      
      dict.put(new Integer(ival), new JLabel(od.unfixstr(ival)));
      ival+=extent;
    }
    return dict;
  }

  private JPanel createRangePanel(int i){
    FixedRange od=(FixedRange)this.od;

    JSlider slider=new JSlider(JSlider.HORIZONTAL,od.min,od.max,od.min);
    slider.addChangeListener(this);
    checkCapabilities(slider);

//    int extent=od.max-od.min;
//    slider.setMinorTickSpacing(extent / 50);
//    slider.setMajorTickSpacing(extent / 10);
//    slider.setPaintTicks(true);

//    slider.setLabelTable(dict);
//    slider.setPaintLabels(true);


    JTextField tf=new JTextField(10);    
    tf.setHorizontalAlignment(SwingConstants.RIGHT);
    tf.addActionListener(this);
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
    sliders[0].setValue(od.getWordValue(0));  // calls stateChanged
  }

  @SuppressWarnings("static-access")
public void stateChanged(ChangeEvent e){
    JSlider slider=(JSlider)e.getSource();
    for(int i=0;i<sliders.length;i++){
      if(sliders[i]==slider){
        JTextField tf=tfs[i];
        tf.setText(((FixedRange)od).unfixstr(slider.getValue()));
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
}

