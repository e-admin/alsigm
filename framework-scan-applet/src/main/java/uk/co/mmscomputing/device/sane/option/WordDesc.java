package uk.co.mmscomputing.device.sane.option;

import uk.co.mmscomputing.device.sane.SaneIOException;

public class WordDesc extends Descriptor{

  protected int[] values=null;

  public WordDesc(
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
    super(handle,no,name,title,desc,type,unit,size,cap);
    this.values=values;
  }

  public int[] getValues() {
	return values;
}

public int getWordValue(int i){
    return values[i];
  }

  public int setWordValue(int i, int val)throws SaneIOException{    
    if(isWritable()){
      values[i]=val;
      int info=setWordArrayControlOption(values);
      if((info&SANE_INFO_INEXACT)==SANE_INFO_INEXACT){
        getWordArrayControlOption(values);
      }
      if((info&SANE_INFO_RELOAD_OPTIONS)==SANE_INFO_RELOAD_OPTIONS){
        signalReloadOptions();
      }
      if((info&SANE_INFO_RELOAD_PARAMS)==SANE_INFO_RELOAD_PARAMS){
        // read parameters only after scan started
      }
    }
    return values[i];
  }

  public String toString(){
    String s=super.toString();
    for(int i=0;i<values.length;i++){
      s+="\n    values["+i+"]= "+values[i];
    }
    return s;
  }
}

