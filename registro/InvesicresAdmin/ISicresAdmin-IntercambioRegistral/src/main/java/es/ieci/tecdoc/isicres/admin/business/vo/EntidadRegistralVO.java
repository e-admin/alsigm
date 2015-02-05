package es.ieci.tecdoc.isicres.admin.business.vo;

import org.apache.commons.lang.StringUtils;

public class EntidadRegistralVO {

	protected int id;
	protected String code;
	protected String name;
	protected int idOfic;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdOfic() {
		return idOfic;
	}
	public void setIdOfic(int idOfic) {
		this.idOfic = idOfic;
	}

	/**
	 * Metodo que valida si los datos principales de la Entidad Registral son blancos (Codigo Entidad y Nombre de la Entidad)
	 * @param codigoEntidad
	 * @param nombreEntidad
	 * @return TRUE - Ambos campos son blancos
	 *         FALSE - Estan rellenos ambos campos
	 */
	public boolean isBlankEntidadRegistral(){
		boolean result = false;

		if (StringUtils.isBlank(this.code)
				&& StringUtils.isBlank(this.name)) {
			result = true;
		}
		return result;
	}



}
