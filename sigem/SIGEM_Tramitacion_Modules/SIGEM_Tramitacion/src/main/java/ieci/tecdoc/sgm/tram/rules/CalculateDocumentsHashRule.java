package ieci.tecdoc.sgm.tram.rules;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.util.hash.HashUtils;

/**
 * Aplica la función resumen sobre los documentos que componen el expediente y
 * almacena el resultado y la función resumen aplicada en la entidad de
 * documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CalculateDocumentsHashRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger
			.getLogger(CalculateDocumentsHashRule.class);

	/**
	 * Función resumen por defecto.
	 */
	public static final String DEFAULT_FUNCION_HASH = HashUtils.SHA1_ALGORITHM;

	/**
	 * Nombre de la variable global que contiene la función resumen a aplicar al
	 * contenido de los documentos.
	 */
	public static final String VARIABLE_GLOBAL_FUNCION_HASH_NAME = "FUNCION_HASH";

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#cancel(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#execute(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		Object connectorSession = null;

		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();

		try {

			IGenDocAPI genDocAPI = invesflowAPI.getGenDocAPI();
			connectorSession = genDocAPI.createConnectorSession();

			try {

				// Obtener los documentos del expediente
				IItemCollection itemcol = invesflowAPI.getEntitiesAPI()
						.getEntities(SpacEntities.SPAC_DT_DOCUMENTOS,
								rulectx.getNumExp());
				while (itemcol.next()) {

					IItem documento = itemcol.value();
					String docref = documento.getString("INFOPAG");
					if (StringUtils.isNotBlank(docref)) {

						// Obtener el contenido del documento
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						genDocAPI.getDocument(connectorSession, docref, baos);

						// Generación del Hash aplicando la función resumen al
						// contenido del documento
						// y conversión a Base64
						String hashBase64 = HashUtils.generateHashBase64(baos
								.toByteArray(), getFuncionHash());

						// Guardar en el documento
						// el resumen y la función resumen aplicada al contenido
						documento.set("HASH", hashBase64);
						documento.set("FUNCION_HASH", getFuncionHash());
						documento.store(ctx);
					}
				}

			} catch (Exception e) {
				logger.error(
						"Error en la regla CalculateDocumentsHashRule:execute",
						e);
				throw new ISPACRuleException(e);
			} finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
			}
		} catch (ISPACRuleException ire) {
			throw ire;
		} catch (ISPACException ie) {
			logger.error(
					"Error en la regla CalculateDocumentsHashRule:execute", ie);
			throw new ISPACRuleException(ie);
		}

		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#init(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		String funcionHashValue = null;
		IClientContext ctx = rulectx.getClientContext();

		try {

			funcionHashValue = ConfigurationMgr.getVarGlobal(ctx,
					VARIABLE_GLOBAL_FUNCION_HASH_NAME);

		} catch (Exception e) {
			logger.warn("Error en la regla CalculateDocumentsHashRule:init", e);
		}

		if (StringUtils.isBlank(funcionHashValue)) {
			funcionHashValue = DEFAULT_FUNCION_HASH;
		}

		setFuncionHash(funcionHashValue);

		return true;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#validate(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean validate(IRuleContext irulecontext)
			throws ISPACRuleException {
		return true;
	}

	public String getFuncionHash() {
		return funcionHash;
	}

	public void setFuncionHash(String funcionHash) {
		this.funcionHash = funcionHash;
	}

	// Función resumen a aplicar al contenido de los documentos
	protected String funcionHash;

}
