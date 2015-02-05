/*
 * invesflow Java - ISPAC
 *
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/dao/TableDAO.java,v $
 * $Revision: 1.5 $
 * $Date: 2009/02/19 16:43:53 $
 * $Author: ildefong $
 */
package ieci.tdw.ispac.ispaclib.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;


/**
 *
 * Clase genérica para acceder a una tabla arbitraria en BBDD.
 *
 * @version $Revision: 1.5 $ $Date: 2009/02/19 16:43:53 $
 */
public class TableDAO extends ObjectDAO
{
	final private String mtablename;
	final private String midkey;
	final private String msequence;


	public TableDAO(DbCnt cnt, String[] tables, String fromSQL, Property[] tableColumns) throws ISPACException {
		super();
		setPosibleProcessEntity(true);
		mtablename=fromSQL;
		midkey="";
		msequence="";
		initTableDesc(cnt,tables,tableColumns);
	}

	public TableDAO(String table, Property[] tableColumns) throws ISPACException {
		this(table,"","",tableColumns);
	}
	public TableDAO(DbCnt cnt, String table, Property[] tableColumns) throws ISPACException {
		this(cnt, table,"","",tableColumns);
	}
	
	public TableDAO(String table, Timestamp timestamp, Property[] tableColumns)	throws ISPACException {
		this(table,"","",timestamp,tableColumns);
	}
	public TableDAO(DbCnt cnt, String table, Timestamp timestamp, Property[] tableColumns)	throws ISPACException {
		this(cnt,table,"","",timestamp,tableColumns);
	}

	public TableDAO(String table, String key, String sequence, Property[] tableColumns) throws ISPACException {
		super();
		setPosibleProcessEntity(true);
		mtablename=table;
		midkey=key;
		msequence=sequence;
		initTableDesc(null,mtablename,tableColumns,null);
	}
	public TableDAO(DbCnt cnt, String table, String key, String sequence, Property[] tableColumns) throws ISPACException {
		super();
		setPosibleProcessEntity(true);
		mtablename=table;
		midkey=key;
		msequence=sequence;
		initTableDesc(cnt,mtablename,tableColumns,null);
	}
	
	public TableDAO(String table, String key, String sequence, Timestamp timestamp, Property[] tableColumns) throws ISPACException
	{
		super();
		setPosibleProcessEntity(true);
		mtablename=table;
		midkey=key;
		msequence=sequence;
		initTableDesc(null,mtablename,tableColumns,timestamp);
	}
	public TableDAO(DbCnt cnt, String table, String key, String sequence, Timestamp timestamp, Property[] tableColumns) throws ISPACException {
		super();
		setPosibleProcessEntity(true);
		mtablename=table;
		midkey=key;
		msequence=sequence;
		initTableDesc(cnt,mtablename,tableColumns,timestamp);
	}

	public TableDAO(String table, String key, String sequence) throws ISPACException {
		super();
		setPosibleProcessEntity(true);
		mtablename=table;
		midkey=key;
		msequence=sequence;
	}
	public TableDAO(DbCnt cnt, String table, String key, String sequence) throws ISPACException {
		super();
		setPosibleProcessEntity(true);
		mtablename=table;
		midkey=key;
		msequence=sequence;
	}

	public static CollectionDAO newCollectionDAO(Class cls,String table,String key,String sequence, Property[] tableColumns)
	throws ISPACException
	{
		Object[] args=new Object[4];
		args[0]=table;
		args[1]=key;
		args[2]=sequence;
		args[3]=tableColumns;
		return new CollectionDAO(cls,args);
	}

	public static CollectionDAO newCollectionDAO(Class cls,String table, Property[] tableColumns)
	throws ISPACException
	{
		Object[] args=new Object[2];
		args[0]=table;
		args[1]=tableColumns;
		return new CollectionDAO(cls,args);
	}

	public static CollectionDAO newCollectionDAO(Class cls,String[] tables, String fromSQL, Property[] tableColumns)
	throws ISPACException
	{
		Object[] args=new Object[3];
		args[0]=tables;
		args[1]=fromSQL;
		args[2]=tableColumns;
		return new CollectionDAO(cls,args);
	}

	public String getTableName()
	{
		return mtablename;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + getKeyName() + " = " + getInt(getKeyName());
	}

	protected void newObject(DbCnt cnt) throws ISPACException
	{
		if (msequence!=null || !msequence.equals(""))
			set(getKeyName(),IdSequenceMgr.getIdSequence(cnt,msequence));
	}

	public String getKeyName() throws ISPACException
	{
		if (midkey==null || midkey.equals(""))
			throw new ISPACException("No se ha definido el acceso por defecto.");
		return midkey;
	}


	/** Crea un nuevo objeto del mismo tipo que contiene las mismas propiedades
	 * y valores excepto la propiedad clave.
	 * @param cnt
	 * @return Objeto duplicado pero con distinta clave.
	 * @throws ISPACException
	 */
	public ObjectDAO duplicate(DbCnt cnt) throws ISPACException
	{
	    Class classobjdao=this.getClass();
	    try
		{
	        Class argstypes[]={String.class,String.class,String.class};
	        Object argsvalues[]={mtablename,midkey,msequence};

	        Constructor constrdao = classobjdao.getConstructor(argstypes);
	        TableDAO obj=(TableDAO)constrdao.newInstance(argsvalues);

			obj.initTableDesc(mTableDesc);
			obj.createNew(cnt);
			obj.copy(this,false);
			return obj;
		}
		catch (Exception e)
		{
			throw new ISPACException("Error en TableDAO:duplicate() ", e);
		}
	}
	
}
