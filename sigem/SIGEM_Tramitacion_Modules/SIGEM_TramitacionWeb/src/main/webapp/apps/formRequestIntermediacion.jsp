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
	
	/*
	function submitForm(){
		document.defaultForm.submit();
		showMsgInProgress();
	}
	*/

	function showMsgInProgress(){
		document.getElementById('inProgress').style.display='block';
		document.getElementById('finalidad').disabled=true;
		document.getElementById('nifTitular').disabled=true;
		document.getElementById('consentimiento').disabled=true;
	}
//--></script>

<div class="acciones_ficha">
    <a href="#" id="btnOk" class="btnOk"><bean:message key="common.message.ok"/></a>
	<a href="#" id="btnCancel" class="btnCancel"><bean:message key="common.message.close" /></a>
</div>

<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>



<div class="cuerpo_ficha">
<div class="seccion_ficha">

<%--
		<div class="cabecera_seccion">
			<h4><bean:message key="svd."/></h4>
		</div>
 --%>
			
	<html:form action="peticionSincrona.do">
		<html:hidden property="method" value="send"/>

		<c:set var="codigoCertificado" value="${requestScope['codigoCertificado']}" />
		<c:set var="nombreCertificado" value="${requestScope['nombreCertificado']}" />
		<jsp:useBean id="codigoCertificado" type="java.lang.String"/>
		<jsp:useBean id="nombreCertificado" type="java.lang.String"/>


		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.codigoCertificado" /></nobr></label>
			<html:text readonly="true" styleClass="inputReadOnly" property="property(codigoCertificado)" value="<%=codigoCertificado%>"/>
		</p>
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.nombreCertificado" /></nobr></label>
			<html:text readonly="true" styleClass="inputReadOnly" property="property(nombreCertificado)" value="<%=nombreCertificado%>" size="64"/>
		</p>

		
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.finalidad" /></nobr></label>
			<html:text styleId="finalidad" property="property(finalidad)"/>
		</p>
				
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.nifTitular" /></nobr></label>
			<html:text styleId="nifTitular" property="property(nifTitular)"/>
		</p>
<%--
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.nombre" /></nobr></label>
			<html:text property="nombreTitular"/>
		</p>
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.apellido1" /></nobr></label>
			<html:text property="apellido1Titular"/>
		</p>
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.apellido2" /></nobr></label>
			<html:text property="apellido2Titular"/>
		</p>
 --%>
		<p class="fila_sub clearfix">
			<label class="mid"><nobr><bean:message key="svd.form.consentimiento" /></nobr></label>
			<html:select styleId="consentimiento" property="property(consentimiento)">
				<html:option value="Si">Si</html:option>
				<html:option value="Ley">Ley</html:option>
			</html:select>
		</p>
	</html:form>

					<!--  Capa para presentar el mensaje de Operacion en progreso al seleccionar un tramite -->
					<div id="inProgress" style="display:none; text-align: center">
						<b><bean:message key="msg.layer.operationInProgress" /></b>
					</div>


				
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

		$("#btnOk").click(function() {
			// Deshabilitar el click de los botones
			$("#btnOk").unbind('click');
			$("#btnCancel").unbind('click');
			document.defaultForm.submit();
			showMsgInProgress();
		});

		$("#btnCancel").click(function() {
			closeFrame();
		});
	
	});



</script>
