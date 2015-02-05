<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mapping" mapping="/descriptor" />

<c:set var="listasDescriptoras" value="${requestScope[appConstants.descripcion.LISTAS_DESCRIPTORAS_KEY]}" />
<c:set var="descriptores" value="${requestScope[appConstants.descripcion.DESCRIPTORES_LISTA_KEY]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.descripcion.descriptor.busqueda"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<td nowrap>
					<script>
						function buscar() {
							var form = document.forms['<c:out value="${mapping.name}" />'];
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
					<html:img page="/pages/images/buscar.gif"
						altKey="archigest.archivo.buscar"
						titleKey="archigest.archivo.buscar"
						styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.buscar"/></a>
				</td>
				<td nowrap width="10px"></td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<html:form action="/descriptor">
		<input type="hidden" name="method" value="buscar">

		<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.descripcion.descriptor.form.nombreLista"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:select property="idLista">
								<option value="">--</option>
								<html:options collection="listasDescriptoras" property="id" labelProperty="nombre" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.descripcion.descriptor.form.nombre"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:text property="nombre" styleClass="input60"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.descripcion.descriptor.form.estado"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:select property="estado">
								<option value="0">--</option>
								<html:option key="archivo.estado.validacion.validado" value="1"/>
								<html:option key="archivo.estado.validacion.no_validado" value="2"/>
							</html:select>
						</td>
					</tr>
				</table>
		</div>
		</html:form>

		<c:if test="${descriptores != null}">
		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
			  <TR>
				<TD class="etiquetaAzul12Bold">
					<bean:message key="archigest.archivo.descripcion.descriptor.resultadoBusqueda"/>
				</TD>
			  </TR>
			</TABLE>
		</div>

		<div class="bloque">
			<display:table name="pageScope.descriptores"
					id="descriptor"
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI="/action/descriptor"
					export="true"
					pagesize="15">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.descripcion.descriptor.busquedaVacia"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
					<c:url var="URL" value="/action/descriptor">
						<c:param name="method" value="retrieveDescriptor" />
						<c:param name="id" value="${descriptor.id}" />
						<c:param name="idLista" value="${descriptor.idLista}" />
						<c:param name="tipo" value="${listaDescriptoraForm.tipoDescriptor}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${descriptor.nombre}"/></a>
				</display:column>
				<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.lista" property="nombreLista" style="width:100px;"/>
				<display:column titleKey="archigest.archivo.descripcion.descriptor.form.estado" sortProperty="estado" style="width:80px;">
					<c:choose>
						<c:when test="${descriptor.estado == 1}">
							<bean:message key="archivo.estado.validacion.validado"/>
						</c:when>
						<c:when test="${descriptor.estado == 2}">
							<bean:message key="archivo.estado.validacion.no_validado"/>
						</c:when>
						<c:otherwise>&nbsp;</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.descripcion.fechaModificacion" sortProperty="fecha" style="width:80px;">
					<bean:write name="descriptor" property="timestamp" format="yyyy/MM/dd HH:mm:ss"/>
				</display:column>
				<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescr" style="width:100px;"/>
				<display:column titleKey="archigest.archivo.descripcion.descrito" sortProperty="tieneDescr" style="width:50px;" media="html">
					<c:choose>
						<c:when test="${descriptor.tieneDescr == 'S'}">
							<html:img page="/pages/images/checkbox-yes.gif"
								   border="0"
								   altKey="archigest.archivo.si"
								   titleKey="archigest.archivo.si"
								   styleClass="imgTextMiddle"/>
						</c:when>
						<c:otherwise>
							<html:img page="/pages/images/checkbox-no.gif"
								   border="0"
								   altKey="archigest.archivo.no"
								   titleKey="archigest.archivo.no"
								   styleClass="imgTextMiddle"/>
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.descripcion.descrito" property="tieneDescr" style="width:50px;" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcm" sortProperty="volumen" style="width:100px;"/>
			</display:table>
		</div>
		</c:if>

	</tiles:put>
</tiles:insert>
