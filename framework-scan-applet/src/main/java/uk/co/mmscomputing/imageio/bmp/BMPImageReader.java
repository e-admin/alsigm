package uk.co.mmscomputing.imageio.bmp;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.ArrayList;

import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.spi.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;
@SuppressWarnings("unused")
public class BMPImageReader extends ImageReader implements BMPConstants{

  private boolean gotHeader = false;

  
private int	size=0;
  private int	offset=0;
 
  private int   headerSize = 40;              // InfoHeader Offset
  private int   width;                        // Width 
  private int   height;                       // Height 
  private int   planes = 1;                   // BitPlanes on Target Device 
  private int   bitsPerPixel = 24;            // Bits per Pixel, default = RGB 
  private int   compression = 0;              // Bitmap Compression 
  private int   sizeImage;                    // Bitmap Image Size 
  private int   xPelsPerMeter = 2953;         // Horiz Pixels Per Meter, default 75 dpi 
  private int   yPelsPerMeter = 2953;         // Vert Pixels Per Meter, default 75 dpi 
  private int   clrUsed = 0;                  // Number of ColorMap Entries 
  private int   clrImportant = 0;             // Number of important colours
        
//  private int[] colortable=null;
  private IndexColorModel icm=null;

  private int   redMask=0;                    // 16bit, 32bit compression
  private int   greenMask=0;
  private int   blueMask=0;

  protected BMPImageReader(ImageReaderSpi originatingProvider){
    super(originatingProvider);
  }

  public BufferedImage read(int imageIndex, ImageReadParam param)throws IOException{
    checkIndex(imageIndex);
    return read((ImageInputStream)getInput());
  }

  public int getHeight(int imageIndex)throws IOException{
    checkIndex(imageIndex);
    readHeader((ImageInputStream)getInput());
    return height;
  }

  public int getWidth(int imageIndex)throws IOException{
    checkIndex(imageIndex);
    readHeader((ImageInputStream)getInput());
    return width;
  }

  @SuppressWarnings("unchecked")
public Iterator getImageTypes(int imageIndex)throws IOException{
//System.err.println("BMPImageReader.getImageTypes "+imageIndex);
    checkIndex(imageIndex);
    readHeader((ImageInputStream)getInput());

    java.util.List l = new ArrayList();
//    l.add(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_BYTE_BINARY));
//    l.add(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_BYTE_INDEXED));
//    l.add(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_USHORT_555_RGB));
//    l.add(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_3BYTE_BGR));
//    l.add(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB));

    return l.iterator();
  }

  public int getNumImages(boolean allowSearch)throws IOException{
    return 1;
  }

  public IIOMetadata getImageMetadata(int imageIndex)throws IOException{
    BMPMetadata md=new BMPMetadata();
    md.setWidth(getWidth(imageIndex));
    md.setHeight(getHeight(imageIndex));
//    private int   planes = 1;                   // BitPlanes on Target Device
    md.setBitsPerPixel(bitsPerPixel);
    md.setCompression(compression);
    md.setImageSize(sizeImage);
    md.setXPixelsPerMeter(xPelsPerMeter);
    md.setYPixelsPerMeter(yPelsPerMeter);
    md.setColorsUsed(clrUsed);
    md.setIndexColorModel(icm);
    md.setColorsImportant(clrImportant);
    md.setRedMask(redMask);md.setGreenMask(greenMask);md.setBlueMask(blueMask);
    return md;
  }

  public IIOMetadata getStreamMetadata() throws IOException{
    return null;
  }

  private void checkIndex(int imageIndex) {
    if (imageIndex != 0) {
      throw new IndexOutOfBoundsException(getClass().getName()+".checkIndex:\n\tBad index in bmp image reader.");
    }
  }

  int getColorsInPalette(){
    if(clrUsed!=0){                           // as in info header
      return clrUsed;
    }else if(bitsPerPixel<16){                // 2, 16, 256 colours
      return 1<<bitsPerPixel;
    }else{                                    // 0 for 16,24,32 bits per pixel
      return 0;
    }
  }

  private void readHeader(ImageInputStream in)throws IOException{
    if (gotHeader) { return; }
    gotHeader = true;

    in.setByteOrder(ByteOrder.LITTLE_ENDIAN);

    byte[] bm = new byte[2];
    in.readFully(bm);
    if((bm[0]!=(byte)'B')||(bm[1]!=(byte)'M')){
      throw new IOException("Invalid BMP 3.0 File.");
    }
    size=in.readInt();
    in.readUnsignedShort();
    in.readUnsignedShort();
    offset=in.readInt();

    headerSize = in.readInt();
    if(headerSize == 12){
      width = in.readUnsignedShort();
      height = in.readUnsignedShort();
      planes = in.readUnsignedShort();
      bitsPerPixel = in.readUnsignedShort();

      compression = 0;                        // BI_RGB
      xPelsPerMeter = 2953;                   // default 75 dpi
      yPelsPerMeter = 2953;
      clrUsed = 0;
      clrImportant = 0;
    }else{
      width = in.readInt();
      height = in.readInt();
      planes = in.readUnsignedShort();        // always = 1; device independent
      bitsPerPixel = in.readUnsignedShort();  // 1,4,8,16,24,32

      compression = in.readInt();             // BI_RGB, BI_RLE8, BI_RLE4, BI_BITFIELDS
      sizeImage = in.readInt();
      xPelsPerMeter = in.readInt();
      yPelsPerMeter = in.readInt();
      clrUsed = in.readInt();
      clrImportant = in.readInt();
    }
    if(sizeImage == 0){
      sizeImage = (((width*bitsPerPixel+31)>>5)<<2)*height;
    }
    int coloursUsed=getColorsInPalette();
    if(coloursUsed > 0){
//      colortable=new int[coloursUsed];
      byte[] redCols  =new byte[coloursUsed];
      byte[] greenCols=new byte[coloursUsed];
      byte[] blueCols =new byte[coloursUsed];
      for (int i=0; i < coloursUsed; i++) {
        blueCols[i] =(byte)in.read();    	      // blue
        greenCols[i]=(byte)in.read();    	      // green
        redCols[i]  =(byte)in.read();    	      // red
        in.read(); 			                        // reserved
//        colortable[i]=in.readInt();
      }
      icm=new IndexColorModel(bitsPerPixel,coloursUsed,redCols,greenCols,blueCols);
    }
    if(compression==BI_BITFIELDS){
      redMask=in.readInt();
      greenMask=in.readInt();
      blueMask=in.readInt();
    }else if(bitsPerPixel==16){
      redMask  =0x00007C00;
      greenMask=0x000003E0;
      blueMask =0x0000001F;
    }else if(bitsPerPixel==32){
      redMask  =0x00FF0000;
      greenMask=0x0000FF00;
      blueMask =0x000000FF;
    }
    int skip=offset-(14+headerSize+coloursUsed*4+((compression==BI_BITFIELDS)?12:0));
    if(skip > 0){ in.skipBytes(skip); }
    if((compression==BI_RLE4)||(compression==BI_RLE8)){
      throw new IOException(getClass().getName()+".readHeader:\n\tCannot read Run Length Encoded BMP Files.");
    }
  }
///*
  private BufferedImage read(ImageInputStream in)throws IOException{
    readHeader(in);
    switch(bitsPerPixel){
    case  1:  return unpack01(in, icm);
    case  4:  return unpack04(in, icm);
    case  8:  return unpack08(in, icm);
    case 16:  return unpack16(in);
    case 24:  return unpack24(in);
    case 32:  return unpack32(in);
    default:  throw new IOException(getClass().getName()+".read:\n    Cannot read BMP with depth="+(1<<bitsPerPixel));
    }
  }

  void copyImage(byte[] imgBuf,int bytesPerLine,ImageInputStream in)throws IOException{
    int padding=(((width*bitsPerPixel+31)>>5)<<2)-bytesPerLine;
    for(int pos=(height-1)*bytesPerLine;pos>=0;pos-=bytesPerLine){
      in.read(imgBuf,pos,bytesPerLine);
      in.skipBytes(padding);
    }
  }

  int getColorValue(int pixel,int mask){
    int color=pixel&mask;
    while((mask&0x80000000)==0){
      color<<=1; mask<<=1;
    }
    return (color>>24)&0x000000FF;
  }

  BufferedImage unpack32(ImageInputStream in)throws IOException{
    BufferedImage   image  =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    DataBufferInt   buffer =(DataBufferInt)image.getRaster().getDataBuffer();
    int[]           imgBuf =buffer.getData();

    if((redMask==0x00FF0000)&&(greenMask==0x0000FF00)&&(blueMask==0x000000FF)){
      for(int pos=(height-1)*width;pos>=0;pos-=width){
        in.readFully(imgBuf,pos,width);
      }
    }else{
      int   color, r, g, b;
      for(int pos=(height-1)*width;pos>=0;pos-=width){
        for(int x=0;x<width;x++){
          color=in.readInt();
          r=getColorValue(color,redMask);
          g=getColorValue(color,greenMask);
          b=getColorValue(color,blueMask);
          imgBuf[pos+x]=(r<<16)|(g<<8)|b;
        }
      }
    }
    return image;
  }

  BufferedImage unpack24(ImageInputStream in)throws IOException{
    BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    copyImage(buffer.getData(),width*3,in);
    return image;
  }

  BufferedImage unpack16(ImageInputStream in)throws IOException{
    BufferedImage    image=new BufferedImage(width,height,BufferedImage.TYPE_USHORT_555_RGB);
    DataBufferUShort buffer=(DataBufferUShort)image.getRaster().getDataBuffer();
    short[]          imgBuf=buffer.getData();

    int padding=(((width*bitsPerPixel+31)>>5)<<2)-(width<<1);

    if((redMask==0x00007C00)&&(greenMask==0x000003E0)&&(blueMask==0x0000001F)){
      for(int pos=(height-1)*width;pos>=0;pos-=width){
        in.readFully(imgBuf,pos,width);
        in.skipBytes(padding);
      }
    }else{
      int     color, r, g, b;
      for(int pos=(height-1)*width;pos>=0;pos-=width){
        for(int x=0;x<width;x++){
          color=in.readShort();
          r=getColorValue(color,redMask)  &0x001F;
          g=getColorValue(color,greenMask)&0x001F;
          b=getColorValue(color,blueMask) &0x001F;
          imgBuf[pos+x]=(short)((r<<10)|(g<<5)|b);
        }
        in.skipBytes(padding);
      }
    }
    return image;
  }

  BufferedImage unpack08(ImageInputStream in, IndexColorModel icm)throws IOException{
    BufferedImage   image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_INDEXED,icm);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    copyImage(buffer.getData(),width,in);
    return image;
  }

  BufferedImage unpack04(ImageInputStream in, IndexColorModel icm)throws IOException{
    BufferedImage   image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY,icm);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    copyImage(buffer.getData(),(width+1)>>1,in);
    return image;
  }

  BufferedImage unpack01(ImageInputStream in, IndexColorModel icm)throws IOException{
    BufferedImage   image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY,icm);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    copyImage(buffer.getData(),(width+7)>>3,in);
    return image;
  }

//*/
/*  
  private BufferedImage read(ImageInputStream in)throws IOException{
    readHeader(in);
    int scanlineSize = ((width*bitsPerPixel+31)/32)*4;
    byte[] rawData = new byte[scanlineSize*height];
    in.readFully(rawData);
    if(rawData != null){
      switch(bitsPerPixel){
      case  1:  return unpack01(rawData, colortable);
      case  4:  return unpack04(rawData, colortable);
      case  8:  return unpack08(rawData, colortable);
      case 24:  return unpack24(rawData);
      default:  throw new IOException(getClass().getName()+".read:\n    Cannot read BMP with depth="+(1<<bitsPerPixel));
      }
    }
    return null;
  }

  BufferedImage unpack24(byte[] data){
    BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    int           padding      = (((width*3)&3)!=0)?4-((width*3)&3):0;
    int x,y,k=0,len=data.length-3;
    for(y=height-1;y>=0;y--){
      for(x=0;(x<width)&&(k<len);x++){
        image.setRGB(
            x,y,0xFF000000 | 
                (((int)(data[k++])) & 0xFF) |            // blue
                (((int)(data[k++])) & 0xFF) << 8 |       // green
                (((int)(data[k++])) & 0xFF) << 16        // red
        );
      }
      k+=padding;
    }
    return image;
  }

  BufferedImage unpack08(byte[] data, int[] colortable){
    BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    int           padding      = ((width&3)!=0)?4-(width&3):0;
    int x,y,k=0,len=data.length;
    for(y=height-1;y>=0;y--){
      for(x=0;(x<width)&&(k<len);x++){
        image.setRGB(x,y,colortable[data[k++]&0x00FF]);
      }
      k+=padding;
    }
    return image;
  }

  BufferedImage unpack04(byte[] data, int[] colortable) {
    BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    int           bytesPerLine = (width+1)>>1;
    int           padding      = ((bytesPerLine&3)!=0)?4-(bytesPerLine&3):0;
    int x,xx,y,k=0,len=data.length;byte b;
    for(y=height-1;y>=0;y--){
      xx=0;
      for(x=0;(x<bytesPerLine)&&(k<len);x++){
        b=data[k++];
        if(xx<width){                                            // last byte in line may have padding bits
          image.setRGB(xx++,y,colortable[(b>>4)&0x0F]);
          if(xx<width){                                          // last byte in line may have padding bits
            image.setRGB(xx++,y,colortable[b&0x0F]);
          }                                                      
        }                                                        // else ignore padding bytes
      }
      k+=padding;
    }
    return image;
  }

  BufferedImage unpack01(byte[] data, int[] colortable) {
    BufferedImage image        = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    int           bytesPerLine = (width+7)>>3;
    int           padding      = ((bytesPerLine&3)!=0)?4-(bytesPerLine&3):0;
    int x,xx,y,k=0,bit,len=data.length;byte b;

    for(y=height-1;y>=0;y--){
      xx=0;
      for(x=0;(x<bytesPerLine)&&(k<len);x++){
        b=data[k++];
        for(bit=0;(bit<8)&&(xx<width);bit++){
          image.setRGB(xx++,y,colortable[(b>>(7-bit))&0x01]);
        }
      }
      k+=padding;
    }
    return image;
  }
*/
}