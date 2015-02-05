// UnsupportedCapabilityException.java

// Copyright (c) 2006-2009, Jeff Friesen (jeff@javajeff.mb.ca).

package es.ieci.tecdoc.fwktd.applets.scan.jtwain;

import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;

/**
 *  This class defines the exception that is thrown from various places in the
 *  <code>jtwain.dll</code> code when an attempt is made to either get the
 *  value(s) or set the current value of an unsupported capability. The message
 *  is in the form ICAP_<i>x</i>, where <i>x</i> is the name of a capability
 *  (ICAP_PIXELTYPE, for example).
 *
 *  @author Jeff Friesen
 */

public class UnsupportedCapabilityException extends ScannerIOException{
	private static final long serialVersionUID = 1L;

/**
    *  Construct a <code>UnsupportedCapabilityException</code> object with the
    *  specified message.
    *
    * @param message a message that identifies the unsupported capability
    */

   public UnsupportedCapabilityException (String message)
   {
      super (message);
   }
}
