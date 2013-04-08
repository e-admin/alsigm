package ieci.tdw.ispac.services.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.services.dto.Documento;
import ieci.tdw.ispac.services.vo.DocumentoVO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Clase con la implementación de los métodos para el acceso a los datos 
 * de los documentos.
 */
public class DocumentosDAO extends BaseDAO {
	
	/** Logger {@link org.apache.log4j.Logger} de la clase */
	protected static final Logger logger = Logger.getLogger(DocumentosDAO.class);

	
	/**
	 * Obtiene la información de un documento firmado de un expediente.
	 * @param localizador Localizador del documento.
	 * @return Información del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static Documento getDocumentoFirmado(DbCnt cnt, String localizador) 
			throws ISPACException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Llamada a getDocumento(" + localizador + ")");
		}

		if (StringUtils.isBlank(localizador)) {
			return null;
		}

		StringBuffer query = new StringBuffer()
			.append(" SELECT")
			.append(" INFOPAG_RDE AS GUID,")
			.append(" DESCRIPCION AS NOMBRE,")
			.append(" EXTENSION_RDE AS EXTENSION")
			.append(" FROM")
			.append(" SPAC_DT_DOCUMENTOS")
			.append(" WHERE")
			.append(" INFOPAG_RDE='")
			.append(localizador)
			.append("' AND REPOSITORIO='")
			.append(ISPACConfiguration.getInstance().get("REPOSITORY"))
			.append("'");

		return (Documento) getVO(cnt, query.toString(), DocumentoVO.class);
	}
	
	/**
	 * Obtiene el infoPag del documento que se ha firmado.
	 * @param cnt
	 * @param localizador Localizador del documento
	 * @return Información del documento
	 * @throws ISPACException
	 */
	
	public static Documento getDocumentoOriginal(DbCnt cnt, String localizador) throws ISPACException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Llamada a getDocumentoOriginal(" + localizador + ")");
		}

		if (StringUtils.isBlank(localizador)) {
			return null;
		}

		StringBuffer query = new StringBuffer()
			.append(" SELECT")
			.append(" INFOPAG AS GUID,")
			.append(" DESCRIPCION AS NOMBRE,")
			.append(" EXTENSION_RDE AS EXTENSION")
			.append(" FROM")
			.append(" SPAC_DT_DOCUMENTOS")
			.append(" WHERE")
			.append(" INFOPAG_RDE='")
			.append(localizador)
			.append("'");

		return (Documento) getVO(cnt, query.toString(), DocumentoVO.class);
	}

}