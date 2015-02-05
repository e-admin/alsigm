package es.ieci.tecdoc.fwktd.sir.api.vo;

import java.util.Date;
import java.util.List;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;

/**
 * Mensaje de propósito general con el objetivo de realizar tareas de avisos y
 * gestión de flujo del intercambio, conforme a la normativa SICRES 3.0.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MensajeVO extends BaseValueObject {

	private static final long serialVersionUID = 2247459706718332827L;

	/**
	 * Código único de la entidad registral origen en el directorio común.
	 * Obligatorio.
	 */
	private String codigoEntidadRegistralOrigen;

	/**
	 * Código único de la entidad registral destino en el directorio común.
	 * Obligatorio.
	 */
	private String codigoEntidadRegistralDestino;

	/**
	 * Identificador de intercambio único de la operación. Obligatorio.
	 */
	private String identificadorIntercambio;

	/**
	 * Tipo de mensaje. Obligatorio.
	 */
	private TipoMensajeEnum tipoMensaje;

	/**
	 * Texto descriptivo del mensaje. Opcional.
	 */
	private String descripcionMensaje;

	/**
	 * Número de registro de entrada en la entidad Registral destino. Utilizado
	 * para completar el ciclo de envío. Opcional.
	 */
	private String numeroRegistroEntradaDestino;

	/**
	 * Fecha y hora de entrada en destino. Opcional.
	 */
	private Date fechaEntradaDestino;

	/**
	 * Indicador de prueba.
	 */
	private IndicadorPruebaEnum indicadorPrueba = IndicadorPruebaEnum.NORMAL;

	/**
	 * Lista de identificadores de ficheros de intercambio que se tienen que
	 * reenviar en caso de error. Opcional.
	 */
	private List<String> identificadoresFicheros;

	/**
	 * Identificador del tipo de error que se ha producido durante el proceso de
	 * intercambio. Opcional.
	 */
	private String codigoError;

	/**
	 * Constructor.
	 */
	public MensajeVO() {
		super();
	}

	public String getCodigoEntidadRegistralOrigen() {
		return codigoEntidadRegistralOrigen;
	}

	public void setCodigoEntidadRegistralOrigen(
			String codigoEntidadRegistralOrigen) {
		this.codigoEntidadRegistralOrigen = codigoEntidadRegistralOrigen;
	}

	public String getCodigoEntidadRegistralDestino() {
		return codigoEntidadRegistralDestino;
	}

	public void setCodigoEntidadRegistralDestino(
			String codigoEntidadRegistralDestino) {
		this.codigoEntidadRegistralDestino = codigoEntidadRegistralDestino;
	}

	public String getIdentificadorIntercambio() {
		return identificadorIntercambio;
	}

	public void setIdentificadorIntercambio(String identificadorIntercambio) {
		this.identificadorIntercambio = identificadorIntercambio;
	}

	public TipoMensajeEnum getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(TipoMensajeEnum tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getDescripcionMensaje() {
		return descripcionMensaje;
	}

	public void setDescripcionMensaje(String descripcionMensaje) {
		this.descripcionMensaje = descripcionMensaje;
	}

	public String getNumeroRegistroEntradaDestino() {
		return numeroRegistroEntradaDestino;
	}

	public void setNumeroRegistroEntradaDestino(
			String numeroRegistroEntradaDestino) {
		this.numeroRegistroEntradaDestino = numeroRegistroEntradaDestino;
	}

	public Date getFechaEntradaDestino() {
		return fechaEntradaDestino;
	}

	public void setFechaEntradaDestino(Date fechaEntradaDestino) {
		this.fechaEntradaDestino = fechaEntradaDestino;
	}

	public List<String> getIdentificadoresFicheros() {
		return identificadoresFicheros;
	}

	public void setIdentificadoresFicheros(List<String> identificadoresFicheros) {
		this.identificadoresFicheros = identificadoresFicheros;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public IndicadorPruebaEnum getIndicadorPrueba() {
		return indicadorPrueba;
	}

	public void setIndicadorPrueba(IndicadorPruebaEnum indicadorPrueba) {
		this.indicadorPrueba = indicadorPrueba;
	}

}
