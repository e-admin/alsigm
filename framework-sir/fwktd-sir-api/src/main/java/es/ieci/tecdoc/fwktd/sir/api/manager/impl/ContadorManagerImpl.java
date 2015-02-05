package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.ContadorDao;
import es.ieci.tecdoc.fwktd.sir.api.manager.ContadorManager;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.ContadorVO;

public class ContadorManagerImpl extends BaseManagerImpl<ContadorVO, String>
		implements ContadorManager {

	/**
	 * Constructor.
	 *
	 * @param aGenericDao
	 *            Dao.
	 */
	public ContadorManagerImpl(BaseDao<ContadorVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.ContadorManager#updateContador(java.lang.String, es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum)
	 */
	public String updateContador(String codigoEntidadRegistral, TipoContadorEnum tipoContador) {

		Assert.hasText(codigoEntidadRegistral, "'codigoEntidadRegistral' must not be empty");
		Assert.notNull(tipoContador, "'tipoContador' must not be null");

		ContadorVO contador = ((ContadorDao)getDao()).get(codigoEntidadRegistral, tipoContador);
		if (contador != null) {

			// Actualizar el contador para el tipo de contador en la entidad registral
			contador.setContador(contador.getContador() + 1);

			((ContadorDao)getDao()).update(contador);

		} else {

			// Iniciar el contador para el tipo de contador en la entidad registral

			contador = new ContadorVO();
			contador.setCodigoEntidadRegistral(codigoEntidadRegistral);
			contador.setTipoContador(tipoContador);
			contador.setContador(0);

			((ContadorDao)getDao()).save(contador);
		}


		return StringUtils.leftPad(String.valueOf(contador.getContador()), 8, "0");
	}
}
