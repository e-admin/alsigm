package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.security.FechSustitucionesDAO;
import ieci.tdw.ispac.ispaclib.dao.security.PermissionsDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SupervisionDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SustitucionDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SustitucionFechaDAO;
import ieci.tdw.ispac.ispaclib.dao.security.SystemFunctionsDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.security.SecurityMgr;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SecurityAPI implements ISecurityAPI
{

	private ClientContext mcontext;

	public SecurityAPI(ClientContext context)
	{
		mcontext = context;
	}
	
	public IItemCollection getFunctions(String uid) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			CollectionDAO collection = new CollectionDAO(SystemFunctionsDAO.class);
			collection.query(cnt, "WHERE UID_USR = '" + DBUtil.replaceQuotes(uid) + "'");
			
			return collection.disconnect();
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public boolean isFunction(String uid, int function) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.isFunction(uid, function);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	/**
	 * Devuelve cierto si el usuario tiene el permiso 
	 * @param respList Cadena de responsabilidad
	 * @param type Tipo de permiso a comprobar 
	 * 0-->Eliminar expediente (enviar a la papelera)
	 * 1->Iniciar expediente
	 * @param idpcd Identificador del procedimiento para el que se va a comprobar el permiso
	 * @return Cierto si el usuario tiene el permiso, falso en caso contrario
	 * @throws ISPACException
	 */
	public boolean isPermission(String respList, int type , int idpcd)throws ISPACException{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.isPermission(respList, type, idpcd);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	public void deleteFunctions(String uid) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			CollectionDAO collection = new CollectionDAO(SystemFunctionsDAO.class);
			collection.delete(cnt, "WHERE UID_USR = '" + DBUtil.replaceQuotes(uid) + "'");
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void changeFunctions(String uid, Map newFunctions) throws ISPACException
	{
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
    	
		try {
			// Abrir transacción
			if (!ongoingTX) {
				mcontext.beginTX();
			}
			
			// Conexión de la transacción
			DbCnt cnt = mcontext.getConnection();
			
			// Funciones del responsable
			IItemCollection functions = getFunctions(uid);
			while (functions.next()) {
				
				IItem function = (IItem) functions.value();
				
				Integer iFunction = new Integer(function.getInt("FUNCION"));
				if (!newFunctions.containsKey(iFunction)) {
					
					// Función desmarcada que se elimina
					function.delete(mcontext);
				}
				
				newFunctions.remove(iFunction);
			}
			
			// Nuevas funciones marcadas
			Iterator it = newFunctions.keySet().iterator();
			while (it.hasNext()) {
				
				SystemFunctionsDAO functionDAO = new SystemFunctionsDAO(cnt);
				functionDAO.createNew(cnt);
				functionDAO.set("UID_USR", uid);
				functionDAO.set("FUNCION", (Integer) it.next());
				functionDAO.store(cnt);
			}
			
			// Transacción finalizada correctamente, hacer el commit
			bCommit = true;
		}
		finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public List getSuperviseds(String uid, int mode)throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE UID_SUPERVISOR = '" + DBUtil.replaceQuotes(uid) + "' AND TIPO = " + mode;
			CollectionDAO collection = new CollectionDAO(SupervisionDAO.class);
			collection.query(cnt, where);
			return CollectionBean.getBeanList(collection.disconnect());
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
		
	}
	public void deleteSuperviseds(String uid, String[] uids, int mode) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			String where = "WHERE UID_SUPERVISOR = '" + DBUtil.replaceQuotes(uid) + "' AND TIPO = " + mode;
			CollectionDAO collection = new CollectionDAO(SupervisionDAO.class);
			if (uids.length > 0)
			{
				where += " AND UID_SUPERVISADO IN (";
				for (int i=0; i<uids.length; i++)
					where += "'" + DBUtil.replaceQuotes(uids[i]) + "',";
				where = where.substring(0, where.length()-1) + ")";
			}
			collection.delete(cnt, where);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public void addSupervised(String supervisor, 
							  String supervised, 
							  int mode) throws ISPACException
	{
		
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SupervisionDAO supervision = new SupervisionDAO(cnt);
			supervision.createNew(cnt);
			supervision.set("UID_SUPERVISOR", supervisor);
			supervision.set("UID_SUPERVISADO", supervised);
			supervision.set("TIPO", mode);
			supervision.store(cnt);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

    public void deletePermissions(int id) throws ISPACException
    {
        DbCnt cnt = mcontext.getConnection();
        try
        {
            String where = "WHERE ID=" + id;
            CollectionDAO collection = new CollectionDAO(PermissionsDAO.class);
            collection.delete(cnt, where);
        }
        finally
        {
            mcontext.releaseConnection(cnt);
        }
    }
        
    public void addSustituciones(String uidSustituido,
    							 String[] uidSustitutos,
    							 Date fechaInicio, 
    							 Date fechaFin,
    							 String descripcion) throws ISPACException {
    	
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
    	
		try {
			// Abrir transacción
			if (!ongoingTX) {
				mcontext.beginTX();
			}
			
			// Conexión de la transacción
			DbCnt cnt = mcontext.getConnection();

			// Crear el período de sustitución
			FechSustitucionesDAO fechSustitucionesDAO = new FechSustitucionesDAO(cnt);
			fechSustitucionesDAO.createNew(cnt);
			fechSustitucionesDAO.set("DESCRIPCION", descripcion);
			fechSustitucionesDAO.set("FECHA_INICIO", fechaInicio);
			fechSustitucionesDAO.set("FECHA_FIN", fechaFin);
			fechSustitucionesDAO.store(cnt);
			
			// Crear las sustituciones relacionadas con el período
	        for (int i = 0; i < uidSustitutos.length; i++) {
	        	
	        	String uidSustituto = uidSustitutos[i];
	        	
		    	SustitucionDAO sustitucionDAO =  SustitucionDAO.getSustitucion(cnt, uidSustituto, uidSustituido);
		    	if (sustitucionDAO == null) {
		    		
		    		sustitucionDAO = new SustitucionDAO(cnt);
		    		sustitucionDAO.createNew(cnt);
		    		sustitucionDAO.set("UID_SUSTITUIDO", uidSustituido);
		    		sustitucionDAO.set("UID_SUSTITUTO", uidSustituto);
		    		sustitucionDAO.store(cnt);
		    	}
		    	
		    	SustitucionFechaDAO sustitucionFechaDAO = new SustitucionFechaDAO(cnt);
		    	sustitucionFechaDAO.createNew(cnt);
		    	sustitucionFechaDAO.set("ID_SUSTITUCION", sustitucionDAO.getKeyInteger());
		    	sustitucionFechaDAO.set("ID_FECHSUSTITUCION", fechSustitucionesDAO.getKeyInteger());
		    	sustitucionFechaDAO.store(cnt);
	        }
			
			// Transacción finalizada correctamente, hacer el commit
	        bCommit = true;
		}
		finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
    }
    
    public void addSustituciones(int idFechSustitucion,
    							 String uidSustituido,
    							 String[] uidSustitutos) throws ISPACException {

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

    	try {
			// Abrir transacción
			if (!ongoingTX) {
				mcontext.beginTX();
			}

			// Conexión de la transacción
			DbCnt cnt = mcontext.getConnection();
			
			// Bloquear la tabla de sustituciones
			ObjectDAO.getForUpdate(cnt, SustitucionDAO.class, null);
			
			// Obtener los uids de los sustitutos ya existentes en el período de sustitución
			Map uidSustitutosInFechSustitucion = SustitucionDAO.getSustitutos(cnt, idFechSustitucion).toMapStringKey("UID_SUSTITUTO");
			
			// Crear las sustituciones relacionadas con el período
	        for (int i = 0; i < uidSustitutos.length; i++) {
	        	
	        	String uidSustituto = uidSustitutos[i];
	        	if (!uidSustitutosInFechSustitucion.containsKey(uidSustituto)) {

			    	SustitucionDAO sustitucionDAO =  SustitucionDAO.getSustitucion(cnt, uidSustituto, uidSustituido);
			    	if (sustitucionDAO == null) {
			    		
			    		sustitucionDAO = new SustitucionDAO(cnt);
			    		sustitucionDAO.createNew(cnt);
			    		sustitucionDAO.set("UID_SUSTITUIDO", uidSustituido);
			    		sustitucionDAO.set("UID_SUSTITUTO", uidSustituto);
			    		sustitucionDAO.store(cnt);
			    	}

			    	SustitucionFechaDAO sustitucionFechaDAO = new SustitucionFechaDAO(cnt);
			    	sustitucionFechaDAO.createNew(cnt);
			    	sustitucionFechaDAO.set("ID_SUSTITUCION", sustitucionDAO.getKeyInteger());
			    	sustitucionFechaDAO.set("ID_FECHSUSTITUCION", new Integer(idFechSustitucion));
			    	sustitucionFechaDAO.store(cnt);	        		
	        	}
	        }

			// Transacción finalizada correctamente, hacer el commit
	        bCommit = true;
    	}
    	finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
    	}
    }
    
    public void modifyFechSustitucion(int id,
			 						  Date fechaInicio, 
			 						  Date fechaFin,
			 						  String descripcion) throws ISPACException {

		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;

    	try {
			// Abrir transacción
			if (!ongoingTX) {
				mcontext.beginTX();
			}
			
			// Conexión de la transacción
			DbCnt cnt = mcontext.getConnection();
			
			// Bloquear la tabla de fechas de sustituciones
			ObjectDAO.getForUpdate(cnt, FechSustitucionesDAO.class, null);

			// Actualizar el período de sustitución
			FechSustitucionesDAO fechSustitucionesDAO = new FechSustitucionesDAO(cnt, id);
			fechSustitucionesDAO.set("DESCRIPCION", descripcion);
			fechSustitucionesDAO.set("FECHA_INICIO", fechaInicio);
			fechSustitucionesDAO.set("FECHA_FIN", fechaFin);
			fechSustitucionesDAO.store(cnt);

			// Transacción finalizada correctamente, hacer el commit
			bCommit = true;
		}
		finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
    }
    
    public void deleteSustituciones(String[] idsSustitucionFecha) throws ISPACException {
		
		// Ejecución en un contexto transaccional
		boolean ongoingTX = mcontext.ongoingTX();
		boolean bCommit = false;
    	
		try {
			// Abrir transacción
			if (!ongoingTX) {
				mcontext.beginTX();
			}
			
			// Conexión de la transacción
			DbCnt cnt = mcontext.getConnection();
			
			// Bloquear la tabla de sustituciones
			ObjectDAO.getForUpdate(cnt, SustitucionDAO.class, null);
			
			// Eliminar las relaciones entre los sustitutos y los períodos de sustitución
			String sqlWhere = "WHERE ID IN (";
			for (int i = 0; i < idsSustitucionFecha.length; i++) {
				sqlWhere += idsSustitucionFecha[i] + ",";
			}
			sqlWhere = sqlWhere.substring(0, sqlWhere.length()-1) + ")";
			
			CollectionDAO collection = new CollectionDAO(SustitucionFechaDAO.class);
			collection.delete(cnt, sqlWhere);
			
			// Eliminar las sustituciones no relacionadas
			sqlWhere = "WHERE ID NOT IN ( SELECT DISTINCT ID_SUSTITUCION FROM " + SustitucionFechaDAO.TABLENAME + " )";
			collection = new CollectionDAO(SustitucionDAO.class);
			collection.delete(cnt, sqlWhere);
			
			// Eliminar los  períodos de sustitución no relacionados
			sqlWhere = "WHERE ID NOT IN ( SELECT DISTINCT ID_FECHSUSTITUCION FROM " + SustitucionFechaDAO.TABLENAME + " )";
			collection = new CollectionDAO(FechSustitucionesDAO.class);
			collection.delete(cnt, sqlWhere);

			// Transacción finalizada correctamente, hacer el commit
			bCommit = true;
		}
		finally {
			if (!ongoingTX) {
				mcontext.endTX(bCommit);
			}
		}
	}

	public boolean existPermissions(IStage stage, String resp, int[] typePermissions) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.existPermissions(stage, resp, typePermissions);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	

	public boolean existPermissions(IProcess process, String resp, int[] typePermissions) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.existPermissions(process, resp, typePermissions);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	
	public boolean existPermissions(ITask task, String resp, int[] typePermissions) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.existPermissions(task, resp, typePermissions);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	
	public boolean existPermissionEditNotFollowSupervision(IStage stage,String cadResponsabilidad, IResponsible resp) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.existPermissionEditNotFollowSupervision(stage,cadResponsabilidad, resp);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}
	
	
	public boolean existPermissionEditNotFollowSupervision(ITask task, String cadenaResponsabilidad, IResponsible resp) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.existPermissionEditNotFollowSupervision(task,cadenaResponsabilidad, resp);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public boolean existPermissionEditNotFollowSupervision(IProcess process, String cadenaResponsabilidad, IResponsible resp) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.existPermissionEditNotFollowSupervision(process,cadenaResponsabilidad, resp);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getPermission(int typeObject, int idObject, int[] typePermissions) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.getPermissions(typeObject, idObject, typePermissions);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	/*public IItemCollection getProcessPermission(int idProcess, int[] typePermissions) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			return securityMgr.getPermissions(ISecurityAPI.PERMISSION_TPOBJ_PROCESS, idProcess, typePermissions);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}*/

	/*public void addProcessPermission(int idProcess, IResponsible responsible, int typePermission) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			securityMgr.addPermission(ISecurityAPI.PERMISSION_TPOBJ_PROCESS, idProcess,  responsible, typePermission);
		}
		finally
		{
		mcontext.releaseConnection(cnt);
		}
	}*/

	public void deletePermission(int typeObject, int idObject, String resp, int typePermission) throws ISPACException
	{
		DbCnt cnt = mcontext.getConnection();
		try
		{
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			securityMgr.deletePermission(typeObject, idObject, resp, typePermission);
		}
		finally
		{
		mcontext.releaseConnection(cnt);
		}
	}
}