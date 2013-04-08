package uk.co.mmscomputing.device.twain;

import java.io.*;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.concurrent.*;

public class TwainSource extends TwainIdentity implements TwainConstants,ScannerDevice{

  private boolean busy;
  private int     state;
  private long    hWnd;

  private int     showUI     = 1;               // show DS GUI
  private int     modalUI    = 0;               // is modeless GUI

  private int     iff        = TWFF_BMP;        // image file format

  private TwainITransferFactory transferFactory;
  private boolean userCancelled;

  private Semaphore tw20Semaphore = null;                // twain 2.0: wait for source to signal new image
  private boolean   tw20HaveImage = false;

  TwainSource(TwainSourceManager manager,long hwnd,boolean bus){
    super(manager);
    this.hWnd=hwnd;
    this.busy=bus;
    this.state=3;

    this.userCancelled=false;
    this.transferFactory=new TwainDefaultTransferFactory();
  }

  byte[] getIdentity(){return identity;}

  public boolean isBusy(){             return busy;}
         void    setBusy(boolean b){   busy=b; jtwain.signalStateChange(this);}
  public int     getState(){           return state;}
         void    setState(int s){      state=s;jtwain.signalStateChange(this);}

  public void    setCancel(boolean c){ userCancelled=c;}
  public boolean getCancel(){          return userCancelled;}


  void checkState(int state)throws TwainIOException{
    if(this.state==state){return;}
    throw new TwainIOException(getClass().getName()+".checkState:\n\tSource not in state "+state+" but in state "+this.state+".");
  }

  int getConditionCode()throws TwainIOException{
    byte[] status=new byte[4];                     // TW_STATUS
    int    rc    =jtwain.callSource(identity,DG_CONTROL,DAT_STATUS,MSG_GET,status);
    if(rc!=TWRC_SUCCESS){
      throw new TwainResultException("Cannot retrieve twain source's status.",rc);
    }
    return jtwain.getINT16(status,0);
  }

  private void checkrc(int rc)throws TwainIOException{
    switch(rc){
    case TWRC_SUCCESS:          return;
    case TWRC_FAILURE:{
      int cc = getConditionCode();
      if(cc==TWCC_OPERATIONERROR){
        // appl shouldn't bother user
      }else{
        throw TwainFailureException.create(cc);
      }
    }break;
    case TWRC_CHECKSTATUS:      throw new TwainResultException.CheckStatus();
    case TWRC_CANCEL:           throw new TwainResultException.Cancel();
    case TWRC_DSEVENT:          return;
    case TWRC_NOTDSEVENT:       throw new TwainResultException.NotDSEvent();
    case TWRC_XFERDONE:         throw new TwainResultException.TransferDone();
    case TWRC_ENDOFLIST:        throw new TwainResultException.EndOfList();
    case TWRC_INFONOTSUPPORTED: throw new TwainResultException.InfoNotSupported();
    case TWRC_DATANOTAVAILABLE: throw new TwainResultException.DataNotAvailable();
    default:
      System.err.println(getClass().getName()+".checkrc\n\trc="+rc);
      throw new TwainResultException("Failed to call source.",rc);
    }
  }

  public void call(int dg,int dat,int msg,byte[] data)throws TwainIOException{
    checkrc(jtwain.callSource(identity,dg,dat,msg,data));
  }

  private void setCallbackProcedure()throws TwainIOException{
    /*
      typedef struct  {
        TW_MEMREF   CallBackProc;          //  0   : 4 or 8 byte ptr to callback procedure
        TW_UINT32   RefCon;                //  4, 8: application reference number
        TW_INT16    Message;               //  8,12:
      } TW_CALLBACK, FAR * pTW_CALLBACK;   // 10,14
   */

    byte[] callback = new byte[(jtwain.getPtrSize()==4)?10:14];
    int off = jtwain.setPtr(callback,0,jtwain.getCallBackMethod());
              jtwain.setINT32(callback,off,0);
    off += 4; jtwain.setINT16(callback,off,0);
    call(DG_CONTROL,DAT_CALLBACK,MSG_REGISTER_CALLBACK,callback);
  }

  void open()throws TwainIOException{                                                // [1] 7-184 state 3: 3 -> 4
    super.open();
    if(isTwain20Source()){
      try{
        setCallbackProcedure();
      }catch(Exception te){
        maskTwain20Source();                                                         // if we cannot set callback routine disable it.
        System.out.println("3\b"+getClass().getName()+".open:\n\tCannot set twain 2.0 callback method.");
        System.err.println(getClass().getName()+".open:\n\tCannot set twain 2.0 callback method.");
      }
    }
  }

  public TwainCapability[] getCapabilities()throws TwainIOException{
    return TwainCapability.getCapabilities(this);
  }

  public TwainCapability getCapability(int cap)throws TwainIOException{              // use only in state 4
    return new TwainCapability(this,cap);
  }

  public TwainCapability getCapability(int cap,int mode)throws TwainIOException{     // use only in state 4
    return new TwainCapability(this,cap,mode);
  }

  public TwainITransferFactory getTransferFactory() {
    return transferFactory;
  }

  public void setTransferFactory(TwainITransferFactory transferFactory){
    if(transferFactory==null){
      throw new IllegalArgumentException(getClass().getName()+".setTransferFactory\n\tTwain transfer factory cannot be null.");
    }
    this.transferFactory = transferFactory;
  }

  public void setShowUI(boolean enable){showUI=0;}
  //i' set this to 0 for a dirty hack
  public boolean isModalUI(){return (modalUI==0);}

  public void setCapability(int cap,boolean v)throws ScannerIOException{
    TwainCapability tc=getCapability(cap,MSG_GETCURRENT);
    if(tc.booleanValue()!=v){
      tc.setCurrentValue(v);
      if(getCapability(cap).booleanValue()!=v){
        throw new ScannerIOException(getClass().getName()+".setCapability:\n\tCannot set capability "+TwainCapability.getCapName(cap)+" to "+v);
      }
    }
  }

  public void setCapability(int cap,int v)throws ScannerIOException{
    TwainCapability tc=getCapability(cap,MSG_GETCURRENT);
    if(tc.intValue()!=v){
      tc.setCurrentValue(v);
      if(getCapability(cap).intValue()!=v){
        throw new ScannerIOException(getClass().getName()+".setCapability:\n\tCannot set capability "+TwainCapability.getCapName(cap)+" to "+v);
      }
    }
  }

  public void setCapability(int cap,double v)throws ScannerIOException{
    TwainCapability tc=getCapability(cap,MSG_GETCURRENT);
    if(tc.doubleValue()!=v){
      tc.setCurrentValue(v);
      if(getCapability(cap).doubleValue()!=v){
        throw new ScannerIOException(getClass().getName()+".setCapability:\n\tCannot set capability "+TwainCapability.getCapName(cap)+" to "+v);
      }
    }
  }

  // negotiation options common to Twain & Sane

  public boolean isUIControllable(){
    try{                                                    // TW_ONEVALUE/TW_BOOL if >=1.6 TWAIN compliant
      return getCapability(CAP_UICONTROLLABLE).booleanValue();
    }catch(Exception e){                                    // if some error assume source does not support ShowUI=false
      jtwain.signalException(getClass().getName()+".isUIControllable:\n\t"+e);return false;
    }
  }

  public boolean isDeviceOnline(){                          // [1] 9-369 CAP_DEVICEONLINE
    try{                                                    // TW_ONEVALUE/TW_BOOL
      return getCapability(CAP_DEVICEONLINE).booleanValue();
    }catch(Exception e){                                    // if some error assume source is on
      jtwain.signalException(getClass().getName()+".isOnline:\n\t"+e);return true;
    }
  }

  public void setShowUserInterface(boolean show)throws ScannerIOException{
    setShowUI(show);
  }

  public void setShowProgressBar(boolean show)throws ScannerIOException{
    setCapability(CAP_INDICATORS,show);                         // works only if user interface is inactive
  }

  public void setResolution(double dpi)throws ScannerIOException{
    setCapability(ICAP_UNITS,TWUN_INCHES);                      // setResolution expects dots per inch
    setCapability(ICAP_XRESOLUTION,dpi);
    setCapability(ICAP_YRESOLUTION,dpi);
  }

  public void setRegionOfInterest(int x, int y, int w, int h)throws ScannerIOException{
    if((x==-1)&&(y==-1)&&(w==-1)&&(h==-1)){                     // int version of setRegionOfInterest expects pixels
      new TwainImageLayout(this).reset();
    }else{
      setCapability(ICAP_UNITS,TWUN_PIXELS);
      TwainImageLayout til=new TwainImageLayout(this);
      til.get();                                                // System.out.println(til.toString());
      til.setLeft(x);til.setTop(y);                             // scan from topLeft(x,y) to rightBottom(x+width,y+height)
      til.setRight(x+w);til.setBottom(y+h);
      til.set();
    }
  }

  public void setRegionOfInterest(double x, double y, double w, double h)throws ScannerIOException{
    if((x==-1)&&(y==-1)&&(w==-1)&&(h==-1)){                     // double version of setRegionOfInterest expects millimeters
      new TwainImageLayout(this).reset();
    }else{
      setCapability(ICAP_UNITS,TWUN_CENTIMETERS);
      TwainImageLayout til=new TwainImageLayout(this);
      til.get();                                                // System.out.println(til.toString());
      til.setLeft(x/10.0);til.setTop(y/10.0);                   // scan from topLeft(x,y) to rightBottom(x+width,y+height)
      til.setRight((x+w)/10.0);til.setBottom((y+h)/10.0);
      til.set();
    }
//    til.get();                                                // System.out.println(til.toString());
  }

  public void select(String name)throws ScannerIOException{
    checkState(3);
    TwainSourceManager manager=jtwain.getSourceManager();
    try{
      TwainIdentity device=new TwainIdentity(manager);
      device.getFirst();                                        // get first identity
      while(true){                                              // while(not EndOfList Exception thrown)
        if(device.getProductName().equals(name)){
          System.arraycopy(device.identity,0,identity,0,identity.length);break;
        }
        device.getNext();                                       // get next identity
      }
    }catch(TwainResultException.EndOfList treeol){
      throw new TwainIOException(getClass().getName()+".select(String name)\n\tCannot find twain data source: '"+name+"'");
    }
  }

  // END: negotiation options common to Twain & Sane

  // image acquisition

  void enable()throws TwainIOException{                         // state 4 -> 5
    checkState(4);
    jtwain.negotiateCapabilities(this);                         // still in state 4 tell application to negotiate capabilities and defaults
    if(getState()<4){return;}                                   // application <=> source negotiation failed => application closed source

    int xfer=new TwainCapability.XferMech(this).intValue();     // what transfer mode do we use
    if(xfer==TWSX_NATIVE){                                      // if native transfer (dib)
    }else if(xfer==TWSX_FILE){                                  // if file transfer mode
      try{                                                      // if source supports different file formats
        iff=getCapability(ICAP_IMAGEFILEFORMAT).intValue();     // cache file format
      }catch(Exception e){                                      // else
        iff=TWFF_BMP;                                           // use default
      }
    }
    if(isTwain20Source()){
      tw20Semaphore = new Semaphore(0,true);
      tw20HaveImage = false;
    }
    byte[] gui=null;
    try{
      gui=new byte[(jtwain.getPtrSize()==4)?8:12];              // TW_USERINTERFACE
      jtwain.setINT16(gui,0,showUI);                            // 1: show gui; 0: do not show gui
      jtwain.setINT16(gui,2,modalUI);
      jtwain.setPtr(gui,4,hWnd);                                // set parent window

      call(DG_CONTROL,DAT_USERINTERFACE,MSG_ENABLEDS,gui);      // enable source; pop up gui if ShowGUI=true
      modalUI=jtwain.getINT16(gui,2);
      setState(5);

    }catch(TwainResultException.CheckStatus trecs){             // ShowGUI=false not supported continue with GUI
      setState(5);                                              // or (twain 2.0 test ds does this the wrong way round;)
      showUI=(~showUI)&0x01;                                    // ShowGUI=true not supported continue without GUI
    }catch(TwainResultException.Cancel trec){
      disable();
      close();
    }
    if(isTwain20Source()){
      try{
        tw20Semaphore.tryAcquire(5*60000,TimeUnit.MILLISECONDS); // wait for max 5 min
        tw20Semaphore.release();                                 // release other threads

        if(tw20HaveImage==true){
          transferImage();
        }else{
          System.out.println("9\b"+getClass().getName()+".enable:\n\tscan timed out. Close data source.");
          System.err.println(getClass().getName()+".enable:\n\tscan timed out Close data source.");
        }
      }catch(TwainIOException tioe){
        System.out.println("9\b"+getClass().getName()+".enable:\n\t"+tioe);
        System.err.println(getClass().getName()+".enable:\n\t"+tioe);
      }catch(InterruptedException ie){
        System.out.println("9\b"+getClass().getName()+".enable:\n\tscan interrupted");
        System.err.println(getClass().getName()+".enable:\n\tscan interrupted");
      }
    }
  }

  private void transfer(TwainITransfer tt)throws TwainIOException{            // 5 -> 6
    try{
      byte[]  pendingXfers=new byte[6];                                       // TW_PENDINGXFERS
      do{
        setState(6);                                                          // [2] 4-31
        jtwain.setINT16(pendingXfers,0,-1);                                   // pendingXfer.Count = -1 => application can deal with multiple images
        try{
          tt.setCancel(userCancelled);
          tt.initiate();
        }catch(TwainResultException.TransferDone tretd){                      // state 7: memory allocated
          setState(7);
          tt.finish();
          call(DG_CONTROL,DAT_PENDINGXFERS,MSG_ENDXFER,pendingXfers);         // tell source we are done with image
          if(jtwain.getINT16(pendingXfers,0)==0){                             // state 6: if pendingXfers!=0
            setState(5);                                                      // state 5: if pendingXfers==0
          }
        }catch(TwainUserCancelException tuce){                                // state 6: cancel via setCancel(true)
          call(DG_CONTROL,DAT_PENDINGXFERS,MSG_RESET,pendingXfers);           // tell source to cancel pending images
          setState(5);
        }catch(TwainResultException.Cancel trec){                             // state 7: cancel via source gui
          tt.cancel();

          call(DG_CONTROL,DAT_PENDINGXFERS,MSG_ENDXFER,pendingXfers);         // state 6: tell source we are done with image
          if(jtwain.getINT16(pendingXfers,0) > 0){
            call(DG_CONTROL,DAT_PENDINGXFERS,MSG_RESET,pendingXfers);         // tell source to cancel pending images
          }
          setState(5);
        }catch(TwainFailureException tfe){                                    // state 6/7: no memory allocated
          jtwain.signalException(getClass().getName()+".transfer:\n\t"+tfe);

          call(DG_CONTROL,DAT_PENDINGXFERS,MSG_ENDXFER,pendingXfers);         // tell source we are done with image
          if(jtwain.getINT16(pendingXfers,0) > 0){
            call(DG_CONTROL,DAT_PENDINGXFERS,MSG_RESET,pendingXfers);         // tell source to cancel pending images
          }
          setState(5);
        }finally{
          tt.cleanup();
        }
      }while(jtwain.getINT16(pendingXfers,0)!=0);                             // ADF scanner: pendingXfers = -1 if not known
    }finally{
      if(userCancelled||(showUI==0)){                                         // User cannot close source
        userCancelled=false;
        disable();                                                            // hence close here
        close();
      }
    }
  }

  void transferImage()throws TwainIOException{
    switch(getXferMech()){
    case TWSX_NATIVE:   transfer(transferFactory.createNativeTransfer(this));  break;
    case TWSX_FILE:     transfer(transferFactory.createFileTransfer(this));    break;
    case TWSX_MEMORY:   transfer(transferFactory.createMemoryTransfer(this));  break;
    default:                                            // shouldn't be here
      System.out.println(getClass().getName()+".transferImage:\n\tDo not support this transfer mode: "+getXferMech());
      System.err.println(getClass().getName()+".transferImage:\n\tDo not support this transfer mode: "+getXferMech());
      break;
    }
  }

  void disable()throws TwainIOException{                    //  state 5 -> 4
    if(state<5){return;}

    byte[] gui=new byte[(jtwain.getPtrSize()==4)?8:12];     // TW_USERINTERFACE
    jtwain.setINT16(gui,0,-1);                              // TWON_DONTCARE8
    jtwain.setINT16(gui,2,0);
    jtwain.setPtr(gui,4,hWnd);                              // set parent window

    call(DG_CONTROL,DAT_USERINTERFACE,MSG_DISABLEDS,gui);
    setState(4);
  }

  public void close()throws TwainIOException{               //  state 4 -> 3
    if(state!=4){return;}                                   // [1] 7-176 state 4: 4 -> 3
    call(DG_CONTROL,DAT_IDENTITY,MSG_CLOSEDS,identity);     // close session
    busy=false;
    setState(3);
  }

  int callback(int dg,int dat,int msg,long data)throws TwainIOException{
    switch(msg){                                            // always messages from source
    case MSG_XFERREADY:                                     // state 5 -> 6
      tw20HaveImage = true;
      tw20Semaphore.release();
      break;
    case MSG_CLOSEDSOK:                                     // Do not use DG_CONTROL/DAT_USERINTERFACE/MSG_ENABLEDSUIONLY,
                                                            // hence shouldn't get this event
    case MSG_CLOSEDSREQ:                                    // source wants us to close it
      System.err.println("MSG_CLOSEDSOK,MSG_CLOSEDSREQ in callback routine");
      tw20HaveImage = false;
      tw20Semaphore.release();
      break;
    case MSG_DEVICEEVENT:                                   // Do not allow this event, hence don't get this event
    case MSG_NULL:                                          // Event fully processed by source
      break;
    default:
      System.err.println("9\b"+getClass().getName()+".callback:\n\tUnknown message in Twain 2.0 callback routine");
      return TWRC_FAILURE;
    }
    return TWRC_SUCCESS;
  }

  int handleGetMessage(long msgPtr)throws TwainIOException{ // callback functions cpp -> java; windows thread;
    if(state<5){return TWRC_NOTDSEVENT;}
    try{
      byte[] event=new byte[(jtwain.getPtrSize()==4)?6:10]; // TW_EVENT
      int off = jtwain.setPtr(event,0,msgPtr);              // twEvent.pEvent=(TW_MEMREF)&msg;
      jtwain.setINT16(event,off,0);                         // twEvent.TWMessage=MSG_NULL;
      call(DG_CONTROL,DAT_EVENT,MSG_PROCESSEVENT,event);    // [1] 7 - 162
      int message=jtwain.getINT16(event,off);               // if event was handled by source
      switch(message){                                      // any messages from source
      case MSG_XFERREADY:                                   // state 5 -> 6
        transferImage();                                    // source has an image for us
        break;
      case MSG_CLOSEDSOK:                                   // Do not use DG_CONTROL/DAT_USERINTERFACE/MSG_ENABLEDSUIONLY,
                                                            // hence shouldn't get this event
      case MSG_CLOSEDSREQ:                                  // source wants us to close it
        disable();
        close();
        break;
      case MSG_DEVICEEVENT:                                 // Do not allow this event, hence don't get this event
      case MSG_NULL:                                        // Event fully processed by source
      default:
      }
      return TWRC_DSEVENT;
    }catch(TwainResultException.NotDSEvent trendse){        // if event was not handled by source
      return TWRC_NOTDSEVENT;
    }
  }

  public int getXferMech()throws TwainIOException{
    return new TwainCapability.XferMech(this).intValue();
  }

  public void setXferMech(int mech){
    try{
      switch(mech){
      case TWSX_NATIVE:
      case TWSX_FILE:
      case TWSX_MEMORY:                     break;
      default:          mech=TWSX_NATIVE;   break;
      }
      TwainCapability tc;
      tc=getCapability(ICAP_XFERMECH,MSG_GETCURRENT);       // some sources cannot use enumerations to set capabilities,
      if(tc.intValue()!=mech){                              // hence use MSG_GETCURRENT => TW_ONEVALUE/TW_UINT16
        tc.setCurrentValue(mech);
        if(getCapability(ICAP_XFERMECH).intValue()!=mech){
          jtwain.signalException(getClass().getName()+".setXferMech:\n\tCannot change transfer mechanism to mode="+mech);
        }
      }
    }catch(TwainIOException e){                             // Shouldn't happen must be supported by all sources.
      jtwain.signalException(getClass().getName()+".setXferMech:\n\t"+e);
    }
  }

  int getImageFileFormat(){return iff;}

  public void setImageFileFormat(int iff){
    try{
      TwainCapability tc;
      switch(iff){
      case TWFF_TIFF:      case TWFF_BMP:     case TWFF_JFIF:
      case TWFF_TIFFMULTI: case TWFF_PNG:	  case TWFF_PDF:
        break;
      default:
        iff=TWFF_BMP;                                       // (must be supported by all windows sources)
        break;
      }
      tc=getCapability(ICAP_IMAGEFILEFORMAT,MSG_GETCURRENT);// TW_ONEVALUE/TW_UINT16
      if(tc.intValue()!=iff){
        tc.setCurrentValue(iff);
        if(getCapability(ICAP_IMAGEFILEFORMAT).intValue()!=iff){
          jtwain.signalException(getClass().getName()+".setImageFileFormat:\n\tCannot change file format to format="+iff);
        }
      }
    }catch(Exception e){
      jtwain.signalException(getClass().getName()+".setImageFileFormat:\n\t"+e);
    }
  }

//  public TwainFileSystem getFileSystem(){return new TwainFileSystem(this);}
}

// [1] Twain Spec 1.9
// [2] Twain Spec 2.0

