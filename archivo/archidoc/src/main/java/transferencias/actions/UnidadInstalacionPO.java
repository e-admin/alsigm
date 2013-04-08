package transferencias.actions;

import java.util.Iterator;
import java.util.List;

import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionVO;

public class UnidadInstalacionPO extends UnidadInstalacionVO {

	private static final long serialVersionUID = 1L;
	List udocsEnUI = null;

	UnidadInstalacionPO(List udocsEnUI) {
		this.udocsEnUI = udocsEnUI;
	}

	public List getUdocs() {
		return this.udocsEnUI;
	}

	public int getUdocCount() {
		int nUdocsEnUI = 0;
		if (udocsEnUI != null)
			nUdocsEnUI = udocsEnUI.size();
		return nUdocsEnUI;
	}

	public boolean isLockedByUdoc() {
		boolean locked = false;

		if (udocsEnUI != null) {
			Iterator it = udocsEnUI.iterator();
			while ((it.hasNext()) && (locked == false)) {
				UnidadDocumentalVO unidadDocumentalVO = (UnidadDocumentalVO) it
						.next();
				if (unidadDocumentalVO.getMarcasBloqueo() > 0) {
					locked = true;
				}
			}
		}

		return locked;
	}

	public boolean isBorrable() {
		return getUdocCount() == 0;
	}
}