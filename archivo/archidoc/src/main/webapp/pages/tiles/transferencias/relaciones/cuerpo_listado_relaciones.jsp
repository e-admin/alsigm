<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="RELACIONES_FINALIZADAS" value="${requestScope[appConstants.transferencias.SON_RELACIONES_FINALIZADAS]}"/>
<c:set var="RELACIONES_RECHAZADAS" value="${requestScope[appConstants.transferencias.SON_RELACIONES_RECHAZADAS]}"/>

<c:set var="SON_RELACIONES_FINALIZADAS" value="false"/>
<c:set var="SON_RELACIONES_RECHAZADAS" value="false"/>

<c:if test="${RELACIONES_FINALIZADAS != null && RELACIONES_FINALIZADAS == true}">
	<c:set var="SON_RELACIONES_FINALIZADAS" value="true"/>
</c:if>

<c:if test="${RELACIONES_RECHAZADAS != null && RELACIONES_RECHAZADAS == true}">
	<c:set var="SON_RELACIONES_RECHAZADAS" value="true"/>
</c:if>

<script>
	<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />
	function eliminarRelaciones(){
		var formularioSeleccion = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
		if (formularioSeleccion.relacionesseleccionadas && elementSelected(formularioSeleccion.relacionesseleccionadas)) {
				if (confirm("<bean:message key='archigest.archivo.transferencias.relaciones.eliminacionWarning'/>")) {
					formularioSeleccion.method.value="eliminarrelaciones";
					formularioSeleccion.submit();
				}
			} else
				alert("<bean:message key='errors.relaciones.esNecesarioSeleccionarAlMenosUnaRelacion'/>");
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
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<c:choose>
			<c:when test="${SON_RELACIONES_FINALIZADAS == true}">
				<bean:message key="archigest.archivo.menu.transferencias.relaciones"/>&nbsp;<bean:message key="archigest.archivo.bandeja.finalizadas"/>
			</c:when>
			<c:when test="${SON_RELACIONES_RECHAZADAS == true}">
				<bean:message key="archigest.archivo.menu.transferencias.relaciones"/>&nbsp;<bean:message key="archigest.archivo.bandeja.rechazadas"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.transferencias.relacionesElaboracion"/>
			</c:otherwise>
		</c:choose>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
		<TD>
			<c:url var="urlCrearRelacion" value="/action/gestionRelaciones">
				<c:param name="method" value="listadoprevision"/>
			</c:url>
			<a class="etiquetaAzul12Bold" href='<c:out value="${urlCrearRelacion}" escapeXml="false"/>'>
				<html:img titleKey="archigest.archivo.transferencias.relacion.crear" altKey="archigest.archivo.transferencias.relacion.crear" page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
				&nbsp;<bean:message key="archigest.archivo.transferencias.relacion.crear"/>
			</a>
		</TD>
		<TD width="10">&nbsp;</TD>
		<c:if test="${!empty listaRelaciones && SON_RELACIONES_FINALIZADAS == false}">
       <TD>
			<a class="etiquetaAzul12Bold" href="javascript:eliminarRelaciones()" >
				<html:img page="/pages/images/delete.gif" border="0" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
				&nbsp;<bean:message key="archigest.archivo.eliminar" />
			</a>
	   </TD>

       <TD width="10">&nbsp;</TD>
       </c:if>
		</security:permissions>
	   <TD>
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

	<c:url var="listaRelacionesPaginationURI" value="/action/gestionRelaciones">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaRelacionesPaginationURI" type="java.lang.String" />

         <display:table name="pageScope.listaRelaciones"
          id="relacion"
		pagesize="10"
                   decorator="transferencias.decorators.ViewRelacionEntregaDecorator"
                   requestURI="<%=listaRelacionesPaginationURI%>"
                   sort="list"
                   export="true"
                   style="width:99%;margin-left:auto;margin-right:auto">

                   <display:setProperty name="basic.msg.empty_list">
                           <bean:message key="archigest.archivo.transferencias.noRel"/>
                   </display:setProperty>

                   <display:column media="html">
                           <c:if test="${relacion.puedeSerEliminada || relacion.puedeSerEnviada}">
                                   <input type="checkbox" name="relacionesseleccionadas" value='<c:out value="${relacion.id}"/>' >
                           </c:if>
                   </display:column>

                   <display:column titleKey="archigest.archivo.relacion" sortProperty="codigoTransferencia" sortable="true" headerClass="sortable" style="white-space: nowrap;" media="html">
                           <c:url var="verRelacionURL" value="/action/gestionRelaciones">
                                   <c:param name="method" value="verrelacion" />
                                   <c:param name="idrelacionseleccionada" value="${relacion.id}" />
                           </c:url>

                           <a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" >
                                   <c:out value="${relacion.codigoTransferencia}"/>
                           </a>
                   </display:column>

                   <display:column titleKey="archigest.archivo.relacion" style="white-space: nowrap;" media="csv excel xml pdf">
                           <c:out value="${relacion.codigoTransferencia}"/>
                   </display:column>

                   <display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable" >
                           <c:set var="keyTitulo">
                                   archigest.archivo.transferencias.tipooperacion<c:out value="${relacion.tipooperacion}"/>
                           </c:set>
                           <fmt:message key="${keyTitulo}" />
                   </display:column>

		<display:column titleKey="archigest.archivo.transferencias.estado" sortable="true" headerClass="sortable">
			<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${relacion.estado}" />
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

		<display:column titleKey="archigest.archivo.transferencias.procedimiento" sortable="true" headerClass="sortable" style="width:25%">
			<c:out value="${relacion.idprocedimiento}"/> <c:out value="${relacion.procedimiento.nombre}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.serie" sortable="true" headerClass="sortable" style="width:25%">
			<c:out value="${relacion.serie.codigo}"/> <c:out value="${relacion.serie.titulo}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.transferencias.formato" sortable="true" headerClass="sortable">
			<c:out value="${relacion.nombreFormato}"/>
		</display:column>


	</display:table>

</DIV>

</div>
</div>

<h2><span>&nbsp;
</span></h2>

</div>
</div>

</html:form>


<script>removeCookie('tabsUdocsRelacion');</script>