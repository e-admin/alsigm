package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class SustitucionDAO extends ObjectDAO {

	static final String TABLENAME = "SPAC_SS_SUSTITUCION";

	static final String IDSEQUENCE = "SPAC_SQ_ID_SSSUSTITUCION";

	static final String IDKEY = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public SustitucionDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public SustitucionDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SustitucionDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException {
		return IDKEY;
	}

	public void newObject(DbCnt cnt) throws ISPACException {
		set(IDKEY, IdSequenceMgr.getIdSequence(cnt, IDSEQUENCE));
	}
	
	static public CollectionDAO getSustituidos(DbCnt cnt, String sustitutoUID)
	throws ISPACException
	{
		String sql="WHERE UID_SUSTITUTO = '" + DBUtil.replaceQuotes(sustitutoUID) + "'";
		CollectionDAO objlist=new CollectionDAO(SustitucionDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	static public CollectionDAO getSustitutos(DbCnt cnt, String sustituidoUID)
	throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "SUSTITUCION");
		factory.addTable(SustitucionFechaDAO.TABLENAME, "SUSTITUCIONFECHA");
		factory.addTable(FechSustitucionesDAO.TABLENAME, "FECHSUSTITUCIONES");

		String sql = "WHERE SUSTITUCION.ID = SUSTITUCIONFECHA.ID_SUSTITUCION" +
					 " AND FECHSUSTITUCIONES.ID = SUSTITUCIONFECHA.ID_FECHSUSTITUCION" +
					 " AND SUSTITUCION.UID_SUSTITUIDO = '" + DBUtil.replaceQuotes(sustituidoUID) + "'" +
					 " ORDER BY FECHSUSTITUCIONES.FECHA_INICIO, FECHSUSTITUCIONES.FECHA_FIN, FECHSUSTITUCIONES.DESCRIPCION";
		
   		return factory.queryTableJoin(cnt, sql);
	}
	static public CollectionDAO getSustitutosAssets(DbCnt cnt, String sustituidoUID, String FechaActual)
	throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "SUSTITUCION");
		factory.addTable(SustitucionFechaDAO.TABLENAME, "SUSTITUCIONFECHA");
		factory.addTable(FechSustitucionesDAO.TABLENAME, "FECHSUSTITUCIONES");

		String sql = "WHERE SUSTITUCION.ID = SUSTITUCIONFECHA.ID_SUSTITUCION" +
					 " AND FECHSUSTITUCIONES.ID = SUSTITUCIONFECHA.ID_FECHSUSTITUCION" +
					 " AND SUSTITUCION.UID_SUSTITUIDO = '" + DBUtil.replaceQuotes(sustituidoUID) + "'" +
					 " AND FECHSUSTITUCIONES.FECHA_FIN >= " + FechaActual +
					 " ORDER BY FECHSUSTITUCIONES.FECHA_INICIO, FECHSUSTITUCIONES.FECHA_FIN, FECHSUSTITUCIONES.DESCRIPCION";
		
   		return factory.queryTableJoin(cnt, sql);
	}
	
	static public CollectionDAO getSustitutosHistorics(DbCnt cnt, String sustituidoUID, String FechaActual)
	throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "SUSTITUCION");
		factory.addTable(SustitucionFechaDAO.TABLENAME, "SUSTITUCIONFECHA");
		factory.addTable(FechSustitucionesDAO.TABLENAME, "FECHSUSTITUCIONES");

		String sql = "WHERE SUSTITUCION.ID = SUSTITUCIONFECHA.ID_SUSTITUCION" +
					 " AND FECHSUSTITUCIONES.ID = SUSTITUCIONFECHA.ID_FECHSUSTITUCION" +
					 " AND SUSTITUCION.UID_SUSTITUIDO = '" + DBUtil.replaceQuotes(sustituidoUID) + "'" +
					 " AND FECHSUSTITUCIONES.FECHA_FIN < " + FechaActual +
					 " ORDER BY FECHSUSTITUCIONES.FECHA_INICIO, FECHSUSTITUCIONES.FECHA_FIN, FECHSUSTITUCIONES.DESCRIPCION";
		
   		return factory.queryTableJoin(cnt, sql);
	}
	
	static public CollectionDAO getSustitutos(DbCnt cnt, int idFechSustitucion)
	throws ISPACException
	{
		String sql = "SELECT ID_SUSTITUCION FROM " + SustitucionFechaDAO.TABLENAME
				   + " WHERE ID_FECHSUSTITUCION = " + idFechSustitucion;

		sql = "WHERE ID IN (" + sql + ")";

		CollectionDAO objlist = new CollectionDAO(SustitucionDAO.class);
		objlist.query(cnt,sql);

		return objlist;
	}

	static public boolean isSustituido(DbCnt cnt, String sustitutoUID, String sustituidoUID) 
	throws ISPACException
	{
		String sql = "WHERE UID_SUSTITUIDO = '" + DBUtil.replaceQuotes(sustituidoUID) 
				   + "' AND UID_SUSTITUTO = '" + DBUtil.replaceQuotes(sustitutoUID) + "'";
		
		CollectionDAO objlist = new CollectionDAO(SustitucionDAO.class);
		return objlist.count(cnt, sql) > 0;
	}
	
	static public SustitucionDAO getSustitucion(DbCnt cnt, String sustitutoUID, String sustituidoUID) 
	throws ISPACException
	{
		String sql = "WHERE UID_SUSTITUIDO = '" + DBUtil.replaceQuotes(sustituidoUID) 
				   + "' AND UID_SUSTITUTO = '" + DBUtil.replaceQuotes(sustitutoUID) + "'";
		
		CollectionDAO objlist = new CollectionDAO(SustitucionDAO.class);
		objlist.query(cnt,sql);
		
		if (objlist.next()) {
			return (SustitucionDAO) objlist.value();
		}
		
		return null;
	}
	
	static public CollectionDAO getSubstitutes(DbCnt cnt, String sustitutoUID)
	throws ISPACException
	{
		return getSubstitutes(cnt, new String[]{sustitutoUID});
	}
	

	
	static public CollectionDAO getSubstitutes(DbCnt cnt, String[] sustitutoUID)
	throws ISPACException
	{	
		Date today = DateUtil.getToday();
		String sTodayByBD = DBUtil.getToDateByBD(cnt, today);
		
		StringBuffer sustitutos = new StringBuffer();
		for (int i = 0; i < sustitutoUID.length; i++) {
			
			if (i != 0) {
				sustitutos.append(", ");
			}
			sustitutos.append("'" + DBUtil.replaceQuotes(sustitutoUID[i]) + "'");
		}
				
		String sql = "SELECT SUSTITUCION.ID FROM " + TABLENAME + " SUSTITUCION, " 
												   + SustitucionFechaDAO.TABLENAME + " SUSTITUCIONFECHA, "
												   + FechSustitucionesDAO.TABLENAME + " FECHSUSTITUCIONES"										
				   + " WHERE SUSTITUCION.ID = SUSTITUCIONFECHA.ID_SUSTITUCION"
				   + " AND FECHSUSTITUCIONES.ID = SUSTITUCIONFECHA.ID_FECHSUSTITUCION"
				   + " AND " + DBUtil.addInResponsibleCondition("SUSTITUCION.UID_SUSTITUTO", sustitutos.toString())
				   + " AND FECHSUSTITUCIONES.FECHA_INICIO <= " + sTodayByBD
				   + " AND FECHSUSTITUCIONES.FECHA_FIN >= " + sTodayByBD;
		
		sql = "WHERE ID IN (" + sql + ")";
		
		CollectionDAO objlist = new CollectionDAO(SustitucionDAO.class);
		objlist.query(cnt,sql);
		
		return objlist;
	}
	
	static public CollectionDAO getSubstitutes(DbCnt cnt, int idFechSustitucion)
	throws ISPACException
	{					
		String sql = "SELECT SUSTITUCION.ID FROM " + TABLENAME + " SUSTITUCION, " 
												   + SustitucionFechaDAO.TABLENAME + " SUSTITUCIONFECHA"								
				   + " WHERE SUSTITUCION.ID = SUSTITUCIONFECHA.ID_SUSTITUCION"
				   + " AND SUSTITUCIONFECHA.ID_FECHSUSTITUCION = " + idFechSustitucion;
		
		sql = "WHERE ID IN (" + sql + ")";
		
		CollectionDAO objlist = new CollectionDAO(SustitucionDAO.class);
		objlist.query(cnt,sql);
		
		return objlist;
	}

	static public CollectionDAO getSubstitutes(DbCnt cnt, Map sustitutosUIDs)
	throws ISPACException
	{	
		String[] sustitutos = new String[sustitutosUIDs.size()]; 
		
		Iterator it = sustitutosUIDs.entrySet().iterator();
		for (int i = 0; it.hasNext(); i++) {
			Map.Entry entry = (Map.Entry)it.next();
			sustitutos[i]= (String)entry.getKey();
		}
		return getSubstitutes(cnt, sustitutos);
	}
	
}