package uk.co.mmscomputing.device.sane;

public interface SaneConstants{

//----- SANE commands -----

  static public final int SANE_FALSE = 0;
  static public final int SANE_TRUE	 = 1;

  static public final int SANE_STATUS_GOOD          = 0;	// everything A-OK
  static public final int SANE_STATUS_UNSUPPORTED   = 1;	// operation is not supported
  static public final int SANE_STATUS_CANCELLED     = 2;	// operation was cancelled
  static public final int SANE_STATUS_DEVICE_BUSY   = 3;	// device is busy; try again later
  static public final int SANE_STATUS_INVAL         = 4;	// data is invalid (includes no dev at open)
  static public final int SANE_STATUS_EOF           = 5;	// no more data available (end-of-file)
  static public final int SANE_STATUS_JAMMED        = 6;	// document feeder jammed
  static public final int SANE_STATUS_NO_DOCS       = 7; 	// document feeder out of documents
  static public final int SANE_STATUS_COVER_OPEN    = 8;	// scanner cover is open
  static public final int SANE_STATUS_IO_ERROR      = 9;	// error during device I/O
  static public final int SANE_STATUS_NO_MEM        = 10;	// out of memory
  static public final int SANE_STATUS_ACCESS_DENIED = 11; // access to resource has been denied

  static public final int SANE_TYPE_BOOL   = 0;
  static public final int SANE_TYPE_INT    = 1;
  static public final int SANE_TYPE_FIXED  = 2;
  static public final int SANE_TYPE_STRING = 3;
  static public final int SANE_TYPE_BUTTON = 4;
  static public final int SANE_TYPE_GROUP  = 5;

  static public final String[] SANE_TYPE = {"BOOL","INT","FIXED","STRING","BUTTON","GROUP"};


  static public final int SANE_UNIT_NONE        = 0;  // the value is unit-less (e.g., # of scans)
  static public final int SANE_UNIT_PIXEL       = 1;	// value is number of pixels
  static public final int SANE_UNIT_BIT         = 2;	// value is number of bits
  static public final int SANE_UNIT_MM          = 3;	// value is millimeters
  static public final int SANE_UNIT_DPI         = 4;	// value is resolution in dots/inch
  static public final int SANE_UNIT_PERCENT     = 5;  // value is a percentage
  static public final int SANE_UNIT_MICROSECOND = 6;	// value is micro seconds

  static public final String[] SANE_UNIT = {"NONE","PIXEL","BIT","MM","DPI","PERCENT","MICROSECOND"};

  static public final int SANE_CAP_SOFT_SELECT      = (1 << 0);
  static public final int SANE_CAP_HARD_SELECT		  = (1 << 1);
  static public final int SANE_CAP_SOFT_DETECT		  = (1 << 2);
  static public final int SANE_CAP_EMULATED		      = (1 << 3);
  static public final int SANE_CAP_AUTOMATIC		    = (1 << 4);
  static public final int SANE_CAP_INACTIVE		      = (1 << 5);
  static public final int SANE_CAP_ADVANCED		      = (1 << 6);
  static public final int SANE_CAP_ALWAYS_SETTABLE	= (1 << 7);

  static public final String[] SANE_CAP = {"SOFT_SELECT","HARD_SELECT","SOFT_DETECT",
    "EMULATED","AUTOMATIC","INACTIVE","ADVANCED","ALWAYS_SETTABLE"
  };

//#define SANE_OPTION_IS_ACTIVE(cap)	(((cap) & SANE_CAP_INACTIVE) == 0)
//#define SANE_OPTION_IS_SETTABLE(cap)	(((cap) & SANE_CAP_SOFT_SELECT) != 0)

  static public final int SANE_INFO_INEXACT		      = (1 << 0);
  static public final int SANE_INFO_RELOAD_OPTIONS	= (1 << 1);
  static public final int SANE_INFO_RELOAD_PARAMS		= (1 << 2);

  static public final int SANE_FRAME_GRAY = 0;    /* band covering human visual range */
  static public final int SANE_FRAME_RGB  = 1;		/* pixel-interleaved red/green/blue bands */
  static public final int SANE_FRAME_RED 	= 2;  	/* red band only */
  static public final int SANE_FRAME_GREEN= 3;		/* green band only */
  static public final int SANE_FRAME_BLUE	= 4;  	/* blue band only */

  static public final int SANE_FRAME_TEXT = 10;
  static public final int SANE_FRAME_JPEG = 11;
  static public final int SANE_FRAME_G31D = 12;
  static public final int SANE_FRAME_G32D = 13;
  static public final int SANE_FRAME_G42D = 14;

  static public final String[] SANE_FRAME = {"GRAY","RGB","RED","GREEN","BLUE","?","?","?","?","?","TEXT","JPEG","G31D","G32D","G42D"};

  static public final int SANE_FIXED_SCALE_SHIFT = 16;

// mmsc state machine additions

  static public final int SANE_STATE_INITIALIZE  = 0;
  static public final int SANE_STATE_OPEN        = 1;
  static public final int SANE_STATE_READY       = 2;
  static public final int SANE_STATE_CANCELLED   = 3;
  static public final int SANE_STATE_CLOSED      = 4;
  static public final int SANE_STATE_EXIT        = 5;

  static public final String[] SANE_STATE = {"INITIALIZE","OPEN","READY","CANCELLED","CLOSED","EXIT"};

}