<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>

<div id="contenido" class="move" >
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="svd.summary.title" /></h4>

<script language='JavaScript' type='text/javascript'><!--
	function closeFrame(){
		//Resituar en http, saliendo de https para poder invocar al top.hideFrame
		document.location.href = '<%="http://"+request.getServerName()+":"+PortsConfig.getHttpPort()+request.getContextPath()+"/apps/closeFrameHttps.jsp?refresh=true"%>';
	}
//--></script>

<div class="acciones_ficha">
	<a href="#" id="btnCancel" onclick='closeFrame()' class="btnCancel"><bean:message key="common.message.close" /></a>
</div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>



<div class="cuerpo_ficha">
<div class="seccion_ficha">
		<%--
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.summary" /></nobr></label>
		</p>
		--%>	
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.summary.codigo" /></nobr></label>
			<c:out value="${requestScope['RespuestaRecubrimientoWS_CodigoEstado']}"/><br/>			
		</p>		
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.summary.descripcion" /></nobr></label>
			<c:out value="${requestScope['RespuestaRecubrimientoWS_LiteralError']}"/>
		</p>		
</div>
<%--seccion ficha --%> 
</div>
<%--fin cuerpo ficha --%>
</div>
<%--fin  ficha --%>
<div>
<%--fin contenido --%>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>
