package ieci.tdw.ispac.ispaclib.dao.wl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.sql.Types;

public class WLBatchTaskExpsDAO extends ObjectDAO {

	private static final long serialVersionUID = 1L;
	
	static final String TABLENAME 	= "SPAC_TRAMITCS_AGRUPADAS_EXPS";
	static final String ID_KEY = "ID_TRAM_AGRUP";

	static final Property[] TABLECOLUMNS= 
	{
		new Property(0,"ID","ID",Types.NUMERIC),
		new Property(1,"ID_TRAM_AGRUP","ID_TRAM_AGRUP",Types.NUMERIC),
        new Property(2,"NUMEXP","NUMEXP",Types.VARCHAR),
        
	};
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public WLBatchTaskExpsDAO() throws ISPACException {
		super(TABLECOLUMNS);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public WLBatchTaskExpsDAO(DbCnt cnt) throws ISPACException	{
		super(cnt, TABLECOLUMNS);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public WLBatchTaskExpsDAO(DbCnt cnt, int id) throws ISPACException	{
		super(cnt, id, TABLECOLUMNS);
	}
	
	public String getTableName() {
		return TABLENAME;
	}
	
	protected void newObject(DbCnt cnt) throws ISPACException {
		throw new ISPACException("No se tiene secuencia");
	}
	
	/*
	 * Obtiene la colección de trámitaciones agrupadas según un responsable, agrupado por
	 * el identificador del trámite en el catálogo y el nombre.
	 * @param cnt clase DbCnt
	 * @param resp responsable a filtrar
	 * @return colección de trámites
	 */
	public static CollectionDAO getBatchTasksExps(DbCnt cnt, int idTramitacionAgrupada)
	throws ISPACException
	{
		String sql="WHERE ID_TRAM_AGRUP = " + idTramitacionAgrupada + " ";
		
		CollectionDAO objlist=new CollectionDAO(WLBatchTaskExpsDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + ID_KEY + " = " + getInt(ID_KEY);
	}

	public String getKeyName() throws ISPACException {
		return ID_KEY;
	}
	
	/**
	 * Elimina todos las relaciones de un expediente en todas las tramitaciones agrupadas que
	 * pudiera participar
	 * @param cnt
	 * @param numExp
	 * @throws ISPACException
	 */
	public static void deleteExpOfAllBatchTasks (DbCnt cnt , String numExp)throws ISPACException {
		
		String sql = "DELETE FROM " +TABLENAME + " WHERE NUMEXP = '" + numExp + "' ";		
		cnt.directExec(sql);

		
	}
	
	
}
