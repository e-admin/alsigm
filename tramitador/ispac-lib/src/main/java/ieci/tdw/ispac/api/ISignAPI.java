package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.sign.ResultadoValidacionCertificado;
import ieci.tdw.ispac.ispaclib.sign.SignCircuitFilter;
import ieci.tdw.ispac.ispaclib.sign.SignDetailEntry;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.ProcessSignProperties;

import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;




public interface ISignAPI {


	/**Propiedades que se van a obtener del certificado*/

	public static final String APELLIDOS 			="apellidos";
	public static final String NOMBRE				="nombre";
	public static final String PRIMER_APELLIDO		="apellido1";
	public static final String SEGUNDO_APELLIDO		="apellido2";
	public static final String NIF					="nif";
	public static final String TIPO_CERTIFICADO		="tipoCertificado";
	public static final String FECHA_FIRMA			="fechaFirma";
	public static final String DN					="DN";
	public static final String INTEGRIDAD			="integridad";


	/**
	 * Estados de la firma
	 */
	public static final String ESTADO_SIN_FIRMA				="00";
	public static final String ESTADO_PDTE_FIRMA			="01";
	public static final String ESTADO_FIRMADO				="02";
	public static final String ESTADO_FIRMADO_CON_REPAROS	="03";
	public static final String ESTADO_RECHAZADO				="04";
	public static final String ESTADO_PDT_CIRCUITO			="05";


	/**
	 * Estados de la integridad de la firma
	 */
	public static final String INTEGRIDAD_OK					="sign.detail.integrity.sign.ok";
	public static final String INTEGRIDAD_ERROR					="sign.detail.integrity.sign.error";
	public static final String INTEGRIDAD_STRANGER				="sign.detail.integrity.sign.stranger";
	public static final String INTEGRIDAD_PORTAFIRMAS			="sign.detail.integrity.sign.portafirmas";
	public static final String INTEGRIDAD_NO_APLICA				="sign.detail.integrity.sign.no.aplica";




	/**
     * @deprecated : Tras la externalización del portafirmas se ha utilizar el método initCircuitPortafirmas que devuelve int
	 * Instancia un circuito de firma donde el <code>'id'<code>
	 * identificada la definici&oacute;n del circuito de firma,
	 * retornando el identificador del circuito instanciado.
	 * @param id identificador del circuito de firma
	 * @param documentId Identificador del documento a firmar por el circuito de firma
	 * @return identificador del circuito de firma instanciado
	 * @throws ISPACException
	 */
	int  initCircuit(int id, int documentId) throws ISPACException;

	/**
	 * Instancia un circuito de firma donde el <code>'id'<code>
	 * identificada la definici&oacute;n del circuito de firma,
	 * retornando el identificador del circuito instanciado.
	 * @param id identificador del circuito de firma
	 * @param documentId Identificador del documento a firmar por el circuito de firma
	 * @param properties Propiedades del proceso de firma
	 * @return identificador del circuito de firma instanciado
	 * @throws ISPACException
	 */
	String  initCircuitPortafirmas(int id, int documentId, ProcessSignProperties properties) throws ISPACException;

	/**
	 * Devuelve los pasos de un circuito de firma instanciado,
	 * retornando en una lista los pasos ordenados que componen el circuito de firma instanciado.
	 * @param instanceCircuitId Identificador del circuito de firma instanciado
	 * @return Lista con los pasos que componen el circuito de firma instanciado
	 * @throws ISPACException
	 */
	IItemCollection getCircuit(int instanceCircuitId) throws ISPACException;

/**
	 * Devuelve los pasos de cualquier circuito de firma instanciado
	 * en el cual el firmante debe ser el usuario indicado en el parámetro <code>'respId'</code>
	 * u otros usuarios a los que sustituya.
	 * @param respId Identificador de usuario
	 * @return Lista de pasos de circuitos de firma que est&aacute;n pendientes de
	 * firmar por el usuario <code>'respId'</code>
	 * @throws ISPACException
	 */
	IItemCollection getCircuitSetps(String respId) throws ISPACException;


	/**
	 * Devuelve los pasos de cualquier circuito de firma instanciado
	 * en el cual el el usuario identificado por <code>'respId'</code> haya firmado,
	 * filtrando la consulta por las firmas realizadas en el intervalo
	 * de fechas [init , end], pudiendo estar alguna o las dos vac&iacute;as,
	 * en cuyo caso no se aplicar&aacute; filtro por el extremo del intervalo
	 * donde no se pase valor.
	 * @param respId Identificador de usuario
	 * @param init Fecha inicio del intervalo
	 * @param end Fecha fin del intervalo
	 * @param state Estado del paso del circuito de firma
	 * @return Lista de pasos de circuitos de firma firmados por el usuario <code>'respId'</code>
	 * filtrados por el intervalo de fechas [<code>'init'</code> , <code>'end'</code>]
	 * @throws ISPACException
	 */
	IItemCollection getHistorics(String respId, Date init, Date end, int state) throws ISPACException;

	/**
	 * Devuelve todos los circuitos de firma definidos en el sistema.
	 * @return Lista de los circuitos de firma definidos en el sistema.
	 * @throws ISPACException
	 */
	IItemCollection getCircuits() throws ISPACException;


	/**
	 * Devuelve todos los circuitos de firma definidos en el sistema
	 * aplicando el filtro, donde el filtro puede contener completados
	 * ciertos campos que servir&aacute;n para acotar el resultado.
	 * @param filter Filtro a aplicar.
	 * @return Lista de circuitos de firma definidos en el sistema aplicando el filtro.
	 * @throws ISPACException
	 */
	IItemCollection getCircuits(SignCircuitFilter filter) throws ISPACException;

	/**
	 * Devuelve el n&uacute;mero de pasos de circuitos de firma instanciados pendientes
	 * de firmar por el usuario <code>'respId'</code> u otros usuarios a los que sustituya.
	 * @param respId Identificador de usuario
	 * @return n&uacute;mero de pasos de circuitos de firma
	 * @throws ISPACException
	 */
	int countCircuitsStepts(String respId) throws ISPACException;

	/**
	 * Completa el paso de un circuito de firma establecimiento la firma al documento asociado
	 * @param signDocument Documento de firma
	 * @param instancedStepId Identificador de un paso de un circuito de firma instanciado
	 * @return true si todo ha ido bien, false en caso contrario
	 * @throws ISPACException
	 */
	boolean signStep(SignDocument signDocument, int instancedStepId) throws ISPACException;

	/**
	 * Firma en bloque de pasos de circuito de firma
	 * @param stepIds Identificadores de los pasos de circuito de firma
	 * @param signs Firmas de los documentos asociados a los pasos
	 * @param certificado con el que se realizo la firma
	 * @return Lista de documentos firmados
	 * @throws ISPACException
	 */
	public List batchSignSteps(String[] stepIds, String[] signs, String certificado) throws ISPACException;

	/**
	 * Obtiene los datos de un paso de un circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param stepId Identificador del paso.
	 * @return IItem con los datos del paso.
	 * @throws ISPACException
	 */
	public IItem getCircuitStep(int circuitId, int stepId) throws ISPACException;

	/**
	 * Obtiene los datos de un paso de circuito de firma instanciado
	 * @param instancedStepId Identificador de un paso de un circuito de firma instanciado
	 * @return IItem con los datos del paso
	 * @throws ISPACException
	 */
	IItem getStepInstancedCircuit(int instancedStepId) throws ISPACException;

	/**
	 * Incluya la firma en el documento, almacenando el documento y la firma en el
	 * repositorio de documentos electr&oacute;nicos
	 * @param signDocument Documento de firma
	 * @param changeState Indica si se debe cambiar el estado del documento a firmado.
	 * @throws ISPACException
	 */
	void sign(SignDocument signDocument, boolean changeState) throws ISPACException;

	/**
	 * Calcula el Hash para el documento a firmar
	 * @param signDocument Documento de firma
	 * @return C&oacute;digo Hash calculado para el documento a firmar
	 * @throws ISPACException
	 */
	String generateHashCode(SignDocument signDocument)throws ISPACException;

	/**
	 * @param documentId Id del documento
	 * @param respId UID de un usuario
	 * @return true si el usuario identificado por '<code>UID</code>' es o ha sido
	 * responsable de firmar el documento identificado por '<code>documentId</code>',
	 * false en caso contrario
	 * @throws ISPACException
	 */
	boolean isResponsible(int documentId, String respId) throws ISPACException;

	/**
	 * Obtiene los pasos del circuito de firma en función del
	 * identificador del documento a firmar o firmado en dicho circuito de firma.
	 *
	 * @param documentId Identificador del documento
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getStepsByDocument(int documentId) throws ISPACException;

	/**
	 * Elimina los pasos del circuito de firma asociados al documento.
	 * @param documentId Identificador del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteStepsByDocument(int documentId) throws ISPACException;

	/**
	 * Elimina los pasos del circuito de firma asociados a los documentos de una fase.
	 * @param stageId Identificador de la fase activa.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteStepsByStage(int stageId) throws ISPACException;

	/**
	 * Muestra los detalles de la firma de un documento asociado al expediente.
	 * La información mostrada es fecha de firma, nombre del firmante, y si el
	 * documento está asociado a un circuito de firma, muestra el nombre de los
	 * usuarios que están pendientes de firmar.
	 *
	 * @param documentId
	 *            Identificador del documento dado por ispac
	 *            (SPAC_DT_DOCUMENTOS)
	 *
	 * @throws ISPACException
	 * @throws InvalidSignatureValidationException
	 */
	public List showSignInfo(int documentId) throws ISPACException, InvalidSignatureValidationException;

	/**
	 *
	 * @param x09Cert Certificado
	 * @return El campo DN del certificado correspondiente al firmante
	 * @throws CertificateException
	 */
	public String getFirmanteFromCertificado (String x09Cert) throws CertificateException;

	/**
	 * Obtiene el número de pasos de un circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @return Número de pasos del circuito.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int countCircuitSteps(int circuitId) throws ISPACException;

	/**
	 * Añade un firmaten a un circuito de firma.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @throws ISPACException si ocurre algún error.
	 * @throws SignerAlreadyExistsException si el firmante ya está asociado al circuito de firmas.
	 */
	public void addSigner(int circuitId, String signerUID) throws ISPACException;

	/**
	 * Añade un firmaten a un circuito de firma.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @param signerName Nombre del firmante.
	 * @param signerType Tipo del firmante
	 * @throws ISPACException si ocurre algún error.
	 * @throws SignerAlreadyExistsException si el firmante ya está asociado al circuito de firmas.
	 */
	public void addSigner(int circuitId, String signerUID, String signerName, String signerType) throws ISPACException;

	/**
	 * Sustituye un firmante en el circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerId Identificador del paso del circuito de firmas.
	 * @param newSignerUID UID del nuevo firmante.
	 * @throws ISPACException si ocurre algún error.
	 * @throws SignerAlreadyExistsException si el nuevo firmante ya está asociado al circuito de firmas.
	 * @throws SameSignerException si el nuevo firmante es el mismo que el antiguo.
	 */
	public void substituteSigner(int circuitId, int signerId, String newSignerUID) throws ISPACException;

	/**
	 * Sustituye un firmante en el circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerId Identificador del paso del circuito de firmas.
	 * @param newSignerUID UID del nuevo firmante.
	 * @param signerName Nombre del firmante.
	 * @param signerType Tipo del firmante.
	 * @throws ISPACException si ocurre algún error.
	 * @throws SignerAlreadyExistsException si el nuevo firmante ya está asociado al circuito de firmas.
	 * @throws SameSignerException si el nuevo firmante es el mismo que el antiguo.
	 */
	public void substituteSigner(int circuitId, int signerId, String newSignerUID, String signerName, String signerType) throws ISPACException;

	/**
	 * Indica si el firmante ya está asociado al circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @return true si el firmante ya está asociado al circuito de firmas, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean existsSigner(int circuitId, String signerUID) throws ISPACException;

	/**
	 * Indica si el firmante ya está asociado al circuito de firmas.
	 * @param circuitId Identificador del circuito de firmas.
	 * @param signerUID UID del firmante.
	 * @param signerType Tipo del firmante.
	 * @return true si el firmante ya está asociado al circuito de firmas, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean existsSigner(int circuitId, String signerUID, String signerType) throws ISPACException;

	/**
	 * Devuelve el número de hojas del documento firmado (pdf)
	 * @param infopagRDE
	 * @return
	 * @throws ISPACException
	 */
	public int getNumHojasDocumentSigned (String infopagRDE) throws ISPACException;
	/**
	 * Muestra los detalles de la firma de un documento enviado al portafirmas
	 * La información mostrada es fecha de firma, nombre del firmante, y si el
	 * documento está asociado a un circuito de firma, muestra el nombre de los
	 * usuarios que están pendientes de firmar.
	 *
	 * @param documentId
	 *            Identificador del documento dado por ispac
	 *            (SPAC_DT_DOCUMENTOS)
	 *
	 * @throws ISPACException
	 */
	public List <SignDetailEntry>  getSignDetailDocumentInPortafirmas(int documentId) throws ISPACException;

	/**
	 * Devuelve el estado en el que se encuentra un documento enviado a firmar al portafirmas
	 * @param documentId
	 * @return
	 * @throws ISPACException
	 */
	public String getStateDocumentInPortafirmas (int documentId) throws ISPACException;


	public ResultadoValidacionCertificado validateCertificate(String X509Certificate) throws ISPACException;

}