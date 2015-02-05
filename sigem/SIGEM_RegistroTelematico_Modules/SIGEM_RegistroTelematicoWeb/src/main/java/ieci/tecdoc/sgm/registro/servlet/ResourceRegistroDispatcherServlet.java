package ieci.tecdoc.sgm.registro.servlet;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.web.ResourceDispatcherServlet;

import javax.servlet.http.HttpServletRequest;

public class ResourceRegistroDispatcherServlet extends ResourceDispatcherServlet {
	/**
	 * metodo que retorna el identificador de identidad para el usuario actualmente logado, para ello debe existir en la 
	 * request o en la session el parámetro <code>ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO</code>
	 * que actualmente tiene el valor "keySesionUsuario";
	 * @param req
	 * @return
	 * @throws SigemException
	 */
	protected String getIdEntidad(HttpServletRequest req)  {
		String idEntidad=(String)req.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
		if(idEntidad==null)
			idEntidad=(String)req.getSession().getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
		return idEntidad;
	}
}
