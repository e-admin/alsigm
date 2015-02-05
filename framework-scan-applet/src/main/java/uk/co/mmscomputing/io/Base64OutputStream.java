package uk.co.mmscomputing.io;

import java.io.*;

public class Base64OutputStream extends FilterOutputStream implements Base64Table{

  static byte[] decodeTable;

  private int     pos;
  private byte[]  b3;
  private byte[]  b4;

  public Base64OutputStream(OutputStream out){   
    super(out);
    pos = 0;
    b3  = new byte[3];
    b4  = new byte[4];
  }

  public void write(int b) throws IOException{
    if(b<=' '){return;}                            // waste white space
    b4[pos++]=(byte)b;
    if(pos==4){
      int len=decode(b3,b4);
      out.write(b3,0,len);
      pos=0;
    }
  }     
        
  public void write(byte[] buf,int off,int len)throws IOException{
    for(int i=0;i<len;i++){
      write(buf[off+i]);
    }        
  }     

  private int decode(byte[] dest,byte[] src)throws IOException{

    // assume this is a valid base64 stream. No error checking !!!

    int buf=0;

    if(src[3]!=equalSign){                         // xxxx -> 3 bytes
      buf =decodeTable[src[0]&0x0000007F]<<18;
      buf+=decodeTable[src[1]&0x0000007F]<<12;
      buf+=decodeTable[src[2]&0x0000007F]<< 6;
      buf+=decodeTable[src[3]&0x0000007F];

      dest[0]=(byte)((buf>>16)&0x000000FF);
      dest[1]=(byte)((buf>> 8)&0x000000FF);
      dest[2]=(byte)((buf    )&0x000000FF);
      return 3;
    }else if(src[2]!=equalSign){                   // xxx= -> 2 bytes
      buf =decodeTable[src[0]&0x0000007F]<<18;
      buf+=decodeTable[src[1]&0x0000007F]<<12;
      buf+=decodeTable[src[2]&0x0000007F]<< 6;

      dest[0]=(byte)((buf>>16)&0x000000FF);
      dest[1]=(byte)((buf>> 8)&0x000000FF);
      return 2;
    }else{                                         // xx== -> 1 byte
      buf =decodeTable[src[0]&0x0000007F]<<18;
      buf+=decodeTable[src[1]&0x0000007F]<<12;

      dest[0]=(byte)((buf>>16)&0x000000FF);
      return 1;
    }
  }
        
  public void flush() throws IOException{
    if(pos!=0){
      System.err.println(getClass().getName()+"flush():\n\tBase64 input not properly padded. Stream length is not a multiple of 4");
    }
    super.flush();
  }
        
  static{
    decodeTable=new byte[128];

    for (int i=0;i<decodeTable.length;i++){decodeTable[i]=-1;}
    for (int i=0;i<64;i++){decodeTable[encodeTable[i]]=(byte)i;}
  }

  public static void main(String[] args){
    try{
//      String s="SW1wb3J0YW50IE1lc3NhZ2UhIQ==";                 // Important Message!!
      String s="PD94bWwgdmVyc2lvbj0iMS4wIj8+CjxjaGVja291dC1zaG9wcGlu";

      ByteArrayOutputStream baos=new ByteArrayOutputStream();
      Base64OutputStream os=new Base64OutputStream(baos);
      os.write(s.getBytes());
      System.out.print(new String(baos.toByteArray()));
      os.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
    
