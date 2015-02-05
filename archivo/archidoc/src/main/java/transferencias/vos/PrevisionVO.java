package transferencias.vos;

import java.util.Date;

import org.apache.log4j.Logger;

import transferencias.EstadoPrevisionConstants;
import transferencias.TipoTransferencia;
import transferencias.model.EstadoPrevision;
import transferencias.model.InvalidTipoTransferenciaException;

import common.CodigoTransferenciaUtils;
import common.bi.ServiceSession;
import common.exceptions.UncheckedArchivoException;
import common.util.DateUtils;
import common.util.NombresUtils;

/**
 * Value Object con la informacion correspondiente a una prevision de
 * transferencia. El proceso de Transferencia se inicia en el Órgano Remitente
 * creando. una Previsión de Transferencia con el, o los, procedimientos a
 * enviar. En el Archivo se acepta la Previsión de Transferencia, asignándole un
 * rango de fechas de envío.
 */
public class PrevisionVO {

	private static final Logger logger = Logger.getLogger(PrevisionVO.class);

	/* Tipos de transferencias */
	/**
	 * Empleadas para la incorporacion al Archivo de Unidades Documentales
	 * procedentes de los Sistemas Automatizados
	 */
	public final static int TRANSFERENCIA_ORDINARIA = 1;
	/**
	 * El Procedimiento Administrativo no automatizado o la Serie Documental a
	 * la que pertenecen las Unidades Documentales a transferir está
	 * perfectamente identificado
	 */
	public final static int TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_DETALLADA = 2;
	/**
	 * Se parte del desconocimiento previo del Procedimiento o la Serie
	 * Documental a la que pertenecen las Unidades Documentales a Transferir a
	 * transferir
	 */
	public final static int TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_NODETALLADA = 3;
	/**
	 * Tambien e parte del desconocimiento previo del Procedimiento o la Serie
	 * Documental a la que pertenecen las Unidades Documentales a Transferir a
	 * transferir y estas además están signaturadas
	 */
	public final static int TRANSFERENCIA_EXTRAORDINARIA_SIGNATURIZADA = 4;
	/** Transferencias entre archivos */
	public final static int TRANSFERENCIA_ENTRE_ARCHIVOS = 5;
	/** Transferencias entre archivos */
	public final static int INGRESO_DIRECTO = 6;

	/* Permiten discernir entre previsiones detalladas y no detalladas */
	/**
	 * Son detalladas las previsiones que se desglosan en varias lineas de
	 * detalle
	 */
	public final static int PREVISION_DETALLADA = 1;
	/** Son no detalladas las que no admiten un desglose en lineas de detalle */
	public final static int PREVISION_NODETALLADA = 2;

	/* Control de acciones sobre la prevision */
	protected boolean editable = false;
	protected boolean enviable = false;
	protected Boolean eliminable = null;
	protected boolean recibible = false;
	protected boolean modificable = false;
	protected boolean aceptaRelaciones = false;
	protected boolean fondoCanBeChanged = false;
	protected boolean archivoRemitenteCanBeChanged = false;
	protected boolean archivoReceptorCanBeChanged = false;
	protected boolean permitidaSeleccionProcedimiento = false;

	/* Informcion referente a la prevision */
	protected String id = null;
	protected int tipotransferencia;
	protected int tipoprevision;
	protected String idorgremitente = null;
	protected String nombreOrgano = null;
	protected String ano = null;
	protected int orden;
	protected String idfondodestino = null;
	protected Date fechainitrans = null;
	protected Date fechafintrans = null;
	protected int numuinstalacion;
	protected int estado;
	protected Date fechaestado = null;
	protected String idarchivoreceptor = null;
	protected String idusrgestor = null;
	protected String nombreUsuario = null;
	protected String apellidosUsuario = null;
	protected String observaciones = null;
	protected String motivorechazo = null;
	protected String idarchivoremitente = null;

	public String getMotivorechazo() {
		return this.motivorechazo;
	}

	public void setMotivorechazo(String motivorechazo) {
		this.motivorechazo = motivorechazo;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getFechafintrans() {
		return fechafintrans;
	}

	public void setFechafintrans(Date fechafintrans) {
		this.fechafintrans = fechafintrans;
	}

	public Date getFechainitrans() {
		return fechainitrans;
	}

	public void setFechainitrans(Date fechainitrans) {
		this.fechainitrans = fechainitrans;
	}

	public String getIdarchivoreceptor() {
		return idarchivoreceptor;
	}

	public void setIdarchivoreceptor(String idarchivoreceptor) {
		this.idarchivoreceptor = idarchivoreceptor;
	}

	public String getIdorgremitente() {
		return idorgremitente;
	}

	public void setIdorgremitente(String idorgremitente) {
		this.idorgremitente = idorgremitente;
	}

	public String getIdusrgestor() {
		return idusrgestor;
	}

	public void setIdusrgestor(String idusrgestor) {
		this.idusrgestor = idusrgestor;
	}

	public int getNumuinstalacion() {
		return numuinstalacion;
	}

	public void setNumuinstalacion(int numuinstalacion) {
		this.numuinstalacion = numuinstalacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getTipoprevision() {
		return tipoprevision;
	}

	public void setTipoprevision(int tipoprevision) {
		this.tipoprevision = tipoprevision;
	}

	public int getTipotransferencia() {
		return tipotransferencia;
	}

	public void setTipotransferencia(int tipotransferencia) {
		this.tipotransferencia = tipotransferencia;
	}

	public String getNombreOrgano() {
		return nombreOrgano;
	}

	public void setNombreOrgano(String nombreorgremitente) {
		this.nombreOrgano = nombreorgremitente;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreusrgestor) {
		this.nombreUsuario = nombreusrgestor;
	}

	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	public String getNombreCompletoUsuario() {
		return NombresUtils.getNombreCompleto(nombreUsuario, apellidosUsuario);
	}

	public boolean isDetallada() {
		return (this.tipoprevision == PREVISION_DETALLADA);
	}

	public String getCodigoPrevision(ServiceSession ss) {
		// return new
		// StringBuffer(getAno()).append("/").append(getOrden()).toString();
		return CodigoTransferenciaUtils.getCodigoTransferenciaFromVO(this, ss);
	}

	public String getCodigoprevision() {
		return new StringBuffer(getAno()).append("/").append(getOrden())
				.toString();
	}

	public boolean isCabeceraEditable() {
		EstadoPrevisionConstants estadoPrevision = EstadoPrevisionConstants
				.getEstadoPrevision(getEstado());
		boolean previsionAbierta = (estadoPrevision == EstadoPrevision.ABIERTA);
		boolean previsionRechazada = (estadoPrevision == EstadoPrevision.RECHAZADA);
		// if (((previsionAbierta || previsionRechazada))&&(!isDetallada()))
		// return true;
		if (previsionAbierta || previsionRechazada)
			return true;
		return false;
	}

	public static int getTipoOperacion(int tipoTransferencia, int tipoPrevision)
			throws InvalidTipoTransferenciaException {
		int tipoOperacion = 0;
		if (tipoTransferencia == TipoTransferencia.ORDINARIA.getIdentificador())
			tipoOperacion = TRANSFERENCIA_ORDINARIA;
		else if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
				.getIdentificador() && tipoPrevision == PREVISION_DETALLADA)
			tipoOperacion = TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_DETALLADA;
		else if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIN_SIGNATURAR
				.getIdentificador() && tipoPrevision != PREVISION_DETALLADA)
			tipoOperacion = TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_NODETALLADA;
		else if (tipoTransferencia == TipoTransferencia.EXTRAORDINARIA_SIGNATURADA
				.getIdentificador())
			tipoOperacion = TRANSFERENCIA_EXTRAORDINARIA_SIGNATURIZADA;
		else if (tipoTransferencia == TipoTransferencia.ENTRE_ARCHIVOS
				.getIdentificador())
			tipoOperacion = TRANSFERENCIA_ENTRE_ARCHIVOS;
		else if (tipoTransferencia == TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador())
			tipoOperacion = INGRESO_DIRECTO;
		else {
			logger.error("Tipo de transferencia no reconocido. Tipo transferencia: "
					+ tipoTransferencia + " , tipo prevision: " + tipoPrevision);
			throw new InvalidTipoTransferenciaException(
					"No se reconoce el tipo de transferencia");
		}
		return tipoOperacion;
	}

	public int getTipooperacion() {
		try {
			return getTipoOperacion(tipotransferencia, tipoprevision);
		} catch (InvalidTipoTransferenciaException e) {
			throw new UncheckedArchivoException(e);
		}
	}

	public boolean getPuedeSerEliminada() {
		boolean returnValue = getEstado() == EstadoPrevision.ABIERTA
				.getIdentificador();
		if (eliminable != null)
			returnValue = eliminable.booleanValue();
		return returnValue;
	}

	public void setPuedeSerEliminada(boolean eliminable) {
		this.eliminable = new Boolean(eliminable);
	}

	public boolean isCaducada() {
		Date hoy = DateUtils.getFechaActualSinHora();

		return DateUtils.isFechaMayor(hoy, fechafintrans);
		// return hoy.compareTo(fechafintrans) > 0;
	}

	public boolean isAutomatizada(){
		return (getEstado() == EstadoPrevision.AUTOMATIZADA.getIdentificador());
	}


	public boolean isOrdinaria() {
		return (getTipooperacion() == TRANSFERENCIA_ORDINARIA);
	}

	public boolean isExtraordinaria() {
		return (getTipooperacion() == TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_DETALLADA)
				|| (getTipooperacion() == TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_NODETALLADA)
				|| (getTipooperacion() == TRANSFERENCIA_EXTRAORDINARIA_SIGNATURIZADA);
	}

	public boolean isEntreArchivos() {
		return (getTipooperacion() == TRANSFERENCIA_ENTRE_ARCHIVOS);
	}

	public void setFondoCanBeChanged(boolean fondoCanBeChanged) {
		this.fondoCanBeChanged = fondoCanBeChanged;
	}

	public boolean getPuedeSerEditada() {
		return editable;
	}

	public void setPuedeSerEditada(boolean editable) {
		this.editable = editable;
	}

	public boolean getFondoCanBeChanged() {
		return editable && fondoCanBeChanged;
	}

	public boolean getPuedeSerEnviada() {
		return this.enviable;
	}

	public void setPuedeSerEnviada(boolean enviable) {
		this.enviable = enviable;
	}

	public boolean getAceptaRelaciones() {
		return aceptaRelaciones;
	}

	public void setAceptaRelaciones(boolean aceptaRelaciones) {
		this.aceptaRelaciones = aceptaRelaciones;
	}

	public boolean getPuedeSerAceptada() {
		return recibible;
	}

	public void setPuedeSerAceptada(boolean recibible) {
		this.recibible = recibible;
	}

	public boolean getPuedeSerModificada() {
		return modificable;
	}

	public void setPuedeSerModificada(boolean modificable) {
		this.modificable = modificable;
	}

	public boolean getPermitidaSeleccionProcedimiento() {
		return permitidaSeleccionProcedimiento;
	}

	public void setPermitidaSeleccionProcedimiento(
			boolean permitidaSeleccionProcedimiento) {
		this.permitidaSeleccionProcedimiento = permitidaSeleccionProcedimiento;
	}

	public String getIdarchivoremitente() {
		return idarchivoremitente;
	}

	public void setIdarchivoremitente(String idarchivoremitente) {
		this.idarchivoremitente = idarchivoremitente;
	}

	public String getIdfondodestino() {
		return idfondodestino;
	}

	public void setIdfondodestino(String idfondodestino) {
		this.idfondodestino = idfondodestino;
	}

	/**
	 * @return el archivoReceptorCanBeChanged
	 */
	public boolean getArchivoReceptorCanBeChanged() {
		return archivoReceptorCanBeChanged;
	}

	/**
	 * @param archivoReceptorCanBeChanged
	 *            el archivoReceptorCanBeChanged a establecer
	 */
	public void setArchivoReceptorCanBeChanged(
			boolean archivoReceptorCanBeChanged) {
		this.archivoReceptorCanBeChanged = archivoReceptorCanBeChanged;
	}

	/**
	 * @return el archivoRemitenteCanBeChanged
	 */
	public boolean getArchivoRemitenteCanBeChanged() {
		return archivoRemitenteCanBeChanged;
	}

	/**
	 * @param archivoRemitenteCanBeChanged
	 *            el archivoRemitenteCanBeChanged a establecer
	 */
	public void setArchivoRemitenteCanBeChanged(
			boolean archivoRemitenteCanBeChanged) {
		this.archivoRemitenteCanBeChanged = archivoRemitenteCanBeChanged;
	}

}