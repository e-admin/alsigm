package es.ieci.tecdoc.fwktd.applets.scan.scanner;

import java.io.*;

@SuppressWarnings("serial")
public class ScannerIOException extends IOException{

  public ScannerIOException(String msg){
    super(msg);
  }

}