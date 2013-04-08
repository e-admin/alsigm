<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%@page import="ieci.tecdoc.sgm.admsistema.proceso.Info"%>

<%
Info informacion = (Info)request.getAttribute(Defs.PARAMETRO_INFORMACION_PROCESO);
%>

<html>
	<head>
		<link href="css/estilos.css" type=text/css rel=stylesheet />
		<script language="javascript">

		</script>
	</head>
	<body>
		<div align="left" style="valign: top; top: 0px;">
			<%
			if (informacion != null) { 
				String size = new Double(informacion.getSize()).toString();
				int pos = size.indexOf(".");
				if (pos != -1)
					size = size.substring(0, (pos+2<size.length()) ? pos + 2 : (pos+1<size.length() ? pos + 1 : pos));
			%>
			<label class="gr"><b><bean:message key="proceso.borrar.num_directorios" /></b>: <%=informacion.getDirectorios()%></label><br/>
			<label class="gr"><b><bean:message key="proceso.borrar.num_ficheros" /></b>: <%=informacion.getFicheros()%></label><br/>
			<label class="gr"><b><bean:message key="proceso.borrar.ocupacion" /></b>: <%=size + " " + informacion.getDescripcion()%></label><br/>
			<%}%>
		</div>
	</body>
</html>