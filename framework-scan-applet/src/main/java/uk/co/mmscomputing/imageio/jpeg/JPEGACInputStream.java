package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGACInputStream extends InputStream implements JPEGConstants{

  protected JPEGHuffmanInputStream in;
  protected int[]    qt;                            // quantization table

  protected int      count;
  protected int[]    buffer=new int[DCTBlockSize];

  public JPEGACInputStream(JPEGHuffmanInputStream in,int[] qt){
    this.in=in;
    this.qt=qt;
    count=DCTBlockSize;
  }

  public void restart()throws IOException{          // Call at beginning of restart interval
    in.restart();
    count=DCTBlockSize;                             // count should be DCTBlockSize !
  }

  public void fillBuffer()throws IOException{       
    int K=1,RS,SSSS,RRRR;                           // [1] p.106 decode
    for(int i=1;i<DCTBlockSize;i++){buffer[i]=0;}   // zero ZZ

    while(K<DCTBlockSize){
      RS=in.read();
      if(RS==-1){throw new IOException(getClass().getName()+"fillBuffer:\n\tUnexpected end of file.");}
      SSSS=RS&0x000F;                               // run length of zero ac coefficients
      RRRR=(RS>>4)&0x000F;                          // bit size of next non zero ac coefficient
      if(SSSS==0){
        if(RRRR!=15){break;}                        //  0/0 => End of block (EOB)
        K+=16;                                      // 15/0 => 16 0s
      }else{                                        //  R/S
        K+=RRRR;                                    // skip over RRRR zero ac coefficients
        buffer[IZigZagTable[K]]=qt[K]*in.readBits(SSSS);// read non zero ac coefficient; [1] p.107 decodeZZ
        K++;
      }
    }
  }

  public int read()throws IOException{          
    if(count==DCTBlockSize){
      fillBuffer();
      count=1;
    }
    return buffer[count++];
  }
}