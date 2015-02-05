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
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<bean:message key="archigest.archivo.transferencias.ingresos.directos.listado.caption"/>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>

	  <c:if test="${!empty listaIngresos && SON_INGRESOS_FINALIZADOS == false}">
	       <td>
				<a class="etiquetaAzul12Bold" href="javascript:eliminarIngreso()" >
					<html:img page="/pages/images/delete.gif" border="0" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextTop"/>
					&nbsp;<bean:message key="archigest.archivo.eliminar" />
				</a>
		   </td>
		    <td width="10">&nbsp;</td>
	   </c:if>

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
			<div class="bloque" id="divTbl">
				<display:table name="pageScope.listaIngresos"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="relacion" 
					pagesize="15"
					sort="list"			
					requestURI="../../action/buscarRelaciones?method=buscar"
					export="true">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.altas.listado.vacio"/>
					</display:setProperty>

					<display:column media="html">
						<c:if test="${relacion.puedeSerEliminada}">
							<input type="checkbox" name="relacionesseleccionadas" value='<c:out value="${relacion.id}"/>' >
						</c:if>
					</display:column>
		
					<display:column titleKey="archigest.archivo.busqueda.form.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
						<c:url var="verURL" value="/action/gestionRelaciones">
							<c:param name="method" value="verrelacion" />
							<c:param name="idrelacionseleccionada" value="${relacion.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
							<c:out value="${relacion.codigoTransferencia}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.relacion" media="csv excel xml pdf">
						<c:out value="${relacion.codigoTransferencia}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.usuario" sortProperty="usuario" sortable="true" headerClass="sortable">
						<c:out value="${relacion.apellidosusuario}"/><c:if test="${!empty relacion.apellidosusuario && !empty relacion.nombreusuario}">, </c:if> <c:out value="${relacion.nombreusuario}"/>	
					</display:column>
					<display:column titleKey="archigest.archivo.deposito.formato" sortProperty="nombreFormato" sortable="true" headerClass="sortable">
						<c:out value="${relacion.nombreFormato}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.estado" sortProperty="estado" sortable="true" headerClass="sortable">
						<c:set var="keyTituloEstado">
							archigest.archivo.transferencias.estadoRelacion.<c:out value="${relacion.estado}"/>
						</c:set>
						<fmt:message key="${keyTituloEstado}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.fEstado" sortProperty="fechaestado" sortable="true" headerClass="sortable">
						<fmt:formatDate value="${relacion.fechaestado}" pattern="${appConstants.common.FORMATO_FECHA}" />
					</display:column>
					
					<security:permissions permission="${appConstants.permissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR}|${appConstants.permissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR}">
					<display:column titleKey="archigest.archivo.transferencias.orgRem" sortProperty="organo" sortable="true" headerClass="sortable">
						<c:out value="${relacion.nombreorgano}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.serie" sortProperty="serie" sortable="true" headerClass="sortable">
						<c:out value="${relacion.codigoserie}"/> <c:out value="${relacion.tituloserie}"/>
					</display:column>
					</security:permissions>
				</display:table> 
			</div> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>


<script>removeCookie('tabsUdocsRelacion');</script>