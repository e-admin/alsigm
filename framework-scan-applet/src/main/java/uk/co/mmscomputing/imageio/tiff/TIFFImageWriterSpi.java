package uk.co.mmscomputing.imageio.tiff;

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;

public class TIFFImageWriterSpi extends ImageWriterSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String writerClassName="uk.co.mmscomputing.imageio.tiff.TIFFImageWriter";
  static final String[] names={"tif","TIF","tiff","TIFF"};
  static final String[] suffixes={"tif","TIF","tiff","TIFF"};
  static final String[] MIMETypes={"image/tiff"};
  static final String[] readerSpiNames={"uk.co.mmscomputing.imageio.tiff.TIFFImageReaderSpi"};

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

  public TIFFImageWriterSpi(){
    super(vendorName,version,
		names,suffixes,
		MIMETypes,writerClassName,
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
    return new TIFFImageWriter(this);
  }

  public boolean canEncodeImage(ImageTypeSpecifier type){
//    int t=type.getBufferedImageType();
//    return (t==BufferedImage.TYPE_INT_RGB)||(t==BufferedImage.TYPE_BYTE_BINARY);
    return true;
  }

  public String getDescription(Locale locale){
    return "mmsc tiff encoder";
  }

}