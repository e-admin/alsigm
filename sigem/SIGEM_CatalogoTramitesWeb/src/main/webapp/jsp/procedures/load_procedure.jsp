<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Documentos" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite" %>
<%@page import="ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites"%>
<%@page import="ieci.tecdoc.sgm.core.services.LocalizadorServicios"%>
<%@page import="ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper"%>

<bean:define id="procedure" name="procedure" type="ieci.tecdoc.sgm.core.services.catalogo.Tramite"/>
<bean:define id="limit" name="limit" type="java.lang.String"/>
<bean:define id="firmap" name="firmap" type="java.lang.String"/>
	
<html>
	<script language="javascript">
		parent.proc.doneLoad("<%=procedure.getId()%>", "<%=procedure.getDescription()%>", "<%=procedure.getTopic()%>", "<%=procedure.getAddressee()%>", "<%=limit%>", "<%=firmap%>", "<%=procedure.getOficina()%>", "<%=procedure.getIdProcedimiento()%>");
        <%
        	ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
			Documentos documents = procedure.getDocuments();
           	DocumentoExtendido document;
           	DocumentoTramite docProc;
           	String mandatory;
           	for (int i = 0; i < documents.count(); i++) {
                document = documents.get(i);
                docProc = oServicio.getProcedureDocument(procedure.getId(), document.getId(), document.getCode(), SesionAdminHelper.obtenerEntidad(request));
                if (docProc.isMandatory())
                    mandatory = "1";
                else
                    mandatory = "0";
                out.write("parent.proc.addDocTramit(\"" + document.getId() + "\", \"" + document.getDescription() + "\" , \"" + docProc.getCode() + "\", \"" + mandatory + "\");");
           }
        %>
    </script>
</html>