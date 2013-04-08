package deposito.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.usuarios.ServiceClient;
import util.TreeModel;
import util.TreeModelItem;
import util.TreeModelListener;

import common.ConfigConstants;
import common.util.ArrayUtils;
import common.util.StringUtils;

import deposito.vos.ElementoVO;

public class EstructuraDeposito extends TreeModel {

	GestorEstructuraDepositoBI depositoBI = null;
	String idElementoForSubtree = null;
	ServiceClient serviceClient = null;

	EstructuraDeposito(GestorEstructuraDepositoBI depositoBI,
			ServiceClient serviceClient) {
		this.depositoBI = depositoBI;
		this.serviceClient = serviceClient;
	}

	EstructuraDeposito(GestorEstructuraDepositoBI depositoBI,
			ServiceClient serviceClient, String idElementoForSubtree) {
		this.depositoBI = depositoBI;
		this.idElementoForSubtree = idElementoForSubtree;
		this.serviceClient = serviceClient;
	}

	public Collection getRootNodes() {
		Collection treeList = null;
		if (StringUtils.isEmpty(idElementoForSubtree))
			if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
				treeList = depositoBI.getEdificios();
			} else {
				List custodyArchiveList = serviceClient.getCustodyArchiveList();
				treeList = depositoBI.getUbicacionesXIdsArchivo(ArrayUtils
						.toString(custodyArchiveList.toArray()));
			}
		else {
			// String
			// tipoElemento=depositoBI.getUbicacion(idElementoForSubtree).getIdTipoElemento();
			// treeList=depositoBI.getHijosElemento(idElementoForSubtree,
			// tipoElemento);
			treeList = new ArrayList();
			// ElementoVO
			// nodoRaizSubarbol=depositoBI.getNoAsignable(idElementoForSubtree);
			// if(nodoRaizSubarbol!=null){
			// String
			// tipoElemento=depositoBI.getUbicacion(idElementoForSubtree).getIdTipoElemento();
			// treeList=depositoBI.getHijosElemento(idElementoForSubtree,
			// tipoElemento);
			// }else
			treeList.add(depositoBI.getUbicacion(idElementoForSubtree));
		}
		return treeList;
	}

	public TreeModelItem getParentItem(TreeModelItem item) {
		return null;
	}

	public Collection getChilds(TreeModelItem item) {
		ElementoVO elementoDeposito = (ElementoVO) item;
		return depositoBI.getHijosElemento(elementoDeposito.getId(),
				elementoDeposito.getIdTipoElemento());
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