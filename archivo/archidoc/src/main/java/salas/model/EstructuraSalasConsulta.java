package salas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import salas.vos.EdificioVO;
import salas.vos.SalasConsultaVOBase;
import se.usuarios.ServiceClient;
import util.TreeModel;
import util.TreeModelItem;
import util.TreeModelListener;

import common.util.ArrayUtils;
import common.util.StringUtils;

public class EstructuraSalasConsulta extends TreeModel {

	GestionSalasConsultaBI gestionSalasConsultaBI = null;
	String idElementoForSubtree = null;
	ServiceClient serviceClient = null;

	public EstructuraSalasConsulta(
			GestionSalasConsultaBI gestionSalasConsultaBI,
			ServiceClient serviceClient) {
		this.gestionSalasConsultaBI = gestionSalasConsultaBI;
		this.serviceClient = serviceClient;
	}

	public EstructuraSalasConsulta(
			GestionSalasConsultaBI gestionSalasConsultaBI,
			ServiceClient serviceClient, String idElementoForSubtree) {
		this.gestionSalasConsultaBI = gestionSalasConsultaBI;
		this.idElementoForSubtree = idElementoForSubtree;
		this.serviceClient = serviceClient;
	}

	public Collection getRootNodes() {
		Collection treeList = null;
		if (StringUtils.isEmpty(idElementoForSubtree)) {
			List custodyArchiveList = serviceClient.getCustodyArchiveList();
			treeList = gestionSalasConsultaBI
					.getEdificiosByIdsArchivo((ArrayUtils
							.toString(custodyArchiveList.toArray())));
		} else {
			treeList = new ArrayList();
			treeList.add(gestionSalasConsultaBI
					.getEdificioById(idElementoForSubtree));
		}
		return treeList;
	}

	public TreeModelItem getParentItem(TreeModelItem item) {
		return null;
	}

	public Collection getChilds(TreeModelItem item) {
		SalasConsultaVOBase elemento = (SalasConsultaVOBase) item;

		if (elemento instanceof EdificioVO) {
			return gestionSalasConsultaBI.getSalas(elemento.getId());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see util.TreeModel#addTreeModelListener(util.TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener listener) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see util.TreeModel#removeTreeModelListener(util.TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener listener) {
	}

	public String getIdElementoForSubtree() {
		return idElementoForSubtree;
	}

	public void setIdElementoForSubtree(String idElementoForSubtree) {
		this.idElementoForSubtree = idElementoForSubtree;
	}

}