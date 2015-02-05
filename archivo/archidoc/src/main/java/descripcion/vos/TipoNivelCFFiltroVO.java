/**
 *
 */
package descripcion.vos;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TipoNivelCFFiltroVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int tipoNivel;

	private int subTipoNivel;

	public TipoNivelCFFiltroVO(int tipoNivel, int subTipoNivel) {
		this.tipoNivel = tipoNivel;
		this.subTipoNivel = subTipoNivel;
	}

	public int getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(int tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public int getSubTipoNivel() {
		return subTipoNivel;
	}

	public void setSubTipoNivel(int subTipoNivel) {
		this.subTipoNivel = subTipoNivel;
	}
}
