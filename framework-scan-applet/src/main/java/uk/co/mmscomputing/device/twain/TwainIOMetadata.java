package uk.co.mmscomputing.device.twain;

import java.io.IOException;

import uk.co.mmscomputing.device.scanner.*;

public class TwainIOMetadata extends ScannerIOMetadata{

  static public final String[] TWAIN_STATE = {
    "",
    "Pre-Session",
    "Source Manager Loaded",
    "Source Manager Open",
    "Source Open",
    "Source Enabled",
    "Transfer Ready",
    "Transferring Data",
  };

  public String        getStateStr(){ return TWAIN_STATE[getState()];}

  private TwainSource  source=null;

         void          setSource(TwainSource source){this.source=source;}
  public TwainSource   getSource(){return source;}
  public ScannerDevice getDevice(){return source;}


  private TwainTransfer.MemoryTransfer.Info memory=null;

  public void        setMemory(TwainTransfer.MemoryTransfer.Info info){memory=info;}
  public TwainTransfer.MemoryTransfer.Info getMemory(){return memory;}

  // only valid when state changes!
  public boolean     isFinished(){return (getState()==3)&&(getLastState()==4);}
}