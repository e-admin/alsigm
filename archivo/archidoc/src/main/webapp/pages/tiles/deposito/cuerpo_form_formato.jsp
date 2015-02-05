<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<c:set var="formato" value="${sessionScope[appConstants.deposito.INFO_FORMATO]}" />
<c:set var="editando" value="${!empty formato.id}" />
<bean:struts id="actionMapping" mapping="/gestionFormatosAction" />

<script>
function cambiarChecks(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	if (form.regular.value=='true')
		form.multidoc.value='true';
	else
		form.multidoc.value='false';
	form.vigente.value='true';
}

function activarOpcionesRegular(){
	var mostrar = false;
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	if (form.regular.value=='true')
		mostrar=true;

	FormsToolKit.setVisible('datosLongitud',mostrar);
	if(!document.all)
		document.getElementById('datosLongitud').style.display=mostrar?'table-row':'none';
}
function configViewInLoad(){
<c:choose>
<c:when test="${!editando}">
	activarOpcionesRegular();
</c:when>
<c:otherwise>
	<c:if test="${formato.regular}">
		FormsToolKit.setVisible('datosLongitud',true);
		if(!document.all)
			document.getElementById('datosLongitud').style.display='table-row';
	</c:if>
</c:otherwise>
</c:choose>
}

</script>

<div style="visibility:hidden;display:none;">
	<html:form action="/gestionFormatosAction">
	<input type="hidden" name="method" value="guardar"/>
	<html:hidden property="id"/>
</div>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${editando}">
				<fmt:message key="archigest.archivo.editar"/>
				<fmt:message key="archigest.archivo.deposito.formato"/>
			</c:when>
			<c:otherwise>
				<fmt:message key="archigest.archivo.crear"/>
				<fmt:message key="archigest.archivo.deposito.formato"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<SCRIPT>
					function save() {
						var form = document.forms['<c:out value="${actionMapping.name}" />'];
						form.submit();
					}
				</SCRIPT>
				<a class="etiquetaAzul12Bold" href="javascript:save()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" 
					titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD noWrap>
				<tiles:insert definition="button.closeButton">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
				</tiles:insert>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.informacion"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.tipo"/>:&nbsp;
						</td>
						<td class="tdDatos">
					<c:choose>
					<c:when test="${!editando}">
						<html:select property="regular" onchange="javascript:activarOpcionesRegular();javascript:cambiarChecks();">
							<html:option value="true"><fmt:message key="archigest.archivo.deposito.formato.regular" /></html:option>
							<html:option value="false"><fmt:message key="archigest.archivo.deposito.formato.irregular" /></html:option>								
						</html:select>
					</c:when>
					<c:otherwise>
							<c:choose>
								<c:when test="${formato.regular}">
									<fmt:message key="archigest.archivo.deposito.formato.regular" />
								</c:when>
								<c:otherwise>
									<fmt:message key="archigest.archivo.deposito.formato.irregular" />
								</c:otherwise>
							</c:choose>
					</c:otherwise>
					</c:choose>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos"><html:text property="nombre" styleClass="input60" maxlength="64"/></td>
					</tr>
				
					<tr id='datosMultidoc'>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.multidoc"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<c:choose>
							<c:when test="${!editando || (editando && formato.modificable)}">
								<html:select property="multidoc">
									<html:option value="true"><fmt:message key="archigest.archivo.si" /></html:option>
									<html:option value="false"><fmt:message key="archigest.archivo.no" /></html:option>								
								</html:select>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${formato.multidoc}">
										<fmt:message key="archigest.archivo.si" />
									</c:when>
									<c:otherwise>
										<fmt:message key="archigest.archivo.no" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>

				<c:if test="${formato.regular || !editando}">
					<tr id='datosLongitud'>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${!editando || (editando && formato.modificable)}">				
									<html:text size="5" maxlength="5" property="longitud" />
								</c:when>
								<c:otherwise>
									<c:out value="${formato.longitud}" />
								</c:otherwise>
							</c:choose>
							<fmt:message key="archigest.archivo.cm"/>
						</td>
					</tr>
				</c:if>					
					<tr>
						<td class="tdTitulo" width="150px">
							<fmt:message key="archigest.archivo.deposito.formato.vigente"/>:&nbsp;
						</td>
						<td class="tdDatos">
					<c:choose>
					<c:when test="${!editando || (editando && formato.modificable)}">
						<html:select property="vigente">							
							<html:option value="true"><fmt:message key="archigest.archivo.si" /></html:option>
							<html:option value="false"><fmt:message key="archigest.archivo.no" /></html:option>															
						</html:select>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${formato.vigente}">
								<fmt:message key="archigest.archivo.si" />
							</c:when>
							<c:otherwise>
								<fmt:message key="archigest.archivo.no" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
					
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
<script>
configViewInLoad();
</script>
