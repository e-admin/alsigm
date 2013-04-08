package uk.co.mmscomputing.device.twain;

public class TwainImageInfo implements TwainConstants{

// DG_IMAGE,DAT_IMAGEINFO,MSG_GET
/*
typedef struct {
   TW_FIX32   XResolution;      // Resolution in the horizontal             
   TW_FIX32   YResolution;      // Resolution in the vertical               
   TW_INT32   ImageWidth;       // Columns in the image, -1 if unknown by DS
   TW_INT32   ImageLength;      // Rows in the image, -1 if unknown by DS   
   TW_INT16   SamplesPerPixel;  // Number of samples per pixel, 3 for RGB   
   TW_INT16   BitsPerSample[8]; // Number of bits for each sample           
   TW_INT16   BitsPerPixel;     // Number of bits for each padded pixel     
   TW_BOOL    Planar;           // True if Planar, False if chunky          
   TW_INT16   PixelType;        // How to interp data; photo interp (TWPT_) 
   TW_UINT16  Compression;      // How the data is compressed (TWCP_xxxx)   
} TW_IMAGEINFO, FAR * pTW_IMAGEINFO;
*/

  TwainSource source;
  byte[]      buf=new byte[42];    // TW_IMAGEINFO
  
  public TwainImageInfo(TwainSource source){
    this.source=source;
  }

  public void get()throws TwainIOException{
    source.call(DG_IMAGE,DAT_IMAGEINFO,MSG_GET,buf);
  }

  public double getXResolution(){        return jtwain.getFIX32(buf,0);}
  public double getYResolution(){        return jtwain.getFIX32(buf,4);}
  public int    getWidth(){              return jtwain.getINT32(buf,8);}
  public int    getHeight(){             return jtwain.getINT32(buf,12);}
  public int    getSamplesPerPixel(){    return jtwain.getINT16(buf,16);}
  public int    getBitsPerSample(int i){ return jtwain.getINT16(buf,18+i*2);}
  public int    getBitsPerPixel(){       return jtwain.getINT16(buf,34);}
  public boolean getPlanar(){            return (jtwain.getINT16(buf,36)!=0);}
  public int    getPixelType(){          return jtwain.getINT16(buf,38);}
  public int    getCompression(){        return jtwain.getINT16(buf,40);}

  public String toString(){
    String s="TwainImageInfo\n";
    s+="\tx-resolution ="+getXResolution()+"\n";
    s+="\ty-resolution ="+getYResolution()+"\n";
    s+="\twidth ="+getWidth()+"\n";
    s+="\theight="+getHeight()+"\n";
    int spp=getSamplesPerPixel();
    s+="\tspp="+spp+"\n";
    for(int i=0;i<spp;i++){
      s+="\tbps["+i+"]="+getBitsPerSample(i)+"\n";
    }
    s+="\tplanar="+getPlanar()+"\n";
    s+="\tpixel type="+getPixelType()+"\n";
    s+="\tcompression="+getCompression()+"\n";
    return s;
  }
}

