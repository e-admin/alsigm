package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

import java.awt.image.*;
import javax.imageio.stream.*;

import uk.co.mmscomputing.io.BitSwapInputStream;
import uk.co.mmscomputing.io.RLEBitInputStream;
import uk.co.mmscomputing.io.ModHuffmanInputStream;
import uk.co.mmscomputing.io.ModREADInputStream;
import uk.co.mmscomputing.io.ModModREADInputStream;

import uk.co.mmscomputing.io.RLEBit1OutputStream;
import uk.co.mmscomputing.io.ModHuffmanOutputStream;
import uk.co.mmscomputing.io.ModREADOutputStream;
import uk.co.mmscomputing.io.ModModREADOutputStream;

/*
    Photometric Interpretation 0,1 : F Profile for Facsimile or TIFF class F images

    required fields bilevel images

    imageWidth,imageLength,Compression,PhotometricInterpretation,
    StripOffsets,RowsPerStrip,StripByteCounts,
    XResolution,YResolution,ResolutionUnit

    1] CCITT Group 3 T.4 1-Dimensional MH Modified Huffman
    2] CCITT Group 3 T.4 2-Dimensional MR Modified READ (Relative Element Address Designate)
    3] CCITT Group 4 T.6 2-Dimensional MMR Modified Modified READ (Relative Element Address Designate)

    RFC 2306
*/

public class TIFFClassFFactory implements TIFFConstants{

  static final String cn="uk.co.mmscomputing.imageio.tiff.TIFFClassFFactory";

  @SuppressWarnings("unchecked")
static public IFD writeImage(ImageOutputStream out,BufferedImage image,int mode,TIFFImageWriteParam param)throws IOException{

//  todo: need to supply resolutions and pagenumbers

    try{
      ColorModel    cm    =image.getColorModel();
      if((image.getType()!=BufferedImage.TYPE_BYTE_BINARY)||(cm.getPixelSize()!=1)){
        throw new IOException(cn+".writeImage:\n\tPlease convert image to black and white picture [TYPE_BYTE_BINARY,1 bps]");
      }
      int width=image.getWidth();
      int height=image.getHeight();
  
      IFD ifd=new IFD();                                                    // entries need to be in tag order !

      ifd.add(new DEFactory.NewSubfileTypeDE(2));                           // 254 single page of multipage file
      ifd.add(new DEFactory.ImageWidthDE(width));                           // 256
      ifd.add(new DEFactory.ImageLengthDE(height));                         // 257

      DEFactory.BitsPerSampleDE bps=new DEFactory.BitsPerSampleDE(1);
      bps.setBitsPerSample(0,1);                                            // one bit per sample
      ifd.add(bps);                                                         // 258

      switch(mode){
      case compT4MH:case compT4MR:
        ifd.add(new DEFactory.CompressionDE(CCITTFAXT4));break;             // 259
      case compT6MMR:       
        ifd.add(new DEFactory.CompressionDE(CCITTFAXT6));break;             // 259
      }
      ifd.add(new DEFactory.PhotometricInterpretationDE(WhiteIsZero));      // 262
      ifd.add(new DEFactory.FillOrderDE(2));                                // 266 2 = Least Significant Bit first

      DEFactory.StripOffsetsDE offsets = new DEFactory.StripOffsetsDE(1);
      ifd.add(offsets);                                                     // 273
      ifd.add(new DEFactory.OrientationDE(1));                              // 274 0,0 = top/left
      ifd.add(new DEFactory.SamplesPerPixelDE(1));                          // 277
      ifd.add(new DEFactory.RowsPerStripDE(height));                        // 278
      DEFactory.StripByteCountsDE counts=new DEFactory.StripByteCountsDE(1);
      ifd.add(counts);                                                      // 279

      if(param==null){
        ifd.add(new DEFactory.XResolutionDE(204.0));                        // 282
        ifd.add(new DEFactory.YResolutionDE(196.0));                        // 283
      }else{
        ifd.add(new DEFactory.XResolutionDE(param.getXResolution()));       // 282
        ifd.add(new DEFactory.YResolutionDE(param.getYResolution()));       // 283
      }
      switch(mode){
      case compT4MH: ifd.add(new DEFactory.T4OptionsDE(4));break;           // 292 MH; byte aligned EOLs
      case compT4MR: ifd.add(new DEFactory.T4OptionsDE(5));break;           // 292 MR; byte aligned EOLs
      case compT6MMR:ifd.add(new DEFactory.T6OptionsDE(0));break;           // 293 MMR
      }
      ifd.add(new DEFactory.ResolutionUnitDE(Inch));                        // 296

//      ifd.add(new DEFactory.PageNumberDE(pagenumber,totalnumber));          // 297


//      Each strip: count run length
//                  encode into MH (modified huffman) codes
//                  save into byte array
//                  write to image file
 
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      ByteArrayOutputStream  baos = new ByteArrayOutputStream();
      ModHuffmanOutputStream mhos;
      switch(mode){
      case compT4MH: mhos = new ModHuffmanOutputStream(baos);break;
      case compT4MR: mhos = new ModREADOutputStream(baos,width);break;
      case compT6MMR:mhos = new ModModREADOutputStream(baos,width);break;
      default: throw new IOException(cn+".writeImage: Internal Error: Unknown compression = "+mode);  // Should never get here.
      }

      copyout(mhos,imgdata,width,height);

      byte[] data=baos.toByteArray();
      counts.setCount(0,data.length);                                    // update ifd strip counter array
      offsets.setOffset(0,out.getStreamPosition());                      // update ifd image data offset array
      out.write(data);                                                   // write to image stream

      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(cn+".writeImage:\n\t"+e.getMessage());
    }
  }

  static private void copyout(ModHuffmanOutputStream mhos,byte[] imgdata,int width,int height)throws IOException{
    RLEBit1OutputStream    rlos = new RLEBit1OutputStream(mhos);

    int len=width>>3;                                                  // eight pixel per byte
    int end=8-(width&0x07);                                            // how many bits of last byte represent image data

    int off=0;
    if(end==8){                                                        // image row ends at byte boundary
      for(int y=0;y<height;y++){
        rlos.setStartCodeWord(0x0001);                                 // white run first; White is Zero: 1s in image => 0s in compressed data
        mhos.writeEOL();                                               // T.6: we don't write EOL code, we just set up buffers here
        rlos.write(imgdata,off,len);
        rlos.flush();
        off+=len;
      }
    }else{
      for(int y=0;y<height;y++){
        rlos.setStartCodeWord(0x0001);                                 // white run first; White is Zero: 1s in image => 0s in compressed data
        mhos.writeEOL();                                               // T.6: we don't write EOL code, we just set up buffers here
        rlos.write(imgdata,off,len);
        rlos.writeBits(imgdata[off+len],7,end);                        // write end of line pixel
        rlos.flush();
        off+=len+1;
      }
    }
    if(mhos instanceof ModModREADOutputStream){                        // T.6: write EOFB after every strip
      ((ModModREADOutputStream)mhos).writeEOFB();
    }
    rlos.close();
  }

  static public BufferedImage readImage(ImageInputStream in,IFD ifd)throws IOException{

    BufferedImage    image=null;

    try{

      int width      =ifd.getWidth();
      int height     =ifd.getHeight();

                       image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      long[] offsets =ifd.getStripOffsets();
      long[] counts  =ifd.getStripByteCounts();

      @SuppressWarnings("unused")
	int h=0,off=0;
      for(int i=0;i<offsets.length;i++){
        /*
          For each strip:
              get chunk of data MH || MR || MMR code
              [swap bits]
              evaluate runlength
              convert into runs
              copy to image buffer
        */
        in.seek(offsets[i]);
        byte[] data=new byte[(int)counts[i]];
        in.read(data);

        ByteArrayInputStream  bais=new ByteArrayInputStream(data);
        ModHuffmanInputStream mhis;
        if(ifd.getFillOrder()==LowColHighBit){              // baseline tiff default
          mhis=getDecoder(new BitSwapInputStream(bais),ifd);
        }else{                                              // fax devices usually code low pixel col low bit positions
          mhis=getDecoder(bais,ifd);
        }
        off=copyin(imgdata,off,mhis,width,ifd.getPhotometricInterpretation()!=WhiteIsZero);
      }
      return image;
    }catch(Exception e){
      System.out.println(cn+".readImage:\n\t"+e.getMessage());
      e.printStackTrace();
      return image;
    }
  }

  static private ModHuffmanInputStream getDecoder(InputStream is,IFD ifd)throws IOException{
    switch(ifd.getCompression()){
    case CCITTFAXT4: 
      int t4o=ifd.getT4Options();

/*
      System.out.println("Tiff Class F T.4");
      System.out.println(((t4o&0x00000001)==0)?"1-dim (MH)":"2-dim (MR)");
      System.out.println(((t4o&0x00000002)==0)?"Compressed":"Uncompressed mode (not allowed in rfc 2306)");
      System.out.println(((t4o&0x00000004)==0)?"non-byte-aligned":"byte-aligned");
*/
      if((t4o&0x00000001)==0){
        return new ModHuffmanInputStream(is);
      }else{
        return new ModREADInputStream(is,ifd.getWidth());
      }
    case CCITTFAXT6:
      @SuppressWarnings("unused")
	int t6o=ifd.getT6Options();                       // Assume: T6Options == 0 => compressed MMR coding

/*
      System.out.println("Tiff Class F T.6");
      System.out.println(((t6o&0x00000001)==0)?"2-Dimensional (MMR)":"Unknown Compression Mode");
      System.out.println(((t6o&0x00000002)==0)?"Compressed":"Uncompressed mode (not allowed in rfc 2306)");
*/
      return new ModModREADInputStream(is,ifd.getWidth());
    }
    throw new IOException(cn+".getCoder: Internal Error: Unknown compression.");  // Should never get here.
  }

  static private int copyin(byte[] imgdata,int off,ModHuffmanInputStream is,int width,boolean invert)throws IOException{
    RLEBitInputStream rlis=new RLEBitInputStream(is);
    rlis.setInvert(invert);

    if((width&0x0007)==0){
      byte[] buf=new byte[width>>3];int len=0;
      while(true){
        rlis.resetToStartCodeWord();                    // start next line with white
        is.readEOL();                                   // set settings for a new line
        try{
          len=rlis.read(buf);                           // read one image line
          if(len==-1){break;}                           // end of page
          System.arraycopy(buf,0,imgdata,off,len);      // copy line to image buffer
        }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
          System.out.println(cn+".copyin:\n\t"+mhce);
        }
        off+=len;
      }
    }else{
      byte[] buf=new byte[(width+7)>>3];int len=0,ecw=8-(width&0x0007),bits;
      while(true){
        rlis.resetToStartCodeWord();                    // start next line with white
        is.readEOL();                                   // set settings for a new line
        try{
          len=rlis.read(buf,0,buf.length-1);            // read one image line
          if(len==-1){break;}                           // end of page
          bits=rlis.readBits(7,ecw);
          buf[len]=(byte)bits;
          System.arraycopy(buf,0,imgdata,off,len+1);    // copy line to image buffer
        }catch(ModHuffmanInputStream.ModHuffmanCodingException mhce){
          System.out.println(cn+".copyin:\n\t"+mhce);
        }
        off+=len+1;
      }
    }
    return off;
  }
}
