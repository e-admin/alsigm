package uk.co.mmscomputing.io;

import java.io.*;

abstract public class READOutputStream extends ModHuffmanOutputStream{

  // T.6 MMR Output Stream. How to use, see ..imageio.tiff.TIFFImageWriter

  private   boolean  H2;                            // if true: code next run length as mod huffman code

  private   int      clindex=0;                     // index into code line

  private   int      a0;                            // pixel position in scan line
  private   int      b1;                            // index into reference line
  private   int      maxb1;                         // maximum available run length values from reference line
  private   int      width;                         // page width

  private   int[]    refline=null;
  private   int[]    codeline=null;

  public READOutputStream(OutputStream out,int width){
    super(out);init(width);
  }

  private void init(int width){
    this.width=width;

    refline  =new int[width+3];
    codeline =new int[width+3];

    H2=false;

    clindex     =0;
    codeline[0] =width;                             // setup imaginary white line
  }

  protected void cleanupEOL()throws IOException{
    if(H2){writeH2(0);}                             // write second part of H code, if left over
  }

  public void flush()throws IOException{
    cleanupEOL();
    super.flush();                                  // padding
  }

  public void writeEOL()throws IOException{         // MMR does not write EOL code, but we can do our initialisations here
    int[] swap=refline;refline=codeline;codeline=swap;

    maxb1=clindex;                                  // set max possible code index from last line.
    refline[maxb1]  =width;                         // set max possible line width
    refline[maxb1+1]=width;
    refline[maxb1+2]=width;

    clindex=0;b1=0;a0=0;H2=false;
  }

  private void writeH2(int rl)throws IOException{
    H2=false;write1D(rl);
  }

  private void setB1(){                             // find b1 so that a0 < b1 
    if(a0==0){                                      // and ref line and code line runlength have opposite colour
      b1=0;
    }else{
      while((0<b1)&&(a0<refline[b1-1])){b1--;}
      while((b1<maxb1)&&(refline[b1]<=a0)){b1++;}
    }
    if((b1&0x0001)!=(clindex&0x0001)){b1++;}        // want opposite colour
  }

  protected void write2D(int rl)throws IOException{
    if(H2){writeH2(rl);return;}                     // write second part of H code

    int a1=a0+rl;

    setB1();                                        // find b1>a0 and opposite colour
    while(refline[b1+1]<a1){                        // pass mode while b2<a1
      write(0x0008,4);                              // P
      a0=refline[b1+1];                             // a0=b2
      setB1();                                      // find b1>a0 and opposite colour
    }
    int offset=a1-refline[b1];
    if(Math.abs(offset)<=3){                        // vertical codes
      switch(offset){
      case -3:write(0x0020,7);break;                // VL3: a1 is three pixels to the left of b1        
      case -2:write(0x0010,6);break;                // VL2: a1 is two   pixels to the left of b1
      case -1:write(0x0002,3);break;                // VL1: a1 is one   pixel  to the left of b1
      case  0:write(0x0001,1);break;                // V0 : a1 is under b1
      case  1:write(0x0006,3);break;                // VR1: a1 is one   pixel  to the right of b1
      case  2:write(0x0030,6);break;                // VR2: a1 is two   pixels to the right of b1
      case  3:write(0x0060,7);break;                // VR3: a1 is three pixels to the right of b1
      }
      a0=a1;
      codeline[clindex++]=a0;
    }else{                                          // horizontal code
      write(0x0004,3);                              // H
      write1D(a1-a0);                               // write rest of runlen as mod huffman code
      H2=true;                                      // write next runlen as mod huffman code
    }
  }

  protected void write1D(int runlen)throws IOException{
    state=((clindex&0x01)==0)?WHITE:BLACK;          // set right 'colour' table in ModHuffmanInputStream
    super.write(runlen);                            // write Modified Huffman code
    a0+=runlen;
    codeline[clindex++]=a0;
  }
}
/*
  [1] FAX: Facsimile Technology and Systems (3rd);
      Kenneth McConnell,Dennis Bodson,Stephen Urban
      Artech House Publishers
      ISBN: 0-89006-944-1

  [2] RFC 2306: Tag Image File Format (TIFF) - F Profile for Facsimile
  [3] ITU T.4: Group 3 MR code; Modified READ Code; READ = Relative Element Address Designate
  [4] ITU T.6: Group 4 MMR code; Modified Modified READ
  [5] http://einstein.informatik.uni-oldenburg.de/rechnernetze/ccitt_t4.htm [last accessed: 2005-08-26]
  [6] http://www.buddism.ru//DHARMA_text/YagpoTibetanOCR/_OCR/WORK_ARCH/hsfsys2.2/src/lib/image/grp4deco.c [last accessed: 2005-10-13]
*/
