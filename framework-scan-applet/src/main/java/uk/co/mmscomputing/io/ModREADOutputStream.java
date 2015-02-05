package uk.co.mmscomputing.io;

import java.io.*;

public class ModREADOutputStream extends READOutputStream{

  private int kmax;
  private int k;                                    // counter: when to write next reference line 

  public ModREADOutputStream(OutputStream out,int width){
    this(out,width,true);
  }

  public ModREADOutputStream(OutputStream out,int width,boolean highresl){
    super(out,width);
    kmax=(highresl)?3:1;                            // 196:98 dpi
  }

  public void writeEOL()throws IOException{         // one EOL needs to be written before start of page
    super.writeEOL();                               // initialize new line
    write(0,3);write(EOLCW,12);                     // EOL
    if(k==0){      
      write(1,1);                                   // Write one bit; 1=reference line
      k=kmax;                                       // reset line counter
    }else{
      write(0,1);                                   // Write one bit; 0=2-dim codes
      k--;                                          // decrease line counter
    }
  }

  public void write(int runlen)throws IOException{
    if(k==kmax){write1D(runlen);}else{write2D(runlen);}
  }
}

/*
[1] http://www.netnam.vn/unescocourse/computervision/105.htm [last accessed 2005-10-15]
[2] http://www.buddism.ru//DHARMA_text/YagpoTibetanOCR/_OCR/WORK_ARCH/hsfsys2.2/src/lib/image/grp4comp.c [last accessed 2005-10-15]
*/