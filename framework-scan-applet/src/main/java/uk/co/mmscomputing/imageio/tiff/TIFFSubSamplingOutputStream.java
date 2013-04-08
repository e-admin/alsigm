package uk.co.mmscomputing.imageio.tiff;

import java.io.*;

public class TIFFSubSamplingOutputStream extends FilterOutputStream{

  protected int      w,yf,xf;

  protected byte[]   red,green,blue;
  protected int      y,x,col;
  protected boolean  needToFlush;
  protected int      ww;

  public TIFFSubSamplingOutputStream(OutputStream out,int width,int yf,int xf){
    super(out);

    this.w=width;this.yf=yf;this.xf=xf;

    y=0;x=0;col=0;needToFlush=false;

    ww    = ((width+xf-1)/xf)*xf;

    red   = new byte[ww*yf]; 
    green = new byte[ww*yf]; 
    blue  = new byte[ww*yf]; 
  }

  protected void writeDataUnits()throws IOException{
    for(int x=0;x<ww;x+=xf){
      for(int y=0;y<ww*yf;y+=ww){
        for(int xx=0;xx<xf;xx++){
          out.write(red  [y+x+xx]);
        }
      }
      for(int y=0;y<ww*yf;y+=ww){
        for(int xx=0;xx<xf;xx++){
          out.write(green[y+x+xx]);
        }
      }
      for(int y=0;y<ww*yf;y+=ww){
        for(int xx=0;xx<xf;xx++){
          out.write(blue [y+x+xx]);
        }
      }
    }
    needToFlush=false;
  }

  public void write(int b)throws IOException{
    int off=y*ww+x;
    switch(col++){
    case 0: 
      red  [off]=(byte)b;        
      break;
    case 1: 
      green[off]=(byte)b;        
      break;
    case 2: 
      blue [off]=(byte)b;
      x++;col=0;  
      break;
    }
    if(x==w){y++;x=0;}
    if(y==yf){
      y=0;writeDataUnits();
    }else{
      needToFlush=true;
    }
  }

  public void flush()throws IOException{
    if(needToFlush){
      writeDataUnits();
    }
    y=0;x=0;col=0;
    super.flush();
  }

  public static void main(String[] argv){
    try{
      ByteArrayOutputStream        baos = new ByteArrayOutputStream();
      TIFFSubSamplingOutputStream  os   = new TIFFSubSamplingOutputStream(baos,10,4,4);

      for(int y=0;y<2;y++){
        for(int x=0;x<10;x++){
          os.write((y<<6)+(x<<2));
          os.write((y<<6)+(x<<2)+1);
          os.write((y<<6)+(x<<2)+2);
        }
      }
      os.flush();os.close();

      byte[] buf=baos.toByteArray();
      for(int i=0;i<buf.length;i++){
//        System.out.println("["+i+"]="+Integer.toHexString(buf[i]&0x000000FF));

        int b=buf[i];
        int y=(b>>6)&0x03;
        int x=(b>>2)&0x0F;
        int c=b&0x03;
     
        System.out.println("["+i+"] => y="+y+" x="+x+" c="+c);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}