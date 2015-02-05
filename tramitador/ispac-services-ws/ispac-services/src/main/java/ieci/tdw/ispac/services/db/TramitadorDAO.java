package ieci.tdw.ispac.services.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbPreparedStatement;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.services.dto.DocElectronico;
import ieci.tdw.ispac.services.dto.DocFisico;
import ieci.tdw.ispac.services.dto.Emplazamiento;
import ieci.tdw.ispac.services.dto.Expediente;
import ieci.tdw.ispac.services.dto.Interesado;
import ieci.tdw.ispac.services.vo.DocElectronicoVO;
import ieci.tdw.ispac.services.vo.DocFisicoVO;
import ieci.tdw.ispac.services.vo.EmplazamientoVO;
import ieci.tdw.ispac.services.vo.ExpedienteVO;
import ieci.tdw.ispac.services.vo.InfoBExpedienteVO;
import ieci.tdw.ispac.services.vo.InteresadoVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Clase con la implementación de los métodos para el acceso a los datos 
 * del Sistema Tramitador.
 */
public class TramitadorDAO extends BaseDAO {
	
	/** Logger {@link org.apache.log4j.Logger} de la clase */
	protected static final Logger logger = Logger.getLogger(TramitadorDAO.class);

	/**
	 * Recupera los identificadores de los expedientes,  del procedimiento identificado 
	 * por idProc, que hayan finalizado en el rango de fechas comprendido entre fechaIni
	 * y fechaFin ordenados por lo que indique el parámetro tipoOrd.
	 * @param idProc Identificador del procedimiento.
	 * @param fechaIni Fecha de inicio.
	 * @param fechaFin Fecha de fin.
	 * @param tipoOrd Tipo de ordenación ({@link TipoOrdenacionExpedientes}).
	 * <p>Valores posibles:
	 * <li>1 - Número de expediente</li>
	 * <li>2 - Fecha finalización</li>
	 * </p>
	 * @return Lista de identificadores de expedientes.
	 * @throws ISPACException si ocurre algún error en el acceso a la base de datos.
	 */
	public static List recuperarIdsExpedientes(DbCnt cnt, String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws ISPACException {
		

		boolean orderByNumExp = tipoOrd==1;		//1	Número de expediente
		boolean orderByFechaFin = tipoOrd==2;		//2	Fecha finalización 
		
		if (StringUtils.isBlank(idProc)){
			throw new ISPACException("Falta el id de procedimiento");
		}

		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT ")
			 .append(" E.NUMEXP ")
			 .append(" FROM ")
			 .append(" SPAC_EXPEDIENTES E,SPAC_CT_PROCEDIMIENTOS P, SPAC_PROCESOS PR")
			 .append(" WHERE ")
			 .append(" P.COD_PCD='").append(DBUtil.replaceQuotes(idProc)).append("' ")
			 .append(" AND E.ID_PCD=P.ID ");
		
		if (fechaIni!=null) {
			query.append(" AND E.FCIERRE >= ")
				.append(DBUtil.getToDateByBD(cnt, fechaIni))
				.append(" ");
		}
		if (fechaFin!=null) {
			query.append(" AND E.FCIERRE <= ")
				.append(DBUtil.getToDateByBD(cnt, fechaFin))
				.append(" ");
		}
		
		query.append(" AND E.NUMEXP=PR.NUMEXP");
		query.append(" AND PR.ESTADO="+ TXConstants.STATUS_CLOSED);
		
		if (orderByNumExp) {
			query.append(" ORDER BY E.NUMEXP  ");
		} else if (orderByFechaFin) {
			query.append(" ORDER BY E.FCIERRE ");
		}
		
		query.append("  "); 

		//return getVOs(cnt, query.toString(), String.class);
		List voList = new ArrayList();
		DbPreparedStatement ps = null;
		DbQuery dbq = null;
		
		try {
			ps = cnt.prepareDBStatement(query.toString());
			dbq = ps.executeQuery();
			while (dbq.next()) {
				voList.add(dbq.getString("NUMEXP"));
		    }
		} finally {
			if (dbq != null) {
				dbq.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		
		return voList;
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en el
	 * parámetro idExps.
	 * 
	 * @param idExps
	 *            Identificadores de expedientes.
	 * @return Lista de expedientes.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoBExpedienteVO}.
	 *         </p>
	 * @throws ISPACException
	 *             si ocurre algún error en el acceso a la base de datos.
	 */
	public static List recuperarInfoBExpedientes(DbCnt cnt, String[] idExps) 
			throws ISPACException {
		List expedientes = new ArrayList();

		if (ArrayUtils.isEmpty(idExps)) {
			return expedientes;
		}

		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT ")
			 .append(" NUMEXP, ASUNTO ")
			 .append(" FROM ")
			 .append(" SPAC_EXPEDIENTES ")
			 .append(" WHERE ")
			 .append(" NUMEXP IN ('")
			 .append(ArrayUtils.join(DBUtil.replaceQuotes(idExps), "','"))
			 .append("') ")
			 .append(" ORDER BY NUMEXP ");

		return getVOs(cnt, query.toString(), InfoBExpedienteVO.class);
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 * @throws ISPACException si ocurre algún error en el acceso a la base de datos.
	 */
	public static Expediente recuperarExpediente(DbCnt cnt, String idExp) 
			throws ISPACException {
		
		ExpedienteVO expediente = null;

		if (logger.isDebugEnabled())
			logger.debug("Llamada a recuperarExpediente(" + idExp + ")");

		if (StringUtils.isBlank(idExp))
			return expediente;

		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT ")
			 .append(" * ")
			 .append(" FROM ")
			 .append(" SPAC_EXPEDIENTES ")
			 .append(" WHERE ")
			 .append(" NUMEXP='").append(DBUtil.replaceQuotes(idExp)).append("' ");

		expediente = (ExpedienteVO) getVO(cnt, query.toString(), ExpedienteVO.class);
		if (expediente!=null){
				// Documentos físicos
				List docFisicos = recuperarDocumentosFisicosExpediente(cnt, 
						expediente.getInformacionBasica().getId());
				expediente.setDocumentosFisicos((DocFisico[]) 
						docFisicos.toArray(new DocFisico[docFisicos.size()]));
		
				// Documentos electrónicos
				List docElectronicos = recuperarDocumentosElectronicosExpediente(
						cnt, expediente.getInformacionBasica().getId());
				expediente.setDocumentosElectronicos((DocElectronico[]) docElectronicos
						.toArray(new DocElectronico[docElectronicos.size()]));
		
				// Interesados
				Interesado interesadoPrincipal = recuperarInteresadoPrincipalExpediente(
						cnt, expediente.getInformacionBasica().getId());
				List interesados = recuperarInteresadosExpediente(cnt, 
						expediente.getInformacionBasica().getId());
				if (interesadoPrincipal != null) {
					interesados.add(0, interesadoPrincipal);
				}
				expediente.setInteresados((Interesado[]) interesados.toArray(
						new Interesado[interesados.size()]));
		
				// Emplazamientos
				List emplazamientos = recuperarEmplazamientosExpediente(cnt, 
						expediente.getInformacionBasica().getId());
				expediente.setEmplazamientos((Emplazamiento[]) emplazamientos
						.toArray(new Emplazamiento[emplazamientos.size()]));
		}
		return expediente;
	}

	/**
	 * Obtiene los documentos físicos de un expediente.
	 * @param idExp Identificador del expediente.
	 * @return Documentos físicos del expediente.
	 * @throws ISPACException si ocurre algún error en el acceso a la base de datos.
	 */
	protected static List recuperarDocumentosFisicosExpediente(DbCnt cnt, String idExp)
			throws ISPACException {
		List documentos = new ArrayList();

		if (logger.isDebugEnabled())
			logger.debug("Llamada a recuperarDocumentosFisicosExpediente(" + idExp + ")");

		if (StringUtils.isBlank(idExp))
			return documentos;

		StringBuffer query = new StringBuffer();

		query.append(" SELECT ")
			 .append(" NOMBRE, DESCRIPCION ")
			 .append(" FROM ")
			 .append(" SPAC_DT_DOCUMENTOS ")
			 .append(" WHERE ")
			 .append(" NUMEXP='").append(DBUtil.replaceQuotes(idExp))
			 .append("' AND REPOSITORIO IS NULL")
			 .append(" ORDER BY DESCRIPCION ");

		return getVOs(cnt, query.toString(), DocFisicoVO.class);

	}

	/**
	 * Obtiene los documentos electrónicos de un expediente.
	 * @param idExp Identificador del expediente.
	 * @return Documentos electrónicos del expediente.
	 * @throws ISPACException si ocurre algún error en el acceso a la base de datos.
	 */
	protected static List recuperarDocumentosElectronicosExpediente(DbCnt cnt, String idExp)
			throws ISPACException {
		List documentos = new ArrayList();

		if (logger.isDebugEnabled())
			logger.debug("Llamada a recuperarDocumentosElectronicosExpediente(" + idExp + ")");

		if (StringUtils.isBlank(idExp))
			return documentos;

		StringBuffer query = new StringBuffer();

		query.append(" SELECT ")
			 .append("	NOMBRE, DESCRIPCION,INFOPAG_RDE,EXTENSION_RDE,REPOSITORIO ")
			 .append(" FROM ")
			 .append(" SPAC_DT_DOCUMENTOS ")
			 .append(" WHERE ")
			 .append(" NUMEXP='").append(DBUtil.replaceQuotes(idExp))
			 .append("' AND NOT REPOSITORIO IS NULL")
			 .append(" ORDER BY DESCRIPCION ");

		return getVOs(cnt, query.toString(), DocElectronicoVO.class);
	}

	/**
	 * Obtiene el interesado principal de un expediente.
	 * @param idExp Identificador del expediente.
	 * @return Interesado principal del expediente.
	 * @throws ISPACException si ocurre algún error en el acceso a la base de datos.
	 */
	protected static Interesado recuperarInteresadoPrincipalExpediente(DbCnt cnt, String idExp)
			throws ISPACException {
		InteresadoVO interesado = null;

		if (logger.isDebugEnabled())
			logger.debug("Llamada a recuperarInteresadoPrincipalExpediente(" + idExp + ")");

		if (StringUtils.isBlank(idExp))
			return interesado;

		// interesado principal
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT ")
			 .append(" IDENTIDADTITULAR as NOMBRE, NIFCIFTITULAR as NDOC, SUSTITUTO,IDTITULAR as ID_EXT")
			 .append(" FROM ")
			 .append(" SPAC_EXPEDIENTES LEFT OUTER JOIN ")
			 .append(ISPACConfiguration.getInstance().getProperty("TABLA_INTERESADOS"))
			 .append(" ON ROLTITULAR=VALOR")
			 .append(" WHERE ")
			 .append(" NUMEXP='").append(DBUtil.replaceQuotes(idExp)).append("' ");

		InteresadoVO ret = (InteresadoVO) getVO(cnt, query.toString(), InteresadoVO.class);
		if ( (ret != null) 
				&& (StringUtils.isNotBlank(ret.getNombre())
						|| StringUtils.isNotBlank(ret.getNumIdentidad()))) {
			ret.setInteresadoPrincipal(true);
			return ret;
		}
		
		return null;
	}

	/**
	 * Obtiene los interesados de un expediente.
	 * 
	 * @param idExp
	 *            Identificador del expediente.
	 * @return Interesados del expediente.
	 * @InteresadoVO
	 * @throws ISPACException
	 *             si ocurre algún error en el acceso a la base de datos.
	 */
	protected static List recuperarInteresadosExpediente(DbCnt cnt, String idExp)
			throws ISPACException {
		List interesados = new ArrayList();

		if (logger.isDebugEnabled())
			logger.debug("Llamada a recuperarInteresadosExpediente(" + idExp + ")");

		if (StringUtils.isBlank(idExp))
			return interesados;

		StringBuffer query = new StringBuffer();
		
		// RESTO DE INTERESADOS
		query.append(" SELECT")
			 .append(" NOMBRE,NDOC,SUSTITUTO,ID_EXT")
			 .append(" FROM ")
			 .append(" SPAC_DT_INTERVINIENTES LEFT OUTER JOIN ")
			 .append(ISPACConfiguration.getInstance().getProperty("TABLA_INTERESADOS"))
			 .append(" ON ROL=VALOR")
			 .append(" WHERE ")
			 .append(" NUMEXP='").append(DBUtil.replaceQuotes(idExp)).append("' ")
			 .append(" ORDER BY NOMBRE ");

		return getVOs(cnt, query.toString(), InteresadoVO.class);

	}

	/**
	 * Obtiene los emplazamientos de un expediente.
	 * @param idExp Identificador del expediente.
	 * @return Emplazamientos del expediente. @EmplazamientoVO
	 * @throws ISPACException si ocurre algún error en el acceso a la base de datos.
	 */
	protected static List recuperarEmplazamientosExpediente(DbCnt cnt, String idExp)
			throws ISPACException {
		List emplazamientos = new ArrayList();
		EmplazamientoVO emplazamiento = null;

		if (logger.isDebugEnabled())
			logger.debug("Llamada a recuperarEmplazamientosExpediente(" + idExp + ")");

		if (StringUtils.isBlank(idExp))
			return emplazamientos;

		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT ")
			 .append(" REGIONPAIS,MUNICIPIO,POBLACION,LOCALIZACION ")
			 .append(" FROM ")
			 .append(" SPAC_EXPEDIENTES ")
			 .append(" WHERE ")
			 .append(" NUMEXP='").append(DBUtil.replaceQuotes(idExp)).append("' "); 

		emplazamiento = (EmplazamientoVO) getVO(cnt, query.toString(), 
				EmplazamientoVO.class);
		if ( (emplazamiento != null) 
				&& (StringUtils.isNotBlank(emplazamiento.getLocalizacion())
						|| StringUtils.isNotBlank(emplazamiento.getPoblacion())
						|| StringUtils.isNotBlank(emplazamiento.getConcejo())
						|| StringUtils.isNotBlank(emplazamiento.getComunidad())
						|| StringUtils.isNotBlank(emplazamiento.getPais()) )) {
			emplazamientos.add(emplazamiento);
		}
		
		return emplazamientos;
	}

}