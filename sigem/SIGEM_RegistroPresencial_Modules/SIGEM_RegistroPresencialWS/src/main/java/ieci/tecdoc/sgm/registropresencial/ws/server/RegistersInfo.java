package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Listado de registros
 *
 */
public class RegistersInfo extends RetornoServicio {

	/**
	 * Conjunto de registros
	 */
	private RegisterInfo[] registers;

	/**
	 * @return
	 */
	public RegisterInfo[] getRegisters() {
		return registers;
	}

	/**
	 * @param registers
	 */
	public void setRegisters(RegisterInfo[] registers) {
		this.registers = registers;
	}

}
