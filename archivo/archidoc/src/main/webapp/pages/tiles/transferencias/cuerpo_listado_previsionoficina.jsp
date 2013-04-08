<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="previsiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}"/>

<c:set var="PREVISIONES_ACEPTADAS_O_RECHAZADAS" value="${requestScope[appConstants.transferencias.SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS]}"/>

<c:set var="SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS" value="false"/>

<c:if test="${PREVISIONES_ACEPTADAS_O_RECHAZADAS != null && PREVISIONES_ACEPTADAS_O_RECHAZADAS == true}">
	<c:set var="SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS" value="true"/>	
</c:if>

<script>
	<bean:struts id="mappingGestionPrevisiones" mapping="/gestionPrevisiones" />

	function eliminarPrevisiones() {
		var formularioSeleccion = document.forms['<c:out value="${mappingGestionPrevisiones.name}" />'];
		if (formularioSeleccion.previsionesseleccionadas) {
			if (elementSelected(formularioSeleccion.previsionesseleccionadas))
				{
				if (confirm("<bean:message key='archigest.archivo.transferencias.previsiones.eliminacionWarning'/>")) 
					{
					formularioSeleccion.method.value="eliminarprevisiones";
					formularioSeleccion.submit();
					}
				}
			else
				alert("<bean:message key='archigest.archivo.transferencias.previsiones.eliminacionMinUna'/>");
		}
	}
</script>


<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
<TR>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<c:choose>
			<c:when test="${SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS == true}">
				<bean:message key="archigest.archivo.menu.transferencias.previsiones"/>&nbsp;<bean:message key="archigest.archivo.bandeja.aceptadasORechazadas"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.transferencias.previsionesElaboracion"/>
			</c:otherwise>
		</c:choose>		
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<security:permissions action="${appConstants.transferenciaActions.CREAR_PREVISION}">
		<TD>
			<c:url var="urlCrearPrevision" value="/action/gestionPrevisiones">
				<c:param name="method" value="seleccionTipoPrevision"/>
			</c:url>
			<a class="etiquetaAzul12Bold" href='<c:out value="${urlCrearPrevision}" escapeXml="false"/>'>
				<html:img titleKey="archigest.archivo.transferencias.prevision.crear" altKey="archigest.archivo.transferencias.prevision.crear" page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.transferencias.prevision.crear"/>
			</a>
		</TD>
		<TD width="10">&nbsp;</TD>
		</security:permissions>						

			<c:if test="${SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS != true}">
		      <security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">
		       <c:if test="${!empty previsiones}">
		       <TD>
					<c:set var="llamadaEliminarPrevisiones">javascript:eliminarPrevisiones()</c:set>
					<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaEliminarPrevisiones}" escapeXml="false"/>' >
						<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
						&nbsp;<bean:message key="archigest.archivo.eliminar" />
					</a>
			   </TD>
			   <TD width="10">&nbsp;</TD>
			   </c:if>
			   </security:permissions>			
			</c:if>
	   <TD>
			<%--boton Cerrar --%>
			<c:url var="volver2URL" value="/action/gestionPrevisiones">
				<c:param name="method" value="goBack" />
			</c:url>				
			<a class="etiquetaAzul12Bold" href="<c:out value="${volver2URL}" escapeXml="false"/>">
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
<html:form action="/gestionPrevisiones">
<input type="hidden" name="method">

	<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

	<c:url var="listaPrevisionesPaginationURI" value="/action/gestionPrevisiones">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaPrevisionesPaginationURI" type="java.lang.String" />

	<display:table name="pageScope.previsiones"
		style="width:99%;margin-left:auto;margin-right:auto"
		id="prevision" 
		decorator="transferencias.decorators.ViewPrevisionDecorator"
		pagesize="15"
		sort="list"
		defaultsort="2"
		requestURI="<%=listaPrevisionesPaginationURI%>"
		export="true"
		defaultorder="descending">

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.transferencias.noPrev"/>
		</display:setProperty>
		
		<c:if test="${SON_PREVISIONES_ACEPTADAS_O_RECHAZADAS != true}">
		 <security:permissions action="${appConstants.transferenciaActions.GESTION_PREVISION_EN_ORGANO_REMITENTE}">
		
		<display:column style="width:20px" headerClass="deleteFolderIcon" media="html">
			<c:if test="${prevision.puedeSerEliminada || prevision.puedeSerEnviada}">
				<input type="checkbox" name="previsionesseleccionadas" value='<c:out value="${prevision.id}"/>' >
			</c:if>
		</display:column>
		</security:permissions>
		</c:if>		

		<display:column titleKey="archigest.archivo.transferencias.prevision" sortable="true" headerClass="sortable" media="html">
			<c:url var="verPrevisionURL" value="/action/gestionPrevisiones">
				<c:param name="method" value="verprevision" />
				<c:param name="idprevision" value="${prevision.id}" />
			</c:url>
			<a class="tdlink" href='<c:out value="${verPrevisionURL}" escapeXml="false"/>' >
				<c:out value="${prevision.codigoTransferencia}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.prevision" media="csv excel xml pdf">
			<c:out value="${prevision.codigoTransferencia}"/>
		</display:column>
		
		<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable" >
			<c:set var="keyTitulo">
				archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
			</c:set>
			<fmt:message key="${keyTitulo}" />		
		</display:column>
		

		<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable" media="html"/>
		<display:column titleKey="archigest.archivo.transferencias.estado" media="csv excel xml pdf">
			<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${prevision.estado}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.fEstado" sortable="true" headerClass="sortable">
			<fmt:formatDate value="${prevision.fechaestado}" pattern="${FORMATO_FECHA}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.FIT">
			<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.FFT">
			<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
		</display:column>

	</display:table>  
</html:form>

</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>


