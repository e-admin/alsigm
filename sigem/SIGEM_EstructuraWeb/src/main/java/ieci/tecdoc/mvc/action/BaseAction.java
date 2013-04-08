package ieci.tecdoc.mvc.action;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.error.ErrorBean;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.api.FolderObject;
import ieci.tecdoc.sbo.idoc.folder.search.FolderSearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.PropertyMessageResources;


public abstract class BaseAction extends Action
{

   private static Logger logger = Logger.getLogger(BaseAction.class);
   protected            ServletContext ctx      =  null;
   private static boolean statesVolumesListInitialized = false;
   private static List statesVolumesList;
   
   private static List repositoryAtts;
   private static boolean repositoryAttsInitialized = false;
   
   private static boolean repositoryTypeListInitialized = false;
   private static List repositoryTypeList;
   
   private static boolean repositoryTypeServerListInicialized = false;
   private static List repositoryTypeServerList;
   
   private static boolean repositoryPlataformListInicialized = false;
   private static List repositoryPlataformList;
    
   public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
                         throws Exception
   {
       
	   	
      if(ctx == null)
      {
      	ctx  =  request.getSession(false).getServletContext();
      }
      
      ActionMessages messages          =  new ActionMessages();
      ActionMessage  message           =  null;
      String         exceptionhandle   =  null;
      ActionForward  forward           =  null;
      String bundle = Constantes.MESSAGES_GENERAL_ERRORS;
      
      try
      {
         exceptionhandle   =  ctx.getAttribute(MvcDefs.PLUGIN_EXCEPTION_HANDLE).toString();
         forward = executeLogic(mapping, form, request, response);
         // return forward;
      }
       catch(IeciTdException ieciEx)
      {

         String   errorCode   =  ieciEx.getErrorCode();
         
         
         PropertyMessageResources pmr_api_errors		= (PropertyMessageResources)ctx.getAttribute(Constantes.MESSAGES_API_ERRORS);
         PropertyMessageResources pmr_general_errors	= (PropertyMessageResources)ctx.getAttribute(Constantes.MESSAGES_GENERAL_ERRORS);
         String errorMessage = new String();
         
         long errorCodeNumber = -1;
         try {
             errorCodeNumber = Long.parseLong(errorCode);
             if (errorCodeNumber > MvcError.EC_PREFIX) {
                 errorMessage = pmr_general_errors.getMessage(errorCode);
             }
             else{
                 errorMessage = pmr_api_errors.getMessage(errorCode);
                 bundle = Constantes.MESSAGES_API_ERRORS;
             }
             
         }catch (Exception e) {// No es un error de administracion
         	errorMessage = pmr_general_errors.getMessage(errorCode);
         	
         }
         
        logger.info("errorCode: " + errorCode);

         if(errorMessage != null)
         {
            message  =  new ActionMessage(errorCode);
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);
            saveMessages(request, messages);
            logger.info("errorMessage: " + errorMessage );

         }
         else
         {
             // Ya que no esta en los ficheros de properties, decimos que es un error de aplicacion
             logger.error("ActionBase gets error: "+ ieciEx.getStackTrace().length, ieciEx);
             message  =  new ActionMessage(MvcDefs.ERROR_APPLICATION_GENERAL);
             messages.add(ActionMessages.GLOBAL_MESSAGE, message);
             saveMessages(request, messages);
         }
         forward = mapping.findForward(MvcDefs.TOKEN_FORWARD_ERROR);
         request.setAttribute("bundle", bundle);

      }
       catch(Exception e)
      {
           
         logger.error("ActionBase gets error: "+e.getStackTrace().length, e);
         message  =  new ActionMessage(MvcDefs.ERROR_APPLICATION_GENERAL);
         messages.add(ActionMessages.GLOBAL_MESSAGE, message);
         saveMessages(request, messages);
         /*
         ErrorBean   error =  popUpException(exceptionhandle, e);
         request.setAttribute(MvcDefs.ERROR_BEAN, error);
         logger.error("Voy a devolver: "+ mapping.findForward(MvcDefs.TOKEN_FORWARD_ERROR)+"_"+ messages);
         */
         forward = mapping.findForward(MvcDefs.TOKEN_FORWARD_ERROR);
         request.setAttribute("bundle", bundle);
      }
       
       return forward; 
      

   }
// Funciones mias
   /**
    * @param request
    * @param nombre
    */
   // 
   
   protected List getStatesVolumeList()
   {
       if (!statesVolumesListInitialized)
       {
           statesVolumesList = new ArrayList();
           
           statesVolumesList.add( new LabelValueBean("Disponible", String.valueOf(VolumeDefs.VOL_STAT_NOT_READY)));
           statesVolumesList.add( new LabelValueBean("Solo lectura",String.valueOf(VolumeDefs.VOL_STAT_READONLY)));
           statesVolumesList.add( new LabelValueBean("Lleno", String.valueOf(VolumeDefs.VOL_STAT_FULL )));
           statesVolumesListInitialized  = true;
       }
       return statesVolumesList; 
   }
   
   protected List getRepositoryAtts()
   {
       if (!repositoryAttsInitialized)
       {
           repositoryAtts = new ArrayList();
           repositoryAtts.add( new LabelValueBean("Disponible", String.valueOf(VolumeDefs.REP_STAT_OFFLINE)));
           repositoryAtts.add( new LabelValueBean("Solo lectura",String.valueOf(VolumeDefs.REP_STAT_READONLY)));
           repositoryAttsInitialized= true;
       }
       return repositoryAtts; 
   }
   
   protected List getRepositoryTypeList()
   {
       if (!repositoryTypeListInitialized){
           repositoryTypeList = new ArrayList();
           repositoryTypeList.add( new LabelValueBean("FTP", String.valueOf(VolumeDefs.REP_TYPE_FTP)));
           repositoryTypeList.add( new LabelValueBean("PFS", String.valueOf(VolumeDefs.REP_TYPE_PFS)));
           repositoryTypeList.add( new LabelValueBean("DB", String.valueOf(VolumeDefs.REP_TYPE_DB)));
           repositoryTypeList.add( new LabelValueBean("CENTERA", String.valueOf(VolumeDefs.REP_TYPE_CENTERA
        		   )));
           repositoryTypeListInitialized = true;
           
       }
       return repositoryTypeList;
   }

   
   protected List getRepositoryTypeServerList()
   {
       if (!repositoryTypeServerListInicialized ) {
           repositoryTypeServerList = new ArrayList();
           repositoryTypeServerList.add(new LabelValueBean("Dirección IP", "1"));
           repositoryTypeServerList.add(new LabelValueBean("Nombre Lógico", "2"));
           repositoryTypeServerListInicialized = true;
       }
       return repositoryTypeServerList; 
   }
   protected List getRepositoryPlataformList()
   {
       if (!repositoryPlataformListInicialized ){
           repositoryPlataformList = new ArrayList();
           repositoryPlataformList.add(new LabelValueBean("Windows NT", String.valueOf(VolumeDefs.OS_NT)));
           repositoryPlataformList.add(new LabelValueBean("Unix", String.valueOf(VolumeDefs.OS_UNIX)));
           repositoryPlataformListInicialized = true;
       }
       return repositoryPlataformList;
   }

   protected int getUserId(HttpServletRequest request)
   {
		HttpSession session = request.getSession(false);
		int id = 0;
		if( session != null ){
		    if (session.getAttribute(Constantes.TOKEN_USER_CONNECTED) != null ){
		        UserConnectedDTO user = (UserConnectedDTO) session.getAttribute(Constantes.TOKEN_USER_CONNECTED);
		        id = user.getId();
		        
		    }
		    // logger.warn("activar seguridad");
		}
		return id;
   }

   
   protected boolean isLdapMethod(String entidad)
   {
       boolean isLdap = false;
       
       //int loginMethod = ((Integer)getServlet().getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD)).intValue();
       HashMap hash=(HashMap)ctx.getAttribute(MvcDefs.TOKEN_LOGIN_METHOD);
       int loginMethod=((Integer)hash.get(entidad)).intValue();
       if (loginMethod == 2)
           isLdap = true;
       return isLdap;
   }
   
   protected boolean useCertificate(){
       boolean useCertificate = false;
       Boolean bool = (Boolean) ( getServlet().getServletContext().getAttribute(Constantes.USE_CERTIFICATE) );
       
       useCertificate =  ((Boolean) ( getServlet().getServletContext().getAttribute(Constantes.USE_CERTIFICATE) )).booleanValue();
       return useCertificate;
   }
   
   protected boolean hasFtsConfig()
   {
       boolean hasFtsConfig = false;
       hasFtsConfig =  ((Boolean) ( getServlet().getServletContext().getAttribute(Constantes.HAS_FTS_CONFIG) )).booleanValue();
       return hasFtsConfig;
   }
   
   // fin funciones mias
   protected boolean haveMessage(HttpServletRequest request, String errorCode)
   {

      PropertyMessageResources   pmr            =  (PropertyMessageResources)ctx.getAttribute("errors");
      String                     errorMessage   =  pmr.getMessage("message."+ errorCode);

      if(errorMessage != null)
         return true;
      else
         return false;

   }

   private ErrorBean popUpException(String exceptionhandle, Exception e)
   {

      ErrorBean   error       =   null;
      HashMap     parameters  =  new HashMap();
      
      logger.error("Pintando el error: ", e);

      //Metodo para ser sobreescrito por los servlet
      //error = fillupErrorBean(e);
      if(error == null)
      {
         error = new ErrorBean();

         if(exceptionhandle.equals(MvcDefs.EXCEPTION_HANDLE_DEVELOPMENT))
         {
            error.setStatus(MvcDefs.EXCEPTION_HANDLE_DEVELOPMENT);
            error.setException(e);
            error.setShowMessage(true);
            error.setStack(e.getStackTrace());
         }
         else if(exceptionhandle.equals(MvcDefs.EXCEPTION_HANDLE_PRODUCCION))
         {
            error.setStatus(MvcDefs.EXCEPTION_HANDLE_PRODUCCION);
            error.setException(e);
            error.setShowMessage(true);
         }
      }

      return error;

   }

   protected abstract ActionForward executeLogic(ActionMapping mapping,
                                                 ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
                                          throws Exception;

   


   protected ArchiveObject getArchiveObject(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (ArchiveObject)session.getAttribute(MvcDefs.TOKEN_ARCHIVE);

      return null;
   }

   protected void removeArchiveObject(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.removeAttribute(MvcDefs.TOKEN_ARCHIVE);
   }

   protected void setArchiveObject(HttpServletRequest request, ArchiveObject arch)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_ARCHIVE, arch);
   }
   /*
   protected AcsAccessObject getAcsObject(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (AcsAccessObject)session.getAttribute(MvcDefs.TOKEN_ACS_AUTH);

      return null;
   }

   protected void setAcsObject(HttpServletRequest request, AcsAccessObject acs)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_ACS_AUTH, acs);
   }
*/
   protected FolderObject getFolderObject(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (FolderObject)session.getAttribute(MvcDefs.TOKEN_FOLDER);

      return null;
   }

   protected void setFolderObject(HttpServletRequest request, FolderObject folder)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_FOLDER, folder);
   }

   protected void removeFolder(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.removeAttribute(MvcDefs.TOKEN_FOLDER);
   }

   protected String getUser(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (String)session.getAttribute(MvcDefs.TOKEN_USER);

      return null;
   }

   protected void setUser(HttpServletRequest request, String user)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_USER, user);
   }

   protected String getIdocSessionId(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (String)session.getAttribute(MvcDefs.TOKEN_IDOC_SESSION_ID);

      return null;
   }

   protected void setIdocSessionId(HttpServletRequest request, String accessId)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_IDOC_SESSION_ID, accessId);
   }

   protected String getActiveFolderId(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (String)session.getAttribute(MvcDefs.TOKEN_ACTIVE_CABINET_ID);

      return null;
   }

   protected void setActiveFolderId(HttpServletRequest request,
                                    String activeFolder)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_ACTIVE_CABINET_ID, activeFolder);
   }

   protected String getDefaultFolderId(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (String)session.getAttribute(MvcDefs.TOKEN_DEFAULT_MENU);

      return null;
   }

   protected void setDefaultFolderId(HttpServletRequest request,
                                     String activeFolder)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_DEFAULT_MENU, activeFolder);
   }
/*
   protected MenuRepository getMenuRepository(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (MenuRepository)session.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);

      return null;
   }

   protected void setMenuRepository(HttpServletRequest request, MenuRepository repository)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MenuRepository.MENU_REPOSITORY_KEY, repository);
   }
*/
   protected void setMemoryFolder(HttpServletRequest request, FolderObject folder)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_MEMORY_FOLDER, folder);
   }

   protected FolderObject getMemoryFolder(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (FolderObject)session.getAttribute(MvcDefs.TOKEN_MEMORY_FOLDER);

      return null;
   }

   protected void removeMemoryFolder(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.removeAttribute(MvcDefs.TOKEN_MEMORY_FOLDER);
   }



   protected void removeMemoryFolderList(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.removeAttribute(MvcDefs.TOKEN_FOLDER_MEMORY_GLOBAL_LIST);
   }


   protected void removeFolderList(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.removeAttribute(MvcDefs.TOKEN_FOLDER_GLOBAL_LIST);
   }

   protected void setFolderIds(HttpServletRequest request, FolderSearchResult folderIds)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_ARCHIVE_SEARCH_RESULTS, folderIds);
   }

   protected FolderSearchResult getFolderIds(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         return (FolderSearchResult)session.getAttribute(MvcDefs.TOKEN_ARCHIVE_SEARCH_RESULTS);

      return null;
   }

   protected void removeFolderIds(HttpServletRequest request, FolderSearchResult folderIds)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.removeAttribute(MvcDefs.TOKEN_ARCHIVE_SEARCH_RESULTS);
   }





   protected String getErrorMessage(HttpServletRequest request, String key,
                                    Object [] params)
   {

      PropertyMessageResources errorMessages = (PropertyMessageResources)ctx.getAttribute("errors");

      if((params != null) && (params.length > 0))
         return errorMessages.getMessage(getLocale(request), key, params);

      return errorMessages.getMessage(getLocale(request), key);

   }
   
   protected String getMessage(HttpServletRequest request, String key, Object [] params, String bundle)
   {

      PropertyMessageResources messages = (PropertyMessageResources)ctx.getAttribute(bundle);

      if(messages != null)
      {
         if((params != null) && (params.length > 0))
            return messages.getMessage(getLocale(request), key, params);

         return messages.getMessage(getLocale(request), key);
      }
      return null;
   }

   protected void setTransactionCommit(HttpServletRequest request, boolean isOperationCommit)
   {
      HttpSession session = request.getSession(false);

      if(session != null)
         session.setAttribute(MvcDefs.TOKEN_IS_MODIFY_FOLDER_COMMIT, new Boolean(isOperationCommit));
   }

   protected void postGlobalError(String errorKey, HttpServletRequest request)
   {
      postGlobalError(errorKey, null, request);
   }

   protected void postGlobalMessage(String messageKey, HttpServletRequest request)
   {
      postGlobalMessage(messageKey, null, request);
   }
   
   protected void postGlobalError(String errorKey, Object arg,
                                  HttpServletRequest request)
   {

      ActionError    error    =  ((arg == null) ? new ActionError(errorKey): new ActionError(errorKey, arg));
      ActionErrors   errors   =  new ActionErrors();
      
      errors.add(ActionErrors.GLOBAL_ERROR, error);
      saveErrors(request, errors);
   }



   protected void postGlobalMessage(String messageKey, Object arg, HttpServletRequest request)
   {

      ActionMessage  message  =  ((arg == null) ? new ActionError(messageKey) : new ActionError(messageKey, arg));
      ActionMessages messages =  new ActionMessages();
      
      messages.add(ActionMessages.GLOBAL_MESSAGE, message);
      saveMessages(request, messages);
   }

}
