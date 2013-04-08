/*
 * Created on 22-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.util.SessionHelper;

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
public class GroupEdit extends BaseAction{
    
    DbConnectionConfig dbconf=null;
    LdapConnection ldapConn = new LdapConnection();
    LdapConnCfg   connCfg = null;
    
    private static Logger logger = Logger.getLogger(GroupEdit.class);

    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{

    	String entidad=SessionHelper.getEntidad(request);
    	
        BasicForm basicForm = (BasicForm) form;
        
        String submited = request.getParameter("submitted");
        String guid =  request.getParameter("guid");
        /*
        DbConnection.open(CfgMdoConfig.getDbConfig());
        connCfg = UasConfigUtil.createLdapConnConfig();
        ldapConn.open(connCfg);
        */
        if (submited == null )
        {
            cargarGrupo(basicForm , guid, entidad);
        }
        else
        {
            guardarDatos(basicForm , guid, entidad);
        }
        
        return mapping.findForward("success");
        
    }

    public void guardarDatos(BasicForm form, String guid, String entidad ) throws Exception {

    	LdapGroup ldapGroup = ObjFactory.createLdapGroup(0);
        ldapGroup.loadFromGuid(guid, entidad);
        // Tengo que leer los permisos asociados a invesdoc
        Permissions perms =ldapGroup.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        int permisos = 0;
    
        if (form.isIdocConsulta())
            permisos += UserDefs.PERMISSION_QUERY;
        if (form.isIdocModificacion())
            permisos += UserDefs.PERMISSION_UPDATE;
        if (form.isIdocCreacion())
            permisos += UserDefs.PERMISSION_CREATION;
        if (form.isIdocBorrado())
            permisos += UserDefs.PERMISSION_DELETION;
        if (form.isIdocImpresion())
            permisos += UserDefs.PERMISSION_PRINTING;
        
        // Permisos
        perm.setPermission( permisos );
        ldapGroup.store(entidad);
        
        if (logger.isDebugEnabled()){
            logger.debug("Permisos de grupo Guardados: " + guid + " Valor: " + permisos);
        }
    }    
    public void cargarGrupo(BasicForm basicForm, String guid, String entidad) throws Exception
    {
        
        LdapGroup ldapGroup = ObjFactory.createLdapGroup(0);
        ldapGroup.loadFromGuid(guid, entidad);
        // Tengo que leer los permisos asociados a invesdoc
        Permissions perms =ldapGroup.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        int permisos = perm.getPermission();
        
        if ( (permisos & UserDefs.PERMISSION_QUERY) == UserDefs.PERMISSION_QUERY ) {
            basicForm.setIdocConsulta(true);
        }
        if ( (permisos & UserDefs.PERMISSION_UPDATE) == UserDefs.PERMISSION_UPDATE) {
            basicForm.setIdocModificacion(true);
        }
        if ( (permisos & UserDefs.PERMISSION_CREATION) == UserDefs.PERMISSION_CREATION) {
            basicForm.setIdocCreacion(true);
        }
        if ( (permisos & UserDefs.PERMISSION_DELETION) == UserDefs.PERMISSION_DELETION) {
            basicForm.setIdocBorrado(true);
        }
        if ( (permisos & UserDefs.PERMISSION_PRINTING)== UserDefs.PERMISSION_PRINTING){
            basicForm.setIdocImpresion(true);
        }
    }

}
