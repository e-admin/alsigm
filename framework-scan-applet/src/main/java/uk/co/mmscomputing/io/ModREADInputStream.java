package uk.co.mmscomputing.io;

import java.io.*;

public class ModREADInputStream extends ModModREADInputStream{

  // T.4 MR Input Stream. How to use, see ..imageio.tiff.TIFFImageReader

  private   boolean  isReferenceLine=false;

  public ModREADInputStream(InputStream in,int width)throws IOException{
    super(in,width);
  }

  protected void init(int width)throws IOException{
  }

  public void readEOL()throws IOException{          // TIFFImageReader (Class F T4 MR)
    initNewLine();                                  // initialise for new line scan
    syncWithEOL();                                  // read EOL code
    isReferenceLine=(readBit()==1);                 // 1 = one-dimensional 0 = two-dimensional
    if(!isReferenceLine){
      getREADCode();
    }
  }

  public int read()throws IOException{
    return (isReferenceLine)?read1D():read2D();
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
