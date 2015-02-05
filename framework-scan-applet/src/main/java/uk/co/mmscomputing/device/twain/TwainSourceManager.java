package uk.co.mmscomputing.device.twain;

import java.util.Vector;
import uk.co.mmscomputing.device.scanner.ScannerIOException;

public class TwainSourceManager implements TwainConstants{

  private TwainSource   source;
  private boolean       is20;

  TwainSourceManager(long hwnd, boolean isTwain20){
    source=new TwainSource(this,hwnd,false);
    source.getDefault();
    is20 = isTwain20;

    System.out.println(getClass().getName()+".<init>:\t\nTwain 2.0 compatible : true");
  }

  boolean isTwain20Manager(){
    return is20;
  }

  int getConditionCode()throws TwainIOException{           // [1] 7-219
    byte[] status=new byte[4];                             // TW_STATUS
    int    rc    =jtwain.callSourceManager(DG_CONTROL,DAT_STATUS,MSG_GET,status);
    if(rc!=TWRC_SUCCESS){
      throw new TwainResultException("Cannot retrieve twain source manager's status.",rc);
    }
    return jtwain.getINT16(status,0);
  }

  public void call(int dg,int dat,int msg,byte[] data)throws TwainIOException{
    int rc=jtwain.callSourceManager(dg,dat,msg,data);
    switch(rc){
    case TWRC_SUCCESS:          return;
    case TWRC_FAILURE:          throw new TwainFailureException(getConditionCode());
    case TWRC_CHECKSTATUS:      throw new TwainResultException.CheckStatus();
    case TWRC_CANCEL:           throw new TwainResultException.Cancel();
    case TWRC_DSEVENT:          return;
    case TWRC_NOTDSEVENT:       throw new TwainResultException.NotDSEvent();
    case TWRC_XFERDONE:         throw new TwainResultException.TransferDone();
    case TWRC_ENDOFLIST:        throw new TwainResultException.EndOfList();
//    case TWRC_INFONOTSUPPORTED: throw new TwainResultException.InfoNotSupported();
//    case TWRC_DATANOTAVAILABLE: throw new TwainResultException.DataNotAvailable();
    default:                    throw new TwainResultException("Failed to call source manager.",rc);
    }
  }

  TwainSource getSource(){return source;}

  TwainSource selectSource()throws TwainIOException{
    source.checkState(3);
    source.setBusy(true);                                  // tell TwainPanel to disable GUI
    try{
      source.userSelect();                                 // new source in state 3
      return source;
    }catch(TwainResultException.Cancel trec){
      return source;
//  }catch(ThreadDeath e){
//    Applet: Select dialog enabled while user reloads webpage.
//    Happens only first time.
    }finally{
      source.setBusy(false);                               // tell TwainPanel to enable GUI
    }
  }

  void getIdentities(Vector identities)throws ScannerIOException{
    source.checkState(3);
    source.setBusy(true);                                  // tell TwainPanel to disable GUI
    try{
      TwainIdentity identity=new TwainIdentity(this);
      identity.getFirst();                                 // get first identity
      identities.add(identity);
      while(true){                                         // while(not EndOfList Exception thrown)
        identity=new TwainIdentity(this);
        identity.getNext();                                // get next identity
        identities.add(identity);
      }
    }catch(TwainResultException.EndOfList treeol){
    }catch(TwainIOException tioe){
      System.out.println(getClass().getName()+".getIdentities:\n\t"+tioe);
    }finally{
      source.setBusy(false);                               // tell TwainPanel to enable GUI
    }
  }

  TwainSource selectSource(String name)throws ScannerIOException{
    source.checkState(3);
    source.setBusy(true);                                  // tell TwainPanel to disable GUI
    try{
      source.select(name);                                 // new source in state 3
      return source;
    }finally{
      source.setBusy(false);                               // tell TwainPanel to enable GUI
    }
  }

  TwainSource openSource()throws TwainIOException{
    source.checkState(3);                                  // old source not enabled
    source.setBusy(true);                                  // tell TwainPanel to disable GUI
    try{
      source.open();
      if(!source.isDeviceOnline()){
        source.close();
        throw new TwainIOException("Selected twain source is not online.");
      }
      source.setState(4);
      return source;
    }catch(TwainResultException.Cancel trec){
      source.setBusy(false);                               // tell TwainPanel to enable GUI
      return source;
    }catch(TwainIOException tioe){
      source.setBusy(false);                               // tell TwainPanel to enable GUI
      throw tioe;
    }
  }
}
