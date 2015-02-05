package ieci.tdw.ispac.ispaclib.invesdoc.directory;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.invesdoc.InvesDocConstants;
import ieci.tdw.ispac.ispaclib.invesdoc.directory.dao.DepartmentDAO;
import ieci.tdw.ispac.ispaclib.invesdoc.directory.dao.GroupDAO;
import ieci.tdw.ispac.ispaclib.invesdoc.directory.dao.UserDAO;
import ieci.tdw.ispac.ispaclib.invesdoc.directory.dao.UserGroupDAO;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class InvesDocEntry implements IDirectoryEntry
{
	// Tipo de entrada
	private int mEntryType;
	// Nombre usuario
	private String msName;
	// Nombre usuario
	private String msPassword;
	// Identificador del departamento padre
	private int mParentId;
	// UID de la entrada
	private int mUID;
	// Departamento
	private InvesDocEntry mParent;
	// Grupos a los que pertenece el usuario 
	private Set mGroups;

	// Nombre largo de usuario
	private String msLongName;
	
	public String getName()
	{
		return msName;
	}
	
	public String getLongName()
	{
		return msLongName;
	}
	public int getID()
	{
		return mUID;
	}
	public String getUID()
	{
		return mEntryType + "-" + mUID;
	}
	public int getParent()
	{
		return mParentId;
	}
	public int getEntryType()
	{
		return mEntryType;
	}
	public IDirectoryEntry getParentEntry()
	throws ISPACException
	{
		return mParent;
	}
	public Set getGroupEntries()
	throws ISPACException
	{
		return mGroups;
	}
	public String getPassword()
	{
		return msPassword;
	}
	
	private InvesDocEntry() 
	throws ISPACException
	{
		ISPACConfiguration parameters = ISPACConfiguration.getInstance();
		msName = parameters.get(InvesDocConstants.INVESDOC_DIRECTORY_CONNECTOR_NAME);
		msPassword = null;
		mUID = 0;
		mEntryType = ET_UNIT;
		mParent = null;
		mGroups = new LinkedHashSet();
		mParentId = -1;
	}

	private InvesDocEntry(ObjectDAO object,DbCnt cnt) 
	throws ISPACException
	{
		msName = object.getString("NAME");
		String property=ISPACConfiguration.getInstance().get("INVESDOC_USER_NAME_FIELD");
		if(StringUtils.isNotBlank(property) && StringUtils.isNotBlank(object.getString(property))){
			msLongName=object.getString(property);
		}
		else{
			msLongName=msName;
		}
		
		msPassword = null;
		mUID = object.getKeyInt();
		mEntryType = ET_UNDEFINED;
		mParent = null;
		mGroups = new LinkedHashSet();
		mParentId = 0;
		
		if (object instanceof UserDAO)
		{
			mEntryType = ET_PERSON;
			mParentId = object.getInt("DEPTID");
			msPassword = object.getString("PASSWORD");
			setParent( cnt);
			setGroups( cnt);
		}
		else if (object instanceof GroupDAO)
		{
			mEntryType = ET_GROUP;
		}
		else if (object instanceof DepartmentDAO)
		{
			mEntryType = ET_UNIT;
			mParentId = object.getInt( "PARENTID");
			setParent( cnt);
		}
	}
	
	/**
	 * Obtiene una entrada
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static InvesDocEntry getEntryFromUID(String sUID, DbCnt cnt)
	throws ISPACException
	{
		switch (getType(sUID))
		{
			case ET_PERSON :
				return getUserEntry(getID(sUID), cnt);
			case ET_UNIT :
				return getDepartmentEntry(getID(sUID), cnt);
			case ET_GROUP :
				return getGroupEntry(getID(sUID), cnt);
			default :
				return null;
		}
	}

	/**
	 * Obtiene una entrada de usuarios por nombre
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static InvesDocEntry getUserEntry(String name, DbCnt cnt)
	throws ISPACException
	{
		CollectionDAO collection;
			
		collection = new CollectionDAO(UserDAO.class);
		String where = "WHERE NAME = '" + DBUtil.replaceQuotes(name) + "'";
		collection.query(cnt,where);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();
		
		if (!iterator.hasNext())
			throw new ISPACNullObject("No existe el usuario");

		InvesDocEntry entry = new InvesDocEntry( (ObjectDAO) iterator.next(), cnt);
		
		return entry;
	}

	/**
	 * Obtiene una entrada de usuarios
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static InvesDocEntry getUserEntry(int uid, DbCnt cnt)
	throws ISPACException
	{
		UserDAO user = new UserDAO(cnt);
		user.setKey(uid);
		user.load(cnt);

		InvesDocEntry entry = new InvesDocEntry( user, cnt);
		
		return entry;
	}

	/**
	 * Obtiene una entrada de departamento
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static InvesDocEntry getDepartmentEntry(int uid, DbCnt cnt)
	throws ISPACException
	{
		InvesDocEntry entry;
		
		if (uid == 0)
		{
			entry = new InvesDocEntry();
		}
		else
		{
			DepartmentDAO department = new DepartmentDAO(cnt);
			department.setKey(uid);
			department.load(cnt);
	
			entry = new InvesDocEntry( department, cnt);
		}

		return entry;
	}

	/**
	 * Obtiene una entrada de grupo
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static InvesDocEntry getGroupEntry(int uid, DbCnt cnt)
	throws ISPACException
	{
		GroupDAO group = new GroupDAO(cnt);
		group.setKey(uid);
		group.load(cnt);

		InvesDocEntry entry = new InvesDocEntry( group,cnt);

		return entry;
	}

	protected static Set getAllGroups(DbCnt cnt)
	throws ISPACException
	{
		LinkedHashSet groups = new LinkedHashSet();
		
		CollectionDAO collection = new CollectionDAO(GroupDAO.class);
		collection.query(cnt,"ORDER BY NAME");
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			GroupDAO group = (GroupDAO) iterator.next(); 
			InvesDocEntry entry = new InvesDocEntry( group,cnt);
			groups.add( entry);
		}
		
		return groups;
	}

	protected static Set getAncestorsFromUID(String sUID, DbCnt cnt)
	throws ISPACException
	{
		LinkedHashSet ancestors=new LinkedHashSet();

		//////
		// Este entry no tiene pq ser un deparamento, puede ser un usuario,
		// y ese es el motivo pq el cual casca
		//////		
		InvesDocEntry entry = getEntryFromUID(sUID, cnt);
		ancestors.add( entry);
		
		IDirectoryEntry parent = entry.getParentEntry();
		
		while (parent != null)
		{
			ancestors.add( parent);
			entry = getDepartmentEntry(entry.getParent(), cnt);
			parent = entry.getParentEntry();
		}
		
		return ancestors;
	}

	protected static Set getOrgUnitsFromUID(String sUID, DbCnt cnt)
	throws ISPACException
	{
		LinkedHashSet departments = new LinkedHashSet();
		
		CollectionDAO collection = new CollectionDAO(DepartmentDAO.class);
		String where = "WHERE PARENTID = " 
					 + getID(sUID)
					 + " ORDER BY NAME"; 
		collection.query(cnt,where);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			DepartmentDAO department = (DepartmentDAO) iterator.next(); 
			InvesDocEntry entry = new InvesDocEntry( department,cnt);
			departments.add( entry);
		}
		
		return departments;
	}

	protected static Set getUsersFromUID(String sUID, DbCnt cnt)
	throws ISPACException
	{
		LinkedHashSet users = new LinkedHashSet();
		
		CollectionDAO collection = new CollectionDAO(UserDAO.class);
		String where = "WHERE DEPTID = " 
					 + getID(sUID) 
					 + " ORDER BY NAME"; 
		collection.query(cnt,where);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			UserDAO user = (UserDAO) iterator.next();
			if (user.getKeyInt() != 0)
			{
				InvesDocEntry entry = new InvesDocEntry( user,cnt);
				users.add( entry);
			}
		}
		
		return users;
	}
	/**
	 * Obtiene los usuarios de un grupo
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static Set getMembersFromUID(String sUID, DbCnt cnt)
	throws ISPACException
	{
		LinkedHashSet members = new LinkedHashSet();

		String where = "WHERE GROUPID = " + getID(sUID);

		CollectionDAO collection = new CollectionDAO(UserGroupDAO.class);
		collection.query(cnt,where);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			IItem item = (UserGroupDAO) iterator.next(); 
			UserDAO user = new UserDAO(cnt);
			user.setKey(item.getInt("USERID"));
			user.load(cnt);
			
			members.add( new InvesDocEntry( user,cnt));
		}
		
		return members;
	}

	/**
	 * Obtiene la entrada raiz
	 * 
	 * @return UserEntry
	 * @throws ISPACException
	 */
	protected static IDirectoryEntry getEntryFromRoot(DbCnt cnt)
	throws ISPACException
	{
		return new InvesDocEntry();
	}

	protected static int getID( String sUID)
	throws ISPACException
	{
	    String sID = null;
	    //String sTYPE = null;
	    StringTokenizer tokens = new StringTokenizer( sUID, "-");
	    
	    if (tokens.hasMoreTokens()) 
	    {
	        //sTYPE = tokens.nextToken();
            tokens.nextToken();
		    if (tokens.hasMoreTokens())
		    {
		        sID = tokens.nextToken();
		    }
	    }
	    
	    if (sID == null)
	    {
			throw new ISPACException( "UID erróneo");
	    }
		
		return Integer.parseInt( sID);
	}
	
	protected static int getType( String sUID)
	throws ISPACException
	{
	    String sTYPE = null;
	    StringTokenizer tokens = new StringTokenizer( sUID, "-");
	    
	    if (tokens.hasMoreTokens()) 
	    {
	        sTYPE = tokens.nextToken();
	    }
	    
	    if (sTYPE == null)
	    {
			throw new ISPACException( "TYPE erróneo");
	    }
		
		return Integer.parseInt( sTYPE);
	}

	protected void setParent(DbCnt cnt)
	throws ISPACException
	{
		if (mParentId == 0)
		{
			mParent = new InvesDocEntry();
		}
		else
		{
			DepartmentDAO department = new DepartmentDAO(cnt);
			department.setKey(mParentId);
			department.load(cnt);
			mParent = new InvesDocEntry( department,cnt);
		}
	}

	protected void setGroups(DbCnt cnt)
	throws ISPACException
	{
		String where = "WHERE USERID = " + mUID;

		CollectionDAO collection = new CollectionDAO(UserGroupDAO.class);
		collection.query(cnt,where);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			IItem item = (UserGroupDAO) iterator.next(); 

			GroupDAO group = new GroupDAO(cnt);
			group.setKey(item.getInt("GROUPID"));
			group.load(cnt);
			
			mGroups.add( new InvesDocEntry( group,cnt));
		}
	}

	protected static Set searchGroups(DbCnt cnt,String nombre , String field)
	throws ISPACException
	{
		LinkedHashSet groups = new LinkedHashSet();
		
		CollectionDAO collection = new CollectionDAO(GroupDAO.class);
		
		String sqlQuery = "WHERE "+field +" LIKE '%" + DBUtil.replaceQuotes(nombre) + "%'";
		collection.query(cnt,sqlQuery);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			GroupDAO group = (GroupDAO) iterator.next(); 
			InvesDocEntry entry = new InvesDocEntry( group,cnt);
			groups.add( entry);
		}
		
		return groups;
	}
	
	protected static Set searchUsers(DbCnt cnt,String nombre , String field)
	throws ISPACException
	{
		LinkedHashSet users = new LinkedHashSet();
		
		CollectionDAO collection = new CollectionDAO(UserDAO.class);
		
		String sqlQuery = "WHERE "+field +" LIKE '%" + DBUtil.replaceQuotes(nombre) + "%'";
		collection.query(cnt,sqlQuery);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			UserDAO user = (UserDAO) iterator.next(); 
			InvesDocEntry entry = new InvesDocEntry( user,cnt);
			users.add( entry);
		}
		
		return users;
	}

	protected static Set searchDepartments(DbCnt cnt,String nombre , String field)
	throws ISPACException
	{
		LinkedHashSet dpts = new LinkedHashSet();
		
		CollectionDAO collection = new CollectionDAO(DepartmentDAO.class);
		
		String sqlQuery = "WHERE "+field +" LIKE '%" + DBUtil.replaceQuotes(nombre) + "%'";
		collection.query(cnt,sqlQuery);
		
		IItemCollection list = collection.disconnect();
		
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			DepartmentDAO dpto = (DepartmentDAO) iterator.next(); 
			InvesDocEntry entry = new InvesDocEntry( dpto,cnt);
			dpts.add( entry);
		}
		
		return dpts;
	}
}