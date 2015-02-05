/*
 * Created on 30-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;

import java.util.Map;
import java.util.TreeMap;

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
public class UserNew extends BaseAction {

    /*
     * (non-Javadoc)
     * 
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserNew.class);
    int userId;
    UserConnectedDTO userDTO;
    
    protected ActionForward executeLogic(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String entidad=SessionHelper.getEntidad(request);

        UserForm userForm = (UserForm) form;
        userId = getUserId(request);
        
        String submitted = request.getParameter("submitted");

        if (submitted == null) {
            
            String objId = request.getParameter("idPadre"); // Id del departamento al que pertenece
            int idDept = Integer.parseInt(objId);
            UserAccess userAccess = ObjFactory.createUserAccess();
            boolean userCanCreateUser = userAccess.userCanCreateUser(userId, idDept, entidad);
            if (!userCanCreateUser)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_CREATE_USER), null);
            
            userForm.setIdoc("standard");
            userForm.setUser("none");
            userDTO = new UserConnectedDTO();
            iniciaBasico(userDTO);
            request.setAttribute("userDTO", userDTO);
            
            //llamada al servicio para obtener un listado con todas las aplicaciones y colocarlo en request
            
            //colocar en request los distintos perfiles que puede haber
            
            
            
            return mapping.findForward("ok");

        } else {
            String objId = request.getParameter("idPadre"); // Id del
                                                            // departamento al
                                                            // que pertenece
            int idDept = 0;
            if (objId != null && !objId.equals(""))
                idDept = Integer.parseInt(objId);

            if (logger.isDebugEnabled()) {
                logger.debug("Creando usuario colgando de: " + idDept);
            }
            User user = ObjFactory.createUser(userId, idDept);
            
            guardarDatos(user, userForm, entidad);
            request.setAttribute("tipo", "Usuario");

            byte tipo = Constantes.DEPARTAMENT;
            String objTipo = String.valueOf(tipo);

            request.setAttribute("id", objId);
            request.setAttribute("deptToken", objTipo);

            return mapping.findForward("success");
        }
    }

    /**
     * @param userDTO2
     */
    private void iniciaBasico(UserConnectedDTO userDTO) {
       Map profiles = new TreeMap();
       profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(UserDefs.PROFILE_NONE) );
       profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(UserDefs.PROFILE_NONE) );
       profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(UserDefs.PROFILE_NONE) );
       userDTO.setProfiles(profiles);
        
    }

    
    /*
     * @author Antonio María
     *return (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_IDOC));
        }
        public Integer getUserProfile()
        {
            return (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_USER));
        }
        public Integer getVolProfile()
        {
            return (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_VOLUME));
     */
    
    /**
     * @param dept
     * @param deptForm
     */
    public void guardarDatos(User user, UserForm userForm, String entidad) throws Exception {
        
        // General
        user.setName(userForm.getNombre());
        user.setPassword(userForm.getPwd());
        user.setPwdmbc(userForm.isPwdmbc());
        user.setPwdvpcheck(userForm.isPwdvpcheck());
        user.setDescription(userForm.getDescripcion());

        user.store(entidad);

        int id = user.getId();
        user.load(id, entidad);

        user.setDescription(userForm.getDescripcion());

        UserProfiles profiles = user.getProfiles();
        // Sistema

        UserProfile profile = profiles
                .getProductProfile(UserDefs.PRODUCT_SYSTEM);
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

        Permissions perms = user.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos
                                                                             // sobre
                                                                             // InvesDoc
        perm.setPermission(permisos);

        // Administrador de volúmenes
        profile = profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
        if (userForm.isVolumeSuperuser())
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else
            profile.setProfile(UserDefs.PROFILE_NONE);

        
        user.store(entidad);// Guardar cambios
        if (useCertificate()){
        	String idCert = userForm.getIdCert();
        	ServiceCertificate service = ServiceCertificate.getInstance(); 
            service.addIdCert(id,idCert,null, entidad) ; // Añadir Certificado digital	
        }
        
        
        if (logger.isDebugEnabled()) {
            logger.debug("Usuario " + user.getName() + " creado");
        }

    }
    
    

}