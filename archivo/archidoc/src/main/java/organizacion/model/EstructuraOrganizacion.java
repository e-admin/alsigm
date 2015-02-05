package organizacion.model;

import java.util.ArrayList;
import java.util.Collection;

import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;

import se.usuarios.ServiceClient;
import util.TreeModel;
import util.TreeModelItem;
import util.TreeModelListener;

import common.util.StringUtils;

public class EstructuraOrganizacion extends TreeModel {

	GestionOrganizacionBI organizacionBI = null;
	String idElementoForSubtree = null;
	ServiceClient serviceClient = null;

	public EstructuraOrganizacion(GestionOrganizacionBI organizacionBI,
			ServiceClient serviceClient) {
		this.organizacionBI = organizacionBI;
		this.serviceClient = serviceClient;
	}

	public EstructuraOrganizacion(GestionOrganizacionBI organizacionBI,
			ServiceClient serviceClient, String idElementoForSubtree) {
		this.organizacionBI = organizacionBI;
		this.idElementoForSubtree = idElementoForSubtree;
		this.serviceClient = serviceClient;
	}

	public Collection getRootNodes() {
		Collection treeList = null;
		if (StringUtils.isEmpty(idElementoForSubtree))
			treeList = organizacionBI.getInstituciones();
		else {
			treeList = new ArrayList();
			treeList.add(organizacionBI
					.getOrganizacionById(idElementoForSubtree));
		}
		return treeList;
	}

	public TreeModelItem getParentItem(TreeModelItem item) {
		return null;
	}

	public Collection getChilds(TreeModelItem item) {
		OrganizacionVO elementoOrganizacion = (OrganizacionVO) item;

		return organizacionBI.getOrganizacionesByIdPadre(elementoOrganizacion
				.getId());
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

	public GestionOrganizacionBI getOrganizacionBI() {
		return organizacionBI;
	}

}