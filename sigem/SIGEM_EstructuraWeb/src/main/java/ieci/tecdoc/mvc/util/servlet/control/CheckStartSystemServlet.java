package ieci.tecdoc.mvc.util.servlet.control;

//import ieci.tecdoc.idoc.admin.api.user.Login;
import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.mvc.util.control.ErrorMessage;
import ieci.tecdoc.sbo.idoc.api.Login;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;


public class CheckStartSystemServlet extends HttpServlet
{

   //~ Static fields/initializers ----------------------------------------------

   private static final Logger      logger         =  Logger.getLogger(CheckStartSystemServlet.class);
   private static       ArrayList   errorMessages  =  new ArrayList();

   private  ServletContext m_context   =  null;
   private  ActionServlet  m_servlet   =  null;
   //private  ModuleConfig   m_config    =  null;
   
   public void init() throws ServletException
   {

      m_context   =  getServletContext();
      m_servlet   =  (ActionServlet)getServletContext().getAttribute(Globals.ACTION_SERVLET_KEY);
      //m_config    =  getModuleConfig();
      
      if(logger.isDebugEnabled())
      {
         logger.debug("Init CheckStartSystemServlet Servlet.");
         //logger.debug("m_servlet: "+ m_servlet);
      }
      
      check();
      saveErrors();
      super.init();
   }

   private void saveErrors()
   {
      
      if(errorMessages.size() > 0)
      {
         m_context.setAttribute(MvcDefs.TOKEN_SYSTEM_PROPERLY_STARTED, errorMessages);
         logErrors();
      }
   }

   private void check()
   {

      int            loginMethod =  -1;
      Login          login;
      ErrorMessage   eMessage;
      String         message;
      
      //m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_DBENGINE,  new Integer(databaseId));
      
      if (m_servlet.getServletContext().getAttribute(MvcDefs.TOKEN_BD_CONNECT_ERROR) != null)
      {
          
          eMessage =  new ErrorMessage();
          Exception e =  (Exception) m_servlet.getServletContext().getAttribute(MvcDefs.TOKEN_BD_CONNECT_ERROR);
          message  =  "Error getting BD connection: ";
          message += e.getMessage();
          
          //logger.error(e);
          eMessage.setMessage(message);
          errorMessages.add(errorMessages.size(), eMessage);
      }
      
      if (m_servlet.getServletContext().getAttribute(MvcDefs.TOKEN_LDAP_CONNECT_ERROR) != null)
      {
          
          eMessage =  new ErrorMessage();
          Exception e =  (Exception) m_servlet.getServletContext().getAttribute(MvcDefs.TOKEN_LDAP_CONNECT_ERROR);
          message  =  "Error getting LDAP connection: ";
          message += e.getMessage();
          
          //logger.error(e);
          eMessage.setMessage(message);
          errorMessages.add(errorMessages.size(), eMessage);
      }
      
      /*
      if (m_context.getAttribute(MvcDefs.PLUGIN_DBENGINE) == null)
      {
          message  =  "BD Not found. Please, check the DDBB connectivity.";
          eMessage =  new ErrorMessage();
          
          logger.error(message);
          eMessage.setMessage(message);
          errorMessages.add(errorMessages.size(), eMessage);    
      }
      */
      
      /*
      try
      {
         login       =  new Login();
         loginMethod =  login.getLoginMethod();
         
         logger.info("Application Login Method: "+ loginMethod);
         m_context.setAttribute(MvcDefs.TOKEN_LOGIN_METHOD, new Integer(loginMethod));
      }
      catch (Exception e)
      {
         message  =  "Error getting the Application Login Method. Please, check the DDBB connectivity.";
         eMessage =  new ErrorMessage();
         
         logger.error(message);
         logger.error(e.getMessage(),e);
         
         eMessage.setMessage(message);
         errorMessages.add(errorMessages.size(), eMessage);
      }
      */
      
      /* POr ahora no se hacen mas comprobaciones
      if(m_context.getAttribute(MvcDefs.TOKEN_SYSTEM_LANGUAGE) != null)
      {
         message  = "Default Application Language is not setted. Please, check the struts-config.xml File.";
         eMessage = new ErrorMessage();
         
         logger.error(message);
         
         eMessage.setMessage(message);
         errorMessages.add(errorMessages.size(), eMessage);
      }
      
      if(m_context.getAttribute(MvcDefs.TOKEN_INIT_SYSTEM_FILE) != null)
      {

         String         fileName =  (String)m_context.getAttribute(MvcDefs.TOKEN_INIT_SYSTEM_FILE);
         StringBuffer   stb      =  new StringBuffer();

         eMessage = new ErrorMessage();
         
         stb.append("Error loading system configuration file. File [").append(fileName);
         stb.append("] not foung. Please, check the struts-config.xml File");
         
         eMessage.setMessage(stb.toString());
         errorMessages.add(errorMessages.size(), eMessage);
      }
     
      if(m_context.getAttribute(MvcDefs.TOKEN_LOG4J_PROPERTY_CHECK) != null)
      {

         String         fileName =  (String)m_context.getAttribute(MvcDefs.TOKEN_LOG4J_PROPERTY_CHECK);
         StringBuffer   stb      =  new StringBuffer();
         
         eMessage = new ErrorMessage();
         
         stb.append("Error loading system log configuration file. File [").append(fileName);
         stb.append("] not foung. Please, check the struts-config.xml File");
         
         eMessage.setMessage(stb.toString());
         errorMessages.add(errorMessages.size(), eMessage);
      }

      if(m_context.getAttribute(MvcDefs.TOKEN_INIT_LDAP_FILE) != null)
      {

         String       fileName = (String)m_context.getAttribute(MvcDefs.TOKEN_INIT_LDAP_FILE);
         StringBuffer stb      = new StringBuffer();

         eMessage = new ErrorMessage();
         
         stb.append("Error loading system ldap configuration file. File [").append(fileName);
         stb.append("] not foung. Please, check the struts-config.xml File");
         
         eMessage.setMessage(stb.toString());
         errorMessages.add(errorMessages.size(), eMessage);
      }
      
      
      // Yo no necesito subir archivos
      if(m_config != null)
      {
         String   filePath =  m_config.getControllerConfig().getTempDir();
         
         if(logger.isDebugEnabled())
            logger.debug("Default Application Temporal Directory for Uploaded Files: "+filePath);
         
         if(filePath == null)
         {
            message  = "Default Application Temporal Directory for Uploaded Files is not setted. Please, check the struts-config.xml File.";
            eMessage = new ErrorMessage();
            
            logger.error(message);
            
            eMessage.setMessage(message);
            errorMessages.add(errorMessages.size(), eMessage);
         } else
         {
            boolean exists = (new File(filePath)).exists();
            
            if(!exists)
            {
               logger.error("No Application Temporal Directory for Uploaded Files exist ["+filePath+"]");
               logger.error("Trying to create a Temporal Directory for Uploaded Files exist");
               
               boolean success = (new File(filePath)).mkdir();
               if (!success) {
                  message  = "Can't create Default Application Temporal Directory for Uploaded Files ["+filePath+"].";
                  eMessage = new ErrorMessage();
                  
                  logger.error(message);
                  
                  eMessage.setMessage(message);
                  errorMessages.add(errorMessages.size(), eMessage);
               }
            }
         }
      }*/

   }
   
   private void logErrors()
   {
      int numErrors = errorMessages.size();
      
      if(numErrors > 0)
      {
         logger.error("The System has detected "+ numErrors +" critical errors initializating the Application.");
         logger.error("Please, check this errors list before start using the Application: ");
         
         for(int i = 0; i < errorMessages.size(); i++)
            logger.fatal("Error Number: " +(i+1) +" - Description: "+ ((ErrorMessage)(errorMessages.get(i))).getMessage());
      }
   }
   
   protected ModuleConfig getModuleConfig () 
   {
      return (ModuleConfig)getServletContext().getAttribute(Globals.MODULE_KEY);
   }

}
