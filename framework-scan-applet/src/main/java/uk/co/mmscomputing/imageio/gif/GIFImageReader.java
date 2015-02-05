package uk.co.mmscomputing.imageio.gif;

import java.io.*;
import java.util.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.metadata.IIOMetadata;

public class GIFImageReader extends ImageReader{

  private boolean gotHeader = false;
  private boolean gotImages = false;
  @SuppressWarnings("unchecked")
private Vector  images = null;

  private int loops=-1;

  protected GIFImageReader(ImageReaderSpi originatingProvider){
    super(originatingProvider);
  }

  public BufferedImage read(int imageIndex, ImageReadParam param)throws IOException{
    readAll();
    checkIndex(imageIndex);
    return (BufferedImage)images.elementAt(imageIndex);
  }

  public int getHeight(int imageIndex)throws IOException{
    readAll();
    checkIndex(imageIndex);
    return ((BufferedImage)images.elementAt(imageIndex)).getHeight();
  }

  public int getWidth(int imageIndex)throws IOException{
    readAll();
    checkIndex(imageIndex);
    return ((BufferedImage)images.elementAt(imageIndex)).getWidth();
  }

  @SuppressWarnings("unchecked")
public Iterator getImageTypes(int imageIndex)throws IOException{
    readAll();
    checkIndex(imageIndex);

    ImageTypeSpecifier imageType = null;
    java.util.List l = new ArrayList();
    imageType=ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

    l.add(imageType);
    return l.iterator();
  }

  public int getNumImages(boolean allowSearch)throws IOException{
    readAll();
    return images.size();
  }

  public IIOMetadata getImageMetadata(int imageIndex)throws IOException{
    readAll();
    checkIndex(imageIndex);
    return null;
  }

  public IIOMetadata getStreamMetadata() throws IOException{
    return null;
  }

////////////////////////////////////////////////////////////////////////////////////

  private void checkIndex(int imageIndex) {
    if (imageIndex > images.size()) {
      throw new IndexOutOfBoundsException("mmsc - GIFImageReader : Bad index in gif image reader");
    }
  }

  //    document header

  int      width, height;
  int      background;
  double   aspectRatio;

  boolean  gcm=false;             // global color map
  boolean  sort=false;            // sort flag

  int      colres, colCount, bitColCount;

  byte[][] gct;

  //    page header

  int     pleft, ptop, pwidth, pheight;
  int     pcolCount=0, pbitColCount=0;
  boolean psort=false, pinterlaced=false, plcm=false;

  byte[][] plct;

  private byte[][] readColorTable(InputStream in, int colCount)throws IOException{

    byte[] rColourTable=new byte[256];
    byte[] gColourTable=new byte[256];
    byte[] bColourTable=new byte[256];

    for(int i=0;i<colCount;i++){
      rColourTable[i]=(byte)in.read();
      gColourTable[i]=(byte)in.read();
      bColourTable[i]=(byte)in.read();
    }

    byte[][] colourTable=new byte[3][];
    colourTable[0]=rColourTable;
    colourTable[1]=gColourTable;
    colourTable[2]=bColourTable;

    return  colourTable;
  }

  private int readShort(InputStream in)throws IOException{
    return (in.read()&0x00FF)
         |((in.read()&0x00FF)<<8)
    ;
  }

  private void readHeader(InputStream in)throws IOException{
    if(gotHeader){return;}
    gotHeader=true;

    byte[] type=new byte[3];
    type[0]=(byte)in.read();type[1]=(byte)in.read();type[2]=(byte)in.read();
    if(((type[0]!='G')||(type[1]!='I')||(type[2]!='F'))){
      throw new IOException(getClass().getName()+".readHeader:\n\tInvalid gif document : Missing 'GIF' byte sequence.");
    }

    byte[] version = new byte[3];
    version[0]=(byte)in.read();version[1]=(byte)in.read();version[2]=(byte)in.read();
    if(((version[0]!='8')||(version[1]!='9')||(version[2]!='a'))){
      if(((version[0]!='8')||(version[1]!='7')||(version[2]!='a'))){
        throw new IOException(getClass().getName()+".readHeader:\n\tInvalid gif document : Version is missing or unsupported.");
      }
    }

    width          = readShort(in);
    height         = readShort(in);
    int resolution = in.read();
    background     = in.read();
    int ar         = in.read();

    aspectRatio=(ar==0)? 1 : (ar+15)/64;

    gcm     =(resolution&0x00000080)!=0;
    colres  =((resolution&0x00000070)>>4)+1;

    sort    =(resolution&0x00000008)!=0;

    bitColCount =(resolution&0x00000007)+1;
    colCount    =1<<bitColCount;

    gct=(gcm)?readColorTable(in,colCount):null;
  }

  private void readPageHeader(InputStream in)throws IOException{
    pleft   =readShort(in);
    ptop    =readShort(in);
    pwidth  =readShort(in);
    pheight =readShort(in);
    int flag=in.read();

    pbitColCount=(flag&0x00000007)+1;
    pcolCount=1<<pbitColCount;
    psort=(flag&0x00000020)!=0;
    pinterlaced=(flag&0x00000040)!=0;
    plcm=(flag&0x00000080)!=0;

    plct=(plcm)?readColorTable(in,pcolCount):null;

    System.out.println("GIF: left   ="+pleft);
    System.out.println("GIF: top    ="+ptop);
    System.out.println("GIF: width  ="+pwidth);
    System.out.println("GIF: height ="+pheight);
  }

  public void readExtension(InputStream in)throws IOException{
    int code=in.read();
///*
    switch(code){
    case 1:                                            //  Plain Text Extension      0x01
      System.out.println("Plain Text Extension ! ");
      break;
    case 249: readCntl(in);    return;                 //  Graphic Control Extension 0xF9
    case 254: readComment(in); return;                 //  Comment Extension         0xFE
    case 255: readApp(in);     return;                 //  Application Extension     0xFF
    default:
      throw new IOException(getClass().getName()+".readExtension\n\tIllegal GIF Extension Block !");
    }
//*/
    int len=in.read();
    while(len>0){
      in.skip(len);
      len=in.read();
    }
  }

  private void readComment(InputStream in)throws IOException{
    String comment="";

    int len=in.read();
    while(len>0){
      for(int i=0;i<len;i++){
        comment+=(char)in.read();
      }
      len=in.read();
    }
    System.out.println("Comment: "+comment);
  }

  private void readApp(InputStream in)throws IOException{
    String id="";

    int len=in.read();                        // read application identifier
    for(int i=0;i<len;i++){
      id+=(char)in.read();
    }
    len=in.read();                            // next sub-block length

    if(id.equals("NETSCAPE2.0")){
      int i=0;
      if(len==3){
        int no=in.read();i++;
        switch(no){
        case 1:
          loops=in.read()|(in.read()<<8);i+=2;
          System.out.println("loops="+loops);
          break;
        default:
          System.out.println("NO:"+no);
          break;
        }
      }
      for(;i<len;i++){
        int b=in.read();
        System.out.println("appl["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);
      }
      len=in.read();                          // next sub-block length
    }else{
      System.out.println("ID:"+id);
    }

    while(len>0){
      for(int i=0;i<len;i++){
        int b=in.read();
        System.out.println("appl["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);
      }
      len=in.read();
    }
  }

  private void readCntl(InputStream in)throws IOException{
    int len=in.read();
    if(len==4){                                        //  BlockSize should be 4
      String str="\n\nGraphic Control Extension:";
      int flags=in.read();
      switch((flags>>2)&0x07){
      case 0: str+="\n\tNo disposal specified.";break;
      case 1: str+="\n\tDo not dispose.";break;
      case 2: str+="\n\tRestore to background color.";break;
      case 3: str+="\n\tRestore to previous.";break;
      default: str+="\n\tUnknown disposal mode.";break;
      }
      switch((flags>>1)&0x01){
      case 0: str+="\n\tUser input is not expected.";break;
      case 1: str+="\n\tUser input is expected.";break;
      }
      switch(flags&0x01){
      case 0: str+="\n\tTransparent Index is not given.";break;
      case 1: str+="\n\tTransparent Index is given.";break;
      }
      int delay=in.read()|(in.read()<<8);
      str+="\n\tDelay: "+delay;
      int background=in.read();
      str+="\n\tTransparency Index: "+background+"\n";

      System.out.println(str);

      len=in.read();                                   // Block Terminator
    }
    while(len>0){
      for(int i=0;i<len;i++){
        int b=in.read();
        System.out.println("cntl["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);
      }
      len=in.read();
    }
  }
  @SuppressWarnings("unused")
  private BufferedImage read(InputStream in)throws IOException{
    readHeader(in);
    int code=in.read();
    while(code>0){
      switch(code){
      case ',':                                        // Image Separator
        readPageHeader(in);

        GIFLZWInputStream is=new GIFLZWInputStream(in);
        byte[][] ct=(plct!=null)?plct:gct;
       
		int      cc=(plct!=null)?pcolCount:colCount;
        int      bcc=(plct!=null)?pbitColCount:bitColCount;
        IndexColorModel cm     = new IndexColorModel(bcc,colCount,ct[0],ct[1],ct[2]);
        BufferedImage   image  = new BufferedImage(pwidth,pheight,BufferedImage.TYPE_BYTE_INDEXED,cm);
        Raster          raster = image.getRaster();
        DataBufferByte  db     = (DataBufferByte)raster.getDataBuffer();    
        byte[]          data   = db.getData();
        is.read(data);                                 // read codes
        int eoi=is.read();                             // read eoi code

        return image;        
      case 0x21:                                       // Extension block
        readExtension(in);
        break;
      case ';':                                        // GIF-Terminator
        return null;
      default:
        throw new IOException(getClass().getName()+".read\n\tIllegal GIF block : "+(char)code+" [0x"+Integer.toHexString(code)+"]");
      }
      code=in.read();
    }
    throw new IOException(getClass().getName()+".read\n\tDid not find GIF Image Descriptor AND/OR GIF Terminator!");
  }

  @SuppressWarnings("unchecked")
private void readAll()throws IOException{
    if(!gotImages){
      gotImages = true;
      GIFFilterInputStream  in=new GIFFilterInputStream((ImageInputStream)getInput());
      images=new Vector();
      BufferedImage image;
      while((image=read(in))!=null){
        images.add(image);
      }
    }
  }

  static private class GIFFilterInputStream extends InputStream{

    private ImageInputStream in;
    private byte[]           buffer;
    private int              count,max;

    int              pos=0;
    
    public GIFFilterInputStream(ImageInputStream in){
      this.in=in;
      buffer=new byte[4096];
      count=0;max=0;
    }

    public int read()throws IOException{
      if(count==max){
        max=in.read(buffer);
        if(max==-1){count=-1;return -1;}
        count=0;
      }
      pos++;
      return (buffer[count++]&0x00FF);
    }    
  }
}



