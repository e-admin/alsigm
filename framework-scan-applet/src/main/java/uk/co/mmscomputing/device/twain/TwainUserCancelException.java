package uk.co.mmscomputing.device.twain;

public class TwainUserCancelException extends TwainIOException{
  public TwainUserCancelException(){
    super("User cancelled next scan.");
  }
}