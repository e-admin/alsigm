package uk.co.mmscomputing.device.scanner;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;

//import uk.co.mmscomputing.device.twain.*;
//import uk.co.mmscomputing.device.sane.*;

abstract public class Scanner{

  static protected boolean installed=false;

  public abstract boolean isAPIInstalled();

  protected Vector listeners=new Vector();  // list of scanner event listeners
  protected ScannerIOMetadata       metadata=null;                            // information structure
  protected boolean                 isbusy = false;                           // is scanner busy

  public abstract void       select()throws ScannerIOException;
  public abstract String[]   getDeviceNames()throws ScannerIOException;
  public abstract void       select(String name)throws ScannerIOException;
  public abstract String     getSelectedDeviceName()throws ScannerIOException;
  public abstract void       acquire()throws ScannerIOException;
  public abstract void       setCancel(boolean c)throws ScannerIOException;

  public boolean isBusy(){ return isbusy;}

  public void waitToExit(){                                       // convenience method, call before System.exit
    try{
      while(isBusy()){                                            // make sure program waits for scanner to finish!
        Thread.currentThread().sleep(200);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void addListener(ScannerListener listener){
    listeners.add(listener);
  }

  public void removeListener(ScannerListener listener){
    listeners.remove(listener);
  }

  public void fireExceptionUpdate(Exception e){
    metadata.setException(e);
    fireListenerUpdate(metadata.EXCEPTION);
  }

  public void fireListenerUpdate(ScannerIOMetadata.Type type){
    if(type.equals(ScannerIOMetadata.STATECHANGE)){
      isbusy=metadata.getDevice().isBusy();
    }
    for(Enumeration e = listeners.elements(); e.hasMoreElements() ;){
      ScannerListener listener=(ScannerListener)e.nextElement();
      listener.update(type,metadata);
    }
  }

  public JComponent getScanGUI()throws ScannerIOException{
    return new JLabel("Dummy Scanner GUI");
  }

  public JComponent getScanGUI(int mode)throws ScannerIOException{
    return new JLabel("Dummy Scanner GUI");
  }

  static public Scanner getDevice(){
    String osname    = System.getProperty("os.name");
    String cn;
    if(osname.startsWith("Linux")){
      cn = "uk.co.mmscomputing.device.sane.SaneScanner";
    }else if(osname.startsWith("Windows")){
      cn = "uk.co.mmscomputing.device.twain.TwainScanner";
//    }else if(osname.startsWith("Mac")){
    }else{
      return null;
    }                                             // use reflection here, because allows jar files with/out
    try{                                          // twain and/or sane files
      Scanner scanner   = (Scanner)Class.forName(cn).newInstance();
      if(scanner.isAPIInstalled()){
/*                                                // doesn't work unfortunately
        Thread shutdown = new Thread(){
          public void run(){
            scanner.waitForNotBusy();
          }
        };
        Runtime.getRuntime().addShutdownHook(shutdown);
*/
        return scanner;
      }
    }catch(IllegalAccessException iae){
      iae.printStackTrace();
    }catch(InstantiationException ie){
      ie.printStackTrace();
    }catch(ClassNotFoundException cnfe){
      cnfe.printStackTrace();
    }
    return null;
  }
}


