package ieci.tecdoc.sgm.catalogo_tramites.plugin;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;


public class BasePlugin implements PlugIn
{

   /* (non-Javadoc)
    * @see org.apache.struts.action.PlugIn#destroy()
    */
   public void destroy()
   {
   }

   /* (non-Javadoc)
    * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
    */
   public void init(ActionServlet servlet, ModuleConfig config)
      throws ServletException
   {
      
   }

   
}
