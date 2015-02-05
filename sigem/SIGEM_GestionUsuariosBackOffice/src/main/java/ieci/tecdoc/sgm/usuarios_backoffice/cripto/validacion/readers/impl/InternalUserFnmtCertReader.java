/**
 * 
 */
package ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.impl;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle.FnmtCertReader;
import ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.IReaderCertInternalUser;

import java.security.cert.X509Certificate;

/**
 * @author IECISA
 * 
 *         Reader para obtener el identificador del certificado de tipo FNMT
 * 
 */
public class InternalUserFnmtCertReader extends FnmtCertReader implements
		IReaderCertInternalUser {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(InternalUserFnmtCertReader.class);

	public String getIdCertInternalUser(X509Certificate certificate, InfoCertificado info) {
		
		String id = info.getSerialNumber();
		if (logger.isDebugEnabled()){
			logger.debug("Numero de serie del certificado: ["+id+"]");
		}
		return id;
	}

}
