<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingGestionSoportes" mapping="/gestionSoportes" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.edicionSoporte"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">	
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionSoportes.name}" />'].submit()" >
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   <TD>
					<c:url var="cancelURI" value="/action/gestionSoportes">
						<c:param name="method" value="volverAvistaUnidadDocumental"  />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
						<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">	
		
		<tiles:insert definition="transferecias.unidadDocumental.contextoUdoc" />
		
		<html:form action="/gestionSoportes">
		<input type="hidden" name="method" value="incorporarSoporte">
		<input type="hidden" name="itemIndex" value="<c:out value="${param.itemIndex}" />">
		
		<div class="bloque"> <%-- primer bloque de datos --%>
			
			<TABLE class="formulario" cellpadding=0 cellspacing=0>
				<TR>
					<TD class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.transferencias.volumen" />:&nbsp;
					</TD>
					<TD class="tdDatos">
						<html:text property="numeroDocumentos" size="4" maxlength="5" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.transferencias.formato" />:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="formatos" value="${requestScope[appConstants.transferencias.LISTA_FORMATOS_DOCUMENTO]}" />
						<html:select property="formato">
							<html:options collection="formatos" property="valor" labelProperty="valor" />
						</html:select>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.transferencias.soporte" />:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:set var="soportes" value="${requestScope[appConstants.transferencias.LISTA_SOPORTES]}" />
						<html:select property="soporte">
							<html:options collection="soportes" property="valor" labelProperty="valor" />
						</html:select>
					</TD>
				</TR>
			</TABLE>
		
		</div> <%-- bloque --%>
	</html:form>
	</tiles:put>
</tiles:insert>