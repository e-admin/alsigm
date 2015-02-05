package uk.co.mmscomputing.imageio.tiff;

import java.io.*;
import java.awt.image.*;
import javax.imageio.stream.*;

//    TIFF 6.0 Specification 3 June 1992

class DEFactory implements TIFFConstants{

  static public DE read(ImageInputStream in)throws IOException{
    int tag=in.readUnsignedShort();

    switch(tag){
    case NewSubfileType:      return new NewSubfileTypeDE(in);      // 254
    case ImageWidth:          return new ImageWidthDE(in);          // 256
    case ImageLength:         return new ImageLengthDE(in);         // 257
    case BitsPerSample:       return new BitsPerSampleDE(in);       // 258
    case Compression:         return new CompressionDE(in);         // 259
    case PhotometricInterpretation: return new PhotometricInterpretationDE(in);   // 262
    case FillOrder:           return new FillOrderDE(in);           // 266
    case DocumentName:        return new DocumentNameDE(in);        // 269
    case ImageDescription:    return new ImageDescriptionDE(in);    // 270
    case StripOffsets:        return new StripOffsetsDE(in);        // 273
    case Orientation:         return new OrientationDE(in);         // 274
    case SamplesPerPixel:     return new SamplesPerPixelDE(in);     // 277
    case RowsPerStrip:        return new RowsPerStripDE(in);        // 278
    case StripByteCounts:     return new StripByteCountsDE(in);     // 279
    case XResolution:         return new XResolutionDE(in);         // 282
    case YResolution:         return new YResolutionDE(in);         // 283
    case PlanarConfiguration: return new PlanarConfigurationDE(in); // 284
    case T4Options:           return new T4OptionsDE(in);           // 292
    case T6Options:           return new T6OptionsDE(in);           // 293
    case ResolutionUnit:      return new ResolutionUnitDE(in);      // 296
    case PageNumber:          return new PageNumberDE(in);          // 297
    case Software:            return new SoftwareDE(in);            // 305
    case DateTime:            return new DateTimeDE(in);            // 306
    case Artist:              return new ArtistDE(in);              // 315
    case Predictor:           return new PredictorDE(in);           // 317
    case ColorMap:            return new ColorMapDE(in);            // 320
    case ExtraSamples:        return new ExtraSamplesDE(in);        // 338
    case SampleFormat:        return new SampleFormatDE(in);        // 339
    case JPEGTables:          return new JPEGTablesDE(in);          // 347

    case YCbCrCoefficients:   return new YCbCrCoefficientsDE(in);   // 529
    case YCbCrSubSampling:    return new YCbCrSubSamplingDE(in);    // 530
    case YCbCrPositioning:    return new YCbCrPositioningDE(in);    // 531
    case ReferenceBlackWhite: return new ReferenceBlackWhiteDE(in); // 532

    default:                  return new UnknownDE(tag,in);
    }
  }

  static public class UnknownDE extends DE{
    public UnknownDE(int tag,ImageInputStream in)throws IOException{
      super(tag);read(in);
    }
  }

  static public class NewSubfileTypeDE extends DE{               // 254

    // Bit 0 is 1 if the image is a reduced-resolution version of another image in this TIFF file; else the bit is 0. 
    // Bit 1 is 1 if the image is a single page of a multi-page image (see the PageNumber field description); else the bit is 0. 
    // Bit 2 is 1 if the image defines a transparency mask for another image in this TIFF file. The PhotometricInterpretation value must be 4, designating a transparency mask.

    public NewSubfileTypeDE(ImageInputStream in)throws IOException{
      super(NewSubfileType);read(in);
    }

    public NewSubfileTypeDE(int val)throws IOException{
      super(NewSubfileType);setType(LONG);setCount(1);setValue(val);
    }

    static int getDefault(){return 0;}
  }

  static public class ImageWidthDE extends DE{                   // 256
    public ImageWidthDE(ImageInputStream in)throws IOException{
      super(ImageWidth);read(in);
    }

    public ImageWidthDE(int val)throws IOException{
      super(ImageWidth);setType(LONG);setCount(1);setValue(val);
    }
  }

  static public class ImageLengthDE extends DE{                  // 257
    public ImageLengthDE(ImageInputStream in)throws IOException{
      super(ImageLength);read(in);
    }

    public ImageLengthDE(int val)throws IOException{
      super(ImageLength);setType(LONG);setCount(1);setValue(val);
    }
  }

  static public class BitsPerSampleDE extends DE{                // 258

    // BitsPerSample 258 SHORT  Samples per pixel

    // read

    long[] bps;

    public BitsPerSampleDE(ImageInputStream in)throws IOException{
      super(BitsPerSample);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      bps=readIntArray(in);
    }

    public int    getBits(int index){return (int)bps[index];}
    public long[] getBits(){return bps;}

    // write

    public BitsPerSampleDE(int len)throws IOException{
      super(BitsPerSample);
      bps=new long[len];
      setType(SHORT);
      setCount(len);
    }

    public void setBitsPerSample(int index,long bits){
      bps[index]=bits;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeIntArray(out,bps);
    }
  }

  static public class CompressionDE extends DE{                  // 259

    // 1,2,32773 mandatory

    public CompressionDE(ImageInputStream in)throws IOException{
      super(Compression);read(in);
    }

    public CompressionDE(int val)throws IOException{
      super(Compression);setType(SHORT);setCount(1);setValue(val);
    }
  }

  static public class PhotometricInterpretationDE extends DE{    // 262

    // 0 = WhiteIsZero        (Compression 2)
    // 1 = BlackIsZero
    // 2 = 

    public PhotometricInterpretationDE(ImageInputStream in)throws IOException{
      super(PhotometricInterpretation);read(in);
    }

    public PhotometricInterpretationDE(int val)throws IOException{
      super(PhotometricInterpretation);setType(SHORT);setCount(1);setValue(val);
    }
  }

  static public class FillOrderDE extends DE{                    // 266

    // 1 = Most Significant Bit first
    // 2 = Least Significant Bit first

    public FillOrderDE(ImageInputStream in)throws IOException{
      super(FillOrder);read(in);
    }

    public FillOrderDE(int val)throws IOException{
      super(FillOrder);setType(SHORT);setCount(1);setValue(val);
    }
  }

  static public class DocumentNameDE extends DE{                 // 269

    String name;

    public DocumentNameDE(ImageInputStream in)throws IOException{
      super(DocumentName);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      name=readString(in)[0];
    }
  }

  static public class ImageDescriptionDE extends DE{             // 270

    String name;

    public ImageDescriptionDE(ImageInputStream in)throws IOException{
      super(ImageDescription);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      name=readString(in)[0];
    }
  }

  static public class StripOffsetsDE extends DE{                 // 273

    private long[] offsets;

    // Tag length = StripsPerlmage for PlanarConfig = 1
    // SamplesPerPixel * StripsPerlmage for PlanarConfig = 2

    // read

    public StripOffsetsDE(ImageInputStream in)throws IOException{
      super(StripOffsets);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      offsets=readIntArray(in);
    }

    public long[] getOffsets(){return offsets;}

    // write

    public StripOffsetsDE(int len)throws IOException{
      super(StripOffsets);
      offsets=new long[len];
      setType(LONG);
      setCount(len);
    }

    public void setOffset(int index,long offset){
      offsets[index]=offset;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeIntArray(out,offsets);
    }
  }

  static public class OrientationDE extends DE{                  // 274
    public OrientationDE(ImageInputStream in)throws IOException{
      super(Orientation);read(in);
    }

    public OrientationDE(int val)throws IOException{
      super(Orientation);setType(SHORT);setCount(1);setValue(val);
    }
  }

  static public class SamplesPerPixelDE extends DE{              // 277
    public SamplesPerPixelDE(ImageInputStream in)throws IOException{
      super(SamplesPerPixel);read(in);
    }

    public SamplesPerPixelDE(int val)throws IOException{
      super(SamplesPerPixel);setType(SHORT);setCount(1);setValue(val);
    }
  }

  static public class RowsPerStripDE extends DE{                 // 278
    public RowsPerStripDE(ImageInputStream in)throws IOException{
      super(RowsPerStrip);read(in);
    }

    public RowsPerStripDE(int val)throws IOException{
      super(RowsPerStrip);setType(LONG);setCount(1);setValue(val);
    }
  }

  static public class StripByteCountsDE extends DE{              // 279

    private long counts[];

    // read

    public StripByteCountsDE(ImageInputStream in)throws IOException{
      super(StripByteCounts);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      counts=readIntArray(in);
    }

    public long[] getCounts(){return counts;}

    // write

    public StripByteCountsDE(int len)throws IOException{
      super(StripByteCounts);
      counts=new long[len];
      setType(LONG);
      setCount(len);
    }

    public void setCount(int index,long count){
      counts[index]=count;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeIntArray(out,counts);
    }
  }

  static public class XResolutionDE extends DE{                  // 282

    double xres;

    public XResolutionDE(ImageInputStream in)throws IOException{
      super(XResolution);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      xres=readReal(in);
    }

    public XResolutionDE(double val)throws IOException{
      super(XResolution);setType(RATIONAL);setCount(1);xres=val;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeReal(out,xres);
    }
  }

  static public class YResolutionDE extends DE{                  // 283

    double yres;

    public YResolutionDE(ImageInputStream in)throws IOException{
      super(YResolution);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      yres=readReal(in);
    }

    public YResolutionDE(double val)throws IOException{
      super(YResolution);setType(RATIONAL);setCount(1);yres=val;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeReal(out,yres);
    }
  }


  static public class PlanarConfigurationDE extends DE{          // 284
    public PlanarConfigurationDE(ImageInputStream in)throws IOException{
      super(PlanarConfiguration);read(in);
    }
  }

  static public class T4OptionsDE extends DE{                    // 292

    // Bit 0: 0 = 1-Dimensional;         1 = 2-Dimensional (MR)
    // Bit 1: 0 = compressed;            1 = uncompressed mode (not allowed in rfc 2306)
    // Bit 2: 0 = non byte aligned EOLs; 1 = byte aligned EOLs

    public T4OptionsDE(ImageInputStream in)throws IOException{
      super(T4Options);read(in);
    }

    public T4OptionsDE(int val)throws IOException{
      super(T4Options);setType(LONG);setCount(1);setValue(val);
    }

    public boolean is1Dimensional(){return (val&0x00000001)==0;}
    public boolean is2Dimensional(){return (val&0x00000001)!=0;}
    public boolean isCompressed(){return (val&0x00000002)==0;}
    public boolean isByteAligned(){return (val&0x00000004) !=0;}
  }

  static public class T6OptionsDE extends DE{                    // 293

    // Bit 0: 0 = 2-Dimensional (MMR)
    // Bit 1: 0 = compressed;            1 = uncompressed mode (not allowed in rfc 2306)

    public T6OptionsDE(ImageInputStream in)throws IOException{
      super(T6Options);read(in);
    }

    public T6OptionsDE(int val)throws IOException{
      super(T6Options);setType(LONG);setCount(1);setValue(val);
    }

    public boolean is2Dimensional(){return (val&0x00000001)==0;}
    public boolean isCompressed(){return (val&0x00000002)==0;}
  }

  static public class ResolutionUnitDE extends DE{               // 296

    // 1 = no unit
    // 2 = inch (default)
    // 3 = cm

    public ResolutionUnitDE(ImageInputStream in)throws IOException{
      super(ResolutionUnit);read(in);
    }

    public ResolutionUnitDE(int val)throws IOException{
      super(ResolutionUnit);setType(SHORT);setCount(1);setValue(val);
    }
  }

  static public class PageNumberDE extends DE{                   // 297

    // SHORT/SHORT <=> page number (0...)/page count (0=unknown)

    private long values[];

    // read

    public PageNumberDE(ImageInputStream in)throws IOException{
      super(PageNumber);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      values=readIntArray(in);
    }


    // write

    public PageNumberDE(int page,int count)throws IOException{
      super(PageNumber);
      values=new long[2];
      setType(SHORT);
      setCount(2);
    }

    public void setPage(int page){values[0]=page;}
    public int  getPage(){return (int)values[0];}

    public void setPageCount(int pc){values[1]=pc;}
    public int  getPageCount(){return (int)values[1];}

    public void write(ImageOutputStream out)throws IOException{
      writeIntArray(out,values);
    }
  }

  static public class SoftwareDE extends DE{                     // 305

    String name;

    public SoftwareDE(ImageInputStream in)throws IOException{
      super(Software);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      name=readString(in)[0];
    }
  }

  static public class DateTimeDE extends DE{                     // 306

    String datetime;

    public DateTimeDE(ImageInputStream in)throws IOException{
      super(DateTime);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      datetime=readString(in)[0];
    }
  }

  static public class ArtistDE extends DE{                       // 315

    String name;

    public ArtistDE(ImageInputStream in)throws IOException{
      super(Artist);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      name=readString(in)[0];
    }
  }

  static public class PredictorDE extends DE{              // 317

    // 1 = No prediction scheme used before coding.
    // 2 = Horizontal differencing.

    public PredictorDE(ImageInputStream in)throws IOException{
      super(Predictor);read(in);
    }

    public PredictorDE(int val)throws IOException{
      super(Predictor);setType(SHORT);setCount(1);setValue(val);
    }

    static int getDefault(){return 1;}
  }

  static public class ColorMapDE extends DE{                     // 320

    private IndexColorModel cm=null;

    public ColorMapDE(ImageInputStream in)throws IOException{
      super(ColorMap);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      long[] map=readIntArray(in);
      int len=map.length/3;
      byte[] r=new byte[len];
      byte[] g=new byte[len];
      byte[] b=new byte[len];

      for(int i=0;i<len;i++){
        r[i]=(byte)(map[i]&0x000000FF);           // first all red
        g[i]=(byte)(map[len+i]&0x000000FF);       // then all green
        b[i]=(byte)(map[len+len+i]&0x000000FF);   // and blue
      }
      int bits=0,l=len;
      while(l>1){bits++;l>>>=1;}                  // System.out.println("9\bBITS+"+bits);
      cm=new IndexColorModel(bits,len,r,g,b);
    }

    public IndexColorModel getColorModel(){return cm;}

/*
    public int getColor(int index){

      // 3*(2^BitsPerSample)
      
      int r=(int)(map[index]&0x000000FF);           // first all red
      int g=(int)(map[len+index]&0x000000FF);       // then all green
      int b=(int)(map[len+len+index]&0x000000FF);   // and blue
      int col=(r<<16)|(g<<8)|b;
      return col;
    }
*/
  }

  static public class ExtraSamplesDE extends DE{                // 338

    // ExtraSamples 338 SHORT  ExtraSamples

    // read

    long[] exs;

    public ExtraSamplesDE(ImageInputStream in)throws IOException{
      super(ExtraSamples);
      read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      exs=readIntArray(in);
    }

    public int    getExtraSample(int index){return (int)exs[index];}
    public long[] getExtraSamples(){return exs;}

    // write

    public ExtraSamplesDE(int len)throws IOException{
      super(ExtraSamples);
      exs=new long[len];
      setType(SHORT);
      setCount(len);
    }

    public void setExtraSample(int index,long value){
      exs[index]=value;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeIntArray(out,exs);
    }
  }

  static public class SampleFormatDE extends DE{                   // 339

    // 1 = unsigned integer data 
    // 2 = two s complement signed integer data 
    // 3 = IEEE floating point data [IEEE] 
    // 4 = undefined data format

    public SampleFormatDE(ImageInputStream in)throws IOException{
      super(SampleFormat);read(in);
    }

    public SampleFormatDE(int val)throws IOException{
      super(SampleFormat);setType(SHORT);setCount(1);setValue(val);
    }

    static int getDefault(){return 1;}
  }

  static public class JPEGTablesDE extends DE{                     // 347

  // "When the JPEGTables field is present, it shall contain a valid JPEG
  // 'abbreviated table specification' datastream."
  //  from TIFFTechNote2

    private byte[] jpegsegment;

  // read

    public JPEGTablesDE(ImageInputStream in)throws IOException{
      super(JPEGTables);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      jpegsegment=readByteArray(in);
    }

    public byte[] getData(){return jpegsegment;}

  // write

    public JPEGTablesDE(byte[] js)throws IOException{
      super(JPEGTables);setType(UNDEFINED);setCount(js.length);
      this.jpegsegment=js;
    }

    public void write(ImageOutputStream out)throws IOException{
      writeByteArray(out,jpegsegment);
    }
  }

  static public class YCbCrCoefficientsDE extends DE{              // 529

    double[] coefficients=null;

    // read

    public YCbCrCoefficientsDE(ImageInputStream in)throws IOException{
      super(YCbCrCoefficients);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      coefficients=readRealArray(in);
    }
    public double[] getCoefficients(){
      return coefficients;
    }

    // write

    public YCbCrCoefficientsDE()throws IOException{
      super(YCbCrCoefficients);
      coefficients=new double[3];
      setType(RATIONAL);
      setCount(3);
    }

    public void setLumaRed  (double value){coefficients[0]=value;}
    public void setLumaGreen(double value){coefficients[1]=value;}
    public void setLumaBlue (double value){coefficients[2]=value;}

    public void write(ImageOutputStream out)throws IOException{
      writeRealArray(out,coefficients);
    }
  }

  static public class YCbCrSubSamplingDE extends DE{               // 530

    long[] subSampling;

    // read

    public YCbCrSubSamplingDE(ImageInputStream in)throws IOException{
      super(YCbCrSubSampling);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      subSampling=readIntArray(in);
    }

    public int    getSubSampling(int index){return (int)subSampling[index];}
    public long[] getSubSampling(){return subSampling;}

    // write

    public YCbCrSubSamplingDE()throws IOException{
      super(YCbCrSubSampling);
      subSampling=new long[2];
      setType(SHORT);
      setCount(2);
    }

    public void setHoriz(int value){subSampling[0]=value;}
    public void setVert (int value){subSampling[1]=value;}

    public void write(ImageOutputStream out)throws IOException{
      writeIntArray(out,subSampling);
    }
  }

  static public class YCbCrPositioningDE extends DE{               // 531

    public YCbCrPositioningDE(ImageInputStream in)throws IOException{
      super(YCbCrPositioning);read(in);
    }

  }

  static public class ReferenceBlackWhiteDE extends DE{            // 532

    double[] reference;

    public ReferenceBlackWhiteDE(ImageInputStream in)throws IOException{
      super(ReferenceBlackWhite);read(in);
    }

    public void read(ImageInputStream in)throws IOException{
      super.read(in);
      reference=readRealArray(in);
    }

    public double   getReference(int index){return (int)reference[index];}
    public double[] getReference(){return reference;}

    // write

    public ReferenceBlackWhiteDE()throws IOException{
      super(ReferenceBlackWhite);
      reference=new double[6];
      setType(RATIONAL);
      setCount(6);
    }

    public void setY (double black,double white){reference[0]=black;reference[1]=white;}
    public void setCb(double black,double white){reference[2]=black;reference[3]=white;}
    public void setCr(double black,double white){reference[4]=black;reference[5]=white;}

    public void write(ImageOutputStream out)throws IOException{
      writeRealArray(out,reference);
    }
  }

}


