package docelectronicos.vos;

import common.vos.BaseVO;

import descripcion.vos.InfoBFichaVO;
import fondos.vos.INivelCFVO;

public class InfoBFichaUDocRepEcmUDocsSerie extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private INivelCFVO nivelUDoc = null;
	private InfoBFichaClfVO fichaClf = null;
	private InfoBFichaVO ficha = null;
	private IRepositorioEcmVO repositorioEcmVO = null;
	private boolean updateUDocs = false;

	public InfoBFichaUDocRepEcmUDocsSerie() {
	}

	public InfoBFichaClfVO getFichaClf() {
		return fichaClf;
	}

	public void setFichaClf(InfoBFichaClfVO fichaClf) {
		this.fichaClf = fichaClf;
	}

	public InfoBFichaVO getFicha() {
		return ficha;
	}

	public void setFicha(InfoBFichaVO ficha) {
		this.ficha = ficha;
	}

	public IRepositorioEcmVO getRepositorioEcm() {
		return repositorioEcmVO;
	}

	public void setRepositorioEcm(IRepositorioEcmVO repositorioEcm) {
		this.repositorioEcmVO = repositorioEcm;
	}

	public INivelCFVO getNivelUDoc() {
		return nivelUDoc;
	}

	public void setNivelUDoc(INivelCFVO nivelUDoc) {
		this.nivelUDoc = nivelUDoc;
	}

	public boolean isUpdateUDocs() {
		return updateUDocs;
	}

	public void setUpdateUDocs(boolean updateUDocs) {
		this.updateUDocs = updateUDocs;
	}

}
