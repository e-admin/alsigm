/**
 *
 */
package fondos.vos;

import common.util.ArrayUtils;
import common.vos.BaseVO;

import fondos.model.TipoNivelCF;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class AmbitoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificadores del Ambito de Fondo
	 */
	private String[] idsFondo = new String[0];

	/**
	 * Identificadores del Ambito de Serie
	 */
	private String[] idsSerie = new String[0];

	/**
	 * Identificadores del Ambito Clasificador de Fondos o Series
	 */
	private String[] idsClasificadores = new String[0];

	public AmbitoVO(String[] idsAmbitos, String[] tiposAmbito) {

		if (ArrayUtils.isNotEmpty(tiposAmbito)) {
			for (int i = 0; i < tiposAmbito.length; i++) {
				String tipoAmbito = tiposAmbito[i];
				String idAmbito = idsAmbitos[i];
				if (TipoNivelCF.FONDO.getIdentificadorAsString().equals(
						tipoAmbito)) {
					idsFondo = (String[]) ArrayUtils.add(idsFondo, idAmbito);
				} else if (TipoNivelCF.SERIE.getIdentificadorAsString().equals(
						tipoAmbito)) {
					idsSerie = (String[]) ArrayUtils.add(idsSerie, idAmbito);
				} else {
					idsClasificadores = (String[]) ArrayUtils.add(
							idsClasificadores, idAmbito);
				}
			}
		}
	}

	public String[] getIdsFondo() {
		return idsFondo;
	}

	public void setIdsFondo(String[] idsFondo) {
		this.idsFondo = idsFondo;
	}

	public String[] getIdsSerie() {
		return idsSerie;
	}

	public void setIdsSerie(String[] idsSerie) {
		this.idsSerie = idsSerie;
	}

	public String[] getIdsClasificadores() {
		return idsClasificadores;
	}

	public void setIdsClasificadores(String[] idsClasificadores) {
		this.idsClasificadores = idsClasificadores;
	}

	public boolean hasClasificadores() {
		return ArrayUtils.isNotEmptyOrBlank(idsClasificadores);
	}

}
