package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGDCTOutputStream extends OutputStream implements JPEGConstants{

  static private final int  levelshift=128;

  protected int          count;
  protected float[]      buffer=new float[DCTBlockSize];

  protected int[]        qt;
  protected OutputStream dc;
  protected OutputStream ac;

  public JPEGDCTOutputStream(int[] qt)throws IOException{
    this.qt=qt;this.dc=null;this.ac=null;count=0;
  }

  public void setHuffmanOutputStreams(JPEGHuffmanOutputStream dc,JPEGHuffmanOutputStream ac)throws IOException{
    this.dc=new JPEGDCOutputStream(dc);
    this.ac=new JPEGACOutputStream(ac);
  }

  public void write(int b)throws IOException{
    buffer[count++]=b-levelshift;
    if(count==DCTBlockSize){
      forwardDCT(buffer);
      dc.write((int)Math.round(buffer[IZigZagTable[0]]/qt[0]));
      for(int i=1;i<DCTBlockSize;i++){
        ac.write((int)Math.round(buffer[IZigZagTable[i]]/qt[i]));
      }
      count=0;
    }
  }

/*
  protected void forwardDCT(int[] buffer)throws IOException{   // The text book DCT algorithm.

    int matr1[]=new int[DCTBlockSize];                      // temp buffer

    double[] coeff={1.0/Math.sqrt(2.0),1,1,1,1,1,1,1};

    double sum;
    for(int v=0;v<DCTSize;v++) {
        for(int u=0;u<DCTSize;u++) {
        sum = 0.0;
        for(int y=0;y<DCTSize;y++){
          for(int x=0;x<DCTSize;x++){
            sum+=buffer[y*DCTSize+x]*Math.cos(((2.0*x+1.0)/16.0)*u*Math.PI)*Math.cos(((2.0*y+1.0)/16.0)*v*Math.PI);
          }
        }
        sum*=(coeff[u]*coeff[v])/4.0;
        matr1[v*DCTSize+u]=((int)Math.round(sum));  
      }
    }
    System.arraycopy(matr1,0,buffer,0,DCTBlockSize);
  }
*/

  // http://forum.java.sun.com/thread.jspa?forumID=426&start=15&threadID=482368 [last accessed 2005-12-14]
  // based on http://www.ijg.org/ 'Independent JPEG Group' [last accessed 2005-12-14]

  private static final float F0 = (float)(1.0 / Math.sqrt(2));                    // 0.7071067811865475
  private static final float F1 = (float)Math.cos(1 * Math.PI / 16) / 2;        // 0.4903926402016152
  private static final float F2 = (float)Math.cos(2 * Math.PI / 16) / 2;        // 0.46193976625564337
  private static final float F3 = (float)Math.cos(3 * Math.PI / 16) / 2;        // 0.4157348061512726
  private static final float F4 = (float)Math.cos(4 * Math.PI / 16) / 2;        // 0.3535533905932738
  private static final float F5 = (float)Math.cos(5 * Math.PI / 16) / 2;        // 0.27778511650980114
  private static final float F6 = (float)Math.cos(6 * Math.PI / 16) / 2;        // 0.19134171618254492
  private static final float F7 = (float)Math.cos(7 * Math.PI / 16) / 2;        // 0.09754516100806417
  private static final float D71 = F7 - F1;                              // -0.39284747919355106
  private static final float D35 = F3 - F5;                              // 0.13794968964147147
  private static final float D62 = F6 - F2;                              // -0.27059805007309845
  private static final float S71 = F7 + F1;                              // 0.5879378012096794
  private static final float S35 = F3 + F5;                              // 0.6935199226610738
  private static final float S62 = F6 + F2;                              // 0.6532814824381883

  protected void forwardDCT(float[] buffer)throws IOException{
    float temp;
    float a0, a1, a2, a3, a4, a5, a6, a7;
    float b0, b1, b2, b3, b4, b5, b6, b7;
 
    for(int y=0;y<64;y+=8){
      b0 = buffer[y+0] + buffer[y+7];
      b7 = buffer[y+0] - buffer[y+7];
      b1 = buffer[y+1] + buffer[y+6];
      b6 = buffer[y+1] - buffer[y+6];
      b2 = buffer[y+2] + buffer[y+5];
      b5 = buffer[y+2] - buffer[y+5];
      b3 = buffer[y+3] + buffer[y+4];
      b4 = buffer[y+3] - buffer[y+4];
 
      a0 = b0 + b3;
      a1 = b1 + b2;
      a2 = b1 - b2;
      a3 = b0 - b3;

      a4 = b4;
      a5 = (b6 - b5) * F0;
      a6 = (b6 + b5) * F0;
      a7 = b7;

      buffer[y+0] =((a0 + a1) * F4);
      buffer[y+4] =((a0 - a1) * F4);
 
      temp = (a3 + a2) * F6;
      buffer[y+2] =(temp - a3 * D62);
      buffer[y+6] =(temp - a2 * S62);
 
      b4 = a4 + a5;
      b7 = a7 + a6;
      b5 = a4 - a5;
      b6 = a7 - a6;
 
      temp = (b7 + b4) * F7;
      buffer[y+1] =(temp - b7 * D71);
      buffer[y+7] =(temp - b4 * S71);
 
      temp = (b6 + b5) * F3;
      buffer[y+5] =(temp - b6 * D35);
      buffer[y+3] =(temp - b5 * S35);
    }
    for(int x=0;x<8;x++){
      b0 = buffer[ 0+x] + buffer[56+x];
      b7 = buffer[ 0+x] - buffer[56+x];
      b1 = buffer[ 8+x] + buffer[48+x];
      b6 = buffer[ 8+x] - buffer[48+x];
      b2 = buffer[16+x] + buffer[40+x];
      b5 = buffer[16+x] - buffer[40+x];
      b3 = buffer[24+x] + buffer[32+x];
      b4 = buffer[24+x] - buffer[32+x];
 
      a0 = b0 + b3;
      a1 = b1 + b2;
      a2 = b1 - b2;
      a3 = b0 - b3;
      a4 = b4;
      a5 = (b6 - b5) * F0;
      a6 = (b6 + b5) * F0;
      a7 = b7;
      buffer[ 0+x] =((a0 + a1) * F4);
      buffer[32+x] =((a0 - a1) * F4);
 
      temp = (a3 + a2) * F6;
      buffer[16+x] =(temp - a3 * D62);
      buffer[48+x] =(temp - a2 * S62);
 
      b4 = a4 + a5;
      b7 = a7 + a6;
      b5 = a4 - a5;
      b6 = a7 - a6;
 
      temp = (b7 + b4) * F7;
      buffer[ 8+x] =(temp - b7 * D71);
      buffer[56+x] =(temp - b4 * S71);
 
      temp = (b6 + b5) * F3;
      buffer[40+x] =(temp - b6 * D35);
      buffer[24+x] =(temp - b5 * S35);
    }
  }
/*
  protected void forwardDCTi(int[] buffer)throws IOException{
    double temp;
    double a0, a1, a2, a3, a4, a5, a6, a7;
    double b0, b1, b2, b3, b4, b5, b6, b7;
 
    for(int y=0;y<64;y+=8){
      b0 = buffer[y+0] + buffer[y+7];
      b7 = buffer[y+0] - buffer[y+7];
      b1 = buffer[y+1] + buffer[y+6];
      b6 = buffer[y+1] - buffer[y+6];
      b2 = buffer[y+2] + buffer[y+5];
      b5 = buffer[y+2] - buffer[y+5];
      b3 = buffer[y+3] + buffer[y+4];
      b4 = buffer[y+3] - buffer[y+4];
 
      a0 = b0 + b3;
      a1 = b1 + b2;
      a2 = b1 - b2;
      a3 = b0 - b3;

      a4 = b4;
      a5 = (b6 - b5) * F0;
      a6 = (b6 + b5) * F0;
      a7 = b7;

      buffer[y+0] =(int)((a0 + a1) * F4);
      buffer[y+4] =(int)((a0 - a1) * F4);
 
      temp = (a3 + a2) * F6;
      buffer[y+2] =(int)(temp - a3 * D62);
      buffer[y+6] =(int)(temp - a2 * S62);
 
      b4 = a4 + a5;
      b7 = a7 + a6;
      b5 = a4 - a5;
      b6 = a7 - a6;
 
      temp = (b7 + b4) * F7;
      buffer[y+1] =(int)(temp - b7 * D71);
      buffer[y+7] =(int)(temp - b4 * S71);
 
      temp = (b6 + b5) * F3;
      buffer[y+5] =(int)(temp - b6 * D35);
      buffer[y+3] =(int)(temp - b5 * S35);
    }
    for(int x=0;x<8;x++){
      b0 = buffer[ 0+x] + buffer[56+x];
      b7 = buffer[ 0+x] - buffer[56+x];
      b1 = buffer[ 8+x] + buffer[48+x];
      b6 = buffer[ 8+x] - buffer[48+x];
      b2 = buffer[16+x] + buffer[40+x];
      b5 = buffer[16+x] - buffer[40+x];
      b3 = buffer[24+x] + buffer[32+x];
      b4 = buffer[24+x] - buffer[32+x];
 
      a0 = b0 + b3;
      a1 = b1 + b2;
      a2 = b1 - b2;
      a3 = b0 - b3;
      a4 = b4;
      a5 = (b6 - b5) * F0;
      a6 = (b6 + b5) * F0;
      a7 = b7;
      buffer[ 0+x] =(int)((a0 + a1) * F4);
      buffer[32+x] =(int)((a0 - a1) * F4);
 
      temp = (a3 + a2) * F6;
      buffer[16+x] =(int)(temp - a3 * D62);
      buffer[48+x] =(int)(temp - a2 * S62);
 
      b4 = a4 + a5;
      b7 = a7 + a6;
      b5 = a4 - a5;
      b6 = a7 - a6;
 
      temp = (b7 + b4) * F7;
      buffer[ 8+x] =(int)(temp - b7 * D71);
      buffer[56+x] =(int)(temp - b4 * S71);
 
      temp = (b6 + b5) * F3;
      buffer[40+x] =(int)(temp - b6 * D35);
      buffer[24+x] =(int)(temp - b5 * S35);
    }
  }
*/
}
