<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vNumHuecosReservados" value="${sessionScope[appConstants.deposito.NUM_HUECOS_RESERVADOS_KEY]}"/>
<c:set var="posibleUbicarRelacion" value="${sessionScope[appConstants.deposito.POSIBLE_UBICAR_RELACION]}"/>

<c:set var="vRelacion" value="${sessionScope[appConstants.deposito.RELACION_KEY]}"/>

<bean:struts id="mappingGestionUbicacion" mapping="/gestionUbicacionRelaciones" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.ubicar" />
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD noWrap>
				<c:url var="urlAtras" value="/action/gestionUbicacionRelaciones">
					<c:param name="method" value="goBack"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${urlAtras}" escapeXml="false"/>">
					<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.atras"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<c:if test="${posibleUbicarRelacion}">
				<TD noWrap>
					<script>
						function recogerAsignableSeleccionado(fieldToSet, frameSeleccion) {
							var form = document.forms['<c:out value="${mappingGestionUbicacion.name}" />'];
							/*var frameSeleccion = document.frames['navegador'];
							var campoSelect = frameSeleccion.document.forms[0].seleccionado;
							if (campoSelect.selectedIndex > -1) {
								form.asignabledestino.value = campoSelect.options[campoSelect.selectedIndex].value;
							}*/
							form.submit();
						}
					</script>
					<a class="etiquetaAzul12Bold" href="javascript:recogerAsignableSeleccionado()" >
						<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.siguiente"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
			</c:if>
			<TD noWrap>
				<c:url var="urlCancelar" value="/action/gestionUbicacionRelaciones">
					<c:param name="method" value="goBack"/>
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${urlCancelar}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<TABLE class="w98m1" cellpadding=0 cellspacing=2>
					<TR>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
							<span class="etiquetaNegra11Normal">
								<c:out value="${vRelacion.codigoTransferencia}"/>
							</span>
						</TD>
						<TD width="25%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.estado"/>:&nbsp;
							<span class="etiquetaNegra11Normal">
								<c:out value="${vRelacion.nombreestado}"/>
							</span>
						</TD>
						<TD width="20%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.fEstado"/>:&nbsp;
							<span class="etiquetaNegra11Normal">
								<fmt:formatDate value="${vRelacion.fechaestado}" pattern="${FORMATO_FECHA}" />
							</span>
						</TD>
						<TD width="35%" class="etiquetaAzul11Bold">
							<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
							<c:set var="gestorEnOrganoRemitente" value="${vRelacion.gestorEnOrganoRemitente}" />
							<span class="etiquetaNegra11Normal">
								<c:out value="${gestorEnOrganoRemitente.nombreCompleto}"/>
							</span>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>			

		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.espacioNecesario" /></tiles:put>
			<tiles:put name="blockContent" direct="true">

				<div class="separador5">&nbsp;</div>
				<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left;margin-left:auto;margin-right:auto">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<c:choose>
						<c:when test="${vNumHuecosReservados > 0}">
							<fmt:message key="archigest.archivo.transferencias.msgNoSitioEnReserva">
								<fmt:param value="${vRelacion.numeroUnidadesInstalacion - vNumHuecosReservados}"/>
							</fmt:message>
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.deposito.ubicarNoReserva" />
						</c:otherwise>
					</c:choose>
				</div>
				<TABLE class="formulario"> 
					<TR>
						<TD class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.transferencias.nUnidInstalacion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vRelacion.numeroUnidadesInstalacion}"/>
						</TD>
					</TR>
					<c:if test="${vNumHuecosReservados > 0}">
						<TR>
							<TD class="tdTitulo" width="300px">
								<bean:message key="archigest.archivo.deposito.numHuecosReservados"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${vNumHuecosReservados}"/>
							</TD>
						</TR>
					</c:if>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vRelacion.formato.nombre}"/>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.ubicacionActual"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vRelacion.ubicacion.nombre}"/>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
		</tiles:insert>
		<c:set var="listaDepositos" value="${sessionScope[appConstants.deposito.DEPOSITOS_EN_UBICACION]}"/>
		<c:choose>
			<c:when test="${!empty listaDepositos}">
				<html:form action="/gestionUbicacionRelaciones" >
				<input type="hidden" name="method" value="informeubicacion">
				<input type="hidden" name="idtipoasignabledestino" value="<c:out value="${param.idtipoasignabledestino}" />" >
		
				<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.deposito.msgSelNuevoDeposito"/></tiles:put>
					<tiles:put name="blockContent" direct="true">
						<div class="separador5">&nbsp;</div>
						<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left;margin-left:auto;margin-right:auto">
							<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
							<bean:message key="archigest.archivo.deposito.msgSelNuevoDeposito"/>
						</div>
						<div class="separador5">&nbsp;</div>
			
						<c:choose>	
							<c:when test="${posibleUbicarRelacion}">
								<display:table name="pageScope.listaDepositos" id="deposito" style="width:99%;margin-left:auto;margin-right:auto">	
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.deposito.msgNoDepositoUbicacion"/>
									</display:setProperty>

									<display:column>
										<c:if test="${deposito.ocupacionDeposito.huecosLibres > 0}">
											<input type="radio" name="idasignabledestino" value="<c:out value="${deposito.id}"/>" onclick="this.form.idtipoasignabledestino.value='<c:out value="${deposito.tipoElemento}" />'" <c:if test="${param.idasignabledestino == deposito.id}">checked</c:if>>
										</c:if>
									</display:column>
									<display:column titleKey="archigest.archivo.nombre" property="nombre" />
									<display:column titleKey="archigestarchivo.deposito.huecosDisponibles" >
										<c:out value="${deposito.ocupacionDeposito.huecosLibres}" />
									</display:column>
								</display:table>
							</c:when>
							<c:otherwise>
								<div class="separador5">&nbsp;</div>
								<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left;margin-left:auto;margin-right:auto">
									<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
									<fmt:message key="archigest.archivo.deposito.msgNoDepositoUbicacionConEspacio">
										<fmt:param value="${vRelacion.ubicacion.nombre}"/>
									</fmt:message>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="separador5">&nbsp;</div>
					</tiles:put>
				</tiles:insert>
				</html:form>
			</c:when>
			<c:otherwise>
				<div class="separador5">&nbsp;</div>
				<div class="bloque" style="color:red;background-color:#F0F0F2;padding:3px;text-align:left;">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<fmt:message key="archigest.archivo.deposito.msgNoDepositoUbicacionConEspacio">
						<fmt:param value="${vRelacion.ubicacion.nombre}"/>
					</fmt:message>
				</div>
			</c:otherwise>
		</c:choose>
	</tiles:put>
</tiles:insert>