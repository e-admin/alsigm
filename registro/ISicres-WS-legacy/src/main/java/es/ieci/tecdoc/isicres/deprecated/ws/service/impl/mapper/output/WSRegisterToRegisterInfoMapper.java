package es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.output;

import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSRegister;

public class WSRegisterToRegisterInfoMapper implements Mapper {

	public Object map(Object obj) {
		WSRegister registro = (WSRegister)obj;
		RegisterInfo registroResult = new RegisterInfo();
		registroResult.setBookId(String.valueOf(registro.getBookId()));
		registroResult.setDate(registro.getDate().toString());
		registroResult.setFolderId(String.valueOf(registro.getFolderId()));
		registroResult.setNumber(registro.getNumber());
		registroResult.setOffice(registro.getOffice());
		registroResult.setOfficeName(registro.getOfficeName());
		registroResult.setState(String.valueOf(registro.getState()));
		registroResult.setUserName(registro.getUserName());
		registroResult.setWorkDate(registro.getSystemDate().toString());
		return registroResult;
	}

}
