package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericReadOnlyDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.CiudadDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;

/**
 *
 * @author IECISA
 *
 */
public class CiudadDaoImpl extends
		IbatisGenericReadOnlyDaoImpl<CiudadVO, String> implements CiudadDao {

	public CiudadDaoImpl(Class<CiudadVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public CiudadVO findByCodigo(String codigo) {
		return (CiudadVO) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"findByCodigo" }, "."), codigo);
	}

	public CiudadVO findByNombre(String nombre) {
		return (CiudadVO) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"findByNombre" }, "."), nombre);
	}

	@SuppressWarnings("unchecked")
	public Integer getCiudadesByProvinciaCount(ProvinciaVO provincia) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"getCiudadesByProvinciaCount" }, "."), provincia);
	}

	@SuppressWarnings("unchecked")
	public List<CiudadVO> getCiudadesByProvincia(ProvinciaVO provincia) {
		return getSqlMapClientTemplate().queryForList(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"getCiudadesByProvincia" }, "."), provincia);
	}

	@SuppressWarnings("unchecked")
	public List<CiudadVO> getCiudadesByProvincia(ProvinciaVO provincia, int from, int to) {
		return getSqlMapClientTemplate().queryForList(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"getCiudadesByProvincia" }, "."), provincia, from, to);
	}

	public List<CiudadVO> getCiudades(int from, int to){
		return getSqlMapClientTemplate().queryForList(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"getCiudadVOs" }, "."), from, to);
	}


	public Integer getCiudadesCount(){
		return (Integer) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"getCiudadesCount" }, "."));
	}
}
