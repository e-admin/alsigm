package descripcion.vos;

import common.Constants;
import common.vos.BaseVO;

import descripcion.model.TipoCampo;

public class CampoTablaVO extends BaseVO implements ICampoVO {

	private static final long serialVersionUID = 9022425270967846832L;
	private String id = null;
	private String nombre = null;
	private int tipoNorma = 0;
	private String idArea = null;
	private String etiquetaXml = null;
	private String etiqXmlFila = null;
	private String descripcion = null;
	private String nombreArea = null;
	private String tipoNormaText = null;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEtiquetaXml() {
		return etiquetaXml;
	}

	public void setEtiquetaXml(String etiquetaXml) {
		this.etiquetaXml = etiquetaXml;
	}

	public String getEtiqXmlFila() {
		return etiqXmlFila;
	}

	public void setEtiqXmlFila(String etiqXmlFila) {
		this.etiqXmlFila = etiqXmlFila;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(int tipoNorma) {
		this.tipoNorma = tipoNorma;
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

	public int getTipo() {
		return TipoCampo.TABLA_VALUE;
	}

	public void setTipo(int tipo) {
	}

	public String getIdTblPadre() {
		return null;
	}

	public void setEtiquetaXmlTabla(String etiquetaXml) {
	}

	public void setNombreTabla(String nombre) {
	}

	public String getTipoText() {
		return TipoCampo.TABLA_LABEL;
	}

	public void setTipoText(String tipoText) {

	}

	public String getEtiquetaXmlText() {
		StringBuilder str = new StringBuilder();

		str.append(getEtiquetaXml()).append(Constants.STRING_SPACE)
				.append(Constants.MAYOR).append(Constants.STRING_SPACE)
				.append(getEtiqXmlFila());

		return str.toString();
	}

	public String getNombreTabla() {
		return null;
	}

	public boolean isTextoCorto() {
		return false;
	}

	public boolean isTextoLargo() {
		return false;
	}

	public boolean isFecha() {
		return false;
	}

	public boolean isNumerico() {
		return false;
	}

	public boolean isReferencia() {
		return false;
	}

}
