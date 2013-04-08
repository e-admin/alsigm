package transferencias.vos;

//TODO revisar si sigue siendo necesaria ... posiblemente con el udocPartID suficiente
public class UdocPartVO {
	// public UdocID udocID = null;
	public String udocID = null;
	public ParteUdocID udocPartID = null;
	public int numeroParte;

	// public boolean udocCompleta = true;

	public UdocPartVO(String udocID, int numeroParte) {
		this.udocID = udocID;
		this.udocPartID = new ParteUdocID(udocID, numeroParte);
		this.numeroParte = numeroParte;
	}

}
