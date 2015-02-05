package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JFIFInputStream extends JPEGInputStream{

  protected int version;                // 1.02
  protected int units;                  // density unit = 0 => Only the aspect ratio is specified.
                                        // density unit = 1 => Density in pixels per inch.
                                        // density unit = 2 => Density in pixels per centimeter.

  protected int xDensity,yDensity;
  protected int xThumbnail,yThumbnail;

  public JFIFInputStream(InputStream in)throws IOException{
    super(in);
  }

  public int getUnits(){return units;}
  public int getXDensity(){return xDensity;}
  public int getYDensity(){return yDensity;}

  public void app0(InputStream in)throws IOException{                      // 0xE0
    String id="";for(int i=0;i<5;i++){id+=(char)readIn(in);}               // System.out.println("ID="+id);
    if(id.equals("JFIF\0")){
      version =readIn(in)<<8|readIn(in);                                   // System.out.println("Version="+Integer.toHexString(version));
      units   =readIn(in);                                                 // System.out.println("Units="+units);
      xDensity=readIn(in)<<8|readIn(in);                                   // System.out.println("Xdensity="+xDensity);
      yDensity=readIn(in)<<8|readIn(in);                                   // System.out.println("Ydensity="+yDensity);
      xThumbnail=readIn(in);                                               // System.out.println("Xthumbnail="+xThumbnail);
      yThumbnail=readIn(in);                                               // System.out.println("Ythumbnail="+yThumbnail);

    }else if(id.equals("JFXX\0")){
    }
  }

  // The Gimp can put in Exif data. I.e.:
  // E x i f 0 0                        Exif Header
  // M M 0 42 0 0 0 8 0 0 0 0 0 0       Empty TIFF Image. 

  public void app1(InputStream in)throws IOException{                      // 0xE1
    String id="";for(int i=0;i<6;i++){id+=(char)readIn(in);}                System.out.println("ID="+id);
    if(id.equals("Exif\0\0")){                                             // TIFF Header [2]
//      dump(in);
    }
  }

  // http://www.color.org/ICC1V42.pdf

  public void app2(InputStream in)throws IOException{                      // 0xE1
    String id="";byte b;int i=0;
    while((b=(byte)in.read())!=-1){
      id+=(char)b;
      System.out.println("APP2["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);i++;
      if(id.equals("ICC_PROFILE\0")){
        break;
      }
    }
  }

  public void app13(InputStream in)throws IOException{                     // 0xED
    String id="";byte b;int i=0;
    while((b=(byte)in.read())!=-1){
      id+=(char)b;
      System.out.println("APP13["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);i++;
      if(id.equals("Photoshop 3.0\0")){  
        break;
      }
    }
  }

  // http://partners.adobe.com/public/developer/en/ps/sdk/5116.DCT_Filter.pdf p.27

  public void app14(InputStream in)throws IOException{                     // 0xEE
    String id="";byte b;int i=0;
    while((b=(byte)in.read())!=-1){
      id+=(char)b;
      System.out.println("APP14["+i+"] 0x"+Integer.toHexString(b)+" "+(char)((b>=' ')?b:' ')+" "+b);i++;
      if(id.equals("Adobe\0")){
/*
        int version=in.read()|(in.read()<<8);
        int flag0=in.read()|(in.read()<<8);  // not essential
        int flag1=in.read()|(in.read()<<8);  // essential
        int ctc=in.read();
        System.out.println(id);
        System.out.println("Version=0x"+Integer.toHexString(version));
        System.out.println("flag0=0x"+Integer.toHexString(flag0));
        System.out.println("flag1=0x"+Integer.toHexString(flag1));
        System.out.println("ctc=0x"+Integer.toHexString(ctc));
*/
        break;
      }
    }
  }

  public int read(int[] buf, int off, int len)throws IOException{
    double Y,Cb,Cr;
    @SuppressWarnings("unused")
	int    YCbCr,R,G,B,RGB;

    len=super.read(buf,off,len);

    for(int i=0;i<len;i++){

      YCbCr=buf[i];

      Y =((YCbCr>>16)&0x000000FF);
      Cb=((YCbCr>> 8)&0x000000FF);
      Cr=((YCbCr    )&0x000000FF);

      R =(int)(Y                        + 1.402     *(Cr-128.0));   if(R<0){R=0;}else if(R>255){R=255;}
      G =(int)(Y - 0.3441363*(Cb-128.0) - 0.71413636*(Cr-128.0));   if(G<0){G=0;}else if(G>255){G=255;}
      B =(int)(Y + 1.772    *(Cb-128.0));                           if(B<0){B=0;}else if(B>255){B=255;}

      buf[i]=(R<<16)|(G<<8)|B;
    }
    return len;
  }
}

// [1] JPEG File Interchange Format (JFIF)
//     Version 1.02 [1992-09-01]
// http://www.jpeg.org/public/jfif.pdf [last accessed 2005-11-28]

// [2] http://www.media.mit.edu/pia/Research/deepview/exif.html [last accessed 2005-11-28]
