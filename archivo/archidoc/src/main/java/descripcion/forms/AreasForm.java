package descripcion.forms;

import org.apache.log4j.Logger;

import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.vos.AreaVO;

public class AreasForm extends CustomForm {

	private static final long serialVersionUID = 1241931714670320754L;
	Logger logger = Logger.getLogger(this.getClass());
	private String id = null;
	private String nombre = null;
	private String tipoNorma = null;
	private String descripcion = null;
	private String guid = null;

	private String[] areasABorrar = null;

	public AreaVO populate(AreaVO areaVO) {
		changeBlankToNull();
		if (areaVO != null) {
			areaVO.setId(getId());

			if (StringUtils.isNotEmpty(getGuid())) {
				areaVO.setId(getGuid());
			}

			areaVO.setNombre(getNombre());
			if (tipoNorma != null)
				areaVO.setTipoNorma(Integer.parseInt(getTipoNorma()));
			areaVO.setDescripcion(getDescripcion());
		}
		return areaVO;
	}

	public void set(AreaVO areaVO) {
		if (areaVO != null) {
			setId(areaVO.getId());
			setNombre(areaVO.getNombre());
			setTipoNorma(String.valueOf(areaVO.getTipoNorma()));
			setDescripcion(areaVO.getDescripcion());
		}
	}

	public void resetBeforeDelete() {
		setAreasABorrar(null);
	}

	public void changeBlankToNull() {
		setId(StringUtils.changeBlankToNull(getId()));
		setNombre(StringUtils.changeBlankToNull(getNombre()));
		setTipoNorma(StringUtils.changeBlankToNull(getTipoNorma()));
		setDescripcion(StringUtils.changeBlankToNull(getDescripcion()));
	}

	public void reset() {
		id = null;
		nombre = null;
		tipoNorma = null;
		descripcion = null;
		areasABorrar = null;
		guid = null;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = StringUtils.trim(id);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = StringUtils.trim(nombre);
	}

	public String getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(String tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public String[] getAreasABorrar() {
		return areasABorrar;
	}

	public void setAreasABorrar(String[] areasABorrar) {
		this.areasABorrar = areasABorrar;
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
