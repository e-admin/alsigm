package ieci.tdw.ispac.ispaclib.dao.wl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.sql.Types;
import java.util.List;

public class WLBatchTaskDAO  extends ObjectDAO {
	
	static final String TABLENAME 	= "SPAC_WL_BATCHTASK";
	//static final String IDSEQUENCE
	public static final String ID_AGRUPACION = "ID_AGRUPACION";
	public static final String ID_FASE = "ID_FASE";
	public static final String ID_RESP = "ID_RESP";
	public static final String ESTADO = "ESTADO";
	public static final String FECHA_CREACION  = "FECHA_CREACION";
	public static final String FASE  = "FASE";
	public static final String COD_PCD = "COD_PCD";
	public static final String PROCEDIMIENTO = "PROCEDIMIENTO";
	public static final String ID_ULTIMO_TRAMITE = "ID_ULTIMO_TRAMITE";
	public static final String ID_ULTIMO_TIPODOC = "ID_ULTIMO_TIPODOC";
	public static final String ID_ULTIMO_TEMPLATE = "ID_ULTIMO_TEMPLATE";
	
//	static final Property[] TABLECOLUMNS_FOR_COUNT= 
//	{
//        new Property(0,"COUNT","COUNT(*)",Types.NUMERIC)
//	};

	static final Property[] TABLECOLUMNS= 
	{

		 
        new Property(0,ID_AGRUPACION,ID_AGRUPACION,Types.NUMERIC),
        new Property(1,ID_RESP,ID_RESP,Types.VARCHAR),
        new Property(2,ESTADO,ESTADO,Types.NUMERIC),
        new Property(3,FECHA_CREACION,FECHA_CREACION,Types.DATE),
        new Property(4,FASE,FASE,Types.LONGVARCHAR),
        new Property(5,COD_PCD,Types.LONGVARCHAR),
        new Property(6,PROCEDIMIENTO,PROCEDIMIENTO,Types.LONGVARCHAR),
        new Property(7,ID_FASE,Types.NUMERIC),
        new Property(8,ID_ULTIMO_TRAMITE,Types.NUMERIC),
        new Property(9,ID_ULTIMO_TIPODOC,Types.NUMERIC),
        new Property(10,ID_ULTIMO_TEMPLATE,Types.NUMERIC),
        
	};

	/**
	 * 
	 * @throws ISPACException
	 */
	public WLBatchTaskDAO() throws ISPACException {
		super(TABLECOLUMNS);
	}
	
	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public WLBatchTaskDAO(DbCnt cnt) throws ISPACException {
		super(cnt, TABLECOLUMNS);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public WLBatchTaskDAO(DbCnt cnt,int id)	throws ISPACException {
		super(cnt, id, TABLECOLUMNS);
	}
		
	public String getTableName() {
		return TABLENAME;
	}
	
	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + getKeyName() + " = " + getInt(getKeyName());
	}
	
	protected void newObject(DbCnt cnt) throws ISPACException {
		throw new ISPACException("No se tiene secuencia");
	}
	
	public String getKeyName() throws ISPACException {
		return ID_AGRUPACION;
	} 
	
	/**
	 * Obtiene la colección de trámitaciones agrupadas 
	 * en función de la responsabilidad
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable a filtrar
	 * @return colección de tramitaciones agrupadas
	 */
	public static CollectionDAO getBatchTasks(DbCnt cnt, String resp)
	throws ISPACException
	{
		String sql = "WHERE ESTADO = 1 "
				   // Añadir los responsables cuando no es el Supervisor
				   + DBUtil.addAndInResponsibleCondition("ID_RESP", resp);
				
		CollectionDAO objlist = new CollectionDAO(WLBatchTaskDAO.class);
		objlist.query(cnt, sql);
		
		return objlist;
	}
	
	public static IItem getBatchTask(DbCnt cnt, int idAgrupacion)
	throws ISPACException
	{
		String sql = "WHERE ESTADO = 1 AND ID_AGRUPACION = " + idAgrupacion
				+ " ";
		
		CollectionDAO objlist=new CollectionDAO(WLBatchTaskDAO.class);
		objlist.query(cnt,sql);
		IItemCollection coll = objlist.disconnect();
		List ret = coll.toList();
		if (ret.size()>0)
			return (IItem)ret.get(0);
		return null;
	}
	
	/**
	 * Obtiene el número de trámitaciones agrupadas 
	 * en función de la responsabilidad
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable a filtrar
	 * @return número de tramitaciones agrupadas
	 * @throws ISPACException
	 */
	public static int countBatchTasks(DbCnt cnt, String resp)
	throws ISPACException
	{
		String sql = "WHERE ESTADO = 1 "
			   	   // Añadir los responsables cuando no es el Supervisor
			   	   + DBUtil.addAndInResponsibleCondition("ID_RESP", resp);
				
		CollectionDAO objlist = new CollectionDAO(WLBatchTaskDAO.class);
		
		return objlist.count(cnt, sql);
	}
	
}
