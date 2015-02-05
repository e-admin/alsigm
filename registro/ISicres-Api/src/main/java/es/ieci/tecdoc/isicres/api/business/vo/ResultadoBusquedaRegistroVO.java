package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.List;

/**
 * Clase que recoge el resultado de la busqueda de registros, ya sea de entrada
 * o de salida, teniendo en cuenta unos criterios de búsqueda.
 * 
 * @author IECISA
 * 
 */
public class ResultadoBusquedaRegistroVO {

	public ResultadoBusquedaRegistroVO(List registers, int total) {
		this.registers = registers;
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public List getRegisters() {
		return registers;
	}

	// Members
	protected int total;

	protected List registers;
}
