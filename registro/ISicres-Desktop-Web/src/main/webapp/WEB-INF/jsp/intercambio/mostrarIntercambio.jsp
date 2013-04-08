<%@page pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<h2><fmt:message key="intercambioRegistral.detalle.titulo"/></h2>

<c:if test="${asientoRegistral == null}">
	<fmt:message key="intercambioRegistral.detalle.error"/>
</c:if>


<c:if test="${asientoRegistral != null}">

	<c:if test="${asientoRegistral.numeroRegistroInicial != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.numExpedienteInicial"/></strong>
		<c:out value="${asientoRegistral.numeroRegistroInicial}"/>
		<br/>
	</c:if>

	<!-- TODO:
		¿Qué campo es asientoRegistral.numeroRegisto?:
		Número de registro en la entidad registral origen
	 -->
	<c:if test="${asientoRegistral.numeroRegistro != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.numRegistro"/></strong>
		<c:out value="${asientoRegistral.numeroRegistro}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.codigoAsunto != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.codigo.asunto"/></strong>
		<c:out value="${asientoRegistral.codigoAsunto}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.resumen != null && asientoRegistral.resumen != ''}">
		<strong><fmt:message key="intercambioRegistral.detalle.resumen"/></strong>
		<c:out value="${asientoRegistral.resumen}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.solicita !=null && asientoRegistral.solicita !=''}">
		<strong><fmt:message key="intercambioRegistral.detalle.solicita"/></strong>
		<c:out value="${asientoRegistral.solicita}"></c:out>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.expone !=null && asientoRegistral.expone !=''}">
		<strong><fmt:message key="intercambioRegistral.detalle.expone"/></strong>
		<c:out value="${asientoRegistral.expone}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.tipoRegistro != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.tipoRegistro"/></strong>
		<fmt:message key="intercambioRegistral.detalle.tipoRegistro.${asientoRegistral.tipoRegistro.value}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.fechaRecepcion != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.fechaRecepcion"/></strong>
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${asientoRegistral.fechaRecepcion}" />
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.fechaEnvio != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.fechaEnvio"/></strong>
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${asientoRegistral.fechaEnvio}"/>
		<br/>
	</c:if>



	<br/>
	<!-- Entidad Registral Origen -->
	<c:if test="${asientoRegistral.codigoEntidadRegistralOrigen != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.codigo.EntidadRegistral.origen"/></strong>
		<c:out value="${asientoRegistral.codigoEntidadRegistralOrigen}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.descripcionEntidadRegistralOrigen != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.entidadRegistral.origen"/></strong>
		<c:out value="${asientoRegistral.descripcionEntidadRegistralOrigen}"/>
		<br/>
	</c:if>

	<br/>
	<!-- TODO: ¿Qué diferencia hay entre entidad registral inicio y entidad registral origen -->
	<!-- Entidad Registral Inicio -->
	<c:if test="${asientoRegistral.codigoEntidadRegistralInicio != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.codigoEntidad.registroInicial"/></strong>
		<c:out value="${asientoRegistral.codigoEntidadRegistralInicio}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.descripcionEntidadRegistralInicio != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.entidadRegistral.inicio"/></strong>
		<c:out value="${asientoRegistral.descripcionEntidadRegistralInicio}"/>
		<br/>
	</c:if>

	<br/>
	<!-- Unidad Tramitación Origen -->
	<c:if test="${asientoRegistral.codigoUnidadTramitacionOrigen != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.codigoUnidadTramitacion.origen"/></strong>
		<c:out value="${asientoRegistral.codigoUnidadTramitacionOrigen}"/>
		<br/>
	</c:if>
	<c:if test="${asientoRegistral.descripcionUnidadTramitacionOrigen != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.unidadTramitacion.origen"/></strong>
		<c:out value="${asientoRegistral.descripcionUnidadTramitacionOrigen}"/>
		<br/>
	</c:if>

	<br/>
	<!-- TODO: ¿A qué se corresponde el campo asientoRegistral.fechaRegistro?:
		Fecha y hora de registro en la entidad registral origen.
	 -->
	<c:if test="${asientoRegistral.fechaRegistro != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.fechaRegistro"/></strong>
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${asientoRegistral.fechaRegistro}" />
		<br/>
	</c:if>

	<!-- TODO: ¿A qué se corresponde el campo asientoRegistral.fechaRegistroInicial? -->
	<c:if test="${asientoRegistral.fechaRegistroInicial != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.fechaRegistro.inicial"/></strong>
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${asientoRegistral.fechaRegistroInicial}" />
		<br/>
	</c:if>

	<!-- Entidad Registral Destino -->
	<c:if test="${asientoRegistral.codigoEntidadRegistralDestino != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.codigoEntidadRegistral.destino"/></strong>
		<c:out value="${asientoRegistral.codigoEntidadRegistralDestino}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.descripcionEntidadRegistralDestino != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.entidadRegistral.destino"/></strong>
		<c:out value="${asientoRegistral.descripcionEntidadRegistralDestino}"/>
		<br/>
	</c:if>

	<!-- Unidad Tramitación Destino -->
	<c:if test="${asientoRegistral.codigoUnidadTramitacionDestino != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.codigo.unidadTramitacion.destino"/></strong>
		<c:out value="${asientoRegistral.codigoUnidadTramitacionDestino}"/>
		<br/>
	</c:if>
	<c:if test="${asientoRegistral.descripcionUnidadTramitacionDestino != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.unidadTramitacion.destino"/></strong>
		<c:out value="${asientoRegistral.descripcionUnidadTramitacionDestino}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.referenciaExterna != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.referenciaExterna"/></strong>
		<c:out value="${asientoRegistral.referenciaExterna}"/>
		<br/>
	</c:if>

	<!-- TODO: ¿A qué se corresponde el campo asientoRegistral.numeroExpediente?:
		Número de expediente objeto de la tramitación administrativa.
	 -->
	<c:if test="${asientoRegistral.numeroExpediente != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.numeroRegistro.original"/>:</strong>
		<c:out value="${asientoRegistral.numeroExpediente}"/>
		<br/>
	</c:if>


	<c:if test="${asientoRegistral.tipoTransporte != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.tipoTransporte"/></strong>
		<fmt:message key="intercambioRegistral.detalle.tipoTransporte.${asientoRegistral.tipoTransporte.value}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.numeroTransporte != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.numeroTransporte"/></strong>
		<c:out value="${asientoRegistral.numeroTransporte}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.nombreUsuario != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.nombreUsuario"/></strong>
		<c:out value="${asientoRegistral.nombreUsuario}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.contactoUsuario != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.contactoUsuario"/></strong>
		<c:out value="${asientoRegistral.contactoUsuario}"/>
		<br/>
	</c:if>




	<c:if test="${asientoRegistral.aplicacion != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.aplicacion"/></strong>
		<c:out value="${asientoRegistral.aplicacion}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.descripcionTipoAnotacion != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.tipoAnotacion"/></strong>
		<c:out value="${asientoRegistral.descripcionTipoAnotacion}"/>
		<br/>
	</c:if>


	<c:if test="${asientoRegistral.documentacionFisica != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.documentacionFisica"/></strong>
		<fmt:message key="intercambioRegistral.tipoDocumentacionFisica.${asientoRegistral.documentacionFisica.value}"/>
		<br/>
	</c:if>

	<c:if test="${asientoRegistral.observacionesApunte != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.observacionesApunte"/></strong>
		<c:out value="${asientoRegistral.observacionesApunte}"/>
		<br/>
	</c:if>

	<%--
	<c:if test="${asientoRegistral.indicadorPrueba != null}">
		<strong><fmt:message key="intercambioRegistral.detalle.indicadorPrueba"/>
		</strong>
		<c:out value="${asientoRegistral.indicadorPrueba}"/>
		<br/>
	</c:if>
	--%>


	<br/>
	<h3><fmt:message key="intercambioRegistral.detalle.anexos.titulo"/></h3>
	<br/>

	<c:if test="${empty asientoRegistral.anexos}">
		<fmt:message key="intercambioRegistral.detalle.anexos.listaVacia"/>
	</c:if>

	<c:if test="${!empty asientoRegistral.anexos}">

		<c:forEach var="anexo" items="${asientoRegistral.anexos}">
			<a href="DescargaDocumentoIntercambioRegistral.do?idAnexo=${anexo.id}&idIntercambio=${asientoRegistral.id}" target="_blank">
				<c:out value="${anexo.nombreFichero}"/>
			</a>
			<br/>
		</c:forEach>
	</c:if>


	<!-- Interesados -->
	<br/>
	<br/>

	<h3><fmt:message key="intercambioRegistral.detalle.interesados.titulo"/></h3>
	<br/>


	<c:if test="${empty asientoRegistral.interesados}">
		<fmt:message key="intercambioRegistral.detalle.interesados.lista.vacia"/>
	</c:if>

	<c:if test="${!empty asientoRegistral.interesados}">

		<c:forEach var="interesado" items="${asientoRegistral.interesados}">
			<h5><fmt:message key="intercambioRegistral.detalle.interesados.informacion.interesado"/></h5>

			<c:if test="${interesado.tipoDocumentoIdentificacionInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.tipoDocumento"/></strong>
				<fmt:message key="intercambioRegistral.detalle.tipoDocumento.${interesado.tipoDocumentoIdentificacionInteresado.value}"/>
				<br/>
			</c:if>

			<c:if test="${interesado.documentoIdentificacionInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.numeroDocumento"/></strong>
				<c:out value="${interesado.documentoIdentificacionInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.razonSocialInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.razonSocial"/></strong>
				<c:out value="${interesado.razonSocialInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.nombreInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.nombre"/></strong>
				<c:out value="${interesado.nombreInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.primerApellidoInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.primerApellido"/></strong>
				<c:out value="${interesado.primerApellidoInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.segundoApellidoInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.segundoApellido"/></strong>
				<c:out value="${interesado.segundoApellidoInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoPaisInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.codigoPais"/></strong>
				<c:out value="${interesado.codigoPaisInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoProvinciaInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.provincia"/></strong>
				<c:out value="${interesado.codigoProvinciaInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoMunicipioInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.municipio"/></strong>
				<c:out value="${interesado.codigoMunicipioInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.direccionInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.direccion"/></strong>
				<c:out value="${interesado.direccionInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoPostalInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.codigoPostal"/></strong>
				<c:out value="${interesado.codigoPostalInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.correoElectronicoInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.correoElectronico"/></strong>
				<c:out value="${interesado.correoElectronicoInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.telefonoInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.telefono"/></strong>
				<c:out value="${interesado.telefonoInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.direccionElectronicaHabilitadaInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.direccionElectronica"/></strong>
				<c:out value="${interesado.direccionElectronicaHabilitadaInteresado }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.canalPreferenteComunicacionInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.canalComunicacion"/></strong>
				<fmt:message key="intercambioRegistral.tipoNotificacion.${interesado.canalPreferenteComunicacionInteresado.value }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.observaciones!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.observaciones"/></strong>
				<c:out value="${interesado.observaciones }"/>
				<br/>
			</c:if>

			<br/>
			<%-- Datos Representante --%>

			<h5><fmt:message key="intercambioRegistral.detalle.interesados.informacion.representante"/></h5>

			<c:if test="${interesado.tipoDocumentoIdentificacionRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.tipoDocumento"/></strong>
				<fmt:message key="intercambioRegistral.detalle.tipoDocumento.${interesado.tipoDocumentoIdentificacionRepresentante.value}"/>
				<br/>
			</c:if>

			<c:if test="${interesado.documentoIdentificacionRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.numeroDocumento"/></strong>
				<c:out value="${interesado.documentoIdentificacionRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.razonSocialRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.razonSocial"/></strong>
				<c:out value="${interesado.razonSocialRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.nombreRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.nombre"/></strong>
				<c:out value="${interesado.nombreRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.primerApellidoRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.primerApellido"/></strong>
				<c:out value="${interesado.primerApellidoRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.segundoApellidoRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.segundoApellido"/></strong>
				<c:out value="${interesado.segundoApellidoRepresentante}"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoPaisRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.codigoPais"/></strong>
				<c:out value="${interesado.codigoPaisRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoProvinciaRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.provincia"/></strong>
				<c:out value="${interesado.codigoProvinciaRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoMunicipioRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.municipio"/></strong>
				<c:out value="${interesado.codigoMunicipioRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.direccionRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.direccion"/></strong>
				<c:out value="${interesado.direccionRepresentante}"/>
				<br/>
			</c:if>

			<c:if test="${interesado.codigoPostalRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.codigoPostal"/></strong>
				<c:out value="${interesado.codigoPostalRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.correoElectronicoRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.correoElectronico"/></strong>
				<c:out value="${interesado.correoElectronicoRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.telefonoRepresentante!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.telefono"/></strong>
				<c:out value="${interesado.telefonoRepresentante }"/>
				<br/>
			</c:if>

			<c:if test="${interesado.direccionElectronicaHabilitadaInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.direccionElectronica"/></strong>
				<c:out value="${interesado.direccionElectronicaHabilitadaInteresado}"/>
				<br/>
			</c:if>

			<c:if test="${interesado.canalPreferenteComunicacionInteresado!=null}">
				<strong><fmt:message key="intercambioRegistral.detalle.interesados.canalComunicacion"/></strong>
				<fmt:message key="intercambioRegistral.tipoNotificacion.${interesado.canalPreferenteComunicacionInteresado.value }"/>
				<br/>
			</c:if>

			<br/>

		</c:forEach>
	</c:if>


</c:if>
