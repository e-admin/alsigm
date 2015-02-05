package uk.co.mmscomputing.imageio.gif;

import java.io.*;

public class GIFLZWOutputStream extends OutputStream{

  final static private int MAXCODE = 4096;
  final static private int PREFIXTABLE = 16;

  private int[]   prefix=new int[MAXCODE];
  private int[]   suffix=new int[MAXCODE];
  private int[][] prefixtab=new int[MAXCODE][PREFIXTABLE];

  private int curPrefix, curSuffix;

  private int dataSize;                 //  'seed' for decoder, count of first codes
  private int clearCode;                //  decoder reset code
  private int eoiCode;                  //  end of information code
  private int availCode;                //  next available code
  private int codeSize;

  private GIFBitOutputStream out;

  public GIFLZWOutputStream(OutputStream os, int ds)throws IOException{
    out=new GIFBitOutputStream(os);
    dataSize=ds;
    resetTables();
    out.write(dataSize);                  //  write data size 'seed'

    curPrefix=MAXCODE;                    // set to impossible value
    out.writeBits(clearCode,codeSize);    // write clear code to reset decoder.
  }

  private void resetTables(){
    codeSize  = dataSize+1;
    clearCode = 1<<dataSize;
    eoiCode   = clearCode+1;              //  end of image code {static}
    availCode = clearCode+2;              //  next available code
    for(int i=0; i<availCode; i++){
      prefix[i]=MAXCODE;
      suffix[i]=i;                        //  initialize codes
    }  
    for(int i=0;i<MAXCODE;i++){
      for(int j=0;j<PREFIXTABLE;j++){
        prefixtab[i][j]=MAXCODE;
      }
    }
  }  
  
  public void write(byte[] b)throws IOException{
    int len=b.length;
    for(int i=0;i<len;i++){
      write(b[i]&0x00FF);
    }
  }

  public void write(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      write(b[off+i]&0x00FF);
    }
  }

  public void write(int code)throws IOException{
    if(code==clearCode){                  // reset variables
      curPrefix=MAXCODE;                  // set to impossible value
      out.writeBits(code,codeSize);       // write clear code
      return;
    }
    curSuffix=code;                       // normal code

    boolean ptfull   =true;
    boolean foundCode=false;
    int     found    =MAXCODE;

    if(curPrefix<MAXCODE){
      int[] pt=prefixtab[curPrefix];
      for(int i=0;i<PREFIXTABLE;i++){     // search in curPrefix part of the prefix table
        found=pt[i];
        if(found==MAXCODE){               // code with this prefix does not exist
          ptfull=false;                   // but found free entry
          break;	
        }                                 // else found code with current prefix
        if(suffix[found]==curSuffix){     // if suffix is correct as well
          foundCode=true;                 // we found code
          break;
        }
      }
    }

    if((curPrefix==MAXCODE)               // prefix does not exist but prefix table full
     ||(!foundCode && ptfull)             // prefix not found or (12 int) prefix table full
    ){
      found=MAXCODE;
      int i=(curPrefix==MAXCODE)?0:clearCode+2;
      while(i<availCode){
        if((prefix[i]==curPrefix)&&(suffix[i]==curSuffix)){
          found=i;
          break;
        }
        i++;
      }
    }
    if(found!=MAXCODE){		
      curPrefix=found;                    // found code, remember
      return;
    }
    if(availCode==MAXCODE){               // code table full ?
      out.writeBits(curPrefix,codeSize);  // write last code
      out.writeBits(clearCode,codeSize);  // reset Decoder
      resetTables();                      // reset code tables
      curPrefix=code;
    }else{
      prefix[availCode]=curPrefix;        // write code in table
      suffix[availCode]=curSuffix;

      int[] pt=prefixtab[curPrefix];
      for(int i=0;i<PREFIXTABLE;i++){
        if(pt[i]==MAXCODE){
          pt[i]=availCode;
          break;	
        }
      }
      out.writeBits(curPrefix,codeSize);
      codeSize=getCodeLength(availCode);
      availCode++;
      curPrefix=curSuffix;
    }
  }  

  private int getCodeLength(int code){
    if((code&2048)!=0){ return 12; }
    else if((code&1024)!=0){ return 11; }
    else if((code&512)!=0){ return 10; }
    else if((code&256)!=0){ return 9; }
    else if((code&128)!=0){ return 8; }
    else if((code&64)!=0){ return 7; }
    else if((code&32)!=0){ return 6; }
    else if((code&16)!=0){ return 5; }
    else if((code&8)!=0){ return 4; }
    else { return 3; }
  }

  public void flush()throws IOException{  // needs to be called at end of data  
    out.writeBits(curPrefix,codeSize);    // write last code
    out.writeBits(eoiCode,codeSize);      // write eoi 'end of image' code
    out.flush();
  }

  public void close()throws IOException{
    flush();
    out.close();
  }
}