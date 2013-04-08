package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class OficinaVO extends BaseOficinaVO {

	private static final long serialVersionUID = -4226096657459393603L;

	protected String codigoOficina;
	protected String acronimoOficina;
	protected String name;
	protected String idDepartamento;
	protected ZonaHorariaVO zonaHoraria;

	public ZonaHorariaVO getZonaHoraria() {
		return zonaHoraria;
	}

	public void setZonaHoraria(ZonaHorariaVO zonaHoraria) {
		this.zonaHoraria = zonaHoraria;
	}

	public String getCodigoOficina() {
		return codigoOficina;
	}

	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	public String getAcronimoOficina() {
		return acronimoOficina;
	}

	public void setAcronimoOficina(String acronimoOficina) {
		this.acronimoOficina = acronimoOficina;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

}
