package uk.co.mmscomputing.imageio.sff;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;

public class SFFImageWriterSpi extends ImageWriterSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.2";
  static final String writerClassName="uk.co.mmscomputing.imageio.sff.SFFImageWriter";
  static final String[] names={"sff","SFF"};
  static final String[] suffixes={"sff","SFF"};
  static final String[] MIMETypes={"image/sff"};
  static final String[] readerSpiNames={"uk.co.mmscomputing.imageio.sff.SFFImageReaderSpi"};

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

  public SFFImageWriterSpi(){
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
    return new SFFImageWriter(this);
  }

  public boolean canEncodeImage(ImageTypeSpecifier type){               // deal with black/white only
    int        t  =type.getBufferedImageType();
    ColorModel cm =type.getColorModel();
    return (t==BufferedImage.TYPE_BYTE_BINARY)&&(cm.getPixelSize()==1);  
  }

  public String getDescription(Locale locale){
    return "mmsc sff encoder";
  }

}