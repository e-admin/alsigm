package uk.co.mmscomputing.imageio.gif;

import java.io.*;
import java.util.*;

import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.spi.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

public class GIFImageWriter extends ImageWriter{

  private GIFFilterOutputStream out;
  private int maxWidth=0,maxHeight=0;

  @SuppressWarnings({ "unchecked", "unused" })
private Vector  images = null;

  int   colourCount=0;
  int[] colourTable=new int[256];

  protected GIFImageWriter(ImageWriterSpi originatingProvider){
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

  public ImageWriteParam getDefaultWriteParam(){
    return new GIFImageWriteParam(getLocale());
  }

  public boolean canInsertImage(int imageIndex)throws IOException{
    super.canInsertImage(imageIndex);
    return (imageIndex==0);                //  use sequence for more than one picture
  }

  public void write(IIOMetadata streamMetadata,IIOImage img,ImageWriteParam param)throws IOException{
    prepareWriteSequence(streamMetadata);
    writeToSequence(img,param);            //  just one page !
    endWriteSequence();
  }

  public boolean canWriteSequence(){
    return true;
  }

  @SuppressWarnings("unchecked")
public void prepareWriteSequence(IIOMetadata streamMetadata)throws IOException{
    images=new Vector();
    colourCount=0;

    out=new GIFFilterOutputStream((ImageOutputStream)getOutput());

    out.write('G');out.write('I');out.write('F');
    out.write('8');out.write('9');out.write('a');

    maxWidth=256;maxHeight=256;

    out.write(maxWidth&0x000000FF);out.write((maxWidth>>8)&0x000000FF);
    out.write(maxHeight&0x000000FF);out.write((maxHeight>>8)&0x000000FF);

    int bitColCount=7;
    int colres     =8;

    int flags=bitColCount&0x00000007;
//    if(sort){ flags|=0x00000008; }
    flags|=((colres-1)&0x00000007)<<4;
//    flags|=0x00000080;           // has global colour map after header

    out.write(flags);
    out.write(0);                  //  colourTable[0] is background colour;
    out.write(0);                  //  no aspect ratio given
  }

  public void writeToSequence(IIOImage img,ImageWriteParam param)throws IOException{
    if(!(img.getRenderedImage() instanceof BufferedImage)){
      throw new IOException(getClass().getName()+".writeToSequence:\n\tCan only write BufferedImage objects");
    }
    BufferedImage image=(BufferedImage)img.getRenderedImage();
    if((image.getType()!=BufferedImage.TYPE_BYTE_INDEXED)||(image.getColorModel().getPixelSize()!=8)){
      throw new IOException(getClass().getName()+".writeToSequence:\n\tPlease convert image first to 8 bit 'Byte Indexed' colour model.");
    }
    int width=image.getWidth();
    int height=image.getHeight();
    if(width>maxWidth){maxWidth=width;}
    if(height>maxHeight){maxHeight=height;}

    int bitColCount =7;
    int left=0, top=0;
    boolean interlaced=false, sort=false, lcm=true;

    int flag=(bitColCount&0x00000007);
    if(sort){ flag|=0x00000020; }
    if(interlaced){ flag|=0x00000040; }
    if(lcm){ flag|=0x00000080; }             // use local colour table

    out.write(',');                          // 0x2C - Image Descriptor Token

    out.write(left  &0x000000FF);out.write((left  >>8)&0x000000FF);
    out.write(top   &0x000000FF);out.write((top   >>8)&0x000000FF);

    out.write(width &0x000000FF);out.write((width >>8)&0x000000FF);
    out.write(height&0x000000FF);out.write((height>>8)&0x000000FF);
    out.write(flag);

    writeColorTable((IndexColorModel)image.getColorModel());

    Raster          raster = image.getRaster();
    DataBufferByte  db     = (DataBufferByte)raster.getDataBuffer();    
    byte[]          data   = db.getData();

    GIFLZWOutputStream lzw=new GIFLZWOutputStream(out,bitColCount+1);
    lzw.write(data);
    lzw.flush();
  }

  public void endWriteSequence()throws IOException{
    out.write(';');                       // GIF-Terminator
    out.flush();

    ImageOutputStream ios=(ImageOutputStream)getOutput();
    if(ios instanceof FileCacheImageOutputStream){
      ((FileCacheImageOutputStream)ios).seek(6);
      ios.write(maxWidth&0x000000FF);ios.write((maxWidth>>8)&0x000000FF);
      ios.write(maxHeight&0x000000FF);ios.write((maxHeight>>8)&0x000000FF);
    }else if(ios instanceof FileImageOutputStream){
      ((FileImageOutputStream)ios).seek(6);
      ios.write(maxWidth&0x000000FF);ios.write((maxWidth>>8)&0x000000FF);
      ios.write(maxHeight&0x000000FF);ios.write((maxHeight>>8)&0x000000FF);
    }

    ios.flush();
    ios.close();
  }

  private void writeColorTable(IndexColorModel cm)throws IOException{
    int len=cm.getMapSize();

    byte[] rColourTable=new byte[len];
    byte[] gColourTable=new byte[len];
    byte[] bColourTable=new byte[len];

    cm.getReds(  rColourTable);
    cm.getGreens(gColourTable);
    cm.getBlues( bColourTable);

    for(int i=0; i<len; i++){
      out.write(rColourTable[i]);
      out.write(gColourTable[i]);
      out.write(bColourTable[i]);
    }
    for(int i=len; i<256; i++){
      out.write(0xFF);
      out.write(0xFF);
      out.write(0xFF);
    }
  }

  static public class GIFFilterOutputStream extends OutputStream{

    private ImageOutputStream out;
    private byte[]            buffer;
    @SuppressWarnings("unused")
	private int               count,max;
    
    public GIFFilterOutputStream(ImageOutputStream out){
      this.out=out;
      buffer=new byte[4096];
      count=0;
    }

    public void write(int b)throws IOException{
      if(count==buffer.length){
        out.write(buffer,0,count);
        count=0;
      }
      buffer[count++]=(byte)b;
    }    

    public void flush()throws IOException{
      if(count>0){
        out.write(buffer,0,count);
        count=0;
      }
    }
  }
}

//  http://www.w3.org/Graphics/GIF/spec-gif89a.txt
