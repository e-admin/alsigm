package uk.co.mmscomputing.device.twain;

import java.util.*;
import java.awt.*;
import java.awt.image.*;

import uk.co.mmscomputing.imageio.bmp.BMPMetadata;

public class TwainBufferedImage extends BufferedImage{

  static private String[]    propertyNames = new String[]{"iiometadata"};
         private BMPMetadata md            = new BMPMetadata();

  public TwainBufferedImage(int width, int height, int type){
    super(width,height,type);                                      // 24 bit

    int bitsPerPixel=(type==TYPE_BYTE_GRAY)?8:24;

    md.setWidth(width);
    md.setHeight(height);
    md.setBitsPerPixel(bitsPerPixel);
    md.setCompression(0);
    md.setImageSize((((width*bitsPerPixel+31)>>5)<<2)*height);
    md.setXPixelsPerMeter(2953);                                   // 75 dpi
    md.setYPixelsPerMeter(2953);
  }

  public TwainBufferedImage(int width, int height, int type,IndexColorModel cm){
    super(width,height,type,cm);                                   // 1,4,8 bit

    md.setWidth(width);
    md.setHeight(height);

    int bitsPerPixel=8;
    switch(cm.getMapSize()){
    case 2:   bitsPerPixel=1; break;
    case 16:  bitsPerPixel=4; break;
//    case 256: bitsPerPixel=8; break;
    }
    md.setBitsPerPixel(bitsPerPixel);
    md.setCompression(0);
    md.setXPixelsPerMeter(2953);                                   // 75 dpi
    md.setYPixelsPerMeter(2953);
    md.setIndexColorModel(cm);
    md.setImageSize((((width*bitsPerPixel+31)>>5)<<2)*height);
  }

  public byte[] getBuffer(){                                       // 1,4,8,24 bit
    Raster          r  = getRaster();
    DataBufferByte  db = (DataBufferByte)r.getDataBuffer();    
    return          db.getData();
  }

  public void setXPixelsPerMeter(int v){
    md.setXPixelsPerMeter(v);
  }

  public void setYPixelsPerMeter(int v){
    md.setYPixelsPerMeter(v);
  }

  public void setPixelsPerMeter(int x,int y){
    setXPixelsPerMeter(x);
    setYPixelsPerMeter(y);
  }

  public Object getProperty(String name){
    if(name.equals("iiometadata")){return md;}
    if(name.equals("resolution")){
      return ""+md.getXDotsPerInch();
    }
    return Image.UndefinedProperty;
  }

  public String[] getPropertyNames(){
    return propertyNames;
  }
}



