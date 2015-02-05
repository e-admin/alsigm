package descripcion.vos;

import descripcion.model.xml.card.Valor;

/**
 * VO para el almacenamiento de información de un campo de tipo referencia.
 */
public class CampoReferenciaVO extends ValorCampoGenericoVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int REFERENCIA_A_ELEMENTO_DESCRIPTOR = 1;
	public static final int REFERENCIA_A_ELEMENTO_CF = 2;
	private int tipoObjRef;
	private String idObjRef = null;
	private String nombre = null;
	private String descripcion = null;

	public CampoReferenciaVO(String idCampo, int numOrden, String idObjRef,
			int tipoObjRef, int tipoElemento) {
		setTipo(TIPO_REFERENCIA);
		setIdCampo(idCampo);
		setOrden(numOrden);
		setTipoObjRef(tipoObjRef);
		setIdObjRef(idObjRef);
		setTipoElemento(tipoElemento);
	}

	public CampoReferenciaVO() {
		super();
		setTipo(TIPO_REFERENCIA);
	}

	public CampoReferenciaVO(String idElementoCF, String idCampo, int orden,
			int tipoObjRef, String idObjRef, int tipoElemento) {
		super();
		setTipo(TIPO_REFERENCIA);
		setIdObjeto(idElementoCF);
		setIdCampo(idCampo);
		setOrden(orden);
		setTipoObjRef(tipoObjRef);
		setIdObjRef(idObjRef);
		setTipoElemento(tipoElemento);
	}

	/**
	 * @return Returns the idObjRef.
	 */
	public String getIdObjRef() {
		return idObjRef;
	}

	/**
	 * @param idObjRef
	 *            The idObjRef to set.
	 */
	public void setIdObjRef(String idObjRef) {
		this.idObjRef = idObjRef;
	}

	/**
	 * @return Returns the tipoObjRef.
	 */
	public int getTipoObjRef() {
		return tipoObjRef;
	}

	/**
	 * @param tipoObjRef
	 *            The tipoObjRef to set.
	 */
	public void setTipoObjRef(int tipoObjRef) {
		this.tipoObjRef = tipoObjRef;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setValue(String value) {
		setNombre(value);
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene la información del Valor.
	 * 
	 * @return Valor.
	 */
	public Valor getValorInfo() {
		Valor v = new Valor();

		v.setValor(this.nombre);
		v.setIdRef(this.idObjRef);
		v.setTipoRef(this.tipoObjRef);
		v.setOrden(this.getOrden());
		v.setDescripcion(this.descripcion);
		v.setTipoElemento(this.getTipoElemento());

		return v;
	}

}
