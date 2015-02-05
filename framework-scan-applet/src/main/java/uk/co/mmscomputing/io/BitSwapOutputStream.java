package uk.co.mmscomputing.io;

import java.io.*;

public class BitSwapOutputStream extends FilterOutputStream implements BitSwapTable{

  public BitSwapOutputStream(OutputStream out)throws IOException{
    super(out);
  }

  public void write(int b)throws IOException{
    out.write(bitSwapTable[b&0x000000FF]);
  }

  public void write(byte[] b)throws IOException{
    int len=b.length;
    for(int i=0;i<len;i++){
      b[i]=bitSwapTable[b[i]&0x000000FF];
    }
    out.write(b);
  }

  public void write(byte[] b, int off, int len)throws IOException{
    for(int i=0;i<len;i++){
      b[off+i]=bitSwapTable[b[off+i]&0x000000FF];
    }
    out.write(b,off,len);
  }
}