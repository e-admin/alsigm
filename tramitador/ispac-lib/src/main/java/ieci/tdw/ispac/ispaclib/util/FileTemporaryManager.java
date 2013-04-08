package ieci.tdw.ispac.ispaclib.util;

import ieci.tdw.ispac.api.errors.ISPACException;

public class FileTemporaryManager extends FileTemporary {
  
  private static FileTemporaryManager mInstance = null;
  
  public static synchronized FileTemporaryManager getInstance()
  throws ISPACException {
  	
 	if ( mInstance == null)
  	{ 
 	    String sFileTemporaryPath;
 	    
 	    ISPACConfiguration parameters = ISPACConfiguration.getInstance();
 	    sFileTemporaryPath = parameters.get( ISPACConfiguration.TEMPORARY_PATH);                      
 	    if (sFileTemporaryPath.endsWith("/"))
 	    {
 	      	sFileTemporaryPath = sFileTemporaryPath.substring(0,sFileTemporaryPath.length() - 1);
 	    }
 		mInstance = new FileTemporaryManager(sFileTemporaryPath);
 	}

  	return mInstance;
  }

  protected FileTemporaryManager( String sFileTemporaryPath) 
  throws ISPACException {
    super( sFileTemporaryPath);
  }
}
