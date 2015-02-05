/*
 * Created on 26-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.security.FechSustitucionesDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.resp.RespFactory;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.security.SecurityMgr;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author iecisa
 * 
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RespManagerAPI implements IRespManagerAPI
{

	private ClientContext mContext;
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(RespManagerAPI.class);
	


	public RespManagerAPI(ClientContext context)
	{
		mContext = context;
	}

	/**
	 * Se comprueba que usuario existe en el LDAP y se recupera
	 * @param user Nombre del usuario
	 * @param password Contraseña
	 * @return El usuario conectado IResponsible
	 */

	public IResponsible login(String user, String password) throws ISPACException
	{
		// Se comprueba que usuario existe en el LDAP y se recupera
		// su información asociada
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		IDirectoryEntry userEntry = directory.login(user, password);

		return RespFactory.createResponsible(userEntry);
	}

	public IResponsible getRootResp() throws ISPACException
	{
		// Se comprueba que usuario existe en el LDAP y se recupera
		// su información asociada
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		IDirectoryEntry respentry = directory.getEntryFromRoot();

		return RespFactory.createResponsible(respentry);
	}

	public IResponsible getResp(String respUID) throws ISPACException
	{
		// Se comprueba que usuario existe en el LDAP y se recupera
		// su información asociada
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();

		IDirectoryEntry respentry = directory.getEntryFromUID(respUID);
		return RespFactory.createResponsible(respentry);
	}

	public IItemCollection getAncestors (String respUID) throws ISPACException
	{
		// Obtiene la coleccion de responsables ancestros del responsable
		// con UID el pasado como parametro
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		Set ancestors = directory.getAncestorsFromUID(respUID);
		Collection collection = RespFactory.createResponsibleSet(ancestors);
		return new CustomItemCollection (collection);
	}

	public IItemCollection getAllGroups () throws ISPACException
	{
		// Obtiene todos los grupos que existen en el directorio
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		Set sGroups = directory.getAllGroups();
		Collection cGroups = RespFactory.createResponsibleSet(sGroups);
		return new CustomItemCollection (cGroups);
	}

	public boolean isSupervised( String sUID, String sResponsible, int userMode)
	throws ISPACException
	{
		DbCnt cnt = mContext.getConnection();

		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			IItemCollection collection = security.getSuperviseds( sResponsible, userMode);
			Iterator iterator = collection.iterator();
			while (iterator.hasNext())
			{
				IItem supervisor = (IItem) iterator.next();
				String sSupervised = supervisor.getString("UID_SUPERVISADO");
				if (sUID.equalsIgnoreCase( sSupervised))
					return true;
			}
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en RespManagerAPI:isInSupervisedList(...)", ie);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}

		return false;
	}

	public LinkedHashMap getRespProperties (String respUID) throws ISPACException
	{
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		return directory.getPropertiesFromUID(respUID);
	}

	public IItemCollection getTotalModeSuperviseds (String uid) throws ISPACException
	{
		IItemCollection collection = null;
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			collection = security.getSuperviseds(uid, ISecurityAPI.SUPERV_TOTALMODE);
			collection = DAOCollectionToRespCollection (collection, "UID_SUPERVISADO");

		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
		return collection;
	}

	public IItemCollection getFollowedModeSuperviseds (String uid) throws ISPACException
	{
		IItemCollection collection = null;
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			collection = security.getSuperviseds(uid, ISecurityAPI.SUPERV_FOLLOWEDMODE);
			collection = DAOCollectionToRespCollection (collection, "UID_SUPERVISADO");
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
		return collection;
	}

	public IItemCollection getPermissions (String uid) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getPermissions(uid);
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }

    public IItemCollection getPermissions (int idpcd) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getPermissions(idpcd);
            //collection = DAOCollectionToRespCollection (collection, "UID_USR");
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }

    /**
     * Retorna una lista con todos los permisos (iniciar expediente, eliminar, editar y consultar) establecidos
     * en un procedimiento
     */
    public IItemCollection getAllPermissionsByPcd(int id_pcd , String uid) 
    	throws ISPACException{
    	DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getAllPermissionsByPcd(id_pcd,uid);
          
        }
        catch(ISPACException e){
        	logger.error("Error en getAllPermissionsByPcd "+e);
        	throw e;
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }
    /**
     * Obtiene los permisos de un objeto.
     * Si es un procedimiento hay que consultar tambien los permisos de iniciar y eliminar
     * @param typeObject
     * @param idObject
     * @return
     * @throws ISPACException
     */
    public IItemCollection getPermissions (int typeObject , int idObject) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getPermissions(typeObject, idObject,null);
          
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }

    /**
     * Obtiene los permisos de un objeto para un usuario en concreto
     * Si es un procedimiento hay que consultar tambien los permisos de iniciar y eliminar
     * @param typeObject
     * @param idObject
     * @return
     * @throws ISPACException
     */
    public IItemCollection getPermissionsResp (int typeObject, int idObject, String uid) throws ISPACException
    {
    	  DbCnt cnt = mContext.getConnection();
          try
          {
              SecurityMgr security = new SecurityMgr(cnt);
              return security.getPermissionsResp(typeObject, idObject,uid);
            
          }
          finally
          {
              mContext.releaseConnection(cnt);
          }
    	
    }
    
    public IItemCollection getPermissions (int idpcd, String uid) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getPermissions(idpcd, uid);
            //collection = DAOCollectionToRespCollection (collection, "UID_USR");
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }
    public IItemCollection getPermissionObject (String uid, int permission) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getPermissionObjects(uid,permission);
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }
    
    

    /**
     *  @param typeObject: 
   	 * 1 --> Identificador del procedimiento (ID en spac_p_procedimientos)
   	 * 2 --> Identificador del proceso (ID en spac_procesos).
   	 * 3 --> Identificador de la fase (ID en spac_fases). Sólo se establecen permisos para la fase, no para los trámites de la fase.
   	 * 4 --> Identificador de la fase (ID en spac_fases). Este permiso incluye también los trámites instanciados de la fase.
   	 * 5 --> Identificador de la fase en el procedimiento (ID en spac_p_fases). Sólo se establecen permisos para la fase, no para los trámites de la fase
   	 * 6 --> Identificador de la fase en el procedimiento (ID en spac_p_fases). Este permisos permite acceder a los trámites de la fase.
   	 * 7 --> Identificador del trámite (ID en spac_tramites)
   	 * 8 --> Identificador del trámite en el procedimiento (ID en spac_p_tramites)
   	 * Graficamente solo se permite dar permisos a nivel de procedimiento (1)
     * @param idObject: Identificador de objeto
     * @param responsible : Responsable
     * @throws ISPACException
     */
    public void deletePermissions (int typeObject, int idObject, IResponsible responsible) throws ISPACException
    {
    	if (logger.isDebugEnabled()) {
			logger.debug("RespManagerAPI -> Inicio deletePermissions tipoObjeto:"
					+ typeObject + " Responsable " + responsible.getUID());
		}

        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            if(typeObject==ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE){
            	security.deletePermissions(idObject ,responsible.getUID());
            }
            security.deletePermission(typeObject, idObject, responsible.getUID());
        }
        finally
        {
        	if (logger.isDebugEnabled()) {
    			logger.debug("RespManagerAPI -> Fin deletePermissions tipoObjeto:"
    					+ typeObject + " Responsable " + responsible.getUID());
    		}
            mContext.releaseConnection(cnt);
        }
    }

    public void addPermissions (int idpcd, String uid, int type) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            security.addPermissions(idpcd, uid, type);
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }
    
    
    public void deletePermissions (int idpcd, String uid) throws ISPACException
    {

        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            security.deletePermissions(idpcd, uid);
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }
    
    /**
     * 
     * @param typeObject: 
	 * 1 --> Identificador del procedimiento (ID en spac_p_procedimientos)
	 * 2 --> Identificador del proceso (ID en spac_procesos).
	 * 3 --> Identificador de la fase (ID en spac_fases). Sólo se establecen permisos para la fase, no para los trámites de la fase.
	 * 4 --> Identificador de la fase (ID en spac_fases). Este permiso incluye también los trámites instanciados de la fase.
	 * 5 --> Identificador de la fase en el procedimiento (ID en spac_p_fases). Sólo se establecen permisos para la fase, no para los trámites de la fase
	 * 6 --> Identificador de la fase en el procedimiento (ID en spac_p_fases). Este permisos permite acceder a los trámites de la fase.
	 * 7 --> Identificador del trámite (ID en spac_tramites)
	 * 8 --> Identificador del trámite en el procedimiento (ID en spac_p_tramites)
	 * Graficamente solo se permite dar permisos a nivel de procedimiento (1)
     * @param idObject: Identificador de objeto
     * @param responsible : Responsable
     * @param typePermissions: Tipo de permiso: 
     * 0 --> Iniciar Expediente
     * 1 --> Eliminar Expediente
     * 2 --> Consulta Expediente
     * 3 --> Edición Expediente
     * Los permisos 0 y 1 solo se permiten a nivel de procedimiento y se almacenaran en la tabla spac_ss_permisos
     * Los de consulta y edición se almacenaran en la tabla spac_permisos
     */
    public void addPermissions(int typeObject, int idObject, IResponsible responsible, int [] typePermissions)throws ISPACException{
    	
		if (logger.isDebugEnabled()) {
			logger.debug("RespManagerAPI -> Inicio addPermissions tipoObjeto:"
					+ typeObject + " Responsable " + responsible.getUID());
		}
		if (typePermissions == null || typePermissions.length == 0) {
			return;
		}
		int i;
		int tipoPermiso = 0;
		DbCnt cnt = mContext.getConnection();
		try {
			SecurityMgr security = new SecurityMgr(cnt);
			for (i = 0; i < typePermissions.length; i++) {
				tipoPermiso = typePermissions[i];
				switch (tipoPermiso) {
				case ISecurityAPI.PERMISSION_INIT_EXP_PCD:
				case ISecurityAPI.PERMISSION_DELETE_EXP_PCD:
					security.addPermissions(idObject, responsible.getUID(),
							tipoPermiso);
					break;
				default:
					security.addPermission(typeObject, idObject, responsible,
							tipoPermiso);
				}
			}

		} finally {
			mContext.releaseConnection(cnt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("RespManagerAPI -> Fin addPermissions tipoObjeto:"
					+ typeObject + " Responsable " + responsible.getUID());
		}
    }

    public void addPermissions (int idpcd, String uid, int[] type) throws ISPACException
    {

        for (int ind=0; ind<type.length; ind++)
        {
            addPermissions(idpcd, uid, type[ind]);
        }
    }

	public Map getResp(List resps, String fieldUID) throws ISPACException {

		Map map = new HashMap();
		if (resps != null) {
			int i = 0;
			String uid = "";
			String uid_name = "";
			for (i = 0; i < resps.size(); i++) {
				IItem item = (IItem) resps.get(i);

				uid = item.getString("UID_USR");
				uid_name = (String) map.get(uid);
				if (StringUtils.isEmpty(uid_name)) {
					IResponsible resp = getResp(uid);
					if (resp != null) {
						uid_name = resp.getName();
						map.put(uid, uid_name);
					} else if (logger.isDebugEnabled()) {
						logger.debug("RespManagerAPI: getResp No se ha encontrado el responsable para el uid "
								+ uid);

					}
				}
			}
		}
		return map;
	}
    public IItemCollection getRespPermissions (int idpcd) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getRespPermissions(idpcd);
            //collection = DAOCollectionToRespCollection (collection, "UID_USR");
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }
    
    public IItemCollection getRespPermissions (int idpcd, int permission) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getRespPermissions(idpcd, permission);
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }

    public IItemCollection getRespPermissions (int idpcd, String uid) throws ISPACException
    {

        DbCnt cnt = mContext.getConnection();
        try
        {
            SecurityMgr security = new SecurityMgr(cnt);
            return security.getRespPermissions(idpcd, uid);
            //collection = DAOCollectionToRespCollection (collection, "UID_USR");
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }

	public IItemCollection getFunctions (String uid) throws ISPACException
	{
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			return security.getFunctions(uid);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}

	public boolean isFunction(String uid, int function) throws ISPACException
	{
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			return security.isFunction(uid, function);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}
	
	public boolean isFunction(List uids, int function) throws ISPACException
	{
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			return security.isFunction(uids, function);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}

    public Set getPermissionsSet (int idpcd) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            Set permset=new HashSet();
            SecurityMgr security = new SecurityMgr(cnt);
            IItemCollection itemcol= security.getPermissions(idpcd);
            while(itemcol.next())
            {
                int role=itemcol.value().getInt("PERMISO");
                permset.add(new Integer(role));
            }
            return permset;
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }

    }

    public Set getPermissionsSet (int idpcd, String uid) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            Set permset=new HashSet();
            SecurityMgr security = new SecurityMgr(cnt);
            IItemCollection itemcol = security.getPermissions(idpcd, uid);
            while(itemcol.next())
            {
                int role=itemcol.value().getInt("PERMISO");
                permset.add(new Integer(role));
            }
            return permset;
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }


    public Set getPermissionPcdSet (String uid,int permission) throws ISPACException
    {
        DbCnt cnt = mContext.getConnection();
        try
        {
            Set pcdset=new HashSet();
            SecurityMgr security = new SecurityMgr(cnt);
            IItemCollection itemcol = security.getPermissionObjects(uid,permission);
            while(itemcol.next())
            {
                int pcdId=itemcol.value().getInt("ID_PCD");
                pcdset.add(new Integer(pcdId));
            }
            return pcdset;
        }
        finally
        {
            mContext.releaseConnection(cnt);
        }
    }

	public Set getFunctionsSet (String uid) throws ISPACException
	{
		DbCnt cnt = mContext.getConnection();
		try
		{
		    Set roleset=new HashSet();
			SecurityMgr security = new SecurityMgr(cnt);
			IItemCollection itemcol = security.getFunctions(uid);
            while(itemcol.next())
            {
                int role=itemcol.value().getInt("FUNCION");
                roleset.add(new Integer(role));
            }
            return roleset;
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}

	public Set getFunctionsSet(List uidList) throws ISPACException {
		
		DbCnt cnt = mContext.getConnection();
		try
		{
		    Set roleset=new HashSet();
			SecurityMgr security = new SecurityMgr(cnt);
			IItemCollection itemcol = security.getFunctions(uidList);
            while(itemcol.next())
            {
                int role=itemcol.value().getInt("FUNCION");
                roleset.add(new Integer(role));
            }
            return roleset;
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}

	private IItemCollection DAOCollectionToRespCollection (IItemCollection daos, String key)
	throws ISPACException
	{
		List list = new ArrayList ();
		Iterator iter = daos.iterator();
		while (iter.hasNext())
		{
			ObjectDAO dao = (ObjectDAO) iter.next();
			String uid = dao.getString(key);
			IResponsible resp = getResp (uid);
			list.add(resp);
		}
		return new ListCollection (list);
	}
	
	/*
	private IItemCollection DAOCollectionToRespDAOCollection (IItemCollection daos, String key)
    throws ISPACException
    {
        List list = new ArrayList ();
        Iterator iter = daos.iterator();
        while (iter.hasNext())
        {
            ObjectDAO dao = (ObjectDAO) iter.next();
            String uid = dao.getString(key);
            dao.set(key, getResp(uid).getString("NAME"));
            list.add(dao);
        }
        return new ListCollection (list);
    }
	*/
	
	public IItemCollection getSustitutes(String uidSustituido) throws ISPACException {
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			IItemCollection sustitutos = security.getSustitutos(uidSustituido);
			return getSubstitutePeriodCollection(sustitutos);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}
	
	public IItemCollection getSubstitutes(String sustitutoUID) throws ISPACException {
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			return security.getSubstitutes(sustitutoUID);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}
	
	public IItemCollection getSubstitutes(int idFechSustitucion) throws ISPACException {
		
		IItemCollection collection = null;
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			collection = security.getSubstitutes(idFechSustitucion);
			collection = DAOCollectionToRespCollection (collection, "UID_SUSTITUTO");
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
		return collection;
	}
	
	public IItem getFechSustitucion(String id) throws ISPACException {
		DbCnt cnt = mContext.getConnection();
		try
		{
			return new FechSustitucionesDAO(cnt, Integer.parseInt(id));
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
	}
	
	private IItemCollection getSubstitutePeriodCollection(IItemCollection substituteCollection) throws ISPACException {
		
        List list = new ArrayList ();
        Properties properties = getSubsitutePeriodProperties();
        Map responsibles = new HashMap();
        
        while (substituteCollection.next()) {
        	
        	IItem itemSubstitute = substituteCollection.value();
        	
        	IItem itemSubstitutePeriod = new GenericItem(properties, "ID");
        	itemSubstitutePeriod.setKey(itemSubstitute.getInt("SUSTITUCIONFECHA:ID"));
        	
        	String uidSustituto = itemSubstitute.getString("SUSTITUCION:UID_SUSTITUTO");
        	IResponsible responsible = (IResponsible) responsibles.get(uidSustituto);
        	if (responsible == null) {
        		
        		responsible = getResp(uidSustituto);
        		responsibles.put(uidSustituto, responsible);
        	}
        	itemSubstitutePeriod.set("NAME", responsible.getName());
        	itemSubstitutePeriod.set("RESPNAME", responsible.getRespName());
        	itemSubstitutePeriod.set("USER", Boolean.valueOf(responsible.isUser()));
        	itemSubstitutePeriod.set("GROUP", Boolean.valueOf(responsible.isGroup()));
        	itemSubstitutePeriod.set("ORGUNIT", Boolean.valueOf(responsible.isOrgUnit()));
        	
        	itemSubstitutePeriod.set("ID_FECH_SUSTITUCIONES", itemSubstitute.getInt("FECHSUSTITUCIONES:ID"));
        	itemSubstitutePeriod.set("FECHA_INICIO", itemSubstitute.getDate("FECHSUSTITUCIONES:FECHA_INICIO"));
        	itemSubstitutePeriod.set("FECHA_FIN", itemSubstitute.getDate("FECHSUSTITUCIONES:FECHA_FIN"));
        	itemSubstitutePeriod.set("DESCRIPCION", itemSubstitute.getString("FECHSUSTITUCIONES:DESCRIPCION"));

            list.add(itemSubstitutePeriod);
        }
        return new ListCollection(list);
    }
	
	
    private Properties getSubsitutePeriodProperties() {
    	
        int ordinal = 0;
        Properties properties = new Properties();
        properties.add( new Property(ordinal++, "ID", Types.INTEGER));
        properties.add( new Property(ordinal++, "NAME", Types.VARCHAR));
        properties.add( new Property(ordinal++, "RESPNAME", Types.VARCHAR));
        properties.add( new Property(ordinal++, "USER", Types.BOOLEAN));
        properties.add( new Property(ordinal++, "GROUP", Types.BOOLEAN));
        properties.add( new Property(ordinal++, "ORGUNIT", Types.BOOLEAN));
        properties.add( new Property(ordinal++, "ID_FECH_SUSTITUCIONES", Types.INTEGER));
        properties.add( new Property(ordinal++, "FECHA_INICIO", Types.DATE));
        properties.add( new Property(ordinal++, "FECHA_FIN", Types.DATE));
        properties.add( new Property(ordinal++, "DESCRIPCION", Types.VARCHAR));

        return properties;
    }

	public IItemCollection getInfoResponsibles(IItemCollection itemcol) throws ISPACException {
		return DAOCollectionToRespCollection (itemcol, "UID_USR");
	}

    /**
     * @param uidSustitutido
     * @return Devuelve todos los sutitutos que tenga una fecha de fin mayor que la fecha actual
     */
	public IItemCollection getSustitutesAssets(String uidSustituido)
			throws ISPACException {
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			IItemCollection sustitutos = security.getSubstitutesAssets(uidSustituido);
			return getSubstitutePeriodCollection(sustitutos);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
		
		
	}
	/**
	 * 
	 * @param uidSustituido
	 * @return Devuelve todos los sutitutos que ya no esten vigente, fecha fin inferior a fecha actual
	 * @throws ISPACException
	 */
	public IItemCollection getSustitutesHistoricals(String uidSustituido)
			throws ISPACException {
		DbCnt cnt = mContext.getConnection();
		try
		{
			SecurityMgr security = new SecurityMgr(cnt);
			IItemCollection sustitutos = security.getSubstitutesHistorics(uidSustituido);
			return getSubstitutePeriodCollection(sustitutos);
		}
		finally
		{
			mContext.releaseConnection(cnt);
		}
		
	}
}
