package transferencias.electronicas.ficha;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.util.ListUtils;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;

public class CampoFilaTabla extends CampoFichaBase{
	private List<ICampoFicha> campos = new ArrayList<ICampoFicha>();

	private String idTabla = null;

	/**
	 * @return el camposTabla
	 */
	public List<ICampoFicha> getCampos() {
		return campos;
	}

	/**
	 * @param camposTabla el camposTabla a fijar
	 */
	public void setCampos(List<ICampoFicha> camposTabla) {
		this.campos = camposTabla;
	}

	public void addCampo(ICampoFicha campoFicha){
		this.campos.add(campoFicha);
	}

	/**
	 * @param idTabla el objeto idTabla a fijar
	 */
	public void setIdTabla(String idTabla) {
		this.idTabla = idTabla;
	}

	/**
	 * @return el objeto idTabla
	 */
	public String getIdTabla() {
		return idTabla;
	}

    public ICampoFicha getCampoListaByEtiqueta(String etiqueta){
    	ICampoFicha campoFicha = null;
    	if(etiqueta != null){
    		if(campos != null && ListUtils.isNotEmpty(campos)){
    			for (Iterator<ICampoFicha> iterator = campos.iterator(); iterator
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

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_FILA;
	}
}
