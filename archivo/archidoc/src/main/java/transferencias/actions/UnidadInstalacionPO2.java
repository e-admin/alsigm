package transferencias.actions;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionVO;

import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;

public class UnidadInstalacionPO2 extends UnidadInstalacionVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ServiceRepository services = null;
	transient GestionRelacionesEntregaBI relacionBI = null;
	transient GestionRelacionesEACRBI relacionCRBI = null;

	List udocsEnUI = null;

	UnidadInstalacionPO2(ServiceRepository services) {
		this.services = services;
	}

	public List getContenido() {
		if (udocsEnUI == null)
			udocsEnUI = getRelacionBI().getUdocsEnUI(getId());
		CollectionUtils.transform(udocsEnUI,
				ParteUnidadDocumentalPO.getInstance(services, null));
		return udocsEnUI;
	}

	public List getContenidoReencajado(String idUI) {
		if (udocsEnUI == null)
			udocsEnUI = getRelacionCRBI().getUDocsByUIReencajado(idUI);
		return udocsEnUI;
	}

	private GestionRelacionesEntregaBI getRelacionBI() {
		if (relacionBI == null)
			relacionBI = services.lookupGestionRelacionesBI();
		return relacionBI;
	}

	private GestionRelacionesEACRBI getRelacionCRBI() {
		if (relacionCRBI == null)
			relacionCRBI = services.lookupGestionRelacionesEACRBI();
		return relacionCRBI;
	}

	public boolean isModificablePorExpedientes() {
		try {
			getRelacionBI().checkModificablePorExpedientes(this);
			return true;
		} catch (RelacionOperacionNoPermitidaException e) {
			return false;
		}
	}

	public boolean isModificablePorPartes() {
		GestionRelacionesEntregaBI relacionService = getRelacionBI();
		List partes = getContenido();
		if (partes != null && partes.size() != 0) {
			try {
				// solo se puede dividir el ultimo expediente de una caja
				ParteUnidadDocumentalVO aPart = (ParteUnidadDocumentalVO) partes
						.get(partes.size() - 1);
				relacionService.checkIsPartDivisible(aPart);
				return true;
			} catch (RelacionOperacionNoPermitidaException e2) {
				for (Iterator itPartes = partes.iterator(); itPartes.hasNext();) {
					IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartes
							.next();
					// se puede eliminar cualquier expediente de la caja,
					// siempre que sea ultima parte
					try {
						relacionService.checkIsPartEliminable(aPart);
						return true;
					} catch (RelacionOperacionNoPermitidaException e) {
					}
				}
			}
		}
		return false;
	}

	public boolean isBorrable() {
		List contenido = getContenido();
		if (contenido != null)
			return contenido.size() == 0;
		return false;

	}

}