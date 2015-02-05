package uk.co.mmscomputing.device.sane.option;

import uk.co.mmscomputing.device.sane.SaneIOException;

public class FixedDesc extends WordDesc{

  public FixedDesc(
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
    return unfixstr(getWordValue(i));
  }

  public String setStringValue(int i, String valstr)throws SaneIOException{
    setWordValue(i,fix(Double.parseDouble(valstr)));
    return getStringValue(i);
  }

  public String toString(){
    String s=super.toString();
    for(int i=0;i<values.length;i++){
      s+="\n    values["+i+"]= "+unfix(getWordValue(i));
    }
    return s;
  }

  public void setPixelValue(double resolution, int ival)throws SaneIOException{
    setWordValue(0,fix(convertPixelValue(resolution,ival)));
    signalNewValue();
  }

  public int getPixelValue(double resolution){
    return convertUnits2Pixels(resolution,unfix(getWordValue(0)));
  }

  public DescriptorPanel getGUI(){
    gui=new FixedPanel(this);
    return gui;
  }

  static public int fix(double value){
    int shift=(1 << SANE_FIXED_SCALE_SHIFT);
    return (int)(value*(double)shift);
  }

  static public double unfix(int value){
    int shift=(1 << SANE_FIXED_SCALE_SHIFT);
    return ((double)value / (double)shift);
  }

  static public String unfixstr(int value){
    return Double.toString(Math.round(unfix(value)*100.0)/100.0);
  }

}

