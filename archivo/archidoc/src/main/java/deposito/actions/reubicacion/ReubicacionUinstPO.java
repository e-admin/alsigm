package deposito.actions.reubicacion;

import common.bi.ServiceRepository;

import deposito.actions.hueco.HuecoPO;
import deposito.vos.HuecoVO;

public class ReubicacionUinstPO {

	private HuecoPO huecoOrigen;
	private String pathOrigen = null;
	private String signaturaOrigen = null;
	private String nombreFormatoOrigen = null;

	private HuecoPO huecoDestino;
	private String pathDestino = null;
	private String signaturaDestino = null;
	private String nombreFormatoDestino = null;

	public ReubicacionUinstPO(HuecoVO huecoOrigenVO, HuecoVO huecoDestinoVO,
			ServiceRepository services) {
		this.huecoOrigen = new HuecoPO(huecoOrigenVO, services);

		if (this.huecoOrigen != null) {
			this.pathOrigen = this.huecoOrigen.getPath();
			this.nombreFormatoOrigen = this.huecoOrigen.getNombreformato();

			if (this.huecoOrigen.getUnidInstalacion() != null) {
				this.signaturaOrigen = this.huecoOrigen.getUnidInstalacion()
						.getSignaturaui();
			}
		}

		this.huecoDestino = new HuecoPO(huecoDestinoVO, services);

		if (this.huecoDestino != null) {
			this.pathDestino = this.huecoDestino.getPath();
			this.nombreFormatoDestino = this.huecoDestino.getNombreformato();

			if (this.huecoDestino.getUnidInstalacion() != null) {
				this.signaturaDestino = this.huecoDestino.getUnidInstalacion()
						.getSignaturaui();
			}
		}
	}

	public HuecoPO getHuecoOrigen() {
		return huecoOrigen;
	}

	public void setHuecoOrigen(HuecoPO huecoOrigen) {
		this.huecoOrigen = huecoOrigen;
	}

	public HuecoPO getHuecoDestino() {
		return huecoDestino;
	}

	public void setHuecoDestino(HuecoPO huecoDestino) {
		this.huecoDestino = huecoDestino;
	}

	public String getPathOrigen() {
		return pathOrigen;
	}

	public void setPathOrigen(String pathOrigen) {
		this.pathOrigen = pathOrigen;
	}

	public String getSignaturaOrigen() {
		return signaturaOrigen;
	}

	public void setSignaturaOrigen(String signaturaOrigen) {
		this.signaturaOrigen = signaturaOrigen;
	}

	public String getNombreFormatoOrigen() {
		return nombreFormatoOrigen;
	}

	public void setNombreFormatoOrigen(String nombreFormatoOrigen) {
		this.nombreFormatoOrigen = nombreFormatoOrigen;
	}

	public String getPathDestino() {
		return pathDestino;
	}

	public void setPathDestino(String pathDestino) {
		this.pathDestino = pathDestino;
	}

	public String getSignaturaDestino() {
		return signaturaDestino;
	}

	public void setSignaturaDestino(String signaturaDestino) {
		this.signaturaDestino = signaturaDestino;
	}

	public String getNombreFormatoDestino() {
		return nombreFormatoDestino;
	}

	public void setNombreFormatoDestino(String nombreFormatoDestino) {
		this.nombreFormatoDestino = nombreFormatoDestino;
	}
}
