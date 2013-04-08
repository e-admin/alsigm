package ieci.tecdoc.sgm.autenticacion.servlet;

import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.xml.DOMConfigurator;


/*
 * Define the following servlet in the web.xml file for your web-application.
 * <servlet>
 *       <servlet-name>log4j-init</servlet-name>
 *       <servlet-class>com.foo.Log4jInit</servlet-class>
 *       <init-param>
 *          <param-name>log4j-init-file</param-name>
 *          <param-value>WEB-INF/classes/log4j.lcf</param-value>
 *       </init-param>
 *       <load-on-startup>1</load-on-startup>
 * </servlet>
 */
public class Log4jInitServlet extends HttpServlet
{   
   //~ Methods -----------------------------------------------------------------

   /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public void init()
   {
    /* ESTO FUNCIONA EN TOMCAT PERO NO EN WEBLOGIC. FUNCIONA  
       * EN TOMCAT SI EN EL WEB.XML SE PONE COMO PARAMETRO DEL
       * SERVLET 
       * <param-name>log4j-init-file</param-name>
       * <param-value>WEB-INF/classes/log4j.xml</param-value>
       * 
     String prefix = getServletContext().getRealPath("/"); 
     String sufix = this.getInitParameter("log4j-init-file");
     DOMConfigurator.configureAndWatch(prefix + sufix);
     System.out.println ("prefix " + prefix);
     System.out.println ("sufix " + sufix);
     System.out.println ("prefix + sufix: " + prefix + sufix);
     */
    
      // ESTO PARA WEBLOGIC Y PROBABLEMENTE PARA TOMCAT
      String logname = this.getInitParameter("log4j-init-file");
     URL logurl = this.getClass().getResource("/resources/" + logname);
     if(logurl != null)
     {
      DOMConfigurator.configure (logurl);
     }
     else
     {
       System.out.println("log.properties not found!!");
     }
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res)
   {

      // nothing to do
   }
   
   public void doPost(HttpServletRequest req, HttpServletResponse res)
   {

      // nothing to do
   }

}
