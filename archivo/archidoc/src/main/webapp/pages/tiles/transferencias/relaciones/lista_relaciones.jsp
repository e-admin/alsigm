<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<DIV class="bloque">

	<c:set var="LISTA_NAME" value="requestScope.${appConstants.transferencias.LISTA_RELACIONES_KEY}"/>
	<jsp:useBean id="LISTA_NAME" type="java.lang.String" />

	<display:table name="<%=LISTA_NAME%>"
		id="listaRegistros" 
		pagesize="10"
		decorator="transferencias.decorators.ViewRelacionEntregaDecorator"
		requestURI='<%=request.getContextPath()+"/action/gestionRelaciones?method=listadorelacionesoficina"%>'			
		sort="list"
		export="false"
		style="width:99%;margin-left:auto;margin-right:auto"
		defaultorder="descending"
		defaultsort="2"
		>

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.transferencias.noRel"/>
		</display:setProperty>
		
		<display:column>
			<input type="checkbox" name="relacionesseleccionadas" value='<c:out value="${listaRegistros.id}"/>' >
		</display:column>

		<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" style="white-space: nowrap;">
			<c:url var="URL" value="/action/gestionRelaciones">
				<c:param name="method" value="verrelacion" />
				<c:param name="idrelacionseleccionada" value="${listaRegistros.id}" />
			</c:url>
			<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
			<c:out value="${listaRegistros.codigoTransferencia}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable" >
			<c:set var="keyTitulo">
				archigest.archivo.transferencias.tipooperacion<c:out value="${listaRegistros.tipooperacion}"/>
			</c:set>
			<fmt:message key="${keyTitulo}" />		
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable"/>

		<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

		<display:column titleKey="archigest.archivo.transferencias.procedimiento" sortable="true" headerClass="sortable" style="width:25%">
				<c:out value="${listaRegistros.idprocedimiento}"/>&nbsp;<c:out value="${listaRegistros.nombreProcedimiento}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.serie" property="idserie" sortable="true" headerClass="sortable" style="width:25%"/>

	</display:table>  

</DIV> <%-- bloque --%>
