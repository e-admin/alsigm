package es.ieci.tecdoc.fwktd.sir.wsclient.mapper;

import java.util.List;

import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriteriosDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.EstadoAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;

/**
 * Interfaz para las clases de mapeo de objetos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface Mapper {

	public CriteriosDTO getCriteriosDTO(CriteriosVO criteriosVO);

	public List<TrazabilidadVO> getTrazabilidadVOList(
			List<TrazabilidadDTO> trazasDTO);

	public List<AsientoRegistralVO> getAsientoRegistralVOList(
			List<AsientoRegistralDTO> asientosDTO);

	public AsientoRegistralFormDTO getAsientoRegistralFormDTO(
			AsientoRegistralFormVO asientoFormVO);

	public InfoBAsientoRegistralDTO getInfoBAsientoRegistralDTO(
			InfoBAsientoRegistralVO infoBAsientoVO);

	public AsientoRegistralVO getAsientoRegistralVO(
			AsientoRegistralDTO asientoDTO);

	public AnexoFormDTO getAnexoFormDTO(AnexoFormVO anexoFormVO);

	public AnexoVO getAnexoVO(AnexoDTO anexoDTO);

	public AnexoDTO getAnexoDTO(AnexoVO anexoVO);

	public InteresadoFormDTO getInteresadoFormDTO(
			InteresadoFormVO interesadoFormVO);

	public InteresadoVO getInteresadoVO(InteresadoDTO interesadoDTO);

	public InteresadoDTO getInteresadoDTO(InteresadoVO interesadoVO);

//	public List<ValidacionAnexoVO> getListaValidacionAnexoVO(
//			List<ValidacionAnexoDTO> validacionesAnexosDTO);

	public InfoRechazoDTO getInfoRechazoDTO(InfoRechazoVO infoRechazoVO);
	
	public InfoReenvioDTO getInfoReenvioDTO(InfoReenvioVO infoReenvioVO);
	
	public EstadoAsientoRegistraVO getEstadoAsientoRegistraVO(EstadoAsientoRegistralDTO estadoDTO);
	
}