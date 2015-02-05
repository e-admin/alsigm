package transferencias.vos;

/**
 * VO para obtener un valor de un campo del xml de información de la unidad
 * documental
 */
public class ValorCampoDescripcionUdocReVO {

	/**
	 * Orden del valor
	 */
	private String orden = null;

	/**
	 * Tipo del valor
	 */
	private String tipo = null;

	/**
	 * Valor del campo
	 */
	private String valor = null;

	/**
	 * Tipo de medida
	 */
	private String tipoMedida = null;

	/**
	 * Unidad de medida
	 */
	private String unidadMedida = null;

	/**
	 * Calificador de la fecha
	 */
	private String calificador = null;

	/**
	 * Formato de la fecha
	 */
	private String formato = null;

	/**
	 * Separador de la fecha
	 */
	private String sep = null;

	/**
	 * Tipo de referencia
	 */
	private String tipoObjRef = null;

	/**
	 * Identificador de la referencia
	 */
	private String idObjRef = null;

	/**
	 * Nombre de la referencia
	 */
	private String nombre = null;

	/**
	 * Constructor por defecto
	 */
	public ValorCampoDescripcionUdocReVO() {
	}

	/**
	 * @param orden
	 *            Orden del valor
	 * @param tipo
	 *            Tipo del valor
	 * @param valor
	 *            Valor del campo
	 * @param tipoMedida
	 *            Tipo de medida
	 * @param unidadMedida
	 *            Unidad de medida
	 * @param calificador
	 *            Calificador de la fecha
	 * @param formato
	 *            Formato de la fecha
	 * @param sep
	 *            Separador de la fecha
	 * @param tipoObjRef
	 *            Tipo de referencia
	 * @param idObjRef
	 *            Id de la referencia
	 * @param nombre
	 *            Nombre de la referencia
	 */
	public ValorCampoDescripcionUdocReVO(String orden, String tipo,
			String valor, String tipoMedida, String unidadMedida,
			String calificador, String formato, String sep, String tipoObjRef,
			String idObjRef, String nombre) {
		super();
		this.orden = orden;
		this.tipo = tipo;
		this.valor = valor;
		this.tipoMedida = tipoMedida;
		this.unidadMedida = unidadMedida;
		this.calificador = calificador;
		this.formato = formato;
		this.sep = sep;
		this.tipoObjRef = tipoObjRef;
		this.idObjRef = idObjRef;
		this.nombre = nombre;
	}

	/**
	 * Devuelve el calificador de la fecha
	 * 
	 * @return calificador de la fecha
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * Establece el calificador de la fecha
	 * 
	 * @param calificador
	 *            calificador de la fecha
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * Devuelve el formato de la fecha
	 * 
	 * @return formato de la fecha
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * Establece el formato de la fecha
	 * 
	 * @param formato
	 *            formato de la fecha
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * Devuelve el id de la referencia
	 * 
	 * @return id de la referencia
	 */
	public String getIdObjRef() {
		return idObjRef;
	}

	/**
	 * Establece el id de la referencia
	 * 
	 * @param idObjRef
	 *            id de la referencia
	 */
	public void setIdObjRef(String idObjRef) {
		this.idObjRef = idObjRef;
	}

	/**
	 * Devuelve el orden del valor
	 * 
	 * @return orden del valor
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * Establece el orden del valor
	 * 
	 * @param orden
	 *            orden del valor
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * Devuelve el separador de la fecha
	 * 
	 * @return separador de la fecha
	 */
	public String getSep() {
		return sep;
	}

	/**
	 * Establece el separador de la fecha
	 * 
	 * @param sep
	 *            separador de la fecha
	 */
	public void setSep(String sep) {
		this.sep = sep;
	}

	/**
	 * Devuelve el tipo del valor
	 * 
	 * @return el tipo del valor
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo del valor
	 * 
	 * @param tipo
	 *            tipo del valor
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve el tipo de medida
	 * 
	 * @return tipo de medida
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * Establece el tipo de medida
	 * 
	 * @param tipoMedida
	 *            tipo de medida
	 */
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * Devuelve el tipo de referencia
	 * 
	 * @return tipo de referencia
	 */
	public String getTipoObjRef() {
		return tipoObjRef;
	}

	/**
	 * Establece el tipo de referencia
	 * 
	 * @param tipoObjRef
	 *            tipo de referencia
	 */
	public void setTipoObjRef(String tipoObjRef) {
		this.tipoObjRef = tipoObjRef;
	}

	/**
	 * Devuelve la unidad de medida
	 * 
	 * @return unidad de medida
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * Establece la unidad de medida
	 * 
	 * @param unidadMedida
	 *            unidad de medida
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * Devuelve el valor del campo
	 * 
	 * @return valor del campo
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Establece el valor del campo
	 * 
	 * @param valor
	 *            valor del campo
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve el nombre de la referencia
	 * 
	 * @return nombre de la referencia
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la referencia
	 * 
	 * @param nombre
	 *            nombre de la referencia
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
