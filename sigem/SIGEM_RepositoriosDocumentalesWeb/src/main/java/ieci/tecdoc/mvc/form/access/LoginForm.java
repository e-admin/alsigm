package ieci.tecdoc.mvc.form.access;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/**
 * 
 * @struts.form
 *   name = "login"
 *
 */
public class LoginForm extends ActionForm
{

   //~ Instance fields ---------------------------------------------------------

   private String m_user;
   private String m_pwd;
   private String m_language;

   //~ Static fields/initializers ----------------------------------------------

   /** Log4J Logger for this class */
   private static Logger logger = Logger.getLogger(LoginForm.class);

   //~ Methods -----------------------------------------------------------------

   public String getUser()
   {

      return m_user;

   }

   public void setUser(String user)
   {

      this.m_user = user;

   }

   public String getPwd()
   {

      return m_pwd;

   }
   
   public void setMaskPwd(String maskPwd)
   {

      this.m_pwd = maskPwd;

   }


   public String getLanguage()
   {

      return m_language;

   }

   public void setLanguage(String language)
   {

      this.m_language = language;

   }

   public void reset(ActionMapping mapping, HttpServletRequest request)
   {

      this.m_user     = null;
      this.m_pwd      = null;
      this.m_language = null;

   }

   /*
    * (non-Javadoc)
    *
    * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
    *      javax.servlet.http.HttpServletRequest)
    */
   public ActionErrors validate(ActionMapping mapping,
                                HttpServletRequest request)
   {

      int          cntsTriesNum;
      ActionErrors errors        = new ActionErrors();
      HttpSession  session       = request.getSession(true);
      
      if(logger.isDebugEnabled())
      {
         logger.debug("session_id: "+session.getId());
         logger.debug("User language: "+getLanguage());
      }
      //ActionMessage
      
      if((getUser() == null) || (getUser().length() < 1))
         errors.add("user", new ActionMessage("error.login.user_is_empty"));

      if((getPwd() == null) || (getPwd().length() < 1))
         errors.add("maskPwd", new ActionMessage("error.login.pwd_is_empty"));
      
      return errors;

   }

   /**
    * toString methode: creates a String representation of the object
    *
    * @return the String representation
    */
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      
      buffer.append("LoginForm[");
      buffer.append("m_user = ").append(m_user);
      buffer.append(", m_pwd = *****");
      buffer.append(", m_language = ").append(m_language);
      buffer.append("]");

      return buffer.toString();

   }

}
