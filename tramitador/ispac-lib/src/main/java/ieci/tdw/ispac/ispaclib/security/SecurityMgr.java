/*
 * Created on Oct 21, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.security;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.DTTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.security.PCDPermissionsDAO;
import ieci.tdw.ispac.ispaclib.dao.security.PermissionDAO;
import ieci.tdw.ispac.ispaclib.dao.security.PermissionsDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SupervisionDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SustitucionDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SystemFunctionsDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.resp.RespFactory;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SecurityMgr
{

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SecurityMgr.class);
	
	private DbCnt mDbCnt= null;

	public SecurityMgr(DbCnt dbCnt)
	{
		mDbCnt = dbCnt;
	}

	public IItemCollection getFunctions(String uid)
	throws ISPACException
	{
		return SystemFunctionsDAO.getFunctions(mDbCnt, uid).disconnect();
	}

	public IItemCollection getFunctions(List uidList) throws ISPACException {
		return SystemFunctionsDAO.getFunctions(mDbCnt, uidList).disconnect();
	}

	public boolean isFunction(String uid, int function)
	throws ISPACException
	{
		return SystemFunctionsDAO.isFunction(mDbCnt, uid, function);
	}
	
	/**
	 * Devuelve cierto si el usuario tiene el permiso 
	 * @param respList cadena de responsabilidad
	 * @param type Tipo de permiso a comprobar 
	 * 0-->Eliminar expediente (enviar a la papelera)
	 * 1->Iniciar expediente
	 * @param idpcd Identificador del procedimiento para el que se va a comprobar el permiso
	 * @return Cierto si el usuario tiene el permiso, falso en caso contrario
	 * @throws ISPACException
	 */
	public boolean isPermission(String respList, int type , int idpcd)
	throws ISPACException{
		
		if (!respList.equals(Responsible.SUPERVISOR)) {
			String sql = "WHERE ID_PCD = " + idpcd
				+ DBUtil.addAndInResponsibleCondition("UID_USR", respList)
			   + " AND PERMISO="+type;
	
			CollectionDAO objlist = new CollectionDAO(PermissionsDAO.class);
			return objlist.exist(mDbCnt, sql);
		}
		return false;
	}
	
	public boolean isSupervisor(String uid)
	throws ISPACException
	{
		return SystemFunctionsDAO.isSupervisor(mDbCnt, uid);
	}

	public boolean isSupervisorTotal(String uid)
	throws ISPACException
	{
		return SystemFunctionsDAO.isSupervisorTotal(mDbCnt, uid);
	}
	
	public boolean isSupervisorMonitoring(String uid)
	throws ISPACException
	{
		return SystemFunctionsDAO.isSupervisorMonitoring(mDbCnt, uid);
	}	
	
	
	public boolean isFunction(List uids, int function)
	throws ISPACException
	{
		return SystemFunctionsDAO.isFunction(mDbCnt, uids, function);
	}

	public IItemCollection getPermissions(int idpcd)
	throws ISPACException
    {
        return PermissionsDAO.getPermissions(mDbCnt, idpcd).disconnect();
    }
	
	public IItemCollection getAllPermissionsByPcd(int pcd , String uid)
	throws ISPACException{
		
		return PCDPermissionsDAO.getPermissions(mDbCnt, pcd,uid).disconnect();
	}

	public IItemCollection getPermissions(String uid)
	throws ISPACException
	{
		return PermissionsDAO.getPermissions(mDbCnt, uid).disconnect();
	}

	public IItemCollection getPermissionObjects(String uid, int codePermission)
	throws ISPACException
	{
		return PermissionsDAO.getPermissionObjects(mDbCnt, uid, codePermission).disconnect();
	}

	public IItemCollection getPermissions(int idpcd, String uid)
	throws ISPACException
    {
        return PermissionsDAO.getPermissions(mDbCnt, idpcd, uid).disconnect();
    }

	public void deletePermissions(int idpcd, String uid)
	throws ISPACException
    {
	    String sql = "WHERE ID_PCD = " + idpcd 
	    		   + " AND UID_USR = '" + DBUtil.replaceQuotes(uid) + "'";
	    
        CollectionDAO objlist = new CollectionDAO(PermissionsDAO.class);
        objlist.delete(mDbCnt, sql);
    }

    public void addPermissions(int idpcd, String uid, int type)
    throws ISPACException
    {
        PermissionsDAO objper = new PermissionsDAO(mDbCnt);
        objper.createNew(mDbCnt);
        
        objper.set("ID_PCD", idpcd);
        objper.set("UID_USR", uid);
        objper.set("PERMISO", type);
        objper.store(mDbCnt);
    }
    
	public boolean isSupervised(String supervisorUID, String supervisedUID, int type)
	throws ISPACException
	{
		return SupervisionDAO.isSupervised(mDbCnt, supervisorUID, supervisedUID, String.valueOf(type));
	}

	public IItemCollection getSupervisors(String uid, int type)
	throws ISPACException
	{
		return SupervisionDAO.getSupervisors(mDbCnt, uid, String.valueOf(type)).disconnect();
	}

	public IItemCollection getSuperviseds(String uid, int type)
	throws ISPACException
	{
		return SupervisionDAO.getSuperviseds(mDbCnt, uid, String.valueOf(type)).disconnect();
	}
	
	public IItemCollection getSuperviseds(String uid) 
	throws ISPACException
	{
		return SupervisionDAO.getSuperviseds(mDbCnt, uid, null).disconnect();
	}
	
	public IItemCollection getAllSuperviseds(IResponsible responsible, String type)
	throws ISPACException
	{

		
		return SupervisionDAO.getSuperviseds(mDbCnt,getUidsResponsible(responsible), type).disconnect();
	}
	
	public IItemCollection getAllSuperviseds(IResponsible responsible, int type)
	throws ISPACException
	{
		return getAllSuperviseds(responsible, String.valueOf(type));
	}
	
	public IItemCollection getAllSuperviseds(IResponsible responsible)
	throws ISPACException
	{
		return getAllSuperviseds(responsible, null);
	}

	public boolean isSupervised(IResponsible responsible, String sUID, String type)
	throws ISPACException
	{	
		// Supervisados del usuario, del departamento y de los grupos a los que pertenece el usuario
		IItemCollection collection = getAllSuperviseds(responsible, type);
		Iterator iterator = collection.iterator();
		while (iterator.hasNext())
		{
			IItem supervised = (IItem) iterator.next();
			Responsible respSupervised = getResp(supervised.getString("UID_SUPERVISADO"));
			
			if (respSupervised.isInResponsibleList(sUID))
				return true;
		}
		
		return false;
	}
	
	public boolean isSupervised(IResponsible responsible, String sUID, int type)
	throws ISPACException
	{	
		return isSupervised(responsible, sUID, String.valueOf(type));
	}
	
	public IItemCollection getAllSubstitutes(IResponsible responsible)
	throws ISPACException
	{

		
		return SustitucionDAO.getSubstitutes(mDbCnt, getUidsResponsible(responsible)).disconnect();
	}
	
	public IItemCollection getRespPermissions(int idpcd)
	throws ISPACException
    {
        CollectionDAO coldao=PermissionsDAO.getPermissions(mDbCnt, idpcd);
        List list = new ArrayList ();
		while (coldao.next())
		{
			ObjectDAO dao = coldao.value();
			String uid = dao.getString("UID_USR");
			IResponsible resp = getResp (uid);
			list.add(resp);
		}
		
		return new ListCollection (list);
    }
	
	public IItemCollection getRespPermissions(int idpcd, int codePermission)
	throws ISPACException
    {
        CollectionDAO coldao=PermissionsDAO.getPermissions(mDbCnt, idpcd, codePermission);
        List list = new ArrayList ();
		while (coldao.next())
		{
			ObjectDAO dao = coldao.value();
			String uid = dao.getString("UID_USR");
			IResponsible resp = getResp (uid);
			list.add(resp);
		}
		
		return new ListCollection (list);
    }

	public IItemCollection getRespPermissions(int idpcd, String uid)
	throws ISPACException
    {
	    CollectionDAO coldao=PermissionsDAO.getPermissions(mDbCnt, idpcd, uid);
        List list = new ArrayList ();
        if (coldao.next())
        {
            IResponsible resp = getResp (uid);
            list.add(resp);
        }
        
		return new ListCollection (list);
    }

	public Responsible getResp(String respUID)
	throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		IDirectoryEntry respentry = directory.getEntryFromUID(respUID);
		
		return RespFactory.createResponsible(respentry);
	}

	public IItemCollection getSustitutos(String uidSustituido)
	throws ISPACException
	{
		CollectionDAO collDAO = SustitucionDAO.getSustitutos(mDbCnt, uidSustituido);
		return collDAO.disconnect();
	}
		
	public IItemCollection getSubstitutes(String sustitutoUID)
	throws ISPACException
	{
		CollectionDAO collDAO = SustitucionDAO.getSubstitutes(mDbCnt, sustitutoUID);
		return collDAO.disconnect();
	}
	
	public IItemCollection getSubstitutes(int idFechSustitucion)
	throws ISPACException
	{
		CollectionDAO collDAO = SustitucionDAO.getSubstitutes(mDbCnt, idFechSustitucion);
		return collDAO.disconnect();
	}
	
	public IItemCollection getSubstitutesAssets(String sustitutoUID)
	throws ISPACException
	{
		CollectionDAO collDAO = SustitucionDAO.getSustitutosAssets(mDbCnt, sustitutoUID,DBUtil.getToDateByBD(mDbCnt, new Date(System.currentTimeMillis())));
		return collDAO.disconnect();
	}
	
	public IItemCollection getSubstitutesHistorics(String sustitutoUID)
	throws ISPACException
	{
		CollectionDAO collDAO = SustitucionDAO.getSustitutosHistorics(mDbCnt, sustitutoUID,DBUtil.getToDateByBD(mDbCnt, new Date(System.currentTimeMillis())));
		return collDAO.disconnect();
	}
	
	public Map getPermissions(IStage stage, String resp)
    throws ISPACException
	{
		CollectionDAO collDAO = PermissionDAO.getPermissions(mDbCnt, stage, resp);
        return collDAO.toMap(PermissionDAO.FIELD_PERMISION_TYPE);
	}

	public Map getPermissions(ITask task, String resp)
    throws ISPACException
	{
		CollectionDAO collDAO = PermissionDAO.getPermissions(mDbCnt, task, resp);
        return collDAO.toMap(PermissionDAO.FIELD_PERMISION_TYPE);
	}

	public Map getPermissions(EntityDAO entity, String resp)
    throws ISPACException
	{
		CollectionDAO collDAO = PermissionDAO.getPermissions(mDbCnt, entity, resp);
        return collDAO.toMap(PermissionDAO.FIELD_PERMISION_TYPE);
	}
	
	
	/**
     * Comprueba si alguno de los elementos de la cadena de responsabilidad que
     * tiene el permiso de edición no se debe a una supervisión en modo consulta
     * @return
     */
    public boolean existPermissionEditNotFollowSupervision(IStage stage ,String cadenaResponsabilidad, IResponsible resp )
    throws ISPACException 
    {		
    	
    	
    	
		return PermissionDAO.existPermissionEditNotFollowSupervision(mDbCnt, stage,cadenaResponsabilidad, getUidsResponsible(resp));
   	 	
    }
    
    
    /**
     * Comprueba si alguno de los elementos de la cadena de responsabilidad que
     * tiene el permiso de edición no se debe a una supervisión en modo consulta
     * @return
     */
    public boolean existPermissionEditNotFollowSupervision(ITask task , String cadenaResponsabilidad , IResponsible resp)
    throws ISPACException 
    {		
		return PermissionDAO.existPermissionEditNotFollowSupervision(mDbCnt, task, cadenaResponsabilidad, getUidsResponsible(resp));
   	 	
    }
    
    /**
     * Comprueba si alguno de los elementos de la cadena de responsabilidad que
     * tiene el permiso de edición no se debe a una supervisión en modo consulta
     * @return
     */
    public  boolean existPermissionEditNotFollowSupervision(IProcess process , String cadenaResponsabilidad, IResponsible resp )
    throws ISPACException 
    {		
		return PermissionDAO.existPermissionEditNotFollowSupervision(mDbCnt, process , cadenaResponsabilidad, getUidsResponsible(resp));
   	 	
    }
        

	public boolean existPermissions(ITask task , String resp, int[] typePermissions)
		    throws ISPACException
	{
			return PermissionDAO.existPermissions(mDbCnt, task, resp, typePermissions);
	}
	
	public boolean existPermissions(IStage stage, String resp, int[] typePermissions)
    throws ISPACException
	{
		return PermissionDAO.existPermissions(mDbCnt, stage, resp, typePermissions);
	}

	public boolean existPermissions(IProcess process, String resp, int[] typePermissions)
    throws ISPACException
	{
		return PermissionDAO.existPermissions(mDbCnt, process, resp, typePermissions);
	}

	public IItemCollection getPermissions(int typeObject, int idObject, int[] typePermissions)
	throws ISPACException
    {
		CollectionDAO coldao = PermissionDAO.getPermissions(mDbCnt, typeObject, idObject, typePermissions);

        List list = new ArrayList();
		while (coldao.next()) {

			ObjectDAO permission = coldao.value();
			String respName = permission.getString("RESP_NAME");
			if (StringUtils.isBlank(respName)) {

				String idResp = permission.getString("ID_RESP");
				IResponsible responsible = null;

				try {
					responsible = getResp(idResp);
				//} catch (ISPACException ie) {
				} catch (Exception ie) {
					// java.lang.NumberFormatException
					// si el ID_RESP no es un UID y es el nombre del responsable
				}

				/*
				if (responsible != null) {

					// Establecer el nombre del responsable
					permission.set("RESP_NAME", responsible.getRespName());
					permission.store(mDbCnt);
				} else {
					// Eliminar el permiso asociado al responsable que ya no existe
					// permission.delete(mDbCnt);
					// No eliminar sino no incluirlo en la lista
					permission = null;
				}
				*/

				if (responsible != null) {
					// Establecer el nombre del responsable
					permission.set("RESP_NAME", responsible.getRespName());
					permission.store(mDbCnt);
				}
				/*else {
					// Establecer el ID como nombre del responsable
					permission.set("RESP_NAME", idResp);
				}
				permission.store(mDbCnt);
				*/
			}

			if (permission != null) {
				list.add(permission);
			}
		}

		return new ListCollection(list);
    }

	public void addPermission(int typeObject, int idObject,
			IResponsible responsible, int typePermission) throws ISPACException {

		// Si el permisos es de lectura y ya tenemos dado de alta el de edición
		// no se insertará
		if (!(existsPermission(typeObject, idObject, responsible.getUID(),
				typePermission))) {

			try {
				
				PermissionDAO permission = new PermissionDAO(mDbCnt);
				permission.createNew(mDbCnt);

				permission.set("TP_OBJ", typeObject);
				permission.set("ID_OBJ", idObject);
				permission.set("ID_RESP", responsible.getUID());
				// Guardar el nombre del responsable para no consultarlo cada
				// vez que se muestra
				permission.set("RESP_NAME", responsible.getRespName());
				permission.set("PERMISO", typePermission);

				permission.store(mDbCnt);
				// Si todo ha sido correcto se hace commit de la transacción
			
			} catch (ISPACException e) {
				logger.error("Error en SecurityMgr:addPermission" + e);
				throw e;
			}
		}
	}

    public boolean existsPermission(int typeObject, int idObject, String resp, int typePermission)
    throws ISPACException
    {
    	return PermissionDAO.existPermission(mDbCnt, typeObject, idObject, resp, typePermission);
    }

	public IItemCollection getPermissionsResp (int typeObject, int idObject, String uid)
    	    throws ISPACException{
	
		CollectionDAO coldao = PermissionDAO.getPermissions(mDbCnt, typeObject, idObject, uid);
		return coldao.disconnect();
		
	}
	public void deletePermission(int typeObject, int idObject, String resp, int typePermission)
	throws ISPACException
    {
		StringBuffer sqlWhere = new StringBuffer();
		 CollectionDAO objlist=null;
		sqlWhere.append(" WHERE PERMISO= ").append(typePermission);
				
		switch (typePermission) {
		case ISecurityAPI.PERMISSION_INIT_EXP_PCD:
		case ISecurityAPI.PERMISSION_DELETE_EXP_PCD:
				sqlWhere.append(" AND ID_PCD = ").append(idObject)
				.append(" AND UID_USR = '").append(DBUtil.replaceQuotes(resp)).append("' ");
	            objlist = new CollectionDAO(PermissionsDAO.class);
	            objlist.delete(mDbCnt, sqlWhere.toString());
			break;
		default:
			sqlWhere.append(" AND TP_OBJ = ").append(typeObject)
			.append(" AND ID_OBJ = ").append(idObject)
			.append(" AND ID_RESP = '").append(DBUtil.replaceQuotes(resp)).append("' ");
		    objlist = new CollectionDAO(PermissionDAO.class);
		    objlist.delete(mDbCnt, sqlWhere.toString());
		}
		

		
    }
	
	/**
	 * Elimina todos los permisos de un responsable para un idObject en particular.
	 * @param typeObject
	 * @param idObject
	 * @param resp
	 * @throws ISPACException
	 */
	public void deletePermission(int typeObject, int idObject, String resp)
	throws ISPACException
    {
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(" WHERE  ")
				.append(" TP_OBJ = ").append(typeObject)
				.append(" AND ID_OBJ = ").append(idObject)
				.append(" AND ID_RESP = '").append(DBUtil.replaceQuotes(resp)).append("' ");

        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
        objlist.delete(mDbCnt, sqlWhere.toString());
    }
	
	private  Map getUidsResponsible(IResponsible res) throws ISPACException{
		 Map supervisorsUID = new HashMap();
		
		// Responsable
		supervisorsUID.put(res.getUID(), null);
		//Departamento al que pertenece
		IResponsible dept = res.getRespOrgUnit(); 
		if (dept != null){
			supervisorsUID.put(dept.getUID(), null);
		}
		
		// Grupos a los que pertenece el responsable
		IItemCollection groups = res.getUserRespGroups();
		while (groups.next()) {
			
			IResponsible group = (IResponsible) groups.value();
			supervisorsUID.put(group.getUID(), null);
		}
		return supervisorsUID;
	}

}