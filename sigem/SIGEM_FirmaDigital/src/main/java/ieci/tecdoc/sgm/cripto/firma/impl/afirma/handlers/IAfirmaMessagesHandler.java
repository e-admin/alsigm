package ieci.tecdoc.sgm.cripto.firma.impl.afirma.handlers;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.SignManagerAFirmaImpl;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento.FirmarDocumento;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.registrarFirma.FirmarUsuario;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.VerificarFirma;

/**
 * Interfaz para los gestores de mensajes contra los servicios de AFirma.
 * 
 */
public interface IAfirmaMessagesHandler {

	/**
	 * Compone el mensaje de petición para almacenar un documento en AFirma.
	 * 
	 * @param af
	 *            Implementación del servicio de firma digital contra AFirma.
	 * @param contentBase64
	 *            Contenido del documento en base64.
	 * @return Mensaje de petición.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public String createRequestAlmacenarDocumento(SignManagerAFirmaImpl af,
			String contentBase64) throws Exception;

	/**
	 * Comprueba el mensaje de respuesta de almacenar un documento en AFirma.
	 * 
	 * @param response
	 *            Respuesta del servicio de AFirma.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void checkResponseAlmacenarDocumento(String response)
			throws Exception;

	/**
	 * Compone el mensaje de petición para firmar un documento en AFirma.
	 * 
	 * @param af
	 *            Implementación del servicio de firma digital contra AFirma.
	 * @param message
	 *            Mensaje de respuesta de almacenar un documento
	 * @return Mensaje de petición.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public String createRequestFirmar(SignManagerAFirmaImpl af, String message)
			throws  Exception;

	/**
	 * Comprueba el mensaje de respuesta de firmar un documento en AFirma.
	 * 
	 * @param fd
	 *            Información del documento.
	 * @param response
	 *            Respuesta del servicio de AFirma.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void checkResponseFirmar(FirmarDocumento fd, String response)
			throws Exception;

	/**
	 * Compone el mensaje de petición para registrar una firma en AFirma.
	 * 
	 * @param af
	 *            Implementación del servicio de firma digital contra AFirma.
	 * @param firmaUsuario
	 *            Firma del usuario en base64.
	 * @param certFirmante
	 *            Certificado del firmate en base64.
	 * @param hash
	 *            Hash del documento firmato.
	 * @return Mensaje de petición.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public String createRequestFirmarUsuario(SignManagerAFirmaImpl af,
			String firmaUsuario, String certFirmante, String hash) throws Exception;

	/**
	 * Comprueba el mensaje de respuesta de registrar la firma de un documento en AFirma.
	 * 
	 * @param fu
	 *            Información del registro de la firma.
	 * @param response
	 *            Respuesta del servicio de AFirma.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void checkResponseFirmaUsuario(FirmarUsuario fu, String response)
			throws Exception;

	/**
	 * Compone el mensaje de petición para verificar una firma en AFirma.
	 * 
	 * @param af
	 *            Implementación del servicio de firma digital contra AFirma.
	 * @param firma
	 *            Firma en base64
	 * @return Mensaje de petición.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public String createRequestVerificar(SignManagerAFirmaImpl af, String firma) throws Exception;

	/**
	 * Comprueba el mensaje de respuesta para verificar la firma de un documento en AFirma.
	 * 
	 * @param vf
	 *            Información de la firma.
	 * @param response
	 *            Respuesta del servicio de AFirma.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void checkResponseVerificar(VerificarFirma vf, String response)
			throws Exception;

}