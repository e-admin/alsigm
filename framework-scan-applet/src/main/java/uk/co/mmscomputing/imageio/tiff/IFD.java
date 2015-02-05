package uk.co.mmscomputing.imageio.tiff;

import java.io.*;
import java.util.*;

import java.awt.image.*;
import javax.imageio.stream.*;

@SuppressWarnings({ "serial", "unchecked" })
class IFD extends Vector implements TIFFConstants{     // Image File Directory

  public IFD(){
  }

  public long read(ImageInputStream in, long pos)throws IOException{
    in.seek(pos);
    int len=in.readUnsignedShort();                    // no of entries
    for(int i=0;i<len;i++){
      add(DEFactory.read(in));                         // directory entries
    }
    return in.readUnsignedInt();                       // offset to next IFD or 0
  }

  public DE getDE(int tag){
    Enumeration e=elements();
    while(e.hasMoreElements()){
      DE de=((DE)e.nextElement());
      if(tag==de.getTag()){
        return de;
      }
    }
    throw new IllegalArgumentException(getClass().getName()+"getIntValue:\n\tIFD does not feature DE with Tag "+tag);
  }

  public long getIntValue(int tag){
    DE de=getDE(tag);
    if(de.getCount()!=1){                              // check for type not implemented
      throw new IllegalArgumentException(getClass().getName()+"getIntValue:\n\tExpect Count=1 for Tag "+tag);
    }
    return de.getValue();
  }


  public int getNewSubfileType(){                      // 254
    try{
      return (int)getIntValue(NewSubfileType);
    }catch(IllegalArgumentException iae){
      return DEFactory.NewSubfileTypeDE.getDefault();
    }
  }

  public int getWidth(){                               // 256
    return (int)getIntValue(ImageWidth);
  }

  public int getHeight(){                              // 257
    return (int)getIntValue(ImageLength);
  }

  public int getLength(){                              // 257
    return (int)getIntValue(ImageLength);
  }

  public int getBitsPerSample(int index){   
    try{
      return ((DEFactory.BitsPerSampleDE)getDE(BitsPerSample)).getBits(index);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: 1
    }
  }

  public long[] getBitsPerSample(){   
    try{
      return ((DEFactory.BitsPerSampleDE)getDE(BitsPerSample)).getBits();
    }catch(IllegalArgumentException iae){
      long[] bitsPerSample=new long[1];
      bitsPerSample[0]=1;
      return bitsPerSample;                            // default: 1
    }
  }

  public int getCompression(){                         // 259
    try{
      return (int)getIntValue(Compression);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: no compression
    }
  }

  public int getPhotometricInterpretation(){           // 262
    try{
      return (int)getIntValue(PhotometricInterpretation);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: black is zero
    }
  }

  public int getFillOrder(){                           // 266
    try{
      return (int)getIntValue(FillOrder);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: 1 = pixel with low column value => high bits
    }
  }

  public int getSamplesPerPixel(){                     // 277
    try{
      return (int)getIntValue(SamplesPerPixel);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: 1
    }
  }

  public int getRowsPerStrip(){                        // 278
    return (int)getIntValue(RowsPerStrip);
  }

  public DEFactory.ColorMapDE getColorMap(){           // 320
    return (DEFactory.ColorMapDE)getDE(ColorMap);
  }

  public IndexColorModel getColorModel(){              // 320
    return ((DEFactory.ColorMapDE)getDE(ColorMap)).getColorModel();
  }

  public int getExtraSamplesLength(){                  // 338
    try{
      return ((DEFactory.ExtraSamplesDE)getDE(ExtraSamples)).getExtraSamples().length;
    }catch(IllegalArgumentException iae){
      return 0;                                        // default: 0; no extra samples
    }
  }

  public int getExtraSample(int index){                // 338
    return ((DEFactory.ExtraSamplesDE)getDE(ExtraSamples)).getExtraSample(index);
  }

  public int getSampleFormat(){                        // 339
    try{
      return (int)getIntValue(SampleFormat);
    }catch(IllegalArgumentException iae){
      return DEFactory.SampleFormatDE.getDefault();
    }
  }

  public byte[] getJPEGTables(){                       // 347
    return ((DEFactory.JPEGTablesDE)getDE(JPEGTables)).getData();  
  }

  public int getT4Options(){                           // required, if compression=3 (rfc2306 p.9)
    try{
      return (int)getIntValue(T4Options);
    }catch(IllegalArgumentException iae){
      return 0;                                        // default: 1-dimensional MH, non-byte aligned
    }
  }

  public int getT6Options(){                           // required, if compression=4 (rfc2306 p.??)
    try{
      return (int)getIntValue(T4Options);
    }catch(IllegalArgumentException iae){
      return 0;                                        // default: 2-dimensional MMR, compressed
    }
  }

  public int getPlanarConfiguration(){
    try{
      return (int)getIntValue(PlanarConfiguration);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: chunky format
    }
  }

  public int getResolutionUnit(){
    try{
      return (int)getIntValue(ResolutionUnit);
    }catch(IllegalArgumentException iae){
      return 2;                                        // default: inch
    }
  }

  public long[] getStripOffsets(){    return ((DEFactory.StripOffsetsDE)getDE(StripOffsets)).getOffsets();}
  public long[] getStripByteCounts(){ return ((DEFactory.StripByteCountsDE)getDE(StripByteCounts)).getCounts();}


  public int getPredictor(){
    try{
      return (int)getIntValue(Predictor);
    }catch(IllegalArgumentException iae){
      return DEFactory.PredictorDE.getDefault();
    }
  }

  public double[] getYCbCrCoefficients(){
    try{
      return ((DEFactory.YCbCrCoefficientsDE)getDE(YCbCrCoefficients)).getCoefficients();
    }catch(IllegalArgumentException iae){
      double[] coeffs={299/1000,587/1000,114/1000};
      return coeffs;
    }
  }

  public long[] getYCbCrSubSampling(){
    try{
      return ((DEFactory.YCbCrSubSamplingDE)getDE(YCbCrSubSampling)).getSubSampling();
    }catch(IllegalArgumentException iae){
      long[] ss={2,2};
      return ss;                                        
    }
  }

  public int getYCbCrPositioning(){
    try{
      return (int)getIntValue(YCbCrPositioning);
    }catch(IllegalArgumentException iae){
      return 1;                                        // default: centered
    }
  }

  public double[] getReferenceBlackWhite(){
    try{
      return ((DEFactory.ReferenceBlackWhiteDE)getDE(ReferenceBlackWhite)).getReference();
    }catch(IllegalArgumentException iae){
      long[] bps=getBitsPerSample();
      double[] reference={0,(1<<bps[0])-1,0,(1<<bps[1])-1,0,(1<<bps[2])-1};
      return reference;                                // default: [0/,NV/1, 0/1, NV/1, 0/1, NV/1] where NV = 2 ** BitsPerSample - 1.
    }
  }

  public long write(ImageOutputStream out, long lastIfdPosPtr)throws IOException{
    Enumeration e;DE de;

    // Write content of IFD tags

    e=elements();
    while(e.hasMoreElements()){
      de=((DE)e.nextElement());
      de.write(out);                                   // write content if > 4 bytes save stream position to val
    }

    // Write IFD

    long pos=out.getStreamPosition();
    while((pos%4)!=0){                                 // align to 32-bit boundary
      out.write(0);pos++;
    }
    out.mark();
    out.seek(lastIfdPosPtr);                           // offset position for ptr to new ifd
    out.writeInt((int)pos);                            // set ifd offset to this ifd stream position
    out.reset();

    int len=size();
    out.writeShort(len);                               // no of entries

    e=elements();
    while(e.hasMoreElements()){
      de=((DE)e.nextElement());
      de.writeEntry(out);                              // write value/address to content
    }

    pos=out.getStreamPosition();                       // offset position for ptr to new ifd. (ifd linked list)
    out.writeInt(0);                                   // no new ifd yet

    return pos;                                        
  }

}
