package transferencias.vos;

public class MapDescUDocVO {

	String idFicha;
	String info;

	public MapDescUDocVO() {
	}

	public MapDescUDocVO(String idFicha, String info) {
		this.idFicha = idFicha;
		this.info = info;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
