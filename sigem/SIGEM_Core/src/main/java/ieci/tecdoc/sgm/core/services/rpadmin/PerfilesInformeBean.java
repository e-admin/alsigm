package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

public class PerfilesInformeBean {
	private List perfilesInforme;

	public PerfilesInformeBean() {
		perfilesInforme = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return perfilesInforme.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public PerfilesInformeBean get(int index) {
		return (PerfilesInformeBean) perfilesInforme.get(index);
	}

	/**
	 * @param perfil
	 */
	public void add(PerfilInformeBean perfil) {
		perfilesInforme.add(perfil);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return perfilesInforme;
	}

	/**
	 * @param perfiles informe
	 */
	public void setLista(List perfilesInforme) {
		this.perfilesInforme = perfilesInforme;
	}
}