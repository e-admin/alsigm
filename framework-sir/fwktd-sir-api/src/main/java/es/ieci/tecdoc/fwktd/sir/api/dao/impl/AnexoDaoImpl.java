package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;

/**
 * DAO de anexos de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AnexoDaoImpl extends
        IbatisGenericDaoImpl<AnexoVO, String> implements
        AnexoDao {

//    protected static final String GET_CONTENIDO_ANEXO = "AnexoVO.getContenidoAnexo";
//    protected static final String UPDATE_CONTENIDO_ANEXO = "AnexoVO.updateContenidoAnexo";
	protected static final String GET_UID_GESTOR_DOCUMENTAL = "AnexoVO.getUIDGestorDocumental";
	protected static final String UPDATE_UID_GESTOR_DOCUMENTAL = "AnexoVO.updateUIDGestorDocumental";
//    protected static final String DELETE_ANEXOS_BY_ID_ASIENTO_REGISTRAL = "AnexoVO.deleteAnexosByIdAsientoRegistral";
	protected static final String GET_IDS_ANEXOS_BY_ID_ASIENTO_REGISTRAL = "AnexoVO.getIdsAnexosByIdAsientoRegistral";

    /**
     * Constructor con parámetros de la clase. Establece el tipo de entidad a
     * persistir/recuperar.
     *
     * @param aPersistentClass
     *            tipo de objeto a persistir/recuperar
     */
    public AnexoDaoImpl(Class<AnexoVO> aPersistentClass) {
        super(aPersistentClass);
    }

//    /**
//     * {@inheritDoc}
//     * @see es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao#getContenidoAnexo(java.lang.String)
//     */
//    public byte[] getContenidoAnexo(String id){
//
//        logger.info("Obteniendo el contenido del anexo [{}]", id);
//
//        byte[] contenido = null;
//
//        @SuppressWarnings("unchecked")
//		Map<String, Object> map = (Map<String, Object>) getSqlMapClientTemplate().queryForObject(GET_CONTENIDO_ANEXO, id);
//        if (map != null) {
//        	Blob blob = (Blob) map.get("CONTENIDO");
//        	if (blob != null) {
//        		try {
//					contenido = FileUtils.retrieve(blob.getBinaryStream());
//				} catch (Exception e) {
//					logger.error("Error al obtener el contenido del anexo [" + id + "]", e);
//					throw new SIRException("error.sir.getContenidoAnexo", new String[] { id }, e.getMessage());
//				}
//        	}
//        }
//
//        return contenido;
//    }
//
//    /**
//     * {@inheritDoc}
//     * @see es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao#setContenidoAnexo(java.lang.String, byte[], byte[])
//     */
//	public void setContenidoAnexo(String id, byte[] contenido, byte[] hash) {
//
//        logger.info("Actualizando el contenido del anexo [{}]", id);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        map.put("id", id);
//        map.put("contenido", contenido);
//        map.put("hash", hash);
//
//        getSqlMapClientTemplate().update(UPDATE_CONTENIDO_ANEXO, map);
//    }

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao#getUIDGestorDocumental(java.lang.String)
	 */
	public String getUIDGestorDocumental(String id) {

		logger.info("Obteniendo el UID del anexo [{}] en el gestor documental",
				id);

		return (String) getSqlMapClientTemplate().queryForObject(
				GET_UID_GESTOR_DOCUMENTAL, id);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao#updateUIDGestorDocumental(java.lang.String, java.lang.String)
	 */
	public void updateUIDGestorDocumental(String id, String uidGestorDocumental) {

		logger.info(
				"Actualizando el anexo [{}] con el UID del gestor documental [{}]",
				id, uidGestorDocumental);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("uidGestorDocumental", uidGestorDocumental);

		getSqlMapClientTemplate().update(UPDATE_UID_GESTOR_DOCUMENTAL, map);
	}

//	/**
//	 * {@inheritDoc}
//	 *
//	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao#deleteByIdAsientoRegistral(java.lang.String)
//	 */
//	public void deleteByIdAsientoRegistral(String idAsientoRegistral) {
//
//		logger.info(
//				"Eliminando anexos del asiento registral con identificador [{}]",
//				idAsientoRegistral);
//
//		getSqlMapClientTemplate().delete(DELETE_ANEXOS_BY_ID_ASIENTO_REGISTRAL,
//				idAsientoRegistral);
//	}

	@SuppressWarnings("unchecked")
	public List<String> getIdsByIdAsientoRegistral(String idAsientoRegistral) {

		logger.info(
				"Obteniendo los identificadores de los anexos del asiento registral con identificador [{}]",
				idAsientoRegistral);

		return (List<String>) getSqlMapClientTemplate().queryForList(
				GET_IDS_ANEXOS_BY_ID_ASIENTO_REGISTRAL, idAsientoRegistral);
	}
}
