<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionValoracion" mapping="/gestionValoracion" />

<c:set var="tipoSerie" value="${sessionScope[appConstants.valoracion.TIPO_SERIES_EN_EDICION_KEY]}" />
<c:set var="listaSeries" value="${requestScope[appConstants.valoracion.LISTA_SERIES_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.valoracion.seleccionSeries"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td nowrap>
					<script>
						function anadirSeries() {
							var form = document.forms['<c:out value="${mappingGestionValoracion.name}" />'];
							var series = '<c:out value="${listaSeries}"/>';
							var radio = form.tipo;
							var msg = '';

							if (!radio || radio[0].checked) {
								if (series == '' || series == '[]') msg = "<bean:message key='archigest.archivo.valoracion.usarBuscadorSelSeries'/>";
								if (form.serieIDs && !elementSelected(form.serieIDs)) msg = "<bean:message key='archigest.archivo.valoracion.unaSerieMinimo'/>";
							} else
								if (form.nombreSerie.value=='') msg = "<bean:message key='archigest.archivo.valoracion.definirNombreSerie'/>";

							if (msg != '' ) alert(msg);
							else {
								form.method.value = 'anadirSerie';
								form.submit();
							}
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:anadirSeries()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.aceptar"/>&nbsp;
					</a>
				</td>
				<TD width="10">&nbsp;</TD>
				<TD>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
						<tiles:put name="action" direct="true">goReturnPoint</tiles:put>
					</tiles:insert>
				<TD>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<html:form action="/gestionValoracion">
		<input type="hidden" name="method">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.valoracion.msgSelSeriesValoracion"/>
			</tiles:put>

			<c:if test="${tipoSerie != appConstants.valoracion.TIPO_SERIE_RELACIONADAS}">
				<tiles:put name="buttonBar" direct="true">
					<TABLE class="w98m1" cellpadding=0 cellspacing=0>
						<TR>
							<TD width="100%" align="right" height="100%">
								<script>
									function buscarSeries() {
										var form = document.forms['<c:out value="${mappingGestionValoracion.name}" />'];
										form.method.value = 'buscarSerieEdicion';
										form.submit();
									}
								</script>
								<a class="etiquetaAzul12Normal" href="javascript:buscarSeries()">
									<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
									<bean:message key="archigest.archivo.buscar"/>
								</a>
							</TD>
						</TR>
					</TABLE>
				</tiles:put>
			</c:if>

			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>

				<c:if test="${tipoSerie == appConstants.valoracion.TIPO_SERIE_RELACIONADAS}">
					<table class="w100" cellpadding=0 cellspacing=0>
					<TR>
						<TD width="10px">&nbsp;</TD>
						<TD class="etiquetaAzul12Bold" width="150px">
							<bean:message key="archigest.archivo.valoracion.incorporarSerie"/>:&nbsp;
						</TD>
						<TD>
							<script>
								function checkTipo() {
									var radio = document.forms["<c:out value="${mappingGestionValoracion.name}" />"].tipo;
									if (radio[0].checked) {
										xDisplay('divManual', 'none');
										xDisplay('divBuscador', 'block');
										xDisplay('divResultados', 'block');
									} else {
										xDisplay('divManual', 'block');
										xDisplay('divBuscador', 'none');
										xDisplay('divResultados', 'none');
									}
								}
							</script>
							<table>
								<tr>
									<td><input type="radio" name="tipo"
										onclick="javascript:checkTipo()"
										class="radio" value="1"
										<c:if test="${empty param.tipo || param.tipo==1}">checked="true"</c:if>
										/></td>
									<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.valoracion.incorporarSerieBuscador"/>&nbsp;</td>
									<td><input type="radio" name="tipo"
										onclick="javascript:checkTipo()"
										class="radio" value="2"
										<c:if test="${param.tipo==2}">checked="true"</c:if>
										/></td>
									<td class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.valoracion.incorporarSerieManualmente"/>&nbsp;</td>
								</tr>
							</table>
						</TD>
					</TR>
					</table>

					<div id="divManual" style="display:none;">
						<table class="formulario">
							<tr>
								<td class="tdTitulo" width="150px"><bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;</td>
								<td class="tdDatos">
									<textarea name="nombreSerie"><c:out value="${param.nombreSerie}" /></textarea>
								</td>
							</tr>
						</table>
					</div>
				</c:if>

				<div id="divBuscador"
					<c:if test="${tipoSerie == appConstants.valoracion.TIPO_SERIE_RELACIONADAS}">
						style="display:none;"
					</c:if>
					>
					<c:if test="${tipoSerie == appConstants.valoracion.TIPO_SERIE_RELACIONADAS}">
						<TABLE cellpadding=0 cellspacing=0 class="w98">
						<TR>
							<TD width="50%" class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.buscarPor"/>:&nbsp;
							</TD>
							<TD width="30%" align="right">
							<TABLE cellpadding=0 cellspacing=0>
							  <TR>
								<TD>
									<script>
										function buscarSeries() {
											var form = document.forms['<c:out value="${mappingGestionValoracion.name}" />'];
											form.method.value = 'buscarSerieEdicion';
											form.submit();
										}
									</script>
									<a class="etiquetaAzul12Normal" href="javascript:buscarSeries()">
										<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
										<bean:message key="archigest.archivo.buscar"/>
									</a>
								</TD>
							 </TR>
							</TABLE>
							</TD>
							<TD>&nbsp;</TD>
						</TR>
						</TABLE>
					</c:if>

					<table class="formulario">
						<tr>
							<td class="tdTitulo" width="150px"><bean:message key="archigest.archivo.cf.fondo"/>:&nbsp;</td>
							<td class="tdDatos">
								<c:set var="listaFondos" value="${sessionScope[appConstants.valoracion.LISTA_FONDOS_KEY]}" />
								<c:set var="idFondo" value="${requestScope['fondo']}"/>
								<c:choose>
									<c:when test="${!empty listaFondos and empty listaFondos[1]}">
										<c:out value="${listaFondos[0].label}" />
										<input type="hidden" name="fondo" value="<c:out value="${listaFondos[0].id}" />"/>
									</c:when>
									<c:otherwise>
										<select name="fondo">
											<option value=""> -- <bean:message key="archigest.archivo.cf.fondosDocumentales"/>--</option>
											<c:forEach var="fondo" items="${listaFondos}">
												<option value="<c:out value="${fondo.id}" />" <c:if test="${fondo.id == param.fondo || fondo.id == idFondo}">selected</c:if>> <c:out value="${fondo.label}" /></option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.cf.codigoSerie"/>:&nbsp;
							</td>
							<td class="tdDatos"><input type="text" size="30" name="codigo" value="<c:out value="${param.codigo}" />"></td>
						</tr>
						<tr>
							<td class="tdTitulo">
								<bean:message key="archigest.archivo.cf.tituloSerie"/>:&nbsp;
							</td>
							<td class="tdDatos"><input type="text" class="input80" name="tituloBuscar" value="<c:out value="${param.tituloBuscar}" />"></td>
						</tr>
					</table>
				</div>
				<c:if test="${tipoSerie == appConstants.valoracion.TIPO_SERIE_RELACIONADAS}">
					<script>checkTipo();</script>
				</c:if>
			</tiles:put>
		</tiles:insert>

		<c:if test="${listaSeries != null}">

			<%-- Display con las series a seleccionar --%>
			<div class="separador5"></div>

			<div id="divResultados" style="display:block;">
				<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockTitle" direct="true">
						<bean:message key="archigest.archivo.resultadosBusqueda"/>
					</tiles:put>

					<tiles:put name="blockContent" direct="true">
						<div style="padding-top:6px; padding-bottom:6px">
							<c:set var="LISTA_SERIES_KEY" value="pageScope.listaSeries"/>
							<jsp:useBean id="LISTA_SERIES_KEY" type="java.lang.String" />
							<display:table name="<%=LISTA_SERIES_KEY%>"
								id="serie"
								export="false"
								style="width:98%;margin-left:auto;margin-right:auto">
								<display:column style="width:15px">
									<input type="checkbox" name="serieIDs" value="<c:out value="${serie.id}" />" >
								</display:column>
								<display:column titleKey="archigest.archivo.codigo" property="codigo" />
								<display:column titleKey="archigest.archivo.series.titulo" property="titulo" />
								<display:column titleKey="archigest.archivo.estado">
									<fmt:message key="archigest.archivo.cf.estadoSerie.${serie.estadoserie}" />
								</display:column>
								<display:column titleKey="archigest.archivo.fondo">
									<c:out value="${serie.fondo.titulo}" />
								</display:column>
								<display:column titleKey="archigest.archivo.gestor">
									<c:out value="${serie.gestor.nombreCompleto}" />
								</display:column>
							</display:table>
						</div>
					</tiles:put>
				</tiles:insert>
			</div>
		</c:if>
		<div style="display:none;"></html:form></div>
	</tiles:put>
</tiles:insert>