/*
 * Created on 21-jun-2005
 *
 */
package fondos.model;

import fondos.vos.INivelCFVO;

public class NivelCF implements INivelCFVO {

	String idNivel = null;
	String nombre = null;
	int tipo;
	String idFichaDescrPref = null;
	TipoNivelCF tipoNivel = null;
	String idFichaClfDocPref;
	String idRepEcmPref;
	int subtipo;

	/**
	 * @param idFichaClfDocPref
	 *            The idFichaClfDocPref to set.
	 */
	public void setIdFichaClfDocPref(String idFichaClfDocPref) {
		this.idFichaClfDocPref = idFichaClfDocPref;
	}

	/**
	 * @param idVolPref
	 *            The idVolPref to set.
	 */
	public void setIdRepEcmPref(String idVolPref) {
		this.idRepEcmPref = idVolPref;
	}

	public NivelCF() {
	}

	public NivelCF(String idNivel) {
		this.idNivel = idNivel;
	}

	public String getIdFichaDescrPref() {
		return idFichaDescrPref;
	}

	public void setIdFichaDescrPref(String idFichaDescrPref) {
		this.idFichaDescrPref = idFichaDescrPref;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
		this.tipoNivel = TipoNivelCF.getTipoNivel(tipo);
	}

	public TipoNivelCF getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(TipoNivelCF tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public String getId() {
		return idNivel;
	}

	public void setId(String idNivel) {
		this.idNivel = idNivel;
	}

	public boolean equals(Object oObject) {
		if (oObject == null)
			return false;
		if (oObject == this)
			return true;
		if (INivelCFVO.class.isInstance(oObject))
			return this.idNivel.equals(((INivelCFVO) oObject).getId());
		return false;
	}

	public String getIdFichaClfDocPref() {
		return idFichaClfDocPref;
	}

	public String getIdRepEcmPref() {
		return idRepEcmPref;
	}

	public int getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public void setTipoNivel(int tipoNivel) {
		this.tipo = tipoNivel;
	}
}