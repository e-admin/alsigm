package transferencias.vos;

public class RangoVO {

	String desde = null;
	String hasta = null;

	public RangoVO() {
	}

	public RangoVO(String desde, String hasta) {

		this.desde = desde;
		this.hasta = hasta;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

}
