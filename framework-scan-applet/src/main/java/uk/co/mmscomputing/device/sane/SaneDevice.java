package uk.co.mmscomputing.device.sane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import uk.co.mmscomputing.concurrent.Semaphore;
import uk.co.mmscomputing.device.sane.gui.SaneAcquirePanel;
import uk.co.mmscomputing.device.sane.option.Descriptor;
import uk.co.mmscomputing.device.sane.option.FixedDesc;
import uk.co.mmscomputing.device.sane.option.FixedRange;
import uk.co.mmscomputing.device.sane.option.IntDesc;
import uk.co.mmscomputing.device.sane.option.IntList;
import uk.co.mmscomputing.device.sane.option.IntRange;
import uk.co.mmscomputing.device.sane.option.StringDesc;
import uk.co.mmscomputing.device.sane.option.StringList;
import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;
import es.ieci.tecdoc.fwktd.applets.scan.utils.JsaneConfig;

public class SaneDevice implements SaneConstants{

  static final private int buffersize = 1 << 15;

  private   String    name;
  private   int       saneHandle;
  private   boolean   cancelled,userCancelled,adfmode;
  private   int       state,adfcount;
  private   boolean   showUI;                // show user interface
  private   boolean   showPB;                // show user progress bar
  private   JProgressBar pbar;               // progressbar to be shown
  private   Semaphore blocker;               // sane function are non reentrant; make sure we call them one after another

  SaneDevice(String device){
    super();
    saneHandle = 0;
    name       = device;
    cancelled  = false;
    userCancelled  = false;
    adfmode    = false;
    adfcount   = -1;                         // -1: no limit; 0: don't scan; >0 scan count pages
    state      = SANE_STATE_EXIT;
    showUI     = true;
    showPB     = true;
    pbar       = null;
    blocker    = new Semaphore(1,true);
  }

  public String  getName(){                return name;}

  public       int     getState(){               return state;}
               void    setState(int s){          
            	   state=s;jsane.signalStateChange(this);
            	   //System.out.println("Scanner State "+s);
            	   }

  synchronized boolean isState(int state){       return this.state==state;}
  public       boolean isBusy(){                 return !isState(SANE_STATE_EXIT);}

  public       void checkBusy()throws SaneIOException{       
    if(isBusy()){throw new SaneIOException(getClass(),"checkBusy","SaneDevice.ex.isbusy",name);}
  }

  public boolean getADFMode(){             return adfmode;}
  public void    setADFMode(boolean mode){ adfmode=mode;}

  public int     getADFCount(){            return adfcount;}
  public void    setADFCount(int count){   adfcount=count;}

  public boolean isOpen(){
    return (state==SANE_STATE_OPEN)||(state==SANE_STATE_READY);
  }

  public void    setShowUserInterface(boolean enable){showUI=enable;}

  public void    setShowProgressBar(boolean enable){showPB=enable;}
  public void    setProgressBar(JProgressBar pbar){this.pbar=pbar;}
  private void   setProgressBar(String str){if(pbar!=null){pbar.setString(str);}}
  private void   resetProgressBar(int max){if(pbar!=null){pbar.setValue(0);pbar.setMaximum(max);}}
  private void   setProgressBar(int progress){
    if(pbar!=null){
      pbar.setValue(progress);
      if(progress>10*1024*1024){
        progress>>=20;
        pbar.setString(""+progress+" MB");
      }else if(progress>10*1024){
        progress>>=10;
        pbar.setString(""+progress+" kB");
      }
    }
  }

  // option descriptors

  public int getNumberOfOptions()throws SaneIOException{
    return jsane.getNumberOfOptions(saneHandle);
  }

  public OptionDescriptor getOptionDescriptor(int option){
    return jsane.getOptionDescriptor(saneHandle,option);
  }

  public OptionDescriptor[] getOptionDescriptors()throws SaneIOException{
    OptionDescriptor[] list=new OptionDescriptor[getNumberOfOptions()];
    for(int i=0;i<list.length;i++){
      try{
        list[i]=getOptionDescriptor(i); 
      }catch(Exception e){ 
        // The SANE 'test' scanner returns a lot of 'Invalid arguments' here.
        // Strange, but I guess that is how it is supposed to be.
        list[i]=null;
      }catch(Error e){ 

      }
    }
    return list;
  }

  // option negotiation

  public void setOption(String option,String value)throws ScannerIOException{
    int n=getNumberOfOptions();
    for(int i=0;i<n;i++){
  	  Descriptor od= null;
	  String name = null;
	  try{
	      od=(Descriptor)getOptionDescriptor(i);
	      name=od.getName();
	  }catch (Exception e) {
	  }

	  if(option.equals(name)){
	    if(od instanceof StringDesc){
	      String res=od.setStringValue(value);
	      if(res.equals(value)){return;}
	    }else if(od instanceof FixedDesc){
	      double val=Double.parseDouble(value);                         // check it is number
	      double res=Double.parseDouble(od.setStringValue(0,value));
	      if(res==val){return;}
	    }else if(od instanceof IntDesc){
	      double val=Double.parseDouble(value);                         // check it is number
	      double res=Double.parseDouble(od.setStringValue(0,value));
	      if(res==val){return;}
	    }
	    String[] args={option,value};
	    throw new SaneIOException(getClass(),"setOption","SaneDevice.ex.couldnotsetoption",args);
	  }

    }
    throw new ScannerIOException(getClass().getName()+".setOption:\n\tCould not find option '"+option);
  }

  public void setOption(String option,double value)throws ScannerIOException{
    int n=getNumberOfOptions();
    for(int i=0;i<n;i++){
    	  Descriptor od= null;
    	  String name = null;
    	  try{
		      od=(Descriptor)getOptionDescriptor(i);
		      name=od.getName();
    	  }catch (Exception e) {
		  }
	      
	      if(option.equals(name)){
	        if(od instanceof FixedDesc){
	          double res=Double.parseDouble(od.setStringValue(0,""+value));
	          if(Math.rint(res)==value){
	        	  return;
	          }
	        }else if(od instanceof IntDesc){
	          double res=Double.parseDouble(od.setStringValue(0,""+((int)value)));
	          if(res==value){
	        	  return;
	          }
	        }
	        String[] args={option,""+value};
	        throw new SaneIOException(getClass(),"setOption","SaneDevice.ex.couldnotsetoption",args);
	      }
    }
    throw new SaneIOException(getClass(),"setOption","SaneDevice.ex.couldnotfindoption",option);
  }

  public Descriptor getOption(String option)throws ScannerIOException{
    int n=getNumberOfOptions();
    for(int i=0;i<n;i++){
    	try{
	      Descriptor od=(Descriptor)getOptionDescriptor(i); 
	      String name=od.getName();
	      if(option.equals(name)){
	        return od;
	      }
    	}catch (Exception e) {
		}
    }
    throw new SaneIOException(getClass(),"getOption","SaneDevice.ex.couldnotfindoption",option);
  }

  public void setResolution(double dpi)throws ScannerIOException{
    // Unit : DPI
    // Type : INT or FIXED
    setOption("resolution",dpi);
  }

  public void setRegionOfInterest(int x, int y, int width, int height)throws ScannerIOException{
    // Unit : PIXEL

    Descriptor tlx = getOption("tl-x");                        // convert into MM if necessary
    if(tlx.getUnit()!=SANE_UNIT_PIXEL){                        // only PIXE: and MM allowed
      int res = getOption("resolution").getWordValue(0);
      x = (int)tlx.convertPixels2Units(res,x);
      y = (int)tlx.convertPixels2Units(res,x);
      width  = (int)tlx.convertPixels2Units(res,width);
      height = (int)tlx.convertPixels2Units(res,height);
    }
    setOption("tl-x",x);
    setOption("tl-y",y);
    setOption("br-x",x+width);
    setOption("br-y",y+height);
  }


  public void setRegionOfInterest(double x, double y, double width, double height)throws ScannerIOException{
    // Unit : MM
    // to do: convert into Pixel if necessary

    setOption("tl-x",x);
    setOption("tl-y",y);
    setOption("br-x",x+width);
    setOption("br-y",y+height);
  }

  public void select(String name)throws ScannerIOException{
    select(name,true);
  }

  public void select(String name,boolean onlyLocal)throws ScannerIOException{

    if(state!=SANE_STATE_INITIALIZE){
      throw new SaneIOException(getClass(),"select","SaneDevice.ex.calledinwrongstate","SANE_STATE_INITIALIZE");
    }

    String[] devices=jsane.getDevices(onlyLocal);
    for(int i=0;i<devices.length;i++){        
      if(devices[i].equals(name)){
        name=devices[i];return;
      }
    }
    throw new SaneIOException(getClass(),"select","SaneDevice.ex.cannotfinddevice",name);
  }

  // sane functions;  the jsane.functions are not reentrant! 

  private void init()throws SaneIOException{
    try{
      blocker.acquire();
      jsane.init();
      setState(SANE_STATE_INITIALIZE);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
  }

  private void exit()throws SaneIOException{
    try{
      blocker.acquire();
      jsane.exit();
      setState(SANE_STATE_EXIT);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
  }

  private void start()throws SaneIOException{
    if(cancelled){return;}
    try{
      blocker.acquire();
      if(cancelled){return;}
      jsane.start(saneHandle);
      setState(SANE_STATE_READY);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
  }

  private void close()throws SaneIOException{
    try{
      blocker.acquire();
      jsane.close(saneHandle);
      setState(SANE_STATE_CLOSED);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
  }

  private void getParameters(Parameters p)throws SaneIOException{
    p.format=-1;
    p.lastFrame=SANE_TRUE;
    if(cancelled){return;}
    try{
      blocker.acquire();
      if(cancelled){return;}
      jsane.getParameters(saneHandle,p);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
  }

  private int read(byte[] buf,int off,int len)throws SaneIOException{
    if(cancelled){return -1;}
    try{
      blocker.acquire();
      if(cancelled){return -1;}
      return jsane.read(saneHandle,buf,off,len);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
    return -1;
  }

  private void cancel(){
    if(!isOpen()||cancelled){return;}
    try{
      blocker.acquire();
      if(!isOpen()||cancelled){return;}
      jsane.cancel(saneHandle);            // System.err.println("cancel sane handle 0x"+Integer.toHexString(saneHandle));
      cancelled=true;
      setState(SANE_STATE_CANCELLED);
    }catch(InterruptedException ie){
    }finally{
      blocker.release();
    }
  }

  public void setCancel(boolean c){
    if(c){cancel();}
    userCancelled=c;
  }

  public boolean getCancel(){return userCancelled;}

  // image acquisition

  private BufferedImage createGrayImage(Parameters p, InputStream in)throws IOException{
    try{
      in=new BufferedInputStream(in,buffersize);
      if(p.depth==1){                                       // P4 black&white
        int width  =p.bytesPerLine;                         // width in bytes
        int height =p.lines;

        resetProgressBar(height);
        setProgressBar(0);

//        System.err.println(getClass().getName()+".createGrayImage[BW]:\n\tp.bytesPerLine="+width);

        BufferedImage  image=new BufferedImage(width<<3,height,BufferedImage.TYPE_BYTE_BINARY);
        Raster         raster = image.getRaster();
        DataBufferByte db     = (DataBufferByte)raster.getDataBuffer();    
        byte[]         data   = db.getData();

        int k=0;
        for(int y=0;y<height;y++){
          for(int x=0;x<width;x++){
            data[k++]=(byte)(~in.read());
          }
          if((y%100)==0){setProgressBar(y);}
        }
        setProgressBar(height);
        return image;
      }else if(p.depth==8){
        int width  =p.bytesPerLine;
        int height =p.lines;

        resetProgressBar(height);
        setProgressBar(0);

//        System.err.println(getClass().getName()+".createGrayImage[GRAY8]:\n\tp.bytesPerLine="+width);

        BufferedImage  image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        Raster         raster = image.getRaster();
        DataBufferByte db     = (DataBufferByte)raster.getDataBuffer();    
        byte[]         data   = db.getData();

        int k=0;
        for(int y=0;y<height;y++){
          for(int x=0;x<width;x++){
            data[k++]=(byte)(in.read());
          }
          if((y%100)==0){setProgressBar(y);}
        }
        setProgressBar(height);
        return image;
      }else if(p.depth==16){
        int width  =p.bytesPerLine>>1;
        int height =p.lines;

        resetProgressBar(height);
        setProgressBar(0);

//        System.err.println(getClass().getName()+".createGrayImage[GRAY16]:\n\tp.bytesPerLine="+width);

        BufferedImage    image=new BufferedImage(width,height,BufferedImage.TYPE_USHORT_GRAY);
        Raster           raster = image.getRaster();
        DataBufferUShort db     = (DataBufferUShort)raster.getDataBuffer();    
        short[]          data   = db.getData();

        int k=0;
        if(isLitteEndian){
          for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
              data[k++]=(short)((in.read()&0x00FF)|((in.read()&0x00FF)<<8));
            }
            if((y%100)==0){setProgressBar(y);}
          }
        }else{
          for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
              data[k++]=(short)(((in.read()&0x00FF)<<8)|(in.read()&0x00FF));
            }
            if((y%100)==0){setProgressBar(y);}
          }
        }
        setProgressBar(height);
        return image;
      }else{
        throw new SaneIOException(getClass(),"createGrayImage","SaneDevice.ex.grayscaledepthnotsupported",""+p.depth);
      }
    }catch(Exception e){                                  // couldn't start device
      jsane.signalException(e);
    }finally{
      in.close();
    }
    return null;
  }

  private BufferedImage createRGBImage(Parameters p, InputStream in)throws IOException{

    if(p.depth==1){                                       // sane "test" scanner allows colour/1bit
      throw new SaneIOException(getClass(),"createRGBImage","SaneDevice.ex.rgbdepthnotsupported",""+p.depth);
    }

    try{
      int width  =p.bytesPerLine/((p.depth>>3)*3);
      int height =p.lines;

//      System.err.println(getClass().getName()+".createRGBImage:\n\tp.bytesPerLine="+p.bytesPerLine);

      BufferedImage   image  = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
      Raster          raster = image.getRaster();
      DataBufferInt   db     = (DataBufferInt)raster.getDataBuffer();    
      int[]           data   = db.getData();

      in=new BufferedInputStream(in,buffersize);

      resetProgressBar(height);
      setProgressBar(0);

      @SuppressWarnings("unused")
	int r,g,b,k=0,pixel;
      if(p.depth==8){                                       // P6 colour
        for(int y=0;y<height;y++){
          for(int x=0;x<width;x++){
            r=in.read()&0x00FF;
            g=in.read()&0x00FF;
            b=in.read()&0x00FF;
            data[k++]=0xFF000000|(r<<16)|(g<<8)|b;
          }
          if((y%100)==0){setProgressBar(y);}
        }
      }else if(p.depth==16){
        if(isLitteEndian){
          for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
              in.read();r=in.read()&0x00FF;                   // scale to 0..255 range; use most significant byte;
              in.read();g=in.read()&0x00FF;
              in.read();b=in.read()&0x00FF;
              data[k++]=0xFF000000|(r<<16)|(g<<8)|b;
            }
            if((y%100)==0){setProgressBar(y);}
          }
        }else{
          for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
              r=in.read()&0x00FF;in.read();                   // scale to 0..255 range; use most significant byte;
              g=in.read()&0x00FF;in.read();
              b=in.read()&0x00FF;in.read();
              data[k++]=0xFF000000|(r<<16)|(g<<8)|b;
            }
            if((y%100)==0){setProgressBar(y);}
          }
        }
      }else{
        throw new SaneIOException(getClass(),"createRGBImage","SaneDevice.ex.rgbdepthnotsupported",""+p.depth);
      }
      setProgressBar(height);
      return image;
    }finally{
      in.close();
    }
  }

  private BufferedImage create3FRGBImage(Parameters p,InputStream red,InputStream green,InputStream blue)throws IOException{
    try{
      if((red==null)||(green==null)||(blue==null)){
        throw new SaneIOException(getClass(),"create3FRGBImage","SaneDevice.ex.rgbframesmissing");
      }

//      System.err.println(getClass().getName()+".create3FRGBImage: "+p.bytesPerLine);

      int width  =p.bytesPerLine/(p.depth>>3);
      int height =p.lines;

      BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
      Raster        raster = image.getRaster();
      DataBufferInt db     = (DataBufferInt)raster.getDataBuffer();    
      int[]         data   = db.getData();

      @SuppressWarnings("unused")
	int r,g,b,k=0,pixel,sample;

      red  =new BufferedInputStream(red,buffersize);
      green=new BufferedInputStream(green,buffersize);
      blue =new BufferedInputStream(blue,buffersize);

      resetProgressBar(height);
      setProgressBar(0);
      if(p.depth==8){
        for(int y=0;y<height;y++){
          for(int x=0;x<width;x++){
            r=red.read();
            g=green.read();
            b=blue.read();
            data[k++]=0xFF000000|(r<<16)|(g<<8)|b;
          }
          if((y%100)==0){setProgressBar(y);}
        }
      }else if(p.depth==16){
        if(isLitteEndian){
          for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                red.read();
              r=red.read();             
                green.read();
              g=green.read();
                blue.read();
              b=blue.read();
              data[k++]=0xFF000000|(r<<16)|(g<<8)|b;
            }
            if((y%100)==0){setProgressBar(y);}
          }
        }else{
          for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
              r=red.read();             
                red.read();
              g=green.read();
                green.read();
              b=blue.read();
                blue.read();
              data[k++]=0xFF000000|(r<<16)|(g<<8)|b;
            }
            if((y%100)==0){setProgressBar(y);}
          }
        }
      }else{
        throw new SaneIOException(getClass(),"create3FRGBImage","SaneDevice.ex.rgbdepthnotsupported",""+p.depth);
      }
      setProgressBar(height);
      return image;
    }finally{
      red.close();green.close();blue.close();
    }
  }

  private File readImageData(Parameters p)throws IOException{
    File file=File.createTempFile("mmsc_sane_",null);
    OutputStream out=new BufferedOutputStream(new FileOutputStream(file),buffersize);

    int len; byte[] buf=new byte[buffersize];
    try{
      start();                                            // when scanning 3 frame images need to call start otherwise getParameter will return wrong values
      getParameters(p);                                   // p.lines might be -1
      resetProgressBar(p.bytesPerLine*p.lines);

      int size=0;
      try{
        while((len=read(buf,0,buf.length))!=-1){
          out.write(buf,0,len);
          size+=len;
          setProgressBar(size);
        }
      }catch(SaneIOException e){
        jsane.signalException(e);
      }
      if(p.lines==-1){p.lines=size/p.bytesPerLine;}
//      getParameters(p);                                 // gives wrong values here 
    }finally{
      out.close();
    }
    return file;
  }

  public BufferedImage getImage(boolean onlyOne)throws IOException{ // also called from SanePreviewPanel
    BufferedImage img = getImage();
    cancel();
    return img;
  }

  public BufferedImage getImage()throws IOException{ // also called from SanePreviewPanel
    File[]        files=new File[3];
    Parameters    p    =new Parameters();

    cancelled = false;
    try{
      int frames;
      for(frames=0;(frames<files.length)&&(p.lastFrame==SANE_FALSE);frames++){
        File file=readImageData(p);
        if(cancelled){break;}
        switch(p.format){
        case SANE_FRAME_GRAY: files[0]=file; break;
        case SANE_FRAME_RGB:  files[0]=file; break;
        case SANE_FRAME_RED:  files[0]=file; break;
        case SANE_FRAME_GREEN:files[1]=file; break;
        case SANE_FRAME_BLUE: files[2]=file; break;
        default: p.lastFrame=SANE_TRUE;frames=-1;break;
        }
      }
      if(!cancelled){
        if(frames==3){
          return create3FRGBImage(p,new FileInputStream(files[0]),new FileInputStream(files[1]),new FileInputStream(files[2]));
        }else if(frames==1){
          if(p.format==SANE_FRAME_RGB){
            return createRGBImage(p,new FileInputStream(files[0]));
          }else if(p.format==SANE_FRAME_GRAY){
            return createGrayImage(p,new FileInputStream(files[0]));
          }
        }
      }
    }finally{
      for(int i=0;i<files.length;i++){
        if(files[i]!=null){files[i].delete();}
      }
    }
    return null;
  }

  ArrayList<BufferedImage> acquire(){	
    ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    try{
      setProgressBar((JProgressBar)null);

      init();
      try{
        adfcount=-1;
        userCancelled=false;
        saneHandle=jsane.open(name);
        setState(SANE_STATE_OPEN);

        jsane.negotiateOptions(this);

        if(showUI){
          //Semaphore s=new Semaphore(0,true);
          //new SaneAcquirePanel(this,s).display();
          //s.acquire();
        	new SaneAcquirePanel(this);
        }
        ProgressMonitor pm=(showPB)?new ProgressMonitor(name):null;
        setProgressBar(pm);
        setProgressBar("preparing "+name);
        try{
          if(!userCancelled){
            if(pm!=null){pm.open();}
            try{
              do{
            	
                //jsane.signalImage(getImage());
                adfcount--;
                images.add(getImage());
              }while(adfmode&&(adfcount!=0));                     // if not in adf-mode we scan only once.
            }catch(SaneNoDocumentsException snde){                // this is the normal end of document condition for adf scanners !!!
              if(!adfmode){
            	  throw snde;
              }
            }catch (Exception e) {
            	 jsane.signalException(e);
            }finally{
              cancel();
            }
          }
        }finally{
          close();
          if(pm!=null){pm.close();}
        }
      }catch(Exception e){
        jsane.signalException(e);
      }finally{
        exit();
      }
    }catch(Exception e){
      jsane.signalException(e);
    }
//    System.setProperty(getClass().getName()+".busy","false");       // applets
	return images;
  }

  static private boolean isLitteEndian;

  static{
    isLitteEndian=true;
    String endian=System.getProperty("sun.cpu.endian");
    if(endian!=null){isLitteEndian=endian.equals("little");}
  }

  @SuppressWarnings("serial")
private class ProgressMonitor extends JProgressBar{
    JFrame              dialog;

    ProgressMonitor(String device){
      super(0,100);
      setStringPainted(true);

      JPanel panel=new JPanel();

      JPanel q=new JPanel();
      q.setLayout(new BorderLayout());
      Border border = q.getBorder();
      Border margin = new EmptyBorder(10,10,5,10);
      q.setBorder(new CompoundBorder(border, margin));
      q.add(new JLabel("Scanner:"),BorderLayout.NORTH);

      //JLabel img=new JLabel(new JarImageIcon(getClass(),"32x32/scanner.png"));
      //img.setBorder(new EmptyBorder(10,10,10,10));
      //q.add(img,BorderLayout.WEST);

      JLabel msg=new JLabel("<html>"+device+"</html>");
      msg.setBorder(new EmptyBorder(10,10,10,10));
      q.add(msg,BorderLayout.CENTER);

      JPanel p=new JPanel();
      p.setLayout(new BorderLayout());

      setBorder(new EmptyBorder(5,0,5,0));
      p.add(this,BorderLayout.NORTH);

      JPanel buttons=new JPanel();
      buttons.setLayout(new GridLayout(0,3));
      buttons.add(new JPanel());

      JButton button=new JButton(
        new AbstractAction("cancel"){public void actionPerformed(ActionEvent ev){setCancel(true);}}
      );
      buttons.add(button);
      buttons.add(new JPanel());

      p.add(buttons,BorderLayout.CENTER);
      q.add(p,BorderLayout.SOUTH);

      panel.setLayout(new BorderLayout());
      panel.setOpaque(true);
      panel.add(q,BorderLayout.NORTH);

      dialog = new JFrame(jsane.getResource("SaneDevice.ProgressMonitor.title"));
      dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      dialog.setContentPane(panel);
      dialog.pack();
    }

    void open(){
      dialog.setLocationRelativeTo(null);
      dialog.toFront();
      dialog.setVisible(true);
    }

    void close(){
      dialog.dispose();
    }
  }
  
  
  public boolean getDuplexSupport(){
	  boolean supported = false;
	  try{
	      init();
	      try{
		        adfcount=-1;
		        userCancelled=false;
		        saneHandle=jsane.open(name);
		        setState(SANE_STATE_OPEN);
		
				StringList descriptor = (StringList) getOption("source");
				String[] a = descriptor.getList();
				for (String string : a) {
					if(string.contains("Duplex")){
						return true;
					}
				}
				
				StringList descriptor1 = (StringList) getOption("adf-mode");
				String[] b = descriptor1.getList();
				for (String string : b) {
					if(string.contains("Duplex")){
						return true;
					}
				}
				
	      }catch(Exception e){
	        jsane.signalException(e);
	      }finally{
	    	  close();
	        exit();
	      }
    }catch(Exception e){
      jsane.signalException(e);
    }
	return supported;
  }

public int[] getPixelTypeList() {
	ArrayList<Integer> suported = new ArrayList<Integer>();
	  try{
	      init();
	      try{
		        adfcount=-1;
		        userCancelled=false;
		        saneHandle=jsane.open(name);
		        setState(SANE_STATE_OPEN);
		
				StringList descriptor = (StringList) getOption("mode");
				String[] a = descriptor.getList();

				for (int cont=0; cont<a.length;cont++) {
					for (String string : JsaneConfig.getString("SANE_MODE_GRIS")) {
						if(a[cont].equals(string)){
							 suported.add(1);
						}
					}
					for (String string2 : JsaneConfig.getString("SANE_MODE_COLOR")) {
						if(a[cont].equals(string2)){
							 suported.add(2);
						}
					}
					for (String string3 : JsaneConfig.getString("SANE_MODE_BN")) {
						if(a[cont].equals(string3)){
							 suported.add(0);
						}
					}
				}
	      }catch(Exception e){
	        jsane.signalException(e);
	      }finally{
	    	  close();
	        exit();
	      }
    }catch(Exception e){
      jsane.signalException(e);
    }
    
    Object[] array = suported.toArray();
    int[] valores = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
         valores[i]=(Integer)array[i];
        }
    
    return valores;
}

public float[] getXResolutionList() {
	ArrayList<Float> suported = new ArrayList<Float>();
	  try{
	      init();
	      try{
		        adfcount=-1;
		        userCancelled=false;
		        saneHandle=jsane.open(name);
		        setState(SANE_STATE_OPEN);
		
		        double max = 0;
		        double min = 0;
				Descriptor descriptor = getOption("resolution");
				if(descriptor.getClass()==FixedRange.class){
					FixedRange descriptor2 = (FixedRange) descriptor;
					
					max = FixedDesc.unfix(descriptor2.getMax());
					min = FixedDesc.unfix(descriptor2.getMin());
					
					if(100>=min && 100<=max){
						suported.add(new Float(100.0));
					}
					if(200>=min && 200<=max){
						suported.add(new Float(200.0));
					}
					if(300>=min && 300<=max){
						suported.add(new Float(300.0));
					}
					if(400>=min && 400<=max){
						suported.add(new Float(400.0));
					}
					if(600>=min && 600<=max){
						suported.add(new Float(600.0));
					}
					if(800>=min && 800<=max){
						suported.add(new Float(800.0));
					}
					if(1200>=min && 1200<=max){
						suported.add(new Float(1200.0));
					}
					
				}else if(descriptor.getClass()==IntRange.class){
					IntRange descriptor2 = (IntRange) descriptor;
					
					max = descriptor2.getMax();
					min =descriptor2.getMin();
					
					if(100>=min && 100<=max){
						suported.add(new Float(100.0));
					}
					if(200>=min && 200<=max){
						suported.add(new Float(200.0));
					}
					if(300>=min && 300<=max){
						suported.add(new Float(300.0));
					}
					if(600>=min && 600<=max){
						suported.add(new Float(600.0));
					}
					
				}else if(descriptor.getClass()==IntList.class){
					IntList descriptor2 = (IntList) descriptor;
					
					int[] lista = descriptor2.getList();
					
					for (int i : lista) {
						suported.add(new Float(i));
					}
				}
	      }catch(Exception e){
	        jsane.signalException(e);
	      }finally{
	    	  close();
	        exit();
	      }
    }catch(Exception e){
      jsane.signalException(e);
    }
	
    Object[] array = suported.toArray();
    float[] valores = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
         valores[i]=(Float)array[i];
        }
    
    return valores;
}


public int[] getSupportedSizes() {
	ArrayList<Integer> suported = new ArrayList<Integer>();   
	  try{
	      init();
	      try{
		        adfcount=-1;
		        userCancelled=false;
		        saneHandle=jsane.open(name);
		        setState(SANE_STATE_OPEN);

				FixedRange descriptor = (FixedRange) getOption("br-x");
				int maxX = descriptor.getMax();
				double maxXd = FixedDesc.unfix(maxX);
				
				FixedRange descriptor2 = (FixedRange) getOption("br-y");
				int maxY = descriptor2.getMax();
				double maxYd = FixedDesc.unfix(maxY);

				if(maxXd>=210 && maxYd>=297){
					suported.add(1);
				}
				if(maxXd>=297 && maxYd>=420){
					suported.add(11);
				}
				
				
	      }catch(Exception e){
	        jsane.signalException(e);
	      }finally{
	    	  close();
	        exit();
	      }
    }catch(Exception e){
      jsane.signalException(e);
    }
    
    Object[] array = suported.toArray();
    int[] valores = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
         valores[i]=(Integer)array[i];
        }
    
    return valores;
}

public boolean getADFSupport() {
	  boolean supported = false;
	  try{
	      init();
	      try{
		        adfcount=-1;
		        userCancelled=false;
		        saneHandle=jsane.open(name);
		        setState(SANE_STATE_OPEN);
		
				StringList descriptor = (StringList) getOption("source");
				String[] a = descriptor.getList();
				for (String string : a) {
					for (String string2 : JsaneConfig.getString("SANE_SOURCE_ADF")) {
						if(string.contains(string2)){
							return true;
						}
					}
					
				}
	      }catch(Exception e){
	        jsane.signalException(e);
	      }finally{
	    	  close();
	        exit();
	      }
    }catch(Exception e){
      jsane.signalException(e);
    }
	return supported;
}
  
}


/*
[1] SANE Standard Version 1.03
    (Scanner Access Now Easy)
    2002-10-10
    http://www.mostang.com/sane

*/