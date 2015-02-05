<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			
			<security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ORGANO_REMITENTE}">
			<c:if test="${vRelacion.puedeSerEnviada}">
<%--ENVIAR --%>
				<TD>
					<a class="etiquetaAzul12Bold" href="javascript:enviarRelacion()">
						 <html:img titleKey="archigest.archivo.enviar" altKey="archigest.archivo.enviar" page="/pages/images/enviar.gif" styleClass="imgTextMiddle"/>
						 &nbsp;<bean:message key="archigest.archivo.enviar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
			</c:if>
			</security:permissions>

			<security:permissions action="${appConstants.transferenciaActions.ELABORACION_INGRESOS_DIRECTOS}">
				<%--SELECCIONAR UBICACION INGRESO --%>
				<TD>				
				<c:choose>	
					<c:when test="${vRelacion.puedeSerValidadoIngreso}">						
							<c:url var="urlAceptar" value="/action/gestionRelaciones">
								<c:param name="method" value="validarRelacion"/>
								<c:param name="idRelacion" value="${vRelacion.id}"/>
							</c:url>
							<script>
								function ValidarIngreso() {
									if (window.top.showWorkingDiv) {
										var title = '<bean:message key="archigest.archivo.operacion.realizandoValidacion"/>';
										var message = '<bean:message key="archigest.archivo.operacion.msgRealizandoValidacionIngreso"/>';
										var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
										window.top.showWorkingDiv(title, message, message2);
									}
									
									window.location = '<c:out value="${urlAceptar}" escapeXml="false" />';
								}
							</script>						
							<a class="etiquetaAzul12Bold" href="javascript:ValidarIngreso();" escapeXml="false"/>
								<html:img page="/pages/images/validar.gif" altKey="archigest.archivo.deposito.validarIngreso" titleKey="archigest.archivo.deposito.validarIngreso" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.deposito.validarIngreso"/>
							</a>						
					</c:when>								
					<c:otherwise>	
						<c:if test="${vRelacion.puedeSerEnviadaSeleccionarUbicacionIngreso}">										
							<c:url var="modificarUbicacionURL" value="/action/gestionRelaciones">
								<c:param name="method" value="enviarIngresoSeleccionUbicacion" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${modificarUbicacionURL}" escapeXml="false"/>">
									<html:img titleKey="archigest.archivo.fondos.seleccionarUbicacion.ingreso.directo" altKey="archigest.archivo.fondos.seleccionarUbicacion.ingreso.directo" page="/pages/images/cambiarUbicacion.gif" styleClass="imgTextMiddle"/>
								 	&nbsp;<bean:message key="archigest.archivo.fondos.seleccionarUbicacion.ingreso.directo"/>
							</a>
						</c:if>						
					</c:otherwise>													
				</c:choose>	
				</TD>			
				<TD width="10">&nbsp;</TD>
			</security:permissions>
			<%--RECIBIR --%>
			<c:if test="${vRelacion.puedeSerRecibida}">
				<security:permissions action="${appConstants.transferenciaActions.RECIBIR_RELACION}">
<%--RECIBIR --%>
				<TD>
					<c:url var="urlRecibir" value="/action/recepcionRelaciones">
						<c:param name="method" value="recibirrelacion"/>
						<c:param name="codigo" value="${vRelacion.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${urlRecibir}" escapeXml="false"/>' >
						 <html:img titleKey="archigest.archivo.recibir" altKey="archigest.archivo.recibir" page="/pages/images/recibir.gif" styleClass="imgTextMiddle"/>
						 &nbsp;<bean:message key="archigest.archivo.recibir"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
<%--RECHAZAR --%>
				<TD>
					<c:url var="urlRechazar" value="/action/recepcionRelaciones">
						<c:param name="method" value="initRechazarRelacion"/>
						<c:param name="codigo" value="${vRelacion.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${urlRechazar}" escapeXml="false"/>' >
						 <html:img titleKey="archigest.archivo.rechazar" altKey="archigest.archivo.rechazar" page="/pages/images/rechazar.gif" styleClass="imgTextMiddle"/>
						 &nbsp;<bean:message key="archigest.archivo.rechazar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>
			
			<c:if test="${vRelacion.puedeSerCotejada}">
				<security:permissions action="${appConstants.transferenciaActions.COTEJAR_RELACION}">
<%--COTEJAR Y SIGNATURAR --%>
				<TD>
					<c:url var="urlCotejar" value="/action/cotejoysignaturizacionAction">
						<c:param name="method" value="verCajas"/>
						<c:param name="codigoseleccionada" value="${vRelacion.id}"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${urlCotejar}" escapeXml="false"/>' >
						<html:img page="/pages/images/cotejar.gif"
							titleKey="archigest.archivo.transferencias.cotejarSignaturar" 
							altKey="archigest.archivo.transferencias.cotejarSignaturar" 
							styleClass="imgTextMiddle"/>
						
						<c:choose>
							<c:when test="${vRelacion.archivoReceptor.tiposignaturacion == appConstants.transferencias.tiposSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO.identificador}">
								<bean:message key="archigest.archivo.transferencias.cotejar"/>									
							</c:when>
							<c:when test="${vRelacion.sinDocsFisicos}">
								<bean:message key="archigest.archivo.transferencias.cotejar"/>									
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.cotejarSignaturar"/>															
							</c:otherwise>
						</c:choose>
						

					</a>
				</TD>
				<TD width="15">&nbsp;</TD>
				</security:permissions>
			</c:if>
			<c:if test="${vRelacion.permitidaFinalizacionCorreccion}">
<%--FINALIZAR CORRECCION DE ERRORES --%>
			<TD>
				<c:url var="finalizarCorreccionURL" value="/action/gestionRelaciones">
					<c:param name="method" value="finalizarCorreccion"/>
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}"/>
				</c:url>

					<a class="etiquetaAzul12Bold" href="<c:out value="${finalizarCorreccionURL}" escapeXml="false"/>">
						<html:img titleKey="archigest.archivo.transferencias.finalizarCorreccion" altKey="archigest.archivo.transferencias.finalizarCorreccion" page="/pages/images/finCorrecion.gif" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.transferencias.finalizarCorreccion"/>
					</a>
				</TD>
				<TD width="15">&nbsp;</TD>
			</c:if>

			
			<c:if test="${vRelacion.permitidaImpresionCartelasDefinitivas}">
				<security:permissions action="${appConstants.transferenciaActions.IMPRESION_CARTELAS_DEFINITIVAS}">
<%--GENERAR CARTELAS EN ARCHIVO RECEPTOR --%>				
				<TD>
					<c:url var="urlImprimir" value="/action/gestionRelaciones">
						<c:param name="method" value="selCartelas"/>
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
						<html:img titleKey="archigest.archivo.transferencias.generarCartelas" altKey="archigest.archivo.transferencias.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
						 &nbsp;<bean:message key="archigest.archivo.transferencias.generarCartelas"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>
			
			<c:if test="${vRelacion.puedeSerValidada}">
				<security:permissions action="${appConstants.transferenciaActions.VALIDAR_RELACION}">
				<%--VALIDAR --%>				
				<c:url var="verEstadoValidacionURL" value="/action/gestionRelaciones">
					<c:param name="method" value="verEstadoValidacion" />
					<c:param name="idRelacion" value="${vRelacion.id}" />
				</c:url>
					<TD>
						<a class="etiquetaAzul12Bold" href="<c:out value="${verEstadoValidacionURL}" escapeXml="false"/>">
							<html:img titleKey="archigest.archivo.transferencias.validar" altKey="archigest.archivo.transferencias.validar" page="/pages/images/validar.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.transferencias.validar"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>

			
			<c:if test="${vRelacion.reservadeposito == appConstants.transferencias.reservaPrevision.PENDIENTE.identificador}">
				<security:permissions action="${appConstants.transferenciaActions.GESTION_SOLICITUD_RESERVA}">
<%--GESTIONAR RESERVA --%>				
				<c:url var="gestionSolicitudReservaURL" value="/action/gestionReservaRelaciones">
					<c:param name="method" value="vernavegador" />
					<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
				</c:url>
					<TD>
						<a class="etiquetaAzul12Bold" href="<c:out value="${gestionSolicitudReservaURL}" escapeXml="false"/>">
							<html:img titleKey="archigest.archivo.transferencias.gestionarReserva" altKey="archigest.archivo.transferencias.gestionarReserva" page="/pages/images/reservar.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.transferencias.gestionarReserva"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</security:permissions>
			</c:if>
			
			<c:choose>
				<c:when test="${vRelacion.isIngresoDirecto}">
					<c:if test="${vRelacion.puedeSerUbicada}">
<%--UBICAR INGRESO DIRECTO--%>				
						<c:url var="ubicarRelacionURL" value="/action/gestionUbicacionRelaciones">
							<c:param name="method" value="comprobarreserva" />
							<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
						</c:url>
							<TD>
								<script>							
									function ubicarRelacion() {
										if (window.top.showWorkingDiv) {
											var title = '<bean:message key="archigest.archivo.operacion.realizandoCHuecos"/>';
											<c:choose>
												<c:when test="${vRelacion.isIngresoDirecto}">	
													var message = '<bean:message key="archigest.archivo.operacion.msgRealizandoUbicacionIngreso"/>';				
												</c:when>
												<c:otherwise>
													var message = '<bean:message key="archigest.archivo.operacion.realizandoCHuecos"/>';
												</c:otherwise>
											</c:choose>
											window.top.showWorkingDiv(title, message);
										}
										window.location = '<c:out value="${ubicarRelacionURL}" escapeXml="false" />';
									}
								</script>
									<a class="etiquetaAzul12Bold" href="javascript:ubicarRelacion();" />
									<html:img titleKey="archigest.archivo.deposito.ubicarRelacion" altKey="archigest.archivo.deposito.ubicarRelacion" page="/pages/images/ubicar.gif" styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.deposito.ubicarRelacion"/>
								</a>
							</TD>
							<TD width="10">&nbsp;</TD>
					</c:if>			
				</c:when>
				<c:otherwise>
					<c:if test="${vRelacion.puedeSerUbicada}">
						<security:permissions action="${appConstants.transferenciaActions.UBICAR_RELACION}">
<%--UBICAR --%>				
						<c:url var="ubicarRelacionURL" value="/action/gestionUbicacionRelaciones">
							<c:param name="method" value="comprobarreserva" />
							<c:param name="idrelacionseleccionada" value="${vRelacion.id}" />
						</c:url>
							<TD>
								<script>
									function ubicarRelacion() {
										if (window.top.showWorkingDiv) {
											var title = '<bean:message key="archigest.archivo.operacion.realizandoCHuecos"/>';
											<c:choose>
												<c:when test="${vRelacion.isIngresoDirecto}">	
													var message = '<bean:message key="archigest.archivo.operacion.msgRealizandoUbicacionIngreso"/>';				
												</c:when>
												<c:otherwise>
													var message = '<bean:message key="archigest.archivo.operacion.realizandoCHuecos"/>';
												</c:otherwise>
											</c:choose>
											window.top.showWorkingDiv(title, message);
										}
										window.location = '<c:out value="${ubicarRelacionURL}" escapeXml="false" />';
									}
								</script>
									<a class="etiquetaAzul12Bold" href="javascript:ubicarRelacion();" />
									<html:img titleKey="archigest.archivo.deposito.ubicarRelacion" altKey="archigest.archivo.deposito.ubicarRelacion" page="/pages/images/ubicar.gif" styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.deposito.ubicarRelacion"/>
								</a>
							</TD>
							<TD width="10">&nbsp;</TD>
						</security:permissions>
					</c:if>	
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${vRelacion.isIngresoDirecto}">
					<c:if test="${vRelacion.permitidaModificacionUbicacion}">
<%--MODIFICAR UBICACION INGRESO DIRECTO --%>				
						<c:url var="modificarUbicacionURL" value="/action/gestionRelaciones">
						<c:param name="method" value="modificarubicacion" />
						</c:url>
						<TD>
							<a class="etiquetaAzul12Bold" href="<c:out value="${modificarUbicacionURL}" escapeXml="false"/>">
								<html:img titleKey="archigest.archivo.transferencias.cambiarUbicacion" altKey="archigest.archivo.transferencias.cambiarUbicacion" page="/pages/images/cambiarUbicacion.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.transferencias.cambiarUbicacion"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
					</c:if>				
				</c:when>
				<c:otherwise>
					<c:if test="${vRelacion.permitidaModificacionUbicacion && (not vRelacion.signaturacionAsociadaHuecoYSignaturaSolicitable)}">
						<security:permissions action="${appConstants.transferenciaActions.MODIFICAR_UBICACION}">
<%--MODIFICAR UBICACION --%>				
						<c:url var="modificarUbicacionURL" value="/action/gestionRelaciones">
						<c:param name="method" value="modificarubicacion" />
						</c:url>
						<TD>
							<a class="etiquetaAzul12Bold" href="<c:out value="${modificarUbicacionURL}" escapeXml="false"/>">
								<html:img titleKey="archigest.archivo.transferencias.cambiarUbicacion" altKey="archigest.archivo.transferencias.cambiarUbicacion" page="/pages/images/cambiarUbicacion.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.transferencias.cambiarUbicacion"/>
							</a>
						</TD>
						<TD width="10">&nbsp;</TD>
						</security:permissions>
					</c:if>
				</c:otherwise>
			</c:choose>
				
			
			<c:if test="${vRelacion.permitidoVerInformeRelacion}">
<%--INFORME DE RELACIÓN --%>				
				<td>
					<c:url var="URL" value="/action/informeRelacion">
						<c:param name="idRelacion" value="${vRelacion.id}" />
			   		</c:url>
					<a class="etiquetaAzul12Bold" 
					   href="<c:out value="${URL}" escapeXml="false"/>"
					><html:img page="/pages/images/documentos/doc_pdf.gif" 
					        border="0" 
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
					        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.relacion"/></a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>
			<c:if test="${vRelacion.permitidoVerInformeDetallesRelacion }">
<%--INFORME DE Entrega (Detalles Relacion)--%>				
				<td>
					<c:url var="URL" value="/action/informeDetallesRelacion">
						<c:param name="idRelacion" value="${vRelacion.id}" />
			   		</c:url>
					<a class="etiquetaAzul12Bold" 
					   href="<c:out value="${URL}" escapeXml="false"/>"
					><html:img page="/pages/images/documentos/doc_pdf.gif" 
					        border="0" 
					        altKey="archigest.archivo.informe"
					        titleKey="archigest.archivo.informe"
					        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.detalles"/>&nbsp;<bean:message key="archigest.archivo.relacion"/></a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>

<%--JUSTIFICANTE DE INGRESO DIRECTO --%>			
			<c:if test="${vRelacion.permitidoVerJustificanteAltaUdocs}">				
				<td>
					<c:url var="URL" value="/action/justificanteAltaUDocs">
						<c:param name="idRelacion" value="${vRelacion.id}" />
			   		</c:url>
					<a class="etiquetaAzul12Bold" 
					   href="<c:out value="${URL}" escapeXml="false"/>"
					><html:img page="/pages/images/print.gif" 
					        border="0" 
					        altKey="archigest.archivo.justificanteAltaUdocs"
					        titleKey="archigest.archivo.justificanteAltaUdocs"
					        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.justificanteAltaUdocs"/></a>
				</td>
				<TD width="10">&nbsp;</TD>
			</c:if>
			
		   	<c:if test="${vRelacion.permitidoVerInformeCotejo}">		   		
<%--INFORME DE COTEJO--%>		   	
		   	<td>
				<c:url var="URL" value="/action/informeCotejo">
					<c:param name="idRelacion" value="${vRelacion.id}" />
		   		</c:url>
				<a class="etiquetaAzul12Bold" 
				   href="<c:out value="${URL}" escapeXml="false"/>"
				><html:img page="/pages/images/documentos/doc_pdf.gif" 
				        border="0" 
				        altKey="archigest.archivo.informe"
				        titleKey="archigest.archivo.informe"
				        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/>&nbsp;<bean:message key="archigest.archivo.cotejo"/></a>
			</td>
			<TD width="10">&nbsp;</TD>	
			</c:if>
			
<%--CERRAR --%>
			<TD>
				<c:url var="volverURL" value="/action/gestionRelaciones">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${volverURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/>
				</a>
			</TD>
		</TR>
		</TABLE>