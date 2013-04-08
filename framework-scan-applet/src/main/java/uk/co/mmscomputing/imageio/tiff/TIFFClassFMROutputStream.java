package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

public class TIFFClassFMROutputStream extends TIFFClassFOutputStream{

  // TIFF Class F; T.4 MR; byte aligned EOLs

  public TIFFClassFMROutputStream(OutputStream out)throws IOException{
    super(out);
  }

  public void write(int cw)throws IOException{          // MR code 'bytes'
    if(lastByteWasZero&&((cw&0x00FF)==0x80)){           // 0x00,0x80 end of line code word
      height++;                                         // end of line increase height
    }
    super.write(cw);
  }

  public void writePageEnd()throws IOException{
    if(lastByteWasZero){buf.write(0);}
    int align=buf.size()&0x01;
    byte[] data=buf.toByteArray();
    int ifdoffset=offset+data.length+align+16; // data.length+xres.size+yres.size
    writeInt(ifdoffset);                       // ptr to ifd; write data in front of ifd
    out.write(data);                           // write to image stream
    if(align>0){out.write(0);}                 // word alignment

    writeInt(xres);                            // xres -16
    writeInt(1);    
    writeInt(yres);                            // yres -8
    writeInt(1);    

    writeShort(12);                            // IFD +0: count directory entries
                                               // entries need to be in tag order !
    writeShort(ImageWidth);                    // 256 +2
    writeShort(LONG);
    writeInt(1);    
    writeInt(width);
    writeShort(ImageLength);                   // 257 +14
    writeShort(LONG);
    writeInt(1);    
    writeInt(height);
    writeShort(Compression);                   // 259 +26
    writeShort(SHORT);
    writeInt(1);    
    writeInt(CCITTFAXT4);
    writeShort(PhotometricInterpretation);     // 262 +38
    writeShort(SHORT);
    writeInt(1);    
    writeInt(WhiteIsZero);

    writeShort(FillOrder);                     // 266 +50
    writeShort(SHORT);
    writeInt(1);    
    writeInt(2);                               // 2 = Least Significant Bit first

    writeShort(StripOffsets);                  // 273 +62
    writeShort(LONG);
    writeInt(1);                               // all data in one strip !
    writeInt(offset);

    writeShort(RowsPerStrip);                  // 278 +74
    writeShort(LONG);
    writeInt(1);    
    writeInt(height);                          // all data in one strip !
    writeShort(StripByteCounts);               // 279 +86
    writeShort(LONG);
    writeInt(1);    
    writeInt(data.length);                     // all data in one strip !
    writeShort(XResolution);                   // 282 +98
    writeShort(RATIONAL);
    writeInt(1);    
    writeInt(ifdoffset-16);                    // 203.0
    writeShort(YResolution);                   // 283 +110
    writeShort(RATIONAL);
    writeInt(1);    
    writeInt(ifdoffset-8);                     // 196.0

    writeShort(T4Options);                     // 292 +122
    writeShort(LONG);
    writeInt(1);    
    writeInt(5);                               // 292 MR; byte aligned EOLs

    writeShort(ResolutionUnit);                // 296 +134
    writeShort(SHORT);
    writeInt(1);    
    writeInt(Inch);

    offset=ifdoffset+150;
  }
}
