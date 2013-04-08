/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.volume.VolumeLists;
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
public class ListTree extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(ListTree.class);
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        List listas = getVolumeListList(entidad);
        Iterator it = listas.iterator();
        
        StringBuffer sbuff = new StringBuffer();
        creaNodoRaiz(sbuff,"Listas");
        while (it.hasNext())
        {
            TipoLista obj = (TipoLista) it.next();
            String nombre = obj.getNombre();
            String id = String.valueOf(obj.getId());
            creaNodo(sbuff, nombre, id);
            anyadeHijo(sbuff, id);
        }
        String arbol = sbuff.toString();
        request.setAttribute("tree", arbol);        
        
        
        return mapping.findForward("success");
    }
    public List getVolumeListList(String entidad) throws Exception // obtener la lista de listas de volumenes
    {
        List lista = new ArrayList();
        VolumeLists volumeLists = new VolumeLists();

        volumeLists.load(entidad);
        int n = volumeLists.count();
        for (int i = 0; i < n; i ++ )
        {
            ieci.tecdoc.idoc.admin.api.volume.VolumeList obj = volumeLists.getVolumeList(i);
            TipoLista tmp= new TipoLista(obj.getId(), obj.getName());
            if (logger.isDebugEnabled())
                logger.debug(tmp.toString());
            lista.add(tmp);
            
        }
        return lista;
        
    }
    
    public void creaNodoRaiz(StringBuffer sbuff, String titulo){
        //String sentencia= "\n var tree = new WebFXTree('"+ titulo +"','javascript:nodoRaizEvent();'); \n";
        String sentencia= "\n var tree = new WebFXTree('"+ titulo +"',\"javascript:nodoRaizEvent('"+ titulo+"');\",'','"+Constantes.TOKEN_ICON_LIST +"','"+Constantes.TOKEN_ICON_LIST +"'); \n";
        
        //String sentencia= "\n var tree = new WebFXTree('"+ titulo +"','javascript:nodoRaizEvent();','','"+Constantes.TOKEN_ICON_LIST+"','"+Constantes.TOKEN_ICON_LIST +"'); \n";
        
        sbuff.append(sentencia);
    }
    public void creaNodo(StringBuffer sbuff, String titulo, String id){
        //String sentencia =  "var nodo" + id + " = new WebFXTreeItem('"+titulo+"','javascript:carga("+id+")','','"+Constantes.TOKEN_ICON_LIST +"','"+Constantes.TOKEN_ICON_LIST +"','"+id+"'); \n";
        String sentencia = "var nodo" + id + " = new WebFXTreeItem('"+titulo+"',\"javascript:carga("+id+",'"+ titulo +"')\",'','"+Constantes.TOKEN_ICON_LIST +"','"+Constantes.TOKEN_ICON_LIST +"','"+id+"'); \n";
        sbuff.append(sentencia);
    }
    public void anyadeHijo(StringBuffer sbuff, String id)
    {
        String sentencia = "tree.add(nodo"+ id +"); \n";
        sbuff.append(sentencia);
    }
    
    class TipoLista
    {
        private int id;
        private String nombre;
        
        
        /**
         * @param id2
         * @param name
         */
        public TipoLista(int id, String name) {
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
            String s =  "Lista: " + id + " "+ nombre;
            return s;
        }
    }

}
