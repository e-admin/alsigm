package uk.co.mmscomputing.imageio.gif;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class GIFImageReaderSpi extends ImageReaderSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String readerClassName="uk.co.mmscomputing.imageio.gif.GIFImageReader";
  static final String[] names={"gif","GIF"};
  static final String[] suffixes={"gif","GIF"};
  static final String[] MIMETypes={"image/gif"};
  static final String[] writerSpiNames={"uk.co.mmscomputing.imageio.gif.GIFImageWriterSpi"};

  static final boolean supportsStandardStreamMetadataFormat = false;
  static final String nativeStreamMetadataFormatName = null;
  static final String nativeStreamMetadataFormatClassName = null;
  static final String[] extraStreamMetadataFormatNames = null;
  static final String[] extraStreamMetadataFormatClassNames = null;
  static final boolean supportsStandardImageMetadataFormat = false;
  static final String nativeImageMetadataFormatName =null;//"uk.co.mmscomputing.imageio.gif.GIFFormatMetadata 0.0.1";
  static final String nativeImageMetadataFormatClassName =null;//"uk.co.mmscomputing.imageio.gif.GIFFormatMetadata";
  static final String[] extraImageMetadataFormatNames = null;
  static final String[] extraImageMetadataFormatClassNames = null;

  public GIFImageReaderSpi(){
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
    return new GIFImageReader(this);
  }

  public boolean canDecodeInput(Object source)throws IOException{
    if(!(source instanceof ImageInputStream)) { return false; }
    ImageInputStream stream = (ImageInputStream)source;
    stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    byte[] type = new byte[3];
    byte[] version = new byte[3];
    try{
      stream.mark();
      stream.readFully(type);
      stream.readFully(version);
      stream.reset();
    }catch(IOException e){
      return false;
    }
    if((type[0]=='G')&&(type[1]=='I')&&(type[2]=='F')){
      if((version[0]=='8')&&(version[1]=='7')&&(version[2]=='a')){
        return true;
      }
      if((version[0]=='8')&&(version[1]=='9')&&(version[2]=='a')){
        return true;
      }
    }
    return false;
  }

  public String getDescription(Locale locale){
    return "mmsc gif decoder";
  }
}