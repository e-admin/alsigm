package transferencias.actions;

import common.bi.GestionCuadroClasificacionBI;

import fondos.model.Serie;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Objeto con propiedades de presentacion de series
 * 
 */
public class SeriePO extends Serie {

	GestionCuadroClasificacionBI cclfBI = null;

	ElementoCuadroClasificacionVO funcion = null;
	ElementoCuadroClasificacionVO subFuncion = null;

	public SeriePO(GestionCuadroClasificacionBI cclfBI) {
		super();
		this.cclfBI = cclfBI;
	}

	public ElementoCuadroClasificacionVO getFuncion() {
		if (funcion == null) {
			ElementoCuadroClasificacionVO subFuncion = getSubFuncion();
			funcion = cclfBI.getElementoCuadroClasificacion(subFuncion
					.getIdPadre());
		}
		return funcion;
	}

	public ElementoCuadroClasificacionVO getSubFuncion() {
		if (subFuncion == null) {
			subFuncion = cclfBI.getElementoCuadroClasificacion(getIdPadre());
		}
		return subFuncion;
	}

}