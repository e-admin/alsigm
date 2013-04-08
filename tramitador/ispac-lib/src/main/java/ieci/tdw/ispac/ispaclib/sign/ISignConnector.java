package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;

import java.util.Locale;
import java.util.Map;



/**
 * Interfaz para los conectores de firma digital.
 * 
 */
public interface ISignConnector {

	/**
	 * Obtiene el código HTML para incluir en la página.
	 * @param locale Información del idioma del cliente.
	 * @param baseURL URL base.
	 * @param hashCode Código HASH del documento a firmar.
	 * @return Código HTML..  
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getHTMLCode(Locale locale, String baseURL, String hashCode) 
		throws ISPACException;
	
	/**
	 * Obtiene el código HTML para incluir en la página.
	 * @param locale Información del idioma del cliente.
	 * @param baseURL URL base.
	 * @param hashCodes Códigos HASH de los documentos a firmar.
	 * @return Código HTML.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getHTMLCode(Locale locale, String baseURL, String [] hashCodes) 
		throws ISPACException;
	
	
	/**
	 * Realiza una validación de una firma o un hash.
	 * 
	 * @param signatureValue Valor de la firma
	 * @param signedContentB64 Contenido de la firma en base 64
	 * @throws InvalidSignatureValidationException
	 *             Si la firma no es válida.
	 * @return Detalles de la validación.
	 * 
	 */
	public Map verify(String signatureValue, String signedContentB64) throws InvalidSignatureValidationException;
	
	/**
	 * Realiza la firma
	 * @param changeState Indica si hay que cambiar el estado de la firma del documento
	 * @throws ISPACException
	 */
	public void sign(boolean changeState) throws ISPACException;
	
	/**
	 * Inicializa el conector con el documento a firmar y el contexto del cliente
	 * @param signDocument
	 * @param clientContext
	 */
	public void initializate(SignDocument signDocument, IClientContext clientContext);
	
	/**
	 * Obtiene la información del certificado
	 * @param x509CertString
	 * @return
	 * @throws ISPACException
	 */
	public String getInfoCert(String x509CertString)throws ISPACException;

	
	/**
	 * @param x509CertString Certificado a validar
	 * @return Objeto con informacion de la validez del certificado
	 * @throws ISPACException
	 */
	public ResultadoValidacionCertificado validateCertificate(String x509CertString)throws ISPACException;
}
