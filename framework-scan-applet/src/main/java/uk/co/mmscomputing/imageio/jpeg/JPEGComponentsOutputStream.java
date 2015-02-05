package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGComponentsOutputStream extends OutputStream implements JPEGConstants{

  protected /*JPEGDCT*/OutputStream[] outs;
  protected byte[][] buffer;
  protected int[]    H,V;
  protected int      HMax,VMax;
  protected int      xMCUStep,yMCUStep;

  protected int      width,ww;
  protected int      y,x,comp,spp;

  public JPEGComponentsOutputStream(/*JPEGDCT*/OutputStream[] outs,int[] HV,int width)throws IOException{
    this.outs=outs;
    this.width=width;

    y=0;x=0;comp=0;
    spp=HV.length;

    H     = new int[spp];
    V     = new int[spp];
    HMax  = 0;
    VMax  = 0;

    for(int c=0;c<spp;c++){
      H[c]=(HV[c]>>4)&0x0F;
      V[c]= HV[c]    &0x0F;
      if(HMax<H[c]){HMax=H[c];}
      if(VMax<V[c]){VMax=V[c];}
    }    

    xMCUStep=HMax*8;
    yMCUStep=VMax*8;

    ww=((width+xMCUStep-1)/xMCUStep)*xMCUStep;
    buffer = new byte[spp][ww*yMCUStep];
  }

  protected void writeDataUnit(OutputStream out,byte[] buffer,int vv,int hh,int off)throws IOException{
    int yStep=VMax-vv+1;
    int xStep=HMax-hh+1;

    for(int y=0;y<DCTSize*yStep;y+=yStep){
      for(int x=0;x<DCTSize*xStep;x+=xStep){
        int c=0;                                  // subsampling
        for(int yy=0;yy<yStep;yy++){
          for(int xx=0;xx<xStep;xx++){
            c+=buffer[off+(y+yy)*ww+(x+xx)]&0x000000FF;
          }
        }
        c/=yStep*xStep;
        out.write(c);
      }
    }
  }
/*
  protected void writeDataUnit(OutputStream out,byte[] buffer,int vv,int hh,int off)throws IOException{
    int yStep=VMax-vv+1;
    int xStep=HMax-hh+1;

    for(int y=0;y<DCTSize*yStep;y+=yStep){
      for(int x=0;x<DCTSize*xStep;x+=xStep){
        int c=0;                                  // subsampling
        for(int yy=0;yy<yStep;yy++){
          for(int xx=0;xx<xStep;xx++){
            c+=buffer[off+(y+yy)*ww+(x+xx)]&0x000000FF;
          }
        }
        c/=yStep*xStep;
        out.write(c);
      }
    }
  }
*/
  protected void writeMCU(OutputStream out,byte[] buffer,int vv,int hh,int off)throws IOException{
    for(int v=0;v<vv;v++){
      int yy=v*DCTSize*ww;                        // row
      for(int h=0;h<hh;h++){
        int xx=off+h*DCTSize;                     // col
        writeDataUnit(out,buffer,vv,hh,yy+xx);
      }
    }
  }

  protected void writeMCUs()throws IOException{
    for(int x=0;x<ww;x+=xMCUStep){
      for(int c=0;c<spp;c++){
        writeMCU(outs[c],buffer[c],V[c],H[c],x);
      }
    }
  }

  public void write(int b)throws IOException{
    buffer[comp++][y*ww+x]=(byte)b;               // fill buffer
    if(comp==spp){comp=0;x++;}                    // next pixel
    if(x==width){                                 // next line
      if(x<ww){                                   // fill rest of line with end of line colour
        for(int c=0;c<spp;c++){
          byte[] data=buffer[c];
          byte   val =data[y*ww+width-1];
          for(int i=width;i<ww;i++){data[y*ww+i]=val;}
        }
      }
      x=0;y++;
    }
    if(y==yMCUStep){                              // buffer full
      y=0;writeMCUs();
    }
  }

  public void flush()throws IOException{
    if(y>0){
      for(int c=0;c<spp;c++){                     // fill buffer with copy of last line
        byte[] data=buffer[c];
        for(int k=y;k<yMCUStep;k++){
          for(int i=0;i<ww;i++){
            data[k*ww+i]=data[(y-1)*ww+i];
          }
        }
      }
      writeMCUs();
    }
    y=0;x=0;comp=0;
  }
}