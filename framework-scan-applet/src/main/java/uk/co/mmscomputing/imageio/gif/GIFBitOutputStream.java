package uk.co.mmscomputing.imageio.gif;

import java.io.*;

public class GIFBitOutputStream extends OutputStream{
  private int    buf=0;
  private int    off=0;
  private int    count=0;

  private OutputStream out;
  private byte[] buffer=new byte[255];

  public GIFBitOutputStream(OutputStream out){
    this.out=out;
    buf=0;off=0;count=0;
  }

  public void write(int b)throws IOException{
    out.write(b);
  }

  public void writeBits(int code,int size)throws IOException{
    size+=off;
    code<<=off;
    buf|=code;
    while(size>=8) { 
      buffer[count++]=(byte)(buf&0x000000FF);
      buf>>=8;size-=8;
      if(count==buffer.length){
        out.write(count);
        out.write(buffer);
        count=0;
      }
    }
    off=size&0x00000007;             // update offset
  }

  public void flush()throws IOException{
    if(off>0){writeBits(0,7);}       // padding, flush
    buf=0;off=0;
    if(count>0){
      out.write(count);
      out.write(buffer,0,count);
      count=0;
      out.write(count);              // write empty length data sub block terminator
    }
    out.flush();
  }

  public void close()throws IOException{
    flush();
    out.close();
  }
}
