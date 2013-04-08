package uk.co.mmscomputing.imageio.sff;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class SFFImageReaderSpi extends ImageReaderSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String readerClassName="uk.co.mmscomputing.imageio.sff.SFFImageReader";
  static final String[] names={"sff","SFF"};
  static final String[] suffixes={"sff","SFF"};
  static final String[] MIMETypes={"image/sff"};
  static final String[] writerSpiNames={"uk.co.mmscomputing.imageio.sff.SFFImageWriterSpi"};

  static final boolean supportsStandardStreamMetadataFormat = false;
  static final String nativeStreamMetadataFormatName = null;
  static final String nativeStreamMetadataFormatClassName = null;
  static final String[] extraStreamMetadataFormatNames = null;
  static final String[] extraStreamMetadataFormatClassNames = null;
  static final boolean supportsStandardImageMetadataFormat = false;
  static final String nativeImageMetadataFormatName =null;//"uk.co.mmscomputing.imageio.sff.SFFFormatMetadata 0.0.1";
  static final String nativeImageMetadataFormatClassName =null;//"uk.co.mmscomputing.imageio.sff.SFFFormatMetadata";
  static final String[] extraImageMetadataFormatNames = null;
  static final String[] extraImageMetadataFormatClassNames = null;

  public SFFImageReaderSpi(){
    super(	vendorName,		version,
		names,			suffixes,
		MIMETypes,		readerClassName,
		STANDARD_INPUT_TYPE,	writerSpiNames,
		supportsStandardStreamMetadataFormat,
		nativeStreamMetadataFormatName,
		nativeStreamMetadataFormatClassName,
		extraStreamMetadataFormatNames,
                extraStreamMetadataFormatClassNames,
		supportsStandardImageMetadataFormat,
		nativeImageMetadataFormatName,
                nativeImageMetadataFormatClassName,
                extraImageMetadataFormatNames,
		extraImageMetadataFormatClassNames
    );
  }

  public ImageReader createReaderInstance(Object extension)throws IOException{
    return new SFFImageReader(this);
  }

  public boolean canDecodeInput(Object source)throws IOException{
    if(!(source instanceof ImageInputStream)) { return false; }
    ImageInputStream stream = (ImageInputStream)source;
    stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    byte[] Sfff = new byte[4];
    try{
      stream.mark();
      stream.readFully(Sfff);
      stream.reset();
    }catch(IOException e){
      return false;
    }
    return(Sfff[0]==(byte)'S')&&(Sfff[1]==(byte)'f')&&(Sfff[2]==(byte)'f')&&(Sfff[3]==(byte)'f');
  }

  public String getDescription(Locale locale){
    return "mmsc sff decoder";
  }
}