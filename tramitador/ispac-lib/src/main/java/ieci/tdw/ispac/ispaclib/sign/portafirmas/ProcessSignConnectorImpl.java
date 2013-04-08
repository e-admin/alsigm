package ieci.tdw.ispac.ispaclib.sign.portafirmas;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.expedients.Document;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.sign.SignCircuitMgr;
import ieci.tdw.ispac.ispaclib.sign.SignDetailEntry;
import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.ProcessSignProperties;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.Signer;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ProcessSignConnectorImpl implements ProcessSignConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(ProcessSignConnectorImpl.class);



	/**
	 * Constructor.
	 */
	public ProcessSignConnectorImpl() {
		super();
	}

	/**
	 * En conector por defecto no maneja estados distintos a los que se muestran ya en en
	 * la aplicacion (pendiente de firma, firmado, sin firma...)
	 *
	 * @throws ISPACException
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getState(ieci.tdw.ispac.ispaclib.context.ClientContext,
	 *      java.lang.String)
	 */
	public String getState(ClientContext ctx, String processId)
			throws ISPACException {


		return "";

	}

	public byte[] getDocument(ClientContext ctx, String documentId) throws ISPACException {
		IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
		IItem itemDoc = entitiesAPI.getDocument(Integer.parseInt(documentId));
		String infoPageRde = itemDoc.getString("INFOPAG_RDE");
		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();
		Object connectorSession = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		connectorSession = genDocAPI.createConnectorSession();
		if (!genDocAPI.existsDocument(connectorSession, infoPageRde)) {
			logger
					.error("No se ha encontrado el documento físico con identificador: '"
							+ infoPageRde
							+ "' en el repositorio de documentos");
			throw new ISPACException("No se ha encontrado el documento físico con identificador: '"
					+ infoPageRde
					+ "' en el repositorio de documentos");
		}

		genDocAPI.getDocument(connectorSession, infoPageRde, baos);
		return baos.toByteArray();
	}



	/**
	 * Este método no tiene aplicación en la implementación por defecto ya que el portafirmas por defecto no maneja estados,
	 * son los mismo que la propia aplicación : Sin firma, firmado, pendiente de firma...
	 * {@inheritDoc}
	 * @throws ISPACException
	 * @throws InvalidSignatureValidationException
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getSigns(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String)
	 */
	public List <SignDetailEntry> getSigns(ClientContext ctx, String documentId) throws InvalidSignatureValidationException, ISPACException {

			/*IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
			IItemCollection documentos = entitiesAPI.getEntities(
					SpacEntities.SPAC_DT_DOCUMENTOS, ctx.getStateContext()
							.getNumexp(), " ID_PROCESO_FIRMA'=" + documentId+"'");
			List <SignDetailEntry> lista=new ArrayList<SignDetailEntry>();
			if(documentos.next()){
				IItem documento=documentos.value();
			return ctx.getAPI().getSignAPI().showSignInfo(documento.getInt("ID"));
			}
			else{
				return lista;
			}*/

		return new ArrayList<SignDetailEntry>();
	}

	/**
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getSignsContent(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String)
	 */
	public byte[] getSignsContent(ClientContext ctx, String documentId)
			throws InvalidSignatureValidationException, ISPACException {
		return new byte[0];
	}
	
	/**
	 * En la implementación por defecto el ID_PROCESO_FIRMA y el ID serán el mismo.
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#initSignProcess(ieci.tdw.ispac.ispaclib.context.ClientContext, java.lang.String, ieci.tdw.ispac.api.expedients.Document)
	 */
	public String initSignProcess(ClientContext ctx, String processTemplateId,
			Document document , ProcessSignProperties properties) throws ISPACException {

		SignCircuitMgr signCircuitMgr = new SignCircuitMgr(ctx);
		signCircuitMgr.initCircuit(Integer.parseInt(processTemplateId), Integer
				.parseInt(document.getId()));
		return document.getId();

	}
	/**
	 * Este método no aplica en el conector por defecto ya que se seguirá tirando de la estructura organizativa como siempre
	 * y se permitirá navegar a través de la misma a la hora de seleccionar u
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector#getSigners(ieci.tdw.ispac.ispaclib.context.ClientContext)
	 */
	public List<Signer> getSigners(ClientContext ctx, String query)
			throws ISPACException {
		return (new ArrayList <Signer>()) ;
	}

	public int getTypeObject() {
		return EventsDefines.EVENT_OBJ_SIGN_CIRCUIT;
	}

	private Signer getSigner(Responsible resp) {
		Signer signer = new Signer();
		signer.setName(resp.getName());
		signer.setIdentifier(resp.getUID());
		return signer;
	}

	public String getIdSystem() {
		return SIGNPROCESS_SYSTEM_DEFAULT;
	}

}
