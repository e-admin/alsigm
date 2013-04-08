package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;

import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.spi.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

public class JPEGImageReader extends ImageReader {

  JFIFInputStream is=null;
  BufferedImage   image=null;

  protected JPEGImageReader(ImageReaderSpi originatingProvider){
    super(originatingProvider);
  }

  public BufferedImage read(int imageIndex, ImageReadParam param)throws IOException{
    checkIndex(imageIndex);
    if(image==null){image=read((ImageInputStream)getInput());}
    return image;
  }

  public int getHeight(int imageIndex)throws IOException{
    checkIndex(imageIndex);
    if(image==null){image=read((ImageInputStream)getInput());}
    return is.getHeight();
  }

  public int getWidth(int imageIndex)throws IOException{
    checkIndex(imageIndex);
    if(image==null){image=read((ImageInputStream)getInput());}
    return is.getWidth();
  }

  @SuppressWarnings("unchecked")
public Iterator getImageTypes(int imageIndex)throws IOException{
    checkIndex(imageIndex);
    if(image==null){image=read((ImageInputStream)getInput());}
    ImageTypeSpecifier imageType = null;
    java.util.List l = new ArrayList();
    switch(is.getNumComponents()){
    case 1: imageType=ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_BYTE_GRAY); break;
    case 3: imageType=ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);   break;
    }
    l.add(imageType);
    return l.iterator();
  }

  public int getNumImages(boolean allowSearch)throws IOException{
    return 1;
  }

  public IIOMetadata getImageMetadata(int imageIndex)throws IOException{
    checkIndex(imageIndex);
    return null;
  }

  public IIOMetadata getStreamMetadata() throws IOException{
    return null;
  }

  private void checkIndex(int imageIndex) {
    if (imageIndex != 0) {
      throw new IndexOutOfBoundsException(getClass().getName()+".checkIndex: Bad index in jpeg image reader");
    }
  }

  private BufferedImage read(ImageInputStream in)throws IOException{
    if(in==null){throw new IllegalArgumentException(getClass().getName()+".read: ImageInputStream is null.");}
    BufferedImage image=null;
    try{
      byte[] data;
      int len = (int)in.length();
      if(len==-1){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        data=new byte[16*1024];
        while((len=in.read(data))!=-1){
          baos.write(data,0,len);
        }
        data = baos.toByteArray();
      }else if(len==0){
        throw new IllegalArgumentException(getClass().getName()+".read: ImageInputStream has length zero.");
      }else{
        data = new byte[len];
        in.readFully(data);
      }

      JFIFInputStream is=new JFIFInputStream(new ByteArrayInputStream(data));

      int height=is.getHeight();
      int width =is.getWidth();
      int spp   =is.getNumComponents();

      if(spp==1){
        image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster   raster=image.getRaster();
        DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
        byte[]            imgdata=(byte[])buffer.getData();

        is.read(imgdata);
      }else{
        image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        WritableRaster   raster=image.getRaster();
        DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
        int[]             imgdata=(int[])buffer.getData();

        is.read(imgdata);
      }
    }catch(Exception e){
      System.out.println("9\b"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }
}