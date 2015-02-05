package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de un país.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class PaisVO extends CatalogoVO {

	private static final long serialVersionUID = -2334421877640539857L;

	private String alfa2;
	private String alfa3;

	/**
	 * Constructor.
	 */
	public PaisVO() {
		super();
	}

	public String getAlfa2() {
		return alfa2;
	}

	public void setAlfa2(String alfa2) {
		this.alfa2 = alfa2;
	}

	public String getAlfa3() {
		return alfa3;
	}

	public void setAlfa3(String alfa3) {
		this.alfa3 = alfa3;
	}
}
