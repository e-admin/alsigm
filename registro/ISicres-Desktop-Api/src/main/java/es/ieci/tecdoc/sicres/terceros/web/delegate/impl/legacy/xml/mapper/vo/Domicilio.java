package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;


/**
 *
 * @author IECISA
 *
 */
public class Domicilio extends Direccion {

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String problacion) {
		this.poblacion = problacion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	protected String poblacion;
	protected String codigoPostal;
	protected String provincia;
	private static final long serialVersionUID = -1021451273714467863L;

}
