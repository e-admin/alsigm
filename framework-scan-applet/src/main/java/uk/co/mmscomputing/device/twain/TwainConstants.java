package uk.co.mmscomputing.device.twain;

public interface TwainConstants{

//----- TWAIN states -----

  static public final int STATE_UNDEFINED    = 0;
  static public final int STATE_PRESESSION   = 1;
  static public final int STATE_SRCMNGLOADED = 2;
  static public final int STATE_SRCMNGOPEN   = 3;
  static public final int STATE_SRCOPEN      = 4;
  static public final int STATE_SRCENABLED   = 5;
  static public final int STATE_TRANSFERREADY= 6;
  static public final int STATE_TRANSFERRING = 7;

//----- twain.h

  static public final int TWRC_CUSTOMBASE       = 0x8000;

  static public final int TWRC_SUCCESS          = 0;
  static public final int TWRC_FAILURE          = 1; // Application may get TW_STATUS for info on failure
  static public final int TWRC_CHECKSTATUS      = 2; // "tried hard"; get status
  static public final int TWRC_CANCEL           = 3;
  static public final int TWRC_DSEVENT          = 4;
  static public final int TWRC_NOTDSEVENT       = 5;
  static public final int TWRC_XFERDONE         = 6;
  static public final int TWRC_ENDOFLIST        = 7; // After MSG_GETNEXT if nothing left
  static public final int TWRC_INFONOTSUPPORTED = 8;
  static public final int TWRC_DATANOTAVAILABLE = 9;

  static public final int TWCC_SUCCESS          = 0; // It worked!
  static public final int TWCC_BUMMER           = 1; // Failure due to unknown causes
  static public final int TWCC_LOWMEMORY        = 2; // Not enough memory to perform operation
  static public final int TWCC_NODS             = 3; // No Data Source
  static public final int TWCC_MAXCONNECTIONS   = 4; // DS is connected to max possible applications
  static public final int TWCC_OPERATIONERROR   = 5; // DS or DSM reported error, application shouldn't
  static public final int TWCC_BADCAP           = 6; // Unknown capability

  static public final int TWCC_BADPROTOCOL      = 9; // Unrecognized MSG DG DAT combination
  static public final int TWCC_BADVALUE         = 10; // Data parameter out of range
  static public final int TWCC_SEQERROR         = 11; // DG DAT MSG out of expected sequence
  static public final int TWCC_BADDEST          = 12; // Unknown destination Application/Source in DSM_Entry
  static public final int TWCC_CAPUNSUPPORTED   = 13; // Capability not supported by source
  static public final int TWCC_CAPBADOPERATION  = 14; // Operation not supported by capability
  static public final int TWCC_CAPSEQERROR      = 15; // Capability has dependancy on other capability

  static public final int DG_CONTROL         = 0x0001;
  static public final int DG_IMAGE           = 0x0002;
  static public final int DG_AUDIO           = 0x0004;
                                                            // Twain 2.0
  static public final int DF_DSM2            = 0x10000000;  // added to the identity by the DSM
  static public final int DF_APP2            = 0x20000000;  // Set by the App to indicate it would prefer to use DSM2
  static public final int DF_DS2             = 0x40000000;  // Set by the DS to indicate it would prefer to use DSM2

  static public final int DG_MASK            = 0x0000FFFF;  // all Data Groups limited to 16bit

  static public final int DAT_NULL           = 0x0000; // No data or structure.
  static public final int DAT_CUSTOMBASE     = 0x8000; // Base of custom DATs.

  // Data Argument Types for the DG_CONTROL Data Group.

  static public final int DAT_CAPABILITY     = 0x0001; // TW_CAPABILITY
  static public final int DAT_EVENT          = 0x0002; // TW_EVENT
  static public final int DAT_IDENTITY       = 0x0003; // TW_IDENTITY
  static public final int DAT_PARENT         = 0x0004; // TW_HANDLE, application win handle in Windows
  static public final int DAT_PENDINGXFERS   = 0x0005; // TW_PENDINGXFERS
  static public final int DAT_SETUPMEMXFER   = 0x0006; // TW_SETUPMEMXFER
  static public final int DAT_SETUPFILEXFER  = 0x0007; // TW_SETUPFILEXFER
  static public final int DAT_STATUS         = 0x0008; // TW_STATUS
  static public final int DAT_USERINTERFACE  = 0x0009; // TW_USERINTERFACE
  static public final int DAT_XFERGROUP      = 0x000a; // TW_UINT32
  static public final int DAT_TWUNKIDENTITY  = 0x000b; // TW_TWUNKIDENTITY
  static public final int DAT_CUSTOMDSDATA   = 0x000c; // TW_CUSTOMDSDATA.
  static public final int DAT_DEVICEEVENT    = 0x000d; // TW_DEVICEEVENT
  static public final int DAT_FILESYSTEM     = 0x000e; // TW_FILESYSTEM
  static public final int DAT_PASSTHRU       = 0x000f; // TW_PASSTHRU
  static public final int DAT_CALLBACK       = 0x0010; // TW_CALLBACK 2.0

  // Data Argument Types for the DG_IMAGE Data Group.

  static public final int DAT_IMAGEINFO      = 0x0101; // TW_IMAGEINFO
  static public final int DAT_IMAGELAYOUT    = 0x0102; // TW_IMAGELAYOUT
  static public final int DAT_IMAGEMEMXFER   = 0x0103; // TW_IMAGEMEMXFER
  static public final int DAT_IMAGENATIVEXFER= 0x0104; // TW_UINT32 loword is hDIB, PICHandle
  static public final int DAT_IMAGEFILEXFER  = 0x0105; // Null data
  static public final int DAT_CIECOLOR       = 0x0106; // TW_CIECOLOR
  static public final int DAT_GRAYRESPONSE   = 0x0107; // TW_GRAYRESPONSE
  static public final int DAT_RGBRESPONSE    = 0x0108; // TW_RGBRESPONSE
  static public final int DAT_JPEGCOMPRESSION= 0x0109; // TW_JPEGCOMPRESSION
  static public final int DAT_PALETTE8       = 0x010a; // TW_PALETTE8
  static public final int DAT_EXTIMAGEINFO   = 0x010b; // TW_EXTIMAGEINFO -- for 1.7 Spec.

                                                       // Data Argument Types for the DG_AUDIO Data Group
  static public final int DAT_AUDIOFILEXFER  = 0x0201; // 1.8 Null data
  static public final int DAT_AUDIOINFO      = 0x0202; // 1.8 TW_AUDIOINFO
  static public final int DAT_AUDIONATIVEXFER= 0x0203; // 1.8 TW_UINT32 handle to WAV, (AIFF Mac)

  static public final int DAT_ICCPROFILE     = 0x0401; // 1.91
  static public final int DAT_IMAGEMEMFILEXFER=0x0402; // 1.91

  static public final int DAT_ENTRYPOINT     = 0x0403; // 2.0

// All message constants are unique.
// Messages are grouped according to which DATs they are used with.

  static public final int MSG_NULL           = 0x0000; // Used in TW_EVENT structure
  static public final int MSG_CUSTOMBASE     = 0x8000; // Base of custom messages

// Generic messages may be used with any of several DATs.

  static public final int MSG_GET            = 0x0001; // Get one or more values
  static public final int MSG_GETCURRENT     = 0x0002; // Get current value
  static public final int MSG_GETDEFAULT     = 0x0003; // Get default (e.g. power up) value
  static public final int MSG_GETFIRST       = 0x0004; // Get first of a series of items, e.g. DSs
  static public final int MSG_GETNEXT        = 0x0005; // Iterate through a series of items.
  static public final int MSG_SET            = 0x0006; // Set one or more values
  static public final int MSG_RESET          = 0x0007; // Set current value to default value
  static public final int MSG_QUERYSUPPORT   = 0x0008; // Get supported operations on the cap.

// Messages used with DAT_NULL

  static public final int MSG_XFERREADY      = 0x0101;
  static public final int MSG_CLOSEDSREQ     = 0x0102;
  static public final int MSG_CLOSEDSOK      = 0x0103;
  static public final int MSG_DEVICEEVENT    = 0x0104;

// Messages used with a pointer to DAT_PARENT data
  static public final int MSG_OPENDSM        = 0x0301; // Open the DSM
  static public final int MSG_CLOSEDSM       = 0x0302; // Close the DSM

  // Messages used with a pointer to a DAT_IDENTITY structure
  static public final int MSG_OPENDS         = 0x0401; // Open a data source
  static public final int MSG_CLOSEDS        = 0x0402; // Close a data source
  static public final int MSG_USERSELECT     = 0x0403; // Put up a dialog of all DS

  // Messages used with a pointer to a DAT_USERINTERFACE structure
  static public final int MSG_DISABLEDS      = 0x0501; // Disable data transfer in the DS
  static public final int MSG_ENABLEDS       = 0x0502; // Enable data transfer in the DS
  static public final int MSG_ENABLEDSUIONLY = 0x0503; // Enable for saving DS state only.

  // Messages used with a pointer to a DAT_EVENT structure
  static public final int MSG_PROCESSEVENT   = 0x0601;

  // Messages used with a pointer to a DAT_PENDINGXFERS structure
  static public final int MSG_ENDXFER        = 0x0701;

// 1.8 Messages used with a pointer to a DAT_FILESYSTEM structure
  static public final int MSG_CHANGEDIRECTORY           =0x0801;
  static public final int MSG_CREATEDIRECTORY           =0x0802;
  static public final int MSG_DELETE                    =0x0803;
  static public final int MSG_FORMATMEDIA               =0x0804;
  static public final int MSG_GETCLOSE                  =0x0805;
  static public final int MSG_GETFIRSTFILE              =0x0806;
  static public final int MSG_GETINFO                   =0x0807;
  static public final int MSG_GETNEXTFILE               =0x0808;
  static public final int MSG_RENAME                    =0x0809;
  static public final int MSG_COPY                      =0x080A;
  static public final int MSG_AUTOMATICCAPTUREDIRECTORY =0x080B;

  static public final int MSG_PASSTHRU                  =0x0901; // Messages used with a pointer to a DAT_PASSTHRU structure
  static public final int MSG_REGISTER_CALLBACK         =0x0902; // used with DAT_CALLBACK
  static public final int MSG_RESETALL                  =0x0A01; // 1.91

  // Capability Container

  static public final int TWON_ARRAY           =3; // indicates TW_ARRAY container
  static public final int TWON_ENUMERATION     =4; // indicates TW_ENUMERATION container
  static public final int TWON_ONEVALUE        =5; // indicates TW_ONEVALUE container
  static public final int TWON_RANGE           =6; // indicates TW_RANGE container

  // Capability Type

  static public final int TWTY_INT8        =0x0000;    // Means Item is a TW_INT8
  static public final int TWTY_INT16       =0x0001;    // Means Item is a TW_INT16
  static public final int TWTY_INT32       =0x0002;    // Means Item is a TW_INT32

  static public final int TWTY_UINT8       =0x0003;    // Means Item is a TW_UINT8
  static public final int TWTY_UINT16      =0x0004;    // Means Item is a TW_UINT16
  static public final int TWTY_UINT32      =0x0005;    // Means Item is a TW_UINT32

  static public final int TWTY_BOOL        =0x0006;    // Means Item is a TW_BOOL

  static public final int TWTY_FIX32       =0x0007;    // Means Item is a TW_FIX32

  static public final int TWTY_FRAME       =0x0008;    // Means Item is a TW_FRAME

  static public final int TWTY_STR32       =0x0009;    // Means Item is a TW_STR32
  static public final int TWTY_STR64       =0x000a;    // Means Item is a TW_STR64
  static public final int TWTY_STR128      =0x000b;    // Means Item is a TW_STR128
  static public final int TWTY_STR255      =0x000c;    // Means Item is a TW_STR255
  static public final int TWTY_STR1024     =0x000d;    // Means Item is a TW_STR1024  ...added 1.9
  static public final int TWTY_UNI512      =0x000e;    // Means Item is a TW_UNI512   ...added 1.9

  static public final int CAP_CUSTOMBASE          =0x8000; // Base of custom capabilities

  // all data sources are REQUIRED to support these caps
  static public final int CAP_XFERCOUNT           =0x0001;

  // image data sources are REQUIRED to support these caps
  static public final int ICAP_COMPRESSION        =0x0100;
  static public final int ICAP_PIXELTYPE          =0x0101;
  static public final int ICAP_UNITS              =0x0102; // default is TWUN_INCHES
  static public final int ICAP_XFERMECH           =0x0103;

// all data sources MAY support these caps
  static public final int CAP_AUTHOR                  =0x01000;
  static public final int CAP_CAPTION                 =0x01001;
  static public final int CAP_FEEDERENABLED           =0x01002;
  static public final int CAP_FEEDERLOADED            =0x01003;
  static public final int CAP_TIMEDATE                =0x01004;
  static public final int CAP_SUPPORTEDCAPS           =0x01005;
  static public final int CAP_EXTENDEDCAPS            =0x01006;
  static public final int CAP_AUTOFEED                =0x01007;
  static public final int CAP_CLEARPAGE               =0x01008;
  static public final int CAP_FEEDPAGE                =0x01009;
  static public final int CAP_REWINDPAGE              =0x0100a;
  static public final int CAP_INDICATORS              =0x0100b;   // Added 1.1
  static public final int CAP_SUPPORTEDCAPSEXT        =0x0100c;   // Added 1.6
  static public final int CAP_PAPERDETECTABLE         =0x0100d;   // Added 1.6
  static public final int CAP_UICONTROLLABLE          =0x0100e;   // Added 1.6
  static public final int CAP_DEVICEONLINE            =0x0100f;   // Added 1.6
  static public final int CAP_AUTOSCAN                =0x01010;   // Added 1.6
  static public final int CAP_THUMBNAILSENABLED       =0x01011;   // Added 1.7
  static public final int CAP_DUPLEX                  =0x01012;   // Added 1.7
  static public final int CAP_DUPLEXENABLED           =0x01013;   // Added 1.7
  static public final int CAP_ENABLEDSUIONLY          =0x01014;   // Added 1.7
  static public final int CAP_CUSTOMDSDATA            =0x01015;   // Added 1.7
  static public final int CAP_ENDORSER                =0x01016;   // Added 1.7
  static public final int CAP_JOBCONTROL              =0x01017;   // Added 1.7
  static public final int CAP_ALARMS                  =0x01018;   // Added 1.8
  static public final int CAP_ALARMVOLUME             =0x01019;   // Added 1.8
  static public final int CAP_AUTOMATICCAPTURE        =0x0101a;   // Added 1.8
  static public final int CAP_TIMEBEFOREFIRSTCAPTURE  =0x0101b;   // Added 1.8
  static public final int CAP_TIMEBETWEENCAPTURES     =0x0101c;   // Added 1.8
  static public final int CAP_CLEARBUFFERS            =0x0101d;   // Added 1.8
  static public final int CAP_MAXBATCHBUFFERS         =0x0101e;   // Added 1.8
  static public final int CAP_DEVICETIMEDATE          =0x0101f;   // Added 1.8
  static public final int CAP_POWERSUPPLY             =0x01020;   // Added 1.8
  static public final int CAP_CAMERAPREVIEWUI         =0x01021;   // Added 1.8
  static public final int CAP_DEVICEEVENT             =0x01022;   // Added 1.8
  static public final int CAP_PAGEMULTIPLEACQUIRE     =0x01023;   // Added 1.8
  static public final int CAP_SERIALNUMBER            =0x01024;   // Added 1.8
  static public final int CAP_FILESYSTEM              =0x01025;   // Added 1.8
  static public final int CAP_PRINTER                 =0x01026;   // Added 1.8
  static public final int CAP_PRINTERENABLED          =0x01027;   // Added 1.8
  static public final int CAP_PRINTERINDEX            =0x01028;   // Added 1.8
  static public final int CAP_PRINTERMODE             =0x01029;   // Added 1.8
  static public final int CAP_PRINTERSTRING           =0x0102a;   // Added 1.8
  static public final int CAP_PRINTERSUFFIX           =0x0102b;   // Added 1.8
  static public final int CAP_LANGUAGE                =0x0102c;   // Added 1.8
  static public final int CAP_FEEDERALIGNMENT         =0x0102d;   // Added 1.8
  static public final int CAP_FEEDERORDER             =0x0102e;   // Added 1.8
  static public final int CAP_PAPERBINDING            =0x0102f;   // Added 1.8
  static public final int CAP_REACQUIREALLOWED        =0x01030;   // Added 1.8
  static public final int CAP_PASSTHRU                =0x01031;   // Added 1.8
  static public final int CAP_BATTERYMINUTES          =0x01032;   // Added 1.8
  static public final int CAP_BATTERYPERCENTAGE       =0x01033;   // Added 1.8
  static public final int CAP_POWERDOWNTIME           =0x01034;   // Added 1.8

  // image data sources MAY support these caps
  static public final int ICAP_AUTOBRIGHT                   =0x01100;
  static public final int ICAP_BRIGHTNESS                   =0x01101;
  static public final int ICAP_CONTRAST                     =0x01103;
  static public final int ICAP_CUSTHALFTONE                 =0x01104;
  static public final int ICAP_EXPOSURETIME                 =0x01105;
  static public final int ICAP_FILTER                       =0x01106;
  static public final int ICAP_FLASHUSED                    =0x01107;
  static public final int ICAP_GAMMA                        =0x01108;
  static public final int ICAP_HALFTONES                    =0x01109;
  static public final int ICAP_HIGHLIGHT                    =0x0110a;
  static public final int ICAP_IMAGEFILEFORMAT              =0x0110c;
  static public final int ICAP_LAMPSTATE                    =0x0110d;
  static public final int ICAP_LIGHTSOURCE                  =0x0110e;
  static public final int ICAP_ORIENTATION                  =0x01110;
  static public final int ICAP_PHYSICALWIDTH                =0x01111;
  static public final int ICAP_PHYSICALHEIGHT               =0x01112;
  static public final int ICAP_SHADOW                       =0x01113;
  static public final int ICAP_FRAMES                       =0x01114;
  static public final int ICAP_XNATIVERESOLUTION            =0x01116;
  static public final int ICAP_YNATIVERESOLUTION            =0x01117;
  static public final int ICAP_XRESOLUTION                  =0x01118;
  static public final int ICAP_YRESOLUTION                  =0x01119;
  static public final int ICAP_MAXFRAMES                    =0x0111a;
  static public final int ICAP_TILES                        =0x0111b;
  static public final int ICAP_BITORDER                     =0x0111c;
  static public final int ICAP_CCITTKFACTOR                 =0x0111d;
  static public final int ICAP_LIGHTPATH                    =0x0111e;
  static public final int ICAP_PIXELFLAVOR                  =0x0111f;
  static public final int ICAP_PLANARCHUNKY                 =0x01120;
  static public final int ICAP_ROTATION                     =0x01121;
  static public final int ICAP_SUPPORTEDSIZES               =0x01122;
  static public final int ICAP_THRESHOLD                    =0x01123;
  static public final int ICAP_XSCALING                     =0x01124;
  static public final int ICAP_YSCALING                     =0x01125;
  static public final int ICAP_BITORDERCODES                =0x01126;
  static public final int ICAP_PIXELFLAVORCODES             =0x01127;
  static public final int ICAP_JPEGPIXELTYPE                =0x01128;
  static public final int ICAP_TIMEFILL                     =0x0112a;
  static public final int ICAP_BITDEPTH                     =0x0112b;
  static public final int ICAP_BITDEPTHREDUCTION            =0x0112c; // Added 1.5
  static public final int ICAP_UNDEFINEDIMAGESIZE           =0x0112d;  // Added 1.6
  static public final int ICAP_IMAGEDATASET                 =0x0112e;  // Added 1.7
  static public final int ICAP_EXTIMAGEINFO                 =0x0112f;  // Added 1.7
  static public final int ICAP_MINIMUMHEIGHT                =0x01130;  // Added 1.7
  static public final int ICAP_MINIMUMWIDTH                 =0x01131;  // Added 1.7
  static public final int ICAP_AUTODISCARDBLANKPAGES        =0x01134;  // Added 1.8
  static public final int ICAP_FLIPROTATION                 =0x01136;  // Added 1.8
  static public final int ICAP_BARCODEDETECTIONENABLED      =0x01137;  // Added 1.8
  static public final int ICAP_SUPPORTEDBARCODETYPES        =0x01138;  // Added 1.8
  static public final int ICAP_BARCODEMAXSEARCHPRIORITIES   =0x01139;  // Added 1.8
  static public final int ICAP_BARCODESEARCHPRIORITIES      =0x0113a;  // Added 1.8
  static public final int ICAP_BARCODESEARCHMODE            =0x0113b;  // Added 1.8
  static public final int ICAP_BARCODEMAXRETRIES            =0x0113c;  // Added 1.8
  static public final int ICAP_BARCODETIMEOUT               =0x0113d;  // Added 1.8
  static public final int ICAP_ZOOMFACTOR                   =0x0113e;  // Added 1.8
  static public final int ICAP_PATCHCODEDETECTIONENABLED    =0x0113f;  // Added 1.8
  static public final int ICAP_SUPPORTEDPATCHCODETYPES      =0x01140;  // Added 1.8
  static public final int ICAP_PATCHCODEMAXSEARCHPRIORITIES =0x01141;  // Added 1.8
  static public final int ICAP_PATCHCODESEARCHPRIORITIES    =0x01142;  // Added 1.8
  static public final int ICAP_PATCHCODESEARCHMODE          =0x01143;  // Added 1.8
  static public final int ICAP_PATCHCODEMAXRETRIES          =0x01144;  // Added 1.8
  static public final int ICAP_PATCHCODETIMEOUT             =0x01145;  // Added 1.8
  static public final int ICAP_FLASHUSED2                   =0x01146;  // Added 1.8
  static public final int ICAP_IMAGEFILTER                  =0x01147;  // Added 1.8
  static public final int ICAP_NOISEFILTER                  =0x01148;  // Added 1.8
  static public final int ICAP_OVERSCAN                     =0x01149;  // Added 1.8
  static public final int ICAP_AUTOMATICBORDERDETECTION     =0x01150;  // Added 1.8
  static public final int ICAP_AUTOMATICDESKEW              =0x01151;  // Added 1.8
  static public final int ICAP_AUTOMATICROTATE              =0x01152;  // Added 1.8
  static public final int ICAP_JPEGQUALITY             		=0x01153;  // Added 1.9
  static public final int ICAP_FEEDERTYPE             		=0x01154;  // Added 1.91
  static public final int ICAP_ICCPROFILE             		=0x01155;  // Added 1.91
  static public final int ICAP_AUTOSIZE             		=0x01156;  // Added 2.0

// Capability Constants

// ICAP_BITORDER values (BO_ means Bit Order)
  static public final int TWBO_LSBFIRST    =0;
  static public final int TWBO_MSBFIRST    =1;

// ICAP_COMPRESSION values (CP_ means ComPression )
  static public final int TWCP_NONE        =0;
  static public final int TWCP_PACKBITS    =1;
  static public final int TWCP_GROUP31D    =2;  // Follows CCITT spec (no End Of Line)
  static public final int TWCP_GROUP31DEOL =3;  // Follows CCITT spec (has End Of Line)
  static public final int TWCP_GROUP32D    =4;  // Follows CCITT spec (use cap for K Factor)
  static public final int TWCP_GROUP4      =5;  // Follows CCITT spec
  static public final int TWCP_JPEG        =6;  // Use capability for more info
  static public final int TWCP_LZW         =7;
  static public final int TWCP_JBIG        =8;  // For Bitonal images  -- Added 1.7 KHL
  static public final int TWCP_PNG         =9;
  static public final int TWCP_RLE4        =10;
  static public final int TWCP_RLE8        =11;
  static public final int TWCP_BITFIELDS   =12;

  static final String[] CompressionStrings={
    "None",
    "Packbits",
    "Fax Modified Huffman 1-dim (no EOL)",
    "Fax Modified Huffman 1-dim",
    "Fax Modified READ 2-dim",
    "Fax Modified Modified READ 2-dim",
    "JPEG",
    "LZW",
    "JBIG",
    "PNG",
    "RLE4",                     // BMP
    "RLE8",                     // BMP
    "Bit Fields"                // BMP
  };

// ICAP_IMAGEFILEFORMAT values (FF_means File Format)
  static public final int TWFF_TIFF        =0;    // Tagged Image File Format
  static public final int TWFF_PICT        =1;    // Macintosh PICT
  static public final int TWFF_BMP         =2;    // Windows Bitmap
  static public final int TWFF_XBM         =3;    // X-Windows Bitmap
  static public final int TWFF_JFIF        =4;    // JPEG File Interchange Format
  static public final int TWFF_FPX         =5;    // Flash Pix
  static public final int TWFF_TIFFMULTI   =6;    // Multi-page tiff file
  static public final int TWFF_PNG         =7;
  static public final int TWFF_SPIFF       =8;
  static public final int TWFF_EXIF        =9;
  static public final int TWFF_PDF         =10;

  static final String[] ImageFileFormatStrings={
    "TIFF","PICT","BMP","XBM","JFIF","FPX","TIFFMULTI","PNG","SPIFF","EXIF","PDF"
  };

  static final String[] ImageFileFormatExts={
    ".tiff",".pict",".bmp",".xbm",".jpeg",".fpx",".tiff",".png",".spiff",".exif",".pdf"
  };

// ICAP_PIXELFLAVOR values (PF_ means Pixel Flavor)
  static public final int TWPF_CHOCOLATE   =0;  // zero pixel represents darkest shade
  static public final int TWPF_VANILLA     =1;  // zero pixel represents lightest shade

// ICAP_PIXELTYPE values (PT_ means Pixel Type)
  static public final int TWPT_BW          =0; // Black and White
  static public final int TWPT_GRAY        =1;
  static public final int TWPT_RGB         =2;
  static public final int TWPT_PALETTE     =3;
  static public final int TWPT_CMY         =4;
  static public final int TWPT_CMYK        =5;
  static public final int TWPT_YUV         =6;
  static public final int TWPT_YUVK        =7;
  static public final int TWPT_CIEXYZ      =8;

// ICAP_XFERMECH values (SX_ means Setup XFer)
  static public final int TWSX_NATIVE      =0;
  static public final int TWSX_FILE        =1;
  static public final int TWSX_MEMORY      =2;
  static public final int TWSX_FILE2       =3;

  static final String[] XferMechStrings={
    "native","file","memory","file2"
  };

// ICAP_UNITS values (UN_ means UNits)
  static public final int TWUN_INCHES      =0;
  static public final int TWUN_CENTIMETERS =1;
  static public final int TWUN_PICAS       =2;
  static public final int TWUN_POINTS      =3;
  static public final int TWUN_TWIPS       =4;
  static public final int TWUN_PIXELS      =5;

/*
  DCItemSize[]={
  sizeof(TW_INT8),sizeof(TW_INT16),sizeof(TW_INT32),
  sizeof(TW_UINT8),sizeof(TW_UINT16),sizeof(TW_UINT32),
  sizeof(TW_BOOL),sizeof(TW_FIX32),sizeof(TW_FRAME),
  sizeof(TW_STR32),sizeof(TW_STR64),sizeof(TW_STR128),sizeof(TW_STR255),
  sizeof(TW_STR1024),sizeof(TW_UNI512),
  };
*/
  static final int[] typeSizes={
  1,2,4,
  1,2,4,
  2,4,16,
  34,66,130,256,
  1026,1024
  };

  static final String[] info={
  "Success",
  "Failure due to unknown causes",
  "Not enough memory to perform operation",
  "No Data Source",
  "DS is connected to max possible applications",
  "DS or DSM reported internal error",
  "Unknown capability",
  "",
  "",
  "Unrecognized MSG DG DAT combination",
  "Data parameter out of range",
  "DG DAT MSG out of expected sequence",
  "Unknown destination Application/Source in DSM_Entry",
  "Capability not supported by source",
  "Operation not supported by capability",
  "Capability has dependancy on other capability",
  "File System operation is denied (file is protected)",
  "Operation failed because file already exists.",
  "File not found",
  "Operation failed because directory is not empty",
  "The feeder is jammed",
  "The feeder detected multiple pages",
  "Error writing the file (i.e. disk full conditions)",
  "The device went offline prior to or during this operation"
  };

// bit patterns: for query the operation that are supported by the data source on a capability
// Application gets these through DG_CONTROL/DAT_CAPABILITY/MSG_QUERYSUPPORT

  static public final int TWQC_GET           =0x0001;
  static public final int TWQC_SET           =0x0002;
  static public final int TWQC_GETDEFAULT    =0x0004;
  static public final int TWQC_GETCURRENT    =0x0008;
  static public final int TWQC_RESET         =0x0010;

}