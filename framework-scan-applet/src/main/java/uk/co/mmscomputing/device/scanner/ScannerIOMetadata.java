package uk.co.mmscomputing.device.scanner;

import java.io.*;
import java.awt.image.*;

abstract public class ScannerIOMetadata{

  static public class Type{}
  static public Type	INFO=new Type();
  static public Type	EXCEPTION=new Type();
//  static public Type	SELECTED=new Type();
  static public Type	ACQUIRED=new Type();
  static public Type	FILE=new Type();
  static public Type	MEMORY=new Type();
  static public Type	NEGOTIATE=new Type();
  static public Type	STATECHANGE=new Type();

  private int           laststate=0,state=0;
  private boolean       cancel=false;               // if true application wants scan engine to abort scan as soon as possible
  private BufferedImage image=null;
  private File          file=null;
  private String        info="";
  private Exception     exception=null;

  public void      setState(int s){laststate=state;state=s;}
  public int       getLastState(){ return laststate;}
  public int       getState(){ return state;}
  public String    getStateStr(){ return "State "+state;}
  public boolean   isState(int state){ return this.state==state;}

  public void      setImage(BufferedImage image){ this.image=image;this.file=null;}
  public BufferedImage getImage(){return image;}

  public void      setFile(File file){ this.image=null;this.file=file;}
  public File      getFile(){return file;}

  public void      setInfo(String info){ this.info=info;}  
  public String    getInfo(){ return info;}  

  public void      setException(Exception ex){ this.exception=ex;}  
  public Exception getException(){ return exception;}  

  public boolean     getCancel(){return cancel;}
  public void        setCancel(boolean cancel){this.cancel=cancel;}

  abstract public boolean isFinished();             // only valid when state changes!
  abstract public ScannerDevice getDevice();        // use only during negotiation
}