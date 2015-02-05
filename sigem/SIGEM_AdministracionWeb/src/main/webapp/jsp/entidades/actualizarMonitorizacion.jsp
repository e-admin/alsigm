<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>

<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>

<%
	List procesosExportacionNew = (ArrayList)request.getAttribute(Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES);
	List procesosImportacionNew = (ArrayList)request.getAttribute(Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES);
	List procesosAccionNew = (ArrayList)request.getAttribute(Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD);

	String proceso = (String) request.getAttribute(Defs.PARAMETRO_ID_PROCESO);
	if (proceso == null) {
		proceso = "";
	}

	File f = new File((String)request.getAttribute(Defs.PARAMETRO_FICHERO_MONITORIZACION));
	InputStream is=null;
	String contenido = null;
	boolean acabado = false;

	if (f != null) {
		try {
			is=new FileInputStream(f);
			byte[]b=new byte[is.available()];
			is.read(b);
			contenido = new String(b);
			if(contenido.indexOf("[FIN]")>0)
				acabado=true;
			is.close();
			is=null;
			f=null;
		} catch (Exception e) {}
	}
%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.admsistema.proceso.Proceso"%>
<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		<script language="javascript">
			function pageHeight() {
				return  window.innerHeight != null? window.innerHeight : document.documentElement && document.documentElement.clientHeight ?  document.documentElement.clientHeight : document.body != null? document.body.clientHeight : null;
			}
			function goToBottom(){
				window.scrollTo(0, pageHeight());
			}

			var exportaciones = parent.document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_EXPORTACIONES%>');
			var valor_exp = '';
			if (exportaciones.selectedIndex > -1)
				valor_exp = exportaciones.options[exportaciones.selectedIndex].value;

			var importaciones = parent.document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_IMPORTACIONES%>');
			var valor_imp = '';
			if (importaciones.selectedIndex > -1)
				valor_imp = importaciones.options[importaciones.selectedIndex].value;

			var acciones = parent.document.getElementById('<%=Defs.PARAMETRO_MONITORIZACION_ACCIONES_MULTIENTIDAD%>');
			var valor_acc = '';
			if (acciones.selectedIndex > -1)
				valor_acc = acciones.options[acciones.selectedIndex].value;

			if (valor_exp != '') {
				while (exportaciones.length > 0)
					exportaciones.remove(exportaciones.length-1);
				<%for(int i=0; i<procesosExportacionNew.size(); i++) {%>
				exportaciones.options[<%=i%>] = new Option("<%=((Proceso)procesosExportacionNew.get(i)).getNombre()%>","<%=((Proceso)procesosExportacionNew.get(i)).getValor()%>");
				<%}%>
			}

			if (valor_imp != '') {
				while (importaciones.length > 0)
					importaciones.remove(importaciones.length-1);
				<%for(int i=0; i<procesosImportacionNew.size(); i++) {%>
				importaciones.options[<%=i%>] = new Option("<%=((Proceso)procesosImportacionNew.get(i)).getNombre()%>","<%=((Proceso)procesosImportacionNew.get(i)).getValor()%>");
				<%}%>
			}

			if (valor_acc != '') {
				while (acciones.length > 0)
					acciones.remove(acciones.length-1);
				<%for(int i=0; i<procesosAccionNew.size(); i++) {%>
				acciones.options[<%=i%>] = new Option("<%=((Proceso)procesosAccionNew.get(i)).getNombre()%>","<%=((Proceso)procesosAccionNew.get(i)).getValor()%>");
				<%}%>
			}

			var i;
			for(i=0; i<	exportaciones.length; i++)
				if (exportaciones.options[i].value == valor_exp)
					exportaciones.selectedIndex = i;
			for(i=0; i<	importaciones.length; i++)
				if (importaciones.options[i].value == valor_imp)
					importaciones.selectedIndex = i;
			for(i=0; i<	acciones.length; i++)
				if (acciones.options[i].value == valor_acc)
					acciones.selectedIndex = i;

			var terminado = <%=acabado%>;
			parent.setTerminado(terminado);
			if (terminado == true) {
				parent.pararActualizacion();
				parent.desactivarBoton('boton_actualizar');
				parent.desactivarMensaje('tiempo_espera');
				parent.activarMensaje('finalizado');
			} else {
				var idProceso = '<%=proceso%>';
				if (idProceso != '') {
					parent.activarBoton('boton_actualizar');
					parent.desactivarMensaje('finalizado');
					parent.activarMensaje('tiempo_espera');
					if (parent.getTimer() == '' && parent.getSegundos() != '') {
						if (parent.getSegundos() < 10) {
							parent.setSegundos(10);
						}
						parent.setTimer();
					}
				} else {
					parent.pararActualizacion();
					parent.desactivarBoton('boton_actualizar');
					parent.desactivarMensaje('tiempo_espera');
					parent.desactivarMensaje('finalizado');
				}
			}
		</script>
	</head>
	<body>
		<div align="center">
			<%if (contenido != null) { %>
				<textarea rows=20 cols=110 class=gr><%=contenido%></textarea>
			<%} else if ((proceso != null) && (proceso != "")) { %>
				<label class="gr"><bean:message key="mensaje.error.proceso.actualizar.no_fichero"/></label>
			<%}%>
		</div>
	</body>
</html>