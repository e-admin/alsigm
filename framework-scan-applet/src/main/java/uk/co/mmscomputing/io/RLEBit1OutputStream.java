package uk.co.mmscomputing.io;

import java.io.*;

public class RLEBit1OutputStream extends FilterOutputStream{

  private int     ccw;                      // the current code word we are counting the run length for
  private int			rlen;                     // the run length

  public RLEBit1OutputStream(OutputStream out){
    super(out);
    ccw=0x01;rlen=0;
  }

  public void setStartCodeWord(int cw){ccw=cw&0x01;rlen=0;}

  public void write(byte[] b)throws IOException{
    write(b,0,b.length);
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){write(b[off+i]&0x00FF);}
  }

  public void writeBits(int cw,int end,int start)throws IOException{
    for(int i=end;i>=start;i--){
      writecw((cw>>i)&0x0001);
    }
  }

  public void write(int cw)throws IOException{
    writecw((cw>>7)&0x0001);
    writecw((cw>>6)&0x0001);
    writecw((cw>>5)&0x0001);
    writecw((cw>>4)&0x0001);
    writecw((cw>>3)&0x0001);
    writecw((cw>>2)&0x0001);
    writecw((cw>>1)&0x0001);
    writecw((cw   )&0x0001);
  }

  protected void writecw(int cw)throws IOException{
    if(cw==ccw){                                 // if still same code word increase run length
      rlen++;
    }else{                                       // else write run length to underlying stream
      out.write(rlen);                           // run length could be 32 bit numbers !!!
      ccw=cw;rlen=1;                             // start counting for new codeword
    }
  }

  public void flush()throws IOException{
    if(rlen>0){out.write(rlen);}
    rlen=0;
    out.flush();
  }
}
