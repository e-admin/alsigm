package uk.co.mmscomputing.imageio.jpeg;

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;

public class JPEGImageWriterSpi extends ImageWriterSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String writerClassName="uk.co.mmscomputing.imageio.jpeg.JPEGImageWriter";
  static final String[] names={"jpg","jpeg","JPG","JPEG"};
  static final String[] suffixes={"jpg","jpeg","JPG","JPEG"};
  static final String[] MIMETypes={"image/jpeg"};
  static final String[] readerSpiNames={"uk.co.mmscomputing.imageio.jpeg.JPEGImageReaderSpi"};

  static final boolean supportsStandardStreamMetadataFormat = false;
  static final String nativeStreamMetadataFormatName = null;
  static final String nativeStreamMetadataFormatClassName = null;
  static final String[] extraStreamMetadataFormatNames = null;
  static final String[] extraStreamMetadataFormatClassNames = null;
  static final boolean supportsStandardImageMetadataFormat = false;
  static final String nativeImageMetadataFormatName =null;//"uk.co.mmscomputing.imageio.jpeg.JPEGFormatMetadata 0.0.1";
  static final String nativeImageMetadataFormatClassName =null;//"uk.co.mmscomputing.imageio.jpeg.JPEGFormatMetadata";
  static final String[] extraImageMetadataFormatNames = null;
  static final String[] extraImageMetadataFormatClassNames = null;

  public JPEGImageWriterSpi(){
    super(    vendorName,            version,
		          names,                 suffixes,
              MIMETypes,             writerClassName,
              STANDARD_OUTPUT_TYPE,  readerSpiNames,
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

  public ImageWriter createWriterInstance(Object extension)throws IOException{
    return new JPEGImageWriter(this);
  }

  public boolean canEncodeImage(ImageTypeSpecifier type){               // deal with black/white only
//    int        t  =type.getBufferedImageType();
    return true;
  }

  public String getDescription(Locale locale){
    return "mmsc jpeg encoder";
  }

}