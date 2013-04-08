package es.ieci.tecdoc.fwktd.sir.api.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;

/**
 * Información de auditoría de los ficheros de datos de intercambio
 * enviados y recibidos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AuditoriaFicheroIntercambioVO extends Entity {

	private static final long serialVersionUID = -7068466423982864157L;

	/**
	 * Bandeja del mensaje (ENVIADO o RECIBIDO)
	 */
	private BandejaEnum bandeja;

	/**
	 * Fecha de creación de la entrada de auditoría
	 */
	private Date fechaCreacion;

	/**
	 * Código único de la entidad registral origen obtenido del directorio
	 * común.
	 */
	private String codigoEntidadRegistralOrigen;

	/**
	 * Descripción de la entidad registral origen.
	 */
	private String descripcionEntidadRegistralOrigen;

	/**
	 * Número de registro en la entidad registral origen.
	 */
	private String numeroRegistro;

	/**
	 * Fecha y hora de registro en la entidad registral origen.
	 */
	private Date fechaRegistro;

	/**
	 * Sello de tiempo del registro de entrada en origen.
	 */
	private byte[] timestampRegistro;

	/**
	 * Código único de la unidad de tramitación de origen obtenido del
	 * directorio común.
	 */
	private String codigoUnidadTramitacionOrigen;

	/**
	 * Descripción de la unidad de tramitación de origen.
	 */
	private String descripcionUnidadTramitacionOrigen;

	/**
	 * Código único de la entidad registral de destino obtenido del directorio
	 * común.
	 */
	private String codigoEntidadRegistralDestino;

	/**
	 * Descripción de la entidad registral de destino.
	 */
	private String descripcionEntidadRegistralDestino;

	/**
	 * Código único de la unidad de tramitación de destino obtenido del
	 * directorio común.
	 */
	private String codigoUnidadTramitacionDestino;

	/**
	 * Descripción de la unidad de tramitación de destino.
	 */
	private String descripcionUnidadTramitacionDestino;

	/**
	 * Abstract o resumen.
	 */
	private String resumen;

	/**
	 * Código de asunto según destino.
	 */
	private String codigoAsunto;

	/**
	 * Referencia externa.
	 */
	private String referenciaExterna;

	/**
	 * Número de expediente objeto de la tramitación administrativa.
	 */
	private String numeroExpediente;

	/**
	 * Tipo de transporte de entrada.
	 */
	private TipoTransporteEnum tipoTransporte;

	/**
	 * Número de transporte de entrada.
	 */
	private String numeroTransporte;

	/**
	 * Nombre del usuario de origen.
	 */
	private String nombreUsuario;

	/**
	 * Contacto del usuario de origen (teléfono o dirección de correo
	 * electrónico).
	 */
	private String contactoUsuario;

	/**
	 * Identificador de intercambio único de la operación.
	 */
	private String identificadorIntercambio;

	/**
	 * Aplicación y versión emisora.
	 */
	private String aplicacion;

	/**
	 * Tipo de anotación.
	 */
	private TipoAnotacionEnum tipoAnotacion;

	/**
	 * Descripción del tipo de anotación.
	 */
	private String descripcionTipoAnotacion;
	/**
	 * Tipo de registro.
	 */
	private TipoRegistroEnum tipoRegistro;

	/**
	 * Documentación física que acompaña al fichero.
	 */
	private DocumentacionFisicaEnum documentacionFisica;

	/**
	 * Observaciones del registro de datos de intercambio recogidos por el
	 * funcionario de registro.
	 */
	private String observacionesApunte;

	/**
	 * Indicador de prueba
	 */
	private IndicadorPruebaEnum indicadorPrueba = IndicadorPruebaEnum.NORMAL;

	/**
	 * Código único de la entidad registral de inicio obtenido del directorio
	 * común.
	 */
	private String codigoEntidadRegistralInicio;

	/**
	 * Descripción de la entidad registral de inicio.
	 */
	private String descripcionEntidadRegistralInicio;

	/**
	 * Exposición de los hechos y antecedentes relacionados con la solicitud.
	 */
	private String expone;

	/**
	 * Descripción del objeto de la solicitud.
	 */
	private String solicita;

	/**
	 * XML del fichero de intercambio.
	 */
	private String xml;


	/**
	 * Constructor.
	 */
	public AuditoriaFicheroIntercambioVO() {
		super();
	}

	public BandejaEnum getBandeja() {
		return bandeja;
	}

	public void setBandeja(BandejaEnum bandeja) {
		this.bandeja = bandeja;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getCodigoEntidadRegistralOrigen() {
		return codigoEntidadRegistralOrigen;
	}

	public void setCodigoEntidadRegistralOrigen(String codigoEntidadRegistralOrigen) {
		this.codigoEntidadRegistralOrigen = codigoEntidadRegistralOrigen;
	}

	public String getDescripcionEntidadRegistralOrigen() {
		return descripcionEntidadRegistralOrigen;
	}

	public void setDescripcionEntidadRegistralOrigen(
			String descripcionEntidadRegistralOrigen) {
		this.descripcionEntidadRegistralOrigen = descripcionEntidadRegistralOrigen;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public byte[] getTimestampRegistro() {
		return timestampRegistro;
	}

	public void setTimestampRegistro(byte[] timestampRegistro) {
		this.timestampRegistro = timestampRegistro;
	}

	public String getCodigoUnidadTramitacionOrigen() {
		return codigoUnidadTramitacionOrigen;
	}

	public void setCodigoUnidadTramitacionOrigen(
			String codigoUnidadTramitacionOrigen) {
		this.codigoUnidadTramitacionOrigen = codigoUnidadTramitacionOrigen;
	}

	public String getDescripcionUnidadTramitacionOrigen() {
		return descripcionUnidadTramitacionOrigen;
	}

	public void setDescripcionUnidadTramitacionOrigen(
			String descripcionUnidadTramitacionOrigen) {
		this.descripcionUnidadTramitacionOrigen = descripcionUnidadTramitacionOrigen;
	}

	public String getCodigoEntidadRegistralDestino() {
		return codigoEntidadRegistralDestino;
	}

	public void setCodigoEntidadRegistralDestino(
			String codigoEntidadRegistralDestino) {
		this.codigoEntidadRegistralDestino = codigoEntidadRegistralDestino;
	}

	public String getDescripcionEntidadRegistralDestino() {
		return descripcionEntidadRegistralDestino;
	}

	public void setDescripcionEntidadRegistralDestino(
			String descripcionEntidadRegistralDestino) {
		this.descripcionEntidadRegistralDestino = descripcionEntidadRegistralDestino;
	}

	public String getCodigoUnidadTramitacionDestino() {
		return codigoUnidadTramitacionDestino;
	}

	public void setCodigoUnidadTramitacionDestino(
			String codigoUnidadTramitacionDestino) {
		this.codigoUnidadTramitacionDestino = codigoUnidadTramitacionDestino;
	}

	public String getDescripcionUnidadTramitacionDestino() {
		return descripcionUnidadTramitacionDestino;
	}

	public void setDescripcionUnidadTramitacionDestino(
			String descripcionUnidadTramitacionDestino) {
		this.descripcionUnidadTramitacionDestino = descripcionUnidadTramitacionDestino;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getCodigoAsunto() {
		return codigoAsunto;
	}

	public void setCodigoAsunto(String codigoAsunto) {
		this.codigoAsunto = codigoAsunto;
	}

	public String getReferenciaExterna() {
		return referenciaExterna;
	}

	public void setReferenciaExterna(String referenciaExterna) {
		this.referenciaExterna = referenciaExterna;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public TipoTransporteEnum getTipoTransporte() {
		return tipoTransporte;
	}

	public void setTipoTransporte(TipoTransporteEnum tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}

	public String getNumeroTransporte() {
		return numeroTransporte;
	}

	public void setNumeroTransporte(String numeroTransporte) {
		this.numeroTransporte = numeroTransporte;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContactoUsuario() {
		return contactoUsuario;
	}

	public void setContactoUsuario(String contactoUsuario) {
		this.contactoUsuario = contactoUsuario;
	}

	public String getIdentificadorIntercambio() {
		return identificadorIntercambio;
	}

	public void setIdentificadorIntercambio(String identificadorIntercambio) {
		this.identificadorIntercambio = identificadorIntercambio;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public TipoAnotacionEnum getTipoAnotacion() {
		return tipoAnotacion;
	}

	public void setTipoAnotacion(TipoAnotacionEnum tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}

	public String getDescripcionTipoAnotacion() {
		return descripcionTipoAnotacion;
	}

	public void setDescripcionTipoAnotacion(String descripcionTipoAnotacion) {
		this.descripcionTipoAnotacion = descripcionTipoAnotacion;
	}

	public TipoRegistroEnum getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TipoRegistroEnum tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public DocumentacionFisicaEnum getDocumentacionFisica() {
		return documentacionFisica;
	}

	public void setDocumentacionFisica(DocumentacionFisicaEnum documentacionFisica) {
		this.documentacionFisica = documentacionFisica;
	}

	public String getObservacionesApunte() {
		return observacionesApunte;
	}

	public void setObservacionesApunte(String observacionesApunte) {
		this.observacionesApunte = observacionesApunte;
	}

	public IndicadorPruebaEnum getIndicadorPrueba() {
		return indicadorPrueba;
	}

	public void setIndicadorPrueba(IndicadorPruebaEnum indicadorPrueba) {
		this.indicadorPrueba = indicadorPrueba;
	}

	public String getCodigoEntidadRegistralInicio() {
		return codigoEntidadRegistralInicio;
	}

	public void setCodigoEntidadRegistralInicio(String codigoEntidadRegistralInicio) {
		this.codigoEntidadRegistralInicio = codigoEntidadRegistralInicio;
	}

	public String getDescripcionEntidadRegistralInicio() {
		return descripcionEntidadRegistralInicio;
	}

	public void setDescripcionEntidadRegistralInicio(
			String descripcionEntidadRegistralInicio) {
		this.descripcionEntidadRegistralInicio = descripcionEntidadRegistralInicio;
	}

	public String getExpone() {
		return expone;
	}

	public void setExpone(String expone) {
		this.expone = expone;
	}

	public String getSolicita() {
		return solicita;
	}

	public void setSolicita(String solicita) {
		this.solicita = solicita;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
