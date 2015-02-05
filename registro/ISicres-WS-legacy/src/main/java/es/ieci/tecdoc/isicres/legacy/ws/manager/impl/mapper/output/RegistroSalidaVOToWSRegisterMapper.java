package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSRegister;

/**
 * Instancia de <code>Mapper</code> que transforma objetos de tipo
 * <code>RegistroSalidaVO</code> en objetos de tipo <code>WSRegister</code>.
 * 
 * @see RegistroSalidaVO
 * @see WSRegister
 * 
 * @author IECISA
 * 
 */
public class RegistroSalidaVOToWSRegisterMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(RegistroSalidaVO.class, obj);

		RegistroSalidaVO registro = (RegistroSalidaVO) obj;
		WSRegister result = new WSRegister();

		result.setBookId(Integer.valueOf(registro.getIdLibro()).intValue());
		if (null != registro.getFechaRegistro()) {
			result.setDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(registro.getFechaRegistro()));
		}
		result.setFolderId(Long.valueOf(registro.getIdRegistro()).intValue());
		result.setNumber(registro.getNumeroRegistro());
		result.setOffice(registro.getOficinaRegistro().getCodigoOficina());
		result.setOfficeName(registro.getOficinaRegistro().getName());
		result.setState(registro.getEstado().getValue());
		if (null != registro.getFechaAlta()) {
			result.setSystemDate(XMLGregorianCalendarHelper
					.toXMLGregorianCalendar(registro.getFechaAlta()));
		}
		result.setUserName(registro.getUsuarioRegistro().getLoginName());

		return result;
	}
}
