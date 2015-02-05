package ieci.tecdoc.sgm.autenticacion.util.signature;

import java.io.InputStream;

public interface Documento 
{
   public boolean validateDocument(InputStream data, String validationInfo);
}