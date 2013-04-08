package uk.co.mmscomputing.io;

import java.io.*;

abstract public class MultiByteInputStream extends FilterInputStream{

  public MultiByteInputStream(InputStream in){super(in);}

  abstract public void convertShortToByte(short[] in,int inoff,byte[] out,int outoff);
  abstract public void convertByteToShort(byte[] in,int inoff,short[] out,int outoff);

           public int read(short[] b)throws IOException{return read(b,0,b.length);}
  abstract public int read(short[] b, int off, int len)throws IOException;

  abstract public int read(int[] b, int off, int len)throws IOException;
//abstract public int read(long[] b, int off, int len)throws IOException;
}