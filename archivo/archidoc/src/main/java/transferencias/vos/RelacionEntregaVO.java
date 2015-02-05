package transferencias.vos;

import java.text.DecimalFormat;
import java.util.Date;

import transferencias.TipoTransferencia;
import transferencias.model.EstadoREntrega;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.bi.ServiceSession;
import common.db.DBUtils;
import common.util.DateUtils;

/**
 * Value Object con la informacion correspondiente a una relacion de entrega
 */
public class RelacionEntregaVO {

	public static final String SIN_PREVISION = "SIN PREVISION";

	String id;
	int tipotransferencia;
	int tipoprevision;
	String idprevision;
	String anoprevision;
	int ordenprevision;
	String iddetprevision;
	String idorganoremitente;
	String nombreorgano;
	String idarchivoreceptor;
	String ano;
	String idfondodestino;
	String codsistproductor;
	String nombresistproductor;
	String idprocedimiento;
	String nombreprocedimiento;
	String idseriedestino;
	String tituloserie;
	String codigoserie;
	int estado;
	Date fechaestado;
	Date fecharecepcion;
	String idusrgestorrem;
	String nombreusuario;
	String apellidosusuario;
	String idusrgestorarchivorec;
	String iddeposito;
	String uiendeposito;
	String devolucionui;
	String regentrada;
	String observaciones;
	int orden;
	String idformatoui;
	String tipoDocumental;
	int reservadeposito;
	String idelmtodreserva;
	String idtipoelmtodreserva;
	String iddescrorgproductor;
	String idarchivoremitente;
	String idfondoorigen;
	String idserieorigen;
	String correccionenarchivo = Constants.FALSE_STRING;
	String idNivelDocumental;
	String sindocsfisicos = Constants.FALSE_STRING;
	String idFicha;
	String conReencajado = Constants.FALSE_STRING;
	String idFormatoRe;

	private String nombreFormato;

	public boolean soloDocumentosElectronicos() {
		if (sindocsfisicos != null
				&& sindocsfisicos.equals(Constants.TRUE_STRING)) {
			return true;
		}
		return false;
	}

	/* Control de acciones sobre relaciones de entrega */
	boolean puedeSerModificada = false;
	boolean puedeSerEliminada = false;
	boolean puedeSerEnviada = false;
	boolean puedeSerRecibida = false;
	boolean puedeSerCotejada = false;
	boolean puedeSerSignaturada = false;
	boolean puedeSerRechazada = false;
	boolean puedeSerEnviadaSeleccionarUbicacionIngreso = false;
	boolean puedeSerValidadoIngreso = false;
	boolean permitidaFinalizacionCorreccion = false;
	boolean permitidaImpresionCartelasProvisionales = false;
	boolean permitidaImpresionCartelasDefinitivas = false;
	boolean puedeSerValidada = false;
	boolean puedeSerUbicada = false;
	boolean permitidaModificacionUbicacion = false;
	boolean permitidaModificacionAsignacionCajas = false;
	boolean permitidaAdicionExpedientes = false;
	boolean permitidaSustraccionExpedientes = false;
	boolean permitidoVerInformeCotejo = false;
	boolean permitidoVerInformeRelacion = false;
	boolean permitidoCorregirErrores = false;
	boolean permitirEditarDescripcionContenido;
	boolean permitidaAdicionDocsElectronicos = false;
	boolean permitidaSustraccionDocsElectronicos = false;
	boolean permitidaImportacionExpedientes = false;
	boolean permitidoBloqueoDesbloqueoExpedientes = false;
	boolean permitidoMostrarBloqueoDesbloqueoExpedientes = false;
	boolean permitidoActivarReencajado = false;
	boolean permitidoAccionesReencajado = false;
	boolean permitidoMarcarRevisada = false;

	/*
	 * boolean camposEnFichaChequeados = false; boolean expedienteEnFicha =
	 * false; boolean productorEnFicha = false;
	 */

	/**
	 * @return el puedeSerRechazada
	 */
	public boolean isPuedeSerRechazada() {
		return puedeSerRechazada;
	}

	/**
	 * @param puedeSerRechazada
	 *            el puedeSerRechazada a fijar
	 */
	public void setPuedeSerRechazada(boolean puedeSerRechazada) {
		this.puedeSerRechazada = puedeSerRechazada;
	}

	public boolean isPermitidaSustraccionExpedientes() {
		return permitidaSustraccionExpedientes;
	}

	public void setPermitidaSustraccionExpedientes(
			boolean permitidaSustraccionExpedientes) {
		this.permitidaSustraccionExpedientes = permitidaSustraccionExpedientes;
	}

	public String getIdelmtodreserva() {
		return this.idelmtodreserva;
	}

	public void setIdelmtodreserva(String idelmtodreserva) {
		this.idelmtodreserva = idelmtodreserva;
	}

	public int getReservadeposito() {
		return this.reservadeposito;
	}

	public void setReservadeposito(int reservaendeposito) {
		this.reservadeposito = reservaendeposito;
	}

	public RelacionEntregaVO() {
	}

	public String getNombresistproductor() {
		return this.nombresistproductor;
	}

	public void setNombresistproductor(String nombresistproductor) {
		this.nombresistproductor = nombresistproductor;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getIdfondodestino() {
		return idfondodestino;
	}

	public void setIdfondo(String idfondodestino) {
		this.idfondodestino = idfondodestino;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdprevision() {
		return idprevision;
	}

	public void setIdprevision(String idprevision) {
		this.idprevision = idprevision;
	}

	public String getIdprocedimiento() {
		return idprocedimiento;
	}

	public void setIdprocedimiento(String idproc) {
		this.idprocedimiento = idproc;
	}

	public String getIdseriedestino() {
		return idseriedestino;
	}

	public void setIdseriedestino(String idseriedestino) {
		this.idseriedestino = idseriedestino;
	}

	public String getCodsistproductor() {
		return codsistproductor;
	}

	public void setCodsistproductor(String consistproductor) {
		this.codsistproductor = consistproductor;
	}

	public String getDevolucionui() {
		return devolucionui;
	}

	public boolean isDevolucionui() {
		return DBUtils.getBooleanValue(uiendeposito);
	}

	public boolean getIsIngresoDirecto() {
		return tipotransferencia == TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador();
	}

	public void setDevolucionui(String devolucioncaja) {
		this.devolucionui = devolucioncaja;
	}

	public void setDevolucionui(boolean devolucioncaja) {
		this.devolucionui = DBUtils.getStringValue(devolucioncaja);
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaestado() {
		return fechaestado;
	}

	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	public Date getFecharecepcion() {
		return fecharecepcion;
	}

	public void setFecharecepcion(Date fecharecepcion) {
		this.fecharecepcion = fecharecepcion;
	}

	public String getIdarchivoreceptor() {
		return idarchivoreceptor;
	}

	public void setIdarchivoreceptor(String idarchivoreceptor) {
		this.idarchivoreceptor = idarchivoreceptor;
	}

	public String getIddeposito() {
		return iddeposito;
	}

	public void setIddeposito(String iddeposito) {
		this.iddeposito = iddeposito;
	}

	public String getIdorganoremitente() {
		return idorganoremitente;
	}

	public void setIdorganoremitente(String idorgremitente) {
		this.idorganoremitente = idorgremitente;
	}

	public String getIdusrgestorarchivorec() {
		return idusrgestorarchivorec;
	}

	public void setIdusrgestorarchivorec(String idusrgestorarchivorec) {
		this.idusrgestorarchivorec = idusrgestorarchivorec;
	}

	public String getIdusrgestorrem() {
		return idusrgestorrem;
	}

	public void setIdusrgestorrem(String idusrgestorrem) {
		this.idusrgestorrem = idusrgestorrem;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIddetprevision() {
		return iddetprevision;
	}

	public void setIddetprevision(String detalleprevision) {
		this.iddetprevision = detalleprevision;
	}

	public String getRegentrada() {
		return regentrada;
	}

	public void setRegentrada(String regentrada) {
		this.regentrada = regentrada;
	}

	public int getTipotransferencia() {
		return tipotransferencia;
	}

	public void setTipotransferencia(int tipotransferencia) {
		this.tipotransferencia = tipotransferencia;
	}

	public boolean isUiendeposito() {
		return DBUtils.getBooleanValue(uiendeposito);
	}

	public String getUiendeposito() {
		return this.uiendeposito;
	}

	public void setUiendeposito(String value) {
		this.uiendeposito = value;
	}

	public void setUiendeposito(boolean value) {
		this.uiendeposito = DBUtils.getStringValue(value);
	}

	// public boolean isEnviable() {
	// return getEstado() == EstadoREntrega.ABIERTA.getIdentificador();
	// }
	public String getIdformatoui() {
		return idformatoui;
	}

	public void setIdformatoui(String formato) {
		this.idformatoui = formato;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getTipoprevision() {
		return tipoprevision;
	}

	public void setTipoprevision(int tipoprevision) {
		this.tipoprevision = tipoprevision;
	}

	public boolean isCaducada(Date fechainittransfprevision,
			Date fechafintransfprevision) {
		// boolean ret = false;
		Date hoy = DateUtils.getFechaActual();
		boolean mayorOIgualFechaInicio = hoy
				.compareTo(fechainittransfprevision) >= 0;
		boolean menorOIgualFechaFin = hoy.compareTo(fechafintransfprevision) <= 0;
		return (mayorOIgualFechaInicio && menorOIgualFechaFin);
	}

	public boolean isConSignatura() {
		return tipotransferencia == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
				.getIdentificador();
	}

	public boolean isConErroresCotejo() {
		return estado == EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador();
	}

	public boolean isCorregidaErrores() {
		return estado == EstadoREntrega.CORREGIDA_ERRORES.getIdentificador();
	}

	public boolean isValidada() {
		return (estado == EstadoREntrega.VALIDADA.getIdentificador()
		 || 	isAutomatizada()
		);
	}

	public boolean isSignaturizada() {
		return estado == EstadoREntrega.SIGNATURIZADA.getIdentificador();
	}

	public boolean isEnviada() {
		return estado == EstadoREntrega.ENVIADA.getIdentificador();
	}

	public boolean isAbierta() {
		return estado == EstadoREntrega.ABIERTA.getIdentificador();
	}

	public boolean isAutomatizada() {
		return estado == EstadoREntrega.VALIDADA_AUTOMATIZADA.getIdentificador();
	}

	public boolean isRecibida() {
		return estado == EstadoREntrega.RECIBIDA.getIdentificador();
	}

	public boolean isRechazada() {
		return estado == EstadoREntrega.RECHAZADA.getIdentificador();
	}

	public boolean isEntreArchivos() {
		return (tipotransferencia == TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador());
	}

	public boolean isOrdinaria() {
		return (tipotransferencia == TipoTransferencia.ORDINARIA
				.getIdentificador());
	}

	static final String FORMATO_CODIGO_RELACION = "0000";
	static final DecimalFormat CODIGO_RELACION_FORMATER = new DecimalFormat(
			FORMATO_CODIGO_RELACION);

	public String getCodigoRelacion(ServiceSession ss) {
		// return new StringBuffer(getAno()).append("/")
		// .append(CODIGO_RELACION_FORMATER.format(getOrden())).toString();
		return CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(this, ss);
	}

	public String getCodigoRelacion() {
		return new StringBuffer(getAno()).append("/")
				.append(CODIGO_RELACION_FORMATER.format(getOrden())).toString();
	}

	/* Acceso a campos de control de acciones sobre la relacion de entrega */

	public boolean getPermitidaAdicionExpedientes() {
		return permitidaAdicionExpedientes;
	}

	public void setPermitidaAdicionExpedientes(
			boolean permitidaAdicionExpedientes) {
		this.permitidaAdicionExpedientes = permitidaAdicionExpedientes;
	}

	public boolean getPermitidaFinalizacionCorreccion() {
		return permitidaFinalizacionCorreccion;
	}

	public void setPermitidaFinalizacionCorreccion(
			boolean permitidaFinalizacionCorreccion) {
		this.permitidaFinalizacionCorreccion = permitidaFinalizacionCorreccion;
	}

	public boolean getPermitidaImpresionCartelasDefinitivas() {
		return permitidaImpresionCartelasDefinitivas;
	}

	public void setPermitidaImpresionCartelasDefinitivas(
			boolean permitidaImpresionCartelasDefinitivas) {
		this.permitidaImpresionCartelasDefinitivas = permitidaImpresionCartelasDefinitivas;
	}

	public boolean getPermitidaImpresionCartelasProvisionales() {
		return permitidaImpresionCartelasProvisionales;
	}

	public void setPermitidaImpresionCartelasProvisionales(
			boolean permitidaImpresionCartelasProvisionales) {
		this.permitidaImpresionCartelasProvisionales = permitidaImpresionCartelasProvisionales;
	}

	public boolean getPermitidaModificacionAsignacionCajas() {
		return permitidaModificacionAsignacionCajas;
	}

	public void setPermitidaModificacionAsignacionCajas(
			boolean permitidaModificacionAsignacionCajas) {
		this.permitidaModificacionAsignacionCajas = permitidaModificacionAsignacionCajas;
	}

	public boolean getPermitidaModificacionUbicacion() {
		return permitidaModificacionUbicacion;
	}

	public void setPermitidaModificacionUbicacion(
			boolean permitidaModificacionUbicacion) {
		this.permitidaModificacionUbicacion = permitidaModificacionUbicacion;
	}

	public boolean getPuedeSerCotejada() {
		return puedeSerCotejada;
	}

	public void setPuedeSerCotejada(boolean puedeSerCotejada) {
		this.puedeSerCotejada = puedeSerCotejada;
	}

	public boolean getPuedeSerSignaturada() {
		return puedeSerSignaturada;
	}

	public void setPuedeSerSignaturada(boolean puedeSerSignaturada) {
		this.puedeSerSignaturada = puedeSerSignaturada;
	}

	public boolean getPuedeSerEliminada() {
		return puedeSerEliminada;
	}

	public void setPuedeSerEliminada(boolean puedeSerEliminada) {
		this.puedeSerEliminada = puedeSerEliminada;
	}

	public boolean getPuedeSerEnviada() {
		return puedeSerEnviada;
	}

	public void setPuedeSerEnviada(boolean puedeSerEnviada) {
		this.puedeSerEnviada = puedeSerEnviada;
	}

	public boolean getPuedeSerModificada() {
		return puedeSerModificada;
	}

	public void setPuedeSerModificada(boolean puedeSerModificada) {
		this.puedeSerModificada = puedeSerModificada;
	}

	public boolean isPuedeSerEnviadaSeleccionarUbicacionIngreso() {
		return puedeSerEnviadaSeleccionarUbicacionIngreso;
	}

	public void setPuedeSerEnviadaSeleccionarUbicacionIngreso(
			boolean puedeSerEnviadaSeleccionarUbicacionIngreso) {
		this.puedeSerEnviadaSeleccionarUbicacionIngreso = puedeSerEnviadaSeleccionarUbicacionIngreso;
	}

	public boolean getPuedeSerRecibida() {
		return puedeSerRecibida;
	}

	public void setPuedeSerRecibida(boolean puedeSerRecibida) {
		this.puedeSerRecibida = puedeSerRecibida;
	}

	public boolean getPuedeSerValidada() {
		return puedeSerValidada;
	}

	public void setPuedeSerValidada(boolean puedeSerValidada) {
		this.puedeSerValidada = puedeSerValidada;
	}

	public boolean isPuedeSerUbicada() {
		return puedeSerUbicada;
	}

	public void setPuedeSerUbicada(boolean puedeSerUbicada) {
		this.puedeSerUbicada = puedeSerUbicada;
	}

	public boolean isPuedeSerValidadoIngreso() {
		return puedeSerValidadoIngreso;
	}

	public void setPuedeSerValidadoIngreso(boolean puedeSerValidadoIngreso) {
		this.puedeSerValidadoIngreso = puedeSerValidadoIngreso;
	}

	/**
	 * Solo se permite ver el informe de cotejo si su estado es CON ERRORES DE
	 * COTEJO
	 *
	 * @return
	 */
	public boolean isPermitidoVerInformeCotejo() {
		return isConErroresCotejo();
	}

	/**
	 * Solo se permite ver el informe de relación si su estado es recibida
	 *
	 * @return
	 */
	public boolean isPermitidoVerInformeRelacion() {
		if ((isEnviada() || isRecibida()) && !isSinDocsFisicos()) {
			return true;
		} else {
			return false;
		}
	}

	public void setPermitidoVerInformeCotejo(boolean permitidoVerInformeCotejo) {
		this.permitidoVerInformeCotejo = permitidoVerInformeCotejo;
	}

	public String getAnoprevision() {
		return anoprevision;
	}

	public void setAnoprevision(String anoprevision) {
		this.anoprevision = anoprevision;
	}

	public String getNombreorgano() {
		return nombreorgano;
	}

	public void setNombreorgano(String nombreorgano) {
		this.nombreorgano = nombreorgano;
	}

	public String getNombreprocedimiento() {
		return nombreprocedimiento;
	}

	public void setNombreprocedimiento(String nombreprocedimiento) {
		this.nombreprocedimiento = nombreprocedimiento;
	}

	public int getOrdenprevision() {
		return ordenprevision;
	}

	public void setOrdenprevision(int ordenprevision) {
		this.ordenprevision = ordenprevision;
	}

	public String getTituloserie() {
		return tituloserie;
	}

	public void setTituloserie(String tituloserie) {
		this.tituloserie = tituloserie;
	}

	/**
	 * @return Returns the codigoserie.
	 */
	public String getCodigoserie() {
		return codigoserie;
	}

	/**
	 * @param codigoserie
	 *            The codigoserie to set.
	 */
	public void setCodigoserie(String codigoserie) {
		this.codigoserie = codigoserie;
	}

	public String getApellidosusuario() {
		return apellidosusuario;
	}

	public void setApellidosusuario(String apellidosusuario) {
		this.apellidosusuario = apellidosusuario;
	}

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getTipoDocumental() {
		return tipoDocumental;
	}

	public void setTipoDocumental(String formaDocumental) {
		this.tipoDocumental = formaDocumental;
	}

	public String getIddescrorgproductor() {
		return iddescrorgproductor;
	}

	public void setIddescrorgproductor(String iddescrorgproductor) {
		this.iddescrorgproductor = iddescrorgproductor;
	}

	public String getIdtipoelmtodreserva() {
		return idtipoelmtodreserva;
	}

	public void setIdtipoelmtodreserva(String idtipoelmtodreserva) {
		this.idtipoelmtodreserva = idtipoelmtodreserva;
	}

	public String getIdarchivoremitente() {
		return idarchivoremitente;
	}

	public void setIdarchivoremitente(String idarchivoremitente) {
		this.idarchivoremitente = idarchivoremitente;
	}

	public String getIdfondoorigen() {
		return idfondoorigen;
	}

	public void setIdfondoorigen(String idfondoorigen) {
		this.idfondoorigen = idfondoorigen;
	}

	public String getIdserieorigen() {
		return idserieorigen;
	}

	public void setIdserieorigen(String idserieorigen) {
		this.idserieorigen = idserieorigen;
	}

	public void setIdfondodestino(String idfondodestino) {
		this.idfondodestino = idfondodestino;
	}

	public void setPermitidoVerInformeRelacion(
			boolean permitidoVerInformeRelacion) {
		this.permitidoVerInformeRelacion = permitidoVerInformeRelacion;
	}

	/**
	 * @return el correccionenarchivo
	 */
	public String getCorreccionenarchivo() {
		return correccionenarchivo;
	}

	/**
	 * @param correccionenarchivo
	 *            el correccionenarchivo a establecer
	 */
	public void setCorreccionenarchivo(String correccionenarchivo) {
		this.correccionenarchivo = correccionenarchivo;
	}

	/**
	 * @return el permitidoCorregirErrores
	 */
	public boolean isPermitidoCorregirErrores() {
		return permitidoCorregirErrores;
	}

	/**
	 * @param permitidoCorregirErrores
	 *            el permitidoCorregirErrores a establecer
	 */
	public void setPermitidoCorregirErrores(boolean permitidoCorregirErrores) {
		this.permitidoCorregirErrores = permitidoCorregirErrores;
	}

	public boolean isPermitirEditarDescripcionContenido() {
		return permitirEditarDescripcionContenido;
	}

	public void setPermitirEditarDescripcionContenido(
			boolean permitirEditarDescripcionContenido) {
		this.permitirEditarDescripcionContenido = permitirEditarDescripcionContenido;
	}

	public String getIdNivelDocumental() {
		return idNivelDocumental;
	}

	public void setIdNivelDocumental(String idNivelDocumental) {
		this.idNivelDocumental = idNivelDocumental;
	}

	public String getSindocsfisicos() {
		return sindocsfisicos;
	}

	public void setSindocsfisicos(String sindocsfisicos) {
		this.sindocsfisicos = sindocsfisicos;
	}

	public boolean isSinDocsFisicos() {
		return Constants.TRUE_STRING.equals(sindocsfisicos);
	}

	public boolean isPermitidaAdicionDocsElectronicos() {
		return permitidaAdicionDocsElectronicos;
	}

	public boolean isPermitidoVerInformeDetallesRelacion() {
		return ((estado >= EstadoREntrega.ENVIADA.getIdentificador() && estado <= EstadoREntrega.VALIDADA
				.getIdentificador()) && !getIsIngresoDirecto())
				|| (estado == EstadoREntrega.COTEJADA.getIdentificador());
	}

	public boolean isPermitidoVerJustificanteAltaUdocs() {
		return (getIsIngresoDirecto() && (isPuedeSerUbicada() || isValidada()));
	}

	public void setPermitidaAdicionDocsElectronicos(
			boolean permitidaAdicionDocsElectronicos) {
		this.permitidaAdicionDocsElectronicos = permitidaAdicionDocsElectronicos;
	}

	public boolean isPermitidaSustraccionDocsElectronicos() {
		return permitidaSustraccionDocsElectronicos;
	}

	public void setPermitidaSustraccionDocsElectronicos(
			boolean permitidaSustraccionDocsElectronicos) {
		this.permitidaSustraccionDocsElectronicos = permitidaSustraccionDocsElectronicos;
	}

	public boolean isPermitidaImportacionExpedientes() {
		return permitidaImportacionExpedientes;
	}

	public void setPermitidaImportacionExpedientes(
			boolean permitidaImportacionExpedientes) {
		this.permitidaImportacionExpedientes = permitidaImportacionExpedientes;
	}

	public boolean isPermitidoBloqueoDesbloqueoExpedientes() {
		return permitidoBloqueoDesbloqueoExpedientes;
	}

	public void setPermitidoBloqueoDesbloqueoExpedientes(
			boolean permitidoBloqueoDesbloqueoExpedientes) {
		this.permitidoBloqueoDesbloqueoExpedientes = permitidoBloqueoDesbloqueoExpedientes;
	}

	public boolean isPermitidoMostrarBloqueoDesbloqueoExpedientes() {
		return permitidoMostrarBloqueoDesbloqueoExpedientes;
	}

	public void setPermitidoMostrarBloqueoDesbloqueoExpedientes(
			boolean permitidoMostrarBloqueoDesbloqueoExpedientes) {
		this.permitidoMostrarBloqueoDesbloqueoExpedientes = permitidoMostrarBloqueoDesbloqueoExpedientes;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public void setNombreFormato(String nombreFormato) {
		this.nombreFormato = nombreFormato;
	}

	public String getNombreFormato() {
		return nombreFormato;
	}

	public String getConReencajado() {
		return conReencajado;
	}

	public void setConReencajado(String conReencajado) {
		this.conReencajado = conReencajado;
	}

	public String getIdFormatoRe() {
		return idFormatoRe;
	}

	public void setIdFormatoRe(String idFormatoRe) {
		this.idFormatoRe = idFormatoRe;
	}

	public boolean isPermitidoActivarReencajado() {
		return permitidoActivarReencajado;
	}

	public void setPermitidoActivarReencajado(boolean permitidoActivarReencajado) {
		this.permitidoActivarReencajado = permitidoActivarReencajado;
	}

	public boolean isPermitidoAccionesReencajado() {
		return permitidoAccionesReencajado;
	}

	public void setPermitidoAccionesReencajado(
			boolean permitidoAccionesReencajado) {
		this.permitidoAccionesReencajado = permitidoAccionesReencajado;
	}

	// IMPLEMENTADOS PROPIOS
	public boolean isRelacionConReencajado() {
		if (Constants.TRUE_STRING.equals(conReencajado)) {
			return true;
		}
		return false;
	}

	public boolean isConReencajadoValidada() {
		if (isRelacionConReencajado()
				&& this.estado == EstadoREntrega.VALIDADA.getIdentificador()) {
			return true;
		}
		return false;
	}

	/**
	 * Obtiene el Identificador del Formato Destino: Con Reecajado: IdFormatoRe
	 * Sin Reencajado: idformatoui
	 *
	 * @return
	 */
	public String getIdFormatoDestino() {
		if (isRelacionConReencajado()) {
			return getIdFormatoRe();
		}
		return getIdformatoui();
	}

	public boolean isPermitidoMarcarRevisada() {
		return permitidoMarcarRevisada;
	}

	public void setPermitidoMarcarRevisada(boolean permitidoMarcarRevisada) {
		this.permitidoMarcarRevisada = permitidoMarcarRevisada;
	}
}