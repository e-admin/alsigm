package es.ieci.tecdoc.fwktd.dir3.api.vo.unidad;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Fichero_Organismos")
public class OrganismosVO {

	@XStreamImplicit()
	@XStreamAlias("Organismo")
	private List<OrganismoVO> organismos;

	public List<OrganismoVO> getOrganismos() {
		return organismos;
	}

	public void setOrganismos(List<OrganismoVO> organismos) {
		this.organismos = organismos;
	}
}
