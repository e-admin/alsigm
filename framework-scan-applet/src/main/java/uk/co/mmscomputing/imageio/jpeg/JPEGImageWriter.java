package uk.co.mmscomputing.imageio.jpeg;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
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

public class JPEGImageWriter extends ImageWriter{

  protected JPEGImageWriter(ImageWriterSpi originatingProvider){
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
    return (imageIndex==0);
  }

  public void write(IIOMetadata streamMetadata,IIOImage img,ImageWriteParam param)throws IOException{
    ImageOutputStream out=(ImageOutputStream)getOutput();

    if(!(img.getRenderedImage() instanceof BufferedImage)){
      throw new IOException(getClass().getName()+"write:\nCan only write BufferedImage objects");
    }
    BufferedImage image=(BufferedImage)img.getRenderedImage();

    int width=image.getWidth();
    int height=image.getHeight();

    try{  
      ByteArrayOutputStream  baos = new ByteArrayOutputStream();
      JFIFOutputStream       os;

      if(image.getType()==BufferedImage.TYPE_BYTE_GRAY){             // one   component;  grey scale
        os =new JFIFOutputStream(baos,false,height,width);           // SOF:start of frame

        WritableRaster   raster=image.getRaster();
        DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
        byte[]           imgdata=(byte[])buffer.getData();

        os.write(imgdata);
        os.close();                                                  // EOF: end of frame
      }else if(image.getType()==BufferedImage.TYPE_INT_RGB){         // three components; YCbCr
        os =new JFIFOutputStream(baos,true,height,width);            // SOF:start of frame

        WritableRaster   raster=image.getRaster();
        DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
        int[]            imgdata=(int[])buffer.getData();

        os.write(imgdata);
        os.close();                                                  // EOF: end of frame
      }else{                                                         // three components; YCbCr
        os =new JFIFOutputStream(baos,true,height,width);            // SOF:start of frame
        for(int y=0;y<height;y++){
          for(int x=0;x<width;x++){
            os.write(image.getRGB(x,y));
          }
        }
        os.close();                                                  // EOF: end of frame
      }      
      out.write(baos.toByteArray());                                 // write to image stream
     }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".write:\n\tCould not write image due to :\n\t"+e.getMessage());
    }
  }
}
