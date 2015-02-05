package uk.co.mmscomputing.imageio.sff;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

import uk.co.mmscomputing.io.ModHuffmanOutputStream;
import uk.co.mmscomputing.io.RLEBit1OutputStream;

public class SFFImageWriter extends ImageWriter{

  /*
    Assume bilevel image (black/white,BufferedImage.TYPE_BYTE_BINARY,one bit per pixel)

    Start of sequence:
                Write sff header
    Each image: 
                count run length
                encode into modified huffman codes
                encode into sff file format
                save to byte array
    End of Sequence:
                write sff end
                write to image file
  */
 
  private RLEBit1OutputStream    rlos;
  private ModHuffmanOutputStream mhos;
  private SFFOutputStream        sffos;
  private ByteArrayOutputStream  baos;

  protected SFFImageWriter(ImageWriterSpi originatingProvider){
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
    return new ImageWriteParam(getLocale());
  }

  public boolean canInsertImage(int imageIndex)throws IOException{
    return (imageIndex==0);                          // use sequence for more than one picture
  }

  public void write(IIOMetadata streamMetadata,IIOImage img,ImageWriteParam param)throws IOException{
    prepareWriteSequence(streamMetadata);
    writeToSequence(img,param);                      // just one page !
    endWriteSequence();
  }

  public boolean canWriteSequence(){
    return true;
  }

  public void prepareWriteSequence(IIOMetadata streamMetadata)throws IOException{
    baos = new ByteArrayOutputStream();
    sffos= new SFFOutputStream(baos);
    mhos = new ModHuffmanOutputStream(sffos);
    rlos = new RLEBit1OutputStream(mhos);
  }

  public void writeToSequence(IIOImage img,ImageWriteParam param)throws IOException{
    if(!(img.getRenderedImage() instanceof BufferedImage)){
      throw new IOException(getClass().getName()+"writeToSequence: Can only write BufferedImage objects");
    }
    BufferedImage image =(BufferedImage)img.getRenderedImage();
    ColorModel    cm    =image.getColorModel();
    if((image.getType()!=BufferedImage.TYPE_BYTE_BINARY)||(cm.getPixelSize()!=1)){
      throw new IOException(getClass().getName()+"writeToSequence:\n\tPlease convert image to black and white picture [TYPE_BYTE_BINARY,1 bps]");
    }  
    try{
      int width =image.getWidth();
      sffos.writePageHeader(width);                  // start sff encoding
      writeBinaryImage(image,width);
      sffos.writePageEnd();
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".writeToSequence:\n\tCouldn't write data to SFF File."+e.getMessage());
    }
  }

  private void writeBinaryImage(BufferedImage image,int width)throws IOException{
    WritableRaster   raster=image.getRaster();
    DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
    byte[]           imgdata=(byte[])buffer.getData();

    int height=image.getHeight();
    int len=width/8;                                   // eight pixel per byte
    int end=7-(width%8);                               // how many bits of last byte represent image data

    int off=0;
    if(end==7){                                        // image row ends at byte boundary
      for(int y=0;y<height;y++){
        rlos.setStartCodeWord(0x0001);                 // white run first
        rlos.write(imgdata,off,len);
        rlos.flush();                                  // write padding after every image row
        mhos.writeEOL();                               // signal SFFOutputStream new line
        off+=len;
      }
    }else{
      for(int y=0;y<height;y++){
        rlos.setStartCodeWord(0x0001);                 // white run first
        rlos.write(imgdata,off,len);
        rlos.writeBits(imgdata[off+len],7,end);        // write end of line pixel
        rlos.flush();                                  // write padding after every image row
        mhos.writeEOL();                               // signal SFFOutputStream new line
        off+=len+1;
      }
    }
  }

  public void endWriteSequence()throws IOException{
    sffos.writeDocumentEnd();
    ((ImageOutputStream)getOutput()).write(baos.toByteArray());
  }
}
