<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_KEY]}"/>
<c:set var="mostrarRenumeracion" value="${requestScope[appConstants.deposito.MOSTRAR_RENUMERACION_KEY]}"/>
<bean:struts id="mappingGestionEstadoHuecos" mapping="/gestionEstadoHuecosAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.edicionHueco"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
				<script>
					function guardarNumeracion() {
						var form = document.forms['<c:out value="${mappingGestionEstadoHuecos.name}" />'];
						var check = document.getElementById('recalcular');
						if(check && check.checked){
							if(window.confirm("<bean:message key='archigest.warning.deposito.renumeracion'/>"))						 
								form.submit();
						}						
						else
							form.submit();
					}					
				</script>
				<td>				
					<a class="etiquetaAzul12Bold" href="javascript:guardarNumeracion()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
				<td width="10">&nbsp;</td>
				<td noWrap>
					<tiles:insert definition="button.closeButton">
						<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
						<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
					</tiles:insert>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.unidInstalacion"/>:&nbsp;
			</tiles:put>
		
			<tiles:put name="blockContent" direct="true">		
				<TABLE class="formulario" cellpadding=0 cellspacing=0>
					<TR>
						<TD class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.ruta"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.path}"/>
						</TD>
					</TR>		
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.numeracion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.numeracion}"/>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.nombreformato}"/>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
		</tiles:insert>
		
		<div class="separador8">&nbsp;</div>
				
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.nuevaNumeracionHueco"/>:&nbsp;
			</tiles:put>
	
			<tiles:put name="blockContent" direct="true">
				<div class="separador8">&nbsp;</div>
				<html:form action="/gestionEstadoHuecosAction">
					<input type="hidden" name="method" value="saveNumeracion"/>
					<html:hidden property="idHueco"/>
						<table class="formulario" cellpadding=0 cellspacing=0>											
							<tr>
								<td class="tdTitulo" width="150px">					
									<fmt:message key="archigest.archivo.numeracion"/>:&nbsp;
								</td>
								<td class="tdDatos" nowrap="nowrap">
									<c:choose> 
										<c:when test="${mostrarRenumeracion}">
											<html:text property="numeracion" styleId="textoNumeracion" maxlength="16" onkeyup="javascript:mostrarRecalcular()"/>
										</c:when>
										<c:otherwise>
											<html:text property="numeracion" styleId="textoNumeracion" maxlength="16"/>
										</c:otherwise>									
									</c:choose>
									<div class="etiquetaAzul11Bold" id="mostrarRecalcular" style="display:none;margin-left:1cm;">
										<html:checkbox property="renumerar" value="true" styleClass="checkbox" styleId="recalcular"/>	
										<fmt:message key="archigest.archivo.recalcularnumeracion"/>									
									</div>
								</td>																											
							</tr>				
						</table>										
				</html:form>
				<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>		
	</tiles:put>
</tiles:insert>
<script>
	mostrarRecalcular();
</script>