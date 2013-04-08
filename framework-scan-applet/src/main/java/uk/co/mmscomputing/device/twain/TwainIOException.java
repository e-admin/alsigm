package uk.co.mmscomputing.device.twain;

import uk.co.mmscomputing.device.scanner.*;

public class TwainIOException extends ScannerIOException implements TwainConstants{
  public TwainIOException(String msg){ // Need this. JNI wouldn't find IOException constructor.
    super(msg);
  }
}