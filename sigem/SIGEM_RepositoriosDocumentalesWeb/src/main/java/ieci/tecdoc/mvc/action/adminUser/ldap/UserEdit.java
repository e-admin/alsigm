/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.form.adminUser.ldap.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class UserEdit extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserEdit.class);
    UserConnectedDTO userDTO;
    int userId;
    HttpSession session;
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        UserForm userForm = (UserForm) form;
        String guid = guid = request.getParameter("guid");
        session = request.getSession(false);
        userId = getUserId(request);
        if (guid == null)
            guid = userForm.getGuid();
        
        String submitted = request.getParameter("submitted");
        if (submitted== null)
        {
            userDTO = new UserConnectedDTO();
            cargarDatos(userForm, guid, entidad);
        }
        else{ 
            guardarDatos(userForm, entidad);
        }
        Object bundle = request.getAttribute("bundle");
        if (bundle != null)
            request.setAttribute("bundle",bundle);
        
        request.setAttribute("userDTO", userDTO);
        return (mapping.findForward("success"));
    }
    

    public void cargarDatos(UserForm userForm, String guid, String entidad) throws Exception{
        UserProfiles    profiles=null;
        UserProfile     profile=null;

        DbConnectionConfig dbconf=null;
        LdapConnection ldapConn = new LdapConnection();
        LdapConnCfg   connCfg = null; 
        DbConnection.open(DBSessionManager.getSession(entidad));
        connCfg = UasConfigUtil.createLdapConnConfig(entidad);
    	ldapConn.open(connCfg);
            
    	LdapUser LUser =ObjFactory.createLdapUser(userId);
    	LUser.loadFromGuid(guid, entidad);
    	
    	int id = LUser.getId();
    	
    	userDTO.setId(id);
    	userDTO.setUserName(LUser.getFullName());
    	
        profiles=LUser.getProfiles();
        // General
        userForm.setGuid(guid);
        userForm.setId(id);
        
        Map _profiles = new HashMap();
        
        // Sistema
        profile=profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
        if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){ // Si es super usuario de sistema activar todos los permisos y listo
        	//System.out.println("Es SuperUsuario del sistema");
        	setSuperusuarioSystem(userForm);
        	
        	userDTO.setIsSystemSuperUser(true);
        	_profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(UserDefs.PROFILE_SUPERUSER) );
            _profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(UserDefs.PROFILE_SUPERUSER) ) ;
            _profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(UserDefs.PROFILE_SUPERUSER) );
        }
        else { 
	        // User
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_USER);
	        _profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(profile.getProfile()));
	        
	        if(profile.getProfile()==UserDefs.PROFILE_NONE ){
	        	userForm.setUser("none");
	        }
	        else {// eN ldap no existe manager, y en la aplicación de windows usa la cte PROFILE_manager para describir a un superuser
	            if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER || profile.getProfile()==UserDefs.PROFILE_MANAGER){
	                //System.out.println("Es SuperUsuario de Administrador de usuarios");
	                userForm.setUser("superuser");
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_MANAGER ){
	                userForm.setUser("manager");
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_STANDARD ){
	                //System.out.println("Es Estandar de Administracion de usuarios");
	                userForm.setUser("standard");
	            }
	        }
	        // Invesdoc
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
	        _profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(profile.getProfile()));
	        
	        if(profile.getProfile()==UserDefs.PROFILE_NONE ){
	        	//System.out.println("No tiene acceso a Invesdoc");
	        	userForm.setIdoc("none"); // Por defecto el formulario se crea con los permisos a false
	        }
	        else {
	            if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){ // Si es manager o administrador no leo los permisos del ldap, deben estar todos activos
	                //System.out.println("Es SuperUsuario de InvesDoc");
	                userForm.setIdoc("superuser");
	                userForm.setIdocConsulta(true);
	                userForm.setIdocModificacion(true);
	                userForm.setIdocCreacion(true);
	                userForm.setIdocBorrado(true);
	                userForm.setIdocImpresion(true);
	            }
	            else if (profile.getProfile() == UserDefs.PROFILE_MANAGER){ 
	                userForm.setIdoc("manager");
	                //System.out.println ("Es Administrador / Manager de InvesDoc");
	                userForm.setIdocConsulta(true);
	                userForm.setIdocModificacion(true);
	                userForm.setIdocCreacion(true);
	                userForm.setIdocBorrado(true);
	                userForm.setIdocImpresion(true);
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_STANDARD ){
	                //System.out.println("Es Administrador Estandar de InvesDoc");
	                userForm.setIdoc("standard");
	                // Tengo que leer los permisos asociados a invesdoc, pero se que al menos puede consultar
	                userForm.setIdocConsulta(true);
	                Permissions perms =LUser.getPermissions();
	    	        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
	    	        int permisos = perm.getPermission();
		            if ( (permisos & UserDefs.PERMISSION_UPDATE) == UserDefs.PERMISSION_UPDATE) {
		                //System.out.println ("Permiso de modificación");
		                userForm.setIdocModificacion(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_CREATION) == UserDefs.PERMISSION_CREATION) {
		                //System.out.println ("Permiso de creación");
		                userForm.setIdocCreacion(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_DELETION) == UserDefs.PERMISSION_DELETION) {
		                //System.out.println ("Permiso de borrado");
		                userForm.setIdocBorrado(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_PRINTING)== UserDefs.PERMISSION_PRINTING){
		                //System.out.println ("Permiso de impresión");
		                userForm.setIdocImpresion(true);
		            }
	            }
	        }
	        // Administrador de volúmenes
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
	        _profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(profile.getProfile()));
	        
	        
	        if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){
	        	userForm.setVolumeSuperuser(true);
	        }
        }
        // Certificado
        if (useCertificate()){
            
            ServiceCertificate service = ServiceCertificate.getInstance();
            userForm.setIdCert(service.getIdCert(LUser.getId(), entidad));
        }
        
        userDTO.setProfiles(_profiles);
    }
    
    public void guardarDatos(UserForm userForm, String entidad) throws Exception{
        int				userID=0;
        UserProfiles    profiles=null;
        UserProfile     profile=null;
        
        DbConnectionConfig dbconf=null;
        LdapConnection ldapConn = new LdapConnection();
        LdapConnCfg   connCfg = null; 
        DbConnection.open(DBSessionManager.getSession(entidad));
        connCfg = UasConfigUtil.createLdapConnConfig(entidad);
        ldapConn.open(connCfg);
               
        LdapUser LUser =ObjFactory.createLdapUser(userId);
        LUser.loadFromGuid(userForm.getGuid(), entidad);

        profiles=LUser.getProfiles();
            
        profiles = LUser.getProfiles();
        
        // Sistema 
        
        profile = profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
        if (userForm.isSystemSuperuser())
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else
            profile.setProfile(UserDefs.PROFILE_NONE);
        
        // Administrador de Usuarios
        profile = profiles.getProductProfile(UserDefs.PRODUCT_USER);
        String perfiles = userForm.getUser();
        if (perfiles.equals("superuser"))
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else if (perfiles.equals("manager"))
            profile.setProfile(UserDefs.PROFILE_MANAGER);
        else if (perfiles.equals("standard"))
            profile.setProfile(UserDefs.PROFILE_STANDARD);
        else if (perfiles.equals("none"))
            profile.setProfile(UserDefs.PROFILE_NONE);
        
        // InvesDoc
        profile = profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
        perfiles = userForm.getIdoc();
        if (perfiles.equals("superuser"))
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else if (perfiles.equals("manager"))
            profile.setProfile(UserDefs.PROFILE_MANAGER);
        else if (perfiles.equals("standard"))
            profile.setProfile(UserDefs.PROFILE_STANDARD);
        else if (perfiles.equals("none"))
            profile.setProfile(UserDefs.PROFILE_NONE);
        // Permisos
        int permisos = 0;
        
        if (userForm.isIdocConsulta())
            permisos += UserDefs.PERMISSION_QUERY;
        if (userForm.isIdocModificacion())
            permisos += UserDefs.PERMISSION_UPDATE;
        if (userForm.isIdocCreacion())
            permisos += UserDefs.PERMISSION_CREATION;
        if (userForm.isIdocBorrado())
            permisos += UserDefs.PERMISSION_DELETION;
        if (userForm.isIdocImpresion())
            permisos += UserDefs.PERMISSION_PRINTING;
        
        Permissions perms =LUser.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        perm.setPermission( permisos );
        
        // Administrador de volúmenes
        profile = profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
        if (userForm.isVolumeSuperuser())
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else
            profile.setProfile(UserDefs.PROFILE_NONE);
         
        LUser.store(entidad); // Almacenar en LDAP
        
        String userAttName = session.getAttribute("userAttName").toString();
        // Certificado
        if (useCertificate()){
            ServiceCertificate service = ServiceCertificate.getInstance();
            service.updateIdCert(LUser.getId(),userForm.getIdCert(), userAttName, entidad);
        }
        
        if (logger.isDebugEnabled())
            logger.debug("Datos guardados");
    }    
    /**
     * Funcion que otorga al usuario asociado a userForm como System superusuario,
     * por lo tanto seria superusuario de administrador de usuarios, InvesDoc, y volumenes, con todos los permisos
     * @param userForm
     */
    public void setSuperusuarioSystem(UserForm userForm)
    {
       userForm.setSystemSuperuser(true);
       userForm.setUser("superuser");
       userForm.setIdoc("superuser");
       userForm.setVolumeSuperuser(true);
       
       userForm.setIdocConsulta(true);
       userForm.setIdocModificacion(true);
       userForm.setIdocCreacion(true);
       userForm.setIdocBorrado(true);
       userForm.setIdocImpresion(true);
    
    }
}
