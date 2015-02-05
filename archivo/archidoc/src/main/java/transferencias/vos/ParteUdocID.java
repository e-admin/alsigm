package transferencias.vos;

/**
 * Cuando un expediente es dividido en partes porque es necesario dividirlo
 * entre varias unidades de instalacion cada una de las partes tiene como
 * identificador unico el par de valores identificador de unida documental y
 * numero de parte
 * 
 */
public class ParteUdocID {

	public String udocID;
	public int numeroParte;

	public ParteUdocID(String udocID, int numeroParte) {
		super();
		this.udocID = udocID;
		this.numeroParte = numeroParte;
	}

	public boolean equals(Object obj) {
		try {
			if (obj.getClass() == this.getClass()) {
				ParteUdocID objAsParteUdocID = (ParteUdocID) obj;
				return objAsParteUdocID.udocID.equals(this.udocID)
						&& objAsParteUdocID.numeroParte == this.numeroParte;
			}
			IParteUnidadDocumentalVO objAsUdocID = (IParteUnidadDocumentalVO) obj;
			if (this.udocID.equals(objAsUdocID.getIdUnidadDoc())
					&& objAsUdocID.getNumParteUdoc() == this.numeroParte)
				return true;
		} catch (ClassCastException cce) {
		}
		return false;
	}

	public String toString() {
		return udocID + ":" + numeroParte;
	}

}
