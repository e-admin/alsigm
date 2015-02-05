package ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad;

import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.resp.User;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Clase base para la ejecución de las acciones multientidad
 * para la Tramitación.
 *
 * @author IECISA
 *
 */
public abstract class TramitacionAccionEjecucionBase extends AccionEjecucionBase implements IProcessManager  {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(TramitacionAccionEjecucionBase.class);

	/**
	 * Constructor.
	 */
	public TramitacionAccionEjecucionBase() {
		// En la clase base se establece el currentDate
		super();
	}

	/**
	 * Establecer la entidad para el acceso a BD (multientidad).
	 *
	 * @param idEntidad Identificador de entidad.
	 */
	protected void setOrganizationUserInfo(String idEntidad) {

		OrganizationUserInfo info = new OrganizationUserInfo();
		info.setOrganizationId(idEntidad);
		info.getSpacPoolName();

		OrganizationUser.setOrganizationUserInfo(info);

		if (logger.isDebugEnabled()) {
			logger.debug("Establecida la entidad en la sesion de tramitacion: " + idEntidad);
		}
	}

	/**
	 * Crear el contexto de tramitación.
	 *
	 * @param responsibleUserName Nombre del usuario responsable.
	 * @return Contexto de tramitación.
	 * @throws Exception
	 */
	protected ClientContext createClientContext(String responsibleUserName) throws Exception {

		ClientContext context = new ClientContext();
		context.setAPI(new InvesflowAPI(context));
		context.setLocale(new Locale("es", "ES"));

		if (StringUtils.isNotBlank(responsibleUserName)) {
			// Autor para las fases, trámites, tipos de documentos...
			Responsible responsible = new User("", responsibleUserName + " [Administracion de Entidades]");
			context.setUser(responsible);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Creado el contexto de tramitacion.");
		}

		return context;
	}
}
