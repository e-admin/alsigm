package uk.co.mmscomputing.io;

import java.io.*;

public class ModModREADOutputStream extends READOutputStream{

  // T.6 MMR Output Stream. How to use, see ..imageio.tiff.TIFFImageWriter

  public ModModREADOutputStream(OutputStream out,int width){
    super(out,width);
  }

  public void writeEOFB()throws IOException{        // TIFF Class F does want EOFB after every strip
    write(0x00800800,24);
  }

  public void flush()throws IOException{
    cleanupEOL();
  // the super classes behaviour is to byte-align the stream here, we don't want that.
  // but RLEOutputStream needs to be flushed after every row
  }

  public void close()throws IOException{
    write(0x0000,8);                                // flush last bits
    super.close();
  }

  public void write(int runlen)throws IOException{
    write2D(runlen);                                // T.6 does only use READ codes, no reference line
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
