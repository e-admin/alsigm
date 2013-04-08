package uk.co.mmscomputing.io;

import java.io.*;

public class LZWInputStream extends BitInputStream{

  private final static int MAXCODE = 4096;

  private int[] prefix=new int[MAXCODE];
  private int[] suffix=new int[MAXCODE];
  private int[] stack =new int[MAXCODE];
  private int   sp=0;

  private int dataSize=0;
  private int clearCode=0;
  private int eoiCode=0;                       // eoi code
  private int availCode=0;                     // next available code
  private int lastCode=MAXCODE;                // last code, set to impossible value
  private int codeSize=0;
  private int codeMask=0;

  private int first=0;

  public LZWInputStream(InputStream in,int datasize,boolean nbms) throws IOException{
    super(in,nbms);
   
    this.dataSize=datasize;

    sp=0;                                      // reset stack pointer
    first=0;
    resetTables();
    for(int i=0; i<clearCode; i++){            // initialize 
      prefix[i]=MAXCODE;
      suffix[i]=i;
    }  
  }

  private void resetTables(){

    // GIF: datasize = 2..8
    // TIFF LZW: datasize = 8 clearCode = 256 eoi = 257

    clearCode  = 1<<dataSize;
    eoiCode    = clearCode+1;                  // eoi code
    availCode  = clearCode+2;                  // next available code
    lastCode   = MAXCODE;                      // last code, set to impossible value
    codeSize   = dataSize+1;
    codeMask   = (1<<codeSize)-1;

//Log.msg("data size : 0x"+Integer.toHexString(dataSize));
//Log.msg("clear code : 0x"+Integer.toHexString(clearCode));
//Log.msg("avail code : 0x"+Integer.toHexString(availCode));
//Log.msg("last code : 0x"+Integer.toHexString(lastCode));
//Log.msg("code size : 0x"+Integer.toHexString(codeSize));
//Log.msg("code mask : 0x"+Integer.toHexString(codeMask));
//Log.msg("eoi code : 0x"+Integer.toHexString(eoiCode));

  }

  public int read(byte[] b)throws IOException{
    return read(b,0,b.length);
  }

  public int read(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      int c=read();
      if(c<0){ return i; }
      b[off+i]=(byte)c;  
    }    
    return len;
  }

  public int read()throws IOException{
    int code;

    if(sp>0){ return stack[--sp]; }
    while((code=readBits(codeSize))>=0){
      if(code==eoiCode){
//        code=super.read();		           // read empty run
//        if(code!=0){
//          throw new IOException(getClass().getName()+".read:\n\tMissing empty run !");
//        }
        return -1;
      }
      decode(code);
      if(sp>0){ return stack[--sp]; }
    }
    throw new IOException(getClass().getName()+".read:\n\tMissing eoi code !");
  }

  private void decode(int code)throws IOException{
    if(code==clearCode){                 // reset variables
      codeSize =dataSize+1;
      codeMask =(1<<codeSize)-1;
      availCode=clearCode+2;             // next available code
      lastCode =MAXCODE;                 // last code, set to impossible value
      return;
    }
    if(code>availCode){
      throw new IOException(getClass().getName()+"decode:\n\tIllegal LZW-Code ["+code+"] > ["+availCode+"]");
    }
    if(lastCode==MAXCODE){               //	first code
      first=suffix[code];
      stack[sp++]=first;
      lastCode=code;
      return;
    }
    int inCode=code;                   // remember

    if(code==availCode){
      stack[sp++]=first;
      code=lastCode;      
    }
    while(code>clearCode){
      stack[sp++]=suffix[code];
      code=prefix[code];
    }

    first=suffix[code];			
    stack[sp++]=first;			
    prefix[availCode]=lastCode;          // generate code table
    suffix[availCode]=first;

    if(availCode<4096){                  // if(availCode==4096) a clear code will reset decoder
      availCode++;
    }
/*
    if(((availCode&codeMask)==0)&&(availCode<4096)){  // GIF
      codeSize++;
      codeMask+=availCode;
    }
*/
    if(availCode==codeMask){             // TIFF
      codeSize++;
      codeMask=(1<<codeSize)-1;
    }
    lastCode=inCode;
  }  
}

// Grafikformate; Thomas W. Lipp; Microsoft Press 1997; ISBN 3-86063-391-0
