package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGBitInputStream extends FilterInputStream implements JPEGConstants{

  JPEGInputStream jpeg;                        // wrapping jpeg input stream. if marked segment then call this object 
  private   int bitBuffer;                     // entropy buffer; huffman coding
  private   int bitCount;


  public JPEGBitInputStream(InputStream in,JPEGInputStream jpeg)throws IOException{
    super(in);this.jpeg=jpeg;
  }

  private int readIn()throws IOException{
    int b=in.read();
    if(b==-1){
      IOException ioe=new IOException(getClass().getName()+"readIn:\n\tUnexpected end of file.");
      ioe.printStackTrace();
      throw ioe;
    }
    return b;
  }

  protected InputStream readMarkedSegment()throws IOException{
    int    length =((readIn()<<8)|readIn())-2;
    byte[] data   = new byte[length];
    int    len    = in.read(data);

    if(len!=length){throw new IOException(getClass().getName()+".readMarkedSegment:\n\tUnexpected end of file.");}
    return new ByteArrayInputStream(data);
  }

  //  JPEG Syntax:
  //  1-coded image data
  //  2-marked segments for additional information: 
  //    a-0xFF 0x00  => 'byte stuffing' 0xFF 0x00 is interpreted as image data 0xFF
  //    b-0xFF 0xmarker
  //    c-0xFF 0xmarker 0xlenHigh 0xlenLow MarkerPayload[0xlenHigh<<8 | 0xlenLow]

  public int readCompressed()throws IOException{
    int b,marker;
    while(true){
      b=readIn();
      if(b==MARK){
        do{marker=readIn();}while(marker==MARK);                                         // [1]p.30 B.1.1.2
        switch(marker){
        case 0:    /*System.out.println("b="+Integer.toBinaryString(b));*/ return 0x00FF;// byte stuffing
        case SOF0:    // BaseLine sequential DCT, non differential, Huffman coding
        case SOF1:    // Extended sequential DCT, non differential, Huffman coding
                   jpeg.startOfFrame(readMarkedSegment(),marker&0x000F);
          break;        

        case DHT:  jpeg.defineHuffmanTables(readMarkedSegment());          break;        // Define Hufman Tables
        case DAC:  jpeg.defineArithmeticConditioning(readMarkedSegment()); break;        // Define Arithmetic Conditioning
        case RST0:case RST1:case RST2:case RST3:case RST4:case RST5:case RST6:case RST7:
                   jpeg.restartIntervalTermination(marker&0x0007);
                   bitBuffer=0;bitCount=0;
          break;
        case SOI:  jpeg.startOfImage();                                    break;        // Start Of Image        
        case EOI:  jpeg.endOfImage();                                      return -1;    // End Of Image        
        case SOS:  jpeg.startOfScan(readMarkedSegment());bitCount = 0;     break;        // Start of Scan;reset entropy decoder buffer
        case DQT:  jpeg.defineQuantizationTables(readMarkedSegment());     break;        // Define Quantization Tables
        case DNL:  jpeg.defineNumberOfLines(readMarkedSegment());          break;        // Define Number of Lines
        case DRI:  jpeg.defineRestartInterval(readMarkedSegment());        break;        // Define Restart Interval
        case DHP:  jpeg.defineHierarchicalProgression(readMarkedSegment());break;        // Define Hierarchical Progression
        case EXP:  jpeg.expandReferenceComponents(readMarkedSegment());    break;        // Expand reference component(s)

        case APP0: jpeg.app0(readMarkedSegment());                         break;        // Application i.e. JFIF
        case APP1: jpeg.app1(readMarkedSegment());                         break;        // Application i.e. Exif
        case APP2: jpeg.app2(readMarkedSegment());                         break;        // Application
        case APP3: jpeg.app3(readMarkedSegment());                         break;        // Application
        case APP4: jpeg.app4(readMarkedSegment());                         break;        // Application
        case APP5: jpeg.app5(readMarkedSegment());                         break;        // Application
        case APP6: jpeg.app6(readMarkedSegment());                         break;        // Application
        case APP7: jpeg.app7(readMarkedSegment());                         break;        // Application
        case APP8: jpeg.app8(readMarkedSegment());                         break;        // Application
        case APP9: jpeg.app9(readMarkedSegment());                         break;        // Application
        case APP10:jpeg.app10(readMarkedSegment());                        break;        // Application
        case APP11:jpeg.app11(readMarkedSegment());                        break;        // Application
        case APP12:jpeg.app12(readMarkedSegment());                        break;        // Application
        case APP13:jpeg.app13(readMarkedSegment());                        break;        // Application
        case APP14:jpeg.app14(readMarkedSegment());                        break;        // Application
        case APP15:jpeg.app15(readMarkedSegment());                        break;        // Application

        case COM:  jpeg.comment(readMarkedSegment());                      break;        // Comment
        default:   throw new IOException(getClass().getName()+".readCompressed:\n\tUnknown marker = "+Integer.toHexString(marker));
        }
      }else{                                                                        
//      System.out.println("b="+Integer.toBinaryString(b));
        return b;                                                                   // coded image data
      }
    }
  }

  public void start()throws IOException{
    int b=readCompressed();
    if(b==-1){return;}   // no image data as such; [1] p.47 B.5 abbreviated format for table specification data; i.e. TIFF JPEGTables tag
    bitBuffer=(b<<24);
    bitCount=8;
  }

  public int readBit()throws IOException{
    if(bitCount==0){
      int b=readCompressed();
      if(b==-1){return -1;}
      bitBuffer=(b<<24);
      bitCount=8;
    }
    int bit=bitBuffer>>>31;
    bitBuffer<<=1;
    bitCount--;
    return bit;
  }

  public int readBits(int neededBits)throws IOException{
    if(neededBits==0){return 0;}
    while(bitCount<neededBits){
      int b=readCompressed();
      if(b==-1){return -1;}
      bitBuffer|=(b<<(24-bitCount));
      bitCount+=8;
    }
    int bits=bitBuffer>>>(32-neededBits);
    bitBuffer<<=neededBits;
    bitCount-=neededBits;
    return bits;
  }
}

//  [1] 'JPEG' : ISO/IEC IS 10918-1
//               ITU-T Recommendation T.81
//  http://www.w3.org/Graphics/JPEG/itu-t81.pdf