package deposito.vos;

import common.util.NumberUtils;

/**
 * Datos de ocupación de un elemento del fondo físico
 */
public class OcupacionElementoDeposito {
	String descripcion = null;
	float longitud = 0;
	int numeroHuecos = 0;
	int huecosLibres = 0;
	int huecosInutilizados = 0;
	String idTipoElemento = null;
	String idElemento = null;

	public OcupacionElementoDeposito(String idElemento, String idTipoElemento) {
		this.idElemento = idElemento;
		this.idTipoElemento = idTipoElemento;
	}

	public OcupacionElementoDeposito(String idElemento, String idTipoElemento,
			String descripcion, int numeroHuecos, int huecosLibres,
			int huecosInutilizados, float longitud) {
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.numeroHuecos = numeroHuecos;
		this.huecosLibres = huecosLibres;
		this.huecosInutilizados = huecosInutilizados;
		this.idElemento = idElemento;
		this.idTipoElemento = idTipoElemento;
	}

	public String getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}

	public String getIdTipoElemento() {
		return idTipoElemento;
	}

	public void setIdTipoElemento(String idTipoElemento) {
		this.idTipoElemento = idTipoElemento;
	}

	public int getHuecosLibres() {
		return huecosLibres;
	}

	public void setHuecosLibres(int huecosLibres) {
		this.huecosLibres = huecosLibres;
	}

	public int getHuecosInutilizados() {
		return huecosInutilizados;
	}

	public void setHuecosInutilizados(int huecosInutilizados) {
		this.huecosInutilizados = huecosInutilizados;
	}

	public float getLongitud() {
		return longitud;
	}

	public String getLongitudEnMetros() {
		float longitudEnMetros = NumberUtils.dividirNumeroFloat(longitud, 100);
		return NumberUtils.formatea(longitudEnMetros,
				NumberUtils.FORMATO_DOS_DECIMALES);
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public int getNumeroHuecos() {
		return numeroHuecos;
	}

	public void setNumeroHuecos(int numeroHuecos) {
		this.numeroHuecos = numeroHuecos;
	}

	public int getPorcentajeOcupacion() {
		return numeroHuecos > 0 ? 100 - Math
				.round(((float) huecosLibres / numeroHuecos) * 100) : 100;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}