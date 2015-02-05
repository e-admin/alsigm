package uk.co.mmscomputing.device.twain;

import java.util.Vector;
import java.io.*;
import java.awt.image.*;
import java.lang.ref.*;
import java.lang.reflect.*;

import uk.co.mmscomputing.util.*;
import uk.co.mmscomputing.concurrent.*;

public class jtwain implements TwainConstants{

  static private boolean            installed;
  static private Thread             nativeThread;                         // make sure to call DSM_ENTRY only from this native thread!

  static private TwainSourceManager sourceManager;                        // applet: this object will live as long as browser window is open
  static private WeakReference      scanner;                              // applet: set to new object if applet reloads
  static private Object             syncScanner = new Object();           // synchronization object

  static private native void        nstart();                             // run native event loop

// 32 | 64 bit system

  static private native int         ngetPtrSize();                        // 32 or 64 bit system
  static private        int         ptrSize = 0;                          // in byte
  static                int         getPtrSize(){ return ptrSize;}

// java -> native
  static private native void   ntrigger(Object caller,int cmd);               // call cbexecute from native thread

// TwainSourceManager -> native
  static private native TwainSourceManager ngetSourceManager()throws TwainException;  // initialize native library
  static private native int    ncallSourceManager(int dg,int dat,int msg,byte[] data)throws TwainException;

// TwainSource -> native
  static         native byte[] ngetContainer(byte[] capBuf);                  // copy container pointed to by TW_CAPABILITY.hContainer into byte buffer
  static         native void   nsetContainer(byte[] capbuf, byte[] conbytes); // copy byte buffer into native memory container and setup capability buffer
  static         native void   nfreeContainer(byte[] capbuf);                 // free native container memory
  static private native int    ncallSource(byte[] source,int dg,int dat,int msg,byte[] data)throws TwainException;

  static private native long   ngetCallBackMethod();                          // get twain 2.0 callback method
  static private        long   ptrCallback = 0;                               // pointer to twain 2.0 callback method
  static                long   getCallBackMethod(){return ptrCallback;}

// for twain native transfer mode
  static private native Object ntransferImage(long imagePtr);                 // returns BufferedImage object

// for twain memory transfer mode
  static         native void   nnew(byte[] twImageMemXfer,int size);          // allocate native memory block
  static         native int    ncopy(byte[] buf,byte[] twImageMemXfer,int size); // copy native memory block into java byte array
//  static         native byte[] ncopy(byte[] twImageMemXfer,int size);         // copy native memory block into java byte array
  static         native void   ndelete(byte[] twImageMemXfer);                // free native memory block

  static public boolean isInstalled(){return installed;}

  static int  getINT16(byte[] buf,int off){return (buf[off++]&0x00FF)|(buf[off]<<8);}
  static void setINT16(byte[] buf,int off,int i){buf[off++]=(byte)i;buf[off++]=(byte)(i>>8);}
  static int  getINT32(byte[] buf,int off){return (buf[off++]&0x00FF)|((buf[off++]&0x00FF)<<8)|((buf[off++]&0x00FF)<<16)|(buf[off]<<24);}
  static void setINT32(byte[] buf,int off,int i){buf[off++]=(byte)i;buf[off++]=(byte)(i>>8);buf[off++]=(byte)(i>>16);buf[off++]=(byte)(i>>24);}

  static long getINT64(byte[] buf,int off){
    return  (buf[off++]&0x00FFL)     |((buf[off++]&0x00FFL)<< 8)|((buf[off++]&0x00FFL)<<16)|((buf[off++]&0x00FFL)<<24)
          |((buf[off++]&0x00FFL)<<32)|((buf[off++]&0x00FFL)<<40)|((buf[off++]&0x00FFL)<<48)|((buf[off++]&0x00FFL)<<56);
  }

  static void setINT64(byte[] buf,int off,long i){
    buf[off++]=(byte) i     ;buf[off++]=(byte)(i>> 8);buf[off++]=(byte)(i>>16);buf[off++]=(byte)(i>>24);
    buf[off++]=(byte)(i>>32);buf[off++]=(byte)(i>>40);buf[off++]=(byte)(i>>48);buf[off++]=(byte)(i>>56);
  }

  static long getPtr(byte[] buf,int off){
    if(ptrSize==8){return getINT64(buf,off);
    }else{         return getINT32(buf,off);
    }
  }

  static int setPtr(byte[] buf,int off,long i){
    if(ptrSize==8){setINT64(buf,off,     i);return 8;
    }else{         setINT32(buf,off,(int)i);return 4;
    }
  }

  static double getFIX32(byte[] buf,int off){
    int whole =((buf[off++]&0x00FF)|(buf[off++]<<8));
    int frac  =((buf[off++]&0x00FF)|((buf[off]&0x00FF)<<8));
    return ((double)whole)+((double)frac)/65536.0;            // [1] 4-76
  }

  static void setFIX32(byte[] buf,int off,double d){
    int value =(int)(d*65536.0+((d<0)?(-0.5):0.5));           // [1] 4-78 + fix for negative numbers
    setINT16(buf,off  ,value>>16);                            // whole
    setINT16(buf,off+2,value&0x0000FFFF);                     // frac
  }

  static void setString(byte[] buf,int off,String s){
    System.arraycopy(s.getBytes(),0,buf,0,s.length());
  }

  static int callSourceManager(int dg,int dat,int msg,byte[] data)throws TwainIOException{
    if(Thread.currentThread()!=nativeThread){
      throw new TwainIOException("Call twain source manager only from within native thread.");
    }
    try{
      return ncallSourceManager(dg,dat,msg,data);
    }catch(TwainException te){                                // disaster: source exception in native code
      installed=false;throw te;                               // disable jtwain, no calls to twain anymore
    }
  }

  static int callSource(byte[] source,int dg,int dat,int msg,byte[] data)throws TwainIOException{
    if(Thread.currentThread()!=nativeThread){
      throw new TwainIOException("Call twain source only from within native thread.");
    }
    try{
      return ncallSource(source,dg,dat,msg,data);
    }catch(TwainException tioe){                              // disaster: source exception in native code
      installed=false;throw tioe;                             // disable jtwain, no calls to twain anymore
    }
  }

  static public TwainSourceManager getSourceManager()throws TwainIOException{
    try{                                                      // wait until native thread is up and running
      while((sourceManager==null)&&installed){Thread.currentThread().sleep(100);}
    }catch(Exception e){}
    if(!installed){
      throw new TwainIOException("Cannot load Twain Source Manager.");
    }
    return sourceManager;
  }

  static public TwainSource getSource()throws TwainIOException{
    return getSourceManager().getSource();
  }

  static private void trigger(Object caller,int cmd) throws TwainIOException{
    try{                                                      // wait until native thread is up and running
      while((sourceManager==null)&&installed){Thread.currentThread().sleep(100);}
      if(installed){ntrigger(caller,cmd);}
    }catch(Exception e){
    	e.printStackTrace();
    	throw new TwainIOException("trigger " + caller + "not executed");
    }
  }

  static public  void callSetSource(Object caller) throws TwainIOException {trigger(caller,0);} // caller.setSource(sourceManager.getSource());

  static private TwainScanner getScanner(){
    return (scanner!=null)?(TwainScanner)scanner.get():null;
  }

  static private boolean setScanner(TwainScanner s)throws TwainIOException{ // use of weak reference to TwainScanner object
    synchronized(syncScanner){
      TwainScanner cs = getScanner();
      if((cs==null)||!cs.isBusy()){
        scanner=new WeakReference(s);                         // allows garbage collection in applets
        return true;
      }
      throw new TwainIOException(jtwain.class.getName()+".setScanner\n\tScanner is still busy.");
    }
  }

  static public void select(TwainScanner sc)throws TwainIOException{
    if(setScanner(sc)){
      TwainSourceManager sm=getSourceManager();                 // jtwain might not be up and running yet
      sm.getSource().checkState(3);
      trigger(sc,1);
    }
  }

  // used by TwainScanner

  static /* public */ void getIdentities(TwainScanner sc, Vector list)throws TwainIOException{
    if(setScanner(sc)){
      TwainSourceManager sm=getSourceManager();                 // jtwain might not be up and running yet
      sm.getSource().checkState(3);                             // System.err.println("select: try "+name);

      Semaphore s = new Semaphore(0,true);                      // need to wait for native thread to retrieve list of identities
      Object[] parameter = {list,s};                            // need to transport two objects via one parameter to cbexecute
      trigger(parameter,2);                                     // call cbexecute case 2
      try{
        s.tryAcquire(3000,TimeUnit.MILLISECONDS);
        if(list.isEmpty() && (parameter[1]!=null)){
          throw new TwainIOException(jtwain.class.getName()+".getIdentities\n\tCould not retrieve device names. Request timed out.");
        }
      }catch(InterruptedException ie){
        throw new TwainIOException(jtwain.class.getName()+".getIdentities\n\tCould not retrieve device names. Request was interrupted.");
      }
    }
  }

  static public void select(TwainScanner sc, String name)throws TwainIOException{
    if(setScanner(sc)){
      TwainSourceManager sm=getSourceManager();                 // jtwain might not be up and running yet
      sm.getSource().checkState(3);                             // System.err.println("select: try "+name);
      trigger(name,3);
    }
  }

  static public void acquire(TwainScanner sc)throws TwainIOException{
    if(setScanner(sc)){
      TwainSourceManager sm=getSourceManager();                 // jtwain might not be up and running yet
      sm.getSource().checkState(3);
      trigger(sc,4);
    }
  }

  static public void setCancel(TwainScanner sc, boolean c)throws TwainIOException{
    getSourceManager().getSource().setCancel(c);
  }

  static private Method getMethod(Class clazz,String name,Class[] paramTypes)throws NoSuchMethodException{
    if(clazz==null){ throw new NoSuchMethodException();}
    try{
      return clazz.getDeclaredMethod(name,paramTypes);
    }catch(NoSuchMethodException nsme){
      return getMethod(clazz.getSuperclass(),name,paramTypes);
    }
  }

  static private void cbexecute(Object obj,int cmd){     // callback function cpp -> java; in windows thread;
    TwainSource source;
    try{
      switch(cmd){
      case 0:                                            // an object wants access to current twain source
       Class clazz =obj.getClass();
        try{                                             // introspection
          source=sourceManager.getSource();
          Method method=getMethod(clazz,"setSource",new Class[]{TwainSource.class});
          method.invoke(obj,new Object[]{source});       // caller.setSource(source);
        }catch(Throwable e){e.printStackTrace();}        // cannot find method: programming error
        break;
      case 1:                                            // select
        sourceManager.selectSource();                    // System.out.println(sourceManager.getSource());
        break;
      case 2:                                            // getIdentities
        Object[]               parameter  = (Object[])obj;             // parameter list from getIdentities
        Vector  list       = (Vector)parameter[0];
        Semaphore              s          = (Semaphore)parameter[1];
        sourceManager.getIdentities(list);
        parameter[1] = null;
        s.release();
        break;
      case 3:                                            // select name
        String name = (String)obj;                       // System.out.println("cbexecute: try "+name);
        sourceManager.selectSource(name);                // System.err.println(sourceManager.getSource());
        break;
      case 4:                                            // acquire
        source=sourceManager.openSource();
        try{
          source.enable();
        }finally{
          source.close();
        }
        break;
      }
    }catch(Throwable e){
      signalException(e.getMessage());
      e.printStackTrace();
    }
  }

  static private int cbhandleGetMessage(long msg){        // callback function cpp -> java; windows thread;
    try{
      return sourceManager.getSource().handleGetMessage(msg);
    }catch(Throwable e){
      signalException(e.getMessage());
      e.printStackTrace();
      return TWRC_NOTDSEVENT;
    }
  }
                                                              // callback function cpp -> java; twain 2.0;
  static private int cbmethod20(byte[] corigin,byte[] cdest,int DG,int DAT,int MSG,long dataptr){
    try{
      TwainIdentity origin = new TwainIdentity(sourceManager,corigin);  // this should always be the twain data source
//      System.out.println(origin.toString());
//      TwainIdentity dest   = new TwainIdentity(sourceManager,cdest);  // this should always be the twain data source manager
//      System.out.println(dest.toString());

      TwainSource source = sourceManager.getSource();
      if(origin.getId()!=source.getId()){                     // we are only waiting for callbacks from our datasource, so validate the originator
        return TWRC_FAILURE;
      }
      return source.callback(DG,DAT,MSG,dataptr);
    }catch(Throwable e){
      signalException(e.getMessage());
      e.printStackTrace();
      return TWRC_FAILURE;
    }
  }

  static void signalStateChange(TwainSource source){
    TwainScanner scanner=getScanner();
    if(scanner!=null){
      scanner.setState(source);
    }
  }

  static void signalException(String msg){
    TwainScanner scanner=getScanner();
    if(scanner!=null){
      scanner.signalException(msg);
    }
  }

  static void negotiateCapabilities(TwainSource source){
    TwainScanner scanner=getScanner();
    if(scanner!=null){
      scanner.negotiateCapabilities(source);
    }
  }

  static void transferNativeImage(long dibHandle){
    BufferedImage image=(BufferedImage)ntransferImage(dibHandle);
    if(image!=null){
      TwainScanner scanner=getScanner();
      if(scanner!=null){
        scanner.setImage(image);
      }
    }
  }

  static void transferFileImage(File file){
    if(file!=null){
      TwainScanner scanner=getScanner();
      if(scanner!=null){
        scanner.setImage(file);
      }
    }
  }

  static void transferMemoryBuffer(TwainTransfer.MemoryTransfer.Info info){
    TwainScanner scanner=getScanner();
    if(scanner!=null){
      scanner.setImageBuffer(info);
    }
  }

  static{
//  win : load library 'jtwain.dll'
//    installed = JarLib.load(jtwain.class,"jtwain");
    installed = TwainNativeLoadStrategySingleton.getInstance().getNativeLoadStrategy().load( jtwain.class,"jtwain");
    if(installed){
      ptrSize     = ngetPtrSize();
      ptrCallback = ngetCallBackMethod();

      nativeThread=new Thread(){
        public void run(){
          try{
            sourceManager=ngetSourceManager();
            if(sourceManager!=null){
              nstart();
            }
          }catch(Throwable e){
            System.err.println("uk.co.mmscomputing.device.twain.jtwain\t\n"+e.getMessage());
            System.err.println(e);
            e.printStackTrace();
          }
          installed=false;
        }
      };
      nativeThread.setDaemon(true);
      nativeThread.start();
    }
  }
}

// [1] Twain Spec 1.9
// [2] Twain Spec 2.0
