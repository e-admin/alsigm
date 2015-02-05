package transferencias.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UIReeaCRVO;

import common.bi.GestionRelacionesEACRBI;

/**
 * Asignacion de unidades documentales a unidades de instalacion en relaciones
 * entre archivos
 * 
 */
public class InfoUDocReeacr {
	Logger logger = Logger.getLogger(InfoUDocReeacr.class);

	GestionRelacionesEACRBI relacionEABI = null;

	RelacionEntregaVO relacionEntrega = null;
	LinkedHashMap uisWithUDocs = null;
	List udocsSinAsignar = null;

	public InfoUDocReeacr(RelacionEntregaVO relacionEntrega,
			GestionRelacionesEACRBI relacionEABI) {
		this.relacionEntrega = relacionEntrega;
		this.relacionEABI = (GestionRelacionesEACRBI) relacionEABI;
		init();
	}

	public void init() {
		List UIs = relacionEABI.getUIsReencajado(relacionEntrega.getId());
		uisWithUDocs = new LinkedHashMap();
		for (Iterator iterator = UIs.iterator(); iterator.hasNext();) {
			UIReeaCRVO uiReeaCrVO = (UIReeaCRVO) iterator.next();
			List udocsUI = relacionEABI.getUDocsByUIReencajado(uiReeaCrVO
					.getId());
			uiReeaCrVO.setNumUdocsUI(udocsUI.size());
			uisWithUDocs.put(uiReeaCrVO, udocsUI);
		}
		udocsSinAsignar = relacionEABI
				.getUDocsSinAsignarByIdRelacion(relacionEntrega.getId());
	}

	public Map getUisWithUDocs() {
		return uisWithUDocs;
	}

	public List getUdocsSinAsignar() {
		return udocsSinAsignar;
	}

	public int getNumUdocsSinAsignar() {
		int numUdocsSinAsignar = 0;
		if (udocsSinAsignar != null)
			numUdocsSinAsignar = udocsSinAsignar.size();
		return numUdocsSinAsignar;
	}

	public int getNumUnidadesInstalacion() {
		int nUInstalacion = 0;
		if (uisWithUDocs != null)
			nUInstalacion = uisWithUDocs.size();
		return nUInstalacion;
	}

	public List getListaUIs() {
		List listaUIs = new ArrayList();
		if (uisWithUDocs != null) {
			for (Iterator iterator = uisWithUDocs.keySet().iterator(); iterator
					.hasNext();) {
				IUnidadInstalacionVO ui = (IUnidadInstalacionVO) iterator
						.next();
				listaUIs.add(ui);
			}
		}

		return listaUIs;
	}
}