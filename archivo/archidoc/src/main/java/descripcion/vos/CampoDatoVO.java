package descripcion.vos;

import ieci.core.db.DbDataType;

import common.Constants;
import common.util.StringUtils;
import descripcion.model.TipoCampo;

/**
 * VO para el almacenamiento de información de un campo de la ficha.
 */
public class CampoDatoVO extends CampoVO implements ICampoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private int tipo = 0;
	private int tipoNorma = 0;
	private String idArea = null;
	private String idTblPadre = null;
	private int posEnTbl = DbDataType.NULL_LONG_INTEGER;
	private String etiquetaXml = null;
	private String descripcion = null;
	private String tipoText = null;
	private String tipoNormaText = null;
	private String nombreArea = null;
	private String nombreTabla = null;

	public CampoDatoVO() {
		super();
	}

	public CampoDatoVO(String id, String nombre, int tipo) {
		this();

		setId(id);
		setNombre(nombre);
		setTipo(tipo);
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
		this.id = StringUtils.trim(id);
	}

	/**
	 * @return Returns the idArea.
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            The idArea to set.
	 */
	public void setIdArea(String idArea) {
		this.idArea = StringUtils.trim(idArea);
	}

	/**
	 * @return Returns the idTblPadre.
	 */
	public String getIdTblPadre() {
		return idTblPadre;
	}

	/**
	 * @param idTblPadre
	 *            The idTblPadre to set.
	 */
	public void setIdTblPadre(String idTblPadre) {
		this.idTblPadre = idTblPadre;
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
		this.nombre = StringUtils.trim(nombre);
	}

	/**
	 * @return Returns the posEnTbl.
	 */
	public int getPosEnTbl() {
		return posEnTbl;
	}

	/**
	 * @param posEnTbl
	 *            The posEnTbl to set.
	 */
	public void setPosEnTbl(int posEnTbl) {
		this.posEnTbl = posEnTbl;
	}

	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Returns the tipoNorma.
	 */
	public int getTipoNorma() {
		return tipoNorma;
	}

	/**
	 * @param tipoNorma
	 *            The tipoNorma to set.
	 */
	public void setTipoNorma(int tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public String getEtiquetaXml() {
		return etiquetaXml;
	}

	public void setEtiquetaXml(String etiquetaXml) {
		this.etiquetaXml = StringUtils.trim(etiquetaXml);
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getTipoNormaText() {
		return tipoNormaText;
	}

	public void setTipoNormaText(String tipoNormaText) {
		this.tipoNormaText = tipoNormaText;
	}

	public String getTipoText() {
		return tipoText;
	}

	public void setTipoText(String tipoText) {
		this.tipoText = tipoText;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public String getEtiquetaXmlText() {
		StringBuilder str = new StringBuilder();

		if (StringUtils.isNotEmpty(getEtiquetaXmlTabla())) {
			str.append(getEtiquetaXmlTabla()).append(Constants.STRING_SPACE)
					.append(Constants.MAYOR).append(Constants.STRING_SPACE);
		}
		if (StringUtils.isNotEmpty(getEtiquetaXmlFila())) {

			str.append(getEtiquetaXmlFila()).append(Constants.STRING_SPACE)
					.append(Constants.MAYOR).append(Constants.STRING_SPACE);
		}
		str.append(getEtiquetaXml());

		return str.toString();
	}

	public String getEtiqXmlFila() {
		return getEtiquetaXmlFila();
	}

	public void setEtiqXmlFila(String etiqXmlFila) {
		this.setEtiquetaXmlFila(etiqXmlFila);

	}

	public boolean isTextoCorto() {
		return this.tipo == TipoCampo.TEXTO_CORTO_VALUE;
	}

	public boolean isTextoLargo() {
		return this.tipo == TipoCampo.TEXTO_LARGO_VALUE;
	}

	public boolean isFecha() {
		return this.tipo == TipoCampo.FECHA_VALUE;
	}

	public boolean isNumerico() {
		return this.tipo == TipoCampo.NUMERICO_VALUE;
	}

	public boolean isReferencia() {
		return this.tipo == TipoCampo.REFERENCIA_VALUE;
	}
}
