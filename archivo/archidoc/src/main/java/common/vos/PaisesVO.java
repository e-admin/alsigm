package common.vos;

import java.util.HashMap;
import java.util.Map;

/**
 * Datos de paises.
 */
public class PaisesVO {
	Map paises;

	public int getNumpaises() {
		return paises.size();
	}

	public PaisesVO() {
	}

	public PaisesVO(Map paises) {
		super();
		this.paises = paises;
	}

	public Map getPaises() {
		return paises;
	}

	public void setPaises(Map paises) {
		this.paises = paises;
	}

	public void addPais(PaisVO paisVO) {
		if (paises == null)
			paises = new HashMap();

		paises.put(paisVO.getCodigo(), paisVO);
	}
}
