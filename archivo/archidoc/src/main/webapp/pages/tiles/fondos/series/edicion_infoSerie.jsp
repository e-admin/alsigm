<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="vPadreElementoCF" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}"/>
<c:set var="ancestros" value="${sessionScope[appConstants.fondos.JERARQUIA_ELEMENTO_CUADRO_CLASIFICACION]}"/>
<bean:struts id="mappingGestionSeries" mapping="/gestionSeries" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.editarSerie"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<a class="etiquetaAzul12Bold" href="javascript:enableChecks();javascript:document.forms['<c:out value="${mappingGestionSeries.name}" />'].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURI" value="/action/gestionSeries">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

	<html:form action="/gestionSeries">
		<input type="hidden" name="method" value="guardarInfoSerie">
		<html:hidden property="idSerie" />

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockContent" direct="true">

		<TABLE class="formulario" cellpadding="0" cellspacing="0">
			<tr>
				<td width="200px" class="tdTitulo" >
					<bean:message key="archigest.archivo.cf.codReferenciaPadre"/>:
				</td>
				<td class="tdDatosBold" >
					<c:out value="${vPadreElementoCF.codReferencia}"/>
				</td>
			</tr>
			<TR>
				<td class="tdTitulo" style="vertical-align:top;">
					<bean:message key="archigest.archivo.cf.contextoSerie"/>:&nbsp;
				</td>

				<td class="tdDatos" >
					<TABLE cellpadding=0 cellspacing=0>
					<c:set var="numeroAncestors" value="0"/>
					<c:forEach var="aAncestor" items="${ancestros}" varStatus="nivel">
						<c:set var="numeroAncestors" value="${numeroAncestors+1}"/>
						<TR>
							 <TD style="vertical-align:top;">
								<span style="padding-left:<c:out value="${(nivel.count-1)*10}px"/>;" class="user_data">
		  						    <c:choose>
										<c:when test="${nivel.first}">
											<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="6px"/>
											<html:img page="/pages/images/padre.gif" styleClass="imgTextMiddle"/>
									    </c:when>
									  	<c:otherwise>
											<html:img page="/pages/images/descendiente.gif" styleClass="imgTextMiddle"/>
										</c:otherwise>
									</c:choose>
									<c:out value="${aAncestor.codigo}" />&nbsp;<c:out value="${aAncestor.titulo}" />
								</span>
							</TD>
						 </TR>
					</c:forEach>
					<TR>
						 <TD style="vertical-align:top;">
								<span style="padding-left:<c:out value="${(numeroAncestors)*10}px"/>;" class="user_data">
								<html:img page="/pages/images/descendiente_last.gif" styleClass="imgTextMiddle"/>
								<c:out value="${vPadreElementoCF.codigo}" />&nbsp;<c:out value="${vPadreElementoCF.titulo}" />
							</span>
						</TD>
					</TR>
					</TABLE>
				</TD>
			</TR>

		</TABLE>
		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<div>
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.cf.datosAltaSerie"/></tiles:put>
		<tiles:put name="blockContent" direct="true">

			<c:set var="listaFichasCLDocumentales" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_CL_DOCUMENTALES_KEY]}" />
			<c:set var="listaNivelesDocumentales" value="${sessionScope[appConstants.fondos.LISTA_NIVELES_DOCUMENTALES_KEY]}" />
			<c:set var="listasControlAcceso" value="${requestScope[appConstants.fondos.LISTAS_CONTROL_ACCESO_KEY]}"/>
			<c:set var="fichasUdocs" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_PARA_UDOCS_KEY]}" />
			<TABLE class="formulario" cellpadding="0" cellspacing="0">
				<TR>
					<td class="tdTitulo" colspan="3">
						<bean:message key="archigest.archivo.cf.serie"/>
					</td>
				</TR>
				<TR>
					<TD colspan="3">
						<TABLE class="formulario" cellpadding="0" cellspacing="0" width="100%">
							<c:if test="${appConstants.configConstants.mostrarCampoOrdenacionCuadro}">
								<tr>
									<TD width="20px">&nbsp;</TD>
									<td width="300px" class="tdTitulo">
										<bean:message key="archigest.archivo.cf.codigoOrdenacion"/>:&nbsp;
									</td>
									<td class="tdDatosBold">
										<html:text property="codOrdenacion" maxlength="32" size="34"/>
									</td>
								</tr>
							</c:if>
							<tr>
								<TD width="20px">&nbsp;</TD>
								<td class="tdTitulo" width="300px">
									<bean:message key="archigest.archivo.cf.fichaDescAsociada"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<c:set var="fichasSerie" value="${sessionScope[appConstants.fondos.LISTA_FICHAS_PARA_SERIES_KEY]}" />
									<c:if test="${!empty fichasSerie}">
										<html:select property="idFichaDescriptivaSerie" disabled="true">
											<html:options collection="fichasSerie" property="id" labelProperty="nombre" />
										</html:select>
										<html:hidden property="idFichaDescriptivaSerie"/>
									</c:if>
								</td>
							</tr>
							<tr>
								<TD>&nbsp;</TD>
								<td class="tdTitulo">
									<bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;
								</td>
								<td class="tdDatos">

									<bean:define id="nombreCampo" value="idRepEcmSerie" toScope="request"/>
									<tiles:insert name="lista.repositorios.ecm" flush="true"/>
								</td>
							</tr>
							<tr>
								<TD>&nbsp;</TD>
								<td class="tdTitulo" width="250px">
									<bean:message key="archigest.archivo.nivelAcceso"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<script>
										function checkListaControlAcceso()
										{
											var form = document.forms["<c:out value="${mappingGestionSeries.name}" />"];
											if (form.nivelAcceso.value == 1)
												form.idLCA.disabled = true;
											else
												form.idLCA.disabled = false;
										}
									</script>
									<html:select property="nivelAcceso" onchange="javascript:checkListaControlAcceso()">
										<html:option key="archivo.nivel_acceso.publico" value="1"/>
										<html:option key="archivo.nivel_acceso.archivo" value="2"/>
										<html:option key="archivo.nivel_acceso.restringido" value="3"/>
									</html:select>
								</td>
							</tr>
							<tr id="trListaControlAcceso">
								<TD>&nbsp;</TD>
								<td class="tdTitulo">
									<bean:message key="archigest.archivo.listaControlAcceso"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<select style="width:100%;" name="idLCA" <c:if test="${SerieForm.nivelAcceso == 1}">disabled="true"</c:if>>
										<option value="">&nbsp;</option>
										<c:forEach var="lca" items="${listasControlAcceso}">
											<option value="<c:out value="${lca.id}"/>"
												<c:if test="${lca.id==SerieForm.idLCA}">selected="true"</c:if>
												>
												<c:out value="${lca.nombre}"/>
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</TABLE>
					</TD>
				</TR>

				<bean:define id="listaNivelesDocumentales" name="listaNivelesDocumentales" toScope="request"/>
				<bean:define id="listaFichasCLDocumentales" name="listaFichasCLDocumentales" toScope="request"/>
				<bean:define id="fichasUdocs" name="fichasUdocs" toScope="request"/>
				<c:set var="infoUDocsSerie" value="${sessionScope[appConstants.fondos.NIVELES_FICHA_UDOC_REP_ECM]}" />
				<c:if test="${empty infoUDocsSerie}">
					<c:set var="infoUDocsSerie" value="${sessionScope[appConstants.fondos.INFO_UDOCS_SERIE]}"/>
				</c:if>
				<c:if test="${!empty infoUDocsSerie}">
					<bean:define id="infoUDocsSerie" name="infoUDocsSerie" toScope="request"/>
				</c:if>
				<tiles:insert page="/pages/tiles/fondos/series/info_ficha_udoc_rep_ecm.jsp" flush="true"/>
			</TABLE>
			<div class="separador5">&nbsp;</div>
		</tiles:put>
		</tiles:insert>
		</div>
	</html:form>

	</tiles:put>
</tiles:insert>