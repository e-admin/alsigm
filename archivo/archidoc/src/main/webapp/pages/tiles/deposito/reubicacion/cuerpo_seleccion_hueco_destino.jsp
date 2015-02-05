<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<c:set var="hayHuecosOcupados" value="${requestScope[appConstants.deposito.HAY_HUECOS_OCUPADOS_KEY]}" />
<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_ORIGEN_COMPACTAR_KEY]}"/>
<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_DESTINO_KEY]}" />
<c:set var="EDITABLE_NUMERACION" value="${requestScope[appConstants.deposito.EDITABLE_NUMERACION_KEY]}" />

<bean:struts id="mappingReubicacionAction" mapping="/reubicacionUdocsAction" />
<c:set var="formBean" value="${sessionScope[mappingReubicacionAction.name]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.reubicar" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.atras</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/Previous.gif</tiles:put>
				</tiles:insert>
		   	</td>
			<TD width="10">&nbsp;</TD>
			<TD>
				<script>
					function go() {
						var form = document.forms['<c:out value="${mappingReubicacionAction.name}" />'];
						if (form.numHuecoDestino && elementSelected(form.numHuecoDestino)){

							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							form.submit();
						}
						else alert("<bean:message key='archigest.archivo.deposito.esNecesarioSeleccionarAlMenosUnHueco'/>");
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:go()" >
					<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.siguiente"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>

			<TD noWrap>
				<c:url var="cancelURL" value="/action/reubicacionUdocsAction">
					<c:param name="method" value="goReturnPoint" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
				<html:img page="/pages/images/close.gif"
					altKey="archigest.archivo.cerrar"
					titleKey="archigest.archivo.cerrar"
					styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>

			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">


		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><fmt:message key="archigest.archivo.deposito.datosBaldaDestino"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<table class="formulario">
					<tr>
						<td width="100px" class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
						</td>
						<td class="tdDatosBold">
							<c:out value="${elementoAsignable.pathName}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.longitud"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.longitud}" /> <bean:message key="archigest.archivo.cm"/>.
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.formato.nombre}" />
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.deposito.huecos"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<c:out value="${elementoAsignable.numhuecos}" />
						</td>
					</tr>
				</table>
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<div class="cabecero_bloque">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
			<TR>
	<html:form action="/reubicacionUdocsAction">
	<input type="hidden" name="method" value="informeMovimiento">
			<TD class="etiquetaAzul12Bold" width="40%">
				<fmt:message key="archigest.archivo.deposito.seleccionHuecoDestino" />
			</TD>
			</TR>
		</TABLE>
	</div> <%-- cabecero bloque --%>

	<div class="bloque">
	<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
		<TABLE class="tblHuecos" style="width:98%;table-layout:auto;margin-left:5px">
		<div class="separador5">&nbsp;</div>
		<TR>
		<c:set var="listaHuecos" value="${requestScope[appConstants.deposito.LISTA_HUECOS_KEY]}" />
<c:if test="${empty listaHuecos}">
			<TD noWrap>
<fmt:message key="archigest.archivo.deposito.noExistenHuecosConUdocsConElMismoFondo"/>
			</TD>
</c:if>
		<c:forEach var="hueco" items="${listaHuecos}" varStatus="loopStatus">
			<%--SALTO de FILA --%>
			<c:if test="${loopStatus.index !=0 && loopStatus.index % 8 == 0}"></TR><TR></c:if>
			<TD class="tdTituloFichaSinBorde" noWrap>

					<c:set var="numeroHueco" value="${hueco.numorden}" />
					<jsp:useBean id="numeroHueco" type="java.lang.Integer" />
					<input type="radio" name="numHuecoDestino" value="<%=numeroHueco%>"
					<c:if test="${formBean.numHuecoDestino==numeroHueco}"> checked </c:if>
					/>

				<bean:message key="archigest.archivo.deposito.inicialHueco" />
				<b><c:out value="${hueco.numorden}" />
					<c:if test="${EDITABLE_NUMERACION && hueco.estado != appConstants.estadosHueco.OCUPADO_STATE}">
						<c:out value="${appConstants.common.SEPARADOR_NUM_HUECOS}"/> <c:out value="${hueco.numeracion}" />
					</c:if>
				</b>

				<c:choose>
					<c:when test="${hueco.estado == appConstants.estadosHueco.OCUPADO_STATE}">
						<c:url var="imgHueco" value="/pages/images/huecoAzul.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.ocupado" />
					</c:when>
					<c:when test="${hueco.estado == appConstants.estadosHueco.RESERVADO_STATE}">
						<c:url var="imgHueco" value="/pages/images/huecoRojo.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.reservado" />
					</c:when>
					<c:when test="${hueco.estado == appConstants.estadosHueco.INUTILIZADO_STATE}">
						<c:url var="imgHueco" value="/pages/images/huecoNegro.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.inutilizado" />
					</c:when>
					<c:otherwise>
						<c:url var="imgHueco" value="/pages/images/huecoBlanco.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.libre" />
					</c:otherwise>
				</c:choose>
				<img src="<c:out value="${imgHueco}" escapeXml="false"/>" alt="<fmt:message key="${altKey}" />" title="<fmt:message key="${altKey}" />"/>
				<c:if test="${hueco.estado == appConstants.estadosHueco.OCUPADO_STATE}">
					<br>
					<c:url var="urlVerUdocs" value="/action/verHuecoAction">
						<c:param name="method" value="listadoudocs"/>
						<c:param name="idHueco" value="${hueco.idElemAPadre}:${hueco.numorden}"/>
					</c:url>
					<c:out value="${hueco.signaturaUI}"/>
				</c:if>
				<c:if test="${(hueco.estado == appConstants.estadosHueco.RESERVADO_STATE)}">
					<br>
					<c:url var="urlVerRelacion" value="/action/verHuecoAction">
						<c:param name="method" value="verrelacion"/>
						<c:param name="idHueco" value="${hueco.idElemAPadre}:${hueco.numorden}"/>
					</c:url>
					<c:out value="${hueco.rentrega}"/>
				</c:if>
			</TD>
		</c:forEach>


		<c:if test="${elementoAsignable.espacioSobrante > 0}">
			<td class="sobrante">
				<bean:message key="archigest.archivo.deposito.sobrante" />:<br>
				<c:out value="${elementoAsignable.espacioSobrante}" /> <bean:message key="archigest.archivo.cm" />
			</td>
		</c:if>

		</TR>
		</TABLE>
	</div>
	</div>
	</html:form>


</tiles:put>
</tiles:insert>