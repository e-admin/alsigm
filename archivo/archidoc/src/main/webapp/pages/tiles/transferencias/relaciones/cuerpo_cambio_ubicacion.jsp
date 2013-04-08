<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="vNoExisteEspacioDisponible" value="${sessionScope[appConstants.transferencias.NO_EXISTE_ESPACIO_DISPONIBLE]}"/>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<bean:struts id="mappingGestionRelaciones" mapping="/gestionRelaciones" />


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.modifUbiRelacion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		<c:if test="${!vNoExisteEspacioDisponible}">
			<TD>
				<script>
					function cambioUbicacion() {
						var form = document.forms[0];
						if (form && form.idubicacionseleccionada && elementSelected(form.idubicacionseleccionada))
						{
							if (window.top.showWorkingDiv) {
								var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
								var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
								window.top.showWorkingDiv(title, message);
							}

							form.submit();
						}
						else alert("<bean:message key='archigest.archivo.transferencias.selNuevaUbicacion'/>");
					}
				</script>
				<a class="etiquetaAzul12Bold" href="javascript:cambioUbicacion()" >
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			 <TD width="10">&nbsp;</TD>
		 </c:if>
		   <TD>
				<c:url var="cancelURI" value="/action/gestionRelaciones">
					<c:param name="method" value="goBack"  />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURI}" escapeXml="false"/>">
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
	<html:form action="/gestionRelaciones" >
		<input type="hidden" name="method" value="aceptarmodificarubicacion" >
		<input type="hidden" name="ubicacion">

		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<c:choose>
				<c:when test="${!vRelacion.isIngresoDirecto}">
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
				</c:when>
				<c:otherwise>
					<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.ingresoDirecto"/></tiles:put>
				</c:otherwise>
			</c:choose>
			<tiles:put name="visibleContent" direct="true">
			<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<c:choose>
					<c:when test="${!vRelacion.isIngresoDirecto}">
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
					</c:when>
					<c:otherwise>
						<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_ingreso.jsp" />
					</c:otherwise>
				</c:choose>
			</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.espacioNecesario"/></tiles:put>
		<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario">
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.transferencias.nUnidInstalacion"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vRelacion.numUIs}"/>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${vRelacion.formatoDestino.nombre}"/>
					</TD>
				</TR>
				<c:if test="${vRelacion.iddeposito!=null}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.ubicacionActual"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vRelacion.ubicacion.nombre}"/>
						</TD>
					</TR>
				</c:if>
			</TABLE>
		</tiles:put>
		</tiles:insert>

		<div class="separador5">&nbsp;</div>



		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
		<tiles:put name="blockTitle" direct="true">
			<c:choose>
				<c:when test="${vRelacion.iddeposito!=null}">
					<bean:message key="archigest.archivo.transferencias.msgModifUbiRelacion"/>
				</c:when>
				<c:otherwise>
					<bean:message key="archigest.archivo.transferencias.msgSeleccionUbicacion"/>
				</c:otherwise>
			</c:choose>
		</tiles:put>
		<tiles:put name="blockContent" direct="true">
		<c:set var="listaUbicaciones" value="${sessionScope[appConstants.transferencias.LISTA_EDIFICIOS_KEY]}" />
		<div id="listaEdificios" style="width:100%;margin:0px;-moz-box-sizing: border-box">
			<c:if test="${!empty listaUbicaciones}">
				<div class="separador5">&nbsp;</div>

				<script>
					function verRelacionesPendientesDeUbicar(idUbicacion) {
						var form = document.forms['<c:out value="${mappingGestionRelaciones.name}" />'];
						form.method.value = 'relacionesUbicacion';
						form.ubicacion.value = idUbicacion;
						form.submit();
					}
				</script>
				<TABLE cellpadding=2 cellspacing=0 class="formulario">
				<TR>
					<TD width="50px">&nbsp;
					</TD>
					<TD class="tdTitulo" width="60%">
						<bean:message key="archigest.archivo.deposito.edificio" />&nbsp;
					</TD>
					<TD class="tdTitulo" width="15%">
						<bean:message key="archigest.archivo.deposito.huecos" />
						<bean:message key="archigest.archivo.deposito.libres" />&nbsp;
					</TD>
					<TD class="tdTitulo" width="20%">
						<bean:message key="archigest.archivo.deposito.huecos" />
						<bean:message key="archigest.archivo.deposito.disponibles" />&nbsp;
					</TD>
				</TR>
				<c:set var="relacionesPendientesUbicar" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}" />
				<c:forEach var="edificio" items="${listaUbicaciones}">
				<TR>
					<TD width="50px" align="right">
						<a href="javascript:verRelacionesPendientesDeUbicar('<c:out value="${edificio.id}" />')">
						<html:img page="/pages/images/expand.gif" titleKey="archigest.archivo.transferencias.relaciones.pendientesUbicar" altKey="archigest.archivo.transferencias.relaciones.pendientesUbicar" styleClass="imgTextMiddleHand" />
						</a>
					</TD>
					<TD class="tdDatos" >
						<c:choose>
						<c:when test="${vRelacion.numeroUnidadesInstalacion <= edificio.numeroHuecosDisponibles and vRelacion.ubicacion.id != edificio.id}">
						<input type=radio name="idubicacionseleccionada" value="<c:out value="${edificio.id}"/>"
							<c:if test="${RelacionForm.idubicacionseleccionada == edificio.id}">
							checked
							</c:if>
							class="radio"/>
						</c:when>
						<c:otherwise>
							<html:img page="/pages/images/pixel.gif" width="20" styleClass="imgTextMiddle"/>
						</c:otherwise>
						</c:choose>
						<c:out value="${edificio.nombre}" />&nbsp;
					</TD>
					<TD class="tdDatos" >
						 <c:out value="${edificio.huecosLibres}"/>
					</TD>
					<TD class="tdDatos" >
						 <c:out value="${edificio.numeroHuecosDisponibles}"/>
					</TD>
				</TR>
				<c:if test="${relacionesPendientesUbicar != null && param.ubicacion == edificio.id}">
				<tr>
					<TD width="50">&nbsp;</TD>
					<td colspan="3">
						<div id="listaRelacionesUbicacion" class="bloque">
						<div class="separador5">&nbsp;</div>
						<display:table name="pageScope.relacionesPendientesUbicar"
							id="relacionPendienteUbicar"
							export="false"
							style="width:99%;margin-left:auto;margin-right:auto">

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.transferencias.msgNoRelacionesUbicacion"/>
							</display:setProperty>

							<display:column titleKey="archigest.archivo.transferencias.relacion" style="white-space: nowrap;">
								<c:url var="verRelacionURL" value="/action/gestionRelaciones">
									<c:param name="method" value="verrelacion" />
									<c:param name="idrelacionseleccionada" value="${relacionPendienteUbicar.id}" />
								</c:url>
								<c:out value="${relacionPendienteUbicar.codigoTransferencia}"/>
							</display:column>

							<display:column titleKey="archigest.archivo.transferencias.tipoTransf">
								<fmt:message key="archigest.archivo.transferencias.tipooperacion${relacionPendienteUbicar.tipooperacion}" />
							</display:column>

							<display:column titleKey="archigest.archivo.transferencias.estado">
								<fmt:message key="archigest.archivo.transferencias.estadoRelacion.${relacionPendienteUbicar.estado}" />
							</display:column>

							<display:column titleKey="archigest.archivo.transferencias.fEstado">
								<fmt:formatDate value="${relacionPendienteUbicar.fechaestado}" pattern="${FORMATO_FECHA}" />
							</display:column>

							<display:column titleKey="archigest.archivo.transferencias.relaciones.busqueda.organo">
								<c:out value="${relacionPendienteUbicar.organoRemitente.nombre}"  />
							</display:column>

							<display:column titleKey="archigest.archivo.transferencias.UndInstal" property="numeroUnidadesInstalacion"  style="width:20%" />


						</display:table>
						<div class="separador5">&nbsp;</div>
						<table cellpadding="0" cellspacing="0" width="100%"><tr><td align="right" bgcolor="#B0B0C6"  style="border-top:1px solid #000000">
						<a class=etiquetaAzul12Bold href="javascript:hideListaProcedimientos()">
							<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>&nbsp;
						</a>
						</td></tr></table>
						<script>
							function hideListaProcedimientos() {
								xGetElementById('listaRelacionesUbicacion').style.display='none';
							}
						</script>
						</div>
					</td>
				</tr>
				</c:if>
				</c:forEach>
				</TABLE>
				<c:if test="${vNoExisteEspacioDisponible}">
					<div class="separador5">&nbsp;</div>
					<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left">
						<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
						<bean:message key="archigest.archivo.transferencias.noUbicacionAsociada"/>
						<bean:message key="archigest.archivo.transferencias.ubicarRelacion.necesarioEspacio"/>
					</div>
					<div class="separador5">&nbsp;</div>
				</c:if>
			</c:if>
			<c:if test="${empty listaUbicaciones}">
				<div class="separador5">&nbsp;</div>
				<div class="bloque" style="width:99%;color:red;background-color:#F0F0F2;padding:3px;text-align:left">
					<html:img page="/pages/images/info.gif" style="float:left;margin-top:4px;margin-right:8px" />
					<bean:message key="archigest.archivo.transferencias.noUbicacionAsociada"/>
					<bean:message key="archigest.archivo.transferencias.ubicarRelacion.necesarioEspacio"/>
				</div>
				<div class="separador5">&nbsp;</div>
			</c:if>
		</div>
		</tiles:put>
		</tiles:insert>

		</html:form>
	</tiles:put>
</tiles:insert>
