package es.ieci.tecdoc.fwktd.dir3.api.vo.relacion;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Fichero_RelacionesOficinasOrganismos")
public class RelacionesUnidOrgOficinaVO {
	@XStreamImplicit()
	@XStreamAlias("OficinaOrganismo")
	private List<RelacionUnidOrgOficinaVO> relacionesUnidOrgOficinaVO;

	public List<RelacionUnidOrgOficinaVO> getRelacionesUnidOrgOficinaVO() {
		return relacionesUnidOrgOficinaVO;
	}

	public void setRelacionesUnidOrgOficinaVO(
			List<RelacionUnidOrgOficinaVO> relacionesUnidOrgOficinaVO) {
		this.relacionesUnidOrgOficinaVO = relacionesUnidOrgOficinaVO;
	}


}
