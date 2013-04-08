package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

import uk.co.mmscomputing.io.BitSwapTable;

public class TIFFClassBOutputStream extends FilterOutputStream implements TIFFConstants,BitSwapTable{

  private ByteArrayOutputStream buf=null;
  private boolean               lastByteWasZero;
  private int                   width,height,offset;

  public TIFFClassBOutputStream(OutputStream out)throws IOException{
    super(out);
    writeHeader();
  }

  public void write(int cw)throws IOException{          // modified huffman code 'bytes'
    if(lastByteWasZero){                                // 0x00,0x80 end of line code words
      if((cw&0x00FF)==0x80){                            // inserted by SFFInputStream
        lastByteWasZero=false;
        height++;                                       // end of line increase height
        return;
      }
      buf.write(0);                                     // if not part of end of line code, write last 0 byte
    }
    if(cw==0x00){                                       // check for possible end of line code
      lastByteWasZero=true;                             // don't write 0 byte just yet, maybe end of line code
    }else{
      lastByteWasZero=false;
      buf.write(bitSwapTable[cw&0x000000FF]);           // write code word
    }
  }

  public void write(byte[] b)throws IOException{
    write(b,0,b.length);
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){write(b[off+i]);}
  }  

  public void writeShort(int i) throws IOException{
    out.write(i&0x000000FF);		    // first : LSB least significant byte
    out.write((i>>8)&0x000000FF);		// MSB most significant byte
  }

  public void writeInt(int i) throws IOException{
    out.write(i&0x000000FF);        // first : LSB least significant byte
    out.write((i>>8)&0x000000FF);
    out.write((i>>16)&0x000000FF);
    out.write((i>>24)&0x000000FF);  // MSB most significant byte
  }

  private void writeHeader()throws IOException{
    writeShort(0x00004949);         // 0: II = intel = little endian
    writeShort(42);                 // 2: version, magic value
//  writeInt(8);                    // 4: offset first Image File Directory
    offset=8;                       // 8: header size
  }

  public void writePageHeader(int width)throws IOException{
    buf=new ByteArrayOutputStream();
    this.width=width;
    this.height=0;
    lastByteWasZero=false;
  }

  public void writePageEnd()throws IOException{
    if(lastByteWasZero){buf.write(0);}         // can that happen ?
    int align=buf.size()&0x01;
    byte[] data=buf.toByteArray();
    int ifdoffset=offset+data.length+align+16; // data.length+xres.size+yres.size
    writeInt(ifdoffset);                       // ptr to ifd; write data in front of ifd
    out.write(data);                           // write to image stream
    if(align>0){out.write(0);}                 // word alignment

    writeInt(203);                             // xres -16
    writeInt(1);    
    writeInt(196);                             // yres -8
    writeInt(1);    

    writeShort(10);                            // IFD +0: count directory entries
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
    writeInt(CCITTGROUP3MODHUFFMAN);
    writeShort(PhotometricInterpretation);     // 262 +38
    writeShort(SHORT);
    writeInt(1);    
    writeInt(WhiteIsZero);
    writeShort(StripOffsets);                  // 273 +50
    writeShort(LONG);
    writeInt(1);                               // all data in one strip !
    writeInt(offset);
    writeShort(RowsPerStrip);                  // 278 +62
    writeShort(LONG);
    writeInt(1);    
    writeInt(height);                          // all data in one strip !
    writeShort(StripByteCounts);               // 279 +74
    writeShort(LONG);
    writeInt(1);    
    writeInt(data.length);                     // all data in one strip !
    writeShort(XResolution);                   // 282 +86
    writeShort(RATIONAL);
    writeInt(1);    
    writeInt(ifdoffset-16);                    // 203.0
    writeShort(YResolution);                   // 283 +98
    writeShort(RATIONAL);
    writeInt(1);    
    writeInt(ifdoffset-8);                     // 196.0
    writeShort(ResolutionUnit);                // 296 +110
    writeShort(SHORT);
    writeInt(1);    
    writeInt(Inch);
                                               //     +122
    offset=ifdoffset+126;
  }

  public void writeDocumentEnd()throws IOException{
    writeInt(0);                               // end of file: ptr to next ifd == NULL
  }
}
