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

<c:set var="listaDescrConDocumentos" value="${sessionScope[appConstants.descripcion.LISTA_DESCRIPTORES_CON_DOCUMENTOS]}"/>
<c:set var="listaDescrConFicha" value="${sessionScope[appConstants.descripcion.LISTA_DESCRIPTORES_CON_FICHA]}"/>

<c:set var="notIsPostBack" value="${requestScope[appConstants.descripcion.NOT_IS_POSTBACK]}"/>

<script language="javascript">
	function unificar(){
		var texto = "";

		<c:if test="${not empty listaDescrConDocumentos || not empty listaDescrConFicha}">
		texto='<bean:message key="archigest.archivo.descripcion.descriptores.unificar.warning.confirm"/>';
			<c:if test="${not empty listaDescrConDocumentos}">
				texto+='\n\n\t<bean:message key="archigest.archivo.descripcion.descriptores.unificar.warning.confirm.con.docs"/>';
			</c:if>
		<c:forEach var="descriptor" items="${listaDescrConDocumentos}">
			texto+='\n\t\t- <c:out value="${descriptor.nombre}"/>';
		</c:forEach>
		<c:if test="${not empty listaDescrConFicha}">
			texto+='\n\n\t<bean:message key="archigest.archivo.descripcion.descriptores.unificar.warning.confirm.con.descripcion"/>';
		</c:if>
		<c:forEach var="descriptor" items="${listaDescrConFicha}">
			texto+='\n\t\t- <c:out value="${descriptor.nombre}"/>';
		</c:forEach>

		if(texto != ""){
			texto+="\n\n";
		}
		</c:if>
		if(window.confirm(texto +'<fmt:message key="archigest.archivo.unificar.descriptores.msg"/>')){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
				var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
				window.top.showWorkingDiv(title, message);
			}


			document.getElementById("formulario").submit();
		}
	}

	function anterior(){
		document.getElementById("method").value = "atras";
		document.getElementById("formulario").submit();
	}
</script>
<html:form action="unificarDescriptores" styleId="formulario">

<tiles:definition id="idDescriptorPrincipal" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<div class="separador5">&nbsp;</div>
		<fmt:message key="archigest.archivo.descripcion.descriptores.lista.a.mantener"/>
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
		<fmt:message key="archigest.archivo.descripcion.descriptores.lista.a.unificar"/>
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
		<bean:message key="NavigationTitle_DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR_CONFIRMAR"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
		<td>
				<a class="etiquetaAzul12Bold" href="javascript:anterior()">
					<html:img page="/pages/images/Previous.gif"
				        altKey="wizard.atras"
				        titleKey="wizard.atras"
				        styleClass="imgTextMiddle"/>
					<bean:message key="wizard.atras"/>
				</a>
			</td>

		<td width="10">&nbsp;</td>
		<td>
			<a class="etiquetaAzul12Bold" href="javascript:unificar()">
					<html:img page="/pages/images/Ok_Si.gif"
				        altKey="archigest.archivo.aceptar"
				        titleKey="archigest.archivo.aceptar"
				        styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.aceptar"/>
			</a>
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
			<c:if test="${(not empty listaDescriptorPrincipal)}">
				<div class="separador8">&nbsp;</div>
				<tiles:insert beanName="idDescriptorPrincipal" />
				<div class="separador8">&nbsp;</div>
			</c:if>

			<c:if test="${(not empty descriptoresAUnificar)}">
				<div class="separador8">&nbsp;</div>
				<tiles:insert beanName="idDescriptoresAUnificar" />
				<div class="separador8">&nbsp;</div>
			</c:if>
	</tiles:put>
</tiles:insert>
</html:form>