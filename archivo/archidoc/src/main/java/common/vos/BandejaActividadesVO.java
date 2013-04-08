package common.vos;

import se.usuarios.AppUser;

/**
 * Valores a mostrar en la bandeja de actividades del usuario
 */
public class BandejaActividadesVO {

	AppUser usuario = null;

	int previsionesEnElaboracion = 0;
	int previsionesAGestionar = 0;
	int previsionesAceptadasORechazadas = 0;
	int relacionesEnElaboracion = 0;
	int relacionesAGestionar = 0;
	int relacionesFinalizadas = 0;
	int relacionesRechazadas = 0;
	int reservasPendientes = 0;
	int relacionesAUbicar = 0;
	int ingresosEnElaboracion = 0;
	int ingresosFinalizados = 0;
	int prestamosEnElaboracion = 0;
	int prestamosAGestionar = 0;
	int prestamosAEntregar = 0;
	int consultasEnElaboracion = 0;
	int consultasAGestionar = 0;
	int consultasAEntregar = 0;
	int seriesEnIdentificacion = 0;
	int solicitudesPendientes = 0;
	int solicitudesAGestionar = 0;
	int valoracionesEnElaboracion = 0;
	int valoracionesAGestionar = 0;
	int seleccionesEnElaboracion = 0;
	int documentosVitalesAGestionar = 0;
	int tareasAGestionar = 0;
	int tareasPendientes = 0;
	int prestamosAGestionarReserva = 0;
	int consultasAGestionarReserva = 0;
	int divisionesFSEnElaboracion = 0;
	int divisionesFSFinalizadas = 0;
	int documentacionUDocsRevisar = 0;
	int registrosAbiertos = 0;

	public BandejaActividadesVO(AppUser usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el número total de tareas pendientes.
	 * 
	 * @return Número total de tareas pendientes.
	 */
	public int getNumeroTotalTareas() {
		return previsionesEnElaboracion + previsionesAGestionar
				+ relacionesEnElaboracion + relacionesAGestionar
				+ reservasPendientes + relacionesAUbicar
				+ prestamosEnElaboracion + prestamosAGestionar
				+ prestamosAEntregar + consultasEnElaboracion
				+ consultasAGestionar + consultasAEntregar
				+ seriesEnIdentificacion + solicitudesPendientes
				+ solicitudesAGestionar + valoracionesEnElaboracion
				+ valoracionesAGestionar + seleccionesEnElaboracion
				+ documentosVitalesAGestionar + tareasAGestionar
				+ tareasPendientes + prestamosAGestionarReserva
				+ consultasAGestionarReserva + divisionesFSEnElaboracion
				+ registrosAbiertos;
	}

	//
	// /** Numero previsiones en elaboración por parte del usuario */
	// public int getNumPrevisionesEnElaboracion() {
	// Collection previsiones =
	// previsionesService.getPrevisionesEnElaboracion(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(previsiones))
	// return previsiones.size();
	// return 0;
	// }
	//
	// /** Numero de previsiones a gestionar por el usuario */
	// public int getNumPrevisionesAGestionar() {
	// try {
	// Collection previsiones =
	// previsionesService.getPrevisionesAGestionar(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(previsiones))
	// return previsiones.size();
	// } catch (ActionNotAllowedException anae) {
	// // El usuario no dispone de permisos de gestión de previsiones
	// }
	// return 0;
	// }
	//
	// /** Numero de relaciones de entrega que el usuario tiene en elaboración
	// */
	// public int getNumRelacionesEnElaboracion(){
	// Collection relaciones =
	// relacionesService.getRelacionesEnElaboracionXUser(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(relaciones))
	// return relaciones.size();
	// return 0;
	// }
	//
	// /** Numero de relaciones a gestionar por parte del usuario */
	// public int getNumRelacionesAGestionar() {
	// try {
	// Collection relaciones =
	// relacionesService.getRelacionesAGestionar(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(relaciones))
	// return relaciones.size();
	// } catch (ActionNotAllowedException anae) {
	// // Usuario no puede realizar gestión de relaciones de entrega
	// }
	// return 0;
	// }
	//
	// /** Numero de reservas de espacio solicitadas a la espera de ser
	// gestionadas */
	// public int getNumRelacionesAGestionarReserva(){
	// try {
	// Collection relaciones = relacionesService.getRelacionesAReservar();
	// if (!CollectionUtils.isEmptyCollection(relaciones))
	// return relaciones.size();
	// } catch (ActionNotAllowedException anae) {
	// // Usuario no puede realizar gestión de reservas de espacio
	// }
	// return 0;
	// }
	//
	// /** Numero de relaciones de entrega a la espera de ser ubicadas */
	// public int getNumRelacionesAGestionarUbicacion(){
	// Collection relaciones = relacionesService.getRelacionesAUbicar();
	// if (!CollectionUtils.isEmptyCollection(relaciones))
	// return relaciones.size();
	// return 0;
	// }
	//
	// /** Numero de prestamos que el usuario tiene solicitados */
	// public int getNumPrestamosEnElaboracion(){
	// Collection prestamos =
	// prestamosService.getPrestamosXUsuarioSolicitante(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(prestamos))
	// return prestamos.size();
	// return 0;
	// }
	//
	// /** Numero de prestamos solicitados y a la espera de ser tratados */
	// public int getNumPrestamosAGestionar(){
	// Collection prestamos = prestamosService.obtenerListadoGestionar() ;
	// if (!CollectionUtils.isEmptyCollection(prestamos))
	// return prestamos.size();
	// return 0;
	// }
	//
	// /** Numero de prestamos autorizados que pueden por tanto ser entregados
	// */
	// public int getNumPrestamosAEntregar(){
	// Collection prestamos = prestamosService.obtenerListadoEntregar();
	// if (!CollectionUtils.isEmptyCollection(prestamos))
	// return prestamos.size();
	// return 0;
	// }
	//
	// /** Numero de consultas que el usuario tiene solicitadas */
	// public int getNumConsultasEnElaboracion(){
	// Collection consultas =
	// consultasService.getConsultasXUsuarioConsultor(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(consultas))
	// return consultas.size();
	// return 0;
	// }
	//
	// /** Numero de consultas solicitadas a la espera de ser gestionadas */
	// public int getNumConsultasAGestionar(){
	// Collection consultas =
	// consultasService.getListadoConsultasGestionar(serviceClient);
	// if (!CollectionUtils.isEmptyCollection(consultas))
	// return consultas.size();
	// return 0;
	// }
	//
	// /** Numero de consultas autorizadas y por tando que pueden ser entregadas
	// */
	// public int getNumConsultasAEntregar(){
	// Collection consultas = consultasService.obtenerListadoEntregar();
	// if (!CollectionUtils.isEmptyCollection(consultas))
	// return consultas.size();
	// return 0;
	// }
	//
	// /** Numero de series que el usuario tiene en identificación */
	// public int getNumSeriesEnIdentificacion() {
	// Collection seriesEnIdentificacion =
	// seriesService.getSeriesEnIdentificacion(serviceClient.getId());
	// return CollectionUtils.collectionSize(seriesEnIdentificacion);
	// }
	//
	// /** Solicitudes de autorización para la gestión de series que han sido
	// realizadas por el usuario */
	// public int getNumSolicitudesEnElaboracion() {
	// Collection solicitudes =
	// seriesService.getSolicitudesGestor(serviceClient.getId());
	// if (!CollectionUtils.isEmptyCollection(solicitudes))
	// return solicitudes.size();
	// return 0;
	// }
	//
	// /** Solicitudes de autorización para la gestión de series a la espera de
	// ser gestionadas **/
	// public int getNumSolicitudesAGestionar() {
	// Collection solicitudes = seriesService.getSolicitudesAGestionar();
	// if (!CollectionUtils.isEmptyCollection(solicitudes))
	// return solicitudes.size();
	// return 0;
	// }
	//
	// /** Numero de valoraciones que el usuario tiene en elaboracion */
	// public int getNumValoracionesEnElaboracion() {
	// Collection valoracionesEnElaboracion =
	// valoracionService.getValoracionesEnElaboracion(serviceClient.getId());
	// return CollectionUtils.collectionSize(valoracionesEnElaboracion);
	// }
	//
	// /** Numero de valoraciones en elaboración a la espera de ser gestionadas
	// */
	// public int getNumValoracionesAGestionar() {
	// Collection valoracionesAGestionar =
	// valoracionService.getValoracionesAGestionar(serviceClient.getId());
	// return CollectionUtils.collectionSize(valoracionesAGestionar);
	// }
	//
	// /** Numero de selecciones en elaboración */
	// public int getNumSeleccionesEnElaboracion() {
	// Collection seleccionesEnElaboracion =
	// seleccionService.getEliminacionesEnElaboracion(serviceClient.getId());
	// return CollectionUtils.collectionSize(seleccionesEnElaboracion);
	// }
	public int getConsultasEnElaboracion() {
		return consultasEnElaboracion;
	}

	public void setConsultasEnElaboracion(int consultaEnElaboracion) {
		this.consultasEnElaboracion = consultaEnElaboracion;
	}

	public int getConsultasAEntregar() {
		return consultasAEntregar;
	}

	public void setConsultasAEntregar(int consultasAEntregar) {
		this.consultasAEntregar = consultasAEntregar;
	}

	public int getConsultasAGestionar() {
		return consultasAGestionar;
	}

	public void setConsultasAGestionar(int consultasAGestionar) {
		this.consultasAGestionar = consultasAGestionar;
	}

	public int getPrestamosAEntregar() {
		return prestamosAEntregar;
	}

	public void setPrestamosAEntregar(int prestamosAEntregar) {
		this.prestamosAEntregar = prestamosAEntregar;
	}

	public int getPrestamosAGestionar() {
		return prestamosAGestionar;
	}

	public void setPrestamosAGestionar(int prestamosAGestionar) {
		this.prestamosAGestionar = prestamosAGestionar;
	}

	public int getPrestamosEnElaboracion() {
		return prestamosEnElaboracion;
	}

	public void setPrestamosEnElaboracion(int prestamosEnElaboracion) {
		this.prestamosEnElaboracion = prestamosEnElaboracion;
	}

	public int getPrevisionesAGestionar() {
		return previsionesAGestionar;
	}

	public void setPrevisionesAGestionar(int previsionesAGestionar) {
		this.previsionesAGestionar = previsionesAGestionar;
	}

	public int getPrevisionesEnElaboracion() {
		return previsionesEnElaboracion;
	}

	public void setPrevisionesEnElaboracion(int previsionesEnElaboracion) {
		this.previsionesEnElaboracion = previsionesEnElaboracion;
	}

	public int getPrevisionesAceptadasORechazadas() {
		return previsionesAceptadasORechazadas;
	}

	public void setPrevisionesAceptadasORechazadas(int previsionesFinalizadas) {
		this.previsionesAceptadasORechazadas = previsionesFinalizadas;
	}

	public int getRelacionesAGestionar() {
		return relacionesAGestionar;
	}

	public void setRelacionesAGestionar(int relacionesAGestionar) {
		this.relacionesAGestionar = relacionesAGestionar;
	}

	public int getRelacionesAUbicar() {
		return relacionesAUbicar;
	}

	public void setRelacionesAUbicar(int relacionesAUbicar) {
		this.relacionesAUbicar = relacionesAUbicar;
	}

	public int getRelacionesEnElaboracion() {
		return relacionesEnElaboracion;
	}

	public void setRelacionesEnElaboracion(int relacionesEnElaboracion) {
		this.relacionesEnElaboracion = relacionesEnElaboracion;
	}

	public int getRelacionesFinalizadas() {
		return relacionesFinalizadas;
	}

	public void setRelacionesFinalizadas(int relacionesFinalizadas) {
		this.relacionesFinalizadas = relacionesFinalizadas;
	}

	public int getReservasPendientes() {
		return reservasPendientes;
	}

	public void setReservasPendientes(int reservasPendientes) {
		this.reservasPendientes = reservasPendientes;
	}

	public int getSeleccionesEnElaboracion() {
		return seleccionesEnElaboracion;
	}

	public void setSeleccionesEnElaboracion(int seleccionesEnElaboracion) {
		this.seleccionesEnElaboracion = seleccionesEnElaboracion;
	}

	public int getSeriesEnIdentificacion() {
		return seriesEnIdentificacion;
	}

	public void setSeriesEnIdentificacion(int seriesEnIdentificacion) {
		this.seriesEnIdentificacion = seriesEnIdentificacion;
	}

	public int getSolicitudesAGestionar() {
		return solicitudesAGestionar;
	}

	public void setSolicitudesAGestionar(int solicitudesAGestionar) {
		this.solicitudesAGestionar = solicitudesAGestionar;
	}

	public int getSolicitudesPendientes() {
		return solicitudesPendientes;
	}

	public void setSolicitudesPendientes(int solicitudesPendientes) {
		this.solicitudesPendientes = solicitudesPendientes;
	}

	public int getValoracionesAGestionar() {
		return valoracionesAGestionar;
	}

	public void setValoracionesAGestionar(int valoracionesAGestionar) {
		this.valoracionesAGestionar = valoracionesAGestionar;
	}

	public int getValoracionesEnElaboracion() {
		return valoracionesEnElaboracion;
	}

	public void setValoracionesEnElaboracion(int valoracionesEnElaboracion) {
		this.valoracionesEnElaboracion = valoracionesEnElaboracion;
	}

	public int getDocumentosVitalesAGestionar() {
		return documentosVitalesAGestionar;
	}

	public void setDocumentosVitalesAGestionar(int documentosVitalesAGestionar) {
		this.documentosVitalesAGestionar = documentosVitalesAGestionar;
	}

	public int getTareasAGestionar() {
		return tareasAGestionar;
	}

	public void setTareasAGestionar(int tareasAGestionar) {
		this.tareasAGestionar = tareasAGestionar;
	}

	public int getTareasPendientes() {
		return tareasPendientes;
	}

	public void setTareasPendientes(int tareasPendientes) {
		this.tareasPendientes = tareasPendientes;
	}

	public int getIngresosEnElaboracion() {
		return ingresosEnElaboracion;
	}

	public void setIngresosEnElaboracion(int ingresosEnElaboracion) {
		this.ingresosEnElaboracion = ingresosEnElaboracion;
	}

	public int getIngresosFinalizados() {
		return ingresosFinalizados;
	}

	public void setIngresosFinalizados(int ingresosFinalizados) {
		this.ingresosFinalizados = ingresosFinalizados;
	}

	public int getPrestamosAGestionarReserva() {
		return prestamosAGestionarReserva;
	}

	public void setPrestamosAGestionarReserva(int prestamosAGestionarReserva) {
		this.prestamosAGestionarReserva = prestamosAGestionarReserva;
	}

	public int getConsultasAGestionarReserva() {
		return consultasAGestionarReserva;
	}

	public void setConsultasAGestionarReserva(int consultasAGestionarReserva) {
		this.consultasAGestionarReserva = consultasAGestionarReserva;
	}

	public int getDivisionesFSEnElaboracion() {
		return divisionesFSEnElaboracion;
	}

	public void setDivisionesFSEnElaboracion(int divisionesFSEnElaboracion) {
		this.divisionesFSEnElaboracion = divisionesFSEnElaboracion;
	}

	public int getDivisionesFSFinalizadas() {
		return divisionesFSFinalizadas;
	}

	public void setDivisionesFSFinalizadas(int divisionesFSFinalizadas) {
		this.divisionesFSFinalizadas = divisionesFSFinalizadas;
	}

	public int getRelacionesRechazadas() {
		return relacionesRechazadas;
	}

	public void setRelacionesRechazadas(int relacionesRechazadas) {
		this.relacionesRechazadas = relacionesRechazadas;
	}

	public int getDocumentacionUDocsRevisar() {
		return documentacionUDocsRevisar;
	}

	public void setDocumentacionUDocsRevisar(int documentacionUDocsRevisar) {
		this.documentacionUDocsRevisar = documentacionUDocsRevisar;
	}

	public int getRegistrosAbiertos() {
		return registrosAbiertos;
	}

	public void setRegistrosAbiertos(int registrosAbiertos) {
		this.registrosAbiertos = registrosAbiertos;
	}
}