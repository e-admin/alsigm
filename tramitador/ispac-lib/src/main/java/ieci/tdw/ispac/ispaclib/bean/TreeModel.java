package ieci.tdw.ispac.ispaclib.bean;

import java.util.Collection;

/**
 * Metodos a implementar por una estructura de datos en forma de arbol jerarquico que permiten que esta
 * pueda ser mostrada mediante un componente de visualizacion
 * 
 * @see TreeView
 */
public interface TreeModel {

	public Collection getRootNodes();
	public TreeModelItem getParentItem(TreeModelItem item);
	public Collection getChilds(String language, TreeModelItem item);
	public void addTreeModelListener(TreeModelListener listener);
	public void removeTreeModelListener(TreeModelListener listener);
}