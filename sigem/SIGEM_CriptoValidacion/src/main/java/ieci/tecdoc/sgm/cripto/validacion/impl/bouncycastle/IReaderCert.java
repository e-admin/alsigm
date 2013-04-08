package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle;


import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;

import java.security.cert.X509Certificate;

public interface IReaderCert {
	/**
	 * 
	 * @param cert
	 * @return Cierto si es el certificado que puede tratar
	 */
	public boolean isTypeOf(X509Certificate cert);
	/**
	 * 
	 * @param cert
	 * @return La informacion del certificado
	 */
	public InfoCertificado getInfo(X509Certificate cert);
	
}
