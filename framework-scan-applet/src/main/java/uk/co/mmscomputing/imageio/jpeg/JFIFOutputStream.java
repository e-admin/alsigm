package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JFIFOutputStream extends JPEGOutputStream{

  protected int version=0x102;          // 1.02
  protected int units=0;                // density unit = 0 => Only the aspect ratio is specified.
                                        // density unit = 1 => Density in pixels per inch.
                                        // density unit = 2 => Density in pixels per centimeter.

  protected int xDensity=1,yDensity=1;
  protected int xThumbnail=0,yThumbnail=0;  // no support yet [2006-01-12]

  public JFIFOutputStream(
      OutputStream out
  )throws IOException{
    super(out);                                                             // start of image
  }

  public JFIFOutputStream(
      OutputStream out,
      boolean      isYCbCr              // gray scale (one component) OR Y-Cb-Cr (three components)      
  )throws IOException{
    super(out);                                                             // start of image

    setZZQuantizationTable(0,JPEGConstants.LQT2);                           // Y component
    setRawDCHuffmanTable(0,JPEGConstants.HLDCTable);
    setRawACHuffmanTable(0,JPEGConstants.HLACTable);

    if(isYCbCr){
      setZZQuantizationTable(1,JPEGConstants.CQT2);                         // Cb,Cr Component
      setRawDCHuffmanTable(1,JPEGConstants.HCDCTable);
      setRawACHuffmanTable(1,JPEGConstants.HCACTable);
    }
  }

  public JFIFOutputStream(
      OutputStream out,
      boolean      isYCbCr,             // gray scale (one component) OR Y-Cb-Cr (three components)      
      int          height,
      int          width
  )throws IOException{
    super(out);                                                             // SOI : start of image

    System.out.println("3\b"+getClass().getName()+"\n\tMMSC-JPEG Encoder.");

    app0(out);                                                              // APP0: straight after SOI

    setZZQuantizationTable(0,JPEGConstants.LQT2);                           // Y,Gray,(RGB) component
    setRawDCHuffmanTable(0,JPEGConstants.HLDCTable);
    setRawACHuffmanTable(0,JPEGConstants.HLACTable);

    if(isYCbCr){
      setZZQuantizationTable(1,JPEGConstants.CQT2);                         // Cb,Cr Component
      setRawDCHuffmanTable(1,JPEGConstants.HCDCTable);
      setRawACHuffmanTable(1,JPEGConstants.HCACTable);

      defineQuantizationTables();                                           // write QTs
      defineHuffmanTables();                                                // write HTs

      int[] hv ={0x22,0x11,0x11};                                           // (Hi<<4)|Vi
      int[] q  ={0,1,1};                                                    // quantization table Y=0, Cb=Cr=1
      startOfFrame(height,width,hv,q);                                      // SOF
      int[] sel={0,1,1};                                                    // DC,AC code table Y=0, Cb=Cr=1
      startOfScan(sel);                                                     // SOS
    }else{
      defineQuantizationTables();                                           // write QTs
      defineHuffmanTables();                                                // write HTs

      int[] hv ={0x11};                                                     // (Hi<<4)|Vi
      int[] q  ={0};                                                        // quantization table Y=0, Cb=Cr=1
      startOfFrame(height,width,hv,q);                                      // SOF
      int[] sel={0};                                                        // DC,AC code table Y=0, Cb=Cr=1
      startOfScan(sel);                                                     // SOS
    }      
  }

  public void setUnits(int units){this.units=units;}
  public int  getUnits(){return units;}
  public void setXDensity(int xDensity){this.xDensity=xDensity;}
  public int  getXDensity(){return xDensity;}
  public void setYDensity(int yDensity){this.yDensity=yDensity;}
  public int  getYDensity(){return yDensity;}

  protected void app0(OutputStream out)throws IOException{                  // 0xE0
    ByteArrayOutputStream  jfif   =new ByteArrayOutputStream();

    jfif.write('J');                                                        // JFIF identifier
    jfif.write('F');                                            
    jfif.write('I');                                            
    jfif.write('F');                                            
    jfif.write('\0');                                            
    jfif.write((version>>8)&0x000000FF);                                    // version
    jfif.write( version    &0x000000FF);
    jfif.write(units);                                                      // units
    jfif.write((xDensity>>8)&0x000000FF);                                   // Xdensity
    jfif.write( xDensity    &0x000000FF);                                                   
    jfif.write((yDensity>>8)&0x000000FF);                                   // Ydensity
    jfif.write( yDensity    &0x000000FF);                                                   
    jfif.write(xThumbnail);                                                 // Xthumbnail
    jfif.write(yThumbnail);                                                 // Ythumbnail

    // to do thumbnail

    byte[] data = jfif.toByteArray();
    int    len  = data.length+2;

    out.write(0xFF);                                                        // JFIF
    out.write(JPEGConstants.APP0);
    out.write((len>>8)&0x000000FF);
    out.write( len    &0x000000FF);
    out.write(data);
  }

  public void writeHeader(OutputStream out,int height, int width)throws IOException{
    app0(out);                                                              // app0 straight after SOI
      
    defineQuantizationTables();                                             // write QTs
    defineHuffmanTables();                                                  // write HTs

    int[] hv={0x22,0x11,0x11};                                              // (Hi<<4)|Vi
    int[] q={0,1,1};                                                        // quantization table Y=0, Cb=Cr=1
    startOfFrame(height,width,hv,q);
    int[] sel={0,1,1};                                                      // DC,AC code table Y=0, Cb=Cr=1
    startOfScan(sel);
  }

  protected int convert(int c){
    double R=(c>>16)&0x000000FF;
    double G=(c>> 8)&0x000000FF;
    double B= c     &0x000000FF;

    int Y  =(int)( 0.299   *R + 0.587    *G + 0.114     *B);          if(Y<0){Y=0;}else if(Y>255){Y=255;}
    int Cb =(int)(-0.168736*R - 0.331264 *G + 0.5       *B + 128.0);  if(Cb<0){Cb=0;}else if(Cb>255){Cb=255;}
    int Cr =(int)( 0.5     *R - 0.4186876*G - 0.08131241*B + 128.0);  if(Cr<0){Cr=0;}else if(Cr>255){Cr=255;}

    return (Y<<16)|(Cb<<8)|Cr;
  }

  // Expect RGB data

  public void write(int c)throws IOException{
    c=convert(c);
    super.write((c>>16)&0x000000FF);
    super.write((c>> 8)&0x000000FF);
    super.write( c     &0x000000FF);
  }

  // use for RGB pictures (three components)

  public void write(int[] buf)throws IOException{
    int len=buf.length;for(int i=0;i<len;i++){write(buf[i]);}
  }
  public void write(int[] buf, int off, int len)throws IOException{
    for(int i=0;i<len;i++){write(buf[off+i]);}
  }

  // use only for grayscale pictures (one component)

  public void write(byte[] buf, int off, int len)throws IOException{
    for(int i=0;i<len;i++){super.write(buf[off+i]);}
  }
}

// [1] JPEG File Interchange Format (JFIF)
//     Version 1.02 [1992-09-01]
// http://www.jpeg.org/public/jfif.pdf [last accessed 2005-11-28]

