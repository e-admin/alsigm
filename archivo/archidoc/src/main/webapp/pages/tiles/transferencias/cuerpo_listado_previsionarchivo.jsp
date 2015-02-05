<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
	<TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.transferencias.previsionesGestionar"/>
	</TD>

    <TD align="right">
	<TABLE cellpadding=0 cellspacing=0>
	   <TD>
			<%--boton Cerrar --%>
			<c:url var="volverURL" value="/action/gestionPrevisiones">
				<c:param name="method" value="goBack" />
			</c:url>				
			<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>">
				<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/>
			</a>
	   </TD>

     </TR>
	</TABLE>
	</TD>

	</TR>
</TABLE>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">


<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<span class="separador1"></span>
<DIV class="bloque">

	<c:url var="listaPrevisionesPaginationURI" value="/action/gestionPrevisiones">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaPrevisionesPaginationURI" type="java.lang.String" />

	<c:set var="listaPrevisiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}"/>

	<display:table name="pageScope.listaPrevisiones"
		style="width:99%;margin-left:auto;margin-right:auto"
		id="listaRegistros" 
		sort="list"
		decorator="transferencias.decorators.ViewPrevisionDecorator"
		pagesize="20"
		requestURI='<%=listaPrevisionesPaginationURI%>'			
		export="true"
		defaultorder="descending"
		defaultsort="4">
		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.transferencias.noPrev" />
		</display:setProperty>

		<c:url var="verURL" value="/action/gestionPrevisiones">
			<c:param name="method" value="verprevision" />
			<c:param name="idprevision" value="${listaRegistros.id}" />
		</c:url>

		<display:column titleKey="archigest.archivo.transferencias.prevision" sortable="true" headerClass="sortable" media="html">
			<html:img page="/pages/images/pixel.gif" width="1" height="20" styleClass="imgTextMiddle"/>
			<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
				<c:out value="${listaRegistros.codigoTransferencia}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.prevision" media="csv excel xml pdf">
			<c:out value="${listaRegistros.codigoTransferencia}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.tipoTrans" sortable="true" headerClass="sortable" >
			<c:set var="keyTitulo">
				archigest.archivo.transferencias.tipooperacion<c:out value="${listaRegistros.tipooperacion}"/>
			</c:set>
			<fmt:message key="${keyTitulo}" />		
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable" media="html"/>
		<display:column titleKey="archigest.archivo.transferencias.estado" media="csv excel xml pdf">
			<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${listaRegistros.estado}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

		<display:column titleKey="archigest.archivo.transferencias.gestor" sortable="true" headerClass="sortable">
			<c:set var="gestor" value="${listaRegistros.gestor}"/>
			<c:out value="${gestor.apellidos}"/><c:if test="${!empty gestor.apellidos && !empty gestor.nombre}">, </c:if> <c:out value="${gestor.nombre}"/>
		</display:column>
		<display:column titleKey="archigest.archivo.transferencias.FIT">
			<fmt:formatDate value="${listaRegistros.fechainitrans}" pattern="${FORMATO_FECHA}" />
		</display:column>
		<display:column titleKey="archigest.archivo.transferencias.FFT">
			<fmt:formatDate value="${listaRegistros.fechafintrans}" pattern="${FORMATO_FECHA}" />
		</display:column>
	</display:table>  

</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
