package uk.co.mmscomputing.imageio.sff;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
@SuppressWarnings("unused")
public class SFFOutputStream extends BufferedOutputStream{


private byte[]  G3WhiteLineCode=new byte[]{(byte)0xB2,(byte)0x59,(byte)0x01}; // 1728;(other codes are possible 1704+24)

  private byte[]  buf=null;
  private boolean lastByteWasZero;
  private int     index;
  private int     width=0/*,xres*/,yres;
  private int     whiteLines;

  public SFFOutputStream(OutputStream out)throws IOException{
    super(out);
    writeHeader();
    index=0;lastByteWasZero=false;
    whiteLines=0;
//    xres=203;
    yres=196;
  }

  public void setXYResolution(int xres,int yres){
//    this.xres=xres;                                     // should be 203 dpi; we don't know any other value!
    this.yres=yres;                                     // should be 98 dpi or 196 dpi
  }

  public void write(int cw)throws IOException{          // modified huffman code 'bytes'
    buf[index++]=(byte)cw;
    if(lastByteWasZero){                                // 0x00,0x80 end of line code words
      if((cw&0x00FF)==0x80){                            // inserted by SFFInputStream or ModHuffmanOutputStream
        writeLine(buf,index-2);index=0;                 // write line data        
      }
    }
    lastByteWasZero=(cw==0x00);                         // check for possible end of line code
  }

  public void write(byte[] b)throws IOException{
    write(b,0,b.length);
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      write(b[off+i]);
    }
  }  

  // Common-ISDN-API Part I : Annex B
  //  http://www.capi.org/download/capi20-1.pdf

  private void writeLine(byte[] buf,int len)throws IOException{
/*
    //  Nobody seems to count white lines; Maybe for a reason.

    if((width==1728)&&(len==G3WhiteLineCode.length)){   // may have another white line
      int i=0;
      while(i<len){                                     // compare with white line code bytes
        if(buf[i]!=G3WhiteLineCode[i]){break;}          // not a white line
        i++;
      }
      if(i==len){whiteLines++;return;}                  // found white line                                                
    }
    writeWhiteLines();                                  // write any white lines first
*/
    if(len>0){
      if(len<=216){
        out.write(len);
      }else{
        out.write(0); 
        out.write(len&0x0FF);out.write((len>>8)&0x0FF); // little endian (low byte first)
      }
      out.write(buf,0,len);
    }
  }

  private void writeWhiteLines()throws IOException{
    if(whiteLines>0){                                  
      while(whiteLines>37){                             // max 37: 217..253
        out.write(253);whiteLines-=37;
      }
      out.write(216+whiteLines);whiteLines=0;
    }
  }

  private void writeHeader()throws IOException{
    out.write('S');out.write('f');                      // 0:  magic value
    out.write('f');out.write('f');
    out.write(0x01);out.write(0x00);                    // 4:  version 0x01 5: 0x00
    out.write(0x00);out.write(0x00);                    // 6:  userInfo, not used by capi
    out.write(0x00);out.write(0x00);                    // 8:  page count; 0x0000 if not known
    out.write(0x14);out.write(0x00);                    // 10: first page header
    out.write(0);out.write(0);out.write(0);out.write(0);// 12: last page header; 0x00000000 if not known
    out.write(0);out.write(0);out.write(0);out.write(0);// 16: offset document end; 0x00000000 if not known
  }                                                     // 20: 0x14

  public void writePageHeader(int width)throws IOException{
    if((buf==null)||(buf.length!=width)){buf=new byte[width];}
    this.width=width;
    out.write(254);                                     // 0:  pageSignature
    out.write(0x10);                                    // 1:  offset from after this byte to data 
    out.write((yres==98)?0x00:0x01);                    // 2:  resVert 0x00 => 98lpi 0x01 => 196lpi
    out.write(0x00);                                    // 3:  resHorz = 0x00 => 203dpi
    out.write(0x00);                                    // 4:  coding 0x00 => modified huffman
    out.write(0);		                                    // 5:  reserved
    out.write(width&0x0FF);out.write((width>>8)&0x0FF); // 6:  width
    out.write(0);out.write(0);                          // 8:  height; 0x0000 if not known
    out.write(0);out.write(0);out.write(0);out.write(0);// 10: offset previous page
    out.write(0);out.write(0);out.write(0);out.write(0);// 14: offset next page
    index=0;lastByteWasZero=false;
  }                                                     // 18: end of page: start image data

  public void writePageEnd()throws IOException{
    writeLine(buf,index);                               // write line data        
  }

  public void writeDocumentEnd()throws IOException{
    out.write(254);                                     // 0:  pageSignature
    out.write(0x00);                                    // 1:  0x00
  }
}