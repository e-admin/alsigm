/*
 * Created on Oct 21, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.dao.sign;

import org.apache.commons.lang.StringUtils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.sign.SignCircuitFilter;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class SignCircuitHeaderDAO extends ObjectDAO
{
	static final String TABLENAME = "SPAC_CTOS_FIRMA_CABECERA";
	static final String IDSEQUENCE = "SPAC_SQ_ID_CTOS_FIRMA_CABECERA";
	static final String IDKEY = "ID_CIRCUITO";

	/**
	 *
	 * @throws ISPACException
	 */
	public SignCircuitHeaderDAO() throws ISPACException	{
		super(null);
	}

	/**
	 *
	 * @param cnt
	 * @throws ISPACException
	 */
	public SignCircuitHeaderDAO(DbCnt cnt) throws ISPACException	{
		super(cnt, null);
	}

	/**
	 *
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SignCircuitHeaderDAO(DbCnt cnt, int id) throws ISPACException	{
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
    {
	    return " WHERE " + IDKEY + " = " + getInt(IDKEY);
    }

	public String getKeyName() throws ISPACException
	{
	    return IDKEY;
	}

	public void newObject(DbCnt cnt) throws ISPACException
	{
	    set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	/**
	 * @param cnt Conexi&oacute;n a BD
	 * @return Lista de todos los circuitos de firma definidos en el sistema.
	 * @throws ISPACException
	 */
	public static CollectionDAO getCircuits(DbCnt cnt) throws ISPACException {
		return getCircuits(cnt, null);
	}

	public static CollectionDAO getCircuits(DbCnt cnt, SignCircuitFilter filter) throws ISPACException {
    	StringBuffer sql = new StringBuffer();

    	//Por compatibilidad con versiones anteriores a la 6.4 en caso de ser el portafirmas por defecto el valor
    	//también puede ser nulo o vacío

    	if ((filter != null) && (filter.getPcdId() > 0)) {
    		sql.append("WHERE (TIPO=1 OR (TIPO=2 AND ID_CIRCUITO IN (SELECT ID_CIRCUITO FROM SPAC_P_CTOSFIRMA WHERE ID_PCD=")
	    		.append(filter.getPcdId())
	    		.append(")) ) AND ");
    	}

    	sql.append(" ( SISTEMA='"+DBUtil.replaceQuotes(filter.getIdSistema())+"'  ");
    	if(filter.isDefaultPortafirmas()){
    		sql.append(" OR SISTEMA IS NULL ");
    	}
    	sql.append(") ORDER BY DESCRIPCION ");
		CollectionDAO objlist = new CollectionDAO(SignCircuitHeaderDAO.class);
		objlist.query(cnt, sql.toString());
		return objlist;
	}

	/**
	 * Obtiene el circuito de firmas a partir de su identificador
	 * @param cnt Conexi&oacute;n a BD
	 * @return Lista de todos los circuitos de firma definidos en el sistema.
	 * @throws ISPACException
	 */
	public static CollectionDAO getCircuit(DbCnt cnt, int id_circuito) throws ISPACException {
    	String sql = "WHERE ID_CIRCUITO = " + id_circuito;
		CollectionDAO objlist = new CollectionDAO(SignCircuitHeaderDAO.class);
		objlist.query(cnt, sql);
		return objlist;
	}
}
