package uk.co.mmscomputing.imageio.sff;

import java.io.*;
import uk.co.mmscomputing.io.ModHuffmanOutputStream;

public class SFFInputStream extends FilterInputStream{
  /*
    Input:   sff image file data
    Output:  modified huffman code

    read(),read(byte[]),read(byte[],int,int):
        signal [-1] means 'end of page'.

    start/resumes reading if hasImage() returns true.
  */

  private int     resVert,resHorz;
  @SuppressWarnings("unused")
private int 	  width,height,offset;

  private byte[]  data;
  private byte[]  lineData=null;
  private int     index,length;

  private boolean eop;                 // end of page

  private byte[]  whiteLine;
  private int     whiteLines;
  private boolean needToDuplicateLine,duplicateLine;

  private int     maxIllegalLineCodings=-1;  // -1 => don't throw IllegalLineCoding exception

  public SFFInputStream(InputStream in)throws IOException{
    super(in);
    readHeader();
    if(in.read()!=254){                // 0: page signature 254
      throw new IOException(getClass().getName()+".readHeader\n\tMissing page signature in sff fax document"); 
    }
  }
 
  public int getWidth(){return width;}
  public int getHeight(){return height;}
  public boolean isEndOfPage(){return eop;}

  public void setMaxAllowedIllegalLineCodings(int max){maxIllegalLineCodings=max;}

  public int getVerticalResolution(){
    switch(resVert){
//  case 0:  return 98;
    case 0:                            // duplicate lines
    case 1:  return 196;
    default: return 0;                 // unknown,error
    }
  }

  public int getHorizontalResolution(){
    switch(resHorz){
    case 0:  return 203;
    default: return 0;                 // unknown,error
    }
  }

  public boolean hasImage()throws IOException{
    data=null;index=0;length=0;
    height=0;eop=false;
    whiteLines=0;whiteLine=null;
    duplicateLine=true;
    return readImageHeader();
  }

  public int read(byte[] b)throws IOException{
    return read(b,0,b.length);
  }

  public int read(byte[] b,int off,int len)throws IOException{
//    eop=false;
    for(int i=0;i<len;i++){
      int c=read();
      if(c<0){                         // if end of page
        if(i>0){return i;}             // have some data in buffer
        return -1;                     // end of page
      }
      b[off+i]=(byte)c;  
    }    
    return len;
  }

  public int read()throws IOException{
    if(index>=length){                 // get next scan line
      if(0<whiteLines){                // if white lines
        whiteLines--;
      }else{
        if(eop){return -1;}            // end of page
        length=readLine();
        if(length==-1){return -1;}     // end of stream
      }
      height++;index=0;
    }
    return data[index++]&0x000000FF;
  }

  private int readUnsignedShort() throws IOException{
    int i;
    i =(((int)in.read())&0x000000FF);
    i|=(((int)in.read())&0x000000FF)<<8;
    return i;
  }

  private int readInt() throws IOException{
    int i =((int)in.read()&0x000000FF);
    i|=((int)in.read()&0x000000FF)<<8;
    i|=((int)in.read()&0x000000FF)<<16;
    i|=((int)in.read()&0x000000FF)<<24;
    return i;
  }

  private void skipBytes(int count) throws IOException{
    byte[] buf=new byte[count];in.read(buf);
  }

  private int readLine()throws IOException{
    eop=false;
    if(needToDuplicateLine){           // if vertical resolution is 96 dpi
      duplicateLine=!duplicateLine;    // output every line twice
      if(duplicateLine){return length;}
    }
    int len=in.read();
    if(len==-1){
      eop=true;  System.err.println(getClass().getName()+".readLine:\n\tMissing page header.");
      return -1;                       // end of stream (we shouldn't be here)
    }else if(len==0){                  // long lines;  modified huffman
      len = readUnsignedShort();
      data=lineData;
      in.read(data,0,len);
      data[len]=0x00;                  // for end of line synchronization, in case of coding errors
      data[len+1]=(byte)0x80;
      return len+2;
    }else if(len<=216){                // normal lines; modified huffman
      data=lineData;
      in.read(data,0,len);
      data[len]=0x00;                  // for end of line synchronization, in case of coding errors
      data[len+1]=(byte)0x80;
      return len+2;
    }else if(len<=253){                // skip white space lines
      if(whiteLine==null){             // get modified huffman codes for white line
        whiteLine=getWhiteLineCode(width);
      }
      whiteLines=(len-216-1);          // -1 : because we are setting first white line buffer here already
      data=whiteLine;
      return whiteLine.length;
    }else if(len==254){                // new page header
      eop=true;return -1;              // signal end of page        
    }else /*if(len==255)*/{
      len=in.read();
      if(len==0){                      // 'illegal line coding'
        if(maxIllegalLineCodings==0){
//        System.err.println(getClass().getName()+".readLine :\n\tToo many illegal line codings.");
          throw new IllegalLineCodingException(getClass().getName()+".readLine :\n\tToo many illegal line codings.");
        }
        maxIllegalLineCodings--;
        if(whiteLine==null){           // get modified huffman codes for white line
          whiteLine=getWhiteLineCode(width);
        }
        data=whiteLine;                // interpret as white line
        return whiteLine.length;
      }else{
        System.err.println(getClass().getName()+".readLine:\n\tReceived additional user information.");
        byte[] aui=new byte[len&0x00FF];
        in.read(aui);                  // additional user information
        for(int i=0;i<aui.length;i++){
          System.err.println("info["+i+"] = 0x"+Integer.toHexString(aui[i]));
        }
        return readLine();
      }
    }
  }

  private byte[] getWhiteLineCode(int width)throws IOException{
    ByteArrayOutputStream  baos=new ByteArrayOutputStream();
    ModHuffmanOutputStream mhos=new ModHuffmanOutputStream(baos);
    mhos.write(width);             
    mhos.flush();
    baos.write(0x00);baos.write(0x80); // for end of line synchronization
    mhos.close();
    return baos.toByteArray();         // 1728 white standard G3 fax line => B2 59 01 [00 80]
  }

  // Common-ISDN-API Part I : Annex B
  //  http://www.capi.org/download/capi20-1.pdf
  @SuppressWarnings("unused")
  private void readHeader()throws IOException{
    byte[] Sfff = new byte[4];
    in.read(Sfff);                                // 0:  'Sfff'
    if((Sfff[0]!=(byte)'S')||(Sfff[1]!=(byte)'f')||(Sfff[2]!=(byte)'f')||(Sfff[3]!=(byte)'f')){
      throw new IOException(getClass().getName()+".readHeader:\n\tInvalid sff fax document : Magic value is missing.");
    }
    int version=readUnsignedShort();              // 4:  version 0x01 5: 0x00
    if(version!=1){
      throw new IOException(getClass().getName()+".readHeader:\n\tUnknown file version ["+version+"]"); 
    }
    
	int userInfo=readUnsignedShort();             // 6:  not used by capi 0x0000
    int pageCount=readUnsignedShort();            // 8:  0x0000 if not known
    int firstPageHeader=readUnsignedShort();      // 10: capi : 0x14 but may be more
    int lastPageHeader=readInt();                 // 12: 0x00000000 if not known
    int offDocEnd=readInt();                      // 16: 0x00000000 if not known
    skipBytes(firstPageHeader-0x14);              // 20: header length (0x14)
  }
  @SuppressWarnings("unused")
  private boolean readImageHeader()throws IOException{
//  int pageSignature=in.read();                  // 0: 254 (read this in readLine)
    int pageHeaderLen=in.read();                  // 1:  offset from after this byte to data (usually 0x10)
    if(pageHeaderLen<=0){
      if(pageHeaderLen==-1){
        System.err.println(getClass().getName()+".readHeader:\n\tMissing page header.");
      }else{ 
        // need to read until -1 so that capi system does not moan:
        // Disconnected during transfer (local abort)
        int b;
        if((b=in.read())!=-1){                    
          System.err.println(getClass().getName()+".readHeader:\n\tAdditional data after end of document.");
          do{b=in.read();}while(b>0);
        }
      }
      return false;                               // end of stream,end of document
    }           
    resVert=in.read();                            // 2:  0x00 => 98lpi 0x01 => 196lpi
    if(resVert==255){return false;}               // end of document (should not be done like that)
    needToDuplicateLine=(resVert==0);
    resHorz=in.read();                            // 3:  0x00 => 203dpi
    int coding=in.read();                         // 4:  0x00 => modified huffman
    if(coding!=0){throw new IOException(getClass().getName()+".readPageHeader:\n\tDo not know how to handle this sff page coding ["+coding+"].");}
    /*reserved=*/in.read();                       // 5:  0x00
    width=readUnsignedShort();                    // 6:  line length
    if((lineData==null)||(lineData.length<width)){// create a buffer for modified huffman codes of current row
      lineData=new byte[width];
    }
    int height=readUnsignedShort();               // 8:  0x0000 if not known
    int offPreviousPage=readInt();                // 10: 0x0000 if not known
    int offNextPage=readInt();                    // 14: 0x0000 if not known
    skipBytes(pageHeaderLen-0x10);                // 18: page header length
    return true;                                  // next document
  }

  static public class IllegalLineCodingException extends IOException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalLineCodingException(String msg){
      super(msg);
    }
  }

}