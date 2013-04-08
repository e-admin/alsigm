package transferencias.vos;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UIReeaCRVO extends UnidadInstalacionBaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String idUIDeposito = new String();
	private int numUdocsUI;
	private String descripcion = new String();

	public UIReeaCRVO() {
		super();
	}

	public UIReeaCRVO(String idRelEntrega, String idFormato) {
		super(idRelEntrega, idFormato);
	}

	/**
	 * @return el idUIDeposito
	 */
	public String getIdUIDeposito() {
		return idUIDeposito;
	}

	/**
	 * @param idUIDeposito
	 *            el idUIDeposito a fijar
	 */
	public void setIdUIDeposito(String idUIDeposito) {
		this.idUIDeposito = idUIDeposito;
	}

	/**
	 * @return el numUdocsUI
	 */
	public int getNumUdocsUI() {
		return numUdocsUI;
	}

	/**
	 * @param numUdocsUI
	 *            el numUdocsUI a fijar
	 */
	public void setNumUdocsUI(int numUdocsUI) {
		this.numUdocsUI = numUdocsUI;
	}

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            el descripcion a fijar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isBorrable() {
		return getNumUdocsUI() == 0;
	}
}