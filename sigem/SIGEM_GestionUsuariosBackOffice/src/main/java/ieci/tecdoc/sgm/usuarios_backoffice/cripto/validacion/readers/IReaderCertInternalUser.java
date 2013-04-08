package ieci.tecdoc.sgm.usuarios_backoffice.cripto.validacion.readers;

import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle.IReaderCert;

import java.security.cert.X509Certificate;

public interface IReaderCertInternalUser extends IReaderCert {

	/**
	 * Devuelve el identificador del certificado.
	 * 
	 * @param certificate
	 *            Certificado X509
	 * @param infoCertificado
	 *            Información del certificado
	 * @return Identificador del certificado
	 */
	public String getIdCertInternalUser(X509Certificate certificate, InfoCertificado infoCertificado);
}
