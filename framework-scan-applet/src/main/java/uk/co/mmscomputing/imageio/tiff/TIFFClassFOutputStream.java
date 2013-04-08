package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

abstract public class TIFFClassFOutputStream extends FilterOutputStream implements TIFFConstants{

  protected ByteArrayOutputStream buf=null;
  protected boolean               lastByteWasZero;
  protected int                   width,height,offset,xres,yres;

  public TIFFClassFOutputStream(OutputStream out)throws IOException{
    super(out);
    writeHeader();
    xres=204;yres=196;
  }

  public void setXYResolution(int xres,int yres){this.xres=xres;this.yres=yres;}

  public void write(int cw)throws IOException{          // MH,MR,MMR code 'bytes'
    lastByteWasZero=(cw==0x00);                         // check for possible end of line code      
    buf.write(cw);                                      // write code word
  }

  public void write(byte[] b)throws IOException{
    write(b,0,b.length);
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){write(b[off+i]);}
  }  

  protected void writeShort(int i) throws IOException{
    out.write(i&0x000000FF);		    // first : LSB least significant byte
    out.write((i>>8)&0x000000FF);		// MSB most significant byte
  }

  protected void writeInt(int i) throws IOException{
    out.write(i&0x000000FF);        // first : LSB least significant byte
    out.write((i>>8)&0x000000FF);
    out.write((i>>16)&0x000000FF);
    out.write((i>>24)&0x000000FF);  // MSB most significant byte
  }

  protected void writeHeader()throws IOException{
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

  abstract public void writePageEnd()throws IOException;

  public void writeDocumentEnd()throws IOException{
    writeInt(0);                               // end of file: ptr to next ifd == NULL
  }
}
