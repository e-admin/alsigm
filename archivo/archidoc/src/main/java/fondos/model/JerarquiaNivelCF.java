/*
 * Created on 21-jun-2005
 *  
 */
package fondos.model;

import fondos.vos.JerarquiaNivelCFVO;

public class JerarquiaNivelCF implements JerarquiaNivelCFVO {

	String idNivelPadre = null;
	int tipoNivelPadre = 0;
	String idNivelHijo = null;
	int tipoNivelHijo = 0;

	public String getIdNivelPadre() {
		return idNivelPadre;
	}

	public void setIdNivelPadre(String idNivelPadre) {
		this.idNivelPadre = idNivelPadre;
	}

	public int getTipoNivelPadre() {
		return tipoNivelPadre;
	}

	public void setTipoNivelPadre(int tipoNivelPadre) {
		this.tipoNivelPadre = tipoNivelPadre;
	}

	public String getIdNivelHijo() {
		return idNivelHijo;
	}

	public void setIdNivelHijo(String idNivelHijo) {
		this.idNivelHijo = idNivelHijo;
	}

	public int getTipoNivelHijo() {
		return tipoNivelHijo;
	}

	public void setTipoNivelHijo(int tipoNivelHijo) {
		this.tipoNivelHijo = tipoNivelHijo;
	}

}