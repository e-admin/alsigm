package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroCamposExtendidosVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroInfoDocumentoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPageRepositoryVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroRepresentanteVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionTelematicaInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDireccionVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroDomicilioInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroInteresadoVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoRegistroPersonaFisicaOJuridicaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

/**
 * DAO para obtener información del registro que se quiere enviar como intercambio registral
 *
 */
public interface InfoRegistroDAO {

	public InfoRegistroVO getInfoRegistroTipoEntrada(IntercambioRegistralSalidaVO intercambioRegistral);
	public InfoRegistroVO getInfoRegistroTipoSalida(IntercambioRegistralSalidaVO intercambioRegistral);
	public List<InfoRegistroCamposExtendidosVO> getInfoRegistroCamposExtendidos(InfoRegistroVO infoRegistro);
	public List<InfoRegistroInteresadoVO> getInfoRegistroInteresados(InfoRegistroVO infoRegistro);
	public InfoRegistroPersonaFisicaOJuridicaVO getInfoRegistroPersonaFisica(InfoRegistroInteresadoVO infoRegistroInteresado);
	public InfoRegistroPersonaFisicaOJuridicaVO getInfoRegistroPersonaJuridica(InfoRegistroInteresadoVO infoRegistroInteresado);
	public List<InfoRegistroDireccionVO> getInfoRegistroDireccionesInteresado(InfoRegistroInteresadoVO interesado);
	public InfoRegistroDomicilioInteresadoVO getInfoRegistroDomicilioInteresado(InfoRegistroDireccionVO direccion);
	public InfoRegistroDireccionTelematicaInteresadoVO getInfoRegistroDireccionTelematicaInteresado(InfoRegistroDireccionVO direccion);
	public InfoRegistroDireccionVO getInfoRegistroDireccion(InfoRegistroInteresadoVO infoRegistroInteresadoVO);

	public InfoRegistroRepresentanteVO getInfoRegistroRepresentante(InfoRegistroInteresadoVO infoRegistroInteresado);

	public List<InfoRegistroPageRepositoryVO> getInfoRegistroPageRepositories(Long idLibro, Long idRegistro);
	public InfoRegistroInfoDocumentoVO getInfoRegistroInfoDocumento(Integer idDocumento);
}
