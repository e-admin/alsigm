package uk.co.mmscomputing.io;

import java.io.*;

public class RGBInputStream extends IntFilterInputStream{

  private int spp; // samples per pixel
  private boolean alpha;

  public RGBInputStream(InputStream in,int spp,boolean alpha)throws IOException{
    super(in);
    this.spp=spp;
    this.alpha=alpha;
  }

  public RGBInputStream(InputStream in,int spp)throws IOException{
    this(in,spp,false);
  }

  public int read()throws IOException{
    int rgb=0xFF000000;                        // alpha = opaque
    rgb|=(in.read()&0x00FF)<<16;
    rgb|=(in.read()&0x00FF)<< 8;
    rgb|=(in.read()&0x00FF);
    if(alpha){
      for(int i=3;i<(spp-1);i++){in.read();}
      rgb|=(in.read()&0x00FF)<<24;             // alpha
    }else{
      for(int i=3;i<spp;i++){in.read();}
    }
    return rgb;
  }

  public int read(byte[] b)throws IOException{
    throw new IOException(getClass().getName()+".read:\t\nInternal Error: Please use read(int[] buf).");
  }

  public int read(byte[] b, int off, int len)throws IOException{
    throw new IOException(getClass().getName()+".read:\t\nInternal Error: Please use read(int[] buf,int off,int len).");
  }

  public int read(int[] b)throws IOException{
    int i=0;
    while(i<b.length){ b[i]=read();i++;}
    return i;
  }

  public int read(int[] b, int off, int len)throws IOException{
    int i=0;
    while(i<len){ b[off+i]=read();i++;}
    return i;
  }
}