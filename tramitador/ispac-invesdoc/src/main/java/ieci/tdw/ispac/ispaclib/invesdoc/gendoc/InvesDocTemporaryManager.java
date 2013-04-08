package ieci.tdw.ispac.ispaclib.invesdoc.gendoc;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.FileTemporary;

public class InvesDocTemporaryManager extends FileTemporary {
  
  private static InvesDocTemporaryManager mInstance = null;
  
  public static synchronized InvesDocTemporaryManager getInstance()
  throws ISPACException {
  	
 	if ( mInstance == null)
  	{ 
 	    String sFileTemporaryPath;
 	    
 	    IDOCConfiguration parameters = IDOCConfiguration.getInstance();
  	    sFileTemporaryPath = parameters.getTemporaryPath();                     
 	    if (sFileTemporaryPath.endsWith("/"))
 	    {
 	      	sFileTemporaryPath = sFileTemporaryPath.substring(0,sFileTemporaryPath.length() - 1);
 	    }
 		mInstance = new InvesDocTemporaryManager(sFileTemporaryPath);
 	}

  	return mInstance;
  }

  protected InvesDocTemporaryManager( String sFileTemporaryPath) 
  throws ISPACException {
    super( sFileTemporaryPath);
  }
}
