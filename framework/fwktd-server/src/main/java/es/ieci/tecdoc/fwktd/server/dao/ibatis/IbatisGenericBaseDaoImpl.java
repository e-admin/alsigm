package es.ieci.tecdoc.fwktd.server.dao.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public abstract class IbatisGenericBaseDaoImpl {

	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	/**
	 * Constructor.
	 */
	public IbatisGenericBaseDaoImpl() {
	}

	/**
	 * Constructor.
	 * 
	 * @param sqlMapClient
	 *            SqlMapClient.
	 */
	public IbatisGenericBaseDaoImpl(SqlMapClient sqlMapClient) {
		this();
		setSqlMapClient(sqlMapClient);
	}

	@Required
	@Autowired
	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}

	public final SqlMapClientTemplate getSqlMapClientTemplate() {
		return this.sqlMapClientTemplate;
	}

}
