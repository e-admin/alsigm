package es.ieci.tecdoc.fwktd.csv.api.manager.impl;

import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.csv.api.dao.AplicacionDao;
import es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager;
import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

/**
 * Implementación del manager de aplicaciones.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionManagerImpl extends BaseManagerImpl<AplicacionVO, String>
        implements AplicacionManager {

    /**
     * Constructor.
     *
     * @param aGenericDao
     */
    public AplicacionManagerImpl(BaseDao<AplicacionVO, String> aGenericDao) {
        super(aGenericDao);
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager#getAplicacionByCodigo(java.lang.String)
     */
    public AplicacionVO getAplicacionByCodigo(String codigo) {

        logger.info("Obteniendo la aplicación a partir del código [{}]", codigo);

        Assert.hasText(codigo, "'codigo' must not be empty");

        return ((AplicacionDao)getDao()).getAplicacionByCodigo(codigo);
    }

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager#deleteAplicacionByCodigo(java.lang.String)
	 */
	public void deleteAplicacionByCodigo(String codigo) {

        logger.info("Eliminando la aplicación a partir del código [{}]", codigo);

        Assert.hasText(codigo, "'codigo' must not be empty");

        ((AplicacionDao)getDao()).deleteAplicacionByCodigo(codigo);
	}

}
