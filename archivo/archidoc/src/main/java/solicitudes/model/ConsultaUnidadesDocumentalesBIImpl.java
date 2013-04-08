package solicitudes.model;

import java.util.List;

import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.db.IDetalleDBEntity;

import common.bi.ServiceBase;
import common.bi.ServiceSession;
import common.exceptions.TooManyResultsException;

import fondos.vos.BusquedaElementosVO;

public class ConsultaUnidadesDocumentalesBIImpl extends ServiceBase implements
		ConsultaUnidadesDocumentalesBI {

	private IDetalleDBEntity detallePrestamoDBEntity = null;

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public ConsultaUnidadesDocumentalesBIImpl(ServiceSession ss) {
		this.detallePrestamoDBEntity = new DetalleDBEntityImpl(
				ss.getTransactionManager());
	}

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public ConsultaUnidadesDocumentalesBIImpl(
			IDetalleDBEntity detallePrestamoDBEntity) {

		this.detallePrestamoDBEntity = detallePrestamoDBEntity;

	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.model.ConsultaUnidadesDocumentalesBI#
	 * getIdsElementosEnPrestamosYConsultas(fondos.vos.BusquedaElementosVO)
	 */
	public List getIdsElementosEnPrestamosYConsultas(
			BusquedaElementosVO busquedaElementosVO, int numMaxResults)
			throws TooManyResultsException {
		return detallePrestamoDBEntity.getIdsElementosEnPrestamosYConsultas(
				busquedaElementosVO, numMaxResults);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.model.ConsultaUnidadesDocumentalesBI#
	 * getElementosEnPrestamosYConsultas(java.util.List,
	 * fondos.vos.BusquedaElementosVO)
	 */
	public List getElementosEnPrestamosYConsultas(List idsTiposToShow,
			BusquedaElementosVO busquedaElementosVO) {
		return detallePrestamoDBEntity.getElementosEnPrestamosYConsultas(
				idsTiposToShow, busquedaElementosVO);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.model.ConsultaUnidadesDocumentalesBI#
	 * getElementosEnPrestamosOrConsultas(java.util.List,
	 * fondos.vos.BusquedaElementosVO, java.lang.String)
	 */
	public List getElementosEnPrestamosOrConsultas(List idsTiposToShow,
			BusquedaElementosVO busquedaElementosVO, String tipoServicio) {
		return detallePrestamoDBEntity.getElementosEnPrestamosOrConsultas(
				idsTiposToShow, busquedaElementosVO, tipoServicio);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.model.ConsultaUnidadesDocumentalesBI#
	 * getIdsElementosEnPrestamosOrConsultas(fondos.vos.BusquedaElementosVO,
	 * java.lang.String)
	 */
	public List getIdsElementosEnPrestamosOrConsultas(
			BusquedaElementosVO busquedaElementosVO, String tipoServicio,
			int numMaxResults) throws TooManyResultsException {
		return detallePrestamoDBEntity.getIdsElementosEnPrestamosOrConsultas(
				busquedaElementosVO, tipoServicio, numMaxResults);
	}

}
