package es.ieci.tecdoc.isicres.api.business.manager.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.contextholder.IsicresContextHolder;

public class ContextoAplicacionManagerImpl implements ContextoAplicacionManager {

	/**
	 * {@inheritDoc}
	 */
	public BaseLibroVO getLibroActual() {

		BaseLibroVO result = this.getContextoAplicacionVO().getLibroActual();

		if (logger.isDebugEnabled()) {
			logger
					.debug("obteniendo libro actual del contexto de la aplicacion="
							+ result);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public UsuarioVO getUsuarioActual() {

		UsuarioVO result = getContextoAplicacionVO().getUsuarioActual();

		if (logger.isDebugEnabled()) {
			logger
					.debug("obteniendo usuario actual del contexto de la aplicacion="
							+ result);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public BaseOficinaVO getOficinaActual() {

		BaseOficinaVO result = getContextoAplicacionVO().getOficinaActual();

		if (logger.isDebugEnabled()) {
			logger
					.debug("obteniendo oficina actual del contexto de la aplicacion="
							+ result);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setOficinaActual(BaseOficinaVO oficina) {

		ContextoAplicacionVO contexto = getContextoAplicacionVO();
		contexto.setOficinaActual(oficina);

		if (logger.isDebugEnabled()) {
			logger
					.debug("seteado/actualizando oficina actual en el contexto de la aplicacion="
							+ oficina);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void setLibroActual(BaseLibroVO libro) {
		ContextoAplicacionVO contexto = getContextoAplicacionVO();
		contexto.setLibroActual(libro);

		if (logger.isDebugEnabled()) {
			logger
					.debug("seteado/actualizando libro actual en el contexto de la aplicacion="
							+ libro);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void setUsuarioActual(UsuarioVO usuario) {
		ContextoAplicacionVO contexto = getContextoAplicacionVO();
		contexto.setUsuarioActual(usuario);

		if (logger.isDebugEnabled()) {
			logger
					.debug("seteado/actualizando usuario actual en el contexto de la aplicacion="
							+ usuario);
		}

	}
	
	
	public BaseRegistroVO getRegistroActual() {
		BaseRegistroVO result = getContextoAplicacionVO().getRegistroActual();

		if (logger.isDebugEnabled()) {
			logger
					.debug("obteniendo registro actual del contexto de la aplicacion="
							+ result);
		}

		return result;
	}

	public void setRegistroActual(BaseRegistroVO registro) {
		ContextoAplicacionVO contexto = getContextoAplicacionVO();
		contexto.setRegistroActual(registro);

		if (logger.isDebugEnabled()) {
			logger
					.debug("seteado/actualizando registro actual en el contexto de la aplicacion="
							+ registro);
		}
		
	}
	
	
	

	/**
	 * {@inheritDoc}
	 */
	public ContextoAplicacionVO getContextoAplicacionVO() {

		ContextoAplicacionVO result = IsicresContextHolder
				.getContextoAplicacion();
		if (result == null) {
			result = new ContextoAplicacionVO();
			IsicresContextHolder.setContextoAplicacion(result);

			if (logger.isDebugEnabled()) {
				logger
						.debug("El contexto de aplicacion era nulo() - se procede a setea uno sin valores");
			}

		}

		return result;
	}
	
	

	// Members
	private static final Logger logger = Logger
			.getLogger(ContextoAplicacionManagerImpl.class);

	
}
