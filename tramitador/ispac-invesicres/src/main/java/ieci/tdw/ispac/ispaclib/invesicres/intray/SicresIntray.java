package ieci.tdw.ispac.ispaclib.invesicres.intray;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;
import ieci.tdw.ispac.ispaclib.invesicres.intray.dao.DistributionIntrayDAO;
import ieci.tdw.ispac.ispaclib.invesicres.intray.dao.GroupLDAPDAO;
import ieci.tdw.ispac.ispaclib.invesicres.intray.dao.StateIntrayDAO;
import ieci.tdw.ispac.ispaclib.invesicres.intray.dao.UserLDAPDAO;
import ieci.tdw.ispac.ispaclib.resp.Group;
import ieci.tdw.ispac.ispaclib.resp.OrgUnit;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.resp.User;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.worklist.WLCodeTableDef;
import ieci.tdw.ispac.ispaclib.worklist.WLSubstituteDef;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.idoc.api.Archive;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.api.Folder;
import ieci.tecdoc.sbo.idoc.api.FolderDocumentObject;
import ieci.tecdoc.sbo.idoc.api.FolderDocumentObjects;
import ieci.tecdoc.sbo.idoc.api.FolderObject;

import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class SicresIntray {

	public static final int PENDIENTE = 1;
	public static final int ACEPTADO = 2;
	public static final int ARCHIVADO = 3;
	public static final int DEVUELTO = 4;

	public static final int PERSON = 1;
	public static final int UNIT = 2;
	public static final int GROUP = 3;

    /**
     * Logger de la clase.
     */
    private static Logger logger = Logger.getLogger(SicresIntray.class);

	private String msPoolName = null;
    private String msUserManager = null;
    private int  mArchive = 0;
    private ClientContext mctx = null;



    /**
     * Constructor.
     * @param ctx Contexto del cliente.
     * @throws ISPACException si ocurre algún error.
     */
    public SicresIntray(ClientContext ctx) throws ISPACException {

		InveSicresConfiguration parameters = InveSicresConfiguration.getInstance();

		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			String pattern = parameters.get(InveSicresConfiguration.POOLNAME_PATTERN);
			msPoolName = info.getSicresPoolName(pattern);
		} else {
			msPoolName = parameters.get(InveSicresConfiguration.POOL_NAME);
		}

		if (!disabled()) {

			msUserManager = parameters.get(InveSicresConfiguration.USER_MANAGER);
			if ( !"INVESDOC".equalsIgnoreCase(msUserManager)
					&& !"LDAP".equalsIgnoreCase(msUserManager)) {
				throw new ISPACException("SICRES: La gestión de usuarios tiene que ser INVESDOC o LDAP.");
			}

			String sArchive = parameters.get(InveSicresConfiguration.ENT_ID_ARCH);
			if (sArchive == null) {
				throw new ISPACException("SICRES: No se ha indicado archivador.");
			}

			mArchive = Integer.parseInt( sArchive);
			mctx = ctx;
		}
	}

    protected boolean disabled() {
        return (msPoolName == null || msPoolName.length() == 0);
    }

	/**
	 * Obtiene el número de registros distribuidos asociados al usuario conectado.
	 * @return Número de registros distribuidos.
	 * @throws ISPACException si ocurre algún error.
	 */
 	public int countIntrais() throws ISPACException {

		if (disabled()) {
			return 0;
		}

		DbCnt cnt = new DbCnt(msPoolName);

		try {

			cnt.getConnection();

			// Filtro por responsable
			String sQueryResponsible = getQueryResponsible(cnt, null);

			String sQuery = "WHERE ID_ARCH = "
						  + Integer.toString( mArchive)
					      + " AND (" + sQueryResponsible + ")"
						  + " AND (STATE = 1 OR STATE = 2)";

			CollectionDAO collection = new CollectionDAO(DistributionIntrayDAO.class);
			return collection.count(cnt, sQuery);

		} finally {
			cnt.closeConnection();
		}
	}

	/**
	 * Obtiene la lista de registros distribuidos asociados al usuario conectado.
	 * @return Lista de registros distribuidos ({@link Intray}).
	 * @throws ISPACException si ocurre algún error.
	 */
 	public List getIntrays() throws ISPACException {

 		List intrays = new ArrayList();

		DbCnt cnt = new DbCnt(msPoolName);

		try {

			cnt.getConnection();

/*
SELECT
	ISSUE_ARCHIVE.MATTER,OFFICE_ARCHIVE.NAME,
	REGISTER.ID,REGISTER.STATE,REGISTER.STATE_DATE,REGISTER.DIST_DATE,REGISTER.MESSAGE,
	ARCHIVE.FLD1,ARCHIVE.FLD9
FROM
	A1SF ARCHIVE
		LEFT OUTER JOIN SCR_CA ISSUE_ARCHIVE ON ( ARCHIVE.FLD16 = ISSUE_ARCHIVE.ID )
		LEFT OUTER JOIN SCR_OFIC OFFICE_ARCHIVE ON ( ARCHIVE.FLD5 = OFFICE_ARCHIVE.ID ),
	SCR_DISTREG REGISTER
WHERE
	REGISTER.ID_ARCH = 1
	AND (REGISTER.STATE = 1 OR REGISTER.STATE = 2)
	AND (REGISTER.TYPE_DEST = 1 AND REGISTER.ID_DEST = 1
			OR REGISTER.TYPE_DEST = 2 AND REGISTER.ID_DEST = 1
			OR REGISTER.TYPE_DEST = 3 AND REGISTER.ID_DEST IN (2,3,4,6,7,5,12))
	AND (REGISTER.ID_FDR = ARCHIVE.FDRID)
*/

			StringBuffer sql = new StringBuffer("WHERE")
				.append(" REGISTER.ID_ARCH = ").append(mArchive)
				.append(" AND (REGISTER.STATE = 1 OR REGISTER.STATE = 2)")
				.append(" AND (").append(getQueryResponsible(cnt, "REGISTER")).append(")")
				.append(" AND REGISTER.ID_FDR = ARCHIVE.FDRID");

			TableJoinFactoryDAO factory = getIntraysTableJoinFactoryDAO(cnt);
	   		IItemCollection collection = factory.queryTableJoin(cnt, sql.toString()).disconnect();
			while (collection.next()) {
				Intray intray = createIntray(cnt, collection.value());
				if (intray != null) {
					intrays.add(intray);
				}
			}

		} finally {
			cnt.closeConnection();
		}

		return intrays;
	}

	/**
	 * Obtiene la información del registro distribuido.
	 * @param register Número de registro.
	 * @return Información del registro distribuido.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Intray getIntray(int register) throws ISPACException {

		Intray intray = null;

		DbCnt cnt = new DbCnt(msPoolName);

		try {

			cnt.getConnection();

/*
SELECT
	ISSUE_ARCHIVE.MATTER,OFFICE_ARCHIVE.NAME,
	REGISTER.ID,REGISTER.STATE,REGISTER.STATE_DATE,REGISTER.DIST_DATE,REGISTER.MESSAGE,
	ARCHIVE.FLD1,ARCHIVE.FLD9
FROM
	A1SF ARCHIVE
		LEFT OUTER JOIN SCR_CA ISSUE_ARCHIVE ON ( ARCHIVE.FLD16 = ISSUE_ARCHIVE.ID )
		LEFT OUTER JOIN SCR_OFIC OFFICE_ARCHIVE ON ( ARCHIVE.FLD5 = OFFICE_ARCHIVE.ID ),
	SCR_DISTREG REGISTER
WHERE
	REGISTER.ID = 339
	AND (REGISTER.ID_FDR = ARCHIVE.FDRID)
*/

			StringBuffer sql = new StringBuffer("WHERE")
				.append(" REGISTER.ID = ").append(register)
				.append(" AND REGISTER.ID_FDR = ARCHIVE.FDRID");

			TableJoinFactoryDAO factory = getIntraysTableJoinFactoryDAO(cnt);
	   		IItemCollection collection = factory.queryTableJoin(cnt, sql.toString()).disconnect();
			if (collection.next()) {
				intray = createIntray(cnt, collection.value());
			}

		} finally {
			cnt.closeConnection();
		}

		return intray;
	}

	protected TableJoinFactoryDAO getIntraysTableJoinFactoryDAO(DbCnt cnt) throws ISPACException {

		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();

		// Deshabilidar la llamada a getCatalogEntityDAO en el createTableJoin
       // ya que la consulta se ejecuta contra la BD de ISICRES
		factory.setDisabledGetCatalogEntitiesInCreateTableJoin(Boolean.TRUE);

		// Añadir la tabla de registros distribuidos
		factory.addTable("SCR_DISTREG", "REGISTER", new Property[] {
				new Property(1, "ID"), // identificador del registro distribuido
				new Property(2, "STATE"), // estado del registro distribuido
				new Property(3, "STATE_DATE"), // fecha del estado del registro distribuido
				new Property(4, "DIST_DATE"), // fecha de distribución
				new Property(5, "MESSAGE"), // mensaje de distribución
				new Property(6, "TYPE_ORIG"), // tipo de origen
				new Property(7, "ID_ORIG"), // id de origen
				new Property(8, "TYPE_DEST"), // tipo de destino
				new Property(9, "ID_DEST") // id de destino
		});

		// Añadir la tabla de registros distribuidos
		factory.addTable("A" + Integer.toString( mArchive) + "SF", "ARCHIVE", new Property[] {
				new Property(1, "FLD1"), // número de registro
				new Property(1, "FLD2"), // fecha de registro
				new Property(2, "FLD9"), // remitente del registro
				new Property(2, "FLD17") // resumen del registro
		});


		WLSubstituteDef sustituteDef = new WLSubstituteDef("ARCHIVE", "FLD16",
				new WLCodeTableDef("ISSUE", "ID", "SCR_CA", "MATTER"));
		sustituteDef.addJoin(cnt, factory);

		sustituteDef = new WLSubstituteDef("ARCHIVE", "FLD5",
				new WLCodeTableDef("OFFICE", "ID", "SCR_OFIC", "NAME"));
		sustituteDef.addJoin(cnt, factory);

		sustituteDef = new WLSubstituteDef("ARCHIVE", "FLD7",
				new WLCodeTableDef("SENDER", "ID", "SCR_ORGS", "NAME"));
		sustituteDef.addJoin(cnt, factory);

		sustituteDef = new WLSubstituteDef("ARCHIVE", "FLD8",
				new WLCodeTableDef("DESTINATION", "ID", "SCR_ORGS", "NAME"));
		sustituteDef.addJoin(cnt, factory);

		return factory;
	}

	protected Intray createIntray(DbCnt cnt, IItem item) throws ISPACException {

		Intray intray = null;

		if (item != null) {
			intray = new Intray();
			intray.setId(item.getString("REGISTER:ID"));
			intray.setRegisterNumber(item.getString("ARCHIVE:FLD1"));
			intray.setRegisterDate(item.getDate("ARCHIVE:FLD2"));
			intray.setState(item.getInt("REGISTER:STATE"));
			intray.setStateDate(item.getDate("REGISTER:STATE_DATE"));
			intray.setDate(item.getDate("REGISTER:DIST_DATE"));
			intray.setMessage(item.getString("REGISTER:MESSAGE"));
			intray.setMatter(item.getString("ARCHIVE:FLD17"));
			intray.setMatterTypeName(item.getString("ISSUE:ARCHIVE:MATTER"));
			intray.setSender(getRespName(cnt, item.getInt("REGISTER:TYPE_ORIG"), item.getInt("REGISTER:ID_ORIG")));
			intray.setDestination(getRespName(cnt, item.getInt("REGISTER:TYPE_DEST"), item.getInt("REGISTER:ID_DEST")));
			intray.setRegisterOffice(item.getString("OFFICE:ARCHIVE:NAME"));
			intray.setRegisterOrigin(item.getString("SENDER:ARCHIVE:NAME"));
			intray.setRegisterSender(new ThirdPerson[] { new ThirdPerson(null, item.getString("ARCHIVE:FLD9")) });
			intray.setRegisterDestination(item.getString("DESTINATION:ARCHIVE:NAME"));
			intray.setRegisterAddressee(null);
		}

		return intray;
	}

	protected String getRespName(DbCnt cnt, int obtType, int objId) throws ISPACException {
		String name = null;

		if (cnt != null) {

			String sql = "SELECT NAME FROM ";
			switch (obtType) {
				case 1:
					sql += "IUSERUSERHDR";
					break;
				case 2:
					sql += "IUSERDEPTHDR";
					break;
				case 3:
					sql += "IUSERGROUPHDR";
					break;
			}
			sql += " WHERE ID=" + objId;

			DbQuery result = null;

			try {
				result = cnt.executeDbQuery(sql);
				if (result.next()) {
					name = result.getString("NAME");
				}
			} finally {
				if (result != null) {
					result.close();
				}
			}
		}

		return name;
	}

// 	/* (non-Javadoc)
//	 * @see ieci.tdw.ispac.ispaclib.invesicres.intray.ISicresIntray#addToProccess(int, int, int)
//	 */
//	public void addToProccess( int register, int process, int type)
//	throws ISPACException
//	{
//		DbCnt cnt = new DbCnt(msPoolName);
//
//		cnt.getConnection();
//
//		try
//		{
//			ProcessIntrayDAO object = new ProcessIntrayDAO( cnt);
//			object.createNew( cnt);
//			object.set( "ID_DIST", register);
//			object.set( "ID_PROC", process);
//			object.set( "TYPE", type);
//			object.store( cnt);
//		}
//		finally
//		{
//			cnt.closeConnection();
//		}
//	}

//	/* (non-Javadoc)
//	 * @see ieci.tdw.ispac.ispaclib.invesicres.intray.ISicresIntray#distribute(int, ieci.tdw.ispac.ispaclib.resp.Responsible, java.lang.String, boolean)
//	 */
//	public void distribute( int register, Responsible responsible, String message, boolean archive)
//	throws ISPACException
//	{
//		DbCnt cnt = new DbCnt(msPoolName);
//		boolean commit = false;
//
//		cnt.getConnection();
//		cnt.openTX();
//		try
//		{
//			Date date = new Date(System.currentTimeMillis());
//
//			User user = (User) mctx.getUser();
//
//			DistributionIntrayDAO distribution = new DistributionIntrayDAO( cnt, register);
//
//	        if (distribution.getInt("STATE") == IInboxAPI.ARCHIVADO)
//	        {
//	        	throw new ISPACException("El registro ya está archivado");
//	        }
//
//	        if (archive)
//	        {
//				distribution.set( "STATE", IInboxAPI.ARCHIVADO);
//				distribution.set( "STATE_DATE", date);
//				distribution.store( cnt);
//
//				setState( cnt, register, IInboxAPI.ARCHIVADO);
//	        }
//
//			int folder = distribution.getInt( "ID_FDR");
//
//	        distribution.createNew( cnt);
//
//			distribution.set( "ID_ARCH", mArchive);
//			distribution.set( "ID_FDR", folder);
//			distribution.set( "DIST_DATE", date);
//			distribution.set( "TYPE_ORIG", getResponsibleType( user));
//			distribution.set( "ID_ORIG", getResponsibleId( cnt, user));
//			distribution.set( "TYPE_DEST", getResponsibleType( responsible));
//			distribution.set( "ID_DEST", getResponsibleId( cnt, responsible));
//			distribution.set( "STATE", IInboxAPI.PENDIENTE);
//			distribution.set( "STATE_DATE", date);
//			distribution.set( "MESSAGE", message);
//			distribution.store( cnt);
//
//			setState( cnt, distribution.getKeyInt(), IInboxAPI.PENDIENTE);
//
//			commit = true;
//		}
//		finally
//		{
//			cnt.closeTX( commit);
//			cnt.closeConnection();
//		}
//	}

	/**
	 * Acepta un registro distribuido.
	 * @param register Número de registro.
    * @throws ISPACException si ocurre algún error.
	 */
	public void acceptIntray(int register) throws ISPACException {
		changeState(register, ACEPTADO, null);
	}

	/**
	 * Rechaza un registro distribuido.
	 * @param register Número de registro.
	 * @param reason Motivo del rechazo.
    * @throws ISPACException si ocurre algún error.
	 */
	public void rejectIntray(int register, String reason) throws ISPACException {
		changeState(register, DEVUELTO, reason);
	}

	/**
	 * Archiva un registro distribuido.
	 * @param register Número de registro.
    * @throws ISPACException si ocurre algún error.
	 */
	public void archiveIntray(int register) throws ISPACException {
		changeState(register, ARCHIVADO, null);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.invesicres.intray.ISicresIntray#changeState(int, int)
	 */
	public void changeState( int register, int state, String remarks)
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);
		boolean commit = false;

		cnt.getConnection();
		cnt.openTX();
		try
		{
			Date date = new Date(System.currentTimeMillis());

			DistributionIntrayDAO distribution = new DistributionIntrayDAO( cnt, register);

	        if (distribution.getInt("STATE") == ARCHIVADO)
	        {
	        	throw new ISPACException("El registro ya está archivado");
	        }

	        if (distribution.getInt( "STATE") != state)
	        {
				distribution.set( "STATE", state);
				distribution.set( "STATE_DATE", date);

				if ((remarks != null) && (remarks.trim().length() > 0)) {
					distribution.set("MESSAGE", remarks);
				}

				distribution.store( cnt);

				setState(cnt, register, state);
	        }

			commit = true;
		}
		finally
		{
			cnt.closeTX( commit);
			cnt.closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.invesicres.intray.ISicresIntray#getAnnexes(int)
	 */
	public Annexe[] getAnnexes( int register)
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);

		cnt.getConnection();
		try
		{
			DistributionIntrayDAO distribution = new DistributionIntrayDAO( cnt, register);

			int archiveId = distribution.getInt( "ID_ARCH");
			int folderId = distribution.getInt( "ID_FDR");

			DbConnectionConfig config = new DbConnectionConfig( msPoolName, null, null);
			Archive archiveAPI = new Archive();
			archiveAPI.setConnectionConfig(config);
			Folder folderAPI = new Folder();
			folderAPI.setConnectionConfig(config);

			ArchiveObject archive = archiveAPI.loadArchive(null, archiveId);

			User user = (User) mctx.getUser();

			int userId = getResponsibleId( cnt, user);

			FolderObject folder = folderAPI.loadFolder(null, userId, archive, folderId);

			FolderDocumentObjects documents = folder.getAllDocuments();

			Annexe [] annexes = new Annexe[documents.count()];

			for (int i = 0; i < documents.count(); i++)
			{
				FolderDocumentObject document = documents.get(i);
				annexes[i] = new Annexe(String.valueOf(document.getId()), document.getName(), document.getFileExt());
			}

			return annexes;
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.invesicres.intray.ISicresIntray#getAnnexe(int, int, java.io.OutputStream)
	 */
	public void getAnnexe( int register, int annexe, OutputStream out)
	throws ISPACException
	{
		DbCnt cnt = new DbCnt(msPoolName);

		cnt.getConnection();
		try
		{
			DistributionIntrayDAO distribution = new DistributionIntrayDAO( cnt, register);

			int archiveId = distribution.getInt( "ID_ARCH");
			int folderId = distribution.getInt( "ID_FDR");

			DbConnectionConfig config = new DbConnectionConfig( msPoolName, null, null);
			Archive archiveAPI = new Archive();
			archiveAPI.setConnectionConfig(config);
			Folder folderAPI = new Folder();
			folderAPI.setConnectionConfig(config);

			ArchiveObject archive = archiveAPI.loadArchive(null, archiveId);

			User user = (User) mctx.getUser();

			int userId = getResponsibleId( cnt, user);

			FolderObject folder = folderAPI.loadFolder(null, userId, archive, folderId);

		    byte[] bytes = folderAPI.retrieveFolderDocumentFile(null, archive, folder, annexe);

		    out.write( bytes);

		    out.flush();
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			cnt.closeConnection();
		}
	}

	protected void setState( DbCnt cnt, int register, int state)
	throws ISPACException
	{
		Date date = new Date(System.currentTimeMillis());

		StateIntrayDAO stateIntray = new StateIntrayDAO(cnt);

		stateIntray.createNew( cnt);
		stateIntray.set( "ID_DIST", register);
		stateIntray.set( "STATE", state);
		stateIntray.set( "STATE_DATE", date);
		stateIntray.set( "USERNAME", mctx.getUser().getName());
		stateIntray.store( cnt);
	}

	protected String getQueryResponsible(DbCnt cnt, String prefix) throws ISPACException {

		StringBuffer responsible = new StringBuffer();

		// Filtro por usuario
		responsible.append(getQueryUser(cnt, prefix));

		// Filtro por departamentos
		String units = getQueryUnit(cnt, prefix);
		if (units != null) {
			responsible.append(" OR ").append(units);
		}

		// Filtro por grupos
		String groups = getQueryGroups(cnt, prefix);
		if (groups != null) {
			responsible.append(" OR ").append(groups);
		}

		return responsible.toString();
	}

	protected String getQueryUser( DbCnt cnt, String sPrefix)
			throws ISPACException {

		String sQuery = null;

		User user = (User) mctx.getUser();

		if (sPrefix == null) {
			sQuery = "TYPE_DEST = 1 AND ID_DEST = ";
		} else {
			sQuery = sPrefix + ".TYPE_DEST = 1 AND " + sPrefix + ".ID_DEST = ";
		}

		sQuery += Integer.toString( getResponsibleId( cnt, user));

		return sQuery;
	}

	protected String getQueryUnit( DbCnt cnt, String sPrefix)
			throws ISPACException {

		String sQuery = null;

		if (msUserManager.equalsIgnoreCase("INVESDOC")) {

			User user = (User) mctx.getUser();

			OrgUnit unit = (OrgUnit) user.getOrgUnit();

			if (sPrefix == null)
				sQuery = "TYPE_DEST = 2 AND ID_DEST = ";
			else
				sQuery = sPrefix + ".TYPE_DEST = 2 AND " + sPrefix + ".ID_DEST = ";

			sQuery += Integer.toString( getResponsibleId( cnt, unit));
		}

		return sQuery;
	}

	protected String getQueryGroups( DbCnt cnt, String sPrefix)
			throws ISPACException {

		String sQuery = null;

		User user = (User) mctx.getUser();

		Collection collection = user.getUserGroups();

		Iterator iterator = collection.iterator();

		if (iterator.hasNext()) {

			Group group = (Group) iterator.next();

			if (sPrefix == null)
				sQuery = "TYPE_DEST = 3 AND ID_DEST IN (";
			else
				sQuery = sPrefix + ".TYPE_DEST = 3 AND " + sPrefix + ".ID_DEST IN (";

			sQuery += Integer.toString( getResponsibleId( cnt, group));

			while (iterator.hasNext()) {
				group = (Group) iterator.next();
				sQuery += "," + Integer.toString( getResponsibleId( cnt, group));
			}
			sQuery += ")";
		}

		return sQuery;
	}

	protected int getResponsibleType( Responsible responsible)
	{
		String sUID = responsible.getUID();

		if (msUserManager.equalsIgnoreCase("INVESDOC"))
		{
		    StringTokenizer tokens = new StringTokenizer( sUID, "-");

			if (tokens.hasMoreTokens())
		    {
		        return Integer.parseInt(tokens.nextToken());
		    }
		}
		else
		{
			if (responsible instanceof  User)
				return IDirectoryEntry.ET_PERSON;
			else if (responsible instanceof  Group)
				return IDirectoryEntry.ET_GROUP;
			else if (responsible instanceof  OrgUnit)
				return IDirectoryEntry.ET_UNIT;
		}

	    return 0;
	}

	protected int getResponsibleId( DbCnt cnt, Responsible responsible)
	throws ISPACException
	{
		String sUID = responsible.getUID();

		if (msUserManager.equalsIgnoreCase("INVESDOC"))
		{
		    StringTokenizer tokens = new StringTokenizer( sUID, "-");

			if (tokens.hasMoreTokens())
		    {
			    if (tokens.hasMoreTokens())
			    {
			    	tokens.nextToken();

			    	if (tokens.hasMoreTokens())
			    	{
			    		return Integer.parseInt(tokens.nextToken());
			    	}
			    }
		    }
		}
		// Si el usuario es LDAP existe una tablas invesdoc
		// (IUSERLDAPUSERHDR) que mantiene la correspondencia
		// entre el GUID de LDAP y el identificador del
		// usuario, grupo de invesdoc
		else if (msUserManager.equalsIgnoreCase("LDAP"))
		{
			CollectionDAO collection;
			IItemCollection list;
			Iterator iterator;

			if (responsible instanceof  User)
			{
				String where = "WHERE LDAPGUID = '"
					  		 + DBUtil.replaceQuotes(sUID)
							 + "'";

				collection = new CollectionDAO(UserLDAPDAO.class);
				collection.query(cnt,where);
				list = collection.disconnect();

				iterator = list.iterator();

				if (!iterator.hasNext())
				{
					logger.info("No existe el usuario LDAP: " + responsible.getName());
					return 0;
				}
			}
			else
			{
				String where = "WHERE LDAPGUID = '"
					  		 + DBUtil.replaceQuotes(sUID)
							 + "'";

				collection = new CollectionDAO(GroupLDAPDAO.class);
				collection.query(cnt,where);
				list = collection.disconnect();

				iterator = list.iterator();

				if (!iterator.hasNext())
				{
					logger.info("No existe el usuario LDAP: " + responsible.getName());
					return 0;
				}
			}

			return ((ObjectDAO) iterator.next()).getKeyInt();
		}

	    return 0;
	}

}
