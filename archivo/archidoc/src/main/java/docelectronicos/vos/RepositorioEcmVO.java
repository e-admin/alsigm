package docelectronicos.vos;

import java.util.Properties;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

/**
 * Información de un Repositorio ECM.
 */
public class RepositorioEcmVO implements IRepositorioEcmVO {
	private String nombre;
	private String id;
	private String tipo;
	private Properties parametros;


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Constructor.
	 */
	public RepositorioEcmVO() {
		super();
	}

	public RepositorioEcmVO(ContentType contentType) {
		super();
		setId(contentType.getId());
		setNombre(contentType.getName());
		setTipo(contentType.getType());

		setParametros(contentType.getProperties());
	}

	@Override
	public String toString() {
		return "RepositorioEcmVO [id=" + id + ", nombre=" + nombre + ", tipo="
				+ tipo + "]";
	}

	public boolean isInterno() {
		if (REPOSITORIO_ECM_INTERNO.equals(tipo)) {
			return true;
		}
		return false;
	}

	public String getIdReal() {
		return id;
	}

	public void setParametros(Properties parametros) {
		this.parametros = parametros;
	}

	public Properties getParametros() {
		return parametros;
	}

}
