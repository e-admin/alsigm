package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.item.Property;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Calcula el número de Tabs Extra a situar.
 * También calcula las propiedades que van en cada Tab para que
 * no queden divididas las categorías en 2 Tabs distintos.
 * @author Ildefonso Noreña
 *
 */
public class ExtraTabsTag extends TagSupport {

    
    /** <code>name</code>: Nombre del atributo que contiene 
     * todas las propiedades dinámicas */
    private String name;
    /** <code>list</code>: Nombre del atributo que se creará 
     * con la lista que almacenará el número de propiedades
     * por cada Tabs*/
    private String list;
    /** <code>map</code>: Nombre del atributo que contiene
     * las categorías de cada propiedad.
     */
    private String map;
    /** <code>var</code>: Nombre del atributo que indica el
     * número de propiedades a mostrar por cada Tab.
     */
    private String var;
    
    public int doStartTag() throws JspException
	{						
        Collection obj = (Collection)pageContext.getAttribute(name);
        Map mapCategorias = (Map)pageContext.getAttribute(map);
        

        //Obtenemos una lista que contendra el numero de propiedades por categoria 
        List listNumProps = getNumPropertiesByCategory(obj, mapCategorias);

        int propertiesByTab = (new Integer (""+pageContext.getAttribute(getVar()))).intValue();
        
        //Obtenemos una lista con el numero de propiedades a mostrar en cada Tab.
        List list = getPropertiesByTab(listNumProps, propertiesByTab);
        //pageContext.setAttribute(getList(),list);
        pageContext.getRequest().setAttribute(getList(),list);

        //TODO No poner el atributo en el pageContext
        pageContext.setAttribute("_extraTabs_", new Integer(list.size()));
        pageContext.getRequest().setAttribute("_extraTabs_", new Integer(list.size()));
		return (EVAL_BODY_INCLUDE);
	}  
    
    
    /**
     * @return
     */
    private List getPropertiesByTab(List listNumProps, int propertiesByTab) {
        int element;
        int subelement;
        List list = new LinkedList();
        for(int i = 0; i< listNumProps.size(); i++){
            
            element = ((Integer)listNumProps.get(i)).intValue();
            if (element >= propertiesByTab || i == listNumProps.size()-1){
                list.add(new Integer(element));
            }
            else{
                int j;
                for(j = i+1; j < listNumProps.size(); j++ ){
                    subelement = ((Integer)listNumProps.get(j)).intValue();
                    element += subelement;
                    if (element >= propertiesByTab){
                        list.add(new Integer(element));
                        break;
                    }
                }
                if (j == listNumProps.size())
                    list.add(new Integer(element));
                i = j;
            }
        }
        
        return list;
    }


    private List getNumPropertiesByCategory(Collection obj, Map mapCategorias){
        Iterator it = obj.iterator();
        Property property;
        
        String currentCategory = "";
        String lastCategory = "";
        int countCurrentCategory = 1;
        List list = new LinkedList();
        while(it.hasNext()){
            property = (Property)it.next();
            currentCategory = ""+mapCategorias.get(property.getRawName());
            
            if (currentCategory.equals(lastCategory))
                countCurrentCategory++;
            else if (!currentCategory.equals(lastCategory) && !lastCategory.equals("") ){
                list.add(new Integer(countCurrentCategory));
                countCurrentCategory = 1;
                
            }
            lastCategory = currentCategory;
        }
        list.add(new Integer(countCurrentCategory));
        return list;
    }
    
    
    /**
     * @return Returns the list.
     */
    public String getList() {
        return list;
    }
    /**
     * @param list The list to set.
     */
    public void setList(String list) {
        this.list = list;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the map.
     */
    public String getMap() {
        return map;
    }
    /**
     * @param map The map to set.
     */
    public void setMap(String map) {
        this.map = map;
    }
    /**
     * @return Returns the var.
     */
    public String getVar() {
        return var;
    }
    /**
     * @param var The var to set.
     */
    public void setVar(String var) {
        this.var = var;
    }
}
