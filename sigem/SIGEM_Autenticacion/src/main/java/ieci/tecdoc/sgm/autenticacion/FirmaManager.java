package ieci.tecdoc.sgm.autenticacion;

import ieci.tecdoc.sgm.autenticacion.exception.AutenticacionExcepcion;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadCodigosError;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadExcepcion;
import ieci.tecdoc.sgm.autenticacion.util.ConectorCertificado;
import ieci.tecdoc.sgm.autenticacion.util.ConectorFirma;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.autenticacion.util.signature.ValidacionFirmasInfo;
import ieci.tecdoc.sgm.autenticacion.vo.ReceiptVO;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.srv.firmar.Firmar;
import ieci.tecdoc.sgm.xml.Gestion;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Clase para la gestión de firmas digitales.
 * 
 * @author IECISA
 * 
 */
public class FirmaManager {

	private static final Logger logger = Logger.getLogger(FirmaManager.class);

	private static FileSystemXmlApplicationContext factory;
	private static String xmlResource = "ieci/tecdoc/sqm/autenticacion/resources/beans.xml";

	/**
	 * Firma unos datos utilizando para ello el certificado que se le pasa como
	 * parámetro.
	 * 
	 * @param data
	 *            Datos del documento a firmar.
	 * @param additionalInfo
	 *            Información adicional para la firma.
	 * @param certificate
	 *            Información del certificado a utilizar.
	 * @param hookId
	 *            Identificador del conector para la generación de la firma.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	public static ByteArrayOutputStream sign(String sessionId, InputStream data,
			String additionalInfo, CertificadoFirmaX509Info certificate, String hookId)
			throws Exception {
		ByteArrayOutputStream output = null;

		try {
			factory = new FileSystemXmlApplicationContext(xmlResource);
			Firmar firmar = (Firmar) factory.getBean("firmar");
			firmar.firmar(Goodies.getBytes(data), null, null);
		} catch (AutenticacionExcepcion exc) {
			logger.error("Error al firmar [sign][AutenticacionExcepcion]", exc.fillInStackTrace());
			throw exc;
		} catch (Exception e) {
			logger.error("Error al firmar [sign][Excepcion]", e.fillInStackTrace());
			throw new SeguridadExcepcion(SeguridadCodigosError.EC_SIGN);
		}

		return output;
	}

	/**
	 * Método para la firma del justificante de registro.
	 * 
	 * @param sessionId
	 *            Identificador de sesión.
	 * @param data
	 *            Datos a firmar.
	 * @param request
	 *            Solicitud de registro.
	 * @param additionalInfo
	 *            Información adicional para la firma.
	 * @param certificate
	 *            Certificado a utilizar en la firma.
	 * @param hookId
	 *            Identificador del conector que implementa la lógica de la
	 *            firma.
	 * @return ByteArrayOutputStrem Justificante de registro firmado.
	 * @throws Exception
	 *             En caso de producirse algún error.
	 */
	public static ReceiptVO signReceipt(String sessionId, InputStream data, String request,
			String additionalInfo, CertificadoFirmaX509Info certificate, String hookId)
			throws Exception {
		//ByteArrayOutputStream output = null;

		ReceiptVO receiptVO = null;
		try {
			String justificanteClass = Configuracion
					.obtenerPropiedad(Configuracion.KEY_JUSTIFICANTE_CLASS);
			receiptVO = ConectorFirma.signReceipt(sessionId, justificanteClass, data, request,
					additionalInfo, certificate);
		} catch (AutenticacionExcepcion exc) {
			logger.error("Error al firmar justificante [signReceipt][AutenticacionExcepcion]",
					exc.fillInStackTrace());
			throw exc;
		} catch (Exception e) {
			logger.error("Error al firmar justificante [signReceipt][Excepcion]",
					e.fillInStackTrace());
			throw new SeguridadExcepcion(SeguridadCodigosError.EC_SIGN);
		}

		return receiptVO;
	}

	/**
	 * Firma unos datos utilizando para ello el certificado que se le pasa como
	 * parámetro.
	 * 
	 * @param data
	 *            Datos del justificante a firmar.
	 * @param additionalInfo
	 *            Información adicional para la firma.
	 * @param hookId
	 *            Identificador del conector para la generación de la firma.
	 * @return La lista de certificados firmantes del documento.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	public static ValidacionFirmasInfo verifySign(String sessionId, InputStream data,
			String additionalInfo, String hookId) throws Exception {
		ValidacionFirmasInfo info = null;
		String res = null;
		try {
			factory = new FileSystemXmlApplicationContext(xmlResource);
			Firmar firmar = (Firmar) factory.getBean("verificarFirma");
			res = firmar.verificar(Goodies.getBytes(data), null, null);
			Gestion.xml(res);
		} catch (AutenticacionExcepcion exc) {
			logger.error("Error al verificar firmar [verifySign][AutenticacionExcepcion]",
					exc.fillInStackTrace());
			throw exc;
		} catch (Exception e) {
			logger.error("Error al verificar firmar [verifySign][Excepcion]", e.fillInStackTrace());
			throw new SeguridadExcepcion(SeguridadCodigosError.EC_VERIFY_SIGN);
		}

		return info;
	}

	/**
	 * Firma un justificante de registro utilizando para ello el certificado del
	 * servidor.
	 * 
	 * @param data
	 *            Datos del justificante a firmar.
	 * @return El justificante de registro firmado.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	public static ReceiptVO signReceipt(String sessionId, InputStream data, String request,
			String additionalInfo, String certificado) throws Exception {
		
		ReceiptVO receiptVO = null;
		CertificadoFirmaX509Info certificate;

		try {
			
			//certificate = cargarInfoCertificado(certificado);
			receiptVO = signReceipt(sessionId, data, request, additionalInfo, null, null);
		} catch (AutenticacionExcepcion exc) {
			logger.error("Error al firmar justificante [signReceipt][AutenticacionExcepcion]",
					exc.fillInStackTrace());
			throw exc;
		} catch (Exception e) {
			logger.error("Error al firmar justificante [signReceipt][Excepcion]",
					e.fillInStackTrace());
			throw new SeguridadExcepcion(SeguridadCodigosError.EC_SIGN);
		}

		return receiptVO;
	}

	/**
	 * Verifica la firma de un justificante de registro.
	 * 
	 * @param data
	 *            Datos del justificante a verificar.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	public static ValidacionFirmasInfo verifyReceiptSign(String sessionId, InputStream data,
			String additionalInfo) throws Exception {
		ValidacionFirmasInfo info = null;

		try {
			info = verifySign(sessionId, data, additionalInfo, null);
		} catch (AutenticacionExcepcion exc) {
			logger.error(
					"Error al verificar firmas justificante [verifyReceiptSign][AutenticacionExcepcion]",
					exc.fillInStackTrace());
			throw exc;
		} catch (Exception e) {
			logger.error("Error al verificar firmas justificante [verifyReceiptSign][Excepcion]",
					e.fillInStackTrace());
			throw new SeguridadExcepcion(SeguridadCodigosError.EC_VERIFY_SIGN);
		}

		return info;
	}

	/**
	 * Firma la solicitud de registro utilizando para ello el certificado del
	 * servidor.
	 * 
	 * @param data
	 *            Datos del justificante a firmar.
	 * @throws java.lang.Exception
	 *             Si se produce algún error.
	 */
	public static ByteArrayOutputStream signRequest(String sessionId, InputStream data,
			String additionalInfo) throws Exception {
		ByteArrayOutputStream output = null;

		output = sign(sessionId, data, additionalInfo, null, null);

		return output;
	}

	/**
	 * Método que obtiene el documento incluido en la firma.
	 * 
	 * @param hookId
	 *            Identificador del conector que implementa la lógica de
	 *            encriptación.
	 * @param data
	 *            Datos de la firma.
	 * @param additionalInfo
	 *            Información adicional necesaria para el proceso.
	 * @return ByteArrayOutputStream Con el documento.
	 * @throws Exception
	 *             En caso de producirse algún error.
	 */
	public static ByteArrayOutputStream getDocument(String hookId, InputStream data,
			String additionalInfo) throws Exception {
		ByteArrayOutputStream output = null;
		return output;
	}

	private static CertificadoFirmaX509Info cargarInfoCertificado(String certificado)
			throws AutenticacionExcepcion {
		CertificadoFirmaX509Info certificate = null;
		String certPath, certPwd, certInfo, certClass;

		certPath = certificado;
		certPwd = Configuracion.obtenerPropiedad(Configuracion.KEY_PASSWORD);
		certInfo = Configuracion.obtenerPropiedad(Configuracion.KEY_ALIAS);
		certClass = Configuracion.obtenerPropiedad(Configuracion.KEY_CERT_CLASS);

		logger.debug("getCertificate");

		try {
			certificate = ConectorCertificado.loadCertificateInfo(certClass, certPath, certPwd,
					certInfo);
		} catch (Exception exc) {
			logger.error("Error al cargar informacion [verifyReceiptSign][AutenticacionExcepcion]",
					exc.fillInStackTrace());
			throw new AutenticacionExcepcion(SeguridadCodigosError.EC_GET_CERTIFICATE);
		}

		return certificate;

	}
}