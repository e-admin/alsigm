package fondos.vos;

import java.util.List;

import descripcion.vos.InfoBFichaVO;
import docelectronicos.vos.IRepositorioEcmVO;
import docelectronicos.vos.InfoBFichaClfVO;

/**
 * Value Object con las opciones de descripción y almacenamiento para series del
 * cuadro
 */
public class OpcionesDescripcionSerieVO {

	InfoBFichaVO fichaDescripcionSerie = null;
	IRepositorioEcmVO volumenSerie = null;
	InfoBFichaClfVO clasificadoresDocumentos = null;

	List infoFichaUDocRepEcmUDocsSerie = null;

	public OpcionesDescripcionSerieVO() {

	}

	public InfoBFichaClfVO getClasificadoresDocumentos() {
		return clasificadoresDocumentos;
	}

	public void setClasificadoresDocumentos(InfoBFichaClfVO clasificadores) {
		this.clasificadoresDocumentos = clasificadores;
	}

	public InfoBFichaVO getFichaDescripcionSerie() {
		return fichaDescripcionSerie;
	}

	public void setFichaDescripcionSerie(InfoBFichaVO fichaDescripcionSerie) {
		this.fichaDescripcionSerie = fichaDescripcionSerie;
	}

	public IRepositorioEcmVO getVolumenSerie() {
		return volumenSerie;
	}

	public void setVolumenSerie(IRepositorioEcmVO volumenSerie) {
		this.volumenSerie = volumenSerie;
	}

	public List getInfoFichaUDocRepEcmUDocsSerie() {
		return infoFichaUDocRepEcmUDocsSerie;
	}

	public void setInfoFichaUDocRepEcmUDocsSerie(
			List infoFichaUDocRepEcmUDocsSerie) {
		this.infoFichaUDocRepEcmUDocsSerie = infoFichaUDocRepEcmUDocsSerie;
	}

}