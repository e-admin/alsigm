<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
List municipios = (ArrayList)request.getAttribute(Defs.PARAMETRO_MUNICIPIOS);
%>

<%@page import="ieci.tecdoc.sgm.admsistema.temporal.Municipio"%>
<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<SCRIPT language=javascript>
			var municipios = parent.document.getElementById('<%=Defs.PARAMETRO_MUNICIPIO%>');
			while (municipios.length > 0)
				municipios.remove(municipios.length-1);
			<%for(int i=0; i<municipios.size(); i++) {%>
			municipios.options[<%=i%>] = new Option("<%=((Municipio)municipios.get(i)).getDescripcion()%>","<%=((Municipio)municipios.get(i)).getIdMunicipio()%>");
			<%}%>
		</SCRIPT>
	</head>
	<body>

	</body>
</html>