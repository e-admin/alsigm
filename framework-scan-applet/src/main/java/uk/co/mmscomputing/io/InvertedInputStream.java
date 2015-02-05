package uk.co.mmscomputing.io;

import java.io.*;

public class InvertedInputStream extends FilterInputStream{

  public InvertedInputStream(InputStream in)throws IOException{
    super(in);
  }

  public int read()throws IOException{
    return ~in.read();
  }

  public int read(byte[] b)throws IOException{
    int len=in.read(b);
    for(int i=0;i<len;i++){
      b[i]=(byte)~b[i];
    }
    return len;
  }

  public int read(byte[] b, int off, int len)throws IOException{
    len=in.read(b,off,len);
    for(int i=0;i<len;i++){
      b[off+i]=(byte)~b[off+i];
    }
    return len;
  }
}