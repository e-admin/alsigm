package ieci.tdw.ispac.services.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.services.dto.OrganoProductor;
import ieci.tdw.ispac.services.dto.Procedimiento;
import ieci.tdw.ispac.services.vo.InfoBProcedimientoVO;
import ieci.tdw.ispac.services.vo.OrganoProductorVO;
import ieci.tdw.ispac.services.vo.ProcedimientoVO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



/**
 * Clase con la implementación de los métodos para el acceso a los datos 
 * del Catálogo de Procedimientos.
 */
public class CatalogoDAO extends BaseDAO {
		
	/** Logger {@link org.apache.log4j.Logger} de la clase */
	protected static final Logger logger = Logger.getLogger(CatalogoDAO.class);
	
	private static final int ESTADO_VIGENTE = 2;
	
	
	/**
	 * Recupera la lista de procedimientos del tipo que se indica en 
	 * tipoProc, con su información básica.
	 * @param tipoProc Tipo de procedimiento.
	 * <p>Valores posibles de tipoProc:
	 * <li>1 - Todos</li>
	 * <li>2 - Procedimientos  automatizados</li>
	 * <li>3 - Procedimientos no automatizados</li>
	 * </p>
	 * @param nombre Nombre del procedimiento.
	 * @return Lista de información de procedimientos.
	 * <p>Los objetos de la lista tienen que implementar el 
	 * interface {@link InfoBProcedimiento}.</p>
	 * @throws DBException si ocurre algún error en el acceso a la base de datos.
	 */
	public static List getProcedimientos(DbCnt cnt, int tipoProc, String nombre) 
			throws ISPACException {
		
		//boolean isTodos = tipoProc==1;
		boolean isAutomaticProc = tipoProc==2;
		boolean isNoAutomaticProc = tipoProc==3;
		
		final StringBuffer sqlProcAutomatizados = new StringBuffer();
		
		sqlProcAutomatizados.append(" SELECT ");
		sqlProcAutomatizados.append(" A.COD_PCD, A.NOMBRE, A.COD_SISTEMA_PRODUCTOR, ");
		sqlProcAutomatizados.append(" C.SUSTITUTO AS NOMBRE_SISTEMA_PRODUCTOR ");
		sqlProcAutomatizados.append(" FROM ");
		sqlProcAutomatizados
			.append(" SPAC_CT_PROCEDIMIENTOS A LEFT OUTER JOIN ").append(ISPACConfiguration.getInstance().getProperty("TABLA_PRODUCTORES"))
				.append(" C ON A.COD_SISTEMA_PRODUCTOR=C.VALOR, SPAC_P_PROCEDIMIENTOS B ");
		sqlProcAutomatizados.append(" WHERE  ");
		sqlProcAutomatizados.append(" A.ID=B.ID ");
		sqlProcAutomatizados.append(" AND B.ESTADO=").append(ESTADO_VIGENTE);
		
		if (isAutomaticProc)
			sqlProcAutomatizados.append(" AND A.COD_SISTEMA_PRODUCTOR<>'00' ");
		else
			if (isNoAutomaticProc)
				sqlProcAutomatizados.append(" AND (A.COD_SISTEMA_PRODUCTOR='00' OR A.COD_SISTEMA_PRODUCTOR IS NULL) ");
		if (!StringUtils.isEmpty(nombre))
			sqlProcAutomatizados.append(" AND UPPER(A.NOMBRE) LIKE '%").append(nombre.toUpperCase()).append("%' ");
		sqlProcAutomatizados.append(" ORDER BY A.NOMBRE ");

	    return getVOs(cnt, sqlProcAutomatizados.toString(),  
	    		InfoBProcedimientoVO.class);
	}
	
	
	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 * <p>Los objetos de la lista tienen que implementar el 
	 * interface {@link InfoBProcedimiento}.</p>
	 * @throws DBException si ocurre algún error en el acceso a la base de datos.
	 */
	public static List getProcedimientos(DbCnt cnt, String [] idProcs) throws ISPACException
	{
		StringBuffer sqlBuff = new StringBuffer();
		
		sqlBuff.append(" SELECT ")
			   .append(" A.COD_PCD, A.NOMBRE, A.COD_SISTEMA_PRODUCTOR, ")
			   .append(" C.SUSTITUTO AS NOMBRE_SISTEMA_PRODUCTOR ")
			   .append(" FROM ")
				.append(" SPAC_CT_PROCEDIMIENTOS A LEFT OUTER JOIN ").append(ISPACConfiguration.getInstance().getProperty("TABLA_PRODUCTORES"))
			   .append(" C ON A.COD_SISTEMA_PRODUCTOR=C.VALOR, SPAC_P_PROCEDIMIENTOS B ")
			   .append(" WHERE ")
			   .append(" A.ID=B.ID ")
			   .append(" AND B.ESTADO=").append(ESTADO_VIGENTE)
			   .append(" AND A.COD_PCD IN ('").append(ArrayUtils.join(DBUtil.replaceQuotes(idProcs), "','")).append("') ")
			   .append(" ORDER BY A.NOMBRE ");
	    
	    return getVOs(cnt, sqlBuff.toString(), InfoBProcedimientoVO.class);
	}
	
	
	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 * @throws DBException si ocurre algún error en el acceso a la base de datos.
	 */
	public static Procedimiento getProcedimiento(DbCnt cnt, String idProc) throws ISPACException
	{
		final StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT ")
		   .append(" A.COD_PCD, A.NOMBRE, A.COD_SISTEMA_PRODUCTOR, ")
		   .append(" C.SUSTITUTO AS NOMBRE_SISTEMA_PRODUCTOR, ")
		   .append(" A.OBJETO, A.DSCR_TRAM, A.NORMATIVA, A.DOCUMENTACION ")
		   .append(" FROM ")
				.append(" SPAC_CT_PROCEDIMIENTOS A LEFT OUTER JOIN ").append(ISPACConfiguration.getInstance().getProperty("TABLA_PRODUCTORES"))
		   .append(" C ON A.COD_SISTEMA_PRODUCTOR=C.VALOR, SPAC_P_PROCEDIMIENTOS B ")
		   .append(" WHERE ")
		   .append(" A.COD_PCD='").append(DBUtil.replaceQuotes(idProc)).append("' ")
		   .append(" AND A.ID=B.ID ")
		   .append(" AND B.ESTADO=").append(ESTADO_VIGENTE);
	    
	    Procedimiento procedimiento = (Procedimiento) getVO(cnt, sql.toString(),
				ProcedimientoVO.class);
	    if (procedimiento!=null){
	    	List organosProductores = getOrganosProductores(cnt, procedimiento.getInformacionBasica().getId());
	    	procedimiento.setOrganosProductores((OrganoProductor [])organosProductores.toArray(new OrganoProductor[organosProductores.size()]));
	    }
		
		return procedimiento;
	    
	}

	
	/**
	 * Obtiene los órganos productores del procedimiento.
	 * @param idProc Identificador del procedimiento.
	 * @return Órganos productores del procedimiento.
	 * @throws DBException si ocurre algún error en el acceso a la base de datos.
	 */
	protected static List getOrganosProductores(DbCnt cnt, String idProc) throws ISPACException
	{
		if (idProc!=null){
			StringBuffer sql = new StringBuffer();
			
			sql.append(" SELECT ")
			   .append(" ID_UNIDAD_TRAMITADORA,FECHA_INI_PROD ")
			   .append(" FROM  ")
			   .append(" SPAC_CT_PCDORG ")
			   .append(" WHERE ")
			   .append(" COD_PCD='").append(DBUtil.replaceQuotes(idProc)).append("' ")
			   .append(" ORDER BY ORDEN	 ");
	
			return getVOs(cnt, sql.toString(), OrganoProductorVO.class);
		}
		return new ArrayList();
	}
	

}