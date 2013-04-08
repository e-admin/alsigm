package uk.co.mmscomputing.device.sane;

import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class jsane{

  // sane functions

  static private SaneDeviceManager      manager;            // applet: this object will live as long as browser window is open  
  static private WeakReference          scanner;            // applet: set to new object if applet reloads

  static public native void             init()throws SaneIOException;
  static public native void             exit();                   

  static public native String[]         getDevices(boolean localOnly)throws SaneIOException;

  static public native int              open(String device)throws SaneIOException;                   
  static public native void             close(int handle);

  static public native OptionDescriptor getOptionDescriptor(int handle,int option);

  static public native void             getControlOption(int handle,int option,byte[] value)throws SaneIOException;
  static public native int              setControlOption(int handle,int option,byte[] value)throws SaneIOException;
  static public native void             setAutoControlOption(int handle,int option)throws SaneIOException;

  static public native void             getParameters(int handle,Parameters p)throws SaneIOException;
  static public native void             start(int handle)throws SaneIOException;

  static public native int              read(int handle,byte[] b,int off,int len)throws SaneIOException;
  static public native void             cancel(int handle);

  // next two functions are implemented but have never been tested. Not really necessary.
  static public native void             setIOMode(int handle,boolean mode)throws SaneIOException;
  static public native int              getSelectFD(int handle)throws SaneIOException;

  static public native String           strStatus(int status);

  // mmsc functions

  static private boolean                isinstalled;
  static public  boolean                isInstalled(){return isinstalled;}

  static public native int              getVersion();

  // control options : added special Word/Int/String getter/setter

  static public native int              getWordControlOption(int handle,int option)throws SaneIOException;
  static public native int              setWordControlOption(int handle,int option,int value)throws SaneIOException;

  static protected native void          getWordArrayControlOption(int handle,int option,int[] values)throws SaneIOException;
  static protected native int           setWordArrayControlOption(int handle,int option,int[] values)throws SaneIOException;

  static protected native String        getStringControlOption(int handle,int option,int strlen)throws SaneIOException;
  static protected native int           setStringControlOption(int handle,int option,int strlen,String str)throws SaneIOException;

  static public int getNumberOfOptions(int handle)throws SaneIOException{
    return getWordControlOption(handle,0);  // [1] p.26 4.3.6
  }  

  static public void setScanner(SaneScanner s){               
    scanner=new WeakReference(s);                                                
  }

  static private SaneScanner getScanner(){
    return (SaneScanner)scanner.get();
  }

  static SaneDeviceManager getDeviceManager(){return manager;}

  static void checkInstalled()throws SaneIOException{
    if(!isinstalled){
      throw new SaneIOException(jsane.class,"checkInstalled","jsane.err.isnotinstalled");
    }
  }

  static public void select(SaneScanner sc)throws SaneIOException{
    checkInstalled();
    manager.getDevice().checkBusy();
    setScanner(sc);
    manager.selectDevice();
  }

  static public void select(SaneScanner sc, String name)throws SaneIOException{
    checkInstalled();
    manager.getDevice().checkBusy();
    setScanner(sc);
    manager.selectDevice(name);
  }

  static public ArrayList<BufferedImage> acquire(SaneScanner sc)throws SaneIOException{
    checkInstalled();
    manager.getDevice().checkBusy();
    setScanner(sc);
    final SaneDevice device=manager.getDevice();
    //new Thread(){public void run(){device.acquire();}}.start();
    return device.acquire();
  }
  
  static public void setCancel(SaneScanner sc,boolean c)throws SaneIOException{
    SaneDevice device = manager.getDevice();
    if(device==null){return;}
    checkInstalled();
    setScanner(sc);
    device.setCancel(c);
  }

  static void negotiateOptions(SaneDevice source){
    SaneScanner scanner=getScanner();
    if(scanner!=null){scanner.negotiateOptions(source);}
  }

  static void signalImage(BufferedImage image){
    if(image!=null){
      SaneScanner scanner=getScanner();
      if(scanner!=null){scanner.setImage(image);}
    }
  }

  static void signalStateChange(SaneDevice device){
    SaneScanner scanner=getScanner();
    if(scanner!=null){scanner.setState(device);}
  }

  static void signalException(Exception e){
    SaneScanner scanner=getScanner();
    if(scanner!=null){scanner.signalException(e);}
  }

  // simple internationalization

  static private es.ieci.tecdoc.fwktd.applets.scan.utils.UtilResources resources;

  static public String getResource(String id){return resources.getString(id);}
  static public String getResource(String id,String arg0){return resources.getString(id,arg0);}
  static public String getResource(String id,String[] args){return resources.getString(id,args);}

  static{
    resources=new es.ieci.tecdoc.fwktd.applets.scan.utils.UtilResources(jsane.class);
//  linux : load library 'libjsane.so'
    isinstalled=es.ieci.tecdoc.fwktd.applets.scan.jtwain.JarLib.load(jsane.class,"jsane");
    if(isinstalled){
      manager=new SaneDeviceManager();
    }
  }

  static public boolean getDuplexSupport(SaneScanner sc)throws SaneIOException{
	    checkInstalled();
	    manager.getDevice().checkBusy();
	    setScanner(sc);
	    final SaneDevice device=manager.getDevice();
	    return device.getDuplexSupport();
  }
  
	public static int[] getPixelTypeList(SaneScanner saneScanner) throws SaneIOException {
	    checkInstalled();
	    manager.getDevice().checkBusy();
	    setScanner(saneScanner);
	    final SaneDevice device=manager.getDevice();
	    return device.getPixelTypeList();
	}
	public static float[] getXResolutionList(SaneScanner saneScanner) throws SaneIOException {
	    checkInstalled();
	    manager.getDevice().checkBusy();
	    setScanner(saneScanner);
	    final SaneDevice device=manager.getDevice();
	    return device.getXResolutionList();
	}
	public static int[] getSupportedSizes(SaneScanner saneScanner) throws SaneIOException  {
	    checkInstalled();
	    manager.getDevice().checkBusy();
	    setScanner(saneScanner);
	    final SaneDevice device=manager.getDevice();
	    return device.getSupportedSizes();
	}
	public static boolean getADFSupport(SaneScanner saneScanner)  throws SaneIOException {
	    checkInstalled();
	    manager.getDevice().checkBusy();
	    setScanner(saneScanner);
	    final SaneDevice device=manager.getDevice();
	    return device.getADFSupport();
	}
}

/*
[1] SANE Standard Version 1.03
    (Scanner Access Now Easy)
    2002-10-10
    http://www.sane-project.org

*/
