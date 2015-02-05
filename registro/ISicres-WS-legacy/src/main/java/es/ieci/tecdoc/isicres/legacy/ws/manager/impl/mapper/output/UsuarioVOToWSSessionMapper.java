package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.WSSession;

/**
 * Instancia de <code>Mapper</code> que devuelve una instancia de
 * <code>WSSession</code> a partir de los atributos que recibe de un objeto de
 * tipo <code>UsuarioVO</code>.
 * 
 * @see Mapper
 * @see WSSession
 * @see UsuarioVO
 * 
 * @author IECISA
 * 
 */
public class UsuarioVOToWSSessionMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(UsuarioVO.class, obj);

		UsuarioVO usuario = (UsuarioVO) obj;

		WSSession result = new WSSession();
		result.setId(Integer.valueOf(usuario.getId()).intValue());
		result.setFullName(usuario.getFullName());
		result.setName(usuario.getLoginName());
		result.setOfficeCode(usuario.getConfiguracionUsuario().getOficina()
				.getCodigoOficina());
		result.setOfficeName(usuario.getConfiguracionUsuario().getOficina()
				.getName());
		result.setProfile(Integer.valueOf(
				usuario.getConfiguracionUsuario().getProfile()).intValue());

		return result;
	}

}
