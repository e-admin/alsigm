package ieci.tdw.ispac.ispaclib.reports.xsl;

import javax.servlet.http.HttpServletRequest;

public class ParameterUtils {

	public void includeParameterResponsible(HttpServletRequest request, String property){
		java.util.Map map = new java.util.HashMap();
		map.put(property, "UID");
		map.put("NAME_"+property, "NAME");
		request.getSession().setAttribute(property, map);
		request.setAttribute("parameters", property);		
	}
}
