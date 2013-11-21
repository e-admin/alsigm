package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralEntradaManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralGeneradorObjetosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSIRManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralSalidaManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper.AsientoRegistralMapper;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoAsientoRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

public class IntercambioRegistralManagerImpl implements IntercambioRegistralManager {

	protected IntercambioRegistralSalidaManager intercambioRegistralSalidaManager;

	protected IntercambioRegistralEntradaManager intercambioRegistralEntradaManager;

	protected IntercambioRegistralSIRManager intercambioRegistralSIRManager;

	protected ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager;

	/**
	 * Manager para construir objetos necesitados por el SIR
	 */
	protected IntercambioRegistralGeneradorObjetosManager intercambioRegistralGeneradorObjetosManager;


	public IntercambioRegistralSalidaManager getIntercambioRegistralSalidaManager() {
		return intercambioRegistralSalidaManager;
	}

	public void setIntercambioRegistralSalidaManager(
			IntercambioRegistralSalidaManager intercambioRegistralSalidaManager) {
		this.intercambioRegistralSalidaManager = intercambioRegistralSalidaManager;
	}

	public IntercambioRegistralEntradaManager getIntercambioRegistralEntradaManager() {
		return intercambioRegistralEntradaManager;
	}

	public void setIntercambioRegistralEntradaManager(
			IntercambioRegistralEntradaManager intercambioRegistralEntradaManager) {
		this.intercambioRegistralEntradaManager = intercambioRegistralEntradaManager;
	}

	public IntercambioRegistralSalidaVO getIntercambioRegistralSalidaById(String id) {
		return getIntercambioRegistralSalidaManager().getIntercambioRegistralSalidaById(id);
	}

	public AsientoRegistralVO getIntercambioRegistralByIdIntercambio(String idIntercambio) {
		return getIntercambioRegistralSIRManager().getAsientoRegistral(idIntercambio);

	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getInfoIntercambioRegistralByIdIntercambio(java.lang.String)
	 */
	public InfoAsientoRegistralVO getInfoIntercambioRegistralByIdIntercambio(String idIntercambio) {
		AsientoRegistralVO asientoRegistralVO = getIntercambioRegistralSIRManager().getAsientoRegistral(idIntercambio);
		InfoAsientoRegistralVO infoAsientoRegistralVO = null;
		if (asientoRegistralVO != null){
			infoAsientoRegistralVO = intercambioRegistralGeneradorObjetosManager.getInfoAsientoRegistralVO(asientoRegistralVO);
		}
		return infoAsientoRegistralVO;
	}

	public boolean isIntercambioRegistral(String idUnidadTramitacionDestino) {
		boolean result = getIntercambioRegistralSalidaManager().isIntercambioRegistral(
				idUnidadTramitacionDestino);
		return result;
	}

	public void toIntercambioRegistral(String idLibro, String idRegistro, String idOfic,
			String tipoOrigen, String idUnidadTramitacionDestino, String user) {

		getIntercambioRegistralSalidaManager().toIntercambioRegistral(idLibro, idRegistro, idOfic,
				tipoOrigen, idUnidadTramitacionDestino, user);

	}

	public void toIntercambioRegistralManual(List<String> idRegistros, String idLibro,
			String idOfic, String tipoOrigen, String user) {
		getIntercambioRegistralSalidaManager().toIntercambioRegistralManual(idRegistros, idLibro,
				idOfic, tipoOrigen, user);

	}

	public void anularIntercambioRegistralSalidaById(String idIntercambioRegistralSalida) {
		getIntercambioRegistralSalidaManager().anularIntercambioRegistralSalidaById(
				idIntercambioRegistralSalida);
	}

	public void undoAnularIntercambioRegistral(String idIntercambioRegistralSalida) {
		getIntercambioRegistralSalidaManager().undoAnularIntercambioRegistral(
				idIntercambioRegistralSalida);
	}

	public BandejaSalidaItemVO completarBandejaSalidaItem(BandejaSalidaItemVO bandejaSalidaItemVO) {
		return getIntercambioRegistralSalidaManager().completarBandejaSalidaItem(
				bandejaSalidaItemVO);
	}

	public BandejaEntradaItemVO completarBandejaEntradaItem(
			BandejaEntradaItemVO bandejaEntradaItemVO) {
		return getIntercambioRegistralEntradaManager().completarBandejaEntradaItem(
				bandejaEntradaItemVO);
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(Integer estado,
			Integer idOficina) {
		List<BandejaSalidaItemVO> result = getIntercambioRegistralSalidaManager()
				.getBandejaSalidaIntercambioRegistral(estado, idOficina);
		return result;
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(Integer estado,
			Integer idOficina, Integer idLibro) {
		List<BandejaSalidaItemVO> result = getIntercambioRegistralSalidaManager()
				.getBandejaSalidaIntercambioRegistral(estado, idOficina, idLibro);
		return result;
	}

	public List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistral(Integer estado,
			Integer idOficina) {
		List<BandejaEntradaItemVO> result = getIntercambioRegistralEntradaManager()
				.getBandejaEntradaIntercambioRegistral(estado, idOficina);
		return result;
	}

	public void rechazarIntercambioRegistralEntradaById(String idIntercambioRegistralEntrada,
			String tipoRechazo, String observaciones) {
		getIntercambioRegistralEntradaManager().rechazarIntercambioRegistralEntradaById(
				idIntercambioRegistralEntrada, tipoRechazo, observaciones);

	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#reenviarIntercambioRegistralEntradaById(java.lang.String, es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO, java.lang.String)
	 */
	public void reenviarIntercambioRegistralEntradaById(
			String idIntercambioRegistralEntrada,
			UnidadTramitacionIntercambioRegistralVO nuevoDestino,
			String observaciones) {
		getIntercambioRegistralEntradaManager().reenviarIntercambioRegistralEntradaById(idIntercambioRegistralEntrada,
				nuevoDestino, observaciones);
	}

	public void deleteIntercambioRegistralSalida(Integer idLibro, Integer idRegistro,
			Integer idOficina) {
		getIntercambioRegistralSalidaManager().deleteIntercambioRegistralSalida(idLibro,
				idRegistro, idOficina);

	}

	public void aceptarIntercambioRegistralEntradaById(String idIntercambioRegistralEntrada,
			String idLibro, String user, Integer idOficina, String codOficina,
			boolean llegoDocFisica) {
		getIntercambioRegistralEntradaManager()
				.aceptarIntercambioRegistralEntradaById(idIntercambioRegistralEntrada, idLibro,
						user, idOficina, codOficina, llegoDocFisica);
	}

	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdministrativaByCodigosComunes(
			String codeEntidadRegistral, String codeUnidadTramitacion) {
		return getConfiguracionIntercambioRegistralManager()
				.getUnidadAdministrativaByCodigosComunes(codeEntidadRegistral,
						codeUnidadTramitacion);

	}




	public void guardarIntercambioRegistralEnAceptados(
			IntercambioRegistralEntradaVO intercambioRegistralEntradaVO) {
		getIntercambioRegistralEntradaManager().guardarIntercambioRegistralEntrada(
				intercambioRegistralEntradaVO);
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistralCompletos(
			List<BandejaSalidaItemVO> bandejaSalida) {
		ArrayList<BandejaSalidaItemVO> bandejaSalidaCompletos = new ArrayList<BandejaSalidaItemVO>();
		for (BandejaSalidaItemVO bandejaSalidaItemVO : bandejaSalida) {
			if (StringUtils.isNotEmpty(bandejaSalidaItemVO.getCodeEntity())) {

				bandejaSalidaCompletos.add(bandejaSalidaItemVO);
			}
		}
		return bandejaSalidaCompletos;
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistralSinERDestino(
			List<BandejaSalidaItemVO> bandejaSalida) {
		ArrayList<BandejaSalidaItemVO> bandejaSalidaSinERDestino = new ArrayList<BandejaSalidaItemVO>();
		for (BandejaSalidaItemVO bandejaSalidaItemVO : bandejaSalida) {
			if (StringUtils.isEmpty(bandejaSalidaItemVO.getCodeEntity())) {
				bandejaSalidaSinERDestino.add(bandejaSalidaItemVO);
			}
		}
		return bandejaSalidaSinERDestino;
	}

	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistralSinEROrigen(
			List<BandejaSalidaItemVO> bandejaSalida) {
		ArrayList<BandejaSalidaItemVO> bandejaSalidaSinEROrigen = new ArrayList<BandejaSalidaItemVO>();
		for (BandejaSalidaItemVO bandejaSalidaItemVO : bandejaSalida) {

			if (StringUtils.isEmpty(bandejaSalidaItemVO.getCodeEntity())) {
				bandejaSalidaSinEROrigen.add(bandejaSalidaItemVO);
			}
		}
		return bandejaSalidaSinEROrigen;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isInBandejasalidaIntercambioRegistral(String idRegistro, String idLibro) {
		return getIntercambioRegistralSalidaManager().isInBandejasalidaIntercambioRegistral(
				idRegistro, idLibro);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getHistorialIntercambioRegistralEntrada(java.lang.String, java.lang.String, java.lang.String)
	 * @deprecated
	 */
	public List<IntercambioRegistralEntradaVO> getHistorialIntercambioRegistralEntrada(
			String idLibro, String idRegistro, String idOficina) {

		List<IntercambioRegistralEntradaVO> intercambios = getIntercambioRegistralEntradaManager()
				.getHistorialIntercambioRegistralEntrada(idLibro, idRegistro, idOficina);

		for (IntercambioRegistralEntradaVO intercambio : intercambios) {

			if (intercambio.getIdIntercambioInterno() != null) {
				// El identificador que se necesita es el id del intercambio interno
				List<TrazabilidadVO> trazas = this.getTrazasIntercambioRegistral(String
						.valueOf(intercambio.getIdIntercambioInterno()));
				intercambio.setTrazas(trazas);
			}

		}

		return intercambios;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getHistorialIntercambioRegistralEntrada(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public List<IntercambioRegistralEntradaVO> getHistorialIntercambioRegistralEntrada(
			String idLibro, String idRegistro, String idOficina,
			boolean incluyeTrazas) {

		List<IntercambioRegistralEntradaVO> intercambios = getIntercambioRegistralEntradaManager()
				.getHistorialIntercambioRegistralEntrada(idLibro, idRegistro,
						idOficina);
		if (incluyeTrazas) {
			for (IntercambioRegistralEntradaVO intercambio : intercambios) {

				if (intercambio.getIdIntercambioInterno() != null) {
					// El identificador que se necesita es el id del intercambio
					// interno
					List<TrazabilidadVO> trazas = this
							.getTrazasIntercambioRegistral(String
									.valueOf(intercambio
											.getIdIntercambioInterno()));
					intercambio.setTrazas(trazas);
				}

			}
		}

		return intercambios;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getHistorialIntercambioRegistralSalida(java.lang.String, java.lang.String, java.lang.String)
	 * @deprecated
	 */
	public List<IntercambioRegistralSalidaVO> getHistorialIntercambioRegistralSalida(
			String idLibro, String idRegistro, String idOficina) {

		List<IntercambioRegistralSalidaVO> intercambios = getIntercambioRegistralSalidaManager()
				.getHistorialIntercambioRegistralSalida(idLibro, idRegistro, idOficina);

		for (IntercambioRegistralSalidaVO intercambio : intercambios) {

			if (intercambio.getIdIntercambioInterno() != null) {
				// El identificador que se necesita es el id del intercambio interno
				List<TrazabilidadVO> trazas = this.getTrazasIntercambioRegistral(String
						.valueOf(intercambio.getIdIntercambioInterno()));
				intercambio.setTrazas(trazas);
			}

		}
		return intercambios;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getHistorialIntercambioRegistralSalida(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	public List<IntercambioRegistralSalidaVO> getHistorialIntercambioRegistralSalida(
			String idLibro, String idRegistro, String idOficina,
			boolean incluyeTrazas) {

		List<IntercambioRegistralSalidaVO> intercambios = getIntercambioRegistralSalidaManager()
				.getHistorialIntercambioRegistralSalida(idLibro, idRegistro,
						idOficina);
		if (incluyeTrazas) {
			for (IntercambioRegistralSalidaVO intercambio : intercambios) {

				if (intercambio.getIdIntercambioInterno() != null) {
					// El identificador que se necesita es el id del intercambio
					// interno
					List<TrazabilidadVO> trazas = this
							.getTrazasIntercambioRegistral(String
									.valueOf(intercambio
											.getIdIntercambioInterno()));
					intercambio.setTrazas(trazas);
				}

			}
		}
		return intercambios;
	}

	public EntidadRegistralVO getEntidadRegistralVOByIdScrOfic(String idOfic) {
		return getConfiguracionIntercambioRegistralManager().getEntidadRegistralVOByIdScrOfic(
				idOfic);

	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getIntercambioRegistralEntradaByRegistro(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public IntercambioRegistralEntradaVO getIntercambioRegistralEntradaByRegistro(
			Integer idLibro, Integer idRegistro, Integer estado){
		return getIntercambioRegistralEntradaManager().getIntercambioRegistralEntradaByRegistro(idLibro, idRegistro, estado);
	}

	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(
			String idScrOrgs) {
		return getConfiguracionIntercambioRegistralManager()
				.getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(idScrOrgs);
	}

	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(
			String codeScrOrgs) {
		return getConfiguracionIntercambioRegistralManager()
				.getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(codeScrOrgs);
	}

	public ConfiguracionIntercambioRegistralManager getConfiguracionIntercambioRegistralManager() {
		return configuracionIntercambioRegistralManager;
	}

	public void setConfiguracionIntercambioRegistralManager(
			ConfiguracionIntercambioRegistralManager configuracionIntercambioRegistralManager) {
		this.configuracionIntercambioRegistralManager = configuracionIntercambioRegistralManager;
	}

	public IntercambioRegistralSIRManager getIntercambioRegistralSIRManager() {
		return intercambioRegistralSIRManager;
	}

	public void setIntercambioRegistralSIRManager(
			IntercambioRegistralSIRManager intercambioRegistralSIRManager) {
		this.intercambioRegistralSIRManager = intercambioRegistralSIRManager;
	}

	public String enviarIntercambioRegistralSalida(String idLibro, String idRegistro,
			String idOfic, String username, String tipoOrigen,
			UnidadTramitacionIntercambioRegistralVO unidadDestino) {
		String result = getIntercambioRegistralSalidaManager().enviarIntercambioRegistralSalida(
				idLibro, idRegistro, idOfic, username, tipoOrigen, unidadDestino);

		return result;
	}





	public List<BandejaEntradaItemVO> findBandejaEntradaByCriterios(
			EstadoIntercambioRegistralEntradaEnumVO estado,
			CriteriosBusquedaIREntradaVO criterios) {
		return getIntercambioRegistralEntradaManager().findBandejaEntradaByCriterios(estado, criterios);
	}

	public PaginatedArrayList<BandejaEntradaItemVO> findBandejaEntradaByCriterios(
			EstadoIntercambioRegistralEntradaEnumVO estado,
			CriteriosBusquedaIREntradaVO criterios, PageInfo pageInfo) {

		return getIntercambioRegistralEntradaManager().findBandejaEntradaByCriterios(estado, criterios, pageInfo);
	}

	public List<BandejaSalidaItemVO> findBandejaSalidaByCriterios(
			EstadoIntercambioRegistralSalidaEnumVO estado,
			CriteriosBusquedaIRSalidaVO criterios, Integer idLibro) {

		return getIntercambioRegistralSalidaManager().findBandejaSalidaByCriterios(estado, criterios, idLibro);
	}

	public PaginatedArrayList<BandejaSalidaItemVO> findBandejaSalidaByCriterios(
			EstadoIntercambioRegistralSalidaEnumVO estado,
			CriteriosBusquedaIRSalidaVO criterios, Integer idLibro, PageInfo pageInfo) {

		return getIntercambioRegistralSalidaManager().findBandejaSalidaByCriterios(estado, criterios, idLibro, pageInfo);
	}



	public List<BandejaEntradaItemVO> findAsientosRegistrales(
			CriteriosVO criterios) {

		List<AsientoRegistralVO> bandejaEntrada = getIntercambioRegistralSIRManager()
				.findAsientosRegistrales(criterios);

		List<BandejaEntradaItemVO> bandejaEntradaItems = new ArrayList<BandejaEntradaItemVO>();

		AsientoRegistralMapper mapper = new AsientoRegistralMapper();
		for (AsientoRegistralVO asientoRegistralVO : bandejaEntrada) {
			BandejaEntradaItemVO bandejaItem = mapper
					.toBandejaEntradaItemVO(asientoRegistralVO);
			bandejaEntradaItems.add(bandejaItem);
		}
		return bandejaEntradaItems;
	}

	public List<EntidadRegistralDCO> buscarEntidadesRegistralesDCO(String code, String nombre) {
		return getConfiguracionIntercambioRegistralManager().buscarEntidadesRegistralesDCO(code,
				nombre);
	}

	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCO(String code, String nombre) {
		return getConfiguracionIntercambioRegistralManager().buscarUnidadesTramitacionDCO(code,
				nombre);

	}

	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCOByEntidad(String codeEntity, String code, String nombre) {
		return getConfiguracionIntercambioRegistralManager().buscarUnidadesTramitacionDCOByEntidad(codeEntity, code,
				nombre);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getContenidoAnexo(java.lang.String)
	 */
	public byte[] getContenidoAnexo(String idAnexo) {
		return getIntercambioRegistralSIRManager().getContenidoAnexo(idAnexo);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getHistoricoCompletoAsientoRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getHistoricoCompletoAsientoRegistral(String id) {

		return getIntercambioRegistralSIRManager().getHistoricoCompletoAsientoRegistral(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager#getTrazasIntercambioRegistral(java.lang.String)
	 */
	public List<TrazabilidadVO> getTrazasIntercambioRegistral(String id) {

		return getIntercambioRegistralSIRManager().getHistoricoAsientoRegistral(id);
	}

	public IntercambioRegistralGeneradorObjetosManager getIntercambioRegistralGeneradorObjetosManager() {
		return intercambioRegistralGeneradorObjetosManager;
	}

	public void setIntercambioRegistralGeneradorObjetosManager(
			IntercambioRegistralGeneradorObjetosManager intercambioRegistralGeneradorObjetosManager) {
		this.intercambioRegistralGeneradorObjetosManager = intercambioRegistralGeneradorObjetosManager;
	}

}
