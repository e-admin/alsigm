package uk.co.mmscomputing.imageio.tiff;

import java.io.*;
import uk.co.mmscomputing.io.IntFilterInputStream;

public class TIFFYCbCrInputStream extends IntFilterInputStream{

  protected double LumaRed,LumaGreen,LumaBlue;
  protected double RfBY,RfBCb,RfBCr,RfWY,RfWCb,RfWCr;

  private   double RCr,BCb,GY,GB,GR;

  public TIFFYCbCrInputStream(IntFilterInputStream in)throws IOException{
    super(in);
  }

  public void setColourCoefficients(double LumaRed,double LumaGreen,double LumaBlue){
    this.LumaRed=LumaRed;this.LumaGreen=LumaGreen;this.LumaBlue=LumaBlue;

    RCr=2.0-2.0*LumaRed;              // R = Cr * ( 2 - 2 * LumaRed ) + Y
    BCb=2.0-2.0*LumaBlue;             // B = Cb * ( 2 - 2 * LumaBlue ) + Y
    GY =1.0/LumaGreen;                // G = ( Y - LumaBlue * B - LumaRed * R ) / LumaGreen
    GB =-LumaBlue/LumaGreen;
    GR =-LumaRed /LumaGreen;
  }

  public double getLumaRed(){return LumaRed;}
  public double getLumaGreen(){return LumaGreen;}
  public double getLumaBlue(){return LumaBlue;}

  public void setRfBWY(double black,double white){RfBY=black;RfWY=white;}
  public void setRfBWCb(double black,double white){RfBCb=black;RfWCb=white;}
  public void setRfBWCr(double black,double white){RfBCr=black;RfWCr=white;}

  public int read()throws IOException{
    throw new IOException(getClass().getName()+".read:\t\nInternal Error: Please use read(int[] buf,int off,int len).");
  }

  protected int convert(int YCbCr)throws IOException{
    double Y,Cb,Cr;

    Y =((YCbCr>>16)&0x000000FF);
    Cb=((YCbCr>> 8)&0x000000FF);
    Cr=((YCbCr    )&0x000000FF);

    // FullRangeValue = (code - ReferenceBlack) * CodingRange / (ReferenceWhite - ReferenceBlack);

    Y =(Y -RfBY )*255.0/(RfWY -RfBY );
    Cb=(Cb-RfBCb)*127.0/(RfWCb-RfBCb);
    Cr=(Cr-RfBCr)*127.0/(RfWCr-RfBCr);

    // R = Cr * ( 2 - 2 * LumaRed  ) + Y
    // B = Cb * ( 2 - 2 * LumaBlue ) + Y
    // G = ( Y - LumaBlue * B - LumaRed * R ) / LumaGreen

    int R =(int)Math.round(Y + RCr * Cr);             if(R<0){R=0;}else if(R>255){R=255;}
    int B =(int)Math.round(Y + BCb * Cb);             if(B<0){B=0;}else if(B>255){B=255;}
    int G =(int)Math.round(Y * GY + GB * B + GR * R); if(G<0){G=0;}else if(G>255){G=255;}

    return (R<<16)|(G<<8)|B;
  }

  public int read(int[] buf, int off, int len)throws IOException{
    len=((IntFilterInputStream)in).read(buf,off,len);
    for(int i=0;i<len;i++){
      buf[off+i]=convert(buf[off+i]);
    }
    return len;
  }
}

// rgb2ycbcr testRGB.tif testYCbCr.tif

