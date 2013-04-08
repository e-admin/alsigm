
package ieci.tecdoc.sgm.autenticacion.util.signature;

import java.io.InputStream;

/**
 * @author IECI S.A.
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Firma 
{  
   /**
    * 
    * @param data
    * @param certificatePath
    * @param password
    * @return
    */	
   public String sign(InputStream data, String certificatePath, String password);

   /**
    * 
    * @param data
    * @param signature
    * @return
    */
   public ValidacionFirmasInfo valitateSignature(InputStream data, String signature);
}