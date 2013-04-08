<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>

<tiles:importAttribute/>

<c:choose>
	<c:when test="${elemento.configurable == 'S'}">
			<c:if test="${elemento.tipo == '2'}">
				<tiles:insert attribute="Generico.Numerico" ignore="false"/>
			</c:if>
			<c:if test="${elemento.tipo == '3'}">
				<tiles:insert attribute="Generico.Fecha" ignore="false"/>
			</c:if>
			<c:if test="${elemento.tipo == '8'}">
				<tiles:insert attribute="Generico.Texto.Corto" ignore="false"/>
			</c:if>
			<c:if test="${elemento.tipo == '9'}">
				<tiles:insert attribute="Generico.Texto.Largo" ignore="false"/>
			</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO}">
			<tiles:insert attribute="Ambito" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA}">
			<tiles:insert attribute="Codigo.Referencia" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO}">
			<tiles:insert attribute="Titulo" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.prestamos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION}">
			<tiles:insert attribute="Codigo.Relacion" />
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO}">
			<tiles:insert attribute="Codigo" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS}">
			<tiles:insert attribute="Rango" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA}">
			<tiles:insert attribute="Signatura" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE}">
			<tiles:insert attribute="Numero.Expediente" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO}">
			<tiles:insert attribute="Texto" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL}">
			<tiles:insert attribute="Fecha.Inicial" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL}">
			<tiles:insert attribute="Fecha.Final" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS}">
			<tiles:insert attribute="Fechas" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR}">
			<tiles:insert attribute="Productor" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO}">
			<tiles:insert attribute="Dato.Numerico" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO}">
			<tiles:insert attribute="Fondo" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION}">
			<tiles:insert attribute="Niveles.Descripcion" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO}">
			<tiles:insert attribute="Estado" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DESCRIPTORES}">
			<tiles:insert attribute="Descriptores" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO}">
			<tiles:insert attribute="Contenido_Fichero" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PROCEDIMIENTO}">
			<tiles:insert attribute="Procedimiento" ignore="false"/>
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS}">
			<tiles:insert attribute="Condiciones.Avanzadas" />
		</c:if>
		<c:if test="${elemento.nombre == appConstants.fondos.camposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO}">
			<tiles:insert attribute="Bloqueo" ignore="false"/>
		</c:if>
</c:otherwise>
</c:choose>



