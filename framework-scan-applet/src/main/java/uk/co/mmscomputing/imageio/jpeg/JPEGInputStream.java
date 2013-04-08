package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;
import uk.co.mmscomputing.io.*;

public class JPEGInputStream extends IntFilterInputStream implements JPEGConstants{

  JPEGBitInputStream in;

  protected int   bps;                         // bits per sample: DCT compression [8,12] or lossless [2..16]
  protected int   height,width;
  protected int   maxHor,maxVert;
  protected int   mcuHeight,mcuWidth,mcuRows,mcuCols;
  protected int   spp;                         // samples per pixel

  private   int[][] qts = new int[4][];        // available quantization tables

  protected JPEGHuffmanInputStream[]   dcins   = new JPEGHuffmanInputStream[4];
  protected JPEGHuffmanInputStream[]   acins   = new JPEGHuffmanInputStream[4];

  protected JPEGComponentInputStream[] compins = new JPEGComponentInputStream[4];
  protected JPEGComponentInputStream[] scanins = new JPEGComponentInputStream[4];

  protected int Ri;                            // restart segment: how many MCUs in interval;

  public JPEGInputStream(InputStream input)throws IOException{
    super(null);
    Ri=0;maxHor=0;maxVert=0;
    in=new JPEGBitInputStream(input,this);
    ((JPEGBitInputStream)in).start();
  }

  public JPEGInputStream(
      InputStream input,
      int[][] qts,
      JPEGHuffmanInputStream[] dcins,
      JPEGHuffmanInputStream[] acins
  )throws IOException{
    super(null);
    Ri=0;maxHor=0;maxVert=0;
    this.qts  =qts;
    this.dcins=dcins;
    this.acins=acins;
    in=new JPEGBitInputStream(input,this);
    ((JPEGBitInputStream)in).start();
    for(int i=0;(dcins[i]!=null)&&(i<dcins.length);i++){dcins[i].setInputStream(in);}
    for(int i=0;(acins[i]!=null)&&(i<acins.length);i++){acins[i].setInputStream(in);}
  }

  public int[][]                  getQTs()  {return qts;}
  public JPEGHuffmanInputStream[] getDCIns(){return dcins;}
  public JPEGHuffmanInputStream[] getACIns(){return acins;}

  public int getHeight(){return height;}
  public int getWidth(){ return width;}
  public int getNumComponents(){return spp;}

  protected int readIn(InputStream in)throws IOException{
    int b=in.read();
    if(b==-1){
      IOException ioe=new IOException(getClass().getName()+"readIn:\n\tUnexpected end of file.");
      ioe.printStackTrace();
      throw ioe;
    }
    return b;
  }

  public void startOfFrame(InputStream in,int mode)throws IOException{ // 0xC0,0xC1
    bps   =readIn(in);
    height=(readIn(in)<<8)|readIn(in);                        // System.out.println("Height="+height);
    width =(readIn(in)<<8)|readIn(in);                        // System.out.println("Width="+width);
    spp   =readIn(in);                                        // System.out.println("3\bspp="+spp);

    for(int i=0;i<spp;i++){
      compins[i]=new JPEGComponentInputStream(readIn(in));    // Component id can be between 0..255
      compins[i].setBitsPerSample(bps);
      compins[i].setDimensions(height,width);
      int b  = readIn(in);
      int Hi = ((b>>4)&0x0F);                                 // 1..4
      int Vi =  (b    &0x0F);                                 // 1..4

      if(spp==1){                                             // [1]p.25 order left to right and top to bottom whatever the Hi,Vi values
        maxHor=1;maxVert=1;
        compins[i].setSamplingRate(1,1);
      }else{
        if(Hi>maxHor){maxHor=Hi;}
        if(Vi>maxVert){maxVert=Vi;}
        compins[i].setSamplingRate(Vi,Hi);
      }
      compins[i].setQuantizationTable(qts[readIn(in)]);       // assign one of the available QTs to a component
//      System.out.println(compins[i].toString());
    }    
    for(int i=0;i<spp;i++){
      compins[i].setMaxSamplingRate(maxVert,maxHor);
    }

    mcuHeight = maxVert*DCTSize;
    mcuWidth  = maxHor *DCTSize;
    mcuRows   =(height+mcuHeight-1)/mcuHeight;
    mcuCols   =(width +mcuWidth -1)/mcuWidth ;
/*
    System.out.println("maxHor="+maxHor);
    System.out.println("maxVert="+maxVert);
    System.out.println("mcuHeight="+mcuHeight);
    System.out.println("mcuWidth="+mcuWidth);
    System.out.println("mcuRows="+mcuRows);
    System.out.println("mcuCols="+mcuCols);
*/
  }

  public void defineHuffmanTables(InputStream tables)throws IOException{   // 0xC4
//    System.out.println("3\bDefine Huffman Tables");

    for(int n=0;n<8;n++){                                                  // max 8 tables possible
      int b=tables.read();
      switch((b>>4)&0x0F){                                                 // table class
      case 0:  dcins[b&0x0F]=new JPEGHuffmanInputStream(in,tables);break;  // DC table 
      case 1:  acins[b&0x0F]=new JPEGHuffmanInputStream(in,tables);break;  // AC table
      default: return;
      }
    }
  }

  public void defineArithmeticConditioning(InputStream in)throws IOException{ // 0xC8
    System.out.println("3\bDefine Arithmetic Conditioning");
  }

  public void restartIntervalTermination(int no)throws IOException{        // 0xD0 .. 0xD7; no = 0..7
    for(int c=0;c<spp;c++){scanins[c].restart();}                          // System.out.println("3\bRestart Interval Termination: no = "+no);
  }

  public void startOfImage(){                                              // 0xD8
//    System.out.println("3\bStart Of Image");
  }

  public void endOfImage(){                                                // 0xD9
//    System.out.println("3\bEnd Of Image");
  }
  @SuppressWarnings("unused")
  public void startOfScan(InputStream in)throws IOException{               // 0xDA
    spp=readIn(in);                                                        // number of components: max 4
    for(int i=0;i<spp;i++){
      int c=readIn(in);                                                    // Component id can be between 0..255
      for(int j=0;(compins[j]!=null)&&(j<4);j++){
        if(compins[j].getId()==c){scanins[i]=compins[j];break;}
      }
      int b=readIn(in);
      scanins[i].setHuffmanTables(dcins[(b>>4)&0x0F],acins[b&0x0F]);
      switch(i){
      case 0: scanins[i].setShift(16); break;   // R        Y
      case 1: scanins[i].setShift( 8); break;   // G        Cb
      case 2: scanins[i].setShift( 0); break;   // B        Cr
      case 3: scanins[i].setShift(24); break;   // alpha
      }
    }
    
	int ss=readIn(in);
    int se=readIn(in);
    
    int b=readIn(in);
    int ah =((b>>4)&0x0F);
    int al = (b    &0x0F);
  }

  public void defineQuantizationTables(InputStream in)throws IOException{  // 0xDB
//    System.out.println("3\bDefine Quantization Tables");

    int[] qt;
    for(int n=0;n<4;n++){                                                  // max 4 tables
      int b=in.read();
      int t=(b>>4)&0x0F;
      int c=b&0x0F;
      switch(t){                                                           // table class
      case 0:                                                              // 8bit table
        qt=new int[64];for(int i=0;i<64;i++){qt[i]=readIn(in);}            // are in zigzag scan order [1]p.40
        break;
      case 1:                                                              // 16bit table
        qt=new int[64];for(int i=0;i<64;i++){qt[i]=(readIn(in)<<8)|readIn(in);}
        break;
      default: return;
      }
      JPEGFastDCTInputStream.normalize(qt); // comment this out if you want to use JPEGDCTInputStream.inverseDCT
      qts[c]=qt;
    }
  }

  public void defineNumberOfLines(InputStream in)throws IOException{       // 0xDC Not allowed in TIFF file
    height = (readIn(in)<<8)|readIn(in);                                      System.out.println("3\bDefine Number of Lines: height="+height);    
  }

  public void defineRestartInterval(InputStream in)throws IOException{     // 0xDD
    Ri     = (readIn(in)<<8)|readIn(in);                                   // System.out.println("3\bDefine Restart Interval: Ri = "+Ri);    
  }

  public void defineHierarchicalProgression(InputStream in)throws IOException{     // 0xDE
    throw new IOException(getClass().getName()+"defineHierarchicalProgression:\n\tDo not support 'Hierarchical Progression' mode.");
  }

  public void expandReferenceComponents(InputStream in)throws IOException{ // 0xDF
    throw new IOException(getClass().getName()+"defineHierarchicalProgression:\n\tDo not support 'expand reference component(s)'.");
  }

  protected void dump(InputStream in)throws IOException{
    int b,i=0;
    while((b=in.read())!=-1){
      System.out.println("appl["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);i++;
    }
  }

  public void app0(InputStream in)throws IOException{dump(in);}            // 0xE0  JFIF
  public void app1(InputStream in)throws IOException{dump(in);}            // 0xE1  Exif
  public void app2(InputStream in)throws IOException{dump(in);}            // 0xE2
  public void app3(InputStream in)throws IOException{dump(in);}            // 0xE3
  public void app4(InputStream in)throws IOException{dump(in);}            // 0xE4
  public void app5(InputStream in)throws IOException{dump(in);}            // 0xE5
  public void app6(InputStream in)throws IOException{dump(in);}            // 0xE6
  public void app7(InputStream in)throws IOException{dump(in);}            // 0xE7
  public void app8(InputStream in)throws IOException{dump(in);}            // 0xE8
  public void app9(InputStream in)throws IOException{dump(in);}            // 0xE9
  public void app10(InputStream in)throws IOException{dump(in);}           // 0xEA
  public void app11(InputStream in)throws IOException{dump(in);}           // 0xEB
  public void app12(InputStream in)throws IOException{dump(in);}           // 0xEC
  public void app13(InputStream in)throws IOException{dump(in);}           // 0xED
  public void app14(InputStream in)throws IOException{dump(in);}           // 0xEE
  public void app15(InputStream in)throws IOException{dump(in);}           // 0xEF
  public void comment(InputStream in)throws IOException{dump(in);}         // 0xFE

  public int read()throws IOException{
    throw new IOException(getClass().getName()+".read():\nInternal Error: Don't support simple read().");
  }

  public int read(int[] buf, int off, int len)throws IOException{
    int y=0;
    for(int row=0;row<mcuRows;row++){
      int x=0;
      for(int col=0;col<mcuCols;col++){
        for(int c=0;c<spp;c++){
          scanins[c].read(
            buf,
            off+y*width+x,
            (mcuHeight<(height-y))?mcuHeight:(height-y),
            (mcuWidth <(width -x))?mcuWidth: (width-x)
          );
        }
        x+=mcuWidth;
      }
      y+=mcuHeight;
    }
    return len;
  }

  // call only if one component and BufferedImage has grayscale type

  public int read(byte[] buf, int off, int len)throws IOException{
    int y=0;
    for(int row=0;row<mcuRows;row++){
      int x=0;
      for(int col=0;col<mcuCols;col++){
        scanins[0].read(
          buf,
          off+y*width+x,
          (mcuHeight<(height-y))?mcuHeight:(height-y),
          (mcuWidth <(width -x))?mcuWidth: (width-x)
        );
        x+=mcuWidth;
      }
      y+=mcuHeight;
    }
    return len;
  }
}

// [1]'JPEG' : ISO/IEC IS 10918-1
//             ITU-T Recommendation T.81
// http://www.w3.org/Graphics/JPEG/itu-t81.pdf
