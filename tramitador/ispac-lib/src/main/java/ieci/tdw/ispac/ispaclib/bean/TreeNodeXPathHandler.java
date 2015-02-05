package ieci.tdw.ispac.ispaclib.bean;

import org.apache.commons.jxpath.DynamicPropertyHandler;


/**
 * Implementacion de DynamicPropertyHandler empleado para la obtencion de nodos dentro de una jerarquia de 
 * elementos en arbol mediante JXPath
 * @see org.apache.commons.jxpath.DynamicPropertyHandler
 *
 */
public class TreeNodeXPathHandler implements DynamicPropertyHandler {

	private static final String[] STRING_ARRAY = new String[0];

	public Object getProperty(Object bean, String propertyName) {
      String childId = propertyName;
      // los id's de nodo que comienzan por un caracter que no sea una letra no son manejados de forma
      // adecuada por jxpath por lo que se les antepone la cadena 'item'
      if (propertyName.indexOf("item") == 0 )
         childId = propertyName.substring(4);
		return bean!= null?((INode)bean).getChild(childId):null;
	}
	public String[] getPropertyNames(Object bean) {
		return (String[])((TreeNode)bean).getChildIDs().toArray(STRING_ARRAY); 
	}
	public void setProperty(Object arg0, String arg1, Object arg2) {

	}
	
}
