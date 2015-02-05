package uk.co.mmscomputing.device.sane.option;

public class ButtonDesc extends WordDesc{

  public ButtonDesc(
    int      handle,
    int      no,

    String   name,
    String   title,
    String   desc,
    int      type,
    int      unit,
    int      size,
    int      cap
  ){
    super(handle,no,name,title,desc,type,unit,size,cap,new int[1]);
  }

  public String toString(){
    String s=super.toString();
    s+="\n    button";
    return s;
  }

  public DescriptorPanel getGUI(){
    return new ButtonPanel(this);
  }

}

