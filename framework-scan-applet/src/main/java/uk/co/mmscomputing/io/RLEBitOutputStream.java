package uk.co.mmscomputing.io;

import java.io.*;

public class RLEBitOutputStream extends FilterOutputStream{

  private int     ccw;                      // the current code word we are counting the run length for
  private int			rlen;                     // the run length

  private int     bpcw;                     // bits per code word

  public RLEBitOutputStream(OutputStream out){
    super(out);
    bpcw=8;ccw=-1;rlen=0;
  }

  public void setBitsPerCodeWord(int bits){bpcw=bits;}
  public void setStartCodeWord(int cw){ccw=cw;rlen=0;}

  public void write(byte[] b)throws IOException{
    write(b,0,b.length);
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){write(b[off+i]&0x00FF);}
  }

  public void writeBits(int cw,int end,int start)throws IOException{
    switch(bpcw){
      case  1:for(int i=end;i>=start;i--){write8((cw>>i)&0x0001);}break;
      case  2:for(int i=end;i>=start;i--){write8((cw>>(i<<1))&0x0003);}break;
      case  4:for(int i=end;i>=start;i--){write8((cw>>(i<<2))&0x000F);}break;
      default:for(int i=end;i>=start;i--){write8(cw);}
      break;
    }
  }

  public void write(int cw)throws IOException{
    switch(bpcw){
      case  1:    write1(cw);break;
      case  2:    write2(cw);break;
      case  4:    write4(cw);break;
      default:    write8(cw);break;
    }
  }

  private void write1(int cw)throws IOException{
    write8((cw>>7)&0x0001);
    write8((cw>>6)&0x0001);
    write8((cw>>5)&0x0001);
    write8((cw>>4)&0x0001);
    write8((cw>>3)&0x0001);
    write8((cw>>2)&0x0001);
    write8((cw>>1)&0x0001);
    write8((cw   )&0x0001);
  }

  private void write2(int cw)throws IOException{
    write8((cw>>6)&0x0003);
    write8((cw>>4)&0x0003);
    write8((cw>>2)&0x0003);
    write8((cw   )&0x0003);
  }

  private void write4(int cw)throws IOException{
    write8((cw>>4)&0x000F);
    write8((cw   )&0x000F);
  }

  private void write8(int cw)throws IOException{
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
