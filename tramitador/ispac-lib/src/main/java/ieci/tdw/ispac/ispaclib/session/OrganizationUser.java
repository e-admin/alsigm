package ieci.tdw.ispac.ispaclib.session;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public class OrganizationUser {

	private static ThreadLocal session = new ThreadLocal();

	public static void setOrganizationUserInfo(OrganizationUserInfo userInfo) {
		session.set(userInfo);

		// Se establece el identificador de la entidad para los módulos del
		// framework-td
		MultiEntityContextHolder.setEntity(userInfo.getOrganizationId());
	}

	public static OrganizationUserInfo getOrganizationUserInfo() {
		return (OrganizationUserInfo) session.get();
	}
}
