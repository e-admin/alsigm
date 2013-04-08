package uk.co.mmscomputing.imageio.tiff;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class TIFFImageReaderSpi extends ImageReaderSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String readerClassName="uk.co.mmscomputing.imageio.tiff.TIFFImageReader";
  static final String[] names={"tif","TIF","tiff","TIFF"};
  static final String[] suffixes={"tif","TIF","tiff","TIFF"};
  static final String[] MIMETypes={"image/tiff"};
  static final String[] writerSpiNames={"uk.co.mmscomputing.imageio.tiff.TIFFImageWriterSpi"};

  static final boolean supportsStandardStreamMetadataFormat = false;
  static final String nativeStreamMetadataFormatName = null;
  static final String nativeStreamMetadataFormatClassName = null;
  static final String[] extraStreamMetadataFormatNames = null;
  static final String[] extraStreamMetadataFormatClassNames = null;
  static final boolean supportsStandardImageMetadataFormat = false;
  static final String nativeImageMetadataFormatName ="uk.co.mmscomputing.imageio.tiff.TIFFMetadata_1.0";
  static final String nativeImageMetadataFormatClassName ="uk.co.mmscomputing.imageio.tiff.TIFFMetadata";
  static final String[] extraImageMetadataFormatNames = null;
  static final String[] extraImageMetadataFormatClassNames = null;

  public TIFFImageReaderSpi(){
    super( vendorName,version,
		names,suffixes,
    MIMETypes,readerClassName,
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
    return new TIFFImageReader(this);
  }

  public boolean canDecodeInput(Object source)throws IOException{
    if(!(source instanceof ImageInputStream)) { return false; }
    ImageInputStream in = (ImageInputStream)source;

    int bo=in.readUnsignedShort();
    if(bo==0x00004D4D){                             // MM
      in.setByteOrder(ByteOrder.BIG_ENDIAN);
    }else if(bo==0x00004949){                       // II
      in.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }else{
      return false;
    }
    int version=in.readUnsignedShort();
    return version==42;
  }

  public String getDescription(Locale locale){
    return "mmsc tiff decoder";
  }
}