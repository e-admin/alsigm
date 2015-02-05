package uk.co.mmscomputing.io;

import java.io.*;

public class RLEInputStream extends FilterInputStream{

  private int			  rlen;                     // the run length
  private int       ccwi;                     // the current code word
  private int[]     c=new int[2];             // code words

  private int       bpcw;                     // bytes per code word
  private int       cwi;                      // code word index
  private byte[][]  ccw=new byte[2][4];       // code word buffer

  public RLEInputStream(InputStream in)throws IOException{
    this(in,1);
  }

  public RLEInputStream(InputStream in, int bytesPerCodeWord){
    super(in);
    setCodeWords(0x00FFFFFF,0x00000000);      // white and black
    bpcw=bytesPerCodeWord;
    resetToStartCodeWord();
  }

  public void setCodeWords(int c1,int c2){    // Assume c1 and c2 are not -1
    if(c1==-1){c1--;}                         // need -1 for eos signal
    if(c2==-1){c2--;}                         // need -1 for eos signal
    c[0]=c1;
    ccw[0][0]=(byte)( c1     &0x000000FF);
    ccw[0][1]=(byte)((c1>> 8)&0x000000FF);
    ccw[0][2]=(byte)((c1>>16)&0x000000FF);
    ccw[0][3]=(byte)((c1>>24)&0x000000FF);

    c[1]=c2;
    ccw[1][0]=(byte)( c2     &0x000000FF);
    ccw[1][1]=(byte)((c2>> 8)&0x000000FF);
    ccw[1][2]=(byte)((c2>>16)&0x000000FF);
    ccw[1][3]=(byte)((c2>>24)&0x000000FF);
  }

  public void resetToStartCodeWord(){ccwi=-1;cwi=0;rlen=0;}

  public int read()throws IOException{
    while(rlen==0){
      rlen=in.read();                         // can be 32 bit number
      if(rlen==-1){return -1;}                // end of stream
      ccwi=(ccwi+1)&0x01;                     // change current code word
    }
    rlen--;
    return c[ccwi];
  }

  private int pread()throws IOException{
    while(rlen==0){
      rlen=in.read();                         // can be 32 bit number
      if(rlen==-1){return -1;}                // end of stream
      ccwi=(ccwi+1)&0x01;                     // change current code word
    }
    int b=ccw[ccwi][cwi];
    cwi++;if(cwi==bpcw){cwi=0;rlen--;}
    return b&0x00FF;
  }

  public int read(byte[] b,int off,int len)throws IOException{
    if(b==null){ 
      throw new NullPointerException(getClass().getName()+".read(byte[] b, int off, int len): b is null");
    }
    if((off<0)||(len<0)||(b.length<(off+len))){
      throw new IndexOutOfBoundsException(getClass().getName()+".read(byte[] b, int off, int len): index off or len out of bounds.");
    }
    int i=0;
    while(i<len){
      int v=pread();
      if(v==-1){return (i==0)?-1:i;}
      b[off+i]=(byte)v;
      i++;
    }
    return len;
  }

  public static void main(String[] argv){
    try{
      byte[] buf={2,4,9};
      ByteArrayInputStream   bais=new ByteArrayInputStream(buf);
      RLEInputStream         rlis=new RLEInputStream(bais,3);
      rlis.setCodeWords(0x00CCBBAA,0x00332211);

/*
      int b,i=0;
      while((b=rlis.read())!=-1){
        System.out.println("["+i+"]="+Integer.toHexString(b));i++;
      }
*/
      buf=new byte[50];
      int len=rlis.read(buf);
      for(int i=0;i<len;i++){
        System.out.println("["+i+"]="+Integer.toHexString(buf[i]));
      }       
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}