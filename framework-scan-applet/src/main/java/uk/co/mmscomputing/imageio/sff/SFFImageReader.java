package uk.co.mmscomputing.imageio.sff;

import java.io.*;
import java.util.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.spi.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

import uk.co.mmscomputing.io.*;

public class SFFImageReader extends ImageReader{

  static private class IFD{
    int    width=0;
    int    height=0;
    byte[] data=null;
  }

  @SuppressWarnings("unchecked")
private Vector  ifds      = null;

  protected SFFImageReader(ImageReaderSpi originatingProvider){
    super(originatingProvider);
  }

  public BufferedImage read(int imageIndex, ImageReadParam param)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return readImage((IFD)ifds.elementAt(imageIndex));
  }

  public int getHeight(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return ((IFD)ifds.elementAt(imageIndex)).height;
  }

  public int getWidth(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);
    return ((IFD)ifds.elementAt(imageIndex)).width;
  }

  @SuppressWarnings("unchecked")
public Iterator getImageTypes(int imageIndex)throws IOException{
    readIFDs();
    checkIndex(imageIndex);

    ImageTypeSpecifier imageType = null;
    java.util.List l = new ArrayList();
    imageType=ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_BYTE_BINARY);

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
    if (imageIndex >= ifds.size()) {
      throw new IndexOutOfBoundsException(getClass().getName()+".checkIndex:\n\tBad index in sff image reader");
    }
  }

  private byte[] readImageData()throws IOException{     // read whole sff stream into memory
    ImageInputStream iis=(ImageInputStream)getInput();
    int len=(int)iis.length();
    byte[] data;
    if(len==-1){                                        // don't know length yet
      ByteArrayOutputStream  baos=new ByteArrayOutputStream();
      data=new byte[1<<13];
      while((len=iis.read(data))!=-1){                  
        baos.write(data,0,len);
      }
      data=baos.toByteArray();
    }else{
      data=new byte[len];
      iis.readFully(data,0,len);
    }
    return data;
  }

  private IFD readIFD(SFFInputStream in)throws IOException{
    try{
      /*
         1. get page data (modified huffman)
            evaluate height
      */
      ByteArrayOutputStream  baos=new ByteArrayOutputStream();
      byte[] data=new byte[2048<<2];int len;
      while(!in.isEndOfPage()&&(len=in.read(data))!=-1){ // read modified Huffman codes of page
        baos.write(data,0,len);
      }
      IFD ifd=new IFD();
      ifd.width        =in.getWidth();
      ifd.height       =in.getHeight();
      ifd.data         =baos.toByteArray();
      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".readIFD:\n\t"+e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
private void readIFDs()throws IOException{
    if(ifds==null){
      ifds=new Vector();
      byte[] data=readImageData();
      SFFInputStream sffis = new SFFInputStream(new ByteArrayInputStream(data));
      while(sffis.hasImage()){
        ifds.add(readIFD(sffis));
      }
    }
  }
/*
  private BufferedImage readImage(IFD ifd)throws IOException{
    try{
      //   2. evaluate runlength
      //      convert into runs
      //      copy to image buffer

      ByteArrayInputStream  bais=new ByteArrayInputStream(ifd.data);
      ModHuffmanInputStream mhis=new ModHuffmanInputStream(bais);
      RLEInputStream        rlis=new RLEInputStream(mhis);

      BufferedImage    image=new BufferedImage(ifd.width,ifd.height,BufferedImage.TYPE_BYTE_GRAY);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      byte[] buf=new byte[ifd.width];int off=0,len=0;
      while(true){
        try{
          len=rlis.read(buf);                           // read one image line
          if(len==-1){break;}                           // end of page
          System.arraycopy(buf,0,imgdata,off,len);      // copy line to image buffer
        }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
          System.err.println(getClass().getName()+".readImage\n\t"+mhce.getMessage());
        }
        mhis.syncWithEOL();                             // skip to eol synchronization bytes
        rlis.resetToStartCodeWord();                    // start next line with white
        off+=ifd.width;
      }
      return image;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".readImage:\n\t"+e.getMessage());
    }
  }
*/
  private BufferedImage readImage(IFD ifd)throws IOException{
    try{
      //   2. evaluate runlength
      //      convert into runs
      //      copy to image buffer

      ByteArrayInputStream  bais=new ByteArrayInputStream(ifd.data);
      ModHuffmanInputStream mhis=new ModHuffmanInputStream(bais);
      RLEBitInputStream     rlis=new RLEBitInputStream(mhis);

      BufferedImage    image=new BufferedImage(ifd.width,ifd.height,BufferedImage.TYPE_BYTE_BINARY);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      if((ifd.width%8)==0){
        byte[] buf=new byte[ifd.width>>3];int off=0,len=0;
        while(true){
          try{
            len=rlis.read(buf);                           // read one image line
            if(len==-1){break;}                           // end of page
            System.arraycopy(buf,0,imgdata,off,len);      // copy line to image buffer
          }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
            System.err.println(getClass().getName()+".readImage\n\t"+mhce.getMessage());
          }
          mhis.syncWithEOL();                             // skip to eol synchronization bytes
          rlis.resetToStartCodeWord();                    // start next line with white
          off+=len;
        }
        return image;
      }else{
        byte[] buf=new byte[(ifd.width+7)>>3];int off=0,len=0,ecw=8-(ifd.width&0x0007),bits;
        while(true){
          try{
            len=rlis.read(buf,0,buf.length-1);            // read one image line
            if(len==-1){break;}                           // end of page
            bits=rlis.readBits(7,ecw);
            buf[len]=(byte)bits;
            System.arraycopy(buf,0,imgdata,off,len+1);    // copy line to image buffer
          }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
            System.err.println(getClass().getName()+".readImage\n\t"+mhce.getMessage());
          }
          mhis.syncWithEOL();                             // skip to eol synchronization bytes
          rlis.resetToStartCodeWord();                    // start next line with white
          off+=len+1;
        }
        return image;
      }
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".readImage:\n\t"+e.getMessage());
    }
  }
}
