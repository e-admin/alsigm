package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Tipo de Transporte
 * 
 * 
 */
public class Transporte {
	
	private int id;
	private String transport;

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getTransport() {
		return transport;
	}

	/**
	 * @param transport
	 */
	public void setTransport(String transport) {
		this.transport = transport;
	}
}
