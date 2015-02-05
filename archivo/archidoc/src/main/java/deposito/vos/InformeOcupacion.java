package deposito.vos;

import java.util.ArrayList;
import java.util.List;

import common.util.NumberUtils;

/**
 * Informe de ocupación de un elemento del fondo físico. Contiene tanto los
 * datos de ocupación del elemento al que se refiere el informe con los de cada
 * uno de sus descendientes
 */
public class InformeOcupacion {
	float totalLongitud = 0;
	int totalHuecos = 0;
	int totalHuecosLibres = 0;
	int totalHuecosOcupados = 0;
	int totalHuecosReservados = 0;
	List entradasInforme = null;
	int totalHuecosInutilizados = 0;

	public int getTotalHuecos() {
		return totalHuecos;
	}

	public void setTotalHuecos(int totalHuecos) {
		this.totalHuecos = totalHuecos;
	}

	public int getTotalHuecosLibres() {
		return totalHuecosLibres;
	}

	public void setTotalHuecosLibres(int totalHuecosLibres) {
		this.totalHuecosLibres = totalHuecosLibres;
	}

	public float getTotalLongitud() {
		return totalLongitud;
	}

	public String getLongitudEnMetros() {
		float longitudEnMetros = NumberUtils.dividirNumeroFloat(totalLongitud,
				100);
		return NumberUtils.formatea(longitudEnMetros,
				NumberUtils.FORMATO_DOS_DECIMALES);
	}

	public void setTotalLongitud(float totalLongitud) {
		this.totalLongitud = totalLongitud;
	}

	public int getPorcentajeOcupacion() {
		return totalHuecos > 0 ? 100 - Math
				.round(((float) totalHuecosLibres / totalHuecos) * 100) : 100;
	}

	public int getTotalHuecosOcupados() {
		return totalHuecosOcupados;
	}

	public int getTotalHuecosReservados() {
		return totalHuecosReservados;
	}

	public void addEntradaInforme(String idElemento, String idTipoElemento,
			String descripcion, int numeroHuecos, int huecosLibres,
			int huecosInutilizados, float longitud, int huecosOcupados,
			int huecosReservados) {
		if (this.entradasInforme == null)
			this.entradasInforme = new ArrayList();
		this.entradasInforme.add(new OcupacionElementoDeposito(idElemento,
				idTipoElemento, descripcion, numeroHuecos, huecosLibres,
				huecosInutilizados, longitud));
		this.totalHuecos += numeroHuecos;
		this.totalHuecosLibres += huecosLibres;
		this.totalHuecosOcupados += huecosOcupados;
		this.totalHuecosReservados += huecosReservados;
		this.totalLongitud += longitud;
		this.totalHuecosInutilizados += huecosInutilizados;
	}

	public List getEntradasInforme() {
		return this.entradasInforme;
	}

	public int getTotalHuecosInutilizados() {
		return totalHuecosInutilizados;
	}
}