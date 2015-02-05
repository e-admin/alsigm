package ieci.tecdoc.sgm.registropresencial.ws.client.axis;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RegistersInfo extends RetornoServicio {

	private RegisterInfo[] registers;

	public RegisterInfo[] getRegisters() {
		return registers;
	}

	public void setRegisters(RegisterInfo[] registers) {
		this.registers = registers;
	}

}
