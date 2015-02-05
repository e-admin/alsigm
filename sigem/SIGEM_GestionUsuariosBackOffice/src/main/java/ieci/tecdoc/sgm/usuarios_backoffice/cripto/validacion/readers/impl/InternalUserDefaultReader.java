package ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.impl;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers.IReaderCertInternalUser;

import java.security.cert.X509Certificate;

public class InternalUserDefaultReader implements IReaderCertInternalUser {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(InternalUserDefaultReader.class);

	public boolean isTypeOf(X509Certificate cert) {

		return true;
	}

	public InfoCertificado getInfo(X509Certificate cert) {

		return null;
	}

	public String getIdCertInternalUser(X509Certificate certificate, InfoCertificado info) {
		if (logger.isDebugEnabled()) {
			logger.debug("getIdCertInternalUser(X509Certificate, InfoCertificado) - start");
		}

		String id = null;
		if (info!=null){
			id = info.getSerialNumber();
		}else{
			id = Integer.toString(certificate.getSerialNumber().intValue());
		}
		if (logger.isDebugEnabled()){
			logger.debug("Numero de serie del certificado: ["+id+"]");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getIdCertInternalUser(X509Certificate, InfoCertificado) - end");
		}
		return id;
	}

}
