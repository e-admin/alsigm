package ieci.tecdoc.sgm.registropresencial.autenticacion;


import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public class Login {

	public static String login(User user, String entidad)
			throws SecurityException, ValidationException {
		String sessionID = SecuritySession.login(user.getUserName(), user
				.getPassword(), user.getLocale(), entidad);

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad);

		return sessionID;
	}
}
