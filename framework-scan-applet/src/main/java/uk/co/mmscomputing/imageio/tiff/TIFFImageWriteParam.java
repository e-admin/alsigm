package uk.co.mmscomputing.imageio.tiff;

import java.util.*;
import javax.imageio.*;

public class TIFFImageWriteParam extends ImageWriteParam implements TIFFConstants{

  static private String[] photometricinterpretations={
      "WHITEISZERO",
      "BLACKISZERO",
      "RGB",
      "PaletteColor",
      "TransparencyMask",
      "CMYK",
      "YCbCr"
  };

  static private String[] compressiontypes={
    "none",
    "mh",
    "t4mh",
    "t4mr",
    "t6mmr",
    "jpeg"
  };

  private int    photometricinterpretation=WhiteIsZero;
  private float  quality=0.5f;
  private int    subsampling=0x22;
  private double xres=72.0;
  private double yres=72.0;

  public TIFFImageWriteParam(Locale locale){
    super(locale);
    super.compressionTypes=compressiontypes;
    setCompressionMode(MODE_EXPLICIT);
    setCompressionType("t6mmr");
    setController(new TIFFIIOParamController(locale));
  }

  public boolean canWriteCompressed(){return true;}

  public void setPhotometricInterpretation(String pi){
    for(int i=0;i<photometricinterpretations.length;i++){
      if(pi.equals(photometricinterpretations[i])){
        photometricinterpretation=i;
        return;
      }
    }
    throw new IllegalArgumentException(getClass().getName()+".setPhotometricInterpretation\n\tUnknown Photometric Interpretation.");
  }

  public void setCompressionType(String compressionType){ 
    super.setCompressionType(compressionType);
    if(compressionType.equals("mh")){
      photometricinterpretation=WhiteIsZero;         //  fax format always WHITE is zero
    }else if(compressionType.equals("t4mh")){
      photometricinterpretation=WhiteIsZero;         //  fax format always WHITE is zero
    }else if(compressionType.equals("t4mr")){
      photometricinterpretation=WhiteIsZero;         //  fax format always WHITE is zero
    }else if(compressionType.equals("t6mmr")){
      photometricinterpretation=WhiteIsZero;         //  fax format always WHITE is zero
    }else if(compressionType.equals("jpeg")){
    	photometricinterpretation=YCbCr;
    	setSubSampling(0x22);
//      if(photometricinterpretation != RGB){          //  only RGB or YCbCr allowed
//        photometricinterpretation=YCbCr;
//      }
    }
  }

  public void setCompressionQuality(float q){
    if((0<=q)&&(q<=1)){
      quality=q;
    }else{
      throw new IllegalArgumentException(getClass().getName()+".setQuality\n\tInvalid Quality Value. Should be in range [0.0 .. 1.0].");
    }
  }

  public float getCompressionQuality(){return quality;}

/*
  public int getQuality(){return quality;}

  public void setQuality(int q){
    if((0<=q)&&(q<=100)){
      quality=q;
    }else{
      throw new IllegalArgumentException(getClass().getName()+".setQuality\n\tInvalid Quality Value [0..100].");
    }
  }
*/

  public void setSubSampling(int ss){
    if((ss==0x11)||(ss==0x22)){
      subsampling=ss;
    }else{
      throw new IllegalArgumentException(getClass().getName()+".setSubSampling\n\tInvalid Sub Sampling Mode [0x11,0x22].");
    }
  }

  public int getPhotometricInterpretation(){return photometricinterpretation;}
  public int getSubSampling(){return subsampling;}

  public void   setXYResolution(double xres,double yres){ this.xres=xres;this.yres=yres;}
  public double getXResolution(){ return xres;}
  public double getYResolution(){ return yres;}
}

