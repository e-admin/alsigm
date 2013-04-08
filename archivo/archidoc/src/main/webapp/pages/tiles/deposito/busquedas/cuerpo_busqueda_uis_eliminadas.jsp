<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<bean:struts id="actionMapping" mapping="/busquedaUISEliminadasAction" />
<bean:define id="actionMappingName" scope="page" name="actionMapping" property="name" toScope="request"/>
<script language="JavaScript1.2" type="text/JavaScript">

	function cleanForm(){
		var form = document.forms['<c:out value="${actionMappingName}" />'];
		cleanFormField(form,'signatura_like');
		cleanFormField(form,'signatura');
		cleanFormField(form,'formato');
		cleanFecha(form);
		cleanFormFieldIdArchivo(form);
		cleanCampoCheckbox(form,'motivos',true);
	}

	function buscarUI(){
		var frm = document.getElementById("formulario");
		if (frm && frm.motivos) {
			var nSelected = FormsToolKit.getNumSelectedChecked(frm,"motivos");
			if(nSelected >= 1) {
				if (window.top.showWorkingDiv) {
					var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
					var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
					window.top.showWorkingDiv(title, message);
				}
				document.forms['<c:out value="${actionMapping.name}" />'].method.value="buscarUI";
				document.forms['<c:out value="${actionMapping.name}" />'].submit();
			} else{
				alert("<bean:message key='archigest.archivo.deposito.motivo.eliminacion.obligatorio'/>");
			}
		} else {
			alert("<bean:message key='archigest.archivo.deposito.motivo.eliminacion.obligatorio'/>.");
		}
	}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.busqueda.ui.eliminadas.caption"/>
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
		<html:form action="/busquedaUISEliminadasAction" styleId="formulario">
			<input type="hidden" name="method" value="buscarUI"/>

			<div class="bloque">
				<bean:define id="classTdTituloCampo" value="tdTituloFicha" toScope="request"/>
				<bean:define id="classTdCampo" value="tdDatosFicha" toScope="request"/>
				<bean:define id="sizeCampo" value="200" toScope="request"/>
				<table class="formulario">
					<tiles:insert page="../busquedas/campo_busqueda_archivo.jsp" flush="true" ignore="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_signatura.jsp" flush="true" ignore="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_formato.jsp" flush="true" ignore="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_fecha.jsp" flush="true" ignore="true"/>
					<tiles:insert page="../busquedas/campo_busqueda_motivo_eliminacion.jsp" flush="true" ignore="true"/>
				</table>
			</div>
		</html:form>

		<div class="separador2">&nbsp;</div>

		<c:set var="unidsInst" value="${sessionScope[appConstants.deposito.LISTA_UIS_ELIMINADAS_KEY]}" />
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
			<div class="separador8">&nbsp;</div>
				<display:table name="pageScope.unidsInst"
						id="uInst"
						style="width:98%;margin-left:auto;margin-right:auto"
						sort="external"
						defaultsort="0"
						export="true"
						requestURI="/action/busquedaUISEliminadasAction"
						pagesize="15">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.deposito.unidsinst.busquedaVacia"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.prestamos.archivoPrestamo" property="nombreArchivo" sortable="true"/>
					<display:column titleKey="archigest.archivo.signatura" property="signaturaUI" sortable="true"/>
					<display:column titleKey="archigest.archivo.deposito.formato" property="nombreFormato" sortable="true"/>
					<display:column titleKey="archigest.archivo.deposito.motivo.eliminacion.ui" sortable="true">
					 	<fmt:message key="archigest.archivo.deposito.motivo.eliminacion.ui.${uInst.motivo}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.fecha" property="feliminacionString" style="width:110px;" sortable="true"/>
				</display:table>
			</div>
		</c:if>
	</tiles:put>
</tiles:insert>