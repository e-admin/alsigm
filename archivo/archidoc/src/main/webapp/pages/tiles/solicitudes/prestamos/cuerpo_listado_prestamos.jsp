<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<c:set var="LISTA_NAME" value="requestScope.${appConstants.prestamos.LISTA_PRESTAMOS_KEY}"/>
<c:if test="${requestScope[appConstants.prestamos.ACCION_ANIADIR_A_PRESTAMO_KEY]}">
	<c:set var="IS_ANIADIR_A_PRESTAMO" value="true"/>
</c:if>

<%@ page import="common.Constants"%>
<%@ page import="solicitudes.prestamos.PrestamosConstants" %>


<c:choose>
	<c:when test="${IS_ANIADIR_A_PRESTAMO == true}">
	<script>
	function aceptarAniadir(){
		var form = document.forms[0];
		if (form && form.id && elementSelected(form.id)){
			if (confirm("<bean:message key='archigest.archivo.prestamos.aniadir.udocs.busquedas.msg.confirm'/>")){
				form.method.value="aniadirUdocsAPrestamo";
				form.submit();
			}
		} else {
			alert("<bean:message key='archigest.archivo.prestamos.aniadir.udocs.busquedas.obligatorio'/>");
		}
	}
	</script>
	</c:when>

	<c:otherwise>

	<script>
	function eliminarPrestamos(){
		if ( document.forms[0].elements['prestamosseleccionados']!=null ) {
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"prestamosseleccionados");
			if(nSelected>=1){
				if (confirm("<bean:message key='archigest.archivo.prestamos.eliminaciones.confirm'/>")){
					document.forms[0].method.value="eliminarprestamos";
					document.forms[0].submit();
				}
			}else{
				alert("<bean:message key='archigest.archivo.prestamos.eliminacion.warning.msg'/>");
			}
		} else {
			alert("<bean:message key='archigest.archivo.prestamos.eliminacion.noPres'/>");
		}
	}
</script>

	</c:otherwise>
</c:choose>


<div id="contenedor_ficha">

<html:form action="/gestionPrestamos">
<html:hidden property="method"/>

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
<TR>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<bean:message key="archigest.archivo.prestamos.prestamos"/>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	 <TR>

   			<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ELIMINAR]}">
			<td nowrap="nowrap">
				<c:url var="nuevaConsultaURL" value="/action/gestionPrestamos">
					<c:param name="method" value="nuevo" />
				</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${nuevaConsultaURL}" escapeXml="false"/>" >
					<html:img
						page="/pages/images/new.gif"
						altKey="archigest.archivo.crear"
						titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.crear"/></a>
			</td>
		 	<TD width="20">&nbsp;</TD>
   			<c:if test="${!empty LISTA_NAME}">
       		<TD>
				<c:set var="llamadaEliminarPrestamos">javascript:eliminarPrestamos()</c:set>
				<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaEliminarPrestamos}" escapeXml="false"/>' >
					<html:img
						page="/pages/images/delete.gif"
						altKey="archigest.archivo.eliminar"
						titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				</a>
		  	 </TD>
		     <TD width="10">&nbsp;</TD>
		  	 </c:if>
		  	 </c:if>

	     <c:if test="${IS_ANIADIR_A_PRESTAMO}">
		 <TD>
				<a class="etiquetaAzul12Bold" href="javascript:aceptarAniadir()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
	     			&nbsp;<bean:message key="archigest.archivo.aceptar"/>
	     		</a>
	     </TD>
	      <TD width="10">&nbsp;</TD>
	     </c:if>
	     <TD>
			<c:url var="cancelURL" value="/action/gestionPrestamos">
				<c:param name="method" value="goBack" />
			</c:url>
			<a class="etiquetaAzul12Bold" href='<c:out value="${cancelURL}" escapeXml="false"/>'>
				<html:img
					page="/pages/images/close.gif"
					altKey="archigest.archivo.cerrar"
					titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
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

<DIV id="barra_errores"><archivo:errors /></DIV>

<span class="separador1"></span>
<DIV class="bloque">

	<%--Establecemos variables para el displaytag --%>
	<c:if test="${requestScope[appConstants.prestamos.VER_COLUMNA_NOTAS]}">
		<c:set var="VER_COLUMNA_NOTAS" value="true"/>
	</c:if>
	<c:if test="${requestScope[appConstants.prestamos.VER_COLUMNA_SELECCIONAR]}">
		<c:set var="VER_COLUMNA_SELECCIONAR" value="true"/>
	</c:if>
	<jsp:useBean id="LISTA_NAME" type="java.lang.String" />

			<%--DISPLAYTAG--%>
			<display:table name="<%=LISTA_NAME%>"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaRegistros"
				decorator="solicitudes.prestamos.decorators.ViewPrestamoDecorator"
				pagesize="15"
				sort="list"
				requestURI='<%=request.getContextPath()+"/action/gestionPrestamos?method=" + request.getAttribute(PrestamosConstants.METHOD)%>'
				excludedParams="*"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noPrev"/>
				</display:setProperty>

				<c:if test="${IS_ANIADIR_A_PRESTAMO}">
				<display:column style="width:20px" media="html">
					<input type="radio" name="id" value='<c:out value="${listaRegistros.id}"/>' >
				</display:column>
				</c:if>

				<%--Checkbox--%>
				<c:if test="${VER_COLUMNA_SELECCIONAR}">
					<display:column style="width:20px" headerClass="deleteFolderIcon" media="html">
						<c:if test="${listaRegistros.estado==1 || listaRegistros.estado==3}">
							<input type="checkbox" name="prestamosseleccionados" value='<c:out value="${listaRegistros.id}"/>' >
						</c:if>
					</display:column>
				</c:if>

				<%--ID prestamo--%>
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

</DIV> <%--bloque --%>
			<span class="separador1"></span>
			<c:if test="${IS_ANIADIR_A_PRESTAMO}">
				<tiles:insert page="/pages/tiles/solicitudes/prestamos/detalle_prestamos_udocs_seleccionadas_busqueda.jsp" />
			</c:if>
</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>

</html:form>

</div> <%--contenedor_ficha --%>

