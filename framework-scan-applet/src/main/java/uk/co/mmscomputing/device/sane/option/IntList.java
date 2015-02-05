package uk.co.mmscomputing.device.sane.option;

public class IntList extends IntDesc{
  int[] list;

  public int[] getList() {
	return list;
}

public IntList(
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
      s+="\n    list["+j+"]= "+list[j];
    }          
    return s;
  }

  public DescriptorPanel getGUI(){
    gui=new IntListPanel(this);
    return gui;
  }
}

