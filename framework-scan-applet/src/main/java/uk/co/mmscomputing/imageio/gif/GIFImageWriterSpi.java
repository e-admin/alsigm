package uk.co.mmscomputing.imageio.gif;

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;

public class GIFImageWriterSpi extends ImageWriterSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String writerClassName="uk.co.mmscomputing.imageio.gif.GIFImageWriter";
  static final String[] names={"gif","GIF"};
  static final String[] suffixes={"gif","GIF"};
  static final String[] MIMETypes={"image/gif"};
  static final String[] readerSpiNames={"uk.co.mmscomputing.imageio.gif.GIFImageReaderSpi"};

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

  public GIFImageWriterSpi(){
    super(	vendorName,		version,
		names,			suffixes,
		MIMETypes,		writerClassName,
		STANDARD_OUTPUT_TYPE,	readerSpiNames,
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
    return new GIFImageWriter(this);
  }

  public boolean canEncodeImage(ImageTypeSpecifier type){
// todo
//    int t=type.getBufferedImageType();
//    return (t==BufferedImage.TYPE_INT_RGB)||(t==BufferedImage.TYPE_BYTE_GRAY);
    return true;
  }

  public String getDescription(Locale locale){
    return "mmsc gif encoder";
  }

}