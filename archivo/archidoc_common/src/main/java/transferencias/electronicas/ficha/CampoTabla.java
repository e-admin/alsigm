package transferencias.electronicas.ficha;

import java.util.ArrayList;
import java.util.List;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;

public class CampoTabla extends CampoFichaBase{
	private List<CampoFilaTabla> filas = new ArrayList<CampoFilaTabla>();

	/**
	 * @return el filas
	 */
	public List<CampoFilaTabla> getFilas() {
		return filas;
	}

	/**
	 * @param filas el filas a fijar
	 */
	public void setFilas(List<CampoFilaTabla> filas) {
		this.filas = filas;
	}

	public void addFila(CampoFilaTabla filaTabla){
		this.filas.add(filaTabla);
	}


	public CampoFilaTabla getFila(int posicion){
		if(posicion > 0 && posicion <= this.filas.size()){
			return this.filas.get(posicion-1);
		}

		return null;
	}

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_TABLA;
	}
}
