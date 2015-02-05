<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="listaIngresos" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="INGRESOS_FINALIZADOS" value="${requestScope[appConstants.transferencias.SON_RELACIONES_FINALIZADAS]}"/>

<c:set var="SON_INGRESOS_FINALIZADOS" value="false"/>

<c:if test="${INGRESOS_FINALIZADOS != null && INGRESOS_FINALIZADOS == true}">
	<c:set var="SON_INGRESOS_FINALIZADOS" value="true"/>	
</c:if>

<script>
	<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />

	function eliminarIngreso(){
		var formularioSeleccion = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
		if (formularioSeleccion.relacionesseleccionadas && elementSelected(formularioSeleccion.relacionesseleccionadas)) {
				if (confirm("<bean:message key='archigest.archivo.fondos.ingresos.eliminacionWarning'/>")) {   
					formularioSeleccion.method.value="eliminaringresos";
					formularioSeleccion.submit();
				}
			} else
				alert("<bean:message key='errors.fondos.esNecesarioSeleccionarAlMenosUnIngresoDirecto'/>");
	}

</script>

<html:form action="/gestionRelaciones">
<div id="contenedor_ficha">
<input type="hidden" name="method">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
<TR>
    <TD class="etiquetaAzul12Bold" width="50%" height="25px">
		<c:choose>
			<c:when test="${SON_INGRESOS_FINALIZADOS == true}">
				<bean:message key="archigest.archivo.bandeja.ingresos.directos"/>&nbsp;<bean:message key="archigest.archivo.bandeja.finalizadas"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.fondos.ingresosDirectosElaboracion"/>
			</c:otherwise>
		</c:choose>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<security:permissions action="${appConstants.transferenciaActions.ELABORACION_INGRESOS_DIRECTOS}">
		<TD>
			<c:url var="urlCrearIngreso" value="/action/gestionIngresoDirecto">
				<c:param name="method" value="nuevo"/>
			</c:url>
			<a class="etiquetaAzul12Bold" href='<c:out value="${urlCrearIngreso}" escapeXml="false"/>'>
				<html:img titleKey="archigest.archivo.fondos.ingreso.directo.crear" altKey="archigest.archivo.fondos.ingreso.directo.crear" page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.fondos.ingreso.directo.crear"/>
			</a>
		</TD>
		<TD width="10">&nbsp;</TD>
		<c:if test="${!empty listaIngresos && SON_INGRESOS_FINALIZADOS == false}">
       <TD>
			<a class="etiquetaAzul12Bold" href="javascript:eliminarIngreso()" >
				<html:img page="/pages/images/delete.gif" border="0" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
				&nbsp;<bean:message key="archigest.archivo.eliminar" />
			</a>
	   </TD>
       <TD width="10">&nbsp;</TD>
       </c:if>
		</security:permissions>						
	   <TD>
			<%--boton Cerrar --%>
			<c:url var="volverURL" value="/action/gestionRelaciones">
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

	<c:url var="listaIngresoPaginationURI" value="/action/gestionRelaciones">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaIngresoPaginationURI" type="java.lang.String" />

	<display:table name="pageScope.listaIngresos"
		id="ingreso" 
		pagesize="10"
		decorator="transferencias.decorators.ViewRelacionEntregaDecorator"
		requestURI="<%=listaIngresoPaginationURI%>"
		sort="list"
		export="true"
		style="width:99%;margin-left:auto;margin-right:auto">

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.fondos.noIngresos"/>
		</display:setProperty>
		
		<display:column media="html">
			<c:if test="${ingreso.puedeSerEliminada}">
				<input type="checkbox" name="relacionesseleccionadas" value='<c:out value="${ingreso.id}"/>' >
			</c:if>
		</display:column>

		<display:column titleKey="archigest.archivo.fondos.ingreso.directo" sortProperty="codigoTransferencia" sortable="true" headerClass="sortable" style="white-space: nowrap;" media="html">
			<c:url var="verIngresoURL" value="/action/gestionRelaciones">
				<c:param name="method" value="veringreso" />
				<c:param name="idingresoseleccionado" value="${ingreso.id}" />
			</c:url>
			
			<a class="tdlink" href="<c:out value="${verIngresoURL}" escapeXml="false"/>" >
				<c:out value="${ingreso.codigoTransferencia}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.fondos.ingreso.directo" style="white-space: nowrap;" media="csv excel xml pdf">
			<c:out value="${ingreso.codigoTransferencia}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable" media="html"/>
		<display:column titleKey="archigest.archivo.transferencias.estado" media="csv excel xml pdf">
			<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${ingreso.estado}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

		<display:column titleKey="archigest.archivo.transferencias.serie" sortable="true" headerClass="sortable" style="width:25%">
			<c:out value="${ingreso.serie.codigo}"/> <c:out value="${ingreso.serie.titulo}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.formato" sortable="true" headerClass="sortable">
			<c:out value="${ingreso.formato.nombre}"/>
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


<script>removeCookie('tabsUdocsRelacion');</script>