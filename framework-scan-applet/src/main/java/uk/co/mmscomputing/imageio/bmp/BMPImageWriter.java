package uk.co.mmscomputing.imageio.bmp;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.IndexColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteOrder;

import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

public class BMPImageWriter extends ImageWriter implements BMPConstants{

  private int	size=0;
  private int	offset=0;
 
  private int   headerSize = 40;              /* InfoHeader Offset*/
  private int   width;                        /* Width */
  private int   height;                       /* Height */
  private int   planes = 1;                   /* BitPlanes on Target Device */
  private int   bitCount = 24;                /* Bits per Pixel, default = RGB */
  private int   compression = BI_RGB;         /* Bitmap Compression */
  private int   sizeImage;                    /* Bitmap Image Size */
  private int   xPelsPerMeter = 2953;         /* Horiz Pixels Per Meter, default 75 dpi */
  private int   yPelsPerMeter = 2953;         /* Vert Pixels Per Meter, default 75 dpi */
  private int   clrUsed = 0;                  /* Number of ColorMap Entries */
  private int   clrImportant = 0;             /* Number of important colours */
        
  @SuppressWarnings("unused")
private int[] colortable=null;

  protected BMPImageWriter(ImageWriterSpi originatingProvider){
    super(originatingProvider);
  }

  public IIOMetadata convertImageMetadata(IIOMetadata inData, ImageTypeSpecifier imageType, ImageWriteParam param){
    return null;
  }

  public IIOMetadata convertStreamMetadata(IIOMetadata inData,ImageWriteParam param){
    return null;
  }

  public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier imageType,ImageWriteParam param){
    return null;
  }

  public IIOMetadata getDefaultStreamMetadata(ImageWriteParam param){
    return null;
  }

  public boolean canInsertImage(int imageIndex)throws IOException{
    super.canInsertImage(imageIndex);
    return (imageIndex==0);		//	can deal only with one image
  }

  int getColorsInPalette(){
    if(clrUsed!=0){                  // as in info header
      return clrUsed;
    }else if(bitCount<16){           // 2, 16, 256 colours
      return 1<<bitCount;
    }else{                           // 0 for 16,24,32 bits per pixel
      return 0;
    }
  }

  int getHeaderSize(){
    int size=14+40;                          // fileheader + infoheader
    size+=getColorsInPalette()*4;            // either colour table
    size+=(compression==BI_BITFIELDS)?12:0;  // or colour masks or none of them
    return size;
  }

  private void writeHeader(ImageOutputStream out,BufferedImage image,IIOMetadata md)throws IOException{
    width=image.getWidth();
    height=image.getHeight();

    if(md!=null){
      if(md instanceof BMPMetadata){
        BMPMetadata bmd=(BMPMetadata)md;
        xPelsPerMeter=bmd.getXPixelsPerMeter();
        yPelsPerMeter=bmd.getYPixelsPerMeter();
      }
    }

    offset=getHeaderSize();				      //	file header

    sizeImage=((((width*bitCount)+31)>>5)<<2)*height;
    size=offset+sizeImage;

    out.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    out.writeByte('B');			//	file header
    out.writeByte('M');
    out.writeInt(size);     //  size of 
    out.writeShort(0);			//	reserved
    out.writeShort(0);			//	reserved
    out.writeInt(offset);

    out.writeInt(headerSize);		//	info header
    out.writeInt(width);
    out.writeInt(height);
    out.writeShort(planes);
    out.writeShort(bitCount);
    out.writeInt(compression);
    out.writeInt(sizeImage);
    out.writeInt(xPelsPerMeter);
    out.writeInt(yPelsPerMeter);
    out.writeInt(clrUsed);
    out.writeInt(clrImportant);
  }

  public void write(ImageOutputStream out,BufferedImage image,IIOMetadata md)throws IOException{
    writeHeader(out,image,md);

    int rawData[] = new int[width*height];
    PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, rawData, 0, width);
    try{
      grabber.grabPixels();
    }catch(InterruptedException e){ 
      throw new IOException(getClass().getName()+".write : Couldn't grab pixels from image !");
    }

    ColorModel model = grabber.getColorModel();

    int x=0,y=0,i=0,j=0,k=0;
    int w=(((width*bitCount)+31)>>5)<<2;        		// dword align width
    int h=height-1;

    byte[] bitmap = new byte[w*height];
    for (y=0;y<height;y++) {
      i=(h-y)*width;                   
      j=y*w;                    
      for (x=0;x<width;x++) {
        k=i+x;
        bitmap[j++]=(byte)((model.getBlue(rawData[k]))&0xFF);
        bitmap[j++]=(byte)((model.getGreen(rawData[k]))&0xFF);
        bitmap[j++]=(byte)((model.getRed(rawData[k]))&0xFF);
      }
    }                        
    out.write(bitmap);            
  }

  void writeColorTable(ImageOutputStream out,IndexColorModel icm)throws IOException{
    byte[] reds=new byte[clrUsed],greens=new byte[clrUsed],blues=new byte[clrUsed];
    icm.getReds(reds);icm.getGreens(greens);icm.getBlues(blues);
    for(int i=0;i<reds.length;i++){
      out.write(blues[i]);out.write(greens[i]);out.write(reds[i]);out.write(0);
    }
  }

  void writeImage(ImageOutputStream out,byte[] buffer,int bytesPerLine)throws IOException{
    int padding=((((width*bitCount)+31)>>5)<<2)-bytesPerLine;
    for(int pos=(height-1)*bytesPerLine;pos>=0;pos-=bytesPerLine){
      out.write(buffer,pos,bytesPerLine);
      for(int p=0;p<padding;p++){out.write(0);}
    }
  }

  private void write01bit(ImageOutputStream out,BufferedImage image,IIOMetadata md,IndexColorModel icm)throws IOException{

    System.out.println("Save as 1 bit BMP");

    bitCount=1;			             //	Save as 1 bit BMP
    compression=BI_RGB;          // no colour masks
    clrUsed=icm.getMapSize();    // two colour table
    writeHeader(out,image,md);
    writeColorTable(out,icm);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    writeImage(out,buffer.getData(),(width+7)>>3);
  }

  private void write04bit(ImageOutputStream out,BufferedImage image,IIOMetadata md,IndexColorModel icm)throws IOException{

    System.out.println("Save as 4 bit BMP");

    bitCount=4;			             //	Save as 4 bit BMP
    compression=BI_RGB;          // no colour masks
    clrUsed=icm.getMapSize();    // max 16 colour table
    writeHeader(out,image,md);
    writeColorTable(out,icm);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    writeImage(out,buffer.getData(),(width+1)>>1);
  }

  private void write08bit(ImageOutputStream out,BufferedImage image,IIOMetadata md,IndexColorModel icm)throws IOException{

    System.out.println("Save as 8 bit BMP");

    bitCount=8;			             //	Save as 8 bit BMP
    compression=BI_RGB;          // no colour masks
    clrUsed=icm.getMapSize();    // max 256 colour table
    writeHeader(out,image,md);
    writeColorTable(out,icm);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    writeImage(out,buffer.getData(),width);
  }

  private void write16bit(ImageOutputStream out,BufferedImage image,IIOMetadata md)throws IOException{

    System.out.println("Save as 8 bit BMP");

    bitCount=16;			        //	Save as 16 bit BMP
    compression=BI_BITFIELDS; //  colour masks
    clrUsed=0;                //  no colour table
    writeHeader(out,image,md);

    out.writeInt(0x00007C00); //  default colour masks
    out.writeInt(0x000003E0);
    out.writeInt(0x0000001F);

    WritableRaster   raster=image.getRaster();
    DataBufferUShort buffer=(DataBufferUShort)raster.getDataBuffer();
    short[]          imgBuf=buffer.getData();

    int padding=(((width*bitCount+31)>>5)<<2)-(width<<1);
    for(int pos=(height-1)*width;pos>=0;pos-=width){
      out.writeShorts(imgBuf,pos,width);
      if(padding!=0){out.write(0);out.write(0);}
    }
  }

  private void write24bit(ImageOutputStream out,BufferedImage image,IIOMetadata md)throws IOException{
    bitCount=24;			      //	Save as 24 bit BMP
    compression=BI_RGB;     //  no colour masks
    clrUsed=0;              //  no colour table
    writeHeader(out,image,md);
    WritableRaster  raster=image.getRaster();
    DataBufferByte  buffer=(DataBufferByte)raster.getDataBuffer();
    writeImage(out,buffer.getData(),width*3);
  }

  private void write32bit(ImageOutputStream out,BufferedImage image,IIOMetadata md)throws IOException{
    bitCount=32;			        //	Save as 32 bit BMP
    compression=BI_BITFIELDS; //  colour masks
    clrUsed=0;                //  no colour table
    writeHeader(out,image,md);
    out.writeInt(0x00FF0000); //  default colour masks
    out.writeInt(0x0000FF00);
    out.writeInt(0x000000FF);

    WritableRaster  raster=image.getRaster();
    DataBufferInt   buffer=(DataBufferInt)raster.getDataBuffer();
    int[]           imgBuf=buffer.getData();

    for(int pos=(height-1)*width;pos>=0;pos-=width){
      out.writeInts(imgBuf,pos,width);
    }
  }

  public void write(IIOMetadata streamMetadata,IIOImage img,ImageWriteParam param)throws IOException{
    ImageOutputStream out=(ImageOutputStream)getOutput();

    if(!(img.getRenderedImage() instanceof BufferedImage)){
      throw new IOException(getClass().getName()+"write:\n\tCan only write BufferedImage objects");
    }
    IIOMetadata   md      = img.getMetadata();
    BufferedImage image   = (BufferedImage)img.getRenderedImage();
    ColorModel    cm      = image.getColorModel();
    if(cm instanceof IndexColorModel){
      IndexColorModel icm =(IndexColorModel)cm;
      if((image.getType()==BufferedImage.TYPE_BYTE_BINARY)&&(cm.getPixelSize()==1)){
        write01bit(out,image,md,icm);
      }else if((image.getType()==BufferedImage.TYPE_BYTE_BINARY)&&(cm.getPixelSize()==4)){
        write04bit(out,image,md,icm);
      }else if((image.getType()==BufferedImage.TYPE_BYTE_INDEXED)&&(cm.getPixelSize()<=8)){
        write08bit(out,image,md,icm);
      }else{
        write(out,image,md);
      }
    }else{
      switch(image.getType()){
      case BufferedImage.TYPE_USHORT_555_RGB:  write16bit(out,image,md);break;
      case BufferedImage.TYPE_3BYTE_BGR:       write24bit(out,image,md);break;
      case BufferedImage.TYPE_INT_RGB:         write32bit(out,image,md);break;
      default:                                 write(out,image,md);break;
      }
    }
  }

  public ImageWriteParam getDefaultWriteParam(){
    return new ImageWriteParam(getLocale());
  }
}
