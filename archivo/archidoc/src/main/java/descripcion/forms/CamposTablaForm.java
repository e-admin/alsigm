package descripcion.forms;

import common.Constants;
import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.model.TipoCampoEntidad;
import descripcion.vos.CampoTablaVO;

public class CamposTablaForm extends CustomForm {

	private static final long serialVersionUID = 6682406889807283842L;
	private String id = null;
	private String nombre = null;
	private String tipoNorma = null;
	private String idArea = null;
	private String etiquetaXml = null;
	private String etiqXmlFila = null;
	private String descripcion = null;
	private String tipoCampo = null;
	private String norma = null;
	private String area = null;
	private String tipoCampoText = null;
	private String guid = null;

	private String[] camposABorrar = null;

	public CampoTablaVO populate(CampoTablaVO campoTablaVO) {
		if (campoTablaVO != null) {
			campoTablaVO.setId(getId());

			if (StringUtils.isNotEmpty(getGuid())) {
				campoTablaVO.setId(getGuid());
			}

			campoTablaVO.setNombre(getNombre());
			if (tipoNorma != null)
				campoTablaVO.setTipoNorma(Integer.parseInt(getTipoNorma()));
			campoTablaVO.setIdArea(getIdArea());
			campoTablaVO.setEtiquetaXml(getEtiquetaXml());

			if(!StringUtils.isEmpty(getEtiqXmlFila())){
				campoTablaVO.setEtiqXmlFila(getEtiqXmlFila());
			}else{
				campoTablaVO.setEtiqXmlFila(getEtiquetaXml() + Constants.UNDERSCORE
						+ Constants.FILA);

			}
			campoTablaVO.setDescripcion(StringUtils
					.changeBlankToNull(getDescripcion()));
		}
		return campoTablaVO;
	}

	public void set(CampoTablaVO campoTablaVO) {
		if (campoTablaVO != null) {
			setId(campoTablaVO.getId());
			setNombre(campoTablaVO.getNombre());
			setTipoNorma(String.valueOf(campoTablaVO.getTipoNorma()));
			setIdArea(campoTablaVO.getIdArea());
			setEtiquetaXml(campoTablaVO.getEtiquetaXml());
			setEtiqXmlFila(campoTablaVO.getEtiqXmlFila());
			setDescripcion(campoTablaVO.getDescripcion());
			setTipoCampo(String.valueOf(TipoCampoEntidad.DATO_VALUE));
		}
	}

	public void reset() {
		id = null;
		nombre = null;
		tipoNorma = null;
		idArea = null;
		etiquetaXml = null;
		etiqXmlFila = null;
		descripcion = null;
		tipoCampo = null;
		guid = null;
	}

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

	public String getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(String tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String[] getCamposABorrar() {
		return camposABorrar;
	}

	public void setCamposABorrar(String[] camposABorrar) {
		this.camposABorrar = camposABorrar;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getNorma() {
		return norma;
	}

	public void setNorma(String norma) {
		this.norma = norma;
	}

	public String getTipoCampoText() {
		return tipoCampoText;
	}

	public void setTipoCampoText(String tipoCampoText) {
		this.tipoCampoText = tipoCampoText;
	}

	/**
	 * @return el guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            el guid a fijar
	 */
	public void setGuid(String guid) {
		this.guid = StringUtils.trim(guid);
	}
}
