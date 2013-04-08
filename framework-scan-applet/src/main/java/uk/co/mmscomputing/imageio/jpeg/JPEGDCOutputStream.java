package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGDCOutputStream extends OutputStream{

  private JPEGHuffmanOutputStream out;
  private int PRED;

  public JPEGDCOutputStream(JPEGHuffmanOutputStream out){
    this.out=out;
    PRED=0;
  }

  public void write(int b)throws IOException{     // [1] p.88
    int DIFF=b-PRED;
    PRED=b;

    int S=0;
    if(DIFF>0){
      while(((-1<<S)&DIFF)!=0){S++;}              // figure out magnitude SSSS
    }else{
      DIFF=-DIFF;while(((-1<<S)&DIFF)!=0){S++;}DIFF=-DIFF-1;
    }
    out.write(S);
    out.writeBits(DIFF,S);
  }
}