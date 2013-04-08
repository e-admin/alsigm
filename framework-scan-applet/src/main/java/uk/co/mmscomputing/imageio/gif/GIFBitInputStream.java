package uk.co.mmscomputing.imageio.gif;

import java.io.*;

public class GIFBitInputStream extends InputStream{
  private    int         buf;
  private    int         bitsAvail;
  protected  boolean     eof=false;
  protected  int         count,max;
  protected  byte[]      data=new byte[255];
  protected  InputStream in;

  public GIFBitInputStream(InputStream in){
    this.in=in;
  }

  public int read()throws IOException{
    if(count==max){
      max=in.read();                                    // read length of chunk
      if(max==0){return -1;}                            // empty chunk, no data anymore
      if((max<0)||(255<max)){throw new IOException(getClass().getName()+".read:\n\tGIF data chunk length out of bounds ["+max+"]");}
      int len=in.read(data,0,max);                      // read chunk max 255 bytes
      if(len!=max){throw new IOException(getClass().getName()+".read:\n\tGIF data chunk missing.");}
      count=0;
    }
    return data[count++]&0x00FF;
  }

  public int readBits(int bitcount)throws IOException{  // read "count" bit
    if(eof && (bitsAvail==0)){return -1;}
    needBits(bitcount);      
    int bits=getBits(bitcount);
    clrBits(bitcount);
    return bits;
  }

  protected void needBits(int bitcount)throws IOException{
    // Assert(bitcount<32);
    while((eof==false)&&(bitsAvail < bitcount)){
      int b=this.read();
      if(b==-1){eof=true; break;}
      buf |= (b<<bitsAvail);
      bitsAvail += 8;
    }
  }

  protected void clrBits(int bitcount){
    bitsAvail -= bitcount;
    buf >>= bitcount;
  }

  protected int getBits(int bitcount){
    return ((int)buf & ((1<<bitcount)-1));              // mask lower x bits out
  }
}