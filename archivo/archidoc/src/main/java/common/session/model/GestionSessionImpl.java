package common.session.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import common.bi.GestionSessionBI;
import common.bi.ServiceBase;
import common.session.db.ISessionDBEntity;
import common.session.vos.SessionVO;
import common.util.DateUtils;

/**
 * Clase de implementacion de los metodos de gestión para las sesiones de
 * usuario
 */
public class GestionSessionImpl extends ServiceBase implements GestionSessionBI {

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(GestionSessionImpl.class);

	private ISessionDBEntity sessionDBEntity = null;

	public GestionSessionImpl(ISessionDBEntity sessionDBEntity) {
		this.sessionDBEntity = sessionDBEntity;
	}

	public String login(String ticket, String idUser) {
		logger.debug("Se va a proceder a iniciar la sesion " + ticket
				+ " para el usuario " + idUser);

		SessionVO sesion = new SessionVO();
		sesion.setId(ticket);
		sesion.setIdUsuario(idUser);
		sesion.setKeepAlive(new Date());

		iniciarTransaccion();
		// Comprobamos si el usuario tiene una sesion abierta
		SessionVO sesionUser = sessionDBEntity.getSessionUser(idUser);
		if (sesionUser == null) {
			sessionDBEntity.insertSession(sesion);
			logger.debug("La sesion no existe.Se ha creado la sesion " + ticket
					+ " para el usuario " + idUser);
		} else if (!DateUtils.isInTimeBeforeNow(sesionUser.getKeepAlive(),
				DateUtils.TIME_24H)) {
			sessionDBEntity.updateSession(sesion);
			logger.debug("La sesion no existe.Se ha creado la sesion " + ticket
					+ " para el usuario " + idUser);
		} else {
			sesion = sesionUser;
			logger.debug("La sesion para el usuario " + idUser
					+ " existe con el ticket " + sesion.getId());
		}
		commit();
		return sesion.getId();
	}

	public void keepAlive(String ticket) {
		logger.debug("Se va a proceder a actualizar la sesion " + ticket);

		try {
			SessionVO sesion = new SessionVO();
			sesion.setId(ticket);
			sesion.setKeepAlive(new java.util.Date(System.currentTimeMillis()));
			iniciarTransaccion();
			sessionDBEntity.updateKeepAlive(sesion);
			commit();
			logger.debug("Se ha actualizado la sesion " + ticket);
		} catch (Exception e) {
			logger.error("Se ha producido un error al actualizar la sesión: "
					+ ticket, e);
		}
	}

	public void logout(String ticket) {
		logger.debug("Se va a proceder a borrar la sesion " + ticket);
		iniciarTransaccion();
		sessionDBEntity.deleteSession(ticket);
		commit();
		logger.debug("Se ha borrado la sesion " + ticket);
	}

	public String loginExpulsando(String ticket, String idUser) {
		logger.debug("Se va a proceder a iniciar la sesion " + ticket
				+ " para el usuario " + idUser);

		SessionVO sesion = new SessionVO();
		sesion.setId(ticket);
		sesion.setIdUsuario(idUser);
		sesion.setKeepAlive(new java.util.Date(System.currentTimeMillis()));

		iniciarTransaccion();
		// Comprobamos si el usuario tiene una sesion abierta
		SessionVO sesionUser = sessionDBEntity.getSessionUser(idUser);
		if (sesionUser == null) {
			sesion = sessionDBEntity.insertSession(sesion);
			logger.debug("La sesion no existe.Se ha creado la sesion " + ticket
					+ " para el usuario " + idUser);
		} else {
			logger.debug("La sesion para el usuario " + idUser
					+ "se ha creado con el nuevo ticket " + sesion.getId());
			sessionDBEntity.deleteSession(sesionUser.getId());
			sessionDBEntity.insertSession(sesion);
		}
		commit();
		return sesion.getId();
	}

	public boolean isAlive(String ticket) {
		boolean resultado = false;

		if (sessionDBEntity.getSession(ticket) != null)
			resultado = true;

		return resultado;

	}

	public List getSessionesActivasActuales() {
		return sessionDBEntity.getSessionesActivasActuales();
	}

	public SessionVO getSessionUser(String idUser) {
		return sessionDBEntity.getSessionUser(idUser);
	}
}