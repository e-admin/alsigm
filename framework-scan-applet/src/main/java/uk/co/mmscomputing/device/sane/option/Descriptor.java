package uk.co.mmscomputing.device.sane.option;

import uk.co.mmscomputing.device.sane.OptionDescriptor;
import uk.co.mmscomputing.device.sane.SaneIOException;

public class Descriptor extends OptionDescriptor{


  String name;	/* name of this option (command-line name) */
  String title;	/* title of this option (single-line) */
  String desc;	/* description of this option (multi-line) */
  int    type;	/* how are values interpreted? */
  int    unit;	/* what is the (physical) unit? */
  int    size;
  int    cap;		/* capabilities */

  public Descriptor(
    int    handle,
    int    no,

    String name,
    String title,
    String desc,
    int    type,
    int    unit,
    int    size,
    int    cap
  ){
    super(handle,no);
    this.name=name;
    this.title=title;
    this.desc=desc;
    this.type=type;
    this.unit=unit;
    this.size=size;
    this.cap=cap;
  }

  protected boolean isWritable(){
    return ((cap&SANE_CAP_INACTIVE)    != SANE_CAP_INACTIVE)
    &&     ((cap&SANE_CAP_SOFT_SELECT) == SANE_CAP_SOFT_SELECT);
  }

//  public int getWordValue(){
//    return getWordValue(0);
//  }

//  public int setWordValue(int val){
//    return setWordValue(0,val);
//  }

  public int getWordValue(int i){
    System.err.println(getClass().getName()+".setWordValue("+i+")\n    I shouldn't be here!");
    return 0;
  }

  public int setWordValue(int i, int val)throws SaneIOException{
    System.err.println(getClass().getName()+".setWordValue("+i+","+val+")\n    I shouldn't be here!");
    return 0;
  }

  public String getStringValue(){
    return getStringValue(0);
  }

  public String getStringValue(int i){
    System.err.println(getClass().getName()+".getStringValue()\n    I shouldn't be here!");
    return "";
  }

  public String setStringValue(String valstr)throws SaneIOException{
    return setStringValue(0,valstr);
  }

  public String setStringValue(int i, String valstr)throws SaneIOException{
    System.err.println(getClass().getName()+".setStringValue("+valstr+")\n    I shouldn't be here!");
    return "";
  }

  public double convertPixelValue(double resolution, int ival){
    double dval=ival;
    switch(unit){
    case SANE_UNIT_PIXEL:
      break;
    case SANE_UNIT_MM:
      dval=(25.4*dval)/resolution;
      break;
    default:
      System.err.println(getClass().getName()+".convertPixelValue: Cannot convert Pixels to "+SANE_UNIT[unit]);
      break;
    }
    return (Math.round(dval*100.0)/100.0);
  }

  public double convertPixels2Units(double resolution, int ival){
    double dval=ival;
    switch(unit){
    case SANE_UNIT_PIXEL:
      break;
    case SANE_UNIT_MM:
      dval=(25.4*dval)/resolution;
      break;
    default:
      System.err.println(getClass().getName()+".convertPixels2Units: Cannot convert Pixels to "+SANE_UNIT[unit]);
      break;
    }
    return (Math.round(dval*100.0)/100.0);
  }

  public int convertUnits2Pixels(double resolution, double dval){
    switch(unit){
    case SANE_UNIT_PIXEL:
      break;
    case SANE_UNIT_MM:
      dval=(dval*resolution)/25.4;
      break;
    default:
      System.err.println(getClass().getName()+".convertUnits2Pixels:\n\t Cannot convert "+SANE_UNIT[unit]+" to Pixels");
      break;
    }
    return (int)Math.round(dval);
  }

  public double convertMM2Units(double resolution, double dval){
    switch(unit){
    case SANE_UNIT_PIXEL:
      dval=Math.round((dval*resolution)/25.4);
      break;
    case SANE_UNIT_MM:
      break;
    default:
      System.err.println(getClass().getName()+".convertMM2Units: Cannot convert MM to "+SANE_UNIT[unit]);
      break;
    }
    return dval;
  }

  public void setPixelValue(double resolution, int value)throws SaneIOException{
    System.err.println(getClass().getName()+".setPixelValue("+resolution+","+value+")\n    I shouldn't be here!");
  }

  protected DescriptorPanel gui=null;

  void signalNewValue(){
    if(gui!=null){gui.signalNewValue();}
  }

  void signalReloadOptions(){
    if(gui!=null){gui.reloadOptions();}
  }

  public String getName(){return name;}
  public int getType(){return type;}
  public String getTitle(){return title;}
  public int getUnit(){return unit;}

  public String toString(){
    String s="";
//    s+="\nno "+no;
    s+="\nname "+name;
    s+="\ntitle "+title;
    s+="\ndesc "+desc;
    s+="\ntype "+SANE_TYPE[type];
    s+="\nunit "+SANE_UNIT[unit];
    s+="\nsize "+size;
    s+="\ncap 0x"+Integer.toHexString(cap);
    for(int k=0;k<8;k++){
      if(((cap>>k)&0x01)==0x01){
        s+="\n    "+SANE_CAP[k];
      }
    }
    return s;
  }

  public DescriptorPanel getGUI(){
    return new DescriptorPanel(this);
  }

}

/*
[1] SANE Standard Version 1.03
    (Scanner Access Now Easy)
    2002-10-10
    http://www.sane-project.org

*/
