package uk.co.mmscomputing.device.sane.option;

import uk.co.mmscomputing.device.sane.SaneIOException;

public class StringDesc extends Descriptor{

  protected  String value="";

  public StringDesc(
    int      handle,
    int      no,

    String   name,
    String   title,
    String   desc,
    int      type,
    int      unit,
    int      size,
    int      cap,
    String   value
  ){
    super(handle,no,name,title,desc,type,unit,size,cap);
    this.value=value;
  }

  public String getStringValue(){
    return value;
  }

  public String setStringValue(String val) throws SaneIOException{
      if(isWritable()){
        value=val;
        int info=setStringControlOption(size,val);
        if((info&SANE_INFO_INEXACT)==SANE_INFO_INEXACT){
          value=getStringControlOption(size);
        }
        if((info&SANE_INFO_RELOAD_OPTIONS)==SANE_INFO_RELOAD_OPTIONS){
          signalReloadOptions();
        }
        if((info&SANE_INFO_RELOAD_PARAMS)==SANE_INFO_RELOAD_PARAMS){
          // read parameters only after scan started
        }
      }
    return value;
  }

  public String toString(){
    String s=super.toString();
    s+="\n    string= "+value;
    return s;
  }

  public DescriptorPanel getGUI(){
    gui=new StringPanel(this);
    return gui;
  }

}

