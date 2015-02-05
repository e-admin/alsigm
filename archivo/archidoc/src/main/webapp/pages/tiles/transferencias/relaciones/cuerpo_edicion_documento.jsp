<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingGestionDocumentos" mapping="/gestionUdocsRelacion" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.editDocumento"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">	
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		    <TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionDocumentos.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="goBackURI" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="volverAvistaUnidadDocumental"  />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${goBackURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">	
	<DIV id="barra_errores">
			<archivo:errors />
	</DIV>

	<tiles:insert definition="transferecias.unidadDocumental.contextoUdoc" />	
	
	<div class="separador5">&nbsp;</div>
	
	<div class="bloque">
	<TABLE class="formulario" cellpadding=0 cellspacing=0>
		<html:form action="/gestionUdocsRelacion">
		<input type="hidden" name="method" value="<c:out value="agregarDocumento" />">
		<TR>
			<TD class="tdTitulo" width="150px">
				<bean:message key="archigest.archivo.transferencias.tipoDoc" />:&nbsp;
			</TD>
			<TD class="tdDatos">
				<html:text property="tituloDocumento" styleClass="input99" maxlength="254"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" width="150px">
				<bean:message key="archigest.archivo.transferencias.asunto" />:&nbsp;
			</TD>
			<TD class="tdDatos">
				<html:text property="descripcionDocumento" styleClass="input99" maxlength="254"/>
			</TD>
		</TR>
		</html:form>	
	</TABLE>
	</div>
	</tiles:put>
</tiles:insert>	