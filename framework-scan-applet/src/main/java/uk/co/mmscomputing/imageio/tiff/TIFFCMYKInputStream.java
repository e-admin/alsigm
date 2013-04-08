package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

public class TIFFCMYKInputStream extends FilterInputStream{

  public TIFFCMYKInputStream(InputStream in)throws IOException{
    super(in);
  }

  public int read()throws IOException{

    double C=in.read()/255.0;
    double M=in.read()/255.0;
    double Y=in.read()/255.0;
    double K=in.read()/255.0;

    double R=C*(1.0-K)+K; R=1.0-((R<1.0)?R:1.0); // 1.0 − C(1.0 − K) - K
    double G=M*(1.0-K)+K; G=1.0-((G<1.0)?G:1.0); // 1.0 − M(1.0 − K) - K
    double B=Y*(1.0-K)+K; B=1.0-((B<1.0)?B:1.0); // 1.0 − Y(1.0 − K) - K

    R*=255.0;G*=255.0;B*=255.0;

    return (((int)R)<<16)|(((int)G)<<8)|((int)B);
  }

  public int read(byte[] buf)throws IOException{
    throw new IOException(getClass().getName()+".read:\t\nInternal Error: Please use read(int[] buf,int off,int len).");
  }

  public int read(byte[] buf, int off, int len)throws IOException{
    throw new IOException(getClass().getName()+".read:\t\nInternal Error: Please use read(int[] buf,int off,int len).");
  }

  public int read(int[] buf, int off, int len)throws IOException{
    for(int i=0;i<len;i++){
      int v=read();
      if(v==-1){return (i==0)?-1:i;}
      buf[off+i]=v;      
    }
    return len;
  }
}


