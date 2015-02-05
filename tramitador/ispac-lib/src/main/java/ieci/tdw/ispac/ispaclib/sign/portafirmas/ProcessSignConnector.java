package ieci.tdw.ispac.ispaclib.sign.portafirmas;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.expedients.Document;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sign.SignDetailEntry;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.ProcessSignProperties;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.Signer;

import java.util.List;

/**
 * Conector de gestión de firmas. Portafirmas
 *
 */
public interface ProcessSignConnector {




    int TYPE_OBJECT=EventsDefines.EVENT_OBJ_SIGN_CIRCUIT_PORTAFIRMAS_NO_DEFAULT;
    public static String SIGNPROCESS_SYSTEM_DEFAULT = "0";
	public static String SIGNPROCESS_SYSTEM_MPT = "1";


	/**
	 * Devuelve la lista de firmantes que pueden formar parte de un circuito de firma
	 * @param ctx
	 * @return
	 * @throws ISPACException
	 * @throws Exception
	 */
	public List<Signer> getSigners(ClientContext ctx, String query) throws ISPACException;
	/**
	 * Envía un documento a firmar a un circuito de firma
	 * @param ctx
	 * @param processTemplateId: Identificador del circuito de firma
	 * @param document: Documento a firmar
	 * @param properties: Propiedades del proceso de firma
	 * @return
	 * @throws ISPACException
	 */
	public String initSignProcess(ClientContext ctx, String processTemplateId, Document document , ProcessSignProperties properties) throws ISPACException;

	/**
	 * Obtiene un documento firmado
	 * @param ctx
	 * @param documentId: Identificador del documento firmado
	 * @return
	 * @throws ISPACException
	 */
	public byte[] getDocument(ClientContext ctx , String documentId) throws ISPACException;

	/**
	 * Devuelve la lista de firmas de un documento tipificado con el objeto SignDetailEntry cuyos atributos son:
	 * el autor, si el documento esta firmado, la fecha de firma y la integridad del documento
	 * @param ctx
	 * @param documentId: Identificador del documento del que se quiere consultar sus firmas.
	 * @return
	 * @throws ISPACException
	 * @throws InvalidSignatureValidationException
	 */
	public List <SignDetailEntry> getSigns(ClientContext ctx, String documentId) throws InvalidSignatureValidationException, ISPACException;

	/**
	 * Este metodo permite obtener el fichero firmado que devuelve el portafirmas
	 * @param ctx
	 * @param documentId: Identificador del documento del que se quiere consultar sus firmas.
	 * @return
	 * @throws ISPACException
	 * @throws InvalidSignatureValidationException
	 */
	public byte[] getSignsContent(ClientContext ctx, String documentId) throws InvalidSignatureValidationException, ISPACException;
	
	/**
	 * Obtiene el  estado firma de un proceso de firma
	 * @param processId: Identificador del proceso de firma externo
	 * @return
	 * @throws ISPACException
	 */
	public String getState(ClientContext ctx, String processId) throws ISPACException;

	/**
	 * Devuelve el tipo de objeto para los circuitos de firma.
	 * En todas las implementaciones, salvo la de por defecto,
	 * se ha de devolver el atributo typeObject
	 * @return
	 */
	public int getTypeObject();

	/**
	 * Devuelve el identificador del sistema
	 * @return
	 */
	public String getIdSystem();
}
