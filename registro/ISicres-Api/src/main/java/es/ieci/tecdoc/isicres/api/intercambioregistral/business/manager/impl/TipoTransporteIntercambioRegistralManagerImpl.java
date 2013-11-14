package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.TipoTransporteIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;

public class TipoTransporteIntercambioRegistralManagerImpl implements TipoTransporteIntercambioRegistralManager{

	/**
	 * DAO
	 */
	protected TipoTransporteIntercambioRegistralDAO tipoTransporteIntercambioRegistralDAO;

	/**
	 * Incrementer para obtener el id
	 */
	protected DataFieldMaxValueIncrementer tipoTransporteIntercambioRegistralIncrementer;


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager#getTipoTransporteByDesc(java.lang.String)
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByDesc(
			String descripcion) {

		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = tipoTransporteIntercambioRegistralDAO.getTipoTransporteByDesc(descripcion);
		if (tipoTransporteIntercambioRegistralVO == null){
			tipoTransporteIntercambioRegistralVO = tipoTransporteIntercambioRegistralDAO.getTipoTransporteByDescLanguages(descripcion);
		}

		return tipoTransporteIntercambioRegistralVO;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager#getTipoTransporteByCodigo(java.lang.String)
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByCodigo(
			String codigo) {

		return tipoTransporteIntercambioRegistralDAO.getTipoTransporteByCodigo(codigo);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager#getCountTipoTransporteByCodigo(java.lang.String)
	 */
	public int getCountTipoTransporteByCodigo(
			String codigo) {

		return tipoTransporteIntercambioRegistralDAO.getCountTipoTransporteByCodigo(codigo);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager#getTipoTransporteByCodigoAndIDScrTT(java.lang.String, java.lang.String)
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByCodigoAndIDScrTT(
			String codigo, Integer idScrTT) {

		return tipoTransporteIntercambioRegistralDAO.getTipoTransporteByCodigoAndIDScrTT(codigo, idScrTT);
	}


	public TipoTransporteIntercambioRegistralVO getTipoTransporteByIdSicres(
			Integer idTipoTransporte){
		return tipoTransporteIntercambioRegistralDAO.getTipoTransporteByIdSicres(idTipoTransporte);
	}
	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager#updateByIdTipoTransporte(es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO)
	 */
	public void updateByIdTipoTransporte(
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO) {
		tipoTransporteIntercambioRegistralDAO.updateByIdTipoTransporte(tipoTransporteIntercambioRegistralVO);

	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager#save(es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO)
	 */
	public TipoTransporteIntercambioRegistralVO save(
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO) {

		tipoTransporteIntercambioRegistralVO.setId(tipoTransporteIntercambioRegistralIncrementer.nextLongValue());
		return tipoTransporteIntercambioRegistralDAO.save(tipoTransporteIntercambioRegistralVO);
	}

	public void delete(Long id) {
		tipoTransporteIntercambioRegistralDAO.delete(id);
	}

	public void deleteByDesc(String descripcion){

	}

	public TipoTransporteIntercambioRegistralDAO getTipoTransporteIntercambioRegistralDAO() {
		return tipoTransporteIntercambioRegistralDAO;
	}

	public void setTipoTransporteIntercambioRegistralDAO(
			TipoTransporteIntercambioRegistralDAO tipoTransporteIntercambioRegistralDAO) {
		this.tipoTransporteIntercambioRegistralDAO = tipoTransporteIntercambioRegistralDAO;
	}

	public DataFieldMaxValueIncrementer getTipoTransporteIntercambioRegistralIncrementer() {
		return tipoTransporteIntercambioRegistralIncrementer;
	}

	public void setTipoTransporteIntercambioRegistralIncrementer(
			DataFieldMaxValueIncrementer tipoTransporteIntercambioRegistralIncrementer) {
		this.tipoTransporteIntercambioRegistralIncrementer = tipoTransporteIntercambioRegistralIncrementer;
	}

}
