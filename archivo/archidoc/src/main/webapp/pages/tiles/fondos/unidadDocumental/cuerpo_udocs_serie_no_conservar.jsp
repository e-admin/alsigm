<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="serie" value="${sessionScope[appConstants.fondos.ELEMENTO_CF_KEY]}" />
<c:set var="vListaUnidadesDocumentales"	value="${sessionScope[appConstants.fondos.LISTA_UNIDADES_DOCUMENTALES]}"/>



<script language="javascript" type="text/javascript">

function generateInformeUdocsSerie(){
	var frm = document.getElementById("formulario");
	document.getElementById("method").value="generateInformeUdocsSerie";
	frm.submit();
}

function ejecutar(){
	if(window.confirm("<bean:message key="archigest.archivo.unidadesDocumentales.no.conservar.warning"/>")){
		var frm = document.getElementById("formulario");
		frm.submit();
	}
}

function desplegarProductor(posicion) {
	switchVisibility("productorNoDesplegado"+posicion);
	switchVisibility("productorDesplegado"+posicion);
}

</script>

<html:form action="/gestionUdocsCF" styleId="formulario">
<input type="hidden" name="method" id="method" value="ejecutarAccionNoConservarUdocs"/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.unidadesDocumentales.no.conservar"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <tr>
				<c:if test="${not empty vListaUnidadesDocumentales}">
				<td>
					<a class="etiquetaAzul12Bold"
						href="javascript:ejecutar()" >
						<html:img page="/pages/images/Ok_Si.gif"
							altKey="archigest.archivo.aceptar"
							titleKey="archigest.archivo.aceptar"
							styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</td>
			   <td width="10">&nbsp;</td>
			   </c:if>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true">
						<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
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
			<bean:message key="archigest.archivo.cf.uDocsSerie.conservadas"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">

			</tiles:put>


			<tiles:put name="blockContent" direct="true">

				<div class="separador5">&nbsp;</div>
				<display:table
					name="pageScope.vListaUnidadesDocumentales"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="unidadDocumental"
					sort="list"
					requestURI="../../action/gestionUdocsCF"
					export="true">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.uDocsSerie.sin.conservadas"/>
					</display:setProperty>

					<display:column media="html" style="width:25px">
						<html-el:multibox style="border:0" property="ids" value="${unidadDocumental.id}"/>
					</display:column>

					<display:column titleKey="archigest.archivo.codigo" sortProperty="codigo" property="codigo" sortable="true" headerClass="sortable"/>
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

					<display:column titleKey="archigest.archivo.transferencias.interesados" media="csv excel xml pdf">
						<c:forEach  items="${unidadDocumental.interesados}" var="interesado" varStatus="pos">
							-<c:if test="${interesado.principal}">(*)</c:if><c:out value="${interesado.nombre}"/>
							<c:if test="${not pos.last}">/</c:if>
						</c:forEach>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.interesados" media="html">
						<c:forEach  items="${unidadDocumental.interesados}" var="interesado" varStatus="pos">
							-<c:if test="${interesado.principal}">
								<html:img page="/pages/images/man.gif"
								altKey="archigest.archivo.transferencias.interesadoPrincipal" titleKey="archigest.archivo.transferencias.interesadoPrincipal"
									styleClass="imgTextBottom" />
							</c:if><c:out value="${interesado.nombre}"/>
								<c:if test="${not pos.last}">
									<br/>
								</c:if>
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
					<display:column titleKey="archigest.archivo.cf.conservada" style="width:30px" media="csv excel xml pdf">
						<c:if test="${!empty elemento.idEliminacion}">
							<bean:message key="archigest.archivo.si" />
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
						<c:choose>
							<c:when test="${elemento.marcasBloqueo>0}" >
								<bean:message key="archigest.archivo.si" />
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.no" />
							</c:otherwise>
						</c:choose>
					</display:column>

				</display:table>

				<div class="separador5">&nbsp;</div>

			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>

