// JTwainException.java

// Copyright (c) 2006-2009, Jeff Friesen (jeff@javajeff.mb.ca).

package es.ieci.tecdoc.fwktd.applets.scan.jtwain;

import es.ieci.tecdoc.fwktd.applets.scan.scanner.ScannerIOException;

/**
 *  This class defines the exception that is thrown from various places in the
 *  <code>jtwain.dll</code> code when some kind of failure occurs other than an
 *  unsupported capability.
 *
 *  @author Jeff Friesen
 */

public class JTwainException extends ScannerIOException{
	private static final long serialVersionUID = 1L;

/**
    *  Construct a <code>JTwainException</code> object with the specified
    *  message.
    *
    *  @param message a message that describes the <code>jtwain.dll</code> code
    *  failure
    */

   public JTwainException (String message){
      super (message);
   }
}
