<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ page import="common.Constants"%>
<%@ page import="solicitudes.prestamos.PrestamosConstants" %>
<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<c:set var="LISTA_NAME" value="requestScope.${appConstants.prestamos.LISTA_PRESTAMOS_KEY}"/>


<div id="contenedor_ficha">

<html:form action="/gestionPrestamos">
<html:hidden property="method"/>

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<bean:message key="archigest.archivo.prestamos.prestamos"/>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	 <TR>
		  	 <%--boton Cerrar --%>
	      	  <TD>
				<c:url var="volver2URL" value="/action/buscarPrestamos">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:image
						page="/pages/images/close.gif" border="0"
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		   		</a>
		   </TD>
	     <TD width="10">&nbsp;</TD>
     </TR>
	</TABLE>
	</TD>
  </TR>
</TABLE>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores"><archivo:errors /></DIV>

<span class="separador1"></span>
<DIV class="bloque">

	<c:if test="${requestScope[appConstants.prestamos.VER_COLUMNA_NOTAS]}">
		<c:set var="VER_COLUMNA_NOTAS" value="true"/>
	</c:if>

	<jsp:useBean id="LISTA_NAME" type="java.lang.String" />

			<display:table name="<%=LISTA_NAME%>"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaRegistros"
				decorator="solicitudes.prestamos.decorators.ViewPrestamoDecorator"
				pagesize="15"
				sort="list"
				requestURI="/action/buscarPrestamos?method=buscar"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noPrev"/>
				</display:setProperty>

				<c:if test="${IS_ANIADIR_A_PRESTAMO}">
				<display:column style="width:20px" media="html">
					<input type="radio" name="id" value='<c:out value="${listaRegistros.id}"/>' >
				</display:column>
				</c:if>

				<c:if test="${VER_COLUMNA_SELECCIONAR}">
					<display:column style="width:20px" headerClass="deleteFolderIcon" media="html">
						<c:if test="${listaRegistros.estado==1 || listaRegistros.estado==3}">
							<input type="checkbox" name="prestamosseleccionados" value='<c:out value="${listaRegistros.id}"/>' >
						</c:if>
					</display:column>
				</c:if>

				<display:column titleKey="archigest.archivo.prestamos.prestamo" sortable="true" headerClass="sortable" sortProperty="codigoTransferencia" media="html">
					<c:url var="URL" value="/action/gestionPrestamos">
						<c:param name="method" value="verprestamo" />
						<c:param name="id" value="${listaRegistros.id}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
						<c:out value="${listaRegistros.codigoTransferencia}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.prestamo" media="csv excel xml pdf">
					<c:out value="${listaRegistros.codigoTransferencia}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante"  sortable="true" headerClass="sortable" property="norgsolicitantePrestamo" decorator="solicitudes.prestamos.decorators.PrestamoDecorator" media="html"/>
				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante"  property="norgsolicitante" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" sortable="true" headerClass="sortable" property="nusrsolicitantePrestamo" decorator="solicitudes.prestamos.decorators.PrestamoDecorator" media="html"/>
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" property="nusrsolicitante" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.prestamos.estado" sortable="true" headerClass="sortable" property="estado" decorator="solicitudes.prestamos.decorators.PrestamoDecorator" media="html"/>
				<display:column titleKey="archigest.archivo.prestamos.estado" media="csv excel xml pdf">
					<fmt:message key="archigest.archivo.solicitudes.estado.${listaRegistros.estado}"/>
				</display:column>
				<%--Notas--%>
				<c:if test="${VER_COLUMNA_NOTAS}">
					<display:column property="notas" title="&nbsp;" media="html"/>
					<display:column property="notasExportar" title="" media="csv excel xml pdf"/>
				</c:if>
				<display:column titleKey="archigest.archivo.prestamos.festado" sortable="true" headerClass="sortable" property="festado" decorator="solicitudes.prestamos.decorators.PrestamoDecorator" media="html"/>
				<display:column titleKey="archigest.archivo.prestamos.festado" media="csv excel xml pdf">
					<fmt:formatDate value="${listaRegistros.festado}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.fPrevDev" sortable="true" headerClass="sortable" property="fmaxfinprestamo" decorator="solicitudes.prestamos.decorators.PrestamoDecorator" media="html">
						<c:out value="${listaRegistros.fmaxfinprestamo}"/>

				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.fPrevDev" media="csv excel xml pdf">
					<fmt:formatDate value="${listaRegistros.fmaxfinprestamo}" pattern="${appConstants.common.FORMATO_FECHA}" />
				</display:column>
			</display:table>

</DIV>

</div>
</div>

<h2><span>&nbsp;
</span></h2>

</div>
</html:form>
</div>

