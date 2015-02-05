package deposito.vos;

import common.vos.IKeyValue;

import deposito.DepositoConstants;

/**
 * Value Object para las ubicaciones del fondo físico
 */
public class DepositoVO extends ElementoNoAsignableVO implements IKeyValue {

	public final static String ID_TIPO_ELEMENTO_UBICACION = DepositoConstants.ID_TIPO_ELEMENTO_UBICACION;
	// public final static String NOMBRE_TIPO_ELEMENTO_UBICACION = "Ubicacion";
	public final static String ID_TIPO_HUECO = "H";

	public DepositoVO() {
		setIdTipoElemento(DepositoConstants.ID_TIPO_ELEMENTO_UBICACION);
	}

	protected String ubicacion;
	protected String idArchivo;
	protected String nombreArchivo;
	protected int tipoSignaturacion;

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	// public String getIdtipoelemento(){
	// return ID_TIPO_ELEMENTO_UBICACION;
	// }
	public boolean isAsignable() {
		return false;
	}

	public void setId(String id) {
		super.setId(id);
		this.iddeposito = id;
	}

	public String getItemPath() {
		return "item" + getId();
	}

	public String getPathName() {
		return getNombre();
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public int getTipoSignaturacion() {
		return tipoSignaturacion;
	}

	public void setTipoSignaturacion(int tipoSignaturacion) {
		this.tipoSignaturacion = tipoSignaturacion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getKey()
	 */
	public String getKey() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getValue()
	 */
	public String getValue() {
		return getNombre();
	}
}
