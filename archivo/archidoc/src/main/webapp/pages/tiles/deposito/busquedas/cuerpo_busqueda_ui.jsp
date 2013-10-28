<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<bean:struts id="actionMapping" mapping="/gestionDepositoAction" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<script language="JavaScript1.2" type="text/JavaScript">

	function cleanForm(){
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		cleanFormField(form,'idUbicacion');
		cleanFormField(form,'nombreUbicacion');
		cleanFormField(form,'signatura_like');
		cleanFormField(form,'signatura');
		cleanFormField(form,'fondo');
		cleanFormField(form,'formato');
		cleanFecha(form);
		removeAmbitos();
	}

	function buscarUI(){
		var ubicacion = document.getElementById('idUbicacion');
		if (ubicacion && ubicacion.value){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.buscando.msgDeposito"/>';
				window.top.showWorkingDiv(title, message);
			}
			document.forms['<c:out value="${actionMapping.name}" />'].method.value="buscarUI";
			document.forms['<c:out value="${actionMapping.name}" />'].submit();
		}
		else{
			window.alert('<bean:message key="archigest.archivo.deposito.ubicacion.obligatorio"/>');
		}
	}

	function generarInformeResultadoBusquedaUI(){
		var ubicacion = document.getElementById('idUbicacion');
		if (ubicacion && ubicacion.value){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.buscando.msgDeposito"/>';
				window.top.showWorkingDiv(title, message);
			}
			document.forms['<c:out value="${actionMapping.name}" />'].method.value="generarInformeBusquedaDeposito";
			document.forms['<c:out value="${actionMapping.name}" />'].submit();
		}
		else{
			window.alert('<bean:message key="archigest.archivo.deposito.ubicacion.obligatorio"/>');
		}

	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.busqueda.ui.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:buscarUI();">
					<html:img page="/pages/images/buscarCaja.gif"
					        altKey="archigest.archivo.buscar"
					        titleKey="archigest.archivo.buscar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.buscar"/></a>
				</td>
				<td width="10">&nbsp;</td>

				<td>
					<a class="etiquetaAzul12Bold" href="javascript:generarInformeResultadoBusquedaUI();">
					<html:img page="/pages/images/documentos/doc_pdf.gif"
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.informeResultadoBusqueda"/></a>
				</td>
				<td width="10">&nbsp;</td>
			   	<td>
					<a class="etiquetaAzul12Bold" href="javascript:cleanForm()">
					<html:img page="/pages/images/clear0.gif"
					        altKey="archigest.archivo.limpiar"
					        titleKey="archigest.archivo.limpiar"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.limpiar"/></a>
				</td>
				<td width="10">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<html:form action="/gestionDepositoAction">
			<input type="hidden" name="method" value="buscarUI"/>

			<div class="bloque">
				<input type="hidden" name="tipoBusqueda" value="<c:out value="${appConstants.fondos.tiposBusquedas.TIPO_BUSQUEDA_FONDOS_RAPIDA}"/>"/>

				<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
				<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
				<bean:define id="sizeCampo" value="200" toScope="request"/>

				<table class="formulario">
					<tiles:insert page="../busquedas/campo_busqueda_ubicacion.jsp" flush="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_condiciones_ambito.jsp" flush="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_signatura.jsp" flush="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_fondo.jsp" flush="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_formato.jsp" flush="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_fecha.jsp" flush="true"/>
				</table>
			</div>
		</html:form>

		<div class="separador2">&nbsp;</div>

		<c:set var="unidsInst" value="${sessionScope[appConstants.deposito.LISTADO_UNIDS_INST_BUSQUEDA_KEY]}" />
		<c:if test="${unidsInst != null}">
			<div class="cabecero_bloque" id="capaCabeceraResultadosBusqueda">
				<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				  <TR>
					<TD class="etiquetaAzul12Bold">
						<bean:message key="archigest.archivo.descripcion.descriptor.resultadoBusqueda"/>
					</TD>
				  </TR>
				</TABLE>
			</div>

			 <div class="bloque" style="position:static" id="capaResultadosBusqueda">
				<display:table name="pageScope.unidsInst"
						id="uInst"
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="external"
						defaultsort="0"
						requestURI="/action/gestionDepositoAction"
						export="true"
						pagesize="15">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.deposito.unidsinst.busquedaVacia"/>
					</display:setProperty>
					<display:column style="width:10" media="csv excel xml pdf">
						<fmt:formatNumber value="${uInst_rowNum}" pattern="0"/>
					</display:column>
					<display:column titleKey="archigest.archivo.signatura" style="width:90px" sortProperty="signaturaui" sortable="true" headerClass="sortable" media="html">
						<c:url var="URL" value="/action/verHuecoAction">
							<c:param name="method" value="listadoudocs" />
							<c:param name="idHueco" value="${uInst.idHueco}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${uInst.signaturaui}"/></a>
					</display:column>
					<display:column titleKey="archigest.archivo.signatura" property="signaturaui" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.ubicacion" property="path" media="csv excel xml pdf html"/>
					<display:column titleKey="archigest.archivo.busqueda.form.fondo" property="tituloFondo" style="width:400px;" media="csv excel xml pdf html"/>
					<display:column titleKey="archigest.archivo.deposito.formato" property="nombreFormato" style="width:150px;" media="csv excel xml pdf html"/>
					<display:column titleKey="archigest.archivo.documentos.clasificador.form.fechaCreacion" property="fcreacionString" style="width:110px;" media="csv excel xml pdf html"/>
				</display:table>
			</div>
		</c:if>
	</tiles:put>
</tiles:insert>