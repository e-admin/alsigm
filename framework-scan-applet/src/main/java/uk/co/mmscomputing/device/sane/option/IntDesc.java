package uk.co.mmscomputing.device.sane.option;

import uk.co.mmscomputing.device.sane.SaneIOException;

public class IntDesc extends WordDesc{

  public IntDesc(
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

  public String getStringValue(int i){
    return Integer.toString(getWordValue(i));
  }

  public String setStringValue(int i, String valstr)throws SaneIOException{
    setWordValue(i, Integer.parseInt(valstr));
    return getStringValue(i);
  }

  public void setPixelValue(double resolution, int ival)throws SaneIOException{
    setWordValue(0,(int)Math.round(convertPixels2Units(resolution,ival)));
    signalNewValue();
  }

  public int getPixelValue(double resolution){
    return convertUnits2Pixels(resolution,getWordValue(0));
  }

/*
  public String toString(){
    String s=super.toString();
    for(int i=0;i<values.length;i++){
      s+="\n    values["+i+"]= "+getWordValue(i);
    }
    return s;
  }
*/
  public DescriptorPanel getGUI(){
    gui=new IntPanel(this);
    return gui;
  }

}

