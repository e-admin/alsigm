/**
 *
 */
package valoracion.vos;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ResumenUinstEliminacionVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private int parciales;
	private int completas;
	private int numUdocs;

	public ResumenUinstEliminacionVO() {
		super();
	}

	public ResumenUinstEliminacionVO(int total, int parciales, int completas,
			int numUdocs) {
		super();
		this.total = total;
		this.parciales = parciales;
		this.completas = completas;
		this.numUdocs = numUdocs;
	}

	public int getTotal() {
		return total;
	}

	public int getParciales() {
		return parciales;
	}

	public int getCompletas() {
		return completas;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setParciales(int parciales) {
		this.parciales = parciales;
	}

	public void setCompletas(int completas) {
		this.completas = completas;
	}

	public void setNumUdocs(int numUdocs) {
		this.numUdocs = numUdocs;
	}

	public int getNumUdocs() {
		return numUdocs;
	}
}
