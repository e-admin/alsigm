package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGDCTInputStream extends JPEGACInputStream{

  protected JPEGDCInputStream dc;
  protected int levelshift,negclamp,posclamp;

  public JPEGDCTInputStream(JPEGHuffmanInputStream dc,JPEGHuffmanInputStream ac,int[] qt,int bps){
    super(ac,qt);
    this.dc=new JPEGDCInputStream(dc);
    levelshift=1<<(bps-1);negclamp=-levelshift;posclamp= levelshift-1;
  }

  public void restart()throws IOException{                 // Call at beginning of restart interval
    dc.restart();
    super.restart();
  }

  public int[] getBuffer(){return buffer;}

  protected void levelShift(){
    int col;
    for(int i=0;i<DCTBlockSize;i++){
      col=buffer[i];
      if(col<negclamp){       col=negclamp;                // clamp to -128 .. 127 or -2048 .. 2047
      }else if(col>posclamp){ col=posclamp;
      }
      col+=levelshift;                                     // level shift;[1] A.3.1; F.1.1.3 p.87
      buffer[i]=col;
    }
  }

  public void fillBuffer()throws IOException{       
    buffer[0]=qt[0]*dc.read();
    super.fillBuffer();
    inverseDCT(buffer);
    levelShift();
    count=0;
  }

  public int read()throws IOException{          
    if(count==DCTBlockSize){
      fillBuffer();
    }
    return buffer[count++];
  }

  protected int matr1[]=new int[DCTBlockSize];             // temp buffer; used in JPEGFastDCTInputStream as well

  public void inverseDCT(int[] buffer)throws IOException{  // The text book DCT algorithm.

    double[] coeff={1.0/Math.sqrt(2.0),1,1,1,1,1,1,1};

    double sum;
    for(int y=0;y<DCTSize;y++){
      for(int x=0;x<DCTSize;x++){
        sum = 0.0;
        for(int v=0;v<DCTSize;v++) {
          for(int u=0;u<DCTSize;u++) {
            sum+=coeff[u]*coeff[v]*buffer[v*DCTSize+u]*Math.cos(((2.0*x+1.0)/16.0)*u*Math.PI)*Math.cos(((2.0*y+1.0)/16.0)*v*Math.PI);
          }
        }
        sum/=4.0;
        matr1[y*DCTSize+x]=((int)Math.round(sum));  
      }
    }
    System.arraycopy(matr1,0,buffer,0,DCTBlockSize);
  }
}

// [1]'JPEG' : ISO/IEC IS 10918-1
//             ITU-T Recommendation T.81
// http://www.w3.org/Graphics/JPEG/itu-t81.pdf
