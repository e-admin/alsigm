<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="listaDivisionesFS" value="${requestScope[appConstants.fondos.LISTA_DIVISIONESFS_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="DIVISIONESENFS_FINALIZADAS" value="${requestScope[appConstants.fondos.SON_DIVISIONESFS_FINALIZADAS]}"/>
<c:set var="SON_DIVISIONESENFS_FINALIZADAS" value="false"/>

<c:if test="${DIVISIONESENFS_FINALIZADAS != null && DIVISIONESENFS_FINALIZADAS == true}">
	<c:set var="SON_DIVISIONESENFS_FINALIZADAS" value="true"/>	
</c:if>

<script>
	<bean:struts id="mappingGestionFS" mapping="/gestionFraccionSerie" />

	function eliminarDivisionesFS(){
		var formularioSeleccion = document.forms['<c:out value="${mappingGestionFS.name}" />'];
		if (formularioSeleccion.divisionesseleccionadas && elementSelected(formularioSeleccion.divisionesseleccionadas)) {
				if (confirm("<bean:message key='archigest.archivo.fondos.divisionesFS.eliminacionWarning'/>")) {   
					formularioSeleccion.method.value="eliminardivisionesfs";
					formularioSeleccion.submit();
				}
			} else
				alert("<bean:message key='errors.archivo.esNecesarioSeleccionarAlMenosUnaDivisionFSAEliminar'/>");
	}

</script>

<html:form action="/gestionFraccionSerie">
<div id="contenedor_ficha">
<input type="hidden" name="method">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
<TR>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<c:choose>
			<c:when test="${SON_DIVISIONESENFS_FINALIZADAS == true}">
				<bean:message key="archigest.archivo.fondos.divisionesfsFinalizadas"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.fondos.divisionesfsEnElaboracion"/>
			</c:otherwise>
		</c:choose>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<c:if test="${!empty listaDivisionesFS && SON_DIVISIONESENFS_FINALIZADAS == false}">
	       <TD>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarDivisionesFS()" >
					<html:img page="/pages/images/delete.gif" border="0" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
					&nbsp;<bean:message key="archigest.archivo.eliminar" />
				</a>
		   </TD>  
       		<TD width="10">&nbsp;</TD>
       </c:if>
					
	   <TD>
			<%--boton Cerrar --%>
			<c:url var="volverURL" value="/action/gestionFraccionSerie">
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

	<c:url var="listaDivisionesFSPaginationURI" value="/action/gestionFraccionSerie">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaDivisionesFSPaginationURI" type="java.lang.String" />

	<display:table name="pageScope.listaDivisionesFS"
		id="divisionfs" 
		pagesize="10"
		requestURI="<%=listaDivisionesFSPaginationURI%>"
		sort="list"
		export="true"
		style="width:99%;margin-left:auto;margin-right:auto">

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.fondos.noDivisionesFS"/>
		</display:setProperty>
		
		<display:column media="html">
			<c:if test="${divisionfs.puedeSerEliminada}">
				<input type="checkbox" name="divisionesseleccionadas" value='<c:out value="${divisionfs.idFS}"/>' >
			</c:if>
		</display:column>

		<display:column titleKey="archigest.archivo.cf.fraccionSerie" sortProperty="fraccionSerie.titulo" sortable="true" headerClass="sortable" style="white-space: nowrap;" media="html">
			<c:url var="verDivisionFSURL" value="/action/gestionFraccionSerie">
				<c:param name="method" value="verDivisionFraccionSerie" />
				<c:param name="iddivisionfsseleccionada" value="${divisionfs.idFS}" />
			</c:url>
			
			<a class="tdlink" href="<c:out value="${verDivisionFSURL}" escapeXml="false"/>" >
				<c:out value="${divisionfs.asunto}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.cf.nivelDocumental" sortable="true" headerClass="sortable">
			<c:out value="${divisionfs.nombreNivel}"/>
		</display:column>
		
		<display:column titleKey="archigest.archivo.cf.ficha" sortable="true" headerClass="sortable" >
			<c:out value="${divisionfs.nombreFicha}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.divisionfs.estado" sortProperty="estado" sortable="true" headerClass="sortable" media="html">
			<fmt:message key="archigest.archivo.fondos.estadoDivisionFS.${divisionfs.estado}" />
		</display:column>
		<display:column titleKey="archigest.archivo.divisionfs.estado" media="csv excel xml pdf">
			<fmt:message key="archigest.archivo.fondos.estadoDivisionFS.${divisionfs.estado}" />
		</display:column>

		<display:column titleKey="archigest.archivo.divisionfs.fEstado" sortable="true" headerClass="sortable">
			<fmt:formatDate value="${divisionfs.fechaEstado}" pattern="${FORMATO_FECHA}" />
		</display:column> 
	</display:table>  

</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>
