package ieci.tdw.ispac.ispaclib.dao.wl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Date;

public class DeadLineDAO extends ObjectDAO {

	public static int TYPE_ALL = 0;
	public static int TYPE_EXP = 1;
	public static int TYPE_STAGE = 2;
	public static int TYPE_TASK = 3;
	public static int TYPE_ACTIVITY = 3;

	static final String TABLENAME = "SPAC_DEADLINE";
	//static final String IDSEQUENCE
	//static final String IDKEY = "ID";
	
	/**
	 * @throws ISPACException
	 */
	public DeadLineDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public DeadLineDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE 1 = 1 ";
	}

	protected void newObject(DbCnt cnt) throws ISPACException
	{
		throw new ISPACException("No se tiene secuencia");
	}

	public String getKeyName() throws ISPACException
	{
		throw new ISPACException("No tiene clave");
	}

	/**
	 * 
	 * @param cnt
	 * @param type
	 * @param resp
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getTerms(DbCnt cnt, int type, String resp)
	throws ISPACException
	{
		return getTerms(cnt, type, null, null, resp);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param type
	 * @param resp
	 * @return
	 * @throws ISPACException
	 */
	public static int countTerms(DbCnt cnt, int type, String resp)
	throws ISPACException
	{
		
        int count = 0;

        // Fecha límite
        StringBuffer sql = new StringBuffer("WHERE FECHA_LIMITE <= " );
		sql.append(DBUtil.getToDateByBD(cnt, new Date()));
		
		// Tipo
		if (type != 0) {
			sql.append(" AND TIPO = ");
			sql.append(type);
		}
		
		// Añadir los responsables cuando no es el Supervisor
		sql.append(DBUtil.addAndInResponsibleCondition("ID_RESP", resp));
		
        CollectionDAO objlist = new CollectionDAO(DeadLineDAO.class);
		count = objlist.count(cnt, sql.toString());
		
		return count;
	}

	/**
	 * 
	 * @param cnt
	 * @param type
	 * @param initDate
	 * @param endDate
	 * @param resp
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getTerms(DbCnt cnt, int type, Date initDate, Date endDate, String resp)
	throws ISPACException
	{	
		StringBuffer sql = new StringBuffer("WHERE");
		
		// Añadir los responsables cuando no es el Supervisor	
		if (!resp.equals(Responsible.SUPERVISOR)) {
			sql.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));
		}
		
		// Fecha de inicio
		if (initDate != null) {
			if (sql.length() > 5) {
				sql.append(" AND");
			}
			sql.append(" FECHA_LIMITE >= ");
		    sql.append(DBUtil.getToDateByBD(cnt, initDate) );
		}
		
		// Fecha de fin
		if (endDate != null) {
			if (sql.length() > 5) {
				sql.append(" AND");
			}
		    sql.append(" FECHA_LIMITE <=  " );
		    sql.append(DBUtil.getToDateByBD(cnt, endDate) );
		}
		
		// Tipo
		if (type != 0) {
			if (sql.length() > 5) {
				sql.append(" AND");
			}
			sql.append(" TIPO = ");
			sql.append(type);
		}
		
		sql.append(" ORDER BY FECHA_LIMITE ASC");

		CollectionDAO objlist = new CollectionDAO(DeadLineDAO.class);
		objlist.query(cnt, sql.toString());
		
		return objlist;
	}
	
}