<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="listaDescriptorPrincipal" value="${sessionScope[appConstants.descripcion.LISTA_DESCRIPTOR_PRINCIPAL]}"/>
<c:set var="descriptoresAUnificar" value="${sessionScope[appConstants.descripcion.LISTA_DESCRIPTORES_A_UNIFICAR]}"/>

<c:set var="notIsPostBack" value="${requestScope[appConstants.descripcion.NOT_IS_POSTBACK]}"/>

<script language="javascript">
	function inicio(){
		document.getElementById("method").value = "inicio";
		document.getElementById("formulario").submit();
	}
</script>
<html:form action="unificarDescriptores" styleId="formulario">

<tiles:definition id="idDescriptorPrincipal" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<div class="separador5">&nbsp;</div>
		<fmt:message key="archigest.archivo.descripcion.informe.unificar.descriptor.final"/>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
				<div class="separador8">&nbsp;</div>
				<display:table name="pageScope.listaDescriptorPrincipal"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="descriptor" >
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.listaVacia"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre"   media="html">
						<c:url var="URL" value="/action/descriptor">
							<c:param name="method" value="retrieveDescriptor" />
							<c:param name="id" value="${descriptor.id}" />
							<c:param name="idLista" value="${descriptor.idLista}" />
							<c:param name="tipo" value="${listaDescriptoraForm.tipoDescriptor}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${descriptor.nombre}"/></a>
					</display:column>
					<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.estado" sortProperty="estado"   style="width:80px;">
						<c:choose>
							<c:when test="${descriptor.estado == 1}">
								<bean:message key="archivo.estado.validacion.validado"/>
							</c:when>
							<c:when test="${descriptor.estado == 2}">
								<bean:message key="archivo.estado.validacion.no_validado"/>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescr"   style="width:100px;"/>
					<display:column titleKey="archigest.archivo.descripcion.descrito" sortProperty="tieneDescr"   style="width:50px;" media="html">
							<c:if test="${descriptor.conDescripcion == true}">
							<center>
								<html:img page="/pages/images/checkbox-yes.gif"
									   border="0"
									   altKey="archigest.archivo.si"
									   titleKey="archigest.archivo.si"
									   styleClass="imgTextMiddle"/>
							</center>
							</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.descripcion.descrito" property="tieneDescr" style="width:50px;" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcm" sortProperty="volumen"   style="width:100px;"/>
					<display:column titleKey="archigest.archivo.transferencias.documentos"  style="width:50px;" media="html">
						<c:if test="${descriptor.conDocumentos == true}">
								<center><html:img page="/pages/images/docsElectronicos.gif"
									   border="0"
									   altKey="archigest.archivo.si"
									   titleKey="archigest.archivo.si"
									   styleClass="imgTextMiddle"/>
								</center>
						</c:if>
					</display:column>
				</display:table>
			<div class="separador8">&nbsp;</div>
   </tiles:put>
</tiles:definition>

<tiles:definition id="idDescriptoresAUnificar" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<div class="separador5">&nbsp;</div>
		<fmt:message key="archigest.archivo.descripcion.informe.unificar.descriptores.reemplazados"/>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
	<div class="separador8">&nbsp;</div>
			<display:table name="pageScope.descriptoresAUnificar"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="descriptor">
					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre"  media="html">
						<c:url var="URL" value="/action/descriptor">
							<c:param name="method" value="retrieveDescriptor" />
							<c:param name="id" value="${descriptor.id}" />
							<c:param name="idLista" value="${descriptor.idLista}" />
							<c:param name="tipo" value="${listaDescriptoraForm.tipoDescriptor}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${descriptor.nombre}"/></a>
					</display:column>
					<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.estado" sortProperty="estado"  style="width:80px;">
						<c:choose>
							<c:when test="${descriptor.estado == 1}">
								<bean:message key="archivo.estado.validacion.validado"/>
							</c:when>
							<c:when test="${descriptor.estado == 2}">
								<bean:message key="archivo.estado.validacion.no_validado"/>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescr"  style="width:100px;"/>
					<display:column titleKey="archigest.archivo.descripcion.descrito" sortProperty="tieneDescr"   style="width:50px;" media="html">
							<c:if test="${descriptor.conDescripcion == true}">
							<center>
								<html:img page="/pages/images/checkbox-yes.gif"
									   border="0"
									   altKey="archigest.archivo.si"
									   titleKey="archigest.archivo.si"
									   styleClass="imgTextMiddle"/>
							</center>
							</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.descripcion.descrito" property="tieneDescr" style="width:50px;" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcm" sortProperty="volumen"  style="width:100px;"/>
					<display:column titleKey="archigest.archivo.transferencias.documentos"  style="width:50px;" media="html">
						<c:if test="${descriptor.conDocumentos == true}">
								<center><html:img page="/pages/images/docsElectronicos.gif"
									   border="0"
									   altKey="archigest.archivo.si"
									   titleKey="archigest.archivo.si"
									   styleClass="imgTextMiddle"/>
								</center>
						</c:if>
					</display:column>
				</display:table>
				<div class="separador8">&nbsp;</div>
   </tiles:put>
</tiles:definition>


<!-- COMPOSICION DE PAGINA A MOSTRAR -->
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.descripcion.informe.unificar.titulo"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
		<td>
			<c:url var="URL" value="/action/informeUnificarDescriptores"/>
				<a class="etiquetaAzul12Bold" href="<c:out value="${URL}" escapeXml="false"/>">
					<html:img page="/pages/images/documentos/doc_pdf.gif"
					        border="0"
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
						    styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
			</td>
		<td width="10">&nbsp;</td>
			<td>
				<tiles:insert definition="button.closeButton" flush="true">
					<tiles:put name="labelKey" direct="true">archigest.archivo.cerrar</tiles:put>
					<tiles:put name="imgIcon" direct="true">/pages/images/close.gif</tiles:put>
					<tiles:put name="action" direct="true">goBackTwice</tiles:put>
				</tiles:insert>
			</td>
			<td width="10">&nbsp;</td>

		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
			<html:hidden property="method" styleId="method" value="unificar"/>
			<html:hidden property="idPrincipal"/>
			<div class="separador8">&nbsp;</div>
			<div class="separador8">&nbsp;</div>
			<tiles:insert beanName="idDescriptorPrincipal" />
			<div class="separador8">&nbsp;</div>
			<div class="separador8">&nbsp;</div>
			<tiles:insert beanName="idDescriptoresAUnificar" />
			<div class="separador8">&nbsp;</div>
	</tiles:put>
</tiles:insert>
</html:form>