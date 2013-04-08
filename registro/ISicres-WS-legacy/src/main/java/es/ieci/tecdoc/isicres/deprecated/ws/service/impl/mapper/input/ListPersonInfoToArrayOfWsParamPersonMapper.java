package es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.input;

import java.util.List;

import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamPerson;

public class ListPersonInfoToArrayOfWsParamPersonMapper implements Mapper {

	public Object map(Object obj) {
		
		List<PersonInfo> listaTerceros = (List<PersonInfo>)obj;
		ArrayOfWSParamPerson tercerosWS = new ArrayOfWSParamPerson();
		
		for (PersonInfo personInfo : listaTerceros) {
			WSParamPerson tercero = new WSParamPerson();
			
			tercero.setPersonName(personInfo.getPersonName());
			
			tercerosWS.getWSParamPerson().add(tercero);
		}
		
		return tercerosWS;
	}

}
