package uk.co.mmscomputing.io;

import java.io.*;

public class ModHuffmanInputStream extends BitInputStream implements ModHuffmanTable{

  protected int state;

  public ModHuffmanInputStream(InputStream in){
    super(in);
    state=WHITE;
  }

  public void skipPadding(int bits)throws IOException{  // TIFFImageReader (Class B MH)
    super.skipPadding(bits);
    state=WHITE;
  }

  public void syncWithEOL()throws IOException{          // SFFImageReader, TIFFImageReader (Class F T4 MH)
    if(state!=EOL){
      needBits(12);
      while(availableBits()>=12){
        if(getBits(12)==EOLCW){
          clrBits(12);break;
        }
        clrBits(1);needBits(12);
      }
    }
    state=WHITE;
  }

  public void readEOL()throws IOException{
    syncWithEOL();
  }

  public int getState(){return state;}
  public int getColour(int colwhite){return (state==BLACK)?~colwhite:colwhite;}

  public int read()throws IOException{
    int r;		
    if(state == WHITE){  state=BLACK;    r=read(makeUpWhite,12,termWhite,8);
    }else{               state=WHITE;    r=read(makeUpBlack,13,termBlack,12);
    }
//  System.err.println(((state==WHITE)?"B ":"W ")+r);
    return r;
  }

  public int read(byte[] b)throws IOException{
    throw new IOException(getClass().getName()+".read:\n\tInternal Error. Cannot read whole byte array with this stream !!!");
  }

  public int read(byte[] b,int off,int len)throws IOException{
    throw new IOException(getClass().getName()+".read:\n\tInternal Error. Cannot read whole byte array with this stream !!!");
  }

  private int read(int[][] makeUp,int maxmakeUp,int[][] term,int maxterm)throws IOException{
    needBits(maxterm);
    int len=findToken(term);                     // read terminating code
    if(len>=0){return len;}
    int runlen=0;
    needBits(maxmakeUp);                         // expect make-up code now
    len=findToken(makeUp);                       // read make-up code
    if(len==0){state=EOL;return 0;}
    if(len>=0){                                  // found make-up code
      while(len==MAXCHUNK){                      // read 2560 codes
        runlen+=MAXCHUNK;
        needBits(maxmakeUp);
        len=findToken(makeUp);
      }
      if(len>=0){runlen+=len;}
      needBits(maxterm);
    }
    len=findToken(term);                         // read terminating code
    if(len>=0){return runlen+len;}
    return checkEOL();
  }

  protected int findToken(int[][] table){
    for(int i=0; i<table.length; i++) {
      int[] entry=table[i];
      int bits=getBits(entry[2]);
      if(entry[0]==bits){
        clrBits(entry[2]);
        return entry[1];
      }
    }
    return -1;
  }

  protected int checkEOL()throws IOException{
    int bits;
    needBits(12);
    while(availableBits()>=12){
      bits=getBits(12);
      if(bits==EOLCW){state=EOL;clrBits(12);return 0;}
      if(bits!=0){throw new ModHuffmanCodingException(getClass().getName()+".checkEOL:\n\tCoding error: End of line code is missing.");}
      clrBits(1);needBits(12);
    }
    return -1;				                           // eof
  }

  static public class ModHuffmanCodingException extends IOException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModHuffmanCodingException(String msg){
      super(msg);
    }
  }

  public static void main(String[] argv){
    try{
//    byte[] buf=new byte[]{0x06,0x25,(byte)0xD0,0x01};  1728=1704+24

//    1728 white standard G3 fax line => B2 59 01

      byte[] buf=new byte[]{(byte)0xB2,0x59,0x01};
      ByteArrayInputStream  bais=new ByteArrayInputStream(buf);
      ModHuffmanInputStream mhis=new ModHuffmanInputStream(bais);

      int runlen;
      while((runlen=mhis.read())!=-1){
        System.out.println("runlen= "+runlen);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}