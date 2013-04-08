package uk.co.mmscomputing.io;

import java.io.*;

public class ModModREADInputStream extends ModHuffmanInputStream implements ModREADTable{

  // T.6 MMR Input Stream. How to use, see ..imageio.tiff.TIFFImageReader

  private   int[]    refline=null;
  private   int[]    codeline=null;

  private   int      clindex=0;                     // index into code line

  private   int      a0;                            // pixel position in scan line
  private   int      b1;                            // index into reference line
  private   int      maxb1;                         // maximum available run length values from reference line
  private   int      code;                          // next READ code
  private   int      width;                         // page width

  public ModModREADInputStream(InputStream in,int width)throws IOException{
    super(in);
    this.width=width;
    refline  =new int[width+3];
    codeline =new int[width+3];
    init(width);
  }

  protected void init(int width)throws IOException{
    clindex     =1;
    codeline[0] =width;                             // setup imaginary white line
    getREADCode();                                  // read first READ code
  }

  protected void initNewLine()throws IOException{
    if(code==HX){readHorizontalMode2();}            // HX is left over when H coded at end of line and second run is zero.

    int[] swap=refline;refline=codeline;codeline=swap;

    maxb1=clindex;                                  // set max possible code index from last line.
    refline[maxb1]  =width;                         // set max possible line width
    refline[maxb1+1]=width;
    refline[maxb1+2]=width;

    clindex=0;b1=0;a0=0;
  }

  public void readEOL()throws IOException{          // remark: T.6 does not use EOL codes
    initNewLine();                                  //         but we need to set up our buffers
  }

  protected int getREADCode()throws IOException{
    needBits(24);
    code=findToken(codes);
    return code;
  }

  private void setB1(){                             // find b1 so that a0 < b1 and ref line and code line runlength have opposite colour
    if(a0==0){                                      
      b1=0;
    }else{
      while((0<b1)&&(a0<refline[b1-1])){b1--;}
      while((b1<maxb1)&&(refline[b1]<=a0)){b1++;}
    }
    if((b1&0x0001)!=(clindex&0x0001)){b1++;}        // want opposite colour
  }

  private int readPassMode()throws IOException{
    int rl=0,len;
    do{
      setB1();                                      // find b1>a0 and opposite colour
      len =refline[b1+1]-a0;                        // b2 - a0
      a0 +=len;
      rl +=len;
    }while(getREADCode()==P);
    rl+=read2D();                                   // colour in pass mode never changes, hence need to add another run
    return rl;
  }

  private int readHorizontalMode1()throws IOException{
    state=((clindex&0x01)==0)?WHITE:BLACK;          // set right 'colour' table in ModHuffmanInputStream
    int rl=super.read();                            // read first mod huffman code
    a0+=rl;
    codeline[clindex++]=a0;
    code=HX;                                        // signal to read second ModHuffman code during next read()
    return rl;
  }

  private int readHorizontalMode2()throws IOException{
    int rl=super.read();                            // read second mod huffman code
    a0+=rl;
    codeline[clindex++]=a0;
    getREADCode();
    return rl;
  }

  private int readVerticalMode()throws IOException{
    setB1();                                        // find b1>a0 and opposite colour

    int offset = code-V0;
    int a1     = refline[b1]+offset;                // a1 = reference line position +- offset
    int rl     = a1-a0;

    a0=a1;
    codeline[clindex++]=a0;
    getREADCode();
    return rl;
  }	

  protected int read2D()throws IOException{
    switch(code){
    case P:    return readPassMode();
    case H:    return readHorizontalMode1();
    case V0:
    case VL1:case VL2:case VL3:
    case VR1:case VR2:case VR3:
               return readVerticalMode();
    case HX:   return readHorizontalMode2();
    case EOFB:                                      // TIFF F : end of strip
    default:   return -1;
    }
  }

  protected int read1D()throws IOException{
    int rl=super.read();
    a0+=rl;
    codeline[clindex++]=a0;
    return rl;
  }

  public int read()throws IOException{
    return read2D();                            // remark: T.6 does only use READ codes, no reference line
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
