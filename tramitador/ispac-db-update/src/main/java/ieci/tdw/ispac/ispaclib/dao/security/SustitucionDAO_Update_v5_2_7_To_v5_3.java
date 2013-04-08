package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class SustitucionDAO_Update_v5_2_7_To_v5_3 extends SustitucionDAO {
	
	// ACTUALIZACIÓN V5.2.7 A V5.3
	// Obtener todas sustituciones
	static public IItemCollection getSubstitutions(DbCnt cnt)
	throws ISPACException
	{
		CollectionDAO objlist=new CollectionDAO(SustitucionDAO_Update_v5_2_7_To_v5_3.class);
		objlist.query(cnt, null);
		return objlist.disconnect();
	}
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public SustitucionDAO_Update_v5_2_7_To_v5_3() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public SustitucionDAO_Update_v5_2_7_To_v5_3(DbCnt cnt) throws ISPACException {
		super(cnt);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SustitucionDAO_Update_v5_2_7_To_v5_3(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
}