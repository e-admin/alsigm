package deposito.vos;

import org.apache.commons.lang.StringUtils;

import xml.config.ConfiguracionArchivoManager;

import common.vos.IKeyId;

import deposito.DepositoConstants;

public class TipoElementoVO implements IKeyId {

	public static final int TIPO_ASIGNABLE = 1;
	public static final int TIPO_NO_ASIGNABLE = 0;

	protected String idTipoElemento;
	protected String nombreTipoElemento;
	protected String nombreAbreviado;
	protected boolean asignable;
	protected String idpadre;
	protected String descripcion;
	protected String caracterorden;
	protected Integer tipoord;

	private boolean elementoReferenciado = false;
	private boolean hasSubniveles = false;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdpadre() {
		return idpadre;
	}

	public void setIdpadre(String idpadre) {
		this.idpadre = idpadre;
	}

	public String getId() {
		return idTipoElemento;
	}

	public void setId(String idTipoElemento) {
		this.idTipoElemento = idTipoElemento;
	}

	public String getNombre() {
		return nombreTipoElemento;
	}

	public void setNombre(String nombreTipoElemento) {
		this.nombreTipoElemento = nombreTipoElemento;
	}

	public String getNombreAbreviado() {
		return nombreAbreviado;
	}

	public void setNombreAbreviado(String nombreAbreviado) {
		this.nombreAbreviado = nombreAbreviado;
	}

	public boolean isTipoAsignable() {
		return asignable;
	}

	public boolean esTipoUbicacion() {
		return StringUtils.equals(idTipoElemento,
				DepositoConstants.ID_TIPO_ELEMENTO_UBICACION);
	}

	public boolean isTipoDeposito() {
		return StringUtils.equals(idTipoElemento,
				DepositoConstants.ID_TIPO_ELEMENTO_DEPOSITO);
	}

	public boolean isTipoUbicacion() {
		return esTipoUbicacion();
	}

	public int getAsignable() {
		return asignable ? TIPO_ASIGNABLE : TIPO_NO_ASIGNABLE;
	}

	public void setAsignable(int asignable) {
		this.asignable = (asignable == TIPO_ASIGNABLE);
	}

	public String getCaracterorden() {
		return caracterorden;
	}

	public void setCaracterOrden(String caracterorden) {
		this.caracterorden = caracterorden;
	}

	/**
	 * @return el tipoord
	 */
	public Integer getTipoord() {
		return tipoord;
	}

	/**
	 * @param tipoord
	 *            el tipoord a establecer
	 */
	public void setTipoord(Integer tipoord) {
		this.tipoord = tipoord;
	}

	// IMPLEMENTACIONES
	public boolean isOrdenacionEnAnchura() {
		if (DepositoConstants.ORDENACION_ANCHURA.equals(this.tipoord)) {
			return true;
		}
		return false;
	}

	public boolean isOrdenacionEnProfundidad() {
		if (DepositoConstants.ORDENACION_PROFUNDIDAD.equals(this.tipoord)) {
			return true;
		}
		return false;
	}

	public String getImagen() {
		return getIcono().getIconoDefecto();
	}

	public IconoDeposito getIcono() {
		return ConfiguracionArchivoManager.getInstance().getIconoDeposito(
				idTipoElemento);
	}

	public boolean isEditable() {
		if (!esTipoUbicacion()) {
			return true;
		}
		return false;
	}

	public boolean isEliminable() {
		if (!isTipoUbicacion() && !isTipoDeposito()) {
			return true;
		}
		return false;
	}

	public boolean isPuedeSerAsignable() {
		if (!DepositoConstants.ID_TIPO_ELEMENTO_UBICACION.equals(idpadre)) {
			return true;
		}
		return false;
	}

	// FIN IMPLEMENTACIONES
	public void setElementoReferenciado(boolean elementoReferenciado) {
		this.elementoReferenciado = elementoReferenciado;
	}

	public boolean isElementoReferenciado() {
		return elementoReferenciado;
	}

	public void setHasSubniveles(boolean hasSubniveles) {
		this.hasSubniveles = hasSubniveles;
	}

	public boolean isHasSubniveles() {
		return hasSubniveles;
	}

}
