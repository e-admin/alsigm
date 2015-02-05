
package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import org.apache.commons.lang.StringUtils;

public class PCDPermissionsDAO extends ObjectDAO
{
	static final String TABLENAME = "SPAC_PCD_PERMISOS";





	/**
	 *
	 * @throws ISPACException
	 */
	public PCDPermissionsDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PCDPermissionsDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 *
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PCDPermissionsDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return "";
	}

	public String getKeyName() throws ISPACException
	{
		throw new ISPACException("El acceso a la tabla " + TABLENAME
				+ " tiene más de una clave única");
	}

	public void newObject(DbCnt cnt) throws ISPACException
	{
	}

	
	public static CollectionDAO getPermissions(DbCnt cnt,int idObject, String uid)
		    throws ISPACException
			{
				StringBuffer sqlWhere = new StringBuffer();

				sqlWhere.append(" WHERE  ")
						.append(" ID_PCD = ").append(idObject);
				if(StringUtils.isNotBlank(uid)){
						sqlWhere.append(" AND UID_USR='"+DBUtil.replaceQuotes(uid)+"'");
				}

				sqlWhere.append(" ORDER BY UID_USR  , PERMISO");

		        CollectionDAO objlist = new CollectionDAO(PCDPermissionsDAO.class);
		        objlist.queryDistinct(cnt, sqlWhere.toString());
		        return objlist;
			}

	
}
