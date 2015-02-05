package uk.co.mmscomputing.io;

import java.io.*;

public class RLEOutputStream extends FilterOutputStream{

  private int     ccw;                      // the current code word we are counting the run length for
  private int			rlen;                     // the run length

  private int     bpcw;                     // bytes per code word
  private int     cw,cwi;                   // code word buffer, code word index


  public RLEOutputStream(OutputStream out){
    this(out,1);
  }

  public RLEOutputStream(OutputStream out, int bytesPerCodeWord){
    super(out);
    bpcw=bytesPerCodeWord;
    cw=0;cwi=0;ccw=-1;rlen=0;
  }

  public void setStartCodeWord(int cw){ccw=cw;}

  public void write(byte[] b)throws IOException{
    write(b,0,b.length);
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      cw<<=8;cw|=(b[off+i]&0x000000FF);cwi++;
      if(cwi==bpcw){write(cw);cw=0;cwi=0;}
    }
  }

  public void write(int cw)throws IOException{   // cw can be 32 bit 
    if(cw==ccw){                                 // if still same code word increase run length
      rlen++;
    }else{                                       // else write run length to underlying stream
      out.write(rlen);                           // run length could be 32 bit numbers !!!
      ccw=cw;rlen=1;                             // start counting for new codeword
    }
  }

  public void flush()throws IOException{
    if(rlen>0){out.write(rlen);}
    ccw=-1;rlen=0;
    out.flush();
  }
}
