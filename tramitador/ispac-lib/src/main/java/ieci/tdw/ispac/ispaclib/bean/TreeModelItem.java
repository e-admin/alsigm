package ieci.tdw.ispac.ispaclib.bean;

/**
 * Metodos a implementar por los elementos que componen una estructura jerarquica en forma de arbol para
 * para que esta pueda ser mostrada por el componente de visualizacion TreeView
 * 
 * @see TreeView, TreeNode
 */
public interface TreeModelItem {
	
	public Object getItemId(); 
	public String getItemName();
	public String getItemPath();
	public boolean isLeaf();
}