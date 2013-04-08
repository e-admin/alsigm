package ieci.tecdoc.mvc.form.access;

import ieci.tecdoc.sbo.uas.std.UasError;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class ChangePwdForm extends ActionForm
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(ChangePwdForm.class);

   //~ Instance fields ---------------------------------------------------------

   private String m_pwd;
   private String m_newPwd;   
   private String m_confNewPwd;
   
   //~ Methods -----------------------------------------------------------------

   public String getPwd()
   {

      return m_pwd;

   }

   public void setMaskPwd(String pwd)
   {

      this.m_pwd = pwd;

   }

   public String getNewPwd()
   {

      return m_newPwd;

   }

   public void setMaskNewPwd(String newPwd)
   {

      this.m_newPwd = newPwd;

   }

   public String getConfNewPwd()
   {

      return m_confNewPwd;

   }

   public void setMaskConfNewPwd(String confNewPwd)
   {

      m_confNewPwd = confNewPwd;

   }

   public ActionErrors validate(ActionMapping mapping,
                                HttpServletRequest request)
   {
      ActionErrors errors         = new ActionErrors();
      // HttpSession  session        = request.getSession();
      
      if((getPwd() == null) || (getPwd().length() < 1))
         errors.add("pwd", new ActionError("error.changePwd.pwd_is_empty"));

      if((getNewPwd() == null) || (getNewPwd().length() < 1))
         errors.add("newPwd", new ActionError("error.changePwd.newPwd_is_empty"));
     
      if((getConfNewPwd() == null) || (getConfNewPwd().length() < 1))
        errors.add("confNewPwd", new ActionError("error.changePwd.confNewPwd_is_empty"));
     
      if(!(getNewPwd().equals(getConfNewPwd())))
        errors.add("nEqPwd", new ActionError("error.changePwd.new_pwd_not_macth"));

      request.setAttribute(UasError.EC_MUST_CHANGE_PWD,new Boolean (true));
      return errors;

   }
   
   
   public void reset(ActionMapping mapping, HttpServletRequest request)
   {

      this.m_pwd        = null;
      this.m_newPwd     = null;
      this.m_confNewPwd = null;

   }

   

   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ChangePwdForm[");
      buffer.append(", m_pwd = ******");
      buffer.append(", m_newPwd = ******");
      buffer.append(", m_confNewPwd = ******");
      buffer.append("]");
      
      return buffer.toString();
   }
}
