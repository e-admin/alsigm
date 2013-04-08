package es.ieci.tecdoc.fwktd.applets.scan.jtwain;

import java.awt.Image;



/**
 *  This class is the entry-point into the JTwain library for Java programs.
 *  Its <code>init()</code> method is used to initialize the library by
 *  loading <code>jtwain.dll</code>. This method must be called before calling
 *  any other method.
 *
 *  <p>
 *  Follow these steps to acquire an image from a data source:
 *
 *  <p>
 *  <ol>
 *    <li>
 *      Open data source manager (see <code>openDSM()</code> method).
 *    </li>
 *
 *    <li>
 *      Open data source (see <code>openDS()</code> method).
 *    </li>
 *
 *    <li>
 *      Acquire image (see <code>acquire()</code> or
 *      <code>acquireToBMP()</code> method).
 *    </li>
 *
 *    <li>
 *      Close data source (see <code>closeDS()</code> method).
 *    </li>
 *
 *    <li>
 *      Close data source manager (see <code>closeDSM()</code> method).
 *    </li>
 *  </ol>
 *
 *  <p>
 *  IMPORTANT: The <code>acquire()</code> and <code>acquireToBMP()</code>
 *  methods must be called on the same thread that is used to open and close
 *  the data source manager and data source.
 *  
 *  @author Jeff Friesen
 */

public class JTwain {
   // Version information.

   /**
    *  Major version code.
    */

   public final static int VMAJOR = 1;

   /**
    *  Minor version code.
    */

   public final static int VMINOR = 3;

   // Return codes.

   public final static int TWRC_SUCCESS = 0;
   public final static int TWRC_FAILURE = 1;
   public final static int TWRC_CHECKSTATUS = 2;
   public final static int TWRC_CANCEL = 3;
   public final static int TWRC_DSEVENT = 4;
   public final static int TWRC_NOTDSEVENT = 5;
   public final static int TWRC_XFERDONE = 6;
   public final static int TWRC_ENDOFLIST = 7;
   public final static int TWRC_INFONOTSUPPORTED = 8;
   public final static int TWRC_DATANOTAVAILABLE = 9;

   // Condition codes. Check condition code if return code is TWRC_FAILURE.

   /**
    *  No failure.
    */

   public final static int TWCC_SUCCESS = 0;

   /**
    *  Failure due to unknown causes.
    */

   public final static int TWCC_BUMMER = 1;

   /**
    *  Low memory conditions.
    */

   public final static int TWCC_LOWMEMORY = 2;

   /**
    *  No data source.
    */

   public final static int TWCC_NODS = 3;

   /**
    *  Data source connected to maximum possible applications.
    */

   public final static int TWCC_MAXCONNECTIONS = 4;

   /**
    *  DS/DSM reported error. Application should not report error.
    */

   public final static int TWCC_OPERATIONERROR = 5;

   /**
    *  Unknown capability.
    */

   public final static int TWCC_BADCAP = 6;

   /**
    *  Unrecognized MSG DG DAT combination.
    */

   public final static int TWCC_BADPROTOCOL = 9;

   /**
    *  Data parameter out of range.
    */

   public final static int TWCC_BADVALUE = 10;

   /**
    *  DG DAT MSG out of expected sequence.
    */

   public final static int TWCC_SEQERROR = 11;

   /**
    *  Unknown destination Application/Source in DSM_Entry().
    */

   public final static int TWCC_BADDEST = 12;

   /**
    *  Capability not supported by data source.
    */

   public final static int TWCC_CAPUNSUPPORTED = 13;

   /**
    *  Operation not supported by capability.
    */

   public final static int TWCC_CAPBADOPERATION = 14;

   /**
    *  Capability has dependency on other capability.
    */

   public final static int TWCC_CAPSEQERROR = 15;

   /**
    *  File system operation is denied (file is protected).
    */

   public final static int TWCC_DENIED = 16;

   /**
    *  Operation failed because file already exists.
    */

   public final static int TWCC_FILEEXISTS = 17;

   /**
    *  File not found.
    */

   public final static int TWCC_FILENOTFOUND = 18;

   /**
    *  Operation failed because directory is not empty.
    */

   public final static int TWCC_NOTEMPTY = 19;

   /**
    *  The feeder is jammed.
    */

   public final static int TWCC_PAPERJAM = 20;

   /**
    *  The feeder detected multiple pages.
    */

   public final static int TWCC_PAPERDOUBLEFEED = 21;

   /**
    *  Error writing the file (disk full or similar conditions).
    */

   public final static int TWCC_FILEWRITEERROR = 22;

   /**
    *  The device went offline prior to or during this operation.
    */

   public final static int TWCC_CHECKDEVICEONLINE = 23;

   // ICAP_PIXELTYPE capability constants

   public final static int TWPT_BW = 0;
   public final static int TWPT_GRAY = 1;
   public final static int TWPT_RGB = 2;
   public final static int TWPT_PALETTE = 3;
   public final static int TWPT_CMY = 4;
   public final static int TWPT_CMYK = 5;
   public final static int TWPT_YUV = 6;
   public final static int TWPT_YUVK = 7;
   public final static int TWPT_CIEXYZ = 8;
   public final static int TWPT_SRGB = 9;
   public final static int TWPT_SRGB64 = 10;

   // ICAP_UNITS capability constants

   public final static int TWUN_INCHES = 0;
   public final static int TWUN_CENTIMETERS = 1;
   public final static int TWUN_PICAS = 2;
   public final static int TWUN_POINTS = 3;
   public final static int TWUN_TWIPS = 4;
   public final static int TWUN_PIXELS = 5;
   public final static int TWUN_MILLIMETERS = 6;

   /**
    *  Initialize JTwain. Initialization succeeds if System.loadLibrary() is
    *  able to find the jtwain.dll library file.
    *
    *  IMPORTANT: This method must be called before any other method. If this
    *  method returns false, do NOT call any other method.
    *
    *  @return true if JTwain successfully initialized, otherwise false
    */

   public static boolean init()
   {
      try
      {          
    	  //String a = System.getProperty("java.library.path");
    	  //System.setProperty("java.library.path", "C:\\jtwain-1.3\\bin\\");  
    	  //C:\ieici\java\jdk1.5.0_15\bin
    	  //System.loadLibrary ("jtwain");
    	  
    	  TwainNativeLoadStrategySingleton.getInstance().getNativeLoadStrategy().load( JTwain.class,"jtwain");
    	  //System.load("dll//jtwain.dll");
          return true;
      }
      catch (UnsatisfiedLinkError e)
      { 
          return false;
      }
   }

   /**
    *  Acquire an array of images from the currently open data source. The
    *  exact number of acquired images can be determined from the array's
    *  length.
    *
    *  @return <code>Image</code> array describing acquired image(s)
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native Image [] acquire (boolean ui)
      	throws JTwainException;
   
   public static native void acquireUI()
   		throws JTwainException;
   
   /**
    *  Acquire one or more images from the currently open data source. Store
    *  these images in sequentially numbered Windows <code>.bmp</code> files.
    *  These files are stored in the current directory.
    *
    *  @param filename base name of file(s) in which to store image(s) -- do
    *         not specify a <code>.bmp</code> file extension (this extension is
    *         provided) or include a path specification
    *
    *  @throws JTwainException if something goes wrong
    */

   public static void acquireToBMP (String filename)
      throws JTwainException
   {
      acquireToBMP (filename, true);
   }

   /**
    *  Acquire one or more images from the currently open data source. Store
    *  these images in sequentially numbered Windows <code>.bmp</code> files.
    *  These files are stored in either the current directory or a specified
    *  directory.
    *
    *  @param filespec path and base name of file(s) in which to store image(s)
    *         -- do not specify a <code>.bmp</code> file extension (this
    *         extension is provided), do not include a path specification if
    *         you pass <code>true</code> to <code>useCur</code>
    *
    *  @param useCur <code>true</code> if file(s) will be stored in the
    *         current directory; <code>false</code> if file(s) will be stored
    *         according to the path (which must exist) that's specified as part
    *         of <code>filespec</code>
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native void acquireToBMP (String filespec, boolean useCur)
      throws JTwainException;
   
   
   public static native int acquireToJPEG (String filespec, boolean useCur, String pathFile, boolean useUI)
   		throws JTwainException;

   public static native int acquireToFile (String filespec, boolean useCur, String pathFile, boolean useUI)
		throws JTwainException;
   
   public static native int acquireNativeToFile (String filespec, boolean useCur, String pathFile, boolean useUI)
			throws JTwainException;
   
   /**
    *  Close the currently open data source.
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native void closeDS ()
      throws JTwainException;

   /**
    *  Close the data source manager.
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native void closeDSM ()
      throws JTwainException;

   /**
    *  Return the condition code from the most recent operation.
    *
    *  @param dest zero if data source manager, nonzero if data source
    *
    *  @return most recent operation's condition code
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native int getCC (int dest)
      throws JTwainException;

   /**
    *  Return the name of the default data source.
    *
    *  @return default source name
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native String getDefaultDS ()
      throws JTwainException;

   /**
    *  Return the name of the first data source.
    *
    *  @return first data source's name
    *
    *  @throws JTwainException if something goes wrong (e.g., no data sources)
    */

   public static native String getFirstDS ()
      throws JTwainException;

   /**
    *  Return the name of the next data source.
    *
    *  @return next data source's name, or "" if no more data source names
    *
    *  @throws JTwainException if something goes wrong (e.g., low memory)
    */

   public static native String getNextDS ()
      throws JTwainException;

   /**
    *  Return the currently open data source's current physical height setting
    *  (ICAP_PHYSICALHEIGHT capability).
    *
    *  @return current physical height
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getPhysicalHeight ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current physical width setting
    *  (ICAP_PHYSICALWIDTH capability).
    *
    *  @return current physical width
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getPhysicalWidth ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current pixel type setting
    *  (ICAP_PIXELTYPE capability).
    *
    *  @return current pixel type (see the <code>TWPT_</code>* constants above)
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native int getPixelType ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's supported pixel type settings
    *  (ICAP_PIXELTYPE capability).
    *
    *  @return array of supported pixel types (see the <code>TWPT_</code>*
    *  constants above)
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native int [] getPixelTypeList ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the return code from the most recent operation.
    *
    *  @return most recent operation's return code
    */

   public static native int getRC ();

   /**
    *  Return the currently open data source's current units setting
    *  (ICAP_UNITS capability).
    *
    *  @return current units (see the <code>TWUN_</code>* constants above)
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native int getUnits ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's default units setting
    *  (ICAP_UNITS capability).
    *
    *  @return default units (see the <code>TWUN_</code>* constants above)
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native int getUnitsDefault ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's supported units settings
    *  (ICAP_UNITS capability).
    *
    *  @return array of supported units (see the <code>TWUN_</code>* constants
    *  above)
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native int [] getUnitsList ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    * Lista de tamaños de papel soportados
    * @return
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native int [] getSupportedSizes()
   	  throws JTwainException, UnsupportedCapabilityException;
   
   /**
    * Se asigna el tamaño del papel
    * @return
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native void setSupportedSizes(int value)
   	  throws JTwainException, UnsupportedCapabilityException;
   
   /**
    *  Return the currently open data source's current X native resolution
    *  setting (ICAP_XNATIVERESOLUTION capability). This appears to be the
    *  scanner optics dots-per-inch resolution.
    *
    *  @return current X native resolution
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getXNativeResolution ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's supported X native resolution
    *  settings (ICAP_XNATIVERESOLUTION capability).
    *
    *  @return array of supported X native resolutions
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float [] getXNativeResolutionList ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current X resolution setting
    *  (ICAP_XRESOLUTION capability).
    *
    *  @return current X resolution
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getXResolution ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's supported X resolution settings
    *  (ICAP_XRESOLUTION capability).
    *
    *  @return array of supported X resolutions
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float [] getXResolutionList ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current X scaling setting
    *  (ICAP_XSCALING capability).
    *
    *  @return current X scaling
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getXScaling ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's default X scaling setting
    *  (ICAP_XSCALING capability).
    *
    *  @return default X scaling
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getXScalingDefault ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current Y native resolution
    *  setting (ICAP_YNATIVERESOLUTION capability). This appears to be the
    *  scanner optics dots-per-inch resolution.
    *
    *  @return current Y native resolution
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getYNativeResolution ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's supported Y native resolution
    *  settings (ICAP_YNATIVERESOLUTION capability).
    *
    *  @return array of supported Y native resolutions
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float [] getYNativeResolutionList ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current Y resolution setting
    *  (ICAP_YRESOLUTION capability).
    *
    *  @return current Y resolution
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getYResolution ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's supported Y resolution settings
    *  (ICAP_YRESOLUTION capability).
    *
    *  @return array of supported Y resolutions
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float [] getYResolutionList ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's current Y scaling setting
    *  (ICAP_YSCALING capability).
    *
    *  @return current Y scaling
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getYScaling ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Return the currently open data source's default Y scaling setting
    *  (ICAP_YSCALING capability).
    *
    *  @return default Y scaling
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native float getYScalingDefault ()
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Open a specific data source. If you successfully open the data source,
    *  you must invoke <code>closeDS()</code> prior to program exit. Otherwise,
    *  the OS will probably throw a fatal exception.
    *
    *  @param srcName data source's name
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native void openDS (String srcName)
      throws JTwainException;

   /**
    *  Open the data source manager. If you successfully open the data source
    *  manager, you must invoke <code>closeDSM()</code> prior to program exit.
    *  Otherwise, the OS will probably throw a fatal exception.
    *
    *  @throws JTwainException if something goes wrong
    */

   public static native void openDSM () throws JTwainException;

   /**
    *  Set the currently open data source's current pixel type setting
    *  (ICAP_PIXELTYPE capability).
    *
    *  @param type one of the <code>TWPT_</code>* constants declared earlier
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native void setPixelType (int type)
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Set the currently open data source's current units setting (ICAP_UNITS
    *  capability).
    *
    *  @param units the <code>TWUN_</code>* constants declared earlier
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native void setUnits (int units)
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Set the currently open data source's current X resolution setting
    *  (ICAP_XRESOLUTION capability).
    *
    *  @param res the new current X resolution
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native void setXResolution (float res)    throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Set the currently open data source's current X scaling setting
    *  (ICAP_XSCALING capability).
    *
    *  @param scale the new current X scale
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native void setXScaling (float scale)
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Set the currently open data source's current Y resolution setting
    *  (ICAP_YRESOLUTION capability).
    *
    *  @param res the new current Y resolution
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native void setYResolution (float res)
      throws JTwainException, UnsupportedCapabilityException;

   /**
    *  Set the currently open data source's current Y scaling setting
    *  (ICAP_YSCALING capability).
    *
    *  @param scale the new current Y scale
    *
    *  @throws JTwainException if something goes wrong
    *  @throws UnsupportedCapabilityException if capability not supported
    */

   public static native void setYScaling (float scale)
      throws JTwainException, UnsupportedCapabilityException;
   
   
   /***
    * Peticcion creada por Pablo Zapico
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native void setAutoDiscardBlankPages(int value)
	   throws JTwainException, UnsupportedCapabilityException;
   
   /***
    * Peticcion creada por Pablo Zapico
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native void setDuplexEnabled(boolean value)
	   throws JTwainException, UnsupportedCapabilityException;

   public static native void setADFEnabled(boolean value)
   throws JTwainException, UnsupportedCapabilityException;
   
   /***
    * Peticcion creada por Pablo Zapico
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native int[] getDuplexList()
	   throws JTwainException, UnsupportedCapabilityException;
   /***
    * Peticcion creada por Pablo Zapico
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   
   public static native boolean getDuplexSupport()
	   throws JTwainException, UnsupportedCapabilityException;

   public static native boolean getADFSupport()
   throws JTwainException, UnsupportedCapabilityException;
   
   /***
    * Se asigna el contraste de la imagen escaneada valores entre -1000 y 1000
    * @param res
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native void setContrast(float res)   
   throws JTwainException, UnsupportedCapabilityException;
   
   /***
    * Se asigna el brillo de la imagen escaneada valores entre -1000 y 1000
    * @param res
    * @throws JTwainException
    * @throws UnsupportedCapabilityException
    */
   public static native void setBright(float res)   
   throws JTwainException, UnsupportedCapabilityException;
   
   public static native void setJPGQuality(int res)
   throws JTwainException, UnsupportedCapabilityException;
   
   public static native void setCompressJPEG()
   throws JTwainException, UnsupportedCapabilityException;

}
