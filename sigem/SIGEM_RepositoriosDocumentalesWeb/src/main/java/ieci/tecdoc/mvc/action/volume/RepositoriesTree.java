/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.volume.Repositories;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class RepositoriesTree extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(RepositoriesTree.class);
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	try {
    		List repositorios = getRepositoriesList(entidad);
    		Iterator it = repositorios.iterator();
        
    		StringBuffer sbuff = new StringBuffer();
    		creaNodoRaiz(sbuff,"Repositorios");
    		while (it.hasNext())
    		{
	            TipoRepositorio obj = (TipoRepositorio) it.next();
	            String nombre = obj.getNombre();
	            String id = String.valueOf(obj.getId());
	            creaNodo(sbuff, nombre, id);
	            anyadeHijo(sbuff, id);
	        }
	        String arbol = sbuff.toString();
	        request.setAttribute("tree", arbol);        
    	} catch(Exception e) {
    		logger.debug("Error al obtener el arbol de repositorios", e.fillInStackTrace());
    		StringBuffer sbuff = new StringBuffer();
    		creaNodoRaiz(sbuff,"Repositorios");
    		request.setAttribute("tree", sbuff.toString());
    	}
        
        return mapping.findForward("success");
    }
    public List getRepositoriesList(String entidad) throws Exception
    {
        List lista = new ArrayList();
        Repositories repositories = new Repositories();
        repositories.load(entidad);
        int n = repositories.count();
        for (int i = 0; i < n; i ++ )
        {
            Repository obj = repositories.getRepository(i);
            TipoRepositorio tmp= new TipoRepositorio(obj.getId(), obj.getName());
            if (logger.isDebugEnabled())
                logger.debug(tmp.toString());
            lista.add(tmp);
            
        }
        return lista;
        
    }
    
    public void creaNodoRaiz(StringBuffer sbuff, String titulo){
        String sentencia= "\n var tree = new WebFXTree('"+ titulo +"',\"javascript:nodoRaizEvent('"+ titulo+"');\",'','"+Constantes.TOKEN_ICON_REPOSITORIES +"','"+Constantes.TOKEN_ICON_REPOSITORIES +"'); \n";
        sbuff.append(sentencia);
    }
    public void creaNodo(StringBuffer sbuff, String titulo, String id){
        String sentencia = "var nodo" + id + " = new WebFXTreeItem('"+titulo+"',\"javascript:carga("+id+",'"+ titulo +"')\",'','"+Constantes.TOKEN_ICON_REPOSITORIES +"','"+Constantes.TOKEN_ICON_REPOSITORIES +"','"+id+"'); \n";
        sbuff.append(sentencia);
    }
    public void anyadeHijo(StringBuffer sbuff, String id)
    {
        String sentencia = "tree.add(nodo"+ id +"); \n";
        sbuff.append(sentencia);
    }
    
    class TipoRepositorio
    {
        private int id;
        private String nombre;
        
        
        /**
         * @param id2
         * @param name
         */
        public TipoRepositorio(int id, String name) {
            this.id = id;
            this.nombre = new String(name);
            // TODO Auto-generated constructor stub
        }
        /**
         * @return Returns the id.
         */
        public int getId() {
            return id;
        }
        /**
         * @param id The id to set.
         */
        public void setId(int id) {
            this.id = id;
        }
        /**
         * @return Returns the nombre.
         */
        public String getNombre() {
            return nombre;
        }
        /**
         * @param nombre The nombre to set.
         */
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public String toString()
        {
            String s =  "Repositorio: " + id + " "+ nombre;
            return s;
        }
    }

}
