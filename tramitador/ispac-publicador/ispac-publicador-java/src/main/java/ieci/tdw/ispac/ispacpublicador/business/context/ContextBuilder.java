package ieci.tdw.ispac.ispacpublicador.business.context;


import ieci.tdw.ispac.ispacpublicador.business.exceptions.RenderException;
import ieci.tdw.ispac.ispacpublicador.business.util.xml.XmlTag;
import ieci.tdw.ispac.ispacpublicador.business.vo.BaseVO;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Clase encargada de la construcción de un contexto.
 * @author Ildefonso Noreña
 *
 */
//TODO Reconstruir el contexto
public class ContextBuilder
{
	private String root; 
    
	protected Logger logger = Logger.getLogger(ContextBuilder.class);
    /**
	 * Mapa que contendrá parejas de atributos-valores del contexto.
	 */
	private final Map itemmap;
	
	//TODO Javadocear
	private static List fixedProperties = 
			Arrays.asList(new String[]{
	        ContextProperties.CTX_MILESTONE, 
	        ContextProperties.CTX_PROCEDURE,	
	        ContextProperties.CTX_STAGE,	
	        ContextProperties.CTX_TASK,	
	        ContextProperties.CTX_DOCTYPE,	
	        ContextProperties.CTX_OBJECT,	
	        ContextProperties.CTX_EVENT,	
	        ContextProperties.CTX_DATE,	
	        ContextProperties.CTX_INFO,
	        ContextProperties.CTX_INFO_AUX
	        });
	
	/**
	 * Constuctor 
	 */
	public ContextBuilder(){
		itemmap=new HashMap();
		root = ContextProperties.CTX_ROOT;
	}

	/**
	 * Constuctor 
	 */
	public ContextBuilder(String root){
		itemmap=new HashMap();
		setRoot(root);
	}	
	
	/**
	 * Añade un nuevo atributo con su valor al contexto a crear.
	 * @param itemKey Nuevo atributo
	 * @param itemValue Valor del atributo
	 */
	public void addContext(String itemKey,String itemValue){
		itemmap.put(itemKey,XmlTag.newTag(itemKey,itemValue, true));
	}

	/**
	 * 
	 * Añade todos las parejas atributo-valor que se encuentran
	 * en <code>'parammap'</code> al contexto a construir.
	 * @param parammap Mapa con parejas de atributos-valor.
	 */
	public void addContext(Map parammap){
		Iterator it=parammap.entrySet().iterator();
		while (it.hasNext()){
		    Map.Entry var = (Map.Entry) it.next();
		    String key="";
		    String value="";

		    if (var.getKey()!=null)
		        key=var.getKey().toString();

		    if (var.getValue()!=null)
		        value=var.getValue().toString();

		    addContext(key,value);
        }
	}

	/**
	 * @return el contexto creado
	 */
	public MilestoneContext getContext(){
		String tags="";
		Iterator it=itemmap.entrySet().iterator();
		while (it.hasNext())
			tags+=(String)((Map.Entry)it.next()).getValue();

		String xml= XmlTag.getXmlInstruction("ISO-8859-1")+
					XmlTag.newTag(getRoot(),tags);
		return new MilestoneContext(xml, getRoot());
	}

	/**
	 * Limpia los datos del contexto 
	 */
	public void newContext(){
		itemmap.clear();
	}

    /**
     * Añade al contexto todos los atributos de <code>'vo'</code>. Para ello,
     * haciendo uso de reflection, obtiene todos los getter de la clase
     * del objeto <code>'vo'</code> y los invoca para obtener el valor de
     * los atributos.
     * @param vo Value Object con los datos a introducir en el contexto
     * @param excludeFixedProperties indica si se excluyen del contexto las Propiedades Fijas
     */
    public void addContext(BaseVO vo, boolean excludeFixedProperties) {
		Method[] methods = vo.getClass().getMethods();
		String methodNname;
		String propertyName;
	    for (int i=0; i<methods.length; i++) {
	        methodNname = methods[i].getName();
	        propertyName = obtainPropertyName(methodNname);
//	        if (methodNname.startsWith("get") 
//	               && !methodNname.equals("getClass") 
//	               && ((      excludeFixedProperties 
//	                       && !fixedProperties.contains(propertyName)) 
//	                  || (    !excludeFixedProperties 
//	                       && !methodNname.substring(3).equalsIgnoreCase(ContextProperties.CTX_INFO_AUX)) )   ){

	        if (isProperty(methods[i]) && include(excludeFixedProperties, propertyName)){ 
		        try {
			            
			            Object result = methods[i].invoke(vo, new Object[]{});
			            result = (result == null) ? "" : ""+result;
			            //itemmap.put(propertyName,XmlTag.newTag(propertyName,result));
			            itemmap.put(propertyName,XmlTag.newTag(propertyName,result,true));
			            
		            } catch (Exception e) {
		                RenderException.show(logger, e, "Error al llamar por reflection al metodo '"+methodNname+"'");
		            }
	        }
	    }	
		
        
    }

    /**
     * @param excludeFixedProperties true si se excluyen las propiedades que se encuentran en el campo 'fixedProperties'
     * @param propertyName Nombre de la propiedad a incluir
     * @return true si se puede incluir la propiedad <code>'propertyName'</code>, false en caso contrario
     */
    private boolean include(boolean excludeFixedProperties, String propertyName){
        if (  (excludeFixedProperties && !fixedProperties.contains(propertyName)) || (!excludeFixedProperties && !propertyName.equalsIgnoreCase(ContextProperties.CTX_INFO_AUX))  )
            return true;
        return false;
    }
    
    
    /**
     * @param getterName Nombre del método a comprobar si es de tipo getter
     * @return nombre de la propiedad sobre la que se aplica el método <code>getterName</code>,
     * si es que se aplica sobre alguna, devolverá la cadena vacia si no es asi.
     */
    private String obtainPropertyName(String getterName)
    {
        if(getterName.startsWith("get"))
            return getterName.substring("get".length()).toLowerCase();
        if(getterName.startsWith("is"))
            return getterName.substring("is".length()).toLowerCase();
        else
            return "";
    }
    
    
    private boolean isProperty(Method method)
    {
        return (method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterTypes().length == 0 && method.getDeclaringClass() != (java.lang.Object.class);
    }
    
    
    /**
     * @return Returns the root.
     */
    public String getRoot() {
        return root;
    }
    /**
     * @param root The root to set.
     */
    public void setRoot(String root) {
        this.root = root;
    }
}