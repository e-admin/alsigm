package uk.co.mmscomputing.device.sane.option;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import uk.co.mmscomputing.device.sane.SaneIOException;

@SuppressWarnings("serial")
public class StringPanel extends DescriptorPanel{
  public StringPanel(StringDesc od){
    super(od);

    JTextField tf=new JTextField(10);
    tf.setHorizontalAlignment(SwingConstants.RIGHT);
    tf.addActionListener(this);
    checkCapabilities(tf);
    tf.setText(od.getStringValue());
    addValuePanel(tf);

/*
    String valstr="Value";
    if(od.unit!=0){ valstr+=" in ["+SANE_UNIT[od.unit]+"]";}
    tf.setBorder(new TitledBorder(valstr));
    tf.setText(od.getStringValue());
    add(tf,BorderLayout.NORTH);     
*/
  }

  public void actionPerformed(ActionEvent e){
    try{
      JTextField tf=(JTextField)e.getSource();
      tf.setText(od.setStringValue(tf.getText()));
    }catch(SaneIOException sioe){
      sioe.printStackTrace();
    }
  }
}

