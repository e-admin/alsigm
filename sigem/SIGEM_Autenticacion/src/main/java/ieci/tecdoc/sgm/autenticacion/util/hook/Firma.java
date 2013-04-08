package ieci.tecdoc.sgm.autenticacion.util.hook;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public interface Firma 
{
   public ByteArrayOutputStream sign(InputStream data, CertificadoFirmaX509Info certificate) throws Exception;
}