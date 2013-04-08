package uk.co.mmscomputing.device.sane.option;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import uk.co.mmscomputing.device.sane.SaneIOException;

@SuppressWarnings("serial")
public class StringListPanel extends DescriptorPanel{

  public StringListPanel(StringList od){
    super(od);

    JComboBox combo=new JComboBox(od.list);
    combo.setMinimumSize(new Dimension(200,100));
    combo.setMaximumRowCount(5);
    combo.addActionListener(this);
    checkCapabilities(combo);

    addValuePanel(combo);

/*
    String valstr="Value";
    if(od.unit!=0){ valstr+=" in ["+SANE_UNIT[od.unit]+"]";}
    combo.setBorder(new TitledBorder(valstr));
    add(combo,BorderLayout.NORTH);     
*/
    combo.setSelectedItem(od.getStringValue());       
  }

  public void actionPerformed(ActionEvent e){
    JComboBox combo=(JComboBox)e.getSource();
    String str=combo.getSelectedItem().toString();
    try{
      str=od.setStringValue(str);
      combo.setSelectedItem(str);
    }catch(SaneIOException sioe){
      sioe.printStackTrace();
    }
  }

}
