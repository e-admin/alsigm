package deposito.vos;

import common.vos.BaseVO;

/**
 * Datos de formato. Las unidades de instalacion transferidas al archivo pueden
 * ser de diferentes formatos. Igualmente los elementos del deposito fisico en
 * los que se ubican dichas unidades de instalacion aceptan diferentes formatos
 */
public class FormatoHuecoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 3948783995936762385L;

	public static String ID_FORMATO_IRREGULAR = "00000000000000000000000000000000";

	String id = null;
	String nombre = null;
	boolean multidoc = false;
	int tipo;
	boolean regular;
	Double longitud;
	String xinfo = null;
	boolean vigente;

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the longitud.
	 */
	public Double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud
	 *            The longitud to set.
	 */
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
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

	/**
	 * @return Returns the regular.
	 */
	public boolean isRegular() {
		return regular;
	}

	/**
	 * @param regular
	 *            The regular to set.
	 */
	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	/**
	 * @return el tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            el tipo a establecer
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Returns the vigente.
	 */
	public boolean isVigente() {
		return vigente;
	}

	/**
	 * @param vigente
	 *            The vigente to set.
	 */
	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	/**
	 * @return Returns the xinfo.
	 */
	public String getXinfo() {
		return xinfo;
	}

	/**
	 * @param xinfo
	 *            The xinfo to set.
	 */
	public void setXinfo(String xinfo) {
		this.xinfo = xinfo;
	}

	public String toString() {
		return "FormatoHuecoVO - " + this.nombre;
	}

	public boolean isMultidoc() {
		return multidoc;
	}

	public void setMultidoc(boolean multidoc) {
		this.multidoc = multidoc;
	}

	public void setEsMultidoc(int esMultidoc) {
		this.multidoc = esMultidoc == 1;
	}

	public int getEsMultidoc() {
		return this.multidoc ? 1 : 0;
	}

	public void setEsRegular(int regular) {
		this.regular = regular == 1;
	}

	public int getEsRegular() {
		return this.regular ? 1 : 0;
	}

	public void setEsVigente(int vigente) {
		this.vigente = vigente == 1;
	}

	public int getEsVigente() {
		return this.vigente ? 1 : 0;
	}

}
