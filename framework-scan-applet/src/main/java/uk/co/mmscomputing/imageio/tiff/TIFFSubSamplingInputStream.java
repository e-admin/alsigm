package uk.co.mmscomputing.imageio.tiff;

import java.io.*;
import uk.co.mmscomputing.io.IntFilterInputStream;

public class TIFFSubSamplingInputStream extends IntFilterInputStream{

  protected int width,yf,xf,positioning;

  public TIFFSubSamplingInputStream(InputStream in,int width)throws IOException{
    super(in);this.width=width;
  }

  public TIFFSubSamplingInputStream(InputStream in,int width,int yf,int xf,int positioning)throws IOException{
    this(in,width);
    setSubSampling(yf,xf);
    setPositioning(positioning);
  }

  public void setSubSampling(int yf,int xf){this.yf=yf;this.xf=xf;}
  public void setPositioning(int positioning){this.positioning=positioning;}     // todo.

  public int read()throws IOException{
    throw new IOException(getClass().getName()+".read:\t\nInternal Error: Please use read(int[] buf,int off,int len).");
  }

  protected void readDataUnit(int[] buf, int off,int maxy,int maxx)throws IOException{
    int yoff=off;
    int y=0;
    while(y<maxy){
      int x=0;
      while(x<maxx){
        buf[yoff+x]=((in.read()&0x000000FF)<<16);
        x++;
      }
      while(x<xf){
        in.read();x++;
      }
      yoff+=width;y++;
    }
    while(y<yf){
      for(int x=0;x<xf;x++){
        in.read();
      }
      y++;
    }
    int cb=(in.read()&0x000000FF)<<8;
    int cr=in.read()&0x000000FF;

    yoff=off;
    for(y=0;y<maxy;y++){
      for(int x=0;x<maxx;x++){
        buf[yoff+x]|=cb|cr;
      }
      yoff+=width;
    }
  }  

  public int read(int[] buf, int off, int len)throws IOException{
    int maxy=len/width;

    for(int y=0;y<maxy;y+=yf){
      for(int x=0;x<width;x+=xf){
        readDataUnit(
          buf,
          off+y*width+x,
          ((maxy -y)>=yf)?yf:maxy &(yf-1),
          ((width-x)>=xf)?xf:width&(xf-1)
        );
      }
    }
    return len;
  }
}
