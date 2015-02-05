package descripcion.forms;

import ieci.core.db.DbDataType;

import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.model.TipoCampoEntidad;
import descripcion.vos.CampoDatoVO;

public class CamposDatoForm extends CustomForm {

	private static final long serialVersionUID = -654866479949149507L;
	private String id = null;
	private String nombre = null;
	private String tipo = null;
	private String tipoNorma = null;
	private String idArea = null;
	private String idTblPadre = null;
	private String posenTbl = null;
	private String etiquetaXml = null;
	private String descripcion = null;
	private String tipoCampo = null;
	private String guid = null;

	private String[] camposABorrar = null;

	public CampoDatoVO populate(CampoDatoVO campoDatoVO) {
		changeBlankToNull();
		if (campoDatoVO != null) {
			campoDatoVO.setId(getId());

			if (StringUtils.isNotBlank(getGuid())) {
				campoDatoVO.setId(getGuid());
			}

			campoDatoVO.setNombre(getNombre());
			if (tipo != null)
				campoDatoVO.setTipo(Integer.parseInt(getTipo()));
			campoDatoVO.setIdArea(getIdArea());
			if (tipoNorma != null)
				campoDatoVO.setTipoNorma(Integer.parseInt(getTipoNorma()));

			campoDatoVO.setIdTblPadre(getIdTblPadre());
			if (posenTbl != null && posenTbl.trim() != "")
				campoDatoVO.setPosEnTbl(Integer.parseInt(getPosenTbl()));
			campoDatoVO.setEtiquetaXml(getEtiquetaXml());
			campoDatoVO.setDescripcion(getDescripcion());
		}
		return campoDatoVO;
	}

	public void set(CampoDatoVO campoDatoVO) {
		if (campoDatoVO != null) {
			setId(campoDatoVO.getId());

			setNombre(campoDatoVO.getNombre());
			setTipo(String.valueOf(campoDatoVO.getTipo()));
			setTipoNorma(String.valueOf(campoDatoVO.getTipoNorma()));
			setIdArea(campoDatoVO.getIdArea());
			setIdTblPadre(campoDatoVO.getIdTblPadre());
			if (campoDatoVO.getPosEnTbl() == DbDataType.NULL_LONG_INTEGER)
				setPosenTbl("");
			else
				setPosenTbl(String.valueOf(campoDatoVO.getPosEnTbl()));
			setEtiquetaXml(campoDatoVO.getEtiquetaXml());
			setDescripcion(campoDatoVO.getDescripcion());
			setTipoCampo(String.valueOf(TipoCampoEntidad.DATO_VALUE));
		}
	}

	public void reset() {
		id = null;
		nombre = null;
		tipo = null;
		tipoNorma = null;
		idArea = null;
		idTblPadre = null;
		posenTbl = null;
		etiquetaXml = null;
		descripcion = null;
		tipoCampo = null;
		setGuid(null);
	}

	public void changeBlankToNull() {
		setId(StringUtils.changeBlankToNull(getId()));
		setNombre(StringUtils.changeBlankToNull(getNombre()));
		setTipo(StringUtils.changeBlankToNull(getTipo()));
		setTipoNorma(StringUtils.changeBlankToNull(getTipoNorma()));
		setIdArea(StringUtils.changeBlankToNull(getIdArea()));
		setIdTblPadre(StringUtils.changeBlankToNull(getIdTblPadre()));
		setPosenTbl(StringUtils.changeBlankToNull(getPosenTbl()));
		setEtiquetaXml(StringUtils.changeBlankToNull(getEtiquetaXml()));
		setDescripcion(StringUtils.changeBlankToNull(getDescripcion()));
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
		this.etiquetaXml = StringUtils.trim(etiquetaXml);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = StringUtils.trim(id);
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = StringUtils.trim(idArea);
	}

	public String getIdTblPadre() {
		return idTblPadre;
	}

	public void setIdTblPadre(String idTblPadre) {
		this.idTblPadre = StringUtils.trim(idTblPadre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = StringUtils.trim(nombre);
	}

	public String getPosenTbl() {
		return posenTbl;
	}

	public void setPosenTbl(String posenTbl) {
		this.posenTbl = posenTbl;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(String tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public void setCamposABorrar(String[] camposABorrar) {
		this.camposABorrar = camposABorrar;
	}

	public String[] getCamposABorrar() {
		return camposABorrar;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public void setGuid(String guid) {
		this.guid = StringUtils.trim(guid);
	}

	public String getGuid() {
		return guid;
	}

}
