package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

public class TIFFYCbCrOutputStream extends FilterOutputStream implements TIFFConstants{

  protected int    w,yf,xf,positioning;
  protected double LumaRed,LumaGreen,LumaBlue;
  protected double RfBY,RfBCb,RfBCr,RfWY,RfWCb,RfWCr;
  protected double RCr,BCb;

  protected byte[] red,green,blue;
  protected int    col,off,max;
  protected boolean  needToFlush;

  public TIFFYCbCrOutputStream(OutputStream out,int width,int yf,int xf){
    super(out);this.w=width;this.yf=yf;this.xf=xf;
    col=0;
    off=0;
    max=yf*xf;
    red   = new byte[max]; 
    green = new byte[max]; 
    blue  = new byte[max]; 
    needToFlush=false;
  }

  public void setPositioning(int positioning){
    this.positioning=positioning;     // todo.
  }

  public void setColourCoefficients(double LumaRed,double LumaGreen,double LumaBlue){
    this.LumaRed=LumaRed;this.LumaGreen=LumaGreen;this.LumaBlue=LumaBlue;

    RCr=1/(2.0-2.0*LumaRed);
    BCb=1/(2.0-2.0*LumaBlue);
  }

  public void setRfBWY (double black,double white){RfBY =black;RfWY =white;}
  public void setRfBWCb(double black,double white){RfBCb=black;RfWCb=white;}
  public void setRfBWCr(double black,double white){RfBCr=black;RfWCr=white;}

  protected void writeDataUnit()throws IOException{
    double cb=0,cr=0;
    for(int i=0;i<max;i++){
      double R=(red[i]  & 0x00FF)/255.0;
      double G=(green[i]& 0x00FF)/255.0;
      double B=(blue[i] & 0x00FF)/255.0;

      double Y = LumaRed*R+LumaGreen*G+LumaBlue*B;
      double Cb=(B-Y)*BCb*255.0;    //if(Cb>255){Cb=255;}
      double Cr=(R-Y)*RCr*255.0;    //if(Cr>255){Cr=255;}
             Y*=255.0;              //if(Y>255) {Y=255;}

    // code = (FullRangeValue * (ReferenceWhite - ReferenceBlack) / CodingRange) + ReferenceBlack;

      Y = ( Y*(RfWY -RfBY )/255.0)+RfBY;
      Cb= (Cb*(RfWCb-RfBCb)/127.0)+RfBCb;
      Cr= (Cr*(RfWCr-RfBCr)/127.0)+RfBCr;

//System.err.println("Y="+Y+" Cb="+Cb+" Cr="+Cr);

      if(Y>255.0) {Y=255.0;}out.write((int)Y);
      cb+=(Cb>255.0)?255.0:Cb;
      cr+=(Cr>255.0)?255.0:Cr;      
    }
    out.write((int)(cb/max));
    out.write((int)(cr/max));
    
    needToFlush=false;
  }

  public void write(int b)throws IOException{
    switch(col){                                 // per dataunit:
    case 0: red  [off++]=(byte)b; break;         // all red first
    case 1: green[off++]=(byte)b; break;         // all green second
    case 2: blue [off++]=(byte)b; break;         // all blue last
    }
    if(off==max){off=0;col++;}                   // next colour
    if(col==3){                                  // got all three colour arrays
      col=0;writeDataUnit();                     // write data unit
    }else{
      needToFlush=true;
    }
  }

  public void write1(int c)throws IOException{
    double R=((c>>16)&0x00FF)/255.0;
    double G=((c>> 8)&0x00FF)/255.0;
    double B=((c    )&0x00FF)/255.0;

    double Y = LumaRed*R+LumaGreen*G+LumaBlue*B;
    double Cb=((B-Y)/(2.0-2.0*LumaBlue))*255.0;   //if(Cb>255){Cb=255;}
    double Cr=((R-Y)/(2.0-2.0*LumaRed))*255.0;    //if(Cr>255){Cr=255;}
           Y*=255.0;                              //if(Y>255) {Y=255;}

    // code = (FullRangeValue * (ReferenceWhite - ReferenceBlack) / CodingRange) + ReferenceBlack;

    Y = ( Y*(RfWY -RfBY )/255)+RfBY;
    Cb= (Cb*(RfWCb-RfBCb)/127)+RfBCb;
    Cr= (Cr*(RfWCr-RfBCr)/127)+RfBCr;

    out.write((int)Y);
    out.write((int)Cb);
    out.write((int)Cr);
  }


  public void flush()throws IOException{

// System.err.println(getClass().getName()+".flush()");

// todo: repeat last value

    if(needToFlush){
      writeDataUnit();
    }
    col=0;off=0;
    super.flush();
  }
}

