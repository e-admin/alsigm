<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
<c:set var="detalles" value="${sessionScope[appConstants.prestamos.DETALLE_PRESTAMO_KEY]}"/>

<script>
	function imprimirEntrada()
	{
		if (elementSelected(document.forms.form0.idUdoc))
			document.forms.form0.submit();
		else
			alert("<bean:message key="archigest.archivo.prestamos.SelAlMenosUnaUDoc"/>");
		
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.prestamos.datosPrestamos"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<a class=etiquetaAzul12Bold href="javascript:imprimirEntrada()">
						<html:img 
							page="/pages/images/print.gif" 
							altKey="archigest.archivo.imprimir" 
							titleKey="archigest.archivo.imprimir" styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.imprimir"/>
			   		</a>
				</td>
				<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<div class="separador1">&nbsp;</div>
		  
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">prestamoInfo</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.identificacion"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/solicitudes/prestamos/cuerpo_cabeceracte_prestamo.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/solicitudes/prestamos/datos_prestamo.jsp" />
			</tiles:put>
		</tiles:insert>		
		
		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.prestamos.udocs"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
					

		
				<form name="form0" method="post" action="<c:url value="/action/papeletaDevolucion"/>">
				<input type="hidden" name="idPrestamo" value="<c:out value="${bPrestamo.id}"/>"/>
				
				<div class="separador1">&nbsp;</div>
				<div class="w100">
					<TABLE class="w98" cellpadding=0 cellspacing=0>
					  <TR>
						<TD width="100%" align="right">
							<TABLE cellpadding=0 cellspacing=0>
							  <TR>
						 		<TD>
						 			<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms.form0.idUdoc);" >
										<html:img 
											page="/pages/images/checked.gif" border="0" 
											altKey="archigest.archivo.selTodos" 
											titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
										<bean:message key="archigest.archivo.selTodos"/>&nbsp;
									</a>
								</TD>
							    <TD width="20">&nbsp;</TD>
							    <TD>
									<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms.form0.idUdoc);" >
										<html:img 
											page="/pages/images/check.gif" border="0" 
											altKey="archigest.archivo.quitarSel" 
											titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
										&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
									</a>
								</TD>
						     </TR>
							</TABLE>
						</TD>
					  </TR>
					</TABLE>
				</div>
				<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.detalles"
					id="detallePrestamo" 
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					pagesize="10"
					requestURI="/action/gestionPrestamos">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.prestamos.noDetallesPrestamo"/> 
					</display:setProperty>
	
					<display:column style="width:20px;">
						<c:if test="${detallePrestamo.estado==appConstants.prestamos.ESTADO_DETALLE_DEVUELTA}">
							<c:set var="idUdocSig" value="${detallePrestamo.idudoc}.${detallePrestamo.signaturaudoc}"/>
							<input type="checkbox" name="idUdoc" value="<c:out value="${pageScope.idUdocSig}" />">
						</c:if>
					</display:column>
					<display:column titleKey="archigest.archivo.num" sortable="true" headerClass="sortable">
						<c:url var="URL" value="/action/gestionDetallesPrestamos">
							<c:param name="method" value="verudoc" />
							<c:param name="ids" value="${detallePrestamo.idsolicitud}|${detallePrestamo.idudoc}|${detallePrestamo.signaturaudoc}" />
						</c:url>
						<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />'>
							<fmt:formatNumber value="${detallePrestamo_rowNum}" pattern="000"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.solicitudes.estado" sortable="true" headerClass="sortable">
						<fmt:message key="archigest.archivo.solicitudes.detalle.estado.${detallePrestamo.estado}" />
					</display:column>
					<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc" sortable="true" headerClass="sortable" >
						<c:out value="${detallePrestamo.signaturaudoc}" />
					</display:column>
					<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc"  sortProperty="expedienteudoc" sortable="true" headerClass="sortable" >
						<c:choose>
							<c:when test="${detallePrestamo.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
								<c:if test="${!empty detallePrestamo.nombreRangos}">
									<c:out value="${detallePrestamo.nombreRangos}"/><br/>
								</c:if>
								<c:out value="${detallePrestamo.expedienteudoc}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${detallePrestamo.expedienteudoc}" />
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.solicitudes.titulo" sortable="true" headerClass="sortable" sortProperty="titulo">
						<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
							<c:url var="URL" value="/action/isadg">
								<c:param name="method" value="retrieve" />
								<c:param name="id" value="${detallePrestamo.idudoc}" />
							</c:url>
							<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />'>
						</security:permissions>
							<bean:write name="detallePrestamo" property="titulo"/>
						<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
								</a>
						</security:permissions>							
					</display:column>
				</display:table>  
				
				<div class="separador5">&nbsp;</div>
				</form>
			</tiles:put>
		</tiles:insert>
		
	</tiles:put>
</tiles:insert>
