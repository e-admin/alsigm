package valoracion.vos;

import java.util.Date;

import common.db.DBUtils;
import common.util.DateUtils;
import common.util.StringUtils;
import common.vos.BaseVO;
import common.vos.IKeyId;

/**
 * VO que encapsula la información necesario de unidades documentales para la
 * eliminacion
 */
public class UnidadDocumentalEliminacionVO extends BaseVO implements IKeyId,
		IUnidadDocumentalEliminacionVO {

	private static final String CAJA_COMPLETA = "C";
	private static final String CAJA_PARCIAL = "P";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String idEliminacion = null;
	private String idudoc = null;
	private String numero = null;
	private String codigo = null;
	private String signaturaudoc = null;
	private String titulo = null;
	private String expedienteudoc = null;
	private String codsistproductor = null;
	private String idfondo = null;
	private String iduinstalacion = null;
	private String ubicacion = null;
	private String tipoDocumental = null;
	private Date fechaIniUdoc = null;
	private Date fechaFinUdoc = null;

	/**
	 * Indica si el tipo al que pertenece el detalle es una consulta(2) o un
	 * prestamo(1)
	 */
	private int tiposolicitud = 0;
	/** Codigo de referencia */
	private String codreferencia = null;

	/**
	 * Indicador del tipo de caja 1-Completa 2-Parcial
	 */
	private String tipoCaja = new String();

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getTitulo()
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setTitulo(java.lang.String)
	 */
	public void setTitulo(String codfondo) {
		this.titulo = codfondo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getExpedienteudoc()
	 */
	public String getExpedienteudoc() {
		return expedienteudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setExpedienteudoc(java.lang.String)
	 */
	public void setExpedienteudoc(String expedienteudoc) {
		this.expedienteudoc = expedienteudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getIdudoc()
	 */
	public String getIdudoc() {
		return idudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setIdudoc(java.lang.String)
	 */
	public void setIdudoc(String idudoc) {
		this.idudoc = idudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getCodigo()
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setCodigo(java.lang.String)
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getSignaturaudoc()
	 */
	public String getSignaturaudoc() {
		return signaturaudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setSignaturaudoc(java.lang.String)
	 */
	public void setSignaturaudoc(String signaturaudoc) {
		this.signaturaudoc = signaturaudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getIdfondo()
	 */
	public String getIdfondo() {
		return idfondo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setIdfondo(java.lang.String)
	 */
	public void setIdfondo(String idFondo) {
		this.idfondo = idFondo;
	}

	/**
	 * Redefinición del metodo equal. Son iguales si coincinde su
	 * identificacion,idudoc,expediente titulo, signatura.
	 */
	public boolean equals(Object arg) {
		boolean resultado = false;

		if (arg != null && (arg instanceof UnidadDocumentalEliminacionVO)) {
			UnidadDocumentalEliminacionVO d = (UnidadDocumentalEliminacionVO) arg;

			if (d.codigo != null && this.codigo != null
					&& d.codigo.equals(this.codigo) && d.idudoc != null
					&& this.idudoc != null && d.idudoc.equals(this.idudoc)
					&& d.expedienteudoc != null && this.expedienteudoc != null
					&& d.expedienteudoc.equals(this.expedienteudoc)
					&& d.titulo != null && this.titulo != null
					&& d.titulo.equals(this.titulo) && d.signaturaudoc != null
					&& this.signaturaudoc != null
					&& d.signaturaudoc.equals(this.signaturaudoc))
				resultado = true;
			else
				resultado = false;
		} else
			resultado = false;

		return resultado;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getTiposolicitud()
	 */
	public int getTiposolicitud() {
		return tiposolicitud;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setTiposolicitud(int)
	 */
	public void setTiposolicitud(int tipo) {
		this.tiposolicitud = tipo;
	}

	public String getCodreferencia() {
		return codreferencia;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setCodreferencia(java.lang.String)
	 */
	public void setCodreferencia(String codReferencia) {
		this.codreferencia = codReferencia;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getIduinstalacion()
	 */
	public String getIduinstalacion() {
		return iduinstalacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setIduinstalacion(java.lang.String)
	 */
	public void setIduinstalacion(String iduinstalacion) {
		this.iduinstalacion = iduinstalacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getUbicacion()
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setUbicacion(java.lang.String)
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getNumero()
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setNumero(java.lang.String)
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getTipoDocumental()
	 */
	public String getTipoDocumental() {
		return tipoDocumental;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setTipoDocumental(java.lang.String)
	 */
	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getCodsistproductor()
	 */
	public String getCodsistproductor() {
		return codsistproductor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setCodsistproductor(java.lang.String)
	 */
	public void setCodsistproductor(String codsistproductor) {
		this.codsistproductor = codsistproductor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getFechaFinUdoc()
	 */
	public Date getFechaFinUdoc() {
		return fechaFinUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getStrFechaFinUdoc()
	 */
	public String getStrFechaFinUdoc() {
		return DateUtils.formatDate(fechaFinUdoc);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setFechaFinUdoc(java.util.Date)
	 */
	public void setFechaFinUdoc(Date fechaFinUdoc) {
		this.fechaFinUdoc = fechaFinUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getFechaIniUdoc()
	 */
	public Date getFechaIniUdoc() {
		return fechaIniUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getStrFechaIniUdoc()
	 */
	public String getStrFechaIniUdoc() {
		return DateUtils.formatDate(fechaIniUdoc);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setFechaIniUdoc(java.util.Date)
	 */
	public void setFechaIniUdoc(Date fechaIniUdoc) {
		this.fechaIniUdoc = fechaIniUdoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#isCajaCompleta()
	 */
	public boolean isCajaCompleta() {
		return CAJA_COMPLETA == tipoCaja;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#isCajaParcial()
	 */
	public boolean isCajaParcial() {
		return CAJA_PARCIAL == tipoCaja;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setCajaParcial()
	 */
	public void setCajaParcial() {
		tipoCaja = CAJA_PARCIAL;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setCajaCompleta()
	 */
	public void setCajaCompleta() {
		tipoCaja = CAJA_COMPLETA;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getId()
	 */
	public String getId() {
		return this.idudoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setTipoCaja(java.lang.String)
	 */
	public void setTipoCaja(String tipoCaja) {
		this.tipoCaja = tipoCaja;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getTipoCaja()
	 */
	public String getTipoCaja() {
		return tipoCaja;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#setIdEliminacion(java.lang.String)
	 */
	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.vos.IUnidadDocumentalEliminacionVO#getIdEliminacion()
	 */
	public String getIdEliminacion() {
		return idEliminacion;
	}

	public String getIdElementocf() {
		return idudoc;
	}

	public void setIdElementocf(String idElementocf) {
		this.idudoc = idElementocf;
	}

	public String getMarcaTipoCaja() {
		if (StringUtils.isNotEmpty(tipoCaja)) {
			return DBUtils.ABRIR_PARENTESIS + tipoCaja
					+ DBUtils.CERRAR_PARENTESIS;
		}
		return tipoCaja;
	}
}
