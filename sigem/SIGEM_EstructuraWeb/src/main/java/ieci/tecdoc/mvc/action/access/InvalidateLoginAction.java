package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.util.MvcDefs;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;


public class InvalidateLoginAction extends BaseAction
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(InvalidateLoginAction.class);

   /* (non-Javadoc)
    * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   protected ActionForward executeLogic(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response)
      throws Exception
   {
      
      HttpSession session = request.getSession(false);
      if(session != null) {
         
         ActionMessages messages      = null;
         Locale         locale        = null;
         Integer        cntsTriesNum  = new Integer(0); 
         
         if(session.getAttribute(Globals.MESSAGE_KEY) != null)
            messages = (ActionMessages)session.getAttribute(Globals.MESSAGE_KEY);
         if(session.getAttribute(Globals.LOCALE_KEY) != null)
            locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
         if(session.getAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES) != null)
            cntsTriesNum = (Integer)session.getAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES);
            
         logger.info("proceeding to invalidate session");
         
         session.invalidate();
         
         logger.info("creating new session");
         
         HttpSession newSession = request.getSession(true);
         
         saveMessages(request,messages);
         //newSession.setAttribute(Globals.MESSAGE_KEY, messages);
         //newSession.setAttribute(Globals.LOCALE_KEY, locale);
         //newSession.setAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES, cntsTriesNum);
         
      }
         
      return mapping.findForward(MvcDefs.TOKEN_FORWARD_SUCCESS);
   }

}
