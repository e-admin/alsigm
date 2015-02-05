package es.ieci.tecdoc.isicres.admin.beans;

import java.util.ArrayList;
import java.util.List;

public class OficinasInformeBean {
	private List oficinasInforme;

	public OficinasInformeBean() {
		oficinasInforme = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return oficinasInforme.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public OficinasInformeBean get(int index) {
		return (OficinasInformeBean) oficinasInforme.get(index);
	}

	/**
	 * @param oficina
	 */
	public void add(OficinaInformeBean oficina) {
		oficinasInforme.add(oficina);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return oficinasInforme;
	}

	/**
	 * @param oficinasTiposAsunto
	 */
	public void setLista(List oficinasInforme) {
		this.oficinasInforme = oficinasInforme;
	}
}
