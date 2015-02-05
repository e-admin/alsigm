<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>


<tiles:importAttribute/>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO}">
		<tiles:insert attribute="Codigo" ignore="false"/>
	</c:if>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE}">
		<tiles:insert attribute="Numero.Expediente" ignore="false"/>
	</c:if>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO}">
		<tiles:insert attribute="Titulo" ignore="false"/>
	</c:if>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL}">
		<tiles:insert attribute="Fecha.Inicial" ignore="false"/>
	</c:if>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL}">
		<tiles:insert attribute="Fecha.Final" ignore="false"/>
	</c:if>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR}">
		<tiles:insert attribute="Productor" ignore="false"/>
	</c:if>
	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO}">
		<tiles:insert attribute="Bloqueo" ignore="false"/>
	</c:if>

	<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONSERVADA}">
		<tiles:insert attribute="Conservada" ignore="false"/>
	</c:if>






