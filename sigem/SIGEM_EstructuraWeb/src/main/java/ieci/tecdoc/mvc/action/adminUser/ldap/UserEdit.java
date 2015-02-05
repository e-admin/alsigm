/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.AplicacionPerfil;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.internal.UserProfileImpl;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.form.adminUser.ldap.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
public class UserEdit extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserEdit.class);

    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	String entidad=SessionHelper.getEntidad(request);

        UserForm userForm = (UserForm) form;

        String guid = request.getParameter("guid");
        if (guid == null)
            guid = userForm.getGuid();

        int userId = getUserId(request);

        String submitted = request.getParameter("submitted");
        if (submitted== null)
        {
        	UserConnectedDTO userDTO = new UserConnectedDTO();
            cargarDatos(userForm, userDTO, guid, userId, entidad);
            request.setAttribute("userDTO", userDTO);
        }
        else{
            guardarDatos(userForm, userId, entidad);
        }
        Object bundle = request.getAttribute("bundle");
        if (bundle != null)
            request.setAttribute("bundle",bundle);

        return (mapping.findForward("success"));
    }


    public void cargarDatos(UserForm userForm, UserConnectedDTO userDTO, String guid, int userId, String entidad) throws Exception{

        UserProfiles    profiles=null;
        UserProfile     profile=null;

        /*
        LdapConnection ldapConn = new LdapConnection();
        LdapConnCfg   connCfg = null;
        DbConnection.open(DBSessionManager.getSession(entidad));
        connCfg = UasConfigUtil.createLdapConnConfig(entidad);
    	ldapConn.open(connCfg);
    	*/

    	LdapUser user =ObjFactory.createLdapUser(userId);
    	user.loadFromGuid(guid, entidad);

    	int id = user.getId();

        UserProfiles userProfiles=user.getProfiles();
        Collection listaAplicacionesModificada=new ArrayList();
        Collection listaAplicacionesDefecto=userForm.getListaAplicaciones();
        for (Iterator iter = listaAplicacionesDefecto.iterator(); iter.hasNext();) {
			AplicacionPerfil aplicacionPerfil = (AplicacionPerfil) iter.next();
			try {
				UserProfile userProfile = userProfiles.getProductProfile(Integer.parseInt(aplicacionPerfil.getIdetificador()));
				aplicacionPerfil.setPerfil(userProfile.getProfile());
			} catch(IeciTdException ieciEx) {
				if (!ieciEx.getErrorCode().equals(Long.toString(UserErrorCodes.EC_USER_NOT_PROFILE_FOR_PRODUCT))) {
					throw ieciEx;
				}
			}
			listaAplicacionesModificada.add(aplicacionPerfil);
        }
        userForm.setListaAplicaciones(listaAplicacionesModificada);

    	userDTO.setId(id);
    	userDTO.setUserName(user.getFullName());

        profiles=user.getProfiles();
        // General
        userForm.setGuid(guid);
        userForm.setId(id);

        Map _profiles = new HashMap();

        // Sistema
        profile=profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
        if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){ // Si es super usuario de sistema activar todos los permisos y listo

        	// SuperUsuario del sistema
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

	            	// Es SuperUsuario de Administrador de usuarios
	                userForm.setUser("superuser");
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_MANAGER ){

	            	userForm.setUser("manager");
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_STANDARD ){

	            	// Es Estandar de Administracion de usuarios
	                userForm.setUser("standard");
	            }
	        }
	        // Invesdoc
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
	        _profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(profile.getProfile()));

	        if(profile.getProfile()==UserDefs.PROFILE_NONE ){

	        	// No tiene acceso a Invesdoc
	        	userForm.setIdoc("none"); // Por defecto el formulario se crea con los permisos a false
	        }
	        else {
	            if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){ // Si es manager o administrador no leo los permisos del ldap, deben estar todos activos

	            	// Es SuperUsuario de InvesDoc
	                userForm.setIdoc("superuser");
	                userForm.setIdocConsulta(true);
	                userForm.setIdocModificacion(true);
	                userForm.setIdocCreacion(true);
	                userForm.setIdocBorrado(true);
	                userForm.setIdocImpresion(true);
	            }
	            else if (profile.getProfile() == UserDefs.PROFILE_MANAGER){

	            	// Es Administrador / Manager de InvesDoc
	            	userForm.setIdoc("manager");
	                userForm.setIdocConsulta(true);
	                userForm.setIdocModificacion(true);
	                userForm.setIdocCreacion(true);
	                userForm.setIdocBorrado(true);
	                userForm.setIdocImpresion(true);
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_STANDARD ){

	            	// Es Administrador Estandar de InvesDoc
	                userForm.setIdoc("standard");
	                // Tengo que leer los permisos asociados a invesdoc, pero se que al menos puede consultar
	                userForm.setIdocConsulta(true);
	                Permissions perms =user.getPermissions();
	    	        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
	    	        int permisos = perm.getPermission();
		            if ( (permisos & UserDefs.PERMISSION_UPDATE) == UserDefs.PERMISSION_UPDATE) {

		            	// Permiso de modificación
		                userForm.setIdocModificacion(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_CREATION) == UserDefs.PERMISSION_CREATION) {

		            	// Permiso de creación
		                userForm.setIdocCreacion(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_DELETION) == UserDefs.PERMISSION_DELETION) {

		            	// Permiso de borrado
		                userForm.setIdocBorrado(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_PRINTING)== UserDefs.PERMISSION_PRINTING){

		            	// Permiso de impresión
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
            userForm.setIdCert(service.getIdCert(user.getId(), entidad));
        }

        userDTO.setProfiles(_profiles);
    }

    public void guardarDatos(UserForm userForm, int userId, String entidad) throws Exception {

    	/*
        LdapConnection ldapConn = new LdapConnection();
        LdapConnCfg   connCfg = null;
        DbConnection.open(DBSessionManager.getSession(entidad));
        connCfg = UasConfigUtil.createLdapConnConfig(entidad);
        ldapConn.open(connCfg);
        */

        LdapUser user =ObjFactory.createLdapUser(userId);
        user.loadFromGuid(userForm.getGuid(), entidad);

        // Perfiles del usuario
        //user.resetProfiles();
        Map profilesMap = new HashMap();
        UserProfiles profiles = user.getProfiles();
        if (profiles != null) {

        	for (int i = 0; i < profiles.count(); i++) {

        		UserProfile userProfile = profiles.get(i);
        		profilesMap.put(new Integer(userProfile.getProduct()), userProfile);
        	}
        }

        // Perfiles modificables en el formulario
        Collection listaPerfilesAplicacion=userForm.getListaAplicaciones();
        for (Iterator iter = listaPerfilesAplicacion.iterator(); iter.hasNext();) {

			AplicacionPerfil element = (AplicacionPerfil) iter.next();
			if ((element.isEditableSinPermisos()) ||
				(element.isEditableUsuarioEstandar()) ||
				(element.isEditableUsuarioAdministrador())) {

				int idAplicacion = Integer.parseInt(element.getIdetificador());
				profilesMap.put(new Integer(idAplicacion), new UserProfileImpl(user.getId(),idAplicacion,element.getPerfil()));
			}
		}

        // Actualizar perfiles
        user.resetProfiles();
        profiles = user.getProfiles();
        Iterator it = profilesMap.entrySet().iterator();
        while (it.hasNext()) {
        	Entry e = (Entry) it.next();
        	profiles.add((UserProfile)e.getValue());
        }

        /*
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

        // Administrador de volúmenes
        profile = profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
        if (userForm.isVolumeSuperuser())
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else
            profile.setProfile(UserDefs.PROFILE_NONE);
        */

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

        Permissions perms =user.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        perm.setPermission( permisos );

        user.store(entidad); // Almacenar en LDAP

        /*
        String userAttName = session.getAttribute("userAttName").toString();
        // Certificado
        if (useCertificate()){
            ServiceCertificate service = ServiceCertificate.getInstance();
            service.updateIdCert(LUser.getId(),userForm.getIdCert(), userAttName, entidad);
        }
        */

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
