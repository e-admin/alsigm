package uk.co.mmscomputing.io;

import java.io.*;

public class PackBitsInputStream extends FilterInputStream{

  private byte n;
  private int  b;

  public PackBitsInputStream(InputStream in){
    super(in);
    n=-128;
  }

  public int read()throws IOException{
    while(n==-128){                      // read next counter if n=-128
      b=in.read();
      if(b==-1){return -1;}              // end of stream
      n=(byte)b;
      if((-128<n)&&(n<0)){
        b=in.read();                     // cache value
        n--;
        break;
      }
    }
    if(0<=n){                            // [0   .. 127]
      if(n==0){
        n=-128;                          // read counter n, next time
      }else{
        n--;
      }
      return in.read();                  // copy next byte in stream
    }                                    // [-127 .. -1]
    if(n==-1){
      n=-128;                            // read counter n, next time
    }else{
      n++;
    }
    return b;                            // return cached byte
  }

  public int read(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      int v=read();
      if(v==-1){return (i==0)?-1:i;}
      b[off+i]=(byte)v;      
    }
    return len;
  }

  public static void main(String[] args){
    try{
      int b,i=0;

      // unpacked: AA AA AA 80 00 2A AA AA AA AA 80 00 2A 22 AA AA AA AA AA AA AA AA AA AA

      byte[] data={(byte)0xFE, (byte)0xAA, (byte)0x02, (byte)0x80, 
                   (byte)0x00, (byte)0x2A, (byte)0xFD, (byte)0xAA, 
                   (byte)0x03, (byte)0x80, (byte)0x00, (byte)0x2A, 
                   (byte)0x22, (byte)0xF7, (byte)0xAA
      };
      InputStream in=new PackBitsInputStream(new ByteArrayInputStream(data));
      while((b=in.read())!=-1){
        System.err.print(" "+Integer.toHexString(b));i++;
//        System.err.println("["+i+"]=0x"+Integer.toHexString(b));i++;
      }
      in.close();      
    }catch(Exception e){
      System.out.println(e);
    }    
  }

}

/*
http://developer.apple.com/technotes/tn/tn1023.html
last accessed: 2005-07-25

Adobe TIFF6.PDF p.42
*/