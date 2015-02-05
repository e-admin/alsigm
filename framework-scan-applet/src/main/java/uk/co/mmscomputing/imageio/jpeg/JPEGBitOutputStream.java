package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGBitOutputStream extends FilterOutputStream{

  private   int bitBuffer;                     // entropy buffer; huffman coding
  private   int bitCount;

  public JPEGBitOutputStream(OutputStream out){
    super(out);
    bitCount=0;
  }

  public void writeBit(int code)throws IOException{
    writeBits(code&0x01,1);
  }

  public void writeBits(int code,int size)throws IOException{
    code&=~(-1<<size);

    bitCount+=size;
    bitBuffer|=code<<(32-bitCount);

    while(bitCount>=8) { 
      int b=(bitBuffer>>>24)&0x000000FF;
      out.write(b);
      if(b==0x00FF){                           // byte stuffing
        out.write(0x00);
      }
      bitBuffer<<=8;
      bitCount-=8;
    }
  }

  public void flush()throws IOException{
    if(bitCount>0){
      writeBits(-1,8-bitCount);
    }
    out.flush();
  }
}