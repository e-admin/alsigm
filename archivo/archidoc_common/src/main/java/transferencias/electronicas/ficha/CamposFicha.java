package transferencias.electronicas.ficha;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.util.ListUtils;


import transferencias.electronicas.ficha.ICampoFicha;

public class CamposFicha {
	private List<ICampoFicha> camposFicha = new ArrayList<ICampoFicha>();

	/**
	 * @return el camposFicha
	 */
	public List<ICampoFicha> getCamposFicha() {
		return camposFicha;
	}

	/**
	 * @param camposFicha el camposFicha a fijar
	 */
	public void setCamposFicha(List<ICampoFicha> camposFicha) {
		this.camposFicha = camposFicha;
	}

    public void addCampoFicha(ICampoFicha campoFicha){
    	this.camposFicha.add(campoFicha);
    }

    public ICampoFicha getCampoListaByEtiqueta(String etiqueta){
    	ICampoFicha campoFicha = null;
    	if(etiqueta != null){
    		if(camposFicha != null && ListUtils.isNotEmpty(camposFicha)){
    			for (Iterator<ICampoFicha> iterator = camposFicha.iterator(); iterator
						.hasNext();) {
					ICampoFicha campo =  iterator.next();

					if(campo != null && etiqueta.equals(campo.getEtiqueta())){
						return campo;
					}
				}
    		}
    	}
    	return campoFicha;
    }

}
