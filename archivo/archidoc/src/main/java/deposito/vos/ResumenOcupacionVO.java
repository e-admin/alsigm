package deposito.vos;

import org.apache.commons.lang.StringUtils;

public class ResumenOcupacionVO {

	int huecosLibres;
	int huecosOcupados;
	int huecosReservados;
	int huecosInutilizados;

	public int getHuecosInutilizados() {
		return huecosInutilizados;
	}

	public void setHuecosInutilizados(int huecosInutilizados) {
		this.huecosInutilizados = huecosInutilizados;
	}

	public int getHuecosLibres() {
		return huecosLibres;
	}

	public void setHuecosLibres(int huecosLibres) {
		this.huecosLibres = huecosLibres;
	}

	public int getHuecosOcupados() {
		return huecosOcupados;
	}

	public void setHuecosOcupados(int huecosOcupados) {
		this.huecosOcupados = huecosOcupados;
	}

	public int getHuecosReservados() {
		return huecosReservados;
	}

	public void setHuecosReservados(int huecosReservados) {
		this.huecosReservados = huecosReservados;
	}

	public int getTotalHuecos() {
		return huecosLibres + huecosOcupados + huecosReservados
				+ huecosInutilizados;
	}

	public void setNumHuecos(String estado, int numHuecos) {
		if (StringUtils.equals(estado, HuecoVO.LIBRE_STATE))
			setHuecosLibres(numHuecos);
		else if (StringUtils.equals(estado, HuecoVO.OCUPADO_STATE))
			setHuecosOcupados(numHuecos);
		else if (StringUtils.equals(estado, HuecoVO.RESERVADO_STATE))
			setHuecosReservados(numHuecos);
		else if (StringUtils.equals(estado, HuecoVO.INUTILIZADO_STATE))
			setHuecosInutilizados(numHuecos);
	}
}