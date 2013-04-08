package uk.co.mmscomputing.imageio.bmp;

import java.util.*;
import javax.imageio.*;

public class BMPImageWriteParam extends ImageWriteParam implements BMPConstants{

  public BMPImageWriteParam(Locale locale){
    super(locale);
    compressionTypes=compressionTypeNames;
    setCompressionMode(MODE_EXPLICIT);
    setCompressionType(compressionTypeNames[0]);          // BI_RGB
//    setController(new BMPIIOParamController(locale));
  }

  public boolean canWriteCompressed(){return false;}      // We don't do compression

}

/*
     <javax_imageio_1.0>
         <Compression>
             <CompressionTypeName value="BI_RGB"></CompressionTypeName>
         </Compression>
         <Data>
             <BitsPerSample value="8 8 8 "></BitsPerSample>
         </Data>
         <Dimension>
             <PixelAspectRatio value="1.0"></PixelAspectRatio>
             <HorizontalPhysicalPixelSpacing value="0"></HorizontalPhysicalPixelSpacing>
             <VerticalPhysicalPixelSpacing value="0"></VerticalPhysicalPixelSpacing>
         </Dimension>
     </javax_imageio_1.0>
 
     <javax_imageio_bmp_1.0>
         <BMPVersion>BMP v. 3.x</BMPVersion>
         <Width>1700</Width>
         <Height>2338</Height>
         <BitsPerPixel>24</BitsPerPixel>
         <Compression>0</Compression>
         <ImageSize>0</ImageSize>
         <PixelsPerMeter>
             <X>2834</X>
             <Y>2834</Y>
         </PixelsPerMeter>
         <ColorsUsed>0</ColorsUsed>
         <ColorsImportant>0</ColorsImportant>
     </javax_imageio_bmp_1.0>
*/