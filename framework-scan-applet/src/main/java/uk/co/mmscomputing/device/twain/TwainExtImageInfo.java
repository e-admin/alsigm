package uk.co.mmscomputing.device.twain;

import java.util.Enumeration;
import java.util.Vector;

public class TwainExtImageInfo implements TwainConstants{

/*

typedef struct {
    TW_UINT16   InfoID;
    TW_UINT16   ItemType;
    TW_UINT16   NumItems;
    TW_UINT16   CondCode;
    TW_UINT32   Item;
}TW_INFO, FAR* pTW_INFO;

typedef struct {
    TW_UINT32   NumInfos;
    TW_INFO     Info[1];
}TW_EXTIMAGEINFO, FAR* pTW_EXTIMAGEINFO;

*/
  TwainSource source;
  byte[]      buf;
  int[]       attributes;               // list of extended image info attributes
  Vector      extInfos;

  public TwainExtImageInfo(TwainSource source,int tweiValue){
    this.source=source;
    attributes=new int[1];
    attributes[0]=tweiValue;
    initBuf();
  }

  public TwainExtImageInfo(TwainSource source,int[] tweiValues){
    this.source=source;
    attributes=tweiValues;
    initBuf();
  }

  private void initBuf(){
    int len = attributes.length;

    buf=new byte[4+len*12];

    jtwain.setINT32(buf,0,attributes.length);
    for (int i=0,off=4;i<len;i++){
      jtwain.setINT16(buf,off,attributes[i]);off+=2;
      jtwain.setINT16(buf,off,0);off+=2;
      jtwain.setINT16(buf,off,0);off+=2;
      jtwain.setINT16(buf,off,0);off+=2;
      jtwain.setINT32(buf,off,0);off+=4;
    }
  }

  public void get()throws TwainIOException{
    source.call(DG_IMAGE,DAT_EXTIMAGEINFO,MSG_GET,buf);
  }

  public Object getInfo()throws TwainIOException{return getInfo(attributes[0]);}

  public Object getInfo(int twei)throws TwainIOException{
    int i=0;
    int len=attributes.length;
    for(;i<len;i++){
      if(attributes[i]==twei){break;}
    }
    if(i==len){return null;}
    int off=4+i*12;
    int cc =jtwain.getINT16(buf,off+6);
    if(cc!=TWRC_SUCCESS){throw new TwainFailureException(cc);}
    int type  =jtwain.getINT16(buf,off+2);
    int count =jtwain.getINT16(buf,off+4);
    switch(type){
    case TWTY_UINT32:
      if(count==1){
        return new Integer(jtwain.getINT16(buf,off+8));
      }

// to do: different types

    default:
      System.err.println(getClass().getName()+".getInfo:\n\tDon't support type = "+type+" yet.");
    }
    return null;
  }

  public String toString(){
    String s="TwainExtImageInfo\n";

    int len = attributes.length;
    for (int i=0,off=4;i<len;i++){
      s+="InfoID = 0x"+Integer.toHexString(jtwain.getINT16(buf,off))+"\n"; off+=2;
      s+="ItemType = "+jtwain.getINT16(buf,off)+"\n";                      off+=2;
      s+="NumItems = "+jtwain.getINT16(buf,off)+"\n";                      off+=2;
      s+="CondCode = "+jtwain.getINT16(buf,off)+"\n";                      off+=2;
      s+="Item = "+jtwain.getINT32(buf,off)+"\n";                          off+=4;
    }
    return s;
  }
}
