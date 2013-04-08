/*
 * Created on 17-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.wl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.sql.Types;

/**
 * @author marisa
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class WLProcedureDAO extends ObjectDAO
{

	static final String TABLENAME = "SPAC_WL_PCD";
	//static final String IDSEQUENCE
	static final String IDKEY = "ID";
	static final Property[] TABLECOLUMNS= 
	{
        new Property(0,"ID",Types.NUMERIC),
        new Property(1,"NAME",Types.VARCHAR),
        new Property(1,"NVERSION",Types.NUMERIC)
	};

	/**
	 * 
	 * @throws ISPACException
	 */
	public WLProcedureDAO() throws ISPACException {
		super(TABLECOLUMNS);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public WLProcedureDAO(DbCnt cnt) throws ISPACException {
	    super(cnt, TABLECOLUMNS);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public WLProcedureDAO(DbCnt cnt, int id) throws ISPACException {
	    super(cnt, id, TABLECOLUMNS);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{

		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	protected void newObject(DbCnt cnt) throws ISPACException
	{
		throw new ISPACException("No se tiene secuencia");
	}

	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}

	/**
	 * método que devuelve una colección de procedimientos
	 * en función de la responsabilidad y el tipo
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable
	 * @param type tipo del procedimiento 
	 * @return colección de procedimientos
	 */
	private static CollectionDAO getProcs(DbCnt cnt, String resp, int type)
	throws ISPACException
	{

		String sql = "WHERE TIPO = " + type;
		if(type==IPcdElement.TYPE_PROCEDURE){//No hay responsabilidad a nivel de subproceso
	   	   		   // Añadir los responsables cuando no es el Supervisor
	   	   	sql+=	 DBUtil.addAndInResponsibleCondition("RESP", resp);
		}
	   	sql+= " ORDER BY NAME";
		
		CollectionDAO objlist = new CollectionDAO(WLProcedureDAO.class);
		objlist.queryDistinct(cnt, sql);
		
		return objlist;
	}

	/**
	 * 
	 * @param cnt
	 * @param resp
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getProcs(DbCnt cnt, String resp)
	throws ISPACException
	{
		return getProcs(cnt, resp, IProcess.PROCESS_TYPE);
	}
	
	
	public static CollectionDAO getSubProcs(DbCnt cnt, String resp)
	throws ISPACException
	{
		return getProcs(cnt, resp, IProcess.SUBPROCESS_TYPE);
	}	
	
}
