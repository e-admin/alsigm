<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="ieci.tecdoc.sgm.pe.struts.Constantes"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas"%>
<%@page import="java.io.File"%>

<%
	ArrayList idiomas = LectorIdiomas.getIdiomas();
	session.setAttribute(Constantes.IDIOMAS_DISPONIBLES, idiomas);

	String lang = (String)request.getParameter(Constantes.KEY_LANGUAGE);
	String country = (String)request.getParameter(Constantes.KEY_COUNTRY);
	
	if (lang!=null && country!=null)
		request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));
	
	String idEntidad = request.getParameter("ENTIDAD_ID");
	
	if (idEntidad != null && !idEntidad.equals("")) {
		String ruta = request.getRealPath("");
		String ruta_entera = ruta + "/img/" + idEntidad;
		File f = new File(ruta_entera);
		if (f.exists())	
			session.setAttribute("PARAMETRO_RUTA_IMAGENES", idEntidad + "/");
		else
			session.setAttribute("PARAMETRO_RUTA_IMAGENES", "");
		
		ruta_entera = ruta + "/css/" + idEntidad;
		f = new File(ruta_entera);
		if (f.exists())	
			session.setAttribute("PARAMETRO_RUTA_ESTILOS", idEntidad + "/");
		else
			session.setAttribute("PARAMETRO_RUTA_ESTILOS", "");
	}
%>

<%@page import="ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper"%>
<logic:redirect page="/login.do" />