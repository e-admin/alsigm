/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.GroupUserRel;
import ieci.tecdoc.idoc.admin.api.user.Groups;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.idoc.admin.internal.GrpUsrRelImpl;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.dto.adminUser.bd.GroupDTO;
import ieci.tecdoc.mvc.dto.adminUser.bd.GroupListDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class UserEdit extends BaseAction {
    HttpSession session;
    UserConnectedDTO userDTO;
    
    int userId;
    int idUsuario ;
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{

    	String entidad=SessionHelper.getEntidad(request);
    	
        UserForm userForm = (UserForm) form;
        userId = getUserId(request);
        String objId = request.getParameter("id");
        session = request.getSession(false);
        
        idUsuario = Integer.parseInt(objId);
        String submited = request.getParameter("submitted");

        
        
        List grupos = getGroupList(idUsuario, entidad);
        
        GroupListDTO groupList= new GroupListDTO ();
        groupList.setGrupos(grupos);
        request.setAttribute("gruposListDTO", groupList);
        
        if (submited == null )
        {
            userDTO = new UserConnectedDTO();
            cargarUsuario(userForm, idUsuario, entidad);
        }
        else
        {
            UserAccess userAccess = ObjFactory.createUserAccess();
            boolean userCanEditUser = userAccess.userCanEditUser(userId, idUsuario, entidad);
            if (!userCanEditUser)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_EDIT_USER), null);
            guardarDatos(userForm, idUsuario, entidad);
        }
        request.setAttribute("userDTO", userDTO);
        return mapping.findForward("success");

    }

    public void guardarDatos(UserForm userForm, int idUsuario, String entidad) throws Exception {

        // General
        User user = ObjFactory.createUser(userId,Defs.NULL_ID);
        user.load(idUsuario, entidad);
        user.setPwdmbc(userForm.isPwdmbc());
        user.setPwdvpcheck(userForm.isPwdvpcheck());
        
        user.setName(userForm.getNombre());
        user.setDescription(userForm.getDescripcion());
        String pwd = userForm.getPwd();
        if (pwd.indexOf("*") == -1 ) // Cambio la contraseña, si se ha modificado el <input type="passwd" 
            user.setPassword(pwd);
        
        // Guardar grupos asociados
        
        String [] gruposAsignados = (String [])session.getAttribute("gruposAsignados"); 
                
        String [] gruposSeleccionados = userForm.getGruposAsignados();
        
        String [] gruposParaAsignar =  diferencia (gruposSeleccionados,gruposAsignados);
        String [] gruposParaEliminar = diferencia (gruposAsignados, gruposSeleccionados);
        
        // session.removeAttribute("gruposAsignados");
        session.setAttribute("gruposAsignados", gruposSeleccionados );
        asignarUsuarioAGrupo(idUsuario, gruposParaAsignar, entidad);
        quitarUsuarioDeGrupos(idUsuario, gruposParaEliminar, entidad);
        
        UserProfiles profiles = user.getProfiles();
               
        // Sistema 
        
        UserProfile profile = profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
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
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        perm.setPermission( permisos );
        
        // Administrador de volúmenes
        profile = profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
        if (userForm.isVolumeSuperuser())
            profile.setProfile(UserDefs.PROFILE_SUPERUSER);
        else
            profile.setProfile(UserDefs.PROFILE_NONE);
    
        // Certificado
        if (useCertificate()){
	        ServiceCertificate service = ServiceCertificate.getInstance(); 
	        service.updateIdCert(user.getId(), userForm.getIdCert(), null, entidad);
        }
        user.store(entidad); // Guardar cambios
        //System.out.println ("DAtos guardados");
        
    }
    
        
    public void cargarUsuario(UserForm userForm, int id, String entidad) throws Exception
    {
        User user = ObjFactory.createUser(userId, Defs.NULL_ID);
        
        user.load(id, entidad);
        UserProfiles    profiles=null;
        UserProfile     profile=null;
        
        // General
        userForm.setNombre(user.getName());
        userForm.setId(user.getId());
        userForm.setPwd("****");
        userForm.setRepwd("****");
        userForm.setPwdvpcheck(user.getPwdvpcheck());
        userForm.setPwdmbc(user.getPwdmbc());
        userForm.setDescripcion(user.getDescription());
        
        userDTO.setId(id);
        userDTO.setUserName(user.getName());
        
        // Cargar datos de grupos
                
        String [] gruposAsignados = getGruposAsignados(id, entidad);
        session.setAttribute("gruposAsignados", gruposAsignados);
        userForm.setGruposAsignados(gruposAsignados);
        
        // Perfiles
        profiles=user.getProfiles();
                
        	// Sistema
        
        profile=profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
        Map _profiles = new HashMap();
        
        if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){ // Si es super usuario de sistema activar todos los permisos y listo
            
            userDTO.setIsSystemSuperUser(true);
            userDTO.setHasAccessSys(true);
            userDTO.setHasAccessUser(true);
            userDTO.setHasAccessVol(true);
            
            _profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(UserDefs.PROFILE_SUPERUSER) );
            _profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(UserDefs.PROFILE_SUPERUSER) ) ;
            _profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(UserDefs.PROFILE_SUPERUSER) );
            
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
        else { 
	        	// User
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_USER);
	        _profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(profile.getProfile()));
	        
	        if(profile.getProfile()==UserDefs.PROFILE_NONE ){
	        	userForm.setUser("none");
	        }
	        else {
	            if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){
	                userForm.setUser("superuser");
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_MANAGER ){
	                userForm.setUser("manager");
	            }
	            else if (profile.getProfile()==UserDefs.PROFILE_STANDARD ){
	                userForm.setUser("standard");
	            }
	        }
	        	// Invesdoc
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
	        _profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(profile.getProfile()));
	        
	        if(profile.getProfile()==UserDefs.PROFILE_NONE ){
	        	userForm.setIdoc("none"); // Por defecto el formulario se crea con los permisos a false
	        }
	        else {
	            if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){ // Si es manager o administrador no leo los permisos del ldap, deben estar todos activos
	                userForm.setIdoc("superuser");
	                userForm.setIdocConsulta(true);
	                userForm.setIdocModificacion(true);
	                userForm.setIdocCreacion(true);
	                userForm.setIdocBorrado(true);
	                userForm.setIdocImpresion(true);
	            }
	            else if (profile.getProfile() == UserDefs.PROFILE_MANAGER){ 
	                userForm.setIdoc("manager");
	                userForm.setIdocConsulta(true);
	                userForm.setIdocModificacion(true);
	                userForm.setIdocCreacion(true);
	                userForm.setIdocBorrado(true);
	                userForm.setIdocImpresion(true);
	            }
	            else if(profile.getProfile()==UserDefs.PROFILE_STANDARD ){
	                userForm.setIdoc("standard");
	                // Tengo que leer los permisos asociados a invesdoc, pero se que al menos puede consultar
	                userForm.setIdocConsulta(true);
	                Permissions perms = user.getPermissions();
	    	        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
	    	        int permisos = perm.getPermission();
		            if ( (permisos & UserDefs.PERMISSION_UPDATE) == UserDefs.PERMISSION_UPDATE) {
		                userForm.setIdocModificacion(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_CREATION) == UserDefs.PERMISSION_CREATION) {
		                userForm.setIdocCreacion(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_DELETION) == UserDefs.PERMISSION_DELETION) {
		                userForm.setIdocBorrado(true);
		            }
		            if ( (permisos & UserDefs.PERMISSION_PRINTING)== UserDefs.PERMISSION_PRINTING){
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
    
    /**
     * @param id
     * @return
     */
    private String[] getGruposAsignados(int id, String entidad) throws Exception {
        
        GroupUserRel gur = new GrpUsrRelImpl();
        List gruposList = gur.loadUserGroupIds(id, entidad);
        Iterator it = gruposList.iterator();
        int n = gruposList.size();
        String s[] = new String[n];
        int i = 0;
        while (it.hasNext()){
            s[i++] = it.next().toString();
        }
        return s;
    }

    
    public List getGroupList(int idUsuario, String entidad) throws Exception
    {
        
        User user = ObjFactory.createUser();
        List list = user.getGroupsIdMgr(userId, entidad); // Lista de grupos de los cuales el user connected es manager
        
        Groups grupos = new Groups();
        grupos.loadLite(entidad);
        int n = grupos.count();
        Group grupo;
        List groupList = new ArrayList();
        for (int i = 0; i < n; i ++){
            grupo = grupos.getGroup(i);
            int id = grupo.getId();
            String name = grupo.getName();
            GroupDTO groupDTO = new GroupDTO(id, name);
            if (!list.contains(new Integer(id))){
                groupDTO.setIsDisabled(true);
            }
            groupList.add(groupDTO);
        }
        return groupList;
    }
    public List getListaGrupos(int idUsuario, String entidad) throws Exception
    {
        List listaGrupos = new ArrayList();
        User u = ObjFactory.createUser();
        List groupsIdList = u.getGroupsIdMgr(idUsuario, entidad);
        
        Iterator it = groupsIdList.iterator();
        
        while (it.hasNext())
        {
            Integer id = (Integer) it.next();
            String name = ServiceCommon.getNameGroupById(id.intValue(), entidad);
            GroupDTO g = new GroupDTO(id.intValue(), name );
            listaGrupos.add(g);
        }
        return listaGrupos;
    }
    
    public void quitarUsuarioDeGrupos(int idUser, String [] grupos, String entidad) throws Exception
    {
        GroupUserRel gur = new GrpUsrRelImpl();
        int n = grupos.length;
        UserAccess userAccess = ObjFactory.createUserAccess();
        for (int i = 0; i < n; i ++){
            int groupId = Integer.parseInt(grupos[i]);
            
             boolean userCanDeleteGroupUser = userAccess.userCanDeleteGroupUser(userId, groupId, entidad);
             if (!userCanDeleteGroupUser)
                 throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_DELETE_GROUP_USER), null);
             
             
            gur.deleteGroupUser(groupId, idUser, entidad);
        }
    }
    public void asignarUsuarioAGrupo(int idUser, String [] grupos, String entidad) throws Exception
    {
        GroupUserRel gur = new GrpUsrRelImpl();
        int n = grupos.length;
        UserAccess userAccess = ObjFactory.createUserAccess();
        for (int i = 0; i < n; i ++){
            int groupId = Integer.parseInt(grupos[i]);
            boolean userCanAssingUserToGroup = userAccess.userCanAssingUserToGroup(userId, this.idUsuario, groupId, entidad);
            if (!userCanAssingUserToGroup)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_ASSING_USER_TO_GROUP), null);
            gur.assignUserToGroup( groupId , idUser, entidad);
        }
    }    
    
    private String [] diferencia(String[] v1, String[] v2) {
        TreeSet resul = new TreeSet();
        int n = v1.length;
        int i;
        for (i = 0; i < n; i ++)
        {
            String s = v1[i];
            if (s != null && !busca (s, v2))
                resul.add(s);
            
        }
        int nresul = resul.size();
        
        String r[] = new String[nresul];
        Iterator it = resul.iterator();
        i = 0; 
        while (it.hasNext())
        {
            r[i] = (String) it.next();
            i++;
        }
               
        return r;

        
    }
    public boolean busca(String obj, String[] vector)
    {
        boolean enc = false;
        int n = vector.length;
        int i = 0; 
        while (i < n  && !enc)
        {
            if (vector[i]!=null && vector[i].equals(obj))
                enc = true;
            i++;
        }
        return enc;
    }

        
}