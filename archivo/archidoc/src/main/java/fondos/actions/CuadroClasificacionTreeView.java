package fondos.actions;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import util.TreeModel;
import util.TreeModelItem;
import util.TreeModelListener;
import util.TreeNode;
import util.TreeView;

import common.util.StringUtils;

import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Vista en forma de árbol jerárquico de la estructura en la que se orgazan los
 * elemento del cuadro de clasificación de fondos documentales
 */
public class CuadroClasificacionTreeView extends TreeView implements
		TreeModelListener {

	public static final String NOMBRE_ATRIBUTO_TIPO_ELEMENTO = "TIPO_ELEMENTO";
	public static final String NOMBRE_ATRIBUTO_COD_REFERENCIA = "COD_REF";
	public static final String NOMBRE_ATRIBUTO_ID = "ID";

	public CuadroClasificacionTreeView(TreeModel model) {
		super("FONDOS DOCUMENTALES", model);
		setNodeComparator(new Comparator() {
			public int compare(Object arg0, Object arg1) {
				if (((TreeNode) arg0).getModelItem() == null
						|| ((TreeNode) arg1).getModelItem() == null)
					return ((TreeNode) arg0).getNodeLabel().compareTo(
							((TreeNode) arg1).getNodeLabel());
				ElementoCuadroClasificacion elem0 = (ElementoCuadroClasificacion) ((TreeNode) arg0)
						.getModelItem();
				ElementoCuadroClasificacion elem1 = (ElementoCuadroClasificacion) ((TreeNode) arg1)
						.getModelItem();

				if (StringUtils.isEmpty(elem0.getOrdPos())
						&& !StringUtils.isEmpty(elem1.getOrdPos())) {
					return -1;
				} else if (StringUtils.isEmpty(elem1.getOrdPos())
						&& !StringUtils.isEmpty(elem0.getOrdPos())) {
					return 1;
				} else if (!StringUtils.isEmpty(elem1.getOrdPos())
						&& !StringUtils.isEmpty(elem0.getOrdPos())) {
					Collator collator = Collator.getInstance(new Locale("es",
							"ES"));
					return collator.compare(elem0.getOrdPos(),
							elem1.getOrdPos());
				} else {
					if (StringUtils.isEmpty(elem0.getCodigo())
							&& !StringUtils.isEmpty(elem1.getCodigo())) {
						return -1;
					} else if (StringUtils.isEmpty(elem1.getCodigo())
							&& !StringUtils.isEmpty(elem0.getCodigo())) {
						return 1;
					} else if (!StringUtils.isEmpty(elem1.getCodigo())
							&& !StringUtils.isEmpty(elem0.getCodigo())) {
						int resultadoComparacion = elem0.getCodigo().compareTo(
								elem1.getCodigo());
						Collator collator = Collator.getInstance(new Locale(
								"es", "ES"));
						if (resultadoComparacion == 0)
							return collator.compare(elem0.getTitulo(),
									elem1.getTitulo());
						return resultadoComparacion;
					} else {
						Collator collator = Collator.getInstance(new Locale(
								"es", "ES"));
						return collator.compare(elem0.getTitulo(),
								elem1.getTitulo());
					}
				}
			}
		});
	}

	public TreeNode insertNode(TreeNode parent, TreeModelItem modelItem) {
		if (parent != null)
			((IElementoCuadroClasificacion) modelItem)
					.setParentElement((IElementoCuadroClasificacion) parent
							.getModelItem());
		TreeNode nuevoNodo = super.insertNode(parent, modelItem);
		nuevoNodo
				.setNodeAtribute(NOMBRE_ATRIBUTO_TIPO_ELEMENTO, String
						.valueOf(((ElementoCuadroClasificacionVO) modelItem)
								.getTipo()));
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_COD_REFERENCIA,
				((ElementoCuadroClasificacionVO) modelItem).getCodReferencia());
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_ID,
				((ElementoCuadroClasificacionVO) modelItem).getId());
		return nuevoNodo;
	}

	public void itemsAdded(TreeModelItem source, List addedItems) {
	}

	public TreeNode itemAdded(TreeModelItem source, TreeModelItem addedItem) {

		TreeNode parentNode = getNode(source.getItemPath());
		if (StringUtils.isEmpty((String) source.getItemId()))
			parentNode = null;
		return insertNode(parentNode, addedItem);
	}

	// public TreeNode createTreeNode(TreeModelItem elemento){
	// CuadroClasificacion cuadroService = (CuadroClasificacion)treeModel;
	// //calculo del itemPath
	// TreeModelItem parentItem = cuadroService.getParentItem(elemento);
	// StringBuffer itemPath = new StringBuffer("");
	// while(parentItem!=null){
	// parentItem = cuadroService.getParentItem(parentItem);
	// if (parentItem!=null){
	// if (itemPath.length()>0)
	// itemPath.append("/");
	//
	// itemPath.append(parentItem.getItemId());
	// }
	// }
	// //busqueda del nodo
	// return getNode(itemPath.toString());
	// }

	public void itemRemoved(TreeModelItem item) {
	}

	public void itemModified(TreeModelItem item) {
	}

}
