<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="serie" value="${sessionScope[appConstants.fondos.SERIE_KEY]}" />
<c:set var="vListaSoportes"	value="${sessionScope[appConstants.fondos.LISTA_SOPORTES_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cf.anadirSoporte"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   <TD>
		   <c:if test="${!empty vListaSoportes}">
				<a class=etiquetaAzul12Bold href="javascript:document.forms[0].submit();">
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</c:if>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="cancelURI" value="/action/gestionIdentificacionAction">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.serie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" style="vertical-align:top;" width="180px">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<tiles:insert definition="fondos.cf.jerarquiaElemento" />
				</td>
			</tr>
			<tr>
				<td width="180px" class="tdTitulo">
					<bean:message key="archigest.archivo.serie"/>:&nbsp;
				</td>
				<td class="tdDatosBold">
					<c:out value="${serie.codigo}" /> <c:out value="${serie.denominacion}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<html:form action="/gestionIdentificacionAction" method="post">
		<input type="hidden" name="method" value="anadirsoporte">

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.listaSoportes"/></tiles:put>
		<tiles:put name="blockContent" direct="true">

			<div class="separador5">&nbsp;</div>

			<display:table name='pageScope.vListaSoportes' 
							style="width:99%;margin-left:auto;margin-right:auto"
							id="soporte" 
							sort="list"
							export="false">

				<display:column style="width:1%">
					<input type="checkbox" name="idsSoportesSeleccionado" value="<c:out value="${soporte.nombre}"/>">
				</display:column>

				<display:column titleKey="archigest.archivo.cf.nombre" >
					<c:out value="${soporte.nombre}"/>
				</display:column>

			</display:table>

			<div class="separador5">&nbsp;</div>

		</tiles:put>
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>