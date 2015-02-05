package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Metodos a implementar por una estructura de datos en forma de arbol
 * jerarquico que permiten que esta pueda ser mostrada mediante un componente de
 * visualizacion
 * 
 * @see TreeView
 */
public abstract class TreeModel {

	public abstract Collection getRootNodes();

	public abstract TreeModelItem getParentItem(TreeModelItem item);

	public abstract Collection getChilds(TreeModelItem item);

	public abstract void addTreeModelListener(TreeModelListener listener);

	public abstract void removeTreeModelListener(TreeModelListener listener);

	public String calculateItemPath(TreeModelItem elemento) {
		// obtencion de padres
		List parents = new ArrayList();
		TreeModelItem parentItem = getParentItem(elemento);

		while (parentItem != null && parentItem.getItemId() != null) {
			parents.add(parentItem);
			parentItem = getParentItem(parentItem);
		}
		// composicion de itempath
		StringBuffer itemPath = new StringBuffer();
		List reverseOrderParents = CollectionUtils.reverse(parents);
		for (Iterator itParents = reverseOrderParents.iterator(); itParents
				.hasNext();) {
			TreeModelItem aParent = (TreeModelItem) itParents.next();
			if (itemPath.length() > 0)
				itemPath.append("/");
			itemPath.append("item").append(aParent.getItemId());
		}
		if (itemPath.length() > 0)
			itemPath.append("/");
		itemPath.append("item").append(elemento.getItemId());
		return itemPath.toString();
	}

}
