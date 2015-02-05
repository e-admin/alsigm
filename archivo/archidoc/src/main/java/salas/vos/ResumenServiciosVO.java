package salas.vos;

import common.vos.BaseVO;

/**
 * VO que recoge la información sobre los servicios de consulta en sala
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ResumenServiciosVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	private Integer numUsuarios;
	private Integer numRegistros;
	private Integer numUnidades;

	public ResumenServiciosVO() {

	}

	public ResumenServiciosVO(int numUsuarios, int numRegistros, int numUnidades) {
		this.numUsuarios = new Integer(numUsuarios);
		this.numRegistros = new Integer(numRegistros);
		this.numUnidades = new Integer(numUnidades);
	}

	public Integer getNumUsuarios() {
		return numUsuarios;
	}

	public void setNumUsuarios(Integer numUsuarios) {
		this.numUsuarios = numUsuarios;
	}

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	public Integer getNumUnidades() {
		return numUnidades;
	}

	public void setNumUnidades(Integer numUnidades) {
		this.numUnidades = numUnidades;
	}
}