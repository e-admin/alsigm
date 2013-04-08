package uk.co.mmscomputing.util;

import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class UtilTextField implements DocumentListener{

  private Document td;

  public UtilTextField(JPanel p, Hashtable map, String key, String defaultValue, String label, int size){
    if(label!=null){
      JLabel l=new JLabel(label);
      l.setBorder(new EtchedBorder());
      p.add(l); 
    }
    JTextField tf=new JTextField(size);
    tf.setText(getString(map,key,defaultValue));
    td=tf.getDocument();
    td.addDocumentListener(this);
    td.putProperty("key",key);
    td.putProperty("map",map);
    p.add(tf);
  }

  public void addDocumentListener(DocumentListener listener){
    td.addDocumentListener(listener);
  }

  public void changedUpdate(DocumentEvent e){ setMap(e);}
  public void insertUpdate(DocumentEvent e){  setMap(e);}
  public void removeUpdate(DocumentEvent e){  setMap(e);}


  protected String getString(Hashtable map,String key, String value){
    Object s=map.get(key);
    if(s==null){ 
      map.put(key,value);
      return value;
    }else if(s instanceof String){
      return (String)s; 
    }else{
      System.out.println("9\b"+getClass().getName()+"\n\tExpect Type String for key ["+key+"].");
      return "";
    }
  }

  protected void setMap(DocumentEvent de){
    try{
      Document d=de.getDocument();
      Map map=(Map)d.getProperty("map");
      String key=(String)d.getProperty("key");
      String value=d.getText(d.getStartPosition().getOffset(),d.getEndPosition().getOffset()).trim();
      map.put(key,value);
    }catch(Exception e){
      System.out.println("9\b"+e.getMessage());
    }
  }
}
