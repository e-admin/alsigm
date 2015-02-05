package descripcion.vos;

import common.vos.BaseVO;

/**
 * Clase que contiene la información de usos de objetos
 */
public class UsoObjetoVO extends BaseVO {

	private static final long serialVersionUID = -8021509214190948954L;
	private String idObj;
	private int tipoObj;
	private String idObjUsuario;
	private int tipoObjUsuario;

	public String getIdObj() {
		return idObj;
	}

	public void setIdObj(String idObj) {
		this.idObj = idObj;
	}

	public String getIdObjUsuario() {
		return idObjUsuario;
	}

	public void setIdObjUsuario(String idObjUsuario) {
		this.idObjUsuario = idObjUsuario;
	}

	public int getTipoObj() {
		return tipoObj;
	}

	public void setTipoObj(int tipoObj) {
		this.tipoObj = tipoObj;
	}

	public int getTipoObjUsuario() {
		return tipoObjUsuario;
	}

	public void setTipoObjUsuario(int tipoObjUsuario) {
		this.tipoObjUsuario = tipoObjUsuario;
	}

}
