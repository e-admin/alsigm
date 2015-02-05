package ieci.tecdoc.sgm.nt.struts.util;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;

import javax.servlet.http.HttpServletRequest;

public class Misc {

	public static Entidad obtenerEntidad(HttpServletRequest request){
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(SesionUserHelper.obtenerIdentificadorEntidad(request));
		return oEntidad;
	}
	
	public static final String IDIOMAS_DISPONIBLES = "IdiomasDisponibles";
	public static final String LANG = "LANG";
	public static final String COUNTRY = "COUNTRY";
}
