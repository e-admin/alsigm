package transferencias.model.helper;

import java.util.Iterator;
import java.util.List;

import transferencias.EstadoCotejo;
import transferencias.db.IUdocEnUIDBEntity;
import transferencias.db.IUnidadInstalacionDBEntity;
import transferencias.model.EstadoREntrega;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadInstalacionVO;

/**
 * Helper para el cotejo de unidades documentales
 */
public class CotejoHelper {

	/**
	 * Permite actualizar el estado de una unidad documental en todas las cajas
	 * en las que esté presente
	 * 
	 * @param relacionEntrega
	 *            Relación de entrega
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param _udocEnUIDBEntity
	 *            Entity de unidad documental
	 * @param _unidadInstalacionDBEntity
	 *            Entity de unidad de instalación
	 */
	public static void actualizarEstadoCotejoUdoc(
			RelacionEntregaVO relacionEntrega, String idUdoc,
			IUdocEnUIDBEntity _udocEnUIDBEntity,
			IUnidadInstalacionDBEntity _unidadInstalacionDBEntity) {
		if (relacionEntrega.getEstado() == EstadoREntrega.CON_ERRORES_COTEJO
				.getIdentificador()) {
			_udocEnUIDBEntity.updateEstadoCotejoUdoc(idUdoc, null,
					EstadoCotejo.PENDIENTE.getIdentificador());

			// REVISAR EL ESTADO DE LAS CAJAS DONDE ESTEN LAS PARTES DE LA UDOC
			// obtener cajas donde se encuentren las partes de la udoc
			List partesUdoc = _udocEnUIDBEntity.fetchRowsByUdoc(idUdoc);
			if (partesUdoc != null) {
				for (Iterator itPartesUdoc = partesUdoc.iterator(); itPartesUdoc
						.hasNext();) {
					IParteUnidadDocumentalVO aPartDeUdoc = (IParteUnidadDocumentalVO) itPartesUdoc
							.next();

					_udocEnUIDBEntity.updateEstadoCotejoUdoc(
							aPartDeUdoc.getIdUnidadDoc(),
							aPartDeUdoc.getIdUinstalacionRe(),
							EstadoCotejo.PENDIENTE.getIdentificador());

					UnidadInstalacionVO cajaDeUdoc = _unidadInstalacionDBEntity
							.fetchRowById(aPartDeUdoc.getIdUinstalacionRe());
					if (cajaDeUdoc != null) {
						boolean hayErrores = false;
						List udocsEnCajaDeUdoc = _udocEnUIDBEntity
								.fetchRowsByUInstalacion(cajaDeUdoc.getId());
						if (udocsEnCajaDeUdoc != null) {
							for (Iterator itUdocs = udocsEnCajaDeUdoc
									.iterator(); itUdocs.hasNext();) {
								IParteUnidadDocumentalVO aPartEnCajaDeUdoc = (IParteUnidadDocumentalVO) itUdocs
										.next();
								if (aPartEnCajaDeUdoc.getEstadoCotejo() == EstadoCotejo.ERRORES
										.getIdentificador()) {
									hayErrores = true;
								}
								if (aPartEnCajaDeUdoc.getEstadoCotejo() == EstadoCotejo.PENDIENTE
										.getIdentificador()) {
									_unidadInstalacionDBEntity
											.updateFieldEstado(cajaDeUdoc
													.getId(),
													EstadoCotejo.PENDIENTE
															.getIdentificador());
								}
							}
						}
						if (hayErrores) {
							if (cajaDeUdoc.getEstadocotejo() != EstadoCotejo.ERRORES
									.getIdentificador()) {
								_unidadInstalacionDBEntity
										.updateFieldEstado(cajaDeUdoc.getId(),
												EstadoCotejo.ERRORES
														.getIdentificador());
							}
						} else {
							if (cajaDeUdoc.getEstadocotejo() == EstadoCotejo.ERRORES
									.getIdentificador()) {
								_unidadInstalacionDBEntity.updateFieldEstado(
										cajaDeUdoc.getId(),
										EstadoCotejo.PENDIENTE
												.getIdentificador());
							}
						}
					}
				}
			}
		}
	}

}
