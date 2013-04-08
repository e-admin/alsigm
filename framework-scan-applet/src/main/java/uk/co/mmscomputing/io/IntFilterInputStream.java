package uk.co.mmscomputing.io;

import java.io.*;

abstract public class IntFilterInputStream extends FilterInputStream{

  public IntFilterInputStream(InputStream in){super(in);}

  public void setIn(InputStream in){this.in=in;}

  public int read(int[] buf)throws IOException{return read(buf,0,buf.length);}

  abstract public int read(int[] buf, int off, int len)throws IOException;
}