package uk.co.mmscomputing.imageio.gif;

import java.io.*;

public class GIFLZWInputStream extends InputStream{

  private final static int MAXCODE = 4096;

  private int[] prefix=new int[MAXCODE];
  private int[] suffix=new int[MAXCODE];
  private int[] stack =new int[MAXCODE];
  private int   sp=0;

  private int dataSize=0;
  private int clearCode=0;
  private int eoiCode=0;             //  eoi code
  private int availCode=0;           //  next available code
  private int lastCode=MAXCODE;      //  last used code, set to impossible value
  private int codeSize=0;
  private int codeMask=0;

  private int first=0;
  private GIFBitInputStream in;

  public GIFLZWInputStream(InputStream is) throws IOException{
    dataSize =is.read();
    in       =new GIFBitInputStream(is);
    sp=0;                            //  reset stack pointer
    first=0;
    resetTables();
    for(int i=0; i<clearCode; i++){  //  initialize 
      prefix[i]=MAXCODE;
      suffix[i]=i;
    }  
  }

  private void resetTables(){
    clearCode  = 1<<dataSize;
    eoiCode    = clearCode+1;        //  eoi code
    availCode  = clearCode+2;        //  next available code
    lastCode   = MAXCODE;            //  old code, set to impossible value
    codeSize   = dataSize+1;
    codeMask   = (1<<codeSize)-1;

//System.err.println("data size : 0x"+Integer.toHexString(dataSize));
//System.err.println("clear code : 0x"+Integer.toHexString(clearCode));
//System.err.println("avail code : 0x"+Integer.toHexString(availCode));
//System.err.println("old code : 0x"+Integer.toHexString(lastCode));
//System.err.println("code size : 0x"+Integer.toHexString(codeSize));
//System.err.println("code mask : 0x"+Integer.toHexString(codeMask));
//System.err.println("eoi code : 0x"+Integer.toHexString(eoiCode));
  }

  public int read()throws IOException{
    int code;

    if(sp>0){return stack[--sp];}
    while((code=in.readBits(codeSize))>=0){
      if(code==eoiCode){
        code=in.read();              // make sure to read empty chunk byte
        if(code!=-1){throw new IOException(getClass().getName()+".read:\n\tEOI code before end of data!");}
        return -1;
      }
      decode(code);
      if(sp>0){return stack[--sp];}
    }
    throw new IOException(getClass().getName()+".read:\n\tMissing eoi code.");
  }

  public int read(byte[] b)throws IOException{
    return read(b,0,b.length);
  }

  public int read(byte[] b,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      int c=read();
      if(c<0){return (i>0)?i:-1; }
      b[off+i]=(byte)c;  
    }    
    return len;
  }

  private void decode(int code)throws IOException{
    if(code==clearCode){             //  reset variables
      codeSize=dataSize+1;
      codeMask=(1<<codeSize)-1;
      availCode=clearCode+2;         //  next available code
      lastCode=MAXCODE;              //    old code, set to impossible value
      return;
    }
    if(code>availCode){
      throw new IOException(getClass().getName()+"decode:\n\tIllegal LZW-Code ["+code+"] > ["+availCode+"]");
    }
    if(lastCode==MAXCODE){           //    first code
      first=suffix[code];
      stack[sp++]=first;
      lastCode=code;
      return;
    }
    int inCode=code;                 //    remember

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
    prefix[availCode]=lastCode;      //    generate code table on fly
    suffix[availCode]=first;

    if(availCode<4096){              //    if(availCode==4096) a clear code will reset decoder
      availCode++;
    }
    if(((availCode&codeMask)==0)&&(availCode<4096)){
      codeSize++;
      codeMask+=availCode;
    }
    lastCode=inCode;
  }  
}