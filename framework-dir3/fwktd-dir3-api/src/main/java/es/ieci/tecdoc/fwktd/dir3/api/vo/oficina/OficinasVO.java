package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Fichero_Oficinas")
public class OficinasVO {

	@XStreamImplicit()
	@XStreamAlias("Oficina")
	private List<OficinaVO> oficinas;

	public List<OficinaVO> getOficinas() {
		return oficinas;
	}

	public void setOficinas(List<OficinaVO> oficinas) {
		this.oficinas = oficinas;
	}
}
