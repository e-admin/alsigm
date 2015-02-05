package uk.co.mmscomputing.imageio.bmp;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class BMPImageReaderSpi extends ImageReaderSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String readerClassName="uk.co.mmscomputing.imageio.bmp.BMPImageReader";
  static final String[] names={"bmp","BMP"};
  static final String[] suffixes={"bmp","BMP"};
  static final String[] MIMETypes={"image/bmp"};
  static final String[] writerSpiNames={"uk.co.mmscomputing.imageio.bmp.BMPImageWriterSpi"};

  static final boolean supportsStandardStreamMetadataFormat = false;
  static final String nativeStreamMetadataFormatName = null;
  static final String nativeStreamMetadataFormatClassName = null;
  static final String[] extraStreamMetadataFormatNames = null;
  static final String[] extraStreamMetadataFormatClassNames = null;
  static final boolean supportsStandardImageMetadataFormat = false;
  static final String nativeImageMetadataFormatName = "uk.co.mmscomputing.imageio.bmp.BMPMetadata 0.0.1";
  static final String nativeImageMetadataFormatClassName = "uk.co.mmscomputing.imageio.bmp.BMPMetadata";
  static final String[] extraImageMetadataFormatNames = null;
  static final String[] extraImageMetadataFormatClassNames = null;

  public BMPImageReaderSpi(){
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
    return new BMPImageReader(this);
  }

  public boolean canDecodeInput(Object source)throws IOException{
    if(!(source instanceof ImageInputStream)) { return false; }
    ImageInputStream stream = (ImageInputStream)source;
    stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    byte[] bm = new byte[2];
    try{
      stream.mark();
      stream.readFully(bm);
      stream.reset();
    }catch(IOException e){
      return false;
    }
    return(bm[0]==(byte)'B')&&(bm[1]==(byte)'M');
  }

  public String getDescription(Locale locale){
    return "mmsc bmp decoder";
  }
}