package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.DireccionDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.DireccionFisicaDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.DireccionTelematicaDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;

/**
 *
 * @author IECISA
 *
 */
public class DireccionDaoImpl extends
		IbatisGenericDaoImpl<BaseDireccionVO, String> implements DireccionDao {

	public DireccionDaoImpl(Class<BaseDireccionVO> aPersistentClass) {
		super(aPersistentClass);
	}

	@Override
	public void delete(String anId) {
		BaseDireccionVO direccion = get(anId);

		if (direccion instanceof DireccionFisicaVO) {
			getDireccionFisicaDao().delete(anId);
		} else {
			getDireccionTelematicaDao().delete(anId);
		}
		super.delete(anId);
	}

	@Override
	public BaseDireccionVO save(BaseDireccionVO anEntity) {
		BaseDireccionVO direccion = super.save(anEntity);

		if (anEntity instanceof DireccionFisicaVO) {
			direccion = getDireccionFisicaDao().save(
					(DireccionFisicaVO) anEntity);
		} else {
			direccion = getDireccionTelematicaDao().save(
					(DireccionTelematicaVO) anEntity);
		}

		return direccion;
	}

	@Override
	public BaseDireccionVO update(BaseDireccionVO anEntity) {
		BaseDireccionVO direccion = super.update(anEntity);

		if (anEntity instanceof DireccionFisicaVO) {
			getDireccionFisicaDao().update((DireccionFisicaVO) anEntity);
		} else {
			getDireccionTelematicaDao()
					.update((DireccionTelematicaVO) anEntity);
		}

		return direccion;
	}

	@Override
	public void deleteAll() {
		super.deleteAll();
		getDireccionFisicaDao().deleteAll();
		getDireccionTelematicaDao().deleteAll();
	}

	public void deleteAll(BaseTerceroVO tercero) {
		getDireccionFisicaDao().deleteAll(tercero);
		getDireccionTelematicaDao().deleteAll(tercero);
		getSqlMapClientTemplate().delete(
				"BaseDireccionVO.deleteBaseDireccionVOByTercero",
				tercero.getId());
	}

	public List<DireccionTelematicaVO> getAllDireccionesTelematicas() {
		return getDireccionTelematicaDao().getAll();
	}

	public List<DireccionFisicaVO> getAllDireccionesFisicas() {
		return getDireccionFisicaDao().getAll();
	}

	public void updateDireccionPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero) {
		if (direccion instanceof DireccionFisicaVO) {
			getDireccionFisicaDao()
					.updateDireccionPrincipal(direccion, tercero);
		} else if (direccion instanceof DireccionTelematicaVO) {
			getDireccionTelematicaDao().updateDireccionPrincipal(direccion,
					tercero);
		}
	}

	public DireccionFisicaDao getDireccionFisicaDao() {
		return direccionFisicaDao;
	}

	public void setDireccionFisicaDao(DireccionFisicaDao direccionFisicaDao) {
		this.direccionFisicaDao = direccionFisicaDao;
	}

	public DireccionTelematicaDao getDireccionTelematicaDao() {
		return direccionTelematicaDao;
	}

	public void setDireccionTelematicaDao(
			DireccionTelematicaDao direccionTelematicaDao) {
		this.direccionTelematicaDao = direccionTelematicaDao;
	}

	// Members
	protected SqlMapClient sqlMapClient;

	protected DireccionFisicaDao direccionFisicaDao;

	protected DireccionTelematicaDao direccionTelematicaDao;
}
