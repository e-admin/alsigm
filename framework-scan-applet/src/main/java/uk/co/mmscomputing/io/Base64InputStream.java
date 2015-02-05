package uk.co.mmscomputing.io;

import java.io.*;

/*
  rfc1341: MIME (Multipurpose Internet Mail Extensions)

  Base64InputStream: Encode; input: byte data to output: 64base byte sequences.
*/

public class Base64InputStream extends FilterInputStream implements Base64Table{

  private int     position;
  private int     lineLength;
  private byte[]  b3;
  private byte[]  b4;

  public Base64InputStream(InputStream in){   
    super(in);
    this.position     = 3;
    this.lineLength   = -1;
    this.b3           = new byte[3];
    this.b4           = new byte[4];
  }
        
  public int read()throws IOException{
    lineLength++;
    if(lineLength==76){                     // break lines after 76 bytes
      lineLength = 0;
      return (byte)'\n';
    }
    position++;
    if(position>=4){
      int len=in.read(b3);                  // read next three bytes
      if(len==-1){return -1;}               // end of stream
      encode(b4,b3,len);
      position=0;
    }
    return b4[position];
  }     

  public int read(byte[] b,int off,int len)throws IOException{
    if(b==null){ 
      throw new NullPointerException(getClass().getName()+".read(byte[] b, int off, int len): b is null");
    }
    if((off<0)||(len<0)||(b.length<(off+len))){
      throw new IndexOutOfBoundsException(getClass().getName()+".read(byte[] b, int off, int len): index off or len out of bounds.");
    }
    int i=0;
    while(i<len){
      int v=read();
      if(v==-1){return (i==0)?-1:i;}
      b[off+i]=(byte)v;
      i++;
    }
    return len;
  }

  private void encode(byte[] dest,byte[] src,int len){

    int buf=0;

    switch(len){
    case 3:
      buf =(src[0]&0x000000FF)<<16;
      buf+=(src[1]&0x000000FF)<<8;
      buf+=(src[2]&0x000000FF);

      dest[0]=encodeTable[(buf>>18)           ];
      dest[1]=encodeTable[(buf>>12)&0x0000003F];
      dest[2]=encodeTable[(buf>> 6)&0x0000003F];
      dest[3]=encodeTable[(buf    )&0x0000003F];
      break;            
    case 2:
      buf =(src[0]&0x000000FF)<<16;
      buf+=(src[1]&0x000000FF)<<8;

      dest[0]=encodeTable[(buf>>18)           ];
      dest[1]=encodeTable[(buf>>12)&0x0000003F];
      dest[2]=encodeTable[(buf>> 6)&0x0000003F];
      dest[3]=equalSign;
      break;
    case 1:
      buf=(src[0]&0x000000FF)<<16;

      dest[0]=encodeTable[(buf>>18)           ];
      dest[1]=encodeTable[(buf>>12)&0x0000003F];
      dest[2]=equalSign;
      dest[3]=equalSign;
      break;                
    }
  }

  static public byte[] encode(String data)throws IOException{
    return encode(data.getBytes());
  }

  static public byte[] encode(byte[] data)throws IOException{
    return encode(new ByteArrayInputStream(data));
  }

  static public byte[] encode(InputStream data)throws IOException{
    return encode(data,256);
  }

  static public byte[] encode(InputStream data,int size)throws IOException{
    Base64InputStream     is = new Base64InputStream(data);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] b=new byte[size];
    int len;
    while((len=is.read(b))!=-1){
      os.write(b,0,len);                                  //        System.out.print(new String(b,0,len));
    }
    return os.toByteArray();
  }

/*
  static final byte[] encodeTable;

  static{
    encodeTable=new byte[64];  
    
    int i=0;
    for(byte c='A';c<='Z';c++){encodeTable[i++]=c};
    for(byte c='a';c<='z';c++){encodeTable[i++]=c};
    for(byte c='0';c<='9';c++){encodeTable[i++]=c};
    encodeTable[i++]='+';encodeTable[i++]='/'; 
  }
*/

  public static void main(String[] args){
    try{
      byte[] data = Base64InputStream.encode("Important Message!!"); 
      System.out.print(new String(data));                          // SW1wb3J0YW50IE1lc3NhZ2UhIQ==
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
    
