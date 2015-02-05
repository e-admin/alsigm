package uk.co.mmscomputing.io;

import java.io.*;

public class BitOutputStream extends FilterOutputStream{

  private int    buf=0;
  private int    off=0;
  protected int  count=0;

  public BitOutputStream(){
    this(new ByteArrayOutputStream());
  }

  public BitOutputStream(OutputStream out){
    super(out);
    buf=0;off=0;count=0;
  }

  protected void cbLength(int len)throws IOException{	//	callback for subclasses
  }

  public void write(int bit)throws IOException{
    bit&=0x00000001;
    write(bit,1);
  }

  public void write(int code, int bitCount)throws IOException{
    bitCount+=off;
    code<<=off;                    // shift to right place
    buf|=code;

    while(bitCount>=8) { 
      super.write(buf&0x000000FF);
      buf>>=8;
      bitCount-=8;
      cbLength(++count);
    }
    off=bitCount&0x00000007;       // update offset
  }

  public void pad()throws IOException{
    if(off>0){		
      write(0,8);                  // padding, flush
    }
    buf=0;off=0;count=0;
  }

  public void flush()throws IOException{
    pad();
    super.flush();
  }

  public void close()throws IOException{
    this.flush();
    super.close();
  }

  public byte[] toByteArray()throws IOException{
    if(out instanceof ByteArrayOutputStream){
      super.flush();
      return ((ByteArrayOutputStream)out).toByteArray();
    }else{
      throw new IOException("mmsc - BitOutputStream cannot convert underlying output stream to array of bytes !");
    }
  }

  public void reset()throws IOException{
    count=0;						
    if(out instanceof ByteArrayOutputStream){
      ((ByteArrayOutputStream)out).reset();
    }else{
      throw new IOException("mmsc - BitOutputStream cannot reset underlying output stream !");
    }
  }
}
