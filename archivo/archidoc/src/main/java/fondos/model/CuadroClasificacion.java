package fondos.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.CollectionUtils;
import util.TreeModel;
import util.TreeModelItem;
import util.TreeModelListener;
import util.TreeView;

import common.ConfigConstants;
import common.bi.GestionCuadroClasificacionBI;

import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;

public class CuadroClasificacion extends TreeModel {

	GestionCuadroClasificacionBI service = null;

	CuadroClasificacion(GestionCuadroClasificacionBI service) {
		this.service = service;
	}

	public void addItem(TreeModelItem item) {
	}

	public Collection getRootNodes() {
		// return service.getHijosElementoCuadroClasificacion(null);
		return getChildNodes(null);
	}

	public Collection getChilds(TreeModelItem item) {
		Collection childs = null;

		ElementoCuadroClasificacionVO modelItem = (ElementoCuadroClasificacionVO) item;
		INivelCFVO nivel = service.getNivelCF(modelItem.getIdNivel());
		if (nivel != null
				&& ElementoCuadroClasificacion.TIPO_SERIE != nivel.getTipo()) {
			// childs =
			// service.getHijosElementoCuadroClasificacion(modelItem.getId());
			childs = getChildNodes(modelItem.getId());
		}

		return childs;
	}

	public TreeModelItem getParentItem(TreeModelItem item) {
		ElementoCuadroClasificacionVO modelItem = (ElementoCuadroClasificacionVO) item;
		return service.getElementoPadre(modelItem.getId());
	}

	public void notifyItemAdded(TreeModelItem source, TreeModelItem addedItem) {
	}

	public void notifyItemsAdded(TreeModelItem source, List addedItems) {
	}

	public void registerView(TreeView treeView) {
	}

	public void addTreeModelListener(TreeModelListener listener) {

	}

	public void removeTreeModelListener(TreeModelListener listener) {
	}

	protected List getChildNodes(String id) {
		// List childNodes = service.getHijosElementoCuadroClasificacion(id);
		List childNodes = null;

		if ((id == null)
				&& (!service.getServiceClient().isPersonalArchivo())
				&& ConfigConstants.getInstance()
						.getOcultarClasificadoresFondosUsuariosOficina()) {
			childNodes = service.getFondosWithPermissions();
		} else {
			childNodes = service
					.getHijosElementoCuadroClasificacionWithPermissions(id);
		}
		if (!CollectionUtils.isEmpty(childNodes)) {

			/*
			 * Sólo mostrar los elementos en estado VIGENTE o NO_VIGENTE, no los
			 * temporales, ni eliminados.
			 */
			List nodesToDelete = new ArrayList();
			ElementoCuadroClasificacionVO aux;
			for (int i = 0; i < childNodes.size(); i++) {
				aux = (ElementoCuadroClasificacionVO) childNodes.get(i);
				if (IElementoCuadroClasificacion.VIGENTE != aux.getEstado()
						&& IElementoCuadroClasificacion.NO_VIGENTE != aux
								.getEstado()) {
					nodesToDelete.add(aux);
				}
			}
			if (nodesToDelete.size() > 0) {
				childNodes.removeAll(nodesToDelete);
			}
		}

		return childNodes;
	}
}