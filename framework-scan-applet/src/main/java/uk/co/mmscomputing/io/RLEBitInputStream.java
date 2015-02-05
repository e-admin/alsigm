package uk.co.mmscomputing.io;

import java.io.*;

public class RLEBitInputStream extends FilterInputStream{

  private int			  rlen;                     // the run length
  private int       ccw;                      // the current code word
  private boolean   invert=false;

  public RLEBitInputStream(InputStream in){
    super(in);
    resetToStartCodeWord();
  }

  public void resetToStartCodeWord(){ccw=1;rlen=0;}
  public void setInvert(boolean invert){this.invert=invert;}

  public int read(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      int v=read();
      if(v==-1){return (i==0)?-1:i;}
      b[off+i]=(byte)v;      
    }
    return len;
  }

  public int readBits(int end,int start)throws IOException{
    int b=0;
    for(int i=end;i>=start;i--){
      while(rlen==0){
        rlen=in.read();                         // can be 32 bit number
        if(rlen==-1){return -1;}                // end of stream
        ccw=(ccw+1)&0x01;                       // change current code word
      }
      rlen--;
      if(ccw!=1){b|=(1<<i);}
    }
    return (invert)?((~b)&0x00FF):b;
  }

  public int read()throws IOException{
    int b=0;
    for(int i=7;i>=0;i--){
      while(rlen==0){
        rlen=in.read();                         // can be 32 bit number
        if(rlen==-1){return -1;}                // end of stream
        ccw=(ccw+1)&0x01;                       // change current code word
      }
      rlen--;
      if(ccw!=1){b|=(1<<i);}
    }
    return (invert)?((~b)&0x00FF):b;
  }
}