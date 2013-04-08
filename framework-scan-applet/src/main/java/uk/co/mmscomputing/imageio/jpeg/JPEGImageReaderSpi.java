package uk.co.mmscomputing.imageio.jpeg;

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class JPEGImageReaderSpi extends ImageReaderSpi {

  static final String vendorName="mm's computing";
  static final String version="0.0.1";
  static final String readerClassName="uk.co.mmscomputing.imageio.jpeg.JPEGImageReader";
  static final String[] names={"jpg","JPG","jpeg","JPEG"};
  static final String[] suffixes={"jpg","JPG","jpeg","JPEG"};
  static final String[] MIMETypes={"image/jpeg"};
  static final String[] writerSpiNames=null;
//  static final String[] writerSpiNames={"uk.co.mmscomputing.imageio.jpeg.JPEGImageWriterSpi"};

  static final boolean supportsStandardStreamMetadataFormat = false;
  static final String nativeStreamMetadataFormatName = null;
  static final String nativeStreamMetadataFormatClassName = null;
  static final String[] extraStreamMetadataFormatNames = null;
  static final String[] extraStreamMetadataFormatClassNames = null;
  static final boolean supportsStandardImageMetadataFormat = false;
  static final String nativeImageMetadataFormatName = null;
  static final String nativeImageMetadataFormatClassName = null;
  static final String[] extraImageMetadataFormatNames = null;
  static final String[] extraImageMetadataFormatClassNames = null;

  public JPEGImageReaderSpi(){
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
    return new JPEGImageReader(this);
  }

  public boolean canDecodeInput(Object source)throws IOException{
    if(!(source instanceof ImageInputStream)) { return false; }
    ImageInputStream in = (ImageInputStream)source;

    if(in.read()!=JPEGConstants.MARK){return false;}
    if(in.read()!=JPEGConstants.SOI){return false;}
    if(in.read()!=JPEGConstants.MARK){return false;}
    if(in.read()!=JPEGConstants.APP0){return false;}
    in.read();in.read();
    if(in.read()!='J'){return false;}
    if(in.read()!='F'){return false;}
    if(in.read()!='I'){return false;}
    if(in.read()!='F'){return false;}
    if(in.read()!= 0 ){return false;}
    return true;
  }

  public String getDescription(Locale locale){
    return "mmsc jpeg decoder";
  }
}

// [1] JPEG File Interchange Format Version 1.02
// http://www.jpeg.org/public/jfif.pdf [last accessed 2005-11-24]