package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ArrayOfWSDistribution;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistribution;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistributionsResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>DistribucionVO</code> en un objeto de tipo
 * <code>WSDistributionsResponse</code>.
 * 
 * @see DistribucionVO
 * @see WSDistributionsResponse
 * 
 * @author IECISA
 * 
 */
public class ListOfDistribucionVOToWSDistributionsResponseMapper implements
		Mapper {

	/**
	 * Constructor con parámetros de la clase. Es necesario pasar el usuario
	 * autenticado para poder utilizar su <i>sessionID</id> para recuperar la
	 * información del libro asociado a una distribución.
	 * 
	 * @param anUsuario
	 *            usuario autenticado
	 */
	public ListOfDistribucionVOToWSDistributionsResponseMapper(
			UsuarioVO anUsuario) {
		this.usuario = anUsuario;
	}

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<DistribucionVO> distributions = (List<DistribucionVO>) obj;

		WSDistributionsResponse result = new WSDistributionsResponse();
		result.setList(new ArrayOfWSDistribution());

		for (DistribucionVO distribution : distributions) {
			result.getList().getWSDistribution().add(
					(WSDistribution) new DistribucionVOToWSDistributionMapper(
							getUsuario()).map(distribution));
		}

		return result;
	}

	protected UsuarioVO getUsuario() {
		return usuario;
	}

	// Members
	protected UsuarioVO usuario;
}
