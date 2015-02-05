package uk.co.mmscomputing.device.sane.option;

public class StringList extends StringDesc{

  String[] list;

  public StringList(
    int      handle,
    int      no,

    String   name,
    String   title,
    String   desc,
    int      type,
    int      unit,
    int      size,
    int      cap,
    String[] list,
    String   value
  ){
    super(handle,no,name,title,desc,type,unit,size,cap,value);
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
    gui=new StringListPanel(this);
    return gui;
  }

public String[] getList() {
	return list;
}
  
}

