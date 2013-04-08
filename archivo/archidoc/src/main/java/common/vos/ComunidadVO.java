/*
 * Created on 19-abr-2005
 *
 */
package common.vos;

/**
 * @author ABELRL
 * 
 */
public class ComunidadVO {
	String codigopais;
	String codigocomunidad;
	String nombre;

	public ComunidadVO() {
	};

	/**
	 * @param codigopais
	 * @param codigoprovincia
	 * @param nombre
	 */
	public ComunidadVO(String codigopais, String codigoprovincia, String nombre) {
		super();
		this.codigopais = codigopais;
		this.codigocomunidad = codigoprovincia;
		this.nombre = nombre;
	}

	public String getCodigopais() {
		return codigopais;
	}

	public void setCodigopais(String idpais) {
		this.codigopais = idpais;
	}

	public String getCodigocomunidad() {
		return codigocomunidad;
	}

	public void setCodigocomunidad(String idprovincia) {
		this.codigocomunidad = idprovincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
