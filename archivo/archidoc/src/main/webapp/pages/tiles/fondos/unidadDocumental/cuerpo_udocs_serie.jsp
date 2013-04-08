<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="mostrarSinProductor" value="${sessionScope[appConstants.fondos.MOSTRAR_SIN_PRODUCTOR]}"/>
<c:set var="productores" value="${sessionScope[appConstants.fondos.LISTA_PRODUCTORES_KEY]}"/>
<c:set var="vListaUnidadesDocumentales"	value="${sessionScope[appConstants.fondos.LISTA_UNIDADES_DOCUMENTALES]}"/>

<security:permissions action="${appConstants.documentosActions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION_ACTION}">
	<c:set var="mostrarLinkDocumentos" value="true"/>
	<tiles:insert page="/pages/tiles/documentos/script_funciones_acceso_documentos.jsp"></tiles:insert>
</security:permissions>

<script language="javascript" type="text/javascript">

function generateInformeUdocsSerie(){
	var frm = document.getElementById("formulario");
	document.getElementById("method").value="generateInformeUdocsSerie";
	frm.submit();
}

function ejecutar(){
	var frm = document.getElementById("formulario");
	frm.submit();
}

function desplegarProductor(posicion) {
	switchVisibility("productorNoDesplegado"+posicion);
	switchVisibility("productorDesplegado"+posicion);
}

</script>

<html:form action="/gestionUdocsCF" styleId="formulario">
<input type="hidden" name="method" id="method" value="filtrarUdocs"/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.unidadesDocumentales"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD>
				<tiles:insert definition="button.closeButton" />
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
					<c:out value="${serie.codigo}" /> <c:out value="${serie.titulo}" />
				</td>
			</tr>
		</table>
		</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>


		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
			<bean:message key="archigest.archivo.cf.uDocsSerie"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
					<c:url var="busquedaContenidoSerieURL" value="/action/buscarElementos">
						<c:param name="method" value="formBusquedaUDocsSerie" />
						<c:param name="idSerie" value="${serie.id}" />
					</c:url>
			   		<a class="etiquetaAzul12Bold"
					   href="<c:out value="${busquedaContenidoSerieURL}" escapeXml="false"/>" >
					   <html:img page="/pages/images/buscar_go.gif"
							altKey="NavigationTitle_CUADRO_FILTRO_CONTENIDO_SERIE"
					        titleKey="NavigationTitle_CUADRO_FILTRO_CONTENIDO_SERIE"
							styleClass="imgTextMiddle"/>&nbsp;<bean:message key="NavigationTitle_CUADRO_FILTRO_CONTENIDO_SERIE"/></a>
					&nbsp;
					<security:permissions action="${appConstants.fondosActions.MODIFICAR_ELEMENTO_ACTION}">
						<c:url var="moverUdocsURL" value="/action/moverUdocsAction">
							<c:param name="method" value="initFormularioBuscarUdocsAMover" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${moverUdocsURL}" escapeXml="false"/>" >
							<html:img page="/pages/images/treeMover.gif"
							altKey="archigest.archivo.cf.mover" titleKey="archigest.archivo.cf.mover"
							styleClass="imgTextBottom" />
							<fmt:message key="archigest.archivo.cf.mover"/>&nbsp;
						</a>
					</security:permissions>
			</tiles:put>


			<tiles:put name="blockContent" direct="true">

				<div class="separador5">&nbsp;</div>



				<display:table
					name="pageScope.vListaUnidadesDocumentales"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="unidadDocumental"
					pagesize="15"
					sort="list"
					requestURI="../../action/gestionUdocsCF"
					export="true">

					<display:footer>
						<div class="separador8">&nbsp;</div>
						<div style="text-align:right">
							<span class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.cf.productor"/>:&nbsp;
							</span>
							<html:select property="productor" styleId="productor">
								<html:option value="0"><bean:message key="archigest.archivo.udoc.productor.todos"/></html:option>
								<c:if test="${not empty mostrarSinProductor and mostrarSinProductor=='S'}">
								<html:option value="1"><bean:message key="archigest.archivo.udoc.sin.productor"/></html:option>
								</c:if>
								<html:optionsCollection name="productores" label="value" value="key"/>
							</html:select>
							<a href="javascript:ejecutar()" class="etiquetaAzul12Bold">
							  <html:img page="/pages/images/go.gif"
		                              altKey="archigest.archivo.busquedas.operacion.accion.ejecutar"
		                              titleKey="archigest.archivo.busquedas.operacion.accion.ejecutar"
		                              styleClass="imgTextMiddle" />
							</a>&nbsp;&nbsp;
						</div>
						<div class="separador8">&nbsp;</div>
					</display:footer>

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.noUDocsSerie"/>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
						<c:url var="verUdocURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verUnidadDocumentalWithPermissions" />
							<c:param name="id" value="${unidadDocumental.id}" />
						</c:url>
						<a href="<c:out value="${verUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${unidadDocumental.codigo}" />
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.codigo" media="csv excel xml pdf">
						<c:out value="${unidadDocumental.codigo}" />
					</display:column>
					<display:column titleKey="archigest.archivo.num.exp" property="numExp" sortable="true" headerClass="sortable"/>
					<display:column titleKey="archigest.archivo.cf.denominacion" property="titulo" sortable="true" headerClass="sortable"/>
					<display:column titleKey="archigest.archivo.cf.nivel" property="nombreNivel" sortable="true" headerClass="sortable" style="width:50px"/>
					<display:column property="fechaInicial" format="{0,date,dd/MM/yyyy}" titleKey="archigest.archivo.busqueda.form.fecha.inicial" style="width:30px" media="csv excel xml pdf html"/>
					<display:column property="fechaFinal" format="{0,date,dd/MM/yyyy}" titleKey="archigest.archivo.busqueda.form.fecha.final" style="width:30px" media="csv excel xml pdf html"/>

					<display:column titleKey="archigest.archivo.cf.productor" style="width:100px" media="html">
						<c:choose>

						<c:when test="${not empty unidadDocumental.nombreProductor && unidadDocumental.nombreProductor != unidadDocumental.nombreCortoProductor}">
							<c:set var="position" value="${unidadDocumental_rowNum - 1}" />
							<c:set var="productorNoDesplegado" value="productorNoDesplegado${position}" />
							<c:set var="productorDesplegado" value="productorDesplegado${position}" />

							<div id="<c:out value="${productorNoDesplegado}"/>">
								<a href="javascript:desplegarProductor(<c:out value="${position}"/>)">
									<html:img page="/pages/images/plus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${unidadDocumental.nombreCortoProductor}" />
							</div>

							<div id="<c:out value="${productorDesplegado}"/>" style="display:none">
								<a href="javascript:desplegarProductor(<c:out value="${position}"/>)">
									<html:img page="/pages/images/minus.gif" styleClass="imgTextMiddle" /></a>
									<c:out value="${unidadDocumental.nombreProductor}" />
							</div>
						</c:when>


						<c:otherwise>
							<c:out value="${unidadDocumental.nombreProductor}" />
						</c:otherwise>
						</c:choose>
					</display:column>

					<display:column titleKey="archigest.archivo.cf.productor" style="width:100px" media="csv excel xml pdf">
							<c:out value="${unidadDocumental.nombreProductor}" />
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.interesados" style="width:200px" media="html">

						<c:forEach  items="${unidadDocumental.interesados}" var="interesado">

							<c:out value="${interesado.nombre}"/>
								<c:if test="${interesado.principal}">
									<html:img page="/pages/images/man.gif"
									altKey="archigest.archivo.transferencias.interesadoPrincipal" titleKey="archigest.archivo.transferencias.interesadoPrincipal"
									styleClass="imgTextBottom" />
								</c:if>
						<br/>
						</c:forEach>

					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.interesados" media="csv excel xml pdf">
						<c:forEach  items="${unidadDocumental.interesados}" var="interesado">
							<c:out value="${interesado.nombre}"/><c:if test="${interesado.principal}">(*)</c:if>/
						</c:forEach>

					</display:column>

					<display:column media="html" titleKey="archigest.archivo.cf.conservada.abreviado" style="text-align:center">
						<c:if test="${! empty unidadDocumental.idEliminacion}">
							<html:img page="/pages/images/udoc/conservada.gif"
							        altKey="archigest.archivo.cf.conservada"
							        titleKey="archigest.archivo.cf.conservada"
							        styleClass="imgTextMiddle"/>
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.cf.conservada.abreviado" media="csv excel xml pdf">
						<c:if test="${!empty unidadDocumental.idEliminacion}">
							<bean:message key="archigest.archivo.si" />
						</c:if>
					</display:column>

						<display:column style="text-align: center;"  titleKey="archigest.archivo.cf.bloqueada.abreviado" media="html">
							<c:choose>
								<c:when test="${unidadDocumental.marcasBloqueo>0}" >
									<html:img
										page="/pages/images/udocBloqueada.gif"
										titleKey="archigest.archivo.bloqueada"
										altKey="archigest.archivo.bloqueada"
										styleClass="imgTextBottom" />
								</c:when>
								<c:otherwise>
									<html:img
										page="/pages/images/udocDesbloqueada.gif"
										titleKey="archigest.archivo.desbloqueada"
										altKey="archigest.archivo.desbloqueada"
										styleClass="imgTextBottom" />
								</c:otherwise>
							</c:choose>
						</display:column>
						<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
							<c:choose>
								<c:when test="${unidadDocumental.marcasBloqueo>0}" >
									<bean:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</display:column>
					<c:if test="${mostrarLinkDocumentos}">
						<display:column titleKey="archigest.archivo.columna.documentos" sortable="true" headerClass="sortable" media="html">
							<tiles:insert page="/pages/tiles/documentos/columna_acceso_documentos.jsp" >
								<tiles:put name="idElemento" direct="true"><c:out value="${unidadDocumental.id}"/></tiles:put>
								<tiles:put name="numDocumentos"><c:out value="${unidadDocumental.numDocumentosElectronicos}"/></tiles:put>
							</tiles:insert>
						</display:column>
					</c:if>
				</display:table>

				<div class="separador5">&nbsp;</div>

			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>