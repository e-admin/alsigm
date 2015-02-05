package uk.co.mmscomputing.device.sane.option;

public class BoolDesc extends WordDesc{

  public BoolDesc(
    int      handle,
    int      no,

    String   name,
    String   title,
    String   desc,
    int      type,
    int      unit,
    int      size,
    int      cap,
    int[]    values
  ){
    super(handle,no,name,title,desc,type,unit,size,cap,values);
  }

  public String toString(){
    String s=super.toString();
    s+="\n    boolean= "+(getWordValue(0)==SANE_TRUE);
    return s;
  }

  public DescriptorPanel getGUI(){
    gui=new BoolPanel(this);
    return gui;
  }

}

