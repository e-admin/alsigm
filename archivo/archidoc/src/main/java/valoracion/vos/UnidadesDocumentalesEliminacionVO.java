/**
 *
 */
package valoracion.vos;

import java.util.HashMap;
import java.util.List;

import common.util.ListUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UnidadesDocumentalesEliminacionVO {
	private List listaUnidades;
	private HashMap mapUInstParciales;

	public List getListaUnidades() {
		return listaUnidades;
	}

	public HashMap getMapUInstParciales() {
		return mapUInstParciales;
	}

	public void setListaUnidades(List listaUnidades) {
		this.listaUnidades = listaUnidades;
	}

	public void setMapUInstParciales(HashMap uInstParciales) {
		this.mapUInstParciales = uInstParciales;
	}

	public UnidadesDocumentalesEliminacionVO(List listaUnidades,
			HashMap mapUInstParciales) {
		super();
		this.listaUnidades = listaUnidades;
		this.mapUInstParciales = mapUInstParciales;
	}

	public UnidadesDocumentalesEliminacionVO(List listaUnidades) {
		super();
		this.listaUnidades = listaUnidades;
	}

	public int getNumeroUdocsAEliminar() {
		if (ListUtils.isNotEmpty(listaUnidades)) {
			return listaUnidades.size();
		}

		return 0;
	}

	public int getNumeroUInstParciales() {
		if (mapUInstParciales != null) {
			return mapUInstParciales.size();
		}

		return 0;
	}
}
