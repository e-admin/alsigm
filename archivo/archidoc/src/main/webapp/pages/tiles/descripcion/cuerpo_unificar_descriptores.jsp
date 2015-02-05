<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<c:set var="listasDescriptoras" value="${sessionScope[appConstants.descripcion.LISTAS_DESCRIPTORAS_KEY]}"/>
<c:set var="mapOperadores" value="${sessionScope[appConstants.descripcion.MAP_OPERADORES_KEY]}"/>

<c:set var="descriptores" value="${sessionScope[appConstants.descripcion.DESCRIPTORES_LISTA_KEY]}"/>
<c:set var="descriptoresAUnificar" value="${sessionScope[appConstants.descripcion.LISTA_DESCRIPTORES_A_REEMPLAZAR]}"/>

<c:set var="notIsPostBack" value="${requestScope[appConstants.descripcion.NOT_IS_POSTBACK]}"/>
<c:set var="maxLeng" value="${sessionScope[appConstants.descripcion.MAX_LENGTH_KEY]}"/>
<bean:struts id="mapping" mapping="/unificarDescriptores" />

<script language="javascript">
	function siguiente(){
		document.getElementById("formulario").submit();
	}

	function add(){
		var formulario = document.forms['<c:out value="${mapping.name}" />'];
		if (formulario.posSeleccionadas && elementSelected(formulario.posSeleccionadas)) {
			document.getElementById("method").value = "addDescriptor";
			document.getElementById("formulario").submit();
		}else{
			alert("<bean:message key='error.descriptores.unificar.seleccionarAlMenosUnDescriptor'/>");
		}
	}

	function remove(){
		var formulario = document.forms['<c:out value="${mapping.name}" />'];
		if (formulario.posReemplazar && elementSelected(formulario.posReemplazar)) {
			if(window.confirm('<fmt:message key="archigest.archivo.transferencias.documentosEliminarWarning"/>')){
				document.getElementById("method").value = "removeDescriptor";
				document.getElementById("formulario").submit();
			}
		}else{
			alert("<bean:message key='error.descriptores.unificar.seleccionarAlMenosUnDescriptor'/>");
		}
	}

	function buscar(){
		document.getElementById("method").value = "busqueda";
		document.getElementById("formulario").submit();
	}

	var maxLongitud = 0;

</script>
<html:form action="unificarDescriptores" styleId="formulario">
<tiles:definition id="idBusqueda" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<div class="separador5">&nbsp;</div>
		<fmt:message key="archigest.archivo.descripcion.descriptor.busqueda"/>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:buscar()">
					<html:img page="/pages/images/buscar.gif"
				        altKey="archigest.archivo.buscar"
				        titleKey="archigest.archivo.buscar"
				        styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.buscar"/>
				</a>
			</td>
		</tr>
		</table>
	</tiles:put>
	<tiles:put name="blockContent" direct="true">
		<table class="formulario">
			<tr>
				<td class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.descripcion.descriptores.listaDescriptora"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<html-el:select property="idListaDescriptora" styleId="idListaDescriptora" style="width:${maxLeng}em">
						<html:optionsCollection name="listasDescriptoras" label="value" value="key"/>
					</html-el:select>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" >
					<div class="separador3" >&nbsp;</div>
					<bean:message key="archigest.archivo.general.filtro"/>:&nbsp;
				</td>
				<td class="tdDatos">
					<div class="separador3" >&nbsp;</div>

					<html-el:select property="calificadorFiltro" style="width:${maxLeng}em">
						<html:optionsCollection name="mapOperadores" label="value" value="key"/>
					</html-el:select>
					<html:text property="filtro" styleClass="input60"/>&nbsp;
				</td>
			</tr>
		</table>
<div class="separador8">&nbsp;</div>
   </tiles:put>
</tiles:definition>

<tiles:definition id="idDescriptores" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<div class="separador5">&nbsp;</div>
		<fmt:message key="archigest.archivo.descripcion.descriptores.lista"/>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
	<c:if test="${(not empty descriptores)}">
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:add()">
					<html:img page="/pages/images/table_add.gif"
				        altKey="archigest.archivo.anadir"
				        titleKey="archigest.archivo.anadir"
				        styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.anadir"/>
				</a>
			</td>
		</tr>
		</table>
	</tiles:put>
	</c:if>
	<tiles:put name="blockContent" direct="true">
		<div id="divContentDescriptores">
			<div class="separador8" >&nbsp;</div>

				<script language="javascript">
					var numElementos = 0;
				</script>
				<c:url var="paginationURL" value="/action/unificarDescriptores" >
					<c:param name="method" value="busqueda"/>
				</c:url>
				<jsp:useBean id="paginationURL" type="java.lang.String" />
				<display:table name="pageScope.descriptores"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="descriptor"
					sort="list"
					requestURI='<%=paginationURL%>'
					>
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.listaVacia"/>
					</display:setProperty>
					<display:column title="" headerClass="addIcon" style="width:23px;" media="html">
						<script language="javascript">
							numElementos++;
						</script>
						<html-el:multibox property="posSeleccionadas" value="${descriptor_rowNum-1}"></html-el:multibox>
					</display:column>
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
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.estado" sortProperty="estado" sortable="true" headerClass="sortable" style="width:80px;">
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
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescr" sortable="true" headerClass="sortable" style="width:100px;"/>
					<display:column titleKey="archigest.archivo.descripcion.descrito" property="tieneDescr" style="width:50px;" media="csv excel xml pdf"/>
					<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcm" sortProperty="nombreRepEcm" sortable="true" headerClass="sortable" style="width:100px;"/>
				</display:table>

				<script language="javascript">
				if(numElementos > 6){
					var capaConScroll=document.getElementById("divContentDescriptores");
					capaConScroll.className="bloqueConScroll175";
				}
				</script>

			<div class="separador8">&nbsp;</div>
		</div>
   </tiles:put>
</tiles:definition>

<tiles:definition id="idDescriptoresAUnificar" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<div class="separador5">&nbsp;</div>
		<fmt:message key="archigest.archivo.descripcion.descriptores.lista.a.unificar"/>
		<div class="separador5">&nbsp;</div>
	</tiles:put>
	<c:if test="${(not empty descriptoresAUnificar)}">
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:remove()">
					<html:img page="/pages/images/table_delete.gif"
				        altKey="archigest.archivo.quitar"
				        titleKey="archigest.archivo.quitar"
				        styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.quitar"/>
				</a>
			</td>
		</tr>
		</table>
	</tiles:put>
	</c:if>
	<tiles:put name="blockContent" direct="true">
			<div id="divContentDescriptoresAUnificar">
			<div class="separador8">&nbsp;</div>
			<c:url var="paginationURL2" value="/action/unificarDescriptores" >
				<c:param name="method" value="busqueda"/>
			</c:url>
			<jsp:useBean id="paginationURL2" type="java.lang.String" />
			<display:table name="pageScope.descriptoresAUnificar"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="descriptor"
					requestURI='<%=paginationURL2%>'>
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.descripcion.listasDescriptoras.form.listaAUnificarVacia"/>
					</display:setProperty>
					<display:column title="" headerClass="establecerNivelIcon" style="width:23px;" media="html">
						<html-el:radio property="idPrincipal" value="${descriptor.id}"></html-el:radio>
					</display:column>
					<display:column title="" headerClass="minusDocIcon" style="width:23px;" media="html">
						<html-el:multibox property="posReemplazar" value="${descriptor_rowNum-1}"></html-el:multibox>
					</display:column>
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
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.estado" sortProperty="estado" sortable="true" headerClass="sortable" style="width:80px;">
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
					<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescr" sortable="true" headerClass="sortable" style="width:100px;"/>
					<display:column titleKey="archigest.archivo.descripcion.descrito" sortProperty="tieneDescr" sortable="true" headerClass="sortable" style="width:50px;" media="html">
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
					<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcm" sortProperty="nombreRepEcm" sortable="true" headerClass="sortable" style="width:100px;"/>
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
			</div>
   </tiles:put>
</tiles:definition>


<!-- COMPOSICION DE PAGINA A MOSTRAR -->
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="NavigationTitle_DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
		<tr>
			<c:if test="${not empty descriptoresAUnificar}">
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:siguiente()">
					<html:img page="/pages/images/add.gif"
				        altKey="wizard.siguiente"
				        titleKey="wizard.siguiente"
				        styleClass="imgTextMiddle"/>
					<bean:message key="wizard.siguiente"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			</c:if>
			<td>
				<tiles:insert definition="button.closeButton" flush="true"/>
			</td>
			<td width="10">&nbsp;</td>
		</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
			<html:hidden property="method" styleId="method" value="informe"/>
			<div class="separador8">&nbsp;</div>
			<tiles:insert beanName="idBusqueda" />

			<c:if test="${(not empty descriptores) or (notIsPostBack != true)}">
				<div class="separador8">&nbsp;</div>
				<tiles:insert beanName="idDescriptores" />
				<div class="separador8">&nbsp;</div>
			</c:if>

			<c:if test="${(not empty descriptoresAUnificar)}">
				<tiles:insert beanName="idDescriptoresAUnificar" />
				<div class="separador8">&nbsp;</div>
			</c:if>

	</tiles:put>
</tiles:insert>
</html:form>