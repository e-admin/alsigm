package uk.co.mmscomputing.io;

import java.io.*;

public class BitInputStream extends FilterInputStream{
  private 	int	    buf;
  private 	int	    bitsAvail;
  private 	boolean eof;
  protected	int	    count=0;
  protected boolean nextByteMoreSignificant;

  public BitInputStream(InputStream in,boolean nbms){
    super(in);
    bitsAvail=0;
    buf=0;
    eof=false;
    count=0;
    nextByteMoreSignificant=nbms;
  }

  public BitInputStream(InputStream in){
    this(in,true);
  }

  public void setNextByteMoreSignificant(boolean nextByteMoreSignificant){
    this.nextByteMoreSignificant=nextByteMoreSignificant;
  }

  public void reset()throws IOException{
    super.reset();
    bitsAvail=0;
    buf=0;
    eof=false;
    count=0;
  }

  public int availableBits(){
    if(eof && (bitsAvail<=0)){return -1;}
    return bitsAvail;
  }

  public void skipPadding(int bits)throws IOException{
    clrBits(bitsAvail%bits);
  }

  public int readBit()throws IOException{		// read one bit
    if(eof && (bitsAvail<=0)){ return -1; }
    needBits(1);			
    int bit=getBits(1);
    clrBits(1);
    return bit;
  }

  public int readBits(int bitcount)throws IOException{	// read "count" bit
    if(eof && (bitsAvail<=0)){ return -1; }
    if(bitcount==0){ return 0; }
    needBits(bitcount);			
    int bits=getBits(bitcount);
    clrBits(bitcount);
    return bits;
  }

  protected int cbCount()throws IOException{		//	callback for subclasses
    return in.read();
  }

  public void needBits(int bitcount)throws IOException{
    // Assert(bitcount<32);
    while((eof==false)&&(bitsAvail<bitcount)){
      int b=cbCount();
      if(b==-1){eof=true;break;}
      b&=0x00FF;
      count++;
      buf|=(nextByteMoreSignificant)?(b<<bitsAvail):(b<<(24-bitsAvail));
      bitsAvail += 8;
    }
  }

  protected void clrBits(int bitcount){
    bitsAvail -= bitcount;
    if(nextByteMoreSignificant){buf>>>=bitcount;}else{buf<<=bitcount;}
  }

  protected int getBits(int bitcount){
    bitcount=32-bitcount;
    return (nextByteMoreSignificant)?((buf<<bitcount)>>>bitcount):(buf>>>bitcount);
  }

  public static void main(String[] argv){

  // bytes 1001 1001 1000 1000 1001 1001

  // nextByteMoreSignificant=true bitcount=9    // i.e. GIF LZW

  // code1 = 0 1001 1001
  // code2 = 0 1100 0100
  // code3 = 0 0010 0110

  // nextByteMoreSignificant=false bitcount=9   // i.e. TIFF LZW, JPEG

  // code1 = 1 0011 0011
  // code2 = 0 0010 0010
  // code3 = 0 1100 1000

    try{
      byte[] buf=new byte[]{(byte)0x99,(byte)0x88,(byte)0x99};
      ByteArrayInputStream  bais=new ByteArrayInputStream(buf);
      BitInputStream is=new BitInputStream(bais,true);

      System.out.println("\nnextByteMoreSignificant = true");
      int code;
      while((code=is.readBits(9))!=-1){System.out.println("code= "+Integer.toBinaryString(code));}


      bais=new ByteArrayInputStream(buf);
      is=new BitInputStream(bais,false);
      
      System.out.println("\nnextByteMoreSignificant = false");
      while((code=is.readBits(9))!=-1){System.out.println("code= "+Integer.toBinaryString(code));}
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}