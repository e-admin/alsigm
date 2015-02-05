package uk.co.mmscomputing.device.sane.option;

public class FixedList extends FixedDesc{

  int[] list;

  public FixedList(
    int      handle,
    int      no,

    String   name,
    String   title,
    String   desc,
    int      type,
    int      unit,
    int      size,
    int      cap,
    int[]    list,
    int[]    values
  ){
    super(handle,no,name,title,desc,type,unit,size,cap,values);
    this.list=list;
  }

  public String toString(){
    String s=super.toString();
    for(int j=0;j<list.length;j++){
      s+="\n    list["+j+"]= 0x"+unfix(list[j]);
    }          
    return s;
  }

  public DescriptorPanel getGUI(){
    gui=new FixedListPanel(this);
    return gui;
  }

}

