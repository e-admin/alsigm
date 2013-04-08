package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import java.util.HashMap;
import java.util.Map;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.ContadorDao;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.ContadorVO;

public class ContadorDaoImpl extends IbatisGenericDaoImpl<ContadorVO, String> implements ContadorDao {

	private static final String CODIGO_ENTIDAD_REGISTRAL = "codigoEntidadRegistral";
	private static final String TIPO_CONTADOR = "tipoContador";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public ContadorDaoImpl(Class<ContadorVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.ContadorDao#get(java.lang.String, es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum)
	 */
	public ContadorVO get(String codigoEntidadRegistral, TipoContadorEnum tipoContador) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put(CODIGO_ENTIDAD_REGISTRAL, codigoEntidadRegistral);
		map.put(TIPO_CONTADOR, tipoContador.getValue());

		return (ContadorVO) getSqlMapClientTemplate().queryForObject(getFindQuery(getPersistentClass()), map);
	}

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl#save(java.lang.Object)
     */
    public ContadorVO save(ContadorVO contador) {
    	getSqlMapClientTemplate().insert(getInsertQuery(getPersistentClass()), contador);
    	return contador;
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl#update(java.lang.Object)
     */
    public ContadorVO update(ContadorVO contador) {
    	getSqlMapClientTemplate().update(getUpdateQuery(getPersistentClass()), contador);
    	return contador;
    }

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.ContadorDao#delete(java.lang.String, es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum)
	 */
	public void delete(String codigoEntidadRegistral, TipoContadorEnum tipoContador) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put(CODIGO_ENTIDAD_REGISTRAL, codigoEntidadRegistral);
		map.put(TIPO_CONTADOR, tipoContador.getValue());

		getSqlMapClientTemplate().delete(getDeleteQuery(getPersistentClass()), map);
	}
}
