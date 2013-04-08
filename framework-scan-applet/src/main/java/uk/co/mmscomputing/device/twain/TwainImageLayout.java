package uk.co.mmscomputing.device.twain;

public class TwainImageLayout implements TwainConstants{

/* DAT_IMAGELAYOUT. Provides image layout information in current units.
typedef struct {
   TW_FRAME   Frame;          // Frame coords within larger document
   TW_UINT32  DocumentNumber;
   TW_UINT32  PageNumber;     // Reset when you go to next document
   TW_UINT32  FrameNumber;    // Reset when you go to next page     
} TW_IMAGELAYOUT, FAR * pTW_IMAGELAYOUT;

// No DAT.  Defines a frame rectangle in ICAP_UNITS coordinates.
typedef struct {
   TW_FIX32   Left;
   TW_FIX32   Top;
   TW_FIX32   Right;
   TW_FIX32   Bottom;
} TW_FRAME, FAR * pTW_FRAME;
*/

  TwainSource source;
  byte[] buf=new byte[28];    // TW_IMAGELAYOUT

  public TwainImageLayout(TwainSource source){
    this.source=source;
  }

  public void get()throws TwainIOException{
    source.call(DG_IMAGE,DAT_IMAGELAYOUT,MSG_GET,buf);
  }

  public void getDefault()throws TwainIOException{
    source.call(DG_IMAGE,DAT_IMAGELAYOUT,MSG_GETDEFAULT,buf);
  }

  public void set()throws TwainIOException{
    source.call(DG_IMAGE,DAT_IMAGELAYOUT,MSG_SET,buf);
  }

  public void reset()throws TwainIOException{
    source.call(DG_IMAGE,DAT_IMAGELAYOUT,MSG_RESET,buf);
  }

  public double getLeft(){               return jtwain.getFIX32(buf,0);}
  public void   setLeft(double v){       jtwain.setFIX32(buf,0,v);}
  public double getTop(){                return jtwain.getFIX32(buf,4);}
  public void   setTop(double v){        jtwain.setFIX32(buf,4,v);}
  public double getRight(){              return jtwain.getFIX32(buf,8);}
  public void   setRight(double v){      jtwain.setFIX32(buf,8,v);}
  public double getBottom(){             return jtwain.getFIX32(buf,12);}
  public void   setBottom(double v){     jtwain.setFIX32(buf,12,v);}

  public int    getDocumentNumber(){     return jtwain.getINT32(buf,16);}
  public void   setDocumentNumber(int v){jtwain.setINT32(buf,16,v);}
  public int    getPageNumber(){         return jtwain.getINT32(buf,20);}
  public void   setPageNumber(int v){    jtwain.setINT32(buf,20,v);}
  public int    getFrameNumber(){        return jtwain.getINT32(buf,24);}
  public void   setFrameNumber(int v){   jtwain.setINT32(buf,24,v);}

  public String toString(){
    String s="TwainImageLayout\n";
    s+="\tleft   ="+getLeft()+"\n";
    s+="\ttop    ="+getTop()+"\n";
    s+="\tright  ="+getRight()+"\n";
    s+="\tbottom ="+getBottom()+"\n";

    s+="\tdocument number ="+getDocumentNumber()+"\n";
    s+="\tpage number     ="+getPageNumber()+"\n";
    s+="\tframe number    ="+getFrameNumber()+"\n";
    return s;
  }

}