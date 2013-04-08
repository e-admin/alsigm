package es.ieci.tecdoc.fwktd.dm.business.vo;

/**
 * Información de un metadato de documentos.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MetadatoVO extends AbstractServiceVO {

	/**
	 * UID de versión.
	 */
	private static final long serialVersionUID = 8453588586674985657L;

	protected String nombre = null;
	protected Object valor = null;


	/**
	 * Conctructor.
	 */
	public MetadatoVO() {
		super();
	}

	/**
	 * Conctructor.
	 * @param nombre Nombre del metadato.
	 * @param valor Valor del metadato.
	 */
	public MetadatoVO(String nombre, Object valor) {
		super();
		setNombre(nombre);
		setValor(valor);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

}
