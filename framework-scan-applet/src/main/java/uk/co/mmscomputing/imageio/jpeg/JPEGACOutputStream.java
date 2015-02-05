package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGACOutputStream extends OutputStream implements JPEGConstants{

  private JPEGHuffmanOutputStream out;
  private int K,R;

  public JPEGACOutputStream(JPEGHuffmanOutputStream out){
    this.out=out;
    K=1;R=0;
  }

  public void write(int b)throws IOException{   // [1] p.92 encode_ac_coefficients
    if(b==0){                                   // if zero coefficient
      R++;                                      // increase run length
    }else{                                      // else
      while(R>=16){                             // while 16 or more zero coefficients
        out.write(0xF0);                        // write ZRL (Zero Run Length) code
        R-=16;
      }
      int S=1;                                  // [1] p.93 encode_R,ZZ(K)
      if(b>0){
        while(((-1<<S)&b)!=0){S++;}             // figure out magnitude SSSS
      }else{
        b=-b;while(((-1<<S)&b)!=0){S++;}b=-b-1;
      }
      S&=0x0F;
      out.write((R<<4)|S);                      // write run length code and size code
      out.writeBits(b,S);                       // write S amplitude bits
      R=0;
    }
    K++;
    if(K==DCTBlockSize){
      if(R>0){out.write(0x00);}                 // write EOB (End Of Block)
      K=1;R=0;
    }
  }
}