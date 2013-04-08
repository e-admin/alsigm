package es.ieci.tecdoc.isicres.terceros.business.manager.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.DireccionDao;
import es.ieci.tecdoc.isicres.terceros.business.manager.DireccionManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 *
 * @author IECISA
 *
 */
public class DireccionManagerImpl extends
		BaseManagerImpl<BaseDireccionVO, String> implements DireccionManager {

	public DireccionManagerImpl(BaseDao<BaseDireccionVO, String> aGenericDao) {
		super(aGenericDao);
	}

	@Override
	public BaseDireccionVO save(BaseDireccionVO anEntity) {
		List<? extends BaseDireccionVO> direcciones = getDirecciones(
				anEntity.getTercero(), anEntity.getTipo());
		// Si el usuario no tiene direcciones de este tipo la direccion a añadir
		// es la preferente
		if (CollectionUtils.isEmpty(direcciones)) {
			anEntity.setPrincipal(true);
		}
		return super.save(anEntity);
	}

	public void addDireccion(BaseDireccionVO direccion, BaseTerceroVO tercero) {
		Assert.notNull(direccion, "No se puede añadir una direccion nula.");
		Assert.notNull(tercero,
				"No se puede añadir una direccion a un tercero nulo.");

		direccion.setTercero(tercero);
		save(direccion);

	}

	public void deleteDireccion(BaseDireccionVO direccion, BaseTerceroVO tercero) {
		Assert.notNull(tercero,
				"No se puede eliminar una direccion de un tercero nulo.");
		Assert.notNull(direccion,
				"No se puede eliminar una direccion nula a un tercero");
		Assert.hasText(tercero.getId(),
				"No se puede eliminar una direccion de un tercero sin identificador");
		Assert.hasText(direccion.getId(),
				"No se puede eliminar una direccion sin identificador");

		direccion.setTercero(tercero);
		delete(direccion.getId());
	}

	public void updateDireccion(BaseDireccionVO direccion, BaseTerceroVO tercero) {
		Assert.notNull(direccion, "No se puede actualizar una direccion nula.");

		direccion.setTercero(tercero);
		update(direccion);
	}

	public void deleteDirecciones(BaseTerceroVO tercero) {
		Assert.notNull(tercero,
				"No se pueden eliminar las direcciones de un tercero nulo.");

		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get(
				tercero.getId());
		deleteAll(terceroValidadoVO.getDirecciones());
	}

	@Override
	public void delete(String anId) {
		BaseDireccionVO direccion = get(anId);
		super.delete(anId);

		// Si tras borrar solo queda una direccion será la preferente
		List<? extends BaseDireccionVO> direcciones = getDirecciones(
				direccion.getTercero(), direccion.getTipo());
		if (!CollectionUtils.isEmpty(direcciones) && direcciones.size() == 1) {
			selectAsPrincipal(direcciones.get(0), direccion.getTercero());
		}
	}

	@SuppressWarnings("unchecked")
	public List<? extends BaseDireccionVO> getDirecciones(
			BaseTerceroVO tercero, DireccionType tipo) {
		Assert.notNull(tercero,
				"No se pueden obtener las direcciones de un tercero nulo");
		Assert.notNull(tipo,
				"No se pueden obtener las direcciones de un tercero sin especificar su tipo");
		Assert.hasText(tercero.getId(),
				"No se pueden obtener las direcciones de un tercero sin identificador");

		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get(
				tercero.getId());
		List<BaseDireccionVO> direcciones = terceroValidadoVO.getDirecciones();

		return (List<? extends BaseDireccionVO>) CollectionUtils
				.select(direcciones, new BeanPropertyValueEqualsPredicate(
						"tipo", tipo));
	}

	public List<? extends BaseDireccionVO> getDirecciones(BaseTerceroVO tercero) {
		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get(
				tercero.getId());

		return terceroValidadoVO.getDirecciones();
	}

	public void selectAsPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero) {
		Assert.notNull(direccion);
		Assert.notNull(tercero);

		((DireccionDao) getDao()).updateDireccionPrincipal(direccion, tercero);
	}

	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	protected TerceroManager terceroManager;

}
