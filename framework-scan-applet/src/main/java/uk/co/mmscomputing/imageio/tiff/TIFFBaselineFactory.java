package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

import java.awt.image.*;
import javax.imageio.stream.*;

import uk.co.mmscomputing.io.BitSwapInputStream;
import uk.co.mmscomputing.io.LZWInputStream;
import uk.co.mmscomputing.io.PackBitsInputStream;
import uk.co.mmscomputing.io.ModHuffmanInputStream;
import uk.co.mmscomputing.io.RLEBitInputStream;
import uk.co.mmscomputing.io.InvertedInputStream;
import uk.co.mmscomputing.io.IntFilterInputStream;
import uk.co.mmscomputing.io.RGBInputStream;
import uk.co.mmscomputing.imageio.jpeg.*;

// [1] Adobe TIFF6.pdf

/*
  Read:

  Black & White : base line : no compression, modified huffman, packbits
                            + lzw [1] p.57ff
  Gray 4 & 8 bit: base line : no compression, packbits, jpeg(7)(8bit grayscale)
                            + lzw [1] p.57ff
  Palette Color 4 & 8 bit: base line : no compression, packbits
                            + lzw [1] p.57ff

  RGB   : 888 rgb images    : no compression, packbits, lzw, jpeg(7)
  YCbCr : 888 YCbCr images  : no compression, packbits, lzw
*/

public class TIFFBaselineFactory implements TIFFConstants{

  static final private String cn="uk.co.mmscomputing.imageio.tiff.TIFFBaselineFactory";
  static final private String email="\nPlease send this tiff file to mm@mms-computing.co.uk.";

  static final private IndexColorModel bwwhiteiszero;
  static final private IndexColorModel bwblackiszero;
  static final private IndexColorModel graywhiteiszero;
  static final private IndexColorModel grayblackiszero;

  static public BufferedImage readImage(ImageInputStream in,IFD ifd)throws IOException{
    int phmi=ifd.getPhotometricInterpretation();
    int spp =ifd.getSamplesPerPixel();
    int bps =ifd.getBitsPerSample(0);
    int pc  =ifd.getPlanarConfiguration();

    if(pc!=1){
      System.out.println("9\b"+cn+".readImage:\n\tDo only support Planar Configuration 1."+email);
      return null;
    }
    if(ifd.getSampleFormat()!=1){
      System.out.println("9\b"+cn+".readImage:\n\tDo not support sample format other than unsigned integer."+email);
      return null;
    }

// todo: check for tile width & length => don't support tiles

//    System.err.println(" phmi = "+phmi+" bps = "+bps);

    switch(phmi){
    case WhiteIsZero:
      if(spp==1){
        switch(bps){
        case 1: return readBWImage(in,ifd,bwwhiteiszero);
        case 4: return read4bitImage(in,ifd,graywhiteiszero);
        case 8: return readGray8bitImage(in,ifd,true);
        }
      }
      break;
    case BlackIsZero:
      if(spp==1){
        switch(bps){
        case 1: return readBWImage(in,ifd,bwblackiszero);
        case 4: return read4bitImage(in,ifd,grayblackiszero);
        case 8: return readGray8bitImage(in,ifd,false);
        }
      }
      break;
    case RGB:
      if(spp>=3){
        for(int i=0;i<spp;i++){
          if(ifd.getBitsPerSample(i)!=8){
            System.out.println(cn+".readImage:\n\tDo only support 8 bits samples.");
            break;
          }
        }
// todo: ReferenceBlackWhite
        return readRGBImage(in,ifd,spp);
      }else{
        System.out.println(cn+".readImage:\n\tInvalid tiff file, less than three samples per pixel in rgb file.");
      }
      break;
    case PaletteColor:
      if(spp==1){                                       // spp must be one [1] p.37
        switch(bps){
        case 4: return read4bitImage(in,ifd,ifd.getColorModel());
        case 8: return read8bitImage(in,ifd);
        }
      }
      break;
    case TransparencyMask:
      if((spp==1)&&(ifd.getBitsPerSample(0)!=1)){       // spp and bps must be one  [1] p.37
                                                        // phmi must have been set
        System.out.println("9\b"+cn+".readImage:\n\tDo not support transparency mask."+email);
      }
      break;
    case CMYK:
      if(spp==4){
        for(int i=0;i<spp;i++){
          if(ifd.getBitsPerSample(i)!=8){
            System.out.println(cn+".readImage:\n\tUnsupported tiff file. Support only 8 bit samples in CMYK file.");
            break;
          }
        }
        return readCMYKImage(in,ifd);
      }else{
        System.out.println(cn+".readImage:\n\tUnsupported tiff file, Support only four samples per pixel CMYK.");
      }
      break;
    case YCbCr:
      if(spp==3){
        for(int i=0;i<spp;i++){
          if(ifd.getBitsPerSample(i)!=8){
            System.out.println(cn+".readImage:\n\tInvalid tiff file. TIFF does only support 8 bit samples in YCbCr file.");
            break;
          }
        }
        return readYCbCrImage(in,ifd);
      }else{
        System.out.println(cn+".readImage:\n\tInvalid tiff file, a TIFF YCbCr file needs to have three samples per pixel.");
      }
      break;
    case CIELab:
      break;
    }
    System.out.println("9\b"+cn+".readImage:\n\tNot a Baseline TIFF File. Invalid or unsupported parameters."+email);
    return null;
  }

  static private BufferedImage readBWImage(ImageInputStream in,IFD ifd,IndexColorModel cm)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

                       image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY,cm);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();
      @SuppressWarnings("unused")
	int    predictor=ifd.getPredictor();

      int offset=0,mbps=((width+7)>>3)*rps,max=((width+7)>>3)*height;
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(cmp==CCITTGROUP3MODHUFFMAN){                                         // 2, baseline
          if(ifd.getFillOrder()==LowColHighBit){is=new BitSwapInputStream(is);}
          offset=readMH(imgdata,offset,is,width);
        }else{
          if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
          switch(cmp){
          case NOCOMPRESSION:                                      break;         // 1,          
          case LZW:           is=new LZWInputStream(is,8,false);   break;         // 5, non base line 
          case PACKBITS:      is=new PackBitsInputStream(is);      break;         // 32773,
          default: 
            System.out.println("9\b"+cn+".readBWImage:\n\tDo not support compression scheme "+cmp+".");
            return image;
          }
          mbps=((max-offset)<mbps)?max-offset:mbps;
          offset+=is.read(imgdata,offset,mbps);                                   // read/decode max. rps image lines
        }
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".readImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static private int readMH(byte[] imgdata,int off,InputStream is,int width)throws IOException{
    ModHuffmanInputStream mhis=new ModHuffmanInputStream(is);
    RLEBitInputStream     rlis=new RLEBitInputStream(mhis);

    if((width&0x0007)==0){
      byte[] buf=new byte[width>>3];int len=0;
      while(true){
        rlis.resetToStartCodeWord();                    // start next line with white
        try{
          len=rlis.read(buf);                           // read one image line
          if(len==-1){break;}                           // end of page
          System.arraycopy(buf,0,imgdata,off,len);      // copy line to image buffer
          mhis.skipPadding(8);                          // skip bits up until next byte boundary
        }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
          System.out.println(cn+".copyin:\n\t"+mhce);
        }
        off+=len;
      }
    }else{
      byte[] buf=new byte[(width+7)>>3];int len=0,ecw=8-(width&0x0007),bits;
      while(true){
        rlis.resetToStartCodeWord();                    // start next line with white
        try{
          len=rlis.read(buf,0,buf.length-1);            // read one image line
          if(len==-1){break;}                           // end of page
          bits=rlis.readBits(7,ecw);
          buf[len]=(byte)bits;
          System.arraycopy(buf,0,imgdata,off,len+1);    // copy line to image buffer
          mhis.skipPadding(8);                          // skip bits up until next byte boundary
        }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
          System.out.println(cn+".copyin:\n\t"+mhce);
        }
        off+=len+1;
      }
    }
    return off;
  }

  static private BufferedImage read4bitImage(ImageInputStream in,IFD ifd,IndexColorModel cm)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

                       image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY,cm);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();
      int    predictor=ifd.getPredictor();

      if(predictor!=1){  // does any program use this ?
        System.out.println("9\b"+cn+".read4bitImage:\n\tDo not support 'Differencing Predictor' yet.");
        return image;
      }
      int offset=0,mbps=((width+1)>>1)*rps,max=((width+1)>>1)*height;           // two 4-bit pixel per byte
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
        switch(cmp){
        case NOCOMPRESSION:                                      break;         // 1,          
        case LZW:           is=new LZWInputStream(is,8,false);   break;         // 5, non base line 
        case PACKBITS:      is=new PackBitsInputStream(is);      break;         // 32773,
        default: 
          System.out.println("9\b"+cn+".read4bitImage:\n\tDo not support compression scheme "+cmp+".");
          return image;
        }
        mbps=((max-offset)<mbps)?max-offset:mbps;
        offset+=is.read(imgdata,offset,mbps);                                   // read/decode max. rps image lines
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".read4bitImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static private BufferedImage readGray8bitImage(ImageInputStream in,IFD ifd,boolean invert)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

                       image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();
      int    predictor=ifd.getPredictor();

      if(predictor!=1){ // todo
        System.out.println("9\b"+cn+".readGray8bitImage:\n\tDo not support 'Differencing Predictor' yet.");
        return image;
      }

      JPEGInputStream tables;
      try{
        tables = new JPEGInputStream(new ByteArrayInputStream(ifd.getJPEGTables()));
      }catch(IllegalArgumentException iae){
        tables=null;
      }

      int offset=0,mbps=width*rps,max=width*height;                             // pixel per byte
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
        switch(cmp){
        case NOCOMPRESSION:                                      break;         // 1,          
        case LZW:           is=new LZWInputStream(is,8,false);   break;         // 5, non base line 
        case JPEG:
          if(tables!=null){ is=new JPEGInputStream(is,tables.getQTs(),tables.getDCIns(),tables.getACIns());
          }else{            is=new JPEGInputStream(is);}
          break;
        case PACKBITS:      is=new PackBitsInputStream(is);      break;         // 32773,
        default: 
          System.out.println("9\b"+cn+".readGray8bitImage:\n\tDo not support compression scheme "+cmp+".");
          return image;
        }
        if(invert){is=new InvertedInputStream(is);}
        mbps=((max-offset)<mbps)?max-offset:mbps;
        offset+=is.read(imgdata,offset,mbps);                                   // read/decode max. rps image lines
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".readGray8bitImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static private BufferedImage read8bitImage(ImageInputStream in,IFD ifd)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

      IndexColorModel  cm=ifd.getColorModel();
                       image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_INDEXED,cm);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();
      int    predictor=ifd.getPredictor();

      if(predictor!=1){ // todo
        System.out.println("9\b"+cn+".read8bitImage:\n\tDo not support 'Differencing Predictor' yet."+email);
        return image;
      }

      int offset=0,mbps=width*rps,max=width*height;                             // pixel per byte
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
        switch(cmp){
        case NOCOMPRESSION:                                      break;         // 1,          
        case LZW:           is=new LZWInputStream(is,8,false);   break;         // 5, non base line 
        case PACKBITS:      is=new PackBitsInputStream(is);      break;         // 32773,
        default: 
          System.out.println("9\b"+cn+".read8bitImage:\n\tDo not support compression scheme "+cmp+".");
          return image;
        }
        mbps=((max-offset)<mbps)?max-offset:mbps;
        offset+=is.read(imgdata,offset,mbps);                                   // read/decode max. rps image lines
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".read8bitImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static private BufferedImage readRGBImage(ImageInputStream in,IFD ifd,int spp)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

      int     alpha=0;
      int     esl=ifd.getExtraSamplesLength();        // do we have extra samples ?
      if(esl>0){
        if((spp-3)==esl){                             // if last sample is alpha data
          alpha=ifd.getExtraSample(esl-1);            // [1] p.77 : 1 = Associated alpha data (with pre-multiplied color)
        }else{
          System.out.println("9\b"+cn+".readRGBImage:\n\tInvalid TIFF file. 'Samples per Pixel' != ('Extra Samples' + 3).");
        }
      }

      switch(alpha){
      case 1:  image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);
      case 2:  image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
      default: image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
      }

      WritableRaster   raster=image.getRaster();
      DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
      int[]            imgdata=(int[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();
      int    predictor=ifd.getPredictor();

      if(predictor!=1){
        System.out.println("9\b"+cn+".readRGBImage:\n\tDo not support 'Differencing Predictor' yet."+email);
        return image;
      }

      JPEGInputStream tables;
      try{
        tables = new JPEGInputStream(new ByteArrayInputStream(ifd.getJPEGTables()));
      }catch(IllegalArgumentException iae){
        tables=null;
      }

      IntFilterInputStream intis;

      int offset=0,mbps=width*rps,max=width*height;
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
        mbps=((max-offset)<mbps)?max-offset:mbps;
        switch(cmp){
        case NOCOMPRESSION:                                                     // 1
          intis=new RGBInputStream(is,spp,alpha!=0);
          break;
        case LZW:                                                               // 5, non base line 
          is=new LZWInputStream(is,8,false);   
          intis=new RGBInputStream(is,spp,alpha!=0);
          break;
        case JPEG:
          if(tables!=null){ intis=new JPEGInputStream(is,tables.getQTs(),tables.getDCIns(),tables.getACIns());
          }else{            intis=new JPEGInputStream(is);}
          break;
        case PACKBITS:                                                          // 32773
          is=new PackBitsInputStream(is);
          intis=new RGBInputStream(is,spp,alpha!=0);
          break;
        default: 
          System.out.println("9\b"+cn+".readRGBImage:\n\tDo not support compression scheme "+cmp+".");
          return image;
        }
        offset+=intis.read(imgdata,offset,mbps);                                // read/decode max. rps image lines
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".readRGBImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static private BufferedImage readCMYKImage(ImageInputStream in,IFD ifd)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

      image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

      WritableRaster   raster=image.getRaster();
      DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
      int[]            imgdata=(int[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();

      int offset=0,mbps=width*rps,max=width*height;
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
        switch(cmp){
        case NOCOMPRESSION:                                      break;         // 1,          
        case LZW:           is=new LZWInputStream(is,8,false);   break;         // 5, non base line 
        case PACKBITS:      is=new PackBitsInputStream(is);      break;         // 32773,
        default: 
          System.out.println("9\b"+cn+".readCMYKImage:\n\tDo not support compression scheme "+cmp+".");
          return image;
        }
        mbps=((max-offset)<mbps)?max-offset:mbps;
        TIFFCMYKInputStream cmykis=new TIFFCMYKInputStream(is);
        offset+=cmykis.read(imgdata,offset,mbps);                               // read/decode max. rps image lines
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".readCMYKImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static private BufferedImage readYCbCrImage(ImageInputStream in,IFD ifd)throws IOException{
    BufferedImage    image=null;
    try{
      int width  =ifd.getWidth();
      int height =ifd.getHeight();      
      int cmp    =ifd.getCompression();

      image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

      WritableRaster   raster=image.getRaster();
      DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
      int[]            imgdata=(int[])buffer.getData();

      long[] offsets  =ifd.getStripOffsets();
      long[] counts   =ifd.getStripByteCounts();
      int    rps      =ifd.getRowsPerStrip();
      int    predictor=ifd.getPredictor();

      if(predictor!=1){
        System.out.println("9\b"+cn+".readYCbCrImage:\n\tDo not support 'Differencing Predictor' yet."+email);
        return image;
      }

      JPEGInputStream tables;
      try{
        tables = new JPEGInputStream(new ByteArrayInputStream(ifd.getJPEGTables()));
      }catch(IllegalArgumentException iae){
        tables=null;
      }

      double[] coeff       = ifd.getYCbCrCoefficients();
      long[]   sampling    = ifd.getYCbCrSubSampling();
      int      positioning = ifd.getYCbCrPositioning();
      double[] rbw         = ifd.getReferenceBlackWhite();

      IntFilterInputStream intis;

      int offset=0,mbps=width*rps,max=width*height;
      for(int i=0;i<offsets.length;i++){
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);                                                          // read codes
        InputStream is=new ByteArrayInputStream(data);
        if(ifd.getFillOrder()!=LowColHighBit){is=new BitSwapInputStream(is);}
        switch(cmp){
        case NOCOMPRESSION:                                                     // 1,          
          intis=new TIFFSubSamplingInputStream(is,width,(int)sampling[1],(int)sampling[0],positioning);
          break;
        case LZW:                                                               // 5, non base line 
          is=new LZWInputStream(is,8,false);   
          intis=new TIFFSubSamplingInputStream(is,width,(int)sampling[1],(int)sampling[0],positioning);
          break;
        case JPEG:                                                              // 7
          if(tables!=null){ intis=new JPEGInputStream(is,tables.getQTs(),tables.getDCIns(),tables.getACIns());
          }else{            intis=new JPEGInputStream(is);}
          break;
        case PACKBITS:                                                          // 32773,
          is=new PackBitsInputStream(is);      
          intis=new TIFFSubSamplingInputStream(is,width,(int)sampling[1],(int)sampling[0],positioning);
          break;
        default: 
          System.out.println("9\b"+cn+".readYCbCrImage:\n\tDo not support compression scheme "+cmp+".");
          return image;
        }
        mbps=((max-offset)<mbps)?max-offset:mbps;

        TIFFYCbCrInputStream ycbcris=new TIFFYCbCrInputStream(intis);
        ycbcris.setColourCoefficients(coeff[0],coeff[1],coeff[2]);
        ycbcris.setRfBWY (rbw[0],rbw[1]);
        ycbcris.setRfBWCb(rbw[2],rbw[3]);
        ycbcris.setRfBWCr(rbw[4],rbw[5]);

        offset+=ycbcris.read(imgdata,offset,mbps);                              // read/decode max. rps image lines
      }
    }catch(Exception e){
      System.out.println("9\b"+cn+".readYCbCrImage:\n\t"+e.getMessage());
      e.printStackTrace();
    }
    return image;
  }

  static{
    byte[] bwiz={ 0,-1};
    byte[] bbiz={-1, 0};

    bwwhiteiszero=new IndexColorModel(1,2,bwiz,bwiz,bwiz);
    bwblackiszero=new IndexColorModel(1,2,bbiz,bbiz,bbiz);

    byte[] gwiz={-1,-18,-35,-52,-69,-86,-103,-120, 119, 102, 85, 68, 51, 34, 17, 0};
    byte[] gbiz={ 0, 17, 34, 51, 68, 85, 102, 119,-120,-103,-86,-69,-52,-35,-18,-1};

    graywhiteiszero=new IndexColorModel(4,16,gwiz,gwiz,gwiz);
    grayblackiszero=new IndexColorModel(4,16,gbiz,gbiz,gbiz);
  }

  public static void main(String[] args){
    try{
      for(int i=0;i<256;i+=17){
        byte b=(byte)i;
        System.out.print(""+b+",");
      }
      System.out.println(" ");
      for(int i=255;i>=0;i-=17){
        byte b=(byte)i;
        System.out.print(""+b+",");
      }
    }catch(Exception e){
      System.out.println(e);
    }    
  }
}
