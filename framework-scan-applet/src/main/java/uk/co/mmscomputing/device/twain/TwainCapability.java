package uk.co.mmscomputing.device.twain;

import java.util.*;

public class TwainCapability implements TwainConstants{

// DAT_CAPABILITY. Used by application to get/set capability from/in a data source.
/*
typedef struct {
   TW_UINT16  Cap;                          // id of capability to set or get, e.g. CAP_BRIGHTNESS
   TW_UINT16  ConType;                      // TWON_ONEVALUE, _RANGE, _ENUMERATION or _ARRAY
   TW_HANDLE  hContainer;                   // Handle to container of type Dat
} TW_CAPABILITY, FAR * pTW_CAPABILITY;
*/

  protected TwainSource    source;
  protected int            cap;
  protected byte[]         buf=new byte[(jtwain.getPtrSize()==4)?8:12];  // TW_CAPABILITY 32 bit OS: 8 Bytes; 64 bit OS: 12 Bytes
  protected TwainContainer container;

  TwainCapability(TwainSource source,int cap)throws TwainIOException{
    this.source=source;
    this.cap=cap;
    this.container=null;
    container=get();
  }

  TwainCapability(TwainSource source,int cap,int mode)throws TwainIOException{
    this.source=source;
    this.cap=cap;
    this.container=null;
    switch(mode){
    case MSG_GET:         container=get();        break;
    case MSG_GETCURRENT:  container=getCurrent(); break;
    case MSG_GETDEFAULT:  container=getDefault(); break;
    default:              container=get();        break;
    }
  }

  private TwainContainer get(int msg,int contype)throws TwainIOException{
    jtwain.setINT16(buf,0,cap);                                   // Cap        : set capability we are looking for
    jtwain.setINT16(buf,2,contype);                               // ConType    : what container do we accept
//    jtwain.setPtr(buf,4,0);                                     // hContainer : source will set handle to container

    source.call(DG_CONTROL,DAT_CAPABILITY,msg,buf);               // [1] 7-145 pp state 4 - 7; get capability container

    int    containerType =jtwain.getINT16(buf,2);                 // get container type

//  10/01/09
//  long   containerPtr  =jtwain.getPTR(buf,4);                   // ptr to native capability container
//  byte[] container     =jtwain.ngetContainer(containerType,containerPtr); // get java capability container, free native container

    byte[] container     =jtwain.ngetContainer(buf);              // get java capability container, free native container

    switch(containerType){                                        // As of TWAIN 1.9 spec these are the only container types possible
      case TWON_ARRAY:       return new TwainArray(cap,container);
      case TWON_ENUMERATION: return new TwainEnumeration(cap,container);
      case TWON_ONEVALUE:    return new TwainOneValue(cap,container);
      case TWON_RANGE:       return new TwainRange(cap,container);
      default: throw new TwainIOException(getClass().getName()+".get:\n\tUnknown container type.");
    }
  }

  private TwainContainer get(int msg)throws TwainIOException{
    return get(msg,-1);                                           // ConType: accept any container type
  }

  public TwainContainer get()throws TwainIOException{
    container= get(MSG_GET);                                      // [1] 7-145 state 4 - 7; get capability container
    return container;
  }

  public TwainContainer getCurrent()throws TwainIOException{
    return get(MSG_GETCURRENT);                                   // [1] 7-147 state 4 - 7; get current capability container
  }

  public TwainContainer getDefault()throws TwainIOException{
    return get(MSG_GETDEFAULT);                                   // [1] 7-149 state 4 - 7; get default capability container
  }

  public int querySupport()throws TwainIOException{               // what actions are allowed on this capability
    return get(MSG_QUERYSUPPORT,TWON_ONEVALUE).intValue();        // [1] 7-151 state 4 - 7; TWTY_INT32 flags TWQC_GET, TWQC_SET ...
  }

  public boolean querySupport(int flagMask){
    try{
      int flags=get(MSG_QUERYSUPPORT,TWON_ONEVALUE).intValue();
      return ((flags&flagMask)!=0);
    }catch(TwainIOException tioe){                                // if some error assume not supported
      System.out.println("3\b"+getClass().getName()+".querySupport:\n\t"+tioe.getMessage());
      return false;
    }
  }

  public TwainContainer reset()throws TwainIOException{
    container = get(MSG_RESET);                                   // [1] 7-153 state 4; reset to twain or source default
    return container;
  }

  public TwainContainer set()throws TwainIOException{
    container = set(container);
    return container;
  }

  private TwainContainer set(TwainContainer container)throws TwainIOException{    // use only in state 4
    int    containerType    =container.getType();
    byte[] containerBytes   =container.getBytes();

//    10/01/09
//    long   containerHandle  =jtwain.nsetContainer(containerType,containerBytes);  // allocate native memory and copy container data into it.

    try{
      jtwain.setINT16(buf,0,cap);                                   // set capability
      jtwain.setINT16(buf,2,containerType);                         // set container type
//      jtwain.setPTR(buf,4,containerHandle);                       // set native container
      jtwain.nsetContainer(buf,containerBytes);                     // allocate native memory and copy container data into it.

/*
      System.err.println("buf.length = "+buf.length);
      System.err.println(TwainContainer.toString(buf));
      System.err.println(container.toString());
      System.err.println(TwainContainer.toString(containerBytes));
*/
      source.call(DG_CONTROL,DAT_CAPABILITY,MSG_SET,buf);
    }catch(TwainResultException.CheckStatus trecs){
      container=get();
    }finally{
      jtwain.nfreeContainer(buf);                                   // release native container memory again
    }
    return container;
  }

  public Object[] getItems(){ return container.getItems();}

  public boolean booleanValue()throws TwainIOException{ return getCurrent().booleanValue();}
  public int     intValue()    throws TwainIOException{ return getCurrent().intValue();}
  public double  doubleValue() throws TwainIOException{ return getCurrent().doubleValue();}

  public void setCurrentValue(boolean v)throws TwainIOException{setCurrentValue(new Boolean(v));}
  public void setCurrentValue(int v)    throws TwainIOException{setCurrentValue(new Integer(v));}
  public void setCurrentValue(double v) throws TwainIOException{setCurrentValue(new Double(v));}

  public void setCurrentValue(Object val)throws TwainIOException{
    container.setCurrentValue(val);set();
  }

  public boolean booleanDefaultValue()throws TwainIOException{ return getDefault().booleanDefaultValue();}
  public int     intDefaultValue()    throws TwainIOException{ return getDefault().intDefaultValue();}
  public double  doubleDefaultValue() throws TwainIOException{ return getDefault().doubleDefaultValue();}

  public void setDefaultValue(boolean v)throws TwainIOException{setDefaultValue(new Boolean(v));}
  public void setDefaultValue(int v)    throws TwainIOException{setDefaultValue(new Integer(v));}
  public void setDefaultValue(double v) throws TwainIOException{setDefaultValue(new Double(v));}

  public void setDefaultValue(Object val)throws TwainIOException{
    container.setDefaultValue(val);set();
  }

  protected String toString(String[] strs){
    String s=getClass().getName()+"\n";

    Object[] items =container.getItems();
    for(int i=0;i<items.length;i++){
      int n=((Number)items[i]).intValue();
      s+=strs[n]+"\n";
    }
    s+="\n"+container.toString();
    return s;
  }

  public String getName(){
    String name=getCapName(cap);
    if(name==null){
      return "Cap_0x"+Integer.toHexString(cap);
    }
    return name;
  }

  public String toString(){
    String s=getClass().getName()+"\n";
    s+="name         = "+getName()+"\n";
    s+=container.toString();
    return s;
  }

  static public TwainCapability[] getCapabilities(TwainSource source)throws TwainIOException{
    TwainCapability tc    = source.getCapability(CAP_SUPPORTEDCAPS);
    Object[]        items = tc.getItems();
    Vector  caps  = new Vector();

    for(int i=0;i<items.length;i++){
      int capid=((Number)items[i]).intValue();
      try{
        switch(capid){
        case ICAP_COMPRESSION:     caps.add(new Compression(source)); break;            // 0x0100
        case ICAP_XFERMECH:        caps.add(new XferMech(source)); break;               // 0x0103
        case ICAP_IMAGEFILEFORMAT: caps.add(new ImageFileFormat(source)); break;        // 0x110C
        default:                   caps.add(new TwainCapability(source,capid)); break;
        }
      }catch(TwainFailureException.BadCap tfebc){           // Unknown capability
      }catch(TwainFailureException.CapUnsupported tfecu){   // Capability not supported by source
      }catch(TwainIOException tioe){
        String name=getCapName(capid);
        if(name==null){name="Cap_0x"+Integer.toHexString(capid)+"["+Integer.toString(capid)+"]";}
        System.out.println("3\b\n"+name+"\n\t"+tioe.toString());
      }
    }
    return (TwainCapability[])caps.toArray(new TwainCapability[0]);
  }

  //----- ICAP_COMPRESSION 0x0100 -----

  static public class Compression extends TwainCapability{
    Compression(TwainSource source) throws TwainIOException{
      super(source,ICAP_COMPRESSION);
    }

    public String toString(){return toString(CompressionStrings);}
  }

  //----- ICAP_XFERMECH 0x0103 -----

  static public class XferMech extends TwainCapability{
    XferMech(TwainSource source) throws TwainIOException{
      super(source,ICAP_XFERMECH);
    }

    public String toString(){return toString(XferMechStrings);}

    public int intValue(){
      try{
        return super.intValue();
      }catch(Exception e){                                      // Shouldn't happen must be supported by all sources.
        System.err.println(getClass().getName()+".intValue:\n\t"+e);
        return TWSX_NATIVE;                                     // if some error assume source does native transfer
      }
    }
  }

  //----- ICAP_IMAGEFILEFORMAT 0x110C -----

  static public class ImageFileFormat extends TwainCapability{
    ImageFileFormat(TwainSource source) throws TwainIOException{
      super(source,ICAP_IMAGEFILEFORMAT);
    }

    public String toString(){return toString(ImageFileFormatStrings);}
  }

  //----- -----

  static private Hashtable caps;

  static public String getCapName(int i){
    return (String)caps.get(new Integer(i));
  }

  static{
     caps = new Hashtable();
     caps.put(new Integer(0x0001), "CAP_XFERCOUNT");
     caps.put(new Integer(0x0100), "ICAP_COMPRESSION");
     caps.put(new Integer(0x0101), "ICAP_PIXELTYPE");
     caps.put(new Integer(0x0102), "ICAP_UNITS");
     caps.put(new Integer(0x0103), "ICAP_XFERMECH");

     caps.put(new Integer(0x1000), "CAP_AUTHOR");
     caps.put(new Integer(0x1001), "CAP_CAPTION");
     caps.put(new Integer(0x1002), "CAP_FEEDERENABLED");
     caps.put(new Integer(0x1003), "CAP_FEEDERLOADED");
     caps.put(new Integer(0x1004), "CAP_TIMEDATE");
     caps.put(new Integer(0x1005), "CAP_SUPPORTEDCAPS");
     caps.put(new Integer(0x1006), "CAP_EXTENDEDCAPS");
     caps.put(new Integer(0x1007), "CAP_AUTOFEED");
     caps.put(new Integer(0x1008), "CAP_CLEARPAGE");
     caps.put(new Integer(0x1009), "CAP_FEEDPAGE");
     caps.put(new Integer(0x100a), "CAP_REWINDPAGE");
     caps.put(new Integer(0x100b), "CAP_INDICATORS");
     caps.put(new Integer(0x100c), "CAP_SUPPORTEDCAPSEXT");
     caps.put(new Integer(0x100d), "CAP_PAPERDETECTABLE");
     caps.put(new Integer(0x100e), "CAP_UICONTROLLABLE");
     caps.put(new Integer(0x100f), "CAP_DEVICEONLINE");

     caps.put(new Integer(0x1010), "CAP_AUTOSCAN");
     caps.put(new Integer(0x1011), "CAP_THUMBNAILSENABLED");
     caps.put(new Integer(0x1012), "CAP_DUPLEX");
     caps.put(new Integer(0x1013), "CAP_DUPLEXENABLED");
     caps.put(new Integer(0x1014), "CAP_ENABLEDSUIONLY");
     caps.put(new Integer(0x1015), "CAP_CUSTOMDSDATA");
     caps.put(new Integer(0x1016), "CAP_ENDORSER");
     caps.put(new Integer(0x1017), "CAP_JOBCONTROL");
     caps.put(new Integer(0x1018), "CAP_ALARMS");
     caps.put(new Integer(0x1019), "CAP_ALARMVOLUME");
     caps.put(new Integer(0x101a), "CAP_AUTOMATICCAPTURE");
     caps.put(new Integer(0x101b), "CAP_TIMEBEFOREFIRSTCAPTURE");
     caps.put(new Integer(0x101c), "CAP_TIMEBETWEENCAPTURES");
     caps.put(new Integer(0x101d), "CAP_CLEARBUFFERS");
     caps.put(new Integer(0x101e), "CAP_MAXBATCHBUFFERS");
     caps.put(new Integer(0x101f), "CAP_DEVICETIMEDATE");

     caps.put(new Integer(0x1020), "CAP_POWERSUPPLY");
     caps.put(new Integer(0x1021), "CAP_CAMERAPREVIEWUI");
     caps.put(new Integer(0x1022), "CAP_DEVICEEVENT");
     caps.put(new Integer(0x1023), "CAP_PAGEMULTIPLEACQUIRE");
     caps.put(new Integer(0x1024), "CAP_SERIALNUMBER");
     caps.put(new Integer(0x1025), "CAP_FILESYSTEM");
     caps.put(new Integer(0x1026), "CAP_PRINTER");
     caps.put(new Integer(0x1027), "CAP_PRINTERENABLED");
     caps.put(new Integer(0x1028), "CAP_PRINTERINDEX");
     caps.put(new Integer(0x1029), "CAP_PRINTERMODE");
     caps.put(new Integer(0x102a), "CAP_PRINTERSTRING");
     caps.put(new Integer(0x102b), "CAP_PRINTERSUFFIX");
     caps.put(new Integer(0x102c), "CAP_LANGUAGE");
     caps.put(new Integer(0x102d), "CAP_FEEDERALIGNMENT");
     caps.put(new Integer(0x102e), "CAP_FEEDERORDER");
     caps.put(new Integer(0x102f), "CAP_PAPERBINDING");

     caps.put(new Integer(0x1030), "CAP_REACQUIREALLOWED");
     caps.put(new Integer(0x1031), "CAP_PASSTHRU");
     caps.put(new Integer(0x1032), "CAP_BATTERYMINUTES");
     caps.put(new Integer(0x1033), "CAP_BATTERYPERCENTAGE");
     caps.put(new Integer(0x1034), "CAP_POWERDOWNTIME");

     caps.put(new Integer(0x1100), "ICAP_AUTOBRIGHT");
     caps.put(new Integer(0x1101), "ICAP_BRIGHTNESS");

     caps.put(new Integer(0x1103), "ICAP_CONTRAST");
     caps.put(new Integer(0x1104), "ICAP_CUSTHALFTONE");
     caps.put(new Integer(0x1105), "ICAP_EXPOSURETIME");
     caps.put(new Integer(0x1106), "ICAP_FILTER");
     caps.put(new Integer(0x1107), "ICAP_FLASHUSED");
     caps.put(new Integer(0x1108), "ICAP_GAMMA");
     caps.put(new Integer(0x1109), "ICAP_HALFTONES");
     caps.put(new Integer(0x110a), "ICAP_HIGHLIGHT");

     caps.put(new Integer(0x110c), "ICAP_IMAGEFILEFORMAT");
     caps.put(new Integer(0x110d), "ICAP_LAMPSTATE");
     caps.put(new Integer(0x110e), "ICAP_LIGHTSOURCE");


     caps.put(new Integer(0x1110), "ICAP_ORIENTATION");
     caps.put(new Integer(0x1111), "ICAP_PHYSICALWIDTH");
     caps.put(new Integer(0x1112), "ICAP_PHYSICALHEIGHT");
     caps.put(new Integer(0x1113), "ICAP_SHADOW");
     caps.put(new Integer(0x1114), "ICAP_FRAMES");

     caps.put(new Integer(0x1116), "ICAP_XNATIVERESOLUTION");
     caps.put(new Integer(0x1117), "ICAP_YNATIVERESOLUTION");
     caps.put(new Integer(0x1118), "ICAP_XRESOLUTION");
     caps.put(new Integer(0x1119), "ICAP_YRESOLUTION");
     caps.put(new Integer(0x111a), "ICAP_MAXFRAMES");
     caps.put(new Integer(0x111b), "ICAP_TILES");
     caps.put(new Integer(0x111c), "ICAP_BITORDER");
     caps.put(new Integer(0x111d), "ICAP_CCITTKFACTOR");
     caps.put(new Integer(0x111e), "ICAP_LIGHTPATH");
     caps.put(new Integer(0x111f), "ICAP_PIXELFLAVOR");

     caps.put(new Integer(0x1120), "ICAP_PLANARCHUNKY");
     caps.put(new Integer(0x1121), "ICAP_ROTATION");
     caps.put(new Integer(0x1122), "ICAP_SUPPORTEDSIZES");
     caps.put(new Integer(0x1123), "ICAP_THRESHOLD");
     caps.put(new Integer(0x1124), "ICAP_XSCALING");
     caps.put(new Integer(0x1125), "ICAP_YSCALING");
     caps.put(new Integer(0x1126), "ICAP_BITORDERCODES");
     caps.put(new Integer(0x1127), "ICAP_PIXELFLAVORCODES");
     caps.put(new Integer(0x1128), "ICAP_JPEGPIXELTYPE");

     caps.put(new Integer(0x112a), "ICAP_TIMEFILL");
     caps.put(new Integer(0x112b), "ICAP_BITDEPTH");
     caps.put(new Integer(0x112c), "ICAP_BITDEPTHREDUCTION");
     caps.put(new Integer(0x112d), "ICAP_UNDEFINEDIMAGESIZE");
     caps.put(new Integer(0x112e), "ICAP_IMAGEDATASET");
     caps.put(new Integer(0x112f), "ICAP_EXTIMAGEINFO");

     caps.put(new Integer(0x1130), "ICAP_MINIMUMHEIGHT");
     caps.put(new Integer(0x1131), "ICAP_MINIMUMWIDTH");


     caps.put(new Integer(0x1134), "ICAP_AUTODISCARDBLANKPAGES");

     caps.put(new Integer(0x1136), "ICAP_FLIPROTATION");
     caps.put(new Integer(0x1137), "ICAP_BARCODEDETECTIONENABLED");
     caps.put(new Integer(0x1138), "ICAP_SUPPORTEDBARCODETYPES");
     caps.put(new Integer(0x1139), "ICAP_BARCODEMAXSEARCHPRIORITIES");
     caps.put(new Integer(0x113a), "ICAP_BARCODESEARCHPRIORITIES");
     caps.put(new Integer(0x113b), "ICAP_BARCODESEARCHMODE");
     caps.put(new Integer(0x113c), "ICAP_BARCODEMAXRETRIES");
     caps.put(new Integer(0x113d), "ICAP_BARCODETIMEOUT");
     caps.put(new Integer(0x113e), "ICAP_ZOOMFACTOR");
     caps.put(new Integer(0x113f), "ICAP_PATCHCODEDETECTIONENABLED");

     caps.put(new Integer(0x1140), "ICAP_SUPPORTEDPATCHCODETYPES");
     caps.put(new Integer(0x1141), "ICAP_PATCHCODEMAXSEARCHPRIORITIES");
     caps.put(new Integer(0x1142), "ICAP_PATCHCODESEARCHPRIORITIES");
     caps.put(new Integer(0x1143), "ICAP_PATCHCODESEARCHMODE");
     caps.put(new Integer(0x1144), "ICAP_PATCHCODEMAXRETRIES");
     caps.put(new Integer(0x1145), "ICAP_PATCHCODETIMEOUT");
     caps.put(new Integer(0x1146), "ICAP_FLASHUSED2");
     caps.put(new Integer(0x1147), "ICAP_IMAGEFILTER");
     caps.put(new Integer(0x1148), "ICAP_NOISEFILTER");
     caps.put(new Integer(0x1149), "ICAP_OVERSCAN");

     caps.put(new Integer(0x1150), "ICAP_AUTOMATICBORDERDETECTION");
     caps.put(new Integer(0x1151), "ICAP_AUTOMATICDESKEW");
     caps.put(new Integer(0x1152), "ICAP_AUTOMATICROTATE");
  }
}

