<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionListasAcceso" mapping="/gestionListasAcceso" />
<c:set var="formBean" value="${sessionScope[mappingGestionListasAcceso.name]}" />
<c:set var="tiposLista" value="${appConstants.tiposListasAcceso}" />

<c:choose>
	<c:when test="${param.method=='edicionListaAcceso'}">
		<c:set var="editando" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="editando" value="false"/>
	</c:otherwise>
</c:choose>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${editando}">
				<bean:message key="archigest.archivo.cacceso.editarListaAcceso"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.cacceso.crearListaAcceso"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
	<script>
		function crearListaAcceso() {
			var form = document.forms['<c:out value="${mappingGestionListasAcceso.name}" />'];
			form.submit();
		}
	</script>
		<table><tr>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:crearListaAcceso()" >
			<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
		</td>		
		<td width="10px"></td>
		<td nowrap>
			<c:url var="cancelURL" value="/action/gestionListasAcceso">
				<c:param name="method" value="goBack" />
			</c:url>
			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="bloque">
		<html:form action="/gestionListasAcceso">								
			<input type="hidden" name="method" value="guardarListaAcceso">
			
			<c:if test="${!empty formBean.idListaAcceso}">
				<html:hidden property="idListaAcceso" />
			</c:if>			
			<table class="formulario" width="99%">		
				<tr>
					<td class="tdTitulo" width="120px">
						<bean:message key="archigest.archivo.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text  property="nombre" styleClass="input99" maxlength="254"/>
					</td>
				</tr>
				<c:choose>
					<c:when test="${editando}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.tipo"/>:&nbsp;
							</td>
							<td class="tdDatos">													
								<c:choose>
									<c:when test="${formBean.tipoLista==tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}">
										<bean:message key="archigest.archivo.elementoDelCuadro"/>
									</c:when> 
									<c:when test="${formBean.tipoLista==tiposLista['DESCRIPTOR']}">
										<bean:message key="archigest.archivo.descriptor"/>
									</c:when> 
									<c:when test="${formBean.tipoLista==tiposLista['FORMATO_FICHA']}">
										<bean:message key="archigest.archivo.formatoFicha"/>
									</c:when> 
								</c:choose>
							</td>
						</tr>			
					</c:when>
					<c:otherwise>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.tipo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<table>
								<tr>
									<td width="10px"  valign="middle">
										<input type="radio" name="tipoLista" value="<c:out value="${tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}" />"
											<c:if test="${formBean.tipoLista==tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}"> checked </c:if>
										class="radio">
									</td>
									<td class="etiquetaAzul11Bold" valign="middle">								
										<bean:message key="archigest.archivo.elementoDelCuadro"/>
									</td>
									<td width="10px">&nbsp;</td>
										
									<td width="10px"  valign="middle">
										<input type="radio" name="tipoLista" value="<c:out value="${tiposLista['DESCRIPTOR']}" />"
											<c:if test="${formBean.tipoLista==tiposLista['DESCRIPTOR']}"> checked </c:if>
										class="radio">
									</td>
									<td class="etiquetaAzul11Bold" valign="middle">
										<bean:message key="archigest.archivo.descriptor"/>
									</td>
									<td width="10px">&nbsp;</td>
	
									<td width="10px"  valign="middle">
										<input type="radio" name="tipoLista" value="<c:out value="${tiposLista['FORMATO_FICHA']}" />"
											<c:if test="${formBean.tipoLista==tiposLista['FORMATO_FICHA']}"> checked </c:if>
										class="radio">
									</td>								
									<td class="etiquetaAzul11Bold" valign="middle">
										<bean:message key="archigest.archivo.formatoFicha"/>
									</td>
								</tr>
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td class="tdTitulo" style="vertical-align:top;">
						<bean:message key="archigest.archivo.descripcion"/>
					</td>
					<td class="tdDatos"><html:textarea rows="4" cols="20" property="descripcion" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" /></td>
				</tr>
			</table>
		</html:form>
		</div>
	</tiles:put>
</tiles:insert>