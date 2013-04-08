package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import com.ieci.tecdoc.common.isicres.AxSf;

import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegister;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>RegistroSalidaVO</code> en una instancia de
 * <code>WsOutputRegister</code>.
 *
 * @see RegistroSalidaVO
 * @see WSOutputRegister
 *
 * @author IECISA
 *
 */
public class RegistroSalidaVOToWSOutputRegisterMapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(RegistroSalidaVO.class, obj);

		RegistroSalidaVO registro = (RegistroSalidaVO) obj;
		WSOutputRegister result = new WSOutputRegister();

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

		result.setAddFields(new ArrayOfWSAddField());
		/*
		 * El comentario es el campo: 
		 * 
		 * Fld 18 para Entrada Fld 14 para Salida
		 */

			WSAddField addFieldComentario = new WSAddField();
			addFieldComentario.setFieldId(AxSf.FLD14_FIELD_ID);
			addFieldComentario.setValue(registro.getComentario());
			result.getAddFields().getWSAddField().add(addFieldComentario);

		if (null != registro.getCamposAdicionales()) {

			ArrayOfWSAddField fields = (ArrayOfWSAddField) new ListOfCampoAdicionalRegistroVOToArrayOfWSAddFieldMapper()
					.map(registro.getCamposAdicionales());
			result.getAddFields().getWSAddField()
					.addAll(fields.getWSAddField());
		}
		
		result.setDestination(registro.getUnidadAdministrativaDestino()
				.getCodigoUnidad());
		result.setDestinationName(registro.getUnidadAdministrativaDestino()
				.getName());
		result.setDocuments((ArrayOfWSDocument) new ListOfDocumentoRegistroVOToArrayOfWSDocumentMapper()
				.map(registro.getDocumentos()));
		result.setMatter(registro.getResumen());
		result.setMatterType(registro.getTipoAsunto().getCodigo());
		result.setMatterTypeName(registro.getTipoAsunto().getDescripcion()); /*
																			 * TODO:
																			 * Debería
																			 * ser
																			 * el
																			 * nombre
																			 * ,
																			 * pero
																			 * no
																			 * lo
																			 * tiene
																			 */

		// Terceros
		result.setPersons((ArrayOfWSPerson) new ListOfInteresadoVOToArrayOfWSPersonMapper()
				.map(registro.getInteresados()));

		result.setSender(registro.getUnidadAdministrativaOrigen()
				.getCodigoUnidad());
		result.setSenderName(registro.getUnidadAdministrativaOrigen().getName());
		result.setTransportNumber(registro.getTransporte()
				.getNumeroTransporte());
		result.setTransportType(registro.getTransporte().getTipoTransporte()
				.getDescripcion()); // TODO: ???

		return result;
	}
}
