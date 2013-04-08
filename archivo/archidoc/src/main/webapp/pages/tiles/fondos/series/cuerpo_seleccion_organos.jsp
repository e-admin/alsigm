<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />

<c:set var="ADD_PRODUCTOR_HISTORICO" value="${sessionScope[appConstants.fondos.ADD_PRODUCTOR_HISTORICO]}"/>

<c:set var="identificacionSerie" value="${sessionScope[appConstants.fondos.IDENTIFICACION_SERIE_KEY]}" />
<c:set var="vListaPosiblesProductores" value="${sessionScope[appConstants.fondos.LISTA_POSIBLES_PRODUCTORES_KEY]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${ADD_PRODUCTOR_HISTORICO}">
				<fmt:message key="archigest.archivo.cf.asociarProductores.historicos"/>

			</c:when>
			<c:otherwise>
				<fmt:message key="archigest.archivo.cf.asociarProductor"/>
			</c:otherwise>
		</c:choose>

	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   <TD>
			   <bean:struts id="mappingIdentificacionSerie" mapping="/gestionIdentificacionAction" />
					<script>
						function seleccionarProductores() {
							var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
							form.submit();
						}


					</script>


					<a class=etiquetaAzul12Bold href="javascript:seleccionarProductores()">
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<tiles:insert definition="button.returnButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					<tiles:put name="action" direct="true">edicionIdentificacion</tiles:put>
				</tiles:insert>

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


		<c:choose>
			<c:when test="${ADD_PRODUCTOR_HISTORICO}">
				<input type="hidden" name="method" value="incorporarProductorHistorico">
			</c:when>
			<c:otherwise>
				<input type="hidden" name="method" value="incorporarProductores">
			</c:otherwise>
		</c:choose>



		<div class="cabecero_bloque" style="text-align:left;padding-left:4px">
			<TABLE cellpadding=0 cellspacing=0 class="w98" height="100%"><TR><TD class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.cf.msgSelProductores"/>
			</TD></TR></TABLE>
		</div>
		<c:set var="tipoOrgano"><c:out value="${param.tipoOrgano}">2</c:out></c:set>
		<div class="bloque" style="padding:4px">
		<c:choose>
		<c:when test="${identificacionSerie.permitidoIncorporarProductorEntidad}">
		<table class="formulario" cellspacing="3">

			<c:choose>
				<c:when test="${ADD_PRODUCTOR_HISTORICO}">
					<tr>
						<td class="tdTitulo" width="300px" nowrap="nowrap">
							<fmt:message key="archigest.archivo.fecha.inicio.vigencia.productor"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<html:text property="fechaInicioVigenciaProductor" styleClass="input" size="12" maxlength="10"/>
						&nbsp;<archigest:calendar
							image="../images/calendar.gif"
		                    formId="IdentificacionForm"
		                    componentId="fechaInicioVigenciaProductor"
		                    format="dd/mm/yyyy"
		                    enablePast="true" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="300px" nowrap="nowrap">
							<fmt:message key="archigest.archivo.fecha.fin.vigencia.productor"/>:&nbsp;
						</td>
						<td class="tdDatos">
						<html:text property="fechaFinVigenciaProductor" styleClass="input" size="12" maxlength="10"/>
						&nbsp;<archigest:calendar
							image="../images/calendar.gif"
		                    formId="IdentificacionForm"
		                    componentId="fechaFinVigenciaProductor"
		                    format="dd/mm/yyyy"
		                    enablePast="true" />
						</td>
					</tr>
				</c:when>
				<c:otherwise>
				<tr>
				<td class="tdTitulo" style="vertical-align:top;">
				<script>
					function incorporarProductorEntidadProductora() {
						var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
						form.method.value = 'incorporarProductorEntidadProductora';
						xDisplay('entidadProductoraFondo','block');
						xDisplay('busquedaProductores','none');

					}
				</script>
					<input type="radio" name="tipoOrgano" value="1" class="radio" onclick="incorporarProductorEntidadProductora()" <c:if test="${tipoOrgano == 1}">checked</c:if>>
					<bean:message key="archigest.archivo.cf.msgAsignarEntidadProductoraComoProductor"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" style="vertical-align:top;">
				<script>
					function buscarProductores() {
						var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
						form.method.value = 'incorporarProductores';
						xDisplay('entidadProductoraFondo','none');
						xDisplay('busquedaProductores','block');
					}
				</script>
					<input type="radio" name="tipoOrgano" value="2" class="radio" onclick="buscarProductores()"  <c:if test="${tipoOrgano == 2}">checked</c:if>>
					<bean:message key="archigest.archivo.cf.msgSelProductoresIdentificacion"/>
				</td>
			</tr>


				</c:otherwise>
			</c:choose>

		</table>
		</c:when>
		<c:otherwise>
			<input type="hidden" name="tipoOrgano" value="2" />
			<c:set var="tipoOrgano" value="2" />
		</c:otherwise>
		</c:choose>

		<div class="separador5">&nbsp;</div>
		<div id="entidadProductoraFondo" <c:if test="${tipoOrgano == 2}">style="display:none"</c:if>>
			<c:set var="entidadProductoraFondo" value="${identificacionSerie.entidadProductoraFondo}" />
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.entidadProductora"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
				<td class="tdTitulo" width="120px"><bean:message key="archigest.archivo.cf.TipoEntidad"/>:&nbsp;</td>
				<td class="tdDatos">
					<c:choose>
					<c:when test="${entidadProductoraFondo.tipoentidad == appConstants.fondos.tiposEntidad.FAMILIA.identificador}">
						<bean:message key="archigest.archivo.cf.familia"/>
					</c:when>
					<c:when test="${entidadProductoraFondo.tipoentidad == appConstants.fondos.tiposEntidad.INSTITUCION.identificador}">
						<bean:message key="archigest.archivo.cf.institucion"/>
					</c:when>
					<c:when test="${entidadProductoraFondo.tipoentidad == appConstants.fondos.tiposEntidad.PERSONA.identificador}">
						<bean:message key="archigest.archivo.cf.persona"/>
					</c:when>
					</c:choose>

				</td>
				</tr>
				<tr>
				<td class="tdTitulo"><bean:message key="archigest.archivo.cf.nombre"/>:&nbsp;</td>
				<td class="tdDatos"><c:out value="${entidadProductoraFondo.nombre}" /></td>
				</tr>
			</table>
			</tiles:put>
			</tiles:insert>
		</div>

		<div id="busquedaProductores" <c:if test="${tipoOrgano == 1}">style="display:none"</c:if>>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.msgSelProductoresIdentificacion"/></tiles:put>
		<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		  <TR>
				<TD>
				<script>
					function buscarPosiblesProductores() {
						var form = document.forms['<c:out value="${mappingIdentificacionSerie.name}" />'];
						form.method.value = "buscarPosiblesProductores";

						if (window.top.showWorkingDiv) {
							var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
							var message = '<bean:message key="archigest.archivo.buscando.msgProductores"/>';
							var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
							window.top.showWorkingDiv(title, message, message2);
						}


						form.submit();
					}
				</script>
				<a class="etiquetaAzul12Normal" href="javascript:buscarPosiblesProductores()">
					<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/>
				</a>
				</TD>
		 </TR>
		</TABLE>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
			<table class="formulario" width="99%">
				<tr>
					<td width="180px" class="tdTitulo"><bean:message key="archigest.archivo.cf.nombreProductor"/>:&nbsp;</td>
					<td class="tdDatos"><input type="text" size="40" name="tokenNombreProductor" value="<c:out value="${param.tokenNombreProductor}" />"></td>
				</tr>
			</table>

			<c:if test="${vListaPosiblesProductores != nul}">
			<display:table name='pageScope.vListaPosiblesProductores'
							style="width:99%;"
							id="productor"
							sort="list"
							export="false">

				<display:column style="width:1%">
					<c:choose>
						<c:when test="${ADD_PRODUCTOR_HISTORICO}">
							<html-el:radio property="guidProductorHistorico" value="${productor.guid}"/>
						</c:when>
						<c:otherwise>
							<html-el:multibox property="guidsProductor" value="${productor.guid}" />
						</c:otherwise>
					</c:choose>
				</display:column>

				<display:column titleKey="archigest.archivo.nombre" property="nombre" />

			</display:table>
			<div class="separador5">&nbsp;</div>
			</c:if>
		</tiles:put>
		</tiles:insert>
		</div>
		</div>

		</html:form>
	</tiles:put>
</tiles:insert>