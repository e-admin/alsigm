<%@page pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<br/>

<table class="Style11" width="95%" align="center">
	<tr height="10"><td colspan="6"></td></tr>
</table>

<table cols="6" class="Style2" width="95%" align="center">
	<tr height="5"><td colspan="6"></td></tr>
	<tr height="10" align="center" class="Style4">
		<td colspan="6"><b><fmt:message key="intercambioRegistral.detalle.titulo"/></b></td>
	</tr>
	<tr height="5"><td colspan="6"></td></tr>


	<c:if test="${asientoRegistral == null}" >
		<tr height="10" align="left" class="Style5">
		<td colspan="6"><fmt:message key="intercambioRegistral.detalle.error"/></td>
		</tr>
		<tr height="5"><td colspan="6"></td></tr>
	</c:if>


	<c:if test="${asientoRegistral != null}">

		<tr align="left" class="Style5">
			<td colspan="1">
				<b><i><fmt:message key="intercambioRegistral.detalle.identificadorIntercambio"/></i></b>
			</td>
			<td colspan="5">
				<c:if test="${asientoRegistral.identificadorIntercambio != null}">
				<c:out value="${asientoRegistral.identificadorIntercambio}"/>
				</c:if>
			</td>
		</tr>
		<tr height="10" align="left" class="Style5">
			<td colspan="1">
				<b><i><fmt:message key="intercambioRegistral.detalle.numExpedienteInicial"/></i></b>
			</td>
			<td colspan="2">
				<c:if test="${asientoRegistral.numeroExpediente != null}">
				<c:out value="${asientoRegistral.numeroExpediente}"/>
				</c:if>
			</td>
			<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.numRegistro"/></i></b></td>
			<td colspan="2">
				<c:if test="${asientoRegistral.numeroRegistro != null}">
				<c:out value="${asientoRegistral.numeroRegistro}"/>
				</c:if>
			</td>

		</tr>


	<c:if test="${asientoRegistral.codigoAsunto != null}">
		<tr height="10" align="left">
			<td colspan="2"><b><i><fmt:message key="intercambioRegistral.detalle.codigo.asunto"/></i></b></td>
			<td colspan="4"><c:out value="${asientoRegistral.codigoAsunto}"/></td>
		</tr>
	</c:if>

	<c:if test="${asientoRegistral.resumen != null && asientoRegistral.resumen != ''}">
		<tr height="10" align="left" class="Style5">
			<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.resumen"/></i></b></td>
			<td colspan="5"><c:out value="${asientoRegistral.resumen}"/></td>
		</tr>
	</c:if>

	<c:if test="${asientoRegistral.solicita !=null && asientoRegistral.solicita !=''}">
		<tr height="10" align="left" class="Style5">
			<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.solicita"/></i></b></td>
			<td colspan="5"><c:out value="${asientoRegistral.solicita}"></c:out></td>
		</tr>
	</c:if>

	<c:if test="${asientoRegistral.expone !=null && asientoRegistral.expone !=''}">
		<tr height="10" align="left" class="Style5">
			<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.expone"/></i></b></td>
			<td colspan="5"><c:out value="${asientoRegistral.expone}"/></td>
		</tr>
	</c:if>


	<tr height="20" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.tipoRegistro"/></i></b></td>
		<td colspan ="1">
		<c:if test="${asientoRegistral.tipoRegistro != null}">
			<fmt:message key="intercambioRegistral.detalle.tipoRegistro.${asientoRegistral.tipoRegistro.value}"/>
		</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.fechaRecepcion"/></i></b></td>
		<td colspan="1">
			<c:if test="${asientoRegistral.fechaRecepcion != null}">
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${asientoRegistral.fechaRecepcion}" />
			</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.fechaEnvio"/></i></b></td>
		<td colspan="1">
			<c:if test="${asientoRegistral.fechaEnvio != null}">
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${asientoRegistral.fechaEnvio}"/>
			</c:if>
		</td>
	</tr>


	<tr height="20" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.fechaRegistro"/></i></b></td>
		<td colspan="1">
		<c:if test="${asientoRegistral.fechaRegistro != null}">
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${asientoRegistral.fechaRegistro}" />
		</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.fechaRegistro.inicial"/></i></b></td>
		<td colspan="1">
		<c:if test="${asientoRegistral.fechaRegistroInicial != null}">
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${asientoRegistral.fechaRegistroInicial}" />
		</c:if>
		</td>
		<td colspan="2"></td>
	</tr>

	<tr height="5"><td colspan="6"></td></tr>

	<!-- Entidad Registral Origen -->

	<tr height="20" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.codigo.EntidadRegistral.origen"/></i></b></td>
		<td colspan="1">
		<c:if test="${asientoRegistral.codigoEntidadRegistralOrigen != null}">
			<c:out value="${asientoRegistral.codigoEntidadRegistralOrigen}"/>
		</c:if>
		</td>

		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.entidadRegistral.origen"/></i></b></td>
		<td colspan="3">
			<c:if test="${asientoRegistral.descripcionEntidadRegistralOrigen != null}">
			<c:out value="${asientoRegistral.descripcionEntidadRegistralOrigen}"/>
			</c:if>
		</td>
	</tr>



	<!-- Entidad Registral Inicio -->

	<tr height="20" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.codigoEntidad.registroInicial"/></i></b></td>
		<td colspan="1">
			<c:if test="${asientoRegistral.codigoEntidadRegistralInicio != null}">
			<c:out value="${asientoRegistral.codigoEntidadRegistralInicio}"/>
			</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.entidadRegistral.inicio"/></i></b></td>
		<td colspan="3">
			<c:if test="${asientoRegistral.descripcionEntidadRegistralInicio != null}">
			<c:out value="${asientoRegistral.descripcionEntidadRegistralInicio}"/>
			</c:if>
		</td>
	</tr>

	<!-- Unidad Tramitación Origen -->

	<tr height="10" align="left" class="Style5">
	<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.codigoUnidadTramitacion.origen"/></i></b></td>
	<td colspan="1">
		<c:if test="${asientoRegistral.codigoUnidadTramitacionOrigen != null}">
			<c:out value="${asientoRegistral.codigoUnidadTramitacionOrigen}"/>
		</c:if>
	</td>
	<td colspan="1"> <b><i><fmt:message key="intercambioRegistral.detalle.unidadTramitacion.origen"/></i></b></td>
	<td colspan="3">
		<c:if test="${asientoRegistral.descripcionUnidadTramitacionOrigen != null}">
		<c:out value="${asientoRegistral.descripcionUnidadTramitacionOrigen}"/>
		</c:if>
	</td>
	</tr>




	<!-- Entidad Registral Destino -->

	<tr height="10" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.codigoEntidadRegistral.destino"/></i></b></td>
		<td colspan="1">
		<c:if test="${asientoRegistral.codigoEntidadRegistralDestino != null}">
			<c:out value="${asientoRegistral.codigoEntidadRegistralDestino}"/>
		</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.entidadRegistral.destino"/></i></b></td>
		<td colspan="3">
		<c:if test="${asientoRegistral.descripcionEntidadRegistralDestino != null}">
			<c:out value="${asientoRegistral.descripcionEntidadRegistralDestino}"/>
		</c:if>
		</td>
	</tr>


	<!-- Unidad Tramitación Destino -->
	<tr height="10" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.codigo.unidadTramitacion.destino"/></i></b></td>
		<td colspan="1">
		<c:if test="${asientoRegistral.codigoUnidadTramitacionDestino != null}">
			<c:out value="${asientoRegistral.codigoUnidadTramitacionDestino}"/>
		</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.unidadTramitacion.destino"/></i></b></td>
		<td colspan="3">
			<c:if test="${asientoRegistral.descripcionUnidadTramitacionDestino != null}">
			<c:out value="${asientoRegistral.descripcionUnidadTramitacionDestino}"/>
			</c:if>
		</td>
	</tr>


	<c:if test="${asientoRegistral.referenciaExterna != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="3"><b><i><fmt:message key="intercambioRegistral.detalle.referenciaExterna"/></i></b></td>
		<td colspan="3"><c:out value="${asientoRegistral.referenciaExterna}"/></td>
		</tr>
	</c:if>

	<c:if test="${asientoRegistral.numeroRegistroInicial != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.numeroRegistro.original"/></i></b></td>
		<td colspan="5"><c:out value="${asientoRegistral.numeroRegistroInicial}"/></td>
		</tr>
	</c:if>



	<tr height="10" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.tipoTransporte"/></i></b></td>
		<td colspan="2">
			<c:if test="${asientoRegistral.tipoTransporte != null}">
				<fmt:message key="intercambioRegistral.detalle.tipoTransporte.${asientoRegistral.tipoTransporte.value}"/>
			</c:if>
		</td>
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.numeroTransporte"/></i></b></td>
		<td colspan="2">
			<c:if test="${asientoRegistral.numeroTransporte != null}">
				<c:out value="${asientoRegistral.numeroTransporte}"/>
			</c:if>
		</td>
	</tr>



	<c:if test="${asientoRegistral.nombreUsuario != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="2"><b><i><fmt:message key="intercambioRegistral.detalle.nombreUsuario"/></i></b></td>
		<td colspan="2"><c:out value="${asientoRegistral.nombreUsuario}"/></td>
		</tr>
	</c:if>

	<c:if test="${asientoRegistral.contactoUsuario != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="2"><b><i><fmt:message key="intercambioRegistral.detalle.contactoUsuario"/></i></b></td>
		<td colspan="4"><c:out value="${asientoRegistral.contactoUsuario}"/></td>
		</tr>
	</c:if>


	<c:if test="${asientoRegistral.aplicacion != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="2"><b><i><fmt:message key="intercambioRegistral.detalle.aplicacion"/></i></b></td>
		<td colspan="2"><c:out value="${asientoRegistral.aplicacion}"/></td>
		</tr>
	</c:if>

	<c:if test="${asientoRegistral.descripcionTipoAnotacion != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="2"><b><i><fmt:message key="intercambioRegistral.detalle.tipoAnotacion"/></i></b></td>
		<td colspan="4"><c:out value="${asientoRegistral.descripcionTipoAnotacion}"/></td>
		</tr>
	</c:if>


	<c:if test="${asientoRegistral.documentacionFisica != null}">
		<tr height="10" align="left" class="Style5">
		<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.documentacionFisica"/></i></b></td>
		<td colspan="5"><fmt:message key="intercambioRegistral.tipoDocumentacionFisica.${asientoRegistral.documentacionFisica.value}"/></td>
		</tr>
	</c:if>

	<!-- Observaciones apunte -->
	<tr height="10" align="left" class="Style5">
	<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.observacionesApunte"/></i></b></td>
		<td colspan="5">
			<c:if test="${asientoRegistral.observacionesApunte != null}">
				<c:out value="${asientoRegistral.observacionesApunte}"/>
			</c:if>
		</td>
	</tr>





	<!-- Interesados -->
	<tr height="10"><td colspan="6"></td></tr>
	<tr height="5"  class="Style3"><td colspan="6"></td></tr>

	<tr height="10" align="left" class="Style5">
		<td colspan="6"><b><u><fmt:message key="intercambioRegistral.detalle.interesados.titulo"/></u></b></td>
	</tr>
	<c:if test="${empty asientoRegistral.interesados}">
		<tr height="10" align="left" class="Style5">
		<td colspan="6"><fmt:message key="intercambioRegistral.detalle.interesados.lista.vacia"/></td>
		</tr>
	</c:if>

	<c:if test="${!empty asientoRegistral.interesadosExReg}">

		<c:forEach var="interesado" items="${asientoRegistral.interesadosExReg}" varStatus="status">
			<tr height="10" align="left"><td colspan="6"></td></tr>
			<tr height="10" align="left" class="Style5">
				<td colspan="6"><b><fmt:message key="intercambioRegistral.detalle.interesados.informacion.interesado"/></b></td>
			</tr>
			<tr height="10" align="left" class="Style5">

				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.tipoDocumento"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.tipoDocumentoIdentificacionInteresado!=null}">
						<fmt:message key="intercambioRegistral.detalle.tipoDocumento.${interesado.tipoDocumentoIdentificacionInteresado.value}"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.numeroDocumento"/></i></b></td>
				<td colspan="3">
					<c:if test="${interesado.documentoIdentificacionInteresado!=null}">
					<c:out value="${interesado.documentoIdentificacionInteresado }"/>
					</c:if>
				</td>
			</tr>
			<c:if test="${interesado.razonSocialInteresado!=null}">
				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.razonSocial"/></i></b></td>
					<td colspan="5"><c:out value="${interesado.razonSocialInteresado }"/></td>
				</tr>
			</c:if>


			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.nombre"/></i></b></td>
				<td colspan="1">
				<c:if test="${interesado.nombreInteresado!=null}">
					<c:out value="${interesado.nombreInteresado }"/>
				</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.primerApellido"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.primerApellidoInteresado!=null}">
						<c:out value="${interesado.primerApellidoInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.segundoApellido"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.segundoApellidoInteresado!=null}">
						<c:out value="${interesado.segundoApellidoInteresado }"/>
					</c:if>
				</td>
			</tr>



			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.pais"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.nombrePaisInteresado!=null}">
						<c:out value="${interesado.nombrePaisInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.provincia"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.nombreProvinciaInteresado!=null}">
						<c:out value="${interesado.nombreProvinciaInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.municipio"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.nombreMunicipioInteresado!=null}">
					<c:out value="${interesado.nombreMunicipioInteresado }"/>
					</c:if>
				</td>
			</tr>

			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoPais"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.codigoPaisInteresado!=null}">
						<c:out value="${interesado.codigoPaisInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoProvincia"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.codigoProvinciaInteresado!=null}">
						<c:out value="${interesado.codigoProvinciaInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoMunicipio"/></i></b></td>
				<td colspan="1">
					<c:if test="${interesado.codigoMunicipioInteresado!=null}">
					<c:out value="${interesado.codigoMunicipioInteresado }"/>
					</c:if>
				</td>
			</tr>



			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.direccion"/></i></b></td>
				<td colspan="3">
					<c:if test="${interesado.direccionInteresado!=null}">
						<c:out value="${interesado.direccionInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoPostal"/></i></b></td>
				<td colspan="1">
				<c:if test="${interesado.codigoPostalInteresado!=null}">
					<c:out value="${interesado.codigoPostalInteresado }"/>
				</c:if>
				</td>
			</tr>



			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.correoElectronico"/></i></b></td>
				<td colspan="2">
					<c:if test="${interesado.correoElectronicoInteresado!=null}">
						<c:out value="${interesado.correoElectronicoInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.telefono"/></i></b></td>
				<td colspan="2">
					<c:if test="${interesado.telefonoInteresado!=null}">
						<c:out value="${interesado.telefonoInteresado }"/>
					</c:if>
				</td>
			</tr>



			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.direccionElectronica"/></i></b></td>
				<td colspan="2">
					<c:if test="${interesado.direccionElectronicaHabilitadaInteresado!=null}">
						<c:out value="${interesado.direccionElectronicaHabilitadaInteresado }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.canalComunicacion"/></i></b></td>
				<td colspan="2">
					<c:if test="${interesado.canalPreferenteComunicacionInteresado!=null}">
						<fmt:message key="intercambioRegistral.tipoNotificacion.${interesado.canalPreferenteComunicacionInteresado.value }"/>
					</c:if>
				</td>

			</tr>

			<tr height="10" align="left" class="Style5">
			<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.observaciones"/></i></b></td>
			<td colspan="5">
				<c:if test="${interesado.observaciones!=null}">
					<c:out value="${interesado.observaciones }"/>
				</c:if>
			</td>
			</tr>


			<tr height="10"><td colspan="6"></td></tr>
			<c:if test="${interesado.razonSocialRepresentante!=null || interesado.nombreRepresentante!=null}">
				<%-- Datos Representante --%>

				<tr height="10" align="left" class="Style5">
					<td colspan="6"><b><fmt:message key="intercambioRegistral.detalle.interesados.informacion.representante"/></b></td>
				</tr>



				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.tipoDocumento"/></i></b></td>
					<td colspan="2">
						<c:if test="${interesado.tipoDocumentoIdentificacionRepresentante!=null}">
							<fmt:message key="intercambioRegistral.detalle.tipoDocumento.${interesado.tipoDocumentoIdentificacionRepresentante.value}"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.numeroDocumento"/></i></b></td>
					<td colspan="2">
						<c:if test="${interesado.documentoIdentificacionRepresentante!=null}">
							<c:out value="${interesado.documentoIdentificacionRepresentante }"/>
						</c:if>
					</td>
				</tr>


				<c:if test="${interesado.razonSocialRepresentante!=null}">
					<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.razonSocial"/></i></b></td>
					<td colspan="5"><c:out value="${interesado.razonSocialRepresentante }"/></td>
					</tr>
				</c:if>


				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.nombre"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.nombreRepresentante!=null}">
							<c:out value="${interesado.nombreRepresentante }"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.primerApellido"/></i></b></td>
					<td colspan="1">
					<c:if test="${interesado.primerApellidoRepresentante!=null}">
						<c:out value="${interesado.primerApellidoRepresentante }"/>
					</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.segundoApellido"/></i></b></td>
					<td colspan="1"><c:if test="${interesado.segundoApellidoRepresentante!=null}">
						<c:out value="${interesado.segundoApellidoRepresentante}"/>
						</c:if>
					</td>
				</tr>


				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.pais"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.nombrePaisRepresentante!=null}">
							<c:out value="${interesado.nombrePaisRepresentante }"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.provincia"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.nombreProvinciaRepresentante!=null}">
							<c:out value="${interesado.nombreProvinciaRepresentante }"/>
						</c:if>
					</td>

					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.municipio"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.nombreMunicipioRepresentante!=null}">
							<c:out value="${interesado.nombreMunicipioRepresentante }"/>
						</c:if>
					</td>
				</tr>

				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoPais"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.codigoPaisRepresentante!=null}">
							<c:out value="${interesado.codigoPaisRepresentante }"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoProvincia"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.codigoProvinciaRepresentante!=null}">
							<c:out value="${interesado.codigoProvinciaRepresentante }"/>
						</c:if>
					</td>

					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoMunicipio"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.codigoMunicipioRepresentante!=null}">
							<c:out value="${interesado.codigoMunicipioRepresentante }"/>
						</c:if>
					</td>
				</tr>



				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.direccion"/></i></b></td>
					<td colspan="3">
						<c:if test="${interesado.direccionRepresentante!=null}">
							<c:out value="${interesado.direccionRepresentante}"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.codigoPostal"/></i></b></td>
					<td colspan="1">
						<c:if test="${interesado.codigoPostalRepresentante!=null}">
							<c:out value="${interesado.codigoPostalRepresentante }"/>
						</c:if>
					</td>
				</tr>



				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.correoElectronico"/></i></b></td>
					<td colspan="2">
						<c:if test="${interesado.correoElectronicoRepresentante!=null}">
							<c:out value="${interesado.correoElectronicoRepresentante }"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.telefono"/></i></b></td>
					<td colspan="2">
						<c:if test="${interesado.telefonoRepresentante!=null}">
							<c:out value="${interesado.telefonoRepresentante }"/>
						</c:if>
					</td>
				</tr>



				<tr height="10" align="left" class="Style5">
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.direccionElectronica"/></i></b></td>
					<td colspan="2">
						<c:if test="${interesado.direccionElectronicaHabilitadaRepresentante!=null}">
							<c:out value="${interesado.direccionElectronicaHabilitadaRepresentante}"/>
						</c:if>
					</td>
					<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.interesados.canalComunicacion"/></i></b></td>
					<td colspan="2">
						<c:if test="${interesado.canalPreferenteComunicacionRepresentante!=null}">
							<fmt:message key="intercambioRegistral.tipoNotificacion.${interesado.canalPreferenteComunicacionRepresentante.value }"/>
						</c:if>
					</td>
				</tr>
			</c:if>
			<c:if test="${not status.last}">
				<tr tabindex="-1" height="2" class="Style3" width="95%"><td colspan="6"></td></tr>
			</c:if>
		</c:forEach>
	</c:if>

	<!-- Información de los anexos -->
	<tr height="5" class="Style3"><td colspan="6"></td></tr>

	<tr height="10" class="Style5">
		<td colspan="6"><b><u><fmt:message key="intercambioRegistral.detalle.anexos.titulo"/></u></b></td>
	</tr>
	<tr tabindex="-1" height="5" width="95%"><td colspan="6"></td></tr>

	<c:if test="${empty asientoRegistral.anexos}">
		<tr height="10" align="left" class="Style5">
			<td colspan="6"><fmt:message key="intercambioRegistral.detalle.anexos.listaVacia"/></td>
		</tr>
	</c:if>

	<c:if test="${!empty asientoRegistral.anexos}">
		<c:forEach var="anexo" items="${asientoRegistral.anexos}" varStatus="status">
			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.anexos.nombreFichero"/></i></b></td>
				<td colspan="1"><a href="DescargaDocumentoIntercambioRegistral.do?idAnexo=${anexo.id}&idIntercambio=${asientoRegistral.id}" target="_blank">
					<c:out value="${anexo.nombreFichero}"/>
				</a>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.anexos.observaciones" /></i></b></td>
				<td colspan="3">
				<c:if test="${anexo.observaciones != null}">
					<c:out value="${anexo.observaciones}"/>
				</c:if>
				</td>
			</tr>
			<tr height="10" align="left" class="Style5">
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.anexos.tipoDocumento"/></i></b></td>
				<td  colspan="2">
					<c:if test="${anexo.tipoDocumento != null}">
						<fmt:message key="asientoRegitral.anexo.tipoDocumento.${anexo.tipoDocumento.value }"/>
					</c:if>
				</td>
				<td colspan="1"><b><i><fmt:message key="intercambioRegistral.detalle.anexos.validez"/></i></b></td>
				<td  colspan="2">
					<c:if test="${anexo.validezDocumento != null}">
						<fmt:message key="asientoRegitral.anexo.validezDocumento.${anexo.validezDocumento.value }"/>
					</c:if>
				</td>
			</tr>
			<!-- Se comprueba si el fichero es una firma de otro fichero/documento -->
			<c:if test="${anexo.identificadorFicheroFirmado != null}">
				<tr height="10" align="left" class="Style5">
					<td colspan="1">
						<b><i><fmt:message key="intercambioRegistral.detalle.firma.del.documento"/></i></b>
					</td>
					<td colspan="5">
						<!-- Se busca entre los anexos la información del fichero que ha sido firmado -->
						<c:forEach var="anexoFirmado" items="${asientoRegistral.anexos}" varStatus="status">
							<c:if test="${anexo.identificadorFicheroFirmado == anexoFirmado.id}">
								<a href="DescargaDocumentoIntercambioRegistral.do?idAnexo=${anexoFirmado.id}&idIntercambio=${asientoRegistral.id}" target="_blank">
									<c:out value="${anexoFirmado.nombreFichero}"/>
								</a>
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:if>
			<c:if test="${not status.last}">
				<tr tabindex="-1" height="10" width="95%"><td colspan="6"></td></tr>
			</c:if>
		</c:forEach>
	</c:if>
	<tr tabindex="-1" height="5" width="95%"><td colspan="6"></td></tr>


</c:if>
<tr tabindex="-1" height="5" class="Style3" width="95%"><td colspan="6"></td></tr>
</table>
