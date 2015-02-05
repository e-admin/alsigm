<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<c:set var="listaUdocsDevueltas" value="${sessionScope[appConstants.prestamos.LISTA_UDOCS_DEVUELTAS]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.solicitudes.udocs.devueltas" />
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<script>
					function imprimirEntrada() {
						//var WBwidth = screen.width-40;
						//var WBheight = screen.height-40;
						//var WBName = '<c:out value="SigiaWB_${appUser.id}" />';
						var URLEnvio = <%="\""+request.getContextPath() + "/action/devolverDetalles?method=imprimirentrada\""%>;
						//window.open(URLEnvio,WBName,'status=yes,resizable,toolbar=no,menubar=no,scrollbars=yes,width=1024,height=768');
						window.location=URLEnvio;
					}

					function verPrestamo(idprestamo){
						document.forms[0].method.value="verPrestamo";
						document.getElementById("idPrestamo").value = idprestamo;
						document.getElementById("devolucion").value = 'S';
						document.forms[0].submit();
					}

					function verSolicitud(idsolicitud, tiposolicitud){
						document.forms[0].method.value="verSolicitud";
						document.getElementById("idsolicitud").value = idsolicitud;
						document.getElementById("tiposolicitud").value = tiposolicitud;
						document.getElementById("devolucion").value = 'S';
						document.forms[0].submit();
					}
				</script>

			 	<c:set var="llamadaImprimir">javascript:imprimirEntrada()</c:set>
				<a class=etiquetaAzul12Bold href="<c:out value="${llamadaImprimir}" escapeXml="false"/>">
					<html:img
					page="/pages/images/print.gif" border="0"
					altKey="archigest.archivo.solicitudes.justificanteDevolucion"
					titleKey="archigest.archivo.solicitudes.justificanteDevolucion" styleClass="imgTextMiddle" />
	   				&nbsp;<bean:message key="archigest.archivo.solicitudes.justificanteDevolucion"/>&nbsp;&nbsp;
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="cancelURL" value="/action/devolverDetalles">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
					<html:img
						page="/pages/images/close.gif"
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>
	   		</TD>
		  </TR>
		 </TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<html:form action="/devolverDetalles">
			<html:hidden styleId="idPrestamo" property="idPrestamo"/>
			<html:hidden styleId="devolucion" property="devolucion"/>
			<html:hidden styleId="idsolicitud" property="idsolicitud"/>
			<html:hidden styleId="tiposolicitud" property="tiposolicitud"/>
			<input type="hidden" name="method"/>
			<div class="bloque">
				<div class="separador10">&nbsp;</div>
					<display:table name="pageScope.listaUdocsDevueltas"
						style="width:98%;margin-left:auto;margin-right:auto"
						id="udocDevuelta"
						decorator="solicitudes.prestamos.decorators.ViewDetalleBusquedaDecorator"
						pagesize="0"
						sort="external"
						defaultsort="0"
						requestURI='/action/devolverDetalles'
						export="false" excludedParams="method">
						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.solicitudes.noPrev"/>
						</display:setProperty>
						<display:column titleKey="archigest.archivo.signatura" property="signaturaudoc" maxLength="15"/>
						<display:column titleKey="archigest.archivo.solicitudes.nExp">
						<c:choose>
							<c:when test="${udocDevuelta.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
								<c:if test="${!empty udocDevuelta.nombreRangos}">
									<c:out value="${udocDevuelta.nombreRangos}"/><br/>
								</c:if>
								<c:out value="${udocDevuelta.expedienteudoc}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${udocDevuelta.expedienteudoc}"/>
							</c:otherwise>
						</c:choose>
						</display:column>
						<display:column titleKey="archigest.archivo.titulo" property="titulo" maxLength="25"/>
						<display:column title="" property="tipo"/>
						<display:column titleKey="archigest.archivo.solicitud" property="codigoSolicitud"/>
					</display:table>
				<div class="separador10">&nbsp;</div>
			</div>
		</html:form>
	</tiles:put>
</tiles:insert>
