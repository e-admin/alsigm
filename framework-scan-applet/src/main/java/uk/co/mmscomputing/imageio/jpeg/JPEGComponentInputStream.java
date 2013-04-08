package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGComponentInputStream extends InputStream{

  protected int   id,shift=0;

  protected int   height,width;
  protected int   bps,Hf,Vf,HMax,VMax,Yf,Xf;
  protected int[] qt;

  protected JPEGDCTInputStream in;

  public JPEGComponentInputStream(int id){this.id=id;}

  public int  getId(){return id;}

  public void setShift(int shift)           {this.shift=shift;}
  public void setBitsPerSample(int bps)     {this.bps=bps;}
  public void setDimensions(int h,int w)    {height=h;width=w;}
  public void setSamplingRate(int Vf,int Hf){this.Vf=Vf;this.Hf=Hf;}
  public void setMaxSamplingRate(int VMax,int HMax){
    this.VMax=VMax;
    this.HMax=HMax;
    Xf=HMax/Hf;
    Yf=VMax/Vf;
  }
  public void setQuantizationTable(int[] qt){this.qt=qt;}

  public void setHuffmanTables(JPEGHuffmanInputStream dc, JPEGHuffmanInputStream ac){
    in=new JPEGFastDCTInputStream(dc,ac,qt,bps);
//    in=new JPEGDCTInputStream(dc,ac,qt,bps);
  }

  public void restart()throws IOException{in.restart();}

  public int read()throws IOException{          
    return in.read();
  }

  // YCbCr

  protected void copyPixel(int[] buf,int off,int maxy,int maxx,int b){
    for(int y=0;y<maxy;y++){
      for(int x=0;x<maxx;x++){
//        buf[off+x]=(buf[off+x]<<8)|b;
        buf[off+x]=buf[off+x]|(b<<shift);
      }
      off+=width;
    }
  }

  protected void copyDataUnit(int[] buf,int off,int maxy,int maxx,int[] buffer)throws IOException{
    int count=0;
    int yoff=off;
    for(int y=0;y<maxy;y+=Yf){
      int x=0;
      while(x<maxx){
        copyPixel(buf,yoff+x,
          ((maxy-y)>=Yf)?Yf:maxy&(Yf-1),
          ((maxx-x)>=Xf)?Xf:maxx&(Xf-1),
          buffer[count++]
        );
        x+=Xf;
      }
      while(x<8*Xf){count++;x+=Xf;}
      yoff+=width*Yf;
    }
  }

  public void read(int[] buf,int off,int maxy,int maxx)throws IOException{
    int yoff=off,yl=0;
    for(int y=0;y<Vf;y++){
      int xoff=yoff,xl=0;
      for(int x=0;x<Hf;x++){
        in.fillBuffer();
        if((yl<maxy)&&(xl<maxx)){
          copyDataUnit(buf,xoff,
            ((maxy-yl)>=8*Yf)?8*Yf:maxy&(8*Yf-1),
            ((maxx-xl)>=8*Xf)?8*Xf:maxx&(8*Xf-1),
            in.getBuffer()
          );
        }
        xoff+=8*Xf;xl+=8*Xf;
      }
      yoff+=width*8*Yf;yl+=8*Yf;
    }
  }

  // grayscale

  protected void copyPixel(byte[] buf,int off,int maxy,int maxx,int b){
    for(int y=0;y<maxy;y++){
      for(int x=0;x<maxx;x++){
        buf[off+x]=(byte)b;
      }
      off+=width;
    }
  }

  protected void copyDataUnit(byte[] buf,int off,int maxy,int maxx,int[] buffer)throws IOException{
    int count=0;
    int yoff=off;
    for(int y=0;y<maxy;y+=Yf){
      int x=0;
      while(x<maxx){
        copyPixel(buf,yoff+x,
          ((maxy-y)>=Yf)?Yf:maxy&(Yf-1),
          ((maxx-x)>=Xf)?Xf:maxx&(Xf-1),
          buffer[count++]
        );
        x+=Xf;
      }
      while(x<8*Xf){count++;x+=Xf;}
      yoff+=width*Yf;
    }
  }

  public void read(byte[] buf,int off,int maxy,int maxx)throws IOException{
    int yoff=off,yl=0;
    for(int y=0;y<Vf;y++){
      int xoff=yoff,xl=0;
      for(int x=0;x<Hf;x++){
        in.fillBuffer();
        if((yl<maxy)&&(xl<maxx)){
          copyDataUnit(buf,xoff,
            ((maxy-yl)>=8*Yf)?8*Yf:maxy&(8*Yf-1),
            ((maxx-xl)>=8*Xf)?8*Xf:maxx&(8*Xf-1),
            in.getBuffer()
          );
        }
        xoff+=8*Xf;xl+=8*Xf;
      }
      yoff+=width*8*Yf;yl+=8*Yf;
    }
  }

  public String toString(){
    String s="";
    s+="id  ="+id+"\n";
    s+="bps ="+bps+"\n";
    s+="Hf  ="+Hf+"\n";
    s+="Vf  ="+Vf+"\n";
    s+="HMax  ="+HMax+"\n";
    s+="VMax  ="+VMax+"\n";
    s+="Yf  ="+Yf+"\n";
    s+="Xf  ="+Xf+"\n";
    return s;
  }
}