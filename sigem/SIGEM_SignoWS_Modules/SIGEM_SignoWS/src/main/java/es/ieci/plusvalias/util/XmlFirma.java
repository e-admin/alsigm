package es.ieci.plusvalias.util;

import java.security.PublicKey;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.w3c.dom.Element;

/**
 * @author angel_castro@ieci.es - 18/11/2010
 */
public class XmlFirma {
	public static Logger logger = Logger.getLogger(CalculoPlusvaliaHelper.class);

	public boolean comprobar(Element nodo, String baseURI) throws Exception{
		
		org.apache.xml.security.Init.init();

		boolean valido = false;
		XMLSignature signature  = new XMLSignature(nodo, baseURI);

		KeyInfo keyInfo = signature.getKeyInfo();
		if (keyInfo != null) {
			X509Certificate cert = keyInfo.getX509Certificate();
			if (cert != null) {
				// Validamos la firma usando un certificado X509
				if (signature.checkSignatureValue(cert)){
					logger.debug("Válido según el certificado");
					valido = true;
				} else {
					logger.debug("Inválido según el certificado");
				}
			} else {
				// No encontramos un Certificado intentamos validar por la cláve pública
				PublicKey pk = keyInfo.getPublicKey();
				if (pk != null) {
					// Validamos usando la clave pública
					if (signature.checkSignatureValue(pk)){
						logger.debug("Válido según la clave pública");
						valido = true;
					} else {
						logger.debug("Inválido según la clave pública");	
					}
				} else {
					logger.debug("No podemos validar, tampoco hay clave pública");
				}
			}
		} else {
			logger.debug("No ha sido posible encontrar el KeyInfo");
		}
		return valido;
	}
}

    