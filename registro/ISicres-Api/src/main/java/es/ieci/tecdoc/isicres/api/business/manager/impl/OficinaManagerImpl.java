package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.manager.OficinaManager;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class OficinaManagerImpl extends OficinaManager {

	public OficinaVO getOficinaByCodigo(UsuarioVO usuario, String codigoOficina) {
		throw new UnsupportedOperationException();
	}

	public List findOficinasByUsuario(UsuarioVO usuario) {
		throw new UnsupportedOperationException();
	}

	public List findOficinasAdministradasByUsuario(UsuarioVO usuario) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.OficinaManager#getOficinaById
	 * (java.util.Locale, java.lang.String)
	 */
	public OficinaVO getOficinaById(Locale locale, String idOficina) {
		OficinaVO result = null;

		// obtenemos la oficina segun el id
		result = getOficinaDAO().getOficinaById(locale, idOficina);

		return result;
	}

	public OficinaDAO getOficinaDAO() {
		return oficinaDAO;
	}

	public void setOficinaDAO(OficinaDAO oficinaDAO) {
		this.oficinaDAO = oficinaDAO;
	}

	// Members
	protected OficinaDAO oficinaDAO;

}
