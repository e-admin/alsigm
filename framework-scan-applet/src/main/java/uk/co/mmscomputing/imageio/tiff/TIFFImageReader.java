package uk.co.mmscomputing.imageio.tiff;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class TIFFImageReader extends ImageReader implements TIFFConstants{

  @SuppressWarnings("unchecked")
private Vector ifds=null;

  protected TIFFImageReader(ImageReaderSpi originatingProvider){
    super(originatingProvider);
  }

  public BufferedImage read(int imageIndex, ImageReadParam param)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return readImage(((IFD)ifds.elementAt(imageIndex)));
  }

  public int getHeight(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return ((IFD)ifds.elementAt(imageIndex)).getHeight();
  }

  public int getWidth(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return ((IFD)ifds.elementAt(imageIndex)).getWidth();
  }

  @SuppressWarnings("unchecked")
public Iterator getImageTypes(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);

    ImageTypeSpecifier imageType = null;
    java.util.List l = new ArrayList();
    imageType=ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

    l.add(imageType);
    return l.iterator();
  }

  public int getNumImages(boolean allowSearch)throws IOException{
    readIFDs();
    return ifds.size();
  }

  public IIOMetadata getImageMetadata(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return null;
  }

  public IIOMetadata getStreamMetadata() throws IOException{
    return null;
  }

  private void checkIndex(int imageIndex) {
    if (imageIndex > ifds.size()) {
      throw new IndexOutOfBoundsException("\n"+getClass().getName()+".checkIndex:\n    Bad index in image reader");
    }
  }

  private long readImageFileHeader(ImageInputStream in)throws IOException{


//  System.err.println("getUseCache = "+ImageIO.getUseCache());


    int bo=in.readUnsignedShort();

    if(bo==0x00004D4D){                             // MM
      in.setByteOrder(ByteOrder.BIG_ENDIAN);
//      System.out.println("3\bbig endian");
    }else if(bo==0x00004949){                       // II
      in.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }else{
      throw new IOException("\n"+getClass().getName()+".readImageFileHeader\n\tInvalid tiff document : MM or II missing.");
    }
    int version=in.readUnsignedShort();
    if(version!=42){                            
      throw new IOException("\n"+getClass().getName()+".readImageFileHeader\n\tInvalid tiff document : Unsupported Version: "+version+".");
    }
    return in.readUnsignedInt();
  }

  @SuppressWarnings("unchecked")
private void readIFDs()throws IOException{        // read image file directories
    if(ifds==null){
      ifds=new Vector();
      ImageInputStream in=(ImageInputStream)getInput();
      long pos=readImageFileHeader(in);
      while(pos!=0){    
        IFD ifd=new IFD();
        pos=ifd.read(in,pos);
        ifds.add(ifd);
      }
    }
  }

  private BufferedImage readImage(IFD ifd)throws IOException{
    int cmp=ifd.getCompression();

    if((cmp==CCITTFAXT4)||(cmp==CCITTFAXT6)){         // Profile F Facsimile
      return TIFFClassFFactory.readImage((ImageInputStream)getInput(),ifd);      
    }
    if(cmp==JPEGDeprecated){
      System.out.println("9\b"+getClass().getName()+".readImage:\n\tDo not support old style JPEG compression.");
      return null;
    }
    return TIFFBaselineFactory.readImage((ImageInputStream)getInput(),ifd);
  }
}

/*
[1] Adobe TIFF6.pdf

[2] RFC 2306 http://www.faqs.org/rfcs/rfc2306.html [last accessed: 2005-10-14]
*/