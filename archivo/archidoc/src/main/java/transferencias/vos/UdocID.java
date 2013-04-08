/*
 * Created on 10-may-2005
 *
 */
package transferencias.vos;

public class UdocID {
	public String value;

	public UdocID(String udocID) {
		this.value = udocID;
	}

	public boolean equals(Object otroObjeto) {
		if (otroObjeto instanceof UnidadDocumentalVO)
			return this.value.equals(((UnidadDocumentalVO) otroObjeto).id);
		return false;
	}
}
