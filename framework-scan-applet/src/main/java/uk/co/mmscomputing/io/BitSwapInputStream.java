package uk.co.mmscomputing.io;

import java.io.*;

public class BitSwapInputStream extends FilterInputStream implements BitSwapTable{

  public BitSwapInputStream(InputStream in)throws IOException{
    super(in);
  }

  public int read()throws IOException{
    int sample=in.read();
    if(sample==-1){ return -1;}
    sample=bitSwapTable[sample];
    return sample&0x000000FF;
  }

  public int read(byte[] b)throws IOException{
    int len=in.read(b);
    for(int i=0;i<len;i++){
      b[i]=bitSwapTable[b[i]&0x000000FF];
    }
    return len;
  }

  public int read(byte[] b, int off, int len)throws IOException{
    len=in.read(b,off,len);
    for(int i=0;i<len;i++){
      b[off+i]=bitSwapTable[b[off+i]&0x000000FF];
    }
    return len;
  }

  public static void main(String[] argv){
    try{
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      for(int i=0;i<256;i++){baos.write(i);}
      InputStream bais = new BitSwapInputStream(new ByteArrayInputStream(baos.toByteArray()));
      for(int i=0;i<256;i++){
        int b=bais.read();
        String si = Integer.toBinaryString(i);  while(si.length()<8){si="0"+si;}
        String sb = Integer.toBinaryString(b);  while(sb.length()<8){sb="0"+sb;}
        System.out.println(" "+si+" "+sb);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}