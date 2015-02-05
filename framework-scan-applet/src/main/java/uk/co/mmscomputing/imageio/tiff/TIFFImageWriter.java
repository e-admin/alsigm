package uk.co.mmscomputing.imageio.tiff;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

import uk.co.mmscomputing.imageio.jpeg.JPEGConstants;
import uk.co.mmscomputing.imageio.jpeg.JPEGOutputStream;
import uk.co.mmscomputing.io.BitSwapOutputStream;
import uk.co.mmscomputing.io.ModHuffmanOutputStream;
import uk.co.mmscomputing.io.RLEOutputStream;

public class TIFFImageWriter extends ImageWriter implements TIFFConstants{

  private long ifdptr=0;

  protected TIFFImageWriter(ImageWriterSpi originatingProvider){
    super(originatingProvider);
  }

  public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier imageType,ImageWriteParam param){
//    return new TIFFMetadata();
    return null;
  }

  public IIOMetadata convertImageMetadata(IIOMetadata inData, ImageTypeSpecifier imageType, ImageWriteParam param){
//    if(inData instanceof TIFFMetadata){    // We only understand our own metadata
//      return inData;
//    }
    return null;
  }

  public IIOMetadata getDefaultStreamMetadata(ImageWriteParam param){
    return null;
  }

  public IIOMetadata convertStreamMetadata(IIOMetadata inData,ImageWriteParam param){
    return null;
  }

  public ImageWriteParam getDefaultWriteParam(){
    return new TIFFImageWriteParam(getLocale());
  }

  public boolean canInsertImage(int imageIndex)throws IOException{
    return (imageIndex==0);		                //	use sequence for more than one picture
  }

  public void write(IIOMetadata streamMetadata,IIOImage img,ImageWriteParam param)throws IOException{
    prepareWriteSequence(streamMetadata);
    writeToSequence(img,param);			          //	just one page !
    endWriteSequence();
  }

  public boolean canWriteSequence(){
    return true;
  }

  public void prepareWriteSequence(IIOMetadata streamMetadata)throws IOException{
    ImageOutputStream out=(ImageOutputStream)getOutput();
    out.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    out.writeShort(0x00004949);               // 0: II = intel = little endian
    out.writeShort(42);                       // 2: version, magic value
    ifdptr=out.getStreamPosition();           // save position (4): write here later the offset to first ifd (ifd linked list)
    out.writeInt(0);                          // 4: offset first Image File Directory
                                              // 8: header size
  }

  public void writeToSequence(IIOImage img,ImageWriteParam param)throws IOException{
    ImageOutputStream out=(ImageOutputStream)getOutput();
    if(!(img.getRenderedImage() instanceof BufferedImage)){
      throw new IOException(getClass().getName()+"writeToSequence:\n\tCan only write BufferedImage objects");
    }
    BufferedImage image=(BufferedImage)img.getRenderedImage();
/*
    // Attempt to convert metadata, if present
    IIOMetadata  imd      = img.getMetadata();
    TIFFMetadata metadata = null;
    if(imd!=null){
      ImageTypeSpecifier type=ImageTypeSpecifier.createFromRenderedImage(image);
      metadata=(TIFFMetadata)convertImageMetadata(imd,type,null);
    }
    // Output metadata if present
    if(metadata != null){
      Iterator keywordIter = metadata.keywords.iterator();
      Iterator valueIter   = metadata.values.iterator();
      while(keywordIter.hasNext()){
        String keyword = (String)keywordIter.next();
        String value   = (String)valueIter.next();
        System.out.println("9\bKEYWORD: "+keyword);
        System.out.println("9\bVALUE: "+value);
      }
    }
*/
    IFD ifd;
    int pmi=RGB,comp=compNone,tiffComp=NOCOMPRESSION;

    TIFFImageWriteParam p=null;
    if((param!=null)&&(param instanceof TIFFImageWriteParam)){
      p=(TIFFImageWriteParam)param;
      pmi =p.getPhotometricInterpretation();
      if(p.getCompressionType().equals("none")){           comp=compNone;       tiffComp=NOCOMPRESSION;
      }else if(p.getCompressionType().equals("mh")){       comp=compBaselineMH; tiffComp=CCITTGROUP3MODHUFFMAN;
      }else if(p.getCompressionType().equals("t4mh")){     comp=compT4MH;       tiffComp=CCITTFAXT4;
      }else if(p.getCompressionType().equals("t4mr")){     comp=compT4MR;       tiffComp=CCITTFAXT4;
      }else if(p.getCompressionType().equals("t6mmr")){    comp=compT6MMR;      tiffComp=CCITTFAXT6;
      }else if(p.getCompressionType().equals("packbits")){ comp=compPackBits;   tiffComp=PACKBITS;
      }else if(p.getCompressionType().equals("lzw")){      comp=compLZW;        tiffComp=LZW;
      }else if(p.getCompressionType().equals("jpeg")){     comp=compJPEG;       tiffComp=JPEG;
      }
//      System.out.println("comp = "+p.getCompressionType()+" "+comp);
    }
    switch(pmi){
    case WhiteIsZero: 
      switch(comp){
      case compBaselineMH: ifd=writeBModHufImage(out,image,p);break;
      case compT4MH:
      case compT4MR:
      case compT6MMR:      ifd=TIFFClassFFactory.writeImage(out,image,comp,p);break;
      default:             ifd=writeRGBImage(out,image,NOCOMPRESSION,p);break; // write image data as uncompressed rgb data.
      }
      break;
    case BlackIsZero: 
      switch(comp){
      default:             ifd=writeGrayImage(out,image,tiffComp,p);break; 
      }
      break;
    case RGB:                                                  // write image data as uncompressed rgb data.
      switch(comp){
      default:             ifd=writeRGBImage(out,image,tiffComp,p);break; 
      }
      break;
    case CMYK:
      switch(comp){
      default:             ifd=writeCMYKImage(out,image,p);break;
      }
      break;
    case YCbCr:
      switch(comp){
      default:             ifd=writeYCbCrImage(out,image,tiffComp,p);break;
      }
      break;
    default:               ifd=writeRGBImage(out,image,NOCOMPRESSION,p);break; // write image data as uncompressed rgb data.
    }
    ifdptr=ifd.write(out,ifdptr);                              // write ifd contents, entries and set ifd linked list pointer
  }

  public void endWriteSequence()throws IOException{
    @SuppressWarnings("unused")
	ImageOutputStream out=(ImageOutputStream)getOutput();
  }

/*
    Photometric Interpretation 0,1 : baseline TIFF bilevel images or TIFF class B images

    required fields bilevel images

    imageWidth,imageLength,Compression,PhotometricInterpretation,
    StripOffsets,RowsPerStrip,StripByteCounts,
    XResolution,YResolution,ResolutionUnit

    No compression, 1 Dimensional Modified Huffman, PackBits
*/

  @SuppressWarnings("unchecked")
private IFD writeBModHufImage(ImageOutputStream out,BufferedImage image,TIFFImageWriteParam param)throws IOException{
    try{
      int width=image.getWidth();
      int height=image.getHeight();
  
      IFD ifd=new IFD();                                                 // entries need to be in tag order !

      ifd.add(new DEFactory.NewSubfileTypeDE(2));                        // 254 single page of multipage file
      ifd.add(new DEFactory.ImageWidthDE(width));                        // 256
      ifd.add(new DEFactory.ImageLengthDE(height));                      // 257
      ifd.add(new DEFactory.CompressionDE(CCITTGROUP3MODHUFFMAN));       // 259
      ifd.add(new DEFactory.PhotometricInterpretationDE(WhiteIsZero));   // 262

      int maxrps,maxstripes;                                             // max RowsPerStrip
      if((1<<13)<=width){
        maxrps=1;maxstripes=height;                                      // one row per stripe
      }else{
        maxrps=(1<<13)/width;
        maxstripes=(height+maxrps-1)/maxrps;
      }

      DEFactory.StripOffsetsDE offsets = new DEFactory.StripOffsetsDE(maxstripes);
      ifd.add(offsets);                                                  // 273
      ifd.add(new DEFactory.RowsPerStripDE(maxrps));                     // 278
      DEFactory.StripByteCountsDE counts=new DEFactory.StripByteCountsDE(maxstripes);
      ifd.add(counts);                                                   // 279

      if(param==null){
        ifd.add(new DEFactory.XResolutionDE(72.0));                      // 282
        ifd.add(new DEFactory.YResolutionDE(72.0));                      // 283
      }else{
        ifd.add(new DEFactory.XResolutionDE(param.getXResolution()));    // 282
        ifd.add(new DEFactory.YResolutionDE(param.getYResolution()));    // 283
      }
      ifd.add(new DEFactory.ResolutionUnitDE(Inch));                     // 296

      int index=0;
      for(int y=0;y<height;y+=maxrps){
        /*
        Assume bilevel image (black/white[=-1])

        Each strip: count run length
                    encode into modified hufman codes
                    swap bits
                    save in byte array
                    write to image file
        */
 
        ByteArrayOutputStream  baos=new ByteArrayOutputStream();
        BitSwapOutputStream    bsos=new BitSwapOutputStream(baos);
        ModHuffmanOutputStream mhos=new ModHuffmanOutputStream(bsos);

        RLEOutputStream        rlos=new RLEOutputStream(mhos,3);         // rgb = 3 bytes per sample code word (not needed here)

        for(int i=0;i<maxrps;i++){
          if((y+i)==height){break;}                                      // last strip might have less rows
          rlos.setStartCodeWord(-1);                                     // white run first
          for(int x=0;x<width;x++){
            rlos.write(image.getRGB(x,y+i));                             
          }
          rlos.flush();                                                  // write padding after ever image row
        }
        rlos.close();

        byte[] data=baos.toByteArray();
        counts.setCount(index,data.length);                              // update ifd strip counter array
        offsets.setOffset(index,out.getStreamPosition());                // update ifd image data offset array
        out.write(data);                                                 // write to image stream

        index++;
      }
      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".writeBModHufImage:\n\t"+e.getMessage());
    }
  }

/*
    Photometric Interpretation 1 : BlackIsZero


    required fields

    imageWidth,imageLength,Compression,PhotometricInterpretation,
    StripOffsets,RowsPerStrip,StripByteCounts,
    XResolution,YResolution,ResolutionUnit,

    BitsPerSample

    SamplesPerPixel
*/

  @SuppressWarnings("unchecked")
private IFD writeGrayImage(ImageOutputStream out,BufferedImage image,int comp,TIFFImageWriteParam param)throws IOException{
    image = convert(image,BufferedImage.TYPE_BYTE_GRAY);
    try{
      int width=image.getWidth();
      int height=image.getHeight();
  
      IFD ifd=new IFD();                                                 // entries need to be in tag order !

      ifd.add(new DEFactory.NewSubfileTypeDE(2));                        // 254 single page of multipage file
      ifd.add(new DEFactory.ImageWidthDE(width));                        // 256
      ifd.add(new DEFactory.ImageLengthDE(height));                      // 257

      DEFactory.BitsPerSampleDE bpss = new DEFactory.BitsPerSampleDE(1);
      bpss.setBitsPerSample(0,8);                                        // gray scale
      ifd.add(bpss);                                                     // 258

      ifd.add(new DEFactory.CompressionDE(comp));                        // 259
      ifd.add(new DEFactory.PhotometricInterpretationDE(BlackIsZero));   // 262

      int maxrps,maxstripes;                                             // max RowsPerStrip
      if((1<<13)<=width){
        maxrps=1;maxstripes=height;                                      // one row per strip
      }else{
        maxrps=(1<<13)/width;
        maxstripes=(height+maxrps-1)/maxrps;
      }
      if(comp==JPEG){
        maxrps=((maxrps+8-1)/8)*8;
        maxstripes=(height+maxrps-1)/maxrps;
      }
      DEFactory.StripOffsetsDE offsets = new DEFactory.StripOffsetsDE(maxstripes);
      ifd.add(offsets);                                                  // 273
      ifd.add(new DEFactory.SamplesPerPixelDE(1));                       // 277
      ifd.add(new DEFactory.RowsPerStripDE(maxrps));                     // 278
      DEFactory.StripByteCountsDE counts=new DEFactory.StripByteCountsDE(maxstripes);
      ifd.add(counts);                                                   // 279
      if(param==null){
        ifd.add(new DEFactory.XResolutionDE(72.0));                      // 282
        ifd.add(new DEFactory.YResolutionDE(72.0));                      // 283
      }else{
        ifd.add(new DEFactory.XResolutionDE(param.getXResolution()));    // 282
        ifd.add(new DEFactory.YResolutionDE(param.getYResolution()));    // 283
      }
      ifd.add(new DEFactory.ResolutionUnitDE(Inch));                     // 296

      ByteArrayOutputStream  baos   =new ByteArrayOutputStream();
      OutputStream           os     =baos;
      JPEGOutputStream       jpegos =null;

      if(comp==JPEG){                                                    // add JPEGTables tag
        jpegos=new JPEGOutputStream(baos);

        int quality=(param==null)?50:(int)(param.getCompressionQuality()*100);

        jpegos.setZZQuantizationTable(0,JPEGConstants.LQT,quality);
        jpegos.setRawDCHuffmanTable(0,JPEGConstants.HLDCTable);
        jpegos.setRawACHuffmanTable(0,JPEGConstants.HLACTable);

        jpegos.defineQuantizationTables();
        jpegos.defineHuffmanTables();
        jpegos.close();

        DEFactory.JPEGTablesDE jpegtables=new DEFactory.JPEGTablesDE(baos.toByteArray());
        ifd.add(jpegtables);                                             // 347

        baos.reset();

        os=jpegos;
      }

      WritableRaster   raster=image.getRaster();
      DataBufferByte   buffer=(DataBufferByte)raster.getDataBuffer();
      byte[]           imgdata=(byte[])buffer.getData();

      int index=0;
      for(int y=0;y<height;y+=maxrps){
        /*
        Assume rgb image.

        Each strip: evaluate gray scale colour
                    save in byte array
                    write to image file
        */
 
        if((height-y)<maxrps){maxrps=height-y;}

        if(jpegos!=null){                                                // jpeg: SOI,SOF,SOS marker
          jpegos.startOfImage();
          int[] hv={0x11};                                               // (Hi<<4)|Vi
          int[] q={0};                                                   // quantization table 0
          jpegos.startOfFrame(maxrps,width,hv,q);
          int[] sel={0};                                                 // DC,AC code table 0
          jpegos.startOfScan(sel);
        }

        os.write(imgdata,y*width,maxrps*width);
        os.close();                                                      // jpeg EOF: end of frame

        byte[] data=baos.toByteArray();
        counts.setCount(index,data.length);                              // update ifd strip counter array
        offsets.setOffset(index,out.getStreamPosition());                // update ifd image data offset array
        out.write(data);                                                 // write to image stream
        baos.reset();
        index++;
      }
      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".writeRGBImage:\n\t"+e.getMessage());
    }
  }

/*
    Photometric Interpretation 2 : baseline TIFF RGB images or TIFF class R images


    required fields

    imageWidth,imageLength,Compression,PhotometricInterpretation,
    StripOffsets,RowsPerStrip,StripByteCounts,
    XResolution,YResolution,ResolutionUnit,

    BitsPerSample

    SamplesPerPixel
*/

  @SuppressWarnings("unchecked")
private IFD writeRGBImage(ImageOutputStream out,BufferedImage image,int comp,TIFFImageWriteParam param)throws IOException{
    image = convert(image,BufferedImage.TYPE_INT_RGB);
    try{
      int width=image.getWidth();
      int height=image.getHeight();
  
      IFD ifd=new IFD();                                                 // entries need to be in tag order !

      ifd.add(new DEFactory.NewSubfileTypeDE(2));                        // 254 single page of multipage file
      ifd.add(new DEFactory.ImageWidthDE(width));                        // 256
      ifd.add(new DEFactory.ImageLengthDE(height));                      // 257

      DEFactory.BitsPerSampleDE bpss = new DEFactory.BitsPerSampleDE(3);
      bpss.setBitsPerSample(0,8);                                        // red
      bpss.setBitsPerSample(1,8);                                        // green
      bpss.setBitsPerSample(2,8);                                        // blue
      ifd.add(bpss);                                                     // 258

      ifd.add(new DEFactory.CompressionDE(comp));                        // 259
      ifd.add(new DEFactory.PhotometricInterpretationDE(RGB));           // 262

      int maxrps,maxstripes;                                             // max RowsPerStrip
      if((1<<13)<=width){
        maxrps=1;maxstripes=height;                                      // one row per strip
      }else{
        maxrps=(1<<13)/width;
        maxstripes=(height+maxrps-1)/maxrps;
      }
      if(comp==JPEG){
        maxrps=((maxrps+8-1)/8)*8;
        maxstripes=(height+maxrps-1)/maxrps;
      }
      DEFactory.StripOffsetsDE offsets = new DEFactory.StripOffsetsDE(maxstripes);
      ifd.add(offsets);                                                  // 273
      ifd.add(new DEFactory.SamplesPerPixelDE(3));                       // 277
      ifd.add(new DEFactory.RowsPerStripDE(maxrps));                     // 278
      DEFactory.StripByteCountsDE counts=new DEFactory.StripByteCountsDE(maxstripes);
      ifd.add(counts);                                                   // 279
      if(param==null){
        ifd.add(new DEFactory.XResolutionDE(72.0));                      // 282
        ifd.add(new DEFactory.YResolutionDE(72.0));                      // 283
      }else{
        ifd.add(new DEFactory.XResolutionDE(param.getXResolution()));    // 282
        ifd.add(new DEFactory.YResolutionDE(param.getYResolution()));    // 283
      }
      ifd.add(new DEFactory.ResolutionUnitDE(Inch));                     // 296

      ByteArrayOutputStream  baos   =new ByteArrayOutputStream();
      OutputStream           os     =baos;
      JPEGOutputStream       jpegos =null;

      if(comp==JPEG){                                                    // add JPEGTables tag
        jpegos=new JPEGOutputStream(baos);

        int quality=(param==null)?50:(int)(param.getCompressionQuality()*100);

        jpegos.setZZQuantizationTable(0,JPEGConstants.LQT,quality);
        jpegos.setRawDCHuffmanTable(0,JPEGConstants.HLDCTable);
        jpegos.setRawACHuffmanTable(0,JPEGConstants.HLACTable);

        jpegos.defineQuantizationTables();
        jpegos.defineHuffmanTables();
        jpegos.close();

        DEFactory.JPEGTablesDE jpegtables=new DEFactory.JPEGTablesDE(baos.toByteArray());
        ifd.add(jpegtables);                                             // 347

        baos.reset();

        os=jpegos;
      }

      WritableRaster   raster=image.getRaster();
      DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
      int[]            imgdata=(int[])buffer.getData();

      int index=0;
      for(int y=0;y<height;y+=maxrps){
        /*
        Assume rgb image.

        Each strip: evaluate r g b colour
                    save in byte array
                    write to image file
        */
 
        if((height-y)<maxrps){maxrps=height-y;}

        if(jpegos!=null){                                                // jpeg: SOI,SOF,SOS marker
          jpegos.startOfImage();
          int[] hv={0x11,0x11,0x11};                                     // (Hi<<4)|Vi
          int[] q={0,0,0};                                               // quantization table 0
          jpegos.startOfFrame(maxrps,width,hv,q);
          int[] sel={0,0,0};                                             // DC,AC code table 0
          jpegos.startOfScan(sel);
        }

        for(int i=0;i<maxrps;i++){                                       // write RGB data
          for(int x=0;x<width;x++){
            int c = imgdata[x+(y+i)*width];
            os.write((c>>16)&0x000000FF);
            os.write((c>> 8)&0x000000FF);
            os.write( c     &0x000000FF);
          }
        }
        os.close();                                                      // jpeg: EOI marker

        byte[] data=baos.toByteArray();
        counts.setCount(index,data.length);                              // update ifd strip counter array
        offsets.setOffset(index,out.getStreamPosition());                // update ifd image data offset array
        out.write(data);                                                 // write to image stream
        baos.reset();
        index++;
      }
      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".writeRGBImage:\n\t"+e.getMessage());
    }
  }

/*
    Photometric Interpretation 5 : TIFF CMYK images


    required fields

    imageWidth,imageLength,Compression,PhotometricInterpretation,
    StripOffsets,RowsPerStrip,StripByteCounts,
    XResolution,YResolution,ResolutionUnit,

    BitsPerSample

    SamplesPerPixel
*/

  @SuppressWarnings("unchecked")
private IFD writeCMYKImage(ImageOutputStream out,BufferedImage image,TIFFImageWriteParam param)throws IOException{
    try{
      int width=image.getWidth();
      int height=image.getHeight();
  
      IFD ifd=new IFD();                                                 // entries need to be in tag order !

      ifd.add(new DEFactory.NewSubfileTypeDE(2));                        // 254 single page of multipage file
      ifd.add(new DEFactory.ImageWidthDE(width));                        // 256
      ifd.add(new DEFactory.ImageLengthDE(height));                      // 257

      DEFactory.BitsPerSampleDE bpss = new DEFactory.BitsPerSampleDE(4);
      bpss.setBitsPerSample(0,8);                                        // cyan
      bpss.setBitsPerSample(1,8);                                        // magneta
      bpss.setBitsPerSample(2,8);                                        // yellow
      bpss.setBitsPerSample(3,8);                                        // key (black)
      ifd.add(bpss);                                                     // 258

      ifd.add(new DEFactory.CompressionDE(NOCOMPRESSION));               // 259
      ifd.add(new DEFactory.PhotometricInterpretationDE(CMYK));          // 262

      int maxrps,maxstripes;                                             // max RowsPerStrip
      if((1<<13)<=width){
        maxrps=1;maxstripes=height;                                      // one row per strip
      }else{
        maxrps=(1<<13)/width;
        maxstripes=(height+maxrps-1)/maxrps;
      }

      DEFactory.StripOffsetsDE offsets = new DEFactory.StripOffsetsDE(maxstripes);
      ifd.add(offsets);                                                  // 273
      ifd.add(new DEFactory.SamplesPerPixelDE(4));                       // 277
      ifd.add(new DEFactory.RowsPerStripDE(maxrps));                     // 278
      DEFactory.StripByteCountsDE counts=new DEFactory.StripByteCountsDE(maxstripes);
      ifd.add(counts);                                                   // 279

      if(param==null){
        ifd.add(new DEFactory.XResolutionDE(72.0));                      // 282
        ifd.add(new DEFactory.YResolutionDE(72.0));                      // 283
      }else{
        ifd.add(new DEFactory.XResolutionDE(param.getXResolution()));    // 282
        ifd.add(new DEFactory.YResolutionDE(param.getYResolution()));    // 283
      }
      ifd.add(new DEFactory.ResolutionUnitDE(Inch));                     // 296

      int index=0;
      for(int y=0;y<height;y+=maxrps){
        /*
        Assume rgb image.

        Each strip: evaluate c m y k colour
                    save in byte array
                    write to image file
        */
 
        ByteArrayOutputStream  baos=new ByteArrayOutputStream();

        for(int i=0;i<maxrps;i++){
          if((y+i)==height){break;}                                      // last strip might have less rows
          for(int x=0;x<width;x++){
            int c=image.getRGB(x,y+i);

            int R=(c>>16)&0x00FF;
            int G=(c>> 8)&0x00FF;
            int B=(c    )&0x00FF;

            if((R==255)&&(G==255)&&(B==255)){
              baos.write(0);
              baos.write(0);
              baos.write(0);
              baos.write(0);
            }else{
              double C=1.0-R/255.0;
              double M=1.0-G/255.0;
              double Y=1.0-B/255.0;
            
              double K=C;if(M<K){K=M;}if(Y<K){K=Y;}

              C=((C-K)/(1.0-K))*255.0;
              M=((M-K)/(1.0-K))*255.0;
              Y=((Y-K)/(1.0-K))*255.0;
              K*=255.0;

              baos.write((int)C);
              baos.write((int)M);
              baos.write((int)Y);
              baos.write((int)K);
            }
          }
        }
        baos.close();

        byte[] data=baos.toByteArray();
        counts.setCount(index,data.length);                              // update ifd strip counter array
        offsets.setOffset(index,out.getStreamPosition());                // update ifd image data offset array
        out.write(data);                                                 // write to image stream

        index++;
      }
      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".writeCMYKImage:\n\t"+e.getMessage());
    }
  }

/*
    Photometric Interpretation 6 : TIFF YCbCr images


    required fields

    imageWidth,imageLength,Compression,PhotometricInterpretation,
    StripOffsets,RowsPerStrip,StripByteCounts,
    XResolution,YResolution,ResolutionUnit,

    BitsPerSample

    SamplesPerPixel
*/

  @SuppressWarnings("unchecked")
private IFD writeYCbCrImage(ImageOutputStream out,BufferedImage image,int comp,TIFFImageWriteParam param)throws IOException{
    image = convert(image,BufferedImage.TYPE_INT_RGB);
    try{
      int width=image.getWidth();
      int height=image.getHeight();
  
      IFD ifd=new IFD();                                                 // entries need to be in tag order !

      int ss=(param==null)?0x22:param.getSubSampling();

      int ssh=(ss>>4)&0x0F;
      int ssv= ss    &0x0F;

      if(ssh<ssv){                                                       // YCbCrSubsampleVert shall always be less than or equal to YCbCrSubsampleHoriz.
        throw new IOException("Internal error: YCbCrSubsampleVert is not less than YCbCrSubsampleHoriz.");
      }

//      int ww=((width +ssh-1)/ssh)*ssh;                                   // [1] p.92
//      int hh=((height+ssv-1)/ssv)*ssv;
      int ww=width;int hh=height;

      ifd.add(new DEFactory.NewSubfileTypeDE(2));                        // 254 single page of multipage file
      ifd.add(new DEFactory.ImageWidthDE (ww));                          // 256
      ifd.add(new DEFactory.ImageLengthDE(hh));                          // 257

      DEFactory.BitsPerSampleDE bpss = new DEFactory.BitsPerSampleDE(3);
      bpss.setBitsPerSample(0,8);                                        // Y
      bpss.setBitsPerSample(1,8);                                        // Cb
      bpss.setBitsPerSample(2,8);                                        // Cr
      ifd.add(bpss);                                                     // 258

      ifd.add(new DEFactory.CompressionDE(comp));                        // 259
      ifd.add(new DEFactory.PhotometricInterpretationDE(YCbCr));         // 262

      int maxrps,maxstripes;                                             // max RowsPerStrip
      if((1<<13)<=width){
        maxrps=1;maxstripes=height;                                      // one row per strip
      }else{
        maxrps=(1<<13)/width;
        maxstripes=(height+maxrps-1)/maxrps;
      }
      if(comp==JPEG){
        maxrps=((maxrps+8*ssv-1)/(8*ssv))*(8*ssv);
        maxstripes=(height+maxrps-1)/maxrps;
      }

      DEFactory.StripOffsetsDE offsets = new DEFactory.StripOffsetsDE(maxstripes);
      ifd.add(offsets);                                                  // 273
      ifd.add(new DEFactory.SamplesPerPixelDE(3));                       // 277
      ifd.add(new DEFactory.RowsPerStripDE(maxrps));                     // 278
      DEFactory.StripByteCountsDE counts=new DEFactory.StripByteCountsDE(maxstripes);
      ifd.add(counts);                                                   // 279

      if(param==null){
        ifd.add(new DEFactory.XResolutionDE(72.0));                      // 282
        ifd.add(new DEFactory.YResolutionDE(72.0));                      // 283
      }else{
        ifd.add(new DEFactory.XResolutionDE(param.getXResolution()));    // 282
        ifd.add(new DEFactory.YResolutionDE(param.getYResolution()));    // 283
      }
      ifd.add(new DEFactory.ResolutionUnitDE(Inch));                     // 296


      ByteArrayOutputStream  baos   =new ByteArrayOutputStream();
      OutputStream           os     =baos;
      JPEGOutputStream       jpegos =null;

      if(comp==JPEG){
        jpegos=new JPEGOutputStream(baos);

        int quality=(param==null)?50:(int)(param.getCompressionQuality()*100);

        jpegos.setZZQuantizationTable(0,JPEGConstants.LQT,quality);
        jpegos.setZZQuantizationTable(1,JPEGConstants.CQT,quality);

        jpegos.setRawDCHuffmanTable(0,JPEGConstants.HLDCTable);
        jpegos.setRawACHuffmanTable(0,JPEGConstants.HLACTable);
        jpegos.setRawDCHuffmanTable(1,JPEGConstants.HCDCTable);
        jpegos.setRawACHuffmanTable(1,JPEGConstants.HCACTable);

        jpegos.defineQuantizationTables();
        jpegos.defineHuffmanTables();
        jpegos.close();

        DEFactory.JPEGTablesDE jpegtables=new DEFactory.JPEGTablesDE(baos.toByteArray());
        ifd.add(jpegtables);                                             // 347

        baos.reset();

        os=jpegos;

      }

//      CCIR Recommendation 601-1  LumaRed=299/1000 LumaGreen=587/1000 LumeBlue=114/1000
//      Y  = ( LumaRed * R + LumaGreen * G + LumaBlue * B )
//      Cb = ( B - Y ) / ( 2 - 2 * LumaBlue )
//      Cr = ( R - Y ) / ( 2 - 2 * LumaRed )

      double LumaRed   = 299.0/1000.0;
      double LumaGreen = 587.0/1000.0;
      double LumaBlue  = 114.0/1000.0;

      DEFactory.YCbCrCoefficientsDE YCbCrCoeff = new DEFactory.YCbCrCoefficientsDE();
      YCbCrCoeff.setLumaRed  (LumaRed);                                  // Y
      YCbCrCoeff.setLumaGreen(LumaGreen);                                // Cb
      YCbCrCoeff.setLumaBlue (LumaBlue);                                 // Cr
      ifd.add(YCbCrCoeff);                                               // 529

      DEFactory.YCbCrSubSamplingDE YCbCrSubSampling = new DEFactory.YCbCrSubSamplingDE();
      YCbCrSubSampling.setHoriz(ssh);
      YCbCrSubSampling.setVert(ssv);
      ifd.add(YCbCrSubSampling);                                         // 530

      double RfBY  =0;            double RfWY  =255;
      double RfBCb =128;          double RfWCb =255;    
      double RfBCr =128;          double RfWCr =255;

      DEFactory.ReferenceBlackWhiteDE ReferenceBlackWhite = new DEFactory.ReferenceBlackWhiteDE();
      ReferenceBlackWhite.setY(RfBY,RfWY);
      ReferenceBlackWhite.setCb(RfBCb,RfWCb);
      ReferenceBlackWhite.setCr(RfBCr,RfWCr);
      ifd.add(ReferenceBlackWhite);                                      // 532

      TIFFYCbCrOutputStream    ycbcros;
      if(jpegos==null){
        ycbcros=new TIFFYCbCrOutputStream(os,width,ssv,ssh);
        os=new TIFFSubSamplingOutputStream(ycbcros,width,ssv,ssh);
      }else{
        ycbcros=new TIFFYCbCrOutputStream(os,width,1,1);                 // jpeg does own subsampling
        os=ycbcros;
      }

      ycbcros.setPositioning(1);
      ycbcros.setColourCoefficients(LumaRed,LumaGreen,LumaBlue);
      ycbcros.setRfBWY (RfBY,RfWY);
      ycbcros.setRfBWCb(RfBCb,RfWCb);
      ycbcros.setRfBWCr(RfBCr,RfWCr);

      WritableRaster   raster=image.getRaster();
      DataBufferInt    buffer=(DataBufferInt)raster.getDataBuffer();
      int[]           imgdata=(int[])buffer.getData();

      int c=0,index=0;
      for(int y=0;y<height;y+=maxrps){

        if((height-y)<maxrps){maxrps=height-y;}

        if(jpegos!=null){
          jpegos.startOfImage();
          int[] hv={(ssh<<4)|ssv,0x11,0x11};                             // (Hi<<4)|Vi
          int[] q={0,1,1};                                               // quantization table Y=0, Cb=Cr=1
//          jpegos.startOfFrame(((maxrps+ssv-1)/ssv)*ssv,ww,hv,q);
          jpegos.startOfFrame(maxrps,ww,hv,q);
          int[] sel={0,1,1};                                             // DC,AC code table Y=0, Cb=Cr=1
          jpegos.startOfScan(sel);
        }

        for(int i=0;i<maxrps;i++){
          int x=0;
          while(x<width){
            c = imgdata[x+(y+i)*width];
//            c = image.getRGB(x,y+i);
            os.write((c>>16)&0x000000FF);
            os.write((c>> 8)&0x000000FF);
            os.write( c     &0x000000FF);
            x++;
          }
          while(x<ww){
            os.write((c>>16)&0x000000FF);
            os.write((c>> 8)&0x000000FF);
            os.write( c     &0x000000FF);
            x++;
          }
        }
        os.close();

        byte[] data=baos.toByteArray();
        counts.setCount(index,data.length);                              // update ifd strip counter array
        offsets.setOffset(index,out.getStreamPosition());                // update ifd image data offset array
        out.write(data);                                                 // write to image stream
        baos.reset();
        index++;
      }
      return ifd;
    }catch(Exception e){
      e.printStackTrace();
      throw new IOException(getClass().getName()+".writeYCbCrImage:\n\t"+e.getMessage());
    }
  }

  private BufferedImage convert(BufferedImage image, int imageType){
    if(image.getType()==imageType){return image;}

    int w=image.getWidth();
    int h=image.getHeight();
    BufferedImage newImg=new BufferedImage(w,h,imageType);
    ColorSpace srcSpace=image.getColorModel().getColorSpace();
    ColorSpace newSpace=newImg.getColorModel().getColorSpace();
    ColorConvertOp convert=new ColorConvertOp(srcSpace,newSpace,null);
    convert.filter(image,newImg);
    return newImg;
  }
}
