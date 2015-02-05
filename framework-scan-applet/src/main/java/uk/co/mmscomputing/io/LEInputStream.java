package uk.co.mmscomputing.io;

import java.io.*;
import java.net.URL;

public class LEInputStream extends MultiByteInputStream{		//	Little Endian Input Stream

  public LEInputStream(InputStream in){super(in);}

  public LEInputStream(File f) throws IOException{
    super(new FileInputStream(f));
  }

  public LEInputStream(URL url) throws IOException{
    super(((url.openConnection()).getInputStream()));
  }

  public boolean readBoolean() throws IOException{
    return (read()!=0);
  }

  public int readInt() throws IOException{
    int i =(read()&0x000000FF);
    i|=(read()&0x000000FF)<<8;
    i|=(read()&0x000000FF)<<16;
    i|=(read()&0x000000FF)<<24;
    return i;
  }

  public short readShort() throws IOException{
    short i;
    i =(short)(((int)read())&0x000000FF);
    i|=(short)((((int)read())&0x000000FF)<<8);
    return i;
  }

  public int readUnsignedShort() throws IOException{
    int i;
    i =(((int)read())&0x000000FF);
    i|=(((int)read())&0x000000FF)<<8;
    return i;
  }

  public void convertByteToShort(byte[] in,int inoff,short[] out,int outoff){
    int len=out.length;short s;
    for(int k=inoff,i=outoff;i<len;i++){
      s = in[k++];                // little endian
      s&= 0x00FF;
      s|= in[k++]<<8;
      out[i]=s;
    }
  }

  public void convertShortToByte(short[] in,int inoff,byte[] out,int outoff){
    int len=in.length;short s;
    for(int k=outoff,i=inoff;i<len;i++){
      s=in[i];
      out[k++]= (byte)s;          // little endian
      out[k++]= (byte)(s>>8);
    }
  }

  public int read(short[] outbuf, int off, int len)throws IOException{
    byte[] inbuf=new byte[len<<1];
    len=in.read(inbuf)>>1;
    convertByteToShort(inbuf,0,outbuf,off);

/*
    short s;
    for(int k=0,i=off;i<len;i++){
      s = buf[k++];                // little endian
      s&= 0x00FF;
      s|= buf[k++]<<8;
      outbuf[i]=s;
    }
*/
    return len;
  }

  public int read(int[] outbuf, int off, int len)throws IOException{
    int s;
    byte[] buf=new byte[len<<2];
    len=in.read(buf)>>2;
    for(int k=0,i=off;i<len;i++){
      s = buf[k++];                // little endian
      s&= 0x00FF;
      s|= buf[k++]<<8;
      s|= buf[k++]<<16;
      s|= buf[k++]<<24;
      outbuf[i]=s;
    }
    return len;
  }
}

