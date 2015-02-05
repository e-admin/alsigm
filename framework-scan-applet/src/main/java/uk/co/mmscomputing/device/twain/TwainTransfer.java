package uk.co.mmscomputing.device.twain;

import java.io.*;

public class TwainTransfer implements TwainConstants, TwainITransfer {

  TwainSource source;
  boolean     isCancelled;

  public TwainTransfer(TwainSource source){
    this.source = source;
    isCancelled = false;
  }

  public void initiate()throws TwainIOException{
    commitCancel();
  }

  public void setCancel(boolean isCancelled){              // program cancelled next scan
    this.isCancelled=isCancelled;
  }
  protected void commitCancel()throws TwainIOException{    // use before transfer setup
    if( isCancelled
    && (source.getState()==STATE_TRANSFERREADY)){          // state 6
      throw new TwainUserCancelException();                // state 6 -> 5
    }
  }

  public void finish()throws TwainIOException{}            // state 7 -> 6 called when image available
  public void cancel()throws TwainIOException{}            // state 7 -> 5 called when source (gui) cancelled
  public void cleanup()throws TwainIOException{}           // called once at end of acquisition loop

  public static class NativeTransfer extends TwainTransfer{

    private byte[] imageHandle=new byte[jtwain.getPtrSize()]; // 32 bit ptr size: 4; 64 bit ptr size: 8

    public NativeTransfer(TwainSource source){
      super(source);
    }

    public void initiate()throws TwainIOException{
      super.initiate();
      source.call(DG_IMAGE,DAT_IMAGENATIVEXFER,MSG_GET,imageHandle);
    }

    public void finish()throws TwainIOException{
      long handle=jtwain.getPtr(imageHandle,0);            // System.err.println("Handle = 0x"+Long.toHexString(handle));
      jtwain.transferNativeImage(handle);                  // convert DIB into BufferedImage and tell listeners about image
    }
  }

  public static class FileTransfer extends TwainTransfer{

    protected File file;

    public FileTransfer(TwainSource source){
      super(source);
      file=null;
    }

    protected int getImageFileFormat(){return source.getImageFileFormat();}

    public void    setFile(File f){file=f;}
    public File    getFile(){
      if(file==null){                                                       // make sure we have a valid file name
        String ext = ImageFileFormatExts[getImageFileFormat()];
        try{
          File dir=new File(System.getProperty("user.home"),"mmsc/tmp");dir.mkdirs();
          file=File.createTempFile("mmsctwain",ext,dir);
        }catch(Exception e){
          file=new File("c:\\mmsctwain."+ext);
        }
      }                                                                     // System.err.println(file.getPath());
      return file;
    }

    public void initiate()throws TwainIOException{
      super.initiate();

      String file = getFile().getPath();
      int    iff  = getImageFileFormat();

      byte[] setup=new byte[260];                                           // TW_SETUPFILEXFER
      jtwain.setString(setup,0,file);                                       // file path for new image
      jtwain.setINT16(setup,256,iff);                                       // setup.Format = i.e. TWFF_BMP;
      jtwain.setINT16(setup,258,0);                                         // setup.VRefNum = 0;  Mac only

      source.call(DG_CONTROL,DAT_SETUPFILEXFER,MSG_SET,setup);              // tell source file path and image type
			source.call(DG_IMAGE,DAT_IMAGEFILEXFER,MSG_GET,null);                 // tranfer data
    }

    public void finish()throws TwainIOException{
      jtwain.transferFileImage(file);                                       // tell listeners about new image file
    }

    public void cancel()throws TwainIOException{
      if((file!=null)&&file.exists()){
        file.delete();                                                      // delete file if it exists
      }
    }

    public void cleanup()throws TwainIOException{
      setFile(null);                                                        // default behaviour is to setup new temp file every time
    }
  }

  public static class MemoryTransfer extends TwainTransfer{

    private byte[]  imx     = new byte[(jtwain.getPtrSize()==4)?38:42];     // TW_IMAGEMEMXFER 32bit: 38; 64bit: 42
    private Info    info;

    protected int   minBufSize=-1;                                          // default -1 don't care: accept sources preferred buffer size
    protected int   maxBufSize=-1;
    protected int   preferredSize=-1;

    public MemoryTransfer(TwainSource source){
      super(source);
    }

    protected void retrieveBufferSizes()throws TwainIOException{
      byte[] setup=new byte[12];                                            // TW_SETUPMEMXFER
      jtwain.setINT32(setup,0,minBufSize);                                  
      jtwain.setINT32(setup,4,maxBufSize);
      jtwain.setINT32(setup,8,preferredSize);
      source.call(DG_CONTROL,DAT_SETUPMEMXFER,MSG_GET,setup);               // get preferred buffer size
      minBufSize=jtwain.getINT32(setup,0);                                  
      maxBufSize=jtwain.getINT32(setup,4);
      preferredSize=jtwain.getINT32(setup,8);
    }

    public void initiate()throws TwainIOException{
      super.initiate();
      retrieveBufferSizes();                                                // 
      jtwain.nnew(imx,preferredSize);                                       // allocate native memory buffer
      byte[] buf = new byte[preferredSize];
      info       = new Info(imx,buf);
      while(true){                                                          // while(call does not throw TwainIOException){
  		  source.call(DG_IMAGE,DAT_IMAGEMEMXFER,MSG_GET,imx);                 //   tranfer data
        int bytesWritten = jtwain.getINT32(imx,22);
        int bytesCopied  = jtwain.ncopy(buf,imx,bytesWritten);              //   copy from native buffer to java buffer
        if(bytesCopied==bytesWritten){
          jtwain.transferMemoryBuffer(info);                                //   and tell listeners about new image buffer
        }
      }
    }

    public void finish()throws TwainIOException{
      int bytesWritten = jtwain.getINT32(imx,22);
      int bytesCopied  = jtwain.ncopy(info.getBuffer(),imx,bytesWritten);   //   copy from native buffer to java buffer
      if(bytesCopied==bytesWritten){
        jtwain.transferMemoryBuffer(info);                                  //   and tell listeners about new image data buffer
      }
    }

    public void cleanup()throws TwainIOException{
      jtwain.ndelete(imx);
    }

    public static class Info{

      private byte[] imx;
      private byte[] buf;
      private int    len;

      Info(byte[] imx,byte[] buf){this.imx=imx;this.buf=buf;}

      public byte[] getBuffer(){return buf;}
      public int getCompression(){return jtwain.getINT16(imx,0);}
      public int getBytesPerRow(){return jtwain.getINT32(imx,2);}
      public int getWidth(){return jtwain.getINT32(imx,6);}         // columns
      public int getHeight(){return jtwain.getINT32(imx,10);}       // rows
      public int getLeft(){return jtwain.getINT32(imx,14);}         // xoffset
      public int getTop(){return jtwain.getINT32(imx,18);}          // yoffset
      public int getLength(){return jtwain.getINT32(imx,22);}       // bytesWritten

      public int getMemFlags(){return jtwain.getINT32(imx,26);}
      public int getMemLength(){return jtwain.getINT32(imx,30);}
      public long getMemPtr(){return jtwain.getPtr(imx,34);}

      public String toString(){
        String s=getClass().getName()+"\n";
        s+="\tcompression = "+getCompression()+"\n";
        s+="\tbytes per row = "+getBytesPerRow()+"\n";
        s+="\ttop = "+getTop()+" left = "+getLeft()+" width = "+getWidth()+" height = "+getHeight()+"\n";
        s+="\tbytes = "+getLength()+"\n";

        s+="\tmemory flags   = 0x"+Integer.toHexString(getMemFlags())+"\n";
        s+="\tmemory length  = "+getMemLength()+"\n";
        s+="\tmemory pointer = 0x"+Long.toHexString(getMemPtr())+"\n";

        return s;
      }
    }
  }
}

/*
System.err.println("min: "+jtwain.getINT32(setup,0));
System.err.println("max: "+jtwain.getINT32(setup,4));
System.err.println("preferred: "+preferred);
*/

/*
System.err.println("compression "+jtwain.getINT16(imx,0));
System.err.println("bytesPerRow "+jtwain.getINT32(imx,2));
System.err.println("columns "+jtwain.getINT32(imx,6));
System.err.println("rows "+jtwain.getINT32(imx,10));
System.err.println("xoffset "+jtwain.getINT32(imx,14));
System.err.println("yoffset "+jtwain.getINT32(imx,18));
System.err.println("bytes written "+jtwain.getINT32(imx,22));

System.err.println("imx flags 0x"+Integer.toHexString(jtwain.getINT32(imx,26)));
System.err.println("imx len "+jtwain.getINT32(imx,30));
System.err.println("imx theMem 0x"+Integer.toHexString(jtwain.getPTR(imx,34)));
*/
