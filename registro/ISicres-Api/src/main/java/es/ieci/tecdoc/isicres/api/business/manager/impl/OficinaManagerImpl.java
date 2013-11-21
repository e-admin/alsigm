package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.manager.OficinaManager;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class OficinaManagerImpl extends OficinaManager {

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.OficinaManager#getOficinaByCodigo(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO, java.lang.String)
	 */
	public OficinaVO getOficinaByCodigo(UsuarioVO usuario, String codigoOficina) {
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.OficinaManager#findOficinasByUsuario(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public List<OficinaVO> findOficinasByUsuario(UsuarioVO usuario) {
		Locale locale = usuario.getConfiguracionUsuario().getLocale();
		return getOficinaDAO().getOficinasByUsuario(locale, usuario);
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.manager.OficinaManager#findOficinasAdministradasByUsuario(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public List<OficinaVO> findOficinasAdministradasByUsuario(UsuarioVO usuario) {
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.OficinaManager#getOficinaById(java.util.Locale,
	 *      java.lang.String)
	 */
	public OficinaVO getOficinaById(Locale locale, String idOficina) {
		OficinaVO result = null;

		// obtenemos la oficina segun el id
		result = getOficinaDAO().getOficinaById(locale, idOficina);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.manager.OficinaManager#getOficinas()
	 */
	@Override
	public List<OficinaVO> getOficinas(Locale locale) {

		if (locale == null) {
			Locale defaultLocale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT);
			return getOficinaDAO().getOficinas(defaultLocale);
		}
		return getOficinaDAO().getOficinas(locale);
	}

	public OficinaDAO getOficinaDAO() {
		return oficinaDAO;
	}

	public void setOficinaDAO(OficinaDAO oficinaDAO) {
		this.oficinaDAO = oficinaDAO;
	}

	protected OficinaDAO oficinaDAO;

}
