package uk.co.mmscomputing.device.sane;

@SuppressWarnings("serial")
public class SaneNoDocumentsException extends SaneIOException{
  public SaneNoDocumentsException(String msg){ // Need this. JNI wouldn't find IOException constructor.
    super(msg);
  }
}