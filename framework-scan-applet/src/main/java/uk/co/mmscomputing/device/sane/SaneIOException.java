package uk.co.mmscomputing.device.sane;

import es.ieci.tecdoc.fwktd.applets.scan.scanner.*;

@SuppressWarnings("serial")
public class SaneIOException extends ScannerIOException{

  public SaneIOException(String msg){ // Need this. JNI wouldn't find IOException constructor.
    super("\n\t"+msg);
  }

  @SuppressWarnings("unchecked")
public SaneIOException(Class clazz,String method,String msgid){
    super(clazz.getName()+"."+method+":\n\t"+jsane.getResource(msgid));
  }

  @SuppressWarnings("unchecked")
public SaneIOException(Class clazz,String method,String msgid,String arg){
    super(clazz.getName()+"."+method+":\n\t"+jsane.getResource(msgid,arg));
  }

  @SuppressWarnings("unchecked")
public SaneIOException(Class clazz,String method,String msgid,String[] args){
    super(clazz.getName()+"."+method+":\n\t"+jsane.getResource(msgid,args));
  }
}