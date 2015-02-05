package fondos.vos;

import java.util.Date;

import org.apache.log4j.Logger;

import common.util.DateUtils;
import common.util.StringUtils;
import common.vos.BaseVO;
import common.vos.UniqueGuid;

import fondos.utils.ProductoresUtils;

public class InfoProductorSerie extends BaseVO implements UniqueGuid,
		IInfoProductorSerie {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger
			.getLogger(InfoProductorSerie.class);

	int tipoProductor;
	String idDescriptor = null;
	String nombre = null;
	Date fechaInicio = null;
	Date fechaFin = null;
	String sustituyeAHistorico = null;
	int marcas = 0;
	String codigo = null;
	private String idSerie = null;

	/**
	 * Objeto productorSerieVO asociado
	 */
	private ProductorSerieVO productorSerieVO = null;

	/**
	 * Identificador unico del productor.
	 */
	private String guid;

	boolean puedeSerEliminado = true;

	public InfoProductorSerie(int tipoProductor, String nombre) {
		super();
		this.tipoProductor = tipoProductor;
		this.nombre = nombre;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setFechaFin(java.lang.String)
	 */
	public void setFechaFin(String fechaFin) {
		if (fechaFin == null)
			this.fechaFin = null;
		else {
			if (DateUtils.isDate(fechaFin)) {
				setFechaFin(DateUtils.getDate(fechaFin));
			} else {
				logger.error("formato de fecha final incorrecto");
				this.fechaFin = null;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setFechaInicio(java.lang.String)
	 */
	public void setFechaInicio(String fechaInicio) {
		if (fechaInicio == null)
			this.fechaInicio = null;
		else {
			if (DateUtils.isDate(fechaInicio)) {
				setFechaInicio(DateUtils.getDate(fechaInicio));
			} else {
				logger.error("formato de fecha inicial incorrecto");
				this.fechaInicio = null;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getFechaFin()
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setFechaFin(java.util.Date)
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;

		// Actualizar la fecha del productorSerieVO
		getProductorSerieVO().setFechaFinal(fechaFin);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getFechaInicio()
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setFechaInicio(java.util.Date)
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		// Actualizar la fecha del productorSerieVO
		getProductorSerieVO().setFechaInicial(fechaInicio);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getIdDescriptor()
	 */
	public String getIdDescriptor() {
		return idDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setIdDescriptor(java.lang.String)
	 */
	public void setIdDescriptor(String idDescriptor) {
		this.idDescriptor = idDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setNombre(java.lang.String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getTipoProductor()
	 */
	public int getTipoProductor() {
		return tipoProductor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setTipoProductor(int)
	 */
	public void setTipoProductor(int tipoProductor) {
		this.tipoProductor = tipoProductor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isPuedeSerEliminado()
	 */
	public boolean isPuedeSerEliminado() {
		return puedeSerEliminado;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setPuedeSerEliminado(boolean)
	 */
	public void setPuedeSerEliminado(boolean puedeSerEliminado) {
		this.puedeSerEliminado = puedeSerEliminado;
	}

	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj != null)
			if (obj == this)
				returnValue = true;
			else if (obj instanceof InfoProductorSerie) {
				IInfoProductorSerie objAsProductor = (IInfoProductorSerie) obj;
				returnValue = this.idDescriptor.equals(objAsProductor
						.getIdDescriptor());
			}
		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getSustituyeAHistorico()
	 */
	public String getSustituyeAHistorico() {
		return sustituyeAHistorico;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setSustituyeAHistorico(java.lang.String)
	 */
	public void setSustituyeAHistorico(String sustituyeAHistorico) {
		this.sustituyeAHistorico = sustituyeAHistorico;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getNombreCorto()
	 */
	public String getNombreCorto() {
		return StringUtils.getToken(nombre, "/", 1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getNodosOrgano()
	 */
	public String[] getNodosOrgano() {
		return nombre.split("/");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getFirstOrgano()
	 */
	public String getFirstOrgano() {
		String[] organos = nombre.split("/");
		if (organos.length > 0)
			return organos[0];
		return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getMarcas()
	 */
	public int getMarcas() {
		return marcas;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setMarcas(int)
	 */
	public void setMarcas(int marcas) {
		this.marcas = marcas;

		// Actualizar las Marcas del ProductorSerieVO
		this.getProductorSerieVO().setMarcas(marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getCodigo()
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setCodigo(java.lang.String)
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isVigente()
	 */
	public boolean isVigente() {
		return ProductoresUtils.isVigente(marcas, this.fechaFin);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isPermitidoPasarAHistorico()
	 */
	public boolean isPermitidoPasarAHistorico() {
		return ProductoresUtils.isPermitidoPasarAHistorico(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isPermitidoReemplazar()
	 */
	public boolean isPermitidoReemplazar() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setGuid(java.lang.String)
	 */
	public void setGuid(String guid) {
		this.guid = guid;

		this.getProductorSerieVO().setGuid(guid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getGuid()
	 */
	public String getGuid() {
		if (StringUtils.isNotEmpty(getIdDescriptor())) {
			return getIdDescriptor();
		}

		return guid;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isSinGuardar()
	 */
	public boolean isSinGuardar() {
		return ProductoresUtils.isSinGuardar(marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isNuevo()
	 */
	public boolean isNuevo() {
		return ProductoresUtils.isNuevo(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getTipoObjeto()
	 */
	public String getTipoObjeto() {
		return ProductoresUtils.getTipoObjeto(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isPermitidoEliminar()
	 */
	public boolean isPermitidoEliminar() {
		return ProductoresUtils.isPermitidoEliminar(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isPermitidoPasarAVigente()
	 */
	public boolean isPermitidoPasarAVigente() {
		return ProductoresUtils.isPermitidoPasarAVigente(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isMostrarRadio()
	 */
	public boolean isMostrarRadio() {
		if (isPermitidoEliminar() || isPermitidoPasarAVigente()) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isPasadoAHistorico()
	 */
	public boolean isPasadoAHistorico() {
		return ProductoresUtils.isPasadoAHistorico(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isIncorporadoComoHistorico()
	 */
	public boolean isIncorporadoComoHistorico() {
		return ProductoresUtils.isIncorporadoComoHistorico(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isSustituidoPorVigente()
	 */
	public boolean isSustituidoPorVigente() {
		return ProductoresUtils.isSustituidoPorVigente(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isVigenteSeleccionable()
	 */
	public boolean isVigenteSeleccionable() {
		return ProductoresUtils.isOriginalVigente(this.marcas, this.fechaFin);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isEliminado()
	 */
	public boolean isEliminado() {
		return ProductoresUtils.isEliminado(marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isHistorico()
	 */
	public boolean isHistorico() {
		return ProductoresUtils.isHistorico(this.marcas, this.fechaFin);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getDebug()
	 */
	public String getDebug() {
		return getTipoObjeto() + "<br/>" + getGuid() + "<br/>" + getNombre()
				+ "<br/>MARCAS:" + this.marcas + "<br/>LCAPREF:" + getIdLCA()
				+ "<br/>FECHAS:" + DateUtils.formatDate(this.fechaInicio) + "-"
				+ DateUtils.formatDate(this.fechaFin);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getTextoMarcas()
	 */
	public String getTextoMarcas() {
		return ProductoresUtils.getTextoMarcas(this.marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setProductorSerieVO(fondos.vos.ProductorSerieVO)
	 */
	public void setProductorSerieVO(ProductorSerieVO productorSerieVO) {
		this.productorSerieVO = productorSerieVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getProductorSerieVO()
	 */
	public ProductorSerieVO getProductorSerieVO() {
		if (productorSerieVO == null) {
			productorSerieVO = new ProductorSerieVO();
			productorSerieVO.setIdserie(getIdSerie());
			productorSerieVO.setTipoProductor(getTipoProductor());
			productorSerieVO.setNombre(getNombre());
			productorSerieVO.setFechaInicial(getFechaInicio());
			productorSerieVO.setFechaFinal(getFechaFin());
			productorSerieVO.setMarcas(getMarcas());
			productorSerieVO.setGuid(getGuid());

		}
		return productorSerieVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#isModificado()
	 */
	public boolean isModificado() {
		return ProductoresUtils.isModificado(marcas);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setIdSerie(java.lang.String)
	 */
	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getIdSerie()
	 */
	public String getIdSerie() {
		return idSerie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getIdDescrSistExt()
	 */
	public String getIdDescrSistExt() {
		if (productorSerieVO != null) {
			return productorSerieVO.getIdDescrSistExt();
		}
		return new String();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getIdLCA()
	 */
	public String getIdLCA() {
		if (productorSerieVO != null) {
			return productorSerieVO.getIdLCAPref();
		}
		return new String();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#getIdProductor()
	 */
	public String getIdProductor() {
		if (productorSerieVO != null) {
			return productorSerieVO.getIdProductor();
		}
		return new String();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.IInfoProductorSerie#setSustituidoVigente(java.lang.String)
	 */
	public void setSustituidoVigente(String sustituidoVigente) {
		if (productorSerieVO != null) {
			productorSerieVO.setSustituidoVigente(sustituidoVigente);
		}
	}

}