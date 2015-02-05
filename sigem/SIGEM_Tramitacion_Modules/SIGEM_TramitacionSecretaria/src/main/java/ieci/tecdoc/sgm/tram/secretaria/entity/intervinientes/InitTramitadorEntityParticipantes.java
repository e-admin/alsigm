package ieci.tecdoc.sgm.tram.secretaria.entity.intervinientes;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Crear un registro en la entidad de spac_dt_intervinientes en el expediente
 * actual con la información del usuario tramitador.
 *
 * Esta regla se ha de asociar al evento tras iniciar el expediente. Necesita la
 * variable de sistema donde se definen los tramitadores y su email El formato
 * del XML de la variable de sistema es el siguiente <?xml version='1.0'
 * encoding='ISO-8859-1'?> <tramitadores> <tramitador
 * uid='1-116'><email>tramitador@server.com</email></tramitador> <tramitador
 * uid='1-117'><email>tramitador2@server.com</email></tramitador>
 * </tramitadores>
 *
 * @author IECISA
 *
 */
public class InitTramitadorEntityParticipantes implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
	.getLogger(InitTramitadorEntityParticipantes.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			// Creamos un registro de la entidad spac_dt_intervinientes
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI()
			.getEntitiesAPI();
			ClientContext ctx = (ClientContext) rulectx.getClientContext();

			Responsible user = ctx.getUser();
			String userUid = user.getUID();
			if (logger.isDebugEnabled()) {
				logger
				.debug("InitTramitadorEntityParticipantes:execute->UID usuario conectado: "
						+ userUid);
			}
			String tramitadores_var = ConfigurationMgr.getVarGlobal(ctx,
					 SecretariaConstants.TRAMITADORES).trim();
			if (StringUtils.isBlank(tramitadores_var)) {
				if (logger.isDebugEnabled()) {
					logger
					.debug("La variable de sistema "
							+ SecretariaConstants.TRAMITADORES
							+ "no tiene ningún valor, por lo que no se dará de alta, de manera automática,  el participantes tramitador ");
				}
				return null;
			} else {
				XmlFacade xmlFacade = new XmlFacade(tramitadores_var);

				if (xmlFacade != null) {
					NodeIterator nodeIt = xmlFacade
					.getNodeIterator("/tramitadores/tramitador");
					for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
					.nextNode()) {

						// Obtenemos el uid
						String uid = (XmlFacade.get(node, "@uid"));
						if (StringUtils.equalsIgnoreCase(userUid, uid)) {
							// Obtenemos el email y damos de alta el registro en
							// spac_dt_participantes
							String email = XmlFacade.get(node,"./email");
							// si el uid del tramitador actualmente conectado(el
							// que inicia el expediente) se encuentra
							// en la lista de tramitadores creamos el registro
							IItem item = entitiesAPI.createEntity(
									"SPAC_DT_INTERVINIENTES", rulectx
									.getNumExp());
							item.set("ROL",  SecretariaConstants.ROL_TRAMITADOR);
							String nameUser=user.getRespName();
							item.set("NOMBRE", nameUser);
							item.set("EMAIL", email);
							if (logger.isDebugEnabled()) {
								logger
								.debug("InitTramitadorEntityParticipantes:execute->UID usuario conectado: "
										+ userUid
										+ " pertenece a la lista de tramitadores, nombre:"+nameUser
										+" email:"+email);
							}
							item.store(ctx);

						}
					}

				}

			}

		} catch (ISPACException e) {
			logger.error("Error en la regla " + getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}
}
